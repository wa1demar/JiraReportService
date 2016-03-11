'use strict';

jiraPluginApp.factory('SprintIssuesFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/sprint_issues/:sprintId', {sprintId: "@sprintId"}, {
    query: { method: 'GET', isArray: true },
    add:   { method: 'POST' }
  });
}]);

jiraPluginApp.factory('SprintIssueFactory', ['$resource', 'CONFIG', function ($resource, CONFIG) {
  return $resource(CONFIG.API_PATH + '/sprint_issue/:issueId', {issueId: "@issueId"}, {
    get:    { method: 'GET', isArray: false },
    update: { method: 'PUT' },
    delete: { method: 'DELETE' }
  });
}]);
