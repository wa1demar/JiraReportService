'use strict';

jiraPluginApp.controller('ConfigureCtrl', ['$scope', '$routeParams', 'ReportFactory',
    function($scope, $routeParams, ReportFactory) {
        var self = this;

        $scope.getReport = function () {
            ReportFactory.get({id: $routeParams.reportId}, function (result) {
                $scope.report = result;
                var admins = [];
                for (var index = 0; index < result.admins.length; index++) {
                    admins.push(result.admins[index].login);
                }
                $scope.report.admins = admins;
                $scope.loaderShow = false;
            });
        };

        $scope.getReport();

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
jiraPluginApp.controller('ConfigureGeneralDataCtrl', ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'Notification',
    function($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, Notification) {
        var self = this;
        $scope.loaderShow = true;

//----------------------------------------------------------------------------------------------------------------------
//Calender
        $scope.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            //minDate: new Date(),
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
                var reportData = $scope.report;
                reportData.closedDate = reportData.closed ? reportData.closedDate : null;
                reportData.closed = reportData.closed === undefined ? false : reportData.closed;
                reportData.closedDate = reportData.closedDate === undefined ? null : reportData.closedDate;

                ReportFactory.update({
                    id: $routeParams.reportId}, reportData, function(){
                    //FIXME not reinit select2
                    $scope.$parent.getReport();
                    Notification.success('Save report success');
                }, function () {
                    Notification.error('Server error');
                });
            }
        };

        $scope.loaderShow = false;
    }
]);

