'use strict';

app.service('smsAllService', function ($http, $log, $location, CONFIG) {
	return {
		sendAll: function(smsAllMessage) {
        console.log("sending message to all users");
            $http.post(CONFIG.backendResources.SMS.sendAll,smsAllMessage).
                then(function (response) {
                    $log.info('sms successfully created!');
                }, function (response) {
                    $log.error('failed to create new sms: ' + response.data);
                });
    }
};
});