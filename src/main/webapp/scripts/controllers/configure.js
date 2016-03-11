'use strict';

jiraPluginApp.controller('ConfigureCtrl', ['$scope',
    function($scope) {
        var self = this;
//--------------------------------------------------------------------------------------------------------------
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
    }
]);

//Configure sprint team data
jiraPluginApp.controller('ConfigureSprintTeamCtrl',
    ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'SprintsFactory', 'SprintFactory', 'SprintTeamFactory', 'CONFIG',
    function($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, SprintsFactory, SprintFactory, SprintTeamFactory, CONFIG) {

        var self = this;
        $scope.sprintTeams = [];

//----------------------------------------------------------------------------------------------------------------------
//get developer
        var users = UsersFactory.query(function() {
            $scope.devUsers = users.users;
        });

//----------------------------------------------------------------------------------------------------------------------
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
                $scope.sprintTeams[index].engineerLvl = parseFloat(value.engineerLvl);
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
            //console.log($scope.totalPreviewDetails);
        };

//----------------------------------------------------------------------------------------------------------------------
//get report data
        $scope.getReport = function () {
            //$scope.report = ReportFactory.get({id: $routeParams.reportId});
            $scope.report = {
                title: "title",
                typeId: 2,
                boardName: "Test",
                isClosed: true,
                dateClose: new Date()
            };
        };

//----------------------------------------------------------------------------------------------------------------------
//get sprints data
        $scope.getSprints = function () {
            //$scope.sprints = SprintsFactory.query({reportId: $routeParams.reportId});
            $scope.sprints = [
                {
                    id: 1,
                    name: 'Sprint 1',
                    type: 1,
                    notCountTarget: true,
                    showUat: true
                },
                {
                    id: 2,
                    name: 'Sprint 2',
                    type: 0,
                    notCountTarget: true,
                    showUat: false
                },
                {
                    id: 3,
                    name: 'Sprint 3',
                    type: 2,
                    notCountTarget: false,
                    showUat: false
                }
            ];
        };

//----------------------------------------------------------------------------------------------------------------------
//get sprint teams data
        $scope.getSprintTeams = function (data) {

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

            if (data.id === 2) {
                $scope.sprintTeams = [
                    {
                        devName: "bridoux",
                        engineerLvl: 3,
                        participationLvl: "0.5",
                        daysInSprint: 5
                    },
                    {
                        devName: "bmurga",
                        engineerLvl: 1,
                        participationLvl: "0.6",
                        daysInSprint: 7
                    }
                ];
            } else {
                $scope.sprintTeams = [];
            }

//----------------------------------------------------------------------------------------------------------------------
//get developer
//TODO need refactoring
            var users = UsersFactory.query(function() {
                $scope.devUsersForAdd = users.users;
                for (var index = 0; index < $scope.devUsersForAdd.length; index++) {
                    for (var indexTeam = 0; indexTeam < $scope.sprintTeams.length; indexTeam++) {
                        if ($scope.devUsersForAdd[index].login === $scope.sprintTeams[indexTeam].devName) {
                            $scope.devUsersForAdd.splice(index, 1);
                        }
                    }
                }
            });

            $scope.calcParams();
        };

//----------------------------------------------------------------------------------------------------------------------
//get sprint configure data
        this.getSprintConfigureData = function () {
            //get report data
            $scope.getReport();

            //get sprint data
            $scope.getSprints();

            //get sprint teams data
            $scope.getSprintTeams();

            //console.log($scope.report);
            //console.log($scope.sprints);
            //console.log($scope.sprintTeams);

            $scope.reportModel = {
                sprint: $scope.sprints[0]
            };
        };

        self.getSprintConfigureData();

//----------------------------------------------------------------------------------------------------------------------
//work with developers
        $scope.dataDeveloper = {
            login: "add"
        };
        $scope.addDeveloper = function (item) {
            console.log(item);
            $scope.sprintTeams.push({
                devName: item,
                engineerLvl: 1,
                participationLvl: "1.0",
                daysInSprint: 1
            });
            $scope.dataDeveloper = {
                login: "add"
            };
            $scope.calcParams();

//----------------------------------------------------------------------------------------------------------------------
//get developer
            var users = UsersFactory.query(function(){
                for (var index = 0; index < users.users.length; index++) {
                    for (var indexTeam = 0; indexTeam < $scope.sprintTeams.length; indexTeam++) {
                        if (users.users[index].login === $scope.sprintTeams[indexTeam].devName) {
                            users.users.splice(index, 1);
                        }
                    }
                }
                $scope.devUsersForAdd = users.users;
            });
        };

        $scope.deleteDeveloper = function (item) {
            console.log(item);
            var index = $scope.sprintTeams.indexOf(item);
            $scope.sprintTeams.splice(index, 1);
        };

//----------------------------------------------------------------------------------------------------------------------
//save sprint configure
        $scope.saveSprintConfigure = function () {
            //TODO save sprint data (with sprint team)
            var sprint = {
                sprint:                 $scope.reportModel.sprint,
                sprintTeams:            $scope.sprintTeams,
                sprintTeamsTotalValues: $scope.totalPreviewDetails
            };

            SprintFactory.update({
                reportId: $routeParams.reportId,
                sprintId: $scope.reportModel.sprint.id
            }, sprint, function () {
                console.log("Save sprint");
            });
        };


//----------------------------------------------------------------------------------------------------------------------
//Dlg process sprint (type: add/edit)
        $scope.dlgData = {};
        $scope.processSprint = function (type) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/report_configure/dlg/dlg_process_sprint.html',
                controller: 'DlgProcessSprintCtrl',
                resolve: {
                    dlgData: function () {
                        return {
                            type: type
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                //TODO add/edit sprint

                if(data.type === "add") {
                    data.sprint['type'] = 0;
                    data.sprint['notCountTarget'] = false;
                    data.sprint['showUat'] = false;
                    SprintsFactory.add({reportId: $routeParams.reportId}, data.sprint, function () {
                        self.getSprintConfigureData();
                    });

                    console.log(data.sprint);

                    self.getSprintConfigureData();
                    $scope.sprints.push(data.sprint);
                } else {

                }

                //SprintsFactory.add({
                //    reportId: $routeParams.reportId
                //}, data
                //);
                //SprintFactory.create({}, data, function(){
                //    self.getReportsData();
                //});
            }, function () {});
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete sprint
        $scope.deleteSprint = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/dlg/dlg_delete_element.html',
                controller: 'DlgDeleteSprintCtrl',
                resolve: {
                    dlgData: function () {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                SprintFactory.delete(data, function() {
                    self.getSprintConfigureData();
                });
            }, function () {});
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg developer activity
        $scope.dlgSprintTeamActivity = function (sprint, developer) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/report_configure/dlg/dlg_sprint_team_activity.html',
                controller: 'DlgSprintTeamActivityCtrl',
                size: 'lg',
                resolve: {
                    dlgData: function () {
                        return {
                            sprint:     sprint,
                            developer:  developer
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                SprintFactory.delete(data, function() {
                    self.getSprintConfigureData();
                });
            }, function () {});
        };
    }
]);

