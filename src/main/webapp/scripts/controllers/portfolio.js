'use strict';

jiraPluginApp.controller("PortfolioCtrl", ['$scope', 'PortfoliosFactory', '$timeout',
    function($scope, PortfoliosFactory, $timeout) {
        var self = this;
        $scope.loaderShow = true;

//----------------------------------------------------------------------------------------------------------------------
//update ProgressBar
        this.updateProgressBar = function (item) {
            var actualPoints = item.actualPoints,
                actualHours  = item.actualHours,
                targetPoints = item.targetPoints,
                targetHours  = item.targetHours;

            actualHours = Math.round(actualHours*100)/100;

            var percentPoints = targetPoints == 0 ? "~" : Math.round(100 * actualPoints / targetPoints);
            var percentHours = targetHours == 0 ? "~" : Math.round(100 * actualHours / targetHours);

            var progressBarData = {
                actualHours: actualHours,
                targetHours: targetHours,
                actualPoints: actualPoints,
                targetPoints: targetPoints,
                percentPoints: percentPoints,
                percentHours: percentHours
            };

            return progressBarData;
        };

//----------------------------------------------------------------------------------------------------------------------
//update chart
        this.updateChart = function (item) {
            var chartData = item.chart;
//----------------------------------------------------------------------------------------------------------------------
//Chart type (bad: red; good: green)
            console.log("Data for chart: actual = "+chartData.actual[chartData.actual.length - 1]+" ; target = "+chartData.target[chartData.actual.length - 1]);
            var actualChartColor = "#FF0000";
            if (chartData.actual[chartData.actual.length - 1] <= chartData.target[chartData.actual.length - 1]) {
                actualChartColor = "#009e0f";
            }

            var chart = {
                labels: chartData.label,
                data: [
                    chartData.actual,
                    chartData.target
                ],
                series: ['Actual', 'Target'],
                colours: [actualChartColor, '#000000'],
                options: {
                    datasetFill : false
                }
            };

            return chart;
        };

//----------------------------------------------------------------------------------------------------------------------
//For pagination
        $scope.reportsData = [];
        $scope.totalReportsData = 0;
        $scope.reportsDataPerPage = 10; // this should match however many results your API puts on one page
        getResultsPage(1);

        $scope.pagination = {
            current: 1
        };

        $scope.pageChanged = function(newPage) {
            getResultsPage(newPage);
        };

        function getResultsPage(pageNumber) {
            // this is just an example, in reality this stuff should be in a service
            $scope.loaderShow = true;
            PortfoliosFactory.query({page: pageNumber}, function (result) {
                $scope.reportsData = result.dashboards;
                $scope.totalReportsData = result.totalItems;
                $scope.reportsDataPerPage = result.itemsPerPage;

                for (var index = 0; index < $scope.reportsData.length; index++) {
                    $scope.reportsData[index] = $scope.reportsData[index].report;

                    //Progress bar
                    $scope.reportsData[index]['progressBarData'] = self.updateProgressBar($scope.reportsData[index]);
                    //Chart
                    $scope.reportsData[index]['chart'] = self.updateChart($scope.reportsData[index]);
                }

                //FIXME fix for normal size chart
                $timeout(function () {
                    $scope.loaderShow = false;
                }, 0);
            }, function (error) {
                $scope.reportsData = [];
                $scope.loaderShow = false;
            });
        }

    }
]);
