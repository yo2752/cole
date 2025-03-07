<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>

<s:hidden name="tramiteRegRbmDto.representanteSolicitante.idInterviniente"/>
<s:hidden name="tramiteRegRbmDto.representanteSolicitante.notario.codigo"/>
<s:hidden name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.idDatRegMercantil"/>

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

<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
	align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
		<td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.tipoPersona"
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
            			<s:textfield name="tramiteRegRbmDto.representanteSolicitante.nif" id="representanteNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
            		</td>
            		
            		<td align="left" nowrap="nowrap">
             			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
							onclick="javascript:buscarIntervinienteRepresentante('representanteNif', '<%= org.gestoresmadrid.core.model.enumerados.TipoInterviniente.RepresentanteSolicitante.getValorEnum()%>', 'Solicitante')" />
					</td>
				</tr>
          	</table>
	    </td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteNombre" >Nombre</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td width="35%"><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.nombre" id="representanteNombre" size="18" maxlength="100"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representante1Apellido" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.apellido1RazonSocial" id="representante1Apellido" size="18" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteApellido2" >2do apellido</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.persona.apellido2" id="representanteApellido2" size="18" maxlength="100"></s:textfield></td>
	</tr>
	
</table>

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
	<tr>
		<td class="tabular"><span class="titulo">Datos del Notario:</span></td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteTipoPersonaNotario" class="small">Tipo de persona<span class="naranja">*</span></label></td>
		<td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.notario.tipoPersona"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
				headerValue="Selecionar Tipo"
				headerKey=""
				listValue="nombreEnum" 
				listKey="valorXML"
				id="representanteTipoPersonaNotario" /></td>
		<td align="left" nowrap="nowrap"><label for="representanteNotarioNif" class="small">Nif. notario<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.nif" id="representanteNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteNotarioNombre" class="small">Nombre notario<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.nombre" id="representanteNotarioNombre" size="18" maxlength="255"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="representanteSolicitanteNotarioApellido1">1er apellido<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.apellido1" id="representanteNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteNotarioApellido2" >2do apellido</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.apellido2" id="representanteNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
		
		<td align="left" nowrap="nowrap"><label for="representantePlazaNotario" >Plaza<span class="naranja">*</span></label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Plaza o lugar"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.plazaNotario" id="representantePlazaNotario" size="18" maxlength="255"></s:textfield></td>
	</tr>

	<tr>
		<td align="left" nowrap="nowrap"><label for="representanteFecOtorgamiento" class="small">Fecha otorgamiento<span class="naranja">*</span></label></td>
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
		<td align="left" nowrap="nowrap"><label for="representanteNumProtocolo" class="small">N&uacute;m. protocolo<span class="naranja">*</span></label></td>
		<td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.notario.numProtocolo" id="representanteNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
	</tr>
</table>

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Datos del registro Mercantil del Solicitante:</span>
		</td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
      <tr>
 			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteRegistoMercantil" class="small">Registro mercantil<span class="naranja">*</span></label></td>
          <td width="30%"><s:select name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.codRegistroMercantil" 
                    list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                     listKey="idRegistro"
                     listValue="nombre"
                     headerKey="" 
                     headerValue="Selecionar Tipo"
                     id="representanteSolicitanteRegistoMercantil"/></td>
          
 			<td align="left" nowrap="nowrap"><label for="representanteSolicitanteTomo" class="small">Tomo inscrip.<span class="naranja">*</span></label></td>
          <td width="35%"><s:textfield name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.tomo" id="representanteSolicitanteTomo" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
 	  </tr>
      <tr> 
          <td align="left" nowrap="nowrap"><label for="representanteSolicitanteFolio" class="small">Folio inscrip.<span class="naranja">*</span></label></td>
          <td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.folio" id="representanteSolicitanteFolio" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
          <td align="left" nowrap="nowrap"><label for="representanteSolicitanteNumInscripcion" class="small">N&uacute;mero inscrip.<span class="naranja">*</span></label></td>
          <td><s:textfield name="tramiteRegRbmDto.representanteSolicitante.datRegMercantil.numInscripcion" id="representanteSolicitanteNumInscripcion" size="5" maxlength="255"></s:textfield></td>
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

</script>
