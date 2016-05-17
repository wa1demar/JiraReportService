(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .factory('logger', logger);

    logger.$inject = ['$log'];

    function logger($log) {
        var service = {
            error: error,
            info: info,
            log: log
        };
        return service;

        function error(msg) {
            var loggedMsg = 'Error: ' + msg;
            $log.error(loggedMsg);
            return loggedMsg;
        }

        function info(msg) {
            var loggedMsg = msg;
            $log.info(loggedMsg);
            return loggedMsg;
        }

        function log(msg) {
            var loggedMsg = msg;
            $log.log(loggedMsg);
            return loggedMsg;
        }
    }
})();