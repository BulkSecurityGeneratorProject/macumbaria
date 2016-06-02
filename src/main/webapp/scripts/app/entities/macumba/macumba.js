'use strict';

angular.module('macumbariaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('macumba', {
                parent: 'entity',
                url: '/macumbas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'macumbariaApp.macumba.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/macumba/macumbas.html',
                        controller: 'MacumbaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('macumba');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('macumba.detail', {
                parent: 'entity',
                url: '/macumba/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'macumbariaApp.macumba.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/macumba/macumba-detail.html',
                        controller: 'MacumbaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('macumba');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Macumba', function($stateParams, Macumba) {
                        return Macumba.get({id : $stateParams.id});
                    }]
                }
            })
            .state('macumba.new', {
                parent: 'macumba',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/macumba/macumba-dialog.html',
                        controller: 'MacumbaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    destinatario: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('macumba', null, { reload: true });
                    }, function() {
                        $state.go('macumba');
                    })
                }]
            })
            .state('macumba.edit', {
                parent: 'macumba',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/macumba/macumba-dialog.html',
                        controller: 'MacumbaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Macumba', function(Macumba) {
                                return Macumba.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('macumba', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('macumba.delete', {
                parent: 'macumba',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/macumba/macumba-delete-dialog.html',
                        controller: 'MacumbaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Macumba', function(Macumba) {
                                return Macumba.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('macumba', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
