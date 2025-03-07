<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<s:hidden name="tramiteRegRbmDto.propiedadDto.idPropiedad" id="idPropiedad"/>
<s:hidden name="tramiteRegRbmDto.propiedadDto.idTramiteRegistro" />
<s:hidden name="tramiteRegRbmDto.proveedor.idInterviniente" />
<s:hidden name="tramiteRegRbmDto.propiedadDto.fecCreacion" />		

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Bienes:</span>
		</td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
        <tr>
           <td align="left" nowrap="nowrap">
           <label for="propiedadDtoCategoria">Categor&iacute;a<span class="naranja">*</span></label>
           </td>
           <td>  <s:select name="tramiteRegRbmDto.propiedadDto.categoria" 
                      list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCategory()" 
                      listKey="key"
                      headerKey="" 
                      headerValue="Selecionar categoría"
                      id="propiedadDtoCategoria"
                      onchange="mostrarDivCategoriaBien();"/>
			</td>
			
			<td align="left" nowrap="nowrap"><label for="numeroRegistral" title="N&uacute;mero inscripci&oacute;n registral del bien">N&uacute;mero de inscripci&oacute;n registral</label></td>
			<td><s:textfield name="tramiteRegRbmDto.propiedadDto.numeroRegistral" id="numeroRegistral" size="16" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '14', '0')"></s:textfield></td>
			
		</tr>
			
		 <s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_7.getValorEnum() 
       		 or tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_9.getValorEnum()">
            <tr>
           		<td align="left" nowrap="nowrap"> <label for="propiedadDtoUnidadCuenta" >Valor contado<span class="naranja">*</span></label>
           			<img src="img/botonDameInfo.gif" alt="Info" title="Valor al contado del bien"/>
           		</td>
           		<td><s:textfield name="tramiteRegRbmDto.propiedadDto.valor" id="propiedadDtoValor" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
        	</tr>
        </s:if>
         
         
    	 <s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_8.getValorEnum() ">
	         <tr>
	           <td align="left" nowrap="nowrap"> <label for="propiedadDtoImpBase" >Importe base<span class="naranja">*</span></label>
	           		<img src="img/botonDameInfo.gif" alt="Info" title="Importe base sin aplicar impuesto"/>
	           </td>
	           <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.impBase" id="propiedadDtoImpBase" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
	           <td align="left" nowrap="nowrap"> <label for="propiedadDtoImpImpuesto">Importe impuesto<span class="naranja">*</span></label></td>
	           <td width="35%"><s:textfield name="tramiteRegRbmDto.propiedadDto.impImpuesto" id="propiedadDtoImpImpuesto" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
	         </tr>
	         <tr>
	           <td align="left" nowrap="nowrap"> <label for="propiedadDtoImpTotal" >Suma importes<span class="naranja">*</span></label>
	         		<img src="img/botonDameInfo.gif" alt="Info" title="Suma del Importe base + Importe impuesto"/>
	           </td>
	           <td><s:textfield name="tramiteRegRbmDto.propiedadDto.impTotal" id="propiedadDtoImpTotal" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
	           
	           <td align="left" nowrap="nowrap"><label for="propiedadDtoImpuestoMatr" class="small">Impuesto matriculaci&oacute;n</label></td>
			   <td><s:textfield name="tramiteRegRbmDto.propiedadDto.impuestoMatr" id="propiedadDtoImpuestoMatr" size="10" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')" ></s:textfield></td>
	         </tr>
         </s:if>
    </table>
    
    <div id= "divVehiculo" style="display:none;">
    	<%@include file="vehiculo.jsp"%>
    </div>
    <div id= "divMaquinaria" style="display:none;">
    	<%@include file="maquinaria.jsp"%>
    </div>
    <div id= "divEstablecimiento" style="display:none;">
    	<%@include file="establecimiento.jsp"%>
    </div>
        <div id= "divBuque" style="display:none;">
    	<%@include file="buque.jsp"%>
    </div>
    <div id= "divAeronave" style="display:none;">
    	<%@include file="aeronave.jsp"%>
    </div>
    <div id= "divPropiedadIndustrial" style="display:none;">
    	<%@include file="propiedadIndustrial.jsp"%>
    </div>
    <div id= "divPropiedadIntelectual" style="display:none;">
    	<%@include file="propiedadIntelectual.jsp"%>
    </div>
     <div id= "divOtrosBienes" style="display:none;">
    	<%@include file="otrosBienes.jsp"%>
    </div>

