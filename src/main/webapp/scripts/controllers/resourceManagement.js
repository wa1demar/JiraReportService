'use strict';

jiraPluginApp.controller('ResourceManagementCtrl',
    ['$scope', '$uibModal', 'ResourceColumn', 'Notification',
        function($scope, $uibModal, ResourceColumn, Notification) {
            var self = this;
            $scope.loaderShow = true;
            $scope.showSearch = true;
            $scope.showMemberInfo = false;

//----------------------------------------------------------------------------------------------------------------------
//Get all data for Resource Board
            $scope.columns = [];
            $scope.getResourceColumns = function () {
                // ResourceColumn.query({}, function (result) {
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
                                    {id: 1, name: 'JS'},
                                    {id: 2, name: 'HTML'},
                                    {id: 3, name: 'Angular'},
                                    {id: 4, name: 'CSS'},
                                    {id: 5, name: 'PHP'}
                                ],
                                projects: [],
                                location: 2,
                                assignmentType: 1,
                                attach: [],
                                description: "description"
                            },
                            {
                                id: 1,
                                name: "Full Name 2",
                                engineerLevel: 3,
                                experiencies: [
                                    {id: 1, name: 'JS'},
                                    {id: 2, name: 'HTML'},
                                    {id: 3, name: 'Angular'}
                                ],
                                projects: [],
                                location: 2,
                                assignmentType: 1,
                                attach: [],
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
                                id: 1,
                                name: "Full Name 2",
                                engineerLevel: 3,
                                experiencies: [
                                    {
                                        id: 1,
                                        name: 'Scrum'
                                    }
                                ],
                                projects: [],
                                location: 2,
                                assignmentType: 1,
                                attach: [],
                                description: "description"
                            }
                        ]
                    }
                ];

                console.log($scope.columns);

            };
            $scope.getResourceColumns();


            $scope.currentMember = {};

            //test data--------------------------
            $scope.technologies = [
                {id: 1, name: "technology 1"},
                {id: 2, name: "technology 2"},
                {id: 3, name: "technology 3"},
                {id: 4, name: "technology 4"},
                {id: 5, name: "technology 5"}
            ];

            $scope.projects = [
                {id: 1, name: "project 1"},
                {id: 2, name: "project 2"},
                {id: 3, name: "project 3"},
                {id: 4, name: "project 4"},
                {id: 5, name: "project 5"}
            ];

            $scope.locations = [
                {id: 1, name: "locations 1"},
                {id: 2, name: "locations 2"},
                {id: 3, name: "locations 3"},
                {id: 4, name: "locations 4"},
                {id: 5, name: "locations 5"}
            ];
            //-----------------------------------

            // Model to JSON for demo purpose
            $scope.$watch('models', function(model) {
                $scope.modelAsJson = angular.toJson(model, true);
            }, true);

            $scope.logEvent = function(message, event) {
                console.log(message, '(triggered by the following', event.type, 'event)');
                console.log(event);
                console.log($scope.columns);
            };

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

            $scope.showMemberInfoData = function(item) {
                console.log('showMemberInfoData');
                $scope.currentMember = item;

                $scope.currentMember.attach = [
                    {id: 1, name: "name"}
                ]
            };

            $scope.showSearchFilters = function() {
                console.log('showSearchFilters');
            };

            $scope.searchFiltersChange = function() {
                console.log('searchFiltersChange');
            };

            $scope.updateMemberDescription = function(data) {
                console.log('updateMemberDescription:');
                console.log(data);
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg process column
            $scope.dlgData = {};
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
                        ResourceColumn.create({}, data, function(data){
                            Notification.success("Create column success");
                            $scope.getResourceColumns();
                        }, function (error) {
                            Notification.error("Server error");
                        });
                    } else {
                        ResourceColumn.update({id: data.id}, data, function(data){
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
                    ResourceColumn.delete({id: data.id}, function() {
                        $scope.getResourceColumns();
                        Notification.success("Delete column success");
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

            console.log($scope.model);

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