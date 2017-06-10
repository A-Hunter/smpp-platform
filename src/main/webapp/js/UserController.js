'use strict';

app.controller('UserController', ['$scope' ,'User','$location','authenService', function($scope, User,$location,authenService) {
	///////////

    //////////
	$scope.admin="admin";
	$scope.disconnect=function(){
		$location.path("/login");
	}
	
    var self = this;
    self.user= new User();
    
    self.users=[];
    /**
     listAllUsers() 
     findAllUsers();
     */
    self.fetchAllUsers = function(){
  	  self.users = User.query();// all users
  	  $scope.allusers=User.query();
  	  $scope.user=authenService.getuser();
  	  $scope.isuser=false;
  	  $scope.isadmin=false;
  	  console.log($scope.allusers);
  	  console.log($scope.user.role);
  	  if(angular.equals($scope.user.role,"admin")){
  		$scope.isadmin=true;
  		  
  		}else{
  			$scope.isuser=true;
  			
  		}
  	  
  	  console.log($scope.isadmin);
    };
     
    self.createUser = function(){
  	  self.user.$save(function(){
  		  self.fetchAllUsers();
  		  //$location.path("/login");
  	  });
    };

    self.updateUser = function(){
  	  self.user.$update(function(){
			  self.fetchAllUsers();
		  });
    };

   self.deleteUser = function(identity){
  	 var user = User.get({id:identity}, function() {
  		  user.$delete(function(){
  			  console.log('Deleting user with id ', identity);
  			  self.fetchAllUsers();
  		  });
  	 });
    };

    self.fetchAllUsers();

    self.submit = function() {
        if(self.user.id==null){
            console.log('Saving New User', self.user);    
            self.createUser();
        }else{
			  console.log('Upddating user with id ', self.user.id);
            self.updateUser();
            console.log('User updated with id ', self.user.id);
        }
        self.reset();
    };
        
    self.edit = function(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.users.length; i++){
            if(self.users[i].id === id) {
               self.user = angular.copy(self.users[i]);
               break;
            }
        }
    };
        
    self.remove = function(id){
        console.log('id to be deleted', id);
        if(self.user.id === id) {//If it is the one shown on screen, reset screen
           self.reset();
        }
        self.deleteUser(id);
    };

    
    self.reset = function(){
        self.user= new User();
        $scope.myForm.$setPristine(); //reset Form
    };

    self.returnCrud  = function(){
  	  $location.path("/crud");
    };
    

    self.seeHistoric  = function(){
    	  $location.path("/histo");
      };
  self.sendSMS  = function(){
	  $location.path("/sms");
  };
  self.sendAllSMS  = function(){
	  $location.path("/smsAll");
  };
  self.sendAllSMSMale  = function(){
	  $location.path("/smsAllMale");
  };
  self.sendAllSMSFemale  = function(){
	  $location.path("/smsAllFemale");
  };
  self.authenticate = function(){
	  $location.path("/login");
  }

}]);