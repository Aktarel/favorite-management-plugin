
/**
 * Create the module.
 */
var responsabilityTransferModule = angular.module('ResponsabilityTransfer', ['ui.bootstrap']);

/**
 * Controller for the todo list.
 */
responsabilityTransferModule.controller('ResponsabilityTransferController', function(identityService, responsabilityService, pageConfigService, $q, $uibModal) {
    var me = this,
        promises;
    me.responsabilities = [];
    me.pageConfig = {};
    me.selectResponsability = [];
    me.selection = [];
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
    me.hasSelectedResponsabilities = function(){
    	return me.selection.length > 0
    }
    me.toggleSelection = function(responsability) {
        var idx = me.selection.indexOf(responsability);
        if (idx > -1) {
        	me.selection.splice(idx, 1);
        }
        else {
          me.selection.push(responsability);
        }
      };
    
    promises = {
        responsabilities: fetchResponsabilities(),
        pageConfig: fetchPageConfig()
    };

    // load the page config and the responsabilities
    $q.all(promises).then(function(result) {
        me.responsabilities = result.responsabilities;
        me.pageConfig = result.pageConfig;
    });
    
    
    me.launchTransfer = function() {
        $uibModal.open({
            animation: false,
            controller: 'TransferResponsabilitiesModalCtrl as ctrl',
            templateUrl: PluginHelper.getPluginFileUrl('TransferResponsabilities', 'ui/html/modal-template.html'),
            resolve: {
            	identityService: function() {
                    return identityService;
                },
                responsabilityService: function() {
                	 return responsabilityService;
                },
                selectedResponsability : function(){
                	return me.selection;
                },
                responsabilities : function(){
                	return me.responsabilities;
                }
            }
        });
    };
});

/**
 * The controller for the notes modal dialog.
 */
responsabilityTransferModule.controller('TransferResponsabilitiesModalCtrl', function($scope,$uibModalInstance, identityService, responsabilityService,responsabilities,selectedResponsability) {
	var _selected;
    var me = this;
    me.identities = [];
    me.loadingIdentities = null;
    me.noResults = null;
    me.responsabilities = responsabilities;
    me.selectedResponsability = selectedResponsability;
    /**
	 * Closes the dialog.
	 */
    me.close = function() {
        $uibModalInstance.close();
    };
    me.launch = function(newUid){
    	responsabilityService.launchTransferProcess(newUid,selectedResponsability);
    	$uibModalInstance.close();
    };
    
    me.getIdentities = function(val) {
    	me.identities = [];
    	me.loadingIdentities = true;
    	me.noResults = false;
    	return fetchIdentities(val).then(function(objects) {
    		if( objects.length == 0){
    			$scope.noResults = true;
    		}
    		else{
    			me.identities = objects;
    			return objects;
    		}
    		me.loadingIdentities = false;
        });
    };
    
    me.formatLabel = function(model) {
    	   for (var i=0; i< this.identities.length; i++) {
    	     if (model === this.identities[i].name) {
    	       return this.identities[i].displayName;
    	     }
    	   }
    	};
    
    function fetchIdentities(val) {
    	console.log("--- fetchIdentities")
        return identityService.getIdentities(val);
    }
    
});


/**
 * Service to get all responsabilities
 */
responsabilityTransferModule.service('responsabilityService', function($http) {
    var config = {
        headers: {
            'X-XSRF-TOKEN': PluginHelper.getCsrfToken()
        }
    };

    return {
    	 getAllResponsabilities: function() {
             var RESPONSABILITIES_URL = PluginHelper.getPluginRestUrl("TransferResponsabilities/responsability");
             return $http.get(RESPONSABILITIES_URL, config).then(function(response) {
                 return response.data;
             });
         },
         launchTransferProcess: function(newUid,responsabilities) {
             var RESPONSABILITIES_URL = PluginHelper.getPluginRestUrl("TransferResponsabilities/responsability");
             var datas = {}
             datas.newUid = newUid;
             datas.responsabilities = responsabilities;
             
             return $http.post(RESPONSABILITIES_URL,datas,{
                 headers: {
                     'X-XSRF-TOKEN': PluginHelper.getCsrfToken(),
                     'Content-Type':'application/x-www-form-urlencoded'
                 }
             }).then(function(response) {
                 return response.data;
             });
         }
    	

    };

});

/**
 * Services that handles functionality around the page configuration.
 */
responsabilityTransferModule.service('pageConfigService', function($http) {

    var config = {
        headers: {
            'X-XSRF-TOKEN': PluginHelper.getCsrfToken()
        }
    };

    return {

        /**
		 * Gets the page configuration.
		 * 
		 * @return Promise A promise resolving with a page configuration object.
		 */
        getPageConfig: function() {
            var PAGE_CONFIG_URL = PluginHelper.getPluginRestUrl("TransferResponsabilities/pageConfig");
            
            return $http.get(PAGE_CONFIG_URL, config).then(function(response) {
                return response.data;
            });
        }
    };
});
    
responsabilityTransferModule.service('identityService', function($http) {

        var config = {
            headers: {
                'X-XSRF-TOKEN': PluginHelper.getCsrfToken()
            }
        };

        return {

            getIdentities : function(val) {
            	var IDENTITIES_URL = PluginHelper.getPluginRestUrl("TransferResponsabilities/identities");
            	config.params = {};
            	config.params.q = val;
            	
            	return $http.get(IDENTITIES_URL, config).then(function(response){
            		return response.data.map(function(item){
                        return item;
                      });
                });
              }
        };
});

