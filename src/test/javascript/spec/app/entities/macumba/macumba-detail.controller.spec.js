'use strict';

describe('Controller Tests', function() {

    describe('Macumba Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMacumba;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMacumba = jasmine.createSpy('MockMacumba');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Macumba': MockMacumba
            };
            createController = function() {
                $injector.get('$controller')("MacumbaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'macumbariaApp:macumbaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
