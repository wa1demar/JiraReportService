'use strict';

jiraPluginApp.factory('ConfigFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/config', {}, {
    get:    { method: 'GET', isArray: false },
    update: { method: 'PATCH'}
  });
}]);
