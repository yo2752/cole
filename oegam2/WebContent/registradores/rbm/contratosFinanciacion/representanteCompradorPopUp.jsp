<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>

<s:hidden name="tramiteRegRbmDto.representanteComprador.idInterviniente"/>
<s:hidden name="tramiteRegRbmDto.representanteComprador.notario.codigo"/>

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
	name="gToday:normal:agenda.js:representComprador" 
	id="gToday:normal:agenda.js:representComprador" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	
</iframe>	

<div id="representanteComprador">
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteComprador.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerKey=""
					headerValue="Selecionar Tipo"
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteCompradorTipoPersona" /></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
			<td width="35%">
	         	 <table  cellSpacing="0">
	         	 	<tr>
	         	 		<td align="left" nowrap="nowrap">
	            			<s:textfield name="tramiteRegRbmDto.representanteComprador.nif" id="representanteCompradorNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
	            		</td>
	            		
	            		<td align="left" nowrap="nowrap">
	             			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
								onclick="javascript:buscarIntervinienteAjax('representanteCompradorNif', 'representanteComprador')" />
						</td>
					</tr>
	          	</table>
		    </td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNombre" >Nombre</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td width="35%"><s:textfield name="tramiteRegRbmDto.representanteComprador.persona.nombre" id="representanteCompradorNombre" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.persona.apellido1RazonSocial" id="representanteCompradorApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.persona.apellido2" id="representanteCompradorApellido2" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorCargo">Cargo<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Cargo que ostenta el representante"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.cargo" id="representanteCompradorCargo" size="18" maxlength="255"></s:textfield></td>
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
			<td align="left" nowrap="nowrap"><label for="representanteCompradorRegion" >Regi&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.regionExtranjera" id="representanteCompradorRegion" size="18"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
			<td colspan="3"><s:select name="tramiteRegRbmDto.representanteComprador.direccion.pais"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
					listKey="codigo" 
					listValue="nombre"
					headerKey="" 
					headerValue="Selecionar País"
					id="representanteCompradorPais" /></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representantAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteComprador.direccion.idProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteCompradorSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteCompradorSelectMunicipioId','representanteCompradorHiddenMunicipioId');
	 				 inicializarTipoVia('representanteCompradorTipoVia','representanteCompradorNombreVia', viaRepresentanteComprador);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCompradorSelectMunicipioId" class="small">Municipio<span class="naranja">*</span></label></td>
			<td width="35%">
				<s:select id="representanteCompradorSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.representanteComprador)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteComprador');
						  inicializarTipoVia('representanteCompradorTipoVia','representanteCompradorNombreVia', viaRepresentanteComprador);" 
						  name="tramiteRegRbmDto.representanteComprador.direccion.idMunicipio"
						  headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteCompradorHiddenMunicipioId" />
				<s:hidden id="representanteCompradorHiddenMunicipality" />  	
				
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorPueblo" class="small">Entidad local</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.pueblo" id="representanteCompradorPueblo" size="25" maxlength="70"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCompradorCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.codPostal" id="representanteCompradorCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
		</tr>
	
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteCompradorTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
			<td><s:select name="tramiteRegRbmDto.representanteComprador.direccion.idTipoVia"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
	                headerKey="" 
	                headerValue="Selecionar tipo"
	                listKey="key"
	                listValue="name"
					id="representanteCompradorTipoVia"
					onchange ="cargarListaNombresVia('representanteCompradorSelectProvinciaId', 'representanteCompradorSelectMunicipioId', this, 'representanteCompradorNombreVia', viaRepresentanteComprador);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.nombreVia" id="representanteCompradorNombreVia" size="20" maxlength="100"
			  onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
	
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.numero" id="representanteCompradorNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCompradorBloque" class="small">Bloque</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.bloque" id="representanteCompradorBloque" size="5" maxlength="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCompradorPlanta" class="small">Planta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.planta" id="representanteCompradorPlanta" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorPuerta" class="small">Puerta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.puerta" id="representanteCompradorPuerta" size="5" maxlength="100"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNumeroBis" class="small">N&uacute;m. bis</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.numeroBis" id="representanteCompradorNumeroBis" size="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCompradorEscalera" class="small">Escalera</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.escalera" id="representanteCompradorEscalera" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorKm" class="small">Punto KM</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.km" id="representanteCompradorKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorPortal" class="small">Portal</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.portal" id="representanteCompradorPortal" size="5"></s:textfield></td>
		</tr>
	    <tr>
			<td align="left" nowrap="nowrap" ><label for="representanteCompradorLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
			</td>
	        <td colspan="3"><s:textfield name="tramiteRegRbmDto.representanteComprador.direccion.lugarUbicacion" id="representanteCompradorLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
	    </tr>
	</table>
	
	
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Datos del Notario:</span></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorTipoPersonaNotario" class="small">Tipo de persona</label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteComprador.notario.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerValue="Selecionar Tipo"
					headerKey=""
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteCompradorTipoPersonaNotario" /></td>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNotarioNif" class="small">Nif. notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.notario.nif" id="representanteCompradorNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNotarioNombre" class="small">Nombre notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.notario.nombre" id="representanteCompradorNotarioNombre" size="18" maxlength="255"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNotarioApellido1">1er apellido</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.notario.apellido1" id="representanteCompradorNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNotarioApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.notario.apellido2" id="representanteCompradorNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNotarioSelectProvinciaId" >C&oacute;digo INE provin.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE de la Provincia del Notario"/>
			</td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteComprador.notario.codProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteCompradorNotarioSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteCompradorNotarioSelectMunicipioId','representanteCompradorNotarioHiddenMunicipioId');"/>
			</td>	
			<td align="left" nowrap="nowrap"><label for="representantNotaryMunicipalityInecode" >C&oacute;digo INE muni.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE del Municipio del Notario"/>
			</td>
			<td width="35%">
				<s:select id="representanteCompradorNotarioSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosNotario(tramiteRegRbmDto.representanteComprador.notario)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteCompradorNotario');" 
						  name="tramiteRegRbmDto.representanteComprador.notario.codMunicipio"
						  headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteCompradorNotarioHiddenMunicipioId" />
				<s:hidden id="representanteCompradorNotarioHiddenMunicipality" />  	
				
			</td>
			
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCompradorFecOtorgamiento" class="small">Fecha otorgamiento</label></td>
			<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteComprador.notario.fecOtorgamientoNotario.dia" id="diaFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteComprador.notario.fecOtorgamientoNotario.mes" id="mesFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteComprador.notario.fecOtorgamientoNotario.anio" id="anioFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.representComprador)representComprador.fPopCalendarSplit(anioFecOtorgamiento, mesFecOtorgamiento, diaFecOtorgamiento); resizeMe();  return false;" 
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
			<td align="left" nowrap="nowrap"><label for="representanteCompradorNumProtocolo" class="small">N&uacute;m. protocolo</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteComprador.notario.numProtocolo" id="representanteCompradorNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
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

	var viaRepresentanteComprador = new BasicContentAssist(document.getElementById('representanteCompradorNombreVia'), [], null, true); 
	recargarNombreVias('representanteCompradorSelectProvinciaId', 'representanteCompradorSelectMunicipioId', 'representanteCompradorTipoVia','representanteCompradorNombreVia',viaRepresentanteComprador);
		

</script>
