(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgAddMemberCtrl', DlgAddMemberCtrl);

    DlgAddMemberCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgAddMemberCtrl($scope, $uibModalInstance, dlgData) {
        $scope.members = dlgData.members;
        $scope.locations = dlgData.locations;
        $scope.technologies = dlgData.technologies;
        $scope.engineerLevelDictionary = dlgData.engineerLevelDictionary;
        $scope.assignmentTypes = dlgData.assignmentTypes;

        $scope.ok = function () {
            if($scope.addMemberForm.$valid) {
                $uibModalInstance.close($scope.model);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();