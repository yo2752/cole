function eliminaEspacios(idCampos){
	for(i=0;i<idCampos.length;i++){
		document.getElementById(idCampos[i]).value = trim (document.getElementById(idCampos[i]).value);
	}
}

function campoVacio(idCampo){
	var text = document.getElementById(idCampo).value;
	text = trim(text);
	if (text.length == 0){
		return true;
	}else{
		document.getElementById(idCampo).value = text;
		return false;
	}
}

function selectVacio(idSelect){
	var indice = document.getElementById(idSelect).selectedIndex;
	if(indice == 0){
		return true;
	}else{
		return false;
	}
}

function trim(text){
    var counter = 0, lastPosition;
    while(text.indexOf(" ", counter)==counter){
        counter++;
    }
    text = text.substr(counter);

    lastPosition = text.length - 1;
    while(text.lastIndexOf(" ", lastPosition) == lastPosition){
        lastPosition--;
    }
    text = text.substr(0, lastPosition + 1);

    return text;
}

function validaExpresionAlfanumerica(campo){
	var er = /\W/;
	return !er.test(campo);
}

function validaNumero(valor){
	valor = valor.replace(',','.');
	return !isNaN(valor);
}

function validaNumeroPositivo(valor){
	valor = valor.replace(',','.');
	if(!isNaN(valor)){
		var num = parseInt(valor);
		if(num>=0){
			return true;
		}
	}
	return false;
}

function validaNumeroEntero(valor){
	var er1 = /^(?:\+|-)?\d+$/;
	return er1.test(valor);
}

function validaEmail(email){ 
	var re  = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/; 
	if (!re.test(email)) {  
	    return false; 
	} 
	return true; 
}

