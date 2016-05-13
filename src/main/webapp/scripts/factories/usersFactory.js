(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('UsersFactory', UsersFactory);

    UsersFactory.$inject = ['$resource', 'CONFIG'];

    function UsersFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/users/:id', {id: "@id"}, {
            query:  { method: 'GET' },
            get:    { method: 'GET', isArray: false },
            update: { method: 'PUT'},
            delete: { method: 'DELETE'}
        });
    }

})();