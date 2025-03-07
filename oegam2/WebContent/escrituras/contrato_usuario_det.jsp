<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript">
function volver(){		
	window.history.back();		
}

</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Detalle del usuario</span></td>
		</tr>
	</table>
</div>

<s:form method="post" name="formData" id="formData">
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Datos del usuario</td>
	</tr>
</table>

<table width="85%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
	<tr>
		<td align="left">
			<label for="estado">Estado</label>
			<s:textfield name="#session.usuarioEstado" id="estado"  cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<label for="nif">NIF / NIE</label>
			<s:textfield name="nifUsuario" id="nif" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left">
			<label for="anagrama">Anagrama Usuario</label>
			<s:textfield name="anagramaUsuario" id="anagrama" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left">
			<label for="apellidosNombre">Apellidos y Nombre</label>
			<s:textfield name="apellidosNombreUsuario" id="apellidosNombre" cssClass="inputview" readonly="true" size="50" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left">
			<label for="correoElectronico">Correo electr&oacute;nico</label>
			<s:textfield name="correoElectronicoUsuario" id="correoElectronico" cssClass="inputview" readonly="true" size="50" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<label for="ultimaConexion">&Uacute;ltima Conexi&oacute;n</label>
			<s:textfield name="ultimaConexionUsuario"  id="ultimaConexion" cssClass="inputview" readonly="true" size="50" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<label for="fechaRenovacion">fecha renovacion</label>
			<s:textfield name="fechaRenovacionUsuario"  id="fechaRemovacion" cssClass="inputview" readonly="true" size="50" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<label for="numColegiado">numero Colegiado</label>
			<s:textfield name="numColegiadoUsuario"  id="numColegiado" cssClass="inputview" readonly="true" size="4" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	
	
	
</table>
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Datos del contrato Asociado al usuario</td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label style="text-aling:left;" for="estadoContrato" >Estado Contrato</label>
			<s:textfield  name="#session.estadoUsuario" id="estado"  cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
		<label for="fecha_Inicio">fecha Inicio</label>			                        					                      		
		<s:textfield name="#session.detalleContratoUser.fecha_Inicio" id="fecha_Inicio" cssClass="inputview"  readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
									
	</tr>
	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="cif">CIF</label>
			<s:textfield name="#session.detalleContratoUser.cif" id="cif" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>

	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="razonSocial">Raz&oacute;n Social</label>
			<s:textfield name="#session.detalleContratoUser.razon_Social" id="razonSocial" cssClass="inputview" size="50" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td>
			<table>
			<tr>
			<td  align="left" nowrap="nowrap">
				<label for="tipoVia">Tipo de v&iacute;a</label>
				<s:textfield name="#session.detalleContratoUser.nombre" id="tipoVia" cssClass="inputview" size="10" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="via">Nombre v&iacute;a p&uacute;blica</label>
				<s:textfield name="#session.detalleContratoUser.via" id="via" size="40" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="numero">N&uacute;mero</label>
				<s:textfield name="#session.detalleContratoUser.numero" id="numero" size="5" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="letra">Letra</label>
				<s:textfield name="#session.detalleContratoUser.letra" id="letra" size="5" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="escalera">Escalera</label>
				<s:textfield name="#session.detalleContratoUser.escalera" id="escalera" size="5" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="piso">Piso</label>
				<s:textfield name="#session.detalleContratoUser.piso" id="piso" size="5"cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="puerta">Puerta</label>
				<s:textfield name="#session.detalleContratoUser.puerta" id="puerta" size="5" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="municipio">Localidad/Municipio</label>
			<s:textfield name="#session.detalleContratoUser.municipio" id="municipio" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="provincia">Provincia</label>
			<s:textfield name="#session.detalleContratoUser.provincia" id="provincia" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="codPostal">C&oacute;d Postal</label>
			<s:textfield name="#session.detalleContratoUser.cod_Postal" id="codPostal" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="telefono">Tel&eacute;fono</label>
			<s:textfield name="#session.detalleContratoUser.telefono" id="telefono" cssClass="inputview" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronico">Correo Electr&oacute;nico</label>
			<s:textfield name="#session.detalleContratoUser.correo_Electronico" id="correoElectronico" cssClass="inputview" size="50" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="nombreColegio">Colegio</label>
			<s:textfield name="#session.nombreColegio" id="nombreColegio" cssClass="inputview" size="50" readonly="true" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
		</td>
	</tr>
</table>
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>permisos contrato/usuario </td>
	</tr>
</table>
<display:table name="listafunciones" excludedParams="*"
		requestURI="loadFuncionesContratos.action"
		class="tablacoi"   
		summary="Listado de Funciones"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
		
		
		<display:column title="codigo Aplicacion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"			
			style="width:20%;text-align:left"> 
			 <s:iterator var="counter" begin="0" end="#attr.listaContatosTable.nivel" >
    			<img  src="img/activo.gif" />
 			</s:iterator>
			${listaContatosTable.codigo_Aplicacion}
		</display:column>
				
       <display:column  title="Provincia Asociada"
			sortProperty="id_Provincia" sortable="false" headerClass="sortable"											
			defaultorder="descending"				
			style="width:4%" >
			<s:property 
				value="%{@escrituras.utiles.modelo.UtilesModelo@convertirIdToProvincia(#attr.listaContratosTable.id_Provincia)}" />
				
			<!--
				value="%{@org.gestoresmadrid.core.model.enumerados.Estado@convertirIdToProvincia(#attr.listaContratosTable.id_Provincia)}" 	  
			-->	
			
			</display:column>
	
		
		<display:column property="codigo_Funcion" title="codigo Funcion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			<display:column property="desc_Funcion" title="desc Funcion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:6%" />
			
			<display:column property="codigo_Funcion_Padre" title="codigo Funcion Padre"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
						
			
			<display:column property="url" title="url"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" maxLength="25"/>
			
			<display:column property="nivel" title="nivel"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			<display:column property="orden" title="orden"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			<display:column property="tipo" title="tipo"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			
			
			
			
			
		    <display:column title=""  style="width:4%">
		    	<input 	type="checkbox" name="asignadasFuncionesUsuario" 
      					value="${listaContatosTable.codigo_Funcion}" 
      					class="checkbox"  disabled="disabled"
      					<s:if test="%{#attr.listaContatosTable.asignada==1}">
      					checked=checked
      					</s:if>
      					>
		   
		   </display:column>
</display:table>
	

<table class="acciones">
	<tr>
		<td>
			<input type="button" class="boton" name="bCancelar" id="bCancelar" value="Volver" onClick="return volver();" onKeyPress="this.onClick" />
		</td>
		
	</tr>
</table>
<s:hidden key="idContrato"/>
</s:form>

