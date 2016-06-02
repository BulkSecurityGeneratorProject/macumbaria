'use strict';

angular.module('macumbariaApp')
	.controller('MacumbaDeleteController', function($scope, $uibModalInstance, entity, Macumba) {

        $scope.macumba = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Macumba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
