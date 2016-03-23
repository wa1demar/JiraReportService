'use strict';

jiraPluginApp.factory('ProfileFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/profile', {}, {
        query:  { method: 'GET' },
        get:    { method: 'GET', isArray: false },
        update: { method: 'PUT'},
        delete: { method: 'DELETE'}
    });
}]);