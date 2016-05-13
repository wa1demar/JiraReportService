(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('SystemAtPriorityCtrl', SystemAtPriorityCtrl);

    SystemAtPriorityCtrl.$inject = ['$scope', '$routeParams', 'ResourceColumnFactory', 'Notification'];

    function SystemAtPriorityCtrl($scope, $routeParams, ResourceColumnFactory, Notification) {
        var self = this;
        $scope.loaderShow = true;

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

        //Dragend item
        $scope.dragendAt = function() {
            $scope.loaderShow = true;
            var dataAfterMove = [];
            var count = $scope.assignmentTypes.length;
            for (var index = 0; index < count; index++) {
                dataAfterMove.push({
                    columnId:       $scope.assignmentTypes[index].id,
                    columnPriority: index + 1
                });
            }

            ResourceColumnFactory.update({id: 'update_priority'}, dataAfterMove, function(result){
                $scope.assignmentTypes = result.columns;
                Notification.success("Update priority success");
                $scope.loaderShow = false;
            }, function (error) {
                Notification.error("Server error: update priority");
                $scope.loaderShow = false;
            });
        };
    }

})();