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
  'ngMessages'
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
      }).when('/', {
        templateUrl: 'views/home.html',
        controller: 'HomeCtrl',
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
      }).when('/portfolio', {
        templateUrl: 'views/report_portfolio/portfolio.html',
        controller: 'PortfolioCtrl',
        access: {
          requiredLogin: true
        }
      }).when('/page2', {
        templateUrl: 'views/page2.html',
        controller: 'Page2Ctrl',
        access: {
          requiredLogin: true
        }
      }).otherwise({
        redirectTo: '/login'
      });
});

jiraPluginApp.run(function($rootScope, $window, $location, AuthenticationFactory) {
  // when the page refreshes, check if the user is already logged in
  AuthenticationFactory.check();

  $rootScope.$on("$routeChangeStart", function(event, nextRoute, currentRoute) {
    if ((nextRoute.access && nextRoute.access.requiredLogin) && !AuthenticationFactory.isLogged) {
      $location.path("/login");
    } else {
      // check if user object exists else fetch it. This is incase of a page refresh
      if (!AuthenticationFactory.user) {
        AuthenticationFactory.user = $window.localStorage.user;
      }
      if (!AuthenticationFactory.userRole) {
        AuthenticationFactory.userRole = $window.localStorage.userRole;
      }
    }
  });

  $rootScope.$on('$routeChangeSuccess', function(event, nextRoute, currentRoute) {
    $rootScope.showMenu = AuthenticationFactory.isLogged;
    $rootScope.role = AuthenticationFactory.userRole;
    // if the user is already logged in, take him to the home page
    if (AuthenticationFactory.isLogged === true && $location.path() === '/login') {
      $location.path('/');
    }
  });
});
