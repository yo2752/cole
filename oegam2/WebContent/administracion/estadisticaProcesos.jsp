<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript" src="js/trafico/comunes.js"></script>
<script type="text/javascript" src="js/Chart.bundle.min.js"></script>
<script type="text/javascript" src="js/colas.js"></script>

<s:form id="formData" name="formData"
	action="estadisticaMonitorizacion.action">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Estad&iacute;sticas de Procesos</span>
				</td>
			</tr>
		</table>
	</div>

	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td colspan="6">
					<h3 class="formularios">Gestionar estadisticas a mostrar</h3>
				</td>
			</tr>
			<tr>
				<td width="8%"><label for="idProceso">Proceso: </label></td>
				<td width="40%"><s:select
						list="@colas.utiles.UtilesVistaColas@getInstance().getComboProcesos()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey=""
						headerValue="TODOS" name="proceso" listKey="nombreEnum"
						listValue="toString()" id="idProceso" /></td>
				<td width="15%"><label for="idCheckAuto">Autorefresco: </label></td>
				<td width="10%"><input type="checkbox" name="checkAuto" id="idCheckAuto" onchange="activarIntervalo()"></td>
				<td width="8%"><label for="idIntervalo">Intervalo: </label></td>
				<td><select id="idIntervalo" disabled="disabled" style="color: #6E6E6E;" onchange="establecerMonitorizacion()">
					<option value="3000">Tres segundos</option>
					<option value="5000">Cinco segundos</option>
					<option value="30000" selected="selected">Treinta segundos</option>
					<option value="60000">Un minuto</option>
					<option value="120000">Dos minutos</option>
					<option value="300000">Cinco minutos</option>
				</select></td>
			</tr>
			<tr>
				<td colspan="6" align="right"><input type="button" class="boton" name="bBuscar"
					id="bBuscar" value="Actualizar" onkeypress="this.onClick"
					onclick="actualizarGraficasProcesos();" /></td>
			</tr>
		</table>
	</div>

	<div id="dashboard">
		<div style="overflow: hidden; width: 100%;">
			<div id="hilos" style="display: inline-block; width: 49%;">
				<h3 class="formularios">Solicitudes encoladas</h3>
				<canvas id="chartHilos"></canvas>
			</div>
			<div id="tramites" style="display: inline-block; width: 49%;">
				<h3 class="formularios">Finalizaciones del proceso</h3>
				<canvas id="chartTramites"></canvas>
			</div>
		</div>
		<div id="general" style="display: none; width: 99%;">
			<h3 class="formularios">Solicitudes pendientes por proceso</h3>
			<canvas id="chartGeneral"></canvas>
		</div>
	</div>

	<div>
		<table align="center" style="width: 90%; padding-top: 30px;">
			<tr>
				<td style="font-weight: bold; font-size: 1.1em; text-align: left;" id=ultimaHora>
			</tr>
		</table>
	</div>
</s:form>