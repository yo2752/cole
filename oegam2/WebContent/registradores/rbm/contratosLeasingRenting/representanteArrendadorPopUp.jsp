<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>

<s:hidden name="tramiteRegRbmDto.representanteArrendador.idInterviniente"/>
<s:hidden name="tramiteRegRbmDto.representanteArrendador.notario.codigo"/>

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
	name="gToday:normal:agenda.js:representArrendador" 
	id="gToday:normal:agenda.js:representArrendador" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	
</iframe>	

<div id="representanteArrendador">
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteArrendador.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerKey=""
					headerValue="Selecionar Tipo"
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteArrendadorTipoPersona" /></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
			<td width="35%">
	         	 <table  cellSpacing="0">
	         	 	<tr>
	         	 		<td align="left" nowrap="nowrap">
	            			<s:textfield name="tramiteRegRbmDto.representanteArrendador.nif" id="representanteArrendadorNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
	            		</td>
	            		
	            		<td align="left" nowrap="nowrap">
	             			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
								onclick="javascript:buscarIntervinienteAjax('representanteArrendadorNif', 'representanteArrendador')" />
						</td>
					</tr>
	          	</table>
		    </td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNombre" >Nombre</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td width="35%"><s:textfield name="tramiteRegRbmDto.representanteArrendador.persona.nombre" id="representanteArrendadorNombre" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.persona.apellido1RazonSocial" id="representanteArrendadorApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.persona.apellido2" id="representanteArrendadorApellido2" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorCargo" >Cargo<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Cargo que ostenta el representante"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.cargo" id="representanteArrendadorCargo" size="18" maxlength="255"></s:textfield></td>
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
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorRegion" >Regi&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.regionExtranjera" id="representanteArrendadorRegion" size="18"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
			<td colspan="3"><s:select name="tramiteRegRbmDto.representanteArrendador.direccion.pais"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
					listKey="codigo" 
					listValue="nombre"
					headerKey="" 
					headerValue="Selecionar País"
					id="representanteArrendadorPais" /></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representantAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteArrendador.direccion.idProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteArrendadorSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteArrendadorSelectMunicipioId','representanteArrendadorHiddenMunicipioId');
	 				inicializarTipoVia('representanteArrendadorTipoVia','representanteArrendadorNombreVia', viaRepresentanteArrendador);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorSelectMunicipioId" class="small">Municipio<span class="naranja">*</span></label></td>
			<td width="35%">
				<s:select id="representanteArrendadorSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.representanteArrendador)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteArrendador');
						  inicializarTipoVia('representanteArrendadorTipoVia','representanteArrendadorNombreVia', viaRepresentanteArrendador);" 
						  name="tramiteRegRbmDto.representanteArrendador.direccion.idMunicipio"
						  headerValue="Seleccione Municipio"  headerKey="" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteArrendadorHiddenMunicipioId" />
				<s:hidden id="representanteArrendadorHiddenMunicipality" />  	
				
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorPueblo" class="small">Entidad local</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.pueblo" id="representanteArrendadorPueblo" size="25" maxlength="70"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.codPostal" id="representanteArrendadorCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
		</tr>
	
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteArrendadorTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
			<td><s:select name="tramiteRegRbmDto.representanteArrendador.direccion.idTipoVia"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
	                headerKey="" 
	                headerValue="Selecionar tipo"
	                listKey="key"
	                listValue="name"
					id="representanteArrendadorTipoVia" 
					onchange ="cargarListaNombresVia('representanteArrendadorSelectProvinciaId', 'representanteArrendadorSelectMunicipioId', this, 'representanteArrendadorNombreVia', viaRepresentanteArrendador);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.nombreVia" id="representanteArrendadorNombreVia" size="20" maxlength="100"
			 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
	
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.numero" id="representanteArrendadorNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorBloque" class="small">Bloque</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.bloque" id="representanteArrendadorBloque" size="5" maxlength="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorPlanta" class="small">Planta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.planta" id="representanteArrendadorPlanta" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorPuerta" class="small">Puerta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.puerta" id="representanteArrendadorPuerta" size="5" maxlength="100"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNumeroBis" class="small">N&uacute;m. bis</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.numeroBis" id="representanteArrendadorNumeroBis" size="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorEscalera" class="small">Escalera</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.escalera" id="representanteArrendadorEscalera" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorKm" class="small">Punto KM</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.km" id="representanteArrendadorKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorPortal" class="small">Portal</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.portal" id="representanteArrendadorPortal" size="5"></s:textfield></td>
		</tr>
	    <tr>
			<td align="left" nowrap="nowrap" ><label for="representanteArrendadorLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
			</td>
	        <td colspan="3"><s:textfield name="tramiteRegRbmDto.representanteArrendador.direccion.lugarUbicacion" id="representanteArrendadorLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
	    </tr>
	</table>
	
	
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Datos del Notario:</span></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorTipoPersonaNotario" class="small">Tipo de persona</label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteArrendador.notario.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerValue="Selecionar Tipo"
					headerKey=""
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteArrendadorTipoPersonaNotario" /></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNotarioNif" class="small">Nif. notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.notario.nif" id="representanteArrendadorNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNotarioNombre" class="small">Nombre notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.notario.nombre" id="representanteArrendadorNotarioNombre" size="18" maxlength="255"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNotarioApellido1">1er apellido</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.notario.apellido1" id="representanteArrendadorNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNotarioApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.notario.apellido2" id="representanteArrendadorNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNotarioSelectProvinciaId" >C&oacute;digo INE provin.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE de la Provincia del Notario"/>
			</td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteArrendador.notario.codProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteArrendadorNotarioSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteArrendadorNotarioSelectMunicipioId','representanteArrendadorNotarioHiddenMunicipioId');"/>
			</td>	
			<td align="left" nowrap="nowrap"><label for="representantNotaryMunicipalityInecode" >C&oacute;digo INE muni.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE del Municipio del Notario"/>
			</td>
			<td width="35%">
				<s:select id="representanteArrendadorNotarioSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosNotario(tramiteRegRbmDto.representanteArrendador.notario)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteArrendadorNotario');" 
						  name="tramiteRegRbmDto.representanteArrendador.notario.codMunicipio"
						  headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteArrendadorNotarioHiddenMunicipioId" />
				<s:hidden id="representanteArrendadorNotarioHiddenMunicipality" />  	
				
			</td>
			
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorFecOtorgamiento" class="small">Fecha otorgamiento</label></td>
			<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteArrendador.notario.fecOtorgamientoNotario.dia" id="diaFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteArrendador.notario.fecOtorgamientoNotario.mes" id="mesFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteArrendador.notario.fecOtorgamientoNotario.anio" id="anioFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.representArrendador)representArrendador.fPopCalendarSplit(anioFecOtorgamiento, mesFecOtorgamiento, diaFecOtorgamiento); resizeMe();  return false;" 
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
			<td align="left" nowrap="nowrap"><label for="representanteArrendadorNumProtocolo" class="small">N&uacute;m. protocolo</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendador.notario.numProtocolo" id="representanteArrendadorNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
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

	var viaRepresentanteArrendador = new BasicContentAssist(document.getElementById('representanteArrendadorNombreVia'), [], null, true); 
	recargarNombreVias('representanteArrendadorSelectProvinciaId', 'representanteArrendadorSelectMunicipioId', 'representanteArrendadorTipoVia','representanteArrendadorNombreVia',viaRepresentanteArrendador);

</script>
