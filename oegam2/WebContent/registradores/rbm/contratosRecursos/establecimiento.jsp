<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		
	
	<s:hidden name="tramiteRegRbmDto.propiedadDto.establecimiento.idEstablecimiento"/>
	<s:hidden name="tramiteRegRbmDto.propiedadDto.establecimiento.idPropiedad"/>
	

	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Establecimiento:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
      			<td align="left" nowrap="nowrap"><label for="establecimientoNombreEstablecimiento">Nombre<span class="naranja">*</span></label></td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.nombreEstablecimiento" id="establecimientoNombreEstablecimiento" size="18" maxlength="255"></s:textfield>	</td>
               <td align="left" nowrap="nowrap"><label for="establecimientoClaseEstablecimiento" class="small">Clase<span class="naranja">*</span></label></td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.claseEstablecimiento" id="establecimientoClaseEstablecimiento" size="18" maxlength="255"></s:textfield></td>
            </tr>
            <tr>
               <td align="left" nowrap="nowrap"><label for="establecimientoNumeroEstablecimiento" class="small">N&uacute;mero<span class="naranja">*</span></label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.numeroEstablecimiento" id="establecimientoModelo" size="18" maxlength="255"></s:textfield></td>
      			
            </tr>
  		</table>
  		
  		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Domicilio:</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">

			<tr>
     		   <td align="left" nowrap="nowrap"><label for="establecimientoProvinciaId" class="small">Provincia</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.establecimiento.codProvincia" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="establecimientoSelectProvinciaId"
 						    onchange="cargarListaMunicipios(this,'establecimientoSelectMunicipioId','establecimientoHiddenMunicipioId');
 						     inicializarTipoVia('establecimientoCodTipoVia','establecimientoNombreVia', viaEstablecimiento);"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="establecimientoMunicipio" class="small">Municipio</label></td>
               <td>       
				<s:select id="establecimientoSelectMunicipioId"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegRbmDto.propiedadDto.establecimiento.codProvincia)"
							onchange="javascript:seleccionMunicipio(this, 'establecimiento');
							inicializarTipoVia('establecimientoCodTipoVia','establecimientoNombreVia', viaEstablecimiento);"
							name="tramiteRegRbmDto.propiedadDto.establecimiento.codMunicipio"
							headerValue="Seleccione Municipio" 
							listKey="idMunicipio"
							listValue="nombre" />
				<s:hidden id="establecimientoHiddenMunicipioId"/>
				<s:hidden id="establecimientoHiddenMunicipality" />  
               </td>
            </tr>
    		<tr>  
               <td align="left" nowrap="nowrap"><label for="establecimientoNumLocal" class="small">Entidad local</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.entidadLocalMenor" id="establecimientoEntidadLocalMenor" size="18" maxlength="255"></s:textfield></td>
             </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="establecimientoCodTipoVia" class="small">Tipo de v&iacute;a</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.establecimiento.codTipoVia" 
                          id="establecimientoCodTipoVia"
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()"
						  headerKey="" headerValue="Selecionar tipo" listKey="key"
						  listValue="name" 
 						  onchange ="cargarListaNombresVia('establecimientoSelectProvinciaId', 'establecimientoSelectMunicipioId', this, 'establecimientoNombreVia', viaEstablecimiento);"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="establecimientoNombreVia" class="small">Nombre de la v&iacute;a</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.nombreVia" id="establecimientoNombreVia" size="35" maxlength="255"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
              </tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
              	<td align="left" nowrap="nowrap"><label for="establecimientoPuntoKilometrico" class="small">Punto Kilom&eacute;trico</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.puntoKilometrico" id="establecimientoPuntoKilometrico" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="establecimientoNumero" class="small">N&uacute;mero</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.numero" id="establecimientoNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="establecimientoNumeroBis" class="small">N&uacute;m. bis</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.numeroBis" id="establecimientoNumeroBis" size="5" maxlength="5"></s:textfield></td>
            </tr>
            <tr>
            	<td align="left" nowrap="nowrap"><label for="establecimientoPortal" class="small">Portal</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.portal" id="establecimientoPortal" size="5" maxlength="5"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="establecimientoBloque" class="small">Bloque</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.bloque" id="establecimientoBloque" size="5" maxlength="5"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="establecimientoEscalera" class="small">Escalera</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.escalera" id="establecimientoEscalera" size="5" maxlength="5"></s:textfield></td>
            </tr>
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="establecimientoPlanta" class="small">Planta</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.planta" id="establecimientoPlanta" size="5" maxlength="100"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="establecimientoPuerta" class="small">Puerta</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.puerta" id="establecimientoPuerta" size="5" maxlength="100"></s:textfield></td>
				<td align="left" nowrap="nowrap"> <label for="establecimientoCodigoPostal" class="small">C&oacute;digo postal</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.codigoPostal" id="establecimientoCodigoPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap" ><label for="establecimientoLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
 			   		<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
 			   </td>
               <td colspan="3"><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.lugarUbicacion" id="establecimientoLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
            </tr>
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Datos Registrales:</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
              	<td align="left" nowrap="nowrap"><label for="establecimientoCodRegistroPropiedad" class="small" >Registro propiedad</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo registro de la propiedad"/>
              	</td>
                <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.establecimiento.codRegistroPropiedad" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroPropiedad()" 
                          listKey="idRegistro"
                          listValue="nombre"
                          headerKey="" 
                          headerValue="Selecionar Tipo"
                          id="establecimientoCodRegistroPropiedad"/>
                </td>
            	<td align="left" nowrap="nowrap"><label for="establecimientoReferenciaCatastral" class="small" >Referencia catastral</label>
           			<img src="img/botonDameInfo.gif" alt="Info" title="Referencia catastral para identificar la finca de ubicaci&oacute;n"/>
            	</td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.referenciaCatastral" id="establecimientoReferenciaCatastral" size="18" maxlength="255"></s:textfield></td>
            </tr>
			<tr>
     		   <td align="left" nowrap="nowrap"><label for="establecimientoProvinciaDatReg" class="small">Provincia</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.establecimiento.codProvinciaDatReg" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="establecimientoDatRegProvincia"
 						  onchange="cargarListaMunicipios(this,'establecimientoDatRegSelectMunicipio','establecimientoDatRegHiddenMunicipio');"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="establecimientoDatRegMunicipio" class="small">Municipio</label></td>
               <td>       
				<s:select id="establecimientoDatRegSelectMunicipio"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegRbmDto.propiedadDto.establecimiento.codProvinciaDatReg)"
							onchange="javascript:seleccionMunicipio(this, 'establecimientoDatReg');"
							name="tramiteRegRbmDto.propiedadDto.establecimiento.codMunicipioDatReg"
							headerValue="Seleccione Municipio" listKey="idMunicipio"
							listValue="nombre" />
				<s:hidden id="establecimientoDatRegHiddenMunicipio"/>
				<s:hidden id="establecimientoDatRegHiddenMunicipality" /> 
               </td>
            </tr>
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="establecimientoSeccionPropiedad" class="small">Secci&oacute;n registral</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.seccionPropiedad" id="establecimientoSeccionPropiedad" size="18" maxlength="255"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="establecimientoNumFinca" class="small">N&uacute;mero de finca</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.numFinca" id="establecimientoNumFinca" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
            </tr>
             <tr>
             	<td align="left" nowrap="nowrap"><label for="establecimientoNumFincaBis" class="small">N&uacute;mero de finca bis</label></td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.numFincaBis" id="establecimientoNumFincaBis" size="18" maxlength="5"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="establecimientoNumSubFinca" class="small">Subn&uacute;mero de finca</label></td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.establecimiento.numSubFinca" id="establecimientoNumSubFinca" size="18" maxlength="5"></s:textfield></td>
            </tr>
		</table>
		
<script type="text/javascript">

	var viaEstablecimiento = new BasicContentAssist(document.getElementById('establecimientoNombreVia'), [], null, true); 
	recargarNombreVias('establecimientoSelectProvinciaId', 'establecimientoSelectMunicipioId', 'establecimientoCodTipoVia','establecimientoNombreVia',viaEstablecimiento);

</script>
