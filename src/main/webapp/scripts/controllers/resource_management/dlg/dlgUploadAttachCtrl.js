(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgUploadAttachCtrl', DlgUploadAttachCtrl);

    DlgUploadAttachCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData', 'FileUploader'];

    function DlgUploadAttachCtrl($scope, $uibModalInstance, dlgData, FileUploader) {
        // $scope.model = dlgData.currentMember;
        // $scope.engineerLevelDictionary = dlgData.engineerLevelDictionary;

        var result = {};

        //test upload
        var uploader = $scope.uploader = new FileUploader({
            url: 'rest/v1/members/'+dlgData.currentMember.login+'/attachment'
        });

        // FILTERS
        uploader.filters.push({
            name: 'customFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });
        // CALLBACKS

        uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function(fileItem) {
            console.info('onAfterAddingFile', fileItem);
        };
        uploader.onAfterAddingAll = function(addedFileItems) {
            console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function(item) {
            console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function(fileItem, progress) {
            console.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function(progress) {
            console.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function(fileItem, response, status, headers) {
            console.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function(fileItem, response, status, headers) {
            console.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.onCancelItem = function(fileItem, response, status, headers) {
            console.info('onCancelItem', fileItem, response, status, headers);
        };
        uploader.onCompleteItem = function(fileItem, response, status, headers) {
            console.info('onCompleteItem', fileItem, response, status, headers);
            result = response;
        };
        uploader.onCompleteAll = function(data) {
            console.info('onCompleteAll');
            $uibModalInstance.close(result);
        };

        console.info('uploader', uploader);

        // $scope.ok = function () {
        //
        // };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();