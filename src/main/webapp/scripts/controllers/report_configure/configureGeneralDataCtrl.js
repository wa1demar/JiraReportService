(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('ConfigureGeneralDataCtrl', ConfigureGeneralDataCtrl);

    ConfigureGeneralDataCtrl.$inject = ['$scope', '$routeParams', '$uibModal', 'ReportFactory', 'UsersFactory', 'Notification'];

    function ConfigureGeneralDataCtrl($scope, $routeParams, $uibModal, ReportFactory, UsersFactory, Notification) {
        $scope.loaderShow = true;

        var self = this;

        $scope.getReport = function () {
            ReportFactory.get({id: $routeParams.reportId}, function (result) {
                $scope.report = result;
                var admins = [];
                for (var index = 0; index < result.admins.length; index++) {
                    admins.push(result.admins[index].login);
                }
                $scope.report.admins = admins;
                $scope.loaderShow = false;

                //data for link
                $scope.configureReportInfo.id = result.id;
                $scope.configureReportInfo.title = result.title;
            });
        };

        $scope.getReport();

        //Calender
        $scope.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            //minDate: new Date(),
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

        var users = UsersFactory.query(function(){
            $scope.users = users.users;
        });

        $scope.saveConfigureGeneralData = function () {
            if($scope.generalConfigure.$valid) {
                var reportData = $scope.report;
                reportData.closedDate = reportData.closed ? reportData.closedDate : null;
                reportData.closed = reportData.closed === undefined ? false : reportData.closed;
                reportData.closedDate = reportData.closedDate === undefined ? null : reportData.closedDate;

                ReportFactory.update({
                    id: $routeParams.reportId}, reportData, function(){
                    //FIXME not reinit select2
                    $scope.getReport();
                    Notification.success('Save report success');
                }, function () {
                    Notification.error('Server error');
                });
            }
        };

        $scope.loaderShow = false;
    }

})();