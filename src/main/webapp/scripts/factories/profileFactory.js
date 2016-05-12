(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('ProfileFactory', ProfileFactory);

    ProfileFactory.$inject = ['$resource', 'CONFIG'];

    function ProfileFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/profile/:action', {action: "@action"}, {
            query:  { method: 'GET' },
            get:    { method: 'GET', isArray: false },
            update: { method: 'PUT'},
            delete: { method: 'DELETE'}
        });
    }

})();