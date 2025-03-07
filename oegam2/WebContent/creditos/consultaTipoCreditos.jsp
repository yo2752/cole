<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/creditos.js" type="text/javascript"></script>
<script type="text/javascript">

function limpiarFormTipoCreditos() {
	document.getElementById("tipoCredito").value = "";
	document.getElementById("descripcion").value = "";
}

function nuevo() {
	document.formData.action="nuevoTipoCredito.action";
	document.formData.submit();
	return true;
}
function numChecked() {
	var checks = document.getElementsByName("check");
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function modificar() {
	if(numChecked() == 0) {
		alert("Debe seleccionar un tipo de crédito.");
		return false;
	}else if(numChecked() > 1) {
		alert("Debe seleccionar un único tipo de crédito.");
		return false;
	}
	document.formData.action="modificarTipoCredito.action";
	document.formData.submit();
	
}
function eliminar() {
	if(numChecked() == 0) {
		alert("Debe seleccionar un tipo de crédito.");
		return false;
	}else if(numChecked() > 1) {
		alert("Debe seleccionar un único tipo de crédito.");
		return false;
	}

	if (confirm("¿Está seguro de que desea eliminar este tipo de crédito?")) {
		document.formData.action="eliminarTipoCredito.action";
		document.formData.submit();
	}
}
</script>

<div class="nav">
<table cellSpacing="0" cellPadding="5" width="100%">
	<tr>
		<td class="tabular">
		<span class="titulo">Créditos: Consulta de Tipo de Crédito/Trámite</span>
		</td>
	</tr>
</table>
</div>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">

	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="tipoCredito">Tipo de Cr&eacute;dito:</label>
				</td>

				<td align="left" >
					<s:textfield name="tipoCreditosBean.tipoCredito" id="tipoCredito"
							onblur="this.value=this.value.toUpperCase(); this.className='input2';"
							onfocus="this.className='inputfocus';" size="10" maxlength="4"
							onkeyup="this.value=this.value.toUpperCase()" />
				</td>
			</tr>
			<tr><!--

			--><td align="left" nowrap="nowrap">
					<label for="descripcion">Descripci&oacute;n:</label>
				</td>
				<td align="left" >
					<s:textfield name="tipoCreditosBean.descripcion" id="descripcion"
							onblur="this.value=this.value.toUpperCase(); this.className='input2';"
							onfocus="this.className='inputfocus';" size="50" maxlength="75"
							onkeyup="this.value=this.value.toUpperCase()" />
				</td>
			</tr>
		</table>

		<div id="botonBusqueda">
			<table width="100%" align="center">
				<tr align="center">
					<td>
						<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onClick="doPost('formData', 'buscarTipoCredito.action')"/>
					</td>
					<td>
						<input type="button" class="boton" name="bLimpiar" id="bLimpiar" value="Limpiar" onkeypress="this.onClick" onclick="return limpiarFormTipoCreditos();"/>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div id="resultado">
		<table class="subtitulo" cellSpacing="0">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
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
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPagina"
									value="resultadosPorPagina"
									title="Resultados por página"
									onchange="cambiarElementosPorPaginaConsulta('navegarTipoCredito.action', 'displayTagDivConsulta', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#displayTagDivConsulta").displayTagAjax();
		});
	</script>

	<div id="displayTagDivConculta" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*"
				requestURI="navegarTipoCredito.action"
				class="tablacoin"
				summary="Listado de Tipo Credito"
				cellspacing="0" cellpadding="0" uid="lista">

			<display:column property="tipoCredito" title="Tipo de Crédito"
				sortable="true" headerClass="sortable"
				href="detalleTipoCredito.action"
				defaultorder="descending"
				paramId="tipoCredito"
				paramProperty="tipoCredito" />

			<display:column property="importe" title="Importe"
				sortable="true" headerClass="sortable"
				defaultorder="descending"/>

			<display:column property="descripcion" title="Descripción"
				sortable="true" headerClass="sortable"
				defaultorder="descending"/>

			<display:column title="Incremental/Decremental"
				sortProperty="increDecre" sortable="true" headerClass="sortable"
				defaultorder="descending" style="color:red">
				<s:property
					value="%{@escrituras.utiles.enumerados.EstadoTramiteCredito@convertirTexto(#attr.lista.increDecre)}" />
			</display:column>

			<display:column style="width:2%">
				<input type="checkbox" name="check" id="check<s:property value="#attr.lista.tipoCredito"/>"
					value='<s:property value="#attr.lista.tipoCredito"/>' />
			</display:column>

		</display:table>
	</div>

	<table class="acciones">
		<tr>
			<td>
				<div>
					&nbsp;
					<input type="button" class="boton" name="bBloquear" id="bBloquear" value="Nuevo" onClick="return nuevo();" onKeyPress="this.onClick" />
					&nbsp;
					<s:if test="%{lista.getFullListSize()>0}">
						<input type="button" class="boton" name="bModificar" id="bModificar" value="Modificar" onClick="return modificar();" onKeyPress="this.onClick" />
						&nbsp;
						<input type="button" class="boton" name="bEliminar" id="bEliminar" value="Eliminar" onClick="return eliminar();" onKeyPress="this.onClick" />
					</s:if>
				</div>
			</td>
		</tr>
	</table>
	<s:hidden key="tipoCredito"/>

</s:form>