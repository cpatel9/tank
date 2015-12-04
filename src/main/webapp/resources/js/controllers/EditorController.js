(function(){
	var app=angular.module('addTaskApp', ['ui.bootstrap','ngDialog']);

	var tasks=[
	           {taskId:1,taskName:'Task 1',assignedTo:'Bharath',taskDesc:'This is task 1',complexity:'Small',effort:10,taskStatus:10},
	           {taskId:2,taskName:'Task 2',assignedTo:'Ramu',taskDesc:'This is task 2',complexity:'Complex',effort:20,taskStatus:40},

	           ];

	var labels={
			lTaskName:'Task Name',
			lTaskDesc:'Task Description',
			lAssignedTo:'Assigned To',
			lComplexity:'Complexity',
			lEdit:'Edit',
			lDelete:'Delete',
			lTaskStatus:'Task Status',
			lTaskEffort:'Task Effort',
			lProjectName:'PLDS',
			lTaskList:'List of Tasks'
	};

	var headers={
			hTaskheader:'Task Creation Form'
	};

	// Example of how to set default values for all dialogs
	app.config(['ngDialogProvider', function (ngDialogProvider) {
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
	app.controller('addTaskController',['$scope','ngDialog','$filter',function($scope,ngDialog,$filter){
		$scope.label=labels;
		$scope.header=headers;
		$scope.tasks=tasks;

		/*$scope.findIndex=function(objs,obj,elem){
			console.log("objs   :"+$filter('json')(objs));
			console.log("obj   :"+$filter('json')(obj));
			console.log("elem   :"+$filter('json')(elem));
			for(i=0;i<objs.lenght;i++){
				if(objs[i].elem=obj.elem)
					return i;
			}
		};*/

		$scope.addTask=function(){
			console.log("tasks"+$filter('json')($scope.tasks));
			console.log("newTask"+$filter('json')($scope.newTask))
			console.log("add task"+$scope.newTask.taskId);
			var taskId=0;
			if($scope.newTask.taskId ==null ||$scope.newTask.taskId =='undefined' ||$scope.newTask.taskId=='')
			{
				angular.forEach($scope.tasks,function(task){
					if(task.taskId>taskId){taskId=task.taskId;}
				})
				taskId=taskId+1;
				console.log("printing task Id after calculation"+taskId);
				$scope.newTask.taskId=taskId;
				$scope.tasks.push(this.newTask);
				//added for emptying the form.
				$scope.newTask={};
				ngDialog.close();
			}  
			else{
				angular.forEach($scope.tasks,function(value,key){
					if(value.taskId==$scope.newTask.taskId){
						console.log("inside if condition");
						$scope.tasks[key]=$scope.newTask;
					}
				});
				console.log("after tasks"+$filter('json')($scope.tasks));
				ngDialog.close();
			}
		};
		
		addTask=function(){
			console.log("tasks"+$filter('json')($scope.tasks));
			console.log("newTask"+$filter('json')($scope.newTask))
			console.log("add task"+$scope.newTask.taskId);
			var taskId=0;
			$scope.$apply(function(){
			if($scope.newTask.taskId ==null ||$scope.newTask.taskId =='undefined' ||$scope.newTask.taskId=='')
			{
				angular.forEach($scope.tasks,function(task){
					if(task.taskId>taskId){taskId=task.taskId;}
				})
				taskId=taskId+1;
				console.log("printing task Id after calculation"+taskId);
				$scope.newTask.taskId=taskId;
				console.log("newTask after adding task Id"+$filter('json')($scope.newTask))
				//$scope.tasks.push(this.newTask);
				$scope.tasks.push($scope.newTask);
				
				//added for emptying the form.
				$scope.newTask={};
				ngDialog.close();
				
			}  
			else{
				angular.forEach($scope.tasks,function(value,key){
					if(value.taskId==$scope.newTask.taskId){
						console.log("inside if condition");
						$scope.tasks[key]=$scope.newTask;
					}
				});
				ngDialog.close();
			}
			});
			console.log("after tasks"+$filter('json')($scope.tasks));
			
		};



		$scope.OpenTaskPopup = function () {
			
			$scope.newTask={};
			ngDialog.open({
				template: 'TaskModal.html',
				className: 'ngdialog-theme-plain',
				scope: $scope,
				controller:'addTaskController'
			});
		};

		$scope.editTask=function(task){
			console.log('Inside editTask'+task.taskId);

			ngDialog.open({
				template: 'TaskModal.html',
				className: 'ngdialog-theme-plain',
				scope: $scope,
				controller:'addTaskController'
			});
			angular.forEach($scope.tasks,function(origTask){
				if(origTask.taskId==task.taskId)
				{
					$scope.newTask=angular.copy(task);
				};
			})


		}




		$scope.deleteTask=function(task){
			console.log("Entering deleteTask fn");
			for(i in $scope.tasks){
				if($scope.tasks[i].taskId==task.taskId)
				{
					/*
				The splice() method adds/removes items to/from an array, and returns the removed item(s).
				Note: This method changes the original array.
					 */
					$scope.tasks.splice(i,1);
				};
			}
			console.log("Leaving deleteTask fn");
		}




	}]);
	
	app.directive('taskdrop', function() {
		  return {
		    restrict: 'A',
		    scope: false,
		    templateUrl: 'taskDropdown.html',
		    controller:'addTaskController'
		  };
		});
	app.directive('taskmodule', function() {
		  return {
		    restrict: 'AE',
		    scope: false,
		    templateUrl: 'TaskModule.html',
		    controller:'addTaskController as cont'
		  };
		});

	
	/*app.config(['$routeProvider',
	         function($routeProvider) {
	           $routeProvider.
	             when('/login', {
	               templateUrl: 'add-order.html',
	               controller: 'addTaskController'
	             }).
	             when('/showOrders', {
	               templateUrl: 'templates/show-orders.html',
	               controller: 'addTaskController'
	             }).
	             otherwise({
	               redirectTo: '/addOrder'
	             });
	         }]);*/




})();
