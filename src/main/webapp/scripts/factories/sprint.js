'use strict';

jiraPluginApp.factory('SprintsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/sprints/:reportId', {reportId: "@reportId"}, {
    query:  { method: 'GET', isArray: true }
  });
}]);

jiraPluginApp.factory('SprintFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/sprint/:id', {id: "@id"}, {
    get:    { method: 'GET'},
    update: { method: 'PUT'},
    delete: { method: 'DELETE'}
  });
}]);
