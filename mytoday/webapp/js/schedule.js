/**
 * angular
 */

app.controller('TodayDoingController', [
        '$http',                                	
		'$scope',
		function($http, $scope) {

			controllers.TodayDoingController = $scope;

			$scope.color = function(type) {
				return types[index(type)].color;
			}

			$scope.types = [];

			$scope.newType = {};

			$scope.newDone = function(type) {
				controllers.inputWindow.setType(type);
			}

			$scope.addContent = function(data) {
				// 애드 컨텐트
			}

			$scope.getTextColor = function(bgColor) {
				if (bgColor == null)
					return "#000000";
				var nThreshold = 105;
				var components = getRGBComponents(bgColor);
				var bgDelta = (components.R * 0.299) + (components.G * 0.587)
						+ (components.B * 0.114);

				return ((255 - bgDelta) < nThreshold) ? "#000000" : "#ffffff";
			}

			function getRGBComponents(color) {

				var r = color.substring(1, 3);
				var g = color.substring(3, 5);
				var b = color.substring(5, 7);

				return {
					R : parseInt(r, 16),
					G : parseInt(g, 16),
					B : parseInt(b, 16)
				};
			}

			$scope.addType = function() {

				if ($('#userId').val() != undefined) {
					$http(postRequest('/type/insert.my', {
						type : JSON.stringify($scope.newType)
					})).success(function(result) {
						if (result.success) {
							add(result.error);
						} else {
							warring("저장 오류" + result.error);
						}
					})
					return;
				}

				function add(id) {
					$scope.newType.id = id;
					$scope.types.push($scope.newType);
					var color = $scope.newType.color;
					var index = $scope.types.length - 1;
					$scope.newType = {};
					setTimeout(function() {
						$('.type:not(.colorpicker-element)').colorpicker({
							'input' : 'input.colorpicker',
							'component' : 'i',
							'color' : color
						}).on('changeColor', function(ev) {
							$scope.types[index].color = ev.color.toHex();
							$scope.$apply();
						});
					}, 100);
				}
			};

			$scope.toggle = function(data) {
				if (data)
					data = false;
				else
					data = true;
			}

			$scope.margin = function(value) {
				return (value * 5) + "px";
			}

			$scope.height = function(value) {
				return ((value * 5) - 1) + "px";
			}

			$scope.timeString = timeString;

			$scope.data = {};

			$scope.dataSetting = function(data) {
				$scope.data = {};
				for (var i = 0; i < data.length; i++) {
					if ($scope.data[data[i].type] == undefined) {
						$scope.data[data[i].type] = [];
					}
					$scope.data[data[i].type].push(data[i]);
				}
				$scope.$apply();
			};

		} ]);

app.controller('inputWindow', [ '$http', '$scope', function($http, $scope) {

	controllers.inputWindow = $scope;

	$scope.reset = function() {
		$scope.content = {
			time : 4,
			userId : $('#userId').val()
		};
	}

	$scope.setType = function(type) {
		$scope.content.type = type;
	}

	$scope.reset();

	$scope.timeString = timeString;

	$scope.submit = function() {

		$scope.content.term = 0;

		$scope.content.date = controllers.TodayDoingController.date;

		controllers.TodayDoingController.addContent($scope.content);

		$('#newDone').modal('hide');

		pieChart.addData(parseChartData($scope.content));

		if ($scope.content.userId != undefined) {
			$http(postRequest('/schedule/insert.my', {
				schedule : JSON.stringify($scope.content)
			})).success(function(result) {
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
	var result = types[data.type];
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

var pieChartReset = function() {
	var ctx = document.getElementById("pieChart").getContext("2d");

	pieChart = new Chart(ctx).Pie([], {
		tooltipTemplate : "<%if (label){%><%=label%><%}%>"
	});

	$('#pieChart').css('width', '100%');
	$('#pieChart').css('height', '100%');

}

$(function() {

	$('.type').colorpicker({
		'input' : 'input.colorpicker',
		'component' : 'i'
	}).on('changeColor', function(ev) {
		controllers.TodayDoingController.newType.color = ev.color.toHex();
	});

	var datepicker = $('#datepicker');

	$('span.big-icon:eq(0)').click(function() {
		var date = datepicker.datepicker('getDate');
		date.setDate(date.getDate() - 1);
		datepicker.datepicker('setDate', date);
	});

	$('span.big-icon:eq(1)').click(function() {
		var date = datepicker.datepicker('getDate');
		date.setDate(date.getDate() + 1);
		datepicker.datepicker('setDate', date);
	});

	datepicker.datepicker({
		format : "yyyy-mm-dd",
		autoclose : true
	}).on('changeDate', function(e) {
		$.ajax({
			url : "/schedule/getlist.my",
			type : "POST",
			data : {
				date : datepicker.val()
			}
		}).done(function(data) {
			if (data == null)
				data = [];
			controllers.TodayDoingController.dataSetting(data);
			pieChartReset();
			toChartData(data);

			function toChartData(data) {
				for (var i = 0; i < data.length; i++)
					pieChart.addData(parseChartData(data[i]));
			}
		});
	});

	datepicker.datepicker('setDate', new Date());

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
