(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .filter('isChecked', isChecked);

    function isChecked() {
        var filterFunction = function (input) {
            return (input === true || input === 1 || input === '1') ? true : false;
        };

        return filterFunction;
    }
})();