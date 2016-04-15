'use strict';

jiraPluginApp.factory('ResourceColumn', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/resource_columns/:id', {id: "@id"}, {
        query:  { method: 'GET' },
        get:    { method: 'GET', isArray: false },
        create: { method: 'POST' },
        update: { method: 'PUT' },
        delete: { method: 'DELETE' }
    });
}]);