<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${empty sessionScope.user}">
		<c:redirect url="/users/login.my" />
	</c:when>
	<c:otherwise>
		<c:redirect url="/mytoday.my" />
	</c:otherwise>
</c:choose>