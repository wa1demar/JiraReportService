(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('DictionaryFactory', DictionaryFactory);

    DictionaryFactory.$inject = ['$resource', 'CONFIG'];

    function DictionaryFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/:name/:id', {name: "@name", id: "@id"}, {
            query:  { method: 'GET' },
            get:    { method: 'GET', isArray: false },
            create: { method: 'POST' },
            update: { method: 'PUT' },
            delete: { method: 'DELETE' }
        });
    }

})();