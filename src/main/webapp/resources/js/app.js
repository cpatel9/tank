'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['AngularSpringApp.filters',
					'AngularSpringApp.service', 
					'AngularSpringApp.directives',
					 'ngRoute', 'ui.bootstrap','ngDialog', 
					 'angularFileUpload', 
					 'nvd3ChartDirectives',
					 'ngMaterial']);


//Example of how to set default values for all dialogs
App.config(['ngDialogProvider', function (ngDialogProvider) {
	ngDialogProvider.setDefaults({
		className: 'ngdialog-theme-default',
		plain: false,
		showClose: true,
		closeByDocument: true,
		closeByEscape: true,
		appendTo: false,
		preCloseCallback: function () {
			console.log('default pre-close callback');
		}
	});
}]);


/*var checkLoggedin = function($q, $timeout, $http, $location, $rootScope){ 
	// Initialize a new promise 
	var deferred = $q.defer(); 
	// Make an AJAX call to check if the user is logged in 
	$http.get('./login/isloggedin').success(function(user){ 
		// Authenticated 
		console.log("user logged in"+user);
		if (user != '0') 
		{
			$('.unique').removeClass('hide');
			console.log("not zero scenario")
			deferred.resolve(); // Not Authenticated 
		}
		else { 
				console.log("credentials are zero");
				$rootScope.message = 'You need to log in.'; 
				deferred.reject(); 
				$location.url('/login');
				$('.unique').addClass('hide');
		} 
	}); 
	return deferred.promise; 
}; */


/*var logout = function($q, $timeout, $http, $location, $rootScope){ 
	// Initialize a new promise 
	var deferred = $q.defer(); 
	// Make an AJAX call to check if the user is logged in 
	$http.get('/CloudSoleAngular/login/logout').success(function(data,config){ 
		console.log("logout success");
		deferred.resolve();
	    $('.unique').addClass('hide');
	}).error(function(){
		console.log("logout failure");
		deferred.reject();
	}); 
	return deferred.promise; 
};*/ 
//Declare app level module which depends on filters, and service
App.config(['$routeProvider', function ($routeProvider) {

	$routeProvider.when('/todo', {
		templateUrl: 'todo/layout',
		controller: TodoController
		//resolve: { loggedin: checkLoggedin }
	});

	$routeProvider.when('/dashboard', {
		templateUrl: 'dashboard/layout',
		//controller: TodoController
		controller: 'CarouselDemoCtrl'
	});

	$routeProvider.when('/login', {
		templateUrl: 'login/layout',
		controller: 'LoginController'
	});
	
	$routeProvider.when('/logout', {
		templateUrl: 'login/layout',
		controller: 'LoginController'
		//resolve: { logout: logout }
	});

	$routeProvider.when('/tasks', {
		templateUrl: 'tasks/layout',
		controller: 'TaskController'
			//,		resolve: { loggedin: checkLoggedin }
	});
	

	$routeProvider.when('/file', {
		templateUrl: 'file/layout',
		controller: 'uploadController'
			//,		resolve: { loggedin: checkLoggedin }
	});
	/*$routeProvider.when('/editor', {
        templateUrl: 'editor/layout',
        controller: EditorController
    });*/
	
	$routeProvider.when('/person', {
		templateUrl: 'report/layout',
		controller: 'ShiftController'
			//,		resolve: { loggedin: checkLoggedin }
	});

	$routeProvider.when('/shifts', {
		templateUrl: 'shifts/layout',
		controller: 'ShiftController'
			//,		resolve: { loggedin: checkLoggedin }
	});

	$routeProvider.when('/restangular', {
		templateUrl: 'restangular/layout',
		controller: RestController
		//,resolve: { loggedin: checkLoggedin }
	});
	$routeProvider.when('/report',{
		templateUrl:'report/layout',
		controller:'reportController'
	});

	$routeProvider.otherwise({redirectTo: '/dashboard'});
}]);
