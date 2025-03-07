<%@ taglib prefix="s" uri="/struts-tags" %>
	
	<s:hidden name="lcTramiteDto.intervinienteRepresentante.idInterviniente"/>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Representante</span>
			</td>  
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			 <td align="left" nowrap="nowrap"><label for="tipoDocumentoR">Tipo de documento: <span class="naranja">*</span> </label></td>
	        	<td width="30%" >
	         	<s:select list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoDocumento()"
						id="tipoDocumentoR" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
						listValue="descripcion" listKey="codigo" title="Tipo Documento"
						headerKey="" headerValue="-" name="lcTramiteDto.intervinienteRepresentante.lcPersona.tipoDocumento"/>
		      	</td>
		      	
		      	<td align="left"nowrap="nowrap"><label for="etNif">NIF / CIF: <span class="naranja">*</span></label></td>
				<td width="24%">
		      	    <table cellSpacing="0" >
						<tr>
							<td align="left">
								<s:textfield id="nifRep" name="lcTramiteDto.intervinienteRepresentante.lcPersona.nif"  
			       				onblur="this.className='input2';calculaLetraNIF(this)" 
			       				onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
							</td>
							<td align="left">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:buscarpersona('036', 'divDatosRepresentante', 'nifRep')"/>
								</s:if>
							</td>
						</tr>
					</table>
	      	   </td>	
    	  </tr>
          <tr>
              <td align="left" nowrap="nowrap"><label for="tipoSujetoR">Tipo de sujeto: <span class="naranja">*</span> </label></td>
	        	<td width="30%" >
	         	<s:select list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoSujeto()"
						id="tipoSujetoR" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						listValue="descripcion" listKey="codigo" title="Tipo Sujeto"
						headerKey="" headerValue="-" name="lcTramiteDto.intervinienteRepresentante.lcPersona.tipoSujeto"/>
		     </td>
              <td align="left" nowrap="nowrap"><label for=repreNombre>Nombre: <span class="naranja">*</span></label> </td>
              <td>
              	<s:textfield name="lcTramiteDto.intervinienteRepresentante.lcPersona.nombre" id="repreNombre" size="18" maxlength="20"></s:textfield>
              </td>
          </tr>
      	 <tr>
  	      	  <td align="left" nowrap="nowrap"><label for="repreApellido1RazonSocial">1er apellido / Raz&oacute;n social: <span class="naranja">*</span></label></td>
              <td><s:textfield name="lcTramiteDto.intervinienteRepresentante.lcPersona.apellido1RazonSocial" id="repreApellido1RazonSocial" size="18"  maxlength="70"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="repreApellido2">2do apellido: <span class="naranja">*</span></label></td>
              <td><s:textfield name="lcTramiteDto.intervinienteRepresentante.lcPersona.apellido2" id="repreApellido2" size="18" maxlength="25"></s:textfield></td>
         </tr>
      	<tr>
   			<td align="left" nowrap="nowrap"><label for="telefono1R" >N&uacute;m. Tel&eacute;fono Principal: <span class="naranja">*</span></label></td>
			<td>  
				<s:textfield name="lcTramiteDto.intervinienteRepresentante.lcPersona.numTelefono1" id="telefono1R" size="18" maxlength="15"></s:textfield>
			</td>
			<td align="left" nowrap="nowrap"><label for="telefono2R" >N&uacute;m. Tel&eacute;fono Secundario:</label></td>
			<td>  
				<s:textfield name="lcTramiteDto.intervinienteRepresentante.lcPersona.numTelefono2" id="telefono2R" size="18" maxlength="15"></s:textfield>
			</td>
      	</tr>
      	<tr>
      		<td align="left" nowrap="nowrap"><label for="faxR" >N&uacute;m. Fax:</label></td>
			<td>  
				<s:textfield name="lcTramiteDto.intervinienteRepresentante.lcPersona.numFax" id="faxR" size="18" maxlength="15"></s:textfield>
			</td>
      		<td align="left" nowrap="nowrap"><label for="repreCorreoElectronico" class="small">Email:</label></td>
            <td><s:textfield name="lcTramiteDto.intervinienteRepresentante.lcPersona.correoElectronico" id="repreCorreoElectronico" size="18"  onblur="validaEmail(this.value)" maxlength="50"></s:textfield></td>
      	</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección representante</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">	
		<tr>
			<td align="left" nowrap="nowrap" ><label for="idPaisRLc" class="small">País: <span class="naranja">*</span></label></td>
      	    <td>
				<s:select id="idPaisRLc"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="lcTramiteDto.intervinienteRepresentante.lcDireccion.pais" 
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().paises()"
					headerKey="" headerValue="Seleccione País" listKey="codigo" listValue="descripcion"/>	
			</td>
		
      		<td align="left" nowrap="nowrap" ><label for="idProvinciaRLc" class="small">Provincia: <span class="naranja">*</span></label></td>
      	    <td>
				<s:select id="idProvinciaRLc"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="lcTramiteDto.intervinienteRepresentante.lcDireccion.provincia" 
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().provincias()"
					headerKey="" headerValue="Seleccione Provincia" listKey="codigo" 
					listValue="descripcion"/>	
			</td>
      	</tr>
      	<tr>      
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
				<label for="idMunicipioRLc">Municipio:</label>
			</td>
      	    
      	    <td align="left" nowrap="nowrap">
      	    	<s:textfield id="idMunicipioRLc" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.municipio" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="70"/>
			</td>
      	  	       			
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="idPuebloRLc">Población: <span class="naranja">*</span></label>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="idPuebloRLc" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.poblacion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="70" />
			</td>
      	</tr>
      	<tr>        
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="cpRLc">Código Postal: <span class="naranja">*</span></label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="cpRLc" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.codigoPostal" 
	   				onkeypress="return validarNumerosEnteros(event)"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="5" maxlength="5"/>
   			</td>
   		</tr>
      	<tr>  		       			
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="tipoViaRLc">Tipo de vía: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<s:select id="tipoViaRLc" name="lcTramiteDto.intervinienteRepresentante.lcDireccion.tipoVia" 
   					headerKey="" headerValue="Seleccione Tipo Vía" 
   					listKey="codigo" listValue="descripcion"
   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoVias()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="nombreViaRLc">Nombre de vía:<span class="naranja">*</span> </label>
   			</td>
   			<td align="left" nowrap="nowrap" colspan="6">
	       			<s:textfield id="nombreViaRLc" 
	       				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="lcTramiteDto.intervinienteRepresentante.lcDireccion.nombreVia"
		       			tyle="text-transform : uppercase"/>
       		</td>       
		</tr>
      	<tr>
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="tipoNumRLc">Tipo Numeración: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<s:select id="tipoNumRLc" name="lcTramiteDto.intervinienteRepresentante.lcDireccion.tipoNumeracion" 
   					headerKey="" headerValue="Seleccione Tipo Vía" 
   					listKey="codigo" listValue="descripcion"
   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoNumeracion()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   			</td>	
   			<td align="left" nowrap="nowrap" class="small"><label for="numeroDireccionR">Número: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap" width="2%">
   				<s:textfield id="numeroDireccionR"  
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.numCalle" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="4" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap" class="small"><label for="portalDireccionR">Portal: </label></td>
       		<td align="left" nowrap="nowrap">
				<s:textfield id="portalDireccionR" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.calificador" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="2"/>
			</td>
   			
      	</tr>
      	<tr>    	
      		<td align="left" nowrap="nowrap" class="small"><label for="plantaDireccionR">Planta: </label></td>
       		<td align="left" nowrap="nowrap">
				<s:select id="plantaDireccionR" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.intervinienteRepresentante.lcDireccion.planta"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Piso"/>
			</td>
			<td align="left" nowrap="nowrap" class="small"><label for="puertaDireccionR">Puerta: </label></td>
       		<td>
       			<s:textfield id="puertaDireccionR" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.puerta" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="4" maxlength="4"/>
   			</td>
   			<td align="left" nowrap="nowrap" class="small"><label for="escaleraDireccionR">Escalera: </label></td>
       		<td><s:textfield id="escaleraDireccionR" name="lcTramiteDto.intervinienteRepresentante.lcDireccion.escalera" size="4" maxlength="4"/></td>		
      	</tr>
		<tr>      
      		<td align="left" nowrap="nowrap">
				<label for="localR">Local:</label>
			</td>
      	    <td align="left" nowrap="nowrap">
      	    	<s:textfield id="localR" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.local" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="4"/>
			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="codLocalR">Código Local:</label>
   			</td>
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="codLocalR" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.codLocal" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="9"/>
			</td>
      	</tr>
      	<tr>      
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
				<label for="clasDirR">Clase Dirección:</label>
			</td>
      	    <td align="left" nowrap="nowrap">
      	    	<s:textfield id="clasDirR" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.claseDireccion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="4"/>
			</td>
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="codDirR">Código Dirección:</label>
   			</td>
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="codDirR" 
	   				name="lcTramiteDto.intervinienteRepresentante.lcDireccion.codDireccion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="9"/>
			</td>
      	</tr>
      </table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
      	<tr>
			<td align="center">
				<input id="bValDirRep" type="button" class="botonGrande" name="bValDirRep" value="Validar Dirección" onClick="validarDireccion('036')" onkeyPress="this.onClick"/>
			</td>
		</tr>
	</table>
	