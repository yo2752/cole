<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>		
	
	<s:hidden name="tramiteRegRbmDto.propiedadDto.maquinaria.idMaquinaria"/>
	<s:hidden name="tramiteRegRbmDto.propiedadDto.maquinaria.idPropiedad"/>

	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Maquinaria:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
      			<td align="left" nowrap="nowrap"><label for="maquinariaTipo" >Tipo</label>
      				<img src="img/botonDameInfo.gif" alt="Info" title="tipo de m&aacute;quina" />
      			</td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.tipo" id="maquinariaTipo" size="18" maxlength="255"></s:textfield>	</td>
               <td align="left" nowrap="nowrap"><label for="maquinariaMarca" class="small">Marca</label></td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.marca" id="maquinariaMarca" size="18" maxlength="255"></s:textfield></td>
            </tr>
            <tr>
               <td align="left" nowrap="nowrap"><label for="maquinariaModelo" class="small">Modelo</label></td>
               <td>
               <s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.modelo" id="maquinariaModelo" size="18" maxlength="255"></s:textfield>
               </td>
      			<td align="left" nowrap="nowrap"><label for="maquinariaGrupoId" >Grupo</label>
      				<img src="img/botonDameInfo.gif" alt="Info" title="Grupo del bien" />
      			</td>
               <td colspan="3">
               		<s:select name="tramiteRegRbmDto.propiedadDto.maquinaria.grupo" 
                          list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListGrupoBien()" 
                          listKey="codigo"
                          listValue="nombre"
                          headerKey="" 
                          headerValue="Selecionar grupo"
                          id="maquinariaGrupoId"/>
               </td>
            </tr>
            <tr>
            	<td align="left" nowrap="nowrap"><label for="maquinariaNumSerie">Serie</label>
            		<img src="img/botonDameInfo.gif" alt="Info" title="N&uacute;mero de serie o fabricaci&oacute;n"  />
            	</td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.numSerie" id="maquinariaNumSerie" size="18" maxlength="255"></s:textfield>	</td>
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
     		   <td align="left" nowrap="nowrap"><label for="maquinariaProvinciaId" class="small">Provincia</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.maquinaria.codProvincia" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="maquinariaSelectProvinciaId"
 						    onchange="cargarListaMunicipios(this,'maquinariaSelectMunicipioId','maquinariaHiddenMunicipioId');
							inicializarTipoVia('maquinariaCodTipoVia','maquinariaNombreVia', viaMaquinaria);"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="maquinariaMunicipio" class="small">Municipio</label></td>
               <td>       
				<s:select id="maquinariaSelectMunicipioId"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegRbmDto.propiedadDto.maquinaria.codProvincia)"
							onchange="javascript:seleccionMunicipio(this, 'maquinaria');
							inicializarTipoVia('maquinariaCodTipoVia','maquinariaNombreVia', viaMaquinaria);"
							name="tramiteRegRbmDto.propiedadDto.maquinaria.codMunicipio"
							headerValue="Seleccione Municipio" 
							listKey="idMunicipio"
							listValue="nombre" />
				<s:hidden id="maquinariaHiddenMunicipioId"/>
				<s:hidden id="maquinariaHiddenMunicipality" />  
               </td>
            </tr>
    		<tr>  
               <td align="left" nowrap="nowrap"><label for="maquinariaNumLocal" class="small">Entidad local</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.entidadLocalMenor" id="maquinariaEntidadLocalMenor" size="18" maxlength="255"></s:textfield></td>
             </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="maquinariaCodTipoVia" class="small">Tipo de v&iacute;a</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.maquinaria.codTipoVia" 
                          id="maquinariaCodTipoVia"
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()"
						  headerKey="" headerValue="Selecionar tipo" listKey="key"
						  listValue="name" 
 						  onchange ="cargarListaNombresVia('maquinariaSelectProvinciaId', 'maquinariaSelectMunicipioId', this, 'maquinariaNombreVia', viaMaquinaria);"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="maquinariaNombreVia" class="small">Nombre de la v&iacute;a</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.nombreVia" id="maquinariaNombreVia" size="35" maxlength="255"
                 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
              </tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
              	<td align="left" nowrap="nowrap"><label for="maquinariaPuntoKilometrico" class="small">Punto Kilom&eacute;trico</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.puntoKilometrico" id="maquinariaPuntoKilometrico" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="maquinariaNumero" class="small">N&uacute;mero</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.numero" id="maquinariaNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="maquinariaNumeroBis" class="small">N&uacute;m. bis</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.numeroBis" id="maquinariaNumeroBis" size="5" maxlength="5"></s:textfield></td>
            </tr>
            <tr>
            	<td align="left" nowrap="nowrap"><label for="maquinariaPortal" class="small">Portal</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.portal" id="maquinariaPortal" size="5" maxlength="5"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="maquinariaBloque" class="small">Bloque</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.bloque" id="maquinariaBloque" size="5" maxlength="5"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="maquinariaEscalera" class="small">Escalera</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.escalera" id="maquinariaEscalera" size="5" maxlength="5"></s:textfield></td>
            </tr>
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="maquinariaPlanta" class="small">Planta</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.planta" id="maquinariaPlanta" size="5" maxlength="100"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="maquinariaPuerta" class="small">Puerta</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.puerta" id="maquinariaPuerta" size="5" maxlength="100"></s:textfield></td>
				<td align="left" nowrap="nowrap"> <label for="maquinariaCodigoPostal" class="small">C&oacute;digo postal</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.codigoPostal" id="maquinariaCodigoPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap" ><label for="maquinariaLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
 			   		<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
 			   </td>
               <td colspan="3"><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.lugarUbicacion" id="maquinariaLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
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
              	<td align="left" nowrap="nowrap"><label for="maquinariaCodRegistroPropiedad" class="small" >Registro propiedad</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="C&oacute;digo registro de la propiedad"/>
              	</td>
                <td width="30%"><s:select name="tramiteRegRbmDto.propiedadDto.maquinaria.codRegistroPropiedad" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroPropiedad()" 
                          listKey="idRegistro"
                          listValue="nombre"
                          headerKey="" 
                          headerValue="Selecionar Tipo"
                          id="maquinariaCodRegistroPropiedad"/>
                </td>
            	<td align="left" nowrap="nowrap"><label for="maquinariaReferenciaCatastral" class="small" >Referencia catastral</label>
            		<img src="img/botonDameInfo.gif" alt="Info" title="Referencia catastral para identificar la finca de ubicaci&oacute;n"/>
            	</td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.referenciaCatastral" id="maquinariaReferenciaCatastral" size="18" maxlength="255"></s:textfield></td>
            </tr>
			<tr>
     		   <td align="left" nowrap="nowrap"><label for="maquinariaProvinciaDatReg" class="small">Provincia</label></td>
               <td>
                <s:select name="tramiteRegRbmDto.propiedadDto.maquinaria.codProvinciaDatReg" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="maquinariaDatRegProvincia"
 						  onchange="cargarListaMunicipios(this,'maquinariaDatRegSelectMunicipio','maquinariaDatRegHiddenMunicipio');"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="maquinariaDatRegMunicipio" class="small">Municipio</label></td>
               <td>       
				<s:select id="maquinariaDatRegSelectMunicipio"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPorProvincia(tramiteRegRbmDto.propiedadDto.maquinaria.codProvinciaDatReg)"
							onchange="javascript:seleccionMunicipio(this, 'maquinariaDatReg');"
							name="tramiteRegRbmDto.propiedadDto.maquinaria.codMunicipioDatReg"
							headerValue="Seleccione Municipio" listKey="idMunicipio"
							listValue="nombre" />
				<s:hidden id="maquinariaDatRegHiddenMunicipio"/>
				<s:hidden id="maquinariaDatRegHiddenMunicipality" /> 
               </td>
            </tr>
            <tr>
 			   <td align="left" nowrap="nowrap"><label for="maquinariaSeccionPropiedad" class="small">Secci&oacute;n registral</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.seccionPropiedad" id="maquinariaSeccionPropiedad" size="18" maxlength="255"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="maquinariaNumFinca" class="small">N&uacute;mero de finca</label></td>
               <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.numFinca" id="maquinariaNumFinca" size="18" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
            </tr>
             <tr>
             	<td align="left" nowrap="nowrap"><label for="maquinariaNumFincaBis" class="small">N&uacute;mero de finca bis</label></td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.numFincaBis" id="maquinariaNumFincaBis" size="18" maxlength="5"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="maquinariaNumSubFinca" class="small">Subn&uacute;mero de finca</label></td>
                <td><s:textfield name="tramiteRegRbmDto.propiedadDto.maquinaria.numSubFinca" id="maquinariaNumSubFinca" size="18" maxlength="5"></s:textfield></td>
            </tr>
		</table>
		
<script type="text/javascript">

	var viaMaquinaria = new BasicContentAssist(document.getElementById('maquinariaNombreVia'), [], null, true); 
	recargarNombreVias('maquinariaSelectProvinciaId', 'maquinariaSelectMunicipioId', 'maquinariaCodTipoVia','maquinariaNombreVia',viaMaquinaria);

</script>
