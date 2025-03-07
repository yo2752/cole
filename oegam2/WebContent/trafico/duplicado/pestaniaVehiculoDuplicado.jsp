<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">

	<s:hidden name="tramiteTraficoDuplicado.vehiculoDto.fechaMatriculacion.hora" />
	<s:hidden name="tramiteTraficoDuplicado.vehiculoDto.fechaMatriculacion.minuto" />
	<s:hidden name="tramiteTraficoDuplicado.vehiculoDto.fechaMatriculacion.segundo" />
	<s:hidden name="tramiteTraficoDuplicado.vehiculoDto.fechaItv.hora" />
	<s:hidden name="tramiteTraficoDuplicado.vehiculoDto.fechaItv.minuto" />
	<s:hidden name="tramiteTraficoDuplicado.vehiculoDto.fechaItv.segundo" />
	<s:hidden id="idDireccion" name="tramiteTraficoDuplicado.vehiculoDto.direccion.idDireccion" />
	<s:hidden id="idVehiculo" name="tramiteTraficoDuplicado.vehiculoDto.idVehiculo" />

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Vehiculo</td>
			<td align="right">
				<img title="Ver evolución" onclick="consultaEvolucionVehiculo('idVehiculoText','<s:property value="tramiteTraficoDuplicado.vehiculoDto.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">VEHÍCULO</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="11%">
				<label for="matriculaVehiculo">Matrícula: <span class="naranja">*</span></label>
			</td>

			<td align="left" nowrap="nowrap">
				<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield id="matriculaVehiculo" disabled="%{flagDisabled}"
								name="tramiteTraficoDuplicado.vehiculoDto.matricula" onkeypress="return validarMatricula(event)"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';"
								onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"
								style="text-transform : uppercase" size="15" maxlength="9"
							readonly="%{tramiteTraficoDuplicado.vehiculoDto.idVehiculo != null}" />
						</td>
						<td align="left" nowrap="nowrap">
							<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" onclick="javascript:buscarVehiculoDuplicado('matriculaVehiculo')"/>
						</td>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<td align="left" nowrap="nowrap">
								<label for="idVehiculoLabel">Id Vehículo:</label>
							</td>
							<td align="left" nowrap="nowrap">
								<s:textfield id="idVehiculoText" value="%{tramiteTraficoDuplicado.vehiculoDto.idVehiculo}" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="10" cssClass="campo_disabled" name="idDireccion_admin" />
							</td>
						</s:if>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="fechaMatriculacionVehiculo">Fecha de Matriculación: <span class="naranja">*</span></label>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield id="diaMatriculacionVehiculo"
								disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.vehiculoDto.fechaMatriculacion.dia"
								onkeypress="return validarDia(this,event)" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield id="mesMatriculacionVehiculo"
								disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.vehiculoDto.fechaMatriculacion.mes"
								onkeypress="return validarMes(this,event)" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield id="anioMatriculacionVehiculo"
								disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.vehiculoDto.fechaMatriculacion.anio"
								onkeypress="return validarAnio(this,event)" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="self.gfPop.calendarioSeleccionado = 'top.quitarTasasSiMasDeQuince(\'anioMatriculacionVehiculo\',\'mesMatriculacionVehiculo\',\'diaMatriculacionVehiculo\');';if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatriculacionVehiculo, document.formData.mesMatriculacionVehiculo, document.formData.diaMatriculacionVehiculo);return false;" title="Seleccionar fecha">
								<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario" />
							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<label for="fechaItvDuplicado">Fecha ITV:<span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="5">
				<table>
					<tr>
						<td>
							<s:textfield id="diaItv"
								disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.vehiculoDto.fechaItv.dia"
								onblur="this.className='input2';" onkeypress="return validarDia(this,event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td align="left" nowrap="nowrap" width="%">
							<s:textfield id="mesItv"
								disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.vehiculoDto.fechaItv.mes"
								onblur="this.className='input2';" onkeypress="return validarMes(this,event)"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td>/</td>
						<td align="left" nowrap="nowrap" width="5">
							<s:textfield id="anioItv"
								disabled="%{flagDisabled}" name="tramiteTraficoDuplicado.vehiculoDto.fechaItv.anio"
								onblur="this.className='input2';" onkeypress="return validarAnio(this,event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>

						<td align="left" nowrap="nowrap">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioItv, document.formData.mesItv, document.formData.diaItv);return false;" title="Seleccionar fecha">
								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario" />
							</a>
						</td>
					</tr>
				</table>
			</td>
			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esUsuarioSive()">
				<td align="left" nowrap="nowrap" style="vertical-align:middle;">
					<label for="idNive">NIVE:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" id="idNive"
						name="tramiteTraficoDuplicado.vehiculoDto.nive"
						onfocus="this.className='inputfocus';"
						size="33" maxLength="32" />
				</td>
			</s:if>
		</tr>
	</table>
	<table align="center">
		<tr>
			<td><input type="button" class="boton" name="bLimpiarVehiculo" id="bLimpiarVehiculo" value="Limpiar Formulario" onClick="return limpiarVehiculoAltaTramiteTraficoDuplicado('Vehiculo');" onKeyPress="this.onClick"/></td>
			<td nowrap="nowrap" width="20%"></td>
		</tr>
	</table>
</div>