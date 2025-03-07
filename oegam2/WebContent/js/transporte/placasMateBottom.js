$(document).ready(function(){
	if($('#mensajesInfo').find('li').length > 0 || $('#mensajesError').find('li').length > 0){
		$('#divError').css('display', 'block');
	}
});

function eliminarYrefrescar(numExpediente){

	var numsExpedientes = $('#numsExpedientes').val().split("-");
	var i = 0;
	$('.contenedorSolicitud').each(function(){
		for(var j=0; j < numsExpedientes.length; j++){
			if($('#numExpediente_' + i).val()==numsExpedientes[j] && numExpediente==numsExpedientes[j]){
				numsExpedientes.splice(j, 1);
				j--;
			}
		}
		i++;
	});

	$('#numsExpedientes').val(numsExpedientes.join("-"));

	recargar();
	
}

function configurarPlacas(modo){

	var numsExpedientes = $('#numsExpedientes').val().split("-");
	var mensaje = "";
	if(modo=='false'){
		mensaje = "\u00bfEst\u00e1 seguro de que quiere configurar individualmente cada placa?";
	} else {
		mensaje = "\u00bfEst\u00e1 seguro de querer aplicar la misma configuraci\u00f3n a todas las placas (se perder\u00e1n los datos introducidos hasta ahora)?";
	}
	
	// Si se confirma hacemos los cambios
	if(confirm(mensaje)){
		if(modo=='false'){
			$('#contenedorSolicitudes_0 .tituloSolicitud').html("Matricula " + $('#matricula_0').val());
			// Mostramos todos
			for(var i = 0; i <= numsExpedientes.length; i++){
				$('#contenedorSolicitudes_' + i).css("display", "block");
				if ($('#tipoPlacaAdicional_' + i).val()!=null && $('#tipoPlacaAdicional_' + i).val()!=""){
					$('#contenedorSolicitudAdicional_'+ i).css("display", "block");
					$('#idAdicionalSi_' + i).attr('checked','checked');
				}
			}
		} else {
			$('#contenedorSolicitudes_0 .tituloSolicitud').html("Configuraci&oacute;n de tipos de placa com&uacute;n a todas las matr&iacute;culas");
			// Ocultamos todos menos el primero, y les aplicamos los tipos de matricula del primero
			for(var i = 1; i <= numsExpedientes.length; i++){
				$('#contenedorSolicitudes_' + i).css("display", "none");
				$('#tipoPlacaTrasera_' + i).val($('#tipoPlacaTrasera_0').val());
				$('#tipoPlacaDelantera_' + i).val($('#tipoPlacaDelantera_0').val());
				$('#tipoPlacaAdicional_' + i).val($('#tipoPlacaAdicional_0').val());
			}
		}
	// Si no, devolvemos el formulario al estado inicial
	} else {
		if(modo=='true'){
			$('#aplicarTodasFalse').attr('checked','checked');
		} else {
			$('#aplicarTodasTrue').attr('checked','checked');
		}
	}

}

function recargar(){

	var numsExpedientes = $('#numsExpedientes').val();
	
	// Comienza el ajax
	var url = "refrescarSolicitudPlacas.action?numsExpedientes=" + numsExpedientes;
	
	var req_monitor = NuevoAjax();
	//Hace la llamada a Ajax
	req_monitor.onreadystatechange = function () {

		if (req_monitor.readyState == 1) { // Complete
			$('#contenedorGeneralSolicitudes').css({
				'text-align': 'center',
				'margin-top': '3em'
			});
			$('#contenedorGeneralSolicitudes').html('<div class="gaLoading"><div class="gaLoadingLogo" style="height:130px; overflow:hidden;"><img src="/oegam2/img/preloader.gif" alt="Ilustre Colegio Oficial de Gestores Administrativos de Madrid" /></div><div id="loadingMsg"><p>Cargando datos...<br /><br /><span>Por favor, espere.</span></p></div></div>');
		}
		if (req_monitor.readyState == 4) { // Complete
			if (req_monitor.status == 200) { // OK response
				$('#loadingMsg').html('<p>Renderizando datos...<br /><br /><span>Por favor, espere.</span></p>');
				setTimeout(function(){
					$('#contenedorGeneralSolicitudes').css({
						'text-align': 'left',
						'margin-top': '3em'
					});
					$('#contenedorGeneralSolicitudes').html(req_monitor.responseText);
					for(var i = 0; i <= document.getElementById('noElementos').value; i++){
						var matriculaPreview = document.getElementById('matriculaPreview_' + i).value;
						var tipoPlacaDelantera = document.getElementById('tipoPlacaDelantera_' + i).value;
						var tipoPlacaAdicional = document.getElementById('tipoPlacaAdicional_' + i).value;
						var tipoPlacaTrasera = document.getElementById('tipoPlacaTrasera_' + i).value;
						dibujarMatricula(i, matriculaPreview, tipoPlacaDelantera, 'delantera');
						dibujarMatricula(i, matriculaPreview, tipoPlacaTrasera, 'trasera');
					}
				}, 1000);
			}
		}
		
	}
	req_monitor.open("POST", url, true);
	req_monitor.send(null);
}