function validaNIF(tbCampo){
	var nif=tbCampo.value;
	if(nif != null&&nif != ""){
		nif=nif.toUpperCase();
		tbCampo.value=nif;
		if(NIF_OK(nif)){

			var longDNI=nif.length;
			if(longDNI < 9){
			
				var resultado=nif;
				var emptyLength=9 - longDNI;
				for(i=0; i<emptyLength; i++){
					resultado="0" + resultado;
				}
				nif=resultado;
				tbCampo.value=nif;
			}
			return true;
	
		}else{
			return false;
		}
 	}
 	return true;
}
function NIF_OK(szNIF){
	if((esNumerico(szNIF.charAt(0)))||(szNIF.charAt(0)==' ')||(szNIF.charAt(0)=='K')||(szNIF.charAt(0)=='L')||(szNIF.charAt(0)=='M')
		||(szNIF.charAt(0)=='X')||(szNIF.charAt(0)=='Y')||(szNIF.charAt(0)=='Z'))
		return ChkDNI(szNIF);
	else
		return ChkCIF(szNIF);
}
function esNumerico(numero){
 	var valorNumero=parseInt(numero,10);
 	if(isNaN(valorNumero)){
			return false;
	}else{
			if(valorNumero.toString().length!=numero.length){
			valorNumero=numero;
			var i;
			for(i=0;i<numero.length;i++){
	 			var letra=valorNumero.charAt(i);
	 			if(letra>"9"||letra<"0"){
					return false
				}
			}
		}
	}
 	return true;
}
function ChkDNI(szDNI){
	var i; 
	var lDni; 
	var cLetra; 
	var resto; 
	var Letras ="TRWAGMYFPDXBNJZSQVHLCKE";
	var parteNumerica;
	var longDNI=szDNI.length;
	var auxszDNI;
	var cero='0';

	switch(szDNI.charAt(0)){
		case 'X':
			szDNI = szDNI.substring(1);
			szDNI = "0" + szDNI;
			break;
		case 'Y':
			szDNI = szDNI.substring(1);
			szDNI = "1" + szDNI;
			break;
		case 'Z':
			szDNI = szDNI.substring(1);
			szDNI = "2" + szDNI;
			break;
		case 'K':
			szDNI = szDNI.substring(1);
			szDNI = "0" + szDNI;
			break;
		case 'L':
			return true;
		case 'M':
			szDNI = szDNI.substring(1);
			szDNI = "0" + szDNI;
			break;
	}
	
 	auxszDNI=eliminarCeros(szDNI, longDNI);

 	if(szDNI.length > 9){
		return false;
 	}

 	if(longDNI < 9){
 		var resultado=szDNI;
 		var emptyLength=9 - longDNI;
 		for(i=0; i<emptyLength; i++){
 			resultado="0" + resultado;
 		}
 		szDNI=resultado;
 	}
 
 	if(szDNI.charAt(0)==' '){
 		parteNumerica=szDNI.substring(1,9);
 		if(esNumerico(parteNumerica))
 			return true;
 		}

 		parteNumerica=szDNI.substring(0,8);
 
 		if(!esNumerico(parteNumerica)){
 			return false;
 		}
 
 		var numero=parseInt(auxszDNI);
 
		if (isNaN(numero)){resto=0;}
		else{resto=numero % 23;}
		
 		cLetra=Letras.charAt(resto);

 		if(cLetra==szDNI.charAt(8)){
 			return true;
 		}else{
			return false;
	 	}
}
function ChkCIF(szCIF){
	var i;
	var Letras ="TRWAGMYFPDXBNJZSQVHLCKE";
	var parteNumerica;
	var abecedario='ABCDEFGHIJKLMNOPQRSTUVWXYZ';
	
	parteNumerica=szCIF.substring(1,8);
 	if(!esNumerico(parteNumerica))
 		return false;

 	if(esNumerico(szCIF.charAt(8))){
 		if(((szCIF.charAt(0)<'A')||(szCIF.charAt(0)>'J')) && szCIF.charAt(0)!='U' && szCIF.charAt(0)!='V' && szCIF.charAt(0)!='N'){
 			return false; 
 		}
 
		var iIndice;
		iIndice=CalculaIndiceCIF(szCIF);
		 
		iIndice=parseInt(iIndice);
		 
		iIndice=iIndice % 10;
		 
		if(szCIF.charAt(8)==iIndice){
		 	return true; 
		}else{
		 	return false;
		}
	}
	 
	 if((szCIF.charAt(0)=='S')||(szCIF.charAt(0)=='P')||(szCIF.charAt(0)=='Q')||(szCIF.charAt(0)=='R')){
		var iIndiceCIF;
		iIndiceCIF=CalculaIndiceCIF(szCIF);
		 
		if(szCIF.charAt(8)==abecedario.charAt(iIndiceCIF-1))
			return true;
		else{
			 return false;
		}
	}
	 
	if(szCIF.charAt(0)=='N' || szCIF.charAt(0)=='W'){
	 	var iIndiceCIF;
	 	iIndiceCIF=CalculaIndiceCIF(szCIF);
	 	if(szCIF.charAt(8)==abecedario.charAt(iIndiceCIF-1))
	 		return true;
	 	else{
		 	return false;
		}
	}
	 
	if((szCIF.charAt(0)=='J')||(szCIF.charAt(0)=='R')){
		var lNum;
		var Resto;
		parteNumerica=szCIF.substring(1,8);
		lNum=parseInt(parteNumerica);
		
		Resto=lNum % 23;
		if(Letras.charAt(Resto)==szCIF.charAt(8))
		 	return true;
		else{
			return false; 
		}
	}
	
	if((szCIF.charAt(0)>='A')&&(szCIF.charAt(0)<='H')){
		var iIndice;
		iIndice=CalculaIndiceCIF(szCIF);
		if(szCIF.charAt(8)==abecedario.charAt(iIndice-1))
			return true;
		else{
			return false;
		}
	}
}
function CalculaIndiceCIF(szCif){
	var Suma;
	var Resto;
	var Tmp;
	var Indice;
	var Aux;
	Suma=0;
	 
	Suma += parseInt(szCif.charAt(2));
	Suma += parseInt(szCif.charAt(4));
	Suma += parseInt(szCif.charAt(6));
	
	Tmp=((parseInt(szCif.charAt(1)))*2).toString();
	Tmp=AlineaDcha(Tmp, 2);
	Suma +=(parseInt(Tmp.charAt(0)))+(parseInt(Tmp.charAt(1)));
	
	Tmp=((szCif.charAt(3))*2).toString();
	Tmp=AlineaDcha(Tmp, 2);
	Suma +=(parseInt(Tmp.charAt(0)))+(parseInt(Tmp.charAt(1)));
	
	Tmp=((szCif.charAt(5))*2).toString();
	Tmp=AlineaDcha(Tmp, 2);
	Suma +=(parseInt(Tmp.charAt(0)))+(parseInt(Tmp.charAt(1)));
	
	Tmp=((szCif.charAt(7))*2).toString();
	Tmp=AlineaDcha(Tmp, 2);
	Suma +=(parseInt(Tmp.charAt(0)))+(parseInt(Tmp.charAt(1)));
	
	Resto=Suma % 10;
	Indice=10-Resto;
	if(Indice==0)
		Indice=10;
	return Indice;
}
function AlineaDcha(texto, posiciones){
	var longTexto=texto.length;
	for(i=1; i<=(posiciones-longTexto); i++){
		texto='0'+texto;
	}
	return texto;
}
function eliminarCeros(auxszDNI, longDNI){
	var auxLongDNI=longDNI;
	for(i=0; i<longDNI; i++){
		if(auxszDNI.charAt(0)=='0'){
			auxszDNI=auxszDNI.substring(1, auxLongDNI);
			auxLongDNI--;
		}
	}
	return auxszDNI;
}	 

function validaRangoFechas(diaI, mesI, annoI, diaF, mesF, annoF, validarFechaPosterior) {
    if(validaInputFecha(diaI, mesI, annoI, validarFechaPosterior) && validaInputFecha(diaF, mesF, annoF, validarFechaPosterior)) {
	    var Idia = parseInt(diaI.value,10);
	    var Imes = parseInt(mesI.value,10);
	    var Iano = parseInt(annoI.value,10);
	    var Fdia = parseInt(diaF.value,10);
	    var Fmes = parseInt(mesF.value,10);
	    var Fano = parseInt(annoF.value,10);
        if(Fano < Iano) {
            alert(MSG_ERR_RANGO_FECHAS);
            return false;        
        }
        else if((Fano == Iano) && (Fmes < Imes)) {
            alert(MSG_ERR_RANGO_FECHAS);
            return false;        
        }
        else if((Fano == Iano) && (Fmes == Imes) && (Fdia < Idia)) {
            alert(MSG_ERR_RANGO_FECHAS);
            return false;
        }
        else {
            return true;
        }
    }
    else return false;
}

