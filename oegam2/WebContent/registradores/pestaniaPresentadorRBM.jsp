<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Presentador:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNifSujPasico">N.I.F:</label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
				<s:textfield id="idNifPresentador" name="tramiteRegRbmDto.presentador.nif"  onblur="this.className='input2';calculaLetraNIF(this)" 
       				readonly="true" onfocus="this.className='inputfocus';" style="text-transform : uppercase"
       				size="9" maxlength="9"/>
       			<s:hidden name="tramiteRegRbmDto.presentador.numColegiado"/>
       			<s:hidden name="tramiteRegRbmDto.presentador.direccionDto.idDireccion"/>
       			
	    	</td>
	    	 <td align="left" nowrap="nowrap">
       			<label for="labelTipoPersona">Tipo de persona:</label>
       		</td>
       		<td><s:textfield value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoPersonaRegistro@convertirTexto(#attr.tramiteRegRbmDto.presentador.tipoPersona)}" readonly="true"/></td>
		</tr>
		<tr>       
       		<td align="left" nowrap="nowrap">
   				<label for="sexoSujetoPasivo">Sexo: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
	         	<s:textfield id="idSexoPresentador" name="tramiteRegRbmDto.presentador.sexo"
	         		list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getComboSexo()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-" readonly="true" size="2"/>
       		</td>
        	
       		<td align="left" nowrap="nowrap">
       			<label for="labelPrimerApeTitularAujPasivo">Primer Apellido / Razón Social:</label>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idPrimerApeRazonSocialPresentador"  name="tramiteRegRbmDto.presentador.apellido1RazonSocial"  
	       			onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;" readonly="true"/>
       		</td> 	       			
      	</tr>
      	<tr>        	       			
   			<td align="left" nowrap="nowrap">
   				<label for="labelSegundoApePresentador">Segundo Apellido:</label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idSegundoApePresentador" name="tramiteRegRbmDto.presentador.apellido2" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" size="30" maxlength="100" readonly="true"/>
   			</td>
        	       			
   			<td align="left" nowrap="nowrap">
   				<label for="labelNombrePresentador">Nombre:</label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idNombrePresentador" name="tramiteRegRbmDto.presentador.nombre" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" size="20" maxlength="100" readonly="true"/>
   			</td>
      	</tr>
      	<tr>
      		<td align="left" nowrap="nowrap">
   				<label for="labelFechaNacPresentador">Fecha Nacimiento: </label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<table width="20%">
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="tramiteRegRbmDto.presentador.fechaNacimiento.dia" id="idDiaNacPresentador" onblur="this.className='input2';" 
								onkeypress="return validarDia(this, event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" readonly="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="tramiteRegRbmDto.presentador.fechaNacimiento.mes" id="idMesNacPresentador" onblur="this.className='input2';" 
								onkeypress="return validarMes(this, event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2" readonly="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="tramiteRegRbmDto.presentador.fechaNacimiento.anio" id="idAnioNacPresentador" onblur="this.className='input2';" onkeypress="return validarAnio(this, event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" readonly="true"/>
						</td>
					</tr>
				</table>
   			</td>
       		<td align="left" nowrap="nowrap">
   				<label for="labelTelefonoSujPasivo">Tel&eacute;fono: </label>
   			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="tramiteRegRbmDto.presentador.telefonos" id="idTelefonoPresentador" size="10" maxlength="9" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';"  readonly="true"/>
			</td>
      	</tr>
      	<tr>        	       			
   			<td align="left" nowrap="nowrap">
   				<label for="labelCorreoPresentador">Correo Electr&oacute;nico:</label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idCorreoPresentador" name="tramiteRegRbmDto.presentador.correoElectronico" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" size="30" maxlength="100" readonly="true"/>
   			</td>
      	</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direcci&oacute;n Presentador:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvinciaPresentador">Provincia:</label>
			</td>
			<td><s:textfield value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getProvinciaNombre(tramiteRegRbmDto.presentador.direccionDto.idProvincia)}" readonly="true"/></td>
			
			<td align="left" nowrap="nowrap">
				<label for="labelMunicipioPresentador">Municipio:</label>
			</td>
			<td><s:textfield value="%{@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipioNombre(tramiteRegRbmDto.presentador.direccionDto.idMunicipio, tramiteRegRbmDto.presentador.direccionDto.idProvincia)}" readonly="true"/></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="labelPuebloPresentador">Pueblo: </label></td>
   			 <td><s:textfield name="tramiteRegRbmDto.presentador.direccionDto.pueblo" id="cesionNumLocal" size="18" maxlength="70" readonly="true"></s:textfield></td>
       	
			<td align="left" nowrap="nowrap">
				<label for="labelTipoViaPresentador">Tipo V&iacute;a:</label>
			</td>
			<td>
				<s:textfield  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()"
					headerKey="-1" headerValue="Seleccione Tipo Vía" listKey="key" listValue="name" 
					name="tramiteRegRbmDto.presentador.direccionDto.idTipoVia"
					id="idTipoViaPresentador" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNombrePresentador">Nombre Vía Pública:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="tramiteRegRbmDto.presentador.direccionDto.nombreVia" id="idNombreViaPresentador" size="25" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelCodPostalPresentador">C&oacute;d. Postal:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="tramiteRegRbmDto.presentador.direccionDto.codPostal" id="idCodPostalPresentador" size="5" maxlength="5" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" readonly="true"/>
			</td>
		</tr>
	</table>
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
      	<tr>    			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelNumeroDireccionPresentador">Número: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idNumeroDireccionPresentador" name="tramiteRegRbmDto.presentador.direccionDto.numero" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)" size="4" maxlength="5" readonly="true"/>
   			</td>
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelLetraDireccionPresentador">Letra: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idLetraDireccionPresentador" name="tramiteRegRbmDto.presentador.direccionDto.letra" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
	   				onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="10" readonly="true"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelEscaleraDireccionPresentador">Escalera: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idEscaleraDireccionPresentador" name="tramiteRegRbmDto.presentador.direccionDto.escalera"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10" readonly="true"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelPisoDireccionPresentador">Piso: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idPisoDireccionPresentador" name="tramiteRegRbmDto.presentador.direccionDto.planta"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" readonly="true"/>
   			</td>
      	</tr>
      	
      	<tr>    			
   			<td align="left" nowrap="nowrap">
   				<label for="labelPuertaDireccionPresentador">Puerta: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idPuertaDireccionPresentador" name="tramiteRegRbmDto.presentador.direccionDto.puerta"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4" readonly="true"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
   				<label for="idBloqueDireccionPresentador">Bloque: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idBloqueDireccionPresentador" name="tramiteRegRbmDto.presentador.direccionDto.bloque"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10" readonly="true"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
   				<label for="labelKmDireccionPresentador">Km: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idKmDireccionPresentador" name="tramiteRegRbmDto.presentador.direccionDto.km"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" readonly="true"/>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="hmDireccionPresentador">Hm: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="hmDireccionPresentador" name="tramiteRegRbmDto.presentador.direccionDto.hm" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" readonly="true"/>
   			</td>
      	</tr>
	</table>
</div>
