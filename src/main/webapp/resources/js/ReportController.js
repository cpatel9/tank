App.controller('reportController',['$scope','$http','FileUploader', '$mdToast','$animate',function($scope,$http,FileUploader,$mdToast,$animate){
	console.log('inside of reportController module ');
	$scope.spinner = false;
	$scope.message = "";
	$scope.error=false;
	var uploader = $scope.uploader = new FileUploader({
	    url:'report/upload',
	    removeAfterUpload: true,
	    onAfterAddingFile: function(item){
	    	console.log('on After adding file ...');
	    	if($scope.uploader.queue[0].file.type != "application/vnd.ms-excel"){
	    		uploader.clearQueue();
	    		console.log('onAfterAddingFile ... ');
	    		$scope.message = "Please select .xls file to upload. ";
	    		Toast('Please select .xls file to upload. ');
	    		$scope.error= true;
	    	}else{
	    		$scope.message = "";
	    		$scope.error = false;
	    	}
	    },
	    onBeforeUploadItem: function (item) {
	    	console.log("started uploading data ...");
	    	$scope.spinner = true;
	    	
	        var stuff = {
	            upload_type: 'BROWSER',
	            email: $scope.email,
	            file_name: $scope.uploader.queue[0].file.name,
	            file_size: $scope.uploader.queue[0].file.size,
	            file_type: $scope.uploader.queue[0].file.type,
	        }
	        item.formData.push(stuff);
	       
	    },
	    onSuccessItem: function (item, response, status, headers) {
	    	if(response === "success"){
	    		Toast("Successfully generated Report and sent mail ");
	    	}else{
	    		$scope.message ="Error occured while generating the Report.. Please look at your email or contact Application Admin";
	    		$scope.error= true;
	    	}
	    		console.log(JSON.stringify(status));
	    		console.log(JSON.stringify(response));
	    },
	    onSuccess:function(item,response,status,headers){
	    	console.log('inside of onSuccess method');
	    },
	    onError:function(item,response,status,headers){
	    	console.log('inside of onError method');
	    },
	    onCompleteAll : function(item,response,status,headers) {
	            console.info('onCompleteAll');
	            $scope.email = "";
	    		$scope.spinner = false;
	    		Toast('Done.. ');
	    		console.log(JSON.stringify(status));
	    		console.log(JSON.stringify(response));
	        },
	    queueLimit: 1,
	});
	
	$scope.$watch('uploader', function(){
		console.log(uploader.queue.length);
		if(uploader.getNotUploadedItems().length>0){
			console.log("uploader value got changed "+uploader.queue[0].file.type);
			console.log("uploader value got changed "+uploader.queue[1].file.type);
		}
	});
	
	
	var Toast = function(message){
		$mdToast.show(
			      $mdToast.simple()
			        .content(message)
			        .position('bottom right')
			        .hideDelay(3000)
			    );
	}
	
	
}]);