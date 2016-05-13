(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgCopyReportCtrl', DlgCopyReportCtrl);

    DlgCopyReportCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgCopyReportCtrl($scope, $uibModalInstance, dlgData) {
        $scope.dlgData = dlgData;
        $scope.dlgData = {
            id:   $scope.dlgData.id,
            name: "Copy of " + $scope.dlgData.title
        };

        $scope.ok = function () {
            $uibModalInstance.close($scope.dlgData);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();