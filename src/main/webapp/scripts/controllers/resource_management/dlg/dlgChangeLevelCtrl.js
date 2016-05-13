(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgChangeLevelCtrl', DlgChangeLevelCtrl);

    DlgChangeLevelCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgChangeLevelCtrl($scope, $uibModalInstance, dlgData) {
        $scope.model = dlgData.currentMember;
        $scope.engineerLevelDictionary = dlgData.engineerLevelDictionary;

        $scope.ok = function () {
            $uibModalInstance.close($scope.model);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();