'use strict';

jiraPluginApp.factory('SprintTeamFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/sprint_teams/:reportId/:sprintId', {reportId: "@reportId", sprintId: "@sprintId"}, {
    query:  { method: 'GET', isArray: false },
    get:    { method: 'GET', isArray: false },
    update: { method: 'PUT' },
    delete: { method: 'DELETE' }
  });
}]);
