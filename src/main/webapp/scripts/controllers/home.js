'use strict';

jiraPluginApp.controller('HomeCtrl', ['$scope', '$uibModal', 'ReportsFactory', 'ReportFactory', 'CONFIG', '$http',
  function($scope, $uibModal, ReportsFactory, ReportFactory, CONFIG, $http) {

    var self = this;

    this.getReportsData = function () {
      // console.log(ReportFactory.get({id: 2}));
      $scope.dataOngoing = ReportsFactory.query();
    };

    //
    // console.log(
    //   $http({
    //     url: CONFIG.API_PATH + '/auth/datainfo',
    //     method:"GET",
    //     headers: {
    //       'Content-Type': 'application/x-www-form-urlencoded'
    //     }
    //   })
    // );

    // this.getOngoingData = function () {
    //   $scope.dataOngoing = [
    //     {
    //       id: 1,
    //       name: "Bluebridge",
    //       project: "Manual",
    //       admin: "Rostyslav Trotsyuk"
    //     },
    //     {
    //       id: 2,
    //       name: "xxx",
    //       project: "Manual",
    //       admin: "Rostyslav Trotsyuk"
    //     },
    //     {
    //       id: 3,
    //       name: "111",
    //       project: "Manual",
    //       admin: "Rostyslav Trotsyuk"
    //     }
    //   ];
    // };

    self.getReportsData();

    //------------------------------------------------------------------------------------------------------------------
    //Tabs
    $scope.tabs = [{
      title: 'Ongoing',
      url: 'views/report_list/ongoing.html'
    }, {
      title: 'Closed',
      url: 'views/report_list/closed.html'
    }];

    $scope.currentTab = 'views/report_list/ongoing.html';

    $scope.onClickTab = function (tab) {
      $scope.currentTab = tab.url;
    };

    $scope.isActiveTab = function(tabUrl) {
      return tabUrl === $scope.currentTab;
    };

    //------------------------------------------------------------------------------------------------------------------
    //Dlg process report
    $scope.dlgData = {};
    $scope.processReport = function (size) {
      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'views/dlg/dlg_process_report.html',
        controller: 'DlgProcessReportCtrl',
        size: size,
        resolve: {
          dlgData: function () {
            return $scope.dlgData;
          }
        }
      });
      modalInstance.result.then(function (data) {
        //TODO add new report
        ReportsFactory.create(data);
        self.getReportsData();
        console.log(data);
      }, function () {});
    };

    //------------------------------------------------------------------------------------------------------------------
    //Dlg delete report
    $scope.deleteReport = function (item) {
      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'views/dlg/dlg_delete_element.html',
        controller: 'DlgDeleteReportCtrl',
        // size: size,
        resolve: {
          dlgData: function () {
            return item;
          }
        }
      });
      modalInstance.result.then(function (data) {
        //TODO delete report
        console.log(data);
      }, function () {});
    };


    //------------------------------------------------------------------------------------------------------------------
    //Dlg copy report
    $scope.copyReport = function (item) {
      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'views/dlg/dlg_copy_element.html',
        controller: 'DlgCopyReportCtrl',
        resolve: {
          dlgData: function () {
            return item;
          }
        }
      });
      modalInstance.result.then(function (data) {
        //TODO copy report
        console.log(data);
      }, function () {});
    };
  }
]);

jiraPluginApp.controller('DlgProcessReportCtrl',
    ['$scope', '$uibModalInstance', 'dlgData', 'ReportFactory', 'UsersFactory', 'BoardsFactory',
  function ($scope, $uibModalInstance, dlgData, ReportFactory, UsersFactory, BoardsFactory) {
    $scope.dlgData = {};

    var users = UsersFactory.query(function(){
      $scope.dlgData['users'] = users.users;
    });
    var boards = BoardsFactory.query(function(){
      $scope.dlgData['boards'] = boards.boards;
    });

    $scope.model = {
      typeId: "1"
    };

    $scope.ok = function () {
      if($scope.processReportForm.$valid) {
        $uibModalInstance.close($scope.model);
      }
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };
  }
]);

jiraPluginApp.controller('DlgDeleteReportCtrl', ['$scope', '$uibModalInstance', 'dlgData',
  function ($scope, $uibModalInstance, dlgData) {
    $scope.dlgData = dlgData;

    $scope.ok = function () {
      $uibModalInstance.close($scope.dlgData);
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };
  }
]);

jiraPluginApp.controller('DlgCopyReportCtrl', ['$scope', '$uibModalInstance', 'dlgData',
  function ($scope, $uibModalInstance, dlgData) {
    $scope.dlgData = dlgData;
    $scope.dlgData = {
      newName: "Copy of " + $scope.dlgData.name
    };

    $scope.ok = function () {
      $uibModalInstance.close($scope.dlgData);
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };
  }
]);