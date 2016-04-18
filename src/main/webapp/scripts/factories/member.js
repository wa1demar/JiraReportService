'use strict';

jiraPluginApp.factory('MemberFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
    return $resource(CONFIG.API_PATH + '/members/:login/:relation/:idRelation', {login: "@login", relation: "@relation", idRelation: "@idRelation"}, {
        query:  { method: 'GET' },
        get:    { method: 'GET', isArray: false },
        create: { method: 'POST' },
        update: { method: 'PUT' },
        delete: { method: 'DELETE' }
    });
}]);