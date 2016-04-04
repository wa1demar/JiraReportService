'use strict';

jiraPluginApp.factory('PortfoliosFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/portfolio/:page', {page: "@page"}, {
    query:  { method: 'GET', isArray: false }
  });
}]);
