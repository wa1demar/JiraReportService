'use strict';

jiraPluginApp.factory('TaskFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/tasks/:name/:action', {name: "@name", action: "@action"}, {
        query:          { method: 'GET' },
        get:            { method: 'GET' },
        start:          { method: 'GET'}
    });
}]);