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

    //$scope.report = ReportFactory.get({id: $routeParams.reportId});
    $scope.report = {
      title: "title",
      typeId: 2,
      boardName: "Test",
      isClosed: true,
      dateClose: new Date()
    };

    var users = UsersFactory.query(function(){
      $scope.users = users.users;
    });

    $scope.saveConfigureGeneralData = function () {
      if($scope.generalConfigure.$valid) {
        $scope.report.dateClose = $scope.report.isClosed ? $scope.report.dateClose : null;
        ReportFactory.update({id: $routeParams.reportId}, $scope.report);
      }
    };

    //==================================================================================================================
    //Calender
    $scope.dateOptions = {
      dateDisabled: disabled,
      formatYear: 'yy',
      maxDate: new Date(2020, 5, 22),
      minDate: new Date(),
      startingDay: 1
    };

    // Disable weekend selection
    function disabled(data) {
      var date = data.date,
          mode = data.mode;
      return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }

    $scope.openCalender = function() {
      $scope.popupCalender.opened = true;
    };

    $scope.setDate = function(year, month, day) {
      $scope.dt = new Date(year, month, day);
    };

    $scope.format = 'MM/dd/yyyy';
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popupCalender = {
      opened: false
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