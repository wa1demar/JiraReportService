'use strict';

jiraPluginApp.controller("HeaderCtrl", ['$scope', '$location', 'UserAuthFactory','$uibModal', 'ConfigFactory', 'Notification',
    function($scope, $location, UserAuthFactory, $uibModal, ConfigFactory, Notification) {
        $scope.menu = [];
        $scope.showToggleMenu = function(item) {
            console.log($scope.menu[item]);
            $scope.menu[item] = !$scope.menu[item];
        };

        $scope.isActive = function(route) {
            return route === $location.path();
        };

        $scope.logout = function () {
            UserAuthFactory.logout();
        };

        //------------------------------------------------------------------------------------------------------------------
        //Dlg process config
        $scope.dlgData = {};
        $scope.processConfig = function () {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/dlg/dlg_process_config.html',
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
]);

jiraPluginApp.controller('DlgProcessConfigCtrl', ['$scope', '$uibModalInstance', 'dlgData', 'ConfigFactory',
    function ($scope, $uibModalInstance, dlgData, ConfigFactory) {
        $scope.dlgData = dlgData;

        var boards = ConfigFactory.get(function(){
            $scope.configData = boards;
        });

        $scope.ok = function () {
            $uibModalInstance.close($scope.configData);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);
