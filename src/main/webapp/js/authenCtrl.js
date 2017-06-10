'use strict';

app.controller('authenCtrl',
		function($scope, $http, $log, CONFIG, $location,authenService) {
	$scope.url="";
	$scope.authen = {
			"email" : "",
			"password":""
		};

		$scope.authenticate= function() {
			authenService.authenUser($scope.authen,function(data){ //récuperer les données des utilisateur à partir du service
				console.log(data.data);
				authenService.setuser(data.data);
			});
		}
});
