(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('ProductivityCtrl', ProductivityCtrl);

    ProductivityCtrl.$inject = ['$scope', '$routeParams', '$uibModal', 'Notification'];

    function ProductivityCtrl($scope, $routeParams, $uibModal, Notification) {
        var self = this;
        $scope.loaderShow = true;
    }

})();