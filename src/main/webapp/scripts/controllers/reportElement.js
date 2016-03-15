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
                                state : "active",
                                type : 0,
                                startDate : 1457992800000,
                                endDate : 1458252000000,
                                completeDate : null,
                                showUat : false,
                                sprintTeam: [
                                    {
                                        devName: "bridoux",
                                        engineerLevel: 3,
                                        participationLevel: "0.5",
                                        daysInSprint: 5,
                                        targetPoints: 5,
                                        targetHours: 5,
                                        defectMin: 5,
                                        defectMax: 5,
                                        defectHours: 5,
                                        uatDefectMin: 5,
                                        uatDefectMax: 5,
                                        uatDefectHours: 5
                                    },
                                    {
                                        id: 2,
                                        devName: "bmurga",
                                        engineerLevel: 1,
                                        participationLevel: "0.6",
                                        daysInSprint: 7,
                                        targetPoints: 5,
                                        targetHours: 5,
                                        defectMin: 5,
                                        defectMax: 5,
                                        defectHours: 5,
                                        uatDefectMin: 5,
                                        uatDefectMax: 5,
                                        uatDefectHours: 5
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
                                showUat : false,
                                sprintTeam: [
                                    {
                                        id: 7,
                                        devName: "bridoux",
                                        engineerLevel: 3,
                                        participationLevel: "0.5",
                                        daysInSprint: 5,
                                        targetPoints: 5,
                                        targetHours: 5,
                                        defectMin: 5,
                                        defectMax: 5,
                                        defectHours: 5,
                                        uatDefectMin: 5,
                                        uatDefectMax: 5,
                                        uatDefectHours: 5
                                    },
                                    {
                                        id: 8,
                                        devName: "bmurga",
                                        engineerLevel: 1,
                                        participationLevel: "0.6",
                                        daysInSprint: 7,
                                        targetPoints: 5,
                                        targetHours: 5,
                                        defectMin: 5,
                                        defectMax: 5,
                                        defectHours: 5,
                                        uatDefectMin: 5,
                                        uatDefectMax: 5,
                                        uatDefectHours: 5
                                    }
                                ]
                            }
                        ]
                };

                console.log($scope.report);
            };

//----------------------------------------------------------------------------------------------------------------------
//get sprints data
            this.getSprints = function () {

                var startDate = new Date();
                var endDate = new Date();
                endDate.setDate(endDate.getDate()+5);
                //TODO get sprints
                //$scope.sprints = SprintsFactory.query({reportId: $routeParams.reportId});
                $scope.sprints = [
                    {
                        id: 1,
                        name: 'Sprint 1',
                        type: 1,
                        notCountTarget: true,
                        showUat: true,
                        startDate: startDate,
                        endDate: endDate,
                        state: "active"
                    },
                    {
                        id: 2,
                        name: 'Sprint 2',
                        type: 0,
                        notCountTarget: true,
                        showUat: false,
                        startDate: startDate,
                        endDate: endDate,
                        state: "active"
                    },
                    {
                        id: 3,
                        name: 'Sprint 3',
                        type: 2,
                        notCountTarget: false,
                        showUat: false,
                        startDate: startDate,
                        endDate: endDate,
                        state: "active"
                    }
                ];
            };

