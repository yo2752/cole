<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/inteve/tramiteInteve.js" type="text/javascript"></script>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen del trámite de inteve</td>
			<s:if
				test="%{tramiteInteve !=null && tramiteInteve.numExpediente != null}">
				<td align="right"><img src="img/history.png"
					alt="ver evolución"
					style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
					onclick="consultaEvolucionTramiteInteve(<s:property value="%{tramiteInteve.numExpediente}"/>);"
					title="Ver evolución" /></td>
			</s:if>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">DATOS GENERALES</span></td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="labelNumExpedienteResumenTRI">Número de Expediente:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteInteve.numExpediente}" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120"><label
				for="refPropiaId">Referencia Propia:</label></td>
			<s:if test="%{tramiteInteve.refPropia!=null}">
				<td align="left" nowrap="nowrap"><s:label
						value="%{tramiteInteve.refPropia}" /></td>
			</s:if>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120"><label
				for="estadoId">Estado: </label></td>
			<s:if test="%{tramiteInteve.estado!=null}">
				<td align="left" nowrap="nowrap"><s:label
						value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(tramiteInteve.estado)}" />
				</td>
			</s:if>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120"><label
				for="labelFechaPrtResumenTRI">Fecha Presentación:</label></td>
			<s:if
				test="%{tramiteInteve.fechaPresentacion != null}">
				<td align="left" nowrap="nowrap"><s:label
						value="%{tramiteInteve.fechaPresentacion.dia}" />/<s:label
						value="%{tramiteInteve.fechaPresentacion.mes}" />/<s:label
						value="%{tramiteInteve.fechaPresentacion.anio}" /></td>
			</s:if>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">SOLICITANTE</span></td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<s:if test="%{tramiteInteve.solicitante.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="200"><label
					for="datosSolicitante">Nombre y apellidos del solicitante:
				</label></td>

				<td align="left" nowrap="nowrap"><s:label
						value="%{tramiteInteve.solicitante.persona.nombre}" /> <s:label
						value="%{tramiteInteve.solicitante.persona.apellido1RazonSocial}" />
					<s:label value="%{tramiteInteve.solicitante.persona.apellido2}" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="nifSolicitante">NIF del solicitante:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteInteve.solicitante.persona.nif}" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</s:if>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">Listado Solicitudes Inteve</span></td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="center" style="width: 100%; text-align: center; align: center">
		<tr>
			<td><display:table name="tramiteInteve.tramitesInteves" class="tablacoin" uid="tablaSolicitudesInteve"
					id="tablaSolicitudesInteve" summary="" excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">

					<display:column property="matricula" title="Matricula" style="width:1%" />

					<display:column property="bastidor" title="Bastidor" style="width:1%" />

					<display:column property="nive" title="Nive" style="width:1%" />

					<display:column title="Tipo Informe" style="width:1%;" sortable="false" headerClass="sortable" defaultorder="descending">
						<s:property value="%{@org.gestoresmadrid.core.enumerados.TipoInformeInteve@convertir(#attr.tablaSolicitudesInteve.tipoInforme).nombreEnum}" />
					</display:column>

					<display:column title="Estado" style="width:1%;">
						<s:property
							value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertir(#attr.tablaSolicitudesInteve.estado).nombreEnum}" />
					</display:column>

					<display:column property="fechaPresentacion" title="Fecha presentación" style="width:1%" format="{0,date,dd/MM/yyyy}" />
		</display:table>
			</td>
		</tr>
	</table>
</div>