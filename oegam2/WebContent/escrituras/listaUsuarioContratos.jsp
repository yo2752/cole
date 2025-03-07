<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript">

function cambiarElementosPorPagina(){

	document.location.href='loadUserNumColegiadoContratos.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}
function numChecked() {
	var checks = document.getElementsByName("idContratos");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}
function habilitar() {
	
	if(numChecked() == 0) {
		alert("Debe seleccionar un contrato.");
		return false;
	}
	if(!confirm("¿Realmente desea habilitar los contratos seleccionados?")) {
		return false;
	}
	document.forms[1].action="habilitarUsuarioContratos.action";
	document.forms[1].submit();
	return true;
	
}
function deshabilitar() {
	if(numChecked() == 0) {
		alert("Debe seleccionar un contrato.");
		return false;
	}
	if(!confirm("¿Realmente desea deshabilitar los contratos seleccionados?")) {
		return false;
	}
	document.forms[1].action="desHabilitarUsuarioContratos.action";
	document.forms[1].submit();
	return true;
}

function eliminar() {
	if(numChecked() == 0) {
		alert("Debe seleccionar un contrato.");
		return false;
	}
	if(!confirm("¿Realmente desea eliminar los contratos seleccionados?")) {
		return false;
	}
	document.forms[1].action="eliminarUsuarioContratos.action";
	document.forms[1].submit();
	return true;
}
function modificar() {
	
	if(numChecked() == 0) {
		alert("Debe seleccionar un contrato.");
		return false;
	}else if(numChecked() > 1) {
		alert("Debe seleccionar un único contrato.");
		return false;
	}
	var checks = document.getElementsByName("idContratos");
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			document.forms[1].id_Contrato.value= checks[i].value;
			alert("id_Contrato"+document.forms[1].id_Contrato.value);
			document.forms[1].action="modificarContratos.action";
			document.forms[1].submit();
			return true;
		}
		i++;
	}
}
</script>

<div class="nav">
<table cellSpacing="0" cellPadding="5" width="100%">
	<tr>
		<td class="tabular">
		<span class="titulo">Contratos: Consulta de Contratos del Usuario</span>
		</td>
	</tr>
</table>
</div>

<s:form id="formData" name="formData" action="buscarUsuarioContratos.action">

<display:table name="listaUsuarioContratos" excludedParams="*"
		requestURI="loadUserNumColegiadoContratos.action"
		class="tablacoi"   
		summary="Listado de Contratos"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
				

		<display:column property="cif" title="cif"
			sortable="false" headerClass="sortable"			
			defaultorder="descending"
			style="width:4%" />
		
		<display:column property="nombre" title="provincia"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
				
		<display:column property="razon_Social" title="Razón Social"
			sortable="false" headerClass="sortable"
			href="detalleContratos.action"
			defaultorder="descending"
			paramId="idContratoSeleccion"
			paramProperty="id_Contrato"
			style="width:8%"  />

		<display:column property="num_Colegiado" title="num_Colegiado"
			sortable="false" headerClass="sortable"
			href="colegiadoContratos.action"
			defaultorder="descending"
			paramId="idContratoSeleccion"
			paramProperty="id_Contrato"
			style="width:8%"  />
			
		       
        <display:column title="Estado"
			sortProperty="estado_Contrato" sortable="false" headerClass="sortable"
			defaultorder="descending"  style="width:8% ;color:red" >
			<s:property 
				value="%{@org.gestoresmadrid.core.model.enumerados.Estado@convertirTexto(#attr.listaContatosTable.estado_Contrato)}" />
		</display:column>
		
</display:table>


 <s:hidden key="id_Contrato"/>
 
 <s:hidden name="idContratoSeleccion"/>
</s:form>
  
