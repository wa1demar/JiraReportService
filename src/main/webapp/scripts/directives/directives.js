'use strict';

jiraPluginApp.directive('printThis', function() {
  /** https://docs.angularjs.org/guide/directive **/

  var directiveDefinitionObject = {
    restrict: 'EA',
    template: '<input ng-model="data" placeholder="Type here"/> {{ data }}'
  }

  return directiveDefinitionObject;
});

jiraPluginApp.directive('stringToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(value) {
        return '' + value;
      });
      ngModel.$formatters.push(function(value) {
        return parseFloat(value, 10);
      });
    }
  };
});