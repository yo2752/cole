var urlObtieneLogs = "recuperarLogs.action";

function cargarListaFicherosLogs(select_maquina,id_select_fichero,id_maquinaSeleccionado){
	document.getElementById(id_maquinaSeleccionado).value = "-1";
	obtenerFicheroLogPorMaquina(select_maquina,document.getElementById(id_select_fichero),id_maquinaSeleccionado);
}

function obtenerFicheroLogPorMaquina(selectMaquina,selectFichero,id_maquinaSeleccionada){
	
	if(selectMaquina.selectedIndex==0){
		selectFichero.length = 0;
		selectFichero.options[0] = new Option("Seleccione M\u00e1quina", "");
		return;
	}
	selectedOption = selectMaquina.options[selectMaquina.selectedIndex].value;
	url=urlObtieneLogs;
	var valores="ipMaquina="+selectedOption;
	var req_generico_fichero = NuevoAjax();
	req_generico_fichero.onreadystatechange = function () { 
				rellenarListafichero(req_generico_fichero,selectFichero,id_maquinaSeleccionada);
			};
		req_generico_fichero.open("POST", url, true);			
		req_generico_fichero.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		req_generico_fichero.send(valores);			
	}

function rellenarListafichero(req_generico_fichero,selectFichero,id_maquinaSeleccionada){
	selectFichero.options.length = 0;
	
	if (req_generico_fichero.readyState == 4) { 
		if (req_generico_fichero.status == 200) { 
			textToSplit = req_generico_fichero.responseText;
			
			returnElements=textToSplit.split("||");
					
			selectFichero.options[0] = new Option("Seleccione Fichero", "");
			var maquinaSeleccionada = document.getElementById(id_maquinaSeleccionada).value;
			for ( var i=0; i<returnElements.length; i++ ){
				 value = returnElements[i].split(";");
				 selectFichero.options[i+1] = new Option(value[0], value[1]);
				if(selectFichero.options[i+1].value == maquinaSeleccionada){
					selectFichero.options[i+1].selected = true;					
				}
			}
			
		}
	}
}

function seleccionarCampo(idselectCampoSeleccionado, idCampo){
	document.getElementById(idselectCampoSeleccionado).value = document.getElementById(idCampo).value; 
}