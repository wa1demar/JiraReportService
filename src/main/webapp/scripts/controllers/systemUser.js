'use strict';

jiraPluginApp.controller('SystemUserCtrl',
    ['$scope', '$routeParams', '$uibModal', 'SystemUsersFactory', 'Notification',
        function($scope, $routeParams, $uibModal, SystemUsersFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;

//----------------------------------------------------------------------------------------------------------------------
//Order reports
            $scope.predicate = 'id';
            $scope.reverse = false;
            $scope.order = function(predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            };

//----------------------------------------------------------------------------------------------------------------------
//get data
            this.getSystemUsers = function () {
                $scope.dataUsers = [];
                SystemUsersFactory.query({}, function (data) {
                    $scope.dataUsers = data.users;
                }, function (error) {
                   Notification.error("Server error");
                });

                $scope.loaderShow = false;
            };

            self.getSystemUsers();


//----------------------------------------------------------------------------------------------------------------------
//Dlg add/edit user
            $scope.processSystemUser = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/system_user/dlg/dlg_system_user.html',
                    controller: 'DlgProcessSystemUserCtrl',
                    size: 'md',
                    resolve: {
                        dlgData: function () {
                            return {
                                item: item
                            };
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    if (data.type === "edit") {
                        var id = data.id;

                        delete data.id;
                        delete data.type;

                        SystemUsersFactory.update({id: id}, data, function(result){
                            self.getSystemUsers();
                            Notification.success("User edit success");
                        }, function (error) {
                            Notification.error("Server error");
                        });
                    } else {
                        delete data.type;
                        SystemUsersFactory.add({}, data, function(result){
                            self.getSystemUsers();
                            Notification.success("User add success");
                        }, function (error) {
                            Notification.error("Server error");
                        });
                    }
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg invite user
            $scope.inviteUser = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    backdrop: 'static',
                    templateUrl: 'views/system_user/dlg/dlg_invite_system_user.html',
                    controller: 'DlgInviteSystemUserCtrl',
                    size: 'md',
                    resolve: {
                        dlgData: function () {
                            return {
                                item: {
                                    inviteType: 1,
                                    dataUsers: $scope.dataUsers
                                }
                            };
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    self.getSystemUsers();
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg reset password user
            $scope.resetPasswordSystemUser = function (item) {
                console.log(item);
                var modalInstance = $uibModal.open({
                    animation: true,
                    backdrop: 'static',
                    templateUrl: 'views/system_user/dlg/dlg_reset_password_system_user.html',
                    controller: 'DlgResetPasswordSystemUserCtrl',
                    size: 'md',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    self.getSystemUsers();
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete user
            $scope.deleteSystemUser = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dlg/dlg_delete_element.html',
                    controller: 'DlgDeleteCtrl',
                    size: 'sm',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    SystemUsersFactory.delete({id: data.id}, function(result){
                        self.getSystemUsers();
                        Notification.success("User delete success");
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

        }
]);

jiraPluginApp.controller('DlgProcessSystemUserCtrl', ['$scope', '$uibModalInstance', 'dlgData',
    function ($scope, $uibModalInstance, dlgData) {
        $scope.item = dlgData.item;
        if ($scope.item === undefined) {
            $scope.item = {
                type:   "add",
                status: "active"
            };
        } else {
            $scope.item["type"] = "edit";
        }

        $scope.ok = function () {
            if($scope.systemUserForm.$valid) {
                $uibModalInstance.close($scope.item);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);

jiraPluginApp.controller('DlgInviteSystemUserCtrl', ['$scope', '$uibModalInstance', 'dlgData', 'UsersFactory', 'SystemUsersFactory', 'Notification',
    function ($scope, $uibModalInstance, dlgData, UsersFactory, SystemUsersFactory, Notification) {
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
]);

jiraPluginApp.controller('DlgResetPasswordSystemUserCtrl', ['$scope', '$uibModalInstance', 'dlgData', 'UsersFactory', 'SystemUsersFactory', 'Notification',
    function ($scope, $uibModalInstance, dlgData, UsersFactory, SystemUsersFactory, Notification) {
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
]);