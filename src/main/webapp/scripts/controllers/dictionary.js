'use strict';

jiraPluginApp.controller('DictionaryCtrl',
    ['$scope', '$routeParams', '$uibModal', 'DictionaryFactory', 'Notification',
        function($scope, $routeParams, $uibModal, DictionaryFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;

            $scope.title = 'Dictionary "' + $routeParams.name + '"';

            // switch ($routeParams.name) {
            //     case 'location':
            //         break;
            //     case 'technology':
            //         break;
            //     default:
            //         break;
            // };


//----------------------------------------------------------------------------------------------------------------------
//Order reports
            $scope.predicate = 'id';
            $scope.reverse = false;
            $scope.order = function (predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            };

//----------------------------------------------------------------------------------------------------------------------
//get data

//----------------------------------------------------------------------------------------------------------------------
//For pagination
            $scope.dataDictionary = [];
            $scope.totalDictionary = 0;
            $scope.dictionaryPerPage = 10; // this should match however many results your API puts on one page
            getResultsPage(1);

            $scope.pagination = {
                current: 1
            };

            $scope.pageChanged = function(newPage) {
                getResultsPage(newPage);
            };

            function getResultsPage(pageNumber) {
                //this is just an example, in reality this stuff should be in a service
                // $scope.loaderShow = true;
                // DueDateIssueFactory.query({name: $routeParams.name, page: pageNumber}, function(result){
                //     $scope.dataDictionary = result.data;
                //     $scope.totalDictionary = result.totalItems;
                //     $scope.dictionaryPerPage = result.itemsPerPage;
                //     $scope.loaderShow = false;
                //     //$scope.setLoading(false);
                // }, function (error) {
                //     Notification.error("Server error");
                // });


                $scope.dataDictionary = [];
                for (var i = 1; i < 10; i++) {
                    $scope.dataDictionary.push({
                        id: i,
                        name: "name " + i
                    });
                }
                $scope.loaderShow = false;
            }

//----------------------------------------------------------------------------------------------------------------------
//Dlg process report
            $scope.dlgData = {};
            $scope.processElement = function (data) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dictionary/dlg/dlg_process_element.html',
                    controller: 'DlgProcessDictionaryCtrl',
                    resolve: {
                        dlgData: function () {
                            return $scope.dlgData;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    ReportsFactory.create({}, data, function(data){
                        $location.url("/report/" + data.id + "/configure");
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete report
            $scope.delElement = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dlg/dlg_delete_element.html',
                    controller: 'DlgDeleteDictionaryCtrl',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    DictionaryFactory.delete({name: $routeParams.name, page: data.id}, function() {
                        getResultsPage($scope.pagination.current);
                        //self.getReportsData();
                        Notification.success("Delete report success");
                    }, function () {
                        Notification.error("Server error");
                    });
                }, function () {});
            };
        }
]);

jiraPluginApp.controller('DlgProcessDictionaryCtrl',
    ['$scope', '$uibModalInstance', 'dlgData', 'UsersFactory', 'BoardsFactory',
        function ($scope, $uibModalInstance, dlgData, UsersFactory, BoardsFactory) {
            $scope.dlgData = {};

            var users = UsersFactory.query(function(){
                $scope.dlgData['users'] = users.users;
            });
            var boards = BoardsFactory.query(function(){
                $scope.dlgData['boards'] = boards.boards;
            });

            $scope.model = {
                typeId: 1
            };

            $scope.ok = function () {
                if($scope.dictionaryForm.$valid) {
                    $uibModalInstance.close($scope.model);
                }
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ]);

jiraPluginApp.controller('DlgDeleteDictionaryCtrl', ['$scope', '$uibModalInstance', 'dlgData',
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