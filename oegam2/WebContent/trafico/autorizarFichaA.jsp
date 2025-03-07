<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<s:form method="post" id="formData" name="formData">
<s:hidden id="valorSelecc" key="formularioAutorizarTramitesDto.valorSeleccionado" />
<s:hidden id="valorAdic" key="formularioAutorizarTramitesDto.valorAdicional" />
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>CERTIFICADO REVISIÓN COLEGIAL</td>
		</tr>
	</table>
</div>
<table class="certificado" cellSpacing="0" cellspacing="0" align="left">
	<tr>
		<td class="espacio"></td>
		<td>EXPEDIENTE REVISADO</td>
	</tr>
</table>
<table cellSpacing=3 class="tablaformbasicaCert" cellPadding="0">
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelNColegiado">Gestor
				administrativo colegiado nº:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.numColegiado" id="idNumColegiado"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="6" maxlength="4"
				disabled="true" /></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelNumExpediente">Número
				de expediente:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.numExpediente"
				id="idNumExpediente" onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="15" maxlength="15"
				disabled="true" /></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelDoiTitular">DOI
				Titular:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.doiTitular" id="idDoiTitular"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="10" maxlength="15"
				disabled="true" /></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelBastiMatri">Bastidor/MatrÍcula:</label>
		</td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.bastiMatri" id="idBastiMatri"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="18" maxlength="18"
				disabled="true" /></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelJefatura">Jefatura
				de presentación:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.jefatura" id="idJefatura"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="10" maxlength="15"
				disabled="true" /></td>
	</tr>
