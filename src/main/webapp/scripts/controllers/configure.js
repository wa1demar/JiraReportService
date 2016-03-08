'use strict';

jiraPluginApp.controller('ConfigureCtrl', ['$scope',
  function($scope) {
    var self = this;
    //------------------------------------------------------------------------------------------------------------------
    //Tabs
    $scope.tabs = [{
      title: 'Team settings',
      url: 'views/report_configure/configure_sprint_team.html'
    }, {
      title: 'General settings',
      url: 'views/report_configure/configure_general_data.html'
    }];

    $scope.currentTab = 'views/report_configure/configure_sprint_team.html';

    $scope.onClickTab = function (tab) {
      $scope.currentTab = tab.url;
    };

    $scope.isActiveTab = function(tabUrl) {
      return tabUrl === $scope.currentTab;
    };
  }
]);

//General Settings
jiraPluginApp.controller('ConfigureGeneralDataCtrl', ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'CONFIG',
  function($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, CONFIG) {

    $scope.report = ReportFactory.get({id: $routeParams.reportId});

    var users = UsersFactory.query(function(){
      $scope.users = users.users;
    });

    $scope.saveConfigureGeneralData = function () {
      ReportFactory.update();
    };
  }
]);

//Configure sprint team data
jiraPluginApp.controller('ConfigureSprintTeamCtrl', ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'CONFIG',
  function($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, CONFIG) {
    //$scope.report = ReportFactory.get({id: $routeParams.reportId});

    var users = UsersFactory.query(function(){
      $scope.devUsers = users.users;
    });

    $scope.report = {
      typeId: 2
    };

    $scope.sprint = {
      type: "0"
    };

  }
]);