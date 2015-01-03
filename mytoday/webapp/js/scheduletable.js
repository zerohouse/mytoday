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

	$scope.toChart = function(){
		if(lineChart != undefined)
			return;
		loading.start();
			
		try{
			var	typekeys = Object.keys($scope.types);
		} catch (e) {
			setTimeout(function(){
				$scope.toChart();
			},300);
			return;
		}
		
		if(typekeys==undefined){
			setTimeout(function(){
				$scope.toChart();
			},300);
			return;
		}
		
		var keys = Object.keys($scope.days);
		var datasets = [];
		var datasetMap = {};
		
		
		if(keys==undefined || keys.length ==0){
			setTimeout(function(){
				console.log(1);
				$scope.toChart();
			},300);
			return;
		}
		
		var keys = quickSort(keys);
		
		for(var i=0; i< keys.length; i++){
			datasetMap[keys[i]] = [];
		}
		
		
		for(var i=0; i<typekeys.length; i++){
			var color = $scope.types[typekeys[i]].color;
			datasetMap[typekeys[i]] = {
				label : $scope.types[typekeys[i]].name,
				pointStrokeColor: "#fff",
			    pointHighlightFill: "#fff",
				fillColor : hexToRgba(color, 0.2),
				strokeColor : hexToRgba(color, 1),
				pointColor : hexToRgba(color, 1),
				pointHighlightStroke : hexToRgba(color, 1),
				data : []
			}
			for(var j=0; j<keys.length; j++){
				datasetMap[typekeys[i]].data.push(0);
			}
		}
		
		
		var j = 0;
		for(var k=0; k<keys.length; k++){
			var data = $scope.days[keys[k]];
			for(var i=0; i<data.length; i++){
				datasetMap[data[i].type].data[j] += data[i].time;
			}
			j++;
		}
		
		for(var i=0; i<typekeys.length; i++){
			datasets.push(datasetMap[typekeys[i]]);
		}
		
				
		
		var data = {
			    labels:  keys,
			    datasets: datasets
			};
		
		var options = {  
				tooltipTemplate: "<%=label%>: <%= value %>",
			    multiTooltipTemplate: "<%=datasetLabel%>: <%= value/4 %>시간"
			    	};
		

		
		var ctx = document.getElementById("chart").getContext("2d");

		var lineChart = new Chart(ctx).Line(data,options);
		
		setTimeout(function(){
			$('#chart').width($('.modal-body').width());
			$('#chart').height($('.modal-body').width()*2/3);
		},200);
		
		console.log(keys);
		console.log(datasets);
		loading.end();
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
	
	$scope.backColor = function(schedule, opacity){
		var rgb = hexToRgb("#555555");
		return "rgba(" + rgb.r + ","+rgb.g + ","+rgb.b + "," + opacity +")";
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
		if(lineChart !=undefined)
			lineChart.destroy();
		loading.start();
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
				loading.end();
			});
		}, 2000);
	});
	
	
	var lineChart;
	
	datepicker2.datepicker({
		format : "yyyy-mm-dd",
		autoclose : true
	}).on('changeDate', function(e) {
		loading.start();
		if(lineChart !=undefined)
			lineChart.destroy();
		var date1 = datepicker1.datepicker('getDate');
		var date2 = datepicker2.datepicker('getDate');
		if(date1 > date2)
			datepicker1.datepicker('setDate', date2);
		
		$http(postRequest('/schedule/getbetween.my', {
			dateFrom : datepicker1.val(),
			dateTo : datepicker2.val()
		})).success(function(data) {
			$scope.setData(data);
			loading.end();
		});
	});
	
	
	$timeout(function(){
		var setDate = new Date();
		setDate.setDate(setDate.getDate()-7);
		datepicker1.datepicker('setDate', setDate);
		datepicker2.datepicker('setDate', new Date());
	});
	
	
	
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
	
}]);


function quickSort(items, left, right) {
    var index;
    if (items.length > 1) {
        left = typeof left != "number" ? 0 : left;
        right = typeof right != "number" ? items.length - 1 : right;
        index = partition(items, left, right);
        
        if (left < index - 1) {
            quickSort(items, left, index - 1);
        }
        
        if (index < right) {
            quickSort(items, index, right);
        }
        
    }
    return items;
    
    
    function partition(items, left, right) {
        var pivot   = items[Math.floor((right + left) / 2)],
            i       = left,
            j       = right;

        while (i <= j) {
            while (items[i] < pivot) {
                i++;
            }
            while (items[j] > pivot) {
                j--;
            }
            if (i <= j) {
                swap(items, i, j);
                i++;
                j--;
            }
        }
        return i;
    }
    function swap(items, firstIndex, secondIndex){
        var temp = items[firstIndex];
        items[firstIndex] = items[secondIndex];
        items[secondIndex] = temp;
    }
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
		loading.end();
	});
});