</table>
<table cellSpacing=3 class="tablaformbasicaCert1" cellPadding="0">
	<tr>
		<td><select id="opciones" name="opciones" onchange="cambioOption()">
				<option value="">Selecciona una opción</option>
				<option value="opcion1">VEHÍCULOS PROCEDENTES DE LA UE E
					IRLANDA DEL NORTE (NUEVOS)</option>
				<option value="opcion2">VEHÍCULOS PROCEDENTES DE UN TERCER
					PAÍS, INCLUYENDO EEE NORUEGA LIECHTENSTEIN ISLANDIA (NUEVOS)</option>
				<option value="opcion3">VEHÍCULOS PROCEDENTES DE LA UE E
					IRLANDA DEL NORTE (USADOS)</option>
				<option value="opcion4">VEHÍCULOS PROCEDENTES DE UN TERCER
					PAÍS, INCLUYENDO EEE NORUEGA LIECHTENSTEIN ISLANDIA (USADOS)</option>
		</select></td>
	</tr>
	<tr>
		<td align="left">
			<div id="opcion1-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idTipoTarjetaA">TARJETA TIPO A:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.tarjetaTipoA1" id="idTipoTarjetaA" onkeypress="this.onClick"/>
						</td>
					</tr>
					<tr>
						<td><label for="idImpuestoIedmt">IMPUESTO DE
								MATRICULACIÓN IEDMT:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIedmt1" id="idImpuestoIedmt" onkeypress="this.onClick"/>
						</td>		
					</tr>
					<tr>
						<td><label for="idImpuestoIvtm">IMPUESTO DE
								CIRCULACIÓN IVTM:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIvtm1" id="idImpuestoIvtm" onkeypress="this.onClick"/>
						</td>			
					</tr>
					<tr>
						<td><label for="idAcreditacionPropiedad">ACREDITACIÓN
								DE LA PROPIEDAD FACTURA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditacionPropiedad1" id="idAcreditacionPropiedad" onkeypress="this.onClick"/>
						</td>			
					</tr>
					<tr>
						<td><label for="idJustificanteIva">JUSTIFICANTE DE
								IVA (MODELO 309 O 300):</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.justificanteIv1" id="idJustificanteIva" onkeypress="this.onClick"/>
						</td>			
					</tr>
					<tr>
						<td><label for="idAcreditarCenso">ACREDITAR ESTAR
								INCLUIDO EN EL CENSO DE SUJETOS PASIVOS IVA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditarCenso1" id="idAcreditarCenso" onkeypress="this.onClick"/>
						</td>			
					</tr>
					<tr>
						<td><label for="idAcreditarServicioVeh">ACREDITAR
								SERVICIO DEL VEHÍCULO:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditarServicioVeh1" id="idAcreditarServicioVeh" onkeypress="this.onClick"/>
						</td>			
					</tr>
					<tr>
						<td><label for="idCema">CEMA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.cema1" id="idCema" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idNoJustifTransp">NO PRECISA
								CERTIFICADO DE TRANSPORTES:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.noJustifTransp1" id="idNoJustifTransp" onkeypress="this.onClick"/>
						</td>	
					</tr>
				</table>

			</div>
			<div id="opcion2-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idTipoTarjetaA">TARJETA TIPO A:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.tarjetaTipoA2" id="idTipoTarjetaA" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idImpuestoIedmt">IMPUESTO DE
								MATRICULACIÓN IEDMT:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIedmt2" id="idImpuestoIedmt" onkeypress="this.onClick"/>
						</td>			
					</tr>
					<tr>
						<td><label for="idImpuestoIvtm">IMPUESTO DE
								CIRCULACIÓN IVTM:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIvtm2" id="idImpuestoIvtm" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idAcreditacionPropiedad">ACREDITACIÓN
								DE LA PROPIEDAD FACTURA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditacionPropiedad2" id="idAcreditacionPropiedad" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idAcreditarServicioVeh">ACREDITAR
								SERVICIO DEL VEHÍCULO:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditarServicioVeh2" id="idAcreditarServicioVeh" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idCema">CEMA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.cema2" id="idCema" onkeypress="this.onClick"/>
						</td>					
					</tr>
					<tr>
						<td><label for="idDua">DUA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.dua2" id="idDua" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idNoJustifTransp">NO PRECISA
								CERTIFICADO DE TRANSPORTES:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.noJustifTransp2" id="idNoJustifTransp" onkeypress="this.onClick"/>
						</td>	
					</tr>
				</table>
			</div>
			<div id="opcion3-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idDocOrigianl1">DOC ORIGINAL DEL
								VEHÍCULO (PAÍS DE ORIGEN) PARTE I:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.docOriginal13" id="idDocOrigianl13" onkeypress="this.onClick"/>
						</td>			
					</tr>
					<tr>
						<td><label for="idDocOrigianl2">DOC ORIGINAL DEL
								VEHÍCULO (PAÍS DE ORIGEN) PARTE II:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.docOriginal23" id="idDocOrigianl23" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idImpuestoIvtm">PLACA VERDE (PAÍS DE
								ORIGEN):</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIvtm3" id="idImpuestoIvtm" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idTipoTarjetaA">TARJETA TIPO A:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.tarjetaTipoA3" id="idTipoTarjetaA" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idImpuestoIedmt">IMPUESTO DE
								MATRICULACIÓN IEDMT:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIedmt3" id="idImpuestoIedmt" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idImpuestoIvtm">IMPUESTO DE
								CIRCULACIÓN IVTM:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIvtm3" id="impuestoIedmt" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idAcreditacionPropiedad">ACREDITACIÓN
								DE LA PROPIEDAD (CONTRATO O FACTURA):</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditacionPropiedad3" id="idAcreditacionPropiedad" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idTraduccionContrato">TRADUCCIÓN
								CONTRATO:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.traduccionContrato3" id="idTraduccionContrato" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idITP">IMPUESTO DE TRANSMISIONES
								PATRIMONIALES ITP:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.itp3" id="idITP" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idIAE">FACTURA (VIES) / IAE:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.iae3" id="idIAE" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idAcreditarServicioVeh">ACREDITAR
								SERVICIO DEL VEHÍCULO:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditarServicioVeh3" id="idAcreditarServicioVeh" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idCema">CEMA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.cema3" id="idCema" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idNoJustifTransp">NO PRECISA
								CERTIFICADO DE TRANSPORTES:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.noJustifTransp3" id="idNoJustifTransp" onkeypress="this.onClick"/>
						</td>	
					</tr>
				</table>
			</div>
			<div id="opcion4-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idDocOrigianl1">DOC ORIGINAL DEL
								VEHÍCULO (PAÍS DE ORIGEN) PARTE I:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.docOriginal14" id="idDocOrigianl14" onkeypress="this.onClick"/>
						</td>			
					</tr>
					<tr>
						<td><label for="idDocOrigianl2">DOC ORIGINAL DEL
								VEHÍCULO (PAÍS DE ORIGEN) PARTE II:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.docOriginal24" id="idDocOrigianl24" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idTipoTarjetaA">TARJETA TIPO A:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.tarjetaTipoA4" id="idTipoTarjetaA" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idImpuestoIedmt">IMPUESTO DE
								MATRICULACIÓN IEDMT:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIedmt4" id="idImpuestoIedmt" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idImpuestoIvtm">IMPUESTO DE
								CIRCULACIÓN IVTM:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.impuestoIvtm4" id="idImpuestoIvtm" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idAcreditacionPropiedad">ACREDITACIÓN
								DE LA PROPIEDAD (CONTRATO O FACTURA):</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditacionPropiedad4" id="idAcreditacionPropiedad" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idTraduccionContrato">TRADUCCIÓN
								CONTRATO:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.traduccionContrato4" id="idTraduccionContrato" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idITP">IMPUESTO DE TRANSMISIONES
								PATRIMONIALES ITP:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.itp4" id="idITP" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idIAE">FACTURA (VIES) / IAE:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.iae4" id="idIAE" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idAcreditarServicioVeh">ACREDITAR
								SERVICIO DEL VEHÍCULO:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditarServicioVeh4" id="idAcreditarServicioVeh" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idCema">CEMA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.cema4" id="idCema" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idDua">DUA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.dua4" id="idDua" onkeypress="this.onClick"/>
						</td>	
					</tr>
					<tr>
						<td><label for="idNoJustifTransp">NO PRECISA
								CERTIFICADO DE TRANSPORTES:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.noJustifTransp4" id="idNoJustifTransp" onkeypress="this.onClick"/>
						</td>	
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<table class="certificado" cellSpacing="0" cellspacing="0" align="left">
	<tr>
		<td class="espacio"></td>
		<td>CAMPOS A VALIDAR</td>
		<td class="espacio"></td>
		<td>VALIDACIÓN</td>
	</tr>
