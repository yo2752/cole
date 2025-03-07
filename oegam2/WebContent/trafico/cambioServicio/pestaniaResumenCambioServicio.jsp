<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen del trámite de cambio de servicio</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
					onclick="consultaEvolucionTramiteTrafico(<s:property value="%{tramiteTraficoCambServ.numExpediente}"/>);" title="Ver evolución"/>
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
				<s:label value="%{tramiteTraficoCambServ.numExpediente}"/>
			</td>
		</tr>

		<s:if test="%{tramiteTraficoCambServ.refPropia!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="refPropiaResumen">Referencia Propia:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.refPropia}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoCambServ.motivoCambioServicio!=null}">
			<tr>
				<td align="left">
					<label for="motivoCambioServicio">Anotaciones:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.motivoCambioServicio}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoCambServ.vehiculoDto.matricula!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="matricula">Matricula:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.vehiculoDto.matricula}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoCambServ.estado != null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Estado: </label>
				</td>
				
				<td align="left" nowrap="nowrap">
					<s:label value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(tramiteTraficoCambServ.estado)}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoCambServ.respuesta!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="respuesta">Respuesta DGT:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.respuesta}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{true.equals(tramiteTraficoCambServ.imprPermisoCircu)}">
			<tr>
				<s:if test="%{true.equals(tramiteTraficoCambServ.imprPermisoCircu)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Permiso de Circulación.
						</td>
					</tr>
				</s:if>
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
		<s:if test="%{tramiteTraficoCambServ.tasa.codigoTasa!=null && tramiteTraficoCambServ.tasa.codigoTasa!= -1 }">
			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">Tipo de Tasa:</label>
				</td>

				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">4.4</label>
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">Tasa Duplicado:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.tasa.codigoTasa}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoCambServ.fechaPresentacion.dia!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
					<label for="fechaPresentacion">Fecha de presentación:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.fechaPresentacion.dia}"/>/
					<s:label value="%{tramiteTraficoCambServ.fechaPresentacion.mes}"/>/
					<s:label value="%{tramiteTraficoCambServ.fechaPresentacion.anio}"/>
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
		<s:if test="%{tramiteTraficoCambServ.titular.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="24%">
					<label for="datosTitular">Nombre y apellidos del titular:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.titular.persona.nombre}"/>
					<s:label value="%{tramiteTraficoCambServ.titular.persona.apellido1RazonSocial}"/>
					<s:label value="%{tramiteTraficoCambServ.titular.persona.apellido2}"/>
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap" width="13%">
					<label for="nifTitular">DNI del titular: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.titular.persona.nif}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoCambServ.representanteTitular.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="datosRepresentante">Nombre y apellidos del representante:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.representanteTitular.persona.nombre}"/>
					<s:label value="%{tramiteTraficoCambServ.representanteTitular.persona.apellido1RazonSocial}"/>
					<s:label value="%{tramiteTraficoCambServ.representanteTitular.persona.apellido2}"/>
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap">
					<label for="nifRepresentante">DNI del representante:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoCambServ.representanteTitular.persona.nif}"/>
				</td>
			</tr>
		</s:if>
	</table>

	<%@include file="../resumenAcciones.jspf" %>
</div>