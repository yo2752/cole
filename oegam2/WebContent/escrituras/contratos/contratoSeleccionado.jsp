 
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

<script>
if(typeof(document.getElementsByClassName) != 'function') {
  	document.getElementsByClassName = function (cn) {
		var rx = new RegExp("\\b" + cn + "\\b"), allT = document.getElementsByTagName("*"), allCN = [], i = 0, a;
			while (a = allT[i++]) {
			  if (a.className && a.className.indexOf(cn) + 1) {
				if(a.className===cn){ allCN[allCN.length] = a; continue;   }
				rx.test(a.className) ? (allCN[allCN.length] = a) : 0;
			  }
			}
		return allCN;
		};
}

</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Contrato Seleccionado
			</span></td>
		</tr>
	</table>
</div>

<!-- Pestañas -->

<ol id="toc">
    <li><a href="#DatosdelContrato">Datos del Contrato</a></li>
	<li><a href="#DatosdelColegiado">Datos del Colegiado</a></li>
	<li><a href="#AplicacionesAsociadas">Aplicaciones Asociadas al Contrato</a></li>
	<li><a href="#UsuariosAsociados">Usuarios Asociados al Contrato</a></li>
</ol>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

<%@include file="../../includes/erroresMasMensajes.jspf" %>
<%@include file="contratoDatosGenerales.jspf" %>
<%@include file="contratoDatosColegiado.jspf" %>
<%@include file="contratoAplicacionesAsociadas.jspf" %>
<%@include file="contratoUsuariosAsociados.jspf" %>

<s:hidden id="idContrato" name="contratoBean.idContrato"></s:hidden>
<s:hidden id="anagramaContrato" name="contratoBean.datosContrato.anagramaContrato"></s:hidden>
<s:hidden id="anagramaColegiado" name="contratoBean.datosColegiado.anagrama"></s:hidden>
<s:hidden id="idTipoContrato" name="contratoBean.datosContrato.idTipoContrato"></s:hidden>

<s:hidden id="estadoUsuario" name="contratoBean.datosColegiado.estadoUsuario"></s:hidden>
<s:hidden id="fechaRenovacionDia" name="contratoBean.datosColegiado.fechaRenovacion.dia"></s:hidden>
<s:hidden id="fechaRenovacionMes" name="contratoBean.datosColegiado.fechaRenovacion.mes"></s:hidden>
<s:hidden id="fechaRenovacionAnio" name="contratoBean.datosColegiado.fechaRenovacion.anio"></s:hidden>
<s:hidden id="idContratoColegiado" name="contratoBean.datosColegiado.idContrato"></s:hidden>
<s:hidden id="idContratoProvinciaColegiado" name="contratoBean.datosColegiado.idContratoProvincia"></s:hidden>
<s:hidden id="provinciaColegiado" name="contratoBean.datosColegiado.provincia"></s:hidden>
<s:hidden id="idUsuario" name="contratoBean.datosColegiado.usuario.idUsuario"></s:hidden>
<!-- INICIO Mantis 0011494: (ihgl) enviamos el id del usuario seleccionado como un campo oculto del formulario -->
<s:hidden id="idUsuarioSeleccionado" name="idUsuarioSeleccionado"></s:hidden>
<!-- FIN Mantis 0011494 (ihgl) -->
</s:form>




