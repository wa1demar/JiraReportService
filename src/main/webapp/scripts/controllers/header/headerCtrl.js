(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('HeaderCtrl', HeaderCtrl);

    HeaderCtrl.$inject = ['$scope', '$location', '$uibModal', 'userAuthFactory', 'ConfigFactory', 'Notification'];

    function HeaderCtrl($scope, $location, $uibModal, userAuthFactory, ConfigFactory, Notification) {
        $scope.status = {
            isopen: false
        };
        $scope.toggleDropdown = function($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.status.isopen = !$scope.status.isopen;
        };


        $scope.isActive = function(route) {
            return route === $location.path();
        };

        $scope.logout = function () {
            userAuthFactory.logout();
        };

        //Dlg process config
        $scope.dlgData = {};
        $scope.processConfig = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                size: "lg",
                templateUrl: 'scripts/controllers/header/dlg/dlg_process_config.html',
                controller: 'DlgProcessConfigCtrl',
                resolve: {
                    dlgData: function () {
                        return $scope.dlgData;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                ConfigFactory.update(data, function () {
                    Notification.success("Settings save success");
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };
    }

})();