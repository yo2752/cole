<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		
	
		<s:hidden name="tramiteRegRbmDto.propiedadDto.otrosBienes.idOtrosBienes"/>
		<s:hidden name="tramiteRegRbmDto.propiedadDto.otrosBienes.idPropiedad"/>

	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Otros bienes:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
      			<td align="left" nowrap="nowrap"><label for="otrosBienesTipo">Tipo</label></td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.tipo" id="otrosBienesTipo" size="18" maxlength="255"></s:textfield>	</td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesMarca" class="small">Marca</label></td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.marca" id="otrosBienesMarca" size="18" maxlength="255"></s:textfield></td>
            </tr>
            <tr>
               <td align="left" nowrap="nowrap"><label for="otrosBienesModelo" class="small">Modelo</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.modelo" id="otrosBienesModelo" size="18" maxlength="255"></s:textfield></td>
            	<td align="left" nowrap="nowrap"><label for="otrosBienesNumSerie" >Serie</label>
            		<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de serie o fabricaci&oacute;n" />
            	</td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numSerie" id="otrosBienesNumSerie" size="18" maxlength="255"></s:textfield>	</td>
            </tr>
             <tr>  
               <td align="left" nowrap="nowrap"><label for="otrosBienesOtraDescripcion" class="small">Otra descripci&oacute;n</label></td>
               <td colspan="3"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.otraDescripcion" id="otrosBienesOtraDescripcion" size="35" maxlength="255"></s:textfield></td>
             </tr>
  		</table>
  		
  		 <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Registro administrativo:</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
      		   <td align="left" nowrap="nowrap"><label for="otrosBienesTipoRegAdm" >Tipo</label>
      		   		<img src="img/botonDameInfo.gif" alt="Info" title="Tipo del registro administrativo"/>
      		   </td>
                <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.otrosBienes.tipoRegAdm" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroAdministrativo()" 
                         listKey="key"
                		 headerKey="" 
                		 headerValue="Seleccionar estado"
                         id="otrosBienesTipoRegAdm"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesNumRegAdm" class="small" >N&uacute;mero</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero identificativo en el registro administrativo"/>
               </td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numRegAdm" id="otrosBienesNumRegAdm" size="18" maxlength="255"></s:textfield></td>
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
     		   <td align="left" nowrap="nowrap"><label for="otrosBienesProvinciaId" class="small">Provincia</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.otrosBienes.codProvincia" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="otrosBienesSelectProvinciaId"
 						    onchange="cargarListaMunicipios(this,'otrosBienesSelectMunicipioId','otrosBienesHiddenMunicipioId');
 						     inicializarTipoVia('otrosBienesCodTipoVia','otrosBienesNombreVia', viaOtrosBienes);"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesMunicipio" class="small">Municipio</label></td>
               <td>       
				<s:select id="otrosBienesSelectMunicipioId"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegRbmDto.propiedadDto.otrosBienes.codProvincia)"
							onchange="javascript:seleccionMunicipio(this, 'otrosBienes');
							inicializarTipoVia('otrosBienesCodTipoVia','otrosBienesNombreVia', viaOtrosBienes);"
							name="tramiteRegRbmDto.propiedadDto.otrosBienes.codMunicipio"
							headerValue="Seleccione Municipio" 
							listKey="idMunicipio"
							listValue="nombre" />
				<s:hidden id="otrosBienesHiddenMunicipioId"/>
				<s:hidden id="otrosBienesHiddenMunicipality" />  
               </td>
            </tr>
    		<tr>  
               <td align="left" nowrap="nowrap"><label for="otrosBienesNumLocal" class="small">Entidad local</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.entidadLocalMenor" id="otrosBienesEntidadLocalMenor" size="18" maxlength="255"></s:textfield></td>
             </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="otrosBienesCodTipoVia" class="small">Tipo de v&iacute;a</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.otrosBienes.codTipoVia" 
                          id="otrosBienesCodTipoVia"
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()"
						  headerKey="" headerValue="Selecionar tipo" listKey="key"
						  listValue="name" 
 					      onchange ="cargarListaNombresVia('otrosBienesSelectProvinciaId', 'otrosBienesSelectMunicipioId', this, 'otrosBienesNombreVia', viaOtrosBienes);"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesNombreVia" class="small">Nombre de la v&iacute;a</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.nombreVia" id="otrosBienesNombreVia" size="35" maxlength="255"
 				onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
              </tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
              	<td align="left" nowrap="nowrap"><label for="otrosBienesPuntoKilometrico" class="small">Punto Kilom&eacute;trico</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.puntoKilometrico" id="otrosBienesPuntoKilometrico" size="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="otrosBienesNumero" class="small">N&uacute;mero</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numero" id="otrosBienesNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesNumeroBis" class="small">N&uacute;m. bis</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numeroBis" id="otrosBienesNumeroBis" size="5" maxlength="5"></s:textfield></td>
            </tr>
            <tr>
            	<td align="left" nowrap="nowrap"><label for="otrosBienesPortal" class="small">Portal</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.portal" id="otrosBienesPortal" size="5" maxlength="5"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesBloque" class="small">Bloque</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.bloque" id="otrosBienesBloque" size="5" maxlength="5"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesEscalera" class="small">Escalera</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.escalera" id="otrosBienesEscalera" size="5" maxlength="5"></s:textfield></td>
            </tr>
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="otrosBienesPlanta" class="small">Planta</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.planta" id="otrosBienesPlanta" size="5" maxlength="100"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesPuerta" class="small">Puerta</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.puerta" id="otrosBienesPuerta" size="5" maxlength="100"></s:textfield></td>
				<td align="left" nowrap="nowrap"> <label for="otrosBienesCodigoPostal" class="small">C&oacute;digo postal</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.codigoPostal" id="otrosBienesCodigoPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap" ><label for="otrosBienesLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
 			   		<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
 			   </td>
               <td colspan="3"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.lugarUbicacion" id="otrosBienesLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
            </tr>
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Datos registrales:</span>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
              	<td align="left" nowrap="nowrap"><label for="otrosBienesCodRegistroPropiedad" class="small" >Registro propiedad</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo registro de la propiedad"/>
              	</td>
                <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.otrosBienes.codRegistroPropiedad" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroPropiedad()" 
                          listKey="idRegistro"
                          listValue="nombre"
                          headerKey="" 
                          headerValue="Selecionar Tipo"
                          id="otrosBienesCodRegistroPropiedad"/>
                </td>
                
            	<td align="left" nowrap="nowrap"><label for="otrosBienesReferenciaCatastral" class="small" >Referencia catastral</label>
            		<img src="img/botonDameInfo.gif" alt="Info" title="Referencia catastral para identificar la finca de ubicaci&oacute;n"/>
            	</td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.referenciaCatastral" id="otrosBienesReferenciaCatastral" size="18" maxlength="255"></s:textfield></td>
            </tr>
			<tr>
     		   <td align="left" nowrap="nowrap"><label for="otrosBienesProvinciaDatReg" class="small">Provincia</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.otrosBienes.codProvinciaDatReg" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="otrosBienesDatRegProvincia"
 						  onchange="cargarListaMunicipios(this,'otrosBienesDatRegSelectMunicipio','otrosBienesDatRegHiddenMunicipio');"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesDatRegMunicipio" class="small">Municipio</label></td>
               <td>       
				<s:select id="otrosBienesDatRegSelectMunicipio"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegRbmDto.propiedadDto.otrosBienes.codProvinciaDatReg)"
							onchange="javascript:seleccionMunicipio(this, 'otrosBienesDatReg');"
							name="tramiteRegRbmDto.propiedadDto.otrosBienes.codMunicipioDatReg"
							headerValue="Seleccione Municipio" listKey="idMunicipio"
							listValue="nombre" />
				<s:hidden id="otrosBienesDatRegHiddenMunicipio"/>
				<s:hidden id="otrosBienesDatRegHiddenMunicipality" /> 
               </td>
            </tr>
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="otrosBienesSeccionPropiedad" class="small">Secci&oacute;n registral</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.seccionPropiedad" id="otrosBienesSeccionPropiedad" size="18" maxlength="255"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesNumFinca" class="small">N&uacute;mero de finca</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numFinca" id="otrosBienesNumFinca" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
            </tr>
             <tr>
             	<td align="left" nowrap="nowrap"><label for="otrosBienesNumFincaBis" class="small">N&uacute;mero de finca bis</label></td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numFincaBis" id="otrosBienesNumFincaBis" size="18" maxlength="5"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="otrosBienesNumSubFinca" class="small">Subn&uacute;mero de finca</label></td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numSubFinca" id="otrosBienesNumSubFinca" size="18" maxlength="5"></s:textfield></td>
            </tr>
		</table>
		
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Sociedad:</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
      		   <td align="left" nowrap="nowrap"><label for="otrosBienesNombreSociedad">Nombre</label></td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.nombreSociedad" id="otrosBienesNombreSociedad" size="18" maxlength="255"></s:textfield>	</td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesCifSociedad" class="small">CIF</label></td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.cifSociedad" id="otrosBienesCifSociedad" size="18" maxlength="255"></s:textfield></td>
            </tr>
             <tr>
      		   <td align="left" nowrap="nowrap"><label for="otrosBienesCnae" >CNAE</label>
      		   	<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo de actividad econ&oacute;mica"/>
      		   </td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.cnae" id="otrosBienesCnae" size="18" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield>	</td>

            </tr>
  		</table>
  		
  			<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Datos del registro Mercantil:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
      		   <td align="left" nowrap="nowrap"><label for="otrosBienesCodRegMercantil" class="small">Registro mercantil</label></td>
               <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.otrosBienes.codRegistroMercantil" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                          listKey="idRegistro"
                          listValue="nombre"
                          headerKey="" 
                          headerValue="Selecionar Tipo"
                          id="otrosBienesCodRegMercantil"/></td>
               
      		   <td align="left" nowrap="nowrap"><label for="otrosBienesSeccionMercantil" class="small">Secci&oacute;n mercantil</label></td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.seccionMercantil" id="otrosBienesSeccionMercantil" size="18" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield></td>
      		</tr>
            <tr>
               <td align="left" nowrap="nowrap"><label for="otrosBienesNumHoja" class="small">N&uacute;m. hoja</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numHoja" id="otrosBienesNumHoja" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
                <td align="left" nowrap="nowrap"><label for="otrosBienesNumHojaDup" class="small">N&uacute;m. hoja duplicada</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numHojaDup" id="otrosBienesNumHojaDup" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
            </tr> 
            <tr>
               <td align="left" nowrap="nowrap"><label for="otrosBienesNumSubHoja" class="small">Subn&uacute;mero de hoja</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.numSubHoja" id="otrosBienesNumSubHoja" size="5" maxlength="255"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesTomoSociedad" class="small">Tomo inscrip.</label></td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.tomoSociedad" id="otrosBienesTomoSociedad" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
      		</tr>
      		 <tr>
               <td align="left" nowrap="nowrap"><label for="otrosBinesFolioSociedad" class="small">Folio inscrip.</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.folioSociedad" id="otrosBinesFolioSociedad" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="otrosBienesInsSociedad" class="small">N&uacute;m. inscrip.</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.otrosBienes.insSociedad" id="otrosBienesInsSociedad" size="5" maxlength="10" onkeypress="return soloNumeroDecimal(event, this, '10', '0')"></s:textfield></td>
      		</tr>
		</table>
		
		
<script type="text/javascript">

	var viaOtrosBienes = new BasicContentAssist(document.getElementById('otrosBienesNombreVia'), [], null, true); 
	recargarNombreVias('otrosBienesSelectProvinciaId', 'otrosBienesSelectMunicipioId', 'otrosBienesCodTipoVia','otrosBienesNombreVia',viaOtrosBienes);

</script>
