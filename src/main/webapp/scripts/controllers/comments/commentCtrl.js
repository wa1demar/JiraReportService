(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .controller('CommentCtrl', CommentCtrl);

    CommentCtrl.$inject = ['$scope', '$routeParams', '$uibModal', 'CommentsFactory', 'CommentFactory', 'authenticationFactory', 'Notification'];

    function CommentCtrl($scope, $routeParams, $uibModal, CommentsFactory, CommentFactory, authenticationFactory, Notification) {
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
                $scope.modelComments.creator = authenticationFactory.user;
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
                templateUrl: 'scripts/controllers/dlg_delete/dlg_delete_element.html',
                controller: 'DlgDeleteCtrl',
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

})();

