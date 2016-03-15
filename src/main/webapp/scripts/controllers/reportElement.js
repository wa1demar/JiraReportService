'use strict';

jiraPluginApp.controller('ReportElementCtrl', ['$scope', '$routeParams', 'ReportsFactory', 'ReportFactory',
  function($scope, $routeParams, ReportsFactory, ReportFactory) {
    var self = this;

    this.getReportsData = function () {
      var dataOngoing = ReportsFactory.query({}, function(){
        $scope.reports = dataOngoing.reports;
        $scope.loaderShow = false;
      });

      //$scope.reports = [
      //  {
      //    "id": 1,
      //    "title": "test report",
      //    "creator": "Creator",
      //    "creatorId": 1,
      //    "boardId": 1,
      //    "boardName": null,
      //    "createdDate": 1457620718676,
      //    "updatedDate": 1457620720113,
      //    "closedDate": null,
      //    "typeId": null,
      //    "closed": false,
      //    "admins": [
      //      {
      //        "id": 1,
      //        "login": "Demo Admin",
      //        "fullName": "Demo Admin"
      //      },
      //      {
      //        "id": 2,
      //        "login": "admin",
      //        "fullName": "Anower Admin"
      //      }
      //    ]
      //  },
      //  {
      //    "id": 2,
      //    "title": "test report 2",
      //    "creator": "Creator",
      //    "creatorId": 1,
      //    "boardId": 1,
      //    "boardName": null,
      //    "createdDate": 1457620718676,
      //    "updatedDate": 1457620720113,
      //    "closedDate": null,
      //    "typeId": null,
      //    "closed": false,
      //    "admins": [
      //      {
      //        "id": 1,
      //        "login": "Demo Admin",
      //        "fullName": "Demo Admin"
      //      },
      //      {
      //        "id": 2,
      //        "login": "admin",
      //        "fullName": "Anower Admin"
      //      }
      //    ]
      //  }
      //];
      console.log("getReportsData");

    };

    self.getReportsData();

    this.getReportData = function () {
      ReportFactory.get({id: $routeParams.reportId}, function (data) {
        $scope.report = data;
      });

      //$scope.report = {
      //  "id": 1,
      //  "title": "Test",
      //  "creator": "admin",
      //  "creatorId": null,
      //  "boardId": 2,
      //  "boardName": null,
      //  "createdDate": null,
      //  "updatedDate": null,
      //  "closedDate": null,
      //  "typeId": null,
      //  "closed": false,
      //  "admins": [
      //    {
      //      "fullName": "Vasyl Balazh",
      //      "login": "vbalazh",
      //      "jiraUserId": null
      //    },
      //    {
      //      "fullName": "Maksim Koshelya",
      //      "login": "mkoshelya",
      //      "jiraUserId": null
      //    }
      //  ]
      //};

      console.log("getReportData");
    };

//----------------------------------------------------------------------------------------------------------------------
//get sprints data
    this.getSprints = function () {

      var startDate = new Date();
      var endDate = new Date();
      endDate.setDate(endDate.getDate()+5);
      //TODO get sprints
      //$scope.sprints = SprintsFactory.query({reportId: $routeParams.reportId});
      $scope.sprints = [
        {
          id: 1,
          name: 'Sprint 1',
          type: 1,
          notCountTarget: true,
          showUat: true,
          startDate: startDate,
          endDate: endDate,
          state: "active"
        },
        {
          id: 2,
          name: 'Sprint 2',
          type: 0,
          notCountTarget: true,
          showUat: false,
          startDate: startDate,
          endDate: endDate,
          state: "active"
        },
        {
          id: 3,
          name: 'Sprint 3',
          type: 2,
          notCountTarget: false,
          showUat: false,
          startDate: startDate,
          endDate: endDate,
          state: "active"
        }
      ];
    };

//----------------------------------------------------------------------------------------------------------------------
//get sprint teams data
    this.getSprintTeams = function (data) {

      if (data === undefined) {
        data = {id: $scope.sprints[0]};
      }

      //TODO add get sprint teams by reportId and agileSprintId
      //SprintTeamFactory.get({
      //    reportId: $routeParams.reportId,
      //    sprintId: data.id
      //}, function (data) {
      //    console.log(data);
      //    //$scope.sprintTeams = data;
      //}, function (error) {
      //    console.log(error);
      //    $scope.sprintTeams = [];
      //});


      if (data.id === 57283) {
        $scope.sprintTeams = [
          {
            id: 1,
            devName: "bridoux",
            engineerLvl: 3,
            participationLvl: "0.5",
            daysInSprint: 5
          },
          {
            id: 2,
            devName: "bmurga",
            engineerLvl: 1,
            participationLvl: "0.6",
            daysInSprint: 7
          }
        ];
      } else {
        $scope.sprintTeams = [];
      }
    };

    self.getReportData();

    $scope.getReportAllData = function (item) {
      console.log(item);
    };

  }
]);
