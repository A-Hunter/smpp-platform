'use strict';

app.controller('histoCtrl', function($scope, $http, $log, CONFIG, $location) {
	$scope.url = "";
	$http.get('http://localhost:8080/smpp-platform/api/historic').success(
			function(data) {
				$scope.msgs = data;
			});
	$http.get('http://localhost:8080/smpp-platform/api/historicAll').success(
			function(data) {
				$scope.msgsAll = data;
			});
	$scope.returnCrud = function() {
		$location.path('/crud')
	}
});