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

        var self = this;

        this.getReport = function () {
            ReportFactory.get({id: $routeParams.reportId}, function (result) {
                $scope.report = result;
                var admins = [];
                for (var index = 0; index < result.admins.length; index++) {
                    admins.push(result.admins[index].login);
                }
                $scope.report.admins = admins;
            });
        };

        self.getReport();

        //$scope.report = {
        //    title: "title",
        //    typeId: 2,
        //    boardName: "Test",
        //    isClosed: true,
        //    dateClose: new Date()
        //};

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

        var users = UsersFactory.query(function(){
            $scope.users = users.users;
        });

        $scope.saveConfigureGeneralData = function () {
            if($scope.generalConfigure.$valid) {
                console.log("isClosed: " + $scope.report.isClosed);
                $scope.report.dateClose = $scope.report.isClosed ? $scope.report.dateClose : null;

                var reportData = {
                    title:      $scope.report.title,
                    isClosed:   $scope.report.isClosed === undefined ? false : $scope.report.isClosed,
                    dateClose:  $scope.report.dateClose === undefined ? null : $scope.report.dateClose,
                    admins:     $scope.report.admins
                };

                ReportFactory.update({
                    id: $routeParams.reportId}, reportData, function(){
                    //FIXME not reinit select2
                    self.getReport();
                });
            }
        };
    }
]);

