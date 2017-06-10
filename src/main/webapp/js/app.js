'use strict';

//defini par trituxsms-ang
var app = angular.module('trituxsms-ang', ['ngRoute','ngResource','ui.bootstrap']);

app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/login', {
        templateUrl: 'views/login.html',
        controller: 'authenCtrl'
      }).
      when('/result', {
        templateUrl: 'views/result.html',
        controller: 'smsCtrl'
      }).
      when('/crud', {
          templateUrl: 'views/crud.html',
          controller: 'UserController'
        }).
      when('/sms', {
            templateUrl: 'views/sms.html',
            controller: 'smsCtrl'
          }).
      when('/smsAll', {
            templateUrl: 'views/smsAll.html',
            controller: 'smsAllCtrl'
            }).
      when('/smsAllMale', {
            templateUrl: 'views/smsAll.html',
            controller: 'smsAllCtrl'
            }).
      when('/smsAllFemale', {
            templateUrl: 'views/smsAll.html',
            controller: 'smsAllCtrl'
            }).
      when('/histo', {
            templateUrl: 'views/histo.html',
            controller: 'histoCtrl'
            }).
      when('/crudUser', {
            templateUrl: 'views/crudUser.html',
            controller: 'UserController'
                }).
      otherwise({
        redirectTo: '/login'
      });
  }]);


var TOMCAT_HOST='http://localhost:8080/smpp-platform';
app.constant('CONFIG', {
	backendResources: {
		SMS:{
			send :TOMCAT_HOST+'/api/sendSMS',
			sendAll :TOMCAT_HOST+'/api/sendAllSMS',
			sendAllMale :TOMCAT_HOST+'/api/sendAllSMSMale',
			sendAllFemale :TOMCAT_HOST+'/api/sendAllSMSFemale',
			crud : TOMCAT_HOST+'/api/user/',
			//historics : TOMCAT_HOST+'/api/historic1/',
			histo1 : TOMCAT_HOST+'/api/historic/',
			histo2 : TOMCAT_HOST+'/api/historicAll/',
			authenUser :TOMCAT_HOST+'/api/authenticate'
		}

	}
});




