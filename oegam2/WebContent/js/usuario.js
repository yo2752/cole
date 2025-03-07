function validar(idCreditos, update){
	eliminaEspacios(new Array("nif", "apellidosNombre", "correoElectronico"));
	if(	
		
		campoVacio("nif") ||
		campoVacio("apellidosNombre")||
		campoVacio("correoElectronico")){
			alert("Debe rellenar todos los campos obligatorios.\nPor favor repase los datos.");
			return false;
	}

	if(!validaNIF(document.getElementById("nif"))){
		alert("El nif del usuario introducido no es válido.\nPor favor repase los datos.");
		return false;
	}
	if((!validaEmail(document.getElementById("correoElectronico").value)) && (document.getElementById("correoElectronico").value.length > 0)){
		alert("El email de usuario introducido no es válido.\nPor favor repase los datos.");
		return false;
	}
	
	eliminaEspacios(idCreditos);
	for(i=0;i<idCreditos.length;i++){
		if(!update){
			if(!validaNumeroPositivo(document.getElementById(idCreditos[i]).value)
					|| !validaNumeroEntero(document.getElementById(idCreditos[i]).value)){
				alert("El número de créditos debe ser un número positivo.\nPor favor repase los datos.");
				return false;
			}
		}else{
			if(!validaNumeroEntero(document.getElementById(idCreditos[i]).value)){
				alert("El número de créditos debe ser un valor numérico.\nPor favor repase los datos.");
				return false;
			}
		}
	}
	
	return true;
}

