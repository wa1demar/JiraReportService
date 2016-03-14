'use strict';

jiraPluginApp.controller('ReportElementCtrl', ['$scope', '$routeParams', 'ReportsFactory', 'ReportFactory',
  function($scope, $routeParams, ReportsFactory, ReportFactory) {
    var self = this;

    this.getReportsData = function () {
      //ReportsFactory.query(function (data) {
      //  $scope.reports = data.reports;
      //});

      $scope.reports = [
          {
            "id": 1,
            "title": "test report",
            "creator": "Creator",
            "creatorId": 1,
            "boardId": 1,
            "boardName": null,
            "createdDate": 1457620718676,
            "updatedDate": 1457620720113,
            "closedDate": null,
            "typeId": null,
            "closed": false,
            "admins": [
              {
                "id": 1,
                "login": "Demo Admin",
                "fullName": "Demo Admin"
              },
              {
                "id": 2,
                "login": "admin",
                "fullName": "Anower Admin"
              }
            ]
          },
          {
            "id": 2,
            "title": "test report 2",
            "creator": "Creator",
            "creatorId": 1,
            "boardId": 1,
            "boardName": null,
            "createdDate": 1457620718676,
            "updatedDate": 1457620720113,
            "closedDate": null,
            "typeId": null,
            "closed": false,
            "admins": [
              {
                "id": 1,
                "login": "Demo Admin",
                "fullName": "Demo Admin"
              },
              {
                "id": 2,
                "login": "admin",
                "fullName": "Anower Admin"
              }
            ]
          }
        ];
      console.log("getReportsData");
    };

    this.getReportData = function () {
      //ReportFactory.get({id: $routeParams.reportId}, function (data) {
      //  $scope.report = data;
      //});

      $scope.report = {
        "id": 1,
        "title": "Test",
        "creator": "admin",
        "creatorId": null,
        "boardId": 2,
        "boardName": null,
        "createdDate": null,
        "updatedDate": null,
        "closedDate": null,
        "typeId": null,
        "closed": false,
        "admins": [
          {
            "fullName": "Vasyl Balazh",
            "login": "vbalazh",
            "jiraUserId": null
          },
          {
            "fullName": "Maksim Koshelya",
            "login": "mkoshelya",
            "jiraUserId": null
          }
        ]
      };

      console.log("getReportData");
    };

    self.getReportsData();
    self.getReportData();

    $scope.getReportAllData = function (item) {
      console.log(item);
    };

  }
]);
