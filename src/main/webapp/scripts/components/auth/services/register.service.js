'use strict';

angular.module('macumbariaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


