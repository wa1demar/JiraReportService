'use strict';

jiraPluginApp.controller('ReportElementCtrl',
    ['$scope', '$routeParams', 'ReportsFactory', 'ReportFactory', 'ReportWithSprintsAndTeamsFactory',
        function($scope, $routeParams, ReportsFactory, ReportFactory, ReportWithSprintsAndTeamsFactory) {
            var self = this;

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
                        id : 4,
                        title : "Man",
                        creator : "admin",
                        boardId : null,
                        boardName : null,
                        createdDate : null,
                        updatedDate : null,
                        closedDate : null,
                        typeId : 2,
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
                        actualUatDefectHours: 1
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
                            notCountTarget : false,
                            name : "Sprint 2",
                            state : "active",
                            type : 0,
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

                console.log($scope.report);
            };

            self.getReportWithSprintsAndTeamsData();

            $scope.getReportAllData = function (item) {
                console.log(item);
            };

            $scope.showSprintDetails = function (item) {
                console.log("showSprintDetails");
            };

        }
    ]);
