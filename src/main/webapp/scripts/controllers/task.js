'use strict';

jiraPluginApp.controller('TaskCtrl',
    ['$scope', '$routeParams', '$uibModal', 'TaskFactory', 'Notification',
        function($scope, $routeParams, $uibModal, TaskFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;

//----------------------------------------------------------------------------------------------------------------------
//Order reports
            $scope.predicate = 'name';
            $scope.reverse = true;
            $scope.order = function(predicate) {
                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                $scope.predicate = predicate;
            };

//----------------------------------------------------------------------------------------------------------------------
//get data
            this.getTask = function () {
                $scope.data = [];
                TaskFactory.query({}, function (data) {
                    $scope.data = data.tasks;
                }, function (error) {
                   Notification.error("Server error");
                });

                $scope.loaderShow = false;
            };

            self.getTask();

//----------------------------------------------------------------------------------------------------------------------
//Start task
            $scope.startTask = function (item) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'views/task/dlg/dlg_start_task.html',
                    controller: 'DlgStartTaskCtrl',
                    size: 'sm',
                    resolve: {
                        dlgData: function () {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function (data) {
                    TaskFactory.start({name: data.name, action: "start"}, function(result){
                        self.getTask();
                        Notification.success("Start task success");
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }, function () {});
            };

        }
]);

jiraPluginApp.controller('DlgStartTaskCtrl', ['$scope', '$uibModalInstance', 'dlgData',
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