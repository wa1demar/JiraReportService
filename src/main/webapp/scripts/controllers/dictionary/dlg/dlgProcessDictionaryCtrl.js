(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgProcessDictionaryCtrl', DlgProcessDictionaryCtrl);

    DlgProcessDictionaryCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgProcessDictionaryCtrl($scope, $uibModalInstance, dlgData) {
        $scope.model = dlgData;

        $scope.ok = function () {
            if($scope.dictionaryForm.$valid) {
                $uibModalInstance.close($scope.model);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();