'use strict';

jiraPluginApp.controller('SystemUserCtrl',
    ['$scope', '$routeParams', 'SystemUsersFactory', 'Notification',
        function($scope, $routeParams, SystemUsersFactory, Notification) {
            var self = this;
            $scope.loaderShow = true;

//----------------------------------------------------------------------------------------------------------------------
//get data
            this.getSystemUsers = function () {
                $scope.dataUsers = [];
                SystemUsersFactory.query({}, function (data) {
                    $scope.dataUsers = data;
                }, function (error) {
                   Notification.error("Server error");
                });

                $scope.loaderShow = false;
            };

            self.getSystemUsers();


        }


]);
