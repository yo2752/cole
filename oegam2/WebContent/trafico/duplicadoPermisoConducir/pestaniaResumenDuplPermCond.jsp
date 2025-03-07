<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen Duplicado Permiso Conducir</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
					onclick="abrirEvolucionDuplPermCond(<s:property value="%{duplicadoPermisoConducirDto.idDuplicadoPermCond}"/>, 'divEmergenteDuplPermCondResumen');" title="Ver evolución"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS GENERALES</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="numExpediente">Número de Expediente:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:label value="%{duplicadoPermisoConducirDto.numExpediente}"/>
			</td>
		</tr>

		<s:if test="%{duplicadoPermisoConducirDto.refPropia!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="refPropiaResumen">Referencia Propia:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{duplicadoPermisoConducirDto.refPropia}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{duplicadoPermisoConducirDto.estado != null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Estado: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{@org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga@convertirTexto(duplicadoPermisoConducirDto.estado)}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{duplicadoPermisoConducirDto.estadoImpresion != null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Estado Impresion:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{@org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga@convertirTexto(duplicadoPermisoConducirDto.estadoImpresion)}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{duplicadoPermisoConducirDto.observaciones!=null}">
			<tr>
				<td align="left">
					<label for="observaciones">Observaciones:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{duplicadoPermisoConducirDto.observaciones}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{duplicadoPermisoConducirDto.respuesta!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="respuesta">Respuesta DGT:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textarea id="respuestaDGT" name="duplicadoPermisoConducirDto.respuesta" cols="80" rows="3" readonly="true" disabled="true"/>
				</td>
			</tr>
		</s:if>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DE LA TRAMITACIÓN</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<s:if test="%{duplicadoPermisoConducirDto.codigoTasa!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">Tipo de Tasa: </label>
				</td>

				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">4.4</label>
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">Tasa: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{duplicadoPermisoConducirDto.codigoTasa}"/>
				</td>
			</tr>
		</s:if>
		<s:if test="%{duplicadoPermisoConducirDto.fechaPresentacion.dia!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
					<label for="fechaPresentacion">Fecha de presentación: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{duplicadoPermisoConducirDto.fechaPresentacion.dia}"/>/
					<s:label value="%{duplicadoPermisoConducirDto.fechaPresentacion.mes}"/>/
					<s:label value="%{duplicadoPermisoConducirDto.fechaPresentacion.anio}"/>
				</td>
			</tr>
		</s:if>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DE LOS INTERVINIENTES</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<s:if test="%{duplicadoPermisoConducirDto.titular.persona.nif!=null && duplicadoPermisoConducirDto.titular.persona.nif!=''}">
			<tr>
				<td align="left" nowrap="nowrap" width="24%">
					<label for="datosTitular">Nombre y apellidos del titular:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{duplicadoPermisoConducirDto.titular.persona.nombre}"/>
					<s:label value="%{duplicadoPermisoConducirDto.titular.persona.apellido1RazonSocial}"/>
					<s:label value="%{duplicadoPermisoConducirDto.titular.persona.apellido2}"/>
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap" width="13%">
					<label for="nifTitular">DNI del titular:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{duplicadoPermisoConducirDto.titular.persona.nif}"/>
				</td>
			</tr>
		</s:if>
	</table>
</div>
<div id="divEmergenteDuplPermCondResumen" style="display: none; background: #f4f4f4;"></div>