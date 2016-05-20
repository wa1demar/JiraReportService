(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgAddMemberProjectCtrl', DlgAddMemberProjectCtrl);

    DlgAddMemberProjectCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgAddMemberProjectCtrl($scope, $uibModalInstance, dlgData) {
        $scope.projects = dlgData.projects;
        $scope.assignmentTypes = dlgData.assignmentTypes;
        $scope.currentMember = dlgData.currentMember;

        $scope.ok = function () {
            if($scope.addMemberProjectForm.$valid) {
                $uibModalInstance.close($scope.model);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();