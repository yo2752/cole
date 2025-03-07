<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/usuario.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>

<script  type="text/javascript">
	var idCreditos = new Array();
	var cont = 0;
	var req;
	var primeraVez = true;
	
	function guardar(){
		
		if(!validar(idCreditos, true)) {
			return false;
		}
		
		if(!confirm("¿Realmente desea modificar el usuario?")) {
			return false;
		}
		document.formData.action="modificarDetalleUsuarioContratos.action";
		document.formData.submit();
		return true;
	}
	function habilitar() {
		if(!confirm("¿Realmente desea habilitar el usuario?")) {
			return false;
		}
		document.formData.action="habilitarDetalleUsuarioContratos.action";
		document.formData.submit();
		return true;
	}
	function deshabilitar() {
		if(!confirm("¿Realmente desea deshabilitar el usuario?")) {
			return false;
		}
		document.formData.action="desHabilitarDetalleUsuarioContratos.action";
		document.formData.submit();
		return true;
	}
	function cancelar(){
		return cancelarForm("listaContratos.action");
	}
	
	
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Modificar usuario</span></td>
		</tr>
	</table>
</div>

<s:form method="post" id="formData" name="formData">
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Datos de usuario</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label style="text-aling:left;" for="usuarioEstado" >Estado usuario</label>
			<s:textfield  name="#session.usuarioEstado" id="usuarioEstado"  cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="usuarioNif">NIF/NIE</label>			
			<s:textfield name="usuarioNif" id="nif" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<label for="usuarioAnagrama">Anagrama Usuario</label>
			<s:textfield name="usuarioAnagrama" id="usuarioAnagrama" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="usuarioApellidosNombre">Apellidos y Nombre</label>	
			<s:textfield name="usuarioApellidosNombre" id="apellidosNombre" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="100" maxlength="100"/>
		</td>
	</tr>
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="usuarioCorreo">Correo Electr&oacute;nico</label>	
				<s:textfield name="usuarioCorreo" id="correoElectronico" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<label for="usuarioUltimaConexion">&Uacute;ltima Conexi&oacute;n</label>
			<s:textfield name="usuarioUltimaConexion"  id="usuarioUltimaConexion" cssClass="inputview" readonly="true" size="50"/>
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<label for="usuarioFechaRenovacion">Fecha renovacion</label>
			<s:textfield name="usuarioFechaRenovacion"  id="usuarioFechaRenovacion" cssClass="inputview" readonly="true" size="50"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="usuarioNumColegiado">N&uacute;mero Colegiado</label>	
			<s:textfield name="usuarioNumColegiado" id="usuarioNumColegiado" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
		
		</table>
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Datos del contrato asociado al usuario</td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label style="text-aling:left;" for="estadoContrato" >Estado Contrato</label>
			<s:textfield  name="#session.estadoUsuario" id="estadoContrato"  cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label style="text-aling:left;" for="nombreColegio" >Nombre Colegio</label>
			<s:textfield  name="#session.nombreColegio" id="nombreColegio"  cssClass="inputview" size="100" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
		<label for="fecha_Inicio">Fecha Inicio</label>			                        					                      		
		<s:textfield name="#session.detalleContratoUser.fecha_Inicio" id="fecha_Inicio" cssClass="inputview"  readonly="true"/>
		</td>
									
	</tr>
	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="cif">CIF</label>
			<s:textfield name="#session.detalleContratoUser.cif" id="cif" cssClass="inputview" readonly="true"/>
		</td>
	</tr>

	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="razonSocial">Raz&oacute;n Social</label>
			<s:textfield name="#session.detalleContratoUser.razon_Social" id="razonSocial" cssClass="inputview" size="50" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td>
			<table>
			<tr>
			<td  align="left" nowrap="nowrap">
				<label for="tipoVia">Tipo de v&iacute;a</label>
				<s:textfield name="#session.detalleContratoUser.nombre" id="tipoVia" cssClass="inputview" size="10" readonly="true"/>
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="via">Nombre v&iacute;a p&uacute;blica</label>
				<s:textfield name="#session.detalleContratoUser.via" id="via" size="40" cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="numero">N&uacute;mero</label>
				<s:textfield name="#session.detalleContratoUser.numero" id="numero" size="5" cssClass="inputview" readonly="true"/>
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="letra">Letra</label>
				<s:textfield name="#session.detalleContratoUser.letra" id="letra" size="5" cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="escalera">Escalera</label>
				<s:textfield name="#session.detalleContratoUser.escalera" id="escalera" size="5" cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="piso">Piso</label>
				<s:textfield name="#session.detalleContratoUser.piso" id="piso" size="5"cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="puerta">Puerta</label>
				<s:textfield name="#session.detalleContratoUser.puerta" id="puerta" size="5" cssClass="inputview" readonly="true"/>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="municipio">Localidad/Municipio</label>
			<s:textfield name="#session.detalleContratoUser.municipio" id="municipio" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="provincia">Provincia</label>
			<s:textfield name="#session.detalleContratoUser.provincia" id="provincia" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="codPostal">C&oacute;d Postal</label>
			<s:textfield name="#session.detalleContratoUser.cod_Postal" id="codPostal" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="telefono">Tel&eacute;fono</label>
			<s:textfield name="#session.detalleContratoUser.telefono" id="telefono" cssClass="inputview" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronico">Correo Electr&oacute;nico</label>
			<s:textfield name="#session.detalleContratoUser.correo_Electronico" id="correoElectronico" cssClass="inputview" size="50" readonly="true"/>
		</td>
	</tr>
