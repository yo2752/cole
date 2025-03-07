<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>

<s:hidden name="tramiteRegRbmDto.representanteArrendatario.idInterviniente"/>
<s:hidden name="tramiteRegRbmDto.representanteArrendatario.notario.codigo"/>

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
	name="gToday:normal:agenda.js:representArrendatario" 
	id="gToday:normal:agenda.js:representArrendatario" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	
</iframe>	

<div id="representanteArrendatario">
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteArrendatario.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerKey=""
					headerValue="Selecionar Tipo"
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteArrendatarioTipoPersona" /></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
			<td width="35%">
	         	 <table  cellSpacing="0">
	         	 	<tr>
	         	 		<td align="left" nowrap="nowrap">
	            			<s:textfield name="tramiteRegRbmDto.representanteArrendatario.nif" id="representanteArrendatarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
	            		</td>
	            		
	            		<td align="left" nowrap="nowrap">
	             			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
								onclick="javascript:buscarIntervinienteAjax('representanteArrendatarioNif', 'representanteArrendatario')" />
						</td>
					</tr>
	          	</table>
		    </td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNombre" >Nombre</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td width="35%"><s:textfield name="tramiteRegRbmDto.representanteArrendatario.persona.nombre" id="representanteArrendatarioNombre" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.persona.apellido1RazonSocial" id="representanteArrendatarioApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.persona.apellido2" id="representanteArrendatarioApellido2" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioCargo" >Cargo<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Cargo que ostenta el representante"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.cargo" id="representanteArrendatarioCargo" size="18" maxlength="255"></s:textfield></td>
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
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioRegion" >Regi&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.regionExtranjera" id="representanteArrendatarioRegion" size="18"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
			<td colspan="3"><s:select name="tramiteRegRbmDto.representanteArrendatario.direccion.pais"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
					listKey="codigo" 
					listValue="nombre"
					headerKey="" 
					headerValue="Selecionar País"
					id="representanteArrendatarioPais" /></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioSelectProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteArrendatario.direccion.idProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteArrendatarioSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteArrendatarioSelectMunicipioId','representanteArrendatarioHiddenMunicipioId');
	 				 inicializarTipoVia('representanteArrendatarioTipoVia','representanteArrendatarioNombreVia', viaRepresentanteArrendatario);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioSelectMunicipioId" class="small">Municipio<span class="naranja">*</span></label></td>
			<td width="35%">
				<s:select id="representanteArrendatarioSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.representanteArrendatario)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteArrendatario');
						  inicializarTipoVia('representanteArrendatarioTipoVia','representanteArrendatarioNombreVia', viaRepresentanteArrendatario);" 
						  name="tramiteRegRbmDto.representanteArrendatario.direccion.idMunicipio"
						  headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteArrendatarioHiddenMunicipioId" />
				<s:hidden id="representanteArrendatarioHiddenMunicipality" />  	
				
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioPueblo" class="small">Entidad local</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.pueblo" id="representanteArrendatarioPueblo" size="25" maxlength="70"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.codPostal" id="representanteArrendatarioCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
		</tr>
	
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteArrendatarioTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
			<td><s:select name="tramiteRegRbmDto.representanteArrendatario.direccion.idTipoVia"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
	                headerKey="" 
	                headerValue="Selecionar tipo"
	                listKey="key"
	                listValue="name"
					id="representanteArrendatarioTipoVia"
					onchange ="cargarListaNombresVia('representanteArrendatarioSelectProvinciaId', 'representanteArrendatarioSelectMunicipioId', this, 'representanteArrendatarioNombreVia', viaRepresentanteArrendatario);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.nombreVia" id="representanteArrendatarioNombreVia" size="20" maxlength="100"
			 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
	
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.numero" id="representanteArrendatarioNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioBloque" class="small">Bloque</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.bloque" id="representanteArrendatarioBloque" size="5" maxlength="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioPlanta" class="small">Planta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.planta" id="representanteArrendatarioPlanta" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioPuerta" class="small">Puerta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.puerta" id="representanteArrendatarioPuerta" size="5" maxlength="100"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNumeroBis" class="small">N&uacute;m. bis</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.numeroBis" id="representanteArrendatarioNumeroBis" size="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioEscalera" class="small">Escalera</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.escalera" id="representanteArrendatarioEscalera" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioKm" class="small">Punto KM</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.km" id="representanteArrendatarioKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioPortal" class="small">Portal</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.portal" id="representanteArrendatarioPortal" size="5"></s:textfield></td>
		</tr>
	    <tr>
			<td align="left" nowrap="nowrap" ><label for="representanteArrendatarioLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
			</td>
	        <td colspan="3"><s:textfield name="tramiteRegRbmDto.representanteArrendatario.direccion.lugarUbicacion" id="representanteArrendatarioLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
	    </tr>
	</table>
	
	
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Datos del Notario:</span></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioTipoPersonaNotario" class="small">Tipo de persona</label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteArrendatario.notario.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerValue="Selecionar Tipo"
					headerKey=""
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteArrendatarioTipoPersonaNotario" /></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNotarioNif" class="small">Nif. notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.notario.nif" id="representanteArrendatarioNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNotarioNombre" class="small">Nombre notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.notario.nombre" id="representanteArrendatarioNotarioNombre" size="18" maxlength="255"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNotarioApellido1">1er apellido</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.notario.apellido1" id="representanteArrendatarioNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNotarioApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.notario.apellido2" id="representanteArrendatarioNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNotarioSelectProvinciaId" >C&oacute;digo INE provin.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE de la Provincia del Notario"/>
			</td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteArrendatario.notario.codProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteArrendatarioNotarioSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteArrendatarioNotarioSelectMunicipioId','representanteArrendatarioNotarioHiddenMunicipioId');"/>
			</td>	
			<td align="left" nowrap="nowrap"><label for="representantNotaryMunicipalityInecode" >C&oacute;digo INE muni.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE del Municipio del Notario"/>
			</td>
			<td width="35%">
				<s:select id="representanteArrendatarioNotarioSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosNotario(tramiteRegRbmDto.representanteArrendatario.notario)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteArrendatarioNotario');" 
						  name="tramiteRegRbmDto.representanteArrendatario.notario.codMunicipio"
						  headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteArrendatarioNotarioHiddenMunicipioId" />
				<s:hidden id="representanteArrendatarioNotarioHiddenMunicipality" />  	
				
			</td>
			
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioFecOtorgamiento" class="small">Fecha otorgamiento</label></td>
			<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteArrendatario.notario.fecOtorgamientoNotario.dia" id="diaFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteArrendatario.notario.fecOtorgamientoNotario.mes" id="mesFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteArrendatario.notario.fecOtorgamientoNotario.anio" id="anioFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.representArrendatario)representArrendatario.fPopCalendarSplit(anioFecOtorgamiento, mesFecOtorgamiento, diaFecOtorgamiento); resizeMe();  return false;" 
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
			<td align="left" nowrap="nowrap"><label for="representanteArrendatarioNumProtocolo" class="small">N&uacute;m. protocolo</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteArrendatario.notario.numProtocolo" id="representanteArrendatarioNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
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

	var viaRepresentanteArrendatario = new BasicContentAssist(document.getElementById('representanteArrendatarioNombreVia'), [], null, true); 
	recargarNombreVias('representanteArrendatarioSelectProvinciaId', 'representanteArrendatarioSelectMunicipioId', 'representanteArrendatarioTipoVia','representanteArrendatarioNombreVia',viaRepresentanteArrendatario);
		

</script>
