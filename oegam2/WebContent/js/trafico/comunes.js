/**
 * Funciones comunes del modulo de Trafico
 */

var ventana_evolucion;
var opciones_ventana = 'width=850,height=400,top=150,left=280';
var opciones_ventana2 = 'width=850,height=450,top=050,left=200';
var opciones_ventana3 = 'width=862,height=450,top=050,left=200';
var mas_opciones = ",toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no";
var opciones_popup = 'width=1250,height=300,top=150,left=50,scrollbars=Yes';
var opciones_popup_direccion = 'width=850,height=500,top=150,left=50,scrollbars=Yes';

var req_generico_calles;

var urlMunicipios = "recuperarMunicipiosPorProvinciaTraficoAjax.action?provinciaSeleccionada=";
var urlColegios = "recuperarColegiosPorProvinciaTraficoAjax.action?provinciaSeleccionada=";
var urlJefaturas = "recuperarJefaturasPorProvinciaTraficoAjax.action?provinciaSeleccionada=";
var urlTipoVia = "recuperarTipoViaPorProvinciaTraficoAjax.action?provinciaSeleccionada=";

var urlCalculoItv = "calcularFechaCaducidadItvTraficoAjax.action?numExpediente=";

var urlNuevoHonorario = "nuevoHonorarioFacturacionAjax.action?honorarioTodo=";
var urlNuevoSuplido = "nuevoSuplidoFacturacionAjax.action?suplidoTodo=";
var urlNuevoGasto = "nuevoGastoFacturacionAjax.action?gastoTodo=";

var ventanaOpciones = null;

// Funcion comun llamada desde varios jsp
function NuevoAjax(){
	var xmlhttp=false;
	try{
		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	}catch(e){
		try{
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}catch(E){
			xmlhttp = false;
		}
	}

	if(!xmlhttp && typeof XMLHttpRequest!='undefined'){
		xmlhttp = new XMLHttpRequest();
	}
	return xmlhttp;
}

