<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set var="esIniciado" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esConsultableOGuardableMatriculacion(tramiteTrafMatrDto)}"/>
<s:set var="esFinalizado" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esFinalizadoTelematicamenteImpreso(tramiteTrafMatrDto)}"/>

<div class="contentTabs" id="PagosEImpuestos" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>PAGOS Y PRESENTACIÓN</td>
			</tr>
		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE PRESENTACIÓN EN LA AEAT</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="width: 250px;">
					<label for="cem">Código Electrónico de Matriculación (CEM)</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.cem"
						id="idCem" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						size="8" maxlength="8"/>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<label for="exentoCem">Exento CEM:</label>
							</td>
							<td align="left" nowrap="nowrap">
								<s:checkbox id="exentoCem" disabled="%{flagDisabled}" name="tramiteTrafMatrDto.exentoCem" onchange="javascript:onchangeExentoCem();" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="width: 250px;">
					<label for="idCema">Código Electrónico de Matriculacion Agrícola (CEMA)</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="idCema" name="tramiteTrafMatrDto.cema"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="8" maxlength="8"/>
				</td>
			</tr>
		</table>

		<!-- //TODO MPC. Cambio IVTM -->
		<%@include file="pestaniaIVTMMatriculacion.jsp" %>

		<s:if test="#admin || #permisoIVTM">
			<script type="text/javascript">
				$(document).ready(function(){checkIvtm();});
			</script>
		</s:if>

		<table class="tablaformbasica" cellSpacing="1" cellPadding="5" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="width:10em">
					<label for="idJustificadoIvtm">Justificado IVTM</label>
				</td>
				<td align="left">
					<s:checkbox name="tramiteTrafMatrDto.justificadoIvtm" id="idJustificadoIvtm"/>
				</td>
			</tr>
		</table>

		<!-- Mantis 30552 -->
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion@getInstance().esSuperTelematicoMatriculacion()}">
			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
				<tr>
					<td class="tabular">
						<span class="titulo">Justificante Pago IVTM</span>
					</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap" colspan="7">
						<span	style="color: #A52642; font-weight: bold; FONT-SIZE: 1.1em;">DECLARACIÓN DE RESPONSABILIDAD</span>
					</td>
				</tr>
				<s:if test="#esIniciado">
					<tr>
						<td align="left" nowrap="nowrap" colspan="7">
							<label	for="idCheckJustifIVTM">
								<b>El Gestor Administrativo Colegiado manifiesta bajo su responsabilidad<br>
								que conoce que el fichero introducido puede ser objeto de comprobación administrativa
								</b>
								<span class="naranja">*</span>
							</label>
							<s:checkbox name="tramiteTrafMatrDto.checkJustifFicheroIvtm" id="idCheckJustifIVTM"/>
						</td>
					</tr>
					<tr>
						<td align="left"><label>Fichero <span class="naranja">(Archivos pdf y tamaño máximo de 20MB)</span></label></td>
						<td>
							<s:label value="%{nombreFicheroJustifIvtm}" name="nombreFicheroJustifIvtm"/>
						</td>
						<td>
							<s:file align="right" id="fichero" size="50" name="fileJustifIvtm" value="fileJustifIvtm"/>
						</td>
					</tr>
					<tr align="center">
						<td></td>
						<td nowrap="nowrap" width="30%">
							<input type="button" class="botonGrande" id="botonSubirFichero" value="Subir fichero" onclick="incluirFichero();" />
						</td>
						<td>
							<input type="button" class="boton" name="bVerJustificanteIVTM" id="idVerJustificanteIVTM"
								value="Ver Justificante" onClick="javascript:descargarJustificanteIVTM();" onKeyPress="this.onClick"/>
						</td>
					 </tr>
				</s:if>
				<s:if test="#esFinalizado">
					<tr>
						<td align="left" nowrap="nowrap" colspan="7">
							<label	for="idCheckJustifIVTM">
								<b>El Gestor Administrativo Colegiado manifiesta bajo su responsabilidad<br>
								que conoce que el fichero introducido puede ser objeto de comprobación administrativa</b>
								<span class="naranja">*</span>
							</label>
							<s:checkbox name="tramiteTrafMatrDto.checkJustifFicheroIvtm" id="idCheckJustifIVTM"/>
						</td>
					</tr>
					<tr align="center">
						<td></td>
						<td>
							<input type="button" class="boton" name="bVerJustificanteIVTM" id="idVerJustificanteIVTM"
								value="Ver Justificante" onClick="javascript:descargarJustificanteIVTM();" onKeyPress="this.onClick"/>
						</td>
					</tr>
				</s:if>
			</table>
		</s:if>
		<s:else>
			<s:hidden id="idHiddenCheckFicheroIvtm" name="tramiteTrafMatrDto.checkJustifFicheroIvtm"/>
		</s:else>
		<!-- -->

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DECLARACIÓN DE RESPONSABILIDAD</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" colspan="3">
					El Gestor Administrativo colegiado asume la responsabilidad de que se trata del correspondiente modelo del AEAT del vehículo que pretende matricular.
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="5%">
					<b>ACEPTA</b>
				</td>
			</tr>
		</table>

		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE PRESENTACIÓN EN LA DGT</span>
					<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
						<s:if test="%{(#numExpediente!=null && #numExpediente!='')}">
							<img title="Ver evolución JPT" onclick="abrirEvolucionJPT('<s:property value="tramiteTrafMatrDto.numExpediente"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
						</s:if>
					</s:if>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="matriculaDGT">Matrícula </label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" name="tramiteTrafMatrDto.vehiculoDto.matricula"
						id="matricula" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarMatricula(event)" size="9" maxlength="9" readonly="true" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="jefaturaId">Jefatura provincial:<span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select id="jefaturaId"	name="tramiteTrafMatrDto.jefaturaTraficoDto.jefaturaProvincial"
						disabled="%{flagDisabled}" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getJefaturasTraficoTodas()"
						headerKey="-1" headerValue="Seleccione jefatura provincial" listKey="jefatura_provincial" listValue="descripcion"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td width="22%" align="left" width="14%">
					<label for="diaAltaIni">Fecha de Presentación</label>
				</td>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="tramiteTrafMatrDto.fechaPresentacion.dia" id="idDiaPresentacionDGT" onkeypress="return validarDia(this,event)"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
									disabled="%{flagDisabled}"/>
							</td>
							<td width="3%" style="font-size:15px;vertical-align:middle;">/</td>
							<td width="5%" nowrap="nowrap" align="left">
								<s:textfield name="tramiteTrafMatrDto.fechaPresentacion.mes" id="idMesPresentacionDGT" onkeypress="return validarMes(this,event)"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2"
									disabled="%{flagDisabled}"/>
							</td>
							<td width="3%" style="font-size:15px;vertical-align:middle;">/</td>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="tramiteTrafMatrDto.fechaPresentacion.anio" id="idAnioPresentacionDGT" onkeypress="return validarAnio(this,event)"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4"
									disabled="%{flagDisabled}" />
							</td>
							<td align="left" nowrap="nowrap" width="5%">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioPresentacionDGT, document.formData.idMesPresentacionDGT, document.formData.idDiaPresentacionDGT);return false;" HIDEFOCUS title="Seleccionar fecha">
									<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
							<td width="auto" nowrap="nowrap" align="left"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			recargarComboTasas('TipoTasa','CodigoTasaId','CodigoTasa');
			habilitarCEMA();
			// JMC: Incidencia Mantis 3020 3187 Usuario Especial necesita tener deshabilitado todo
			if (document.getElementById("permisoEspecial").value == "true") {
				document.getElementById("idCheckPagoEfectivo").disabled = true;
				document.getElementById("idCem").disabled = true;
			}
			onchangeExentoCem();
		</script>
	</div>
</div>