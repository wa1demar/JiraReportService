'use strict';

jiraPluginApp.factory('BoardsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/boards', {}, {
    query:  { method: 'GET' }
  });
}]);