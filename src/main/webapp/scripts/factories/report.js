'use strict';

jiraPluginApp.factory('ReportsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/report', {}, {
    query:  { method: 'GET', isArray: true },
    create: { method: 'POST' }
  });
}]);

jiraPluginApp.factory('ReportFactory', ['$resource', function ($resource) {
  return $resource('http://localhost:3000/auth/:id', {}, {
    get:    { method: 'GET' },
    update: { method: 'PUT', params: {id: '@id'} },
    delete: { method: 'DELETE', params: {id: '@id'} }
  });
}]);
