<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script  type="text/javascript">
	
var idCreditos = new Array();
var cont = 0;
var req;
var req1;
var req2;
   
	function guardar(){	
		if(!validar(idCreditos, false)) {
			return false;
		}		
		document.formData.action="guardarContratos.action";	
		document.formData.submit();
		return true;
	}
	function cancelar(){
		return cancelarForm("listaContratos.action");
	}

	function obtenerColegios(){
		selectProvincia = document.getElementById('idProvincia');
    
		//Sin elementos
		if(selectProvincia.selectedIndex==0){
			document.getElementById('idColegio').length = 0;
			document.getElementById('idColegio').options[0] = new Option("Seleccione Colegio", "");		
			return;
		}
		selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
			
		url="updateColegiosContratos.action?selectedOption="+selectedOption;
  
		//Hace la llamada a ajax
		if (window.XMLHttpRequest){ // Non-IE browsers
			req1 = new XMLHttpRequest();
			req1.onreadystatechange = rellenarColegios;			
			req1.open("POST", url, true);
			
			req1.send(null);
		} else if (window.ActiveXObject) { // IE      
			req1= new ActiveXObject("Microsoft.XMLHTTP");
			if (req1) {
				req1.onreadystatechange = rellenarColegios;
				req1.open("POST", url, true);
				req1.send();
			}
		}
	}

	function obtenerJefaturas(){
		selectProvincia = document.getElementById('idProvincia');
    
		//Sin elementos
		if(selectProvincia.selectedIndex==0){
			document.getElementById('idJefatura').length = 0;
			document.getElementById('idJefatura').options[0] = new Option("Seleccione Jefatura", "");		
			return;
		}
		selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
			
		url="updateJefaturasContratos.action?selectedOption="+selectedOption;
  
		//Hace la llamada a ajax
		if (window.XMLHttpRequest){ // Non-IE browsers
			req2 = new XMLHttpRequest();
			req2.onreadystatechange = rellenarJefaturas;			
			req2.open("POST", url, true);
			
			req2.send(null);
		} else if (window.ActiveXObject) { // IE      
			req2= new ActiveXObject("Microsoft.XMLHTTP");
			if (req2) {
				req2.onreadystatechange = rellenarJefaturas;
				req2.open("POST", url, true);
				req2.send();
			}
		}
	}
   
    //Callback function
	function rellenarColegios(){
		
		document.getElementById('idColegio').options.length = 0;
		
		if (req1.readyState == 4) { // Complete
			if (req1.status == 200) { // OK response
				textToSplit = req1.responseText;
				//Split the document
				returnElements=textToSplit.split("||");                
          		document.getElementById('idColegio').options[0] = new Option("Seleccione Colegio", "");	          			
				//Process each of the elements 	
				for ( var i=0; i<returnElements.length; i++ ){
					value = returnElements[i].split(";");					
					document.getElementById('idColegio').options[i+1] = new Option(value[0], value[1]);					
					if(selectColegio.options[i+1].value == '<s:property value="contratoIdColegio"/>') {
						selectColegio.options[i+1].selected = true;
					}
					
				}	
			}
		}
    }

	//Callback function
	function rellenarJefaturas(){
		
		document.getElementById('idJefatura').options.length = 0;
		
		if (req2.readyState == 4) { // Complete
			if (req2.status == 200) { // OK response
				textToSplit = req2.responseText;
				//Split the document
				returnElements=textToSplit.split("||");                
          		document.getElementById('idJefatura').options[0] = new Option("Seleccione Jefatura", "");	          			
				//Process each of the elements 	
				for ( var i=0; i<returnElements.length; i++ ){
					value = returnElements[i].split(";");					
					document.getElementById('idJefatura').options[i+1] = new Option(value[0], value[1]);					
					if(selectJefatura.options[i+1].value == '<s:property value="contratoIdJefatura"/>') {
						selectJefatura.options[i+1].selected = true;
					}
					
				}	
			}
		}
    }
    
    // Recarga el combo cuando se recarga la pagina
    function recargarComboColegios(){
    	selectProvincia = document.getElementById('idProvincia');
    	selectColegio = document.getElementById('idColegio');    	
    	if(selectProvincia.selectedIndex > 0){
    		obtenerColegios();
		}
	}

 // Recarga el combo cuando se recarga la pagina
    function recargarComboJefaturas(){
    	selectProvincia = document.getElementById('idProvincia');
    	selectJefatura = document.getElementById('idJefatura');    	
    	if(selectProvincia.selectedIndex > 0){
    		obtenerJefaturas();
		}
	}
</script>
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Nuevo contrato
			</span></td>
		</tr>
	</table>
</div>

<s:form method="post" id="formData" name="formData" >
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Datos del contrato</td>
	</tr>
