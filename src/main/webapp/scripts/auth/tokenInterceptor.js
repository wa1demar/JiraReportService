(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('tokenInterceptor', tokenInterceptor);

    tokenInterceptor.$inject = ['$q', '$window'];

    function tokenInterceptor($q, $window) {
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
    }

})();
