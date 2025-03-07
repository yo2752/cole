<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.displaytag-ajax-oegam.js"></script>
<script type="text/javascript" src="js/tabs.js"></script>
<script type="text/javascript" src="js/registradores/registradores.js"></script>
<script type="text/javascript" src="js/general.js"></script>
<script type="text/javascript" src="js/trafico/comunes.js"></script>
<script type="text/javascript" src="js/trafico/consultaTramites.js"></script>
<script type="text/javascript" src="js/validaciones.js"></script>
<script type="text/javascript" src="js/traficoConsultaPersona.js"></script>
<script type="text/javascript" src="js/genericas.js"></script>
<script type="text/javascript" src="js/mensajes.js"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Presentación de cuentas</span>
			</td>
		</tr>
	</table>
</div>
<!-- Lista para las pestañas de la jsp -->
<ol id="toc">
	<li id="documentacion"><a id="documentacion" href="#documentacion">Documentación aportada/recibida</a></li>
	<li ><a style="display:none;" id="presentador" href="#presentador">Presentador</a></li>
<!-- 	<li><a style="display:none;" id="Cuenta" href="#Cuenta">Cuenta</a></li> -->
	<s:if test="%{tramiteRegistro.acusesPendientes!=null && tramiteRegistro.acusesPendientes.size>0}">
		<li id="pesPendientes"><a href="#pesPendientes">Acuses pendientes</a></li>
	</s:if>
	<li id="Resumen"><a style="display:none;" id="pesResumen" href="#Resumen">Resumen tr&aacute;mite</a></li> 
</ol>

<!-- Comprobaciones para mostrar las pestañas de forma pertinente -->
<s:if test="%{tramiteRegistro.sociedadEstablecida==true}">
	<script type="text/javascript">habilitarPestaniasCuenta();</script>
</s:if>

<%@include file="../../../includes/erroresMasMensajes.jspf"%>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

<s:hidden id="tipoTramiteRegistro" name="tramiteRegistro.tipoTramite"/>
<s:hidden id="numColegiadoRegistro" name="tramiteRegistro.numColegiado"/>
<s:hidden id="idTramiteRegistro" name="tramiteRegistro.idTramiteRegistro"/>
<s:hidden name="tramiteRegistro.idTramiteCorpme"/>
<s:hidden name="tramiteRegistro.idUsuario"/>
<s:hidden id="idContratoRegistro" name="tramiteRegistro.idContrato"/>
<s:hidden name="tramiteRegistro.estado"/>
<s:hidden name="tramiteRegistro.fechaCreacion.dia"/>
<s:hidden name="tramiteRegistro.fechaCreacion.mes"/>
<s:hidden name="tramiteRegistro.fechaCreacion.anio"/>
<s:hidden name="tramiteRegistro.fechaCreacion.hora"/>
<s:hidden name="tramiteRegistro.fechaCreacion.minutos"/>
<s:hidden name="tramiteRegistro.fechaCreacion.segundos"/>
<s:hidden name="tramiteRegistro.sociedad.numColegiado"/>
<s:hidden name="tramiteRegistro.subsanacion"/>
<s:hidden name="tramiteRegistro.sociedadEstablecida"/>
<s:hidden name="tipoContratoRegistro" id="tipoContratoRegistro" />
<s:hidden name="tramiteRegistro.aplicarIrpf" id="aplicaIrpf" />
<s:hidden name="tramiteRegistro.idRegistro"/>
<s:hidden name="tramiteRegistro.fechaDocumento.dia"/>
<s:hidden name="tramiteRegistro.fechaDocumento.mes"/>
<s:hidden name="tramiteRegistro.fechaDocumento.anio"/>
<s:hidden name="tramiteRegistro.fechaDocumento.hora"/>
<s:hidden name="tramiteRegistro.fechaDocumento.minutos"/>
<s:hidden name="tramiteRegistro.fechaDocumento.segundos"/>
<s:hidden name="tramiteRegistro.idDireccionDestinatario"/>

<div id="documentacion" class="contentTabs">
	<%@include file="/registradores/resumen/listaFicheros.jsp" %>
</div>

  <div id="presentador" class="contentTabs">
      <jsp:include page="../../pestaniaPresentador.jsp" flush="true"/>
  </div>
	    
<%-- <div id="Cuenta"><jsp:include page="cuenta.jsp" flush="true" /></div> --%>

<s:if test="%{tramiteRegistro.acusesPendientes!=null && tramiteRegistro.acusesPendientes.size>0}">
	<%@include file="../../acusesPendientes.jsp" %>
</s:if>

<!-- Contenido de la pestaña del formulario que contiene el resumen del estado del trámite -->
<div id="Resumen" class="contentTabs">
	<%@include file="resumenTramiteCuenta.jsp" %>
</div>

<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
	
</s:form>
	

<script>
	$(document).ready(function(){
		mostrarIrpf();
	});
	trimCamposInputs();
</script>
