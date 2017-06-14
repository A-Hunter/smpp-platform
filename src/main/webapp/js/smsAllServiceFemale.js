'use strict';

app.service('smsAllServiceFemale', function ($http, $log, $location, CONFIG) {
	return {
		sendAllFemale: function(smsAllMessage) {
        console.log("sending message to all users");
            $http.post(CONFIG.backendResources.SMS.sendAllFemale,smsAllMessage).
                then(function (response) {
                 
                    $log.info('sms successfully created!');
                }, function (response) {
                    $log.error('failed to create new sms: ' + response.data);
                });
    }
};
});