'use strict';

jiraPluginApp.controller('ResourceManagementCtrl',
    ['$scope', 'DueDateIssueFactory', 'Notification',
        function($scope, DueDateIssueFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;

            //random
            function getRandomInt(min, max) {
                return Math.floor(Math.random() * (max - min)) + min;
            }

            var projects = ['success', 'error', 'current', 'complete', 'moved'];

            $scope.models = {
                selected: null,
                lists: {"A": [], "B": [], "C": [], "D": []}
            };

            // Generate initial model
            for (var i = 1; i <= 3; ++i) {
                $scope.models.lists.A.push({label: "User A" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.B.push({label: "User B" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.C.push({label: "User C" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.D.push({label: "User D" + i, project: projects[getRandomInt(0, projects.length)]});
            }

            // Model to JSON for demo purpose
            $scope.$watch('models', function(model) {
                $scope.modelAsJson = angular.toJson(model, true);
            }, true);

            $scope.logEvent = function(message, event) {
                console.log(message, '(triggered by the following', event.type, 'event)');
                console.log(event);
                console.log($scope.models);
            };
        }
]);