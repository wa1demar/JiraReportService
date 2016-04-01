'use strict';

jiraPluginApp.controller('LoginCtrl', ['$scope', '$window', '$location', 'UserAuthFactory', 'AuthenticationFactory',
    function($scope, $window, $location, UserAuthFactory, AuthenticationFactory) {
        $scope.user = {
            username: '',
            password: ''
        };
        $scope.loginError = {
            status: false,
            message: ''
        };

        $scope.login = function() {
            if($scope.loginForm.$valid) {
                var username = $scope.user.username,
                    password = $scope.user.password;
                if (username !== undefined && password !== undefined) {
                    UserAuthFactory.login(username, password).success(function(data) {
                        if (data.status === "error") {
                            $scope.loginError.status = true;
                            $scope.loginError.message = data.message;
                        } else {
                            AuthenticationFactory.isLogged = true;
                            AuthenticationFactory.user = data.user.username;
                            AuthenticationFactory.roles = data.user.roles;


                            $window.localStorage.token = data.token;
                            $window.localStorage.user = data.user.username; // to fetch the user details on refresh
                            $window.localStorage.roles = JSON.stringify(data.user.roles); // to fetch the user details on refresh

                            $location.path("/");
                        }

                    }).error(function(status) {
                        $scope.loginError = {
                            status: true,
                            message: 'Wrong username or password!'
                        };
                    });
                } else {
                    $scope.loginError = {
                        status: true,
                        message: 'Invalid credentials!'
                    };
                }
            }

        };

    }
]);
