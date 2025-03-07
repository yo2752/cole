<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/docBase/docBase.js" type="text/javascript"></script>

<%@include file="../../includes/erroresMasMensajes.jspf" %>

<s:form method="post" id="formData" name="formData" theme="simple">
	<s:hidden id="idContratoPreferencias" name="preferencias.idContrato" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoSessionBigDecimal()}" />
	<div class="nav">
		<table style="width:100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Preferencias de configuración de Gestión Documental</span>
				</td>
			</tr>
		</table>
	</div>
	<div id="busqueda" align="left">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<s:label for="ordenDocBase">Orden del documento base</s:label>
				</td>
				<td align="left">
					<s:select id="ordenDocBase" name="preferencias.ordenDocbaseYb" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboOrdenDocBase()" 
						listKey="valorEnum" listValue="nombreEnum" onchange="advertirPreferencias(this.value)" />
				</td>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left">
					<s:label id="listaAdvertencias" cssStyle="font-size:1.2em; text-align:left; font-weight:bold; color:#080;"/>
				</td>
			</tr>
		</table>
		<br/>
	</div>
	<div class="acciones center">
		<input type="button" value="Guardar" class="boton" onclick="guardarPreferencias();" />
		<input type="reset" value="Resetear Formulario" class="boton" />
	</div>			
</s:form>
<script type="text/javascript">
	function guardarPreferencias(){
		document.getElementById('formData').action = 'guardarPreferenciasDocBase.action';
		document.getElementById('formData').submit();
	}

	function advertirPreferencias(orden){
		var listaAdvertencias = document.getElementById('listaAdvertencias');
			
		if(orden!="2") {
			listaAdvertencias.innerHTML = 'Recuerde que si selecciona este tipo de orden, las simultáneas siempre aparecerán al final del documento base.<br />Recuerde además, asignar correctamente el orden de simultáneas dentro de cada trámite, o no se podrá garantizar un orden correcto.';
		} else {
			listaAdvertencias.innerHTML = "";
		}

		if(listaAdvertencias.childNodes.length > 0) {
			listaAdvertencias.style.display = 'block';
		} else {
			listaAdvertencias.style.display = 'none';
		}
	}
</script>