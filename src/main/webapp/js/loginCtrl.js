'use strict';

app.controller('loginCtrl',
		function($scope, $http, $log, CONFIG, smsService, $location) {
	
	$scope.login={};
	$scope.cnx =  function(){
		$scope.login={
				email : $scope.login.email,
				pwd : $scope.login.pwd
		};
		console.log($scope.login);
		$location.path("/login");
	};
	
});