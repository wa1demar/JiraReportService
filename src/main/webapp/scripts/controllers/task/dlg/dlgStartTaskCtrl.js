(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgStartTaskCtrl', DlgStartTaskCtrl);

    DlgStartTaskCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgStartTaskCtrl($scope, $uibModalInstance, dlgData) {
        $scope.dlgData = dlgData;

        $scope.ok = function () {
            $uibModalInstance.close($scope.dlgData);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();