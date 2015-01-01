<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/components/_css.jspf"%>
<link href="/plugin/bootstrap/datepicker/datepicker.css"
	rel="stylesheet" media="screen">
<link href="/plugin/bootstrap/colorpicker/bootstrap-colorpicker.css"
	rel="stylesheet" media="screen">
<link href="/css/scheduletable.css" rel="stylesheet" media="screen">

<title>나의 하루 - MyToday</title>
</head>
<body ng-app='module'>
	<%@ include file="/components/_header.jspf"%>


	<div ng-controller='TableController' ng-init="setDraggable()">
		<div class='container'>
			<div class='row'>
				<div class='col-md-5 col-md-offset-1'>
					<span class="glyphicon glyphicon-chevron-left big-icon"></span> <input
						class='datepicker' ng-model="dateFrom" type="text"> <span
						class="glyphicon glyphicon-chevron-right big-icon"></span>

				</div>
				<div class='col-md-5'>
					<span class="glyphicon glyphicon-chevron-left big-icon"></span> <input
						class='datepicker' ng-model="dateTo" type="text"> <span
						class="glyphicon glyphicon-chevron-right big-icon"></span>
				</div>
			</div>
		</div>



		<div class='container'>
			<div class='row'>
				<ul class='daytable'>
					<li ng-repeat="(key, day) in days" ng-drop='true'
						ng-style="{height:tableHeight, width:dayWidth()}">
						<span class="days-header">
							<h3>{{key}}</h3>
						</span>
						<hr ng-repeat="time in times" style="margin-top:0" ng-style="{marginBottom:gridpx}"/>

						<div ng-repeat="schedule in day"
							ng-style="{top:scheduleTop(schedule), height:scheduleHeight(schedule), backgroundColor:backColor(schedule)}">
							<p class='lead'>
								<strong>{{schedule.head}}</strong> {{schedule.body}}
							</p>
						</div>


					</li>

				</ul>
			</div>
		</div>
	</div>


	<%@ include file="/components/_imports.jspf"%>
	<script src="/plugin/chartjs/Chart.min.js"></script>
	<script src="/plugin/bootstrap/bootstrap-datepicker.js"></script>
	<script src="/plugin/bootstrap/colorpicker/bootstrap-colorpicker.js"></script>
	<script src="/js/scheduletable.js"></script>
</html>