function showAdicional(cont, mostrar){
	if(mostrar){
		$('#contenedorSolicitudAdicional_' + cont).css('display', 'block');
		calculoCreditosPlacas();
	} else {
		$('#contenedorSolicitudAdicional_' + cont).css('display', 'none');
		vaciarComboPlaca('#tipoPlacaAdicional_' + cont, "");
		calculoCreditosPlacas();
	}
}

function vaciarComboPlaca(idCombo, valorDefecto){
	var option = idCombo + ' option[value="' + valorDefecto + '"]';
	$(option).prop('selected', true);
}

function dibujarMatriculas(){
	var numElementos = document.getElementById('noElementos').value;
	for(var i = 0; i <= numElementos; i++){
		var matriculaPreview = document.getElementById('matriculaPreview_' + i).value;
		var tipoPlacaDelantera = document.getElementById('tipoPlacaDelantera_' + i).value;
		dibujarMatricula(i, matriculaPreview, tipoPlacaDelantera, 'delantera');
		
		if (document.getElementById('tipoPlacaTrasera_' + i)!=null){
			var tipoPlacaTrasera = document.getElementById('tipoPlacaTrasera_' + i).value;
			dibujarMatricula(i, matriculaPreview, tipoPlacaTrasera, 'trasera');
		}
	}
}

function habilitarAdicionales(){
	var numElementos = document.getElementById('noElementos').value;
	var tipoPlacaAdicional = null;
	for(var i = 0; i <= numElementos; i++){
		if (document.getElementById('tipoPlacaAdicional_' + i)!=null && document.getElementById('tipoPlacaAdicional_' + i)!='undefined'){
			tipoPlacaAdicional = document.getElementById('tipoPlacaAdicional_' + i).value;
		}
		if(tipoPlacaAdicional && tipoPlacaAdicional!=null && tipoPlacaAdicional!='undefined' && tipoPlacaAdicional!=''){
			$('#idAdicionalSi_' + i).prop('checked', true);
			showAdicional(i, true);
		}
	}
}

function habilitarDuplicado(){
	var numElementos = document.getElementById('noElementos').value;
	for(var i = 0; i <= numElementos; i++){
		var placaDuplicada = document.getElementById('hiddenDuplicada_' + i).value;
		if(placaDuplicada && placaDuplicada!=null && placaDuplicada!='undefined' && placaDuplicada!='' && placaDuplicada == "true"){
			$('#idDuplicadaSi_' + i).attr('checked', true);
		}else{
			$('#idDuplicadaNo_' + i).attr('checked', true);
		}
	}
}

function calculoCreditosPlacas(){

	var creditos = document.getElementById("numCreditosSolicitados");
	var numCreditos = 0;
	
	var numElementos = document.getElementById('noElementos').value;
	var mismaConfiguracion = false;
	if ($('#aplicarTodasTrue').prop('checked')){
		mismaConfiguracion = true; 
	}
	for(var i = 0; i <= numElementos; i++){
		var tipoPlacaAdicional = false;
		var tipoPlacaTrasera = false;
		
		var tipoPlacaDelantera = document.getElementById('tipoPlacaDelantera_' + i).value;
		if (document.getElementById('tipoPlacaTrasera_' + i)!=null){
			tipoPlacaTrasera = document.getElementById('tipoPlacaTrasera_' + i).value;
		}
		if (document.getElementById('tipoPlacaAdicional_' + i)!=null){
			tipoPlacaAdicional = document.getElementById('tipoPlacaAdicional_' + i).value;
		}
		if(tipoPlacaDelantera && tipoPlacaDelantera!=null && tipoPlacaDelantera!='undefined' && tipoPlacaDelantera!=''){
			numCreditos++;
		}

		if(tipoPlacaTrasera && tipoPlacaTrasera!=null && tipoPlacaTrasera!='undefined' && tipoPlacaDelantera!=''){
			numCreditos++;
		}
		
		if(tipoPlacaAdicional && tipoPlacaAdicional!=null && tipoPlacaAdicional!='undefined' && tipoPlacaAdicional!=''){
			numCreditos++;
		}
		
		if (mismaConfiguracion){
			numCreditos = parseInt(numCreditos) * (parseInt(numElementos) + 1);
			break;
		}
	}
	
	creditos.value = parseInt(numCreditos);
	
}