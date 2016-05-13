(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('MainCtrl', MainCtrl);

    MainCtrl.$inject = ['$scope'];

    function MainCtrl($scope) {
        $scope.isLoading = false;

        $scope.setLoading = function (loading) {
            $scope.isLoading = loading;
        };
    }

})();
