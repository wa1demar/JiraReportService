'use strict';

jiraPluginApp.controller('ConfigureCtrl', ['$scope',
    function($scope) {
        var self = this;
        //------------------------------------------------------------------------------------------------------------------
        //Tabs
        $scope.tabs = [{
            title: 'Team settings',
            url: 'views/report_configure/configure_sprint_team.html'
        }, {
            title: 'General settings',
            url: 'views/report_configure/configure_general_data.html'
        }];

        $scope.currentTab = 'views/report_configure/configure_sprint_team.html';

        $scope.onClickTab = function (tab) {
            $scope.currentTab = tab.url;
        };

        $scope.isActiveTab = function(tabUrl) {
            return tabUrl === $scope.currentTab;
        };
    }
]);

//General Settings
jiraPluginApp.controller('ConfigureGeneralDataCtrl', ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'CONFIG',
    function($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, CONFIG) {

        //$scope.report = ReportFactory.get({id: $routeParams.reportId});
        $scope.report = {
            title: "title",
            typeId: 2,
            boardName: "Test",
            isClosed: true,
            dateClose: new Date()
        };

        var users = UsersFactory.query(function(){
            $scope.users = users.users;
        });

        $scope.saveConfigureGeneralData = function () {
            if($scope.generalConfigure.$valid) {
                $scope.report.dateClose = $scope.report.isClosed ? $scope.report.dateClose : null;
                ReportFactory.update({id: $routeParams.reportId}, $scope.report);
            }
        };

        //==================================================================================================================
        //Calender
        $scope.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            minDate: new Date(),
            startingDay: 1
        };

        // Disable weekend selection
        function disabled(data) {
            var date = data.date,
                mode = data.mode;
            return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
        }

        $scope.openCalender = function() {
            $scope.popupCalender.opened = true;
        };

        $scope.setDate = function(year, month, day) {
            $scope.dt = new Date(year, month, day);
        };

        $scope.format = 'MM/dd/yyyy';
        $scope.altInputFormats = ['M!/d!/yyyy'];

        $scope.popupCalender = {
            opened: false
        };

    }
]);

