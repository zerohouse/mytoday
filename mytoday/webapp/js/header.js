/**
 * 
 */

var app = angular.module('module', []);


var controllers = {};

function warring(text){
	var warring = $('#warring');
	warring.show('fold',500);
	warring.text(text);
	
	warring.click(function(){
		warring.hide();
	});
	
	setTimeout(function(){
		warring.hide('fold',500);
	}, 4000);
}



function postRequest(url, data) {
	return {
		method: 'POST',
		url: url,
		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		transformRequest: function (obj){
			var str = [];
			for(var p in obj)
				str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			return str.join("&");
		},
		data: data
	}
}

app.controller('LoginController', ['$http','$scope' , function($http, $scope) {
	
	controllers.LoginController = $scope;
	
	$scope.user = {};
	$scope.submit = function() {
		$http(postRequest('/users/login.my', { user : JSON.stringify($scope.user) }))
		.success( function(result) {
			if (result.success) {
				location.reload();
			} else {
				warring(result.error);
			}
		});
	}
	
}]);

var loading = {};
loading.start = function(){
	$('.loading').show('fade',500)
	};
loading.end = function(){
	$('.loading').hide('fade',500);
	};

	
function hexToRgba(hex, alpha){
	var rgb = hexToRgb(hex);
	return "rgba(" + rgb.r + ","+rgb.g + ","+rgb.b + "," + alpha +")";
}



function hexToRgb(hex) {
	// Expand shorthand form (e.g. "03F") to full form (e.g. "0033FF")
	var shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
	hex = hex.replace(shorthandRegex, function(m, r, g, b) {
		return r + r + g + g + b + b;
	});
	
	var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
	return result ? {
		r : parseInt(result[1], 16),
		g : parseInt(result[2], 16),
		b : parseInt(result[3], 16)	} : null;
}

Date.prototype.yyyymmdd = function() {
	   var yyyy = this.getFullYear().toString();
	   var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
	   var dd  = this.getDate().toString();
	   return yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]); // padding
	  };
