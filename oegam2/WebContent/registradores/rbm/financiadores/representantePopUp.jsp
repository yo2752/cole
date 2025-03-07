<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>
	<s:hidden name="idSession" />
	<s:hidden name="representante.idInterviniente"/>
	<s:hidden name="representante.numColegiado"/>
	<s:hidden name="representante.idRepresentado"/>
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
	name="gToday:normal:agenda.js:representFinanciadores" 
	id="gToday:normal:agenda.js:representFinanciadores" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	
</iframe>	

<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
	align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
		<td width="30%"><s:select name="representante.tipoPersona"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
				headerKey=""
				headerValue="Selecionar Tipo"
				listValue="nombreEnum" 
				listKey="valorXML"
				id="representantCorpmePersonType" /></td>

		<td align="left" nowrap="nowrap"><label for="representanteNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
		<td width="35%">
         	 <table  cellSpacing="0">
         	 	<tr>
         	 		<td align="left" nowrap="nowrap">
            			<s:textfield name="representante.nif" id="representanteNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
            		</td>
            		
            		<td align="left" nowrap="nowrap">
             			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
							onclick="javascript:buscarRepresentanteFinanciadores(${financiador.idInterviniente})" />
					</td>
				</tr>
          	</table>
	    </td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteNombre" >Nombre</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td width="35%"><s:textfield name="representante.persona.nombre" id="representanteNombre" size="18" maxlength="100"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representante1Apellido" >1er apellido<span class="naranja">*</span></label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
		</td>
		<td><s:textfield name="representante.persona.apellido1RazonSocial" id="representante1Apellido" size="18" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteApellido2" >2do apellido</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td><s:textfield name="representante.persona.apellido2" id="representanteApellido2" size="18" maxlength="100"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representanteCargo" >Cargo<span class="naranja">*</span></label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Cargo que ostenta el representante"/>
		</td>
		<td><s:textfield name="representante.cargo" id="representanteCargo" size="18" maxlength="255"></s:textfield></td>
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
		<td align="left" nowrap="nowrap"><label for="representanteRegion" >Regi&oacute;n</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
		</td>
		<td><s:textfield name="representante.direccion.regionExtranjera" id="representanteRegion" size="18"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representantePais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
		<td colspan="3"><s:select name="representante.direccion.pais"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
				listKey="codigo" 
				listValue="nombre"
				headerKey="" 
				headerValue="Selecionar País"
				id="representantCorpmeCountryId" /></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representantAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
		<td width="30%"><s:select name="representante.direccion.idProvincia" 
				list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
                headerKey="" 
                headerValue="Seleccionar"
                listKey="idProvincia"
                listValue="nombre"
				id="representanteFinanciadoresSelectProvinciaId"
 				onchange="cargarListaMunicipios(this,'representanteFinanciadoresSelectMunicipioId','representanteFinanciadoresHiddenMunicipioId');
 				 inicializarTipoVia('representantAddressCorpmeStreetTypeId','representanteNombreVia', viaRepresentanteFinanciadores);"/>
		</td>

		<td align="left" nowrap="nowrap"><label for="representanteFinanciadoresSelectMunicipioId" class="small">Municipio<span class="naranja">*</span></label></td>
		<td width="35%">
			<s:select id="representanteFinanciadoresSelectMunicipioId" 
					  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(representante)"
					  onchange="javascript:seleccionMunicipio(this, 'representanteFinanciadores');
					   inicializarTipoVia('representantAddressCorpmeStreetTypeId','representanteNombreVia', viaRepresentanteFinanciadores);"
					  name="representante.direccion.idMunicipio"
					  headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre"/>
			<s:hidden id="representanteFinanciadoresHiddenMunicipioId" />
			<s:hidden id="representanteFinanciadoresHiddenMunicipality" />  	
			
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representantePueblo" class="small">Entidad local</label></td>
		<td><s:textfield name="representante.direccion.pueblo" id="representantePueblo" size="25" maxlength="70"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="representanteCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
		<td><s:textfield name="representante.direccion.codPostal" id="representanteCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	</tr>

	<tr>
		<td align="left" nowrap="nowrap"><label
			for="representanteIdTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
		<td><s:select name="representante.direccion.idTipoVia"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                headerKey="" 
                headerValue="Selecionar tipo"
                listKey="key"
                listValue="name"
				id="representantAddressCorpmeStreetTypeId" 
				onchange ="cargarListaNombresVia('representanteFinanciadoresSelectProvinciaId', 'representanteFinanciadoresSelectMunicipioId', this, 'representanteNombreVia', viaRepresentanteFinanciadores);"/>
		</td>

		<td align="left" nowrap="nowrap"><label for="representanteNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
		<td><s:textfield name="representante.direccion.nombreVia" id="representanteNombreVia" size="20" maxlength="100"
		 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>

	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
		<td><s:textfield name="representante.direccion.numero" id="representanteNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="representanteBloque" class="small">Bloque</label></td>
		<td><s:textfield name="representante.direccion.bloque" id="representanteBloque" size="5" maxlength="5"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="representantePlanta" class="small">Planta</label></td>
		<td><s:textfield name="representante.direccion.planta" id="representantePlanta" size="5" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representantePuerta" class="small">Puerta</label></td>
		<td><s:textfield name="representante.direccion.puerta" id="representantePuerta" size="5" maxlength="100"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="representanteNumeroBis" class="small">N&uacute;m. bis</label></td>
		<td><s:textfield name="representante.direccion.numeroBis" id="representanteNumeroBis" size="5"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="representanteEscalera" class="small">Escalera</label></td>
		<td><s:textfield name="representante.direccion.escalera" id="representanteEscalera" size="5" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteKm" class="small">Punto KM</label></td>
		<td><s:textfield name="representante.direccion.km" id="representanteKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representantePortal" class="small">Portal</label></td>
		<td><s:textfield name="representante.direccion.portal" id="representantePortal" size="5"></s:textfield></td>
	</tr>
    <tr>
		<td align="left" nowrap="nowrap" ><label for="representanteFinanciadoresLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Descripción libre del lugar de ubicación"/>
		</td>
        <td colspan="3"><s:textfield name="representante.direccion.lugarUbicacion" id="representanteFinanciadoresLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
    </tr>
