(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('ResourceManagementCtrl', ResourceManagementCtrl);

    ResourceManagementCtrl.$inject = ['$scope', '$uibModal', '$filter', '$window', 'ResourceColumnFactory', 'UsersFactory', 'DictionaryFactory', 'MemberFactory', 'ProjectFactory', 'Notification'];

    function ResourceManagementCtrl($scope, $uibModal, $filter, $window, ResourceColumnFactory, UsersFactory, DictionaryFactory, MemberFactory, ProjectFactory, Notification) {
        var self = this;
        $scope.loaderShow = true;
        $scope.showSearch = true;
        $scope.showMemberInfo = false;

        //For change assignment type in sselect box
        $scope.model = {
            memberAssignmentTypeId: null
        };

        // $scope.filterFunction = function(element) {
        //
        //     var searchFlag = true;
        //
        //     console.log($scope.search.name);
        //
        //     if ($scope.search.name !== '' && !element.name.match('/^'+$scope.search.name+'/')) {
        //         searchFlag = false;
        //     }
        //
        //     return searchFlag;
        //
        //     // return element.name.match(/^Dima/) ? true : false;
        // };

// ----------------------------------------------------------------------------------------------------------------------
//prepare search params
        if ($window.localStorage.rm_search) {
            $scope.search = JSON.parse($window.localStorage.rm_search);
        } else {
            $scope.search = {
                technology: [],
                project: [],
                engineerLevel: [],
                location: [],
                name: null
            };
        }

        //Get all data for Resource Board
        $scope.columns = [];
        $scope.getResourceColumns = function (showLoader) {
            var showLoader = showLoader !== undefined ? showLoader : false;
            if (!!showLoader) {
                $scope.loaderShow = true;
            }

            //set data to search query
            var searchQuery = {
                "technology": $scope.search.technology,
                "project": $scope.search.project,
                "engineerLevel": $scope.search.engineerLevel,
                "location": $scope.search.location,
                name: $scope.search.name === '' ? null : $scope.search.name
            };

            ResourceColumnFactory.query(searchQuery, function (result) {
                $scope.columns = result.columns;

                //TODO need more tests
                //Find user for check selected
                if ($scope.currentMember != null) {
                    var result = undefined;
                    var count = $scope.columns.length;
                    for (var index = 0; index < count; index++) {
                        result = _.findWhere($scope.columns[index].users, {login: $scope.currentMember.login});
                        if (result !== undefined) {
                            //save selected member
                            $scope.columns.selected = result;
                            break;
                        }
                    }
                }

                $scope.loaderShow = false;
            }, function (error) {
                Notification.error("Server error");
            });
        };

        $scope.currentMember = {};

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

        //TODO Get projects
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

        //Get member by login
        $scope.getMemberByLogin = function (login) {
            MemberFactory.get({login: login}, function(result){
                $scope.currentMember = result;
            }, function (error) {
                Notification.error("Server error: get member by login");
            });
        };

        //Update member counts
        // $scope.updateMemberCounts = function () {
        //     _.each($scope.columns, function(element, index, list) {
        //         $scope.columns[index].allMembersCount = $scope.columns[index].users.length;
        //     });
        // };

        $scope.$watch('search', function() {
            console.log(" ---- new search");
            $window.localStorage.rm_search = JSON.stringify($scope.search);
            $scope.currentMember = null;

            $scope.isSearch = true;
            if ($scope.search.technology.length === 0 &&
                $scope.search.project.length === 0 &&
                $scope.search.engineerLevel.length === 0 &&
                $scope.search.location.length === 0 &&
                ($scope.search.name === null || $scope.search.name === '')) {
                $scope.isSearch = false;
            }

            $scope.getResourceColumns(true);
        }, true);

        //Get dragend AssignmentType
        $scope.dragendAssignmentType = function() {
            var dataForUpdate = $scope.columns.map(function(value, index) {
                return {
                    columnId: value.id,
                    columnPriority: index
                };
            });
            console.log(dataForUpdate);

            //request for save new data
            ResourceColumnFactory.update({id: 'sort'}, {filters: $scope.search, items: dataForUpdate}, function(result){
                $scope.columns = result.columns;
                if ($scope.currentMember !== null) {
                    $scope.selectElement($scope.currentMember);
                }
                Notification.success("Save changes success");
            }, function (error) {
                Notification.error("Server error: save assignment type");
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
        };
        
        //Dragend member
        $scope.dragendElement = function(item, columnFrom, indexMember, indexParent) {
            $scope.selectElement(item);

            //Find column when drag
            // var columnTo = undefined;
            // var result = undefined;
            // var indexColumn = null;
            // var indexElementInColumn = null;
            // var count = $scope.columns.length;
            // for (var index = 0; index < count; index++) {
            //     result = _.findWhere($scope.columns[index].users, {login: item.login});
            //     if (result !== undefined) {
            //         //save column data
            //         columnTo = $scope.columns[index];
            //         indexColumn = index;
            //         //find index new position in column
            //         indexElementInColumn = $scope.columns[index].users.indexOf(result);
            //         break;
            //     }
            // }

            if ($scope.insertedPositions === null ||
                $scope.insertedPositions.columnIndex === null ||
                $scope.insertedPositions.memberIndex === null) {
                return false;
            }
            var columnTo = $scope.columns[$scope.insertedPositions.columnIndex];

            //if not change position
            if (columnFrom.id === columnTo.id && item.resourceOrder === $scope.insertedPositions.memberIndex) {
                console.info("Not change position");
                $scope.insertedPositions = null;
                return true;
            }

            var dataForUpdate = {
                assignmentType: {
                    fromAssignmentTypeId:   columnFrom.id,
                    toAssignmentTypeId:     columnTo.id
                },
                users: columnTo.users.map(function(value, index) {
                    return {
                        login: value.login,
                        index: index
                    };
                }),
                projectId: null
            };

            //TODO check member projects
            if (item.projects.length > 1) {
                var groupByAssignmentType =_.groupBy(item.projects, function(item){
                    return item.assignmentType.id == columnFrom.id ? 'current' : 'other';
                });

                if (groupByAssignmentType.current != undefined && groupByAssignmentType.current.length >= 2 && columnFrom.id !== columnTo.id) {

                    console.log(111);

                    var modalInstance = $uibModal.open({
                        animation: true,
                        templateUrl: 'scripts/controllers/resource_management/dlg/dlg_member_change_column.html',
                        controller: 'DlgMemberChangeColumn',
                        size: 'md',
                        resolve: {
                            dlgData: function () {
                                return {
                                    projects: groupByAssignmentType.current
                                };
                            }
                        }
                    });
                    modalInstance.result.then(function (data) {
                        console.log(data);
                        dataForUpdate.projectId = data.projectId;
                        //TODO save new data
                        if (data.btnType == "ok") {
                            //add request for save new data
                            $scope.moveMember(dataForUpdate, $scope.insertedPositions.columnIndex, $scope.insertedPositions.memberIndex);
                        } else {
                            //delete element which moved
                            console.log($scope.insertedPositions);
                            $scope.columns[$scope.insertedPositions.columnIndex].users.splice($scope.insertedPositions.memberIndex, 1);
                            $scope.columns[indexParent].users.splice(indexMember, 0, item);
                        }

                        $scope.insertedPositions = null;
                    }, function (error) {
                        //delete element which moved
                        $scope.columns[$scope.insertedPositions.memberIndex].users.splice($scope.insertedPositions.memberIndex, 1);
                        $scope.columns[indexParent].users.splice(indexMember, 0, item);
                    });
                } else {
                    //add request for save new data
                    $scope.moveMember(dataForUpdate, $scope.insertedPositions.columnIndex, $scope.insertedPositions.memberIndex);
                    $scope.insertedPositions = null;
                }
            } else {
                //add request for save new data
                $scope.moveMember(dataForUpdate, $scope.insertedPositions.columnIndex, $scope.insertedPositions.memberIndex);
                $scope.insertedPositions = null;
            }
        };

        //Select member
        $scope.selectElement = function (item) {
            console.log(item);

            if ($scope.columns.selected !== null && $scope.columns.selected !== undefined && $scope.columns.selected.login === item.login) {
                $scope.columns.selected = null;

                $scope.showSearch = true;
                $scope.showMemberInfo = false;

                $scope.showSearchFilters();
            } else {
                $scope.columns.selected = item;

                $scope.showSearch = false;
                $scope.showMemberInfo = true;

                $scope.showMemberInfoData(item);
            }
        };

        //Show member info in right part
        $scope.showMemberInfoData = function(item) {
            // console.log('showMemberInfoData');
            $scope.currentMember = item;
        };

        //Show search in right part
        $scope.showSearchFilters = function() {
            // console.log('showSearchFilters');
        };

        //Clear some search filter
        $scope.clearSearch = function() {
            $scope.search = {
                technology: [],
                project: [],
                engineerLevel: [],
                location: [],
                name: null
            };

            $window.localStorage.rm_search = JSON.stringify($scope.search);
            $scope.currentMember = null;
        };

        //Update member description
        $scope.updateMemberDescription = function(data) {
            $scope.currentMember.description = data;

            var memberForUpdate = {
                engineerLevel:          $scope.currentMember.engineerLevel,
                description:            data,
                locationId:             $scope.currentMember.location.id,
                assignmentType: {
                    fromAssignmentTypeId:   $scope.currentMember.column.id,
                    toAssignmentTypeId:     $scope.currentMember.column.id
                }
            };

            MemberFactory.update({login: $scope.currentMember.login}, memberForUpdate, function(data){
                Notification.success("Update description success");
                //get member info
                $scope.currentMember = data;
                $scope.getResourceColumns();
            }, function (error) {
                Notification.error("Server error");
            });
        };

        //Update member location or assignmentType
        $scope.chnageMemberInfoData = function (fromAssignmentTypeId, type) {
            var memberForUpdate = {
                engineerLevel:          $scope.currentMember.engineerLevel,
                description:            $scope.currentMember.description,
                locationId:             $scope.currentMember.location.id,
                assignmentType: {
                    fromAssignmentTypeId:   fromAssignmentTypeId,
                    toAssignmentTypeId:     $scope.currentMember.column.id
                }
            };

            MemberFactory.update({login: $scope.currentMember.login}, memberForUpdate, function(data){
                Notification.success("Update member info success");
                //get member info
                $scope.currentMember = data;
                $scope.getResourceColumns();
            }, function (error) {
                Notification.error("Server error: update member info");
            });
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
            $scope.selectElement(item);

            var menu = [];
            menu.push(customItem);
            for (var index = 0; index < $scope.assignmentTypes.length; index++) {
                //check column by id
                if (column.id != $scope.assignmentTypes[index].id) {
                    menu.push(
                        {
                            html: '<a tabindex="-1" href="#" class="context-menu-item" data-id-assignment-type-index="'+index+'">'+$scope.assignmentTypes[index].name+'</a>',
                            enabled: function() {return true},
                            click: function ($itemScope, $event, value, html) {

                                var columnFromIndex = $itemScope.$parent.$index,
                                    columnToIndex   = $(html).data("idAssignmentTypeIndex");

                                //change assignment type
                                $scope.columns[columnFromIndex].users.splice($itemScope.$index, 1);
                                $scope.columns[columnToIndex].users.push($itemScope.item);

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

                                //add request for save new data
                                $scope.moveMember(dataForUpdate, columnToIndex, $scope.columns[columnToIndex].users.length - 1);
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
                        $scope.moveMember(dataForUpdate, $itemScope.$parent.$index, 0);
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
                        $scope.moveMember(dataForUpdate, $itemScope.$parent.$index, $scope.columns[$itemScope.$parent.$index].users.length - 1);
                    }
                }
            );

            return menu;
        };

        //Save data after member move
        $scope.moveMember = function (dataForUpdate, indexColumn, indexElementInColumn) {
            console.log($scope.insertedPositions);
            dataForUpdate["filters"] = $scope.search;
            MemberFactory.update({login: $scope.currentMember.login, relation: "move"}, dataForUpdate, function(data){
                //update member info from backend
                // $scope.columns = data.columns;
                // if ($scope.currentMember !== null) {
                //     $scope.selectElement($scope.currentMember);
                // }

                //update member info without getResourceColumns (commented for fix double members)
                // $scope.columns[indexColumn].users[indexElementInColumn] = data;
                $scope.columns[indexColumn].users[indexElementInColumn].column = data.column;
                //reindexing resourceOrder
                $scope.columns[indexColumn].users.map(function(value, index) {
                    value.resourceOrder = index;

                    return value;
                });
                $scope.columns.selected = null;
                $scope.selectElement(data);
                //TODO ned for show member counts after move
                $scope.getResourceColumns();
                Notification.success("Save changes success");
            }, function (error) {
                Notification.error("Server error: get assignment type");
            });
        };

        //Dlg process column
        $scope.processColumn = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/resource_management/dlg/dlg_process_column.html',
                controller: 'DlgProcessColumnCtrl',
                size: 'md',
                resolve: {
                    dlgData: function () {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                if (data.id == undefined) {
                    ResourceColumnFactory.create({}, data, function(data){
                        Notification.success("Create column success");
                        $scope.getResourceColumns();
                        $scope.getAssignmentTypes();
                    }, function (error) {
                        Notification.error("Server error");
                    });
                } else {
                    ResourceColumnFactory.update({id: data.id}, data, function(data){
                        Notification.success("Update column success");
                        $scope.getResourceColumns();
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }
            }, function () {});
        };

        //Dlg delete column
        $scope.delColumn = function (item) {
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
                ResourceColumnFactory.delete({id: data.id}, function() {
                    $scope.getResourceColumns();
                    $scope.getAssignmentTypes();
                    Notification.success("Delete column success");
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg add member
        $scope.addMember = function () {

            UsersFactory.query({}, function(users){
                var members = users.users;

                //delete duplicate members
                var count = $scope.columns.length;
                for (var index = 0; index < count; index++) {
                    var countUser = $scope.columns[index].users.length;
                    var indexForDelete = null;
                    for (var indexUser = 0; indexUser < countUser; indexUser++) {
                        indexForDelete = members.indexOf(_.findWhere(members, {login: $scope.columns[index].users[indexUser].login}));
                        if (indexForDelete !== null) {
                            members.splice(indexForDelete, 1);
                            continue;
                        }
                    }
                }

                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'scripts/controllers/resource_management/dlg/dlg_add_member.html',
                    controller: 'DlgAddMemberCtrl',
                    resolve: {
                        dlgData: function () {
                            return {
                                members:            members,
                                locations:          $scope.locations,
                                technologies:       $scope.technologies,
                                positions:          $scope.positions,
                                assignmentTypes:    $scope.assignmentTypes
                            };
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    MemberFactory.create({}, data, function(data){
                        Notification.success("Add member success");
                        $scope.getResourceColumns();
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }, function () {});
            });
        };

        //Dlg delete member
        $scope.delMember = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/dlg_delete/dlg_delete_element.html',
                controller: 'DlgDeleteCtrl',
                resolve: {
                    dlgData: function () {
                        return $scope.currentMember;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                MemberFactory.delete({login: data.login, relation: "full_delete"}, function() {
                    Notification.success("Delete member success");

                    $scope.columns.selected = null;
                    $scope.showSearch = true;
                    $scope.showMemberInfo = false;
                    $scope.showSearchFilters();

                    $scope.getResourceColumns();
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg add experience
        $scope.addExperience = function () {
            var count = $scope.technologies.length;
            var result = [];
            for (var index = 0; index < count; index++) {
                var flag = _.findWhere($scope.currentMember.technologies, {id: $scope.technologies[index].id});
                if (flag === undefined) {
                    result.push($scope.technologies[index]);
                }
            }

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/resource_management/dlg/dlg_add_experience.html',
                controller: 'DlgAddExperienceCtrl',
                resolve: {
                    dlgData: function () {
                        return {
                            technologies: result
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                MemberFactory.create({login: $scope.currentMember.login, relation: "technologies"}, data, function(data){
                    Notification.success("Add new technology success");
                    //get member info
                    $scope.currentMember = data;
                    $scope.getResourceColumns();
                }, function (error) {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg delete experience
        $scope.delExperience = function (item) {
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
                MemberFactory.delete({login: $scope.currentMember.login, relation: "technologies", idRelation: data.id}, function(data) {
                    Notification.success("Delete technology success");
                    //get member info
                    $scope.currentMember = data;
                    $scope.getResourceColumns();
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg add project
        $scope.addMemberProject = function () {
            var count = $scope.projects.length;
            var result = [];
            for (var index = 0; index < count; index++) {
                var flag = _.findWhere($scope.currentMember.projects, {id: $scope.projects[index].id});
                if (flag === undefined) {
                    result.push($scope.projects[index]);
                }
            }

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/resource_management/dlg/dlg_add_project.html',
                controller: 'DlgAddMemberProjectCtrl',
                resolve: {
                    dlgData: function () {
                        return {
                            projects: result,
                            assignmentTypes: $scope.assignmentTypes,
                            currentMember: $scope.currentMember
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                MemberFactory.create({login: $scope.currentMember.login, relation: "projects"}, data, function(data){
                    Notification.success("Add new project success");
                    //get member info
                    $scope.currentMember = data;
                    $scope.getResourceColumns();
                }, function (error) {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg delete project
        $scope.delMemberProject = function (item) {
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
                MemberFactory.delete({login: $scope.currentMember.login, relation: "projects", idRelation: data.id}, function(data) {
                    Notification.success("Delete project success");
                    //get member info
                    $scope.currentMember = data;
                    $scope.getResourceColumns();
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg add project
        $scope.changeLevel = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/resource_management/dlg/dlg_add_change_level.html',
                controller: 'DlgChangeLevelCtrl',
                resolve: {
                    dlgData: function () {
                        return {
                            currentMember:  $scope.currentMember,
                            positions:      $scope.positions
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                var memberForUpdate = {
                    engineerLevel:          data.engineerLevel,
                    description:            data.description,
                    locationId:             data.location.id,
                    assignmentType: {
                        fromAssignmentTypeId:   data.column.id,
                        toAssignmentTypeId:     data.column.id
                    }
                };
                MemberFactory.update({login: data.login}, memberForUpdate, function(data){
                    Notification.success("Change level success");
                    //get member info
                    $scope.currentMember = data;
                    $scope.getResourceColumns();
                }, function (error) {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg upload files
        $scope.uploadAttach = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/resource_management/dlg/dlg_upload_attach.html',
                controller: 'DlgUploadAttachCtrl',
                size: 'lg',
                resolve: {
                    dlgData: function () {
                        return {
                            currentMember: $scope.currentMember
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                Notification.success("Upload attachments success");
                $scope.currentMember = data;
                $scope.getResourceColumns();
            }, function () {});
        };

        //Dlg delete attach
        $scope.delAttach = function (item) {
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
                MemberFactory.delete({login: $scope.currentMember.login, relation: "attachment", idRelation: data.id}, function(data) {
                    Notification.success("Delete attach success");
                    //get member info
                    $scope.currentMember = data;
                    $scope.getResourceColumns();
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };
    }

})();