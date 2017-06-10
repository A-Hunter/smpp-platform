'use strict';

app.controller('smsAllCtrl',
		function($scope, $http,$log, CONFIG, $location,smsAllService,smsAllServiceMale,smsAllServiceFemale) {

	$scope.url="";
	$scope.smsAllMessage = {
			"sendDate" : "",
			"text":""
		};
		$scope.sendAllSMS= function() {
			smsAllService.sendAll($scope.smsAllMessage);
		}
		$scope.sendAllSMSMale= function() {
			smsAllServiceMale.sendAllMale($scope.smsAllMessage);
		}
		$scope.sendAllSMSFemale= function() {
			smsAllServiceFemale.sendAllFemale($scope.smsAllMessage);
		}
		
			$scope.backToSMS= function() {
			$location.path('/sms');
		}
		
});
