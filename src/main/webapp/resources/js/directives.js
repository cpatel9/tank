(function(){
'use strict';

/* Directives */

angular.module('AngularSpringApp.directives', [])
.directive('appVersion', ['version', function (version) {
    return function (scope, elm, attrs) {
        elm.text(version);
    };
}]).directive('taskdrop', function() {
	  return {
	    restrict: 'A',
	    scope: false,
	    templateUrl: 'resources/views/tasks/taskDropdown.html',
	    controller:'TaskController'
	  };
	})
.directive('leftBar',function(){
	return {
	    restrict: 'A',
	    scope: {
	    	user:"=user"
	    },
	    templateUrl: 'resources/views/leftnavigation/leftbar.html',
	    controller:'LeftController'
	  };
	
})
.directive('leftChildBar',function(){
	return {
	    restrict: 'A',
	    scope: {
	    	leftchildjson:"=leftchildjson",
	    		navs:"=navs"
	    },
	    templateUrl: 'resources/views/leftnavigation/leftchildnavs.html',
	    controller:'LeftChildController'
	  };
	
})
})();