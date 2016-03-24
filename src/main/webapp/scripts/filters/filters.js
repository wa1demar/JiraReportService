'use strict';

jiraPluginApp.filter('isChecked', function() {

    var filterFunction = function(input) {
        return (input === true || input === 1 || input === '1') ? true : false;
    };

    return filterFunction;
});

jiraPluginApp.filter('hasRole', function() {

    var filterFunction = function(roles, roleName) {
        return _.findWhere(roles, {name: roleName});
    };

    return filterFunction;
});