function numCheckedImprimir(){
	var checks = document.getElementsByName("listaExpedientes");
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function cerrar() {
	this.close();
}

function cerrarEvolucionTramite() {
	this.close();
}

function cerrarEvolucionJustificante() {
	this.close();
}

function habilitarAutonomo(idAutonomo, idIAE){
	var check = document.getElementById(idAutonomo).checked &&
				!document.getElementById(idAutonomo).disabled;
	if (check) {
		document.getElementById(idIAE).disabled = false;
	} else {
		document.getElementById(idIAE).disabled = true;
		document.getElementById(idIAE).value = '-1';
	}
}

/**
 * Método para habilitar/deshabilitar los campos del documento alternativo;
 * */
function habilitarDocumentoAlternativo(idCheckDocAlternativo, idDocAlternativo, diaCadAlternativo, mesCadAlternativo, anioCadAlternativo, idFecha, idIndefinido){

	var check = document.getElementById(idCheckDocAlternativo).checked &&
				!document.getElementById(idCheckDocAlternativo).disabled;
	var checkIndefinido = document.getElementById(idIndefinido).checked;
	if (check) {
		if(!checkIndefinido){
			document.getElementById(idDocAlternativo).disabled = false;
			document.getElementById(diaCadAlternativo).disabled = false;
			document.getElementById(mesCadAlternativo).disabled = false;
			document.getElementById(anioCadAlternativo).disabled = false;
			document.getElementById(idFecha).style.display="block";
		}else{
			document.getElementById(idDocAlternativo).disabled = false;
		}
	} else {
		document.getElementById(idDocAlternativo).disabled = true;
		document.getElementById(idDocAlternativo).value = '-1';
		document.getElementById(diaCadAlternativo).value ="";
		document.getElementById(mesCadAlternativo).value ="";
		document.getElementById(anioCadAlternativo).value ="";
		document.getElementById(diaCadAlternativo).disabled = true;
		document.getElementById(mesCadAlternativo).disabled = true;
		document.getElementById(anioCadAlternativo).disabled = true;
		document.getElementById(idFecha).style.display="none";
	}
}

/**
 * Método para comprobar que la fecha del documento es indefinida y deshabilitar los demás.
 * */
function documentoIndefinido(idCheckIndefinido, idCheckAlternativo, idDocAlternativo, idDiaDni, idMesDni, idAnioDni, idDiaAlter, idMesAlter, idAnioAlter, idFecha, idFechaAlter){
	var check = document.getElementById(idCheckIndefinido).checked;
	var checkAlter = document.getElementById(idCheckAlternativo).checked;
	if(check){
		document.getElementById(idDiaAlter).disabled=true;
		document.getElementById(idMesAlter).disabled=true;
		document.getElementById(idAnioAlter).disabled=true;
		document.getElementById(idDiaDni).disabled=true;
		document.getElementById(idMesDni).disabled=true;
		document.getElementById(idAnioDni).disabled=true;
		document.getElementById(idFecha).style.display="none";
		if(checkAlter){
			document.getElementById(idDocAlternativo).disabled=false;
			document.getElementById(idFechaAlter).style.display="none";
		}
	}else{
		document.getElementById(idDiaDni).disabled=false;
		document.getElementById(idMesDni).disabled=false;
		document.getElementById(idAnioDni).disabled=false;
		document.getElementById(idFecha).style.display="block";
		if(checkAlter){
			document.getElementById(idDiaAlter).disabled = false;
			document.getElementById(idMesAlter).disabled = false;
			document.getElementById(idAnioAlter).disabled = false;
			document.getElementById(idDocAlternativo).disabled=false;
			document.getElementById(idFechaAlter).style.display="block";
		}
	}
}

//Recibe el elemento select de las provincias, el id del select de municipios a cargar y el id oculto que guarda el municipio seleccionado
//Es la función que hay que llamar desde el onchange del select de la provincia
function cargarListaMunicipios(select_provincia,id_select_municipio,id_municipioSeleccionado){
	document.getElementById(id_municipioSeleccionado).value = "-1";
	obtenerMunicipiosPorProvincia(select_provincia,document.getElementById(id_select_municipio),id_municipioSeleccionado);
}

//Recibe el elemento select de las provincias, el id del select de tipos de via a cargar y el id oculto que guarda el tipo de via seleccionado
//Es la función que hay que llamar desde el onchange del select de la provincia
function cargarListaTipoVia(select_provincia,id_select_tipoVia,id_tipoViaSeleccionado){
	document.getElementById(id_select_tipoVia).value = "-1";
	obtenerTipoViaPorProvincia(select_provincia,document.getElementById(id_select_tipoVia),id_tipoViaSeleccionado);
}

//Recibe el elemento select de las provincias, el id del select de colegios a cargar y el id oculto que guarda el colegio seleccionado
//Es la función que hay que llamar desde el onchange del select de la provincia
function cargarListaColegios(select_provincia,id_select_colegio,id_colegioSeleccionado){
	document.getElementById(id_colegioSeleccionado).value = "-1";
	obtenerColegiosPorProvincia(select_provincia,document.getElementById(id_select_colegio),id_colegioSeleccionado);
}

//Recibe el elemento select de las provincias, el id del select de jefaturas a cargar y el id oculto que guarda la jefatura seleccionada
//Es la función que hay que llamar desde el onchange del select de la provincia
function cargarListaJefaturas(select_provincia,id_select_jefatura,id_jefaturaSeleccionado){
	document.getElementById(id_jefaturaSeleccionado).value = "-1";
	obtenerJefaturasPorProvincia(select_provincia,document.getElementById(id_select_jefatura),id_jefaturaSeleccionado);
}

function cargarListaPueblos(select_provincia,select_municipio, id_select_pueblo, id_puebloSeleccionado){
	document.getElementById(id_puebloSeleccionado).value="";
	obtenerPueblosPorMunicipio(document.getElementById(select_provincia),select_municipio, document.getElementById(id_select_pueblo), id_puebloSeleccionado); 
}

function cargarListaPueblosDGT(select_provincia,select_municipio, id_select_pueblo, id_puebloSeleccionado){
	document.getElementById(id_puebloSeleccionado).value="";
	obtenerPueblosDGTPorMunicipio(document.getElementById(select_provincia),select_municipio, document.getElementById(id_select_pueblo), id_puebloSeleccionado); 
}

function cargarListaNombresVia(select_provincia,select_municipio, select_tipoVia, id_select_nombreVia, via){
	document.getElementById(id_select_nombreVia).value="";
	via.processor.setWords([]);
	obtenerNombreViasPorProvinciaMunicipioYTipoVia(document.getElementById(select_provincia),select_municipio, select_tipoVia,document.getElementById(id_select_nombreVia),via); 
}

function cargarListaModelos(select_marca, id_select_tipoVehiculo, id_anioDevengo, id_select_modelo, id_modeloSeleccionado){
	document.getElementById(id_modeloSeleccionado).value="-1";
	var marca = document.getElementById('idMarcaCam').value;
	if(document.getElementById(id_select_tipoVehiculo).value != "-1" &&
		document.getElementById(id_anioDevengo).value != "" && marca != ""){ //Si el tipo de vehiculo o el año de devengo no están rellenos, no se realiza la llama Ajax
		obtenerModelosPorMarca(select_marca, document.getElementById(id_select_tipoVehiculo), id_anioDevengo, document.getElementById(id_select_modelo), id_modeloSeleccionado); 
	} else {
		document.getElementById(id_select_modelo).length = 0;
		document.getElementById(id_select_modelo).options[0] = new Option("Seleccione Modelo", "");
	}
}

function getEstacionesItvDeProvincia(select_provincia, id_select_estacionItv, idEstacionItvSeleccionada){
	document.getElementById(id_select_estacionItv).value = null;
	obtenerEstacionesItvDeProvincia(select_provincia,document.getElementById(id_select_estacionItv),idEstacionItvSeleccionada);
}

function sitexComprobarCTITPrevio(matricula){
	var url = "sitexComprobarCTITPrevioTraficoAjax.action?matricula="+matricula;

	var req_sitex = NuevoAjax();
	//Hace la llamada a Ajax
	req_sitex.onreadystatechange = function() {
		if (req_sitex.readyState == 4) { // Complete
			if (req_sitex.status == 200) { // OK response
				var responseText = req_sitex.responseText;

				if(responseText == 'OK'){
					document.getElementById("duplicadoImpresoPermiso").checked = true;
					document.getElementById("tasaDuplicado").disabled = true;
					document.getElementById("mensajeMotivo").style.display = 'block';
					document.getElementById("mensajeMotivo").innerHTML = '<strong style="padding:1em 0; color:#007f0e; font-size:1.1em;">Procede de un cambio de titularidad telem&aacute;tico y por ello no requiere de una tasa de duplicado para expedir el permiso de circulaci&oacute;n</strong>';
				} else {
					document.getElementById("mensajeMotivo").style.display = 'block';
					document.getElementById("mensajeMotivo").innerHTML = '<strong style="padding:1em 0; color:#ff000e; font-size:1.1em;">Bajas de exportaci&oacute;n y tr&aacute;nsito posteriores a CTIT, requieren de un tr&aacute;mite de transmisi&oacute;n electr&oacute;nica previo.</strong>';
				}

			}
		}
	}
	req_sitex.open("POST", url, true);
	req_sitex.send(null);
}

//Generar la llamada Ajax para obtener las estaciones ITV dependiendo de la provincia
function obtenerEstacionesItvDeProvincia(selectProvincia, selectEstacionItv,idEstacionItvSeleccionada){
	//Sin elementos
	if(selectProvincia.selectedIndex==0){
		selectEstacionItv.length = 0;
		selectEstacionItv.options[0] = new Option("Seleccione Estacion ITV", "");
		return;
	}
	selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
	url="obtenerEstacionesItvDeProvinciaTraficoAjax.action?provinciaSeleccionada="+selectedOption;
	var req_generico_estacionItv = NuevoAjax();
	//Hace la llamada a ajax
	req_generico_estacionItv.onreadystatechange = function() {
		rellenarEstacionesItvDeProvincia(req_generico_estacionItv, selectEstacionItv,idEstacionItvSeleccionada);
	}
	req_generico_estacionItv.open("POST", url, true);
	req_generico_estacionItv.send(null);
}

	//Callback function de obtenerEstacionesItvDeProvincia
	function rellenarEstacionesItvDeProvincia(req_generico_estacionItv, selectEstacionItv, idEstacionItvSeleccionada){

		selectEstacionItv.options.length = 0;

		if (req_generico_estacionItv.readyState == 4) { // Complete
		if (req_generico_estacionItv.status == 200) { // OK response
			textToSplit = req_generico_estacionItv.responseText;

			//Split the document
			returnElements=textToSplit.split("||");
			selectEstacionItv.options[0] = new Option("Seleccione Estacion ITV", "");
			var estacionSeleccionada = document.getElementById(idEstacionItvSeleccionada).value;
			for (var i=0; i<returnElements.length; i++ ){
				value = returnElements[i].split(";");
					selectEstacionItv.options[i+1] = new Option(value[1], value[0]);
					if(selectEstacionItv.options[i+1].value == estacionSeleccionada){
						selectEstacionItv.options[i+1].selected = true;
					}
				}
			}
		}
	}

	function seleccionadaEstacionItv(selectEscacionItv){
		if(selectEscacionItv.selectedIndex != 0){
			var indice = selectEscacionItv.selectedIndex;
			var valor = selectEscacionItv.options[indice].value;
			document.getElementById("idEstacionItvSeleccionada").value = valor;
			return;
		}
	}

	//Generar la llamada Ajax para obtener municipios
	function obtenerTipoViaPorProvincia(selectProvincia,selectTipoVia,id_tipoViaSeleccionada){
		//Sin elementos
		if(selectProvincia.selectedIndex==0){
			selectTipoVia.length = 0;
			selectTipoVia.options[0] = new Option("Seleccione Tipo Via", "");
			return;
		}
		selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
		url=urlTipoVia+selectedOption;
		var req_generico_tipo_via = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_tipo_via.onreadystatechange = function() {
			rellenarListaTipoVia(req_generico_tipo_via,selectTipoVia,id_tipoViaSeleccionada);
		};
		req_generico_tipo_via.open("POST", url, true);
		req_generico_tipo_via.send(null);
	}

	//Generar la llamada Ajax para obtener municipios
	function obtenerMunicipiosPorProvincia(selectProvincia,selectMunicipio,id_municipioSeleccionado){
		//Sin elementos
		if(selectProvincia.selectedIndex==0){
			selectMunicipio.length = 0;
			selectMunicipio.options[0] = new Option("Seleccione Municipio", "");
			return;
		}
		selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
		url=urlMunicipios+selectedOption;
		var req_generico_municipio = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_municipio.onreadystatechange = function() {
			rellenarListaMunicipios(req_generico_municipio,selectMunicipio,id_municipioSeleccionado);
		};
		req_generico_municipio.open("POST", url, true);
			req_generico_municipio.send(null);
		}

		//Generar la llamada Ajax para obtener municipios
		function obtenerPueblosPorMunicipio(selectProvincia, hiddenMunicipio, selectPueblo,id_PuebloSeleccionado){
			//Sin elementos
		if(hiddenMunicipio.value==0){
			selectPueblo.length = 0;
			selectPueblo.options[0] = new Option("Seleccione Pueblo", "");
			return;
		}
		if (selectProvincia.selectedIndex==0){
			selectIndex.length=0;
			selectProvincia.options[0]= new Option("Seleccione Provincia","");
			return;
		}
		municipioSeleccionado = hiddenMunicipio.value;
		provinciaSeleccionado = selectProvincia.options[selectProvincia.selectedIndex].value;

		var urlPueblo = "recuperarPueblosPorMunicipioTraficoAjax.action?municipioSeleccionado=";
		url=urlPueblo+municipioSeleccionado;
		url=url+"&provinciaSeleccionado=" + provinciaSeleccionado;

		var req_generico_pueblo = NuevoAjax();
		//Hace la llamada AJAX
		req_generico_pueblo.onreadystatechange = function() {
			rellenarListaPueblos(req_generico_pueblo,selectPueblo,id_PuebloSeleccionado);
		};
		req_generico_pueblo.open("POST", url, true);
		req_generico_pueblo.send(null);
	}

	//Generar la llamada Ajax para obtener municipios
	function obtenerPueblosDGTPorMunicipio(selectProvincia, hiddenMunicipio, selectPueblo,id_PuebloSeleccionado){
		//Sin elementos
		if(hiddenMunicipio.value==0){
			selectPueblo.length = 0;
			selectPueblo.options[0] = new Option("Seleccione Pueblo", "");
			return;
		}
		if (selectProvincia.selectedIndex==0){
			selectIndex.length=0;
			selectProvincia.options[0]= new Option("Seleccione Provincia","");
			return;
		}
		municipioSeleccionado = hiddenMunicipio.value;
		provinciaSeleccionado = selectProvincia.options[selectProvincia.selectedIndex].value;

		var urlPueblo = "recuperarPueblosDGTPorMunicipioTraficoAjax.action?municipioSeleccionado=";
		url=urlPueblo+municipioSeleccionado;
		url=url+"&provinciaSeleccionado=" + provinciaSeleccionado;

		var req_generico_pueblo = NuevoAjax();
		//Hace la llamada AJAX
		req_generico_pueblo.onreadystatechange = function() {
			rellenarListaPueblos(req_generico_pueblo,selectPueblo,id_PuebloSeleccionado);
		};
		req_generico_pueblo.open("POST", url, true);
		req_generico_pueblo.send(null);
	}

	//Generar la llamada Ajax para obtener municipios
	function obtenerNombreViasPorProvinciaMunicipioYTipoVia(selectProvincia, id_selectMunicipioSeleccionado, selectTipoVia, selectNombreVia, via){
		var provinciaSeleccionada = selectProvincia.options[selectProvincia.selectedIndex].value;
		var municipioSeleccionado = document.getElementById(id_selectMunicipioSeleccionado).value;
		var tipoViaSeleccionado = selectTipoVia.value;

		var url = "recuperarNombreViasPorProvinciaMunicipioYTipoViaTraficoAjax.action?provinciaSeleccionada=" + provinciaSeleccionada;
		url = url + "&municipioSeleccionado=" + municipioSeleccionado;
		url = url + "&tipoViaSeleccionado=" + escape(tipoViaSeleccionado);

		var req_generico_nombre_via = NuevoAjax();
		//Hace la llamada AJAX
		req_generico_nombre_via.onreadystatechange = function () {
			rellenarListaNombreVias(req_generico_nombre_via, selectNombreVia, via);
		};
		req_generico_nombre_via.open("POST", url, true);
		req_generico_nombre_via.send(null);
	}

	function inicializarTipoVia(select_tipoVia,select_nombreVia,via){
		document.getElementById(select_tipoVia).value = "-1";
		document.getElementById(select_tipoVia).selectedIndex = 0;
		document.getElementById(select_nombreVia).value="";
		via.processor.setWords([]);
	}

	function borrarRestoCampos(select_codigoPostal,select_numero,select_letra,select_escalera,select_piso,select_puerta,select_bloque,select_km,select_hm) {
		document.getElementById(select_codigoPostal).value="";
		document.getElementById(select_numero).value="";
		document.getElementById(select_letra).value="";
		document.getElementById(select_escalera).value="";
		document.getElementById(select_piso).value="";
		document.getElementById(select_puerta).value="";
		document.getElementById(select_bloque).value="";
		document.getElementById(select_km).value="";
		document.getElementById(select_hm).value="";
	}

	function obtenerCodigoPostalPorMunicipio(selectProvincia, hiddenMunicipio, idCodigoPostal){
		municipioSeleccionado = hiddenMunicipio.value;
		var provincia = document.getElementById(selectProvincia);
		provinciaSeleccionado = provincia.options[provincia.selectedIndex].value;
		var urlPueblo = "recuperarCodigoPostalPorMunicipioTraficoAjax.action?municipioSeleccionado=";
		url=urlPueblo+municipioSeleccionado;
		url=url+"&provinciaSeleccionado=" + provinciaSeleccionado;

		var req_generico_codigo_postal = NuevoAjax();
		//Hace la llamada AJAX
		req_generico_codigo_postal.onreadystatechange = function () { 
			rellenarDatosCodigoPostal(req_generico_codigo_postal,idCodigoPostal);
		};
		req_generico_codigo_postal.open("POST", url, true);
		req_generico_codigo_postal.send(null);
	}

	function rellenarDatosCodigoPostal(req_generico_codigo_postal,idCodigoPostal){
		if (req_generico_codigo_postal.readyState == 4) { // Complete
		if (req_generico_codigo_postal.status == 200) { // OK response
			textToSplit = req_generico_codigo_postal.responseText;
			document.getElementById(idCodigoPostal).value =textToSplit!=undefined?textToSplit:"";
			}
		}
	}

	//Generar la llamada AJAX para obtener marcas
	function obtenerModelosPorMarca(selectMarca, select_tipoVehiculo, _id_anioDevengo, selectModelo,id_modeloSeleccionado){
		//Sin elementos
		if(selectMarca.selectedIndex==0){
			selectModelo.length = 0;
			selectModelo.options[0] = new Option("Seleccione Modelo", "");
			return;
		}

		tipoVehiculoSeleccionado = select_tipoVehiculo.options[select_tipoVehiculo.selectedIndex].value;
		marcaSeleccionada = selectMarca.options[selectMarca.selectedIndex].value;

		url="recuperarModelosPorMarcaTraficoAjax.action?marcaSeleccionada="+marcaSeleccionada+"&tipoVehiculoSeleccionado="+selectedOption+"&anioDevengo="+document.getElementById("anioDevengo").value;

		var req_generico_modelo = NuevoAjax();
		//Hace la llamada AJAX
		req_generico_modelo.onreadystatechange = function () {
			rellenarListaModelos(req_generico_modelo,selectModelo,id_modeloSeleccionado);
		};
		req_generico_modelo.open("POST", url, true);
		req_generico_modelo.send(null);
	}

	// DRC@28-11-2012 Incidencia Mantis: 2901
	function validarMarca(marca, idMarca, listaMarcas, checkMarca, marcasDgt) {
		checkMarca.innerHTML = '';
		checkMarca.style.fontWeight = 'normal';

		if (isNaN(marca.value.substr(0,1))) { // Caracteres Alfanumericos
			idMarca.value = '';
			var letra = marca.value.substr(0,1).toUpperCase();
			var marcas = eval("listaMarcas.processor.words."+letra).toString();
			var arrayMarcas = marcas.split(",");
			var codigos = eval("listaMarcas.processor.keys."+letra).toString();

			var arrayCodigos = codigos.split(",");
			checkMarca.style.color = '#0000ff';
			checkMarca.innerHTML = 'Verificando...';

			for(i = 0; i <= arrayMarcas.length; i++){
				if(arrayMarcas[i] == marca.value) {
					checkMarca.style.fontWeight = 'bold';
					checkMarca.style.color = '#00aa00';
					checkMarca.innerHTML = 'Marca verificada';
					idMarca.value = arrayCodigos[i];
					return;
				}
			}
			checkMarca.style.fontWeight='bold';
			checkMarca.style.color = '#ff0000';
			checkMarca.innerHTML = 'Marca incorrecta';
			idMarca.value = '';
		} else { //Caracters Numericos
			checkMarca.style.color = '#0000ff';
			checkMarca.innerHTML = 'Verificando...';
			var url = "confirmarValidezMarcasTraficoAjax.action?nombreMarca=" + marca.value + "&marcasDgt=" + marcasDgt;

			var req_generico_marca = NuevoAjax();
			// Hace la llamada a Ajax
			req_generico_marca.onreadystatechange = function() {
				comprobarMarcaBBDD(req_generico_marca, marca, idMarca.id, checkMarca);
			};
			req_generico_marca.open("POST", url, true);
			req_generico_marca.send(null);
			return;
		}
	}

	// Validamos el fabricante
	function validarFabricante(fabricante, listaFabricantes, checkCodFabricante) {
		var valido = false;
		var inicialFabricante = fabricante.charAt(0);
		var words = listaFabricantes.processor.words;

		var arrayBusqueda = words[inicialFabricante];
		var checkCodigoFabricante = document.getElementById(checkCodFabricante);

		if (inicialFabricante != null && inicialFabricante != ''
			&& inicialFabricante != 'undefined') {
			if (arrayBusqueda != null) {
				for (var i = 0; i < arrayBusqueda.length; i++) {
					if (fabricante == arrayBusqueda[i])
						valido = true;
				}
			}

			if (valido) {
				checkCodigoFabricante.style.fontWeight = 'bold';
				checkCodigoFabricante.style.color = '#00aa00';
				checkCodigoFabricante.innerHTML = "Fabricante verificado";
			} else {
				checkCodigoFabricante.style.fontWeight = 'bold';
				checkCodigoFabricante.style.color = '#ff0000';
				checkCodigoFabricante.innerHTML = "Fabricante incorrecto";
			}
		}

	}

//Generar la llamada Ajax para marcas
function obtenerMarcas(marca, id, idHidden, versionMatriculacion) {
	var valorMarca = '';
	var url = "recuperarMarcasTraficoAjax.action?nombreMarca=" + valorMarca + "&versionMatriculacion=" + versionMatriculacion;

	var req_generico_marca = NuevoAjax();
	//Hace la llamada a Ajax
	req_generico_marca.onreadystatechange = function() {
		rellenarListaMarcasVehiculo(req_generico_marca, marca, id, idHidden);
	};
	req_generico_marca.open("POST", url, true);
	req_generico_marca.send(null);
}

function rellenarListaMarcasVehiculo(req_generico_marca, marca, id, idHidden) {
	if (req_generico_marca.readyState == 4) { // Complete
		if (req_generico_marca.status == 200) { // OK response
			var textToSplit = req_generico_marca.responseText;
			//Split the document
			var marcascodigos = textToSplit.split("[#]");
			var marcas = marcascodigos[0].split("||");
			var codigos = marcascodigos[1].split("||");

			// Asignamos las palabras sugeridas a la lista de nombres de marcas
			marca.processor.setWords(marcas);

			// Asignamos las claves sugeridas
			marca.processor.setKeys(marcas, codigos);
			// Recuperamos el nombre de la marca a través del código cuando obtenemos el detalle de un trámite
			for (x = 0; x < codigos.length; x++) {
				if (document.getElementById(idHidden).value == codigos[x]) document.getElementById(id).value = marcas[x];
			}
		}
	}
}

// Recarga el combo cuando se recarga la pagina
function recargarComboModelos(id_select_marca,id_marcaSeleccionada,id_select_tipoVehiculo,id_anioDevengo,id_select_modelo,id_modeloSeleccionado){
	if(document.getElementById(id_marcaSeleccionada)!="" &&
			document.getElementById(id_select_tipoVehiculo).selectedIndex > 0 &&
			document.getElementById(id_anioDevengo)!=""){
		obtenerModelosPorMarcaSeleccionada(id_marcaSeleccionada, document.getElementById(id_select_tipoVehiculo), id_anioDevengo, document.getElementById(id_select_modelo), id_modeloSeleccionado);
	}
}

//Generar la llamada Ajax para obtener marcas
function obtenerModelosPorMarcaSeleccionada(idMarcaSeleccionada, select_tipoVehiculo, id_anioDevengo, selectModelo,id_modeloSeleccionado){
	tipoVehiculoSeleccionado = select_tipoVehiculo.options[select_tipoVehiculo.selectedIndex].value;
	marcaSeleccionada = document.getElementById(idMarcaSeleccionada).value;

	url="recuperarModelosPorMarcaTraficoAjax.action?marcaSeleccionada="+marcaSeleccionada+"&tipoVehiculoSeleccionado="+selectedOption+"&anioDevengo="+document.getElementById("anioDevengo").value;

	var req_generico_modelo = NuevoAjax();
	//Hace la llamada a ajax
	req_generico_modelo.onreadystatechange = function () {
		rellenarListaModelos(req_generico_modelo,selectModelo,id_modeloSeleccionado);
	};
	req_generico_modelo.open("POST", url, true);
	req_generico_modelo.send(null);
}

//Generar la llamada AJAX para obtener codigos de tasa
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
	};
	req_generico_codigosTasa.open("POST", url, true);
	req_generico_codigosTasa.send(null);
}

