'use strict';

jiraPluginApp.factory('DueDateIssueFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/due_date_issue', {}, {
        query:          { method: 'GET' },
        get:            { method: 'GET', isArray: false }
    });
}]);