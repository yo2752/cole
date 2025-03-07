<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

	<s:hidden name="tramiteRegRbmDto.comprador.idInterviniente"/>


	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
					<span class="titulo">Datos comprador:</span>
			</td>
		</tr>
	</table>
	
<div id="comprador">	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
              <td align="left" nowrap="nowrap"><label for="compradorTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
              <td width="30%">  <s:select name="tramiteRegRbmDto.comprador.tipoPersona" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
						 listKey="valorXML"
						 headerKey="" 
						 headerValue="Selecionar Tipo"
						 listValue="nombreEnum" 
                         id="compradorTipoPersona"/>
			</td>
              <td align="left" nowrap="nowrap"><label for="compradorNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
               <td width="35%">
              	 	<table  cellSpacing="0">
              	 		<tr>
              	 			<td align="left" nowrap="nowrap">
		               			<s:textfield name="tramiteRegRbmDto.comprador.nif" id="compradorNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
		               		</td>
		               		
		               		<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteAjax('compradorNif', 'comprador')" />
								</s:if>
							</td>
						</tr>
               		</table>
               </td>
               
             </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="compradorNombre" >Nombre</label>
              	<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas" />
              </td>
              <td>
              <s:textfield name="tramiteRegRbmDto.comprador.persona.nombre" id="compradorNombre" size="18" maxlength="100"></s:textfield>
              </td>
              <td align="left" nowrap="nowrap"><label for="compradorApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.persona.apellido1RazonSocial" id="compradorApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="compradorApellido2" >2do apellido</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.persona.apellido2" id="compradorApellido2" size="18" maxlength="100"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="compradorCorreoElectronico" class="small">Email</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.persona.correoElectronico" id="compradorCorreoElectronico" size="18" onblur="validaEmail(this.value)" maxlength="100" ></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="compradorTelefonos" class="small" >N&uacute;mero tel&eacute;fono</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.persona.telefonos" id="compradorTelefonos" size="18" maxlength="20" ></s:textfield></td>
            </tr>
       </table>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci&oacute;n del comprador:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="compradorRegion"  >Regi&oacute;n</label>
     				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
     			</td>
              <td width="30%"><s:textfield name="tramiteRegRbmDto.comprador.direccion.regionExtranjera" id="compradorRegion" size="18"  ></s:textfield>	</td>
              <td align="left" nowrap="nowrap"><label for="compradorPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
              <td width="35%">
              <s:select name="tramiteRegRbmDto.comprador.direccion.pais" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
                         listKey="codigo" 
                         listValue="nombre" 
                         headerKey=""
                         headerValue="Selecionar País"
                         id="compradorPais"/>
              </td>
            </tr>
		<tr>
    		   <td align="left" nowrap="nowrap"><label for="compradorAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.comprador.direccion.idProvincia" 
                         list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
                         headerKey="" 
                         headerValue="Seleccionar"
                         listKey="idProvincia"
                         listValue="nombre"
                         id="compradorSelectProvinciaId"
						 onchange="cargarListaMunicipios(this,'compradorSelectMunicipioId','compradorHiddenMunicipioId');
						  inicializarTipoVia('compradorTipoVia','compradorNombreVia', viaComprador);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="compradorAddressMunicipio" class="small">Municipio<span class="naranja">*</span></label></td>
               <td>       
				<s:select id="compradorSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.comprador)"
					onchange="javascript:seleccionMunicipio(this, 'comprador');
					inicializarTipoVia('compradorTipoVia','compradorNombreVia', viaComprador);"
					name="tramiteRegRbmDto.comprador.direccion.idMunicipio"
					headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="compradorHiddenMunicipioId"/>
				<s:hidden name="tramiteRegRbmDto.comprador.direccion.nombreMunicipio" id="compradorHiddenMunicipality" />
               </td>
            </tr>
   		<tr>  
              <td align="left" nowrap="nowrap"><label for="compradorPueblo" class="small">Entidad local</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.direccion.pueblo" id="compradorPueblo" size="18" maxlength="70"></s:textfield></td>
              
              <td align="left" nowrap="nowrap"> <label for="compradorCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.direccion.codPostal" id="compradorCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="compradorTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.comprador.direccion.idTipoVia" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                         headerKey="" 
                         headerValue="Selecionar tipo"
                         listKey="key"
                         listValue="name"
                         id="compradorTipoVia"
                         onchange ="cargarListaNombresVia('compradorSelectProvinciaId', 'compradorSelectMunicipioId', this, 'compradorNombreVia', viaComprador);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="compradorNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
              <td> <s:textfield name="tramiteRegRbmDto.comprador.direccion.nombreVia" id="compradorNombreVia" size="35" maxlength="100"
               onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
             </tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
			<td align="left" nowrap="nowrap"><label for="compradorNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.direccion.numero" id="compradorNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="compradorBloque" class="small">Bloque</label></td>
              <td> <s:textfield name="tramiteRegRbmDto.comprador.direccion.bloque" id="compradorBloque" size="5" maxlength="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="compradorPlanta" class="small">Planta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.direccion.planta" id="compradorPlanta" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="compradorPuerta" class="small">Puerta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.direccion.puerta" id="compradorPuerta" size="5" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="compradorNumLocal" class="small">N&uacute;m. bis</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.direccion.numeroBis" id="compradorNumeroBis" size="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="compradorEscalera" class="small">Escalera</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.direccion.escalera" id="compradorEscalera" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
				<td align="left" nowrap="nowrap"><label for="compradorKm" class="small">Punto KM</label></td>
				<td><s:textfield name="tramiteRegRbmDto.comprador.direccion.km" id="compradorKm" size="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="compradorPortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.comprador.direccion.portal" id="compradorPortal" size="5"></s:textfield></td>
            </tr>
             <tr>
 			   <td align="left" nowrap="nowrap" ><label for="compradorLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
 			   		<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
 			   </td>
               <td colspan="3"><s:textfield name="tramiteRegRbmDto.comprador.direccion.lugarUbicacion" id="compradorLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
            </tr>
	</table>
 
 	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del registro Mercantil del comprador:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="compradorcrDataRegistryOfficeId" class="small">Registro mercantil</label></td>
              <td width="30%"><s:select name="tramiteRegRbmDto.comprador.datRegMercantil.codRegistroMercantil" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                         listKey="idRegistro"
                         listValue="nombre"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         id="compradorcrDataRegistryOfficeId"/></td>
              
     			<td align="left" nowrap="nowrap"><label for="compradorTomo" class="small">Tomo inscrip. </label></td>
              <td width="35%"><s:textfield name="tramiteRegRbmDto.comprador.datRegMercantil.tomo" id="compradorTomo" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr> 
              <td align="left" nowrap="nowrap"><label for="compradorLibro" class="small">Libro inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.datRegMercantil.libro" id="compradorLibro" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="compradorFolio" class="small">Folio inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.datRegMercantil.folio" id="compradorFolio" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr>  
              <td align="left" nowrap="nowrap"><label for="compradorHoja" class="small">Hoja inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.datRegMercantil.hoja" id="compradorHoja" size="5" maxlength="255"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="compradorNumInscripcion" class="small">N&uacute;mero inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.comprador.datRegMercantil.numInscripcion" id="compradorNumInscripcion" size="5" maxlength="255"></s:textfield></td>
            </tr>
	</table>
</div>

<script type="text/javascript">
$( document ).ready(function() {
	recargarComboMunicipios("comprador");
	});

var viaComprador = new BasicContentAssist(document.getElementById('compradorNombreVia'), [], null, true); 
recargarNombreVias('compradorSelectProvinciaId', 'compradorSelectMunicipioId', 'compradorTipoVia','compradorNombreVia',viaComprador);
</script>
