(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .constant('CONFIG', {
            APP_NAME: 'Swan Intranet',
            APP_VERSION: '0.1',
            DESIGN_VERSION: 'v1',
            //API_PATH: 'http://localhost:3000',
            API_PATH: '/rest/v1',
            JIRA_PATH: 'https://swansoftwaresolutions.atlassian.net'
        });

})();