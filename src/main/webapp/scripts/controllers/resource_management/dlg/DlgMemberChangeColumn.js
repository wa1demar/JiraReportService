(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgMemberChangeColumn', DlgMemberChangeColumn);

    DlgMemberChangeColumn.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgMemberChangeColumn($scope, $uibModalInstance, dlgData) {
        $scope.dlgData = dlgData;

        $scope.ok = function () {
            $uibModalInstance.close($scope.model);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();