function rellenarListaPueblos(req_generico_pueblo,selectPueblo, id_PuebloSeleccionado){
	selectPueblo.options.length = 0;

	if (req_generico_pueblo.readyState == 4) { // Complete
		if (req_generico_pueblo.status == 200) { // OK response
			textToSplit = req_generico_pueblo.responseText;

			//Split the document
			returnElements=textToSplit.split("||");

			selectPueblo.options[0] = new Option("Seleccione Pueblo", "");
			//Process each of the elements
			var puebloSeleccionado = document.getElementById(id_PuebloSeleccionado).value;
			for (var i=0; i<returnElements.length; i++ ){
				value = returnElements[i].split(";");
				selectPueblo.options[i+1] = new Option(value[0], value[1]);
				if(selectPueblo.options[i+1].value == puebloSeleccionado){
					selectPueblo.options[i+1].selected = true;
				}
			}
		}
	}
}

function rellenarListaNombreVias(req_NombreVia, selectNombreVia, via){
	if (req_NombreVia.readyState == 4) { // Complete
	if (req_NombreVia.status == 200) { // OK response
		var textToSplit = req_NombreVia.responseText;
		//Split the document
		returnElements=textToSplit.split("||");
			var anterior = selectNombreVia.value;
			via.processor.setWords(returnElements);
			selectNombreVia.value=anterior;
		}
	}
}

//Callback function de ObtenerTipoViaPorProvincia
function rellenarListaTipoVia(req_generico_tipo_via,selectTipoVia,id_tipoViaSeleccionado){

	selectTipoVia.options.length = 0;

	if (req_generico_tipo_via.readyState == 4) { // Complete
		if (req_generico_tipo_via.status == 200) { // OK response
			textToSplit = req_generico_tipo_via.responseText;

			//Split the document
			returnElements=textToSplit.split("||");

			selectTipoVia.options[0] = new Option("Seleccione Tipo Via", "");
			//Process each of the elements
			var tipoViaSeleccionado = document.getElementById(id_tipoViaSeleccionado).value;
			for (var i=0; i<returnElements.length; i++ ){
				value = returnElements[i].split(";");
				selectTipoVia.options[i+1] = new Option(value[0], value[1]);
				if(selectTipoVia.options[i+1].value == tipoViaSeleccionado){
					selectTipoVia.options[i+1].selected = true;
				}
			}

		}
	}
}

//Callback function de ObtenerMunicipiosPorProvincia
function rellenarListaMunicipios(req_generico_municipio,selectMunicipio,id_municipioSeleccionado){

	selectMunicipio.options.length = 0;

	if (req_generico_municipio.readyState == 4) { // Complete
		if (req_generico_municipio.status == 200) { // OK response
			textToSplit = req_generico_municipio.responseText;

			//Split the document
			returnElements=textToSplit.split("||");

			selectMunicipio.options[0] = new Option("Seleccione Municipio", "");
			//Process each of the elements
			var municipioSeleccionado = document.getElementById(id_municipioSeleccionado).value;
			for (var i=0; i<returnElements.length; i++){
				value = returnElements[i].split(";");
				selectMunicipio.options[i+1] = new Option(value[0], value[1]);
				if(selectMunicipio.options[i+1].value == municipioSeleccionado){
					selectMunicipio.options[i+1].selected = true;
				}
			}

		}
	}
}

// Recarga el combo cuando se recarga la pagina
function recargarComboLocalidadesDGT(id_select_provincia, id_hidden_municipio,id_select_pueblo,id_puebloSeleccionado){
	var selectProvincia = document.getElementById(id_select_provincia);
	var hiddenMunicipio= document.getElementById(id_hidden_municipio);
	var selectPueblo = document.getElementById(id_select_pueblo);
	if(selectProvincia != null && selectProvincia.selectedIndex >0){
		if(hiddenMunicipio != null && hiddenMunicipio.value > 0){
			obtenerPueblosDGTPorMunicipio(selectProvincia,hiddenMunicipio,selectPueblo,id_puebloSeleccionado);
		}
	}
}

// Recarga el combo cuando se recarga la pagina
function recargarNombreVias(id_select_provincia, id_select_municipio_seleccionado,id_select_tipoVia, id_select_nombre_via,via){
	var selectProvincia = document.getElementById(id_select_provincia);
	//var selectMunicipio = document.getElementById(id_select_municipio);
	var selectTipoVia = document.getElementById(id_select_tipoVia);

	var selectNombreVia = document.getElementById(id_select_nombre_via);
	if(selectProvincia != null && selectProvincia.selectedIndex >0){
		if(document.getElementById(id_select_municipio_seleccionado).value > 0){
			if (selectTipoVia!=null && selectTipoVia.value != ''){
				obtenerNombreViasPorProvinciaMunicipioYTipoVia(selectProvincia,id_select_municipio_seleccionado,selectTipoVia, selectNombreVia,via);
			}
		}
	}
}

// Recarga el combo cuando se recarga la pagina
function recargarComboColegios(id_select_provincia,id_select_colegio,id_colegioSeleccionado){
	selectProvincia = document.getElementById(id_select_provincia);
	selectColegio = document.getElementById(id_select_colegio);

	if(selectProvincia != null && selectProvincia.selectedIndex > 0){
		obtenerColegiosPorProvincia(selectProvincia,selectColegio,id_colegioSeleccionado);
	}
}

// Recarga el combo cuando se recarga la pagina
function recargarComboJefaturas(id_select_provincia,id_select_jefatura,id_jefaturaSeleccionado){
	selectProvincia = document.getElementById(id_select_provincia);
	selectJefatura = document.getElementById(id_select_jefatura);

	if(selectProvincia != null && selectProvincia.selectedIndex > 0){
		obtenerJefaturasPorProvincia(selectProvincia,selectJefatura,id_jefaturaSeleccionado);
	}
}

//LLAMADAS AJAX

// Genera la llamada ajax para calcular la fecha de caducidad ITV de un vehículo:
function calcularFechaCaducidadItv(numExpediente){
	url = urlCalculoItv + numExpediente;
	var req_calculoFechaItv = NuevoAjax();
	//Hace la llamada a ajax
	req_calculoFechaItv.onreadystatechange = function () {
		callBackCalcularFechaCaducidadItv(req_calculoFechaItv);
	};
	req_calculoFechaItv.open("POST", url, true);
	req_calculoFechaItv.send(null);
}

//Callback function de calcularFechaCaducidadItv
function callBackCalcularFechaCaducidadItv(req_calculoFechaItv){
	if (req_calculoFechaItv.readyState == 4) { // Complete
		if (req_calculoFechaItv.status == 200) { // OK response
			textToSplit = req_calculoFechaItv.responseText;

			//Split the document
			returnElements=textToSplit.split("||");

			if(returnElements.length == 1){
				document.getElementById("idFechaCaducidadITV").checked = false;
				alert(returnElements[0]);
				return;
			}

			document.getElementById("diaCaducidadTarjetaITV").value = returnElements[0];
			document.getElementById("mesCaducidadTarjetaITV").value = returnElements[1];
			document.getElementById("anioCaducidadTarjetaITV").value = returnElements[2];
		}
	}
}

//Generar la llamada Ajax para obtener Colegios
function obtenerColegiosPorProvincia(selectProvincia,selectColegio,id_colegioSeleccionado){
	//Sin elementos
	if(selectProvincia.selectedIndex==0){
		selectColegio.length = 0;
		selectColegio.options[0] = new Option("Seleccione Colegio", "");
		return;
	}
	selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
	url=urlColegios+selectedOption;
	var req_generico_colegio = NuevoAjax();
	//Hace la llamada a ajax
	req_generico_colegio.onreadystatechange = function () {
		rellenarListaColegios(req_generico_colegio,selectColegio,id_colegioSeleccionado);
	};
	req_generico_colegio.open("POST", url, true);
	req_generico_colegio.send(null);
}

//Callback function de ObtenerColegiosPorProvincia
function rellenarListaColegios(req_generico_colegio,selectColegio,id_colegioSeleccionado){
	selectColegio.options.length = 0;

	if (req_generico_colegio.readyState == 4) { // Complete
		if (req_generico_colegio.status == 200) { // OK response
			textToSplit = req_generico_colegio.responseText;

			//Split the document
			returnElements=textToSplit.split("||");

			selectColegio.options[0] = new Option("Seleccione Colegio", "");
			//Process each of the elements
			var colegioSeleccionado = document.getElementById(id_colegioSeleccionado).value;
			for ( var i=0; i<returnElements.length; i++){
				value = returnElements[i].split(";");
				selectColegio.options[i+1] = new Option(value[0], value[1]);
				if(selectColegio.options[i+1].value == colegioSeleccionado){
					selectColegio.options[i+1].selected = true;
				}
			}

		}
	}
}

//Generar la llamada Ajax para obtener jefaturas
function obtenerJefaturasPorProvincia(selectProvincia,selectJefatura,id_jefaturaSeleccionado){
	//Sin elementos
	if(selectProvincia.selectedIndex==0){
		selectJefatura.length = 0;
		selectJefatura.options[0] = new Option("Seleccione Jefatura", "");
		return;
	}
	selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
	url=urlJefaturas+selectedOption;
	var req_generico_jefatura = NuevoAjax();
	//Hace la llamada AJAX
	req_generico_jefatura.onreadystatechange = function () { 
		rellenarListaJefaturas(req_generico_jefatura,selectJefatura,id_jefaturaSeleccionado);
	};
	req_generico_jefatura.open("POST", url, true);
	req_generico_jefatura.send(null);
}

//Callback function de ObtenerJefaturasPorProvincia
function rellenarListaJefaturas(req_generico_jefatura,selectJefatura,id_jefaturaSeleccionado){

	selectJefatura.options.length = 0;

	if (req_generico_jefatura.readyState == 4) { // Complete
		if (req_generico_jefatura.status == 200) { // OK response
			textToSplit = req_generico_jefatura.responseText;

			//Split the document
			returnElements=textToSplit.split("||");

			selectJefatura.options[0] = new Option("Seleccione Jefatura", "");
			//Process each of the elements
			var jefaturaSeleccionado = document.getElementById(id_jefaturaSeleccionado).value;
			for ( var i=0; i<returnElements.length; i++){
				value = returnElements[i].split(";");
				selectJefatura.options[i+1] = new Option(value[0], value[1]);
				if(selectJefatura.options[i+1].value == jefaturaSeleccionado){
					selectJefatura.options[i+1].selected = true;
				}
			}
		}
	}
}

function seleccionarCampo(idselectCampoSeleccionado, idCampo){
	document.getElementById(idselectCampoSeleccionado).value = document.getElementById(idCampo).value;
}

function calculaLetraNIF(campo){
	if(campo.value != ""){
		if(!isNaN(campo.value)){ //Si el campo es numérico
			if(campo.value.length == 8){ //Si la longitud es 8, calculamos la letra
				var JuegoCaracteres="TRWAGMYFPDXBNJZSQVHLCKET";
				var Posicion= campo.value % 23;	
				campo.value = campo.value + JuegoCaracteres.charAt(Posicion);
			} else { //Si no, avisamos de que el dni es invalido
				campo.value = "";
				alert("El DNI introducido es inv\u00e1lido.");
				return;
			}
		} else if(!isNaN(campo.value.substr(0,1))){ //Miramos si el primer caracter es una letra o un número. Si es letra, no hacemos nada.
			//Si es número, comprobamos si se han introducido 8 números y una letra. Si es así, recalculamos la letra.
			if(campo.value.length == 9 && isNaN(campo.value.substr(8,1))){
				var JuegoCaracteres="TRWAGMYFPDXBNJZSQVHLCKET";
				var dniSinLetra = campo.value.substr(0,8);
				var Posicion= dniSinLetra % 23;	
				campo.value = dniSinLetra + JuegoCaracteres.charAt(Posicion);
			} else { //Si no, avisamos de que el DNI es inválido
				campo.value = "";
				alert("El DNI introducido es inv\u00e1lido.");
				return;
			}
		}
	}
}

