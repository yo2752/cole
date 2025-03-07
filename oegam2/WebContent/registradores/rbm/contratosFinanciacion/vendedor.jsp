<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

	<s:hidden name="tramiteRegRbmDto.vendedor.idInterviniente"/>


	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
					<span class="titulo">Datos vendedor:</span>
			</td>
		</tr>
	</table>
	
<div id="vendedor">	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
              <td align="left" nowrap="nowrap"><label for="vendedorTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
              <td width="30%">  <s:select name="tramiteRegRbmDto.vendedor.tipoPersona" 
                      	 list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
						 listKey="valorXML" headerKey="" headerValue="Selecionar Tipo"
						 listValue="nombreEnum"
                         id="vendedorTipoPersona"/>
			</td>
              <td align="left" nowrap="nowrap"><label for="vendedorNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
              
               <td width="35%">
              	 	<table  cellSpacing="0">
              	 		<tr>
              	 			<td align="left" nowrap="nowrap">
		               			<s:textfield name="tramiteRegRbmDto.vendedor.nif" id="vendedorNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
		               		</td>
		               		
		               		<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteAjax('vendedorNif', 'vendedor')" />
								</s:if>
							</td>
						</tr>
               		</table>
               </td>
               
             </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="vendedorNombre"  >Nombre</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td>
              <s:textfield name="tramiteRegRbmDto.vendedor.persona.nombre" id="vendedorNombre" size="18" maxlength="100"></s:textfield>
              </td>
              <td align="left" nowrap="nowrap"><label for="vendedorApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.persona.apellido1RazonSocial" id="vendedorApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="vendedorApellido2" >2do apellido</label>
              	<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.persona.apellido2" id="vendedorApellido2" size="18" maxlength="100"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="vendedorCorreoElectronico" class="small">Email</label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.persona.correoElectronico" id="vendedorCorreoElectronico" size="18" onblur="validaEmail(this.value)" maxlength="100" ></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="vendedorTelefonos" class="small" >N&uacute;mero tel&eacute;fono</label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.persona.telefonos" id="vendedorTelefonos" size="18" maxlength="20" ></s:textfield></td>
            </tr>
       </table>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci&oacute;n del vendedor:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="vendedorRegion" >Regi&oacute;n</label>
     				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
     			</td>
              <td width="30%"><s:textfield name="tramiteRegRbmDto.vendedor.direccion.regionExtranjera" id="vendedorRegion" size="18"  ></s:textfield>	</td>
              <td align="left" nowrap="nowrap"><label for="vendedorPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
              <td width="35%">
              <s:select name="tramiteRegRbmDto.vendedor.direccion.pais" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
                         listKey="codigo" 
                         listValue="nombre" 
                         headerKey=""
                         headerValue="Selecionar País"
                         id="vendedorPais"/>
              </td>
            </tr>
		<tr>
    		   <td align="left" nowrap="nowrap"><label for="vendedorAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.vendedor.direccion.idProvincia" 
                         list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
                         headerKey="" 
                         headerValue="Seleccionar"
                         listKey="idProvincia"
                         listValue="nombre"
                         id="vendedorSelectProvinciaId"
						 onchange="cargarListaMunicipios(this,'vendedorSelectMunicipioId','vendedorHiddenMunicipioId');
						  inicializarTipoVia('vendedorTipoVia','vendedorNombreVia', viaVendedor);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="vendedorAddressMunicipio" class="small">Municipio<span class="naranja">*</span></label></td>
               <td>       
				<s:select id="vendedorSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.vendedor)"
					onchange="javascript:seleccionMunicipio(this, 'vendedor');
					inicializarTipoVia('vendedorTipoVia','vendedorNombreVia', viaVendedor);"
					name="tramiteRegRbmDto.vendedor.direccion.idMunicipio"
					headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="vendedorHiddenMunicipioId"/>
				<s:hidden name="tramiteRegRbmDto.vendedor.direccion.nombreMunicipio" id="vendedorHiddenMunicipality" />
               </td>
            </tr>
   		<tr>  
              <td align="left" nowrap="nowrap"><label for="vendedorPueblo" class="small">Entidad local</label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.pueblo" id="vendedorPueblo" size="18" maxlength="70"></s:textfield></td>
              
              <td align="left" nowrap="nowrap"> <label for="vendedorCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.codPostal" id="vendedorCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="vendedorTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.vendedor.direccion.idTipoVia" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                         headerKey="" 
                         headerValue="Selecionar tipo"
                         listKey="key"
                         listValue="name"
                         id="vendedorTipoVia"
                         onchange ="cargarListaNombresVia('vendedorSelectProvinciaId', 'vendedorSelectMunicipioId', this, 'vendedorNombreVia', viaVendedor);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="vendedorNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
              <td> <s:textfield name="tramiteRegRbmDto.vendedor.direccion.nombreVia" id="vendedorNombreVia" size="35" maxlength="100"
                onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
             </tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
			<td align="left" nowrap="nowrap"><label for="vendedorNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.numero" id="vendedorNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="vendedorBloque" class="small">Bloque</label></td>
              <td> <s:textfield name="tramiteRegRbmDto.vendedor.direccion.bloque" id="vendedorBloque" size="5" maxlength="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="vendedorPlanta" class="small">Planta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.planta" id="vendedorPlanta" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="vendedorPuerta" class="small">Puerta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.puerta" id="vendedorPuerta" size="5" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="vendedorNumLocal" class="small">N&uacute;m. bis</label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.numeroBis" id="vendedorNumeroBis" size="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="vendedorEscalera" class="small">Escalera</label></td>
              <td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.escalera" id="vendedorEscalera" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
				<td align="left" nowrap="nowrap"><label for="vendedorKm" class="small">Punto KM</label></td>
				<td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.km" id="vendedorKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="vendedorPortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.vendedor.direccion.portal" id="vendedorPortal" size="5"></s:textfield></td>
             </tr>
             <tr>
				<td align="left" nowrap="nowrap" ><label for="vendedorLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
				</td>
		        <td colspan="3"><s:textfield name="tramiteRegRbmDto.vendedor.direccion.lugarUbicacion" id="vendedorLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
		    </tr>
	</table>
</div>	

<script type="text/javascript">

	var viaVendedor = new BasicContentAssist(document.getElementById('vendedorNombreVia'), [], null, true); 
	recargarNombreVias('vendedorSelectProvinciaId', 'vendedorSelectMunicipioId', 'vendedorTipoVia','vendedorNombreVia',viaVendedor);

</script>

