(function() {
  'use strict';

  angular
      .module('jiraPluginApp')
      .factory('ReportsFactory', ReportsFactory);

  ReportsFactory.$inject = ['$resource', 'CONFIG'];

  function ReportsFactory($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/reports/:type/:page', {type: "@type", page: "@page"}, {
      query:  { method: 'GET', isArray: false },
      create: { method: 'POST' }
    });
  }

})();

'use strict';

jiraPluginApp.factory('ReportsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/reports/:type/:page', {type: "@type", page: "@page"}, {
    query:  { method: 'GET', isArray: false },
    create: { method: 'POST' }
  });
}]);

jiraPluginApp.factory('ReportFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/reports/:id', {id: "@id"}, {
    get:    { method: 'GET' },
    update: { method: 'PUT' },
    delete: { method: 'DELETE' }
  });
}]);

jiraPluginApp.factory('CopyReportFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/reports/:reportId/copy', {reportId: "@reportId"}, {
    copy:   { method: 'GET' }
  });
}]);

jiraPluginApp.factory('ReportWithSprintsAndTeamsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/reports/:id/data_with_sprints_and_teams', {id: "@id"}, {
    get:    { method: 'GET' }
  });
}]);
