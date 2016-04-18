'use strict';

jiraPluginApp.controller('ResourceManagementCtrl',
    ['$scope', '$uibModal', '$filter', 'ResourceColumnFactory', 'UsersFactory', 'DictionaryFactory', 'MemberFactory', 'Notification',
        function($scope, $uibModal, $filter, ResourceColumnFactory, UsersFactory, DictionaryFactory, MemberFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;
            $scope.showSearch = true;
            $scope.showMemberInfo = false;

            $scope.search = {
                technology: [],
                project: [],
                location: [],
                name: null
            };

//----------------------------------------------------------------------------------------------------------------------
//Get all data for Resource Board
            $scope.columns = [];
            $scope.getResourceColumns = function () {
                // ResourceColumnFactory.query({}, function (result) {
                //     $scope.columns = result;
                //     $scope.loaderShow = false;
                // }, function (error) {
                //     Notification.error("Server error");
                // });

                $scope.columns = [
                    {
                        id: 1,
                        name: "Bench",
                        color: "#CF2926",
                        fixed: true,
                        members: [
                            {
                                id: 1,
                                name: "Full Name 1",
                                engineerLevel: 1,
                                experiencies: [
                                    {id: 31, name: 'JS'},
                                    {id: 41, name: 'HTML'},
                                    {id: 51, name: 'CSS'},
                                    {id: 61, name: 'Angular'}
                                ],
                                projects: [
                                    {id: 1, name: "project 1"}
                                ],
                                location: 2,
                                assignmentType: 1,
                                attachments: [
                                    {id: 1, name: "attach 1", url: "http://google.com"}
                                ],
                                description: "description"
                            },
                            {
                                id: 2,
                                name: "Full Name 2",
                                engineerLevel: 3,
                                experiencies: [
                                    {id: 13, name: 'JS'},
                                    {id: 14, name: 'HTML'},
                                    {id: 16, name: 'Angular'}
                                ],
                                projects: [],
                                location: 2,
                                assignmentType: 1,
                                attachments: [
                                    {id: 1, name: "attach 1", url: "http://google.com"},
                                    {id: 2, name: "attach 2", url: "http://google.com"}
                                ],
                                description: "description 2"
                            }
                        ]
                    },
                    {
                        id: 2,
                        name: "PM",
                        color: "#1E90FF",
                        fixed: false,
                        members: [
                            {
                                id: 3,
                                name: "Full Name 2",
                                engineerLevel: 3,
                                experiencies: [
                                    {id: 111, name: 'Scrum'}
                                ],
                                projects: [],
                                location: 2,
                                assignmentType: 2,
                                attachments: [
                                    {id: 1, name: "attach 1", url: "http://google.com"}
                                ],
                                description: "description"
                            }
                        ]
                    }
                ];

                console.log($scope.columns);

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
                console.log($scope.search);
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
                    result = _.findWhere($scope.columns[index].members, {id: item.id});
                    if (result !== undefined) {
                        //save column data
                        column = $scope.columns[index];
                        //find index new position in column
                        indexElementInColumn = $scope.columns[index].members.indexOf(result);
                        break;
                    }
                }

                //TODO save new member position
                //change member assignment type
                $scope.columns[index].members[indexElementInColumn].assignmentType = column.id;
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

                    var modalInstance = $uibModal.open({
                        animation: true,
                        templateUrl: 'views/resource_management/dlg/dlg_add_member.html',
                        controller: 'DlgAddMemberCtrl',
                        resolve: {
                            dlgData: function () {
                                return {
                                    members:      members,
                                    technologies: $scope.technologies
                                };
                            }
                        }
                    });
                    modalInstance.result.then(function (data) {
                        console.log(data);
                        // ResourceMember.create({}, data, function(data){
                        //     Notification.success("Create column success");
                        //     $scope.getResourceColumns();
                        // }, function (error) {
                        //     Notification.error("Server error");
                        // });
                    }, function () {});
                });
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
                    MemberFactory.create({id: $scope.currentMember.id, relation: "technologies"}, data, function(data){
                        Notification.success("Add new technology success");
                        $scope.getResourceColumns();
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
                    MemberFactory.delete({relation: "technologies", idRelation: data.id}, function() {
                        $scope.getResourceColumns();
                        Notification.success("Delete technology success");
                    }, function () {
                        Notification.error("Server error");
                    });
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
            // $scope.model = dlgData;

            $scope.members = dlgData.members;
            $scope.technologies = dlgData.technologies;

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