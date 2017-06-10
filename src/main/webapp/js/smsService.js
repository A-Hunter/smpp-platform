'use strict';

app.service('smsService', function ($http, $log, $location, CONFIG,smsAllService) {
	return {
		send: function(smsMessage) {
        console.log("sending message to  "+smsMessage.phone +" in this time :"+smsMessage.sendDate);
        
     //   $location.path("/result");
   
            // call rest ws to create new testHBC
            $http.post(CONFIG.backendResources.SMS.send,smsMessage).
                then(function (response) {
                 
                    $log.info('sms successfully created!');
        		//	$location.path("/result");
                }, function (response) {
                    $log.error('failed to create new sms: ' + response.data);
                });
                
    }
};
});
