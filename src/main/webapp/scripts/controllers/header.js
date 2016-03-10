'use strict';

jiraPluginApp.controller("HeaderCtrl", ['$scope', '$location', 'UserAuthFactory','$uibModal', 'ConfigFactory',
  function($scope, $location, UserAuthFactory, $uibModal, ConfigFactory) {

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
        //TODO save config
        ConfigFactory.update(data);
        console.log(data);
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
