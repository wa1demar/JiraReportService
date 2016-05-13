(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('PortfoliosFactory', PortfoliosFactory);

    PortfoliosFactory.$inject = ['$resource', 'CONFIG'];

    function PortfoliosFactory($resource, CONFIG) {
        return $resource(CONFIG.API_PATH + '/portfolio/:page', {page: "@page"}, {
            query:  { method: 'GET', isArray: false }
        });
    }

})();