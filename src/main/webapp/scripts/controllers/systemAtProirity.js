'use strict';

jiraPluginApp.controller('SystemAtPriorityCtrl',
    ['$scope', '$routeParams', 'ResourceColumnFactory', 'Notification',
        function($scope, $routeParams, ResourceColumnFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;

//----------------------------------------------------------------------------------------------------------------------
//Get assignment type
            $scope.assignmentTypes = [];
            $scope.getAssignmentTypes = function () {
                ResourceColumnFactory.query({id: 'list'}, function(result){
                    $scope.assignmentTypes = result.columns;
                    $scope.loaderShow = false;
                }, function (error) {
                    Notification.error("Server error: get assignment type");
                    $scope.loaderShow = false;
                });
            };
            $scope.getAssignmentTypes();

//----------------------------------------------------------------------------------------------------------------------
//Dragend item
            $scope.dragendAt = function() {
                $scope.loaderShow = true;
                var dataAfterMove = [];
                var count = $scope.assignmentTypes.length;
                for (var index = 0; index < count; index++) {
                    dataAfterMove.push({
                        id:         $scope.assignmentTypes[index].id,
                        priority:   index + 1
                    });
                }

                console.log(dataAfterMove);

                ResourceColumnFactory.update({id: 'update_priority'}, dataAfterMove, function(result){
                    Notification.success("Update priority success");
                    $scope.loaderShow = false;
                }, function (error) {
                    Notification.error("Server error: update priority");
                    $scope.loaderShow = false;
                });
            };
        }
]);