</table>
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Permisos contrato/usuario </td>
	</tr>
</table>
<div id="fieldError">
<s:actionmessage/>									
</div>
<display:table name="listafunciones" excludedParams="*"
		requestURI="loadFuncionesContratos.action"
		class="tablacoi"   
		summary="Listado de Funciones" decorator="trafico.utiles.OcultaFilas"
		cellspacing="0" cellpadding="0" uid="listaContratosTable">
		
		<display:column title="C&oacute;digo Aplicaci&oacute;n"
			sortable="false" headerClass="sortable"
			defaultorder="descending"							
			style="width:18%;text-align:left;"> 
			<s:iterator var="counter" begin="0" end="#attr.listaContratosTable.nivel - 1" >
    			<img  src="img/activo.gif" />
 			</s:iterator>
			${listaContratosTable.codigo_Aplicacion}
			<s:if test="%{#attr.listaContratosTable.esPadre}">
				<img id="desp${listaContratosTable.codigo_Funcion}"  alt="Mostrar" src="img/plus.gif" onclick="return mostrarLineas('${listaContratosTable.codigo_Funcion}');" />
			</s:if>
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
		
			<display:column property="codigo_Funcion_Padre" title="Funci&oacute;n Padre"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			<display:column property="codigo_Funcion" title="Funci&oacute;n"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			<display:column property="desc_Funcion" title="Descripci&oacute;n"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:20%" />
						
			<display:column property="url" title="URL"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" maxLength="15"/>
			
			<display:column property="nivel" title="Nivel"
			defaultorder="descending"
			style="width:4%" />
			
			<display:column property="orden" title="Orden"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			<display:column property="tipo" title="Tipo"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			
			
			
			
			
		    <display:column title=""  style="width:4%">
		    	<input 	type="checkbox" name="asignadasFuncionesUsuario" 
      					value="${listaContratosTable.codigo_Funcion}" 
      					class="checkbox"  
      					<s:if test="%{#attr.listaContratosTable.asignada==1}">
      					checked=checked
      					</s:if>
      					>
		   
		   </display:column>

</display:table>

<table class="acciones">
	<tr>
		<td>
			<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Aceptar" onClick="return guardar();" onKeyPress="this.onClick"/>
			&nbsp;
			<s:if test="%{usuarioEstado==@escrituras.utiles.ConstantesContratos@ESTADO_USUARIO_HABILITADO}">
				<input type="button" class="boton" name="bDeshabilitar" id="bDeshabilitar" value="Deshabilitar Usuario" onClick="return deshabilitar();" onKeyPress="this.onClick" />
			</s:if>
			<s:elseif test="%{usuarioEstado==@escrituras.utiles.ConstantesContratos@ESTADO_USUARIO_DESHABILITADO}">
				<input type="button" class="boton" name="bHabilitar" id="bHabilitar" value="Habilitar Usuario" onClick="return habilitar();" onKeyPress="this.onClick" />
			</s:elseif>
			&nbsp;
			<input type="button" class="boton" name="bCancelar" id="bCancelar" value="Cancelar" onClick="return cancelar();" onKeyPress="this.onClick" />
		</td>
	</tr>
</table>

<s:hidden name="id_Usuario" id="id_Usuario"></s:hidden>
<s:hidden name="cif" id="cif"></s:hidden>
<s:hidden name="id_Contrato" id="id_Contrato"></s:hidden>
<s:hidden name="codigoAppc" id="codigoAppc"></s:hidden>
<s:hidden name="codigoFun" id="codigoFun"></s:hidden>
<s:hidden name="estado"></s:hidden>

</s:form>


<script type="text/javascript">ocultaHijos();</script>