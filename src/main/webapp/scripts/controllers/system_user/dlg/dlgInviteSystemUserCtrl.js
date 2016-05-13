(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgInviteSystemUserCtrl', DlgInviteSystemUserCtrl);

    DlgInviteSystemUserCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData', 'UsersFactory', 'SystemUsersFactory', 'Notification'];

    function DlgInviteSystemUserCtrl($scope, $uibModalInstance, dlgData, UsersFactory, SystemUsersFactory, Notification) {
        $scope.item = dlgData.item;
        var dataUsers = dlgData.item.dataUsers;

        UsersFactory.query(function(data) {
            $scope.jiraUsers = data.users;
            for (var index = 0; index < $scope.jiraUsers.length; index++) {
                for (var indexTeam = 0; indexTeam < dataUsers.length; indexTeam++) {
                    if ($scope.jiraUsers[index].login === dataUsers[indexTeam].username) {
                        $scope.jiraUsers.splice(index, 1);
                    }
                }
            }

        });
        $scope.ok = function () {
            if($scope.inviteSystemUserForm.$valid) {
                $scope.dlgLoaderShow = true;
                SystemUsersFactory.add({id: "invite"}, {
                    inviteParam: $scope.item.inviteType === 1 ? $scope.item.username : $scope.item.email
                }, function(result){
                    Notification.success("Invite send success");
                    $scope.dlgLoaderShow = false;
                    $uibModalInstance.close($scope.item);
                }, function (error) {
                    Notification.error("Server error");
                    $scope.dlgLoaderShow = false;
                    $uibModalInstance.close($scope.item);
                });
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();