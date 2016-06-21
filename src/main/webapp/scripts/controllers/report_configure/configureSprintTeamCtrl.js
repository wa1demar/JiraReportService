(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('ConfigureSprintTeamCtrl', ConfigureSprintTeamCtrl);

    ConfigureSprintTeamCtrl.$inject = ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'SprintsFactory', 'SprintFactory', 'SprintTeamFactory', 'SprintWithTeamFactory', 'Notification'];

    function ConfigureSprintTeamCtrl($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, SprintsFactory, SprintFactory, SprintTeamFactory, SprintWithTeamFactory, Notification) {
        var self = this;
        var sprintTeamDataForAutoFill = {};
        $scope.showAutoFillData = {};

        $scope.loaderShow = true;
        $scope.sprintTeams = [];

        //get developer
        UsersFactory.query({}, function(result) {
            $scope.devUsers = result.users;
        });

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

            //get developer
            //TODO need refactoring
            UsersFactory.query({id: "filtered"}, function(users) {
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

        //get report data
        $scope.getReport = function (data) {
            //get report
            ReportFactory.get({id: $routeParams.reportId}, function(result){
                $scope.report = result;
                $scope.getSprints(data);

                //data for link
                $scope.configureReportInfo.id = result.id;
                $scope.configureReportInfo.title = result.title;
            });
        };

        //get sprint configure data
        this.getSprintConfigureData = function (data) {
            //get report data
            $scope.getReport(data);
        };

        self.getSprintConfigureData();

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

            //get developer
            UsersFactory.query({id: "filtered"}, function(users){
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

            //get developer
            UsersFactory.query({id: "filtered"}, function(users){
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

        //Dlg process sprint (type: add/edit)
        $scope.dlgData = {};
        $scope.processSprint = function (type) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/report_configure/dlg/dlg_process_sprint.html',
                controller: 'DlgProcessSprintCtrl',
                resolve: {
                    dlgData: function () {
                        return {
                            item: $scope.reportModel != undefined ? $scope.reportModel.sprint : undefined,
                            type: type,
                            report: $scope.report
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

        //Dlg delete sprint
        $scope.deleteSprint = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/dlg_delete/dlg_delete_element.html',
                controller: 'DlgDeleteCtrl',
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

        //Dlg developer activity
        $scope.dlgSprintTeamActivity = function (sprint, developer) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/report_configure/dlg/dlg_sprint_team_activity.html',
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

})();