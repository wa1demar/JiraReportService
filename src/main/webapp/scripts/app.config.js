(function() {
    'use strict';

    angular
        .module('jiraPluginApp')
        .config(config);

    config.$inject = ['$routeProvider', '$httpProvider'];

    function config($routeProvider, $httpProvider) {
        $httpProvider.interceptors.push('tokenInterceptor');

        //FIXME need for auth
        $httpProvider.defaults.withCredentials = true;
        //$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $routeProvider
            .when('/login', {
                templateUrl: 'scripts/auth/login.html',
                controller: 'LoginCtrl',
                access: {
                    requiredLogin: false
                }
            }).when('/dashboards', {
            templateUrl: 'scripts/controllers/report_list/report_list.html',
            controller: 'ReportListCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/', {
            templateUrl: 'scripts/controllers/portfolio/main.html',
            controller: 'PortfolioCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/report/:reportId', {
            templateUrl: 'scripts/controllers/report_element/report_element.html',
            controller: 'ReportElementCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/report/:reportId/configure', {
            templateUrl: 'scripts/controllers/report_configure/configure.html',
            controller: 'ConfigureCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/profile', {
            templateUrl: 'scripts/controllers/profile/profile.html',
            controller: 'ProfileCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/system_user', {
            templateUrl: 'scripts/controllers/system_user/main.html',
            controller: 'SystemUserCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/system_at_priority', {
            templateUrl: 'scripts/controllers/system_at_priority/main.html',
            controller: 'SystemAtPriorityCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/task', {
            templateUrl: 'scripts/controllers/task/main.html',
            controller: 'TaskCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/due_date_issue', {
            templateUrl: 'scripts/controllers/due_date_issue/main.html',
            controller: 'DueDateIssueCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/productivity', {
            templateUrl: 'scripts/controllers/productivity/main.html',
            controller: 'ProductivityCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/resource_management', {
            templateUrl: 'scripts/controllers/resource_management/main.html',
            controller: 'ResourceManagementCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/project_management', {
            templateUrl: 'scripts/controllers/project_management/main.html',
            controller: 'ProjectManagementCtrl',
            access: {
                requiredLogin: true
            }
        }).when('/dictionary/:name', {
            templateUrl: 'scripts/controllers/dictionary/main.html',
            controller: 'DictionaryCtrl',
            access: {
                requiredLogin: true
            }
        }).otherwise({
            redirectTo: '/login'
        });
    }

})();