function obtenerCalles(){
	calle = document.getElementById('domicilioTitular').value;
	provincia = document.getElementById('provinciaTitular').value;
	municipio = document.getElementById('municipioTitular').value;
	pueblo = document.getElementById('puebloTitular').value;

	url="recuperarPueblosPorMunicipioTraficoAjax.action?municipioSeleccionado=";
	//Hace la llamada a ajax
	if (window.XMLHttpRequest){ // Non-IE browsers
		req_generico_calles = new XMLHttpRequest();
		req_generico_calles.onreadystatechange = rellenarCalles;
		req_generico_calles.open("POST", url, true);
		req_generico_calles.send(null);
	} else if (window.ActiveXObject) { // IE
		req_generico_calles = new ActiveXObject("Microsoft.XMLHTTP");
		if (req_generico_calles) {
			req_generico_calles.onreadystatechange = rellenarCalles;
			req_generico_calles.open("POST", url, true);
			req_generico_calles.send();
		}
	}
}

//Callback function
function rellenarCalles(){
	calle = document.getElementById('domicilioTitular').value;
	document.getElementById('comboDir').options.length = 0;

	if (req_generico_calles.readyState == 4) { // Complete
		if (req_generico_calles.status == 200) { // OK response
			textToSplit = req_generico_calles.responseText;

			//Split the document
			returnElements=textToSplit.split(";");

			document.getElementById('comboDir').options[0] = new Option(calle, calle);
			//Process each of the elements
			for ( var i=0; i<returnElements.length; i++){
				document.getElementById('comboDir').options[i+1] = new Option(returnElements[i], returnElements[i]);
			}
		}
	}
}

//Oculta o muestra la pestaña de Renting dependiendo de si está o no marcado el check de Renting.
function cambioRentingId(){
	if(document.getElementById('rentingId').checked){
		document.getElementById('pestaniaRentingMO').style.display = "BLOCK";
	} else {
		document.getElementById('pestaniaRentingMO').style.display = "NONE";
		limpiarFormularioArrendatarioTransmision();
		document.getElementById("idNifArrendatario").value = "";
		document.getElementById("idNifRepresentanteArrendatario").value = "";
	}
}

