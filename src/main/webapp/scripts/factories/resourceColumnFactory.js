(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('ResourceColumnFactory', ResourceColumnFactory);

    ResourceColumnFactory.$inject = ['$resource', 'CONFIG'];

    function ResourceColumnFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/resource_columns/:id', {id: "@id"}, {
            query:  { method: 'GET' },
            get:    { method: 'GET', isArray: false },
            create: { method: 'POST' },
            update: { method: 'PUT' },
            delete: { method: 'DELETE' }
        });
    }

})();