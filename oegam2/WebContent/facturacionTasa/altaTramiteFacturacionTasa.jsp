<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/facturacionTasa.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Altas de Facturación de Tasas</span>
				<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
				<s:set var="numExpediente" value="tramiteTrafFacturacionDto.numExpediente"/>
			</td>
		</tr>
	</table>
</div>

<div>
	<%@include file="../../includes/erroresMasMensajes.jspf"%>

	<s:form method="post" id="formData" name="formData">
	
		<s:hidden id="datosFacturacionIdDireccion" name="tramiteTrafFacturacionDto.direccion.idDireccion"></s:hidden>
		<s:hidden name="tramiteTrafFacturacionDto.persona.numColegiado" />
		<s:hidden id="nifBusqueda" name="nifBusqueda"/>
		<s:hidden name="tramiteTrafFacturacionDto.numExpediente"/>
		
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Datos del Trámite</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="left" nowrap="nowrap" width="14%">
					<label for="idNumExpediente">Nº de Expediente: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="idNumExpediente" name="tramiteTrafFacturacionDto.numExpediente" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="22" maxlength="21" disabled="true"/>
				</td>
				<td></td><td></td>
			</tr>
		</table>
	
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Datos de la Tasa</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="left" nowrap="nowrap">
        				<label for="TipoTasa">Tipo de Tasa:</label>
        		</td>
        		<td align="left">
		        	<s:select list="@org.gestoresmadrid.core.tasas.model.enumeration.TipoTasaDGT@values()"
						headerKey="-1" headerValue="-" name="tramiteTrafFacturacionDto.tipoTasa" listKey="valorEnum" listValue="nombreEnum" id="idTipoTasa"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
						onchange="obtenerTasasFacturacion(this,'codigoTasaFacturacion','codigoTasaFacturacionSeleccionado');"/>
				</td>
				<td align="left">
	   				<label for="codigoTasaFacturacion">Código Tasa: <span class="naranja">*</span></label>
	   			</td>
				<td align="left" nowrap="nowrap">	
					<select id="codigoTasaFacturacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						onchange="seleccionarCampo('codigoTasaFacturacionSeleccionado','codigoTasaFacturacion');">
						<option value="-1">Seleccione código de tasa</option> 
					</select>
					<s:hidden id="codigoTasaFacturacionSeleccionado" name="tramiteTrafFacturacionDto.codTasa"/>
				</td>
		
				<td align="left">
	   				<label for="fechaAplicacion">Fecha Aplicación Tasa: <span class="naranja">*</span></label>
	   			</td>
				
				<td align="left" nowrap="nowrap">
					<s:textfield id="diaAplicacion" name="tramiteTrafFacturacionDto.fechaAplicacion.dia" 
						onblur="this.className='input2';" onkeypress="return validarDia(this,event)"
						onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="false"/>
				</td>
				<td>/</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="mesAplicacion" name="tramiteTrafFacturacionDto.fechaAplicacion.mes" 
						onblur="this.className='input2';" onkeypress="return validarMes(this,event)"
						onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="false"/>
				</td>
				<td>/</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="anioAplicacion" name="tramiteTrafFacturacionDto.fechaAplicacion.anio"
						onblur="this.className='input2';" onkeypress="return validarAnio(this,event)"
						onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="false" />
				</td>
				<td align="left" nowrap="nowrap">
		    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAplicacion, document.formData.mesAplicacion, document.formData.diaAplicacion);return false;" title="Seleccionar fecha">
		    			<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
		    		</a>
				</td>
			</tr>
		</table>
	
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Datos del Vehículo</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="left" >
					<label for="idBastidorFact">Bastidor: <span class="naranja">*</span></label>
				</td>
	
				<td align="left" nowrap="nowrap">
					<s:textfield id="idBastidorFact" name="tramiteTrafFacturacionDto.bastidor" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="22" maxlength="21"/>
				</td>
				<td align="left">
					<label for="idMatriculaFact">Matrícula: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteTrafFacturacionDto.matricula" id="idMatriculaFact" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="13" maxlength="9" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)"  onmousemove="return validarMatricula_alPegar(event)"  />
				</td>
			</tr>
		</table>
	
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Datos del Titular de la Facturación</td>
				<s:set var="identificacion" value="tramiteTrafFacturacionDto.nif"/>
				<s:set var="colegiado" value="tramiteTrafFacturacionDto.persona.numColegiado"/>
				<td align="right">
					<s:if test="%{(#identificacion!='' && #identificacion!=null) && (#colegiado!='' && #colegiado!=null)}">
						<img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="tramiteTrafFacturacionDto.nif"/>', '<s:property value="tramiteTrafFacturacionDto.persona.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
					</s:if>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="etNif">NIF / CIF: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap" width="24%">
					<table style="align: left;">
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield id="nifTasaFacturacion" name="tramiteTrafFacturacionDto.nif"
									onblur="this.className='input2';calculaLetraNIF(this)"
									onfocus="this.className='inputfocus';" style="text-transform : uppercase" 
									size="9" maxlength="9" />
							</td>
							<td align="left" nowrap="nowrap">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteFacturacionTasa('nifTasaFacturacion')" />
								</s:if>
							</td>
						</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="tipoPersona">Tipo de persona:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersona" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="tramiteTrafFacturacionDto.persona.tipoPersona" listValue="nombreEnum"
						listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
	       			<label for="sexoAdquiriente">Sexo:</label>
	       		</td>
	         	<td align="left" nowrap="nowrap" >
		         	<s:select id="sexoAdquiriente" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
		         		disabled="%{flagDisabled}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="tramiteTrafFacturacionDto.persona.sexo"
						listValue="nombreEnum" listKey="valorEnum" title="Sexo Persona"
						headerKey="-1" headerValue="-"/>
	       		</td>
	       		<td align="left" nowrap="nowrap" colspan="2">
					<label for="apellido1">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="apellido1" name="tramiteTrafFacturacionDto.persona.apellido1RazonSocial"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for=apellido2>Segundo Apellido:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="1">
					<s:textfield id="apellido2" name="tramiteTrafFacturacionDto.persona.apellido2"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" maxlength="100" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="nombre">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield id="nombre" name="tramiteTrafFacturacionDto.persona.nombre"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30" maxlength="100" />
				</td>
			</tr>
		</table>
		<s:if test="tramiteTrafFacturacionDto.direccion.idDireccion != null " >
			<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
				<tr>
					<td align="left" nowrap="nowrap" width="100%">
						<table  style="align:left;">
							<tr>
								<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
									<td>
										<label>Id dirección</label>
										<span><s:textfield id="idDireccionFact" cssClass="campo_disabled" name="idDireccion_admin" readonly="true" value="%{tramiteTrafFacturacionDto.direccion.idDireccion}"></s:textfield></span>
									</td>
								</s:if>
							</tr>
						</table>
					</td>
				</tr>	
			</table>
		</s:if>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="etProvincia">Provincia:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idProvinciaFacturacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="tramiteTrafFacturacionDto.direccion.idProvincia"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
						headerKey="-1" headerValue="Seleccione Provincia"
						listKey="idProvincia" listValue="nombre" onchange="cargarListaMunicipios(this,'idMunicipioFacturacion','municipioSeleccionadoFacturacion');
						cargarListaTipoVia(this,'tipoViaFacturacion','tipoViaSeleccionadaFacturacion');
						inicializarTipoVia('tipoViaFacturacion','nombreViaFacturacion',viaFacturacionDuplicado);
						borrarComboPueblo('idPuebloFacturacion');" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="idMunicipioFacturacion">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="idMunicipioFacturacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onchange="cargarListaPueblos('idProvinciaFacturacion', this, 'idPuebloFacturacion', 'puebloSeleccionadoFacturacion');
						seleccionarCampo('municipioSeleccionadoFacturacion','idMunicipioFacturacion');
						obtenerCodigoPostalPorMunicipio('idProvinciaFacturacion', this, 'cpFacturacion'); 
						inicializarTipoVia('tipoViaFacturacion','nombreViaFacturacion',viaFacturacionDuplicado);" style="width: 200px;">
							<option value="-1">Seleccione Municipio</option> 
					</select> 
					<s:hidden id="municipioSeleccionadoFacturacion" name="tramiteTrafFacturacionDto.direccion.idMunicipio" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="idPuebloFacturacion">Pueblo: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="idPuebloFacturacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" onchange="seleccionarCampo('puebloSeleccionadoFacturacion','idPuebloFacturacion');"
						style="width: 200px;">
							<option value="-1">Seleccione Pueblo</option>
					</select> 
					<s:hidden id="puebloSeleccionadoFacturacion" name="tramiteTrafFacturacionDto.direccion.pueblo" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="cpFacturacion">Código Postal:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="cpFacturacion" name="tramiteTrafFacturacionDto.direccion.codPostal"
						onkeypress="return validarNumerosEnteros(event)"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="5" maxlength="5" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="tipoViaFacturacion">Tipo de vía:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<select id="tipoViaFacturacion" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onchange="seleccionarCampo('tipoViaSeleccionadaFacturacion','tipoViaFacturacion');
						cargarListaNombresVia('idProvinciaFacturacion', 'municipioSeleccionadoFacturacion', this, 'nombreViaFacturacion',viaFacturacionDuplicado);">
							<option value="-1">Seleccione Tipo Via</option> 
					</select> 
					<s:hidden id="tipoViaSeleccionadaFacturacion" name="tramiteTrafFacturacionDto.direccion.idTipoVia" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="nombreViaFacturacion">Nombre de vía:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="6">
					<s:textfield id="nombreViaFacturacion" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="tramiteTrafFacturacionDto.direccion.nombreVia"
						cssStyle="width:200px;position:relative;" autocomplete="off" />
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="">
			<tr>
				<td align="left" nowrap="nowrap" width="7%" style="vertical-align: middle">
					<label for="numeroDireccionFacturacion">Número:</label>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<s:textfield id="numeroDireccionFacturacion" name="tramiteTrafFacturacionDto.direccion.numero"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarSN2(event,this)" size="4" maxlength="3" />
				</td>
				<td align="left" nowrap="nowrap" width="7%" style="vertical-align: middle">
					<label 	for="letraDireccionFacturacion">Letra: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="letraDireccionFacturacion" name="tramiteTrafFacturacionDto.direccion.letra"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="3" />
				</td>
				<td align="left" nowrap="nowrap" width="7%" style="vertical-align: middle">
					<label for="escaleraDireccionFacturacion">Escalera: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="escaleraDireccionFacturacion" name="tramiteTrafFacturacionDto.direccion.escalera"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
				</td>
				<td align="left" nowrap="nowrap" width="7%" style="vertical-align: middle">
					<label for="pisoDireccionFacturacion">Piso: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="pisoDireccionFacturacion" name="tramiteTrafFacturacionDto.direccion.planta"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="puertaDireccionFacturacion">Puerta: </label>
				</td>
				<td align="left" nowrap="nowrap" width="7%">
					<s:textfield id="puertaDireccionFacturacion" name="tramiteTrafFacturacionDto.direccion.puerta"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="bloqueDireccionFacturacion">Bloque: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="bloqueDireccionFacturacion" name="tramiteTrafFacturacionDto.direccion.bloque"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="kmDireccionFacturacion">Km: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="kmDireccionFacturacion" name="tramiteTrafFacturacionDto.direccion.puntoKilometrico"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<label for="hmDireccionFacturacion">Hm: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield id="hmDireccionFacturacion" name="tramiteTrafFacturacionDto.direccion.hm"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<s:if test="tramiteTrafFacturacionDto.numExpediente == null || tramiteTrafFacturacionDto.numExpediente == ''">
			<table class="acciones">
				<tr>
					<td align="center" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
						<input 	type="button" class="boton" name="bGuardarFacturacion" id="bGuardarFacturacion" value="Guardar Facturación"
						onClick="return guardarFacturacion();" onKeyPress="this.onClick" />
					</td>
				</tr>
				<tr>
					<td colspan="2"><img id="loadingImageFac" style="display: none; margin-left: auto; margin-right: auto;" src="img/loading.gif"></td>
				</tr>
			</table>
		</s:if>
		<script>
			var viaFacturacionDuplicado = new BasicContentAssist(document.getElementById('nombreViaFacturacion'), [], null, true);
			recargarComboMunicipios('idProvinciaFacturacion', 'idMunicipioFacturacion', 'municipioSeleccionadoFacturacion'); 
			recargarComboTipoVia('idProvinciaFacturacion', 'tipoViaFacturacion', 'tipoViaSeleccionadaFacturacion');
			recargarComboPueblos('idProvinciaFacturacion', 'municipioSeleccionadoFacturacion', 'idPuebloFacturacion', 'puebloSeleccionadoFacturacion');
			recargarNombreVias('idProvinciaFacturacion', 'municipioSeleccionadoFacturacion', 'tipoViaSeleccionadaFacturacion', 'nombreViaFacturacion', viaFacturacionDuplicado);
			recargarTasasFacturacion('idTipoTasa', 'codigoTasaFacturacion', 'codigoTasaFacturacionSeleccionado');
		</script>
		
		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" 
				src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>
	</s:form>
</div>

<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>