//Metodo que cuenta el numero de checks seleccionados.
function numChecked() {
	var checks = document.getElementsByName("listaChecksConsultaTramite");
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function numCheckedFacturaNormal() {
	var checks = document.getElementsByName("listaChecksConsultaFacturaNormal");
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function numCheckedFacturaRect() {
	var checks = document.getElementsByName("listaChecksConsultaFacturaRect");
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

//Actualiza los radioButton dependiendo de los estados de los trámites.
function actualizaRadios(){
	var activar417PDF = true;
	var activarPresentacionTelematica = true;
	var activarPermisoTemporalCirculacion = true;
	var activarDocumentosTelematicos = true;
	var activarFichaTecnica = true;

	var checks = document.getElementsByName("listaChecksConsultaTramite");
	var i = 0;
	//recorro todos los checks a ver cuales están activos
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(checks[i].value!=8&&checks[i].value!=9&&checks[i].value!=13){
				activar417PDF = false;
			}
			if(checks[i].value!=12&&checks[i].value!=14&&checks[i].value!=35){
				activarPresentacionTelematica = false;
				activarPermisoTemporalCirculacion = false;
				activarFichaTecnica = false;
			}
			if(checks[i].value!=12&&checks[i].value!=14&&checks[i].value!=16&&checks[i].value!=35){
				activarDocumentosTelematicos = false;
			}
		}
		i++;
	}
	var radioButton;

	if(activar417PDF){
		radioButton = document.getElementById("PDF417");
		radioButton.disabled = "";
	} else{
		radioButton = document.getElementById("PDF417");
		radioButton.disabled = "disabled";
	}

	radioButtonTelematica = document.getElementById("PDFPresentacionTelematica");
	if(null!=radioButtonTelematica){
		if( activarPresentacionTelematica){
			radioButtonTelematica.disabled = "";
		} else{
			radioButtonTelematica.disabled = "disabled";
		}
	}

	if(activarPermisoTemporalCirculacion){
		radioButton = document.getElementById("PermisoTemporalCirculacion");
		if(radioButton!=null) {
			radioButton.disabled = "";
		}
	} else{
		radioButton = document.getElementById("PermisoTemporalCirculacion");
		if(radioButton!=null) {
			radioButton.disabled = "disabled";
		}
	}

	if(activarFichaTecnica){
		radioButton = document.getElementById("FichaTecnica");
		if(radioButton!=null) {
			radioButton.disabled = "";
		}
	} else{
		radioButton = document.getElementById("FichaTecnica");
		if(radioButton!=null) {
			radioButton.disabled = "disabled";
		}
	}

	if(activarDocumentosTelematicos){
		radioButton = document.getElementById("DocumentosTelematicos");
		if(radioButton!=null) {
			radioButton.disabled = "";
		}
	} else{
		radioButton = document.getElementById("DocumentosTelematicos");
		if(radioButton!=null) {
			radioButton.disabled = "disabled";
		}
	}
	radioButton = document.getElementById("GenerarDistintivo");
	if(radioButton!=null) {
		controlarMostrarBotonGenerarDistintivo();
	}

	//Controlamos que haya ese RadioButton de Etiquetas
	radioButton = document.getElementById("PegatinasEtiquetaMatricula");
	if(radioButton!=null) {
		controlarMostrarNumEtiquetas();
	}

	//Controlamos que haya ese RadioButton de Representantes
	var bloque = document.getElementById("bloqueTipoRepresenta");
	if(bloque!=null) {
		controlarMostrarTipoRepresenta();
	}
	var bloque = document.getElementById("bloqueTipoRepresentaDeclaracionET");
	if(bloque!=null) {
		controlarMostrarTipoRepresentaDeclaracionET();
	}
	var bloque = document.getElementById("bloqueTipoRepresentaDeclaracionExFt");
	if(bloque!=null) {
		controlarMostrarTipoRepresentaDeclaracionExFt();
	}
	var bloque = document.getElementById("bloqueTipoRepresentaDeclaracionExPermFt");
	if(bloque!=null) {
		controlarMostrarTipoRepresentaDeclaracionExPermFt();
	}
	var bloque = document.getElementById("bloqueTipoRepresentaDeclaracionExPermL");
	if(bloque!=null) {
		controlarMostrarTipoRepresentaDeclaracionExPermL();
	}
	var bloque = document.getElementById("bloqueTipoRepresentaDeclaracionEAPerm");
	if(bloque!=null) {
		controlarMostrarTipoRepresentaDeclaracionEAPerm();
	}
}

function cambiaComaPorPunto(elemento){
	if(null!=document.getElementById(elemento.id)){
		var valorNumero = document.getElementById(elemento.id);
		valorNumero.value = valorNumero.value.replace(',','.');
	}
}

//Comprueba que este seleccionado el radio de Imprimir etiquetas y permite introducir el número de etiquetas a introducir.
function controlarMostrarNumEtiquetas(){
	var etiquetas = document.getElementById("PegatinasEtiquetaMatricula");

	if(etiquetas.checked){
		mostrarNumEtiquetas();
	} else{
		ocultarNumEtiquetas();
	}
}

//Comprueba que este seleccionado el radio de Imprimir etiquetas y permite introducir el número de etiquetas a introducir.
function controlarMostrarBotonGenerarDistintivo(){
	var etiquetas = document.getElementById("GenerarDistintivo");

	if(etiquetas.checked){
		mostrarBotonDistintivo();
	} else{
		ocultarBotonDistintivo();
	}
}

//Se muestra cuadro
function mostrarNumEtiquetas(){
	var bloqueNumEtiquetas = document.getElementById("bloqueNumEtiquetas");
	bloqueNumEtiquetas.style.display="block";
}

//Se oculta el cuadro
function ocultarNumEtiquetas(){
	var bloqueNumEtiquetas = document.getElementById("bloqueNumEtiquetas");
	bloqueNumEtiquetas.style.display="none";
}

function mostrarBotonDistintivo(){
	var botonGeneracionDistintivo = document.getElementById("botonGeneracionDistintivo");
	botonGeneracionDistintivo.style.display="block";
}

//Se oculta el cuadro
function ocultarBotonDistintivo(){
	var botonGeneracionDistintivo = document.getElementById("botonGeneracionDistintivo");
	botonGeneracionDistintivo.style.display="none";
}

//CAMBIA EL ESTADO DE TODOS LOS CHECKBOX y calcula credito

//Calcula los créditos para cuando seleccionamos un radioButtom
//Bajas y Solicitudes
function calculoCreditosSolicitados_BS(numero){
	var creditos = document.getElementById("numCreditosSolicitados");
	creditos.value = numero * numChecked();
	actualizaRadios_BS();
}

function marcarTodosImprimir(objCheck, arrayObjChecks, tipoTramite) {
	marcarTodos(objCheck, arrayObjChecks);
	marcarImprimir(tipoTramite);
}

function marcarImprimir(tipoTramite){
	calcularCreditosImprimir();
	if(tipoTramite != "T3"){
		activarRadiosImprimir();
	}else{
		activarRadiosImprimirBaja();
	}
	
}

function cambiarRadioImprimir(){
	calcularCreditosImprimir();
	activarRadiosImprimir();
}

function cambiarRadioImprimirBaja(){
	calcularCreditosImprimir();
	activarRadiosImprimirBaja();
}

function calcularCreditosImprimir(){
	var impreso = document.getElementsByName("impreso");
	var numero = 1;
	var j = 0;
	while(j < impreso.length && !impreso[j].checked) {
		j++;
	}
	if (j<impreso.length){
		if(impreso[j].value!="PDF417"){
			if (document.getElementById("numCreditosSolicitados")!=null){
				document.getElementById("numCreditosSolicitados").value = 0;
			}
		} else {
			document.getElementById("numCreditosSolicitados").value = numero * numChecked();
		}
	}
}

function activarRadiosImprimirBaja(){
	var activar417PDF = true;
	var activarInformeBaja = true;
	var activarPresentacionTelematica = true;
	var activarDocumentosTelematicos = true;
	var checks = document.getElementsByName("listaChecksConsultaTramite");
	var i = 0;
	// Recorro todos los checks a ver cuales están activos
	while(checks[i] != null) {
		if(checks[i].checked) {
			var estado = document.getElementById("estado"+checks[i].value);
				if(estado.value!=8&&estado.value!=9 && estado.value!=13 && estado.value!=26 && estado.value!=105){
				activar417PDF = false;
			}
			if(estado.value!=12&&estado.value!=14 && estado.value!=35 && estado.value!=105){ 
				activarPresentacionTelematica = false;
				activarInformeBaja = false;
				activarDocumentosTelematicos = false;
			}
		}
		i++;
	}
	var radioButton = document.getElementById("PDF417");
	if(activar417PDF){
		if (radioButton!=null){
			radioButton.disabled = "";
		}
	}else if (radioButton!=null){
		radioButton.disabled = "disabled";
	}
	
	radioButtonTelematica = document.getElementById("PDFPresentacionTelematica");
	if(null!=radioButtonTelematica){
		if(activarPresentacionTelematica){
			radioButtonTelematica.disabled = "";
		}else{
			radioButtonTelematica.disabled = "disabled";
		}
	}
	radioButton = document.getElementById("InformeBajaTelematica");
	if(activarInformeBaja){
		if (radioButton!=null){
			radioButton.disabled = "";
		}
	}else if (radioButton!=null){
		radioButton.disabled = "disabled";
	}
	if(activarDocumentosTelematicos){
		radioButton = document.getElementById("InformeBajaSive");
		if(radioButton!=null){
			radioButton.disabled = "";
		}
	}else{
		radioButton = document.getElementById("InformeBajaSive");
		if(radioButton!=null){
			radioButton.disabled = "disabled";
		}
	}
	if(activarDocumentosTelematicos){
		radioButton = document.getElementById("JustifRegEntradaBajaSive");
		if(radioButton!=null){
			radioButton.disabled = "";
		}
	}else{
		radioButton = document.getElementById("JustifRegEntradaBajaSive");
		if(radioButton!=null){
			radioButton.disabled = "disabled";
		}
	}
}

function activarRadiosImprimir(){
	var activar417PDF = true;
	var activarPresentacionTelematica = true;
	var activarPermisoTemporalCirculacion = true;
	var activarDocumentosTelematicos = true;
	var activarSolicitudNRE06 = true;
	var activarFichaTecnica = true;
	var activarDistintivo = true;
	var checks = document.getElementsByName("listaChecksConsultaTramite");
	var i = 0;
	// Recorro todos los checks a ver cuáles están activos
	while(checks[i] != null) {
		if(checks[i].checked) {
			var estado = document.getElementById("estado"+checks[i].value);
			if(estado.value!=8&&estado.value!=9 && estado.value!=13 && estado.value!=26 && estado.value!=105){
				activar417PDF = false;
			}
			if(estado.value!=12&&estado.value!=14 && estado.value!=35 && estado.value!=105){ 
				activarPresentacionTelematica = false;
				activarPermisoTemporalCirculacion = false;
				activarFichaTecnica = false;
				activarDistintivo = false;
			}
			if(estado.value!=12&&estado.value!=14 && estado.value!=16 && estado.value!=35){
				activarDocumentosTelematicos = false;
			}
		}
		i++;
	}
	var radioButton;
	if(activar417PDF){
		radioButton = document.getElementById("PDF417");
		if (radioButton!=null){
			radioButton.disabled = "";
		}
	}else{
		radioButton = document.getElementById("PDF417");
		if (radioButton!=null){
			radioButton.disabled = "disabled";
		}
	}
	radioButtonTelematica = document.getElementById("PDFPresentacionTelematica");
	if(null!=radioButtonTelematica){
		if(activarPresentacionTelematica){
			radioButtonTelematica.disabled = "";
		}else{
			radioButtonTelematica.disabled = "disabled";
		}
	}
	if(activarPermisoTemporalCirculacion){
		radioButton = document.getElementById("PermisoTemporalCirculacion");
		if(radioButton!=null){
			radioButton.disabled = "";
		}
	}else{
		radioButton = document.getElementById("PermisoTemporalCirculacion");
		if(radioButton!=null){
			radioButton.disabled = "disabled";
		}
	}
	if(activarFichaTecnica){
		radioButton = document.getElementById("FichaTecnica");
		if(radioButton!=null){
			radioButton.disabled = "";
		}
	}else{
		radioButton = document.getElementById("FichaTecnica");
		if(radioButton!=null){
			radioButton.disabled = "disabled";
		}
	}

	radioButton = document.getElementById("GenerarDistintivo");
	if(radioButton!=null){
		controlarMostrarBotonGenerarDistintivo();
	}

	if(activarDocumentosTelematicos){
		radioButton = document.getElementById("DocumentosTelematicos");
		if(radioButton!=null){
			radioButton.disabled = "";
		}
	}else{
		radioButton = document.getElementById("DocumentosTelematicos");
		if(radioButton!=null){
			radioButton.disabled = "disabled";
		}
	}
	if(activarSolicitudNRE06){
		radioButton = document.getElementById("SolicitudNre06");
		if(radioButton!=null){
			radioButton.disabled = "";
		}
	}else{
		radioButton = document.getElementById("SolicitudNre06");
		if(radioButton!=null){
			radioButton.disabled = "disabled";
		}
	}
	//Controlamos que haya ese RadioButton de Etiquetas
	radioButton = document.getElementById("PegatinasEtiquetaMatricula");
	if(radioButton!=null){
		controlarMostrarNumEtiquetas();
	}

	radioButtonTelematica = document.getElementById("PDTEL");
	if(null!=radioButtonTelematica){
		if( activarPresentacionTelematica){
			radioButtonTelematica.disabled = "";
		}else{
			radioButtonTelematica.disabled = "disabled";
		}
	}

	radioButtonTelematica = document.getElementById("InformeBajaTelematica");
	if(null!=radioButtonTelematica){
		if( activarPresentacionTelematica){
			radioButtonTelematica.disabled = "";
		}else{
			radioButtonTelematica.disabled = "disabled";
		}
	}
	//Controlamos que haya ese RadioButton de Representantes
	var bloque = document.getElementById("bloqueTipoRepresenta");
	if(bloque!=null){
		controlarMostrarTipoRepresenta();
	}
	var bloque1 = document.getElementById("bloqueTipoRepresentaDeclaracionET");
	if(bloque1!=null){
		controlarMostrarTipoRepresentaDeclaracionET();
	}
	var bloque2 = document.getElementById("bloqueTipoRepresentaDeclaracionExFt");
	if(bloque2!=null){
		controlarMostrarTipoRepresentaDeclaracionExFt();
	}
	var bloque3 = document.getElementById("bloqueTipoRepresentaDeclaracionExPermFt");
	if(bloque3!=null){
		controlarMostrarTipoRepresentaDeclaracionExPermFt();
	}
	var bloque4 = document.getElementById("bloqueTipoRepresentaDeclaracionExPermL");
	if(bloque4!=null){
		controlarMostrarTipoRepresentaDeclaracionExPermL();
	}
	var bloque5 = document.getElementById("bloqueTipoRepresentaDeclaracionEAPerm");
	if(bloque5!=null){
		controlarMostrarTipoRepresentaDeclaracionEAPerm();
	}
}

//Muestra un div, e intercambia la visibilidad del div mostrar y el div ocultar
//recibe el elemento DIV a mostrar, y los div de los botones
function mostrarBloqueValidadosTelematicamente() {
	var divValidos = document.getElementById("bloqueValidadosTelematicamente");
	var desplegable = document.getElementById("despValidadoTelematicamente");

	if(divValidos.style.display == 'none'){
		divValidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";
	} else if(divValidos.style.display == 'block'){
		divValidos.style.display='none';	
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";
	}
}

//Muestra un div, e intercambia la visibilidad del div mostrar y el div ocultar
//recibe el elemento DIV a mostrar, y los div de los botones
function mostrarBloqueValidadosPDF() {
	var divValidos = document.getElementById("bloqueValidadosPDF");
	var desplegable = document.getElementById("despValidadoPDF");

	if(divValidos.style.display == 'none'){
		divValidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";
	} else if(divValidos.style.display == 'block'){
		divValidos.style.display='none';
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";
	}
}

//Oculta un div, e intercambia la visibilidad del div mostrar y el div ocultar
//recibe el elemento DIV a mostrar, y los div de los botones
function mostrarBloqueFallidos() {
	var divFallidos = document.getElementById("bloqueFallidos");
	var desplegable = document.getElementById("despFallidos");

	if(divFallidos.style.display == 'none'){
		divFallidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";
	} else if(divFallidos.style.display == 'block'){
		divFallidos.style.display='none';
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";
	}
}

//Muestra un div, e intercambia la visibilidad del div mostrar y el div ocultar
//recibe el elemento DIV a mostrar, y los div de los botones
function mostrarBloqueTramitadosTelematicamente() {
	var divValidos = document.getElementById("bloqueTramitadosTelematicamente");
	var desplegable = document.getElementById("imgTramitadosTelematicamente");

	if(divValidos.style.display == 'none'){
		divValidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";
	} else if(divValidos.style.display == 'block'){
		divValidos.style.display='none';
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";
	}
}

//Oculta un div, e intercambia la visibilidad del div mostrar y el div ocultar
//recibe el elemento DIV a mostrar, y los div de los botones
function mostrarBloqueFallidosTelematicamente() {
	var divFallidos = document.getElementById("bloqueFallidosTelematicamente");
	var desplegable = document.getElementById("imgFallidosTelematicamente");

	if(divFallidos.style.display == 'none'){
		divFallidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";
	} else if(divFallidos.style.display == 'block'){
		divFallidos.style.display='none';
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";
	}
}

///////////////////////////////////////////////////////////////////////
///////FIN PARTE DE CONSULTAS, EVOLUCION, CREDITOS E IMPRESIONES///////
///////////////////////////////////////////////////////////////////////

function borrarComboPueblo(id_pueblo){
	var pueblo = document.getElementById(id_pueblo);
	for (var i=pueblo.childElementCount-1;i>0;i--) { pueblo.options[i] = null; pueblo.selectedIndex=0; }
}

function borrarComboPuebloCorreos(id_pueblo_Correos){
	var pueblo = document.getElementById(id_pueblo_Correos);
	for (var i=pueblo.childElementCount-1;i>0;i--) { pueblo.options[i] = null; pueblo.selectedIndex=0; }
}

//Saca por popup el html del div pasado por parámetro y permite imprimirlo o guardarlo
//nombre=id del elemento a imprimir
//cerrar=si se quiere que se cierre el popup automáticamente tras imprimir
//modificar=nombre de una función específica si se quiere ejecutar algo sobre el popup
function imprimirId(nombre,cerrar,modificar) {
	var ficha = document.getElementById(nombre);
	var ventimp = window.open(' ', 'popimpr','status=1,scrollbars=yes');
	var css = "<link media=\"screen\" type=\"text/css\" rel=\"stylesheet\" href=\"css/estilos.css\">";
	css+="<link media=\"screen\" type=\"text/css\" rel=\"stylesheet\" href=\"css/global.css\">";
	ventimp.document.write(css+ficha.innerHTML);
	if (modificar!=undefined && modificar!=null && modificar!="") eval(modificar);
	ventimp.document.close();
	ventimp.print( );
	if (cerrar==true) ventimp.close();
}

function editarFechaCaducidad(tarjetaITV) {
	var bloqueado = false;
	var color = "white";
	if (tarjetaITV=="A") {
		bloqueado = false;
	} else {
		if (document.getElementById('idCodigoItv').value.length == 8 && document.getElementById('idCodigoItv').value != "SINCODIG") {
			bloquedao = true;
			var color = "#EEEEEE";
		} else
			bloqueado = false;
	}
	document.getElementById("diaCaducidadTarjetaITV").disabled = bloqueado;
	document.getElementById("mesCaducidadTarjetaITV").disabled = bloqueado;
	document.getElementById("anioCaducidadTarjetaITV").disabled = bloqueado;
	document.getElementById("diaCaducidadTarjetaITV").style.backgroundColor = color;
	document.getElementById("mesCaducidadTarjetaITV").style.backgroundColor = color;
	document.getElementById("anioCaducidadTarjetaITV").style.backgroundColor = color;
}

/*
 * Invoca el action de la búsqueda de peticiones pendientes
 */
function buscarPeticiones() {
	// Validación del formato del campo hora:
	var hora = document.getElementById("idHoraEntrada").value;
	if(hora != null && hora != ""){
		if (!validaHora(hora)) {
			alert("El formato del campo 'Hora de entrada' debe ser: hh:mm");
			return false;
		}
	}
	// Muestra el loading:
	// mostrarLoadingBuscar(boton);
	document.formData.action = "buscarSolicitudes.action";
	document.formData.submit();
}

// CAMBIA EL ESTADO DE TODOS LOS CHECKBOX DE LOS DATOS SENSIBLES
function marcarDatosSensibles(objCheck, arrayObjChecks) {
	var marcar = objCheck.checked;
	if(!arrayObjChecks.length){ // Controlamos el caso en que solo hay un elemento...
		arrayObjChecks.checked = marcar;
	}
	for (var i=0; i< arrayObjChecks.length; i++) {
		arrayObjChecks[i].checked = marcar;
	}
}

//Metodo que cuenta el numero de checks seleccionados de Datos Sensibles
function numCheckedDatosSensibles() {
	var checks = document.getElementsByName("listaChecksDatosSensibles");
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

/************************************************************************************************************************************/
/* SCL. Coloco aquí funciones que ejecutan los javaScript que necesitan ciertas pestañas de los trámites, para liberarlas de código */
/************************************************************************************************************************************/

function toggleTasaInforme() {
	if ($("#idCheckboxInforme").attr("checked")) {
		$("#codigoTasaInformeId").parents("tr").show();
	} else {
		$("#codigoTasaInformeId").parents("tr").hide();
		$("#codigoTasaInformeId").val("-1");
	}
}

function toggleCheckTasaInforme() {
	var seleccionTipo = 0;
	if (document.getElementById('tipoTransferenciaId') != null){
		seleccionTipo = document.getElementById('tipoTransferenciaId').value;
	}

	if(seleccionTipo == "4" || seleccionTipo == "5"){
		$("#idCheckboxInforme").removeAttr("disabled");
	} else {
		$("#idCheckboxInforme").removeAttr("checked");
		$("#idCheckboxInforme").attr("disabled", "true");
		toggleTasaInforme();
	}
}

function initTasaInforme() {
	if (!$("#idCheckboxInforme").attr("disabled") && $("#codigoTasaInformeId").val()!="" && $("#codigoTasaInformeId").val()!="-1") {
		$("#idCheckboxInforme").attr("checked", "true");
	} else {
		toggleCheckTasaInforme();
	}
}

function procesando(idBoton,idImagen){
	document.getElementById(idBoton).disabled = "true";
	document.getElementById(idBoton).style.color = "#6E6E6E";
	document.getElementById(idBoton).value = "Procesando...";
	document.getElementById(idImagen).style.display = "block";
	return;
}

function deshabilitaBasti(){
	$(document).ready(function(){
		if($('#idBastidor').val()!='' && $('#idBastidor').val()!=null && $('#idBastidor').val()!='undefined'
			&& $('#idVehiculo').val()!='' && $('#idVehiculo').val()!=null && $('#idVehiculo').val()!='undefined'){
			$('#idBastidor').prop('readonly', true);
			$('#idBastidor').addClass('inputfocus2');
		}
	});
}

// Recarga el combo cuando se recarga la pagina
function recargarComboTipoVia(id_select_provincia,id_select_tipo_via,id_tipoViaSeleccionada){
	selectProvincia = document.getElementById(id_select_provincia);
	selectTipoVia = document.getElementById(id_select_tipo_via);

	if(selectProvincia != null && selectProvincia.selectedIndex > 0){
		obtenerTipoViaPorProvincia(selectProvincia, selectTipoVia, id_tipoViaSeleccionada);
	}
}

// Recarga el combo cuando se recarga la pagina
function recargarComboMunicipios(id_select_provincia,id_select_municipio,id_municipioSeleccionado){
	selectProvincia = document.getElementById(id_select_provincia);
	selectMunicipio = document.getElementById(id_select_municipio);

	if(selectProvincia != null && selectProvincia.selectedIndex > 0){
		obtenerMunicipiosPorProvincia(selectProvincia,selectMunicipio,id_municipioSeleccionado);
	}
}

// Recarga el combo cuando se recarga la pagina
// recargarComboEstacionesITV('idProvinciaEstacionItv', 'idEstacionItv', 'idEstacionItvSeleccionada');
function recargarComboEstacionesITV(idProvinciaEstacionItv,idEstacionItv,idEstacionItvSeleccionada){
	selectProvincia = document.getElementById(idProvinciaEstacionItv);
	selectEstacion = document.getElementById(idEstacionItv);

	if(selectProvincia != null && selectProvincia.selectedIndex > 0){
		obtenerEstacionesItvDeProvincia(selectProvincia,selectEstacion,idEstacionItvSeleccionada);
	}
}

function desbloqueaTasaDuplicado() {
	if (document.getElementById('duplicadoImpresoPermiso').checked){
		document.getElementById('tasaDuplicado').disabled=false;
	} else{
		document.getElementById('tasaDuplicado').disabled=true;
	}
	return true;
}

function desbloqueaPaisBaja() {
	var pais = document.getElementById('idPaisBaja');
	if (document.getElementById('motivoBaja').value == '7' || document.getElementById('motivoBaja').value == '8'){
		if(pais != null && pais != 'undefined'){
			document.getElementById('idPaisBaja').disabled=false;
		}
	}else{
		if(pais != null && pais != 'undefined'){
			document.getElementById('idPaisBaja').disabled=true;
		}
	}
	return true;
}

//	function abrirVentanaSeleccionTipo() {
//		var permiso=false;
//		if (document.getElementById("bObtenerXML")!=null) {
//			 permiso=true;
//		}
//		if (numCheckedImprimir() == 0) {
//			alert("Debe seleccionar algun tr\u00E1mite");
//			return false;
//		}
//		var left = (screen.width - 350) / 2;
//		var top = (screen.height - 200) / 2;
//		ventanaOpciones = window.open("", "ventana1", "width=360,height=100,top=" + top + ",left=" + left + ",scrollbars=NO,resizable=NO,toolbar=NO,status=NO,menubar=NO");
//		ventanaOpciones.document.write("<link href='css/estilos.css' rel='stylesheet' type='text/css' media='screen' />");
//		ventanaOpciones.document.write("<script>var padre; function setPadre(p) { padre=p; } " + "</script>");
//		ventanaOpciones.setPadre(this);
//		ventanaOpciones.document.write("<script>function mensajeAlPadre(boton) { if (boton.value=='XML') padre.exportarTramite(padre.document.getElementById('bExportarDatos'));if (boton.value=='XMLSESION') padre.exportarTramiteXML(padre.document.getElementById('bExportarDatos'));if (boton.value=='BM') padre.exportarTramiteBM(padre.document.getElementById('bExportarDatos')); this.close; }</script>");
//		ventanaOpciones.document.write("<body style='background:#ededed'><div>");
//		ventanaOpciones.document.write("<table class='subtitulo' cellspacing='0'>");
//		ventanaOpciones.document.write(	"<tr><td>Seleccione el formato para exportar:</td></tr></table>");
//		ventanaOpciones.document.write("<table align='center' border='0' cellpadding='0'>");
//		ventanaOpciones.document.write("<tr><td>&nbsp;&nbsp;&nbsp;</td>");
//		ventanaOpciones.document.write("<td><input type='button' value='XML' class='boton' onkeypress='this.onClick' onclick='mensajeAlPadre(this);'></td>");
//		if (permiso){
//			ventanaOpciones.document.write("<td><input type='button' class='boton' id='botonSesion' value='XMLSESION' onkeypress='this.onClick' onclick='mensajeAlPadre(this);'></td>");
//		}
//		ventanaOpciones.document.write("<td><input type='button' class='boton' value='BM' onkeypress='this.onClick' onclick='mensajeAlPadre(this);'></td></tr></div></body>");
//	}

//Función de prueba para detectar si el sitio es de confianza en IE
function isTrustedIE() {
	var navegador = navigator.appName;
	if (navegador == "Microsoft Internet Explorer") {
		try {
			var test=new ActiveXObject("Scripting.FileSystemObject");
		} catch(e) {
			return false;
		}
	}else
		return true;
	return true; 
}

function limpiarFormCreditos(){
	document.getElementById("contratoColegiado").value = "";
	document.getElementById("tipoCredito").value = "-1";
	document.getElementById("diaAltaIni").value = "";
	document.getElementById("mesAltaIni").value = "";
	document.getElementById("anioAltaIni").value = "";
	document.getElementById("diaAltaFin").value = "";
	document.getElementById("mesAltaFin").value = "";
	document.getElementById("anioAltaFin").value = "";
}
/*
 * Avisará que no se imprimirá el permiso de circulación en una transmisión si no se selecciona el checkbox.
 */
function sinPermiso(){
	var seleccionTipo = 0;
	if (document.getElementById('tipoTransferenciaId') != null){
		seleccionTipo = document.getElementById('tipoTransferenciaId').value;
	}

	if(seleccionTipo != "5") {
		if(!document.getElementsByName("tramiteTraficoTransmisionBean.impresionPermiso")[0].checked){
			if(!confirm("No se imprimir\u00E1 permiso circulaci\u00f3n definitivo. \u00bfEst\u00e1 usted seguro?")){
				document.getElementsByName("tramiteTraficoTransmisionBean.impresionPermiso")[0].checked = true;
			}
		}
	}
}

function tieneNumExpediente(){
	var numExp =document.getElementsByName("tramiteTraficoBean.numExpediente");

	if (numExp != null && numExp.value!=null && document.getElementById("tipoTramiteId") != null)
		document.getElementById("tipoTramiteId").disabled=true;
}

//Callback function de obtenerEstacionesItvDeProvincia
function rellenarEstacionesItvDeProvincia(req_generico_estacionItv, selectEstacionItv, idEstacionItvSeleccionada){
	selectEstacionItv.options.length = 0;

	if (req_generico_estacionItv.readyState == 4) { // Complete
		if (req_generico_estacionItv.status == 200) { // OK response
			textToSplit = req_generico_estacionItv.responseText;

			//Split the document
			returnElements=textToSplit.split("||"); 
			selectEstacionItv.options[0] = new Option("Seleccione Estacion ITV", "");
			var estacionSeleccionada = document.getElementById(idEstacionItvSeleccionada).value;
			for (var i=0; i<returnElements.length; i++ ){
				value = returnElements[i].split(";");
				selectEstacionItv.options[i+1] = new Option(value[1], value[0]);
				if(selectEstacionItv.options[i+1].value == estacionSeleccionada){
					selectEstacionItv.options[i+1].selected = true;
				}
			}
		}
	}
}

function seleccionadaEstacionItv(selectEscacionItv){
	if(selectEscacionItv.selectedIndex != 0){
		var indice = selectEscacionItv.selectedIndex;
		var valor = selectEscacionItv.options[indice].value;
		document.getElementById("idEstacionItvSeleccionada").value = valor;
		return;
	}
}

/** Obtiene las direcciones por numColegiado y NIF, en Transmisiones** */
function consultaDireccion(nif, numColegiado, numExpediente, pestania, isMatw) {
	if (null == nif) {
		alert('No se puede referenciar a ning\u00FAn nif.');
		return false;
	}
	if (isMatw === undefined) {
		var isMatw = '';
	}

	var ventana_direccion = window.open('consultaDireccionTrafico.action?nif='
			+ nif + '&numColegiado=' + numColegiado + '&numExpediente='
			+ numExpediente + '&isMatw=' + isMatw + '&pestania=' + pestania, 'popup', opciones_popup);
}

function cerrarConsultaDireccion() {
	this.close();
}

/** Obtiene las direcciones por numColegiado y NIF, en Bajas** */
function seleccionarDireccion(nif, numColegiado, pestania) {
	if (null == nif) {
		alert('No se puede referenciar a ning\u00FAn nif.');
		return false;
	}
	var ventana_direccion = window.open('inicioSeleccionarDireccion.action?nif='
			+ nif + '&numColegiado=' + numColegiado + "&pestania=" + pestania, 'popup', opciones_popup_direccion);
}

function imprimirPdfBaja() {
	document.formData.action = "imprimirConsultaTramiteTrafico.action?listaExpedientes="
			+ document.getElementById("idHiddenNumeroExpediente").value;
	document.formData.submit();
	return true;
}

function loadingGenerarXML() {
	document.getElementById("bGuardar1").disabled = "true";
	document.getElementById("bGuardar1").style.color = "#6E6E6E";
	document.getElementById("bGuardar2").disabled = "true";
	document.getElementById("bGuardar2").style.color = "#6E6E6E";
	document.getElementById("bGuardar3").disabled = "true";
	document.getElementById("bGuardar3").style.color = "#6E6E6E";
	document.getElementById("bGuardar4").disabled = "true";
	document.getElementById("bGuardar4").style.color = "#6E6E6E";
	document.getElementById("bGuardar5").disabled = "true";
	document.getElementById("bGuardar5").style.color = "#6E6E6E";
	if (document.getElementById("bGuardar6")) {
		document.getElementById("bGuardar6").disabled = "true";
		document.getElementById("bGuardar6").style.color = "#6E6E6E";
	}
	if (document.getElementById("bGuardar7")) {
		document.getElementById("bGuardar7").disabled = "true";
		document.getElementById("bGuardar7").style.color = "#6E6E6E";
	}
	document.getElementById("bGuardar8").disabled = "true";
	document.getElementById("bGuardar8").style.color = "#6E6E6E";
	document.getElementById("bGuardar9").disabled = "true";
	document.getElementById("bGuardar9").style.color = "#6E6E6E";
	document.getElementById("bGuardar10").disabled = "true";
	document.getElementById("bGuardar10").style.color = "#6E6E6E";
	document.getElementById("bDuplicar").disabled = "true";
	document.getElementById("bDuplicar").style.color = "#6E6E6E";
	if (document.getElementById("bFinalizar")) {
		document.getElementById("bFinalizar").disabled = "true";
		document.getElementById("bFinalizar").style.color = "#6E6E6E";
	}
	if (document.getElementById("bTramitacionTelematica")) {
		document.getElementById("bTramitacionTelematica").disabled = "true";
		document.getElementById("bTramitacionTelematica").style.color = "#6E6E6E";
	}
	if (document.getElementById("bImprimir")) {
		document.getElementById("bImprimir").disabled = "true";
		document.getElementById("bImprimir").style.color = "#6E6E6E";
	}

	document.getElementById("bValidar620").disabled = "true";
	document.getElementById("bValidar620").style.color = "#6E6E6E";

	document.getElementById("bGenerarXML").disabled = "true";
	document.getElementById("bGenerarXML").style.color = "#6E6E6E";
	document.getElementById("bGenerarXML").value = "Procesando";
	document.getElementById("loadingImage13").style.display = "block";
}

function unLoadingGenerarXML() {
	document.getElementById("bGenerarXML").style.color = "black";
	document.getElementById("bGenerarXML").value = "Generar XML";
	document.getElementById("loadingImage13").style.display = "none";
}

function limpiarFormConsulta() {
	if (document.getElementById("idEstadoTramite")) {
		document.getElementById("idEstadoTramite").value = -1;
	} 

	if (document.getElementById("idTipoTramite")) {
		document.getElementById("idTipoTramite").value = -1;
	}

	if (document.getElementById("numColegiado")) {
		document.getElementById("numColegiado").value = "";
	}

	if (document.getElementById("refPropia")) {
		document.getElementById("refPropia").value = "";
	}

	if (document.getElementById("numExpediente")) {
		document.getElementById("numExpediente").value = "";
	}

	if (document.getElementById("dniTitular")) {
		document.getElementById("dniTitular").value = "";
	}

	if (document.getElementById("numBastidor")) {
		document.getElementById("numBastidor").value = "";
	}

	if (document.getElementById("matricula")) {
		document.getElementById("matricula").value = "";
	}

	if (document.getElementById("nive")) {
		document.getElementById("nive").value = "";
	}
	if (document.getElementById("conNive")) {
		document.getElementById("conNive").value = "";
	}

	if (document.getElementById("tipoTasa")) {
		document.getElementById("tipoTasa").value = "";
	}

	if (document.getElementById("dniFacturacion")) {
		document.getElementById("dniFacturacion").value = "";
	}

	// Fechas

	if (document.getElementById("diaAltaIni")) {
		document.getElementById("diaAltaIni").value = "";
	}
	if (document.getElementById("mesAltaIni")) {
		document.getElementById("mesAltaIni").value = "";
	}
	if (document.getElementById("anioAltaIni")) {
		document.getElementById("anioAltaIni").value = "";
	}

	if (document.getElementById("diaAltaFin")) {
		document.getElementById("diaAltaFin").value = "";
	}
	if (document.getElementById("mesAltaFin")) {
		document.getElementById("mesAltaFin").value = "";
	}
	if (document.getElementById("anioAltaFin")) {
		document.getElementById("anioAltaFin").value = "";
	}

	if (document.getElementById("diaPresentacionIni")) {
		document.getElementById("diaPresentacionIni").value = "";
	}
	if (document.getElementById("mesPresentacionIni")) {
		document.getElementById("mesPresentacionIni").value = "";
	}
	if (document.getElementById("anioPresentacionIni")) {
		document.getElementById("anioPresentacionIni").value = "";
	}

	if (document.getElementById("diaPresentacionFin")) {
		document.getElementById("diaPresentacionFin").value = "";
	}
	if (document.getElementById("mesPresentacionFin")) {
		document.getElementById("mesPresentacionFin").value = "";
	}
	if (document.getElementById("anioPresentacionFin")) {
		document.getElementById("anioPresentacionFin").value = "";
	}

	//JPT
	if (document.getElementById("presentadoJPT")) {
		document.getElementById("presentadoJPT").value = "";
	}
	if (document.getElementById("esLiberableEEFF")) {
		document.getElementById("esLiberableEEFF").value = "";
	}
	if(document.getElementById("jefaturaProvincial")){
		document.getElementById("jefaturaProvincial").value = "";
	}
}

function clonarTramite(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaExpedientes");
	var codigos = "";
	/*if(numCheckedImprimir() > 1){
		alert("Solo se puede seleccionar un tr\u00E1mite para clonar.");
		return false;
	}*/
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
			var estadoTramite = document.getElementById('estado_' + checks[i].value).value;
			var tipoTramite= document.getElementById('tipo_' + checks[i].value).value; 
			var bastidor= document.getElementById('bastidor_' + checks[i].value).value; 

			document.getElementById("estadoTramiteSeleccionado").value = estadoTramite;
			document.getElementById("tipoTramiteSeleccionado").value = tipoTramite;
			document.getElementById("bastidorSeleccionado").value = bastidor;
		}
		i++;
		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","clonarConsultaTramiteTrafico.action");
		$('#formData').submit();
	}
}

