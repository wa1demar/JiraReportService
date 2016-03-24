'use strict';

jiraPluginApp.factory('UsersFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/users/:id', {id: "@id"}, {
        query:  { method: 'GET' },
        get:    { method: 'GET', isArray: false },
        update: { method: 'PUT'},
        delete: { method: 'DELETE'}
    });
}]);