<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen del trámite de alta de matriculación</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
					onclick="consultaEvolucionTramiteTrafico(<s:property value="%{tramiteTrafMatrDto.numExpediente}"/>);" title="Ver evolución"/>
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
				<label for="numExpediente">Número de expediente:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:label id="numExpediente" value="%{tramiteTrafMatrDto.numExpediente}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="refPropia">Referencia propia:</label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.refPropia}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="estado">Estado: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(tramiteTrafMatrDto.estado)}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="P_Respuesta">P_Respuesta </label>
			</td>
				<td align="left">
					<%if (request.getAttribute("tramiteTrafMatrDto.respuesta") != null) {
						String respuesta = (String) request.getAttribute("tramiteTrafMatrDto.respuesta");
						String[] aux = null;
						if (respuesta.contains("<BR>")) {
							aux = respuesta.split("<BR>");
						} else if (respuesta.contains(":")) {
							aux = respuesta.split("\\:");
						} else if (respuesta.contains(".")) {
							aux = respuesta.split("\\.");
						}
						if (aux != null && aux.length > 0) {
							for (String s : aux) {
								out.println(s + "<br/>");
							}
						} else {
							out.println(respuesta);
						}
					}%>
			</td>
		</tr>
		<s:if test="tramiteTrafMatrDto.eeffLiberacionDTO.respuesta!=null">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelMensajeLiberarEEFF">Mensaje EEFF:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:property value="tramiteTrafMatrDto.eeffLiberacionDTO.respuesta"/>
				</td>
			</tr>
		</s:if>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="estado">Estado IVTM:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<!-- //TODO MPC. Cambio IVTM. -->
				<s:if test="%{ivtmMatriculacionDto.estadoIvtm!= null}">
					<s:label value="%{@escrituras.utiles.UtilesVista@getInstance().getEstadoIvtm(ivtmMatriculacionDto.estadoIvtm)}"/>
				</s:if>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="P_Respuesta">Respuesta IVTM:</label>
			</td>
			<td align="left" nowrap="nowrap">
			<!-- //TODO MPC. Cambio IVTM. -->
				<%if(request.getAttribute("ivtmMatriculacionDto.mensaje")!=null){
					String[] aux = ((String)request.getAttribute("ivtmMatriculacionDto.mensaje")).split("<BR>");
					for(String s:aux){
						out.println(s+"<br/>");
					}
				}%>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="P_Respuesta">Respuesta eITV:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.respuestaEitv}"/>
				<s:hidden name="tramiteTrafMatrDto.respuestaEitv"></s:hidden>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRespuesta576">Respuesta 576: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:property value="tramiteTrafMatrDto.respuesta576"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="bastidor">Bastidor:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.vehiculoDto.bastidor}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="fechaPresentacion">Fecha de presentación:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.fechaPresentacion.dia}"/>/
				<s:label value="%{tramiteTrafMatrDto.fechaPresentacion.mes}"/>/
				<s:label value="%{tramiteTrafMatrDto.fechaPresentacion.anio}"/>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DE LA TRAMITACIÓN</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="fechaPresentacion">Código de tasa:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.tasa.codigoTasa}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="fechaPresentacion">Tipo de tasa:</label>
			</td>
			<s:if test="tramiteTrafMatrDto.tasa.tipoTasa!= null && tramiteTrafMatrDto.tasa.tipoTasa!=-1">
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.tasa.tipoTasa}"/>
				</td>
			</s:if>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="fechaPresentacion">Matrícula: </label>
			</td>
			<s:if test="tramiteTrafMatrDto.vehiculoDto.matricula!= null">
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.vehiculoDto.matricula}"/>
				</td>
			</s:if>
		</tr>
		<tr>
			<s:if test="tramiteTrafMatrDto.vehiculoDto.fechaMatriculacion != null && tramiteTrafMatrDto.vehiculoDto.fechaMatriculacion.dia!= null">
				<td align="left" nowrap="nowrap">
					<label for="fechaPresentacion">Fecha de matriculación:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.vehiculoDto.fechaMatriculacion.dia}"/>/
					<s:label value="%{tramiteTrafMatrDto.vehiculoDto.fechaMatriculacion.mes}"/>/
					<s:label value="%{tramiteTrafMatrDto.vehiculoDto.fechaMatriculacion.anio}"/>
				</td>
			</s:if>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DE LOS INTERVINIENTES</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="190">
				<label for="datosTitular">Nombre y apellidos del titular: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.titular.persona.nombre}"/>
				<s:label value="%{tramiteTrafMatrDto.titular.persona.apellido1RazonSocial}"/>
				<s:label value="%{tramiteTrafMatrDto.titular.persona.apellido2}"/>
			</td>
			<td width="20"></td>
			<td align="left" nowrap="nowrap" width="120">
				<label for="nifTitular">NIF del titular:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.titular.persona.nif}"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.titular.persona.tipoPersona.valor}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="datosRepresentante">Nombre y apellidos del representante: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.representanteTitular.persona.nombre}"/>
				<s:label value="%{tramiteTrafMatrDto.representanteTitular.persona.apellido1RazonSocial}"/>
				<s:label value="%{tramiteTrafMatrDto.representanteTitular.persona.apellido2}"/>
			</td>
			<td width="20"></td>
			<td align="left" nowrap="nowrap">
				<label for="nifRepresentante">NIF del representante: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.representanteTitular.persona.nif}"/>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTrafMatrDto.representanteTitular.persona.tipoPersona.valor}"/>
			</td>
		</tr>
		<tr>
			<s:if test="tramiteTrafMatrDto.arrendatario.persona.nif!=null">
				<td align="left" nowrap="nowrap">
					<label for="datosArrendatario">Nombre y apellidos del arrendatario: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.arrendatario.persona.nombre}"/>
					<s:label value="%{tramiteTrafMatrDto.arrendatario.persona.apellido1RazonSocial}"/>
					<s:label value="%{tramiteTrafMatrDto.arrendatario.persona.apellido2}"/>
				</td>
				<td width="20"></td>
				<td align="left" nowrap="nowrap">
					<label for="nifAdquiriente">NIF del arrendatario:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.arrendatario.persona.nif}"/>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.arrendatario.persona.tipoPersona.valor}"/>
				</td>
			</s:if>
		</tr>
		<tr>
			<s:if test="tramiteTrafMatrDto.conductorHabitualBean.persona.nif!=null">
				<td align="left" nowrap="nowrap">
					<label for="datosArrendatario">Nombre y apellidos del conductor habitual: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.conductorHabitual.persona.nombre}"/>
					<s:label value="%{tramiteTrafMatrDto.conductorHabitual.persona.apellido1RazonSocial}"/>
					<s:label value="%{tramiteTrafMatrDto.conductorHabitual.persona.apellido2}"/>
				</td>
				<td width="20"></td>
				<td align="left" nowrap="nowrap">
					<label for="nifAdquiriente">NIF del conductor habitual: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.conductorHabitual.persona.nif}"/>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.conductorHabitual.persona.tipoPersona.valor}"/>
				</td>
			</s:if>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS PENDIENTE AUTORIZACIÓN</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esFichaA(tramiteTrafMatrDto)}">
				<td align="left" nowrap="nowrap" width="15%">
					<label for="fichasA">Autorizado Ficha A:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.autorizadoFichaA}"/>
				</td>
			</s:if>
		</tr>
		<tr>
			<s:if test="%{tramiteTrafMatrDto.exentoCtr=='SI'}">
				<td align="left" nowrap="nowrap" width="15">
					<label for="exentoCtr">Autorizado Exento CTR: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTrafMatrDto.autorizadoExentoCtr}"/>
				</td>
			</s:if>
		</tr>
	</table>
	
	<s:if test="docBaseCarpetaTramiteBean.tipoCarpeta!=null">
		<table class="nav" cellspacing="1" cellpadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DIGITALIZACIÓN</span>
				</td>
			</tr>
		</table>
		<table cellspacing="3" class="tablaformbasica" cellpadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="tipoCarpeta">Carpeta de Digitalización Asignada:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:property value="docBaseCarpetaTramiteBean.tipoCarpeta" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="motivoAsignacionCarpeta">Motivo Asignación:</label>
				</td>
				<td align="left">
					<s:property value="docBaseCarpetaTramiteBean.motivo" />
				</td>
			</tr>
			<s:if test="docBaseCarpetaTramiteBean.idDocBase!=null">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="docBaseExpediente">Documento Base generado:</label>
					</td>
					<td align="left">
						<a href="javascript: descargarDocBaseMatriculacion('Resumen')" title="Descargar documento base">Descargar</a>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="docBaseExpediente">Tipo de carpeta del Documento Base:</label>
					</td>
					<td align="left">
						<s:property value="%{@org.gestoresmadrid.oegamDocBase.enumerados.DocumentoBaseTipoCarpetaEnum@convertirTexto(docBaseCarpetaTramiteBean.tipoCarpetaDocBase)}" />
					</td>
				</tr>
				<s:if test="docBaseCarpetaTramiteBean.tipoCarpetaDocBase != @org.gestoresmadrid.oegamDocBase.enumerados.DocumentoBaseTipoCarpetaEnum@convertirNombreAValor(docBaseCarpetaTramiteBean.tipoCarpeta)">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="docBaseAdvertencia">Advertencia:</label>
						</td>
						<td align="left">
							La carpeta prevista del trámite, no coincide con la del documento generado
						</td>
					</tr>
				</s:if>
			</s:if>
		</table>
	</s:if>

	<%@include file="../resumenAcciones.jspf" %>

</div>
<div id="divEmergenteMatricula" style="display: none; background: #f4f4f4;"></div>