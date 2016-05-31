(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('ProjectManagementCtrl', ProjectManagementCtrl);

    ProjectManagementCtrl.$inject = ['$scope', '$uibModal', '$filter', '$window', 'ProjectFactory', 'UsersFactory', 'DictionaryFactory', 'ResourceColumnFactory', 'MemberFactory', 'Notification'];

    function ProjectManagementCtrl($scope, $uibModal, $filter, $window, ProjectFactory, UsersFactory, DictionaryFactory, ResourceColumnFactory, MemberFactory, Notification) {
        var self = this;
        $scope.loaderShow = true;
        $scope.showSearch = true;
        $scope.showProjectInfo = false;
        $scope.showResourceLoader = false;

// ----------------------------------------------------------------------------------------------------------------------
        $scope.lightColor = function (hex, lum) {
            return colorLuminance(hex, lum);
        };

// ----------------------------------------------------------------------------------------------------------------------
//prepare search params
        if ($window.localStorage.pm_search) {
            $scope.search = JSON.parse($window.localStorage.pm_search);
        } else {
            $scope.search = {
                technology: [],
                location: [],
                project: [],
                engineerLevel: [],
                assignmentType: []
            };
        }

//----------------------------------------------------------------------------------------------------------------------
//Get all data for Resource Board
        $scope.columns = [];
        $scope.getProjectColumns = function (showLoader, showResourceLoader) {
            var showLoader = showLoader !== undefined ? showLoader : false;
            if (!!showLoader) {
                $scope.loaderShow = true;
            }
            var showResourceLoader = showResourceLoader !== undefined ? showResourceLoader : false;
            if (!!showResourceLoader) {
                $scope.showResourceLoader = true;
            }

            //set data to search query
            var searchQuery = {
                "id": "list_with_users",
                "technology": $scope.search.technology,
                "location": $scope.search.location,
                "project": $scope.search.project,
                "engineerLevel": $scope.search.engineerLevel,
                "assignmentType": $scope.search.assignmentType
            };

            ProjectFactory.query(searchQuery, function (result) {
                $scope.columns = result.projects;
                $scope.loaderShow = false;
                $scope.showResourceLoader = false;
            }, function (error) {
                Notification.error("Server error");
            });

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

        //Get projects
        $scope.projects = [];
        $scope.getProjects = function () {
            ProjectFactory.query(function(result){
                $scope.projects = result.projects;
            }, function (error) {
                Notification.error("Server error: get projects");
            });
        };
        $scope.getProjects();

        //Get positions
        $scope.positions = [];
        $scope.getPositions = function () {
            DictionaryFactory.query({name: 'positions'}, function(result){
                $scope.positions = result.items;
            }, function (error) {
                Notification.error("Server error: get positions");
            });
        };
        $scope.getPositions();

        //Get locations
        $scope.locations = [];
        $scope.getLocations = function () {
            DictionaryFactory.query({name: 'locations'}, function(result){
                $scope.locations = result.items;
            }, function (error) {
                Notification.error("Server error: get locations");
            });
        };
        $scope.getLocations();

        $scope.$watch('search', function() {
            console.log(" ---- new search");
            $window.localStorage.pm_search = JSON.stringify($scope.search);

            $scope.isSearch = true;
            if ($scope.search.technology.length === 0 &&
                $scope.search.location.length === 0 &&
                $scope.search.project.length === 0 &&
                $scope.search.engineerLevel.length === 0 &&
                $scope.search.assignmentType.length === 0) {
                $scope.isSearch = false;
            }

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
            //request for save new data
            ProjectFactory.update({id: 'sort'}, {filters: $scope.search, items: dataForUpdate}, function(result){
                $scope.columns = result.projects;
                Notification.success("Save changes success");
            }, function (error) {
                Notification.error("Server error: save projects");
            });
        };

        $scope.insertedPositions = {
            columnIndex: null,
            memberIndex: null
        };
        $scope.insertedElement = function(columnIndex, memberIndex, event) {
            $scope.insertedPositions = {
                columnIndex: columnIndex,
                memberIndex: memberIndex
            };
            console.log('$scope.insertedElement');
        };

        //Dragend member
        $scope.dragendElement = function(item, columnFrom, projectIndex, memberIndex) {

            var moveMember = $scope.columns[projectIndex].users[memberIndex];

            if ($scope.insertedPositions === null ||
                $scope.insertedPositions.columnIndex === null ||
                $scope.insertedPositions.memberIndex === null) {
                return false;
            }
            var columnTo = $scope.columns[$scope.insertedPositions.columnIndex];

            //if sort elements
            if ($scope.insertedPositions.columnIndex === projectIndex) {
                //delete old position
                $scope.columns[projectIndex].users.splice(memberIndex, 1);

                var dataForUpdate = {
                    projects: {
                        fromProjectId:   columnFrom.id,
                        toProjectId:     columnTo.id
                    },
                    users: $scope.columns[projectIndex].users.map(function(value, index) {
                        return {
                            login: value.login,
                            index: index
                        };
                    }),
                    assignmentTypeId: moveMember.assignmentTypes[0].id
                };

                //save new data
                $scope.moveMember(dataForUpdate, moveMember.login, "move", true);

                $scope.insertedPositions = null;
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
                    console.log($scope.insertedPositions);

                    //Count users in column > 1
                    if (_.where(
                            $scope.columns[$scope.insertedPositions.columnIndex].users,
                            {login: $scope.columns[projectIndex].users[memberIndex].login}
                        ).length > 1) {
                        $scope.columns[$scope.insertedPositions.columnIndex].users.splice($scope.insertedPositions.memberIndex, 1);
                        $scope.insertedPositions = null;
                        Notification.error("Error: user exist in this project");

                        return true;
                    }

                    if (data.moveType === 'move') {
                        $scope.columns[projectIndex].users.splice(memberIndex, 1);
                    }

                    var dataForUpdate = {
                        projects: {
                            fromProjectId:   columnFrom.id,
                            toProjectId:     columnTo.id
                        },
                        users: $scope.columns[$scope.insertedPositions.columnIndex].users.map(function(value, index) {
                            return {
                                login: value.login,
                                index: index
                            };
                        }),
                        assignmentTypeId: data.assignmentType
                    };

                    //save new data
                    $scope.moveMember(dataForUpdate, moveMember.login, data.moveType, true);

                    $scope.insertedPositions = null;
                }, function (error) {
                    //delete element which moved
                    $scope.columns[$scope.insertedPositions.columnIndex].users.splice($scope.insertedPositions.memberIndex, 1);

                    $scope.insertedPositions = null;
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
                // $scope.currentProject = data;
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
                ProjectFactory.delete({id: $scope.currentProject.id, relation: "members", idRelation: data.login}, function(data) {
                    Notification.success("Delete member success");
                    //get member info
                    $scope.currentProject = data;
                    $scope.getProjectColumns(false, true);
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Contextmenu
        var customItem = {
            html: '<a><b>Send to</b></a>',
            enabled: function() {return false}
        };

        var hideContextMenu = false;
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

                                    //user exist in project
                                    if (_.findWhere($scope.columns[columnToIndex].users, {login: $itemScope.item.login}) !== undefined) {
                                        Notification.error("Error: user exist in this project");

                                        return true;
                                    }

                                    if (data.moveType === 'move') {
                                        $scope.columns[columnFromIndex].users.splice($itemScope.$index, 1);
                                        $scope.columns[columnToIndex].users.push($itemScope.item);
                                    } else {
                                        $scope.columns[columnToIndex].users.push($itemScope.item);
                                    }

                                    var dataForUpdate = {
                                        projects: {
                                            fromProjectId:   $scope.columns[columnFromIndex].id,
                                            toProjectId:     $scope.columns[columnToIndex].id
                                        },
                                        users: $scope.columns[columnToIndex].users.map(function(value, index) {
                                            return {
                                                login: value.login,
                                                index: index
                                            };
                                        }),
                                        assignmentTypeId: data.assignmentType
                                    };

                                    //save new data
                                    $scope.moveMember(dataForUpdate, $itemScope.item.login, data.moveType, true);
                                }, function (error) {

                                });
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
                            projects: {
                                fromProjectId:   $scope.columns[$itemScope.$parent.$index].id,
                                toProjectId:     $scope.columns[$itemScope.$parent.$index].id
                            },
                            users: $scope.columns[$itemScope.$parent.$index].users.map(function(value, index) {
                                return {
                                    login: value.login,
                                    index: index
                                };
                            }),
                            assignmentTypeId: $itemScope.item.assignmentTypes[0].id
                        };

                        //save new data
                        $scope.moveMember(dataForUpdate, $itemScope.item.login, 'move');
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
                            projects: {
                                fromProjectId:   $scope.columns[$itemScope.$parent.$index].id,
                                toProjectId:     $scope.columns[$itemScope.$parent.$index].id
                            },
                            users: $scope.columns[$itemScope.$parent.$index].users.map(function(value, index) {
                                return {
                                    login: value.login,
                                    index: index
                                };
                            }),
                            assignmentTypeId: $itemScope.item.assignmentTypes[0].id
                        };

                        //save new data
                        $scope.moveMember(dataForUpdate, $itemScope.item.login, 'move');
                    }
                }
            );

            return menu;
        };

        //Save data after member move
        $scope.moveMember = function (dataForUpdate, memberLogin, moveType, showResourceLoader) {
            // $scope.loaderShow = true;
            $scope.showResourceLoader = true;
            dataForUpdate["filters"] = $scope.search;
            ProjectFactory.update({id: 0, relation: 'members', idRelation: memberLogin, typeRelation: moveType}, dataForUpdate, function(data){
                // $scope.columns = data.projects;

                //FIXME need change to $scope.columns = data.projects;
                $scope.getProjectColumns(false, showResourceLoader);

                Notification.success("Save changes success");
            }, function (error) {
                Notification.error("Server error: save changes");
            });
        };

        $scope.exportProjects = function () {
            //dlg
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/project_management/dlg/dlg_export_projects.html',
                controller: 'DlgExportProjects',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    dlgData: function () {
                        return {
                            columns: $scope.columns
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {

            }, function (error) {

            });
        };

    }

})();