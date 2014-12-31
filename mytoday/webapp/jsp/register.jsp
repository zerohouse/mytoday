<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Register - Mytoday</title>
<c:if test="${not empty sessionScope.user}">
	<c:redirect url="/users/modify.my" />
</c:if>
<%@ include file="/components/_css.jspf"%>
<link href="/css/register.css" rel="stylesheet" media="screen">
</head>
<body ng-app='module'>

	<%@ include file="/components/_header.jspf"%>
	<div class="container" ng-controller="registerController">
		<div class="row">
			<div class="register text-center col-md-6 col-md-offset-3">
				<h1>회원가입</h1>
				<span ng-show="!check.id()">아이디는 4~12자의 영문자와 숫자여야 합니다.</span>
				<span class="red" ng-show="check.isExist">{{check.isExist}}</span> 
				<div class="input-group input-group-lg">
					<span class="input-group-addon">UserID</span><input type="text"
						class="form-control" ng-change="checkId()" placeholder="UserID" maxlength="12"
						ng-model="user.id">
				</div>

				<span ng-show="!check.password()">패스워드는 6~20자여야 합니다.</span>
				<span class="red" ng-show="!check.passwordConfirm()">패스워드가 일치하지 않습니다.</span>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">Password</span> <input
						type="password" class="form-control" placeholder="Password"
						maxlength="20" ng-model="user.password"> <input
						type="password" class="form-control"
						placeholder="Password Confirm" maxlength="20"
						ng-model="user.passwordConfirm">
				</div>
				<span ng-show="!check.email()">이메일형식 : id@domain.com</span>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">Email</span><input type="text"
						class="form-control" placeholder="Email" maxlength="30"
						ng-model="user.email">
				</div>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">Name</span><input type="text"
						class="form-control" placeholder="UserName" maxlength="30"
						ng-model="user.name">
				</div>
				<div class="input-group input-group-lg margin-auto">
					<span class="radio"> <label><input type="radio"
							value="M" ng-model="user.gender" name="gender">남</label> <label>
							<input type="radio" value="F" ng-model="user.gender"
							name="gender">여
					</label>
					</span>
				</div>
				<div class="input-group input-group-lg">
					<span class="input-group-addon">NickName</span><input type="text"
						class="form-control" placeholder="UserName" maxlength="30"
						ng-model="user.nickname">
				</div>

				<div>User ==> ID : {{user.id}} | e-mail : {{user.email}} |
					name : {{user.name}} | nickname : {{user.nickname}} | gender :
					{{user.gender}}</div>
				<div>
					Check ==> <span class="red" ng-class="{green:check.id()&&!check.isExist}">ID
						: {{check.isExist||check.id()}}</span> | <span
						class="red" ng-class="{green:check.password()}">password :
						{{check.password()}}</span> | <span class="red"
						ng-class="{green:check.passwordConfirm()}">passwordConfirm
						: {{check.passwordConfirm()}}</span> | <span class="red"
						ng-class="{green:check.email()}">e-mail : {{check.email()}}</span>
					| <span class="red" ng-class="{green:check.done()}">done :
						{{check.done()}}</span>
				</div>
				<div class="btn btn-info btn-lg" ng-click="submit()">회원가입</div>

			</div>
		</div>
	</div>
	</div>

	<%@ include file="/components/_imports.jspf"%>
	<script src="/js/register.js"></script>
</body>
</html>