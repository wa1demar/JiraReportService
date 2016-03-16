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
        type:     "=type",
        target :  "=target",
        actual :  "=actual",
        min :     "=min",
        max :     "=max"
    };

    directive.compile = function(element, attributes) {
        //element.css("border", "1px solid #cccccc");

        var linkFunction = function($scope, element, attributes) {
            var attrClass = "";
            if ($scope.type === "iconsVelocity") {
                if ($scope.actual >= $scope.target) {
                    attrClass = "circle green";
                } else if ($scope.actual < $scope.target && ((($scope.target - $scope.actual)) * 100) / $scope.target <= 10) {
                    attrClass = "circle yellow";
                } else {
                    attrClass = "circle red";
                }
            } else if ($scope.type === "icons") {
                if ($scope.actual  == 0 && $scope.min == 0 && $scope.max == 0) {
                    attrClass = "star-five";
                } else if ($scope.actual < $scope.min) {
                    attrClass = "star-five";
                } else if ($scope.actual >= $scope.min && $scope.actual <= $scope.max) {
                    attrClass = "circle green";
                } else if ($scope.actual > $scope.max && ((($scope.actual - $scope.max)) * 100) / $scope.max <= 10) {
                    attrClass = "circle yellow";
                } else {
                    attrClass = "circle red";
                }
            } else if ($scope.type === "iconsHours") {
                if ($scope.actual == 0) {
                    attrClass = "star-five";
                } else if ($scope.actual <= $scope.target) {
                    attrClass = "circle green";
                } else if ($scope.actual > $scope.target && ((($scope.actual - $scope.target)) * 100) / $scope.target <= 10) {
                    attrClass = "circle yellow";
                } else {
                    attrClass = "circle red";
                }
            }

            element.html('<span class="'+attrClass+'"></span>');
            //element.css("background-color", "#ff00ff");
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