(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('TaskFactory', TaskFactory);

    TaskFactory.$inject = ['$resource', 'CONFIG'];

    function TaskFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/tasks/:name/:action', {name: "@name", action: "@action"}, {
            query:          { method: 'GET' },
            get:            { method: 'GET' },
            start:          { method: 'GET'}
        });
    }

})();