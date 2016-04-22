'use strict';

jiraPluginApp.controller('ProjectManagementCtrl',
    ['$scope', '$uibModal', '$filter', '$window', 'ProjectFactory', 'UsersFactory', 'DictionaryFactory', 'MemberFactory', 'Notification',
        function($scope, $uibModal, $filter, $window, ProjectFactory, UsersFactory, DictionaryFactory, MemberFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;
            $scope.showSearch = true;
            $scope.showProjectInfo = false;
            //TODO need get from db
            $scope.engineerLevelDictionary = [1, 2, 3, 4];

// ----------------------------------------------------------------------------------------------------------------------
//prepare search params
            if ($window.localStorage.pm_search) {
                $scope.search = JSON.parse($window.localStorage.pm_search);
            } else {
                $scope.search = {
                    technology: [],
                    engineerLevel: [],
                    pm: [],
                    project: []
                };
            }

//----------------------------------------------------------------------------------------------------------------------
//Get all data for Resource Board
            $scope.columns = [];
            $scope.getProjectColumns = function () {

                //set data to search query
                // var searchQuery = {
                //     "technology[]": $scope.search.technology,
                //     "engineerLevel[]": $scope.search.engineerLevel,
                //     "pm[]": $scope.search.pm,
                //     "project[]": $scope.search.project
                // };
                //
                // ResourceColumnFactory.query(searchQuery, function (result) {
                //     $scope.columns = result.columns;
                //     $scope.loaderShow = false;
                // }, function (error) {
                //     Notification.error("Server error");
                // });
                
                $scope.lightColor = function (hex, lum) {
                    return colorLuminance(hex, lum);
                };

                $scope.columns = [
                    {
                        id: 1,
                        name: "Project 1",
                        description: "Description",
                        users: [
                            {
                                id: 1,
                                name: "Full Name 1",
                                engineerLevel: 1,
                                assignmentType: {id: 1, name: "PM"},
                                color: "#4086E7",
                                avatar: "https://swansoftwaresolutions.atlassian.net/secure/useravatar?ownerId=slevchenko&avatarId=13706"
                            },
                            {
                                id: 2,
                                name: "Full Name 2",
                                engineerLevel: 3,
                                assignmentType: {id: 2, name: "Dev"},
                                color: "#179f1c"
                            },
                            {
                                id: 3,
                                name: "Full Name 3",
                                engineerLevel: 2,
                                assignmentType: {id: 2, name: "Dev"},
                                color: "#179f1c"
                            },
                            {
                                id: 3,
                                name: "Full Name 5",
                                engineerLevel: 2,
                                assignmentType: {id: 3, name: "QA"},
                                color: "#F4D520"
                            }
                        ]
                    },
                    {
                        id: 2,
                        name: "Project 2",
                        description: "Description",
                        users: [
                            {
                                id: 3,
                                name: "Full Name 2",
                                engineerLevel: 3,
                                assignmentType: {id: 1, name: "PM"},
                                color: "#4086E7"
                            }
                        ]
                    }
                ];

                console.log($scope.columns);

            };
            $scope.getProjectColumns();

            $scope.currentProject = {};

//----------------------------------------------------------------------------------------------------------------------
//Get technologies
            $scope.technologies = [];
            $scope.getTechnologies = function () {
                DictionaryFactory.query({name: 'technologies'}, function(result){
                    $scope.technologies = result.items;
                }, function (error) {
                    Notification.error("Server error: get technologies");
                });
            };
            $scope.getTechnologies();

//----------------------------------------------------------------------------------------------------------------------
//TODO Get projects
            $scope.projects = [];
            $scope.getProjects = function () {
                $scope.projects = [
                    {id: 1, name: "project 1"},
                    {id: 2, name: "project 2"},
                    {id: 3, name: "project 3"},
                    {id: 4, name: "project 4"},
                    {id: 5, name: "project 5"}
                ];
            };
            $scope.getProjects();

            // $scope.changeSearch = function() {
            //     // console.log($scope.search);
            // };

            $scope.$watch('search', function() {
                console.log(" ---- new search");
                $window.localStorage.pm_search = JSON.stringify($scope.search);
                $scope.getProjectColumns();
            }, true);

//----------------------------------------------------------------------------------------------------------------------
//Get dragend member
            $scope.dragendElement = function(item) {
                //Find column when drag
                var column = undefined;
                var result = undefined;
                var indexElementInColumn = null;
                var count = $scope.columns.length;
                for (var index = 0; index < count; index++) {
                    result = _.findWhere($scope.columns[index].users, {login: item.login});
                    if (result !== undefined) {
                        //save column data
                        column = $scope.columns[index];
                        //find index new position in column
                        indexElementInColumn = $scope.columns[index].users.indexOf(result);
                        break;
                    }
                }

                //TODO save new member position
                //change member assignment type
                $scope.columns[index].users[indexElementInColumn].assignmentType = column.id;
                console.log("column id: " + column.id + " ---- position: " + indexElementInColumn);
                // $scope.getProjectColumns();
            };

//----------------------------------------------------------------------------------------------------------------------
//Select member
            $scope.selectElement = function (item) {
                console.log(item);
                if ($scope.columns.selected === item) {
                    $scope.columns.selected = null;

                    $scope.showSearch = true;
                    $scope.showProjectInfo = false;

                    $scope.showSearchFilters();
                } else {
                    $scope.columns.selected = item;

                    $scope.showSearch = false;
                    $scope.showProjectInfo = true;

                    $scope.showProjectInfoData(item);
                }
            };

//----------------------------------------------------------------------------------------------------------------------
//Show member info in right part
            $scope.showProjectInfoData = function(item) {
                console.log('showProjectInfoData');
                $scope.currentProject = item;
            };

//----------------------------------------------------------------------------------------------------------------------
//Show search in right part
            $scope.showSearchFilters = function() {
                console.log('showSearchFilters');
            };

//----------------------------------------------------------------------------------------------------------------------
//Change some search filter
            $scope.searchFiltersChange = function() {
                console.log('searchFiltersChange');
            };

//----------------------------------------------------------------------------------------------------------------------
//Update member description
            $scope.updateProjectDescription = function(data) {
                console.log('updateProjectDescription:');
                console.log(data);
                $scope.currentProject.description = data;
                ProjectFactory.update({id: $scope.currentProject.id}, $scope.currentProject, function(data){
                    Notification.success("Update description success");
                    //get projects
                    // $scope.getProjectColumns();
                }, function (error) {
                    Notification.error("Server error");
                });
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg process project
            $scope.processProject = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/project_management/dlg/dlg_process_project.html',
                    controller: 'DlgProcessProjectCtrl',
                    size: 'md',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    if (data.id == undefined) {
                        ProjectFactory.create({}, data, function(data){
                            Notification.success("Create project success");
                            $scope.getProjectColumns();
                        }, function (error) {
                            Notification.error("Server error");
                        });
                    } else {
                        //TODO need fix
                        // delete data.users;
                        ProjectFactory.update({id: data.id}, data, function(data){
                            Notification.success("Update project success");
                            $scope.getProjectColumns();
                        }, function (error) {
                            Notification.error("Server error");
                        });
                    }
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete project
            $scope.delProject = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dlg/dlg_delete_element.html',
                    controller: 'DlgDeleteProjectCtrl',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    ProjectFactory.delete({id: $scope.currentMember.id}, function() {
                        Notification.success("Delete project success");
                        //get projects
                        $scope.getProjectColumns();
                    }, function () {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete project member
            $scope.delProjectMember = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dlg/dlg_delete_element.html',
                    controller: 'DlgDeleteProjectMemberCtrl',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    console.log(data);
                    // ProjectFactory.delete({id: $scope.currentMember.id}, function() {
                    //     Notification.success("Delete project success");
                    //     //get projects
                    //     $scope.getProjectColumns();
                    // }, function () {
                    //     Notification.error("Server error");
                    // });
                }, function () {});
            };

        }
]);

jiraPluginApp.controller('DlgProcessProjectCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
        function ($scope, $uibModalInstance, dlgData) {
            $scope.model = dlgData;

            $scope.ok = function () {
                if($scope.processProjectForm.$valid) {
                    $uibModalInstance.close($scope.model);
                }
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ]);

jiraPluginApp.controller('DlgDeleteProjectCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
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

jiraPluginApp.controller('DlgDeleteProjectMemberCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
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