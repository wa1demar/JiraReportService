'use strict';

jiraPluginApp.factory('CommentsFactory', ['$resource', function ($resource) {
  return $resource('http://localhost:3000/comments/:id', {id: '@id'}, {
    query:  { method: 'GET', isArray: true },
    create: { method: 'POST' }
  });
}]);

jiraPluginApp.factory('CommentFactory', ['$resource', function ($resource) {
  return $resource('http://localhost:3000/comment/:id', {id: '@id'}, {
    get:    { method: 'GET' },
    delete: { method: 'DELETE'}
  });
}]);
