(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgSprintTeamActivityCtrl', DlgSprintTeamActivityCtrl);

    DlgSprintTeamActivityCtrl.$inject = ['$scope', '$uibModal', '$uibModalInstance', 'dlgData', 'SprintIssuesFactory', 'SprintIssueFactory', 'Notification'];

    function DlgSprintTeamActivityCtrl($scope, $uibModal, $uibModalInstance, dlgData, SprintIssuesFactory, SprintIssueFactory, Notification) {
        var self = this;
        $scope.dlgData = dlgData;

        //get issues by sprintId and assignee
        this.getIssues = function() {
            SprintIssuesFactory.query({
                sprintId: $scope.dlgData.sprint.id,
                assignee: $scope.dlgData.developer.developerLogin
            }, function (data) {
                $scope.data = data;
            });

            //$scope.data = [
            //    {
            //        date: $scope.dlgData.sprint.startDate,
            //        issues: [
            //            {
            //                id: 1,
            //                typeName: "Story",
            //                statusName: "To Do",
            //                point: 3,
            //                hours: 8.0
            //            }
            //        ]
            //    },
            //    {
            //        date: $scope.dlgData.sprint.startDate,
            //        issues: [
            //            {
            //                id: 2,
            //                typeName: "Story",
            //                statusName: "In Progress",
            //                point: 3,
            //                hours: 8.0
            //            }
            //        ]
            //    },
            //    {
            //        date: $scope.dlgData.sprint.endDate,
            //        issues: []
            //    },
            //    {
            //        date: $scope.dlgData.sprint.endDate,
            //        issues: []
            //    }
            //]
        };
        self.getIssues();

        //get issues bys

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        //Dlg add/edit issue
        $scope.processIssue = function (item, issueDate) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/report_configure/dlg/dlg_issue.html',
                controller: 'DlgProcessIssueCtrl',
                size: 'md',
                resolve: {
                    dlgData: function () {
                        return {
                            item:       item,
                            issueDate:  issueDate
                        };
                    }
                }
            });
            modalInstance.result.then(function (data) {
                if (data.type === "edit") {
                    var idIssue = data.id;
                    //delete data.id;
                    //delete data.type;
                    SprintIssueFactory.update({issueId: idIssue}, data, function(result){
                        self.getIssues();
                        Notification.success("Issue edit success");
                    }, function (error) {
                        Notification.error("Server error");
                    });
                } else {
                    delete data.type;
                    SprintIssuesFactory.add({sprintId: $scope.dlgData.sprint.id, assignee: $scope.dlgData.developer.developerLogin}, data, function(result){
                        self.getIssues();
                        Notification.success("Issue add success");
                    }, function (error) {
                        Notification.error("Server error");
                    });
                }
            }, function () {});
        };

        //Dlg delete issue
        $scope.deleteIssue = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'scripts/controllers/dlg_delete/dlg_delete_element.html',
                controller: 'DlgDeleteCtrl',
                size: 'sm',
                resolve: {
                    dlgData: function () {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                SprintIssueFactory.delete({issueId: data.id}, function(result){
                    self.getIssues();
                    Notification.success("Issue delete success");
                }, function (error) {
                    Notification.error("Server error");
                });
            }, function () {});
        };
    }

})();