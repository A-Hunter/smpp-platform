'use strict';

app.service('histoAllService', function ($http, $log, $location, CONFIG) {
	return {
		historicss: function(smsAllMessage) {
        console.log("sending message to all users");
 //sendAllSMS
            $http.get(CONFIG.backendResources.SMS.historics,smsAllMessage).
                then(function (response) {
                 
                    $log.info('show all messages!');
        			$location.path("/histo");
                }, function (response) {
                    $log.error('mo messages: ' + response.data);
                });
                
    }
};
});