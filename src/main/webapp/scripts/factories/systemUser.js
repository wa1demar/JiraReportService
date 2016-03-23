'use strict';

jiraPluginApp.factory('SystemUsersFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/system_users/:username', {username: "@username"}, {
        query:  { method: 'GET' },
        get:    { method: 'GET', isArray: false },
        update: { method: 'PUT'},
        delete: { method: 'DELETE'}
    });
}]);