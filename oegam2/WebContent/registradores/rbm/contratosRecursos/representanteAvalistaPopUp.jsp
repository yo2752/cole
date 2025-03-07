<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>

<s:hidden name="tramiteRegRbmDto.representanteAvalista.idInterviniente"/>
<s:hidden name="tramiteRegRbmDto.representanteAvalista.notario.codigo"/>

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
	name="gToday:normal:agenda.js:representAvalista" 
	id="gToday:normal:agenda.js:representAvalista" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	
</iframe>	

<div id="representanteAvalista">

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteAvalista.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerKey=""
					headerValue="Selecionar Tipo"
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteAvalistaTipoPersona" /></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
			<td width="35%">
	         	 <table  cellSpacing="0">
	         	 	<tr>
	         	 		<td align="left" nowrap="nowrap">
	            			<s:textfield name="tramiteRegRbmDto.representanteAvalista.nif" id="representanteAvalistaNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
	            		</td>
	            		
	            		<td align="left" nowrap="nowrap">
	             			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
								onclick="javascript:buscarIntervinienteAjax('representanteAvalistaNif', 'representanteAvalista')" />
						</td>
					</tr>
	          	</table>
		    </td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNombre" >Nombre</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td width="35%"><s:textfield name="tramiteRegRbmDto.representanteAvalista.persona.nombre" id="representanteAvalistaNombre" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.persona.apellido1RazonSocial" id="representanteAvalistaApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.persona.apellido2" id="representanteAvalistaApellido2" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaCargo" >Cargo<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Cargo que ostenta el representante"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.cargo" id="representanteAvalistaCargo" size="18" maxlength="255"></s:textfield></td>
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
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaRegion" >Regi&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.regionExtranjera" id="representanteAvalistaRegion" size="18"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
			<td colspan="3"><s:select name="tramiteRegRbmDto.representanteAvalista.direccion.pais"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
					listKey="codigo" 
					listValue="nombre"
					headerKey="" 
					headerValue="Selecionar País"
					id="representanteAvalistaPais" /></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representantAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteAvalista.direccion.idProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteAvalistaSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteAvalistaSelectMunicipioId','representanteAvalistaHiddenMunicipioId');
	 				 inicializarTipoVia('representanteAvalistaTipoVia','representanteAvalistaNombreVia', viaRepresentanteAvalista);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaSelectMunicipioId" class="small">Municipio<span class="naranja">*</span></label></td>
			<td width="35%">
				<s:select id="representanteAvalistaSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.representanteAvalista)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteAvalista');
						  inicializarTipoVia('representanteAvalistaTipoVia','representanteAvalistaNombreVia', viaRepresentanteAvalista);"
						  name="tramiteRegRbmDto.representanteAvalista.direccion.idMunicipio"
						  headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteAvalistaHiddenMunicipioId" />
				<s:hidden id="representanteAvalistaHiddenMunicipality" />  	
				
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaPueblo" class="small">Entidad local</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.pueblo" id="representanteAvalistaPueblo" size="25" maxlength="70"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.codPostal" id="representanteAvalistaCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
		</tr>
	
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteAvalistaTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
			<td><s:select name="tramiteRegRbmDto.representanteAvalista.direccion.idTipoVia"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
	                headerKey="" 
	                headerValue="Selecionar tipo"
	                listKey="key"
	                listValue="name"
					id="representanteAvalistaTipoVia"
					onchange ="cargarListaNombresVia('representanteAvalistaSelectProvinciaId', 'representanteAvalistaSelectMunicipioId', this, 'representanteAvalistaNombreVia', viaRepresentanteAvalista);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.nombreVia" id="representanteAvalistaNombreVia" size="20" maxlength="100"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
	
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.numero" id="representanteAvalistaNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaBloque" class="small">Bloque</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.bloque" id="representanteAvalistaBloque" size="5" maxlength="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaPlanta" class="small">Planta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.planta" id="representanteAvalistaPlanta" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaPuerta" class="small">Puerta</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.puerta" id="representanteAvalistaPuerta" size="5" maxlength="100"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNumeroBis" class="small">N&uacute;m. bis</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.numeroBis" id="representanteAvalistaNumeroBis" size="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaEscalera" class="small">Escalera</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.escalera" id="representanteAvalistaEscalera" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaKm" class="small">Punto KM</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.km" id="representanteAvalistaKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaPortal" class="small">Portal</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.portal" id="representanteAvalistaPortal" size="5"></s:textfield></td>
		</tr>
	    <tr>
			<td align="left" nowrap="nowrap" ><label for="representanteAvalistaLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
			</td>
	        <td colspan="3"><s:textfield name="tramiteRegRbmDto.representanteAvalista.direccion.lugarUbicacion" id="representanteAvalistaLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
	    </tr>
	</table>
	
	
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Datos del Notario:</span></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaTipoPersonaNotario" class="small">Tipo de persona</label></td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteAvalista.notario.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerValue="Selecionar Tipo"
					headerKey=""
					listValue="nombreEnum" 
					listKey="valorXML"
					id="representanteAvalistaTipoPersonaNotario" /></td>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNotarioNif" class="small">Nif. notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.notario.nif" id="representanteAvalistaNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNotarioNombre" class="small">Nombre notario</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.notario.nombre" id="representanteAvalistaNotarioNombre" size="18" maxlength="255"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNotarioApellido1">1er apellido</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.notario.apellido1" id="representanteAvalistaNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNotarioApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.notario.apellido2" id="representanteAvalistaNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNotarioSelectProvinciaId" >C&oacute;digo INE provin.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE de la Provincia del Notario"/>
			</td>
			<td width="30%"><s:select name="tramiteRegRbmDto.representanteAvalista.notario.codProvincia" 
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
	                headerKey="" 
	                headerValue="Seleccionar"
	                listKey="idProvincia"
	                listValue="nombre"
					id="representanteAvalistaNotarioSelectProvinciaId"
	 				onchange="cargarListaMunicipios(this,'representanteAvalistaNotarioSelectMunicipioId','representanteAvalistaNotarioHiddenMunicipioId');"/>
			</td>	
			<td align="left" nowrap="nowrap"><label for="representantNotaryMunicipalityInecode" >C&oacute;digo INE muni.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE del Municipio del Notario"/>
			</td>
			<td width="35%">
				<s:select id="representanteAvalistaNotarioSelectMunicipioId" 
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosNotario(tramiteRegRbmDto.representanteAvalista.notario)"
						  onchange="javascript:seleccionMunicipio(this, 'representanteAvalistaNotario');" 
						  name="tramiteRegRbmDto.representanteAvalista.notario.codMunicipio"
						  headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre"/>
				<s:hidden id="representanteAvalistaNotarioHiddenMunicipioId" />
				<s:hidden id="representanteAvalistaNotarioHiddenMunicipality" />  	
				
			</td>
			
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaFecOtorgamiento" class="small">Fecha otorgamiento</label></td>
			<td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteAvalista.notario.fecOtorgamientoNotario.dia" id="diaFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteAvalista.notario.fecOtorgamientoNotario.mes" id="mesFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.representanteAvalista.notario.fecOtorgamientoNotario.anio" id="anioFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.representAvalista)representAvalista.fPopCalendarSplit(anioFecOtorgamiento, mesFecOtorgamiento, diaFecOtorgamiento); resizeMe();  return false;" 
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
			<td align="left" nowrap="nowrap"><label for="representanteAvalistaNumProtocolo" class="small">N&uacute;m. protocolo</label></td>
			<td><s:textfield name="tramiteRegRbmDto.representanteAvalista.notario.numProtocolo" id="representanteAvalistaNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
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

	var viaRepresentanteAvalista = new BasicContentAssist(document.getElementById('representanteAvalistaNombreVia'), [], null, true); 
	recargarNombreVias('representanteAvalistaSelectProvinciaId', 'representanteAvalistaSelectMunicipioId', 'representanteAvalistaTipoVia','representanteAvalistaNombreVia',viaRepresentanteAvalista);

</script>
