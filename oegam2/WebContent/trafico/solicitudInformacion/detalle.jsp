<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/solicitudes.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<ol id="toc">
	<li><a href="#Vehiculos">Veh&iacute;culos</a></li>
	<li><a href="#Solicitud">Solicitud</a></li>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoSolicitudes() && tramite.estado==\"13\"}">
		<li><a href="#Facturacion">Facturaci&oacute;n</a></li>
    </s:if>	
	<s:if test="%{tramite.numExpediente!=null}">
		<li><a href="#Resumen">Resumen</a></li>
	</s:if>
</ol>

<s:form method="post" id="formData" name="formData" cssStyle="100%">
	<s:hidden id="idHiddenNumeroExpediente" name="tramite.numExpediente" />
	<s:hidden name="tramite.contrato.idContrato" />
	<s:hidden name="tramite.usuarioDto.idUsuario" />
	<s:hidden id="numColegiado" name="tramite.numColegiado" />
	<s:hidden id="estado" name="tramite.estado" />
	<s:hidden name="tramite.idTipoCreacion" />
	<s:hidden name="tramite.idContrato" />
	<s:hidden name="existeFichero" />
	<input type="hidden" id="idVehiculoBorrar" name="idVehiculoBorrar" />

	<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
	<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>

	<%@include file="../../includes/erroresMasMensajes.jspf"%>

	<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
		<%@include file="../../includes/bloqueLoading.jspf"%>
	</div>
	<div class="contentTabs" id="Vehiculos" style="width: 100%;">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%">
			<tr>
				<td class="tabular"><span class="titulo">VEHÍCULO:</span></td>
			</tr>
		</table>
		<table cellPadding="1" cellSpacing="3" class="tablaformbasica" width="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="10%"><label for="matricula">Matrícula: </label></td>
				<td>
					<s:textfield disabled="%{flagDisabled}"
						name="solicitud.vehiculo.matricula" id="matricula"
						style="text-transform : uppercase"
						onblur="this.className='input';"
						onkeypress="return validarMatricula(event)"
						onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" 
						onfocus="this.className='inputfocus';" size="9" maxlength="9" />
				</td>
				<td align="left" nowrap="nowrap" width="10%"><label for="bastidor">Nº Bastidor: </label></td>
				<td>
					<s:textfield disabled="%{flagDisabled}"
						name="solicitud.vehiculo.bastidor" id="bastidor"
						onblur="this.className='input';"
						onfocus="this.className='inputfocus';"
						onkeypress="return validarBastidor(event);" size="21"
						maxlength="21" />
					</td>
				<s:if test="@trafico.utiles.UtilesVistaTrafico@getInstance().nuevoInteve()">
				<td align="left" nowrap="nowrap" width="10%">
					<label for="bastidor">NIVE: </label>
				</td>
				<td>
					<s:textfield name="solicitud.vehiculo.nive" 
							disabled="%{flagDisabled}"
							onblur="this.className='input';" 
           					onfocus="this.className='inputfocus';"
           					onkeypress="return validarBastidor(event);"
           					size="35" maxlength="32"
           					id="nive"/>
				</td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="10%"><label for="tipoTasa">Tipo de Tasa:</label></td>
				<td align="left" nowrap="nowrap"><label for="tipoTasa">4.1</label></td>
				<td align="left" nowrap="nowrap" width="13%"><label for="codigoTasa">Código de Tasa: <span class="naranja">*</span></label></td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select id="codigoTasa"
						onblur="this.className='input2';" disabled="%{flagDisabled}"
						onfocus="this.className='inputfocus';"
						name="solicitud.tasa.codigoTasa"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCodigosTasaBajas(tramite.idContrato)"
						headerKey="" headerValue="Seleccione Código de Tasa"
						listKey="codigoTasa" listValue="codigoTasa" />
				</td>
			</tr>
			
			<s:if test="@trafico.utiles.UtilesVistaTrafico@getInstance().nuevoInteve() || @trafico.utiles.UtilesVistaTrafico@getInstance().sesionUsuarioPruebasInteve3() == 'SI'">
				<tr>        	       			
					<td align="left">
	   					<label for="anotaciones">Motivo Informe:<span class="naranja">*</span></label>
	   				</td>
		       		<td align="left" nowrap="nowrap" colspan="5">
		       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getMotivoInteve()"
							name="solicitud.motivoInteve" id="motivoInteve" headerKey=""
							onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							headerValue="Seleccionar Motivo" listKey="valorEnum"
							listValue="nombreEnum" />
					</td>
				</tr>
			</s:if>
		</table>
		<table cellPadding="1" cellSpacing="3" class="tablaformbasica"
			width="100%" align="center">
			<tr>
				<td align="center">
					<display:table name="tramite.solicitudes"
						excludedParams="*" class="tablacoin" id="SolicitudesVehiculos"
						summary="Listado de Solicitudes Vehículos" cellspacing="0"
						cellpadding="0" uid="row">
						<display:column property="vehiculo.matricula" title="Matrícula" defaultorder="descending" style="width:3%" />
						<display:column property="vehiculo.bastidor" title="Bastidor" defaultorder="descending" style="width:5%" />
						<display:column property="vehiculo.nive" title="NIVE" defaultorder="descending" style="width:5%"/>
						<display:column property="tasa.codigoTasa" title="Código Tasa" defaultorder="descending" style="width:4%" />
						<display:column property="resultado" title="Resultado" defaultorder="descending" style="width:8%" />
						<display:column sortProperty="estado" title="Estado" defaultorder="descending" style="width:3%">
							<s:property value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteSolicitudInformacion@convertirTexto(#attr.row.estado)}" />
						</display:column>
						<display:column title="Motivo Inteve" defaultorder="descending" style="width:3%">
							<s:property value="%{@org.gestoresmadrid.core.model.enumerados.MotivoConsultaInteve@convertirTexto(#attr.row.motivoInteve)}" />
						</display:column>
						<display:column style="width:3%">
							<s:if
								test="%{!(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
								<a onclick="" href="javascript:borrarSolicitudInformacion('${row.vehiculo.idVehiculo}');">Borrar</a>
							</s:if>
						</display:column>
					</display:table>
				</td>
			</tr>
		</table>

		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoSolicitudes() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
			<table class="acciones">
				<tr>
					<s:if test="tramite.estado!=null && (tramite.estado==\"13\" || tramite.estado==\"10\")"></s:if>
					<s:else>
						<td align="center" style="size: 100%; TEXT-ALIGN: center;">
							<input id="bGuardarSolicitud" type="button"
								value="Guardar Solicitud Vehículo"
								style="size: 100%; TEXT-ALIGN: center;"
								onClick="return guardarSolicitudInformacion('Vehiculos');" />
						</td>
					</s:else>
					
					<s:if test="tramite.estado!=null && tramite.estado!=\"13\" && tramite.estado!=\"10\" && tramite.solicitudes.size() > 0">

						<s:if test="@trafico.utiles.UtilesVistaTrafico@getInstance().nuevoInteve()">
								<td align="right" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
									<input
										type="button" style="size: 100%; TEXT-ALIGN: center;"
										name="bFinalizar" id="botonInteveNuevo"
										value="Consultar en INTEVE3"
										onClick="return solicitarInforme(this);"
										onKeyPress="this.onClick" />
								</td>
						</s:if>
						<s:else>
							<%--<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().isSeridorInformesTecnicosAVPO()}">
									<td align="right" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
										<input
											type="button" style="size: 100%; TEXT-ALIGN: center;"
											name="bFinalizar" id="bFinalizarSolicitud"
											value="Consultar AVPO Vehículos"
											onClick="return solicitarInforme(this);"
											onKeyPress="this.onClick" />
									</td>
							</s:if> --%>
							<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().isSeridorInformesTecnicosATEM() ||
								@trafico.utiles.UtilesVistaTrafico@getInstance().sesionUsuarioPruebasServidorAtem() == 'SI'}">
								<td align="right" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
									<input
										type="button" style="size: 100%; TEXT-ALIGN: center;"
										name="bFinalizar" id="botonAtem" value="Consultar en ATEM"
										onClick="return solicitarInforme(this);"
										onKeyPress="this.onClick" />
								</td>
							</s:if>
							<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().isSeridorInformesTecnicosINTEVE() ||
								@trafico.utiles.UtilesVistaTrafico@getInstance().sesionUsuarioPruebasInteve() == 'SI'}">
									<td align="right" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
										<input
											type="button" style="size: 100%; TEXT-ALIGN: center;"
											name="bFinalizar" id="botonInteve" value="Solicitar INTEVE's"
											onClick="return solicitarInforme(this);"
											onKeyPress="this.onClick" />
									</td>
							</s:if>
							<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().sesionUsuarioPruebasInteve3() == 'SI'}">
								<td align="right" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
									<input
										type="button" style="size: 100%; TEXT-ALIGN: center;"
										name="bFinalizar" id="botonInteveNuevo"
										value="Consultar en INTEVE3"
										onClick="return solicitarInforme(this);"
										onKeyPress="this.onClick" />
								</td>
							</s:if>
						</s:else>
					</s:if>
					
					<s:if test="tramite.solicitudes.size() > 0 && tramite.estado!=\"10\"">
						<td align="right" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
							<input
								type="button" style="size: 100%; TEXT-ALIGN: center;"
								name="bReiniciar" id="bReiniciar" value="Reiniciar Estados"
								onClick="reiniciarSolicitudInformacion();"
								onKeyPress="this.onClick" />
						</td>
					</s:if>
					<s:if test="%{existeFichero}">
						<td align="right" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
							<input
								type="button" style="size: 100%; TEXT-ALIGN: center;"
								name="bObtenerZip" id="bObtenerZip" value="Obtener Zip Generado"
								onClick="return descargaFicheroSolicitudInformacion();"
								onKeyPress="this.onClick" />
						</td>
					</s:if>
				</tr>
			</table>
		</s:if>
	</div>
	<div class="contentTabs" id="Solicitud" style="width: 100%;">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%">
			<tr>
				<td class="tabular"><span class="titulo">SOLICITUD:</span></td>
			</tr>
		</table>
		<table cellPadding="1" cellSpacing="3" cellpadding="1" border="0" class="tablaformbasica" width="100%">
			<tr>
				<td colspan="8">&nbsp;</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="refPropia">Referencia Propia:</label></td>
				<td align="left" nowrap="nowrap">
					<s:textfield
						disabled="%{flagDisabled}" name="tramite.refPropia"
						id="refPropia" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td align="left"><label for="anotaciones">Anotaciones:</label></td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:textarea
						disabled="%{flagDisabled}" name="tramite.anotaciones"
						id="anotaciones" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" rows="5" cols="50" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" colspan="2">
					<table>
						<tr>
							<td align="left" nowrap="nowrap" width="17%"><label for="fechaPresentacion">Fecha Presentación: </label></td>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" id="diaPresentacion"
									name="tramite.fechaPresentacion.dia"
									onblur="this.className='input2';"
									onkeypress="return validarDia(this,event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" id="mesPresentacion"
									name="tramite.fechaPresentacion.mes"
									onblur="this.className='input2';"
									onkeypress="return validarMes(this,event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" id="anioPresentacion"
									name="tramite.fechaPresentacion.anio"
									onblur="this.className='input2';"
									onkeypress="return validarAnio(this,event)"
									onfocus="this.className='inputfocus';" size="4" maxlength="4" />
							</td>
							<td align="left" nowrap="nowrap">
								<a href="javascript:;"
									onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacion, document.formData.mesPresentacion, document.formData.diaPresentacion);return false;"
									title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" ${displayDisabled}
									width="15" height="16" border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<div id="solicitanteDiv">
			<%@include file="solicitante.jsp"%>
		</div>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoSolicitudes() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
			<table class="acciones">
				<tr>
					<s:if test="tramite.estado!=null && (tramite.estado==\"13\" || tramite.estado==\"10\")"></s:if>
					<s:else>
						<td align="center" style="size: 100%; TEXT-ALIGN: center;">
							<input id="bGuardarSolicitud1" type="button" value="Guardar"
								style="size: 100%; TEXT-ALIGN: center;"
								onClick="return guardarSolicitudInformacion('Solicitud');" />
						</td>
					</s:else>
				</tr>
			</table>
		</s:if>
	</div>
	<div class="contentTabs" id="Facturacion" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<div id="facturacionDiv">
			<%@include file="pestaniaFacturacionSolInfo.jsp"%>
		</div>
	</div>
	<s:if test="%{tramite.numExpediente!=null}">
		<div class="contentTabs" id="Resumen" style="width: 100%;">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%">
				<tr>
					<td class="tabular"><span class="titulo">SOLICITUD:</span></td>
				</tr>
			</table>
			<table cellPadding="1" cellSpacing="3" cellpadding="1" border="0" class="tablaformbasica" width="100%">
				<s:if test="%{tramite.numExpediente!=null}">
					<s:if test="%{tramite.numExpediente!='-1'}">
						<tr>
							<td align="left" nowrap="nowrap" width="120"><label for="numExpediente">Número de Expediente:</label></td>
							<td align="left" nowrap="nowrap"><s:label value="%{tramite.numExpediente}" /></td>
						</tr>
					</s:if>
				</s:if>
				<s:if test="%{tramite.refPropia!=null}">
					<tr>
						<td align="left" nowrap="nowrap" width="13%"><label for="refPropiaId">Referencia Propia:</label></td>
						<td align="left" nowrap="nowrap"><s:label value="%{tramite.refPropia}" /></td>
					</tr>
				</s:if>
				<s:if test="%{tramite.anotaciones!=null}">
					<tr>
						<td align="left"><label for="anotacionesId">Anotaciones:</label></td>
						<td align="left" nowrap="nowrap" colspan="3"><s:label value="%{tramite.anotaciones}" /></td>
					</tr>
				</s:if>
				<s:if test="%{tramite.estado!=null}">
					<tr>
						<td align="left"><label for="estadoId">Estado: </label></td>
						<td align="left" nowrap="nowrap" colspan="3"><s:label value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(tramite.estado)}" /></td>
					</tr>
				</s:if>
				<s:if test="%{tramite.fechaPresentacion.dia!=null}">
					<tr>
						<td align="left" nowrap="nowrap" width="15%"><label for="fechaPagoPresentacion">Fecha de Presentación: </label></td>
						<td align="left">
							<s:label value="%{tramite.fechaPresentacion.dia}" />/ <s:label
								value="%{tramite.fechaPresentacion.mes}" />/ <s:label
								value="%{tramite.fechaPresentacion.anio}" />
						</td>
					</tr>
				</s:if>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%">
				<tr>
					<td class="tabular"><span class="titulo">SOLICITANTE:</span></td>
				</tr>
			</table>
			<table cellPadding="1" cellSpacing="3" class="tablaformbasica" width="100%">
				<s:if test="%{tramite.solicitante.persona.nif!=null}">
					<tr>
						<td align="left" nowrap="nowrap" width="24%">
							<label for="datosSolicitante">Nombre y apellidos del solicitante: </label></td>
						<td align="left" nowrap="nowrap">
							<s:label value="%{tramite.solicitante.persona.nombre}" /> <s:label
								value="%{tramite.solicitante.persona.apellido1RazonSocial}" /> <s:label
								value="%{tramite.solicitante.persona.apellido2}" />
						</td>
						<td width="20">&nbsp;</td>
						<td align="left" nowrap="nowrap" width="13%"><label for="nifSolicitante">NIF del solicitante: </label></td>
						<td align="left" nowrap="nowrap"><s:label value="%{tramite.solicitante.persona.nif}" /></td>
					</tr>
				</s:if>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
			<s:if test="%{tramite.solicitudes!=null && tramite.solicitudes.size > 0}">
				<table class="nav" cellSpacing="1" cellPadding="5" width="100%">
					<tr>
						<td class="tabular"><span class="titulo">VEHÍCULOS</span></td>
					</tr>
				</table>
				<table cellPadding="1" cellSpacing="3" class="tablaformbasica" width="100%">
					<s:iterator var="ite" value="%{tramite.solicitudes}">
						<tr>
							<td align="left">
								<s:label value="%{vehiculo.matricula}" />&nbsp;<s:label value="%{vehiculo.bastidor}" />&nbsp;&nbsp;<s:label value="%{tasa.codigoTasa}" />&nbsp;&nbsp;
								<s:label value="%{@org.gestoresmadrid.core.model.enumerados.MotivoConsultaInteve@convertirTexto(motivoInteve)}"/>
							</td>
						</tr>
					</s:iterator>
				</table>
			</s:if>
			<%@include file="../resumenAcciones.jspf"%>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoSolicitudes() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
				<table class="acciones">
					<tr>
						<s:if test="tramite.estado!=null && (tramite.estado==\"13\" || tramite.estado==\"10\")"></s:if>
						<s:else>
							<td align="center" style="size: 100%; TEXT-ALIGN: center;">
								<input
									id="bGuardarSolicitud2" type="button" value="Guardar"
									style="size: 100%; TEXT-ALIGN: center;"
									onClick="return guardarSolicitudInformacion('Resumen');" />
							</td>
						</s:else>
					</tr>
				</table>
			</s:if>
		</div>
	</s:if>
</s:form>
<s:if test="%{imprimible}">
	<script type="text/javascript">
		$(window).load(function() {
			$('#formData').attr("action", "imprimirOKSolicitudInformacion.action").trigger("submit");
		});
	</script>
</s:if>
<s:elseif test="%{tramite.estado.valorEnum == 10}">
	<script type="text/javascript">
		bloqueosSiEstadoPendiente();
	</script>
</s:elseif>
