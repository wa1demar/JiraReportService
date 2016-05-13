(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('authenticationFactory', authenticationFactory);

    authenticationFactory.$inject = ['$window', '$http', '$q', '$location', 'CONFIG'];

    function authenticationFactory($window, $http, $q, $location, CONFIG) {
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
                        delete $window.localStorage.roles;

                        $location.path("/login");
                    }
                    def.reject();
                });

                return def.promise;
            }
        };

        return auth;
    }

})();