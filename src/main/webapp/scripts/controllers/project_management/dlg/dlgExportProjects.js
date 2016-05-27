(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgExportProjects', DlgExportProjects);

    DlgExportProjects.$inject = ['$scope', '$uibModalInstance', 'dlgData', 'ProjectFactory', 'Notification'];

    function DlgExportProjects($scope, $uibModalInstance, dlgData, ProjectFactory, Notification) {
        $scope.dlgData = dlgData;
        $scope.downloadLink = "";
        $scope.loaderShow = true;

        $scope.exportProjectsData = function () {
            ProjectFactory.create({id: 'export'}, function(data){
                $scope.downloadLink = data.downloadLink;
                $scope.loaderShow = false;
                Notification.success("Export projects success");
            }, function (error) {
                Notification.error("Server error: export projects");
                // $scope.cancel();
            });
        };
        $scope.exportProjectsData();

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();