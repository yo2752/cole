<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 

	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Persona:</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNif">N.I.F<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
	     		<table style="align:left;" >
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield id="idNifConductor" name="conductorDto.persona.nif"  onblur="this.className='input2';calculaLetraNIF(this)" 
			       				onfocus="this.className='inputfocus';" style="text-transform : uppercase"
			       				size="9" maxlength="9"/>
			       				<s:hidden name="conductorDto.persona.numColegiado"/>
			       		</td>
						<td align="left" nowrap="nowrap">							
								<input type="button" id="idBotonBuscarNifConductor" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarConductor()"/>
						</td>
					</tr>
				</table>
	    	</td>
	    	<td align="left" nowrap="nowrap">
       			<label for="labelTipoPersona">Tipo de persona<span class="naranja">*</span>:</label>
       		</td>
         	<td align="left" nowrap="nowrap">
	         	<s:select list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getComboTipoPersonas()"
					id="idTipoPersonaConductor" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" name="conductorDto.persona.tipoPersona"
					listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" headerValue="-" disabled="true"
				
					/>
       		</td> 
		</tr>
		<tr>       
       		<td align="left" nowrap="nowrap">
   				<label for="sexoConductor">Sexo: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap" >
	         	<s:select id="idSexoConductor" name="conductorDto.persona.sexo"
	         		list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getComboSexo()"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
					listKey="valorEnum" title="Sexo Persona" headerKey="-1" headerValue="-" disabled="false"/>
       		</td>
        	
       		<td align="left" nowrap="nowrap" >
       			<label for="labelPrimerApeTitularConductor">Primer Apellido / Razón Social<span class="naranja">*</span>:</label>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idPrimerApeRazonSocialConductor"  name="conductorDto.persona.apellido1RazonSocial"  
	       			onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;"  />
       		</td> 	       			
      	</tr>
      	<tr>        	       			
   			<td align="left" nowrap="nowrap">
   				<label for="labelSegundoApeConductor">Segundo Apellido:</label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idSegundoApeConductor" name="conductorDto.persona.apellido2" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" size="30" maxlength="100" />
   			</td>
        	       			
   			<td align="left" nowrap="nowrap">
   				<label for="labelNombreConductor">Nombre<span class="naranja">*</span>:</label>
   			</td>
   			
       		<td align="left" nowrap="nowrap" colspan="2">
   				<s:textfield id="idNombreConductor" name="conductorDto.persona.nombre" onblur="this.className='input2';" 
	   				onfocus="this.className='inputfocus';" size="20" maxlength="100" />
   			</td>
      	</tr>
      	<tr>
      		<td align="left" nowrap="nowrap">
   				<label for="labelFechaNacConductor">Fecha Nacimiento: </label>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="conductorDto.persona.fechaNacimiento.dia" id="idDiaNacConductor" onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="conductorDto.persona.fechaNacimiento.mes" id="idMesNacConductor" onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="conductorDto.persona.fechaNacimiento.anio" id="idAnioNacConductor" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" size="4" maxlength="4" />
						</td>
						
						<td align="left" nowrap="nowrap">
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioNacConductor, document.formData.idMesNacConductor, document.formData.idDiaNacConductor);return false;" 
				    			title="Seleccionar fecha">
				    			<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				    		</a>
						</td>
					</tr>
				</table>
   			</td>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Dirección Persona:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
			<tr id="trDireccionConductor">
				<td align="left" nowrap="nowrap">
					<label for="labelIdDir">Id direccion:</label>
				</td>
				<td>
					<s:textfield name="conductorDto.persona.direccionDto.idDireccion" id="idDireccionConductor" size="25" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" disabled="true"/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden id="idDireccionConductor" name="conductorDto.persona.direccionDto.idDireccion"/>
		</s:else>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelProvinciaConductor">Provincia<span class="naranja">*</span>:</label>
			</td>
			<td> 
				<s:select list="@org.gestoresmadrid.oegam2comun.arrendatarios.utiles.UtilesVistaArrendatarios@getInstance().getListaProvincias()" 
								onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Provincia" 
					    		name="conductorDto.persona.direccionDto.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaConductor"					    		
						onchange="cargarListaMunicipiosCAYC('idProvinciaConductor','idMunicipioConductor'); 
						cargarListaTipoViaCAYC('idProvinciaConductor','idTipoViaConductor');							
						borrarComboPuebloCAYC('idPuebloConductor');	
						inicializarTipoVia('idTipoViaConductor','idNombreViaConductor',viaConductor);										
						borrarRestoCampos('idCodPostalConductor','idNumeroConductor','idLetraDireccionConductor','idEscaleraDireccionConductor','idPisoDireccionConductor','idPuertaDireccionConductor','idBloqueDireccionConductor','idKmDireccionConductor','hmDireccionConductor');" />
						
			</td> 
			<td align="left" nowrap="nowrap">
				<label for="labelMunicipioConductor">Municipio<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
			    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioConductor"
					headerKey=""	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
					name="conductorDto.persona.direccionDto.idMunicipio"
					list="@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().getListaMunicipiosPorProvincia(conductorDto)"
					onchange="obtenerCodigoPostalPorMunicipioCAYC('idProvinciaConductor', 'idMunicipioConductor', 'idCodPostalConductor'); 
					cargarListaPueblosCAYC('idProvinciaConductor', 'idMunicipioConductor', 'idPuebloConductor');
					inicializarTipoVia('idTipoViaConductor','idNombreViaConductor',viaConductor);
					cargarListaTipoViaCAYC('idProvinciaConductor','idTipoViaConductor'); 
					borrarRestoCampos('idCodPostalConductor','idNumeroConductor','idLetraDireccionConductor','idEscaleraDireccionConductor','idPisoDireccionConductor','idPuertaDireccionConductor','idBloqueDireccionConductor','idKmDireccionConductor','hmDireccionConductor'); "/>
			</td>  
		</tr>
		<tr>
					<td align="left" nowrap="nowrap">
		   				<label for="labelPuebloConductor">Pueblo: </label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">   				
		   				<s:select id="idPuebloConductor" onblur="this.className='input2';"
		   					list="@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().getListaPueblos(conductorDto)"
		   					headerKey="" headerValue="Seleccione Pueblo" listKey="pueblo" listValue="pueblo"
							name="conductorDto.persona.direccionDto.pueblo"
							onfocus="this.className='inputfocus';"
							onchange="obtenerCodigoPostalPorMunicipioCAYC('idProvinciaConductor', 'idMunicipioConductor', 'idCodPostalConductor');"
							style="width:200px;"/>						  
		   			</td>
				
					<td align="left" nowrap="nowrap">
						<label for="labelTipoViaConductor">Tipo Via<span class="naranja">*</span>:</label>
					</td>
					<td>
						<s:select  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
							list="@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().getListaTipoVias(conductorDto)"
							headerKey="" headerValue="Seleccione Tipo Via" listKey="idTipoVia" listValue="nombre" 
							name="conductorDto.persona.direccionDto.idTipoVia"
							id="idTipoViaConductor"
							onchange="cargarListaNombresVia_Conductor('idProvinciaConductor', 'idMunicipioConductor', this, 'idNombreViaConductor',viaConductor);"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNombreConductor">Nombre Vía Pública<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="conductorDto.persona.direccionDto.nombreVia" id="idNombreViaConductor" size="25" onblur="this.className='input2';" 
			       			onfocus="this.className='inputfocus';" autocomplete="off" cssStyle="width:200px;position:relative;" maxlength="50"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelCodPostalConductor">Cod. Postal<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="conductorDto.persona.direccionDto.codPostal" id="idCodPostalConductor" size="5" maxlength="5" onblur="this.className='input2';" 
			       			onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
		      	<tr>    			
		   			<td align="left" nowrap="nowrap" width="7%">
		   				<label for="labelNumeroConductor">Número<span class="naranja">*</span>:</label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">
		   				<s:textfield id="idNumeroConductor" name="conductorDto.persona.direccionDto.numero" 
			   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)" size="4" maxlength="5"/>
		   			</td>
		   				<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelLetraDireccionConductor">Letra: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idLetraDireccionConductor" name="conductorDto.persona.direccionDto.letra" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
	   				onkeypress="return validarLetras(event)" style="text-transform : uppercase" size="4" maxlength="10"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelEscaleraDireccionConductor">Escalera: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idEscaleraDireccionConductor" name="conductorDto.persona.direccionDto.escalera"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap" width="7%">
   				<label for="labelPisoDireccionConductor">Piso: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idPisoDireccionConductor" name="conductorDto.persona.direccionDto.planta"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
   			</td>
      	</tr>
      	
      	<tr>    			
   			<td align="left" nowrap="nowrap">
   				<label for="labelPuertaDireccionConductor">Puerta: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idPuertaDireccionConductor" name="conductorDto.persona.direccionDto.puerta"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
   				<label for="idBloqueDireccionConductor">Bloque: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idBloqueDireccionConductor" name="conductorDto.persona.direccionDto.bloque"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="10"/>
   			</td>
   			
   			<td align="left" nowrap="nowrap">
   				<label for="labelKmDireccionConductor">Km: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="idKmDireccionConductor" name="conductorDto.persona.direccionDto.km"
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
   			</td>
   			<td align="left" nowrap="nowrap">
   				<label for="hmDireccionTitular">Hm: </label>
   			</td>
   			
       		<td align="left" nowrap="nowrap">
   				<s:textfield id="hmDireccionConductor" name="conductorDto.persona.direccionDto.hm" 
	   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	   				onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
   			</td>
		   		</tr>		
		</table>

<script>	
	var viaConductor = new BasicContentAssist(document.getElementById('idNombreViaConductor'), [], null, true);
</script>
