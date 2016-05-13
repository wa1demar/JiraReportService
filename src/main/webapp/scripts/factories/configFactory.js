(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('ConfigFactory', configFactory);

    configFactory.$inject = ['$resource', 'CONFIG'];

    function configFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/config', {}, {
            get:    { method: 'GET', isArray: false },
            update: { method: 'PUT'}
        });
    }

})();
