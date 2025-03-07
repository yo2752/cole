<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>
<script src="js/registradores/registradores.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Listado de financiadores</span>
			</td>
		</tr>
	</table>
</div>
<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<div>
	<%@include file="/includes/erroresYMensajes.jspf" %>
	<s:form method="post" id="formData" name="formData">
	<!-- Almacena una marca para no ejecutarse el action en caso de 'refresh' de la pagina -->
	<s:hidden name="dobleSubmit" id="dobleSubmit"/>
		<div id="busqueda">
			<table width="100%" border="0" class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap"><label for="name">Nombre: </label></td>
					<td align="left">
						<s:textfield name="consultaFinanciadoresBean.nombre" id="name"></s:textfield>
					</td>
					<td align="left" nowrap="nowrap"><label for="surname">Primer Apellido/Raz&oacute;n Social: </label></td>
					<td align="left">
						<s:textfield name="consultaFinanciadoresBean.apellido1RazonSocial" id="surname"></s:textfield>
					</td>
					<td align="left" nowrap="nowrap"><label for="surname2">Apellido 2º: </label></td>
					<td align="left">
						<s:textfield name="consultaFinanciadoresBean.apellido2" id="surname2"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label for="fiscalId">NIF/CIF: </label></td>
					<td align="left">
						<s:textfield name="consultaFinanciadoresBean.nif" id="fiscalId" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsulta" id="bBuscar" value="Consultar"  onkeypress="this.onClick" onclick="return buscarFinanciadores()" />			
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarFormulario();" value="Limpiar"/>		
		</div>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
							</td>
							<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"
									id="idResultadosPorPagina" value="resultadosPorPagina"
									onchange="cambiarElementosPorPaginaConsulta('navegarRecuperarFinanciadores.action', 'displayTagDiv', this.value);" /> 
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
	
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>

		<div id="displayTagDiv" class="divScroll">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../includes/bloqueLoading.jspf"%>
			</div>
				<display:table name="lista" uid="row"
					requestURI="navegarRecuperarFinanciadores.action" class="tablacoin" 
					excludedParams="*"
					id="financiadores" summary="Listado de financiadores" cellspacing="0"
					cellpadding="0">
					<display:column title="Nombre" property="persona.nombre" sortable="true" headerClass="sortable" style="width:20%"/>
					<display:column title="1er apellido" property="persona.apellido1RazonSocial" sortable="true" headerClass="sortable" style="width:20%"/>
					<display:column title="2do apellido" property="persona.apellido2" sortable="true" headerClass="sortable" style="width:20%"/>
					<display:column title="NIF/CIF" property="nif" sortable="true" headerClass="sortable" style="width:20%"/>
					<display:column title="Eliminar" style="width:10%">						
						 <a href="javascript:doConfirmPost('¿Está seguro de que desea borrar este financiador?', 'formData', 'borrarRecuperarFinanciadores.action?identificador=<s:property value="#attr.financiadores.idInterviniente"/>', null);">
							Eliminar
						</a>
					</display:column>					
					<display:column style="width:3%">
						<a href="javascript:doPost('formData', 'inicioFinanciadores.action?identificador=<s:property value="#attr.financiadores.idInterviniente"/>');" >
							<img src="img/mostrar.gif" alt="Detalle" title="Detalle" />
						</a>
					</display:column>
			</display:table>
		 </div>
		 <div id="bloqueLoadingConsultarReg" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../../includes/bloqueLoading.jspf" %>
		</div>
	</s:form>
</div>
<script type="text/javascript">

	function limpiarFormulario() {
		$('#name').attr("value", "");
		$('#surname').attr("value", "");
		$('#surname2').attr("value", "");
		$('#agentId').attr("value", "");
		$('#fiscalId').attr("value", "");
	}
	
	function buscarFinanciadores() {
		// //Para que muestre el loading
		mostrarLoadingConsultarReg(document.getElementById("bBuscar"));
		document.formData.action = "buscarRecuperarFinanciadores.action";
		document.formData.submit();
		ocultarLoadingConsultarReg(document.getElementById('bBuscar'), "Buscar");
	}
	
	function mostrarLoadingConsultarReg(boton) {
		//bloqueaBotonesConsulta();
		document.getElementById("bloqueLoadingConsultarReg").style.display = "block";
		boton.value = "Procesando..";
	}
</script>