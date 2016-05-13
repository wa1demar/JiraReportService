(function() {
    'use strict';

    ///v1/reports/{report_id}/sprints
    angular
        .module('jiraPluginApp')
        .factory('SprintsFactory', SprintsFactory);

    SprintsFactory.$inject = ['$resource', 'CONFIG'];

    function SprintsFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:reportId/sprints', {reportId: "@reportId"}, {
            query:  { method: 'GET', isArray: false },
            add:    { method: 'POST' }
        });
    }

    ///v1/reports/{report_id}/sprints/{id}
    angular
        .module('jiraPluginApp')
        .factory('SprintFactory', SprintFactory);

    SprintFactory.$inject = ['$resource', 'CONFIG'];

    function SprintFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:reportId/sprints/:sprintId', {reportId: "@reportId", sprintId: "@sprintId"}, {
            get:    { method: 'GET', isArray: false },
            update: { method: 'PUT'},
            delete: { method: 'DELETE'}
        });
    }

})();
