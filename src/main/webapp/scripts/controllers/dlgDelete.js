'use strict';

jiraPluginApp.controller('DlgDeleteCtrl',
    ['$scope', '$uibModalInstance', 'dlgData',
        function ($scope, $uibModalInstance, dlgData) {
            console.log(dlgData);
            $scope.dlgData = dlgData;

            $scope.ok = function () {
                $uibModalInstance.close($scope.dlgData);
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    ]
);