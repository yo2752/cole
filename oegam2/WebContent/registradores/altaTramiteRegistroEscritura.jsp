<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/tabs.js"></script>
<script type="text/javascript" src="js/general.js"></script>
<script type="text/javascript" src="js/trafico/comunes.js"></script>
<script type="text/javascript" src="js/trafico/consultaTramites.js"></script>
<script type="text/javascript" src="js/validaciones.js"></script>
<script type="text/javascript" src="js/traficoConsultaPersona.js"></script>
<script type="text/javascript" src="js/jquery.displaytag-ajax-oegam.js"></script>
<script type="text/javascript" src="js/notario/notarioFunction.js"></script>
<script type="text/javascript" src="js/genericas.js"></script>
<script type="text/javascript" src="js/mensajes.js" ></script>
<script src="js/seleccionarDireccion.js" type="text/javascript"></script>
<script type="text/javascript" src="js/escrituraRegistro.js"></script>
<script type="text/javascript" src="js/registradores/registradores.js"></script>

<iframe width="174" height="189" name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" 
	frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Escritura</span>
			</td>
		</tr>
	</table>
</div>

<ol id="toc">
	<li id="escritura"><a href="#escritura">Escritura</a></li>
	<li id="presentador"><a href="#presentador">Presentador</a></li>
	<li id="destinatario"><a href="#destinatario">Cliente o destinatario</a></li>
	<li id="registro"><a href="#registro">Registro destino</a></li>
	<li id="bienes"><a id="pestaniaBienes" href="#bienes">Bienes inmuebles</a></li>	
	<s:if test="%{tramiteRegistro.acusesPendientes!=null && tramiteRegistro.acusesPendientes.size>0}">
		<li id="pesPendientes"><a href="#pesPendientes">Acuses pendientes</a></li>
	</s:if> 
	<li id="documentacion"><a href="#documentacion">Documentación aportada/recibida</a></li>
	
	<li id="formaPago"><a href="#formaPago">Forma de pago</a></li>
	
	<li id="resumen"><a id="pestaniaResumen" style="display:none;visible:false;" href="#resumen">Resumen</a></li>	
	<s:if test="tramiteRegistro.estado == 19">
		<li id="justificantesPago"><a href="#justificantesPago">Confirmaci&oacute;n pago</a></li> 
	</s:if>  
</ol>

<s:if test="%{tramiteRegistro.escrituraEstablecida==true}">
	<script type="text/javascript">mostrarResumenSinIfs();</script>
</s:if>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

