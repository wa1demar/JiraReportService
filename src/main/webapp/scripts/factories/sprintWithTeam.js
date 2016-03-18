'use strict';

jiraPluginApp.factory('SprintsWithTeamFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/reports/:reportId/sprints_with_team', {reportId: "@reportId"}, {
        query:  { method: 'GET', isArray: false },
        add:    { method: 'POST' }
    });
}]);

jiraPluginApp.factory('SprintWithTeamFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/reports/:reportId/sprints_with_team/:sprintId', {reportId: "@reportId", sprintId: "@sprintId"}, {
        get:    { method: 'GET', isArray: false },
        update: { method: 'PUT' }
    });
}]);
