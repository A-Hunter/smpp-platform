'user strict';
//authenUser

app.service('authenService', function ($http, $log, $location, CONFIG) {
	var myuser=[];
	return {
		authenUser: function(authen,callback) {
        console.log("Searching for :  "+authen.email);
     //   $location.path("/result");
        var test=JSON.stringify(authen);
            // call rest ws to create new testHBC
        
        	var req ={
        			method :'POST',
        			url:CONFIG.backendResources.SMS.authenUser,
        			headers:{
        				'content-type': 'application/json'
        			},
        			data:test
        	}
        	$http(req).then(function(data){console.log(data);
        			//console.log(JSON.parse(data.data));
        			if(!angular.equals(data.data,"")){
        				callback(data);
        				
        				if(angular.equals(data.data.role,"user")){
        					$location.path("/crud");
        				}else{
        					$location.path("/crud");
        				}
        			}
        				
        			else
        				//alert('user not found');
        				$location.path('/crudUser');
        		
        		},function(){console.log('no access')});
        
        /*
            $http.post(CONFIG.backendResources.SMS.authenUser,authen).
                then(function (response) {
                	console.log(response);
                    $log.info('user already exists !');
        			$location.path("/sms");
                }, function (response) {
                    $log.error('user is not authenticated ' + response.data);
                    $log.error('Go to inscription page');
                    $location.path("/crud");
                });*/
                
    },
	setuser:function(user){
		myuser=user
	},
	getuser:function(){
		return myuser;
	}
};


/*this.getuser=function(){
	return myuser;
}*/
});