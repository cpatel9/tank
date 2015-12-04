App.controller('uploadController',['$scope','$http','FileUploader', '$mdToast','$animate',function($scope,$http,FileUploader,$mdToast,$animate){
	
	console.log("inside file upload controller");
		$scope.spinner = false;
		$scope.error = false;
		var uploader = $scope.uploader = new FileUploader({
			onAfterAddingFile: function(item){
			    	console.log('on After adding file ...');
			    	console.log('$scope.uploader.queue[0].file.type'+ $scope.uploader.queue[0].file.type);
			    	if($scope.uploader.queue[0].file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
			    		uploader.clearQueue();
			    		console.log('onAfterAddingFile ... ');
			    		$scope.message = "Please select .xlsx file to upload. ";
			    		Toast('Please select .xls file to upload. ');
			    		$scope.error =true;
			    	}else{
			    		$scope.message = "";
			    	}
			    },
		    url:'file/upload',
		    removeAfterUpload: true,
		    onBeforeUploadItem: function (item) {
		    	console.log("started uploading data ...");
		        var stuff = {
		            upload_type: 'BROWSER',
		            title: $scope.title,
		            description: $scope.description,
		            file_name: $scope.uploader.queue[0].file.name,
		            file_size: $scope.uploader.queue[0].file.size,
		            file_type: $scope.uploader.queue[0].file.type,
		        }
		        item.formData.push(stuff);
		    	$scope.spinner = true;
		    },
		    onSuccessItem: function (item, response, status, headers) {
		    	console.log('response '+ response);
		    	if(response ==="success"){
		    		Toast('File generated Successfully');
		    		$scope.error = false;
		    		
		    	}else{
		    		$scope.message = "Error occured.. Please contact Application admin for stackTrace ...";
		    		$scope.error = true;
		    	}
		    },
		    onCompleteAll : function() {
		            console.info('onCompleteAll');
		            $scope.title = "";
		    		$scope.description ="";
		    		$scope.spinner = false;
		    		Toast('Done...');
		        },
		    queueLimit: 1,
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