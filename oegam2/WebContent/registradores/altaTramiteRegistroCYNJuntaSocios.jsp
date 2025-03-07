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

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">S.L Cese y Nombramiento</span>
			</td>
		</tr>
	</table>
</div>
<!-- Lista para las pestañas de la jsp -->
<ol id="toc">
	<li><a href="#Sociedad">Sociedad</a></li>
	<li id="Presentador"><a style="display:none;" id="pesPresentador" href="#presentador">Presentador</a></li>
	<li id="Certificantes"><a style="display:none;" id="pesCertif" href="#Certificantes">Certificantes</a></li>
	<li id="Junta"><a style="display:none;" id="pesJunta" href="#Junta">Junta Socios</a></li>
	<li id="Acuerdos"><a style="display:none;" id="pesAcuerdos" href="#Acuerdos">Acuerdos</a></li>
	<s:if test="%{tramiteRegistro.acusesPendientes!=null && tramiteRegistro.acusesPendientes.size>0}">
		<li id="pesPendientes"><a href="#pesPendientes">Acuses pendientes</a></li>
	</s:if>
	<li id="documentacion"><a style="display:none;" id="pesDocumentacion" href="#documentacion">Documentación aportada/recibida</a></li>  
	<li id="formaPago"><a style="display:none;" id="pesFormaPago" href="#formaPago">Forma de pago</a></li>
	<li id="Resumen"><a style="display:none;" id="pesResumen" href="#Resumen">Resumen tr&aacute;mite</a></li> 
</ol>

<!-- Comprobaciones para mostrar las pestañas de forma pertinente -->
<s:if test="%{tramiteRegistro.sociedadEstablecida==true}">
	<script type="text/javascript">habilitarPestanias();</script>
</s:if>
<s:if test="%{tramiteRegistro.reunionEstablecida==true}">
	<script type="text/javascript">habilitarAcuerdos();</script>
</s:if>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

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
<s:hidden name="tramiteRegistro.reunion.idReunion"/>
<s:hidden name="tramiteRegistro.subsanacion"/>
<s:hidden name="tramiteRegistro.sociedadEstablecida"/>
<s:hidden name="tramiteRegistro.reunionEstablecida"/>
<s:hidden name="tipoAcuerdo" id="tipoAcuerdo"/>
<s:hidden name="tipoContratoRegistro" id="tipoContratoRegistro" />
<s:hidden name="rowidCargo" id="rowidCargo"/>

<div class="contentTabs" id="Sociedad">
	<%@include file="sociedad.jsp" %>
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		&nbsp;
		<div id="botonBusqueda">
			<table width="100%">
				<tr>
					<td>
						<input type="button" value="Guardar" class="boton"  id="botonSeleccionarSociedad" onclick="return (validacionCamposSociedadGuardar('TramiteRegistroMd4'));" onKeyPress="this.onClick"/>
					</td>
					<td>
						<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
					</td>
					<td>
						<input type="button" class="boton" value="Limpiar campos" onclick="limpiarCamposSoc();"/>
					</td>
				</tr>
			</table>
		</div>
	</s:if>
</div>

 <div id="presentador" class="contentTabs">
     <jsp:include page="pestaniaPresentador.jsp" flush="true"/>
 </div>
 
<div class="contentTabs" id="Certificantes">
	<%@include file="certificantesLista.jsp" %>
	&nbsp;
	<%@include file="lugarFechaCertif.jsp" %>
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		&nbsp;
		<div id="botonBusqueda">
			<table width="100%">
				<tr>
					<td> 
						<input type="button" class="boton" value="Guardar" id="botonGuardarCYNJuntaSocios" onclick="guardarTramiteRegistro();"/>
						&nbsp;
						<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">  
					</td>
				</tr>
			</table>
		</div>
	</s:if>
</div>

<%@include file="juntaSocios.jsp" %>

<div class="contentTabs" id="Acuerdos">
	<%@include file="acuerdoCese.jsp"%>
	&nbsp;
	<%@include file="acuerdoNombramiento.jsp" %>
</div>

<s:if test="%{tramiteRegistro.acusesPendientes!=null && tramiteRegistro.acusesPendientes.size>0}">
	<%@include file="acusesPendientes.jsp" %>
</s:if>

<div id="documentacion" class="contentTabs">
	<%@include file="/registradores/resumen/listaFicheros.jsp" %>
</div>

<div  id="formaPago" class="contentTabs">  
	<%@include file="pestaniaFormaPagoCYNJuntaSocios.jsp" %>
</div>

<!-- Contenido de la pestaña del formulario que contiene el resumen del estado del trámite -->
<%@include file="resumenTramiteCYNJuntaSocios.jsp" %>

<!-- iframe para el correcto funcionamiento del calendario -->
<iframe width="174" height="189" name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" 
	frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>

</s:form>
	
<!-- Ejecuta las funciones de visibilidad de las cajas para que se mantengan tras una recarga de la página las selecciones del usuario -->
<script>
	recargarComboRegistros();
	mostrarCajaDuracion();
	trimCamposInputs();
	/* certificantes(); */
	/* mostrarBotones(); */
</script>
