
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script  type="text/javascript">
	
var idCreditos = new Array();
var cont = 0;
var req;
   
	function guardar(){	
		if(!validar(idCreditos, false)) {
			return false;
		}
		document.forms[0].action="guardarContratos.action";	
		document.forms[0].submit();
		return true;
	}
	function cancelar(){
		return cancelarForm("listaContratos.action");
	}
	
	function obtenerMunicipios(){
		selectProvincia = document.getElementById('idProvincia');
    
		//Sin elementos
		if(selectProvincia.selectedIndex==0){
			document.getElementById('idMunicipio').length = 0;
			document.getElementById('idMunicipio').options[0] = new Option("Seleccione Municipio", "");
			return;
		}
		selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
			
		url="updateMunicipiosContratos.action?selectedOption="+selectedOption;
  
		//Hace la llamada a ajax
		if (window.XMLHttpRequest){ // Non-IE browsers
			req = new XMLHttpRequest();
			req.onreadystatechange = rellenarMunicipios;
			req.open("POST", url, true);
			
			req.send(null);
		} else if (window.ActiveXObject) { // IE      
			req = new ActiveXObject("Microsoft.XMLHTTP");
			if (req) {
				req.onreadystatechange = rellenarMunicipios;
				req.open("POST", url, true);
				req.send();
			}
		}
	}
  
	//Callback function
	function rellenarMunicipios(){
		
		document.getElementById('idMunicipio').options.length = 0;
		
		if (req.readyState == 4) { // Complete
			if (req.status == 200) { // OK response
				textToSplit = req.responseText;
				
				//Split the document
				returnElements=textToSplit.split("||");
          
          		document.getElementById('idMunicipio').options[0] = new Option("Seleccione Municipio", "");		
				//Process each of the elements 	
				for ( var i=0; i<returnElements.length; i++ ){
					value = returnElements[i].split(";");
					document.getElementById('idMunicipio').options[i+1] = new Option(value[0], value[1]);
					if(selectMunicipio.options[i+1].value == '<s:property value="contrato.idMunicipio"/>') {
						selectMunicipio.options[i+1].selected = true;
					}
				}	
			}
		}
    }
    // Recarga el combo cuando se recarga la pagina
    function recargarComboMunicipios(){
    	selectProvincia = document.getElementById('idProvincia');
    	selectMunicipio = document.getElementById('idMunicipio');
    	
    	if(selectProvincia.selectedIndex > 0){
			obtenerMunicipios();
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
			<s:textfield name="contrato.cif" id="cif" onblur="this.className='input2'; calculaLetraDni('cif');" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="razonSocial">Raz&oacute;n Social<span class="naranja">*</span></label>
			<s:textfield name="contrato.razonSocial" id="razonSocial" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="70" maxlength="120"/>
		</td>
	</tr>
	<tr>
		<td>
			<table>
			<tr>
				<td align="left" nowrap="nowrap">
								
				<label for="idTipoVia">Tipo de v&iacute;a<span class="naranja">*</span></label>
					<s:select 	list="tiposVia"
								listKey="idTipoVia"
								listValue="nombre"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" 
								name="contrato.idTipoVia" 
								id="idTipoVia"
								/>
								
			 </td>
					
				<td align="left" nowrap="nowrap">
					<label for="via">Nombre v&iacute;a p&uacute;blica<span class="naranja">*</span></label>
					<s:textfield name="contrato.via" id="via" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="40" maxlength="120"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="numero">N&uacute;mero<span class="naranja">*</span></label>
					<s:textfield name="contrato.numero" id="numero" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="letra">Letra</label>
					<s:textfield name="contrato.letra" id="letra" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="escalera">Escalera</label>
					<s:textfield name="contrato.escalera" id="escalera" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="piso">Piso</label>
					<s:textfield name="contrato.piso" id="piso" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="puerta">Puerta</label>
					<s:textfield name="contrato.puerta" id="puerta" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
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
							name="contrato.idProvincia" 
							id="idProvincia"
							headerKey="-1"
							headerValue="Seleccione Provincia"
							onchange="obtenerMunicipios()"/>		
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="idMunicipio">Localidad/Municipio<span class="naranja">*</span></label>
				<select id="idMunicipio" name="contrato.idMunicipio" onblur="this.className='input2';" onfocus="this.className='inputfocus';">
					<option value="-1">Seleccione Municipio</option>
				</select>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="codPostal">C&oacute;d.Postal<span class="naranja">*</span></label>
				<s:textfield name="contrato.codPostal" id="codPostal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="5"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="telefono">Tel&eacute;fono</label>
				<s:textfield name="contrato.telefono" id="telefono" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronico">Correo Electr&oacute;nico<span class="naranja">*</span></label>
			<s:textfield name="contrato.correoElectronico" id="correoElectronico" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
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
			<s:textfield name="contrato.numColegiadoUsuarioPpal" id="numColegiadoUsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="nifNieUsuarioPpal">NIF/NIE<span class="naranja">*</span></label>
			<s:textfield name="contrato.nifNieUsuarioPpal" id="nifNieUsuarioPpal" onblur="this.className='input2'; calculaLetraDni('nifNieUsuarioPpal');" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="apellidosNombreUsuarioPpal">Apellidos y Nombre<span class="naranja">*</span></label>
			<s:textfield name="contrato.apellidosNombreUsuarioPpal" id="apellidosNombreUsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronicoUsuario">Correo Electr&oacute;nico</label>
			<s:textfield name="usuario.correoElectronico" id="correoElectronicoUsuario" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
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
<script> recargarComboMunicipios(); </script>

