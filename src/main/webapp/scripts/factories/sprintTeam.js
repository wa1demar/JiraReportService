'use strict';

jiraPluginApp.factory('SprintTeamFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/sprint_teams/:reportId/:sprintId', {reportId: "@reportId", sprintId: "@sprintId"}, {
    get:    { method: 'GET', isArray: true },
    update: { method: 'PUT'},
    delete: { method: 'DELETE'}
  });
}]);
