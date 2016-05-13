(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('CommentsFactory', CommentsFactory);

    CommentsFactory.$inject = ['$resource', 'CONFIG'];

    function CommentsFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:id/comments', {id: '@id'}, {
            query:  { method: 'GET', isArray: true },
            add:    { method: 'POST' }
        });
    }

    angular
        .module('jiraPluginApp')
        .factory('CommentFactory', CommentFactory);

    CommentFactory.$inject = ['$resource', 'CONFIG'];

    function CommentFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/comments/:id', {id: '@id'}, {
            get:    { method: 'GET' },
            delete: { method: 'DELETE'}
        });
    }

})();