</table>



<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
	<tr>
		<td class="tabular"><span class="titulo">Datos del Notario:</span></td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteFinanciadoresTipoPersonaNotario" class="small">Tipo de persona</label></td>
		<td width="30%"><s:select name="representante.notario.tipoPersona"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
				headerValue="Selecionar Tipo"
				headerKey=""
				listValue="nombreEnum" 
				listKey="valorXML"
				id="representanteTipoPersonaNotario" /></td>
		<td align="left" nowrap="nowrap"><label for="representanteNotarioNif" class="small">Nif. notario</label></td>
		<td><s:textfield name="representante.notario.nif" id="representanteNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteNotarioNombre" class="small">Nombre notario</label></td>
		<td><s:textfield name="representante.notario.nombre" id="representanteNotarioNombre" size="18" maxlength="255"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representanteFinanciadoresNotarioApellido1">1er apellido</label></td>
		<td><s:textfield name="representante.notario.apellido1" id="representanteNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteFinanciadoresNotarioApellido2" >2do apellido</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td><s:textfield name="representante.notario.apellido2" id="representanteNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteFinanciadoresNotarioSelectProvinciaId" >C&oacute;digo INE provin.</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE de la Provincia del Notario"/>
		</td>
		<td width="30%"><s:select name="representante.notario.codProvincia" 
				list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()" 
                headerKey="" 
                headerValue="Seleccionar"
                listKey="idProvincia"
                listValue="nombre"
				id="representanteFinanciadoresNotarioSelectProvinciaId"
 				onchange="cargarListaMunicipios(this,'representanteFinanciadoresNotarioSelectMunicipioId','representanteFinanciadoresNotarioHiddenMunicipioId');"/>
		</td>	
		<td align="left" nowrap="nowrap"><label for="representantNotaryMunicipalityInecode" >C&oacute;digo INE muni.</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE del Municipio del Notario"/>
		</td>
		<td width="35%">
			<s:select id="representanteFinanciadoresNotarioSelectMunicipioId" 
					  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosNotario(representante.notario)"
					  onchange="javascript:seleccionMunicipio(this, 'representanteFinanciadoresNotario');" 
					  name="representante.notario.codMunicipio"
					  headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre"/>
			<s:hidden id="representanteFinanciadoresNotarioHiddenMunicipioId" />
			<s:hidden id="representanteFinanciadoresNotarioHiddenMunicipality" />  	
			
		</td>
		
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteFecOtorgamiento" class="small">Fecha otorgamiento</label></td>
		<td>
			<table style="width:20%">
				<tr>
					<td align="left">
						<s:textfield name="representante.notario.fecOtorgamientoNotario.dia" id="diaFecOtorgamiento"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="this.className='input2';validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
					</td>
					<td align="left">
						<label class="labelFecha">/</label>
					</td>
					<td align="left">
						<s:textfield name="representante.notario.fecOtorgamientoNotario.mes" id="mesFecOtorgamiento"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="this.className='input2';validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
					</td>
					<td align="left">
						<label class="labelFecha">/</label>
					</td>
					<td align="left">
						<s:textfield name="representante.notario.fecOtorgamientoNotario.anio" id="anioFecOtorgamiento"
							onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur="this.className='input2';validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));"/>
					</td>
					
					<td align="left" nowrap="nowrap" width="5%" >
			    		<a href="javascript:;" 
			    			onClick="if(self.representFinanciadores)representFinanciadores.fPopCalendarSplit(anioFecOtorgamiento, mesFecOtorgamiento, diaFecOtorgamiento); resizeMe();  return false;" 
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
		<td align="left" nowrap="nowrap"><label for="representanteNumProtocolo" class="small">N&uacute;m. protocolo</label></td>
		<td><s:textfield name="representante.notario.numProtocolo" id="representanteNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
	</tr>
</table>
<script type="text/javascript">

	function resizeMe(){
		var $cal = $('iframe:eq(1)',parent.document);
		var position = $("#diaFecOtorgamiento").position();
		$cal.css({
		    left:  position.left + "px",
		    top: (position.top - 100) + "px"
		});
	}

	var viaRepresentanteFinanciadores = new BasicContentAssist(document.getElementById('representanteNombreVia'), [], null, true); 
	recargarNombreVias('representanteFinanciadoresSelectProvinciaId', 'representanteFinanciadoresSelectMunicipioId', 'representantAddressCorpmeStreetTypeId','representanteNombreVia',viaRepresentanteFinanciadores);

</script>