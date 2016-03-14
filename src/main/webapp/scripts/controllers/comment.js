'use strict';

jiraPluginApp.controller('CommentCtrl', ['$scope', '$routeParams', 'CommentsFactory', 'CommentFactory', '$uibModal', 'AuthenticationFactory',
  function($scope, $routeParams, CommentsFactory, CommentFactory, $uibModal, AuthenticationFactory) {
    var self = this;

    this.getCommentsData = function () {
      CommentsFactory.query({id: $routeParams.reportId}, function(data) {
        $scope.comments = data.comments;
      });

      $scope.comments = [
        {
          txt: "first",
          createDate: new Date(),
          creator: "admin",
          creatorDisplayName: "Admin"
        },
        {
          txt: "second",
          createDate: new Date(),
          creator: "admin",
          creatorDisplayName: "Admin"
        },
      ]
    };
    self.getCommentsData();

    $scope.commentsHide = true;
    $scope.toggleComments = function() {
      $scope.commentsHide = $scope.commentsHide === false ? true: false;
    };

    $scope.modelComments = {
      txt: ''
    };

//----------------------------------------------------------------------------------------------------------------------
//Add comment
    $scope.addComment = function() {
      $scope.modelComments.creator = AuthenticationFactory.user;
      $scope.modelComments.createDate = new Date();
      CommentsFactory.add({id: $routeParams.reportId}, $scope.modelComments, function(){
        self.getCommentsData();
        $scope.modelComments.txt = '';
      });
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
        //TODO delete comment
        console.log(data);
        CommentFactory.delete({id: 1}, function() {
          self.getCommentsData();
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

