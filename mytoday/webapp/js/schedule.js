/**
 * angular
 */

var emoticon = [
    [ [1,7], [74,7], [142,7], [210,7], [280,7] ],
    [ [1,84], [74,84], [142,84], [210,84], [278,84] ],
]



app.controller('TodayDoingController', [
		'$http',
		'$scope',
		function($http, $scope) {

			controllers.TodayDoingController = $scope;
			
			controllers.TodayDoingController.saved = true;
			
			$scope.deleteSchedule = function(data){
				if(!confirm("스케줄을 삭제하시겠습니까?"))
					return;
				controllers.TodayDoingController.saved  = false;
				$http(postRequest('/schedule/delete.my', {
					id : data.id
				})).success(function(result) {
					if (result.success) {
						var index = $scope.data[data.type].indexOf(data);
						delete $scope.data[data.type][index];
						$scope.data[data.type].splice(index, 1);
						controllers.TodayDoingController.saved  = true; controllers.TodayDoingController.$apply();
					} else {
						warring("저장 오류" + result.error);
					}
				})
			}
			
			// 데이터 헤더
			$scope.dateheaderReset = function(){
				$scope.dateheader = {};
				$scope.dateheader.emoticonSelected = [1,7];
			}
			
			$scope.dateheaderReset();
			
			// 이모티콘
			$scope.emoticon = emoticon;
			
			$scope.emoticonSelect = function(e){
				$scope.dateheader.emoticonSelected = e;
				$scope.dateHeaderUpdate();
			}
			
			$scope.emoticonSet = function(array){
				return array[0]+"px " + array[1]+"px"
			}
			
			$scope.dateHeaderUpdate = function(){
				controllers.TodayDoingController.saved  = false;
				clearTimeout($scope.dateHeaderTimer);
				$scope.dateHeaderTimer = setTimeout(function() {
					var dateheader = {};
					dateheader.date = $scope.date;
					dateheader.header = $scope.dateheader.header;
					dateheader.emoticon = JSON.stringify($scope.dateheader.emoticonSelected);
					$.ajax({
						url : "/dateheader/update.my",
						type : "POST",
						data : {
							dateheader : JSON.stringify(dateheader)
						}
					}).done(function(result) {
						if (result.success) {
							controllers.TodayDoingController.saved  = true; controllers.TodayDoingController.$apply();
						} else {
							warring("저장 오류" + result.error);
						}
					});
				}, 3000);
			}
			
			$scope.dateHeaderSetting = function(dateheader){
				$scope.dateheader.header = dateheader.header;
				$scope.dateheader.emoticonSelected = JSON.parse(dateheader.emoticon);
				$scope.$apply();
			}
			
			

			$scope.color = function(type) {
				return types[index(type)].color;
			}

			$scope.newType = {};

			$scope.newDone = function(type) {
				controllers.inputWindow.setType(type);
			}

			$scope.addContent = function(data) { 
				// 애드 컨텐트
				if($scope.data[data.type] == undefined)
					$scope.data[data.type] = [];
				$scope.originalData.push(data);
				$scope.data[data.type].push(data);
				
				setTimeout(function(){$scope.setDraggable();}, 300);
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

			$scope.typesTimer = {};
			
			$scope.updateType = updateType;

			$scope.deleteType = function(id) {

				if ($('#userId').val() != undefined) {
					if (!confirm("해당 유형과 포함된 스케줄이 모두 삭제됩니다.")){
						controllers.TodayDoingController.saved  = false;
						return;}
					$http(postRequest('/type/delete.my', {
						id : id
					})).success(function(result) {
						if (result.success) {
							delete $scope.types[id];
							controllers.TodayDoingController.saved  = true; controllers.TodayDoingController.$apply();
						} else {
							warring("저장 오류" + result.error);
						}
					})
					return;
				}
			}

			$scope.addType = function() {
				if ($scope.newType.color == undefined)
					$scope.newType.color = "#000000";
				if ($('#userId').val() != undefined) {
					controllers.TodayDoingController.saved  = false;
					$http(postRequest('/type/insert.my', {
						type : JSON.stringify($scope.newType)
					})).success(function(result) {
						if (result.success) {
							add(result.error);
							controllers.TodayDoingController.saved  = true;
							controllers.TodayDoingController.$apply();
						} else {
							warring("저장 오류" + result.error);
						}
					})
					return;
				}

				function add(id) {
					$scope.data[id] = [];
					$scope.newType.id = id;
					$scope.types[id] = ($scope.newType);
					var color = $scope.newType.color;
					$scope.newType = {};
					setTimeout(function() {
						$('.type:not(.colorpicker-element)').colorpicker({
							'input' : 'input.colorpicker',
							'component' : 'i',
							'color' : color
						}).on('changeColor', function(ev) {
							$scope.types[id].color = ev.color.toHex();
							$scope.$apply();
							updateType(id);
						});
					}, 100);
				}
			};

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

			$scope.data = {};

			$scope.dataSetting = function(data) {
				$scope.originalData = data;
				$scope.data = {};
				for (var i = 0; i < data.length; i++) {
					if ($scope.data[data[i].type] == undefined) {
						$scope.data[data[i].type] = [];
					}
					$scope.data[data[i].type].push(data[i]);
				}
				$scope.$apply();
				setTimeout(function(){$scope.setDraggable();},300);
			};

			
			
			
			var gridpx = 7;
			
			$scope.gridpx = (gridpx-1) + "px";
			
			$scope.tableHeight = gridpx * 96;
			
			$scope.times = [];
			for(var i=0; i<96; i++)
				$scope.times.push(i);
				
			
			$scope.setDraggable = function (){
				$('.schedule:not(.ui-draggable)').draggable({containment: "parent", axis: "y" , grid : [gridpx,gridpx], 
					drag : function(even, ui){
						$scope.saved = false;
						var time = angular.element($(this)).data().$scope.schedule.startTime + (ui.position.top - ui.originalPosition.top) / gridpx;
						angular.element($(this)).data().$scope.schedule.timeHelper = true;
						$scope.timeHelper = timeString(time, "시");
						$scope.$apply();
					},
					stop : function(event, ui){
					angular.element($(this)).data().$scope.schedule.timeHelper = false;
					angular.element($(this)).data().$scope.schedule.startTime += (ui.position.top - ui.originalPosition.top) / gridpx;
					var schedule = angular.element($(this)).data().$scope.schedule;
					schedule.date = $('#datepicker').val();
				    $http(postRequest('/schedule/update.my', {
						schedule : JSON.stringify(schedule)
					})).success(function(result) {
						if (result.success) {
							$scope.saved = true;
						} else {
							warring("저장 오류" + result.error);
						}
					});    
				}});
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
			
			$scope.backColor = function(schedule, opacity){
				var rgb = hexToRgb($scope.types[schedule.type].color);
				return "rgba(" + rgb.r + ","+rgb.g + ","+rgb.b + "," + opacity +")";
			}
			

		} ]);

app.controller('inputWindow', [ '$http', '$scope', function($http, $scope) {

	controllers.inputWindow = $scope;

	$scope.reset = function() {
		$scope.content = {
			time : 4,
			startTime : 40,
			userId : $('#userId').val()
		};
	}

	$scope.setType = function(type) {
		$scope.type = type;
	}

	$scope.reset();

	$scope.timeString = timeString;

	$scope.submit = function() {

		$scope.content.term = 0;

		$scope.content.date = controllers.TodayDoingController.date;

		$scope.content.type = $scope.type.id;


		$('#newDone').modal('hide');

		if ($scope.content.userId != undefined) {
			controllers.TodayDoingController.saved  = false;
			$http(postRequest('/schedule/insert.my', {
				schedule : JSON.stringify($scope.content)
			})).success(function(result) {
				if (result.success) {
					$scope.content.id = result.error;
					controllers.TodayDoingController.addContent($scope.content);
					$scope.reset();
					controllers.TodayDoingController.saved  = true;
				} else {
					warring("저장 오류" + result.error);
				}
			})
			return;
		}

		$scope.reset();

	}
} ]);

function updateType(id) {
	controllers.TodayDoingController.saved  = false;
	clearTimeout(controllers.TodayDoingController.typesTimer[id]);
	controllers.TodayDoingController.typesTimer[id] = setTimeout(function() {
		$.ajax({
			url : "/type/update.my",
			type : "POST",
			data : {
				type : JSON.stringify(controllers.TodayDoingController.types[id])
			}
		}).done(function(result) {
			if (result.success) {
				controllers.TodayDoingController.saved  = true; controllers.TodayDoingController.$apply();
			} else {
				warring("저장 오류" + result.error);
			}
		});
	}, 3000);
}

function parseChartData(data) {
	if(controllers.TodayDoingController.types == undefined)
		setTimeout(function(){parseChartData(data)}, 500);
	var result = {};
	if(controllers.TodayDoingController.types == undefined)
		controllers.TodayDoingController.types = {};
	var type = controllers.TodayDoingController.types[data.type];
	
	if(type == undefined)
		return; 
	
	result.highlight = ColorLuminance(type.color, 0.3);
	result.color = type.color;
	result.name = type.name;
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

function timeString(value, suffix) {
	var result = time(value) + suffix;
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

function ColorLuminance(hex, lum) {

	// validate hex string
	hex = String(hex).replace(/[^0-9a-f]/gi, '');
	if (hex.length < 6) {
		hex = hex[0] + hex[0] + hex[1] + hex[1] + hex[2] + hex[2];
	}
	lum = lum || 0;

	// convert to decimal and change luminosity
	var rgb = "#", c, i;
	for (i = 0; i < 3; i++) {
		c = parseInt(hex.substr(i * 2, 2), 16);
		c = Math.round(Math.min(Math.max(0, c + (c * lum)), 255)).toString(16);
		rgb += ("00" + c).substr(c.length);
	}

	return rgb;
}


/**
 * jquery
 */


$(function() {
	$.ajax({
		url : "/type/getlist.my",
		type : "POST"
	}).done(function(data) {
		controllers.TodayDoingController.types = {};
		if (data == null) {
			return;
		}

		for (var i = 0; i < data.length; i++) {
			if (data[i] == undefined)
				continue;
			controllers.TodayDoingController.types[data[i].id] = data[i];
			controllers.TodayDoingController.$apply();
		}
		
		var typeLength = $('.type.input-group').length;
		for (var i =0; i< typeLength-1 ;i++){
			var each = $('.type.input-group:eq('+i+')');
			var color = angular.element(each.parent().parent()).data().$scope.type.color;
			each.colorpicker({'input' : 'input.colorpicker',
							'component' : 'i',
							'color' : color
									}).on('changeColor', function(ev) {
				angular.element($(this).parent().parent()).data().$scope.type.color = ev.color.toHex();
				controllers.TodayDoingController.$apply();
				updateType(angular.element($(this).parent().parent()).data().$scope.type.id);
			});
		}
		var each = $('.type.input-group:eq('+(typeLength-1)+')');
		each.colorpicker({'input' : 'input.colorpicker',
						'component' : 'i'
								}).on('changeColor', function(ev) {
			controllers.TodayDoingController.newType.color = ev.color.toHex();
			controllers.TodayDoingController.$apply();
		});

	});
	
	setTimeout(function(){

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
		loading.start();
		clearTimeout(controllers.TodayDoingController.dateHeaderTimer);
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
	
		 	loading.end();
			 
		});
		$.ajax({
			url : "/dateheader/get.my",
			type : "POST",
			data : {
				date : datepicker.val()
			}
		}).done(function(data) {
			if (data == null){
				controllers.TodayDoingController.dateheaderReset();
				return;
				}
			controllers.TodayDoingController.dateHeaderSetting(data);
			loading.end();
		});
	});

	datepicker.datepicker('setDate', new Date());

	$(".times:eq(0)").slider({
		range : "min",
		value : 4,
		min : 1,
		max : 48,
		slide : function(event, ui) {
			controllers.inputWindow.content.time = ui.value;
			controllers.inputWindow.$apply();
		}
	});
	
	$(".times:eq(1)").slider({
		range : "min",
		value : 4,
		min : 1,
		max : 96,
		slide : function(event, ui) {
			controllers.inputWindow.content.startTime = ui.value;
			controllers.inputWindow.$apply();
		}
	});
	
	}, 300);
});
