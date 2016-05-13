(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('SprintsWithTeamFactory', SprintsWithTeamFactory);

    SprintsWithTeamFactory.$inject = ['$resource', 'CONFIG'];

    function SprintsWithTeamFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:reportId/sprints_with_team', {reportId: "@reportId"}, {
            query:  { method: 'GET', isArray: false },
            add:    { method: 'POST' }
        });
    }

    angular
        .module('jiraPluginApp')
        .factory('SprintWithTeamFactory', SprintWithTeamFactory);

    SprintWithTeamFactory.$inject = ['$resource', 'CONFIG'];

    function SprintWithTeamFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:reportId/sprints_with_team/:sprintId', {reportId: "@reportId", sprintId: "@sprintId"}, {
            get:    { method: 'GET', isArray: false },
            update: { method: 'PUT' }
        });
    }

})();
