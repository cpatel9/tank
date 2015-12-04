App.controller('TaskController',function($scope,ngDialog,$filter,tasks){
	/*var tasks=[
	           {taskId:1,taskName:'Task 1',assignedTo:'Bharath',taskDesc:'This is task 1',complexity:'Small',effort:10,taskStatus:10},
	           {taskId:2,taskName:'Task 2',assignedTo:'Ramu',taskDesc:'This is task 2',complexity:'Complex',effort:20,taskStatus:40},

	           ];*/

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


	$scope.label=labels;
	$scope.header=headers;
	$scope.tasks=tasks;

	$scope.addTask=function(){
		console.log("tasks"+$filter('json')($scope.tasks));
		console.log("newTask"+$filter('json')($scope.newTask))
		console.log("add task"+$scope.newTask.taskId);
		var taskId=0;
		if($scope.newTask.taskId ==null ||$scope.newTask.taskId ==undefined ||$scope.newTask.taskId=='')
		{
			angular.forEach($scope.tasks,function(task){
				if(task.taskId>taskId){taskId=task.taskId;}
			})
			taskId=taskId+1;
			console.log("printing task Id after calculation"+taskId);
			$scope.newTask.taskId=taskId;
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
			console.log("after tasks"+$filter('json')($scope.tasks));
			ngDialog.close();
		}
	};



	$scope.OpenTaskPopup = function () {
		$scope.newTask={};
		ngDialog.open({
			template: 'resources/views/tasks/TaskModal.html',
			className: 'ngdialog-theme-default',
			scope: $scope
			//controller:'TaskController' // dont do this.This would create new instanse of TaskController
		});
	};

	$scope.editTask=function(task){
		
		console.log('Inside editTask'+task.taskId);
		
		angular.forEach($scope.tasks,function(origTask){
			if(origTask.taskId==task.taskId)
			{
				$scope.newTask=angular.copy(task);
			};
		});

		ngDialog.open({
			template: 'resources/views/tasks/TaskModal.html',
			className: 'ngdialog-theme-default',
			scope: $scope
			//controller:'TaskController' // dont do this.This would create new instanse of TaskController
		});
		


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




});

