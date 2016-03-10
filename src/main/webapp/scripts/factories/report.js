'use strict';

jiraPluginApp.factory('ReportsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/reports', {}, {
    query:  { method: 'GET', isArray: false },
    create: { method: 'POST' },
    delete: { method: 'DELETE'}
  });
}]);

jiraPluginApp.factory('ReportFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/report/:id', {id: "@id"}, {
    get: { method: 'GET'},
    update: { method: 'PUT'}
  });
}]);

jiraPluginApp.factory('CopyReportFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/report/copy', {}, {
    copy:   { method: 'POST' }
  });
}]);
