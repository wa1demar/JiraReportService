(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .run(runBlock);

    runBlock.$inject = ['$rootScope', '$window', '$location', '$filter', 'authenticationFactory', 'editableOptions', 'CONFIG'];

    function runBlock($rootScope, $window, $location, $filter, authenticationFactory, editableOptions, CONFIG) {
        $rootScope.CONFIG = CONFIG;

        //For angular-xeditable
        editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'

        // when the page refreshes, check if the user is already logged in
        authenticationFactory.check();

        $rootScope.$on("$routeChangeStart", function(event, nextRoute, currentRoute) {
            //Check session on server
            authenticationFactory.checkSession();

            if ((nextRoute.access && nextRoute.access.requiredLogin) && !authenticationFactory.isLogged) {
                $location.path("/login");
            } else {
                // check if user object exists else fetch it. This is incase of a page refresh
                if (!authenticationFactory.user) {
                    authenticationFactory.user = $window.localStorage.user;
                }
                if (!authenticationFactory.roles && $window.localStorage.roles) {
                    authenticationFactory.roles = JSON.parse($window.localStorage.roles);
                }
            }
        });

        $rootScope.$on('$routeChangeSuccess', function(event, nextRoute, currentRoute) {
            $rootScope.showMenu = authenticationFactory.isLogged;
            $rootScope.roles = authenticationFactory.roles;
            $rootScope.isAdmin = $filter('hasRole')(authenticationFactory.roles, "ROLE_ADMIN");
            $rootScope.isCurrentUser = function (data) {
                var res = false;
                if (data && data.admins) {
                    $.each(data.admins, function (index, value) {
                        console.log(value.login);
                        console.log(value.login === authenticationFactory.user);
                        if (value.login === authenticationFactory.user) {
                            res = true;
                        }
                    });
                }

                if($rootScope.isAdmin) {
                    res = true;
                }

                return res;
            };

            // if the user is already logged in, take him to the home page
            if (authenticationFactory.isLogged === true && $location.path() === '/login') {
                $location.path('/');
            }
        });
    }

})();