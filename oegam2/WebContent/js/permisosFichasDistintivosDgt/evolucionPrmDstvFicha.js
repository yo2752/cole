function cambiarElementosPorPaginaEvolucionPrmDstvFicha(){
	var $dest = $("#displayTagEvolucionPrmDstvFichaDiv");
	$.ajax({
		url:"navegarEvolucionPrmDstvFicha.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolPrmDstvFicha").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la evolucion.');
	    }
	});
}

function cambiarElementosPorPaginaEvolucionDocPrmDstvFicha(){
	var $dest = $("#displayTagEvolucionDocPrmDstvFichaDiv");
	$.ajax({
		url:"navegarEvoDocPrmDstvFicha.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolDocPrmDstvFicha").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la evolucion.');
	    }
	});
}