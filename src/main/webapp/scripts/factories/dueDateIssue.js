'use strict';

jiraPluginApp.factory('DueDateIssueFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/issues/due_dates/:page', {page: "@page"}, {
        query:          { method: 'GET' },
        get:            { method: 'GET', isArray: false }
    });
}]);