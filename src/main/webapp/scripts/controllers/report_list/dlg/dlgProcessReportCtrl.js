(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgProcessReportCtrl', DlgProcessReportCtrl);

    DlgProcessReportCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData', 'UsersFactory', 'BoardsFactory'];

    function DlgProcessReportCtrl($scope, $uibModalInstance, dlgData, UsersFactory, BoardsFactory) {
        $scope.dlgData = {};

        var users = UsersFactory.query(function(){
            $scope.dlgData['users'] = users.users;
        });
        var boards = BoardsFactory.query(function(){
            $scope.dlgData['boards'] = boards.boards;
        });

        $scope.model = {
            typeId: 1
        };

        $scope.ok = function () {
            if($scope.processReportForm.$valid) {
                $uibModalInstance.close($scope.model);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();