(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('MemberFactory', MemberFactory);

    MemberFactory.$inject = ['$resource', 'CONFIG'];

    function MemberFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/members/:login/:relation/:idRelation', {login: "@login", relation: "@relation", idRelation: "@idRelation"}, {
            query:  { method: 'GET' },
            get:    { method: 'GET', isArray: false },
            create: { method: 'POST' },
            update: { method: 'PUT' },
            delete: { method: 'DELETE' }
        });
    }

})();