function validaInputFecha(diaFech,mesFech,anyoFech,validarFechaPosterior) {

    var bisiesto;
    var days = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
    var fecha = diaFech.value.toString();
    var dia = parseInt(diaFech.value,10);
    var mes = parseInt(mesFech.value,10);
    var ano = parseInt(anyoFech.value,10);
    
    if (isNaN(dia)) {
        alert(ERROR_FECHA_NAN_DIA);
        diaFech.focus();
        return false;
    }
    
    if (isNaN(mes)){
        alert(ERROR_FECHA_NAN_MES);
        mesFech.focus();
        return false;
    }
    
    if (isNaN(ano)) {
        alert(ERROR_FECHA_NAN_ANYO);
        anyoFech.focus();
        return false;
    }
    
    if (anyoFech.value.length < 4) {
        alert(ERROR_FECHA_TAM_ANYO);
        anyoFech.focus();
        return false;
    }

    var limite=0 ;
    var indice;
    var fecha1 = new Date();
    var actual = Date.parse(fecha1);
    
    if (mes>12 || mes<1) {
        alert(ERROR_FECHA_MES_LIMITE);
        mesFech.focus();
        return false;
    }
    
    indice = mes--;
    
    (ano % 4 == 0) ? ((ano %100 ==0) ? ((ano % 400 ==0) ? bisiesto = true : bisiesto = false) : bisiesto = true) : bisiesto = false;
    
    if((bisiesto== true) && (indice ==2)) {
        limite = 29;
    }
    else {
        limite = days[mes];
    }
    
    if(dia<0 || dia>limite) { alert (ERROR_FECHA_DIA_LIMITE); diaFech.focus();return false;}

	if(validarFechaPosterior){
	    if(esMenorFecha(diaFech,mesFech,anyoFech)) return true;
	    else {
	        alert(ERROR_FECHA_MENOR);
	        return false;
	    }
	}else{
		return true;
	}
}

function esMenorFecha(diaFech,mesFech,anyoFech) {
    
    var fechaActual=new Date();
    var annioActual=fechaActual.getFullYear();
    var diaActual=fechaActual.getDate();
    var mesActual=fechaActual.getMonth()+1;
    
    if(anyoFech.value > annioActual) {
        anyoFech.focus();
        return false;
    }
    else if(anyoFech.value < annioActual) {
        return true;
    }
    else if(anyoFech.value==annioActual) {
        if(mesFech.value > mesActual) {
            mesFech.focus();
            return false;
        }
        else if(mesFech.value < mesActual) {
            return true;
        }else if(mesFech.value==mesActual) {
            if(diaFech.value > diaActual){
                diaFech.focus();
                return false;
            }
            else if(diaFech.value <=diaActual){
                return true;
            }
        }
    }
}

function validaCampoFecha(campo){
	var fecha = document.getElementById(campo).value;
	if(valSep(fecha)){
		validaInputFecha(fecha.substring(1,2), fecha.substring(4,5), fecha.substring(7,10));
	}else{
		return false;
	}
}

function valSep(txt){
	if((txt.length==10) && (txt.value.charAt(2) == "/") && (oTxt.value.charAt(5) == "/")){
		return true;
	}else{
		return false;
	}
}

function validarDecimalSinComas(e){
	 tecla = (document.all) ? e.keyCode : e.which;
	 if (tecla==8) return true;
	 var patron = /^[0-9]+(\.[0-9]+)?$/;
	 te = String.fromCharCode(tecla);
	 return patron.test(te);
}


function validarSN(e){
	 tecla = (document.all) ? e.keyCode : e.which;
	 if (tecla==8) return true;
	 var patron = /(^(s|sn)\b)|(^(S|SN)\b)|(^\d$)/;
	 te = String.fromCharCode(tecla);
	 return patron.test(te);
}

function validarSN2(e,elem){
	 tecla = (document.all) ? e.keyCode : e.which;
	 if (tecla==8) return true;
	 var patron = /(^(s|sn)\b)|(^(S|SN)\b)|(^\d*$)/;
	 te = String.fromCharCode(tecla);
	 return patron.test(elem.value+te);
}

function validaHora(hora){
	if(hora.length != 5){
		return false;
	}
	var trozos = new Array();
	trozos = hora.split(':');
	if(trozos.length != 2){
		return false;
	}
	var horas = parseInt(trozos[0]);
	var minutos = parseInt(trozos[1]);
	if(horas == 'NaN' || minutos == 'NaN'){
		return false;
	}
	if(horas < 0 || horas > 24){
		return false;
	}
	if(minutos < 0 || minutos > 59){
		return false;
	}
	return true;
}

