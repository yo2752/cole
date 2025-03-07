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
<s:hidden id="acreditacionPago" key="formularioAutorizarTramitesDto.acreditacionPago" />
<s:hidden id="acreditacionHerenciaDonacion" key="formularioAutorizarTramitesDto.acreditacionHerenciaDonacion" />
<s:hidden id="acreditacionSubastaJudicial" key="formularioAutorizarTramitesDto.acreditacionSubastaJudicial" />
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
<!-- <a href="descargarAnexo1ConsultaTramiteTrafico.action" class="anexos" title="consultar/descargar anexo">Descargar ANEXO I</a> -->
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
				Adquiriente:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.doiAdquiriente" id="idDoiAdquiriente"
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';" size="10" maxlength="15"
				disabled="true" /></td>
	</tr>
		<tr>
		<td align="left" nowrap="nowrap"><label for="labelDoiTitular">DOI
				Transmitente:</label></td>
		<td align="left" nowrap="nowrap"><s:textfield
				name="formularioAutorizarTramitesDto.doiTransmitente" id="idDoiTransmitente"
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
		<td><select id="opciones" name="opciones">
				<option value="">Selecciona una opción</option>
				<option value="opcion1">IVTM</option>
				<option value="opcion2">HERENCIA</option>
				<option value="opcion3">DONACIÓN</option>
				<option value="opcion4">EXENTO CTR</option>
				<option value="opcion5">SUBASTA</option>
				<option value="opcion6">SENTENCIA JUDICIAL</option>
		</select></td>
	</tr>
	<tr>
		<td align="left">
			<div id="opcion1-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idAcreditacionAbono">ACREDITACIÓN ABONO:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.acreditacionAbono" id="idAcreditacionAbono" onkeypress="this.onClick"/>
						</td>
					</tr>
					<tr>
						<td><label for="idInformeVehiculo">INFORME VEHICULO:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.informeVehiculo" id="idInformeVehiculo" onkeypress="this.onClick"/>
						</td>		
					</tr>
					
				</table>

			</div>
			<div id="opcion2-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idAnexoI">DECLARACIÓN RESPONSABLE FIRMADA POR EL INTERESADO (modelo ANEXO I):</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.anexoI" id="idAnexoI" onkeypress="this.onClick"/>
						</td>
					</tr>
					<tr>
						<td><label for="idJustificanteIsd">JUSTIFICANTE LIQUIDACIÓN IMPUESTO SUCESIONES (ISD):</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.justificanteIsd" id="idJustificanteIsd" onkeypress="this.onClick"/>
						</td>
					</tr>
				</table>
			</div>
			<div id="opcion3-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idDocumentoAcreditativoDonacion">DOCUMENTO ACREDITATIVO DE LA DONACION:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.documentoAcreditativoDonacion" id="idDocumentoAcreditativoDonacion" onkeypress="this.onClick"/>
						</td>
					</tr>
					<tr>
						<td><label for="idJustificanteIsd">JUSTIFICANTE LIQUIDACIÓN IMPUESTO SUCESIONES (ISD):</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.justificanteIsd" id="idJustificanteIsd" onkeypress="this.onClick"/>
						</td>
					</tr>
				</table>
			</div>
			<div id="opcion4-content" style="display: none;">
			<table>
					<tr>
						<td>
						<div class = "sangria">
							<label for="labelAcreditacion" >ACREDITACION: Comprobar y marcar el check</label>
						</div>
						<table>
							<tr>
								<td align="left" nowrap="nowrap" class="checkAcreditar">
										<s:checkbox name="formularioAutorizarTramitesDto.checkAcreditacion" id="idAcreditar" onkeypress="this.onClick"/>
								</td>
							</tr>
							<s:if test="%{formularioAutorizarTramitesDto.esTransporteBasura}">
								<tr class = "ajustarText" >
								<td>
									<div class='motivo'><label for="idTransporteBasura">Vehículos especialmente acondicionados para el transporte de basuras e inmundicias </label></div>
									<label for="idTransporteBasura">Ficha ITV, revisar clasificación industria (campo CL), termina con el código “55” y servicio A-11 Basurero. </label></td>
								</tr>
							</s:if>
							<s:elseif test="%{formularioAutorizarTramitesDto.esTransporteDinero}">
								<tr class = "ajustarText" >
									<td>
									<div class='motivo'><label for="idTransporteDinero">Vehículos especialmente acondicionados para el transporte de dinero, valores y mercancías preciosas </label></div>
									<label for="idTransporteDinero">Ficha ITV, revisar clasificación industria (campo CL), termina con el código “22”. </label></td>
								</tr>
							</s:elseif>
							<s:elseif test="%{formularioAutorizarTramitesDto.esVelMaxAutorizada}">
								<tr class = "ajustarText" >
									<td>
									<div class='motivo'><label for="idVelMaxAutorizado">Vehículos cuya velocidad máxima autorizada no supere los 40 kilómetros por hora </label></div>
									<label for="idVelMaxAutorizado">Ficha ITV, revisar velocidad máxima (campo T): no supere 40km/h y tipo de matrícula vehículo especial. </label></td>
								</tr>
							</s:elseif>
							<s:elseif test="%{formularioAutorizarTramitesDto.esVehUnidoMaquina}">
								<tr class = "ajustarText" >
									<td>
									<div class='motivo'><label for="idVehUnidoMaquina">Vehículos que lleven unidos de forma permanente máquinas o instrumentos </label></div>
									<label for="idVehUnidoMaquina">Ficha ITV, en el campo observaciones se indica que el vehículo lleva acoplados elementos para realizar actividades concretas y distintas al transporte.</label></td>
								</tr>
							</s:elseif>
							<s:elseif test="%{formularioAutorizarTramitesDto.esEmpresaASC}">
								<tr class = "ajustarText" >
									<td>
									<div class='motivo'><label for="idEmpresaASC">Vehículos dedicados al arrendamiento sin conductor cuya titularidad vaya a corresponder a empresas profesionales dedicadas al ASC </label></div>
									<label for="idEmpresaASC">IAE del año en curso, comprobar que el titular del vehículo está dado de alta como empresa dedicada al Alquiler Sin Conductor (ASC).Ej: Epígrafe IAE 854.1. </label></td>
								</tr>
							</s:elseif>
								<s:elseif test="%{formularioAutorizarTramitesDto.esMinistCCEntLocal}">
								<tr class = "ajustarText" >
									<td>
									<div class='motivo'><label for="idMinistCCEntLocal">Vehículos cuya titularidad vaya a corresponder a Ministerios, Comunidades Autónomas o Entidades Locales </label></div>
									<label for="idMinistCCEntLocal">Comprobar que el titular del vehículo se corresponde con un Ministerio, una Comunidad Autónoma o una Entidad Local. El CIF comienza por las letras P, Q o S. </label></td>
								</tr>
							</s:elseif>
								<s:elseif test="%{formularioAutorizarTramitesDto.esAutoescuela}">
								<tr class = "ajustarText" >
									<td>
									<div class='motivo'><label for="idAutoescuela">Vehículos dedicados al aprendizaje de la conducción cuya titularidad vaya a corresponder a autoescuelas o centros de formación autorizados </label></div>
									<label for="idAutoescuela">IAE del año en curso, comprobar que el titular del vehículo está dado de alta como empresa dedicada a la enseñanza de la conducción.Ej: Epígrafe IAE 933.1. </label></td>
								</tr>
							</s:elseif>
								<s:elseif test="%{formularioAutorizarTramitesDto.esCompraventaVeh}">
								<tr class = "ajustarText" >
									<td>
									<div class='motivo'><label for="idCompraventaVeh">Vehículos de titularidad de empresas cuya actividad principal sea la compra-venta de vehículos, para tal objeto </label></div>
									<label for="idCompraventaVeh">IAE del año en curso, comprobar que el titular del vehículo está dado de alta como empresa dedicada a la compraventa de vehículos.Ej: Epígrafe IAE 615.1 654.1. </label></td>
								</tr>
							</s:elseif>
								<s:elseif test="%{formularioAutorizarTramitesDto.esVivienda}">
								<tr class = "ajustarText" >
									<td>
									<div class='motivo'><label for="idVivienda">Vehículos remolque, semirremolque o camiones acondicionados como vivienda </label></div>
									<label for="idVivienda">Ficha ITV, revisar que se trata de uno de estos tipos de vehículo y que en la ficha ITV se indique revisar clasificación industria (campo CL), termina con el código “48” y servicio B-17 Vivienda.  </label></td>
								</tr>
							</s:elseif>
						</table>	
						</td>	
					</tr>
				</table>
			</div>
			<div id="opcion5-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idDocumentoAcreditativoSubastaJudicial">DOCUMENTO ACREDITATIVO DE LA SUBASTA:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.documentoAcreditativoSubastaJudicial" id="idDocumentoAcreditativoSubastaJudicial" onkeypress="this.onClick"/>
						</td>
					</tr>
				</table>
			</div>
			<div id="opcion6-content" style="display: none;">
				<table>
					<tr>
						<td><label for="idDocumentoAcreditativoSubastaJudicial">DOCUMENTO ACREDITATIVO DE LA SENTENCIA JUDICIAL:</label></td>
						<td align="left" nowrap="nowrap">
							<s:checkbox name="formularioAutorizarTramitesDto.documentoAcreditativoSubastaJudicial" id="idDocumentoAcreditativoSubastaJudicial" onkeypress="this.onClick"/>
						</td>
					</tr>
				</table>
			</div>
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
<script type="text/javascript">
	cambioOptionCtit(acreditacionPago);
</script>
<style>
 	    .anexos {
            position: absolute;
  			top: 7em;
   			left: 59em;
    		color: white;
    		text-decoration: none;
        }
		.anexos:hover{
		  color: red;
		}
		
		.anexos:active{
		  color: blue;
		}
</style>