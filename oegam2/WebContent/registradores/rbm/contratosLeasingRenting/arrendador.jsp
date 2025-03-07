<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

	<s:hidden name="tramiteRegRbmDto.arrendador.idInterviniente"/>
		
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Arrendador:</span>
			</td>
		</tr>
	</table>
	
	
<div id="arrendador">
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendadorTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
              <td width="30%">  <s:select name="tramiteRegRbmDto.arrendador.tipoPersona" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         listValue="nombreEnum" listKey="valorXML"
                         id="arrendadorTipoPersona"/>
			</td>
              <td align="left" nowrap="nowrap"><label for="arrendadorNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
              
              <td width="35%">
              	 	<table  cellSpacing="0">
              	 		<tr>
              	 			<td align="left" nowrap="nowrap">
		               			<s:textfield name="tramiteRegRbmDto.arrendador.nif" id="arrendadorNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
		               		</td>
		               		
		               		<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteAjax('arrendadorNif', 'arrendador')" />
								</s:if>
							</td>
						</tr>
               		</table>
               </td>
               
             </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendadorNombre"  >Nombre</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td>
              <s:textfield name="tramiteRegRbmDto.arrendador.persona.nombre" id="arrendadorNombre" size="18" maxlength="100"></s:textfield>
              </td>
              <td align="left" nowrap="nowrap"><label for="arrendadorApellido1RazonSocial">1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.persona.apellido1RazonSocial" id="arrendadorApellido1RazonSocial" size="18"  maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendadorApellido2">2do apellido</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.persona.apellido2" id="arrendadorApellido2" size="18"  maxlength="100"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendadorCorreoElectronico" class="small">Email</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.persona.correoElectronico" id="arrendadorCorreoElectronico" size="18"  onblur="validaEmail(this.value)" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendadorTelefonos" class="small" >N&uacute;mero tel&eacute;fono</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.persona.telefonos" id="arrendadorTelefonos" size="18"  maxlength="20"></s:textfield></td>
            </tr>
       </table>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci&oacute;n del Arrendador:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="arrendadorRegion" >Regi&oacute;n</label>
     				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
     			</td>
              <td width="30%"><s:textfield name="tramiteRegRbmDto.arrendador.direccion.regionExtranjera" id="arrendadorRegion" size="18"  ></s:textfield>	</td>
              <td align="left" nowrap="nowrap"><label for="arrendadorPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
              <td width="35%">
              <s:select name="tramiteRegRbmDto.arrendador.direccion.pais" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
                         listKey="codigo" 
                         listValue="nombre" 
                         headerKey=""
                         headerValue="Selecionar País"
                         id="arrendadorPais"/>
              </td>
            </tr>
		<tr>
    		   <td align="left" nowrap="nowrap"><label for="arrendadorAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.arrendador.direccion.idProvincia" 
                         list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
                         headerKey="" 
                         headerValue="Seleccionar"
                         listKey="idProvincia"
                         listValue="nombre"
                         id="arrendadorSelectProvinciaId"
						  onchange="cargarListaMunicipios(this,'arrendadorSelectMunicipioId','arrendadorHiddenMunicipioId');
						   inicializarTipoVia('arrendadorTipoVia','arrendadorNombreVia', viaArrendador);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="arrendadorAddressMunicipio" class="small">Municipio<span class="naranja">*</span></label></td>
               <td>       
				<s:select id="arrendadorSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.arrendador)"
					onchange="javascript:seleccionMunicipio(this, 'arrendador');
					 inicializarTipoVia('arrendadorTipoVia','arrendadorNombreVia', viaArrendador);"
					name="tramiteRegRbmDto.arrendador.direccion.idMunicipio"
					headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="arrendadorHiddenMunicipioId"/>
				<s:hidden name="tramiteRegRbmDto.arrendador.direccion.nombreMunicipio" id="arrendadorHiddenMunicipality" />
               </td>
            </tr>
   		<tr>  
              <td align="left" nowrap="nowrap"><label for="arrendadorPueblo" class="small">Entidad local</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.pueblo" id="arrendadorPueblo" size="18"  maxlength="70"></s:textfield></td>
              
              <td align="left" nowrap="nowrap"> <label for="arrendadorCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.codPostal" id="arrendadorCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendadorTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.arrendador.direccion.idTipoVia" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                         headerKey="" 
                         headerValue="Selecionar tipo"
                         listKey="key"
                         listValue="name"
                         id="arrendadorTipoVia"
                         onchange ="cargarListaNombresVia('arrendadorSelectProvinciaId', 'arrendadorSelectMunicipioId', this, 'arrendadorNombreVia', viaArrendador);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="arrendadorNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
              <td> <s:textfield name="tramiteRegRbmDto.arrendador.direccion.nombreVia" id="arrendadorNombreVia" size="35" maxlength="100"
              	 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
             </tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
			<td align="left" nowrap="nowrap"><label for="arrendadorNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.numero" id="arrendadorNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendadorBloque" class="small">Bloque</label></td>
              <td> <s:textfield name="tramiteRegRbmDto.arrendador.direccion.bloque" id="arrendadorBloque" size="5" maxlength="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendadorPlanta" class="small">Planta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.planta" id="arrendadorPlanta" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="arrendadorPuerta" class="small">Puerta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.puerta" id="arrendadorPuerta" size="5" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="arrendadorNumeroBis" class="small">N&uacute;m. bis</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.numeroBis" id="arrendadorNumeroBis" size="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendadorEscalera" class="small">Escalera</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.escalera" id="arrendadorEscalera" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
				<td align="left" nowrap="nowrap"><label for="arrendadorKm" class="small">Punto KM</label></td>
				<td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.km" id="arrendadorKm" size="5" maxlength="5"  onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="arrendadorPortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.arrendador.direccion.portal" id="arrendadorPortal" size="5"></s:textfield></td>
             </tr>
             <tr>
				<td align="left" nowrap="nowrap" ><label for="arrendadorLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
				</td>
		        <td colspan="3"><s:textfield name="tramiteRegRbmDto.arrendador.direccion.lugarUbicacion" id="arrendadorLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
		    </tr>
	</table>
 
 	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del registro Mercantil del Arrendador:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="arrendadorcrDataRegistryOfficeId" class="small">Registro mercantil</label></td>
              <td width="30%"><s:select name="tramiteRegRbmDto.arrendador.datRegMercantil.codRegistroMercantil" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                         listKey="idRegistro"
                         listValue="nombre"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         id="arrendadorcrDataRegistryOfficeId"/></td>
              
     			<td align="left" nowrap="nowrap"><label for="arrendadorTomo" class="small">Tomo inscrip. </label></td>
              <td width="35%"><s:textfield name="tramiteRegRbmDto.arrendador.datRegMercantil.tomo" id="arrendadorTomo" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr> 
              <td align="left" nowrap="nowrap"><label for="arrendadorLibro" class="small">Libro inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.datRegMercantil.libro" id="arrendadorLibro" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendadorFolio" class="small">Folio inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.datRegMercantil.folio" id="arrendadorFolio" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr>  
              <td align="left" nowrap="nowrap"><label for="arrendadorHoja" class="small">Hoja inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.datRegMercantil.hoja" id="arrendadorHoja" size="5" maxlength="255"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="arrendadorNumInscripcion" class="small">N&uacute;mero inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.arrendador.datRegMercantil.numInscripcion" id="arrendadorNumInscripcion" size="5" maxlength="255"></s:textfield></td>
            </tr>
	</table>
</div>	

<script type="text/javascript">

var viaArrendador = new BasicContentAssist(document.getElementById('arrendadorNombreVia'), [], null, true); 
recargarNombreVias('arrendadorSelectProvinciaId', 'arrendadorSelectMunicipioId', 'arrendadorTipoVia','arrendadorNombreVia',viaArrendador);

</script>
