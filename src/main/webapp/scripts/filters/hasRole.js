(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .filter('hasRole', hasRole);

    function hasRole() {
        var filterFunction = function(roles, roleName) {
            return _.findWhere(roles, {name: roleName});
        };

        return filterFunction;
    }
})();