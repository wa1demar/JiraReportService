(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgProcessConfigCtrl', DlgProcessConfigCtrl);

    DlgProcessConfigCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData', 'ConfigFactory'];

    function DlgProcessConfigCtrl($scope, $uibModalInstance, dlgData, ConfigFactory) {
        $scope.dlgData = dlgData;

        var boards = ConfigFactory.get(function(){
            $scope.configData = boards;
        });

        $scope.ok = function () {
            $uibModalInstance.close($scope.configData);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();
