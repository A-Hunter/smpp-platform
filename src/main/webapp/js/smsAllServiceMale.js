'use strict';

app.service('smsAllServiceMale', function ($http, $log, $location, CONFIG) {
	return {
		sendAllMale: function(smsAllMessage) {
        console.log("sending message to all users");
            $http.post(CONFIG.backendResources.SMS.sendAllMale,smsAllMessage).
                then(function (response) {
                 
                    $log.info('sms successfully created!');
        		//	$location.path("/result");
                }, function (response) {
                    $log.error('failed to create new sms: ' + response.data);
                });
    }
};
});