(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('TaskCtrl', TaskCtrl);

    TaskCtrl.$inject = ['$scope', '$routeParams', '$uibModal', '$interval', 'TaskFactory', 'Notification'];

    function TaskCtrl($scope, $routeParams, $uibModal, $interval, TaskFactory, Notification) {
        var self = this;
        $scope.loaderShow = true;
        $scope.hasInProgress = true;
        $scope.indexInProgressData = null;
        //Initiate the Timer object.
        $scope.timer = null;

        //Timer start function.
        $scope.startTimer = function () {
            //Set the Timer start message.
            console.log("Timer started");
            $scope.timer = $interval(function () {
                var newData = [];
                TaskFactory.query({}, function (data) {
                    newData = data.tasks;
                    var inProgressData = _.findWhere(newData, {status: "IN_PROGRESS"});
                    $scope.indexInProgressData = newData.indexOf(_.findWhere(newData, {status: "IN_PROGRESS"}));
                    console.log(inProgressData);
                    $scope.data = newData;

                    if (inProgressData === undefined) {
                        $scope.hasInProgress = false;
                        $scope.stopTimer();
                    } else {
                        $scope.hasInProgress = true;
                    }
                }, function (error) {
                    Notification.error("Server error");
                });

            }, 2000);
        };

        //Timer stop function.
        $scope.stopTimer = function () {
            //Set the Timer stop message.
            console.log("Timer stopped.");
            //Cancel the Timer.
            if (angular.isDefined($scope.timer)) {
                $interval.cancel($scope.timer);
            }
        };

//----------------------------------------------------------------------------------------------------------------------
//Order reports
//            $scope.predicate = 'name';
//            $scope.reverse = true;
//            $scope.order = function(predicate) {
//                $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
//                $scope.predicate = predicate;
//            };

        //get data
        this.getTask = function () {
            $scope.data = [];
            TaskFactory.query({}, function (data) {
                $scope.data = data.tasks;
                $scope.indexInProgressData = $scope.data.indexOf(_.findWhere($scope.data, {status: "IN_PROGRESS"}));
                if ($scope.indexInProgressData === -1) {
                    $scope.hasInProgress = false;
                }
            }, function (error) {
                Notification.error("Server error");
            });

            $scope.loaderShow = false;
        };

        self.getTask();
        $scope.startTimer();

        //Start task
        $scope.startTask = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/task/dlg/dlg_start_task.html',
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

                }, function (error) {
                    //Notification.error("Server error");
                });
                Notification.success("Start task success");
                item.status = "IN_PROGRESS";
                $scope.startTimer();

                //self.getTask();
            }, function () {});
        };

        $scope.$on("$destroy", function () {
            $scope.stopTimer();
        });
    }

})();