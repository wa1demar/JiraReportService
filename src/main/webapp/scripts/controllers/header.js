'use strict';

jiraPluginApp.controller("HeaderCtrl", ['$scope', '$location', 'UserAuthFactory','$uibModal', 'CONFIG',
  function($scope, $location, UserAuthFactory, $uibModal, CONFIG) {

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
        console.log(data);
      }, function () {});
    };
  }
]);

jiraPluginApp.controller('DlgProcessConfigCtrl', ['$scope', '$uibModalInstance', 'dlgData',
  function ($scope, $uibModalInstance, dlgData) {
    $scope.dlgData = dlgData;

    $scope.ok = function () {
      $uibModalInstance.close($scope.model);
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };
  }
]);
