'use strict';

var jiraPluginApp = angular.module('jiraPluginApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ngTouch',
    'ui.bootstrap',
    'ui.select2',
    'ngMessages',
    'chart.js',
    'ui-notification',
    'nl2br',
    'angularUtils.directives.dirPagination',
    'dndLists'
]);

jiraPluginApp.config(function($routeProvider, $httpProvider, CONFIG) {

    $httpProvider.interceptors.push('TokenInterceptor');

    //FIXME need for auth
    $httpProvider.defaults.withCredentials = true;
    //$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    $routeProvider
        .when('/login', {
            templateUrl: 'views/auth/login.html',
            controller: 'LoginCtrl',
            access: {
                requiredLogin: false
            }
        }).when('/dashboards', {
            templateUrl: 'views/home.html',
            controller: 'HomeCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/', {
            templateUrl: 'views/report_portfolio/main.html',
            controller: 'PortfolioCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/report/:reportId', {
            templateUrl: 'views/report_element/report_element.html',
            controller: 'ReportElementCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/report/:reportId/configure', {
            templateUrl: 'views/report_configure/configure.html',
            controller: 'ConfigureCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/profile', {
            templateUrl: 'views/user/profile.html',
            controller: 'ProfileCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/system_user', {
            templateUrl: 'views/system_user/main.html',
            controller: 'SystemUserCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/task', {
            templateUrl: 'views/task/main.html',
            controller: 'TaskCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/due_date_issue', {
            templateUrl: 'views/due_date_issue/main.html',
            controller: 'DueDateIssueCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/productivity', {
            templateUrl: 'views/productivity/main.html',
            controller: 'ProductivityCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/resource_management', {
            templateUrl: 'views/resource_management/main.html',
            controller: 'ResourceManagementCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/dictionary/:name', {
            templateUrl: 'views/dictionary/main.html',
            controller: 'DictionaryCtrl',
            access: {
                requiredLogin: true
            }
        }).otherwise({
            redirectTo: '/login'
        });
});

jiraPluginApp.run(function($rootScope, $window, $location, $filter, AuthenticationFactory, CONFIG) {
    $rootScope.CONFIG = CONFIG;

    // when the page refreshes, check if the user is already logged in
    AuthenticationFactory.check();

    $rootScope.$on("$routeChangeStart", function(event, nextRoute, currentRoute) {
        //Check session on server
        AuthenticationFactory.checkSession();

        if ((nextRoute.access && nextRoute.access.requiredLogin) && !AuthenticationFactory.isLogged) {
            $location.path("/login");
        } else {
            // check if user object exists else fetch it. This is incase of a page refresh
            if (!AuthenticationFactory.user) {
                AuthenticationFactory.user = $window.localStorage.user;
            }
            if (!AuthenticationFactory.roles && $window.localStorage.roles) {
                AuthenticationFactory.roles = JSON.parse($window.localStorage.roles);
            }
        }
    });

    $rootScope.$on('$routeChangeSuccess', function(event, nextRoute, currentRoute) {
        $rootScope.showMenu = AuthenticationFactory.isLogged;
        $rootScope.roles = AuthenticationFactory.roles;
        $rootScope.isAdmin = $filter('hasRole')(AuthenticationFactory.roles, "ROLE_ADMIN");

        // if the user is already logged in, take him to the home page
        if (AuthenticationFactory.isLogged === true && $location.path() === '/login') {
            $location.path('/');
        }
    });
});