function generarDistintivoDesdeImprDocum() {
	var valueBoton = $("#bGenerarDistintivo").val();
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "," + this.value;
			}
		}
	});

	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder generar el documento del trámite seleccionado.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar el documento para los tr\u00E1mites seleccionados?")){
		input = $("<input>").attr("type", "hidden").attr("name", "listaExpedientes").val(codigos);
		$('#formData').append($(input));
		$('#formData').attr("action", "generarDistintivoConsultaTramiteTrafico.action").trigger("submit");
	}
}

// CAMBIA EL ESTADO DE TODOS LOS CHECKBOX DE UNA DISPLAY TAG
function marcarExpedientes(objCheck, arrayObjChecks) {
	var marcar = objCheck.checked;
	if(!arrayObjChecks.length){ // Controlamos el caso en que solo hay un
								// elemento...
		arrayObjChecks.checked = marcar;
	}
	for (var i=0; i< arrayObjChecks.length; i++) {
		arrayObjChecks[i].checked = marcar;
	}

	if (marcar == true) {
		document.getElementById("bNuevaFactura").disabled = false;
		document.getElementById("comboFacturacion").disabled = false;
		document.getElementById("bNuevaFactura").style.color = "#000000";
		document.getElementById("comboFacturacion").style.color = "#000000";
	} else {
		document.getElementById("bNuevaFactura").disabled = true;
		document.getElementById("comboFacturacion").disabled = true;
		document.getElementById("bNuevaFactura").style.color = "#6E6E6E";
		document.getElementById("comboFacturacion").style.color = "#6E6E6E";
	}

}

