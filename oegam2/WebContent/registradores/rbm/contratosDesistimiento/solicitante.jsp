<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>

	<s:hidden name="tramiteRegRbmDto.solicitante.idInterviniente"/>
		
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Solicitante:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteTipoPersona" class="small">Tipo de persona<span class="naranja">*</span></label></td>
              <td width="30%">  <s:select name="tramiteRegRbmDto.solicitante.tipoPersona" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         listValue="nombreEnum" listKey="valorXML"
                         id="solicitanteTipoPersona"/>
			</td>
              <td align="left" nowrap="nowrap"><label for="solicitanteNif" class="small">Nif/Cif.<span class="naranja">*</span></label></td>
              
              <td width="35%">
              	 	<table  cellSpacing="0">
              	 		<tr>
              	 			<td align="left" nowrap="nowrap">
		               			<s:textfield name="tramiteRegRbmDto.solicitante.nif" id="solicitanteNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
		               		</td>
		               		
		               		<td align="left" nowrap="nowrap">
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
			               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
										onclick="javascript:buscarIntervinienteAjax('solicitanteNif', 'solicitante')" />
								</s:if>
							</td>
						</tr>
               		</table>
               </td>
               
             </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteNombre"  >Nombre</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td>
              <s:textfield name="tramiteRegRbmDto.solicitante.persona.nombre" id="solicitanteNombre" size="18" maxlength="100"></s:textfield>
              </td>
              <td align="left" nowrap="nowrap"><label for="solicitanteApellido1RazonSocial">1er apellido / Raz&oacute;n social<span class="naranja">*</span></label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.persona.apellido1RazonSocial" id="solicitanteApellido1RazonSocial" size="18"  maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteApellido2">2do apellido</label>
              		<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
              </td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.persona.apellido2" id="solicitanteApellido2" size="18"  maxlength="100"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitanteCorreoElectronico" class="small">Email</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.persona.correoElectronico" id="solicitanteCorreoElectronico" size="18"  onblur="validaEmail(this.value)" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteTelefonos" class="small" >N&uacute;mero tel&eacute;fono</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.persona.telefonos" id="solicitanteTelefonos" size="18"  maxlength="20"></s:textfield></td>
            </tr>
       </table>

   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci&oacute;n del Solicitante:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="solicitanteRegion" >Regi&oacute;n</label>
     				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
     			</td>
              <td width="30%"><s:textfield name="tramiteRegRbmDto.solicitante.direccion.regionExtranjera" id="solicitanteRegion" size="18"  ></s:textfield>	</td>
              <td align="left" nowrap="nowrap"><label for="solicitantePais" class="small">Pa&iacute;s<span class="naranja">*</span></label></td>
              <td width="35%">
              <s:select name="tramiteRegRbmDto.solicitante.direccion.pais" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
                         listKey="codigo" 
                         listValue="nombre" 
                         headerKey=""
                         headerValue="Selecionar País"
                         id="solicitantePais"/>
              </td>
            </tr>
		<tr>
    		   <td align="left" nowrap="nowrap"><label for="solicitanteAddressProvinciaId" class="small">Provincia<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.solicitante.direccion.idProvincia" 
                         list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
                         headerKey="" 
                         headerValue="Seleccionar"
                         listKey="idProvincia"
                         listValue="nombre"
                         id="solicitanteSelectProvinciaId"
						  onchange="cargarListaMunicipios(this,'solicitanteSelectMunicipioId','solicitanteHiddenMunicipioId');
						   inicializarTipoVia('solicitanteTipoVia','solicitanteNombreVia', viaSolicitante);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="solicitanteAddressMunicipio" class="small">Municipio<span class="naranja">*</span></label></td>
               <td>       
				<s:select id="solicitanteSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.solicitante)"
					onchange="javascript:seleccionMunicipio(this, 'solicitante');
					 inicializarTipoVia('solicitanteTipoVia','solicitanteNombreVia', viaSolicitante);"
					name="tramiteRegRbmDto.solicitante.direccion.idMunicipio"
					headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="solicitanteHiddenMunicipioId"/>
				<s:hidden name="tramiteRegRbmDto.solicitante.direccion.nombreMunicipio" id="solicitanteHiddenMunicipality" />
               </td>
            </tr>
   		<tr>  
              <td align="left" nowrap="nowrap"><label for="solicitantePueblo" class="small">Entidad local</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.pueblo" id="solicitantePueblo" size="18"  maxlength="70"></s:textfield></td>
              
              <td align="left" nowrap="nowrap"> <label for="solicitanteCodPostal" class="small">C&oacute;digo postal<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.codPostal" id="solicitanteCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitanteTipoVia" class="small">Tipo de v&iacute;a<span class="naranja">*</span></label></td>
              <td>
               <s:select name="tramiteRegRbmDto.solicitante.direccion.idTipoVia" 
                         list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                         headerKey="" 
                         headerValue="Selecionar tipo"
                         listKey="key"
                         listValue="name"
                         id="solicitanteTipoVia"
                         onchange ="cargarListaNombresVia('solicitanteSelectProvinciaId', 'solicitanteSelectMunicipioId', this, 'solicitanteNombreVia', viaSolicitante);"/>
              </td>
              <td align="left" nowrap="nowrap"><label for="solicitanteNombreVia" class="small">Nombre calle<span class="naranja">*</span></label></td>
              <td> <s:textfield name="tramiteRegRbmDto.solicitante.direccion.nombreVia" id="solicitanteNombreVia" size="35" maxlength="100"
              	 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
             </tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
             <tr>
			<td align="left" nowrap="nowrap"><label for="solicitanteNumero" class="small">N&uacute;m. calle<span class="naranja">*</span></label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.numero" id="solicitanteNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitanteBloque" class="small">Bloque</label></td>
              <td> <s:textfield name="tramiteRegRbmDto.solicitante.direccion.bloque" id="solicitanteBloque" size="5" maxlength="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitantePlanta" class="small">Planta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.planta" id="solicitantePlanta" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
              <td align="left" nowrap="nowrap"><label for="solicitantePuerta" class="small">Puerta</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.puerta" id="solicitantePuerta" size="5" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="solicitanteNumLocal" class="small">N&uacute;m. bis</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.numeroBis" id="solicitanteNumeroBis" size="5"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitanteEscalera" class="small">Escalera</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.escalera" id="solicitanteEscalera" size="5" maxlength="100"></s:textfield></td>
            </tr>
            <tr>
				<td align="left" nowrap="nowrap"><label for="solicitanteKm" class="small">Punto KM</label></td>
				<td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.km" id="solicitanteKm" size="5" maxlength="5"  onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="solicitantePortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.solicitante.direccion.portal" id="solicitantePortal" size="5"></s:textfield></td>
             </tr>
             <tr>
				<td align="left" nowrap="nowrap" ><label for="solicitanteLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
				</td>
		        <td colspan="3"><s:textfield name="tramiteRegRbmDto.solicitante.direccion.lugarUbicacion" id="solicitanteLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
		    </tr>
	</table>
 
 	   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del registro Mercantil del Solicitante:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
     			<td align="left" nowrap="nowrap"><label for="solicitantecrDataRegistryOfficeId" class="small">Registro mercantil</label></td>
              <td width="30%"><s:select name="tramiteRegRbmDto.solicitante.datRegMercantil.codRegistroMercantil" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListRegistroMercantil()" 
                         listKey="idRegistro"
                         listValue="nombre"
                         headerKey="" 
                         headerValue="Selecionar Tipo"
                         id="solicitantecrDataRegistryOfficeId"/></td>
              
     			<td align="left" nowrap="nowrap"><label for="solicitanteTomo" class="small">Tomo inscrip. </label></td>
              <td width="35%"><s:textfield name="tramiteRegRbmDto.solicitante.datRegMercantil.tomo" id="solicitanteTomo" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr> 
              <td align="left" nowrap="nowrap"><label for="solicitanteLibro" class="small">Libro inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.datRegMercantil.libro" id="solicitanteLibro" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitanteFolio" class="small">Folio inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.datRegMercantil.folio" id="solicitanteFolio" size="5"  maxlength="19" onkeypress="return soloNumeroDecimal(event, this, '19', '0')"></s:textfield></td>
     		</tr>
            <tr>  
              <td align="left" nowrap="nowrap"><label for="solicitanteHoja" class="small">Hoja inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.datRegMercantil.hoja" id="solicitanteHoja" size="5" maxlength="255"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="solicitanteNumInscripcion" class="small">N&uacute;mero inscrip.</label></td>
              <td><s:textfield name="tramiteRegRbmDto.solicitante.datRegMercantil.numInscripcion" id="solicitanteNumInscripcion" size="5" maxlength="255"></s:textfield></td>
            </tr>
	</table>
	
	
<script type="text/javascript">

var viaSolicitante = new BasicContentAssist(document.getElementById('solicitanteNombreVia'), [], null, true); 
recargarNombreVias('solicitanteSelectProvinciaId', 'solicitanteSelectMunicipioId', 'solicitanteTipoVia','solicitanteNombreVia',viaSolicitante);

</script>
