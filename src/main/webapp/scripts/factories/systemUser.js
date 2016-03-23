'use strict';

jiraPluginApp.factory('SystemUsersFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/system_users/:id', {id: "@id"}, {
        query:  { method: 'GET' },
        get:    { method: 'GET', isArray: false },
        update: { method: 'PUT'},
        delete: { method: 'DELETE'}
    });
}]);