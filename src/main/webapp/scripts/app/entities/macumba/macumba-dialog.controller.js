'use strict';

angular.module('macumbariaApp').controller('MacumbaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Macumba',
        function($scope, $stateParams, $uibModalInstance, entity, Macumba) {

        $scope.macumba = entity;
        $scope.load = function(id) {
            Macumba.get({id : id}, function(result) {
                $scope.macumba = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('macumbariaApp:macumbaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.macumba.id != null) {
                Macumba.update($scope.macumba, onSaveSuccess, onSaveError);
            } else {
                Macumba.save($scope.macumba, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
