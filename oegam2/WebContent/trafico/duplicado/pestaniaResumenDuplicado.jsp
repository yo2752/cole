<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="contenido">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen del trámite de duplicado</td>
			<td align="right"><img src="img/history.png" alt="ver evolución"
				style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
				onclick="consultaEvolucionTramiteTrafico(<s:property value="%{tramiteTraficoDuplicado.numExpediente}"/>);"
				title="Ver evolución" />
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">DATOS GENERALES</span></td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="numExpediente">Número de Expediente:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTraficoDuplicado.numExpediente}" />
			</td>
		</tr>

		<s:if test="%{tramiteTraficoDuplicado.refPropia!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="refPropiaResumen">Referencia Propia:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoDuplicado.refPropia}" />
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoDuplicado.anotaciones!=null}">
			<tr>
				<td align="left">
					<label for="anotaciones">Anotaciones:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoDuplicado.anotaciones}" />
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoDuplicado.vehiculoDto.matricula!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="matricula">Matr&iacute;cula:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoDuplicado.vehiculoDto.matricula}" />
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoDuplicado.estado != null}">
		<s:label style="display:none" value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(tramiteTraficoDuplicado.estado)}" id="estado_duplicado" />
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Estado:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(tramiteTraficoDuplicado.estado)}" />
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoDuplicado.resCheckDupl!=null}">
			<tr>
				<td align="left" nowrap="nowrap"><label for="resCheckDupl">Respuesta Check duplicado:</label></td>
				<td align="left" nowrap="nowrap"><s:label value="%{tramiteTraficoDuplicado.resCheckDupl}" /></td>
			</tr>
		</s:if>
		<s:if test="%{tramiteTraficoDuplicado.respuesta!=null}">
			<tr>
				<td align="left" nowrap="nowrap"><label for="respuesta">Respuesta DGT:</label></td>
				<td align="left" nowrap="nowrap"><s:label value="%{tramiteTraficoDuplicado.respuesta}" /></td>
			</tr>
		</s:if>
		<s:if test="%{@trafico.utiles.enumerados.MotivoDuplicado@convertirTexto(tramiteTraficoDuplicado.motivoDuplicado)!=null}">
			<tr>
				<td align="left" nowrap="nowrap"><label for="estado">Motivos del duplicado:</label></td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{@trafico.utiles.enumerados.MotivoDuplicado@convertirTexto(tramiteTraficoDuplicado.motivoDuplicado)}" />
				</td>
			</tr>
		</s:if>
		<s:if test="%{tramiteTraficoDuplicado.tipoDocumento!=null}">
			<tr>
				<td align="left" nowrap="nowrap"><label for="estado">Documento a solicitar a DGT:</label></td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{@org.gestoresmadrid.core.enumerados.TipoDocumentoDuplicado@convertirNombre(tramiteTraficoDuplicado.tipoDocumento)}" />
				</td>
			</tr>
		</s:if>

		<s:if
			test="%{true.equals(tramiteTraficoDuplicado.imprPermisoCircu) || true.equals(tramiteTraficoDuplicado.importacion)}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Datos de la tarjeta de ITV:</label>
				</td>

				<s:if
					test="%{true.equals(tramiteTraficoDuplicado.imprPermisoCircu)}">
					<tr>
						<td align="left" width="10%"></td>
						<td>Permiso de Circulación.</td>
					</tr>
				</s:if>
				<s:if test="%{true.equals(tramiteTraficoDuplicado.importacion)}">
					<tr>
						<td align="left" width="10%"></td>
						<td>Vehiculo Importación.</td>
					</tr>
				</s:if>
			</tr>
		</s:if>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">DATOS DE LA TRAMITACIÓN</span></td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<s:if test="%{tramiteTraficoDuplicado.tasaPermiso!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">C&oacute;digo tasa Permiso (4.4):</label>
				</td>

				<td align="left" nowrap="nowrap" width="120">
					<s:label value="%{tramiteTraficoDuplicado.tasaPermiso}" />
				</td>
			</tr>
		</s:if>
		<s:if test="%{tramiteTraficoDuplicado.tasaFichaTecnica!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">C&oacute;digo tasa Ficha (4.1):</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoDuplicado.tasaFichaTecnica}" />
				</td>
			</tr>
		</s:if>
		<s:if test="%{tramiteTraficoDuplicado.tasaImportacion != null}">
			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">C&oacute;digo tasa Importación (4.1):</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoDuplicado.tasaImportacion}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoDuplicado.fechaPresentacion.dia!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
					<label for="fechaPresentacion">Fecha de presentaci&oacute;n: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label
						value="%{tramiteTraficoDuplicado.fechaPresentacion.dia}" />/ <s:label
						value="%{tramiteTraficoDuplicado.fechaPresentacion.mes}" />/ <s:label
						value="%{tramiteTraficoDuplicado.fechaPresentacion.anio}" />
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

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<s:if test="%{tramiteTraficoDuplicado.titular.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="24%">
					<label for="datosTitular">Nombre y apellidos del titular:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label
						value="%{tramiteTraficoDuplicado.titular.persona.nombre}" /> <s:label
						value="%{tramiteTraficoDuplicado.titular.persona.apellido1RazonSocial}" />
					<s:label value="%{tramiteTraficoDuplicado.titular.persona.apellido2}" />
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap" width="13%">
					<label for="nifTitular">DNI del titular:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoDuplicado.titular.persona.nif}" />
				</td>
			</tr>
		</s:if>

		<s:if
			test="%{tramiteTraficoDuplicado.representanteTitular.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="datosRepresentante">Nombre y apellidos del representante:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label
						value="%{tramiteTraficoDuplicado.representanteTitular.persona.nombre}" />
					<s:label
						value="%{tramiteTraficoDuplicado.representanteTitular.persona.apellido1RazonSocial}" />
					<s:label
						value="%{tramiteTraficoDuplicado.representanteTitular.persona.apellido2}" />
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap">
					<label for="nifRepresentante">DNI del representante:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoDuplicado.representanteTitular.persona.nif}" />
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoDuplicado.cotitular.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="datosCotitular">Nombre y apellidos del Cotitular:</label>
				</td>

				<td align="left" nowrap="nowrap"><s:label
						value="%{tramiteTraficoDuplicado.cotitular.persona.nombre}" /> <s:label
						value="%{tramiteTraficoDuplicado.cotitular.persona.apellido1RazonSocial}" />
					<s:label value="%{tramiteTraficoDuplicado.cotitular.persona.apellido2}" />
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap"><label for="nifCotitular">DNI del Cotitular:</label></td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoDuplicado.cotitular.persona.nif}" />
				</td>
			</tr>
		</s:if>
	</table>

	<%@include file="../resumenAcciones.jspf"%>
</div>