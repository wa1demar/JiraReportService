(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgMemberChangeColumn', DlgMemberChangeColumn);

    DlgMemberChangeColumn.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgMemberChangeColumn($scope, $uibModalInstance, dlgData) {
        $scope.dlgData = dlgData;

        $scope.ok = function () {
            $scope.model['btnType'] = "ok";
            $uibModalInstance.close($scope.model);
        };

        $scope.cancel = function () {
            $scope.model['btnType'] = "cancel";
            $uibModalInstance.close($scope.model);
        };
    }

})();