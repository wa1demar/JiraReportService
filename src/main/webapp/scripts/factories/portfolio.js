'use strict';

jiraPluginApp.factory('PortfoliosFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/portfolio', {}, {
    query:  { method: 'GET', isArray: false }
  });
}]);
