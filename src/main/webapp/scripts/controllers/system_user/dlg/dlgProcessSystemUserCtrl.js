(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgProcessSystemUserCtrl', DlgProcessSystemUserCtrl);

    DlgProcessSystemUserCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgProcessSystemUserCtrl($scope, $uibModalInstance, dlgData) {
        $scope.item = dlgData.item;
        if ($scope.item === undefined) {
            $scope.item = {
                type:   "add",
                status: "active"
            };
        } else {
            $scope.item["type"] = "edit";
        }

        $scope.ok = function () {
            if($scope.systemUserForm.$valid) {
                $uibModalInstance.close($scope.item);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();