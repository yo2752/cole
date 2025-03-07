<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

	<s:hidden name="tramiteRegRbmDto.arrendatario.idInterviniente"/>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Arrendatario:</span>
			</td>
		</tr>
	</table>

<div id="arrendatario">
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendatarioTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
              <td width="30%">  <s:select name="tramiteRegRbmDto.arrendatario.tipoPersona" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         listValue="nombreEnum" listKey="valorXML"
                         id="arrendatarioTipoPersona"/>
			</td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
              
              <td width="35%">
              	 	<table  cellSpacing="0">
              	 		<tr>
              	 			<td align="left" nowrap="nowrap">
		               			<s:textfield name="tramiteRegRbmDto.arrendatario.nif" id="arrendatarioNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
		               		</td>
		               		
		               		<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteAjax('arrendatarioNif', 'arrendatario')" />
								</s:if>
							</td>
						</tr>
               		</table>
               </td>
               
             </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendatarioNombre" >Nombre</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td>
              <s:textfield name="tramiteRegRbmDto.arrendatario.persona.nombre" id="arrendatarioNombre" size="18" maxlength="100"></s:textfield>
              </td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
        			<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.persona.apellido1RazonSocial" id="arrendatarioApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendatarioApellido2" >2do apellido</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.persona.apellido2" id="arrendatarioApellido2" size="18" maxlength="100"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioCorreoElectronico" class="small">Email</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.persona.correoElectronico" id="arrendatarioCorreoElectronico" size="18" onblur="validaEmail(this.value)" maxlength="100" ></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendatarioTelefonos" class="small" >N&uacute;mero tel&eacute;fono</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.persona.telefonos" id="arrendatarioTelefonos" size="18" maxlength="20" ></s:textfield></td>
            </tr>
       </table>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci&oacute;n del Arrendatario:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="arrendatarioRegion" >Regi&oacute;n</label>
     				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
     			</td>
              <td width="30%"><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.regionExtranjera" id="arrendatarioRegion" size="18"  ></s:textfield>	</td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
              <td width="35%">
              <s:select name="tramiteRegRbmDto.arrendatario.direccion.pais" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
                         listKey="codigo" 
                         listValue="nombre" 
                         headerKey=""
                         headerValue="Selecionar País"
                         id="arrendatarioPais"/>
              </td>
            </tr>
		<tr>
    		   <td align="left" nowrap="nowrap"><label for="arrendatarioAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.arrendatario.direccion.idProvincia" 
                         list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
                         headerKey="" 
                         headerValue="Seleccionar"
                         listKey="idProvincia"
                         listValue="nombre"
                         id="arrendatarioSelectProvinciaId"
						 onchange="cargarListaMunicipios(this,'arrendatarioSelectMunicipioId','arrendatarioHiddenMunicipioId');
 							inicializarTipoVia('arrendatarioTipoVia','arrendatarioNombreVia', viaArrendatario);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioAddressMunicipio" class="small">Municipio<span class="naranja">*</span></label></td>
               <td>       
				<s:select id="arrendatarioSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.arrendatario)"
					onchange="javascript:seleccionMunicipio(this, 'arrendatario');
					 inicializarTipoVia('arrendatarioTipoVia','arrendatarioNombreVia', viaArrendatario);"
					name="tramiteRegRbmDto.arrendatario.direccion.idMunicipio"
					headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="arrendatarioHiddenMunicipioId"/>
				<s:hidden name="tramiteRegRbmDto.arrendatario.direccion.nombreMunicipio" id="arrendatarioHiddenMunicipality" />
               </td>
            </tr>
   		<tr>  
              <td align="left" nowrap="nowrap"><label for="arrendatarioPueblo" class="small">Entidad local</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.pueblo" id="arrendatarioPueblo" size="18" maxlength="70"></s:textfield></td>
              
              <td align="left" nowrap="nowrap"> <label for="arrendatarioCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.codPostal" id="arrendatarioCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendatarioTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.arrendatario.direccion.idTipoVia" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                         headerKey="" 
                         headerValue="Selecionar tipo"
                         listKey="key"
                         listValue="name"
                         id="arrendatarioTipoVia"
                         onchange ="cargarListaNombresVia('arrendatarioSelectProvinciaId', 'arrendatarioSelectMunicipioId', this, 'arrendatarioNombreVia', viaArrendatario);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
              <td> <s:textfield name="tramiteRegRbmDto.arrendatario.direccion.nombreVia" id="arrendatarioNombreVia" size="35" maxlength="100"
               onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
             </tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
			<td align="left" nowrap="nowrap"><label for="arrendatarioNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.numero" id="arrendatarioNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioBloque" class="small">Bloque</label></td>
              <td> <s:textfield name="tramiteRegRbmDto.arrendatario.direccion.bloque" id="arrendatarioBloque" size="5" maxlength="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioPlanta" class="small">Planta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.planta" id="arrendatarioPlanta" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendatarioPuerta" class="small">Puerta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.puerta" id="arrendatarioPuerta" size="5" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="arrendatarioNumeroBis" class="small">N&uacute;m. bis</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.numeroBis" id="arrendatarioNumeroBis" size="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioEscalera" class="small">Escalera</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.escalera" id="arrendatarioEscalera" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
				<td align="left" nowrap="nowrap"><label for="arrendatarioKm" class="small">Punto KM</label></td>
				<td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.km" id="arrendatarioKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="arrendatarioPortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.portal" id="arrendatarioPortal" size="5"></s:textfield></td>
             </tr>
             <tr>
				<td align="left" nowrap="nowrap" ><label for="arrendatarioLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
				</td>
		        <td colspan="3"><s:textfield name="tramiteRegRbmDto.arrendatario.direccion.lugarUbicacion" id="arrendatarioLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
		    </tr>
	</table>
 
 	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del registro Mercantil del Arrendatario:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="arrendatariocrDataRegistryOfficeId" class="small">Registro mercantil</label></td>
              <td width="30%"><s:select name="tramiteRegRbmDto.arrendatario.datRegMercantil.codRegistroMercantil" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                         listKey="idRegistro"
                         listValue="nombre"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         id="arrendatariocrDataRegistryOfficeId"/></td>
              
     			<td align="left" nowrap="nowrap"><label for="arrendatarioTomo" class="small">Tomo inscrip. </label></td>
              <td width="35%"><s:textfield name="tramiteRegRbmDto.arrendatario.datRegMercantil.tomo" id="arrendatarioTomo" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr> 
              <td align="left" nowrap="nowrap"><label for="arrendatarioLibro" class="small">Libro inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.datRegMercantil.libro" id="arrendatarioLibro" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioFolio" class="small">Folio inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.datRegMercantil.folio" id="arrendatarioFolio" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr>  
              <td align="left" nowrap="nowrap"><label for="arrendatarioHoja" class="small">Hoja inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.datRegMercantil.hoja" id="arrendatarioHoja" size="5" maxlength="255"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendatarioNumInscripcion" class="small">N&uacute;mero inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendatario.datRegMercantil.numInscripcion" id="arrendatarioNumInscripcion" size="5" maxlength="255"></s:textfield></td>
            </tr>
	</table>
</div>
	
<script type="text/javascript">

	var viaArrendatario = new BasicContentAssist(document.getElementById('arrendatarioNombreVia'), [], null, true); 
	recargarNombreVias('arrendatarioSelectProvinciaId', 'arrendatarioSelectMunicipioId', 'arrendatarioTipoVia','arrendatarioNombreVia',viaArrendatario);

</script>