//Configure jira_sprint team data
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
//get jira_sprint teams data
        $scope.getSprintTeams = function (data) {
            console.log("getSprintTeams");
            console.log(data);
            var firstLoad = true;
            if (data === undefined) {
                if ($scope.sprints !== undefined && $scope.sprints.length > 0) {
                    data = {id: $scope.sprints[0].id};
                }
            } else {
                firstLoad = false;
            }

            console.log(data);

            //TODO add get jira_sprint teams by reportId and agileSprintId
            if (data !== undefined && data.id !== undefined) {
                SprintTeamFactory.query({
                    reportId: $routeParams.reportId,
                    sprintId: data.id
                }, function (data) {
                    //console.log(data);
                    $scope.sprintTeams = data;
                }, function (error) {
                    //console.log(error);
                    $scope.sprintTeams = [];
                });
            }

            if (data.id === 57284) {
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

            console.log($scope.sprints);

            //TODO need only in first load
            if (firstLoad && $scope.sprints !== undefined) {
                $scope.reportModel = {
                    sprint: $scope.sprints[0]
                };
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
//get sprints data
        $scope.getSprints = function () {
            var startDate = new Date();
            var endDate = new Date();
            endDate.setDate(endDate.getDate()+5);
            //TODO get sprints
            SprintsFactory.query({reportId: $routeParams.reportId}, function(data) {
                $scope.sprints = data.sprints;
                console.log("getSprints");
                console.log($scope.sprints);
                $scope.getSprintTeams();
            });
            //$scope.sprints = [
            //    {
            //        id: 1,
            //        name: 'Sprint 1',
            //        type: 1,
            //        notCountTarget: true,
            //        showUat: true,
            //        startDate: startDate,
            //        endDate: endDate,
            //        state: "active"
            //    },
            //    {
            //        id: 2,
            //        name: 'Sprint 2',
            //        type: 0,
            //        notCountTarget: true,
            //        showUat: false,
            //        startDate: startDate,
            //        endDate: endDate,
            //        state: "active"
            //    },
            //    {
            //        id: 3,
            //        name: 'Sprint 3',
            //        type: 2,
            //        notCountTarget: false,
            //        showUat: false,
            //        startDate: startDate,
            //        endDate: endDate,
            //        state: "active"
            //    }
            //];
        };

//----------------------------------------------------------------------------------------------------------------------
//get report data
        $scope.getReport = function () {
            //TODO get report
            ReportFactory.get({id: $routeParams.reportId}, function(result){
                $scope.report = result;
                console.log("getReport");
                $scope.getSprints();
            });

            //$scope.report = {
            //    title: "title",
            //    typeId: 2,
            //    boardName: "Test",
            //    isClosed: true,
            //    dateClose: new Date()
            //};
        };

//----------------------------------------------------------------------------------------------------------------------
//get jira_sprint configure data
        this.getSprintConfigureData = function () {
            //get report data
            $scope.getReport();

            //get jira_sprint data
            //$scope.getSprints();

            //get jira_sprint teams data
            //$scope.getSprintTeams();

            console.log($scope.report);
            console.log($scope.sprints);
            //console.log($scope.sprintTeams);

            //if ($scope.sprints !== undefined) {
            //    $scope.reportModel = {
            //        jira_sprint: $scope.sprints[0]
            //    };
            //}
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
//save jira_sprint configure
        $scope.saveSprintConfigure = function () {
            //TODO save jira_sprint data (with jira_sprint team)
            var sprint = {
                sprint:                 $scope.reportModel.sprint,
                sprintTeams:            $scope.sprintTeams,
                sprintTeamsTotalValues: $scope.totalPreviewDetails
            };

            SprintFactory.update({
                reportId: $routeParams.reportId,
                sprintId: $scope.reportModel.sprint.id
            }, sprint, function () {
                console.log("Save jira_sprint");
            });
        };


//----------------------------------------------------------------------------------------------------------------------
//Dlg process jira_sprint (type: add/edit)
        $scope.dlgData = {};
        $scope.processSprint = function (type) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/report_configure/dlg/dlg_process_sprint.html',
                controller: 'DlgProcessSprintCtrl',
                resolve: {
                    dlgData: function () {
                        console.log($scope.reportModel);
                        return {
                            item: $scope.reportModel != undefined ? $scope.reportModel.sprint : undefined,
                            type: type
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                //TODO add/edit jira_sprint
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
                    SprintFactory.update({reportId: $routeParams.reportId, sprintId: $scope.reportModel.sprint.id}, data.sprint, function () {
                        self.getSprintConfigureData();
                    });
                }
            }, function () {});
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete jira_sprint
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
                SprintFactory.delete({reportId: $routeParams.reportId, sprintId: $scope.reportModel.sprint.id}, function() {
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
        $scope.sprint = dlgData.item;
        if (dlgData.type === "add") {
            $scope.sprint = {
                state: "active"
            }
        }

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

jiraPluginApp.controller('DlgSprintTeamActivityCtrl', ['$scope', '$uibModal', '$uibModalInstance', 'dlgData', 'SprintIssuesFactory', 'SprintIssueFactory',
    function ($scope, $uibModal, $uibModalInstance, dlgData, SprintIssuesFactory, SprintIssueFactory) {
        var self = this;
        $scope.dlgData = dlgData;
        console.log($scope.dlgData);

        //get issues by sprintId and assignee
        this.getIssues = function() {
            SprintIssuesFactory.query({
                sprintId: $scope.dlgData.sprint.id,
                assignee: $scope.dlgData.developer.devName
            }, function (data) {
                $scope.issues = data;
            });

            $scope.data = [
                {
                    date: $scope.dlgData.sprint.startDate,
                    issues: [
                        {
                            id: 1,
                            typeName: "Story",
                            statusName: "To Do",
                            point: 3,
                            hours: 8.0
                        }
                    ]
                },
                {
                    date: $scope.dlgData.sprint.startDate,
                    issues: [
                        {
                            id: 2,
                            typeName: "Story",
                            statusName: "In Progress",
                            point: 3,
                            hours: 8.0
                        }
                    ]
                },
                {
                    date: $scope.dlgData.sprint.endDate,
                    issues: []
                },
                {
                    date: $scope.dlgData.sprint.endDate,
                    issues: []
                }
            ]
        };
        self.getIssues();

        console.log($scope.data);

        //get issues by

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg add fields
        $scope.processIssue = function (item, issueDate) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/report_configure/dlg/dlg_issue.html',
                controller: 'DlgProcessIssueCtrl',
                size: 'md',
                resolve: {
                    dlgData: function () {
                        return {
                            item:       item,
                            issueDate:  issueDate
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                console.log(data);
                if (data.type === "edit") {
                    var idIssue = data.id;
                    delete data.id;
                    delete data.type;
                    SprintIssueFactory.update({issueId: idIssue}, data, function(result){
                        self.getIssues();
                    });
                } else {
                    delete data.type;
                    SprintIssuesFactory.add({sprintId: $scope.dlgData.sprint.id, assignee: $scope.dlgData.developer.devName}, data, function(result){
                        self.getIssues();
                    });
                }
            }, function () {});
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete fields
        $scope.deleteIssue = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/dlg/dlg_delete_element.html',
                controller: 'DlgDeleteIssueCtrl',
                size: 'sm',
                resolve: {
                    dlgData: function () {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                SprintIssueFactory.delete({issueId: data.id}, function(result){
                    self.getIssues();
                });
            }, function () {});
        };
    }
]);

jiraPluginApp.controller('DlgProcessIssueCtrl', ['$scope', '$uibModalInstance', 'dlgData',
    function ($scope, $uibModalInstance, dlgData) {
        $scope.issue = dlgData.item;
        if ($scope.issue === undefined) {
            $scope.issue = {
                type: "add",
                typeName: "Story",
                statusName: "To Do",
                hours: 8.0,
                issueDate: dlgData.issueDate
            }
        } else {
            $scope.issue["type"] = "edit";
        }

        $scope.ok = function () {
            $uibModalInstance.close($scope.issue);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);

jiraPluginApp.controller('DlgDeleteIssueCtrl', ['$scope', '$uibModalInstance', 'dlgData',
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