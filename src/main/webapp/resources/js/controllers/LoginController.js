(function(){

	'use strict';
	App.controller('LoginController',function($scope, $modal, $log,$location,$rootScope,dbAPI) {

		console.log("Inside of LoginController .....");
		$scope.userInfo={};

		$scope.loginUser = function(){
			console.log("inside loginuser method")
			dbAPI.validateUser($scope.user).success(function (data) {
				console.log("prinitng data"+data.handle);
				if(undefined!=data && undefined!=data.handle && ""!=data.handle){
					$scope.userInfo=data;
					console.log("userinfo"+angular.toJson($scope.userInfo))
					$location.path('/file');
				}
				else{
					$location.path('/login');
				}	
			}).error(function (data, status, headers, config){
				console.log("prinitng data in error"+data);
				$location.path('/login');
			});



		
		}

	});
})();