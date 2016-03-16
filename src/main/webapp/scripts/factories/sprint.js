'use strict';

///v1/reports/{report_id}/sprints
jiraPluginApp.factory('SprintsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/reports/:reportId/sprints', {reportId: "@reportId"}, {
    query:  { method: 'GET', isArray: false },
    add:    { method: 'POST' }
  });
}]);

///v1/reports/{report_id}/sprints/{id}
jiraPluginApp.factory('SprintFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/reports/:reportId/sprints/:sprintId', {reportId: "@reportId", sprintId: "@sprintId"}, {
    get:    { method: 'GET', isArray: false },
    update: { method: 'PUT'},
    delete: { method: 'DELETE'}
  });
}]);
