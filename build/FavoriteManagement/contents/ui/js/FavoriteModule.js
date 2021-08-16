/**
 * Create the module.
 */
var favoriteManagementModule = angular.module('FavoriteManagement', [
		'ui.bootstrap', 'btorfs.multiselect', 'ngRoute','ui.select']);



favoriteManagementModule.controller('FavoriteManagementController', function(
		identityService, responsabilityService, pageConfigService, $q,
		$uibModal, $route, $routeParams, $location) {

	var me = this, promises;
	this.$route = $route;
    this.$location = $location;
    this.$routeParams = $routeParams;
	me.timeout = 5000;
	me.responsabilities = [];
	me.pageConfig = {};
	me.selectResponsability = [];
	me.alerts = [];
	me.closeAlert = function(index) {
		console.log("-- close!");
		me.alerts.splice(index, 1);
	};

	function fetchResponsabilities() {
		return responsabilityService.getAllResponsabilities();
	}
	function fetchPageConfig() {
		return pageConfigService.getPageConfig();
	}
	me.getResponsabilities = function() {
		fetchResponsabilities().then(function(objects) {
			me.responsabilities = objects;
		});
	};
	me.hasSelectedResponsabilities = function() {
		return me.selection.length > 0
	}
	me.toggleSelection = function(responsability) {
		var idx = me.selection.indexOf(responsability);
		if (idx > -1) {
			me.selection.splice(idx, 1);
		} else {
			me.selection.push(responsability);
		}
	};

	promises = {
		responsabilities : fetchResponsabilities(),
		pageConfig : fetchPageConfig()
	};

	// load the page config and the responsabilities
	$q.all(promises).then(function(result) {
		me.responsabilities = result.responsabilities;
		me.pageConfig = result.pageConfig;
	});

	me.requestFavModal = function() {
		$uibModal.open({
			animation : false,
			controller : 'RequestFavoriteModalCtrl as ctrl',
			templateUrl : PluginHelper.getPluginFileUrl('FavoriteManagement',
					'ui/html/request-fav-modal.html'),
			resolve : {
				identityService : function() {
					return identityService;
				},
				alerts : function() {
					return me.alerts;
				}
			}
		});
	};

	me.editFavModal = function() {
		$uibModal.open({
			animation : false,
			controller : 'EditFavoriteModalCtrl as ctrl',
			templateUrl : PluginHelper.getPluginFileUrl('FavoriteManagement',
					'ui/html/edit-fav-modal.html'),
			resolve : {
				identityService : function() {
					return identityService;
				}
			}
		});
	};

	me.editSecurityFavModal = function() {
		$uibModal.open({
			animation : false,
			controller : 'EditSecurityFavoriteModalCtrl as ctrl',
			templateUrl : PluginHelper.getPluginFileUrl('FavoriteManagement',
					'ui/html/edit-security-fav-modal.html'),
			resolve : {
				identityService : function() {
					return identityService;
				}
			}
		});
	};

	me.deleteFavModal = function() {
		$uibModal.open({
			animation : false,
			controller : 'DeleteRequestFavoriteModalCtrl as ctrl',
			templateUrl : PluginHelper.getPluginFileUrl('FavoriteManagement',
					'ui/html/delete-fav-modal.html'),
			resolve : {}
		});
	};
})
.controller('RequestFavoriteModalCtrl', function(
		$scope, $uibModalInstance, identityService, alerts) {
	var _selected;
	var me = this;
	me.identities = [];
	me.loadingIdentities = null;
	me.noResults = null;
	/**
	 * Closes the dialog.
	 */
	me.close = function() {
		$uibModalInstance.close();
	};
	me.launch = function(newUid) {
		alerts.push({
			msg : 'Your favorite has been successfully requested !',
			type : 'success',
			timeout : '5000'
		});
		$uibModalInstance.close();
	};

	me.getIdentities = function(val) {
		me.identities = [];
		me.loadingIdentities = true;
		me.noResults = false;
		return fetchIdentities(val).then(function(objects) {
			if (objects.length == 0) {
				$scope.noResults = true;
			} else {
				me.identities = objects;
				return objects;
			}
			me.loadingIdentities = false;
		});
	};

	me.formatLabel = function(model) {
		for (var i = 0; i < this.identities.length; i++) {
			if (model === this.identities[i].name) {
				return this.identities[i].displayName;
			}
		}
	};

	function fetchIdentities(val) {
		console.log("--- fetchIdentities")
		return identityService.getIdentities(val);
	}

})
.controller('EditFavoriteModalCtrl', function($scope,
		$uibModalInstance, identityService) {
	var _selected;
	var me = this;
	me.identities = [];
	me.loadingIdentities = null;
	me.noResults = null;

	/**
	 * Closes the dialog.
	 */
	me.close = function() {
		$uibModalInstance.close();
	};
	me.launch = function(newUid) {
		$uibModalInstance.close();
	};

	me.getIdentities = function(val) {
		me.identities = [];
		me.loadingIdentities = true;
		me.noResults = false;
		return fetchIdentities(val).then(function(objects) {
			if (objects.length == 0) {
				$scope.noResults = true;
			} else {
				me.identities = objects;
				return objects;
			}
			me.loadingIdentities = false;
		});
	};

	me.formatLabel = function(model) {
		for (var i = 0; i < this.identities.length; i++) {
			if (model === this.identities[i].name) {
				return this.identities[i].displayName;
			}
		}
	};
	function fetchIdentities(val) {
		console.log("--- fetchIdentities")
		return identityService.getIdentities(val);
	}
})
.controller('EditSecurityFavoriteModalCtrl', function(
		$scope, $uibModalInstance, identityService) {
	var _selected;
	var me = this;
	me.identities = [];
	me.loadingIdentities = null;
	me.noResults = null;
	me.selection = [];
	me.selectionBr = [];
	me.selectionUsersAllowed = [];
	me.optionsUsersAllowed = [ {name:"Nicolas LEBEC",id:"c98847"},{name:"Patrick EVRARD",id:"123456"},{name:"Delphine MENJIKOFF",id:"544947"}  ];
	me.options = [ {name:"APAC SAILPOINT Business Analyst",id:"1111"},{name:"AMER SAILPOINT Business Analyst",id:"1234"},{name:"AMER SAILPOINT ADMIN Workitem Administrator",id:"4567"}];
	me.optionsBusinessrole = [ {name:"APAC SSSC Business Analyst",id:"7894"},{name:"Global SAILPOINT Security Officer STG",id:"8987"},{name:"Global SAILPOINT Data referential STG",id:"7899"} ];
	/**
	 * Closes the dialog.
	 */
	me.close = function() {
		$uibModalInstance.close();
	};
	me.launch = function(newUid) {
		$uibModalInstance.close();
	};

	me.getIdentities = function(val) {
		me.identities = [];
		me.loadingIdentities = true;
		me.noResults = false;
		return fetchIdentities(val).then(function(objects) {
			if (objects.length == 0) {
				$scope.noResults = true;
			} else {
				me.identities = objects;
				return objects;
			}
			me.loadingIdentities = false;
		});
	};

	me.formatLabel = function(model) {
		for (var i = 0; i < this.identities.length; i++) {
			if (model === this.identities[i].name) {
				return this.identities[i].displayName;
			}
		}
	};
	function fetchIdentities(val) {
		console.log("--- fetchIdentities")
		return identityService.getIdentities(val);
	}
})
.controller('DeleteRequestFavoriteModalCtrl', function(
		$scope, $uibModalInstance) {
	var _selected;
	var me = this;

	me.close = function() {
		$uibModalInstance.close();
	};

}).service('responsabilityService', function($http) {
	var config = {
		headers : {
			'X-XSRF-TOKEN' : PluginHelper.getCsrfToken()
		}
	};

	return {
		getAllResponsabilities : function() {
			var RESPONSABILITIES_URL = PluginHelper
					.getPluginRestUrl("FavoriteManagement/responsability");
			return $http.get(RESPONSABILITIES_URL, config).then(
					function(response) {
						return response.data;
					});
		},
		launchTransferProcess : function(newUid, responsabilities) {
			var RESPONSABILITIES_URL = PluginHelper
					.getPluginRestUrl("FavoriteManagement/responsability");
			var datas = {}
			datas.newUid = newUid;
			datas.responsabilities = responsabilities;

			return $http.post(RESPONSABILITIES_URL, datas, {
				headers : {
					'X-XSRF-TOKEN' : PluginHelper.getCsrfToken(),
					'Content-Type' : 'application/x-www-form-urlencoded'
				}
			}).then(function(response) {
				return response.data;
			});
		}

	};

}).service('pageConfigService', function($http) {

	var config = {
		headers : {
			'X-XSRF-TOKEN' : PluginHelper.getCsrfToken()
		}
	};

	return {

		/**
		 * Gets the page configuration.
		 * 
		 * @return Promise A promise resolving with a page configuration object.
		 */
		getPageConfig : function() {
			var PAGE_CONFIG_URL = PluginHelper
					.getPluginRestUrl("FavoriteManagement/pageConfig");

			return $http.get(PAGE_CONFIG_URL, config).then(function(response) {
				return response.data;
			});
		}
	};
}).service('identityService', function($http) {

	var config = {
		headers : {
			'X-XSRF-TOKEN' : PluginHelper.getCsrfToken()
		}
	};

	return {

		getIdentities : function(val) {
			var IDENTITIES_URL = PluginHelper
					.getPluginRestUrl("TransferResponsabilities/identities");
			config.params = {};
			config.params.q = val;

			return $http.get(IDENTITIES_URL, config).then(function(response) {
				return response.data.map(function(item) {
					return item;
				});
			});
		}
	};
})
.controller('CreateFavoriteManagementController',function($routeParams) {
		var me = this, promises;
		this.params = $routeParams;
})
.config(function($routeProvider, $locationProvider) {
	$routeProvider.when('/', {
      templateUrl: PluginHelper.getPluginFileUrl('FavoriteManagement','ui/html/empty-search.html'),
    });
	$routeProvider.when('/createFavorite', {
      templateUrl: PluginHelper.getPluginFileUrl('FavoriteManagement','ui/html/create-fav-form.html'),
      controller: 'CreateFavoriteManagementController',
      controllerAs: 'CreateFav'
    });
	$routeProvider.when('/searchFavorite', {
	      templateUrl: PluginHelper.getPluginFileUrl('FavoriteManagement','ui/html/search-fav.html'),
	      controller: 'FavoriteManagementController'
	});
	
});
