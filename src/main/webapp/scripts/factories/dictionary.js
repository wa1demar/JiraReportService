'use strict';

jiraPluginApp.factory('DictionaryFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/:name/:page', {name: "@name", page: "@page"}, {
        query:  { method: 'GET' },
        get:    { method: 'GET', isArray: false },
        create: { method: 'POST' },
        update: { method: 'PUT' },
        delete: { method: 'DELETE' }
    });
}]);