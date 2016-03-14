'use strict';

jiraPluginApp.factory('CommentsFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/reports/:id/comments', {id: '@id'}, {
    query:  { method: 'GET', isArray: true },
    add:    { method: 'POST' }
  });
}]);

jiraPluginApp.factory('CommentFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/comments/:id', {id: '@id'}, {
    get:    { method: 'GET' },
    delete: { method: 'DELETE'}
  });
}]);