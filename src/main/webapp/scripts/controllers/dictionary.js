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
            getResults();

            function getResults() {
                //this is just an example, in reality this stuff should be in a service
                $scope.loaderShow = true;
                DictionaryFactory.query({name: $routeParams.name}, function(result){
                    $scope.dataDictionary = result.items;
                    $scope.loaderShow = false;
                    //$scope.setLoading(false);
                }, function (error) {
                    Notification.error("Server error");
                });


                // $scope.dataDictionary = [];
                // for (var i = 1; i < 10; i++) {
                //     $scope.dataDictionary.push({
                //         id: i,
                //         name: "name " + i
                //     });
                // }
                // $scope.loaderShow = false;
            }

//----------------------------------------------------------------------------------------------------------------------
//Dlg process report
            $scope.dlgData = {};
            $scope.processElement = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/dictionary/dlg/dlg_process_element.html',
                    controller: 'DlgProcessDictionaryCtrl',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    if (data.id === undefined) {
                        DictionaryFactory.create({name: $routeParams.name}, data, function(data){
                            getResults();
                            Notification.success('Add new element success');
                        }, function (error) {
                            Notification.error("Server error");
                        });
                    } else {
                        DictionaryFactory.update({name: $routeParams.name, id: data.id}, data, function(data){
                            getResults();
                            Notification.success('Edit element success');
                        }, function (error) {
                            Notification.error("Server error");
                        });
                    }
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
                    DictionaryFactory.delete({name: $routeParams.name, id: data.id}, function() {
                        getResults();
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
            $scope.model = dlgData;

            console.log($scope.model);

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