function actualizarPadre(){
	
	var tipoModelo = document.formData.tipoModelo.value; 	
	
	var descripcionDerechoReal = document.formData.descripcionDerechoReal.value;
	var numAnio =  document.formData.numAnio.value;
	var duracionDerechoReal;
	var anioFechaUsufructo = document.formData.anioAltaIniUsufructo.value;
	
	duracionDerechoReal = document.formData.duracionDerechoReal.value;
	
	
	if (descripcionDerechoReal != "OTROS"){
		if (duracionDerechoReal == "TEMPORAL"){
			if (numAnio == ""){
				alert("Para derechos reales temporales ha de indicar el 'número de años'");
				return;
			}
		}else if (duracionDerechoReal == "VITALICIO"){
			if (anioFechaUsufructo == ""){
				alert("Para derechos reales vitalicios ha de indicar la 'Fecha de nacimiento del usufructuario'");
				return;
			}
		}else{
			alert("Seleccione duración de Derecho Real");
			return;
		}
	}
	
	if (tipoModelo == "Modelo"){
		var anioDevengo = document.formData.fechaDevengoanioAltaIni.value;
		var valorDeclarado = document.formData.modeloValorDeclarado.value;
		valorDeclarado=valorDeclarado.replace('.','');
		valorDeclarado=valorDeclarado.replace(',','.');
		
		var usufructo = calculaUsufructo(duracionDerechoReal, numAnio, anioFechaUsufructo, anioDevengo);
		var baseImponible = calculaBaseImponible(descripcionDerechoReal, usufructo, valorDeclarado);	
	}
}

function calculaUsufructo(duracionDerechoReal, numAnio, anioNacUsu, anioDevengo){
	var usufructo = 0;
	var edad = 0;
	
	if (duracionDerechoReal == "TEMPORAL") {
		usufructo = 2 * numAnio;
	}else{
		edad = anioNacUsu - anioDevengo;
		usufructo = 89 - edad;
		if (usufructo > 70){
			usufructo = 70;
		}
	}
	
	return usufructo;
	
}

function calculaBaseImponible(descripcionDerechoReal, usufructo, valorDeclarado){
	
	var baseImponible = 0;
	
	if (descripcionDerechoReal == "USUFRUCTO"){
		baseImponible = parseFloat(valorDeclarado) * parseFloat(usufructo) / 100;
	}else if (descripcionDerechoReal == "NUDA PROPIEDAD"){
		baseImponible = parseFloat(valorDeclarado) * (1- parseFloat(usufructo) / 100);
	}else if (descripcionDerechoReal == "USO O HABITACION"){
		baseImponible = parseFloat(valorDeclarado) * 0.75 * parseFloat(usufructo) / 100;
	}
	
	return baseImponible;

}

function cerrarVentana(){
	
	window.close();
	
}

function get_radio_value(radio)
{
	for (var i=0; i < document.formData.radio.length; i++)
   	{
   		if (document.formData.radio[i].checked)
   		{
      		var rad_val = document.formData.radio[i].value;
      		alert(rad_val);
     	}
   }
}


function seleccionadoTemporalVitalicio(){	

	
	if (document.getElementById('duracionDerechoReal').value == 'TEMPORAL'){
		document.getElementById('diaAltaIniUsufructo').value = '';
		document.getElementById('mesAltaIniUsufructo').value = '';
		document.getElementById('anioAltaIniUsufructo').value = '';
		document.getElementById('diaAltaIniUsufructo').disabled = true;
		document.getElementById('mesAltaIniUsufructo').disabled = true;
		document.getElementById('anioAltaIniUsufructo').disabled = true;
		document.getElementById('botonFechaDR').style.display="none";
		document.getElementById('botonFechaDR').visible = false;
		document.getElementById('numAnio').readOnly = false;	
		document.getElementById('numAnio').disabled = false;
		
	}else if (document.getElementById('duracionDerechoReal').value == 'VITALICIO'){
		document.getElementById('numAnio').value = '';
		document.getElementById('numAnio').readOnly = true;
		document.getElementById('numAnio').disabled = true;
		document.getElementById('diaAltaIniUsufructo').disabled = false;
		document.getElementById('mesAltaIniUsufructo').disabled = false;
		document.getElementById('anioAltaIniUsufructo').disabled = false;
		document.getElementById('botonFechaDR').style.display="inline";
		document.getElementById('botonFechaDR').visible = true;
	}else{
		document.getElementById('diaAltaIniUsufructo').value = '';
		document.getElementById('mesAltaIniUsufructo').value = '';
		document.getElementById('anioAltaIniUsufructo').value = '';
		document.getElementById('diaAltaIniUsufructo').disabled = true;
		document.getElementById('mesAltaIniUsufructo').disabled = true;
		document.getElementById('anioAltaIniUsufructo').disabled = true;
		document.getElementById('numAnio').value = '';
		document.getElementById('numAnio').readOnly = true;
		document.getElementById('numAnio').disabled = true;
	}
	
}

function comprobarDerechoReal(){
	
	if (document.formData.descripcionDerechoReal.value == "OTROS"){
		document.getElementById('diaAltaIniUsufructo').disabled = true;
		document.getElementById('diaAltaIniUsufructo').value = "";
		document.getElementById('mesAltaIniUsufructo').disabled = true;
		document.getElementById('mesAltaIniUsufructo').value = "";
		document.getElementById('anioAltaIniUsufructo').disabled = true;
		document.getElementById('anioAltaIniUsufructo').value = "";
		document.getElementById('botonFechaDR').style.display="none";
		document.getElementById('botonFechaDR').visible = false;
		document.getElementById('duracionDerechoReal').disabled=true;
		document.getElementById('numAnio').readOnly = true;
		document.getElementById('numAnio').disabled = true;
		document.getElementById('numAnio').value = "";
	}else{
		document.getElementById('diaAltaIniUsufructo').disabled = true;
		document.getElementById('diaAltaIniUsufructo').value = "";
		document.getElementById('mesAltaIniUsufructo').disabled = true;
		document.getElementById('mesAltaIniUsufructo').value = "";
		document.getElementById('anioAltaIniUsufructo').disabled = true;
		document.getElementById('anioAltaIniUsufructo').value = "";
		document.getElementById('botonFechaDR').style.display="none";
		document.getElementById('botonFechaDR').visible = false;
		document.getElementById('duracionDerechoReal').disabled=false;
		document.getElementById('numAnio').readOnly = true;
		document.getElementById('numAnio').disabled = true;
		document.getElementById('numAnio').value = "";
	}
	
	
}
function rellenarDerechoReal(){
 	
	document.formData.action="rellenarDerechoReal";
	document.formData.submit();
	
}
function validarNumeros(e) {
    var tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true; 
    var patron = /[0-9]+/;  
    var te = String.fromCharCode(tecla);
    return patron.test(te); 
}