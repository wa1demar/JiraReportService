(function() {
    'use strict';

    angular
        .module('jiraPluginApp', [
                'ngAnimate',
                'ngCookies',
                'ngResource',
                'ngRoute',
                'ngSanitize',
                'ngTouch',
                'ngTouch',
                'ui.bootstrap',
                'ui.select2',
                'ngMessages',
                'chart.js',
                'ui-notification',
                'nl2br',
                'angularUtils.directives.dirPagination',
                'dndLists',
                'xeditable',
                'colorpicker.module',
                'angularFileUpload',
                'ui.bootstrap.contextMenu'
            ])
        .controller('AppController', AppController);

    AppController.$inject = ['CONFIG'];

    function AppController(CONFIG) {
        var self = this;

        self.CONFIG = CONFIG;
    }

})();