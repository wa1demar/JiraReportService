(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('SystemUserCtrl', SystemUserCtrl);

    SystemUserCtrl.$inject = ['$scope', '$routeParams', '$uibModal', 'SystemUsersFactory', 'Notification'];

    function SystemUserCtrl($scope, $routeParams, $uibModal, SystemUsersFactory, Notification) {
        var self = this;
        $scope.loaderShow = true;

        //Order reports
        $scope.predicate = 'id';
        $scope.reverse = false;
        $scope.order = function(predicate) {
            $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
            $scope.predicate = predicate;
        };

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

        //Dlg add/edit user
        $scope.processSystemUser = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/system_user/dlg/dlg_system_user.html',
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

        //Dlg invite user
        $scope.inviteUser = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: 'scripts/controllers/system_user/dlg/dlg_invite_system_user.html',
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

        //Dlg reset password user
        $scope.resetPasswordSystemUser = function (item) {
            console.log(item);
            var modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: 'scripts/controllers/system_user/dlg/dlg_reset_password_system_user.html',
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

        //Dlg delete user
        $scope.deleteSystemUser = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/dlg_delete/dlg_delete_element.html',
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

})();