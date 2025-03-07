<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>

<s:hidden
	name="tramiteRegRbmDto.representanteCesionario.idInterviniente" />
<s:hidden name="tramiteRegRbmDto.representanteCesionario.notario.codigo" />

<%@include file="/includes/erroresYMensajes.jspf"%>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
	align="left">
	<tr>
		<td class="tabular"><span class="titulo">Representante:</span></td>
	</tr>
</table>
<iframe width="174" height="189"
	name="gToday:normal:agenda.js:representCesionario"
	id="gToday:normal:agenda.js:representCesionario"
	src="calendario/ipopeng.htm" scrolling="no" frameborder="0"
	style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">

</iframe>

<div id=representanteCesionario>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
			<td width="30%"><s:select
					name="tramiteRegRbmDto.representanteCesionario.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerKey="" headerValue="Selecionar Tipo" listValue="nombreEnum"
					listKey="valorXML" id="representanteCesionarioTipoPersona" /></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioNif"
				class="small">Nif/Cif.<span class="naranja">*</span></label></td>
			<td width="35%">
				<table cellSpacing="0">
					<tr>
						<td align="left" nowrap="nowrap"><s:textfield
								name="tramiteRegRbmDto.representanteCesionario.nif"
								id="representanteCesionarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
	
						<td align="left" nowrap="nowrap"><input type="button"
							id="idBotonBuscarNif" class="boton-persona"
							style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
							onclick="javascript:buscarIntervinienteAjax('representanteCesionarioNif', 'representanteCesionario')" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioNombre" >Nombre</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td width="35%"><s:textfield name="tramiteRegRbmDto.representanteCesionario.persona.nombre" id="representanteCesionarioNombre" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioApellido1RazonSocial" >1er apellido / Raz&oacute;n Social<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteCesionario.persona.apellido1RazonSocial" id="representanteCesionarioApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteCesionario.persona.apellido2" id="representanteCesionarioApellido2" size="18" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioCargo" >Cargo<span class="naranja">*</span></label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Cargo que ostenta el representante"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteCesionario.cargo" id="representanteCesionarioCargo" size="18" maxlength="255"></s:textfield></td>
		</tr>
	
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">Direcci&oacute;n
					del Representante:</span></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioRegion" >Regi&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteCesionario.direccion.regionExtranjera" id="representanteCesionarioRegion" size="18"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
			<td colspan="3"><s:select
					name="tramiteRegRbmDto.representanteCesionario.direccion.pais"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
					listKey="codigo" listValue="nombre" headerKey=""
					headerValue="Selecionar País" id="representanteCesionarioPais" /></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioSelectProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
			<td width="30%"><s:select
					name="tramiteRegRbmDto.representanteCesionario.direccion.idProvincia"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
					headerKey="" headerValue="Seleccionar" listKey="idProvincia"
					listValue="nombre" id="representanteCesionarioSelectProvinciaId"
					onchange="cargarListaMunicipios(this,'representanteCesionarioSelectMunicipioId','representanteCesionarioHiddenMunicipioId');
					inicializarTipoVia('representanteCesionarioTipoVia','representanteCesionarioNombreVia', viaRepresentanteCesionario);"/>
			</td>
	
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioSelectMunicipioId" class="small">Municipio<span class="naranja">*</span></label></td>
			<td width="35%"><s:select
					id="representanteCesionarioSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.representanteCesionario)"
					onchange="javascript:seleccionMunicipio(this, 'representanteCesionario');
					 inicializarTipoVia('representanteCesionarioTipoVia','representanteCesionarioNombreVia', viaRepresentanteCesionario);"
					name="tramiteRegRbmDto.representanteCesionario.direccion.idMunicipio"
					headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
					listValue="nombre" /> <s:hidden
					id="representanteCesionarioHiddenMunicipioId" /> <s:hidden
					id="representanteCesionarioHiddenMunicipality" /></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioPueblo"
				class="small">Entidad local</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.pueblo"
					id="representanteCesionarioPueblo" size="25" maxlength="70"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioCodPostal" class="small">C&oacute;digo
					postal<span class="naranja">*</span></label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.codPostal"
					id="representanteCesionarioCodPostal" size="5" maxlength="5"
					onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
		</tr>
	
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioIdTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
			<td><s:select
					name="tramiteRegRbmDto.representanteCesionario.direccion.idTipoVia"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()"
					headerKey="" headerValue="Selecionar tipo" listKey="key"
					listValue="name" id="representanteCesionarioTipoVia" 
					onchange ="cargarListaNombresVia('representanteCesionarioSelectProvinciaId', 'representanteCesionarioSelectMunicipioId', this, 'representanteCesionarioNombreVia', viaRepresentanteCesionario);"/>
				</td>
	
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
			<td>
				<s:textfield name="tramiteRegRbmDto.representanteCesionario.direccion.nombreVia" id="representanteCesionarioNombreVia" size="20" maxlength="100"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioNumero"
				class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.numero"
					id="representanteCesionarioNumero" size="5" maxlength="5"
					onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioBloque"
				class="small">Bloque</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.bloque"
					id="representanteCesionarioBloque" size="5" maxlength="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioPlanta"
				class="small">Planta</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.planta"
					id="representanteCesionarioPlanta" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioPuerta"
				class="small">Puerta</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.puerta"
					id="representanteCesionarioPuerta" size="5" maxlength="100"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioNumeroBis" class="small">N&uacute;m. bis</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.numeroBis"
					id="representanteCesionarioNumeroBis" size="5"></s:textfield></td>
	
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioEscalera" class="small">Escalera</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.escalera"
					id="representanteCesionarioEscalera" size="5" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioKm"
				class="small">Punto KM</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.km"
					id="representanteCesionarioKm" size="5" maxlength="5"
					onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioPortal"
				class="small">Portal</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.direccion.portal"
					id="representanteCesionarioPortal" size="5"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
			</td>
			<td colspan="3"><s:textfield name="tramiteRegRbmDto.representanteCesionario.direccion.lugarUbicacion" id="representanteCesionarioLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
		</tr>
	</table>
	
	
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">Datos del
					Notario:</span></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioTipoPersonaNotario" class="small">Tipo
					de persona</label></td>
			<td width="30%"><s:select
					name="tramiteRegRbmDto.representanteCesionario.notario.tipoPersona"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
					headerValue="Selecionar Tipo" headerKey="" listValue="nombreEnum"
					listKey="valorXML" id="representanteCesionarioTipoPersonaNotario" /></td>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioNotarioNif" class="small">Nif. notario</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.notario.nif"
					id="representanteCesionarioNotarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioNotarioNombre" class="small">Nombre notario</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.notario.nombre"
					id="representanteCesionarioNotarioNombre" size="18" maxlength="255"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioNotarioApellido1">1er apellido</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.notario.apellido1"
					id="representanteCesionarioNotarioApellido1" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioNotarioApellido2" >2do apellido</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
			</td>
			<td><s:textfield name="tramiteRegRbmDto.representanteCesionario.notario.apellido2" id="representanteCesionarioNotarioApellido2" size="18" maxlength="100"></s:textfield></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="representanteCesionarioNotarioSelectProvinciaId" >C&oacute;digo INE provin.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE de la Provincia del Notario"/>
			</td>
			<td width="30%"><s:select
					name="tramiteRegRbmDto.representanteCesionario.notario.codProvincia"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
					headerKey="" headerValue="Seleccionar" listKey="idProvincia"
					listValue="nombre"
					id="representanteCesionarioNotarioSelectProvinciaId"
					onchange="cargarListaMunicipios(this,'representanteCesionarioNotarioSelectMunicipioId','representanteCesionarioNotarioHiddenMunicipioId');" />
			</td>
			<td align="left" nowrap="nowrap"><label for="representantNotaryMunicipalityInecode" >C&oacute;digo INE muni.</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo INE del Municipio del Notario"/>
			</td>
			<td width="35%"><s:select
					id="representanteCesionarioNotarioSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosNotario(tramiteRegRbmDto.representanteCesionario.notario)"
					onchange="javascript:seleccionMunicipio(this, 'representanteCesionarioNotario');"
					name="tramiteRegRbmDto.representanteCesionario.notario.codMunicipio"
					headerValue="Seleccione Municipio" listKey="idMunicipio"
					listValue="nombre" /> <s:hidden
					id="representanteCesionarioNotarioHiddenMunicipioId" /> <s:hidden
					id="representanteCesionarioNotarioHiddenMunicipality" /></td>
	
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioFecOtorgamiento" class="small">Fecha
					otorgamiento</label></td>
			<td>
				<table style="width: 20%">
					<tr>
						<td align="left"><s:textfield
								name="tramiteRegRbmDto.representanteCesionario.notario.fecOtorgamientoNotario.dia"
								id="diaFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"
								onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));" />
						</td>
						<td align="left"><label class="labelFecha">/</label></td>
						<td align="left"><s:textfield
								name="tramiteRegRbmDto.representanteCesionario.notario.fecOtorgamientoNotario.mes"
								id="mesFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"
								onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));" />
						</td>
						<td align="left"><label class="labelFecha">/</label></td>
						<td align="left"><s:textfield
								name="tramiteRegRbmDto.representanteCesionario.notario.fecOtorgamientoNotario.anio"
								id="anioFecOtorgamiento"
								onfocus="this.className='inputfocus';" size="5" maxlength="4"
								onblur="validaInputFecha(document.getElementById('diaFecOtorgamiento'), document.getElementById('mesFecOtorgamiento'), document.getElementById('anioFecOtorgamiento'));" />
						</td>
	
						<td align="left" nowrap="nowrap" width="5%"><a
							href="javascript:;"
							onClick="if(self.representCesionario)representCesionario.fPopCalendarSplit(anioFecOtorgamiento, mesFecOtorgamiento, diaFecOtorgamiento); resizeMe();  return false;"
							title="Seleccionar fecha"> <img class="PopcalTrigger"
								align="left" src="img/ico_calendario.gif" width="15" height="16"
								border="0" alt="Calendario" />
						</a></td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap"><label
				for="representanteCesionarioNumProtocolo" class="small">N&uacute;m.
					protocolo</label></td>
			<td><s:textfield
					name="tramiteRegRbmDto.representanteCesionario.notario.numProtocolo"
					id="representanteCesionarioNumProtocoloNotario" size="18" maxlength="255"></s:textfield></td>
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

	var viaRepresentanteCesionario = new BasicContentAssist(document.getElementById('representanteCesionarioNombreVia'), [], null, true); 
	recargarNombreVias('representanteCesionarioSelectProvinciaId', 'representanteCesionarioSelectMunicipioId', 'representanteCesionarioTipoVia','representanteCesionarioNombreVia',viaRepresentanteCesionario);

</script>

