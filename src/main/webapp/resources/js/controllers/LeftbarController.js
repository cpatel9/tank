(function(){

	'use strict';
	App.controller('LeftController',function($scope,leftnavs,$location,dbAPI) {

		console.log("Inside of LeftController .....");
		
		$scope.sidebar='Dashboard';
		
		$scope.navs ={
				childrendisplay:false,
				leftnavigation:{}
		};
		//$scope.userInfo=dbAPI.getuser();
		
		/*dbAPI.getuser().success(function (data) {
			console.log("prinitng data"+data.handle);
			if(undefined!=data && undefined!=data.handle && ""!=data.handle){
				$scope.userInfo=data;
			}
			else{
				$location.path('/login');
			}	
		}).error(function (data, status, headers, config){
			console.log("prinitng data in error"+data);
			$location.path('/login');
		});*/

		
		
		leftnavs.success(function (data) {
			console.log("prinitng data from leftnavs"+JSON.parse(angular.toJson(data)));
			console.log("prinitng data from leftnavs object"+JSON.parse(angular.toJson(data)));
			if(undefined!=data){
				data=JSON.parse(angular.toJson(data));
				console.log("printing data"+data);
				console.log(data['NPI/LC-Load Files']['parentlink']['name']);
				$scope.navs.leftnavigation=data;
			}
		})

		
		
		$scope.openchildren=function(link){
			console.log("inside openchilder method"+link);
			$scope.sidebar=link;
			//console.log("$scope.navs.leftnavigation"+angular.toJson($scope.navs.leftnavigation));
			//console.log("$scope.navs.leftnavigation[link]"+angular.toJson($scope.navs.leftnavigation[link]))
			
				if(undefined!=$scope.navs.leftnavigation[link]){
					$scope.navs.childnavs=$scope.navs.leftnavigation[link];
					if(undefined!=$scope.navs.leftnavigation[link]['childlinks']){
					$scope.navs.childrendisplay=true;
					}
					else{
						
						$scope.navs.childrendisplay=false;
						var url=$scope.navs.leftnavigation[link]['parentlink']['url']
						if(url.indexOf("http")>-1){
							//$scope.sidebar="";
							console.log("has http")
							window.open(
									  url,
									  $scope.navs.leftnavigation[link]['parentlink']['target'] // <- This is what makes it open in a new window.
									);
							
							$location.path("/dashboard");
							
						}
						else{
							console.log("has url");
							$location.path(url);
						}
					}
					console.log("child json="+angular.toJson($scope.navs.childnavs))
				}
				else{
					console.log("else condition");
				}
			
			
		}
		
		

	});
	App.controller('LeftChildController',function($scope) {

		console.log("Inside of LeftChildController .....");

		
		$scope.closechildren=function(link){
			console.log("closing children");
			$scope.navs.childrendisplay=false;
			
		}

	})
})();