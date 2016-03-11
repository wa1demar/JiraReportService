'use strict';

jiraPluginApp.factory('SprintsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/sprints/:reportId', {reportId: "@reportId"}, {
    query:  { method: 'GET', isArray: true },
    add:    { method: 'POST' }
  });
}]);

jiraPluginApp.factory('SprintFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/sprint/:reportId/:sprintId', {reportId: "@reportId", sprintId: "@sprintId"}, {
    get:    { method: 'GET', isArray: false },
    update: { method: 'PUT'},
    delete: { method: 'DELETE'}
  });
}]);
