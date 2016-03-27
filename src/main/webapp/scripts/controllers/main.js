'use strict';

jiraPluginApp.controller('MainCtrl', ['$scope',
    function($scope) {
        $scope.isLoading = false;

        $scope.setLoading = function(loading) {
            $scope.isLoading = loading;
        };

    }
]);