</table>
<table cellSpacing=3 class="tablaformbasicaCert" cellPadding="0">
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelEstacionItv">ESTACIÓN ITV:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.estacionItv" id="idEstacionItv"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="10" maxlength="10"
				disabled="true" /></td>
		<td align="left" nowrap="nowrap">
			<s:checkbox name="formularioAutorizarTramitesDto.validacionEstacion" id="idValidarEstacion" onkeypress="this.onClick"/>
		</td>			
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelKilometraje">KILOMETRAJE:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.kilometraje" id="idKilometraje"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="10" maxlength="10"
				disabled="true" /></td>
		<td align="left" nowrap="nowrap">
			<s:checkbox name="formularioAutorizarTramitesDto.validacionKm" id="idValidarKm" onkeypress="this.onClick"/>
		</td>			
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelKilometraje">PAIS MATRÍCULA PREVIA:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.paisPreviaMatri" id="idPaisPreviaMatri"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="10" maxlength="10"
				disabled="true" /></td>
		<td align="left" nowrap="nowrap">
			<s:checkbox name="formularioAutorizarTramitesDto.validacionPaisPrevMatr" id="idValidarPaisPrevMatr" onkeypress="this.onClick"/>
		</td>			
	</tr>
</table>
<table cellSpacing=3 class="tablaformbasicaCert" cellPadding="0">
	<tr>
		<td align="left" nowrap="nowrap"><label for="labelObservaciones">Observaciones:</label></td>
		<td align="left" nowrap="nowrap" colspan="1">
   				<s:textarea name="formularioAutorizarTramitesDto.observaciones" 
   				id="observacionesId" 
   				disabled="%{flagDisabled}"
   				onblur="this.className='input2';" 
   				onfocus="this.className='inputfocus';" 
   				rows="3"
   				cols="50"/>
		</td>
</table>
<table align="center">
	<tr>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">				
			<td>                     
				<input class="botonMasGrande" type="button" id="bAutorizar" name="bAutorizar" value="Autorizado" onclick="return autorizarCertificacion(this);" onKeyPress="this.onClick"/>
			</td>
			<td>                     
				<input class="botonMasGrande" type="button" id="idDenegar " name="bDenegar" value="Denegar autorización" onclick="return denegarAutorizacionCertificacion(this);" onKeyPress="this.onClick"/>
			</td>
		</s:if>
	</tr>
</table>
</s:form>