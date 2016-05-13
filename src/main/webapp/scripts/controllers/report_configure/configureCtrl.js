(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('ConfigureCtrl', ConfigureCtrl);

    ConfigureCtrl.$inject = ['$scope', '$routeParams', 'ReportFactory'];

    function ConfigureCtrl($scope, $routeParams, ReportFactory) {
        //data for link
        $scope.configureReportInfo = {};

        //Tabs
        $scope.tabs = [{
            title: 'Team settings',
            url: 'scripts/controllers/report_configure/configure_sprint_team.html'
        }, {
            title: 'General settings',
            url: 'scripts/controllers/report_configure/configure_general_data.html'
        }];

        $scope.currentTab = 'scripts/controllers/report_configure/configure_sprint_team.html';

        $scope.onClickTab = function (tab) {
            $scope.currentTab = tab.url;
        };

        $scope.isActiveTab = function(tabUrl) {
            return tabUrl === $scope.currentTab;
        };
    }

})();