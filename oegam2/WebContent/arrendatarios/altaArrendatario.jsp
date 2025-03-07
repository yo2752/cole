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
<script src="js/arrendatarios/arrendatarios.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<s:if
					test="%{@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().esOperacionAltaArrendatario(arrendatarioDto)}">
					<span class="titulo">Alta Arrendatario</span>
				</s:if>
				<s:else>
					<span class="titulo">Modificación Arrendatario</span>
				</s:else>
			</td>
		</tr>
	</table>
</div>

<div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>

	<ol id="toc">
		<li id="DatosTramite"><a href="#DatosTramite">Datos Tramite</a></li>
		<li id="Consulta"><a href="#Consulta">Arrendatario</a></li>
		<li id="Vehiculo"><a href="#Vehiculo">Vehículo</a></li>

		<s:if test="arrendatarioDto != null && arrendatarioDto.estado != null">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>

	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " />
		</td>
		<s:if
			test="%{@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().esOperacionAltaArrendatario(arrendatarioDto)}">
			<td>Datos del Alta de Arrendamiento</td>
		</s:if>
		<s:else>
			<td>Datos de la Modificación de Arrendamiento</td>
		</s:else>

			<td style="border: 0px;" align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
				onclick="abrirEvolucionAltaCayc('idNumExpArrendatario','divEmergenteEvolucionAltaCayc');" title="Ver evolución" />
			</td>
		</tr>
	</table>

	<s:form method="post" id="formData" name="formData">
		<s:hidden name="arrendatarioDto.numColegiado"/>
		<s:hidden name="arrendatarioDto.fechaAlta"/>
		<s:hidden id="idNumExpArrendatario" name="arrendatarioDto.numExpediente"/>
		<s:hidden name="arrendatarioDto.idArrendatario"/>
		<s:hidden name="arrendatarioDto.estado"/>
		<s:hidden name="arrendatarioDto.operacion"/>
		<s:hidden name="arrendatarioDto.numRegistro"/>
		<div class="contentTabs" id="DatosTramite" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaDatosTramiteArrendatario.jsp" %>
		</div>
		<div class="contentTabs" id="Consulta" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaConsultaArrendatario.jsp" %>
		</div>
		<div class="contentTabs" id="Vehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaVehiculoArrendatario.jsp" %>
		</div>
		<div class="contentTabs" id="Resumen" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaResumenArrendatario.jsp" %>
		</div>
		&nbsp;
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().esEstadoGuardable(arrendatarioDto)}">
				<input type="button" class="boton" name="bGuardarArrendatario" id="idGuardarArrendatario" value="Guardar" onClick="javascript:guardarArrendatario();"
					onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().esEstadoConsultable(arrendatarioDto)}">
				<input type="button" class="boton" name="bConsultarArrendatario" id="idConsultarArrendatario" value="Consultar" onClick="javascript:consultarArrendatario();"
					onKeyPress="this.onClick"/>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().esEstadoIniciado(arrendatarioDto)}">
				<input type="button" class="boton" name="bValidarArrendatario" id="idValidarArrendatario" value="Validar" onClick="javascript:validarArrendatario();"
					onKeyPress="this.onClick"/>
			</s:if>

			<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver Listado" onClick="javascript:Volver_Arrendarario();"
					onKeyPress="this.onClick"/>
		</div>
		<div id="divEmergenteEvolucionAltaCayc" style="display: none; background: #f4f4f4;"></div>
	</s:form>
</div>
<script type="text/javascript">
	datosInicialesArrendatario();
</script>