(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('SprintIssuesFactory', SprintIssuesFactory);

    SprintIssuesFactory.$inject = ['$resource', 'CONFIG'];

    function SprintIssuesFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/sprint_issues/:sprintId/:assignee', {sprintId: "@sprintId", assignee: "@assignee"}, {
            query: { method: 'GET', isArray: true },
            add:   { method: 'POST' }
        });
    }

    ///v1/reports/{report_id}/sprints/{id}
    angular
        .module('jiraPluginApp')
        .factory('SprintIssueFactory', SprintIssueFactory);

    SprintIssueFactory.$inject = ['$resource', 'CONFIG'];

    function SprintIssueFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/sprint_issues/:issueId', {issueId: "@issueId"}, {
            get:    { method: 'GET', isArray: false },
            update: { method: 'PUT' },
            delete: { method: 'DELETE' }
        });
    }

})();
