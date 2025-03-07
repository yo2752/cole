<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden name="tramite.solicitante.nifInterviniente"/>
<s:hidden name="tramite.solicitante.numColegiado" />
<s:hidden name="tramite.solicitante.numExpediente"/>

<table class="nav" cellSpacing="1" cellPadding="5" width="100%">
	<tr>
		<td class="tabular"><span class="titulo">SOLICITANTE:</span></td>
	</tr>
</table>

<table cellPadding="1" cellSpacing="3" cellpadding="1"
	class="tablaformbasica" width="100%">
	<tr>
		<td align="left" nowrap="nowrap"><label for="nifSolicitante">NIF
				/ CIF: <span class="naranja">*</span>
		</label></td>

		<td align="left" nowrap="nowrap">
			<table>
				<tbody>
					<tr>
						<td><s:textfield disabled="%{flagDisabled}"
								name="tramite.solicitante.persona.nif" id="nifSolicitante"
								onblur="this.className='input';calculaLetraNIF(this)"
								onfocus="this.className='inputfocus';" size="9" maxlength="9" />
						</td>
						<td><s:if
								test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input align="left" type="button" class="boton-persona"
									style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
									onclick="javascript:buscarIntervinienteSolicitudInformacion()" />
							</s:if></td>

					</tr>
				</tbody>


			</table>

		</td>
		<td align="left" nowrap="nowrap"><label
			for="apellido1RazonSocialSolicitante">Primer Apellido / Razón
				Social: <span class="naranja">*</span>
		</label></td>

		<td align="left" nowrap="nowrap"><s:textfield
				disabled="%{flagDisabled}"
				name="tramite.solicitante.persona.apellido1RazonSocial"
				id="apellido1RazonSocialSolicitante"
				onblur="this.className='input';"
				onfocus="this.className='inputfocus';" size="40" maxlength="100" />
		</td>
	</tr>

	<tr>
		<td align="left" nowrap="nowrap"><label
			for="apellido2Solicitante">Segundo Apellido: </label></td>

		<td align="left" nowrap="nowrap"><s:textfield
				disabled="%{flagDisabled}"
				name="tramite.solicitante.persona.apellido2"
				id="apellido2Solicitante" onblur="this.className='input';"
				onfocus="this.className='inputfocus';" size="25" maxlength="100" />
		</td>

		<td align="left" nowrap="nowrap"><label for="nombreSolicitante">Nombre:
		</label></td>

		<td align="left" nowrap="nowrap"><s:textfield
				disabled="%{flagDisabled}" name="tramite.solicitante.persona.nombre"
				id="nombreSolicitante" onblur="this.className='input';"
				onfocus="this.className='inputfocus';" size="25" maxlength="100" />
		</td>
	</tr>

</table>

<iframe width="174" height="189" name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
	scrolling="no" frameborder="0"
	style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;"></iframe>

