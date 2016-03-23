'use strict';

jiraPluginApp.controller('HomeCtrl', ['$scope', '$location', 'AuthenticationFactory', '$uibModal', 'ReportsFactory', 'ReportFactory', 'CopyReportFactory', 'Notification',
    function($scope, $location, AuthenticationFactory, $uibModal, ReportsFactory, ReportFactory, CopyReportFactory, Notification) {

        var self = this;
        $scope.loaderShow = true;

        this.getReportsData = function () {
            ReportsFactory.query({type: "ongoing"}, function(result){
                $scope.dataOngoing = result.reports;
                $scope.loaderShow = false;
            });

            ReportsFactory.query({type: "closed"}, function(result){
                $scope.dataClosed = result.reports;
            });
        };

        self.getReportsData();

//----------------------------------------------------------------------------------------------------------------------
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

//----------------------------------------------------------------------------------------------------------------------
//Order reports
        $scope.predicate = 'closedDate';
        $scope.reverse = true;
        $scope.order = function(predicate) {
            $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
            $scope.predicate = predicate;
        };

//----------------------------------------------------------------------------------------------------------------------
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
                data['creator'] = AuthenticationFactory.user;
                ReportsFactory.create({}, data, function(data){
                    $location.url("/report/" + data.id + "/configure");
                }, function (error) {
                    Notification.error("Server error");
                });
            }, function () {});
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete report
        $scope.deleteReport = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/dlg/dlg_delete_element.html',
                controller: 'DlgDeleteReportCtrl',
                resolve: {
                    dlgData: function () {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                ReportFactory.delete({id: data.id}, function() {
                    self.getReportsData();
                    Notification.success("Delete report success");
                }, function () {
                    Notification.success("Server error");
                });
            }, function () {});
        };


//----------------------------------------------------------------------------------------------------------------------
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
                CopyReportFactory.copy({reportId: data.id}, function(){
                    self.getReportsData();
                    Notification.success("Copy report success");
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };
    }
]);

jiraPluginApp.controller('DlgProcessReportCtrl',
    ['$scope', '$uibModalInstance', 'dlgData', 'UsersFactory', 'BoardsFactory',
        function ($scope, $uibModalInstance, dlgData, UsersFactory, BoardsFactory) {
            $scope.dlgData = {};

            var users = UsersFactory.query(function(){
                $scope.dlgData['users'] = users.users;
            });
            var boards = BoardsFactory.query(function(){
                $scope.dlgData['boards'] = boards.boards;
            });

            $scope.model = {
                typeId: 1
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
            id:   $scope.dlgData.id,
            name: "Copy of " + $scope.dlgData.title
        };

        $scope.ok = function () {
            $uibModalInstance.close($scope.dlgData);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
]);