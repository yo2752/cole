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
<script src="js/trafico/solInfo.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Tramite Solicitud de Información</span>
			</td>
		</tr>
	</table>
</div>

<div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
		scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"> 
	</iframe>
	
	<ol id="toc" >
		<li id="Tramite"><a href="#Tramite">Solicitud</a></li>
		
		<li id="Vehiculos"><a href="#Vehiculos">Vehiculos</a></li>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().visualizarFacturacion(tramiteInteveDto)}">
			<li id="Facturacion"><a href="#Facturacion">Facturación</a></li>
		</s:if>
		
		<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().visualizarResumen(tramiteInteveDto)}">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>
	
	<s:form method="post" id="formData" name="formData">
		<input type="hidden" name="numExpediente" id="numExpediente" value="<s:property value="tramiteInteveDto.numExpediente"/>"/>
		<s:hidden name="urlJNLP" id="urlJNLP"/>
 		<s:hidden name="tramiteInteveDto.numColegiado" id="numColegiado"/>
		<s:hidden name="tramiteInteveDto.tipoTramite"/>
		<s:hidden name="tramiteInteveDto.usuarioDto.idUsuario"/>
		<s:hidden name="tramiteInteveDto.fechaAlta.dia"/>
		<s:hidden name="tramiteInteveDto.fechaAlta.mes"/>
		<s:hidden name="tramiteInteveDto.fechaAlta.anio"/>
		<s:hidden name="tramiteInteveDto.fechaUltModif.dia"/>
		<s:hidden name="tramiteInteveDto.fechaUltModif.mes"/>
		<s:hidden name="tramiteInteveDto.fechaUltModif.anio"/>		
		<table class="subtitulo" cellSpacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del Tramite de Solicitud de Información</td>
				<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().visualizarResumen(tramiteInteveDto)}">
					<td style="border: 0px;" align="right">
						<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
					  		onclick="abrirEvolucionInteve('<s:property value="tramiteInteveDto.numExpediente"/>','divEmergenteEvolucionAltaTramSolInfo');" title="Ver evolución"/>
					</td>
				</s:if>
			</tr>
		</table>
		<%@include file="../../includes/erroresMasMensajes.jspf" %>
		<div class="contentTabs" id="Tramite" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaTramiteSolInfo.jsp" %>
		</div>
		
		<div class="contentTabs" id="Vehiculos" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaVehiculosSolInfo.jsp" %>
		</div>
		
		<div class="contentTabs" id="Facturacion" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaFacturacionSolInfo.jsp" %>
		</div>
		
		<div class="contentTabs" id="Resumen" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaResumenSolInfo.jsp" %>
		</div>
		&nbsp;
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esEstadoGuardable(tramiteInteveDto)}">
				<input type="button" class="boton" name="bGuardarTramSolInfo" id="idGuardarTramSolInfo" value="Guardar" onClick="javascript:guardarTramSolInfo();"
		 			onKeyPress="this.onClick"/>
		 	</s:if>
 		 	<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esEstadoConsultable(tramiteInteveDto)}">	
 				<input type="button" class="boton" name="bGenerarXmlTramSolInfo" id="idGenerarXmlTramSolInfo" value="Generar XML" onClick="javascript:generarXmlTramSolInfo();"
			 		onKeyPress="this.onClick"/>	
 			</s:if>
 		 	<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esEstadoDescargarXml(tramiteInteveDto)}">	
 				<input type="button" class="boton" name="bDescargarXmlTramSolInfo" id="idDescargarXmlTramSolInfo" value="Descargar XML" onClick="javascript:descargarXML();"
			 		onKeyPress="this.onClick"/>	
 			</s:if>

			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esEstadoImprimir(tramiteInteveDto)}">	
				<input type="button" class="boton" name="bImprimirTramSolInfo" id="idImprimirTramSolInfo" value="Descargar Informes" onClick="javascript:imprimirTramSolInfo();"
			 		onKeyPress="this.onClick"/>	
			</s:if>

		 	<input type="button" class="boton" name="bVolverTramSolInfo" id="idVolverTramSolInfo" value="Volver" onClick="javascript:volverTramSolInfo();"
		 		onKeyPress="this.onClick"/>	
		</div>
		<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esEstadoDescargarXml(tramiteInteveDto)}">
 			<input type="button" class="boton" name="bImprimirTramSolInfo" id="idImprimirTramSolInfo" value="Descarga INTEVE" onClick="descargarCliente();"
			 		onKeyPress="this.onClick"/>
		</s:if>
		<div id="divEmergenteEvolucionAltaTramSolInfo" style="display: none; background: #f4f4f4;"></div>
	</s:form>
</div>
