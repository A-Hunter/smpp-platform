'use strict';

app.service('histoService', function ($http, $log, $location, CONFIG) {
	return {
		histo1: function(smsMessage) {
        console.log("sending message to all users");
            $http.get(CONFIG.backendResources.SMS.historics,smsMessage).
                then(function (response) {
                    $log.info('show  messages!');
        			$location.path("/historic");
                }, function (response) {
                    $log.error('no messages ' + response.data);
                });
    }
};
});