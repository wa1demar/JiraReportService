'use strict';

jiraPluginApp.factory('PortfoliosFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/portfolios', {}, {
    query:  { method: 'GET', isArray: true }
  });
}]);
