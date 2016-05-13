(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgProcessColumnCtrl', DlgProcessColumnCtrl);

    DlgProcessColumnCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgProcessColumnCtrl($scope, $uibModalInstance, dlgData) {
        $scope.model = dlgData;

        $scope.ok = function () {
            if($scope.processColumnForm.$valid) {
                $uibModalInstance.close($scope.model);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();