'use strict';

jiraPluginApp.controller('ReportElementCtrl', ['$scope', '$routeParams', 'ReportsFactory', 'ReportFactory',
  function($scope, $routeParams, ReportsFactory, ReportFactory) {
    var self = this;

    this.getReportsData = function () {
      $scope.reports = ReportsFactory.query();
    };

    this.getReportData = function () {
      var xz = ReportFactory.get({id: $routeParams.reportId});
      console.log(xz);
      $scope.report = xz;
    };

    self.getReportData();
    self.getReportsData();
  }
]);
