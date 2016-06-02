 'use strict';

angular.module('macumbariaApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-macumbariaApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-macumbariaApp-params')});
                }
                return response;
            }
        };
    });
