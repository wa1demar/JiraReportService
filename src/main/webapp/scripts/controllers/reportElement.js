'use strict';

jiraPluginApp.controller('ReportElementCtrl',
    ['$scope', '$routeParams', 'ReportsFactory', 'ReportFactory', 'ReportWithSprintsAndTeamsFactory',
        function($scope, $routeParams, ReportsFactory, ReportFactory, ReportWithSprintsAndTeamsFactory) {
            var self = this;

//----------------------------------------------------------------------------------------------------------------------
//update ProgressBar
            $scope.updateProgressBar = function (item) {
                console.log($scope.showSprintId);
                var actualPoints,
                    actualHours,
                    targetPoints,
                    targetHours;

                if ($scope.showSprintId === null || $scope.showSprintId === undefined) {
                    console.log("update ProgressBar for report");
                    console.log($scope.reportData.report);
                    actualPoints = $scope.reportData.report.actualPoints;
                    actualHours  = $scope.reportData.report.actualHours;
                    targetPoints = $scope.reportData.report.targetPoints;
                    targetHours  = $scope.reportData.report.targetHours;
                } else {
                    console.log("update ProgressBar for sprint");
                    console.log(item);

                    actualPoints = item.actualPoints;
                    actualHours  = item.actualHours;
                    targetPoints = item.targetPoints;
                    targetHours  = item.targetHours;
                }

                actualHours = Math.round(actualHours*100)/100;
                console.log("Data for Progress bar: actualHours = " + actualHours + " ; targetHours = " + targetHours);

                var percentPoints = targetPoints == 0 ? "~" : Math.round(100 * actualPoints / targetPoints);
                var percentHours = targetHours == 0 ? "~" : Math.round(100 * actualHours / targetHours);

                $scope.progressBarData = {
                    actualHours: actualHours,
                    targetHours: targetHours,
                    actualPoints: actualPoints,
                    targetPoints: targetPoints,
                    percentPoints: percentPoints,
                    percentHours: percentHours
                };
            };

//----------------------------------------------------------------------------------------------------------------------
//update chart
            $scope.updateChart = function (item) {
                console.log($scope.showSprintId);
                if ($scope.showSprintId === null || $scope.showSprintId === undefined) {
                    console.log("update Chart for report");
                    $scope.chartData = $scope.reportData.report.chart;
                } else {
                    console.log("update Chart for sprint");
                    $scope.chartData = item.chart;
                }

//----------------------------------------------------------------------------------------------------------------------
//Chart type (bad: red; good: green)
                console.log("Data for chart: actual = "+$scope.chartData.actual[$scope.chartData.actual.length - 1]+" ; target = "+$scope.chartData.target[$scope.chartData.actual.length - 1]);
                var actualChartColor = "#FF0000";
                if ($scope.chartData.actual[$scope.chartData.actual.length - 1] <= $scope.chartData.target[$scope.chartData.actual.length - 1]) {
                    actualChartColor = "#009e0f";
                }

                $scope.labels = $scope.chartData.label;
                $scope.series = ['Actual', 'Terget'];
                $scope.data = [
                    $scope.chartData.actual,
                    $scope.chartData.target
                ];
                $scope.colours = [actualChartColor, '#000000'];
                $scope.options = {
                    datasetFill : false
                };
            };

            this.getReportsData = function () {
                var dataOngoing = ReportsFactory.query({}, function(){
                    $scope.reports = dataOngoing.reports;
                    $scope.loaderShow = false;
                });
            };

            self.getReportsData();

            this.getReportWithSprintsAndTeamsData = function () {
                ReportWithSprintsAndTeamsFactory.get({id: $routeParams.reportId}, function (data) {
                    $scope.report = data.data;
                });

                $scope.reportData = {
                    report: {
                        id : 1,
                        title : "Man",
                        creator : "admin",
                        boardId : null,
                        boardName : null,
                        createdDate : null,
                        updatedDate : null,
                        closedDate : null,
                        typeId : 1,
                        closed : false,
                        admins: [
                            {
                                fullName : "Alina Nor",
                                login : "anor",
                                jiraUserId : null
                            }
                        ],
                        targetPoints: 1,
                        targetHours: 1,
                        targetQatDefectMin: 1,
                        targetQatDefectMax: 1,
                        targetQatDefectHours: 1,
                        targetUatDefectMin: 1,
                        targetUatDefectMax: 1,
                        targetUatDefectHours: 1,
                        actualPoints: 1,
                        actualHours: 1,
                        actualQatDefectPoints: 1,
                        actualQatDefectHours: 1,
                        actualUatDefectPoints: 1,
                        actualUatDefectHours: 1,

                        chart: {
                            label:  [0, 1, 2, 3],
                            target: [129, 83, 41, 0],
                            actual: [129, 77, 31, -3]
                        }
                    },
                    sprints: [
                        {
                            id : 3,
                            reportId : 4,
                            agileSprintId : null,
                            notCountTarget : false,
                            name : "Sprint 1",
                            state : "closed",
                            type : 0,
                            startDate : 1457992800000,
                            endDate : 1458252000000,
                            completeDate : null,
                            showUat : false,

                            targetPoints: 28,
                            targetHours: 38,
                            targetQatDefectMin: 2,
                            targetQatDefectMax: 4,
                            targetQatDefectHours: 16,
                            targetUatDefectMin: 1,
                            targetUatDefectMax: 1,
                            targetUatDefectHours: 1,
                            actualPoints: 36,
                            actualHours: 1,
                            actualQatDefectPoints: 3,
                            actualQatDefectHours: 12,
                            actualUatDefectPoints: 1,
                            actualUatDefectHours: 1,

                            chart: {
                                label:  ["0", "01/27/2016", "01/28/2016", "01/29/2016", "02/01/2016", "02/02/2016"],
                                target: [41.0, 33, 25, 16, 8, 0],
                                actual: [41, 33, 25, 17, 7, 7]
                            },

                            sprintTeam: [
                                {
                                    devName: "bridoux",
                                    engineerLevel: 3,
                                    participationLevel: "0.5",
                                    daysInSprint: 5,

                                    targetPoints: 14,
                                    actualPoints: 18,
                                    defectMin: 0,
                                    defectMax: 1,
                                    defectActual: 0,
                                    defectTargetHours: 8,
                                    defectActualHours: 0,
                                    uatDefectMin: 5,
                                    uatDefectMax: 5,
                                    uatDefectActual: 5,
                                    uatDefectTargetHours: 5,
                                    uatDefectActualHours: 5
                                },
                                {
                                    id: 2,
                                    devName: "bmurga",
                                    engineerLevel: 1,
                                    participationLevel: "0.6",
                                    daysInSprint: 7,

                                    targetPoints: 14,
                                    actualPoints: 18,
                                    defectMin: 2,
                                    defectMax: 3,
                                    defectActual: 3,
                                    defectTargetHours: 8,
                                    defectActualHours: 12,
                                    uatDefectMin: 5,
                                    uatDefectMax: 5,
                                    uatDefectActual: 5,
                                    uatDefectTargetHours: 5,
                                    uatDefectActualHours: 5
                                }
                            ]
                        },
                        {
                            id : 5,
                            reportId : 6,
                            agileSprintId : null,
                            notCountTarget : true,
                            name : "Sprint 2",
                            state : "active",
                            type : 2,
                            startDate : 1457992800000,
                            endDate : 1458252000000,
                            completeDate : null,
                            showUat : true,

                            targetPoints: 1,
                            targetHours: 1,
                            targetQatDefectMin: 1,
                            targetQatDefectMax: 1,
                            targetQatDefectHours: 1,
                            targetUatDefectMin: 1,
                            targetUatDefectMax: 1,
                            targetUatDefectHours: 1,
                            actualPoints: 1,
                            actualHours: 1,
                            actualQatDefectPoints: 1,
                            actualQatDefectHours: 1,
                            actualUatDefectPoints: 1,
                            actualUatDefectHours: 1,

                            chart: {
                                label:  ["0", "01/12/2016", "01/13/2016", "01/14/2016", "01/15/2016", "01/18/2016", "01/19/2016"],
                                target: [46.0, 38, 31, 23, 15, 8, 0],
                                actual: [46, 41, 34, 24, 14, 4, -6]
                            },

                            sprintTeam: [
                                {
                                    id: 7,
                                    devName: "bridoux",
                                    engineerLevel: 3,
                                    participationLevel: "0.5",
                                    daysInSprint: 5,

                                    targetPoints: 5,
                                    actualPoints: 5,
                                    defectMin: 5,
                                    defectMax: 5,
                                    defectActual: 5,
                                    defectTargetHours: 5,
                                    defectActualHours: 5,
                                    uatDefectMin: 5,
                                    uatDefectMax: 5,
                                    uatDefectActual: 5,
                                    uatDefectTargetHours: 5,
                                    uatDefectActualHours: 5
                                },
                                {
                                    id: 8,
                                    devName: "bmurga",
                                    engineerLevel: 1,
                                    participationLevel: "0.6",
                                    daysInSprint: 7,

                                    targetPoints: 5,
                                    actualPoints: 5,
                                    defectMin: 5,
                                    defectMax: 5,
                                    defectActual: 5,
                                    defectTargetHours: 5,
                                    defectActualHours: 5,
                                    uatDefectMin: 5,
                                    uatDefectMax: 5,
                                    uatDefectActual: 5,
                                    uatDefectTargetHours: 5,
                                    uatDefectActualHours: 5
                                }
                            ]
                        }
                    ]
                };

                //count hideUatCount, closedSprintCount and add some new fields
                $scope.hideUatCount = 0;
                $scope.closedSprintCount = 0;
                for(var index = 0; index < $scope.reportData.sprints.length; index++) {
                    $scope.hideUatCount = !$scope.reportData.sprints[index].showUat ? $scope.hideUatCount + 1 : $scope.hideUatCount;
                    $scope.closedSprintCount = $scope.reportData.sprints[index].state === "closed" ? $scope.closedSprintCount + 1 : $scope.closedSprintCount;

                    $scope.reportData.sprints[index]['stateName'] = $scope.reportData.sprints[index].type == 1 ? "(additional blue)" : $scope.reportData.sprints[index].type == 2 ? "(additional red)" : "";
                    $scope.reportData.sprints[index]['stateClass'] = $scope.reportData.sprints[index].type == 1 ? "sprint-additional-blue" : $scope.reportData.sprints[index].type == 2 ? "sprint-additional-red" : "";

                    $scope.reportData.sprints[index]['endDate'] = $scope.reportData.report.typeId === 2 ? $scope.reportData.sprints[index]['endDate'] : $scope.reportData.sprints[index]['completeDate'];
                }

                //update data for ProgressBar
                $scope.updateProgressBar();
                //update data for chart
                $scope.updateChart();
            };

            self.getReportWithSprintsAndTeamsData();

            $scope.getReportAllData = function (item) {
                console.log(item);
            };

            $scope.showSprintDetails = function (item) {
                console.log("showSprintDetails");
                $scope.showSprintId = $scope.showSprintId == item.id ? null : item.id;

                $scope.updateProgressBar(item);
                $scope.updateChart(item);
            };
        }
    ]);
