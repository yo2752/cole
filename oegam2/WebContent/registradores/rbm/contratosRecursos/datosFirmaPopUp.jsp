<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

	<s:hidden name="tramiteRegRbmDto.datoFirmaDto.idDatoFirma" />
	<s:hidden name="tramiteRegRbmDto.datoFirmaDto.idTramiteRegistro" />
	<s:hidden name="tramiteRegRbmDto.datoFirmaDto.fecCreacion" />		

   	<table class="nav" cellSpacing="1" cellPadding="5" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos de la firma:</span>
			</td>
		</tr>
	</table>

	<iframe width="174"  
		height="189" 
		name="gToday:normal:agenda.js:datosFirma" 
		id="gToday:normal:agenda.js:datosFirma" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		
	</iframe>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
		  <td  colspan="2" align="left" nowrap="nowrap"><label for="tipoIntervencion">Tipo intervenci&oacute;n<span class="naranja">*</span></label></td>
		  <td colspan="2"> 
		      <s:select name="tramiteRegRbmDto.datoFirmaDto.tipoIntervencion"
						list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListInterventionType()" 
						listKey="key"
						headerKey=""
						headerValue="Seleccione el tipo" 
						id="datoFirmaDtoTipoIntervencion"/>
		  </td>
		  <td  colspan="2" align="left" nowrap="nowrap"><label for="fechaFirma">Fecha &uacute;ltima firma<span class="naranja">*</span></label></td>
		  <td>
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datoFirmaDto.fecFirmaDatFirma.dia" id="diaFecFirma"
							   onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diaFecFirma'), document.getElementById('mesFecFirma'), document.getElementById('anioFecFirma'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datoFirmaDto.fecFirmaDatFirma.mes" id="mesFecFirma"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur="validaInputFecha(document.getElementById('diaFecFirma'), document.getElementById('mesFecFirma'), document.getElementById('anioFecFirma'));"/>
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteRegRbmDto.datoFirmaDto.fecFirmaDatFirma.anio" id="anioFecFirma"
								onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "validaInputFecha(document.getElementById('diaFecFirma'), document.getElementById('mesFecFirma'), document.getElementById('anioFecFirma'));"/>
						</td>
						
						<td align="left" nowrap="nowrap" width="5%" >
				    		<a href="javascript:;" 
				    			onClick="if(self.datosFirma)datosFirma.fPopCalendarSplit(anioFecFirma, mesFecFirma, diaFecFirma); resizeMe();  return false;" 
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
		</tr>
		<tr>
	      <td align="left" nowrap="nowrap">
	      	<label for="derechoDes">Derecho desestimiento</label>
	      </td>
 	      <td align="left" width="25%">
	      	<s:checkbox name="tramiteRegRbmDto.datoFirmaDto.derechoDes" fieldValue="true" label="ejemplo" id="derechoDes"/>
		 </td>
	      <td align="left" nowrap="nowrap">
	      	<label for="consumidor">Consumidor</label>
	      </td>
	      <td align="left" width="25%">
	      	<s:checkbox name="tramiteRegRbmDto.datoFirmaDto.consumidor" fieldValue="true" label="ejemplo" id="consumidor"/>
		  </td> 
	      <td align="left" nowrap="nowrap">
	      	<label for="informado">Informador</label>
	      </td>
	      <td align="left" width="25%">
	      	<s:checkbox name="tramiteRegRbmDto.datoFirmaDto.informado" fieldValue="true" label="ejemplo" id="informado"/>
	      </td> 
	    </tr>
	 </table>
	 
	 <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
	<tr>
		<td class="tabular"><span class="titulo">Lugar firma</span></td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
   			<td align="left" nowrap="nowrap"><label for="datoFirmaProvinciaId" class="small">Provincia</label></td>
       		<td>
           	<s:select name="tramiteRegRbmDto.datoFirmaDto.direccion.idProvincia" 
				list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
				headerKey="" headerValue="Seleccionar" listKey="idProvincia"
				listValue="nombre" id="datoFirmaSelectProvinciaId"
		 		onchange="cargarListaMunicipios(this,'datoFirmaSelectMunicipioId','datoFirmaHiddenMunicipioId');
		 		inicializarTipoVia('tipoViaId','datoFirmaNombreVia', viaDatoFirma);"/>
          	</td>
         	<td align="left" nowrap="nowrap"><label for="datoFirmaMunicipio" class="small">Municipio</label></td>
      	 	<td>       
				<s:select id="datoFirmaSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegRbmDto.datoFirmaDto.direccion.idProvincia)"
					onchange="javascript:seleccionMunicipio(this, 'datoFirma');
					 inicializarTipoVia('tipoViaId','datoFirmaNombreVia', viaDatoFirma);"
					name="tramiteRegRbmDto.datoFirmaDto.direccion.idMunicipio"
					headerValue="Seleccione Municipio" 
					listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="datoFirmaHiddenMunicipioId"/>
				<s:hidden id="datoFirmaHiddenMunicipality" />  
	      </td>
     </tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="datoFirmaCodPostal" class="small">C&oacute;digo postal</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.codPostal" id="datoFirmaCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	</tr>

	<tr>
		<td align="left" nowrap="nowrap"><label
			for="datoFirmaIdTipoVia" class="small">Tipo de v&iacute;a</label></td>
		<td><s:select name="tramiteRegRbmDto.datoFirmaDto.direccion.idTipoVia"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                headerKey="" 
                headerValue="Selecionar tipo"
                listKey="key"
                listValue="name"
				id="tipoViaId"
				onchange ="cargarListaNombresVia('datoFirmaSelectProvinciaId', 'datoFirmaSelectMunicipioId', this, 'datoFirmaNombreVia', viaDatoFirma);"/>
		</td>

		<td align="left" nowrap="nowrap"><label for="datoFirmaNombreVia" class="small">Nombre calle</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.nombreVia" id="datoFirmaNombreVia" size="20" maxlength="100"
		 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>

	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="datoFirmaNumero" class="small">N&uacute;m. calle</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.numero" id="datoFirmaNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="datoFirmaBloque" class="small">Bloque</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.bloque" id="datoFirmaBloque" size="5" maxlength="5"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="datoFirmaPlanta" class="small">Planta</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.planta" id="datoFirmaPlanta" size="5" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="datoFirmaPuerta" class="small">Puerta</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.puerta" id="datoFirmaPuerta" size="5" maxlength="100"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="datoFirmaNumeroBis" class="small">N&uacute;m. bis</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.numeroBis" id="datoFirmaNumeroBis" size="5"></s:textfield></td>

		<td align="left" nowrap="nowrap"><label for="datoFirmaEscalera" class="small">Escalera</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.escalera" id="datoFirmaEscalera" size="5" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="datoFirmaKm" class="small">Punto KM</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.km" id="datoFirmaKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="datoFirmaPortal" class="small">Portal</label></td>
		<td><s:textfield name="tramiteRegRbmDto.datoFirmaDto.direccion.portal" id="datoFirmaPortal" size="5"></s:textfield></td>
	</tr>
</table>
	
<script type="text/javascript">

function resizeMe(){
	var $cal = $('iframe:eq(1)',parent.document);
	var position = $("#diaFecFirma").position();
	$cal.css({
	    left:  position.left + "px",
	    top: (position.top + 20) + "px"
	});
}

var viaDatoFirma = new BasicContentAssist(document.getElementById('datoFirmaNombreVia'), [], null, true); 
recargarNombreVias('datoFirmaSelectProvinciaId', 'datoFirmaSelectMunicipioId', 'tipoViaId','datoFirmaNombreVia',viaDatoFirma);

</script> 