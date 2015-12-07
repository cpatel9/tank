(function() {

'use strict';


/* Services */

var AppServices = angular.module('AngularSpringApp.service', []);

AppServices.value('version', '0.1');


/*
 * This factory has been created to make sure that 
 * same set of data can be shared over all the modules.
 */


AppServices.factory('tasks', function () {
    return [   {taskId:1,taskName:'Task 1',assignedTo:'Bharath',taskDesc:'This is task 1',complexity:'Small',effort:10,taskStatus:10},
	           {taskId:2,taskName:'Task 2',assignedTo:'Ramu',taskDesc:'This is task 2',complexity:'Complex',effort:20,taskStatus:40},
	           ];
});


AppServices.factory('leftnavs', function ($http) {
    return $http.get('resources/info/navigation.json');       
});


AppServices.factory('dbAPI', function($http) {
	var dbAPI = {};

	//return http.post('oAuth/getArtifact/', angular.toJson({ artifactType: 'copyDesign', artifactData: designId }));


	dbAPI.validateUser=function(user){
		console.log("printing user json"+angular.toJson(user))
		return $http.post('./login/secured/',angular.toJson(user));
	}
	dbAPI.getuser=function(){
			   $http.post('./login/secured/getuser').success(function (data) {
				   return data;
			   }).error(function(data){
				   return data;
			   })
			}
	
	
	dbAPI.getAllShifts=function(){
				return $http.get('resources/info/shift.info');
			}

	/*dbAPI.getDrivers = function() {
      return $http({
        method: 'JSONP', 
        url: 'http://ergast.com/api/f1/2013/driverStandings.json?callback=JSON_CALLBACK'
      });
    }


    dbAPI.getDriversData = function() {
    var data;
       $http({
        method: 'JSONP', 
        url: 'http://ergast.com/api/f1/2013/driverStandings.json?callback=JSON_CALLBACK'
      }).success(function (response,status, headers, config) {
        console.log("success"+response);
        data = response.MRData.StandingsTable.StandingsLists[0].DriverStandings;
    })
      .error(function (data, status, headers, config)
        {
          console.log("error");
          data=data;
        });

       return data
    }
	dbAPI.getDriverDetails = function(id) {
      return $http({
        method: 'JSONP', 
        url: 'http://ergast.com/api/f1/2013/drivers/'+ id +'/driverStandings.json?callback=JSON_CALLBACK'
      });
    }

    dbAPI.getDriverRaces = function(id) {
      return $http({
        method: 'JSONP', 
        url: 'http://ergast.com/api/f1/2013/drivers/'+ id +'/results.json?callback=JSON_CALLBACK'
      });
    }*/
	return dbAPI;

});

})();



