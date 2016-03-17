'use strict';

jiraPluginApp.controller('HomeCtrl', ['$scope', 'AuthenticationFactory', '$uibModal', 'ReportsFactory', 'ReportFactory', 'CopyReportFactory', 'CONFIG',
    function($scope, AuthenticationFactory, $uibModal, ReportsFactory, ReportFactory, CopyReportFactory, CONFIG) {

        var self = this;
        $scope.loaderShow = true;

        this.getReportsData = function () {
            ReportsFactory.query({}, function(result){
                $scope.dataOngoing = result.reports;
                $scope.loaderShow = false;
            });

            $scope.dataClosed = [];
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
                ReportsFactory.create({}, data, function(){
                    self.getReportsData();
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
                CopyReportFactory.copy(data, {}, function(){
                    self.getReportsData();
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