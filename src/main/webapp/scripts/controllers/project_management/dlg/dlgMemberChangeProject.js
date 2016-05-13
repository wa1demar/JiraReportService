(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgMemberChangeProject', DlgMemberChangeProject);

    DlgMemberChangeProject.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgMemberChangeProject($scope, $uibModalInstance, dlgData) {
        $scope.dlgData = dlgData;

        $scope.ok = function () {
            $uibModalInstance.close($scope.model);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();