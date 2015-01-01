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
				<div class='col-md-3 col-md-offset-1'>
					<span class="glyphicon glyphicon-chevron-left big-icon pointer"></span> <input
						class='datepicker' ng-model="dateFrom" type="text"> <span
						class="glyphicon glyphicon-chevron-right big-icon pointer"></span>
				</div>
				<div class='col-md-3'>
					<span class="glyphicon glyphicon-chevron-left big-icon pointer"></span> <input
						class='datepicker' ng-model="dateTo" type="text"> <span
						class="glyphicon glyphicon-chevron-right big-icon pointer"></span>
				</div>
				<div class='col-md-2 pull-right'>
					<a type="button" data-toggle="modal" data-target="#newDone"
						ng-click="toChart()"><span class='glyphicon glyphicon-signal big-icon right-buffer pointer'></span></a>
					<span ng-show="!saved"
						class="glyphicon glyphicon-refresh bigger-icon red"></span> <span
						ng-show="saved" class="glyphicon glyphicon-ok bigger-icon green"></span>
				</div>
			</div>
		</div>



		<div class='container'>
			<div class='row'>
				<ul class='daytable panel panel-default'>
					<li ng-repeat="(key, day) in days" ng-drop='true'
						ng-style="{height:tableHeight, width:dayWidth()}"><span
						class="days-header">
							<h3>{{key}}</h3>
					</span>
						<hr ng-repeat="time in times" style="margin-top: 0"
							ng-style="{marginBottom:gridpx}" />

						<div class="pointer" ng-repeat="schedule in day"
							ng-style="{top:scheduleTop(schedule), height:scheduleHeight(schedule), backgroundColor:backColor(schedule, 0.5), borderColor:backColor(schedule)}">
							<p class='lead'>
								<strong>{{schedule.head}}</strong> <span class='draghelper'></span><small
									ng-show="schedule.timeHelper">{{timeHelper}}부터</small><small
									ng-show="!schedule.timeHelper">{{timeString(schedule.startTime)}}부터</small>
								{{schedule.body}}
							</p>
						</div></li>

				</ul>
			</div>
		</div>
	</div>

	<div class="modal fade" id="newDone" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h1>차트보기</h1>
				</div>
				<div class="modal-body">
					<canvas id='chart' width="600" height="440"></canvas>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>


	<%@ include file="/components/_imports.jspf"%>
	<script src="/plugin/chartjs/Chart.min.js"></script>
	<script src="/plugin/bootstrap/bootstrap-datepicker.js"></script>
	<script src="/plugin/bootstrap/colorpicker/bootstrap-colorpicker.js"></script>
	<script src="/js/scheduletable.js"></script>
</html>