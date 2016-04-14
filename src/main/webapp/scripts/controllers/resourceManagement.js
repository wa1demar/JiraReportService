'use strict';

jiraPluginApp.controller('ResourceManagementCtrl',
    ['$scope', 'DueDateIssueFactory', 'Notification',
        function($scope, DueDateIssueFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;
            $scope.showSearch = true;
            $scope.showMemberInfo = false;

            $scope.currentMember = {};

            //test data--------------------------
            $scope.technologies = [
                {id: 1, name: "technology 1"},
                {id: 2, name: "technology 2"},
                {id: 3, name: "technology 3"},
                {id: 4, name: "technology 4"},
                {id: 5, name: "technology 5"}
            ];

            $scope.projects = [
                {id: 1, name: "project 1"},
                {id: 2, name: "project 2"},
                {id: 3, name: "project 3"},
                {id: 4, name: "project 4"},
                {id: 5, name: "project 5"}
            ];

            $scope.locations = [
                {id: 1, name: "locations 1"},
                {id: 2, name: "locations 2"},
                {id: 3, name: "locations 3"},
                {id: 4, name: "locations 4"},
                {id: 5, name: "locations 5"}
            ];
            //-----------------------------------

            //random
            function getRandomInt(min, max) {
                return Math.floor(Math.random() * (max - min)) + min;
            }

            var projects = ['success', 'error', 'current', 'complete', 'moved'];

            $scope.models = {
                // selected: null,
                lists: {"A": [], "B": [], "C": [], "D": [], "E": [], "F": [], "X": [], "Y": []}
            };

            // Generate initial model
            for (var i = 1; i <= 5; ++i) {
                $scope.models.lists.A.push({id: i, label: "Full Name A" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.B.push({id: i, label: "Full Name B" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.C.push({id: i, label: "Full Name C" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.D.push({id: i, label: "Full Name D" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.E.push({id: i, label: "Full Name E" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.F.push({id: i, label: "Full Name F" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.X.push({id: i, label: "Full Name X" + i, project: projects[getRandomInt(0, projects.length)]});
                $scope.models.lists.Y.push({id: i, label: "Full Name Y" + i, project: projects[getRandomInt(0, projects.length)]});
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

            $scope.selectElement = function (item) {
                console.log(item);
                if ($scope.models.selected === item) {
                    $scope.models.selected = null;

                    $scope.showSearch = true;
                    $scope.showMemberInfo = false;

                    $scope.showSearchFilters();
                } else {
                    $scope.models.selected = item;

                    $scope.showSearch = false;
                    $scope.showMemberInfo = true;

                    $scope.showMemberInfoData(item);
                }
            };

            $scope.showMemberInfoData = function(item) {
                console.log('showMemberInfoData');
                $scope.currentMember = item;

                $scope.currentMember.attach = [
                    {id: 1, name: "name"}
                ]
            };

            $scope.showSearchFilters = function() {
                console.log('showSearchFilters');
            };

            $scope.searchFiltersChange = function() {
                console.log('searchFiltersChange');
            };

            $scope.updateMemberDescription = function(data) {
                console.log('updateMemberDescription:');
                console.log(data);
            };
        }
]);