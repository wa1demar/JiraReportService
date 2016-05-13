(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .config(config);

    config.$inject = ['$httpProvider'];

    function config($httpProvider) {
        $httpProvider.interceptors.push('tokenInterceptor');

        //FIXME need for auth
        $httpProvider.defaults.withCredentials = true;
        //$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }

})();
