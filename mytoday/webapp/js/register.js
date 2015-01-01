/**
 * angular
 */

app.controller('registerController', [ '$http', '$scope',
		function($http, $scope) {
			controllers.registerController = $scope;
			$scope.user = {
					id:"",
					email:"",
					password:"",
					passwordConfirm:"",
					name:"",
					gender:"",
					nickname:""
			}
			
			$scope.submit = function(){
				if(!$scope.check.done())
					return;
				$http(postRequest('/users/register.my', {
					user : JSON.stringify($scope.user)
				})).success(function(result) {
					if (result.success) {
						location.href="/";
					} else {
						warring("회원가입 실패")
					}
				});
			}
			
			$scope.checkId = function(){
				clearTimeout($scope.checkIdTimer);
				$scope.checkIdTimer = setTimeout(function(){
				if(!$scope.check.id()){
					$scope.check.isExist = "아이디 중복 체크를 하지 않았습니다.";
					return;
					}
				$scope.check.isExist = "아이디 중복 체크 중";
				$http(postRequest('/users/checkid.my', {
					id : $scope.user.id
				})).success(function(result) {
					if (result.success) {
						$scope.check.isExist = false;
					} else {
						$scope.check.isExist = "이미 존재하는 아이디입니다.";
					}
				})}, 2000);
			}
			
			$scope.check = {
				isExist: "중복체크를 하지 않았습니다.",
				id : function() {
					var pattern = /^[a-zA-Z0-9]{4,12}$/;
					if ($scope.user.id.search(pattern) != 0)
						return false;
					return true;
				},
				password : function() {
					var pattern = /.{6,20}$/;
					if ($scope.user.password.search(pattern) != 0)
						return false;
					return true;
				},
				passwordConfirm : function() {
					if($scope.user.password != $scope.user.passwordConfirm)
						return false;
					return true;
				},
				email : function() {
					var pattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
					if ($scope.user.email.search(pattern) != 0)
						return false;
					return true;
				},
				done : function(){
					if(!$scope.check.id())
						return false;
					if(!$scope.check.password())
						return false;
					if(!$scope.check.passwordConfirm())
						return false;
					if(!$scope.check.email())
						return false;
					if($scope.user.nickname.length==0)
						return false;
					if($scope.user.gender.length==0)
						return false;
					if($scope.user.name.length==0)
						return false;
					if($scope.isExist)
						return false;
					return true;
				}
			}
			loading.end();
		} ]);
