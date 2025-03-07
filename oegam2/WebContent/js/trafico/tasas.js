	/**
	 * Funciones JS relacionadas con Tasas
	 */
	
	var ventana_evolucion;
	var opciones_ventana = 'width=850,height=400,top=150,left=280';
	var opciones_ventana2 = 'width=850,height=450,top=050,left=200';
	var opciones_ventana3 = 'width=862,height=450,top=050,left=200';
	var mas_opciones = ",toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no";
	var opciones_popup = 'width=1250,height=300,top=150,left=50,scrollbars=Yes';
	var opciones_popup_direccion = 'width=850,height=500,top=150,left=50,scrollbars=Yes';
	
	function cargarListaCodigosTasa(select_tipoTasa,id_select_codigoTasa,id_codigoTasaSeleccionado){
			document.getElementById(id_codigoTasaSeleccionado).value = "-1";
			obtenerCodigosTasaPorTipoTasa(select_tipoTasa,document.getElementById(id_select_codigoTasa),id_codigoTasaSeleccionado);
	}
	
	//Recarga el combo cuando se recarga la pagina
	function recargarComboTasas(id_select_tipoTasa,id_select_codigoTasa,id_codigoTasaSeleccionado){
		selectTipoTasa = document.getElementById(id_select_tipoTasa);
		selectCodigoTasa = document.getElementById(id_select_codigoTasa);
		
		if(selectTipoTasa != null && selectTipoTasa.selectedIndex > 0){
			obtenerCodigosTasaPorTipoTasa(selectTipoTasa,selectCodigoTasa,id_codigoTasaSeleccionado);
		}
	}
	
	//Carga el codigo de tasa del hidden en el combo, si este no la contiene
	function anyadeTasaSiNoExiste(id_selectTasa, id_tasaSeleccionadaHidden){
		if(document.getElementById(id_tasaSeleccionadaHidden).value != "-1"){
			document.getElementById(id_selectTasa).value = document.getElementById(id_tasaSeleccionadaHidden).value;
			if(document.getElementById(id_selectTasa).selectedIndex <= 0){
				var selectCodigosTasa = document.getElementById(id_selectTasa);
				var codigoTasaSeleccionado = document.getElementById(id_tasaSeleccionadaHidden).value;
				selectCodigosTasa.options[selectCodigosTasa.options.length] = new Option(codigoTasaSeleccionado, codigoTasaSeleccionado, true, true);
			}
		}
	}
	
	//Generar la llamada Ajax para comentarios de una tasa
	function cargaComentariosTasa(codigoTasa){
		
		var url = "recuperarComentariosTasaTraficoAjax.action?codigoTasa=" + codigoTasa;
				
		var req_generico_comTasa = NuevoAjax();
		//Hace la llamada a Ajax
		req_generico_comTasa.onreadystatechange = function () {
			
			if (req_generico_comTasa.readyState == 4) { // Complete
				if (req_generico_comTasa.status == 200) { // OK response
					var respuesta = req_generico_comTasa.responseText;
					if(respuesta!=null && respuesta!='undefined' && respuesta!=''){
						alert(respuesta);
					}
				}
			}		
		};
		req_generico_comTasa.open("POST", url, true);
		req_generico_comTasa.send(null);
	}
	
	//Callback function de ObtenerCodigosTasaPorTipoTasa
	function rellenarListaCodigosTasa(req_generico_codigosTasa,selectCodigosTasa,id_codigoTasaSeleccionado){
		
		selectCodigosTasa.options.length = 0;
		
		if (req_generico_codigosTasa.readyState == 4) { // Complete
			if (req_generico_codigosTasa.status == 200) { // OK response
				textToSplit = req_generico_codigosTasa.responseText;
				
				//Split the document
				returnElements=textToSplit.split("||");
						
				selectCodigosTasa.options[0] = new Option("Seleccione c\u00f3digo de tasa", "");
				//Process each of the elements
				var codigoTasaSeleccionado = document.getElementById(id_codigoTasaSeleccionado).value;
				var existe = false;
				for ( var i=0; i<returnElements.length; i++ ){
					 value = returnElements[i].split(";");
					 if(!isNaN(value[0])){
						 selectCodigosTasa.options[i+1] = new Option(value[0], value[1]);
						 /* 
						  * La siguiente secuencia serviría para asociar un estilo concreto a una tasa dependiendo de un parámetro.
						  * Por ejemplo, para poner en rojo las importadas por un colegiado. Si son varios estilos, se le puede asociar un className
						  * directamente en vez de modificar repetidamente el style. (Ej: selectCodigosTasa.options[i+1].className = 'optionRoja';)
						  * 
						  * if(i == 0) {
						  * 	selectCodigosTasa.options[i+1].style.fontWeight = 'bold';
						  * 	selectCodigosTasa.options[i+1].style.color = '#ff0000';
						  * }
						  */
						 if(selectCodigosTasa.options[i+1].value == codigoTasaSeleccionado){
							 selectCodigosTasa.options[i+1].selected = true;
							 existe = true;
						 }
					 }
				}
				if(!existe && codigoTasaSeleccionado!="-1"){
					selectCodigosTasa.options[selectCodigosTasa.options.length] = new Option(codigoTasaSeleccionado, codigoTasaSeleccionado, true, true);
				}
			}
		}
	}
	
	function quitarTasasSiMasDeQuince(idAnyo,idMes,idDia) {
		
		if (tieneMenosDeQuinceById(idAnyo,idMes,idDia) ) {
			document.getElementById('codigoTasaPagoPresentacion').disabled=false;			
		} else if (document.getElementById("motivoBaja") != null && (document.getElementById("motivoBaja").value==7 || document.getElementById("motivoBaja").value==8)){
			document.getElementById('codigoTasaPagoPresentacion').disabled=true;
			document.getElementById('codigoTasaPagoPresentacion').selectedIndex=0;
		}else if (document.getElementById("motivoBaja") != null && document.getElementById("motivoBaja").value==5){
			document.getElementById('codigoTasaPagoPresentacion').disabled=true;
			document.getElementById('codigoTasaPagoPresentacion').selectedIndex=0;
		}
	}
	
	function tieneMenosDeQuince(anyo,mes,dia) {
		var a = parseFloat(anyo)+15;
		var m = parseFloat(mes)-1;
		var d = parseFloat(dia);
		var fechaMatri = new Date(a,m,d);
		var fechaActual = new Date();
		return fechaMatri>fechaActual;
	}
	
	function tieneMenosDeQuinceById(idAnyo,idMes,idDia) {
		var anyo = document.getElementById(idAnyo).value;
		var mes = document.getElementById(idMes).value;
		var dia = document.getElementById(idDia).value;
		if (anyo!="" && anyo!=null && mes!="" && mes!=null && dia!="" && dia!=null) return tieneMenosDeQuince(anyo,mes,dia);
		else return true;
	}
	
	function habilitarCodigoTasa(idTipoTasa,idCodigoTasa){
		if (document.getElementById(idTipoTasa).selectedIndex!= null && document.getElementById(idTipoTasa).selectedIndex == "0"){
			document.getElementById(idCodigoTasa).disabled=true;
		}else if(document.getElementById(idTipoTasa).selectedIndex == null){
			document.getElementById(idCodigoTasa).disabled=true;
		}else{
			document.getElementById(idCodigoTasa).disabled=false;
		}
		
	}
	
	function deshabilitarCodigoTasa(idCodigoTasa){
		document.getElementById(idCodigoTasa).disabled=true;
	}
	
	function validarCodigoTasa(){
		if (document.getElementById("codigoTasa").value == -1){
			alert ("Es obligatorio seleccionar una tasa");
			return false;
		}
		return true;
	}
	
	function generarCertificadosTasas(boton) {
		if (numChecked() == 0 && numCheckedPegatina()==0) {
			alert("Debe seleccionar alguna tasa");
			return false;
		}
	
		$('#formData').attr("action","generarCertificadosConsultaTasa.action");
		$('#formData').submit();
		return true;
	}
	
	//BUSCAR CONSULTAS DE TASAS
	function consultaTasaBuscar() {
		document.formData.action = "buscarConsultaTasa.action";
		document.formData.submit();
		return true;
	}
	
	//Generar la llamada Ajax para obtener codigos de tasa
	function obtenerCodigosTasaPorTipoTasa(selectTipoTasa,selectCodigosTasa,id_CodigoTasaSeleccionado){
		//Sin elementos
		if(selectTipoTasa.selectedIndex==0){
			selectCodigosTasa.length = 0;
			selectCodigosTasa.options[0] = new Option("Seleccione c\u00f3digo de tasa", "");
			return;
		}
		var selectedOption = selectTipoTasa.options[selectTipoTasa.selectedIndex].value;
		var idContrato = document.getElementById("idContratoTramite").value;
		var url = "recuperarCodigosTasaLibresPorTipoTasaTraficoAjax.action?tipoTasaSeleccionado="+selectedOption+"&idContrato="+idContrato;		
		var req_generico_codigosTasa = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_codigosTasa.onreadystatechange = function () { 
			rellenarListaCodigosTasa(req_generico_codigosTasa,selectCodigosTasa,id_CodigoTasaSeleccionado);
		}
		req_generico_codigosTasa.open("POST", url, true);			
		req_generico_codigosTasa.send(null);			
	}
	
	//Limpia los campos del formulario de consultaTasa.jsp
	function limpiarFormConsultaTasas() {
		$("#TipoTasa").val("");
		$("#CodigoTasa").val("");
		$("#idAsignada").val("");
		$("#idTipoTramite").val("");
		$("#NumeroExpediente").val("");
		$("#ReferenciaPropia").val("");
		$("#numColegiado").val("");
		$("#diaAltaIni").val("");
		$("#mesAltaIni").val("");
		$("#anioAltaIni").val("");
		$("#diaAltaFin").val("");
		$("#mesAltaFin").val("");
		$("#anioAltaFin").val("");
		$("#diaAsignacionIni").val("");
		$("#mesAsignacionIni").val("");
		$("#anioAsignacionIni").val("");
		$("#diaAsignacionFin").val("");
		$("#mesAsignacionFin").val("");
		$("#anioAsignacionFin").val("");
//		$("#diaFinVigencia").val("");
//		$("#mesFinVigencia").val("");
		$("#anioFinVigencia").val("");
		$("#idFormato").val("");
		$("#idEstadoTasaBloqueo").val("");
		$("#idFormato").val("");
		$("#esImportColegio").val("");
		$("#idTipoAlmacenTasa").val("");
	}
	
	//metodo que abrira un popup con la evolucion del vehiculo
	function abrirEvolucionTasa(codTasa) {
	
		if (codTasa != null && codTasa != "") {
			var ventana_evolucion = window.open('evolucionTasa.action?codTasa='
					+ codTasa, 'popup', opciones_ventana3);
		} else
			alert('Codigo de tasa no valido');
	}
	
	function descargarCertificadoTasas() {
		$('#formData').attr("action","descargarCertificadoConsultaTasa.action");
		$('#formData').submit();
	}
	
	function mostrarBloqueValidados(){
		$("#bloqueFallidos").hide();
		if($("#bloqueValidados").is(":visible")){
			$("#bloqueValidados").hide();
			$("#despValidado").attr("src","img/plus.gif");
			$("#despValidado").attr("alt","Mostrar");
		}else{
			$("#bloqueValidados").show();	
			$("#despValidado").attr("src","img/minus.gif");
			$("#despValidado").attr("alt","Ocultar");
		}
		$("#despFallidos").attr("src","img/plus.gif");
	}

	function mostrarBloqueFallidos(){
		$("#bloqueValidados").hide();
		if($("#bloqueFallidos").is(":visible")){
			$("#bloqueFallidos").hide();
			$("#despFallidos").attr("src","img/plus.gif");
			$("#despFallidos").attr("alt","Mostrar");
		}else{
			$("#bloqueFallidos").show();	
			$("#despFallidos").attr("src","img/minus.gif");
			$("#despFallidos").attr("alt","Ocultar");
			
		}
		$("#despValidado").attr("src","img/plus.gif");
	}
	
	function generarPopPupMotivoTasa(dest, codigos) {
		var $dest = $("#" + dest);
		$.post("cargarPopUpMotivoConsultaTasa.action?accion=bloquearTasa",
			function(data) {
				if (data.toLowerCase().indexOf("<html") < 0) {
					$dest.empty().append(data).dialog(
						{
							modal : true,
							overflow : 'hidden',
							show : {
								effect : "blind",
								duration : 300
							},
							dialogClass : 'no-close',
							width : 500,
							height : 300,
							buttons : {
								Continuar : function() {
												limpiarHiddenInput("motivo");
												input = $("<input>").attr("type","hidden").attr("name","motivo").val($("#idMotivo")	.val());
												$('#formData').append($(input));
												limpiarHiddenInput("idCodigoTasaSeleccion");
												input = $("<input>").attr("type","hidden").attr("name","idCodigoTasaSeleccion").val(codigos);
												$('#formData').append($(input));
												var action = "bloquearTasaConsultaTasa";
												$("#formData").attr("action",action).trigger("submit");
											},
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
	
	function almacenTasas(){
		var valueBoton = $("#bAlmacenTasa").val();
		var listaCheck = $("#listaChecksConsultaTasa").val();
		var listaCheckPega = $("#listaChecksConsultaTasaPegatina").val();
		mostrarLoading("bAlmacenTasa");
		var codigos = "";
		$("input[name='listaChecksConsultaTasa']:checked").each(function() {
			if(this.checked){
				if(codigos==""){
					codigos += this.value;
				}else{
					codigos += "-" + this.value;
				}
			}
		});
		if(codigos == ""){
			ocultarLoading('bAlmacenTasa',valueBoton);
			return alert("Debe seleccionar alguna tasa de tipo electrónico para incluirla en un almacén.");
		}
		var $dest = $("#divEmegergenteTasas");
		$.post("cargarPopUpCambioAlmacenConsultaTasa.action", function(data) {
			if (data.toLowerCase().indexOf("<html")<0) {
				$dest.empty().append(data).dialog({
					modal : true,
					show : {
						effect : "blind",
						duration : 300
					},
					dialogClass : 'no-close',
					width: 350,
					buttons : {
						Guardar : function() {
							var almacenNuevo = $("#idAlmacenNuevo").val();
							if(almacenNuevo == ""){
								alert("Seleccione un almacén");
								return false;
							}
							$("#divEmegergenteTasas").dialog("close");
							limpiarHiddenInput("almacenNuevo");
							input = $("<input>").attr("type", "hidden").attr("name", "almacenNuevo").val(almacenNuevo);
							$('#formData').append($(input));
							limpiarHiddenInput("codSeleccionados");
							input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
							$('#formData').append($(input));
							$("#formData").attr("action", "cambiarAlmacenConsultaTasa.action").trigger("submit");
						},
						Cerrar : function() {
							ocultarLoading('bAlmacenTasa',valueBoton);
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
	
	function mostrarLoading(boton){
		bloqueaBotonesTasa();
		document.getElementById("bloqueLoadingConsultaTasa").style.display = "block";
		$('#'+boton).val("Procesando..");
	}

	function ocultarLoading(boton, value) {
		document.getElementById("bloqueLoadingConsultaTasa").style.display = "none";
		$('#'+boton).val(value);
		desbloqueaBotonesTasa();
	}
	function bloqueaBotonesTasa() {	
		$('#bAlmacenTasa').prop('disabled', true);
		$('#bAlmacenTasa').css('color', '#6E6E6E');	
	}
	function desbloqueaBotonesTasa() {	
		$('#bAlmacenTasa').prop('disabled', false);
		$('#bAlmacenTasa').css('color', '#000000');	
	}
	function limpiarHiddenInput(nombre){
		if ($("input[name='"+nombre+"']").is(':hidden')){
			$("input[name='"+nombre+"']").remove();
		}
	}