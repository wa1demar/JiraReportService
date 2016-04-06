'use strict';

jiraPluginApp.controller('ProductivityCtrl',
    ['$scope', '$routeParams', '$uibModal', 'Notification',
        function($scope, $routeParams, $uibModal, Notification) {
            var self = this;
            $scope.loaderShow = true;
        }
]);