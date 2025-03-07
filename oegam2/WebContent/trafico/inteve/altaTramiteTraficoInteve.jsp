<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/inteve/tramiteInteve.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Trámite Inteve</span>
			</td>
		</tr>
	</table>
</div>
<div>
	<iframe width="174"
		height="189"
		name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js"
		src="calendario/ipopeng.htm"
		scrolling="no"
		frameborder="0"
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
	<ol id="toc">
		<li id="TramiteInteve" ><a href="#TramiteInteve">Trámite</a></li>
		<li id="Solicitudes"><a href="#Solicitudes">Solicitudes</a></li>
		<s:if test="%{tramiteInteve !=null && tramiteInteve.numExpediente != null}">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
		<s:hidden name="tramiteInteve.fechaPresentacion.dia"/>
		<s:hidden name="tramiteInteve.fechaPresentacion.mes"/>
		<s:hidden name="tramiteInteve.fechaPresentacion.anio"/>
		<s:hidden name="tramiteInteve.estado"/>
		<s:hidden name="tramiteInteve.numColegiado"/>
		<s:hidden name="tramiteInteve.fechaAlta.dia"/>
		<s:hidden name="tramiteInteve.fechaAlta.mes"/>
		<s:hidden name="tramiteInteve.fechaAlta.anio"/>
		<s:hidden name="tramiteInteve.tipoTramite"/>
		<s:hidden name="tramiteInteve.usuario.idUsuario"/>
		<s:hidden id="idHiddenNumeroExpediente" name="tramiteInteve.numExpediente"/>
		<div class="contentTabs" id="TramiteInteve" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaTramiteInteve.jsp" %>
		</div>

		<div class="contentTabs" id="Solicitudes"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaSolicitudes.jsp" %>
		</div>

		<div class="contentTabs" id="Resumen"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaResumenTramiteInteve.jsp" %>
		</div>
		&nbsp;
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esGuardable(tramiteInteve)}">
				<input type="button" class="boton" name="bGuardarTRInteve" id="idBGuardarTRInteve"
					value="Guardar" onClick="javascript:guardarTramiteInteve();" onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esValidableTramite(tramiteInteve)}">
				<input type="button" class="boton" name="bValidarTRInteve" id="idBValidarTRInteve"
					value="Validar" onClick="javascript:validarTramite();" onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esSolicitableTramite(tramiteInteve)}">
				<input type="button" class="boton" name="bSolicitarTRInteve" id="idBSolicitarTRInteve"
					value="Solicitar Inteve" onClick="javascript:solicitarInteve();" onKeyPress="this.onClick"/>
			</s:if>
		</div>
		<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
	</s:form>
</div>
<script type="text/javascript">
	inicializarCampos();
</script>