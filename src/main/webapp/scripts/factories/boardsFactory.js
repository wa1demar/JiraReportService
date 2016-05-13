(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('BoardsFactory', BoardsFactory);

    BoardsFactory.$inject = ['$resource', 'CONFIG'];

    function BoardsFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/boards', {}, {
            query:  { method: 'GET' }
        });
    }

})();