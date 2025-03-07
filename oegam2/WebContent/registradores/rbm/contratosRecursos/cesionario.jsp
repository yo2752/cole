<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

	<div id=cesionario>
		<s:hidden name="tramiteRegRbmDto.cesionario.idInterviniente"/>

	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Cesionario:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
               <td align="left" nowrap="nowrap"><label for="cesionarioTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
               <td width="30%">  <s:select name="tramiteRegRbmDto.cesionario.tipoPersona"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
							headerKey="" headerValue="Selecionar Tipo"
							listValue="nombreEnum" listKey="valorXML"
							id="cesionarioTipoPersona" />
			   </td>
               <td align="left" nowrap="nowrap"><label for="cesionarioNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
               <td width="35%">
              	 	<table  cellSpacing="0">
              	 		<tr>
              	 			<td align="left" nowrap="nowrap">
		               			<s:textfield name="tramiteRegRbmDto.cesionario.nif" id="cesionarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
		               		</td>
		               		
		               		<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteAjax('cesionarioNif', 'cesionario')" />
								</s:if>
							</td>
						</tr>
               		</table>
               </td>
              </tr>
            <tr>
               <td align="left" nowrap="nowrap"><label for="cesionarioNombre" >Nombre</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
               </td>
               <td>
               <s:textfield name="tramiteRegRbmDto.cesionario.persona.nombre" id="cesionarioNombre" size="18" maxlength="100"></s:textfield>
               </td>
               <td align="left" nowrap="nowrap"><label for="cesionarioApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
               </td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.persona.apellido1RazonSocial" id="cesionarioApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
             </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="cesionarioApellido2" >2do apellido</label>
               		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
               </td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.persona.apellido2" id="cesionarioApellido2" size="18" maxlength="100"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="cesionarioCorreoElectronico" class="small">Email</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.persona.correoElectronico" id="cesionarioCorreoElectronico" size="18" onblur="validaEmail(this.value)" maxlength="100" ></s:textfield></td>
             </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="cesionarioTelefonos" class="small" >N&uacute;mero tel&eacute;fono</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.persona.telefonos" id="cesionarioTelefonos" size="18"  maxlength="20"></s:textfield></td>
             </tr>
        </table>

	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Direcci&oacute;n del Cesionario:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
      			<td align="left" nowrap="nowrap"><label for="cesionarioPueblo"  >Regi&oacute;n</label>
      				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
      			</td>
               <td width="30%"><s:textfield name="tramiteRegRbmDto.cesionario.direccion.regionExtranjera" id="cesionarioPueblo" size="18" maxlength="255"></s:textfield>	</td>
               <td align="left" nowrap="nowrap"><label for="cesionPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
               <td width="35%">
               <s:select name="tramiteRegRbmDto.cesionario.direccion.pais" 
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()"
							listKey="codigo" listValue="nombre" headerKey=""
							headerValue="Selecionar Tipo" id="cesionarioPais" />
               </td>
             </tr>
			<tr>
     		   <td align="left" nowrap="nowrap"><label for="cesionarioAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
               <td>
                <s:select name="tramiteRegRbmDto.cesionario.direccion.idProvincia" 
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="" headerValue="Seleccionar" listKey="idProvincia"
							listValue="nombre" id="cesionarioSelectProvinciaId"
 						  onchange="cargarListaMunicipios(this,'cesionarioSelectMunicipioId','cesionarioHiddenMunicipioId');
 						  inicializarTipoVia('cesionarioTipoVia','cesionarioNombreVia', viaCesionario);"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="cesionarioAddressMunicipio" class="small">Municipio<span class="naranja">*</span></label></td>
               <td>       
				<s:select id="cesionarioSelectMunicipioId"
							list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.cesionario)"
							onchange="javascript:seleccionMunicipio(this, 'cesionario');
							 inicializarTipoVia('cesionarioTipoVia','cesionarioNombreVia', viaCesionario);"
							name="tramiteRegRbmDto.cesionario.direccion.idMunicipio"
							headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
							listValue="nombre" />
				<s:hidden id="cesionarioHiddenMunicipioId"/>
				<s:hidden name="tramiteRegRbmDto.cesionario.direccion.nombreMunicipio" id="cesionarioHiddenMunicipality" />
               </td>
             </tr>
    		<tr>  
               <td align="left" nowrap="nowrap"><label for="cesionarioNumLocal" class="small">Entidad local</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.pueblo" id="cesionarioNumLocal" size="18" maxlength="70"></s:textfield></td>
               
               <td align="left" nowrap="nowrap"> <label for="cesionarioCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.codPostal" id="cesionarioCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
             </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="cesionarioTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
               <td>
                <s:select name="tramiteRegRbmDto.cesionario.direccion.idTipoVia" 
                          id="cesionarioTipoVia"
						  list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()"
						  headerKey="" headerValue="Selecionar tipo" listKey="key"
						  listValue="name" 
						  onchange ="cargarListaNombresVia('cesionarioSelectProvinciaId', 'cesionarioSelectMunicipioId', this, 'cesionarioNombreVia', viaCesionario);"/>
               </td>
               <td align="left" nowrap="nowrap"><label for="cesionarioNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
               <td> <s:textfield name="tramiteRegRbmDto.cesionario.direccion.nombreVia" id="cesionarioNombreVia" size="35" maxlength="100" 
               onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
              </tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
              <tr>
				<td align="left" nowrap="nowrap"><label for="cesionarioNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.numero" id="cesionarioNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="cesionarioBloque" class="small">Bloque</label></td>
               <td> <s:textfield name="tramiteRegRbmDto.cesionario.direccion.bloque" id="cesionarioBloque" size="5" maxlength="5"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="cesionarioPlanta" class="small">Planta</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.planta" id="cesionarioPlanta" size="5" maxlength="100"></s:textfield></td>
             </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="cesionarioPuerta" class="small">Puerta</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.puerta" id="cesionarioPuerta" size="5" maxlength="100"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="cesionarioNumeroBis" class="small">N&uacute;m. bis</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.numeroBis" id="cesionarioNumeroBis" size="5"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="cesionarioEscalera" class="small">Escalera</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.escalera" id="cesionarioEscalera" size="5" maxlength="100"></s:textfield></td>
             </tr>
             <tr>
               <td align="left" nowrap="nowrap"><label for="cesionarioKm" class="small">Punto KM</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.km" id="cesionarioKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="cesionarioPortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.cesionario.direccion.portal" id="cesionarioPortal" size="5"></s:textfield></td>
              </tr>
              <tr>
				<td align="left" nowrap="nowrap" ><label for="cesionarioLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
				</td>
		        <td colspan="3"><s:textfield name="tramiteRegRbmDto.cesionario.direccion.lugarUbicacion" id="cesionarioLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
		    </tr>
		</table>
  
  	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Datos del registro Mercantil del Cesionario:</span>
				</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
      			<td align="left" nowrap="nowrap"><label for="cesionariocrDataRegistryOfficeId" class="small">Registro mercantil</label></td>
               <td width="30%"><s:select name="tramiteRegRbmDto.cesionario.datRegMercantil.codRegistroMercantil" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                          listKey="idRegistro"
                          listValue="nombre"
                          headerKey="" 
                          headerValue="Selecionar Tipo"
                          id="cesionariocrDataRegistryOfficeId"/></td>
               
      			<td align="left" nowrap="nowrap"><label for="cesionarioTomo" class="small">Tomo inscrip.</label></td>
               <td width="35%"><s:textfield name="tramiteRegRbmDto.cesionario.datRegMercantil.tomo" id="cesionarioTomo" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
      		</tr>
             <tr> 
               <td align="left" nowrap="nowrap"><label for="cesionarioLibro" class="small">Libro inscrip.</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.datRegMercantil.libro" id="cesionarioLibro" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="cesionarioFolio" class="small">Folio inscrip.</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.datRegMercantil.folio" id="cesionarioFolio" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
      		</tr>
             <tr>  
               <td align="left" nowrap="nowrap"><label for="cesionarioHoja" class="small">Hoja inscrip.</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.datRegMercantil.hoja" id="cesionarioHoja" size="5" maxlength="255"></s:textfield></td>
               <td align="left" nowrap="nowrap"><label for="cesionarioNumInscripcion" class="small">N&uacute;mero inscrip.</label></td>
               <td><s:textfield name="tramiteRegRbmDto.cesionario.datRegMercantil.numInscripcion" id="cesionarioNumInscripcion" size="5" maxlength="255"></s:textfield></td>
             </tr>
		</table>
</div>	
		
<script type="text/javascript">

var viaCesionario = new BasicContentAssist(document.getElementById('cesionarioNombreVia'), [], null, true); 
recargarNombreVias('cesionarioSelectProvinciaId', 'cesionarioSelectMunicipioId', 'cesionarioTipoVia','cesionarioNombreVia',viaCesionario);

</script>
