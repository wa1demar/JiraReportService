(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .directive('convertToDouble', convertToDouble);

    function convertToDouble() {
        return {
            require: 'ngModel',
            link: function(scope, element, attrs, ngModel) {
                ngModel.$parsers.push(function(val) {
                    return parseFloat(val);
                });
                ngModel.$formatters.push(function(val) {
                    return '' + val;
                });
            }
        };
    }

})();