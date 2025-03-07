<%@ taglib prefix="s" uri="/struts-tags" %>
	
	<s:hidden name="lcTramiteDto.intervinienteOtrosInteresados.idInterviniente"/>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Otros Interesados </span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			 <td align="left" nowrap="nowrap"><label for="tipoDocOInt">Tipo de documento: <span class="naranja">*</span> </label></td>
	        	 <td width="30%" >
	         	<s:select list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoDocumento()"
						id="tipoDocOInt" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						listValue="descripcion" listKey="codigo" title="Tipo Documento"
						headerKey="" headerValue="-" name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.tipoDocumento"/>
		      	</td>
				<td align="left"nowrap="nowrap"><label for="etNif">NIF / CIF: <span class="naranja">*</span></label></td>
				<td width="24%">
		      	    <table cellSpacing="0" >
						<tr>
							<td align="left">
								<s:textfield id="nifOtros" name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.nif"  
			       				onblur="this.className='input2';calculaLetraNIF(this)" 
			       				onfocus="this.className='inputfocus';" size="9" maxlength="9"
			       				/>
							</td>
							<td align="left">
								<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
									<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:buscarpersona('035', 'divDatosOtrosInteresados', 'nifOtros')"/>
								</s:if>
							</td>
						</tr>
					</table>
	      	   </td>	
    	  </tr>
          <tr>
          	<td align="left" nowrap="nowrap"><label for="tipoSujetoOLc">Tipo de sujeto: <span class="naranja">*</span> </label></td>
	        	<td width="30%" >
	         	<s:select list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoSujeto()"
						id="tipoSujetoOLc" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 	
						listValue="descripcion" listKey="codigo" title="Tipo Sujeto"
						headerKey="" headerValue="-" name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.tipoSujeto"/>
		     </td>
              <td align="left" nowrap="nowrap"><label for="otroInteresadoNombreDotLc">Nombre: <span class="naranja">*</span></label> </td>
              <td>
              <s:textfield name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.nombre" id="otroInteresadoNombre" size="18" maxlength="20"></s:textfield>
              </td>
          </tr>
  	      <tr>
  	      	  <td align="left" nowrap="nowrap"><label for="otroInteresadoApellido1RazonSocial">1er apellido / Raz&oacute;n social: <span class="naranja">*</span></label></td>
              <td><s:textfield name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.apellido1RazonSocial" id="otroInteresadoApellido1RazonSocial" size="18"  maxlength="70"></s:textfield></td>
              <td align="left" nowrap="nowrap"><label for="otroInteresadoApellido2">2do apellido: <span class="naranja">*</span></label></td>
              <td><s:textfield name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.apellido2" id="otroInteresadoApellido2" size="18"  maxlength="25"></s:textfield></td>
        </tr>
      	<tr>
   			<td align="left" nowrap="nowrap"><label for="telefono1OLc" >N&uacute;m. Tel&eacute;fono Principal: <span class="naranja">*</span></label> </td>
			<td><s:textfield name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.numTelefono1" id="telefono1OLc" size="18" maxlength="15"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="telefono2OLc" class="small">N&uacute;m. Tel&eacute;fono Secundario:</label></td>
            <td><s:textfield name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.numTelefono2" id="telefono2OLc" size="18" maxlength="15"></s:textfield></td>
      	</tr>
      	<td align="left" nowrap="nowrap"><label for="faxOLc" >N&uacute;m. Fax:</label></td>
			<td>  
				<s:textfield name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.numFax" id="faxOLc" size="18" maxlength="15"></s:textfield>
			</td>
      		<td align="left" nowrap="nowrap"><label for="correoElectronicoOLc" class="small">Email:</label></td>
            <td><s:textfield name="lcTramiteDto.intervinienteOtrosInteresados.lcPersona.correoElectronico" id="correoElectronicoOLc" size="18"  onblur="validaEmail(this.value)" maxlength="50"></s:textfield></td>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección otro interesado</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">	
		<tr>
			<td align="left" nowrap="nowrap" ><label for="etPaisOLc" class="small">País: <span class="naranja">*</span></label></td>
      	    <td>
				<s:select id="etPaisOLc"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.pais" 
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().paises()"
					headerKey="" headerValue="Seleccione País" listKey="codigo" listValue="descripcion"/>	
			</td>
		
      		<td align="left" nowrap="nowrap" ><label for="idProvinciaOItLc" class="small">Provincia: <span class="naranja">*</span></label></td>
      	    <td>
				<s:select id="idProvinciaOItLc"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.provincia" 
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().provincias()"
					headerKey="" headerValue="Seleccione Provincia" listKey="codigo" 
					listValue="descripcion"/>	
			</td>
      	</tr>
      	<tr>        	       			
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
				<label for="idMunicipioOItLc">Municipio:</label>
			</td>
      	    
      	    <td align="left" nowrap="nowrap">
      	    	<s:textfield id="idMunicipioOItLc" 
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.municipio" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="70"/>
			</td>
      	  	       			
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="idPuebloOItLc">Población: <span class="naranja">*</span></label>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="idPuebloOItLc" 
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.poblacion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="70"/>
			</td>
      	</tr>
      	<tr>        	       			
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="cpOItLc">Código Postal: <span class="naranja">*</span></label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="cpOItLc" 
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.codigoPostal" 
	   				onkeypress="return validarNumerosEnteros(event)"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="5" maxlength="5"/>
   			</td>      
		</tr>
		<tr>  		       			
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="tipoViaOItLc">Tipo de vía: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<s:select id="tipoViaOItLc" name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.tipoVia" 
   					headerKey="" headerValue="Seleccione Tipo Vía" 
   					listKey="codigo" listValue="descripcion"
   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoVias()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   			</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="nombreViaOItLc">Nombre de vía:<span class="naranja">*</span> </label>
   			</td>
   			<td align="left" nowrap="nowrap" colspan="6">
	       			<s:textfield id="nombreViaOItLc" 
	       				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.nombreVia"
		       			tyle="text-transform : uppercase"/>
       		</td>        
		</tr>
      	<tr>
      		<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="tipoNumOItLc">Tipo Numeración: <span class="naranja">*</span></label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<s:select id="tipoNumOItLc" name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.tipoNumeracion" 
   					headerKey="" headerValue="Seleccione Tipo Vía" 
   					listKey="codigo" listValue="descripcion"
   					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoNumeracion()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   			</td>	
   			<td align="left" nowrap="nowrap" class="small"><label for="numeroDireccionOILc">Número: <span class="naranja">*</span></label></td>
       		<td align="left" nowrap="nowrap" width="2%">
   				<s:textfield id="numeroDireccionOILc"  
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.numCalle" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="4" maxlength="5"/>
   			</td>
   			<td align="left" nowrap="nowrap" class="small"><label for="portalDireccionOItLc">Portal: </label></td>
       		<td align="left" nowrap="nowrap">
				<s:textfield id="portalDireccionOItLc" 
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.calificador" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="2"/>
			</td>
      	</tr>
      	<tr>    	
      		<td align="left" nowrap="nowrap" class="small"><label for="plantaDireccionOItLc">Planta: </label></td>
       		<td align="left" nowrap="nowrap">
				<s:select id="plantaDireccionOItLc" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.planta"
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
					listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Piso"/>
			</td>
			<td align="left" nowrap="nowrap" class="small"><label for="puertaDireccionOItLc">Puerta: </label></td>
       		<td>
       			<s:textfield id="puertaDireccionOItLc" 
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.puerta" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="4" maxlength="4"/>
   			</td>
   			<td align="left" nowrap="nowrap" class="small"><label for="escaleraDireccionOItLc">Escalera: </label></td>
       		<td><s:textfield id="escaleraDireccionOItLc" name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.escalera" size="4" maxlength="4"/></td>		
      	</tr>
		<tr>      
      		<td align="left" nowrap="nowrap">
				<label for="localOItLc">Local:</label>
			</td>
      	    <td align="left" nowrap="nowrap">
      	    	<s:textfield id="localOItLc" 
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.local" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="4"/>
			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="codLocalOItLc">Código Local:</label>
   			</td>
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="codLocalOItLc" 
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.codLocal" 
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
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.claseDireccion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="4"/>
			</td>
   			<td align="left" nowrap="nowrap" style="vertical-align:middle">
   				<label for="codDirOItLc">Código Dirección:</label>
   			</td>
   			<td align="left" nowrap="nowrap">
      	    	<s:textfield id="codDirOItLc" 
	   				name="lcTramiteDto.intervinienteOtrosInteresados.lcDireccion.codDireccion" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				size="20" maxlength="9"/>
			</td>
      	</tr>
      </table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista otros interesados</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="listOtrosInteresados.jsp" />
        	</td>
        </tr>
	</table>