//Configure sprint team data
jiraPluginApp.controller('ConfigureSprintTeamCtrl',
    ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'SprintsFactory', 'SprintFactory', 'SprintTeamFactory', 'SprintWithTeamFactory', 'Notification',
    function($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, SprintsFactory, SprintFactory, SprintTeamFactory, SprintWithTeamFactory, Notification) {
        var self = this;
        var sprintTeamDataForAutoFill = {};
        $scope.showAutoFillData = {};

        $scope.loaderShow = true;
        $scope.sprintTeams = [];

//----------------------------------------------------------------------------------------------------------------------
//get developer
        UsersFactory.query(function(result) {
            $scope.devUsers = result.users;
        });

//----------------------------------------------------------------------------------------------------------------------
//Function for calc preview values
        $scope.calcParams = function(){
            var total = {
                engineerLevel: 0,
                participationLevel: 0,
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

                var engineerLevel = parseFloat(value.engineerLevel);
                var dv = engineerLevel == 4 ? 3 : engineerLevel;
                var pl = parseFloat(value.participationLevel);
                var dis = parseFloat(value.daysInSprint);

                var dDailyForMin = 1;
                var dDailyForMax = 2;
                if (engineerLevel == 2) {
                    dDailyForMin = 0.5;
                    dDailyForMax = 1;
                } else if (engineerLevel == 3) {
                    dDailyForMin = 0.3;
                    dDailyForMax = 0.5;
                } else if (engineerLevel == 4)  {
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
                $scope.sprintTeams[index].engineerLevel = parseFloat(value.engineerLevel);
                $scope.sprintTeams[index].targetPoints = tp;
                $scope.sprintTeams[index].targetHours = th;
                $scope.sprintTeams[index].defectMin = dMin;
                $scope.sprintTeams[index].defectMax = dMax;
                $scope.sprintTeams[index].defectHours = dh;
                $scope.sprintTeams[index].uatDefectMin = uatdMin;
                $scope.sprintTeams[index].uatDefectMax = uatdMax;
                $scope.sprintTeams[index].uatDefectHours = uatdh;

                //Total
                total.engineerLevel       += (parseFloat(value.engineerLevel) === 4 ? 3 : parseFloat(value.engineerLevel));

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
//get sprint teams data
        $scope.getSprintTeams = function (data) {
            $scope.loaderShowSprintTeam = true;
            var firstLoad = true;
            if (data === undefined) {
                if ($scope.sprints !== undefined && $scope.sprints.length > 0) {
                    data = {
                        id: $scope.sprints[0].id,
                        name: $scope.sprints[0].name
                    };
                }
            } else {
                firstLoad = false;
            }

            //get sprint teams by sprintId
            if (data !== undefined && data.id !== undefined) {
                SprintTeamFactory.query({
                    sprintId: data.id
                }, function (result) {
                    $scope.sprintTeams = result.developers;
//TODO need more tests
//----------------------------------------------------------------------------------------------------------------------
//Autofill
                    if (result.developers.length > 0) {
                        sprintTeamDataForAutoFill = {
                            sprintName:  data.name,
                            sprintTeams: result.developers
                        };

                        $scope.showAutoFillData = {
                            showAutoFillLabel: false
                        };
                    } else {
                        if (sprintTeamDataForAutoFill.sprintTeams !== undefined && sprintTeamDataForAutoFill.sprintTeams.length > 0) {
                            $scope.sprintTeams = sprintTeamDataForAutoFill.sprintTeams;

                            for (var index = 0; index < $scope.sprintTeams.length; index++) {
                                delete $scope.sprintTeams[index].id;
                            }

                            $scope.showAutoFillData = {
                                sprintName:  sprintTeamDataForAutoFill.sprintName,
                                showAutoFillLabel: true
                            };
                        }
                    }
//----------------------------------------------------------------------------------------------------------------------

                    $scope.calcParams();
                }, function (error) {
                    $scope.sprintTeams = [];
                    $scope.calcParams();
                });
            }

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
                        if ($scope.devUsersForAdd[index].login === $scope.sprintTeams[indexTeam].developerLogin) {
                            $scope.devUsersForAdd.splice(index, 1);
                        }
                    }
                }
            });

            $scope.loaderShow = false;
            $scope.loaderShowSprintTeam = false;
        };

//----------------------------------------------------------------------------------------------------------------------
//get sprints data
        $scope.getSprints = function (data) {
            var startDate = new Date();
            var endDate = new Date();
            endDate.setDate(endDate.getDate()+5);
            //get sprints
            SprintsFactory.query({reportId: $routeParams.reportId}, function(result) {
                $scope.sprints = result.sprints;
                $scope.getSprintTeams(data);
            });
        };

//----------------------------------------------------------------------------------------------------------------------
//get report data
        $scope.getReport = function (data) {
            //get report
            ReportFactory.get({id: $routeParams.reportId}, function(result){
                $scope.report = result;
                $scope.getSprints(data);
            });
        };

//----------------------------------------------------------------------------------------------------------------------
//get sprint configure data
        this.getSprintConfigureData = function (data) {
            //get report data
            $scope.getReport(data);
        };

        self.getSprintConfigureData();

//----------------------------------------------------------------------------------------------------------------------
//work with developers
        $scope.dataDeveloper = "";
        $scope.addDeveloper = function (item) {
            item = JSON.parse(item);
            $scope.sprintTeams.push({
                developerLogin: item.login,
                developerName: item.fullName,
                engineerLevel: 1,
                participationLevel: 1,
                daysInSprint: 1
            });
            $scope.dataDeveloper = "";

            $scope.calcParams();

//----------------------------------------------------------------------------------------------------------------------
//get developer
            var users = UsersFactory.query(function(){
                for (var index = 0; index < users.users.length; index++) {
                    for (var indexTeam = 0; indexTeam < $scope.sprintTeams.length; indexTeam++) {
                        if (users.users[index].login === $scope.sprintTeams[indexTeam].developerLogin) {
                            users.users.splice(index, 1);
                        }
                    }
                }
                $scope.devUsersForAdd = users.users;
            });
        };

        $scope.deleteDeveloper = function (item) {
            var index = $scope.sprintTeams.indexOf(_.findWhere($scope.sprintTeams, {developerLogin: item}));
            $scope.sprintTeams.splice(index, 1);

//----------------------------------------------------------------------------------------------------------------------
//get developer
            var users = UsersFactory.query(function(){
                for (var index = 0; index < users.users.length; index++) {
                    for (var indexTeam = 0; indexTeam < $scope.sprintTeams.length; indexTeam++) {
                        if (users.users[index].login === $scope.sprintTeams[indexTeam].developerLogin) {
                            users.users.splice(index, 1);
                        }
                    }
                }
                $scope.devUsersForAdd = users.users;
            });

            $scope.calcParams();
        };

//----------------------------------------------------------------------------------------------------------------------
//save sprint configure
        $scope.saveSprintConfigure = function () {
            //save sprint data (with sprint team)
            var sprintData = $scope.reportModel.sprint;

            sprintData.developers = [];
            for (var index = 0; index < $scope.sprintTeams.length; index++) {
                sprintData.developers.push(
                    {
                        developerName:      $scope.sprintTeams[index].developerLogin,
                        engineerLevel:      $scope.sprintTeams[index].engineerLevel,
                        participationLevel: $scope.sprintTeams[index].participationLevel,
                        daysInSprint:       $scope.sprintTeams[index].daysInSprint
                    }
                );

                if ($scope.sprintTeams[index].id !== undefined) {
                    sprintData.developers[index].id = $scope.sprintTeams[index].id;
                }
            }

            //values for sprintTeams and total sprint values
            sprintData['sprintTeams'] = $scope.sprintTeams;
            sprintData['sprintTeamsTotalValues'] = $scope.totalPreviewDetails;

            SprintWithTeamFactory.update({
                reportId: $routeParams.reportId,
                sprintId: $scope.reportModel.sprint.id
            }, sprintData, function () {
                $scope.getSprintTeams({
                    id: $scope.reportModel.sprint.id,
                    name: $scope.reportModel.sprint.name
                });
                console.log("Save sprint");
                Notification.success('Data save success');
            }, function (error) {
                $scope.getSprintTeams({
                    id: $scope.reportModel.sprint.id
                });
                Notification.error("Server error");
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
                            item: $scope.reportModel != undefined ? $scope.reportModel.sprint : undefined,
                            type: type
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                //add/edit sprint
                if(data.type === "add") {
                    data.sprint['type'] = 0;
                    data.sprint['notCountTarget'] = false;
                    data.sprint['showUat'] = false;
                    SprintsFactory.add({reportId: $routeParams.reportId}, data.sprint, function (result) {
                        self.getSprintConfigureData();
                        Notification.success('New sprint add success');
                    }, function () {
                        Notification.error("Server error");
                    });
                } else {
                    SprintFactory.update({reportId: $routeParams.reportId, sprintId: $scope.reportModel.sprint.id}, data.sprint, function () {
                        self.getSprintConfigureData({
                            id: $scope.reportModel.sprint.id
                        });
                        Notification.success('Edit sprint success');
                    }, function () {
                        Notification.error("Server error");
                    });
                }
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
                SprintFactory.delete({reportId: $routeParams.reportId, sprintId: $scope.reportModel.sprint.id}, function() {
                    self.getSprintConfigureData();
                    Notification.success('Delete sprint success');
                }, function () {
                    Notification.error("Server error");
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
                    Notification.success('Delete success');
                }, function () {
                    Notification.error('Server error');
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
            //minDate: new Date(),
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

jiraPluginApp.controller('DlgSprintTeamActivityCtrl', ['$scope', '$uibModal', '$uibModalInstance', 'dlgData', 'SprintIssuesFactory', 'SprintIssueFactory', 'Notification',
    function ($scope, $uibModal, $uibModalInstance, dlgData, SprintIssuesFactory, SprintIssueFactory, Notification) {
        var self = this;
        $scope.dlgData = dlgData;

        //get issues by sprintId and assignee
        this.getIssues = function() {
            SprintIssuesFactory.query({
                sprintId: $scope.dlgData.sprint.id,
                assignee: $scope.dlgData.developer.developerLogin
            }, function (data) {
                $scope.data = data;
            });

            //$scope.data = [
            //    {
            //        date: $scope.dlgData.sprint.startDate,
            //        issues: [
            //            {
            //                id: 1,
            //                typeName: "Story",
            //                statusName: "To Do",
            //                point: 3,
            //                hours: 8.0
            //            }
            //        ]
            //    },
            //    {
            //        date: $scope.dlgData.sprint.startDate,
            //        issues: [
            //            {
            //                id: 2,
            //                typeName: "Story",
            //                statusName: "In Progress",
            //                point: 3,
            //                hours: 8.0
            //            }
            //        ]
            //    },
            //    {
            //        date: $scope.dlgData.sprint.endDate,
            //        issues: []
            //    },
            //    {
            //        date: $scope.dlgData.sprint.endDate,
            //        issues: []
            //    }
            //]
        };
        self.getIssues();

        //get issues bys

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg add/edit issue
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
                if (data.type === "edit") {
                    var idIssue = data.id;
                    //delete data.id;
                    //delete data.type;
                    SprintIssueFactory.update({issueId: idIssue}, data, function(result){
                        self.getIssues();
                        Notification.success("Issue edit success");
                    }, function (error) {
                        Notification.error("Sertver error");
                    });
                } else {
                    delete data.type;
                    SprintIssuesFactory.add({sprintId: $scope.dlgData.sprint.id, assignee: $scope.dlgData.developer.developerLogin}, data, function(result){
                        self.getIssues();
                        Notification.success("Issue add success");
                    }, function (error) {
                        Notification.error("Sertver error");
                    });
                }
            }, function () {});
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete issue
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
                    Notification.success("Issue delete success");
                }, function (error) {
                    Notification.error("Sertver error");
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