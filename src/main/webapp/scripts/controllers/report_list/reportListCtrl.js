(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('ReportListCtrl', ReportListCtrl);

    ReportListCtrl.$inject = ['$scope', '$location', '$uibModal', 'authenticationFactory', 'ReportsFactory', 'ReportFactory', 'CopyReportFactory', 'Notification'];

    function ReportListCtrl($scope, $location, $uibModal, AuthenticationFactory, ReportsFactory, ReportFactory, CopyReportFactory, Notification) {
        var self = this;
        $scope.loaderShow = true;
        $scope.currentTabTitle = 'ongoing';
        //$scope.setLoading(true);

        //TODO For pagination
        $scope.dataOngoing = [];
        $scope.totalOngoing = 0;
        $scope.ongoingPerPage = 10; // this should match however many results your API puts on one page
        getResultsPage(1);

        $scope.pagination = {
            current: 1
        };

        $scope.pageChanged = function(newPage) {
            getResultsPage(newPage);
        };

        function getResultsPage(pageNumber) {
            // this is just an example, in reality this stuff should be in a service
            $scope.loaderShow = true;
            ReportsFactory.query({type: $scope.currentTabTitle, page: pageNumber}, function(result){
                $scope.dataOngoing = result.reports;
                $scope.totalOngoing = result.totalItems;
                $scope.ongoingPerPage = result.itemsPerPage;
                $scope.loaderShow = false;
                //$scope.setLoading(false);
            });
        }

//----------------------------------------------------------------------------------------------------------------------
//        this.getReportsData = function () {
//            ReportsFactory.query({type: "ongoing"}, function(result){
//                $scope.dataOngoing = result.reports;
//                $scope.loaderShow = false;
//                //$scope.setLoading(false);
//            });
//
//            ReportsFactory.query({type: "closed"}, function(result){
//                $scope.dataClosed = result.reports;
//            });
//        };
//
//        self.getReportsData();

        //Tabs
        $scope.tabs = [{
            title: 'Ongoing',
            url: ' scripts/controllers/report_list/ongoing.html'
        }, {
            title: 'Closed',
            url: 'scripts/controllers/report_list/closed.html'
        }];

        $scope.currentTab = 'scripts/controllers/report_list/ongoing.html';

        $scope.onClickTab = function (tab) {
            $scope.currentTab = tab.url;
            $scope.currentTabTitle = tab.title.toLowerCase();
            getResultsPage(1);
        };

        $scope.isActiveTab = function(tabUrl) {
            return tabUrl === $scope.currentTab;
        };

        //Order reports
        $scope.predicate = 'title';
        $scope.reverse = false;
        $scope.order = function(predicate) {
            $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
            $scope.predicate = predicate;
        };

        //Dlg process report
        $scope.dlgData = {};
        $scope.processReport = function (size) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/report_list/dlg/dlg_process_report.html',
                controller: 'DlgProcessReportCtrl',
                size: size,
                resolve: {
                    dlgData: function () {
                        return $scope.dlgData;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                //add new report
                data['creator'] = AuthenticationFactory.user;
                if (data.typeId === 2) {
                    data['boardId'] = null;
                }
                ReportsFactory.create({}, data, function(data){
                    $location.url("/report/" + data.id + "/configure");
                }, function (error) {
                    Notification.error("Server error");
                });
            }, function () {});
        };

        //Dlg delete report
        $scope.deleteReport = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/dlg_delete/dlg_delete_element.html',
                controller: 'DlgDeleteCtrl',
                resolve: {
                    dlgData: function () {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                ReportFactory.delete({id: data.id}, function() {
                    getResultsPage($scope.pagination.current);
                    //self.getReportsData();
                    Notification.success("Delete report success");
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };


        //Dlg copy report
        $scope.copyReport = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/report_list/dlg/dlg_copy_element.html',
                controller: 'DlgCopyReportCtrl',
                resolve: {
                    dlgData: function () {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                CopyReportFactory.copy({reportId: data.id}, function(){
                    getResultsPage($scope.pagination.current);
                    //self.getReportsData();
                    Notification.success("Copy report success");
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };
    }

})();