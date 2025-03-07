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
	var req1;
	var req2;
	var primeraVez = true;
	var primeraVez1 = true;
	var primeraVez2 = true;
	function guardar(){
		
		if(!validar(idCreditos, true)) {
			return false;
		}
		
		if(!confirm("¿Realmente desea modificar el contrato?")) {
			return false;
		}
		document.formData.action="modificarDetalleContratos.action";
		document.formData.submit();
		return true;
	}
	function habilitar() {
		if(!confirm("¿Realmente desea habilitar el contrato?")) {
			return false;
		}
		document.formData.action="habilitarDetalleContratos.action";
		document.formData.submit();
		return true;
	}
	function deshabilitar() {
		if(!confirm("¿Realmente desea deshabilitar el contrato?")) {
			return false;
		}
		document.formData.action="desHabilitarDetalleContratos.action";
		document.formData.submit();
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
					if(primeraVez && selectMunicipio.options[i+1].value == '<s:property value="municipioSeleccionado"/>') {
						selectMunicipio.options[i+1].selected = true;
						primeraVez=false;
					}
				}	
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
					if(primeraVez1 && selectColegio.options[i+1].value == '<s:property value="colegioSeleccionado"/>') {
						selectColegio.options[i+1].selected = true;
						primeraVez1=false;
					}
					
				}	
			}
		}
    }

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
					if(primeraVez2 && selectJefatura.options[i+1].value == '<s:property value="jefaturaSeleccionada"/>') {
						selectJefatura.options[i+1].selected = true;
						primeraVez2=false;
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
	
	function seleccionar(){		
		alert("muniifgvtgtgtg");
		alert(document.getElementById('municipioSeleccionado'));
		selectMunicipio=document.getElementById('municipioSeleccionado') ;
		selectColegio=document.getElementById('colegioSeleccionado') ;
		selectJefatura=document.getElementById('jefaturaSeleccionada') ;
	}
	
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Modificar contrato</span></td>
		</tr>
	</table>
</div>
<div id="fieldError">
<s:actionmessage/>									
</div>
<s:form method="post" id="formData" name="formData">
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Datos del contrato</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label style="text-aling:left;" for="contratoEstado" >Estado</label>
			<s:textfield  name="#session.contratoEstado" id="contratoEstado"  cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
		<label for="fecha_Inicio">fecha Inicio</label>			                        					                      		
		<s:textfield name="contratoFechaInicio" id="fechaInicio" cssClass="inputview" readonly="true"/>
		</td>
									
	</tr>
	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="cif">CIF</label>			
			<s:textfield name="contratoCif" id="cif" onblur="this.className='input2'; calculaLetraDni('cif');" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
		</td>
	</tr>

	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="razonSocial">Raz&oacute;n Social</label>			
			<s:textfield name="contratoRazonSocial" id="razonSocial" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="70" maxlength="120"/>
		</td>
	</tr>
	<tr>
		<td>
			<table>
			<tr>
			<td  align="left" nowrap="nowrap">
				<label for="tipoVia">Tipo de v&iacute;a</label>				
				<s:select 	list="tiposVia"
							listKey="idTipoVia"
							listValue="nombre"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" 
							name="contratoIdTipoVia"
							id="idTipoVia"							
							/>
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="via">Nombre v&iacute;a p&uacute;blica</label>				
				<s:textfield name="contratoVia" id="via" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="40" maxlength="120"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="numero">N&uacute;mero</label>				
				<s:textfield name="contratoNumero" id="numero" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
			<td  align="left" nowrap="nowrap">
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
				<s:textfield name="contratoPuerta"  id="puerta" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="provincia">Provincia</label>		
			<s:select	list="provincias"
							listKey="idProvincia"
							listValue="nombre"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							name="contratoIdProvincia" 
							id="idProvincia"
							headerKey="-1"
							headerValue="Seleccione Provincia"
							onchange="obtenerMunicipios();obtenerColegios();;obtenerJefaturas()"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="municipio">Localidad/Municipio</label>	
			<select id="idMunicipio" name="municipioSeleccionado">
					<option value="-1">Seleccione Municipio</option>
				</select>
							
		
		</td>
	</tr>	
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="codPostal">C&oacute;d Postal</label>		
			<s:textfield name="contratoCodPostal" id="codPostal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" maxlength="5"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="telefono">Tel&eacute;fono</label>			
				<s:textfield name="contratoTelefono" id="telefono" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronico">Correo Electr&oacute;nico</label>			
			<s:textfield name="contratoCorreoElectronico"  id="correoElectronico" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="100" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
				<label for="idColegio">Colegios<span class="naranja">*</span></label>
				<select id="idColegio" name="colegioSeleccionado" >
					<option value="-1">Seleccione Colegio</option>
				</select>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
				<label for="idJefatura">Jefaturas<span class="naranja">*</span></label>
				<select id="idJefatura" name="jefaturaSeleccionada" >
					<option value="-1">Seleccione Jefatura</option>
				</select>
		</td>
	</tr>
</table>
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt="-"/></td>
		<td>Datos del Colegiado</td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" summary="Datos usuario principal">
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="numColegiadoUsuarioPpal">N&uacute;mero Colegiado</label>	
			<s:textfield name="contratoNumColegiadoUsuarioPpal" id="numColegiadoUsuarioPpal" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="nifNieUsuarioPpal">NIF/NIE</label>			
			<s:textfield name="contratoNifNieUsuarioPpal" id="nifNieUsuarioPpal" onblur="this.className='input2'; calculaLetraDni('nifNieUsuarioPpal');" onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
		</td>
	</tr>

	<tr>
		<td align="left" nowrap="nowrap">
			<label for="apellido1">Apellido1<span class="naranja">*</span></label>
			<s:textfield name="apellido1razonSocialCont" id="apellido1UsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="apellido2">Apellido2<span class="naranja">*</span></label>
			<s:textfield name="apellido2Cont" id="apellido2UsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="nombre">Nombre<span class="naranja">*</span></label>
			<s:textfield name="nombreCont" id="nombreUsuarioPpal" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronicoUsuario">Correo Electr&oacute;nico<span class="naranja">*</span></label>	
				<s:textfield name="usuarioCorreoElectronico" id="correoElectronicoUsuario" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
		<td align="left" nowrap="nowrap">
			<label for="NcorpmeUsuario">Ncorpme</label>
			<s:textfield name="ncorpme" id="NcorpmeUsuario" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="30"/>
		</td>
	</tr>
	
</table>

<table class="subtitulo" cellSpacing="0" cellspacing="0" summary="Permisos">
	<tr>

		<td class="espacio"><img src="img/activo.gif" alt="-" /></td>
		<td>Aplicaciones Asociadas</td>
	</tr>
</table>
<display:table name="listaAplicaciones" excludedParams="*"
		requestURI="cargarContratos.action"
		class="tablacoin"   
		summary="Listado de Aplicaciones"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
				

		<display:column property="codigo_Aplicacion" title="codigo Aplicacion"
			sortable="false" headerClass="sortable"			
			defaultorder="descending"
			style="width:4%" />
		
		<display:column property="desc_Aplicacion" title="desc Aplicacion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			
		    <display:column title=""  style="width:4%">
		    	<input 	type="checkbox" name="asignadas" 
      					value="${listaContatosTable.codigo_Aplicacion}" 
      					class="checkbox"
      					<s:if test="%{#attr.listaContatosTable.asignada==1}">
      					checked=checked
      					</s:if>
      					>
		   
		   </display:column>
</display:table>
	
		


<table class="subtitulo" cellSpacing="0" cellspacing="0" summary="Permisos">
	<tr>

		<td class="espacio"><img src="img/activo.gif" alt="-" /></td>
		<td>Lista de usuarios asociados al contrato</td>
	</tr>
</table>
<display:table name="listaUsuarios" excludedParams="*"
		requestURI="loadUserContratos.action"
		class="tablacoi"   
		summary="Listado de Aplicaciones"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
				

		<display:column property="nif" title="nif"
			sortable="false" headerClass="sortable"	
			href="detalleUsuarioContratos.action?numColegiadoUsuario=${numColegiadoUsuario}"
			defaultorder="descending"
			paramId="idUsuarioSeleccionado"
			paramProperty="id_Usuario"										
			style="width:4%" />
		
		<display:column property="apellidos_Nombre" title="apellidos_Nombre"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
		<display:column property="correo_Electronico" title="correo_Electronico"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />	
			
			
		<display:column property="ultima_Conexion" title="ultima_Conexion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />	
			
			 <display:column title="Estado Usuario"
			sortProperty="estado_Usuario" sortable="false" headerClass="sortable"
			defaultorder="descending" style="width:8% ;color:red"  >
			<s:property
				value="%{@org.gestoresmadrid.core.model.enumerados.Estado@convertirTexto(#attr.listaContatosTable.estado_Usuario)}" />
		</display:column>
		    
</display:table>

<table class="acciones">
	<tr>
		<td>
			<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Aceptar" onClick="return guardar();" onKeyPress="this.onClick"/>
			&nbsp;
			<s:if test="%{contratoEstado==@escrituras.utiles.ConstantesContratos@ESTADO_CONTRATO_HABILITADO}">
				<input type="button" class="boton" name="bDeshabilitar" id="bDeshabilitar" value="Deshabilitar" onClick="return deshabilitar();" onKeyPress="this.onClick" />
			</s:if>
			<s:elseif test="%{contratoEstado==@escrituras.utiles.ConstantesContratos@ESTADO_CONTRATO_DESHABILITADO}">
				<input type="button" class="boton" name="bHabilitar" id="bHabilitar" value="Habilitar" onClick="return habilitar();" onKeyPress="this.onClick" />
			</s:elseif>
			&nbsp;
			<input type="button" class="boton" name="bCancelar" id="bCancelar" value="Cancelar" onClick="return cancelar();" onKeyPress="this.onClick" />
		</td>
	</tr>
</table>
<s:hidden key="contrato.idContrato"/>
<s:hidden name="municipioSeleccionado" id="municipioSeleccionado"/>
<s:hidden name="contratoId" id="contratoId"/>
<!--<s:hidden name="contratoRowId" id="contratoRowId"/>-->
<!--<s:hidden name="usuarioRowId" id="usuarioRowId"/>-->
<s:hidden name="listaAplicaciones" id="listaAplicaciones"></s:hidden>
<s:hidden name="listaCodigoAplication" id="listaCodigoAplication"></s:hidden>
<s:hidden  name="numColegiadoUsuario"  id="numColegiadoUsuario"></s:hidden>


</s:form>
<script>
recargarComboMunicipios();
recargarComboColegios();
recargarComboJefaturas();
</script>

