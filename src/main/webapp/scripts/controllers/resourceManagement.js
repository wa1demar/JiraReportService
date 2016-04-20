'use strict';

jiraPluginApp.controller('ResourceManagementCtrl',
    ['$scope', '$uibModal', '$filter', '$window', 'ResourceColumnFactory', 'UsersFactory', 'DictionaryFactory', 'MemberFactory', 'Notification',
        function($scope, $uibModal, $filter, $window, ResourceColumnFactory, UsersFactory, DictionaryFactory, MemberFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;
            $scope.showSearch = true;
            $scope.showMemberInfo = false;
            //TODO need get from db
            $scope.engineerLevelDictionary = [1, 2, 3, 4];

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

//----------------------------------------------------------------------------------------------------------------------
//Get all data for Resource Board
            $scope.columns = [];
            $scope.getResourceColumns = function () {
                ResourceColumnFactory.query($scope.search, function (result) {
                    $scope.columns = result.columns;
                    $scope.loaderShow = false;
                }, function (error) {
                    Notification.error("Server error");
                });

                // $scope.columns = [
                //     {
                //         id: 1,
                //         name: "Bench",
                //         color: "#CF2926",
                //         fixed: true,
                //         members: [
                //             {
                //                 id: 1,
                //                 name: "Full Name 1",
                //                 engineerLevel: 1,
                //                 experiencies: [
                //                     {id: 31, name: 'JS'},
                //                     {id: 41, name: 'HTML'},
                //                     {id: 51, name: 'CSS'},
                //                     {id: 61, name: 'Angular'}
                //                 ],
                //                 projects: [
                //                     {id: 1, name: "project 1"}
                //                 ],
                //                 location: 2,
                //                 assignmentType: 1,
                //                 attachments: [
                //                     {id: 1, name: "attach 1", url: "http://google.com"}
                //                 ],
                //                 description: "description"
                //             },
                //             {
                //                 id: 2,
                //                 name: "Full Name 2",
                //                 engineerLevel: 3,
                //                 experiencies: [
                //                     {id: 13, name: 'JS'},
                //                     {id: 14, name: 'HTML'},
                //                     {id: 16, name: 'Angular'}
                //                 ],
                //                 projects: [],
                //                 location: 2,
                //                 assignmentType: 1,
                //                 attachments: [
                //                     {id: 1, name: "attach 1", url: "http://google.com"},
                //                     {id: 2, name: "attach 2", url: "http://google.com"}
                //                 ],
                //                 description: "description 2"
                //             }
                //         ]
                //     },
                //     {
                //         id: 2,
                //         name: "PM",
                //         color: "#1E90FF",
                //         fixed: false,
                //         members: [
                //             {
                //                 id: 3,
                //                 name: "Full Name 2",
                //                 engineerLevel: 3,
                //                 experiencies: [
                //                     {id: 111, name: 'Scrum'}
                //                 ],
                //                 projects: [],
                //                 location: 2,
                //                 assignmentType: 2,
                //                 attachments: [
                //                     {id: 1, name: "attach 1", url: "http://google.com"}
                //                 ],
                //                 description: "description"
                //             }
                //         ]
                //     }
                // ];
                //
                // console.log($scope.columns);

            };
            $scope.getResourceColumns();


            $scope.currentMember = {};

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
//Get projects
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

//----------------------------------------------------------------------------------------------------------------------
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

//----------------------------------------------------------------------------------------------------------------------
//Get assignment type
            $scope.assignmentTypes = [];
            $scope.getAssignmentTypes = function () {
                $scope.assignmentTypes = [
                    {id: 1, name: "Bench"},
                    {id: 2, name: "PM"}
                ];
            };
            $scope.getAssignmentTypes();

            // Model to JSON for demo purpose
            $scope.$watch('models', function(model) {
                $scope.modelAsJson = angular.toJson(model, true);
            }, true);

            $scope.logEvent = function(message, event) {
                console.log(message, '(triggered by the following', event.type, 'event)');
                console.log(event);
                console.log($scope.columns);
            };

            // $scope.changeSearch = function() {
            //     // console.log($scope.search);
            // };

            $scope.$watch('search', function() {
                console.log(" ---- new search");
                $window.localStorage.rm_search = JSON.stringify($scope.search);
                $scope.getResourceColumns();
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
                // $scope.getResourceColumns();
            };

//----------------------------------------------------------------------------------------------------------------------
//Select member
            $scope.selectElement = function (item) {
                console.log(item);
                if ($scope.columns.selected === item) {
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

//----------------------------------------------------------------------------------------------------------------------
//Show member info in right part
            $scope.showMemberInfoData = function(item) {
                console.log('showMemberInfoData');
                $scope.currentMember = item;
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
            $scope.updateMemberDescription = function(data) {
                console.log('updateMemberDescription:');
                console.log(data);
                $scope.currentMember.description = data;
                MemberFactory.update({login: $scope.currentMember.login}, $scope.currentMember, function(data){
                    Notification.success("Update description success");
                    //get member info
                    // $scope.getResourceColumns();
                }, function (error) {
                    Notification.error("Server error");
                });
            };

//----------------------------------------------------------------------------------------------------------------------
//Update member location or assignmentType
            $scope.chnageMemberInfoData = function (type) {
                console.log(type);

                if (type === 'location') {
                    console.log($scope.currentMember.location.id);
                } else {
                    console.log($scope.currentMember.assignmentType.id);
                }
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg process column
            $scope.processColumn = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/resource_management/dlg/dlg_process_column.html',
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

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete column
            $scope.delColumn = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dlg/dlg_delete_element.html',
                    controller: 'DlgDeleteColumnCtrl',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    ResourceColumnFactory.delete({id: data.id}, function() {
                        $scope.getResourceColumns();
                        Notification.success("Delete column success");
                    }, function () {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
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
                        templateUrl: 'views/resource_management/dlg/dlg_add_member.html',
                        controller: 'DlgAddMemberCtrl',
                        resolve: {
                            dlgData: function () {
                                return {
                                    members:                    members,
                                    locations:                  $scope.locations,
                                    technologies:               $scope.technologies,
                                    engineerLevelDictionary:    $scope.engineerLevelDictionary
                                };
                            }
                        }
                    });
                    modalInstance.result.then(function (data) {
                        console.log(data);
                        MemberFactory.create({}, data, function(data){
                            Notification.success("Add member success");
                            $scope.getResourceColumns();
                        }, function (error) {
                            Notification.error("Server error");
                        });
                    }, function () {});
                });
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete member
            $scope.delMember = function () {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dlg/dlg_delete_element.html',
                    controller: 'DlgDeleteMemberCtrl',
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

//----------------------------------------------------------------------------------------------------------------------
//Dlg add experience
            $scope.addExperience = function () {
                var count = $scope.technologies.length;
                var result = [];
                for (var index = 0; index < count; index++) {
                    var flag = _.findWhere($scope.currentMember.experiencies, {name: $scope.technologies[index].name});
                    if (flag === undefined) {
                        result.push($scope.technologies[index]);
                    }
                }

                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/resource_management/dlg/dlg_add_experience.html',
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
                        // $scope.getResourceColumns();
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete experience
            $scope.delExperience = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dlg/dlg_delete_element.html',
                    controller: 'DlgDeleteExperienceCtrl',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    MemberFactory.delete({login: $scope.currentMember.login, relation: "technologies", idRelation: data.id}, function() {
                        Notification.success("Delete technology success");
                        //get member info
                        // $scope.getResourceColumns();
                    }, function () {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg add project
            $scope.addProject = function () {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/resource_management/dlg/dlg_add_project.html',
                    controller: 'DlgAddProjectCtrl',
                    resolve: {
                        dlgData: function () {
                            return {
                                projects: $scope.projects
                            };
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    MemberFactory.create({login: $scope.currentMember.login, relation: "projects"}, data, function(data){
                        Notification.success("Add new project success");
                        //get member info
                        // $scope.getResourceColumns();
                    }, function (error) {
                        Notification.error("Server error");
                    });
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
                    MemberFactory.delete({login: $scope.currentMember.login, relation: "projects", idRelation: data.id}, function() {
                        Notification.success("Delete project success");
                        //get member info
                        // $scope.getResourceColumns();
                    }, function () {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg add project
            $scope.changeLevel = function () {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/resource_management/dlg/dlg_add_change_level.html',
                    controller: 'DlgChangeLevelCtrl',
                    resolve: {
                        dlgData: function () {
                            return {
                                currentMember:              $scope.currentMember,
                                engineerLevelDictionary:    $scope.engineerLevelDictionary
                            };
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    MemberFactory.update({login: data.login}, data, function(data){
                        Notification.success("Change level success");
                        //get member info
                        // $scope.getResourceColumns();
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg upload files
            $scope.uploadAttach = function () {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/resource_management/dlg/dlg_upload_attach.html',
                    controller: 'DlgUploadAttachCtrl',
                    size: 'lg',
                    resolve: {
                        dlgData: function () {
                            return {
                                currentMember:              $scope.currentMember,
                                engineerLevelDictionary:    $scope.engineerLevelDictionary
                            };
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    console.log(data);
                    // MemberFactory.update({login: data.login}, data, function(data){
                    //     Notification.success("Change level success");
                    //     //get member info
                    //     // $scope.getResourceColumns();
                    // }, function (error) {
                    //     Notification.error("Server error");
                    // });
                }, function () {});
            };

        }
]);

jiraPluginApp.controller('DlgProcessColumnCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
        function ($scope, $uibModalInstance, dlgData) {
            $scope.model = dlgData;

            $scope.ok = function () {
                if($scope.processColumnForm.$valid) {
                    $uibModalInstance.close($scope.model);
                }
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ]);

jiraPluginApp.controller('DlgDeleteColumnCtrl',
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

jiraPluginApp.controller('DlgAddMemberCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
        function ($scope, $uibModalInstance, dlgData) {

            $scope.members = dlgData.members;
            $scope.locations = dlgData.locations;
            $scope.technologies = dlgData.technologies;
            $scope.engineerLevelDictionary = dlgData.engineerLevelDictionary;

            $scope.ok = function () {
                if($scope.addMemberForm.$valid) {
                    $uibModalInstance.close($scope.model);
                }
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ]);

jiraPluginApp.controller('DlgDeleteMemberCtrl',
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

jiraPluginApp.controller('DlgAddExperienceCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
        function ($scope, $uibModalInstance, dlgData) {
            $scope.technologies = dlgData.technologies;

            $scope.ok = function () {
                if($scope.addExperienceForm.$valid) {
                    $uibModalInstance.close($scope.model);
                }
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ]);

jiraPluginApp.controller('DlgDeleteExperienceCtrl',
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

jiraPluginApp.controller('DlgAddProjectCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
        function ($scope, $uibModalInstance, dlgData) {
            $scope.projects = dlgData.projects;

            $scope.ok = function () {
                if($scope.addProjectForm.$valid) {
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

jiraPluginApp.controller('DlgChangeLevelCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
        function ($scope, $uibModalInstance, dlgData) {
            $scope.model = dlgData.currentMember;
            $scope.engineerLevelDictionary = dlgData.engineerLevelDictionary;

            $scope.ok = function () {
                $uibModalInstance.close($scope.model);
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ]);

jiraPluginApp.controller('DlgUploadAttachCtrl',
    ['$scope', '$uibModalInstance', 'dlgData', 'FileUploader',
        function ($scope, $uibModalInstance, dlgData, FileUploader) {
            // $scope.model = dlgData.currentMember;
            // $scope.engineerLevelDictionary = dlgData.engineerLevelDictionary;
            
//----------------------------------------------------------------------------------------------------------------------
//test upload
            var uploader = $scope.uploader = new FileUploader({
                url: 'upload'
            });

            // FILTERS
            uploader.filters.push({
                name: 'customFilter',
                fn: function(item /*{File|FileLikeObject}*/, options) {
                    return this.queue.length < 10;
                }
            });
            // CALLBACKS

            uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
                console.info('onWhenAddingFileFailed', item, filter, options);
            };
            uploader.onAfterAddingFile = function(fileItem) {
                console.info('onAfterAddingFile', fileItem);
            };
            uploader.onAfterAddingAll = function(addedFileItems) {
                console.info('onAfterAddingAll', addedFileItems);
            };
            uploader.onBeforeUploadItem = function(item) {
                console.info('onBeforeUploadItem', item);
            };
            uploader.onProgressItem = function(fileItem, progress) {
                console.info('onProgressItem', fileItem, progress);
            };
            uploader.onProgressAll = function(progress) {
                console.info('onProgressAll', progress);
            };
            uploader.onSuccessItem = function(fileItem, response, status, headers) {
                console.info('onSuccessItem', fileItem, response, status, headers);
            };
            uploader.onErrorItem = function(fileItem, response, status, headers) {
                console.info('onErrorItem', fileItem, response, status, headers);
            };
            uploader.onCancelItem = function(fileItem, response, status, headers) {
                console.info('onCancelItem', fileItem, response, status, headers);
            };
            uploader.onCompleteItem = function(fileItem, response, status, headers) {
                console.info('onCompleteItem', fileItem, response, status, headers);
            };
            uploader.onCompleteAll = function() {
                console.info('onCompleteAll');
            };

            console.info('uploader', uploader);

            $scope.ok = function () {
                $uibModalInstance.close($scope.model);
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ]);