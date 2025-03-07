<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/procesos/gestionProceso.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Monitor del Gestor de Procesos</span></td>
		</tr>
	</table>
</div>
<s:form id="formData" name="formData">
		<s:hidden name="logado"/>
		<s:hidden name="username"/>
		<h3 class="formularios">Gestionar por patrones de procesos</h3>
		<table width="100%">
		<tr>
			<td align="center">
				<s:select id="idPatron" onchange="cargarProcesosPatron()" headerKey="" headerValue="Seleccionar patrón"
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaProceso@getInstance().getListaPatronesProcesos()" 
					name="patron"  onfocus="this.className='inputfocus';"
					listValue="nombreEnum" listKey="valorEnum"/>
			</tr>
			<td> 				
				<input type="button" value="Arrancar patrón" onclick="arrancarPatron()"	class="boton" />
				<input type="button" value="Parar patrón" onclick="pararPatron()"class="boton" />
			</td>
		<tr>
		</table>
		<s:if test="%{listaProcesosPatron != null && !listaProcesosPatron.isEmpty()}">
			<div id="procesosAfectados">
				<h4>Procesos que resultarán afectados</h4>
				<p id="listaProcesosPatron"></p>
			</div>
		</s:if>
	<h3 class="formularios">Lista de procesos</h3>
	<div>
		<table align="center" style="width: 90%; padding-top: 30px;">
			<tr>
				<td style="font-weight: bold; font-size: 1.1em; text-align: left;">
					&Uacute;ltima actualización&nbsp; ${fechaActualizacion}</td>
			</tr>
		</table>
	</div>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<display:table name="listaProcesos" excludedParams="*"
		decorator="org.gestoresmadrid.oegam2.view.decorator.GestorProcesosDecorator"
		list="listaProcesos" class="tablacoin" summary="Listado de Procesos" 
		style="width:90%;margin-left:auto" cellspacing="0" cellpadding="0" uid="listaProcesosTable">
  
  		<display:column property="orden" title="" media="none"/>
  
		<display:column property="descripcion" title="Descripci&oacute;n" headerClass="centro"
			style="width:30%;text-align:left;padding-left:10px;" />
	
		<display:column property="estado" title="Estado" headerClass="centro" style="width:5%;"/>
 
		<display:column property="accion" title="ACT/DES" style="text-align:center; width:10%;" headerClass="centro"/>
		
		<display:column title="Modificar" headerClass="centro" style="width:10%;">
			<input type="button" class="botonpeque" id="btnModificar<s:property value="#attr.listaProcesosTable.orden"/>" 
			onclick="javascript:modificar(<s:property value="#attr.listaProcesosTable.orden"/>)" 
				title="Modificar el número de procesos" value="Modificar"/>
		</display:column>
		
		<display:column title="MaxInt" headerClass="centro" style="width:5%;" >
			<s:textfield id="intentosMax_%{#attr.listaProcesosTable.orden}" size="2" maxlength="2" 
				value="%{#attr.listaProcesosTable.nIntentosMax}" />
		</display:column>
		
		<display:column property="fechaUltimaEjecucion"	title="&Uacute;ltima ejecución" headerClass="centro" style="width:5%;" 
			format="{0,date,dd.MM.yyyy HH:mm:ss}"/>
		
		<display:column title="Hilos" headerClass="centro" style="width:5%;">
			<s:textfield id="hilosColas_%{#attr.listaProcesosTable.orden}" size="2" maxlength="2"
			 value="%{#attr.listaProcesosTable.hilosColas}" />
		</display:column>
		
		<display:column title="HoraIni" headerClass="centro" style="width:5%;">
			<s:textfield id="horaInicio_%{#attr.listaProcesosTable.orden}" size="3" maxlength="4" 
			value="%{#attr.listaProcesosTable.horaInicio}" />
		</display:column>
		
		<display:column title="HoraFin"	headerClass="centro" style="width:5%;">
			<s:textfield id="horaFin_%{#attr.listaProcesosTable.orden}" size="3" maxlength="4" 
			value="%{#attr.listaProcesosTable.horaFin}" />
		</display:column>

		<display:column title="tOk" headerClass="centro"  style="width:5%;">
			<s:textfield id="tiempoCorrecto_%{#attr.listaProcesosTable.orden}" size="3" maxlength="4" 
			value="%{#attr.listaProcesosTable.tiempoCorrecto}" />
		</display:column>
		
		<display:column title="tRecup" headerClass="centro" style="width:5%;">
			<s:textfield id="tiempoRecuperable_%{#attr.listaProcesosTable.orden}" size="3" maxlength="4" 
			value="%{#attr.listaProcesosTable.tiempoErroneoRecuperable}" />
		</display:column>
		
		<display:column title="tNoRecup" headerClass="centro" style="width:5%;">
			<s:textfield id="tiempoNoRecuperable_%{#attr.listaProcesosTable.orden}" size="3" maxlength="4" 
			value="%{#attr.listaProcesosTable.tiempoErroneoNoRecuperable}" />
		</display:column>
		
		<display:column title="tNoDatos" headerClass="centro" style="width:5%;">
			<s:textfield id="tiempoSinDatos_%{#attr.listaProcesosTable.orden}" size="3" maxlength="4" 
			value="%{#attr.listaProcesosTable.tiempoSinDatos}" />
		</display:column>

	</display:table>
	<s:if test="%{listaProcesos != null && !listaProcesos.isEmpty()}">
		<div class="acciones center">
			<input type="button" class="boton" name="bActualizar" id="idBtnActualizar" value="Actualizar" onClick="actualizar();" />
		</div>
	</s:if>
</s:form>