<!-- El proveedor solo se muestra para los contratos Leasing -->
<s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_8.getValorEnum() ">

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Proveedor:</span>
			</td>
		</tr>
	</table>
	
	
	<div id="proveedor">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	           <tr>
	             <td align="left" nowrap="nowrap"><label for="proveedorTipoPersona" class="small">Tipo de persona</label></td>
	             <td width="30%">  <s:select name="tramiteRegRbmDto.proveedor.tipoPersona" 
	                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCorpmePersonTYpe()"
	                        listKey="valorXML"
	                        listValue="nombreEnum"
	                        headerKey="" 
	                        headerValue="Selecionar Tipo"
	                        id="proveedorTipoPersona"/>
				</td>
	             <td align="left" nowrap="nowrap"><label for="proveedorNif" class="small">Nif/Cif.</label></td>
	             
	             <td width="35%">
	              	 	<table  cellSpacing="0">
	              	 		<tr>
	              	 			<td align="left" nowrap="nowrap">
			               			<s:textfield name="tramiteRegRbmDto.proveedor.nif" id="proveedorNif" size="18" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"></s:textfield>
			               		</td>
			               		
			               		<td align="left" nowrap="nowrap">
									<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
				               			<input type="button" id="idBotonBuscarNif" class="boton-persona" style="background-image: url(img/mostrar.gif); height: 20px; width: 20px;"
											onclick="javascript:buscarIntervinienteAjax('proveedorNif', 'proveedor')" />
									</s:if>
								</td>
							</tr>
	               		</table>
	               </td>
	            </tr>
	           <tr>
	             <td align="left" nowrap="nowrap"><label for="proveedorNombre" >Nombre</label>
	             	<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
	             </td>
	             <td>
	             <s:textfield name="tramiteRegRbmDto.proveedor.persona.nombre" id="proveedorNombre" size="18" maxlength="100"></s:textfield>
	             </td>
	             <td align="left" nowrap="nowrap"><label for="proveedorApellido1RazonSocial" >1er apellido</label>
	             	<img src="img/botonDameInfo.gif" alt="Info" title="Apellido si es persona f&iacute;sica y denominaci&oacute;n social o raz&oacute;n social si es jur&iacute;dica"/>
	             </td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.persona.apellido1RazonSocial" id="proveedorApellido1RazonSocial" size="18" maxlength="100"></s:textfield></td>
	           </tr>
	           <tr>
	             <td align="left" nowrap="nowrap"><label for="proveedorApellido2" >2do apellido</label>
	             	<img src="img/botonDameInfo.gif" alt="Info" title="S&oacute;lo para personas f&iacute;sicas"/>
	             </td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.persona.apellido2" id="proveedorApellido2" size="18" maxlength="100"></s:textfield></td>
	             <td align="left" nowrap="nowrap"><label for="proveedorCorreoElectronico" class="small">Email</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.persona.correoElectronico" id="proveedorCorreoElectronico" size="18" onblur="validaEmail(this.value)" maxlength="100"></s:textfield></td>
	           </tr>
	           <tr>
	             <td align="left" nowrap="nowrap"><label for="proveedorTelefonos" class="small" >N&uacute;mero tel&eacute;fono</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.persona.telefonos" id="proveedorTelefonos" size="18" maxlength="20"></s:textfield></td>
	           </tr>
	      </table>
	
	  	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Direcci&oacute;n del proveedor:</span>
				</td>
			</tr>
		</table>
	
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	          <tr>
	    			<td align="left" nowrap="nowrap"><label for="proveedorRegion"  >Regi&oacute;n</label>
	    				<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a"/>
	    			</td>
	             <td width="30%"><s:textfield name="tramiteRegRbmDto.proveedor.direccion.regionExtranjera" id="proveedorRegion" size="18"  ></s:textfield>	</td>
	             <td align="left" nowrap="nowrap"><label for="proveedorPais" class="small">Pa&iacute;s</label></td>
	             <td width="35%">
	             <s:select name="tramiteRegRbmDto.proveedor.direccion.pais" 
	                       list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
	                       listKey="codigo" 
	                         listValue="nombre" 
	                        headerKey="" 
	                        headerValue="Selecionar País"
	                        id="proveedorPais"/>
	             </td>
	           </tr>
			<tr>
	   		   <td align="left" nowrap="nowrap"><label for="proveedorAddressProvinciaId" class="small">Provincia</label></td>
	             <td>
	              <s:select name="tramiteRegRbmDto.proveedor.direccion.idProvincia" 
	                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getProvinciasBienes()" 
	                        headerKey="" 
	                        headerValue="Seleccionar"
	                        listKey="idProvincia"
	                        listValue="nombre"
	                        id="proveedorSelectProvinciaId"
						    onchange="cargarListaMunicipios(this,'proveedorSelectMunicipioId','proveedorHiddenMunicipioId');
						     inicializarTipoVia('proveedorTipoVia','proveedorNombreVia', viaProveedor);"/>
	             </td>
	             <td align="left" nowrap="nowrap"><label for="proveedorAddressMunicipio" class="small">Municipio</label></td>
	             <td>       
					<s:select id="proveedorSelectMunicipioId"
						list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipios(tramiteRegRbmDto.proveedor)"
						onchange="javascript:seleccionMunicipio(this, 'proveedor');
						 inicializarTipoVia('proveedorTipoVia','proveedorNombreVia', viaProveedor);"
						name="tramiteRegRbmDto.proveedor.direccion.idMunicipio"
						headerValue="Seleccione Municipio" headerKey="" listKey="idMunicipio"
						listValue="nombre" />
					<s:hidden id="proveedorHiddenMunicipioId"/>
					<s:hidden name="tramiteRegRbmDto.proveedor.direccion.nombreMunicipio" id="proveedorHiddenMunicipality" />
	               </td>
	           </tr>
	  		<tr>  
	             <td align="left" nowrap="nowrap"><label for="proveedorPueblo" class="small">Entidad local</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.pueblo" id="proveedorPueblo" size="18" maxlength="70"></s:textfield></td>
	             
	             <td align="left" nowrap="nowrap"> <label for="proveedorCodPostal" class="small">C&oacute;digo postal</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.codPostal" id="proveedorCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
	           </tr>
	           <tr>
	             <td align="left" nowrap="nowrap"><label for="proveedorTipoVia" class="small">Tipo de v&iacute;a</label></td>
	             <td>
	              <s:select name="tramiteRegRbmDto.proveedor.direccion.idTipoVia" 
	                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
	                        headerKey="" 
	                        headerValue="Selecionar tipo"
	                        listKey="key"
	                        listValue="name"
	                        id="proveedorTipoVia"
	                        onchange ="cargarListaNombresVia('proveedorSelectProvinciaId', 'proveedorSelectMunicipioId', this, 'proveedorNombreVia', viaProveedor);"/>
	             </td>
	             <td align="left" nowrap="nowrap"><label for="proveedorNombreVia" class="small">Nombre calle</label></td>
	             <td> <s:textfield name="tramiteRegRbmDto.proveedor.direccion.nombreVia" id="proveedorNombreVia" size="35" maxlength="100"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
	            </tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	            <tr>
			<td align="left" nowrap="nowrap"><label for="proveedorNumero" class="small">N&uacute;m. calle</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.numero" id="proveedorNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
	             <td align="left" nowrap="nowrap"><label for="proveedorBloque" class="small">Bloque</label></td>
	             <td> <s:textfield name="tramiteRegRbmDto.proveedor.direccion.bloque" id="proveedorBloque" size="5" maxlength="5"></s:textfield></td>
	             <td align="left" nowrap="nowrap"><label for="proveedorPlanta" class="small">Planta</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.planta" id="proveedorPlanta" size="5" maxlength="100"></s:textfield></td>
	           </tr>
	           <tr>
	             <td align="left" nowrap="nowrap"><label for="proveedorPuerta" class="small">Puerta</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.puerta" id="proveedorPuerta" size="5" maxlength="100"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="proveedorNumeroBis" class="small">N&uacute;m. bis</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.numeroBis" id="proveedorNumeroBis" size="5"></s:textfield></td>
	             <td align="left" nowrap="nowrap"><label for="proveedorEscalera" class="small">Escalera</label></td>
	             <td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.escalera" id="proveedorEscalera" size="5" maxlength="100"></s:textfield></td>
	           </tr>
	           <tr>
					<td align="left" nowrap="nowrap"><label for="proveedorKm" class="small">Punto KM</label></td>
					<td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.km" id="proveedorKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
					<td align="left" nowrap="nowrap"><label for="proveedorPortal" class="small">Portal</label></td>
					<td><s:textfield name="tramiteRegRbmDto.proveedor.direccion.portal" id="proveedorPortal" size="5"></s:textfield></td>
	            </tr>
	            
	            <tr>
					<td align="left" nowrap="nowrap" ><label for="proveedorLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
						<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
					</td>
			        <td colspan="3"><s:textfield name="tramiteRegRbmDto.proveedor.direccion.lugarUbicacion" id="proveedorLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
			    </tr>
		</table>
	</div>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lugar de utilizaci&oacute;n:</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
          <tr>
    		 <td align="left" nowrap="nowrap"><label for="lugarUtilizacRegion" >Regi&oacute;n</label>
    		 	<img src="img/botonDameInfo.gif" alt="Info" title="Estado, Regi&oacute;n o Provincia si el pa&iacute;s no es Espa&ntilde;a" />
    		 </td>
             <td width="30%"><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.regionExtranjera" id="lugarUtilizacRegion" size="18"  ></s:textfield>	</td>
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacPais" class="small">Pa&iacute;s</label></td>
             <td width="35%">
             <s:select name="tramiteRegRbmDto.propiedadDto.direccion.pais" 
                       list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPaisRegistro()" 
                       listKey="codigo" 
                         listValue="nombre" 
                        headerKey="" 
                        headerValue="Selecionar País"
                        id="lugarUtilizacCorpmeCountryId"/>
             </td>
           </tr>
		<tr>
   		   <td align="left" nowrap="nowrap"><label for="lugarUtilizacAddressProvinciaId" class="small">Provincia</label></td>
             <td>
              <s:select name="tramiteRegRbmDto.propiedadDto.direccion.idProvincia" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getProvinciasBienes()" 
                        headerKey="" 
                        headerValue="Seleccionar"
                        listKey="idProvincia"
                        listValue="nombre"
                        id="lugarUtilizacSelectProvinciaId"
					    onchange="cargarListaMunicipios(this,'lugarUtilizacSelectMunicipioId','lugarUtilizacHiddenMunicipioId');
					     inicializarTipoVia('lugarUtilizacAddressCorpmeStreetTypeId','lugarUtilizacNombreVia', viaLugarUtilizac);"/>
             </td>
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacAddressMunicipio" class="small">Municipio</label></td>
             <td>       
				<s:select id="lugarUtilizacSelectMunicipioId"
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosPropiedad(tramiteRegRbmDto.propiedadDto)"
					onchange="javascript:seleccionMunicipio(this, 'lugarUtilizac');
					inicializarTipoVia('lugarUtilizacAddressCorpmeStreetTypeId','lugarUtilizacNombreVia', viaLugarUtilizac);"
					name="tramiteRegRbmDto.propiedadDto.direccion.idMunicipio"
					headerValue="Seleccione Municipio" listKey="idMunicipio"
					listValue="nombre" />
				<s:hidden id="lugarUtilizacHiddenMunicipioId"/>
				<s:hidden id="lugarUtilizacHiddenMunicipality" />
               </td>
           </tr>
  		<tr>  
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacPueblo" class="small">Entidad local</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.pueblo" id="lugarUtilizacPueblo" size="18" maxlength="70"></s:textfield></td>
             
             <td align="left" nowrap="nowrap"> <label for="lugarUtilizacCodPostal" class="small">C&oacute;digo postal</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.codPostal" id="lugarUtilizacCodPostal" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>   
           </tr>
           <tr>
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacAddressCorpmeStreetTypeId" class="small">Tipo de v&iacute;a</label></td>
             <td>
              <s:select name="tramiteRegRbmDto.propiedadDto.direccion.idTipoVia" 
                        list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()" 
                        headerKey="" 
                        headerValue="Selecionar tipo"
                        listKey="key"
                        listValue="name"
                        id="lugarUtilizacAddressCorpmeStreetTypeId"
                        onchange ="cargarListaNombresVia('lugarUtilizacSelectProvinciaId', 'lugarUtilizacSelectMunicipioId', this, 'lugarUtilizacNombreVia', viaLugarUtilizac);"/>
             </td>
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacNombreVia" class="small">Nombre calle</label></td>
             <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.nombreVia" id="lugarUtilizacNombreVia" size="35" maxlength="100" 
                 onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssStyle="width:200px;position:relative;"></s:textfield></td>
            </tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
            <tr>
		<td align="left" nowrap="nowrap"><label for="lugarUtilizacNumero" class="small">N&uacute;m. calle</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.numero" id="lugarUtilizacNumero" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacBloque" class="small">Bloque</label></td>
             <td> <s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.bloque" id="lugarUtilizacBloque" size="5" maxlength="5"></s:textfield></td>
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacPlanta" class="small">Planta</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.planta" id="lugarUtilizacPlanta" size="5" maxlength="100"></s:textfield></td>
           </tr>
           <tr>
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacPuerta" class="small">Puerta</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.puerta" id="lugarUtilizacPuerta" size="5" maxlength="100"></s:textfield></td>
		<td align="left" nowrap="nowrap"><label for="lugarUtilizacNumeroBis" class="small">N&uacute;m. bis</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.numeroBis" id="lugarUtilizacNumeroBis" size="5"></s:textfield></td>
             <td align="left" nowrap="nowrap"><label for="lugarUtilizacEscalera" class="small">Escalera</label></td>
             <td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.escalera" id="lugarUtilizacEscalera" size="5" maxlength="100"></s:textfield></td>
           </tr>
           <tr>
				<td align="left" nowrap="nowrap"><label for="lugarUtilizacKm" class="small">Punto KM</label></td>
				<td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.km" id="lugarUtilizacKm" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"></s:textfield></td>
				<td align="left" nowrap="nowrap"><label for="lugarUtilizacPortal" class="small">Portal</label></td>
				<td><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.portal" id="lugarUtilizacPortal" size="5"></s:textfield></td>
            </tr>
            
            <tr>
				<td align="left" nowrap="nowrap" ><label for="lugarUtilizacLugarUbicacion" class="small" >Lugar ubicaci&oacute;n</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Descripci&oacute;n libre del lugar de ubicaci&oacute;n"/>
				</td>
		        <td colspan="3"><s:textfield name="tramiteRegRbmDto.propiedadDto.direccion.lugarUbicacion" id="lugarUtilizacLugarUbicacion" size="35" maxlength="255"></s:textfield></td>
		    </tr>
	</table>
	
<script type="text/javascript">
	var viaProveedor = new BasicContentAssist(document.getElementById('proveedorNombreVia'), [], null, true); 
	recargarNombreVias('proveedorSelectProvinciaId', 'proveedorSelectMunicipioId', 'proveedorTipoVia','proveedorNombreVia',viaProveedor);

	var viaLugarUtilizac = new BasicContentAssist(document.getElementById('lugarUtilizacNombreVia'), [], null, true); 
	recargarNombreVias('lugarUtilizacSelectProvinciaId', 'lugarUtilizacSelectMunicipioId', 'lugarUtilizacAddressCorpmeStreetTypeId','lugarUtilizacNombreVia',viaLugarUtilizac);
</script>

</s:if>
