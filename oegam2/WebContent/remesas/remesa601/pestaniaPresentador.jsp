<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Remesa 601</td>
		</tr>
	</table>
	<%@include file="../../../includes/erroresMasMensajes.jspf" %>
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
				<label for="labelNifSujPasico">N.I.F<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
	     		<table style="align:left;" >
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idNifPresentador" name="remesa.presentador.persona.nif"  onblur="this.className='input2';calculaLetraNIF(this)" 
			       				disabled="true" onfocus="this.className='inputfocus';" style="text-transform : uppercase"
			       				size="9" maxlength="9"/>
			       			<s:hidden name="remesa.presentador.persona.numColegiado"/>
			       			<s:hidden name="remesa.presentador.direccion.idDireccion"/>
			       			<s:hidden name="remesa.presentador.idDireccion"/>
			       			<s:hidden name="remesa.presentador.idInterviniente"/>
			       			<s:hidden name="remesa.presentador.tipoInterviniente"/>
						</td>
					</tr>
				</table>
	    	</td>
	    	 <td align="left" nowrap="nowrap">
       			<label for="labelTipoPersona">Tipo de persona<span class="naranja">*</span>:</label>
       		</td>
         	<td align="left" nowrap="nowrap">
	         	<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getComboTipoPersonas()"
					id="idTipoPersonaPresentador" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="remesa.presentador.persona.tipoPersona" disabled="true"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-"/>
       		</td>
		</tr>
		<tr>       
       		<td align="left" nowrap="nowrap">
   				<label for="sexoSujetoPasivo">Sexo: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap" >
	         	<s:select id="idSexoPresentador" name="remesa.presentador.persona.sexo"
	         		list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getComboSexo()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-" disabled="true"/>
       		</td>
        	
       		<td align="left" nowrap="nowrap" colspan="2">
       			<label for="labelPrimerApeTitularAujPasivo">Primer Apellido / Razón Social<span class="naranja">*</span>:</label>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idPrimerApeRazonSocialPresentador"  name="remesa.presentador.persona.apellido1RazonSocial"  
	       			onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;" disabled="true"/>
       		</td> 	       			
      	</tr>
      	<tr>        	       			
   			<td align="left" nowrap="nowrap">
   				<label for="labelSegundoApePresentador">Segundo Apellido:</label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idSegundoApePresentador" name="remesa.presentador.persona.apellido2" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" size="30" maxlength="100" disabled="true"/>
   			</td>
        	       			
   			<td align="left" nowrap="nowrap">
   				<label for="labelNombrePresentador">Nombre<span class="naranja">*</span>:</label>
   			</td>
   			
       		<td align="left" nowrap="nowrap" colspan="2">
   				<s:textfield id="idNombrePresentador" name="remesa.presentador.persona.nombre" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" size="20" maxlength="100" disabled="true"/>
   			</td>
      	</tr>
      	<tr>
      		<td align="left" nowrap="nowrap">
   				<label for="labelFechaNacPresentador">Fecha Nacimiento: </label>
   			</td>
   			<td align="left" nowrap="nowrap" colspan="2">
   				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="remesa.presentador.persona.fechaNacimiento.dia" id="idDiaNacPresentador" onblur="this.className='input2';" 
								onkeypress="return validarDia(this, event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="remesa.presentador.persona.fechaNacimiento.mes" id="idMesNacPresentador" onblur="this.className='input2';" 
								onkeypress="return validarMes(this, event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2" disabled="true"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="remesa.presentador.persona.fechaNacimiento.anio" id="idAnioNacPresentador" onblur="this.className='input2';" onkeypress="return validarAnio(this, event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true"/>
						</td>
					</tr>
				</table>
   			</td>
      	</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Direccion Presentador:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvinciaPresentador">Provincia<span class="naranja">*</span>:</label>
			</td>
			<td>
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaProvincias()" onblur="this.className='input2';" 
			    		onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
			    		name="remesa.presentador.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaPresentador" 
						disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelMunicipioPresentador">Municipio<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
			    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioPresentador"
					headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
					name="remesa.presentador.direccion.idMunicipio"
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaMunicipiosPorProvincia(remesa,'002')"
				/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
   				<label for="labelPuebloPresentador">Pueblo: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">   				
		    	<s:select id="idPuebloPresentador" onblur="this.className='input2';"
   					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaPueblosPorMunicipioYProvincia(remesa,'002')"
   					headerKey="-1" headerValue="Seleccione Pueblo" listKey="pueblo" listValue="pueblo"
					name="remesa.presentador.direccion.pueblo"
					onfocus="this.className='inputfocus';"
					style="width:200px;"/>	
   			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoViaPresentador">Tipo Via<span class="naranja">*</span>:</label>
			</td>
			<td>
			    <s:select  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaTiposVias(remesa,'002')"
					headerKey="-1" headerValue="Seleccione Tipo Via" listKey="idTipoViaCam" listValue="nombre" 
					name="remesa.presentador.direccion.idTipoVia"
					id="idTipoViaPresentador"/> 
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNombrePresentador">Nombre Vía Pública<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="remesa.presentador.direccion.nombreVia" id="idNombreViaPresentador" size="25" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelCodPostalPresentador">Cod. Postal<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="remesa.presentador.direccion.codPostal" id="idCodPostalPresentador" size="5" maxlength="5" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" disabled="true"/>
			</td>
		</tr>
	</table>
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
      	<tr>    			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelNumeroDireccionPresentador">Número: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idNumeroDireccionPresentador" name="remesa.presentador.direccion.numero" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)" size="4" maxlength="5" disabled="true"/>
   			</td>
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelLetraDireccionPresentador">Letra: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idLetraDireccionPresentador" name="remesa.presentador.direccion.letra" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
	   				onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="10" disabled="true"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelEscaleraDireccionPresentador">Escalera: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idEscaleraDireccionPresentador" name="remesa.presentador.direccion.escalera"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10" disabled="true"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelPisoDireccionPresentador">Piso: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idPisoDireccionPresentador" name="remesa.presentador.direccion.planta"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3" disabled="true"/>
   			</td>
      	</tr>
      	
      	<tr>    			
   			<td align="left" nowrap="nowrap">
   				<label for="labelPuertaDireccionPresentador">Puerta: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idPuertaDireccionPresentador" name="remesa.presentador.direccion.puerta"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
   				<label for="idBloqueDireccionPresentador">Bloque: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idBloqueDireccionPresentador" name="remesa.presentador.direccion.bloque"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10" disabled="true"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
   				<label for="labelKmDireccionPresentador">Km: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idKmDireccionPresentador" name="remesa.presentador.direccion.km"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" disabled="true"/>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="hmDireccionPresentador">Hm: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="hmDireccionPresentador" name="remesa.presentador.direccion.hm" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3" disabled="true"/>
   			</td>
      	</tr>
	</table>
</div>
