(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('DueDateIssueFactory', DueDateIssueFactory);

    DueDateIssueFactory.$inject = ['$resource', 'CONFIG'];

    function DueDateIssueFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/issues/due_dates/:page', {page: "@page"}, {
            query:          { method: 'GET' },
            get:            { method: 'GET', isArray: false }
        });
    }

})();