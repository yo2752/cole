<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

	<s:hidden name="tramiteRegRbmDto.avalista.idInterviniente"/>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Avalista:</span>
			</td>
		</tr>
	</table>

<div id="avalista">
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
              <td align="left" nowrap="nowrap"><label for="avalistaTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
              <td width="30%">  <s:select name="tramiteRegRbmDto.avalista.tipoPersona" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
						headerKey="" headerValue="Selecionar Tipo"
						listValue="nombreEnum" listKey="valorXML"
                        id="avalistaTipoPersona"/>
			</td>
              <td align="left" nowrap="nowrap"><label for="avalistaNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
              <td width="35%">
              	 	<table  cellSpacing="0">
              	 		<tr>
              	 			<td align="left" nowrap="nowrap">
		               			<s:textfield name="tramiteRegRbmDto.avalista.nif" id="avalistaNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
		               		</td>
		               		
		               		<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteAjax('avalistaNif', 'avalista')" />
								</s:if>
							</td>
						</tr>
               		</table>
               </td>
               
             </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="avalistaNombre"  >Nombre</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td>
              <s:textfield name="tramiteRegRbmDto.avalista.persona.nombre" id="avalistaNombre" size="18" maxlength="100"></s:textfield>
              </td>
              <td align="left" nowrap="nowrap"><label for="avalistaApellido1RazonSocial" >1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.persona.apellido1RazonSocial" id="avalistaApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="avalistaApellido2" >2do apellido</label>
              	<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.persona.apellido2" id="avalistaApellido2" size="18" maxlength="100"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="avalistaCorreoElectronico" class="small">Email</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.persona.correoElectronico" id="avalistaCorreoElectronico" size="18" onblur="validaEmail(this.value)" maxlength="100" ></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="avalistaTelefonos" class="small" >N&uacute;mero tel&eacute;fono</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.persona.telefonos" id="avalistaTelefonos" size="18" maxlength="20" ></s:textfield></td>
            </tr>
       </table>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci&oacute;n del Avalista:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
   				<td align="left" nowrap="nowrap"><label for="avalistaRegion" >Regi&oacute;n</label>
   					<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
   				</td>
              <td width="30%"><s:textfield name="tramiteRegRbmDto.avalista.direccion.regionExtranjera" id="avalistaRegion" size="18"  ></s:textfield>	</td>
              <td align="left" nowrap="nowrap"><label for="avalistaPais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
              <td width="35%">
              <s:select name="tramiteRegRbmDto.avalista.direccion.pais" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
                         listKey="codigo" 
                         listValue="nombre" 
                         headerKey=""
                         headerValue="Selecionar País"
                         id="avalistaPais"/>
              </td>
            </tr>
		<tr>
    		   <td align="left" nowrap="nowrap"><label for="avalistaAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.avalista.direccion.idProvincia" 
                         list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
                         headerKey="" 
                         headerValue="Seleccionar"
                         listKey="idProvincia"
                         listValue="nombre"
                         id="avalistaSelectProvinciaId"
						 onchange="cargarListaMunicipios(this,'avalistaSelectMunicipioId','avalistaHiddenMunicipioId');
						   inicializarTipoVia('avalistaTipoVia','avalistaNombreVia', viaAvalista);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="avalistaAddressMunicipio" class="small">Municipio<span class="naranja">*</span></label></td>
               <td>       
				<s:select id="avalistaSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.avalista)"
					onchange="javascript:seleccionMunicipio(this, 'avalista');
 					 inicializarTipoVia('avalistaTipoVia','avalistaNombreVia', viaAvalista);"
					name="tramiteRegRbmDto.avalista.direccion.idMunicipio"
					headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="avalistaHiddenMunicipioId"/>
				<s:hidden name="tramiteRegRbmDto.avalista.direccion.nombreMunicipio" id="avalistaHiddenMunicipality" />
               </td>
            </tr>
   		<tr>  
              <td align="left" nowrap="nowrap"><label for="avalistaPueblo" class="small">Entidad local</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.direccion.pueblo" id="avalistaPueblo" size="18" maxlength="70"></s:textfield></td>
              
              <td align="left" nowrap="nowrap"> <label for="avalistaCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.direccion.codPostal" id="avalistaCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="avalistaTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.avalista.direccion.idTipoVia" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                         headerKey="" 
                         headerValue="Selecionar tipo"
                         listKey="key"
                         listValue="name"
                         id="avalistaTipoVia"
                         onchange ="cargarListaNombresVia('avalistaSelectProvinciaId', 'avalistaSelectMunicipioId', this, 'avalistaNombreVia',viaAvalista);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="avalistaNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
              <td> <s:textfield name="tramiteRegRbmDto.avalista.direccion.nombreVia" id="avalistaNombreVia" size="35" maxlength="100" 
				onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
             </tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
			<td align="left" nowrap="nowrap"><label for="avalistaNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.direccion.numero" id="avalistaNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="avalistaBloque" class="small">Bloque</label></td>
              <td> <s:textfield name="tramiteRegRbmDto.avalista.direccion.bloque" id="avalistaBloque" size="5" maxlength="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="avalistaPlanta" class="small">Planta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.direccion.planta" id="avalistaPlanta" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="avalistaPuerta" class="small">Puerta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.direccion.puerta" id="avalistaPuerta" size="5" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="avalistaNumLocal" class="small">N&uacute;m. bis</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.direccion.numeroBis" id="avalistaNumeroBis" size="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="avalistaEscalera" class="small">Escalera</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.direccion.escalera" id="avalistaEscalera" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
				<td align="left" nowrap="nowrap"><label for="avalistaKm" class="small">Punto KM</label></td>
				<td><s:textfield name="tramiteRegRbmDto.avalista.direccion.km" id="avalistaKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="avalistaPortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.avalista.direccion.portal" id="avalistaPortal" size="5"></s:textfield></td>
             </tr>
             <tr>
				<td align="left" nowrap="nowrap" ><label for="avalistaLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
				</td>
		        <td colspan="3"><s:textfield name="tramiteRegRbmDto.avalista.direccion.lugarUbicacion" id="avalistaLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
		    </tr>
	</table>
 
 	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del registro Mercantil del Avalista:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="avalistacrDataRegistryOfficeId" class="small">Registro mercantil</label></td>
              <td width="30%"><s:select name="tramiteRegRbmDto.avalista.datRegMercantil.codRegistroMercantil" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                         listKey="idRegistro"
                         listValue="nombre"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         id="avalistacrDataRegistryOfficeId"/></td>
              
     			<td align="left" nowrap="nowrap"><label for="avalistaTomo" class="small">Tomo inscrip. </label></td>
              <td width="35%"><s:textfield name="tramiteRegRbmDto.avalista.datRegMercantil.tomo" id="avalistaTomo" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr> 
              <td align="left" nowrap="nowrap"><label for="avalistaLibro" class="small">Libro inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.datRegMercantil.libro" id="avalistaLibro" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="avalistaFolio" class="small">Folio inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.datRegMercantil.folio" id="avalistaFolio" size="5" maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr>  
              <td align="left" nowrap="nowrap"><label for="avalistaHoja" class="small">Hoja inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.datRegMercantil.hoja" id="avalistaHoja" size="5" maxlength="255"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="avalistaNumInscripcion" class="small">N&uacute;mero inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.avalista.datRegMercantil.numInscripcion" id="avalistaNumInscripcion" size="5" maxlength="255"></s:textfield></td>
            </tr>
	</table>
</div>	
<script type="text/javascript">

	var viaAvalista = new BasicContentAssist(document.getElementById('avalistaNombreVia'), [], null, true); 
	recargarNombreVias('avalistaSelectProvinciaId', 'avalistaSelectMunicipioId', 'avalistaTipoVia','avalistaNombreVia',viaAvalista);

</script>
