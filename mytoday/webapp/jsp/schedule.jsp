<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/components/_css.jspf"%>
<title>나의 하루.mytoday</title>
</head>
<body ng-app='module'>
	<%@ include file="/components/_header.jspf"%>
	<br>
	<br>
	<br>
	<div class='container'>
		<div class='jumbotron'>
			<input id='datepicker' ng-model="date" type="text" class="span2"><span class="add-on"><span
					class="glyphicon glyphicon-calendar"></span></span>
		</div>
		<div class='row' ng-controller='TodayDoingController'>
			<div class='col-md-3'>
				<div class='panel panel-success'>
					<div class='panel-heading'>시간</div>
						<ul class="list-group fixed-time-bar">
							<li ng-repeat="do in done" class="list-group-item" style="
							padding-top:0;
							margin-top:{{margin(do.term)}};
							height:{{height(do.time)}};
							line-height:{{height(do.time)}};
							background-color:{{color(do.type)}};
							">{{do.head}}
								{{do.type}} {{timeString(do.time)}}</li>
						</ul>
					</div>
			</div>
			<div class='col-md-5'>
				<div class='panel panel-primary'
					>
					<div class='panel-heading'>오늘 한 일</div>
					<ul class="list-group">
						<li ng-repeat="do in done" class="list-group-item" ng-click="toggle(do)">{{do.head}}
							{{do.type}} {{timeString(do.time)}}
							<div ng-show="do.showbody">{{do.body}}</div>	
						</li>
						<li class="list-group-item"><a type="button"
							data-toggle="modal" data-target="#newDone">추가하기</a></li>
					</ul>
				</div>
			</div>
			<div class='col-md-4'>
				<div class='panel panel-primary'>
					<div class='panel-heading'>나의 하루</div>
					<div class='panel-body'>
						<canvas id='pieChart'></canvas>
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
					<input type="text" placeholder="한 일" class="form-control" ng-model="content.head">
				</div>
				<div class="modal-body">
					<div class="form-group">
						<div class="btn-group" data-toggle="buttons">
							<label ng-repeat="type in types" class="btn btn-primary"
								ng-class="{active:type.on}"> <input type="radio"
								ng-value="type.name"> {{type.name}}
							</label>
						</div>
					</div>

					<div class="form-group">
						<label for="recipient-name" class="control-label">몇시간 했나요?
							<mark> <span class='font-big' id='timesvalue'>{{timeString(content.time)}}</span></mark>
						</label>
						<div id='times'></div>
					</div>

					<div class="form-group">
						<textarea placeholder="내용" class="form-control" ng-model="content.body"></textarea>
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

	<%@ include file="/components/_imports.jspf"%>
	<script src="/plugin/chartjs/Chart.min.js"></script>
	<script src="/plugin/bootstrap/bootstrap-datepicker.js"></script>
	<script src="/js/schedule.js"></script>
</html>