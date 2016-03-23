'use strict';

jiraPluginApp.controller('ProfileCtrl',
    ['$scope', '$routeParams', 'ProfileFactory', 'AuthenticationFactory', 'Notification',
        function($scope, $routeParams, ProfileFactory, AuthenticationFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;

//----------------------------------------------------------------------------------------------------------------------
//get profile data
            this.getProfile = function () {

                //ProfileFactory.get({}, function (data) {
                //    $scope.profile = data;
                //}, function (error) {
                //   Notification.error("Server error");
                //});

                $scope.profile = {
                    id:         1,
                    username:   "admin",
                    email:      "admin@j.com",
                    password:   "$2a$10$IuW9oZDlNablymo7s145F.lAhZD2z3toNaasFDB/ZwOTfJWuOrW.O",
                    status:     "ACTIVE"
                };

                $scope.loaderShow = false;
            };

            self.getProfile();

//----------------------------------------------------------------------------------------------------------------------
//save profile
            $scope.saveProfile = function () {
                ProfileFactory.update({username: AuthenticationFactory.user}, $scope.profile, function (data) {
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
                    ProfileFactory.update({username: AuthenticationFactory.user}, {
                        newPassword:        $scope.profile.newPassword,
                        newPasswordAgain:   $scope.profile.newPasswordAgain
                    }, function (result) {
                        Notification.success("Profile save success");
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }
            };
        }
    ]);
