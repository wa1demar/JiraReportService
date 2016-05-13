(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('userAuthFactory', userAuthFactory);

    userAuthFactory.$inject = ['$window', '$location', '$http', 'authenticationFactory'];

    function userAuthFactory($window, $location, $http, authenticationFactory) {
        return {
            login: function(username, password) {
                return $http({
                    url: '/rest/auth/login',
                    method:"POST",
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    transformRequest: function(obj) {
                        var str = [];
                        for(var p in obj) {
                            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                        }
                        return str.join("&");
                    },
                    data: {
                        username: username,
                        password: password
                    }
                });
            },
            logout: function() {
                if (authenticationFactory.isLogged) {
                    $http({
                        url: '/rest/auth/logout',
                        method:"POST",
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    });

                    authenticationFactory.isLogged = false;
                    delete authenticationFactory.user;
                    delete authenticationFactory.roles;

                    delete $window.localStorage.token;
                    delete $window.localStorage.user;
                    delete $window.localStorage.roles;

                    $location.path("/login");
                }
            }
        };
    }

})();
