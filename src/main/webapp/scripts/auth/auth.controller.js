'use strict';

jiraPluginApp.controller('LoginCtrl', ['$scope', '$window', '$location', 'UserAuthFactory', 'AuthenticationFactory',
    function($scope, $window, $location, UserAuthFactory, AuthenticationFactory) {
        $scope.user = {
            username: 'admin',
            password: 'password'
        };
        $scope.loginError = {
            status: false,
            message: ''
        };

        $scope.login = function() {
            console.log($scope.user);

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
                            AuthenticationFactory.userRole = data.user.role;

                            $window.localStorage.token = data.token;
                            $window.localStorage.user = data.user.username; // to fetch the user details on refresh
                            $window.localStorage.userRole = data.user.role; // to fetch the user details on refresh

                            $location.path("/");
                        }

                    }).error(function(status) {
                        $scope.loginError = {
                            status: true,
                            message: 'Oops something went wrong!'
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
