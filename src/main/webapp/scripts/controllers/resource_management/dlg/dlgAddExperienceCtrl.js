(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgAddExperienceCtrl', DlgAddExperienceCtrl);

    DlgAddExperienceCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgAddExperienceCtrl($scope, $uibModalInstance, dlgData) {
        $scope.technologies = dlgData.technologies;

        $scope.ok = function () {
            if($scope.addExperienceForm.$valid) {
                $uibModalInstance.close($scope.model);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();