(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('ProjectFactory', ProjectFactory);

    ProjectFactory.$inject = ['$resource', 'CONFIG'];

    function ProjectFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/projects/:id/:relation/:idRelation/:typeRelation', {id: "@id", relation: "@relation", idRelation: "@idRelation", typeRelation: "@typeRelation"}, {
            query:  { method: 'GET' },
            get:    { method: 'GET', isArray: false },
            create: { method: 'POST' },
            update: { method: 'PUT' },
            delete: { method: 'DELETE' }
        });
    }

})();