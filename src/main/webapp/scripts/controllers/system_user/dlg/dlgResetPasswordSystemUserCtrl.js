(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgResetPasswordSystemUserCtrl', DlgResetPasswordSystemUserCtrl);

    DlgResetPasswordSystemUserCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData', 'UsersFactory', 'SystemUsersFactory', 'Notification'];

    function DlgResetPasswordSystemUserCtrl($scope, $uibModalInstance, dlgData, UsersFactory, SystemUsersFactory, Notification) {
        $scope.item = dlgData;
        console.log($scope.item);
        $scope.ok = function () {
            $scope.dlgLoaderShow = true;
            SystemUsersFactory.resetPassword({id: $scope.item.id, action: "reset_password"}, function(result){
                Notification.success("Reset password success");
                $scope.dlgLoaderShow = false;
                $uibModalInstance.close($scope.item);
            }, function (error) {
                Notification.error("Server error");
                $scope.dlgLoaderShow = false;
                $uibModalInstance.close($scope.item);
            });
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();