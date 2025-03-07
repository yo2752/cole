<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/creditos.js" type="text/javascript"></script>

<script type="text/javascript">
	function volver() {
		location.href="inicioConsultaColegiado.action";
		return false;
	}

	function guardar() {
		if (!confirm("¿Realmente desea cargar los creditos?")) {
			return false;
		}

		var check = document.getElementsByName("check");
		var tipoCreditoCantidad = '';
		var i = 0;
		while (check[i] != null) {
			var value = check[i].value;
			if (value != null && value != '') {
				if (tipoCreditoCantidad != '') {
					tipoCreditoCantidad = tipoCreditoCantidad + "," + value + ";" + check[i].id;
				} else {
					tipoCreditoCantidad = value + ";" + check[i].id;
				}
			}
			i++;
		}

		document.formData.action="guardarConsultaCreditoCargar.action?tipoCreditoCantidad="+tipoCreditoCantidad;
		document.formData.submit();
	}
</script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Créditos: Cargar Créditos asociado al colegiado  <s:property value="idContratoProvincia"/></span>
				</td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../../includes/erroresYMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">

	<s:hidden name="idContratoProvincia" id="idContratoProvincia" />
	<s:hidden name="idContrato"/>

	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	</div>

	<div class="divScroll">
		<display:table name="listaCreditos" excludedParams="*" class="tablacoin" summary="Listado de Creditos" cellspacing="0" cellpadding="0" uid="listaCreditosTable">

			<display:column title="Tipo de Crédito" sortable="false" headerClass="sortable" defaultorder="descending" style="width:4%">
				<a href="javascript:onClick=detalleHistorico('${listaCreditosTable.tipoCredito}')">	
					<s:property value="#attr.listaCreditosTable.tipoCredito"/> 
				</a>
			</display:column>

			<display:column property="creditos" title="Créditos" sortable="false" headerClass="sortable" defaultorder="descending" style="width:4%" />

			<display:column style="width:0.2%" title="Créditos añadidos">
				<input type="text" name="check" id="<s:property value="#attr.listaCreditosTable.tipoCredito"/>" value='' onkeypress="{if (event.keyCode==13)guardar();}"/>
			</display:column>

			<display:column property="descripcionTipoCredito" title="Descripción" sortable="false" headerClass="sortable" defaultorder="descending" style="width:9%" />

		</display:table>
	</div>

	<table class="acciones">
		<tr>
			<td>
				<div>
					<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Aceptar" onClick="return guardar();" onKeyPress="this.onClick" />
					<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="return volver();" onKeyPress="this.onClick" />
				</div>
			</td>
		</tr>
	</table>
</s:form>

<script type="text/javascript">
	function detalleHistorico(tipoCredito) {
		document.formData.action="buscarConsultaHistCredito.action?historicoCreditosBean.tipoCredito=" + tipoCredito
				+ "&idContratoYProvincia=" + document.getElementById("idContratoProvincia").value
				+ "&volverCargaCredito=true";
		document.formData.submit();
		return true;
	}
</script>