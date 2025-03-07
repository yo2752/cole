<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen del trámite de baja</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				onclick="consultaEvolucionTramiteTrafico(<s:property value="%{tramiteTraficoBaja.numExpediente}"/>);" title="Ver evolución"/>
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
				<label for="numExpediente">Número de Expediente: </label>
			</td>

			<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTraficoBaja.numExpediente}"/>
			</td>
		</tr>

		<s:if test="%{tramiteTraficoBaja.refPropia!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="refPropiaResumen">Referencia Propia: </label>
				</td>

				<td align="left" nowrap="nowrap">
				<s:label value="%{tramiteTraficoBaja.refPropia}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.anotaciones!=null}">
			<tr>
				<td align="left">
					<label for="anotaciones">Anotaciones:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.anotaciones}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.vehiculoDto.matricula!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="matricula">Matrícula: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.vehiculoDto.matricula}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.tramiteTrafico.vehiculoDto.tipoVehiculo.tipoVehiculo!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="matricula">Tipo de Vehículo: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.vehiculoDto.tipoVehiculo.tipoVehiculo} - 
						%{tramiteTraficoBaja.vehiculoDto.tipoVehiculo.descripcion}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.estado!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Estado: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(tramiteTraficoBaja.estado)}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esNuevasBajas() && tramiteTraficoBaja.resCheckBtv != null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="respuesta">Respuesta Check BTV: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.resCheckBtv}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.respuesta!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="respuesta">Respuesta DGT: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.respuesta}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{@org.gestoresmadrid.core.model.enumerados.MotivoBaja@convertirTexto(tramiteTraficoBaja.motivoBaja)!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Motivos de la baja: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{@org.gestoresmadrid.core.model.enumerados.MotivoBaja@convertirTexto(tramiteTraficoBaja.motivoBaja)}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.dnicotitulares!=null}">
			<tr>
				<td align="left">
					<label for="anotaciones">Cotitulares:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.dnicotitulares}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{true.equals(tramiteTraficoBaja.permisoCircu) || true.equals(tramiteTraficoBaja.tarjetaInspeccion) ||
					  true.equals(tramiteTraficoBaja.bajaImpMunicipal) || true.equals(tramiteTraficoBaja.justificanteDenuncia) ||
					  true.equals(tramiteTraficoBaja.propiedadDesguace) || true.equals(tramiteTraficoBaja.pagoImpMunicipal) ||
					  true.equals(tramiteTraficoBaja.declaracionResiduo)}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="estado">Datos de la tarjeta de ITV: </label>
				</td>

				<s:if test="%{true.equals(tramiteTraficoBaja.permisoCircu)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Permiso de Circulación.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.tarjetaInspeccion)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Tarjeta de inspección técnica.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.bajaImpMunicipal)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Baja del Impuesto Municipal sobre Vehiculos de Tracción Mecánica.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.justificanteDenuncia)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Justificante de denuncia por sustracción.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.propiedadDesguace)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Documento acreditativo de la propiedad del vehículo, si se ha adquirido para el desguace.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.pagoImpMunicipal)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Justificante del pago del Impuesto Municipal sobre Vehículos de Tracción Mecánica.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.declaracionResiduo)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Declaración NO es Residuo Sólido.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.declaracionNoEntregaCatV)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Declaración jurada No Existe No Circula / Declaración No entrega CATV.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.cartaDGTVehiculoMasDiezAn)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Declaración Carta DGT Vehículo más de 10 años.
						</td>
					</tr>
				</s:if>

				<s:if test="%{true.equals(tramiteTraficoBaja.certificadoMedioAmbiental)}">
					<tr>
						<td align="left" width="10%">
						</td>
						<td>
							Certificado Medioambiental.
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
		<s:if test="%{tramiteTraficoBaja.tasa.codigoTasa!=null && tramiteTraficoBaja.tasa.codigoTasa != '-1'}">
			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">Tipo de Tasa: </label>
				</td>

				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">4.1</label>
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap" width="120">
					<label for="tasa">Código de Tasa: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.tasa.codigoTasa}"/>
				</td>
			</tr>
		</s:if>
		<s:if test="%{tramiteTraficoBaja.tasaDuplicado!=null}">
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
					<label for="tasa">Codigo de Tasa: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.tasaDuplicado}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.fechaPresentacion.dia!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
					<label for="fechaPresentacion">Fecha de presentación: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.fechaPresentacion.dia}"/>/
					<s:label value="%{tramiteTraficoBaja.fechaPresentacion.mes}"/>/
					<s:label value="%{tramiteTraficoBaja.fechaPresentacion.anio}"/>
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
		<s:if test="%{tramiteTraficoBaja.titular.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="24%">
					<label for="datosTitular">Nombre y apellidos del titular: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.titular.persona.nombre}"/>
					<s:label value="%{tramiteTraficoBaja.titular.persona.apellido1RazonSocial}"/>
					<s:label value="%{tramiteTraficoBaja.titular.persona.apellido2}"/>
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap" width="13%">
					<label for="nifTitular">DNI del titular: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.titular.persona.nif}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.representanteTitular.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="datosRepresentante">Nombre y apellidos del representante titular: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.representanteTitular.persona.nombre}"/>
					<s:label value="%{tramiteTraficoBaja.representanteTitular.persona.apellido1RazonSocial}"/>
					<s:label value="%{tramiteTraficoBaja.representanteTitular.persona.apellido2}"/>
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap">
					<label for="nifRepresentante">DNI del representante titular: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.representanteTitular.persona.nif}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.adquiriente.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="datosAdquiriente">Nombre y apellidos del adquiriente: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.adquiriente.persona.nombre}"/>
					<s:label value="%{tramiteTraficoBaja.adquiriente.persona.apellido1RazonSocial}"/>
					<s:label value="%{tramiteTraficoBaja.adquiriente.persona.apellido2}"/>
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap">
					<label for="nifAdquiriente">DNI del adquiriente: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.adquiriente.persona.nif}"/>
				</td>
			</tr>
		</s:if>

		<s:if test="%{tramiteTraficoBaja.representanteAdquiriente.persona.nif!=null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="datosRepresentanteAdquiriente">Nombre y apellidos del representante adquiriente: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.representanteAdquiriente.persona.nombre}"/>
					<s:label value="%{tramiteTraficoBaja.representanteAdquiriente.persona.apellido1RazonSocial}"/>
					<s:label value="%{tramiteTraficoBaja.representanteAdquiriente.persona.apellido2}"/>
				</td>

				<td width="20"></td>

				<td align="left" nowrap="nowrap">
					<label for="nifRepresentanteAdquiriente">DNI del representante adquiriente: </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:label value="%{tramiteTraficoBaja.representanteAdquiriente.persona.nif}"/>
				</td>
			</tr>
		</s:if>
	</table>
	<%@include file="../resumenAcciones.jspf" %>
	<table class="acciones" width="720px" align="left">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esConsultableOGuardableBaja(tramiteTraficoBaja)}">
			<td>
				<input class="boton" type="button" id="bGuardarBaja" name="bGuardarBaja" value="Guardar" onClick="javascript:guardarAltaTramiteTraficoBaja();" onKeyPress="this.onClick"/>
			</td>
		</s:if>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esValidable(tramiteTraficoBaja)}">
			<td id="idTdValidar">
				<input class="boton" type="button" id="bValidarBaja" name="bValidarBaja" value="Validar" onClick="javascript:validarAltaTramiteTraficoBaja();" onKeyPress="this.onClick"/>
			</td>
		</s:if>
	
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esConsultaBTV(tramiteTraficoBaja)}">
			<td>
				<input class="boton" type="button" id="bComprobarBaja" name="bComprobarBaja" value="Comprobar" onClick="javascript:comprobarBaja();" onKeyPress="this.onClick"/>
			</td>
		</s:if>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esEnvioExcelBajas(tramiteTraficoBaja)}">
			<td>
				<input class="boton" type="button" id="bPdteExcel" name="bPdteExcel" value="Envío a Excel" onClick="return pendientesEnvioAExcelAltaBaja();" onKeyPress="this.onClick"/>
			</td>
		</s:if>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esTramitableTelematicamente(tramiteTraficoBaja)}">
			<td>
				<input class="boton" type="button" id="bTramTelematicamenteBaja" name="bTramTelematicamenteBaja" value="Baja Telemática" onClick="return tramTelematicamenteTramiteTraficoBaja();" onKeyPress="this.onClick"/>
			</td>
		</s:if>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esImprimible(tramiteTraficoBaja)}">
			<td>
				<input type="button" class="boton" name="bImprimir" id="idBotonImprimir" value="Imprimir PDF" onClick="return imprimirPdf();" onKeyPress="this.onClick"/>
				&nbsp;
			</td>
		</s:if>
	</table>
</div>