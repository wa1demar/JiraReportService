'use strict';

jiraPluginApp.directive('printThis', function() {
  /** https://docs.angularjs.org/guide/directive **/

  var directiveDefinitionObject = {
    restrict: 'EA',
    template: '<input ng-model="data" placeholder="Type here"/> {{ data }}'
  }

  return directiveDefinitionObject;
});

//directive for indicators
jiraPluginApp.directive('indicator', function() {
  var directive = {
      restrict: 'E',
      template: "<b>{{show}}</b> <br/>"
  };

  directive.scope = {
    show:     "=show",
    target :  "=target",
    actual :  "=actual"
  };

  directive.compile = function(element, attributes) {
    element.css("border", "1px solid #cccccc");

    var linkFunction = function($scope, element, attributes) {
      element.html("<b>"+$scope.show +"</b> <br/>");
      element.css("background-color", "#ff00ff");
    };

    return linkFunction;
  };

  return directive;
});

jiraPluginApp.directive('convertToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(val) {
        return parseInt(val, 10);
      });
      ngModel.$formatters.push(function(val) {
        return '' + val;
      });
    }
  };
});