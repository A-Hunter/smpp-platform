'user strict';

app.service('authenService', function ($http, $log, $location, CONFIG) {
	var myuser=[];
	return {
		authenUser: function(authen,callback) {
        console.log("Searching for :  "+authen.email);
        var test=JSON.stringify(authen);
        	var req ={
        			method :'POST',
        			url:CONFIG.backendResources.SMS.authenUser,
        			headers:{
        				'content-type': 'application/json'
        			},
        			data:test
        	}
        	$http(req).then(function(data){console.log(data);
        			if(!angular.equals(data.data,"")){
        				callback(data);
        				
        				if(angular.equals(data.data.role,"user")){
        					$location.path("/crud");
        				}else{
        					$location.path("/crud");
        				}
        			}
        			else
        				$location.path('/crudUser');
        		},function(){console.log('no access')});
    },
	setuser:function(user){
		myuser=user
	},
	getuser:function(){
		return myuser;
	}
};

});