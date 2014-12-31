/**
 * angular
 */

app.controller('registerController', [
		'$http',
		'$scope',
		function($http, $scope) {
			controllers.registerController = $scope;

			$scope.oldPassword = "";
			
			$scope.user = user;

			$scope.oldUser = oldUser;

			$scope.submit = function() {
				
				var submit = {};
				
				if($scope.modify.name())
					submit.name = $scope.user.name;
				if($scope.modify.nickname())
					submit.nickname = $scope.user.nickname;
				if($scope.modify.email())
					submit.email = $scope.user.email;
				if($scope.modify.gender())
					submit.gender = $scope.user.gender;
				if($scope.modify.password())
					submit.password = $scope.user.password;
				if(submit =={})
					return;
				
				$http(postRequest('/users/modify.my', {
					user : JSON.stringify(submit),
					oldPassword : $scope.oldPassword
				})).success(function(result) {
					if (result.success) {
						alert("정보가 수정되었습니다.");
						location.reload();
					} else {
						warring(result.error);
					}
				});
			}

			$scope.modify = {
				name : function() {
					return $scope.oldUser.name != $scope.user.name
							&& $scope.user.name.length > 0;
				},
				nickname : function() {
					return $scope.oldUser.nickname != $scope.user.nickname
							&& $scope.user.nickname.length > 0;
				},
				email : function() {
					return $scope.oldUser.email != $scope.user.email
							&& $scope.check.email();
				},
				gender : function() {
					return $scope.oldUser.gender != $scope.user.gender
							&& $scope.user.gender.length > 0;
				},
				password : function() {
					return $scope.check.password()
							&& $scope.check.passwordConfirm()
							&& $scope.check.oldPassword()
							&& $scope.oldPassword != $scope.user.password;
				},
			}

			$scope.check = {
				oldPassword : function() {
					var pattern = /.{6,20}$/;
					if ($scope.oldPassword.search(pattern) != 0)
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
					if ($scope.user.password != $scope.user.passwordConfirm)
						return false;
					return true;
				},
				email : function() {
					var pattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
					if ($scope.user.email.search(pattern) != 0)
						return false;
					return true;
				}
			}

		} ]);
