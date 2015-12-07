(function(){
	'use strict'

	App.controller('CarouselDemoCtrl', function ($scope) {
		 $scope.myInterval = 5000;
		  var slides = $scope.slides = [];
		  $scope.addSlide = function() {
		    var newWidth =slides.length + 1;
		    slides.push({
		      image: 'resources/slider/' + newWidth + '.jpg',
		      text: ['Incidents','Collaboration','Tier 3'][slides.length % 4] + ' ' +
		        ['Resolution', 'NPI Releases', 'Support'][slides.length % 4]
		    });
		  };
		  for (var i=0; i<3; i++) {
		    $scope.addSlide();
		  }
		});
	
	
})()