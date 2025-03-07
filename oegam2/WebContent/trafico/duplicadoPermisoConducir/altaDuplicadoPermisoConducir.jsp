
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/duplicadoPermisoConducir/duplicadoPermisoConducir.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Duplicado Permiso Conducir</span>
				<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
				<s:set var="stringDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
				<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
			</td>
		</tr>
	</table>
</div>

<div>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<ol id="toc">
		<li id="DuplicadoPermCond"><a href="#DuplicadoPermCond">Duplicado Permiso Conducir</a></li>
		<li id="Titular"><a href="#Titular">Titular</a></li>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().puedeGenerarMandato(duplicadoPermisoConducirDto)}">
			<li id="Mandato"><a href="#Mandato">Mandato</a></li>
		</s:if>
		<li id="Documentacion"><a href="#Documentacion">Documentación</a></li>
		<s:if test="%{duplicadoPermisoConducirDto.numExpediente!=null}">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>

	<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

		<div class="contentTabs" id="DuplicadoPermCond" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaDuplicadoPermisoConducir.jsp" %>
		</div>

		<div class="contentTabs" id="Titular" 
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTitularDuplPermCond.jsp" %>
		</div>

		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().puedeGenerarMandato(duplicadoPermisoConducirDto)}">
			<div class="contentTabs" id="Mandato" 	
				style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
				<%@include file="pestaniaMandato.jsp" %>
			</div>
		</s:if>

		<div class="contentTabs" id="Documentacion" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaDocumentacion.jsp" %>
		</div>

		<s:if test="%{duplicadoPermisoConducirDto.numExpediente!=null}">
			<div class="contentTabs" id="Resumen" 	
				style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
				<%@include file="pestaniaResumenDuplPermCond.jsp" %>
			</div>
		</s:if>

		<div id="bloqueBotones" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>

		<table align="center">
			<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esGuardable(duplicadoPermisoConducirDto)}">
					<td>
						<input class="boton" type="button" id="ibGuardarDuplPermCond" name="bGuardarDuplPermCond" value="Guardar" onClick="javascript:guardarDuplPermCond();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().puedeFirmarDeclaracionYSolicitud(duplicadoPermisoConducirDto)}">
					<td>
						<input class="boton" type="button" id="ibFirmarDocDuplPermCond" name="bFirmarDocDuplPermCond" value="Firmar Documentos" onClick="javascript:firmarDeclYSolicitudDuplCond();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esValidable(duplicadoPermisoConducirDto)}">
					<td>
						<input class="boton" type="button" id="ibValidarDuplPermCond" name="bValidarDuplPermCond" value="Validar" onClick="javascript:validarDuplPermCond();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esTramitable(duplicadoPermisoConducirDto)}">
					<td>
						<input class="boton" type="button" id="ibTramitarDuplPermCond" name="bTramitarDuplPermCond" value="Tramitar" onClick="javascript:tramitarDuplPermCond();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esEnviableDocumentacion(duplicadoPermisoConducirDto)}">
					<td>
						<input class="boton" type="button" id="ibEnviarDocumentacion" name="bEnviarDocumentacion" value="Enviar Doc. Pdt." onClick="javascript:enviarDocumentacion();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esImprimible(duplicadoPermisoConducirDto)}">
					<td>
						<input class="boton" type="button" id="ibImprimirDuplPermCond" name="bImprimirDuplPermCond" value="Imprimir" onClick="javascript:imprimirDuplPermCond();" onKeyPress="this.onClick"/>
					</td>
				</s:if>
				<td>
					<input type="button" class="boton" name="bVolverDuplPermCond" id="ibVolverDuplPermCond" value="Volver" onClick="javascript:volverDuplPermCond();" onKeyPress="this.onClick"/>
				</td>
			</tr>
		</table>

		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
				src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>

		<s:hidden id="nifBusqueda" name="nifBusqueda"/>

		<s:hidden id="idContratoDuplPermCond" name="duplicadoPermisoConducirDto.idContrato"/>
		<s:hidden id="idDuplicadoPermCond" name="duplicadoPermisoConducirDto.idDuplicadoPermCond"/>

		<s:hidden name="duplicadoPermisoConducirDto.tipoTramite"/>
		<s:hidden name="duplicadoPermisoConducirDto.doiTipo"/>
		<s:hidden id="numColegiadoDPC" name="duplicadoPermisoConducirDto.numColegiado"/>
		<s:hidden name="duplicadoPermisoConducirDto.estado"/>
		<s:hidden name="duplicadoPermisoConducirDto.jefatura"/>
		<s:hidden name="duplicadoPermisoConducirDto.estadoImpresion"/>
		<s:hidden name="duplicadoPermisoConducirDto.estadoDeclaracion"/>
		<s:hidden name="duplicadoPermisoConducirDto.estadoSolicitud"/>
		<s:hidden name="duplicadoPermisoConducirDto.estadoDeclaracionTitular"/>
		<s:hidden name="duplicadoPermisoConducirDto.estadoMandato"/>
		<s:hidden name="duplicadoPermisoConducirDto.estadoOtroDocumento"/>

		<s:hidden name="duplicadoPermisoConducirDto.fechaUltModif.dia"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaUltModif.mes"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaUltModif.anio"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaUltModif.hora"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaUltModif.minutos"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaUltModif.segundos"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaAlta.dia"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaAlta.mes"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaAlta.anio"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaAlta.hora"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaAlta.minutos"/>
		<s:hidden name="duplicadoPermisoConducirDto.fechaAlta.segundos"/>
	</s:form>

	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
</div> 