<s:form method="post" id="formData" name="formData" cssClass="transformable" enctype="multipart/form-data">

	<s:hidden name="tramiteRegistro.idTramiteRegistro" id="idTramiteRegistro"/>
	<s:hidden name="tramiteRegistroJustificante.idTramiteRegistro" id="idTramiteRegistroJustificante"/>
	<s:hidden name="tramiteRegistro.idTramiteCorpme"/>
	<s:hidden name="tramiteRegistroJustificante.idTramiteCorpme"/>
	<s:hidden name="tramiteRegistro.numColegiado" id="numColegiadoRegistro" />
	<s:hidden name="tramiteRegistroJustificante.numColegiado" id="numColegiadoRegistroJustificante" />
	<s:hidden name="tramiteRegistro.fechaCreacion.dia"/>
	<s:hidden name="tramiteRegistro.fechaCreacion.mes"/>
	<s:hidden name="tramiteRegistro.fechaCreacion.anio"/>
	<s:hidden name="tramiteRegistro.fechaCreacion.hora"/>
	<s:hidden name="tramiteRegistro.fechaCreacion.minutos"/>
	<s:hidden name="tramiteRegistro.fechaCreacion.segundos"/>
	<s:hidden name="tramiteRegistroJustificante.fechaCreacion.dia"/>
	<s:hidden name="tramiteRegistroJustificante.fechaCreacion.mes"/>
	<s:hidden name="tramiteRegistroJustificante.fechaCreacion.anio"/>
	<s:hidden name="tramiteRegistroJustificante.fechaCreacion.hora"/>
	<s:hidden name="tramiteRegistroJustificante.fechaCreacion.minutos"/>
	<s:hidden name="tramiteRegistroJustificante.fechaCreacion.segundos"/>
	<s:hidden name="tramiteRegistro.tipoTramite" id="tipoTramiteRegistro" />
	<s:hidden name="tramiteRegistroJustificante.tipoTramite" id="tipoTramiteRegistroJustificante" />
	<s:hidden name="tramiteRegistro.estado" id="estado"/>
	<s:hidden name="tramiteRegistroJustificante.estado"/>
	<s:hidden name="tramiteRegistro.idContrato" id="idContratoRegistro" />
	<s:hidden name="tramiteRegistroJustificante.idContrato" id="idContratoRegistroJustificante" />
	<s:hidden name="tramiteRegistro.idUsuario"/>
	<s:hidden name="tramiteRegistroJustificante.idUsuario"/>
	<s:hidden name="nifBusqueda" id="nifBusqueda" />
	<s:hidden name="tramiteRegistro.escrituraEstablecida"/>
	<s:hidden name="tramiteRegistro.subsanacion"/>
	<s:hidden name="tramiteRegistro.numRegSub"/>
	<s:hidden name="tramiteRegistro.anioRegSub"/>
	<s:hidden name="tipoContratoRegistro" id="tipoContratoRegistro" />
	
	<div id="escritura" class="contentTabs"> 
		<%@include file="escritura.jsp" %>
	</div>
	
	<div id="presentador" class="contentTabs">
		<%@include file="pestaniaPresentadorEscritura.jsp" %>
	</div>
	
	<div id="destinatario" class="contentTabs"> 
		<%@include file="destinatario.jsp" %>
	</div>
	
	<div id="registro" class="contentTabs"> 
		<%@include file="registroTramiteEscritura.jsp" %>
	</div>
	
	<div id="bienes" class="contentTabs"> 
		<%@include file="pestaniaBienesInmuebles.jsp" %>
	</div>
	
	<s:if test="%{tramiteRegistro.acusesPendientes!=null && tramiteRegistro.acusesPendientes.size>0}">
		<%@include file="acusesPendientes.jsp" %>
	</s:if>
	
	<div id="documentacion" class="contentTabs">
		<%@include file="/registradores/resumen/listaFicherosEscritura.jsp" %>
	</div>
	
	<div  id="formaPago" class="contentTabs">  
		<%@include file="pestaniaFormaPagoEscritura.jsp" %>
	</div>
	
	<div id="resumen" class="contentTabs">
		<%@include file="resumenTramiteEscritura.jsp" %>
	</div>
	
	<s:if test="tramiteRegistro.estado == @org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@Inscrito.getValorEnum() 
	&& tramiteRegistro.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_6.getValorEnum()">
		<div id="justificantesPago" class="contentTabs">
			<%@include file="justificantesPagoEscritura.jsp" %>
		</div>
	</s:if>  

	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>

</s:form>

<script>
	var viaDestinatario = new BasicContentAssist(document.getElementById('nombreViaDestinatario'), [], null, true); 
	recargarComboRegistros();
	recargarComboMunicipios('idProvinciaDestinatario','idMunicipioDestinatario','municipioSeleccionadoDestinatario');
	recargarComboTipoVia('idProvinciaDestinatario','tipoViaDestinatario','tipoViaSeleccionadaDestinatario');
	recargarNombreVias('idProvinciaDestinatario', 'municipioSeleccionadoDestinatario', 'tipoViaSeleccionadaDestinatario','nombreViaDestinatario',viaDestinatario);
	mostrarBien();
	estadoIdentificacion();
	mostrarIdentificacion();
	trimCamposInputs();
</script>
	