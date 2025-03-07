function cargarTasas(selectContrato, selectTasa){
	document.getElementById(selectTasa).value = "";
	obtenerTasas(selectContrato,document.getElementById(selectTasa));
}

function obtenerTasas(selectContrato, selectTasa){
	if(selectContrato.selectedIndex==0){
		selectTasa.length = 0;
		selectTasa.options[0] = new Option("Seleccione Codigo de Tasa", "");
		return;
	}
	selectedOption = selectContrato.options[selectContrato.selectedIndex].value;
	
	var url = "recuperarTasaAjaxAtex5.action?idContratoSeleccionado="+selectedOption;
	
	var req_generico_tasa = NuevoAjax();
	req_generico_tasa.onreadystatechange = function () { 
		rellenarTasas(req_generico_tasa,selectTasa);
	};
	req_generico_tasa.open("POST", url, true);			
	req_generico_tasa.send(null);			
}

function rellenarTasas(req_generico_tasa,selectTasa){
	selectTasa.options.length = 0;
	if (req_generico_tasa.readyState == 4) { 
		if (req_generico_tasa.status == 200) { 
			textToSplit = req_generico_tasa.responseText;
			returnElements=textToSplit.split("||");
			selectTasa.options[0] = new Option("Seleccione Codigo de Tasa", "");
			for ( var i=0; i<returnElements.length; i++ ){
				value = returnElements[i].split(";");
				selectTasa.options[i+1] = new Option(value[0], value[1]);
			}
		}
	}
}

function mostrarBloqueActualizados(){
	$("#bloqueFallidos").hide();
	if($("#bloqueActualizados").is(":visible")){
		$("#bloqueActualizados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	}else{
		$("#bloqueActualizados").show();	
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidos(){
	$("#bloqueActualizados").hide();
	if($("#bloqueFallidos").is(":visible")){
		$("#bloqueFallidos").hide();
		$("#despFallidos").attr("src","img/plus.gif");
		$("#despFallidos").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidos").show();	
		$("#despFallidos").attr("src","img/minus.gif");
		$("#despFallidos").attr("alt","Ocultar");
		
	}
}

function obtenerPestaniaSeleccionada() {
	var toc = document.getElementById("toc");
    if (toc) {
        var lis = toc.getElementsByTagName("li");
        for (var j = 0; j < lis.length; j++) {
            var li = lis[j];
            if (li.className == "current") {
            	return li.id;
            }
        }
    }
}

function getChecksMarcados(name){
	var codigos = "";
	$("input[name='"+name+"']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	return codigos;
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function cambiarElementosPorPaginaEvolucionAtex5(){
	var $dest = $("#displayTagEvolucionAtex5Div");
	$.ajax({
		url:"navegarEvolucionAtex5.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolAtex5").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las evoluciones de la consulta de atex5.');
	    }
	});
}

function abrirEvolucionAlta(idNumExpediente, divDestino){
	return abrirEvolucion($("#"+idNumExpediente).val(),divDestino);
}

function abrirEvolucion(numExpediente, destino){
	if(numExpediente == null || numExpediente == ""){
		return alert("Debe seleccionar alguna consulta para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvolucionAtex5.action?numExpediente=" + numExpediente;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right', at: 'right' },
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 950,height: 500,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}