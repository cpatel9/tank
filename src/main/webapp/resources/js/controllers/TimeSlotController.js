App.controller('ShiftController', function($scope, $http,
		$rootScope,$modal,$log,dbAPI,$filter) {
	$scope.search ={};
	$scope.search.date=new Date();
	$scope.dateOptions = {
			formatYear: 'yy',
			startingDay: 1
	};

	$scope.$watch('search.date', function() {
		$scope.search.date=$filter('date')($scope.search.date,'MM/dd/yyyy')
	});
	
	dbAPI.getAllShifts().success(function(data){
		console.log("getAllShifts success"+data);
		
		console.log("json response"+angular.toJson(data));
		if(undefined!=data && ''!=data )
		$scope.info=data
		else
		$scope.info=[];
		
	}).error(function(data){
		
		console.log("getAllShifts error"+data);
		$scope.info=[];
		
	});

	$scope.showDetails = function(scope, dateText1){
		console.log("insode showDetails function"+dateText1);
		/*$http.get('info.json').success(function(response){
			angular.forEach(response, function(value,key) {
				angular.forEach(value, function(value1,key) {
					scope.info.push(value1);
					console.log(key);
				});
			}); 
		});  */
		
		
		
		scope.info=$scope.info;
		console.log("before "+angular.toJson(scope.info))
		
	/*	$filter('date')(scope.info, {date:dateText1|'dd-mm-yyyy'})
		
		console.log("after "+angular.toJson(scope.info))*/
		

	} 


});