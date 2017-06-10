'use strict';
var TOMCAT_HOST='http://localhost:8080/smpp-platform';
app.factory('histoFact', ['$resource', function ($resource) {
	//$resource() function returns an object of resource class
    return $resource(
    		//TOMCAT_HOST+'/api/historic1',
    		//TOMCAT_HOST+'/api/historic2'
    		TOMCAT_HOST+'/api/historic',
    		TOMCAT_HOST+'/api/historicAll'
    		    		
    );
}]);