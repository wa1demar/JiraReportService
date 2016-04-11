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
            $scope.dataDueDate = [];
            $scope.totalDueDate = 0;
            $scope.dueDatePerPage = 10; // this should match however many results your API puts on one page
            // getResultsPage(1);
            //
            // $scope.pagination = {
            //     current: 1
            // };
            //
            // $scope.pageChanged = function(newPage) {
            //     getResultsPage(newPage);
            // };
            //
            // function getResultsPage(pageNumber) {
            //     // this is just an example, in reality this stuff should be in a service
            //     $scope.loaderShow = true;
            //     DueDateIssueFactory.query({page: pageNumber}, function(result){
            //         $scope.dataDueDate = result.dates;
            //         $scope.totalDueDate = result.totalItems;
            //         $scope.dueDatePerPage = result.itemsPerPage;
            //         $scope.loaderShow = false;
            //         //$scope.setLoading(false);
            //     }, function (error) {
            //         Notification.error("Server error");
            //     });
            // }

            $scope.loaderShow = false;
            $scope.dataDueDate = [
                {
                    id: 1,
                    project: "test 1",
                    assignee: "assignee 1",
                    key: "key-1",
                    summary: "summary 1",
                    description: [null],
                    status: "In Progress",
                    dueDate: [new Date("2016-04-06"), new Date(new Date - 86400 * 1000), new Date(new Date - 172800 * 1000)]
                }
            ];


            //this.getSystemUsers = function () {
            //    $scope.dataDueDate = [];
            //    DueDateIssueFactory.query({}, function (data) {
            //        $scope.dataDueDate = data.dates;
            //        $scope.loaderShow = false;
            //    }, function (error) {
            //        Notification.error("Server error");
            //    });
            //
            //};
            //
            //self.getSystemUsers();
        }
]);