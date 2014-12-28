/**
 * angular
 */


app.controller('TodayDoingController', [ '$scope', function($scope) {

	controllers.TodayDoingController = $scope;

	$scope.color = function(type) {
		return types[index(type)].color;
	}

	$scope.toggle = function(data) {
		if (data.showbody)
			data.showbody = false;
		else
			data.showbody = true;
	}

	$scope.margin = function(value) {
		return (value * 5) + "px";
	}

	$scope.height = function(value) {
		return ((value * 5) - 1) + "px";
	}

	$scope.timeString = timeString;

	$scope.done = [];
} ]);

var types = [ {
	name : "공부",
	on : true,
	color : "#F7464A",
	highlight : "#FF5A5E"
}, {
	name : "운동",
	color : "#46BFBD",
	highlight : "#5AD3D1",
}, {
	name : "일",
	color : "#FDB45C",
	highlight : "#FFC870",

}, {
	name : "기타",
	color : "#B48EAD",
	highlight : "#C69CBE",
} ];

app.controller('inputWindow', [ '$http','$scope', function($http, $scope) {

	controllers.inputWindow = $scope;

	$scope.reset = function() {
		$scope.content = {
			time : 4,
			userId : $('#userId').val()
		};
	}

	$scope.reset();

	$scope.types = types;

	$scope.timeString = timeString;

	$scope.submit = function() {
				
		$scope.content.type = $('.active>input').val();

		$scope.content.term = 0;
		
		$scope.content.date = controllers.TodayDoingController.date;

		controllers.TodayDoingController.done.push($scope.content);

		$('#newDone').modal('hide');

		pieChart.addData(parseChartData($scope.content));

		if($scope.content.userId!=undefined){
			$http(postRequest('/schedule/insert.my', { schedule : JSON.stringify($scope.content) }))
			.success( function(result) {
				if (result.success) {
					$scope.reset();
				} else {
					warring("저장 오류" + result.error);
				}
			})
			return;
		}
		$scope.reset();

	}
} ]);


function parseChartData(data) {
	var result = types[index(data.type)];
	result.value = data.time;
	result.label = data.head;
	return result;
}

function index(type) {
	for (var i = 0; i < types.length; i++) {
		if (types[i].name == type)
			return i;
	}
}

function timeString(value) {
	var result = time(value) + "시간";
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

/**
 * jquery
 */

var pieChart;

var pieChartReset = function(){
	var ctx = document.getElementById("pieChart").getContext("2d");

	pieChart = new Chart(ctx).Pie([], {
		tooltipTemplate : "<%if (label){%><%=label%><%}%>"
	});
	
}

$(function() {
	
	var datepicker = $('#datepicker');
	datepicker.datepicker({
	    format: "yyyy-mm-dd",
	    autoclose: true
	}).on('changeDate', function(e){
		$.ajax({
			url : "/schedule/getlist.my",
			type : "POST",
			data : {
				date : datepicker.val()
			}
		}).done(function(data) {
			if(data==null)
				data = [];
			controllers.TodayDoingController.done = data;
			controllers.TodayDoingController.$apply();
			pieChartReset();
			toChartData(data);
			
			function toChartData(data){
				for(var i=0; i< data.length;i++)
					pieChart.addData(parseChartData(data[i]));
			}
		});
	});

	var ctx = document.getElementById("pieChart").getContext("2d");

	pieChart = new Chart(ctx).Pie([], {
		tooltipTemplate : "<%if (label){%><%=label%><%}%>"
	});
	


	$('#pieChart').css('width', '100%');
	$('#pieChart').css('height', '100%');

	$("#times").slider({
		range : "min",
		value : 4,
		min : 1,
		max : 48,
		slide : function(event, ui) {
			controllers.inputWindow.content.time = ui.value;
			controllers.inputWindow.$apply();
		}
	});
});