function cargarListaMarcasVehiculo(marca, id, idHidden, matw){
	marca.processor.setWords([]);
	if(matw != null && matw == "true"){
		obtenerMarcas(marca, id, idHidden, 'MATW');
	}else{
		obtenerMarcas(marca, id, idHidden, '');
	}
}

// Recarga el combo cuando se recarga la pagina
function recargarComboPueblos(id_select_provincia, id_hidden_municipio,id_select_pueblo,id_puebloSeleccionado){
	var selectProvincia = document.getElementById(id_select_provincia);
	var hiddenMunicipio= document.getElementById(id_hidden_municipio);
	var selectPueblo = document.getElementById(id_select_pueblo);
	if(selectProvincia != null && selectProvincia.selectedIndex >0){
		if(hiddenMunicipio != null && hiddenMunicipio.value > 0){
			obtenerPueblosPorMunicipio(selectProvincia,hiddenMunicipio,selectPueblo,id_puebloSeleccionado);
		}
	}
}

//CEMA MATRICULACION
function habilitarCEMA(){
	//Si el servicio es agrícula no lleva CEM y sí lleva CEMA
	var idServicio =document.getElementById("idServicioTrafico");
	if(idServicio!=undefined && idServicio!=null && idServicio.options[idServicio.selectedIndex].value=="B06"){
		document.getElementById("idCema").disabled = false;
		 //Mantis 0002862. 11/12/2012. Gilberto.
		//Activar tambien el check de Vehículo Agricola cuando se selecciona Servicio: B06
		if (document.getElementById("vehiculoAgricola")!=undefined &&
		document.getElementById("vehiculoAgricola")!=null){
				document.getElementById("vehiculoAgricola").checked=true;
		}
		if(document.getElementById("idCem")!= null){
			document.getElementById("idCem").disabled = false;
		}
	} else {
		document.getElementById("idCema").disabled = true;
		if (document.getElementById("vehiculoAgricola")!=undefined &&
		document.getElementById("vehiculoAgricola")!=null){
				document.getElementById("vehiculoAgricola").checked=false;
		}
		if(document.getElementById("idCem")!= null){
			document.getElementById("idCem").disabled = false;
		}

	}
}

