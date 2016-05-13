(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('SystemUsersFactory', SystemUsersFactory);

    SystemUsersFactory.$inject = ['$resource', 'CONFIG'];

    function SystemUsersFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/system_users/:id/:action', {id: "@id", action: "@action"}, {
            query:          { method: 'GET' },
            get:            { method: 'GET', isArray: false },
            add:            { method: 'POST'},
            resetPassword:  { method: 'GET'},
            update:         { method: 'PUT'},
            delete:         { method: 'DELETE'}
        });
    }

})();