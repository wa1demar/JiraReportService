'use strict';

jiraPluginApp.factory('SprintTeamFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/sprints/:sprintId/developers', {sprintId: "@sprintId"}, {
        query:  { method: 'GET', isArray: false },
        get:    { method: 'GET', isArray: false },
        update: { method: 'PUT' },
        delete: { method: 'DELETE' }
    });
}]);
