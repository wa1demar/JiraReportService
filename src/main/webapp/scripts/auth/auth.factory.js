'use strict';

jiraPluginApp.factory('AuthenticationFactory', ['$window', '$http', 'CONFIG', '$q', function($window, $http, CONFIG, $q) {
    var auth = {
        isLogged: false,
        check: function() {
            var self = this;
            if ($window.localStorage.token && $window.localStorage.user) {
                self.isLogged = true;
            } else {
                self.isLogged = false;
                delete self.user;
            }
        },
        checkSession: function () {
            //Check session
            var self = this;
            var def = $q.defer();

            $http({
                method: 'GET',
                url: CONFIG.API_PATH + '/check_session'
            }).then(function successCallback(response) {
                if ($window.localStorage.token && $window.localStorage.user) {
                    self.isLogged = true;
                } else {
                    self.isLogged = false;
                    delete self.user;
                }
                def.resolve(response);
            }, function errorCallback(response) {
                if (response.status === 401) {
                    console.log("Error status 401");
                    self.isLogged = false;
                    delete self.user;

                    delete $window.localStorage.token;
                    delete $window.localStorage.user;
                    delete $window.localStorage.userRole;
                }
                def.reject();
            });

            return def.promise;
        }
    };

    return auth;
}]);

jiraPluginApp.factory('UserAuthFactory', ['$window', '$location', '$http', 'AuthenticationFactory', function($window, $location, $http, AuthenticationFactory) {
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
            if (AuthenticationFactory.isLogged) {
                $http({
                    url: '/rest/auth/logout',
                    method:"POST",
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                });

                AuthenticationFactory.isLogged = false;
                delete AuthenticationFactory.user;
                delete AuthenticationFactory.userRole;

                delete $window.localStorage.token;
                delete $window.localStorage.user;
                delete $window.localStorage.userRole;

                $location.path("/login");
            }
        }
    };
}]);

jiraPluginApp.factory('TokenInterceptor', ['$q', '$window', function($q, $window) {
    return {
        request: function(config) {
            config.headers = config.headers || {};
            config.headers['Content-Type'] = "application/x-www-form-urlencoded";

            if ($window.localStorage.token) {
                // config.headers['X-Access-Token'] = $window.localStorage.token;
                //config.headers['X-Key'] = $window.localStorage.user;

                //config.headers['X-AUTH-TOKEN'] = $window.localStorage.token;
                // config.headers['XSRF-TOKEN'] = $window.localStorage.token;

                config.headers['Content-Type'] = "application/json";

                // config.headers['Content-Type'] = "application/x-www-form-urlencoded";
            }
            return config || $q.when(config);
        },

        response: function(response) {
            return response || $q.when(response);
        }
    };
}]);
