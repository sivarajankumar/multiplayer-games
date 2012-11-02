angular.module('uiComponents', []).directive('datatable', function() {
	var htmlTable = '<table></table>';
	return {
		restrict : 'E',
		template : htmlTable,
		replace : true,
		scope : {
			aaData : '=',
			aoColumns : '=',
			postProcessor : '&'
		},
		compile : function(tElement, tAttrs, transclude){
			
			var linkFunction = function(scope, element, attrs){
				var dataTable = element.dataTable({
					aaData : scope.aaData,
					aoColumns : scope.aoColumns
				});
				scope.postProcessor({dataTable: dataTable});
				
				scope.$watch('aaData', function() {
					dataTable.fnClearTable();
					dataTable.fnAddData(scope.aaData);
			    });
			};
			
			return linkFunction;
		}
	};
});