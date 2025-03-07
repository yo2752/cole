<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="Modificar">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos Interviniente</td>
				<td align="right">
					<img title="Ver evolución" onclick="consultaEvolucionPersona('${interviniente.persona.nif}','${interviniente.persona.numColegiado}');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
				</td>
			</tr>
		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">PERSONA</span></td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="Nif">Nif:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:textfield name="intervinienteTrafico.persona.nif" id="Nif" size="9" maxlength="9" onblur="this.className='input';" onfocus="this.className='inputfocus';" readonly="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="Nif">Num Colegiado:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:textfield name="intervinienteTrafico.persona.numColegiado" id="Nif" size="9" maxlength="9" onblur="this.className='input';" onfocus="this.className='inputfocus';" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="apellido1RazonSoc">1º Apellido o Razón Social:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="intervinienteTrafico.persona.apellido1RazonSocial" id="apellido1RazonSoc" maxlength="100" onblur="this.className='input';" onfocus="this.className='inputfocus';" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="apellido2">2º Apellido:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="intervinienteTrafico.persona.apellido2" id="apellido2" size="30" maxlength="100" onblur="this.className='input';"
						onfocus="this.className='inputfocus';" readonly="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="nombre">Nombre:</label>
				</td>
				<td align="left"  nowrap="nowrap">
					<s:textfield name="intervinienteTrafico.persona.nombre" id="nombre" size="20" maxlength="100" onblur="this.className='input';"
						onfocus="this.className='inputfocus';" readonly="true" />
				</td>
			</tr>
		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular"><span class="titulo">INTERVINIENTE</span></td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="iae">Num Expediente:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="intervinienteTrafico.numExpediente" id="nombre" size="20" maxlength="100" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" readonly="true"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoPersona">Tipo de persona:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoIntervinientes()" id="tipoPersona"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" value="%{intervinienteTrafico.tipoInterviniente}"
						listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" 
						headerValue="-" disabled="true"/>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="iae">Código IAE:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select name="intervinienteTrafico.codigoIAE" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCodigosIAE()"
						headerKey="-1" headerValue="Seleccione Código" listKey="Codigo_IAE" listValue="descripcion"
						id="idIae" onblur="this.className='input2';" onfocus="this.className='inputfocus';" style="width:250px"/>
				</td>
				<td align="left" nowrap="nowrap" colspan="4">
					<label for="autonomo">Autónomo:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="1">
					<s:checkbox name="intervinienteTrafico.autonomo" id="idAutonomo" onclick="" disabled="%{flagDisabled}" onkeypress="this.onClick" onchange="habilitarAutonomo('idAutonomo','idIae');"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
					<label for="numero">Fecha Inicio Renting:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="4">
					<table>
						<tr>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" name="intervinienteTrafico.fechaInicio.dia"
									id="diaInicioRenting" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
							</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" name="intervinienteTrafico.fechaInicio.mes"
									id="mesInicioRenting" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
							</td>
							<td width="1%">/</td>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" name="intervinienteTrafico.fechaInicio.anio"
									id="anioInicioRenting" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
							</td>
							<td align="left" nowrap="nowrap">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioRenting, document.formData.mesInicioRenting, document.formData.diaInicioRenting);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="horaInicioRenting">Hora Inicio Renting:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" name="intervinienteTrafico.horaInicio"
						id="horaInicio" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
					<label for="numero">Fecha Fin Renting:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="4">
						<table>
							<tr>
								<td align="left" nowrap="nowrap" width="5%">
									<s:textfield disabled="%{flagDisabled}" name="intervinienteTrafico.fechaFin.dia"
										id="diaFinRenting" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
								</td>
								<td width="1%">/</td>
								<td align="left" nowrap="nowrap" width="5%">
									<s:textfield disabled="%{flagDisabled}" name="intervinienteTrafico.fechaFin.mes"
										id="mesFinRenting" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
								</td>
								<td width="1%">/</td>
								<td align="left" nowrap="nowrap" width="5%">
									<s:textfield disabled="%{flagDisabled}" name="intervinienteTrafico.fechaFin.anio"
										id="anioFinRenting" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
								</td>
								<td align="left" nowrap="nowrap">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinRenting, document.formData.mesFinRenting, document.formData.diaFinRenting);return false;" title="Seleccionar fecha">
										<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="horaFinRenting">Hora Fin Renting:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield disabled="%{flagDisabled}" name="intervinienteTrafico.horaFin"
						id="horaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="conceptoRepresentanteTitular">Concepto:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getConceptoTutela()"
							id="idConceptoInterviniente"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							name="intervinienteTrafico.conceptoRepre"
							disabled="%{flagDisabled}"
							listValue="nombreEnum"
							listKey="valorEnum"
							title="Concepto tutela"
							headerKey="-1" headerValue="-"
							onchange="cambioConceptoInterviniente()"/>
				</td>

				<td align="left" nowrap="nowrap">
					<label for="motivoRepresentanteTitular">Motivo:</label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposTutela()"
							id="idMotivoInterviniente"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							name="intervinienteTrafico.idMotivoTutela"
							listValue="nombreEnum"
							listKey="valorEnum"
							title="Motivo tutela"
							headerKey="-1" headerValue="-"
							disabled="true"/>
				</td>
			</tr>
		</table>

		<table class="acciones" width="95%" align="left">
			<tr>
				<td align="center" style="size: 100%; text-align: center; list-style: none;">
					<input type="button" class="botonGrande" name="bGuardar" id="idBotonGuardar" value="Actualizar Interviniente" onClick="return guardarInterviniente();"
						onKeyPress="this.onClick" />
					&nbsp;&nbsp;&nbsp; 
					<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver" onClick="javascript:document.location.href='inicioConsultaInterviniente.action';"
						onKeyPress="this.onClick" />
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	</div>
</div>