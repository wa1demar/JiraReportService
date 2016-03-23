'use strict';

jiraPluginApp.controller('ProfileCtrl',
    ['$scope', '$routeParams', 'ProfileFactory', 'AuthenticationFactory', 'UserAuthFactory', 'Notification',
        function($scope, $routeParams, ProfileFactory, AuthenticationFactory, UserAuthFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;

//----------------------------------------------------------------------------------------------------------------------
//get profile data
            this.getProfile = function () {

                ProfileFactory.get({}, function (data) {
                    $scope.profile = data;
                }, function (error) {
                   Notification.error("Server error");
                });

                $scope.loaderShow = false;
            };

            self.getProfile();

//----------------------------------------------------------------------------------------------------------------------
//save profile
            $scope.saveProfile = function () {
                ProfileFactory.update({}, $scope.profile, function (data) {
                    self.getProfile();
                    Notification.success("Profile save success");
                }, function (error) {
                   Notification.error("Server error");
                });
            };

//----------------------------------------------------------------------------------------------------------------------
//change password
            $scope.changePassword = function () {
                $scope.changePasswordError = {
                    status: false,
                    message: ""
                };

                if ($scope.profile.newPassword !== $scope.profile.newPasswordAgain) {
                    $scope.changePasswordError = {
                        status: true,
                        message: "Passwords do not match"
                    };
                } else {
                    ProfileFactory.update({action: "change_password"}, {
                        oldPassword:        $scope.profile.oldPassword,
                        newPassword:        $scope.profile.newPassword,
                        newPasswordAgain:   $scope.profile.newPasswordAgain
                    }, function (result) {
                        Notification.success("Change password success");
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }
            };
        }
    ]);
