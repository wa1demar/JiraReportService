'use strict';

jiraPluginApp.factory('AuthenticationFactory', ['$window', function($window) {
  var auth = {
    isLogged: false,
    check: function() {
      if ($window.localStorage.token && $window.localStorage.user) {
        this.isLogged = true;
      } else {
        this.isLogged = false;
        delete this.user;
      }
    }
  };

  return auth;
}]);

jiraPluginApp.factory('UserAuthFactory', ['$window', '$location', '$http', 'AuthenticationFactory', 'CONFIG', function($window, $location, $http, AuthenticationFactory, CONFIG) {
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