jiraPluginApp.controller('DlgProcessSprintCtrl',
    ['$scope', '$uibModalInstance', 'dlgData', 'SprintFactory',
    function ($scope, $uibModalInstance, dlgData, SprintFactory) {
        $scope.dlgData = dlgData;
        if(dlgData.type === "edit") {
            //var sprint = SprintFactory.query(function(){
            //    $scope.sprint = sprint;
            //    $scope.sprint['state'] = "active";
            //});
        }

        $scope.sprint = {};
        $scope.sprint['state'] = "active";

//----------------------------------------------------------------------------------------------------------------------
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

        $scope.openCalenderStartDate = function() {
            $scope.popupCalenderStartDate.opened = true;
        };
        $scope.openCalenderEndDate = function() {
            $scope.popupCalenderEndDate.opened = true;
        };

        $scope.setDate = function(year, month, day) {
            $scope.dt = new Date(year, month, day);
        };

        $scope.format = 'MM/dd/yyyy';
        $scope.altInputFormats = ['M!/d!/yyyy'];

        $scope.popupCalenderStartDate = {
            opened: false
        };
        $scope.popupCalenderEndDate = {
            opened: false
        };

        var result = {
            type:   dlgData.type,
            sprint: $scope.sprint
        };

        $scope.ok = function () {
            if($scope.processSprintForm.$valid) {
                $uibModalInstance.close(result);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);

jiraPluginApp.controller('DlgDeleteSprintCtrl', ['$scope', '$uibModalInstance', 'dlgData',
    function ($scope, $uibModalInstance, dlgData) {
        $scope.dlgData = dlgData;

        $scope.ok = function () {
            $uibModalInstance.close($scope.dlgData);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);

jiraPluginApp.controller('DlgSprintTeamActivityCtrl', ['$scope', '$uibModalInstance', 'dlgData',
    function ($scope, $uibModalInstance, dlgData) {
        $scope.dlgData = dlgData;

        console.log($scope.dlgData);

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);