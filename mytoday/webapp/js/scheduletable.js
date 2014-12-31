/**
 * angular
 */

app.controller('TableController', [ '$timeout', '$http', '$scope', function($timeout,$http, $scope) {

	controllers.TableController = $scope;
	
	$scope.days = {};
	
	$scope.daysLength = 0;

	$http(postRequest('/schedule/getbetween.my', {
		schedule : JSON.stringify($scope.content)
	})).success(function(data) {
		for(var i=0;i<data.length;i++){
			if($scope.days[data[i].date] == undefined){
				$scope.days[data[i].date] = [];
				$scope.daysLength++;
				}
			$scope.days[data[i].date].push(data[i]);
		}
		
	});
	
	
	$timeout(function () {
		$scope.setDraggable();
	});

	
	$scope.setDraggable = function (){
		$('.daytable li div').draggable({containment: "parent", axis: "y" , grid : [10,10], stop : function(event, ui){
		    angular.element($(this)).data().$scope.schedule.startTime += (ui.position.top - ui.originalPosition.top) / 10;
		}});
	}

	$scope.dayWidth = function() {
		return 100 / $scope.daysLength + "%";
	}
	
	$scope.scheduleHeight = function(schedule) {
		return schedule.time * 10 + "px";
	}

	$scope.scheduleTop = function(schedule) {
		return schedule.startTime * 10 + "px";;
	}
	
} ]);
