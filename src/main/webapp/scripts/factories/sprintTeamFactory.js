(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('SprintTeamFactory', SprintTeamFactory);

    SprintTeamFactory.$inject = ['$resource', 'CONFIG'];

    function SprintTeamFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/sprints/:sprintId/developers', {sprintId: "@sprintId"}, {
            query:  { method: 'GET', isArray: false },
            get:    { method: 'GET', isArray: false },
            update: { method: 'PUT' },
            delete: { method: 'DELETE' }
        });
    }

})();
