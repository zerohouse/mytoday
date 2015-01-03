<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/components/_css.jspf"%>
<link href="/plugin/bootstrap/datepicker/datepicker.css"
	rel="stylesheet" media="screen">
<link href="/plugin/bootstrap/colorpicker/bootstrap-colorpicker.css"
	rel="stylesheet" media="screen">
<link href="/css/schedule.css" rel="stylesheet" media="screen">

<title>나의 하루 - MyToday</title>
</head>
<body ng-app='module'>
	<%@ include file="/WEB-INF/components/_header.jspf"%>


	<div ng-controller='TodayDoingController'>
		<div class='container'>
			<div class='row'>
				<div class='col-md-5 col-md-offset-1'>
					<span class="glyphicon glyphicon-chevron-left big-icon pointer"></span>
					<input id='datepicker' ng-model="date" type="text"> <span
						class="glyphicon glyphicon-chevron-right big-icon pointer"></span>
				</div>
				<div class='col-md-2 pull-right'>
					<span ng-show="!saved"
						class="glyphicon glyphicon-refresh bigger-icon red"></span> <span
						ng-show="saved" class="glyphicon glyphicon-ok bigger-icon green"></span>
				</div>
			</div>
		</div>

		<div class='jumbotron jumbo-padding'>
			<div class='container'>
				<div class='input-group'>
					<div class="input-group-addon transparent">
						<button type="button" class="transparent" data-toggle="dropdown"
							aria-expanded="false">
							<div class="emoticon"
								ng-style="{'background-position':emoticonSet(dateheader.emoticonSelected)}"></div>
						</button>
						<ul class="dropdown-menu emoticonWrap" role="menu">
							<li ng-repeat="earray in emoticon">
								<div>
									<span ng-repeat="e in earray" class='emoticon'
										ng-click="emoticonSelect(e)"
										ng-style="{'background-position':emoticonSet(e)}"></span>
								</div>
							</li>
						</ul>
					</div>


					<input class='input-heading' ng-model='dateheader.header'
						ng-change="dateHeaderUpdate()" placeholder="오늘은 한마디로!" />
				</div>
			</div>
		</div>

		<div class='container'>
			<div class='row'>
				<div class='col-md-8'>
					<div ng-repeat='type in types' class="col-md-6">
						<div class='panel' ng-style="{'border-color': type.color}">

							<div class="input-group type"
								ng-style="{'background-color':type.color}">


								<span class="input-group-addon transparent"
									style="padding-right: 0"><i
									class="glyphicon glyphicon-star"></i></span> <input type='hidden'
									class='colorpicker' ng-model="type.color" value="type.color">
								<input type="text" class="form-control transparent"
									ng-style="{'color': getTextColor(type.color)}"
									placeholder="한 일" ng-change="updateType(type.id)"
									ng-model="type.name" value="type.name"><span
									ng-click="deleteType(type.id)"
									class="input-group-addon transparent"><span
									class='glyphicon glyphicon-minus pointer'></span></span>
							</div>


							<ul class="list-group">
								<li ng-repeat="eachdata in data[type.id]"
									class="list-group-item" class='pointer'
									ng-click="toggle(eachdata)">{{eachdata.head}}
									{{timeString(eachdata.time, "시간")}} <span
									class="glyphicon glyphicon-minus pull-right pointer"
									ng-click="deleteSchedule(eachdata)"></span>
									<div ng-show="eachdata.showbody">{{eachdata.body}}</div>
								</li>
								<li class="list-group-item" class='pointer'><a
									type="button" data-toggle="modal" ng-click="newDone(type)"
									data-target="#newDone">추가하기 <span
										class='glyphicon glyphicon-plus'></span></a></li>
							</ul>
						</div>
					</div>

					<div class="col-md-6">
						<div class='panel panel-default'>
							<div class="input-group type">
								<span class="input-group-addon"><i></i></span> <input
									type='hidden' class='colorpicker' ng-model="newType.color">
								<input type="text" class="form-control" placeholder="새로운 유형"
									ng-model="newType.name"> <span
									class="input-group-addon pointer" ng-click="addType()">새로운
									유형 <span class='glyphicon glyphicon-plus'></span>
								</span>
							</div>

						</div>
					</div>
				</div>

				<div class='col-md-4'>
					<div class='panel panel-primary'>
						<div class='panel-heading'>나의 하루</div>
						<div class='panel-body relative'>
							<hr ng-repeat="time in times" style="margin-top: 0"
								ng-style="{marginBottom:gridpx}" />
								<div class="pointer schedule" ng-repeat="schedule in originalData"
									ng-style="{top:scheduleTop(schedule) ,height:scheduleHeight(schedule), backgroundColor:backColor(schedule, 0.5), borderColor:backColor(schedule)}">
									<p class='lead'
										ng-style="{lineHeight: scheduleHeight(schedule)}">
										<strong>{{schedule.head}}</strong> <span class='draghelper'></span><small
											ng-show="schedule.timeHelper">{{timeHelper}}부터</small><small
											ng-show="!schedule.timeHelper">{{timeString(schedule.startTime, "시")}}부터</small>
										{{schedule.body}}
									</p>
								</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div ng-controller="inputWindow" class="modal fade" id="newDone"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h1>{{type.name}}</h1>
				</div>
				<div class="modal-body">

					<input type="text" placeholder="한 일" class="form-control"
						ng-model="content.head">

					<div class="form-group">
						<label for="recipient-name" class="control-label">몇시간 했나요?
							<mark> <span class='font-big'>{{timeString(content.time,
								"시간")}}</span></mark>
						</label>
						<div class='times'></div>
					</div>

					<div class="form-group">
						<label for="recipient-name" class="control-label">몇시부터
							했나요? <mark> <span class='font-big'>{{timeString(content.startTime,
								"시")}}</span></mark>
						</label>
						<div class='times'></div>
					</div>

					<div class="form-group">
						<textarea placeholder="내용" class="form-control"
							ng-model="content.body"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					<button type="button" class="btn btn-primary" ng-click="submit()">한
						일 등록</button>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/components/_imports.jspf"%>
	<script src="/plugin/chartjs/Chart.min.js"></script>
	<script src="/plugin/bootstrap/bootstrap-datepicker.js"></script>
	<script src="/plugin/bootstrap/colorpicker/bootstrap-colorpicker.js"></script>
	<script src="/js/schedule.js"></script>
</html>