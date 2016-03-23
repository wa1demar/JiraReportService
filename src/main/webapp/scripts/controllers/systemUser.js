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
                    $scope.dataUsers = data;
                }, function (error) {
                   Notification.error("Server error");
                });

                $scope.dataUsers = [
                    {
                        id:         1,
                        username:   "test1",
                        fullName:   "fullName test",
                        email:      "fullName@gmail.com",
                        status:     "PAUSE"
                    },
                    {
                        id:         2,
                        username:   "test2",
                        fullName:   "fullName test",
                        email:      "fullName@gmail.com",
                        status:     "PAUSE"
                    },
                    {
                        id:         3,
                        username:   "test3",
                        fullName:   "fullName test",
                        email:      "fullName@gmail.com",
                        status:     "ACTIVE"
                    }
                ];

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
                    templateUrl: 'views/system_user/dlg/dlg_invite_system_user.html',
                    controller: 'DlgInviteSystemUserCtrl',
                    size: 'md',
                    resolve: {
                        dlgData: function () {
                            return {
                                item: {
                                    inviteType: 1
                                }
                            };
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    SystemUsersFactory.add({id: "invite"}, {
                        inviteParam: data.inviteType === 1 ? data.username : data.email
                    }, function(result){
                        self.getSystemUsers();
                        Notification.success("Invite send success");
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete user
            $scope.deleteSystemUser = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dlg/dlg_delete_element.html',
                    controller: 'DlgDeleteSystemUserCtrl',
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

jiraPluginApp.controller('DlgInviteSystemUserCtrl', ['$scope', '$uibModalInstance', 'dlgData', 'UsersFactory',
    function ($scope, $uibModalInstance, dlgData, UsersFactory) {
        $scope.item = dlgData.item;
        UsersFactory.query(function(data) {
            $scope.jiraUsers = data.users;
        });
        $scope.ok = function () {
            if($scope.inviteSystemUserForm.$valid) {
                $uibModalInstance.close($scope.item);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);

jiraPluginApp.controller('DlgDeleteSystemUserCtrl', ['$scope', '$uibModalInstance', 'dlgData',
    function ($scope, $uibModalInstance, dlgData) {
        $scope.dlgData = dlgData;

        $scope.ok = function () {
            $uibModalInstance.close($scope.dlgData);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);