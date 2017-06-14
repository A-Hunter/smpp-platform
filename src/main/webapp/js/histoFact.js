'use strict';
var TOMCAT_HOST='http://localhost:8080/smpp-platform';
app.factory('histoFact', ['$resource', function ($resource) {
    return $resource(
    		TOMCAT_HOST+'/api/historic',
    		TOMCAT_HOST+'/api/historicAll'
    );
}]);