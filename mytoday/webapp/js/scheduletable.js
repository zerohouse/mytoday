/**
 * angular
 */

app.controller('TableController', [ '$timeout', '$http', '$scope', function($timeout,$http, $scope) {

	controllers.TableController = $scope;
	
	$scope.saved = true;
	
	$scope.days = {};
	
	$scope.daysLength = 0;
	
	var gridpx = 7;
	
	$scope.gridpx = (gridpx-1) + "px";
	
	$scope.tableHeight = gridpx * 96;
	
	$scope.times = [];
	for(var i=0; i<96; i++)
		$scope.times.push(i);
		
	$scope.scheduleUpdateTimer;
	
	$scope.setDraggable = function (){
		$('.daytable li div').draggable({containment: "parent", axis: "y" , grid : [gridpx,gridpx], 
			drag : function(even, ui){
				$scope.saved = false;
				var time = angular.element($(this)).data().$scope.schedule.startTime + (ui.position.top - ui.originalPosition.top) / gridpx;
				angular.element($(this)).data().$scope.schedule.timeHelper = true;
				$scope.timeHelper = timeString(time);
				$scope.$apply();
			},
			stop : function(event, ui){
			angular.element($(this)).data().$scope.schedule.timeHelper = false;
			angular.element($(this)).data().$scope.schedule.startTime += (ui.position.top - ui.originalPosition.top) / gridpx;
			var schedule = angular.element($(this)).data().$scope.schedule;
			clearTimeout($scope.scheduleUpdateTimer);
		    $scope.scheduleUpdateTimer = setTimeout(function(){
		    $http(postRequest('/schedule/update.my', {
				schedule : JSON.stringify(schedule)
			})).success(function(result) {
				if (result.success) {
					$scope.saved = true;
				} else {
					warring("저장 오류" + result.error);
				}
			});    
		    }, 3000);
		}});
	}

	$scope.dayWidth = function() {
		return (100 - $scope.daysLength*2) / $scope.daysLength + "%";
	}
	
	$scope.scheduleHeight = function(schedule) {
		return schedule.time * gridpx + "px";
	}

	$scope.scheduleTop = function(schedule) {
		return schedule.startTime * gridpx + "px";;
	}
	
	$scope.startTime = function (schedule){
		return timeString(schedule.startTime);
	}
	
	
	
	function timeString(value) {
		var result = time(value) + "시";
		var min = minutes(value);
		if (min != 0)
			result += " " + minutes(value) + "분";
		return result;
	}

	function time(fity) {
		return parseInt(fity / 4);
	}

	function minutes(fity) {
		return (fity % 4) * 15;
	}

	$scope.timeString = timeString;
	
	$scope.setData = function (data){
		$scope.days = {"스케줄 없음":[]};
		$scope.daysLength =0;
		if(data=="null"){
			$scope.daysLength =1;
			return;
			}
		if(data==undefined)
			return;
		delete $scope.days["스케줄 없음"];
		for(var i=0;i<data.length;i++){
			if($scope.days[data[i].date] == undefined){
				$scope.days[data[i].date] = [];
				$scope.daysLength++;
				}
			$scope.days[data[i].date].push(data[i]);
		}
		$timeout(function(){
				$scope.setDraggable();
		},300);
	}
	
	var datepicker1 = $('.datepicker:eq(0)');

	$('span.big-icon:eq(0)').click(function() {
		var date = datepicker1.datepicker('getDate');
		date.setDate(date.getDate() - 1);
		datepicker1.datepicker('setDate', date);
	});

	$('span.big-icon:eq(1)').click(function() {
		var date = datepicker1.datepicker('getDate');
		date.setDate(date.getDate() + 1);
		datepicker1.datepicker('setDate', date);
	});
	
	var datepicker2 = $('.datepicker:eq(1)');

	$('span.big-icon:eq(2)').click(function() {
		var date = datepicker2.datepicker('getDate');
		date.setDate(date.getDate() - 1);
		datepicker2.datepicker('setDate', date);
	});

	$('span.big-icon:eq(3)').click(function() {
		var date = datepicker2.datepicker('getDate');
		date.setDate(date.getDate() + 1);
		datepicker2.datepicker('setDate', date);
	});
	
	var dateUpdateTimer;
	
	datepicker1.datepicker({
		format : "yyyy-mm-dd",
		autoclose : true
	}).on('changeDate', function(e) {
		clearTimeout(dateUpdateTimer)
		dateUpdateTimer = setTimeout(function(){
			var date1 = datepicker1.datepicker('getDate');
			var date2 = datepicker2.datepicker('getDate');
			if(date1 > date2)
				datepicker2.datepicker('setDate', date1);
			
			$http(postRequest('/schedule/getbetween.my', {
				dateFrom : datepicker1.val(),
				dateTo : datepicker2.val()
			})).success(function(data) {
				$scope.setData(data);
			});
		}, 2000);
	});
	
	datepicker2.datepicker({
		format : "yyyy-mm-dd",
		autoclose : true
	}).on('changeDate', function(e) {
		var date1 = datepicker1.datepicker('getDate');
		var date2 = datepicker2.datepicker('getDate');
		if(date1 > date2)
			datepicker1.datepicker('setDate', date2);
		
		$http(postRequest('/schedule/getbetween.my', {
			dateFrom : datepicker1.val(),
			dateTo : datepicker2.val()
		})).success(function(data) {
			$scope.setData(data);
		});
	});
	
	
	$timeout(function(){
		var setDate = new Date();
		setDate.setDate(setDate.getDate()-7);
		datepicker1.datepicker('setDate', setDate);
		datepicker2.datepicker('setDate', new Date());
	});
	
	$scope.backColor = function(schedule, opacity){
			var rgb = hexToRgb("#555555");
		return "rgba(" + rgb.r + ","+rgb.g + ","+rgb.b + "," + opacity +")";
	}
	
}]);
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

$(function() {
	$.ajax({
		url : "/type/getlist.my",
		type : "POST"
	}).done(function(data) {
		controllers.TableController.types = {};
		if (data == null) {
			return;
		}

		for (var i = 0; i < data.length; i++) {
			if (data[i] == undefined)
				continue;
			controllers.TableController.types[data[i].id] = data[i];
		}
		controllers.TableController.backColor = function(schedule){
			var rgb = hexToRgb(controllers.TableController.types[schedule.type].color);
			return "rgba(" + rgb.r + ","+rgb.g + ","+rgb.b + "," + 0.5 +")";
		}
	});
});
