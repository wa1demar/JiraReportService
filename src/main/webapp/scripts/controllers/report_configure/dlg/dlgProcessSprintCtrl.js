(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('DlgProcessSprintCtrl', DlgProcessSprintCtrl);

    DlgProcessSprintCtrl.$inject = ['$scope', '$uibModalInstance', 'dlgData', 'SprintFactory'];

    function DlgProcessSprintCtrl($scope, $uibModalInstance, dlgData, SprintFactory) {
        $scope.sprint = dlgData.item;
        $scope.report = dlgData.report;
        console.log(dlgData.report);
        if (dlgData.type === "add") {
            $scope.sprint = {
                state: "active"
            }
        }

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

        $scope.openCalenderStartDate = function() {
            $scope.popupCalenderStartDate.opened = true;
        };
        $scope.openCalenderEndDate = function() {
            $scope.popupCalenderEndDate.opened = true;
        };

        $scope.setDate = function(year, month, day) {
            $scope.dt = new Date(year, month, day);
        };

        $scope.format = 'MM/dd/yyyy';
        $scope.altInputFormats = ['M!/d!/yyyy'];

        $scope.popupCalenderStartDate = {
            opened: false
        };
        $scope.popupCalenderEndDate = {
            opened: false
        };

        var result = {
            type:   dlgData.type,
            sprint: $scope.sprint
        };

        $scope.ok = function () {
            if($scope.processSprintForm.$valid) {
                $uibModalInstance.close(result);
            }
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

})();