//----------------------------------------------------------------------------------------------------------------------
//get sprint teams data
            this.getSprintTeams = function (data) {

                if (data === undefined) {
                    data = {id: $scope.sprints[0]};
                }

                //TODO add get sprint teams by reportId and agileSprintId
                //SprintTeamFactory.get({
                //    reportId: $routeParams.reportId,
                //    sprintId: data.id
                //}, function (data) {
                //    console.log(data);
                //    //$scope.sprintTeams = data;
                //}, function (error) {
                //    console.log(error);
                //    $scope.sprintTeams = [];
                //});


                if (data.id === 57283) {
                    $scope.sprintTeams = [
                        {
                            id: 1,
                            devName: "bridoux",
                            engineerLvl: 3,
                            participationLvl: "0.5",
                            daysInSprint: 5
                        },
                        {
                            id: 2,
                            devName: "bmurga",
                            engineerLvl: 1,
                            participationLvl: "0.6",
                            daysInSprint: 7
                        }
                    ];
                } else {
                    $scope.sprintTeams = [];
                }
            };

            self.getReportWithSprintsAndTeamsData();

            $scope.getReportAllData = function (item) {
                console.log(item);
            };

            $scope.showSprintDetailsData = false;
            $scope.showSprintDetails = function (item) {
                //$scope.showSprintDetailsData = !$scope.showSprintDetailsData;

                //$(".sprint-id[data-id-sprint='"+item.id+"']").parent().after("test");

                $scope.showSprintTeam(item);

                //console.log(item);
            };

            $scope.showSprintTeam = function (data) {
                var object =  $("td[data-id-sprint='"+data.id+"']");
                var idSprint = data.id;
                var state = data.state;
                var idSprintForChart = idSprint;

                $(".sprint-team-tr[data-id-sprint!='"+idSprint+"']").hide();
                $(".sprint-id[data-id!='"+idSprint+"']").removeAttr("rowspan");
                $(".sprint-id[data-id!='"+idSprint+"']").removeClass("open");

                if ($(".sprint-team-tr[data-id-sprint='"+idSprint+"']").eq(0).is(":visible")) {
                    $(".sprint-team-tr[data-id-sprint='"+idSprint+"']").hide();
                    $(object).parent().find(".sprint-id").removeAttr("rowspan");
                    $(object).parent().find(".sprint-id").removeClass("open");

                    idSprintForChart = 0;
                } else {
                    if (!$(".sprint-team-tr[data-id-sprint='"+idSprint+"']").length) {
                        $scope.getSprintTeam(idSprint, state);
                    }
                    $(".sprint-team-tr[data-id-sprint='"+idSprint+"']").show();
                    $(object).parent().find(".sprint-id").attr("rowspan", $(".sprint-team-tr[data-id-sprint='"+idSprint+"']").length+1);
                    $(object).parent().find(".sprint-id").addClass("open");
                }

                ////Change chart title
                //var chartTitle = idSprintForChart == 0 ? "Project to date" : $(".show-sprint-details[data-id-sprint='"+idSprintForChart+"']").data("name");
                //$("#chart-title").text(chartTitle);
                //
                ////Show chart
                //createChartNew(idSprintForChart);
                //
                ////Show Progress bar
                //changeProgressBar(idSprintForChart);
            };

            $scope.getSprintTeam = function(idSprint, state) {

                $(".sprint-id[data-id-sprint='"+idSprint+"']").parent().after('<tr ng-repeat="dataSprint in data.sprintTeam" class="sprint-team-tr" data-id-sprint="{{dataSprint.id}}"><td>xxx</td></tr>');
                $(".sprint-id[data-id-sprint='"+idSprint+"']").attr("rowspan", $(".sprint-team-tr[data-id-sprint='"+idSprint+"']").length+1);

                //$(".sprint-team[data-id='"+idSprint+"'] .td-for-sprint-team").html('<span class="aui-icon aui-icon-wait">Loading...</span>');
                //return false;


                //AJS.$.ajax({
                //    url: config.pathToAjax + "/plugins/servlet/getreportdata",
                //    type: "POST",
                //    data: ({
                //        action:     "getSprintTeam",
                //        idReport:   $("#change-report-element option:selected").val(),
                //        idBoard:    $("#change-report-element option:selected").data("boardId"),
                //        idSprint:   idSprint,
                //        state:      state
                //    }),
                //    //dataType: "json",
                //    beforeSend: function () {
                //
                //    },
                //    success: function(response){
                //        $(".sprint-id[data-id-sprint='"+idSprint+"']").parent().after(response);
                //        $(".sprint-id[data-id-sprint='"+idSprint+"']").attr("rowspan", $(".sprint-team-tr[data-id-sprint='"+idSprint+"']").length+1);
                //    }
                //});
            };

        }
    ]);
