(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('ProjectManagementCtrl', ProjectManagementCtrl);

    ProjectManagementCtrl.$inject = ['$scope', '$uibModal', '$filter', '$window', 'ProjectFactory', 'UsersFactory', 'DictionaryFactory', 'ResourceColumnFactory', 'Notification'];

    function ProjectManagementCtrl($scope, $uibModal, $filter, $window, ProjectFactory, UsersFactory, DictionaryFactory, ResourceColumnFactory, Notification) {
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

// ----------------------------------------------------------------------------------------------------------------------
        $scope.lightColor = function (hex, lum) {
            return colorLuminance(hex, lum);
        };

//----------------------------------------------------------------------------------------------------------------------
//Get all data for Resource Board
        $scope.columns = [];
        $scope.getProjectColumns = function (showLoader) {
            var showLoader = showLoader !== undefined ? showLoader : false;
            if (!!showLoader) {
                $scope.loaderShow = true;
            }

            //set data to search query
            var searchQuery = {
                "technology": $scope.search.technology,
                "engineerLevel": $scope.search.engineerLevel,
                "pm": $scope.search.pm,
                "project": $scope.search.project
            };

            ProjectFactory.query({id: 'list_with_users'}, searchQuery, function (result) {
                $scope.columns = result.projects;
                $scope.loaderShow = false;
            }, function (error) {
                Notification.error("Server error");
            });

            // $scope.columns = [
            //     {
            //         id: 1,
            //         title: "Project 1",
            //         description: "Description",
            //         users: [
            //             {
            //                 id: 1,
            //                 name: "Full Name 1",
            //                 engineerLevel: 1,
            //                 assignmentTypes: [{id: 3, name: "PM", color: "#4086E7"}],
            //                 avatar: "https://swansoftwaresolutions.atlassian.net/secure/useravatar?ownerId=slevchenko&avatarId=13706",
            //                 column: {id: 1, name: "Project 1"}
            //             },
            //             {
            //                 id: 2,
            //                 name: "Full Name 2",
            //                 engineerLevel: 3,
            //                 assignmentTypes: [
            //                     {id: 11, name: "Dev", color: "#179f1c"},
            //                     {id: 12, name: "Shadow", color: "#424242"}
            //                 ],
            //                 column: {id: 1, name: "Project 1"}
            //             },
            //             {
            //                 id: 11,
            //                 name: "Full Name 3",
            //                 engineerLevel: 2,
            //                 assignmentTypes: [{id: 11, name: "Dev", color: "#179f1c"}],
            //                 column: {id: 1, name: "Project 1"}
            //             },
            //             {
            //                 id: 12,
            //                 name: "Full Name 5",
            //                 engineerLevel: 2,
            //                 assignmentTypes: [{id: 5, name: "QA", color: "#F4D520"}],
            //                 column: {id: 1, name: "Project 1"}
            //             }
            //         ]
            //     },
            //     {
            //         id: 2,
            //         title: "Project 2",
            //         description: "Description",
            //         users: [
            //             {
            //                 id: 3,
            //                 name: "Full Name 2",
            //                 engineerLevel: 3,
            //                 assignmentTypes: [{id: 3, name: "PM", color: "#4086E7"}],
            //                 column: {id: 2, name: "Project 2"}
            //             }
            //         ]
            //     },
            //     {
            //         id: 3,
            //         title: "Project 3",
            //         description: "Description",
            //         users: [
            //             {
            //                 id: 4,
            //                 name: "Full Name 3",
            //                 engineerLevel: 2,
            //                 assignmentTypes: [{id: 3, name: "PM", color: "#4086E7"}],
            //                 column: {id: 3, name: "Project 3"}
            //             },
            //             {
            //                 id: 2,
            //                 name: "Full Name 2",
            //                 engineerLevel: 3,
            //                 assignmentTypes: [
            //                     {id: 12, name: "Shadow", color: "#424242"},
            //                     {id: 11, name: "Dev", color: "#179f1c"}
            //                 ],
            //                 column: {id: 1, name: "Project 1"}
            //             }
            //         ]
            //     },
            //     {
            //         id: 4,
            //         title: "Project 4",
            //         description: "Description",
            //         users: [
            //             {
            //                 id: 5,
            //                 name: "Full Name 4",
            //                 engineerLevel: 2,
            //                 assignmentTypes: [{id: 3, name: "PM", color: "#4086E7"}],
            //                 column: {id: 4, name: "Project 4"}
            //             }
            //         ]
            //     }
            // ];

            console.log($scope.columns);
        };

        $scope.currentProject = {};

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

        //Get assignment type
        $scope.assignmentTypes = [];
        $scope.getAssignmentTypes = function () {
            ResourceColumnFactory.query({id: 'sorted_list'}, function(result){
                $scope.assignmentTypes = result.columns;
            }, function (error) {
                Notification.error("Server error: get assignment type");
            });
        };
        $scope.getAssignmentTypes();

        //TODO Get projects
        $scope.projects = [];
        $scope.getProjects = function () {

            // $scope.projects = [
            //     {id: 1, title: "Project 1"},
            //     {id: 2, title: "Project 2"},
            //     {id: 3, title: "Project 3"},
            //     {id: 4, title: "Project 4"},
            // ];

            ProjectFactory.query(function(result){
                $scope.projects = result.projects;
            }, function (error) {
                Notification.error("Server error: get projects");
            });
        };
        $scope.getProjects();

        $scope.$watch('search', function() {
            console.log(" ---- new search");
            $window.localStorage.pm_search = JSON.stringify($scope.search);
            $scope.getProjectColumns(true);
        }, true);

        //Dragend project
        $scope.dragendProject = function () {
            var dataForUpdate = $scope.columns.map(function(value, index) {
                return {
                    columnId: value.id,
                    columnPriority: index
                };
            });
            console.log(dataForUpdate);

            //TODO need add request for save new data
            // ProjectFactory.update({id: 'sort'}, {filters: $scope.search, items: dataForUpdate}, function(result){
            //     $scope.columns = result.columns;
            // }, function (error) {
            //     Notification.error("Server error: save assignment type");
            // });
        };

        $scope.dragoverPositions = {
            parentIndex: null,
            index: null
        };
        $scope.dragoverCallback = function(event, projectIndex, memberIndex, external, type) {
            $scope.dragoverPositions = {
                projectIndex:   projectIndex,
                memberIndex:    memberIndex
            };

            return true;
        };

        //Dragend member
        $scope.dragendElement = function(item, projectIndex, memberIndex) {

            console.log('----------------------');
            console.log($scope.dragoverPositions);
            console.log('----------------------');
            if ($scope.dragoverPositions.parentIndex === null || $scope.dragoverPositions.memberIndex === null) {
                return false;
            }

            //if sort elements
            if ($scope.dragoverPositions.projectIndex === projectIndex) {
                //delete old position
                $scope.columns[projectIndex].users.splice(memberIndex, 1);

                //TODO save new data

                Notification.success("Update projects success");
            } else {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'scripts/controllers/project_management/dlg/dlg_member_change_project.html',
                    controller: 'DlgMemberChangeProject',
                    size: 'md',
                    resolve: {
                        dlgData: function () {
                            return {
                                item: item,
                                projectIndex: projectIndex,
                                memberIndex: memberIndex,
                                assignmentTypes: $scope.assignmentTypes
                            };
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    console.log($scope.dragoverPositions);
                    if (data.moveType === 'move') {
                        $scope.columns[projectIndex].users.splice(memberIndex, 1);
                    }

                    //TODO save new data

                    Notification.success("Update projects success");
                }, function (error) {
                    //delete element which moved
                    $scope.columns[$scope.dragoverPositions.projectIndex].users.splice($scope.dragoverPositions.memberIndex, 1);
                });
            }

            // console.log(projectIndex);
            return true;
        };

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

        //Show member info in right part
        $scope.showProjectInfoData = function(item) {
            // console.log('showProjectInfoData');
            $scope.currentProject = item;
        };

        //Show search in right part
        $scope.showSearchFilters = function() {
            // console.log('showSearchFilters');
        };

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

        //Dlg process project
        $scope.processProject = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/project_management/dlg/dlg_process_project.html',
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

        //Dlg delete project
        $scope.delProject = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/dlg_delete/dlg_delete_element.html',
                controller: 'DlgDeleteCtrl',
                resolve: {
                    dlgData: function () {
                        return {
                            id: item.id,
                            name: item.title
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                ProjectFactory.delete({id: data.id}, function() {
                    Notification.success("Delete project success");
                    //get projects
                    $scope.getProjectColumns();
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg delete project member
        $scope.delProjectMember = function (item) {
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

        //Contextmenu
        var customItem = {
            html: '<a><b>Send to</b></a>',
            enabled: function() {return false}
        };

        var hideContextMenu = true;
        $scope.memberMenuOptions = function (column, item) {
            if (hideContextMenu) { return []; }

            $scope.columns.selected = null;
            $scope.selectElement(column);

            var menu = [];
            menu.push(customItem);
            for (var index = 0; index < $scope.projects.length; index++) {
                //check column by id
                if (column.id != $scope.projects[index].id) {
                    menu.push(
                        {
                            html: '<a tabindex="-1" href="#" class="context-menu-item" data-id-project-index="'+index+'">'+$scope.projects[index].title+'</a>',
                            enabled: function() {return true},
                            click: function ($itemScope, $event, value, html) {

                                var columnFromIndex = $itemScope.$parent.$index,
                                    columnToIndex   = $(html).data("idProjectIndex");

                                //change assignment type
                                // $scope.columns[columnFromIndex].users.splice($itemScope.$index, 1);
                                // $scope.columns[columnToIndex].users.push($itemScope.item);

                                var dataForUpdate = {
                                    assignmentType: {
                                        fromAssignmentTypeId:   $scope.columns[columnFromIndex].id,
                                        toAssignmentTypeId:     $scope.columns[columnToIndex].id
                                    },
                                    users: $scope.columns[columnToIndex].users.map(function(value, index) {
                                        return {
                                            login: value.login,
                                            index: index
                                        };
                                    })
                                };

                                //dlg
                                var modalInstance = $uibModal.open({
                                    animation: true,
                                    templateUrl: 'scripts/controllers/project_management/dlg/dlg_member_change_project.html',
                                    controller: 'DlgMemberChangeProject',
                                    size: 'md',
                                    resolve: {
                                        dlgData: function () {
                                            return {
                                                item: item,
                                                projectIndex: columnFromIndex,
                                                memberIndex: $itemScope.$index,
                                                assignmentTypes: $scope.assignmentTypes
                                            };
                                        }
                                    }
                                });
                                modalInstance.result.then(function (data) {
                                    if (data.moveType === 'move') {
                                        $scope.columns[columnFromIndex].users.splice($itemScope.$index, 1);
                                        $scope.columns[columnToIndex].users.push($itemScope.item);
                                    } else {
                                        $scope.columns[columnToIndex].users.push($itemScope.item);
                                    }

                                    //TODO save new data

                                    Notification.success("Update projects success");
                                }, function (error) {

                                });

                                //add request for save new data
                                // $scope.moveMember(dataForUpdate, columnToIndex, $scope.columns[columnToIndex].users.length - 1);
                            }
                        }
                    );
                }
            }

            menu.push(
                null,
                {
                    html: '<a tabindex="-1" href="#" class="context-menu-item">Top of the column</a>',
                    enabled: function($itemScope) {
                        //check position for enabled button
                        return $itemScope.$index !== 0;
                    },
                    click: function ($itemScope, $event, value) {
                        console.log('Top of the column');
                        $scope.columns[$itemScope.$parent.$index].users.splice($itemScope.$index, 1);
                        $scope.columns[$itemScope.$parent.$index].users.unshift($itemScope.item);

                        var dataForUpdate = {
                            assignmentType: {
                                fromAssignmentTypeId:   $scope.columns[$itemScope.$parent.$index].id,
                                toAssignmentTypeId:     $scope.columns[$itemScope.$parent.$index].id
                            },
                            users: $scope.columns[$itemScope.$parent.$index].users.map(function(value, index) {
                                return {
                                    login: value.login,
                                    index: index
                                };
                            })
                        };

                        //add request for save new data
                        // $scope.moveMember(dataForUpdate, $itemScope.$parent.$index, 0);
                    }
                },
                {
                    html: '<a tabindex="-1" href="#" class="context-menu-item">Bottom of the column</a>',
                    enabled: function($itemScope) {
                        //check position for enabled button
                        return $itemScope.$index !== $scope.columns[$itemScope.$parent.$index].users.length - 1;
                    },
                    click: function ($itemScope, $event, value) {
                        console.log('Bottom of the column');
                        $scope.columns[$itemScope.$parent.$index].users.splice($itemScope.$index, 1);
                        $scope.columns[$itemScope.$parent.$index].users.push($itemScope.item);

                        var dataForUpdate = {
                            assignmentType: {
                                fromAssignmentTypeId:   $scope.columns[$itemScope.$parent.$index].id,
                                toAssignmentTypeId:     $scope.columns[$itemScope.$parent.$index].id
                            },
                            users: $scope.columns[$itemScope.$parent.$index].users.map(function(value, index) {
                                return {
                                    login: value.login,
                                    index: index
                                };
                            })
                        };

                        //add request for save new data
                        // $scope.moveMember(dataForUpdate, $itemScope.$parent.$index, $scope.columns[$itemScope.$parent.$index].users.length - 1);
                    }
                }
            );

            return menu;
        };

        //Save data after member move
        $scope.moveMember = function (dataForUpdate, indexColumn, indexElementInColumn) {
            // dataForUpdate["filters"] = $scope.search;
            // ProjectFactory.update({login: $scope.currentMember.login, relation: "move"}, dataForUpdate, function(data){
            //     //update member info from backend
            //     // $scope.columns = data.columns;
            //
            //     // if ($scope.currentMember !== null) {
            //     //     $scope.selectElement($scope.currentMember);
            //     // }
            //
            //     //update member info without getResourceColumns
            //     $scope.columns[indexColumn].users[indexElementInColumn] = data;
            //     //reindexing resourceOrder
            //     $scope.columns[indexColumn].users.map(function(value, index) {
            //         value.resourceOrder = index;
            //
            //         return value;
            //     });
            //     $scope.selectElement(data);
            //
            //     Notification.success("Save changes success");
            // }, function (error) {
            //     Notification.error("Server error: get assignment type");
            // });
        };

    }

})();