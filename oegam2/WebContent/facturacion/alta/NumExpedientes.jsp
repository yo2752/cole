 <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/facturacionBotones.js" type="text/javascript"></script>

<s:form method="post" id="formData" name="formData" action="GestionFacturar">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expedientes</title>
</head>
	<body>

<display:table name="listaExpedientesBean" excludedParams="*"
					class="tablacoin"
					uid="mitablaExpedientes"
					cellspacing="0" cellpadding="0"
					summary="Listado de expedientes"
					style="width:50%; align:left;"
					>
							
	<display:column property="numExpediente" title="Num Expediente"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%;" 
			paramId="numExpediente" 	 
			/>	
<%--  	<display:column	class="fila04" >
					
		<img src="img/editar.gif" alt="Editar Concepto" id="idCambioConcepto<s:property value='#attr.mitablaconcepto_rowNum - 1'/>" style="height:18px;width:18px;cursor:pointer;" 
			  		onclick="pasarHonorarioAjax('<s:property value='#attr.mitablaconcepto_rowNum - 1'/>','defaultConcepto');" title="Seleccionar Concepto"/>

 	</display:column> --%>
 	
 		<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaTramite);' onKeyPress='this.onClick'/>" 
			style="width:1%" >
		<table align="center">
		<tr>
			<td style="border: 0px;">
				<input type="checkbox" name="listaChecksNumExpedientes" id="<s:property value="#attr.mitablaExpedientes.numExpediente"/>" 
				value='<s:property value="#attr.mitablaExpedientes.numExpediente"/>' />
			</td>
		</tr>
		</table>	
	 	</display:column>
	</display:table>
	 	<table align="center">
	 		<tr>
	 			<td>
	 				<input type="button" class="boton" name="bVerExpedientes" id="idVerExpedientes" value="Borrar expedientes" 
						onClick="eliminarNumExpediente(); recargarPadre();" onKeyPress="this.onClick" />
				</td>
			</tr>	
	 	</table> 	
	</body>
</html>
</s:form>
