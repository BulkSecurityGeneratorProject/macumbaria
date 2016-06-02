'use strict';

angular.module('macumbariaApp')
    .controller('MacumbaDetailController', function ($scope, $rootScope, $stateParams, entity, Macumba) {
        $scope.macumba = entity;
        $scope.load = function (id) {
            Macumba.get({id: id}, function(result) {
                $scope.macumba = result;
            });
        };
        var unsubscribe = $rootScope.$on('macumbariaApp:macumbaUpdate', function(event, result) {
            $scope.macumba = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
