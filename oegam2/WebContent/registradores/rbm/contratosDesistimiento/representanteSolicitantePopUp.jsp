<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>

<s:hidden name="tramiteRegRbmDto.representanteSolicitante.idInterviniente"/>
<s:hidden name="tramiteRegRbmDto.representanteSolicitante.notario.codigo"/>

<%@include file="/includes/erroresYMensajes.jspf" %>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Representante:</span>
		</td>
	</tr>
</table>
<iframe width="174"  
	height="189" 
	name="gToday:normal:agenda.js:representSolicitante" 
	id="gToday:normal:agenda.js:representSolicitante" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	
</iframe>	

<div id="representanteSolicitante">
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerKey=""
					headerValue="Selecionar Tipo"
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteSolicitanteTipoPersona" /></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
			<td width="35%">
	         	 <table  cellSpacing="0">
	         	 	<tr>
	         	 		<td align="left" nowrap="nowrap">
	            			<s:textfield name="tramiteRegRbmDto.representanteSolicitante.nif" id="representanteSolicitanteNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
	            		</td>
	            		
	            		<td align="left" nowrap="nowrap">
	             			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
								onclick="javascript:buscarIntervinienteAjax('representanteSolicitanteNif', 'representanteSolicitante')" />
						</td>
					</tr>
	          	</table>
		    </td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNombre" >Nombre</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td width="35%"><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.nombre" id="representanteSolicitanteNombre" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.apellido1RazonSocial" id="representanteSolicitanteApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.apellido2" id="representanteSolicitanteApellido2" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteCargo" >Cargo<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Cargo que ostenta el representante"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.cargo" id="representanteSolicitanteCargo" size="18" maxlength="255"></s:textfield></td>
		</tr>
		
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Direcci&oacute;n del Representante:</span></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteRegion" >Regi&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.regionExtranjera" id="representanteSolicitanteRegion" size="18"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitantePais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
			<td colspan="3"><s:select name="tramiteRegRbmDto.representanteSolicitante.direccion.pais"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
					listKey="codigo" 
					listValue="nombre"
					headerKey="" 
					headerValue="Selecionar País"
					id="representanteSolicitantePais" /></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representantAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.direccion.idProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteSolicitanteSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteSolicitanteSelectMunicipioId','representanteSolicitanteHiddenMunicipioId');
	 				inicializarTipoVia('representanteSolicitanteTipoVia','representanteSolicitanteNombreVia', viaRepresentanteSolicitante);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteSelectMunicipioId" class="small">Municipio<span class="naranja">*</span></label></td>
			<td width="35%">
				<s:select id="representanteSolicitanteSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.representanteSolicitante)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteSolicitante');
						  inicializarTipoVia('representanteSolicitanteTipoVia','representanteSolicitanteNombreVia', viaRepresentanteSolicitante);" 
						  name="tramiteRegRbmDto.representanteSolicitante.direccion.idMunicipio"
						  headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteSolicitanteHiddenMunicipioId" />
				<s:hidden id="representanteSolicitanteHiddenMunicipality" />  	
				
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitantePueblo" class="small">Entidad local</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.pueblo" id="representanteSolicitantePueblo" size="25" maxlength="70"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.codPostal" id="representanteSolicitanteCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
		</tr>
	
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteSolicitanteTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
			<td><s:select name="tramiteRegRbmDto.representanteSolicitante.direccion.idTipoVia"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
	                headerKey="" 
	                headerValue="Selecionar tipo"
	                listKey="key"
	                listValue="name"
					id="representanteSolicitanteTipoVia" 
					onchange ="cargarListaNombresVia('representanteSolicitanteSelectProvinciaId', 'representanteSolicitanteSelectMunicipioId', this, 'representanteSolicitanteNombreVia', viaRepresentanteSolicitante);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.nombreVia" id="representanteSolicitanteNombreVia" size="20" maxlength="100"
			 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
	
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.numero" id="representanteSolicitanteNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteBloque" class="small">Bloque</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.bloque" id="representanteSolicitanteBloque" size="5" maxlength="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteSolicitantePlanta" class="small">Planta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.planta" id="representanteSolicitantePlanta" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitantePuerta" class="small">Puerta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.puerta" id="representanteSolicitantePuerta" size="5" maxlength="100"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNumeroBis" class="small">N&uacute;m. bis</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.numeroBis" id="representanteSolicitanteNumeroBis" size="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteEscalera" class="small">Escalera</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.escalera" id="representanteSolicitanteEscalera" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteKm" class="small">Punto KM</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.km" id="representanteSolicitanteKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitantePortal" class="small">Portal</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.portal" id="representanteSolicitantePortal" size="5"></s:textfield></td>
		</tr>
	    <tr>
			<td align="left" nowrap="nowrap" ><label for="representanteSolicitanteLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
			</td>
	        <td colspan="3"><s:textfield name="tramiteRegRbmDto.representanteSolicitante.direccion.lugarUbicacion" id="representanteSolicitanteLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
	    </tr>
	</table>
	
	
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Datos del Notario:</span></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteTipoPersonaNotario" class="small">Tipo de persona</label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.notario.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerValue="Selecionar Tipo"
					headerKey=""
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteSolicitanteTipoPersonaNotario" /></td>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNotarioNif" class="small">Nif. notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.nif" id="representanteSolicitanteNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNotarioNombre" class="small">Nombre notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.nombre" id="representanteSolicitanteNotarioNombre" size="18" maxlength="255"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNotarioApellido1">1er apellido</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.apellido1" id="representanteSolicitanteNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNotarioApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.apellido2" id="representanteSolicitanteNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNotarioSelectProvinciaId" >C&oacute;digo INE provin.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE de la Provincia del Notario"/>
			</td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.notario.codProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteSolicitanteNotarioSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteSolicitanteNotarioSelectMunicipioId','representanteSolicitanteNotarioHiddenMunicipioId');"/>
			</td>	
			<td align="left" nowrap="nowrap"><label for="representantNotaryMunicipalityInecode" >C&oacute;digo INE muni.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE del Municipio del Notario"/>
			</td>
			<td width="35%">
				<s:select id="representanteSolicitanteNotarioSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosNotario(tramiteRegRbmDto.representanteSolicitante.notario)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteSolicitanteNotario');" 
						  name="tramiteRegRbmDto.representanteSolicitante.notario.codMunicipio"
						  headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteSolicitanteNotarioHiddenMunicipioId" />
				<s:hidden id="representanteSolicitanteNotarioHiddenMunicipality" />  	
				
			</td>
			
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteFecOtorgamiento" class="small">Fecha otorgamiento</label></td>
			<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.fecOtorgamientoNotario.dia" id="diaFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.fecOtorgamientoNotario.mes" id="mesFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.fecOtorgamientoNotario.anio" id="anioFecOtorgamiento"
							    onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.representSolicitante)representSolicitante.fPopCalendarSplit(anioFecOtorgamiento, mesFecOtorgamiento, diaFecOtorgamiento); resizeMe();  return false;" 
				    				title="Seleccionar fecha">
				    			<img class="PopcalTrigger" 
				    				align="left" 
				    				src="img/ico_calendario.gif" 
				    				width="15" height="16" 
				    				border="0" alt="Calendario"/>
				    		</a>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNumProtocolo" class="small">N&uacute;m. protocolo</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.numProtocolo" id="representanteSolicitanteNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
		</tr>
	</table>
</div>	

<script type="text/javascript">

	function resizeMe(){
		var $cal = $('iframe:eq(1)',parent.document);
		var position = $("#diaFecOtorgamiento").position();
		$cal.css({
		    left:  position.left + "px",
		    top: (position.top - 100) + "px"
		});
	}

	var viaRepresentanteSolicitante = new BasicContentAssist(document.getElementById('representanteSolicitanteNombreVia'), [], null, true); 
	recargarNombreVias('representanteSolicitanteSelectProvinciaId', 'representanteSolicitanteSelectMunicipioId', 'representanteSolicitanteTipoVia','representanteSolicitanteNombreVia',viaRepresentanteSolicitante);

</script>
