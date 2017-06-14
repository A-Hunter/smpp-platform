'use strict';

app.controller('smsCtrl',
		function($scope, $http, $log, CONFIG, $location,smsService,smsAllService) {
	$scope.url="";
	$scope.smsMessage = {
			"phone" : "",
			"sendDate" : "",
			"text":""
		};
		$scope.sendSMS= function() {
			smsService.send($scope.smsMessage);
		}
		$scope.sendAllSMS=function(){
			$location.path("/smsAll");
		}
		$scope.returnCrud=function(){
			$location.path("/crud");
		}
});
