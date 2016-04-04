'use strict';

jiraPluginApp.controller('DueDateIssueCtrl',
    ['$scope', 'DueDateIssueFactory', 'Notification',
        function($scope, DueDateIssueFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;
            $scope.todayDate = new Date();

//----------------------------------------------------------------------------------------------------------------------
//Order reports
            $scope.predicate = 'id';
            $scope.reverse = false;
            $scope.order = function (predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            };

//----------------------------------------------------------------------------------------------------------------------
//get data

//----------------------------------------------------------------------------------------------------------------------
//TODO For pagination
//            $scope.dataDueDate = [];
//            $scope.totalDueDate = 0;
//            $scope.dueDatePerPage = 10; // this should match however many results your API puts on one page
//            getResultsPage(1);
//
//            $scope.pagination = {
//                current: 1
//            };
//
//            $scope.pageChanged = function(newPage) {
//                getResultsPage(newPage);
//            };
//
//            function getResultsPage(pageNumber) {
//                // this is just an example, in reality this stuff should be in a service
//                $scope.loaderShow = true;
//                DueDateIssueFactory.query({page: pageNumber}, function(result){
//                    $scope.dataDueDate = result.dueDate;
//                    $scope.totalDueDate = result.totalItems;
//                    $scope.dueDatePerPage = result.itemsPerPage;
//                    $scope.loaderShow = false;
//                    //$scope.setLoading(false);
//                }, function (error) {
//                    Notification.error("Server error");
//                });
//            }


            this.getSystemUsers = function () {
                //$scope.dataDueDate = [];
                //DueDateIssueFactory.query({}, function (data) {
                //    $scope.dataDueDate = data.dueDate;
                //}, function (error) {
                //    Notification.error("Server error");
                //});

                $scope.dataDueDate = [
                    {
                        id: 1,
                        project: "test 1",
                        assignee: "assignee 1",
                        key: "key-1",
                        summary: "summary 1",
                        description: "description 1",
                        status: "In Progress",
                        dueDate: ['03/23/2016','03/25/2016','03/28/2016']
                    },
                    {
                        id: 2,
                        project: "test 2",
                        assignee: "assignee 2",
                        key: "key-2",
                        summary: "summary 2",
                        description: "description 2",
                        status: "To Do",
                        dueDate: ['03/22/2016']
                    },
                    {
                        id: 3,
                        project: "test 2",
                        assignee: "assignee 2",
                        key: "key-2",
                        summary: "summary 2",
                        description: "description 2",
                        status: "To Do",
                        dueDate: ['03/22/2016']
                    },
                    {
                        id: 4,
                        project: "test 2",
                        assignee: "assignee 2",
                        key: "key-2",
                        summary: "summary 2",
                        description: "description 2",
                        status: "To Do",
                        dueDate: ['03/22/2016']
                    },
                    {
                        id: 5,
                        project: "test 2",
                        assignee: "assignee 2",
                        key: "key-2",
                        summary: "summary 2",
                        description: "description 2",
                        status: "To Do",
                        dueDate: ['03/22/2016']
                    }
                ];

                $scope.loaderShow = false;
            };

            self.getSystemUsers();
        }
]);