<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/components/_css.jspf"%>
<link href="/css/login.css" rel="stylesheet" media="screen">
<title>Login - MyToday</title>
</head>
<body ng-app="module" ng-controller="LoginController">
	<%@ include file="/components/_headerlogin.jspf"%>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<form name="loginForm" class="top-margin-percent" ng-submit="submit()" >
					<h1 class="text-center login-title bottom-buffer">MyToday Login</h1>
					<div class="account-wall">
						<fieldset class="form-group">
							<input ng-model="user.id" type="text" class="form-control"
								placeholder="User ID" autofocus required />
						</fieldset>
						<fieldset class="form-group">
							<input ng-model="user.password" type="password"
								class="form-control" placeholder="Password" required /><br />
						</fieldset>
						<fieldset class="form-group">
							<input type="submit" class="btn btn-lg btn-primary btn-block"
								value="Login" />
						</fieldset>
						<h4>{{state.errorMessage}}</h4>
					</div>
					<a href="/users/register.my" class="text-center new-account">Create
						an account </a>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/components/_imports.jspf"%>
	<script>loading.end();</script>
</body>
</html>