</table>
<div id="fieldError">
<s:actionmessage/>									
</div>
<table width="85%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="cif">CIF<span class="naranja">*</span></label>
			<s:textfield name="contratoCif" id="cif" onblur="this.className='input2'; calculaLetraDni('cif');" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="razonSocial">Raz&oacute;n Social<span class="naranja">*</span></label>
			<s:textfield name="contratoRazonSocial" id="razonSocial" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="70" maxlength="120"/>
		</td>
	</tr>
	<tr>
		<td>
			<table>
			<tr>
			
				<td align="left" nowrap="nowrap">
	   				<label for="idTipoVia">Tipo de vía: </label>
		         	<select id="idTipoVia" 
							onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"
							onchange="seleccionarCampo('idTipoViaSeleccionada','idTipoVia');" >
							<option value="-1">Seleccione Tipo Via</option>
					</select>
			    	<s:hidden id="idTipoViaSeleccionada"
			    		name="contratoIdTipoVia"/>
	         	
	       		</td>
			
				<td align="left" nowrap="nowrap">
					<label for="via">Nombre v&iacute;a p&uacute;blica<span class="naranja">*</span></label>
					<s:textfield name="contratoVia" id="via" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="40" maxlength="120"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="numero">N&uacute;mero<span class="naranja">*</span></label>
					<s:textfield name="contratoNumero" id="numero" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="letra">Letra</label>
					<s:textfield name="contratoLetra" id="letra" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="escalera">Escalera</label>
					<s:textfield name="contratoEscalera" id="escalera" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="piso">Piso</label>
					<s:textfield name="contratoPiso" id="piso" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="puerta">Puerta</label>
					<s:textfield name="contratoPuerta" id="puerta" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
		<table>
		<tr>
			<td align="left" nowrap="nowrap">
								<label for="idProvincia">Provincia<span class="naranja">*</span></label>
				<s:select	list="provincias"
							listKey="idProvincia"
							listValue="nombre"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							name="contratoIdProvincia" 
							id="idProvincia"
							headerKey="-1"
							headerValue="Seleccione Provincia"
							onchange="
							cargarListaMunicipios(this,'idMunicipio','municipioSeleccionado');
							cargarListaTipoVia(this,'idTipoVia','idTipoViaSeleccionada');
							obtenerColegios();
							obtenerJefaturas();"/>		
			</td>
			
		
			
			<td align="left" nowrap="nowrap">
				<label for="idMunicipio">Municipio<span class="naranja">*</span></label>
					<select id="idMunicipio" 
					onchange="seleccionarCampo('municipioSeleccionado','idMunicipio');" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" >
					<option value="-1">Seleccione Municipio</option>
					</select>						  
		    		<s:hidden id="municipioSeleccionado" name="contratoIdMunicipio"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="codPostal">C&oacute;d.Postal<span class="naranja">*</span></label>
				<s:textfield name="contratoCodPostal" id="codPostal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="telefono">Tel&eacute;fono</label>
				<s:textfield name="contratoTelefono" id="telefono" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap" >
			<label for="correoElectronico">Correo Electr&oacute;nico<span class="naranja">*</span></label>
			<s:textfield name="contratoCorreoElectronico" id="correoElectronico" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap" colspan="1">
				<label for="idColegio">Colegios<span class="naranja">*</span></label>
				<select id="idColegio" name="contratoIdColegio" onblur="this.className='input2';" onfocus="this.className='inputfocus';">
					<option value="-1">Seleccione Colegio</option>
				</select>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap" colspan="1">
				<label for="idJefatura">Jefaturas<span class="naranja">*</span></label>
				<select id="idJefatura" name="contratoJefatura" >
					<option value="-1">Seleccione Jefatura</option>
				</select>
		</td>
	</tr>
</table>

<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>

		<td class="espacio"><img src="img/activo.gif" alt="-"/></td>
		<td>Datos del colegiado</td>
	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" summary="Datos usuario principal">
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="numColegiadoUsuarioPpal">N&uacute;mero Colegiado<span class="naranja">*</span></label>
			<s:textfield name="contratoNumColegiadoUsuarioPpal" id="numColegiadoUsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="nifNieUsuarioPpal">NIF/NIE<span class="naranja">*</span></label>
			<s:textfield name="contratoNifNieUsuarioPpal" id="nifNieUsuarioPpal" onblur="this.className='input2'; calculaLetraDni('nifNieUsuarioPpal');" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
		</td>
	</tr>
	<!--<tr>
		<td align="left" nowrap="nowrap">
			<label for="apellidosNombreUsuarioPpal">Apellidos y Nombre<span class="naranja">*</span></label>
			<s:textfield name="contratoApellidosNombreUsuarioPpal" id="apellidosNombreUsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	--><tr>
		<td align="left" nowrap="nowrap">
			<label for="apellido1">Apellido1<span class="naranja">*</span></label>
			<s:textfield name="contratoApellidos1UsuarioPpal" id="apellido1UsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="apellido2">Apellido2<span class="naranja">*</span></label>
			<s:textfield name="contratoApellidos2UsuarioPpal" id="apellido2UsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="nombre">Nombre<span class="naranja">*</span></label>
			<s:textfield name="contratoNombreUsuarioPpal" id="nombreUsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronicoUsuario">Correo Electr&oacute;nico<span class="naranja">*</span></label>
			<s:textfield name="usuarioCorreoElectronico" id="correoElectronicoUsuario" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
		<td align="left" nowrap="nowrap">
			<label for="NcorpmeUsuario">Ncorpme</label>
			<s:textfield name="usuarioNcorpme" id="NcorpmeUsuario" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="30"/>
		</td>
	</tr>
</table>


<table class="acciones">
	<tr>
		<td>
			<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Aceptar" onClick="return guardar();" onKeyPress="this.onClick"/>
			&nbsp;
			<input type="button" class="boton" name="bCancelar" id="bCancelar" value="Cancelar" onClick="return cancelar();" onKeyPress="this.onClick" />
		</td>
	</tr>
</table>
<s:hidden key="contrato.idContrato"/>

</s:form>
<script>

recargarComboMunicipios('idProvincia','idMunicipio','municipioSeleccionado');
recargarComboTipoVia('idProvincia','idTipoVia','idTipoViaSeleccionada');
recargarComboColegios();
recargarComboJefaturas();
</script>


