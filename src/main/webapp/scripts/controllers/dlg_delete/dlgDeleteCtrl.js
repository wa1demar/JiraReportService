(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgDeleteCtrl', DlgDeleteCtrl);

    DlgDeleteCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgDeleteCtrl($scope, $uibModalInstance, dlgData) {
        console.log(dlgData);
        $scope.dlgData = dlgData;

        $scope.ok = function () {
            $uibModalInstance.close($scope.dlgData);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();