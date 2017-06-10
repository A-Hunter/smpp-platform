'use strict';

app.controller('indexCtrl',
		function($scope, $http, $log, CONFIG, smsService) {
	$scope.smsMessage = {
		"phone" : "",
		"text":""
	};
	$scope.sendSMSOld= function() {
		smsService.send($scope.smsMessage);
	}
});