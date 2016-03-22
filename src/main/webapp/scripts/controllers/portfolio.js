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

        this.getPortfoliosData = function () {
            PortfoliosFactory.query({}, function (result) {
                $scope.reportsData = result.dashboards;

                for (var index = 0; index < $scope.reportsData.length; index++) {
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

            //$scope.reportsData = [
            //    {
            //        id : 1,
            //        title : "Man",
            //        typeId : 2,
            //
            //        targetPoints: 1,
            //        targetHours: 1,
            //        targetQatDefectMin: 1,
            //        targetQatDefectMax: 1,
            //        targetQatDefectHours: 1,
            //        targetUatDefectMin: 1,
            //        targetUatDefectMax: 1,
            //        targetUatDefectHours: 1,
            //        actualPoints: 1,
            //        actualHours: 1,
            //        actualQatDefectPoints: 1,
            //        actualQatDefectHours: 1,
            //        actualUatDefectPoints: 1,
            //        actualUatDefectHours: 1,
            //
            //        chart: {
            //            label:  [0, 1, 2, 3],
            //            target: [129, 83, 41, 0],
            //            actual: [129, 77, 31, -3]
            //        },
            //
            //        showUat: true,
            //        closedSprintCount: 1
            //    },
            //    {
            //        id : 2,
            //        title : "test2",
            //        typeId : 2,
            //
            //        targetPoints: 1,
            //        targetHours: 1,
            //        targetQatDefectMin: 1,
            //        targetQatDefectMax: 1,
            //        targetQatDefectHours: 1,
            //        targetUatDefectMin: 1,
            //        targetUatDefectMax: 1,
            //        targetUatDefectHours: 1,
            //        actualPoints: 1,
            //        actualHours: 1,
            //        actualQatDefectPoints: 1,
            //        actualQatDefectHours: 1,
            //        actualUatDefectPoints: 1,
            //        actualUatDefectHours: 1,
            //
            //        chart: {
            //            label:  [0, 1, 2, 3],
            //            target: [129, 83, 41, 0],
            //            actual: [129, 77, 31, 2]
            //        },
            //
            //        showUat: true,
            //        closedSprintCount: 1
            //    },
            //    {
            //        id : 3,
            //        title : "more report",
            //        typeId : 2,
            //
            //        targetPoints: 1,
            //        targetHours: 1,
            //        targetQatDefectMin: 1,
            //        targetQatDefectMax: 1,
            //        targetQatDefectHours: 1,
            //        targetUatDefectMin: 1,
            //        targetUatDefectMax: 1,
            //        targetUatDefectHours: 1,
            //        actualPoints: 1,
            //        actualHours: 1,
            //        actualQatDefectPoints: 1,
            //        actualQatDefectHours: 1,
            //        actualUatDefectPoints: 1,
            //        actualUatDefectHours: 1,
            //
            //        chart: {
            //            label:  [0, 1, 2, 3],
            //            target: [129, 83, 41, 0],
            //            actual: [129, 77, 31, -3]
            //        },
            //
            //        showUat: true,
            //        closedSprintCount: 1
            //    }
            //];
            //
            //for (var index = 0; index < $scope.reportsData.length; index++) {
            //    //Progress bar
            //    $scope.reportsData[index]['progressBarData'] = self.updateProgressBar($scope.reportsData[index]);
            //    //Chart
            //    $scope.reportsData[index]['chart'] = self.updateChart($scope.reportsData[index]);
            //}
            //
            ////FIXME fix for normal size chart
            //$timeout(function () {
            //    $scope.loaderShow = false;
            //}, 0);

        };

        self.getPortfoliosData();
    }
]);
