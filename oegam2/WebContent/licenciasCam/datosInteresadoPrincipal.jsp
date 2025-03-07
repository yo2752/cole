<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<div class="contenido">	
	<s:hidden name="lcTramiteDto.intervinienteInteresado.idInterviniente"/>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Interesado</span>
			</td>  
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
			 	<td align="left" nowrap="nowrap"><label for="tipoDocumento">Tipo de documento: <span class="naranja">*</span> </label></td>
	        	<td width="30%" >
	         	<s:select list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoDocumento()"
						id="tipoDocumento" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
						listValue="descripcion" listKey="codigo" title="Tipo Documento"
						headerKey="" headerValue="-" name="lcTramiteDto.intervinienteInteresado.lcPersona.tipoDocumento"/>
		      	</td>
				<td align="left"nowrap="nowrap"><label for="etNif">NIF / CIF: <span class="naranja">*</span></label></td>
				<td width="24%">
		      	    <table cellSpacing="0" >
						<tr>
							<td align="left">
								<s:textfield id="nif" name="lcTramiteDto.intervinienteInteresado.lcPersona.nif"  
			       				onblur="this.className='input2';calculaLetraNIF(this)" 
			       				onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
							</td>
							<td align="left">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:buscarpersona('034', 'divDatosInteresadoP', 'nif')"/>
								</s:if>
							</td>
						</tr>
					</table>
	      	   </td>															
    	  </tr>
          <tr>
          	<td align="left" nowrap="nowrap"><label for="tipoSujeto">Tipo de sujeto: <span class="naranja">*</span> </label></td>
	        	<td width="30%" >
	         	<s:select list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoSujeto()"
						id="tipoSujeto" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						listValue="descripcion" listKey="codigo" title="Tipo Sujeto"
						headerKey="" headerValue="-" name="lcTramiteDto.intervinienteInteresado.lcPersona.tipoSujeto"/>
		     </td>
              <td align="left" nowrap="nowrap"><label for="interesadoNombreItLc">Nombre: <span class="naranja">*</span></label> </td>
              <td>
              	<s:textfield name="lcTramiteDto.intervinienteInteresado.lcPersona.nombre" id="interesadoNombre" size="18" maxlength="20"></s:textfield>
              </td>
              
          </tr>
  	      <tr>
  	      	  <td align="left" nowrap="nowrap"><label for="interesadoApellido1RazonSocial">1er apellido / Raz&oacute;n social: <span class="naranja">*</span></label></td>
              <td><s:textfield name="lcTramiteDto.intervinienteInteresado.lcPersona.apellido1RazonSocial" id="interesadoApellido1RazonSocial" size="18"  maxlength="70"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="interesadoApellido2">2do apellido: <span class="naranja">*</span></label></td>
              <td><s:textfield name="lcTramiteDto.intervinienteInteresado.lcPersona.apellido2" id="interesadoApellido2" size="18"  maxlength="25"></s:textfield></td>
         </tr>
      	<tr>
   			<td align="left" nowrap="nowrap"><label for="telefono1" >N&uacute;m. Tel&eacute;fono Principal: <span class="naranja">*</span></label></td>
			<td>  
				<s:textfield name="lcTramiteDto.intervinienteInteresado.lcPersona.numTelefono1" id="telefono1" size="18" maxlength="15"></s:textfield>
			</td>
			<td align="left" nowrap="nowrap"><label for="telefono2" >N&uacute;m. Tel&eacute;fono Secundario:</label></td>
			<td>  
				<s:textfield name="lcTramiteDto.intervinienteInteresado.lcPersona.numTelefono2" id="telefono2" size="18" maxlength="15"></s:textfield>
			</td>
      	</tr>
      	<tr>
      		<td align="left" nowrap="nowrap"><label for="fax" >N&uacute;m. Fax:</label></td>
			<td>  
				<s:textfield name="lcTramiteDto.intervinienteInteresado.lcPersona.numFax" id="fax" size="18" maxlength="15"></s:textfield>
			</td>
      		<td align="left" nowrap="nowrap"><label for="interesadoCorreoElectronico" class="small">Email:</label></td>
            <td><s:textfield name="lcTramiteDto.intervinienteInteresado.lcPersona.correoElectronico" id="interesadoCorreoElectronico" size="18"  onblur="validaEmail(this.value)" maxlength="50"></s:textfield></td>
      	</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección interesado</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">	
		<tr>
			<td align="left" nowrap="nowrap" ><label for="etPais" class="small">País: <span class="naranja">*</span></label></td>
      	    <td>
				<s:select id="idPaisItLc"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="lcTramiteDto.intervinienteInteresado.lcDireccion.pais" 
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().paises()"
					headerKey="" headerValue="Seleccione País" listKey="codigo" listValue="descripcion"/>	
			</td>
		
      		<td align="left" nowrap="nowrap" ><label for="etProvincia" class="small">Provincia: <span class="naranja">*</span></label></td>
      	    <td>
				<s:select id="idProvinciaItLc"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="lcTramiteDto.intervinienteInteresado.lcDireccion.provincia" 
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().provincias()"
					headerKey="" headerValue="Seleccione Provincia" listKey="codigo" 
					listValue="descripcion"/>	
			</td>
      	</tr>
      	<tr>      
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
				<label for="idMunicipioItLc">Municipio:</label>
			</td>
      	    
      	    <td align="left" nowrap="nowrap">
      	    	<s:textfield id="idMunicipioItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.municipio" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="70"/>
			</td>
      	  	       			
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="idPuebloItLc">Población: <span class="naranja">*</span></label>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="idPuebloItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.poblacion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="70"/>
			</td>
      	</tr>
      	<tr>        
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="cpItLc">Código Postal: <span class="naranja">*</span></label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="cpItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.codigoPostal" 
	   				onkeypress="return validarNumerosEnteros(event)"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="5" maxlength="5"/>
   			</td>
   		</tr>
      	<tr>  		       			
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="tipoViaItLc">Tipo de vía: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<s:select id="tipoViaItLc" name="lcTramiteDto.intervinienteInteresado.lcDireccion.tipoVia" 
   					headerKey="" headerValue="Seleccione Tipo Vía" 
   					listKey="codigo" listValue="descripcion"
   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoVias()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="nombreViaItLc">Nombre de vía:<span class="naranja">*</span> </label>
   			</td>
   			<td align="left" nowrap="nowrap" colspan="6">
	       			<s:textfield id="nombreViaItLc" 
	       				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="lcTramiteDto.intervinienteInteresado.lcDireccion.nombreVia"/>
       		</td>        
		</tr>
      	<tr>
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="tipoNumItLc">Tipo Numeración: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<s:select id="tipoNumItLc" name="lcTramiteDto.intervinienteInteresado.lcDireccion.tipoNumeracion" 
   					headerKey="" headerValue="Seleccione Tipo Vía" 
   					listKey="codigo" listValue="descripcion"
   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoNumeracion()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   			</td>	
   			<td align="left" nowrap="nowrap" class="small"><label for="numeroDireccionItLc">Número: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap" width="2%">
   				<s:textfield id="numeroDireccionILc"  
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.numCalle" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="4" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap" class="small"><label for="portalDireccionItLc">Portal: </label></td>
       		<td align="left" nowrap="nowrap">
				<s:textfield id="portalDireccionItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.calificador" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="2"/>
			</td>
      	</tr>
      	<tr>    	
      		<td align="left" nowrap="nowrap" class="small"><label for="plantaDireccionItLc">Planta: </label></td>
       		<td align="left" nowrap="nowrap">
				<s:select id="plantaDireccionItLc" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.intervinienteInteresado.lcDireccion.planta"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Piso"/>
			</td>
			<td align="left" nowrap="nowrap" class="small"><label for="puertaDireccionItLc">Puerta: </label></td>
       		<td>
       			<s:textfield id="puertaDireccionItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.puerta" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="4" maxlength="4"/>
   			</td>
   			<td align="left" nowrap="nowrap" class="small"><label for="escaleraDireccionItLc">Escalera: </label></td>
       		<td><s:textfield id="escaleraDireccionItLc" name="lcTramiteDto.intervinienteInteresado.lcDireccion.escalera" size="4" maxlength="4"/></td>		
      	</tr>
		<tr>      
      		<td align="left" nowrap="nowrap">
				<label for="localItLc">Local:</label>
			</td>
      	    <td align="left" nowrap="nowrap">
      	    	<s:textfield id="localItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.local" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="4"/>
			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="codLocalItLc">Código Local:</label>
   			</td>
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="codLocalItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.codLocal" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="9"/>
			</td>
      	</tr>
      	<tr>      
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
				<label for="clasDirItLc">Clase Dirección:</label>
			</td>
      	    <td align="left" nowrap="nowrap">
      	    	<s:textfield id="clasDirItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.claseDireccion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="4"/>
			</td>
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="codDirItLc">Código Dirección:</label>
   			</td>
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="codDirItLc" 
	   				name="lcTramiteDto.intervinienteInteresado.lcDireccion.codDireccion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="9"/>
			</td>
      	</tr>
      </table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
      	<tr>
			<td align="center">
				<input id="bValDirIP" type="button" class="botonGrande" name="bValDirIP" value="Validar Dirección" onClick="validarDireccion('034')" onkeyPress="this.onClick"/>
			</td>
		</tr>
	</table>
	</div>
	
