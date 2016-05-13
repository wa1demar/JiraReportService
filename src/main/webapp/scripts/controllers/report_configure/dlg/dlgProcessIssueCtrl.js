(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgProcessIssueCtrl', DlgProcessIssueCtrl);

    DlgProcessIssueCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData'];

    function DlgProcessIssueCtrl($scope, $uibModalInstance, dlgData) {
        $scope.issue = dlgData.item;
        if ($scope.issue === undefined) {
            $scope.issue = {
                type: "add",
                typeName: "Story",
                statusName: "To Do",
                hours: 8.0,
                issueDate: dlgData.issueDate
            }
        } else {
            $scope.issue["type"] = "edit";
        }

        $scope.ok = function () {
            $uibModalInstance.close($scope.issue);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();