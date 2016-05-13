(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgProcessProjectCtrl', DlgProcessProjectCtrl);

    DlgProcessProjectCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgProcessProjectCtrl($scope, $uibModalInstance, dlgData) {
        $scope.model = dlgData;

        $scope.ok = function () {
            if($scope.processProjectForm.$valid) {
                $uibModalInstance.close($scope.model);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();