$(document).ready(function(){
   $('#unidades').on("cut copy paste",function(e) {
      e.preventDefault();
   });
});


function  buscarMateriales(){
	var nif = $("#idNifTitular").val();
	var tipoTramite = $("#idTipoTramite").val();
	if(nif != ""  && tipoTramite == ""){
		return alert("Tiene que seleccionar un tipo de trámite para realizar la consulta.");
	}
	$("#formData").attr("action", "buscarStockMateriales").trigger("submit");
}

function limpiarFiltrosStockMateriales(){
	$("#idTipoJefatura").val("");
	$("#idTipoMaterial").val("");
}

function recargarStock(){
	$("#formData").attr("action", "recargarStockMateriales").trigger("submit");
}

function cargarPopUpAgregarMaterial(id){
	var $dest = $("#divEmergenteAgregarMaterial");
	var form = $("#formData");
	$.post("cargarPopUpAgregarStockMateriales.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				appendTo: form,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 500,
				height: 300,
				buttons : {
					Agregar : function() {
						$("#formData").attr("action", "agregarMaterialStockMateriales.action?idSeleccionado="+id).trigger("submit");
					},
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			// Viene <html>, así que no es un modal
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});

}

function cargarPopUp(id){
	var $dest = $("#divEmergenteRecargarStock");
	var form = $("#formData");
	$.post("cargarPopUpRecargarStockMateriales.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				appendTo: form,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 400,
				height: 200,
				buttons : {
					Recargar : function() {
						$("#formData").attr("action", "recargarMaterialStockMateriales.action?idSeleccionado="+id).trigger("submit");
					},
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			// Viene <html>, así que no es un modal
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

function cargarPopUpEvolucion(id){
	var $dest = $("#divEmergenteEvolucionStock");
	var form = $("#formData");
	$.post("inicioEvolucionMaterial.action?idStock="+id, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				appendTo: form,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 300,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			// Viene <html>, así que no es un modal
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

function eliminarMaterial(id){
	var txt;
	var r = confirm("¿Desea borrar el material y su evolución?");
	if (r == true) {
		$("#formData").attr("action", "eliminarStockMateriales.action?idSeleccionado="+id).trigger("submit");
	}
	
}

function validaNumericos(event) {
    if(event.charCode >= 48 && event.charCode <= 57){
      return true;
     }
     return false;        
}

function cambiarElementosPorPagina(){
	var $dest = $("#displayTagDivStockMateriales");
	$.ajax({
		url:"navegarStockMateriales.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPagina").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los expedientes.');
	    }
	});
}