// Función que se ejecuta cuando se pulsa 'Cambiar Estados' en la consulta de trámites de tráfico
function abrirVentanaSeleccionEstados(_idBoton){
	if(numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	ventanaEstados = window.open('cargarPopUpCambioEstado.action','popup','width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
}

// Función invocada desde el padre del pop up de cambios de estados de tramites
function invokeCambiarEstado(estado) {
	ventanaEstados.close();
//	var checks = document.getElementsByName("listaChecksConsultaTramite");
//	var codigos = "";
//	var i = 0;
//	while(checks[i] != null) {
//		if(checks[i].checked) {
//			if(codigos==""){
//				codigos += checks[i].value;
//			}else{
//				codigos += "-"+ checks[i].value;
//			}
//		}
//		i++;
//	}
	mostrarLoadingConsultar(document.getElementById("bCambiarEstado"));
//	document.forms[0].action="cambiarEstadosConsultaTramiteTrafico.action?valorEstadoCambio=" + estado + "&numsExpediente=" + codigos;
//	document.forms[0].submit();
	$('#valorEstadoCambio').attr("value",estado);

	$('#formData').attr("action","cambiarEstadosConsultaTramiteTrafico.action");
	$('#formData').submit();
}

// Para mostrar el loading en los botones de consultar.
function mostrarLoadingConsultar(boton) {
	bloqueaBotonesConsulta();
	document.getElementById("bloqueLoadingConsultar").style.display = "block";
	boton.value = "Procesando..";
}

// Bloquea los botones de la pantalla de consulta
function bloqueaBotonesConsulta() {	
	$('table.acciones input').prop('disabled', 'true');
	$('table.acciones input').css('color', '#6E6E6E');
}

// Función que se ejecuta cuando se selecciona estado en el pop up de cambiar estados de trámites
function estadoSeleccionado(){
	var estado = document.getElementById("estadoTramite").value;
	if(estado == "-"){
		alert("Seleccione un estado");
		return false;
	}
	window.opener.invokeCambiarEstado(estado);
}

// DRC@29-11-2012 Incidencia Mantis: 2901
function comprobarMarcaBBDD(req_generico_marca, _marca, idHidden, checkMarca) {
	// Definicion de los siguientes estados de la llamada AJAX: 0 (uninitialized), 1 (loading), 2 (loaded), 3 (interactive), 4 (complete)
	if (req_generico_marca.readyState == 4) { // Complete
		if (req_generico_marca.status == 200) { // OK response
			var textToSplit = req_generico_marca.responseText;

			//Split the document
			var marcascodigos = textToSplit.split("[#]");
			var marcas = marcascodigos[0].split("||");
			var codigos = marcascodigos[1].split("||");

			document.getElementById(idHidden).value = codigos;

			checkMarca.style.fontWeight='bold';

			if (document.getElementById(idHidden).value != "") {
				checkMarca.style.color = '#00aa00';
				checkMarca.innerHTML = 'Marca verificada';
			} else {
				checkMarca.style.color = '#ff0000';
				checkMarca.innerHTML = 'Marca incorrecta';
			}
			return;
		}
	}
}

function cargarListaPaisesProcedencia(pais, id, idHidden){
	pais.processor.setWords([]);	
	obtenerPaisesProcedencia(pais, id, idHidden);
}
function validarPaisProcedencia(pais, idPais, listaPaises, checkPais) {
		checkPais.innerHTML = '';
		checkPais.style.fontWeight = 'normal';

		if (isNaN(pais.value.substr(0,1))) { // Caracteres Alfanumericos
			idPais.value = '';
			var letra = pais.value.substr(0,1).toUpperCase();
			var paises = eval("listaPaises.processor.words."+letra).toString();
			var arrayPaises = paises.split(",");
			var codigos = eval("listaPaises.processor.keys."+letra).toString();

			var arrayCodigos = codigos.split(",");
			checkPais.style.color = '#0000ff';
			checkPais.innerHTML = 'Verificando...';

			for(i = 0; i <= arrayPaises.length; i++){
				if(arrayPaises[i] == pais.value) {
					checkPais.style.fontWeight = 'bold';
					checkPais.style.color = '#00aa00';
					checkPais.innerHTML = 'País verificado';
					idPais.value = arrayCodigos[i];
					return;
				}
			}
			checkPais.style.fontWeight='bold';
			checkPais.style.color = '#ff0000';
			checkPais.innerHTML = 'País incorrecto';
			idPais.value = '';
		} else { //Caracters Numericos
			checkPais.style.color = '#0000ff';
			checkPais.innerHTML = 'Verificando...';
			var url = "confirmarValidezPaisesProcedenciaTraficoAjax.action?nombrePais=" + pais.value;

			var req_generico_pais = NuevoAjax();
			// Hace la llamada a Ajax
			req_generico_pais.onreadystatechange = function() {
				comprobarPaisBBDD(req_generico_pais, pais, idPais.id, checkPais);
			};
			req_generico_pais.open("POST", url, true);
			req_generico_pais.send(null);
			return;
		}
	}
	
function comprobarPaisBBDD(req_generico_pais, _pais, idHidden, checkPais) {
	// Definicion de los siguientes estados de la llamada AJAX: 0 (uninitialized), 1 (loading), 2 (loaded), 3 (interactive), 4 (complete)
	if (req_generico_pais.readyState == 4) { // Complete
		if (req_generico_pais.status == 200) { // OK response
			var textToSplit = req_generico_pais.responseText;

			//Split the document
			var paisesSiglas = textToSplit.split("[#]");
			var paises = paisesSiglas[0].split("||");
			var siglas = paisesSiglas[1].split("||");

			document.getElementById(idHidden).value = siglas;

			checkPais.style.fontWeight='bold';

			if (document.getElementById(idHidden).value != "") {
				checkPais.style.color = '#00aa00';
				checkPais.innerHTML = 'País verificado';
			} else {
				checkPais.style.color = '#ff0000';
				checkPais.innerHTML = 'País incorrecto';
			}
			return;
		}
	}
}

//Generar la llamada Ajax para paises de procedencia 
function obtenerPaisesProcedencia(pais, id, idHidden) {
	var valorPais = '';
	var url = "recuperarPaisesProcedenciaTraficoAjax.action?nombrePais=" + valorPais;

	var req_generico_pais = NuevoAjax();
	//Hace la llamada a Ajax
	req_generico_pais.onreadystatechange = function() {
		rellenarListaPaisesProcedencia(req_generico_pais, pais, id, idHidden);
	};
	req_generico_pais.open("POST", url, true);
	req_generico_pais.send(null);
}

function rellenarListaPaisesProcedencia(req_generico_pais, pais, id, idHidden) {
	if (req_generico_pais.readyState == 4) { // Complete
		if (req_generico_pais.status == 200) { // OK response
			var textToSplit = req_generico_pais.responseText.toUpperCase();
			//Split the document
			var paisesSiglas = textToSplit.split("[#]");
			var paises = paisesSiglas[0].split("||");
			var siglas = paisesSiglas[1].split("||");

			// Asignamos las palabras sugeridas a la lista de nombres de paises
			pais.processor.setWords(paises);

			// Asignamos las claves sugeridas
			pais.processor.setKeys(paises, siglas);
			// Recuperamos el nombre del país a través de la sigla cuando obtenemos el detalle de un trámite
			for (x = 0; x < siglas.length; x++) {
				if (document.getElementById(idHidden).value == siglas[x]) document.getElementById(id).value = paises[x];
			}
		}
	}
}

function muestraOcultaTabla(){
	var mostrar = document.getElementById("idDiv");
	if (mostrar.style.display == "block"){
		mostrar.style.display = "none";
	}else{
		mostrar.style.display = "block";
	}
}

function numCheckedConsultaNotificacionesSS() {
	var checks = document.getElementsByName("listaChecksNotificacion");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

//Comprueba que este seleccionado el radio de Imprimir etiquetas y permite introducir el número de etiquetas a introducir.
function controlarMostrarTipoRepresenta(){
	var mandatoGenerico = document.getElementById("MandatoGenerico");
	var mandatoEspecifico = document.getElementById("MandatoEspecifico");

	if(mandatoGenerico.checked || mandatoEspecifico.checked){
		mostrarTipoRepresenta();
	} else{
		ocultarTipoRepresenta();
	}
}

//Se muestra cuadro
function mostrarTipoRepresenta(){
	var bloqueTipoRepresenta = document.getElementById("bloqueTipoRepresenta");
	bloqueTipoRepresenta.style.display="block";
}

//Se oculta el cuadro
function ocultarTipoRepresenta(){
	var bloqueTipoRepresenta = document.getElementById("bloqueTipoRepresenta");
	bloqueTipoRepresenta.style.display="none";
}
function controlarMostrarTipoRepresentaDeclaracionET(){
	var declaracionJuradaExportacionTransito = document.getElementById("DeclaracionJuradaExportacionTransito");
	if(declaracionJuradaExportacionTransito.checked){
		mostrarTipoRepresentaDeclaracionET();
	} else{
		ocultarTipoRepresentaDeclaracionET();
	}
}

//Se muestra cuadro
function mostrarTipoRepresentaDeclaracionET(){
	var bloqueTipoRepresentaDeclaracionET = document.getElementById("bloqueTipoRepresentaDeclaracionET");
	bloqueTipoRepresentaDeclaracionET.style.display="block";
}

//Se oculta el cuadro
function ocultarTipoRepresentaDeclaracionET(){
	var bloqueTipoRepresentaDeclaracionET = document.getElementById("bloqueTipoRepresentaDeclaracionET");
	bloqueTipoRepresentaDeclaracionET.style.display="none";
}

//-----------------------
function controlarMostrarTipoRepresentaDeclaracionExFt(){
	var declaracionJuradaExtravioFicha = document.getElementById("DeclaracionJuradaExtravioFicha");
	if(declaracionJuradaExtravioFicha.checked){
		mostrarTipoRepresentaDeclaracionExtravioFicha();
	} else{
		ocultarTipoRepresentaDeclaracionExtravioFicha();
	}
}

//Se muestra cuadro
function mostrarTipoRepresentaDeclaracionExtravioFicha(){
	var bloqueTipoRepresentaDeclaracionExFt = document.getElementById("bloqueTipoRepresentaDeclaracionExFt");
	bloqueTipoRepresentaDeclaracionExFt.style.display="block";
}

//Se oculta el cuadro
function ocultarTipoRepresentaDeclaracionExtravioFicha(){
	var bloqueTipoRepresentaDeclaracionExFt = document.getElementById("bloqueTipoRepresentaDeclaracionExFt");
	bloqueTipoRepresentaDeclaracionExFt.style.display="none";
}
//-----------------------
function controlarMostrarTipoRepresentaDeclaracionExPermFt(){
	var declaracionJuradaExtravioPermisoFicha = document.getElementById("DeclaracionJuradaExtravioPermisoFicha");
	if(declaracionJuradaExtravioPermisoFicha.checked){
		mostrarTipoRepresentaDeclaracionExPermFt();
	} else{
		ocultarTipoRepresentaDeclaracionExPermFt();
	}
}

//Se muestra cuadro
function mostrarTipoRepresentaDeclaracionExPermFt(){
	var bloqueTipoRepresentaDeclaracionExPermFt = document.getElementById("bloqueTipoRepresentaDeclaracionExPermFt");
	bloqueTipoRepresentaDeclaracionExPermFt.style.display="block";
}

//Se oculta el cuadro
function ocultarTipoRepresentaDeclaracionExPermFt(){
	var bloqueTipoRepresentaDeclaracionExPermFt = document.getElementById("bloqueTipoRepresentaDeclaracionExPermFt");
	bloqueTipoRepresentaDeclaracionExPermFt.style.display="none";
}
//-----------------------

function controlarMostrarTipoRepresentaDeclaracionExPermL(){
	var declaracionJuradaExtravioPermisoLicencia = document.getElementById("DeclaracionJuradaExtravioPermisoLicencia");
	if(declaracionJuradaExtravioPermisoLicencia.checked){
		mostrarTipoRepresentaDeclaracionExPermL();
	} else{
		ocultarTipoRepresentaDeclaracionExPermL();
	}
}

//Se muestra cuadro
function mostrarTipoRepresentaDeclaracionExPermL(){
	var bloqueTipoRepresentaDeclaracionExPermL = document.getElementById("bloqueTipoRepresentaDeclaracionExPermL");
	bloqueTipoRepresentaDeclaracionExPermL.style.display="block";
}

//Se oculta el cuadro
function ocultarTipoRepresentaDeclaracionExPermL(){
	var bloqueTipoRepresentaDeclaracionExPermL = document.getElementById("bloqueTipoRepresentaDeclaracionExPermL");
	bloqueTipoRepresentaDeclaracionExPermL.style.display="none";
}
//-----------------------
function controlarMostrarTipoRepresentaDeclaracionEAPerm(){
	var declaracionJuradaEntregaAnteriorPermiso = document.getElementById("DeclaracionJuradaEntregaAnteriorPermiso");
	if(declaracionJuradaEntregaAnteriorPermiso.checked){
		mostrarTipoRepresentaDeclaracionEAPerm();
	} else{
		ocultarTipoRepresentaDeclaracionEAPerm();
	}
}

//Se muestra cuadro
function mostrarTipoRepresentaDeclaracionEAPerm(){
	var bloqueTipoRepresentaDeclaracionEAPerm = document.getElementById("bloqueTipoRepresentaDeclaracionEAPerm");
	bloqueTipoRepresentaDeclaracionEAPerm.style.display="block";
}

//Se oculta el cuadro
function ocultarTipoRepresentaDeclaracionEAPerm(){
	var bloqueTipoRepresentaDeclaracionEAPerm = document.getElementById("bloqueTipoRepresentaDeclaracionEAPerm");
	bloqueTipoRepresentaDeclaracionEAPerm.style.display="none";
}
//-----------------------

//CAMBIA EL ESTADO DE TODOS LOS CHECKBOX DE UNA DISPLAY TAG
function marcarTodos(objCheck, arrayObjChecks) {
	var marcar = objCheck.checked;
	if(!arrayObjChecks.length){ //Controlamos el caso en que solo hay un elemento...
		arrayObjChecks.checked = marcar;
	}
	for (var i=0; i< arrayObjChecks.length; i++) {
		arrayObjChecks[i].checked = marcar;
	}
}

function faConValor() {
	if (document.getElementById("idFa").value != null
			&& document.getElementById("idFa").value != "") {
		document.getElementById("idCheckFa").checked = true;
		document.getElementById("idFa").disabled = false;
		document.getElementById("idLabelFa").style.color = "black";
	}
}
function mensajeCheckBTVPermiso(){
	if(document.getElementById('checkBoxPermisoCirculacion').checked){
		alert("Al marcar esta opción, el Gestor Administrativo se compromete a presentar el documento original del Permiso de Circulación.");
	}
}
function mensajeCheckBTVFichaTecnica(){
	if(document.getElementById('idTarjeInspecc').checked){
		alert("Al marcar esta opción, el Gestor Administrativo se compromete a presentar el documento original de la Ficha técnica.");
	}
}
function mensajeCheckBTVDeclaracionJuradaExt(){
	if(document.getElementById('idDeclaracJurExt').checked){
		alert("Al marcar esta opción, el Gestor Administrativo se compromete a presentar la declaración jurada de extravío firmada.");
	}
}

function verTodasTasas(idContrato,tipoTasa,destino){
	var $dest = $("#"+destino);
	var contrato = $("#"+idContrato);
	var $tipo = $("#"+tipoTasa);
	if(contrato != null && contrato.val() != ""){
		$.ajax({
			url:"recuperarCodTasaLibresPorTipoTasaTraficoAjax.action",
			data:"idContrato="+ contrato.val() +"&codigoTasa=" +$dest.val() +"&tipoTasaSeleccionado=" +$tipo.val(),
			type:'POST',
			success: function(data){
				if(data != ""){
					$dest.find('option').remove().end().append('<option value="">Seleccione Código de Tasa</option>').val('whatever');
					var listTasas = data.split(";");
					$.each(listTasas,function(i,o){
						$dest.append($('<option>', { 
								value: listTasas[i],
								text : listTasas[i]
							}));
					});
				}
			},
			error: function() {
				alert('Ha sucedido un error a la hora de cargar las tasas.');
			}
		});
	}
}
function autorizarCertificacion(){
	$("#idNumColegiado").prop("disabled", false);
	$("#idNumExpediente").prop("disabled", false);
	$("#idDoiTitular").prop("disabled", false);
	$("#idDoiTransmitente").prop("disabled", false);
	$("#idDoiAdquiriente").prop("disabled", false);
	$("#idBastiMatri").prop("disabled", false);
	$("#idJefatura").prop("disabled", false);
	$("#idEstacionItv").prop("disabled", false);
	$("#idKilometraje").prop("disabled", false);
	$("#idPaisPreviaMatri").prop("disabled", false);
	if(confirm("Se va a proceder a AUTORIZAR la tramitación del expediente. \u00bfEst\u00e1 usted seguro?")){
		$("#formData").attr("action", "autorizarCertificacionConsultaTramiteTrafico.action").trigger("submit");
	}

}

function denegarAutorizacionCertificacion(){
	$("#idNumColegiado").prop("disabled", false);
	$("#idNumExpediente").prop("disabled", false);
	$("#idDoiTitular").prop("disabled", false);
	$("#idDoiTransmitente").prop("disabled", false);
	$("#idDoiAdquiriente").prop("disabled", false);
	$("#idBastiMatri").prop("disabled", false);
	$("#idJefatura").prop("disabled", false);
	$("#idEstacionItv").prop("disabled", false);
	$("#idKilometraje").prop("disabled", false);
	$("#idPaisPreviaMatri").prop("disabled", false);
	if(confirm("Se va a proceder a DENEGAR la autorización para la tramitación del expediente. \u00bfEst\u00e1 usted seguro?")){
		$("#formData").attr("action", "denegarAutorizacionCertificacionConsultaTramiteTrafico.action").trigger("submit");
	}
}
function cambioOption(){
	var select = document.getElementById('opciones');
	var opcion1Content = document.getElementById('opcion1-content');
	var opcion2Content = document.getElementById('opcion2-content');
	var opcion3Content = document.getElementById('opcion3-content');
	var opcion4Content = document.getElementById('opcion4-content');

	var selectedOption = select.value;

	opcion1Content.style.display = 'none';
	opcion2Content.style.display = 'none';
	opcion3Content.style.display = 'none';
	opcion4Content.style.display = 'none';

	if (selectedOption === 'opcion1') {
		opcion1Content.style.display = 'block';
	} else if (selectedOption === 'opcion2') {
		opcion2Content.style.display = 'block';
	} else if (selectedOption === 'opcion3') {
		opcion3Content.style.display = 'block';
	}else if (selectedOption === 'opcion4') {
		opcion4Content.style.display = 'block';
	}

	var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i].checked) {
			alert('Si cambia de opción en el desplegable, se borraran los check marcados. Sólo se pueden marcar check en una sola opción');
			break;
		}
	}
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i].checked) {
			checkboxes[i].checked = false;
		}
	}
}
function cambioOptionCtit(acreditacionPago){
	$('#opciones').prop('disabled', true);
	var opcion1Content = document.getElementById('opcion1-content');
	var opcion2Content = document.getElementById('opcion2-content');
	var opcion3Content = document.getElementById('opcion3-content');
	var opcion4Content = document.getElementById('opcion4-content');
	var opcion5Content = document.getElementById('opcion5-content');
	var opcion6Content = document.getElementById('opcion6-content');

	var selectedOption = "";
	var acreditarPago = acreditacionPago.value;
/*	var acreditarHerencia = acreditacionHerenciaDonacion.value;
	var acreditarSubJud = acreditacionSubastaJudicial.value;
	var valorSeleccionado = valorSelecc.value;
	var valorAdicional = valorAdic.value;*/
	if("SI" === acreditarPago){
		selectedOption ='opcion1';
		$('#opciones').val(selectedOption);
	}
/*	if("HERENCIA" === acreditarHerencia && "HERENCIA"===valorSeleccionado && "NO"===valorAdicional){
		selectedOption ='opcion2';
		$('#opciones').val(selectedOption);
	}else if("DONACION" === acreditarHerencia && "DONACION"===valorSeleccionado && "NO"===valorAdicional){
		selectedOption ='opcion3';
		$('#opciones').val(selectedOption);
	}else if("SI" === acreditarSubJud && "SUBASTA"===valorSeleccionado && "NO"===valorAdicional){
		selectedOption ='opcion5';
		$('#opciones').val(selectedOption);
	}else if("SI" === acreditarSubJud && "JUDICIAL"===valorSeleccionado && "NO"===valorAdicional){
		selectedOption ='opcion6';
		$('#opciones').val(selectedOption);
	}*/

	opcion1Content.style.display = 'none';
	opcion2Content.style.display = 'none';
	opcion3Content.style.display = 'none';
	opcion4Content.style.display = 'none';
	opcion5Content.style.display = 'none';
	opcion6Content.style.display = 'none';

	if (selectedOption === 'opcion1') {
		opcion1Content.style.display = 'block';
	}
	/* else if (selectedOption === 'opcion2') {
		opcion2Content.style.display = 'block';
	} else if (selectedOption === 'opcion3') {
		opcion3Content.style.display = 'block';
	}else if (selectedOption === 'opcion4') {
		opcion4Content.style.display = 'block';
	}else if (selectedOption === 'opcion5') {
		opcion5Content.style.display = 'block';
	}else if (selectedOption === 'opcion6') {
		opcion6Content.style.display = 'block';
	}*/

}
function cambioOptionMatw(exentoCtr){
	$('#opciones').prop('disabled', true);
	var opcion1Content = document.getElementById('opcion1-content');
	var opcion2Content = document.getElementById('opcion2-content');
	var opcion3Content = document.getElementById('opcion3-content');
	var opcion4Content = document.getElementById('opcion4-content');

	var selectedOption = "";
	var exencionCtr = exentoCtr.value;
	if("SI" === exencionCtr){
		selectedOption ='opcion1';
		$('#opciones').val(selectedOption);
	}

	opcion1Content.style.display = 'none';
	opcion2Content.style.display = 'none';
	opcion3Content.style.display = 'none';
	opcion4Content.style.display = 'none';

	if (selectedOption === 'opcion1') {
		opcion1Content.style.display = 'block';
	} else if (selectedOption === 'opcion2') {
		opcion2Content.style.display = 'block';
	} else if (selectedOption === 'opcion3') {
		opcion3Content.style.display = 'block';
	}else if (selectedOption === 'opcion4') {
		opcion4Content.style.display = 'block';
	}
}


function volverCanjes(){
	$("#formData").attr("action", "inicioGestCanjes.action");
	$('#formData').submit();
	
}

