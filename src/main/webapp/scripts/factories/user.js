'use strict';

jiraPluginApp.factory('UsersFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/users', {}, {
    query:  { method: 'GET' }
  });
}]);