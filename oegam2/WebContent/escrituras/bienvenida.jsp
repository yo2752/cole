<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%-- <%@ page import="utilidades.propiedades.PropertiesReader" %> --%>
<%@ page import="org.gestoresmadrid.utilidades.components.GestorPropiedades" %>


<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script type="text/javascript" src="js/registradores/registradores.js"></script>

<script type="text/javascript">

function cambiarElementosPorPagina(){

	document.location.href='navegarRegistrar.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

function numChecked() {
	var checks = document.getElementsByName("IdNotificaciones");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}
function eliminar() {
	if(numChecked() == 0) {
		alert("Debe seleccionar una notificacion.");
		return false;
	}
	if(!confirm("¿Realmente desea eliminar las notificaciones seleccionadas?")) {
		return false;
	}
	document.formData.action="eliminarRegistrar.action";
	document.formData.submit();
	return true;
}
function recuperarTramite(idTramiteRegistro,tipoTramite,descripcion){
	var tipo = tipoTramite.split("-")[0];	
	if (tipo=="IM1"){
		document.location.href='inicioConsultaImpresionMasiva.action';	
	}else if (tipo =="T14"){
		document.location.href='inicioConsultaIVTM.action';
	}else if (tipo =="T15"){
		document.location.href='inicioConsultaEEFF.action';
	}else if (tipo=="T1" || tipo=="T2" || tipo=="T7" || tipo=="T3" || tipo=="T4"|| tipo=="T8" || tipo=="T27"){
		document.location.href='detalleConsultaTramiteTrafico.action?numExpediente='+idTramiteRegistro +
		 "&linkeado="+true+ "&tipoTramite="+tipoTramite.substr(0,2);
	} else if (descripcion=="PROCESO COMPRA DE TASAS FINALIZADO"){
		document.location.href='detalleCompraTasas.action?idCompras='+idTramiteRegistro;
	} else if (descripcion=="PROCESO EMISION TASAS ETIQUETAS FINALIZADO"){
		document.location.href='consultaConsultaFicherosTemporales.action?idFichero='+idTramiteRegistro+'&tipoDoc=0';
	} else if (tipo == "R1"){
		document.location.href='recuperarTramiteRegistroMd1.action?numExpediente='+idTramiteRegistro;
	} else if (tipo == "R2"){
		document.location.href='recuperarTramiteRegistroMd2.action?numExpediente='+idTramiteRegistro;
	} else if (tipo == "R3"){
		document.location.href='recuperarTramiteRegistroMd3.action?numExpediente='+idTramiteRegistro;
	} else if (tipo == "R4"){
		document.location.href='recuperarTramiteRegistroMd4.action?numExpediente='+idTramiteRegistro;
	} else if (tipo == "R5"){
		document.location.href='recuperarTramiteRegistroMd5.action?numExpediente='+idTramiteRegistro;
	} else if (tipo == "R6"){
		document.location.href='recuperarTramiteRegistroMd6.action?numExpediente='+idTramiteRegistro;
	} else if (tipo == "R7"){
		document.location.href='recuperarContratoFinanciacion.action?identificador='+idTramiteRegistro;
	} else if (tipo == "R8"){
		document.location.href='recuperarContratoLeasing.action?identificador='+idTramiteRegistro;
	} else if (tipo == "R9"){
		document.location.href='recuperarContratoRenting.action?identificador='+idTramiteRegistro;
	} else if (tipo == "R10"){
		document.location.href='recuperarContratoCancelacion.action?identificador='+idTramiteRegistro;
	} else if (tipo == "R11"){
		document.location.href='recuperarCuenta.action?numExpediente='+idTramiteRegistro;
	} else if (tipo == "R12"){
		document.location.href='recuperarLibro.action?numExpediente='+idTramiteRegistro;
	} else if (tipo == "R13"){
		document.location.href='recuperarContratoDesistimiento.action?identificador='+idTramiteRegistro;
	}

	if(descripcion=="PROCESO GENERAR JP FINALIZADO") {
		<% 
			String habilitarJustificantesNuevo = GestorPropiedades.dameValorPropiedad("habilitar.justificante.nuevo");
			if (habilitarJustificantesNuevo != null && "SI".equals(habilitarJustificantesNuevo)) {
		%>
				document.location.href='inicioConsultaTramiteTraficoJustificanteProfesional.action';
		<% 
			} else {
		%>
				document.location.href='inicioJustificantesProfesionales.action';
		<% 
			}
		%>
	}
	
	if(descripcion=="PROCESO VERIFIC JP FINALIZADO") {
		<% 
			if (habilitarJustificantesNuevo != null && "SI".equals(habilitarJustificantesNuevo)) {
		%>
				document.location.href='inicioVerificacionJustificante.action';
		<% 
			} else {
		%>
				document.location.href='inicioVerificarJustificantesProfesionales.action';
		<% 
			}
		%>
	}
}
</script>

<s:include value="bienvenida_aviso.jsp"></s:include>

<script type="text/javascript">
	
	window.localStorage.setItem('entrada_aplicacion', (new Date()).getTime());
</script>
