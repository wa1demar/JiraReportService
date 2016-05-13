(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('ReportsFactory', ReportsFactory);

    ReportsFactory.$inject = ['$resource', 'CONFIG'];

    function ReportsFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:type/:page', {type: "@type", page: "@page"}, {
            query:  { method: 'GET', isArray: false },
            create: { method: 'POST' }
        });
    }

    angular
        .module('jiraPluginApp')
        .factory('ReportFactory', ReportFactory);

    ReportFactory.$inject = ['$resource', 'CONFIG'];

    function ReportFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:id', {id: "@id"}, {
            get:    { method: 'GET' },
            update: { method: 'PUT' },
            delete: { method: 'DELETE' }
        });
    }

    angular
        .module('jiraPluginApp')
        .factory('CopyReportFactory', CopyReportFactory);

    CopyReportFactory.$inject = ['$resource', 'CONFIG'];

    function CopyReportFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:reportId/copy', {reportId: "@reportId"}, {
            copy:   { method: 'GET' }
        });
    }

    angular
        .module('jiraPluginApp')
        .factory('ReportWithSprintsAndTeamsFactory', ReportWithSprintsAndTeamsFactory);

    ReportWithSprintsAndTeamsFactory.$inject = ['$resource', 'CONFIG'];

    function ReportWithSprintsAndTeamsFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/reports/:id/data_with_sprints_and_teams', {id: "@id"}, {
            get:    { method: 'GET' }
        });
    }

})();
