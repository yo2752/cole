<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<script src="js/genericas.js" type="text/javascript"></script>
<script type="text/javascript">
function cambiarElementosPorPagina(){

	document.location.href='navegarContratos.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

function numChecked() {
	var checks = document.getElementsByName("idUsuarios");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function habilitarUsuarios() {
	
	if(numChecked() == 0) {
		alert("Debe seleccionar un usuario.");
		return false;
	}
	if(!confirm("¿Realmente desea habilitar los usuarios seleccionados?")) {
		return false;
	}
	document.formData.action="habilitarUsuariosContratos.action";
	document.formData.submit();
	return true;
	
}

function deshabilitarUsuarios() {
	if(numChecked() == 0) {
		alert("Debe seleccionar un usuario.");
		return false;
	}
	if(!confirm("¿Realmente desea deshabilitar los usuarios seleccionados?")) {
		return false;
	}
	document.formData.action="desHabilitarUsuariosContratos.action";
	document.formData.submit();
	return true;
}
function eliminarUsuarios() {
	if(numChecked() == 0) {
		alert("Debe seleccionar un usuario.");
		return false;
	}
	if(!confirm("¿Realmente desea eliminar los usuarios seleccionados?")) {
		return false;
	}
	document.formData.action="eliminarUsuariosContratos.action";
	document.formData.submit();
	return true;
}

	
	function manteniemientoPermisos(){
		document.formData.action="manteniemientoPermisosContratos.action?idContratoSelect=";		
		document.formData.submit();
		return false;
	}

	function volver(){		
			return cancelarForm("listaContratos.action");	
	}
	function guardar() {
		
		
		if(!confirm("¿Realmente desea dar de alta a un nuevo Usuario?")) {
			return false;
		}
		document.formData.action="altaUsuarioContratos.action";
		document.formData.submit();
		return true;
		
	}
	function modificarUsuario() {
		
		if(numChecked() == 0) {
			alert("Debe seleccionar un usuario.");
			return false;
		}else if(numChecked() > 1) {
			alert("Debe seleccionar un único usuario.");
			return false;
		}
		var checks = document.getElementsByName("idUsuarios");
		
		var i = 0;
		while(checks[i] != null) {
			if(checks[i].checked) {		
					
				document.formData.id_Usuario.value= checks[i].value;					
				document.formData.action="modificarUsuarioContratos.action";
				document.formData.submit();
				return true;
			}
			i++;
		}
	}
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Detalle de contrato</span></td>
		</tr>
	</table>
</div>

<s:form method="post" name="formData" id="formData">
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
		<s:textfield name="#session.contratoDao.fecha_Inicio" id="fecha_Inicio" cssClass="inputview"  readonly="true"/>
		</td>
									
	</tr>
	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="cif">CIF</label>
			<s:textfield name="#session.contratoDao.cif" id="cif" cssClass="inputview" readonly="true"/>
		</td>
	</tr>

	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="razonSocial">Raz&oacute;n Social</label>
			<s:textfield name="#session.contratoDao.razon_Social" id="razonSocial" cssClass="inputview" size="50" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td>
			<table>
			<tr>
			<td  align="left" nowrap="nowrap">
				<label for="tipoVia">Tipo de v&iacute;a</label>
				<s:textfield name="#session.contratoDao.nombre" id="tipoVia" cssClass="inputview" size="10" readonly="true"/>
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="via">Nombre v&iacute;a p&uacute;blica</label>
				<s:textfield name="#session.contratoDao.via" id="via" size="40" cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="numero">N&uacute;mero</label>
				<s:textfield name="#session.contratoDao.numero" id="numero" size="5" cssClass="inputview" readonly="true"/>
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="letra">Letra</label>
				<s:textfield name="#session.contratoDao.letra" id="letra" size="5" cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="escalera">Escalera</label>
				<s:textfield name="#session.contratoDao.escalera" id="escalera" size="5" cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="piso">Piso</label>
				<s:textfield name="#session.contratoDao.piso" id="piso" size="5"cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="puerta">Puerta</label>
				<s:textfield name="#session.contratoDao.puerta" id="puerta" size="5" cssClass="inputview" readonly="true"/>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="municipio">Localidad/Municipio</label>
			<s:textfield name="#session.contratoDao.municipio" id="municipio" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="provincia">Provincia</label>
			<s:textfield name="#session.contratoDao.provincia" id="provincia" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="codPostal">C&oacute;d Postal</label>
			<s:textfield name="#session.contratoDao.cod_Postal" id="codPostal" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="telefono">Tel&eacute;fono</label>
			<s:textfield name="#session.contratoDao.telefono" id="telefono" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronico">Correo Electr&oacute;nico</label>
			<s:textfield name="#session.contratoDao.correo_Electronico" id="correoElectronico" cssClass="inputview" size="50" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="colegio">Colegio</label>
			<s:textfield name="#session.nombreColegio" id="colegio" cssClass="inputview" size="80" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="jefatura">Jefatura</label>
			<s:textfield name="#session.contratoJefatura" id="colegio" cssClass="inputview" size="50" readonly="true"/>
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
			<s:textfield name="#session.colegiadoDao.num_Colegiado" id="numColegiadoUsuarioPpal" cssClass="inputview" readonly="true" size="4"/>
		</td>
	</tr>
	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="nifNieUsuarioPpal">NIF/NIE</label>
			<s:textfield name="#session.colegiadoDao.nif" id="nifNieUsuarioPpal" cssClass="inputview" readonly="true" size="9"/>
		</td>
	</tr>

	<tr>
		<td align="left" nowrap="nowrap">
			<label for="apellido1RazonSocial">Apellido1</label>
			<s:textfield name="#session.colegiadoDao.apellido1_Razon_Social" id="apellido1RazonSocial" size="20" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="apellido2">Apellido2</label>
			<s:textfield name="#session.colegiadoDao.apellido2" id="apellido2" size="20" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="nombre">Nombre</label>
			<s:textfield name="#session.colegiadoDao.nombre" id="nombre" size="20" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronicoUsuario">Correo Electr&oacute;nico</label>
			<s:textfield name="#session.colegiadoDao.correo_Electronico" id="correoElectronicoUsuario" size="60" cssClass="inputview" readonly="true"/>
		</td>
		<td align="left" nowrap="nowrap">
			<label for="ncorpme">Ncorpme</label>
			<s:textfield name="#session.colegiadoDao.ncorpme" id="ncorpme" size="20" cssClass="inputview" readonly="true"/>
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
		class="tablacoi"   
		summary="Listado de Aplicaciones"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
				

		<display:column property="codigo_Aplicacion" title="codigo Aplicacion"
			sortable="false" headerClass="sortable"	
			href="manteniemientoPermisosContratos.action?idContratoSelect=${idContratoSelect}"			
			defaultorder="descending"
			paramId="codigo_AplicacionSeleccion"
			paramProperty="codigo_Aplicacion"					
			style="width:4%" />
		
		<display:column property="desc_Aplicacion" title="desc Aplicacion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			
		    <display:column title=""  style="width:4%">
		    <s:if test="%{#attr.listaContatosTable.asignada==1}">
			<input type="checkbox" name="asignada" id="check<s:property value="#attr.listaContatosTable.asignada"/>"
				value="<s:property value="#attr.listaContatosTable.asignada"/>" checked="checked" disabled="disabled" />
			</s:if>
			<s:else>
			<input type="checkbox" name="asignada" id="check<s:property value="#attr.listaContatosTable.asignada"/>"
				value="<s:property value="#attr.listaContatosTable.asignada"/>"  disabled="disabled" />
			</s:else>
		   </display:column>
</display:table>
	
		


<table class="subtitulo" cellSpacing="0" cellspacing="0" summary="Permisos">
	<tr>

		<td class="espacio"><img src="img/activo.gif" alt="-" /></td>
		<td>Lista de usuarios asociados al contrato</td>
	</tr>
</table>
<div id="fieldError">
<s:actionmessage/>									
</div>
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
		
		<display:column  style="width:5%">		
		<input type="checkbox" name="idUsuarios" id="check<s:property value="#attr.listaContatosTable.id_Usuario"/>" 
			value='<s:property value="#attr.listaContatosTable.id_Usuario"/>' />
	    </display:column>
		    
</display:table>

<div class="contenido">
					<table border="0" class="acciones">
					    <tr>
					    	<td align="center">
			                  <input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volver();" onKeyPress="this.onClick" />&nbsp;                     
					        </td>
					        
					        <td align="center">
			                  <input type="button" class="boton" name="bAceptar" id="bAceptar" value="Alta Nuevo Usuario" onClick="guardar();" onKeyPress="this.onClick" />&nbsp;                     
					        </td>
					        <s:if test="%{listaUsuarios.size>0}">
					        <td align="center">
			                 <input type="button" class="boton" name="bModificar" id="bModificar" value="Modificar Usuario" onClick="return modificarUsuario();" onKeyPress="this.onClick" />&nbsp;                 
					        </td>
					        <td align="center">
					        <input type="button" class="boton" name="bBloquear" id="bBloquear" value="HabilitarUsuarios" onClick="return habilitarUsuarios();" onKeyPress="this.onClick" />
				             &nbsp;
				             </td>
				             <td align="center">
				            <input type="button" class="boton" name="bDesbloquear" id="bDesbloquear" value="DeshabilitarUsuarios" onClick="return deshabilitarUsuarios();" onKeyPress="this.onClick" />
				               &nbsp;
				               </td>
				               <td align="center">
				            <input type="button" class="boton" name="bEliminar" id="bEliminar" value="EliminarUsuarios" onClick="return eliminarUsuarios();" onKeyPress="this.onClick" />
				            </td>
				           </s:if>
					    </tr>
					</table>
</div>			
		
<s:hidden name="idContratoSelect"  id="idContratoSelect"/>
<s:hidden name="numColegiadoUsuario" id="numColegiadoUsuario"></s:hidden>
<s:hidden name="cif"  id="cif"></s:hidden>
<s:hidden name="idContrato" id="idContrato"></s:hidden>
<s:hidden name="id_Contrato" id="id_Contrato"></s:hidden>

<s:hidden key="id_Usuario"/>
</s:form>
<s:if test="%{#session.resultBeanUsuarios.listaMensajes.size!=0}">
<script type="text/javascript">

var prueba = '<s:property value="#session.resultBeanUsuarios.listaMensajes"/>';
alert(prueba);
</script>
</s:if>

