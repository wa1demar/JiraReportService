'use strict';

jiraPluginApp.controller('CommentCtrl', ['$scope', '$routeParams', 'CommentsFactory', 'CommentFactory', '$uibModal', 'AuthenticationFactory', 'Notification',
    function($scope, $routeParams, CommentsFactory, CommentFactory, $uibModal, AuthenticationFactory, Notification) {
        var self = this;

        this.getCommentsData = function () {
            CommentsFactory.query({id: $routeParams.reportId}, function(data) {
                $scope.comments = data;
            });
        };
        self.getCommentsData();

        $scope.commentsHide = true;
        $scope.toggleComments = function() {
            $scope.commentsHide = $scope.commentsHide === false ? true: false;
        };

        $scope.modelComments = {
            text: ''
        };

//----------------------------------------------------------------------------------------------------------------------
//Add comment
        $scope.addComment = function() {
            if($scope.processCommentForm.$valid) {
                $scope.modelComments.creator = AuthenticationFactory.user;
                //$scope.modelComments.createDate = new Date();
                CommentsFactory.add({id: $routeParams.reportId}, $scope.modelComments, function(){
                    self.getCommentsData();
                    $scope.modelComments.text = '';
                    Notification.success("Add comment success");
                }, function () {
                    Notification.error("Server error");
                });
            } else {
                Notification.error("Comment text is required");
            }
        };

//----------------------------------------------------------------------------------------------------------------------
//Dlg delete comment
        $scope.deleteComment = function (item) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/dlg/dlg_delete_element.html',
                controller: 'DlgDeleteCommentCtrl',
                resolve: {
                    dlgData: function () {
                        return item;
                    }
                }
            });
            modalInstance.result.then(function (data) {
                //delete comment
                console.log(data);
                CommentFactory.delete({id: data.id}, function() {
                    self.getCommentsData();
                    Notification.success("Delete comment success");
                }, function () {
                    Notification.error("Server error");
                });
            }, function () {});
        };

    }
]);

jiraPluginApp.controller('DlgDeleteCommentCtrl', ['$scope', '$uibModalInstance', 'dlgData',
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