//Configure sprint team data
jiraPluginApp.controller('ConfigureSprintTeamCtrl', ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'CONFIG',
    function($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, CONFIG) {

        var self = this;

        var users = UsersFactory.query(function(){
            $scope.devUsers = users.users;
        });

        //$scope.report = ReportFactory.get({id: $routeParams.reportId});
        $scope.report = {
            title: "title",
            typeId: 1,
            boardName: "Test",
            isClosed: true,
            dateClose: new Date(),
            sprints: [
                {
                    id: 1,
                    name: 'Sprint 1',
                    type: '1',
                    notCountTarget: true,
                    showUat: true
                },
                {
                    id: 2,
                    name: 'Sprint 2',
                    type: '0',
                    notCountTarget: true,
                    showUat: false,
                    sprintTeams: [
                        {
                            devName: "vholovko",
                            engineerLvl: "3",
                            participationLvl: "0.5",
                            daysInSprint: "5"
                        },
                        {
                            devName: "slevchenko",
                            engineerLvl: "1",
                            participationLvl: "0.6",
                            daysInSprint: "7"
                        }
                    ]
                },
                {
                    id: 3,
                    name: 'Sprint 3',
                    type: '2',
                    notCountTarget: false,
                    showUat: false
                }
            ]
        };

        $scope.reportModel = {
            sprint: $scope.report.sprints[0]
        };

        $scope.sprintTeams = [];

        //Function for calc preview values
        $scope.calcParams = function(){
            var total = {
                engineerLvl: 0,
                participationLvl: 0,
                daysInSprint: 0,

                targetPoints: 0,
                targetHours: 0,
                defectMin: 0,
                defectMax: 0,
                defectHours: 0,
                uatDefectMin: 0,
                uatDefectMax: 0,
                uatDefectHours: 0
            };

            for (var index = 0; index < $scope.sprintTeams.length; index++) {
                var value = $scope.sprintTeams[index];

                var engineerLvl = parseFloat(value.engineerLvl);
                var dv = engineerLvl == 4 ? 3 : engineerLvl;
                var pl = parseFloat(value.participationLvl);
                var dis = parseFloat(value.daysInSprint);

                var dDailyForMin = 1;
                var dDailyForMax = 2;
                if (engineerLvl == 2) {
                    dDailyForMin = 0.5;
                    dDailyForMax = 1;
                } else if (engineerLvl == 3) {
                    dDailyForMin = 0.3;
                    dDailyForMax = 0.5;
                } else if (engineerLvl == 4)  {
                    dDailyForMin = 0;
                    dDailyForMax = 0.1;
                }

                var tp = Math.round(dv * dis * pl * 0.9);
                var th = Math.round(dis * pl) * 8;
                var dMin = Math.round(dDailyForMin * dis * pl);
                var dMax = Math.round(dDailyForMax * dis * pl);
                var dh = Math.round(1.6 * dis * pl);

                //Check show UAT
                var uatdMin = 0;
                var uatdMax = 0;
                var uatdh = 0;
                if ($scope.reportModel.sprint.showUat) {
                    uatdMin = Math.round(dMin * 0.25);
                    uatdMax = Math.round(dMax * 0.25);
                    uatdh = Math.round(dh * 0.25);
                }

                //Sprint teams
                $scope.sprintTeams[index].targetPoints = tp;
                $scope.sprintTeams[index].targetHours = th;
                $scope.sprintTeams[index].defectMin = dMin;
                $scope.sprintTeams[index].defectMax = dMax;
                $scope.sprintTeams[index].defectHours = dh;
                $scope.sprintTeams[index].uatDefectMin = uatdMin;
                $scope.sprintTeams[index].uatDefectMax = uatdMax;
                $scope.sprintTeams[index].uatDefectHours = uatdh;

                //Total
                total.engineerLvl       += (parseFloat(value.engineerLvl) === 4 ? 3 : parseFloat(value.engineerLvl));

                total.targetPoints      += parseFloat(value.targetPoints);
                total.targetHours       += parseFloat(value.targetHours);
                total.defectMin         += parseFloat(value.defectMin);
                total.defectMax         += parseFloat(value.defectMax);
                total.defectHours       += parseFloat(value.defectHours);
                total.uatDefectMin      += parseFloat(value.uatDefectMin);
                total.uatDefectMax      += parseFloat(value.uatDefectMax);
                total.uatDefectHours    += parseFloat(value.uatDefectHours);
            }

            $scope.totalPreviewDetails = total;
            console.log($scope.totalPreviewDetails);
        };

        $scope.getSprintTeam = function (data) {
            if (data.id === 2) {
                $scope.sprintTeams = [
                    {
                        devName: "Alina Nor",
                        engineerLvl: "3",
                        participationLvl: "0.5",
                        daysInSprint: 5
                    },
                    {
                        devName: "Yevhenii Vuksta",
                        engineerLvl: "1",
                        participationLvl: "0.6",
                        daysInSprint: 7
                    }
                ];
            } else {
                $scope.sprintTeams = [];
            }
            $scope.calcParams();
        };

        $scope.dataDeveloper = {
            fullName: 0
        };
        $scope.addDeveloper = function (item) {
            console.log(item);
            $scope.sprintTeams.push({
                fullName: item,
                devName: item,
                engineerLvl: "1",
                participationLvl: "1.0",
                daysInSprint: 1
            });
            $scope.dataDeveloper = {
                fullName: 0
            };
            $scope.calcParams();
        };

        $scope.deleteDeveloper = function (item) {
            console.log(item);
            var index = $scope.sprintTeams.indexOf(item);
            $scope.sprintTeams.splice(index, 1);
        };

        $scope.saveSprintConfigure = function () {
            console.log($routeParams.reportId);
            console.log($scope.reportModel);
            console.log($scope.sprintTeams);
        };
    }
]);