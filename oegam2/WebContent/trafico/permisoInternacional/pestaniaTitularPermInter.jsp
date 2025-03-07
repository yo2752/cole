<%@ taglib prefix="s" uri="/struts-tags" %>
  
<div class="contenido">		
<div id="divTitularPermInter">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left" style="100%">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del titular</td>
			<td align="right">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().existeTitular(permisoInternacionalDto)}">
					<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="permisoInternacionalDto.titular.persona.nif"/>','<s:property value="permisoInternacionalDto.titular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
				</s:if>
			</td>
		</tr>
	</table>
				
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">TITULAR</span>
			</td>
		</tr>
	</table>
	
	<s:hidden name="permisoInternacionalDto.titular.id"/>
	<s:hidden name="permisoInternacionalDto.titular.persona.fechaCaducidadNif.dia" />
	<s:hidden name="permisoInternacionalDto.titular.persona.fechaCaducidadNif.mes" />
	<s:hidden name="permisoInternacionalDto.titular.persona.fechaCaducidadNif.anio" />
	<s:hidden name="permisoInternacionalDto.titular.persona.fechaCaducidadAlternativo.dia" />
	<s:hidden name="permisoInternacionalDto.titular.persona.fechaCaducidadAlternativo.mes" />
	<s:hidden name="permisoInternacionalDto.titular.persona.fechaCaducidadAlternativo.anio" />
	<s:hidden name="permisoInternacionalDto.titular.persona.tipoDocumentoAlternativo" />
	
	<s:hidden name="permisoInternacionalDto.titular.persona.estado" />
	<s:hidden id ="idHiddenDireccionTitularPermInter" name="permisoInternacionalDto.titular.direccion.idDireccion" />
	
	<s:hidden name="permisoInternacionalDto.titular.numColegiado" />
	<s:hidden name="permisoInternacionalDto.titular.nif"/>
	<s:hidden name="permisoInternacionalDto.titular.numExpediente"/>
	<s:hidden name="permisoInternacionalDto.titular.persona.anagrama"/>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="dniTitular">NIF / CIF: <span class="naranja">*</span></label>
				</td>
				<td  align="left" nowrap="nowrap" width="24%">
	       	    	<table style="align:left;" >
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield id="idNifTitularPI" disabled="%{flagDisabled}"
				       				name="permisoInternacionalDto.titular.persona.nif" onblur="this.className='input2';calculaLetraNIF(this)" 
				       				onchange="limpiarFormularioTitular()"  onfocus="this.className='inputfocus';" style="text-transform : uppercase" size="9" maxlength="9"/>
							</td>
							<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().esGuardable(permisoInternacionalDto)}">
								<td align="left" nowrap="nowrap">
									<input type="button" class="boton-persona" id="botonBuscarTitular" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:buscarIntervinientePermInter()"/>
								</td>
							</s:if>
						</tr>
					</table>
	       	    </td>
	       		 <td align="left" nowrap="nowrap">
	       			<label for="tipoPersonaRepresentante">Tipo de persona: </label>
	       		</td>
	
	         	<td align="left" nowrap="nowrap">
		         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersonaTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="permisoInternacionalDto.titular.persona.tipoPersona" listValue="nombreEnum"
						listKey="valorEnum" title="Tipo Persona" headerKey="" headerValue="-" disabled="true"/>
	       		</td>	
		    </tr>
	       	<tr>       
	       		<td align="left" nowrap="nowrap">
	   				<label for="sexoTitular">Sexo: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap" >
		         	<s:select id="sexoTitular" name="permisoInternacionalDto.titular.persona.sexo"
		         		disabled="%{flagDisabled}" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
						listKey="valorEnum" title="Sexo Persona" headerKey="" headerValue="-"/>
	       		</td>
	       		<td align="left" nowrap="nowrap">
	       			<label for="primerApellidoTitular">Primer Ape./Razón Social: <span class="naranja">*</span></label>
	       		</td>
	       		<td align="left" nowrap="nowrap">
	       			<s:textfield id="idPrApeRzsSolicitantePI" name="permisoInternacionalDto.titular.persona.apellido1RazonSocial"  
	       				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:16em;" />
	       		</td> 	       			
	      	</tr>
	      	<tr>        	       			
	   			<td align="left" nowrap="nowrap">
	   				<label for="segundoApellidoTitular">Segundo Apellido: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="segundoApellidoTitular" name="permisoInternacionalDto.titular.persona.apellido2" 
	   					disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="30" maxlength="100"/>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	   				<label for="nombreTitular">Nombre: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="nombreTitular" name="permisoInternacionalDto.titular.persona.nombre" 
	   					disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
	   			</td>
	      	</tr>
		</table>
		
		<table class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="right" nowrap="nowrap">
					<s:checkbox name="cambioDomicilio" id="cambioDomicilio" onkeypress="this.onClick" onclick="cambioDomicilioPermiso();"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<b id="idCheckCambioDomicilio" for="checkCambioDomicilio"> Actualmente está deshabilitada
					la acción de realizar un cambio de domicilio a través del permiso internacional.
				</b>
				</td>
			</tr>
		</table>
		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
	      	<tr>
	      		<td align="left" nowrap="nowrap">
					<label for="idProvinciaTitular">Provincia: </label>
				</td>
	      	    <td align="left" nowrap="nowrap">
					<s:select id="idProvinciaTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="permisoInternacionalDto.titular.direccion.idProvincia" 
						disabled="%{flagDisabled}" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasTitular()"
						headerKey="" headerValue="Seleccione Provincia"
						listKey="idProvincia" listValue="nombre"
						onchange="cargarListaMunicipios(this,'idMunicipioTitular','municipioSeleccionadoTitular');
							cargarListaTipoVia(this,'tipoViaTitular','tipoViaSeleccionadaTitular');
							inicializarTipoVia('tipoViaTitular','nombreViaTitular',viaTitular);
							borrarComboPueblo('idPuebloTitular');
							borrarRestoCampos('cpTitular','numeroDireccionTitular','letraDireccionTitular','escaleraDireccionTitular','pisoDireccionTitular','puertaDireccionTitular','bloqueDireccionTitular','kmDireccionTitular','hmDireccionTitular');"/>	
				</td>
	   			
	       	    <td align="left" nowrap="nowrap">
					<label for="idMunicipioTitular">Municipio: </label>
				</td>
	      	    
	      	    <td align="left" nowrap="nowrap">
					<select id="idMunicipioTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						${stringDisabled}
						onchange="cargarListaPueblos('idProvinciaTitular', this, 'idPuebloTitular', 'puebloSeleccionadoTitular');
						seleccionarCampo('municipioSeleccionadoTitular','idMunicipioTitular');
						obtenerCodigoPostalPorMunicipio('idProvinciaTitular', this, 'cpTitular'); 
						inicializarTipoVia('tipoViaTitular','nombreViaTitular',viaTitularDuplicado);
						borrarRestoCampos('cpTitular','numeroDireccionTitular','letraDireccionTitular','escaleraDireccionTitular','pisoDireccionTitular','puertaDireccionTitular','bloqueDireccionTitular','kmDireccionTitular','hmDireccionTitular');"
						style="width:200px;">
						<option value="-1">Seleccione Municipio</option>
					</select>						  
			    	<s:hidden id="municipioSeleccionadoTitular" name="permisoInternacionalDto.titular.direccion.idMunicipio"/>
				</td>
	      	</tr>
	      	
	      	<tr>        	       			
	   			<td align="left" nowrap="nowrap">
	   				<label for="idPuebloTitular">Pueblo: </label>
	   			</td>
	   			
	       		<td align="left" nowrap="nowrap">   				
	   				<select id="idPuebloTitular" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" ${stringDisabled}
						onchange="seleccionarCampo('puebloSeleccionadoTitular','idPuebloTitular');"
						style="width:200px;">
						<option value="-1">Seleccione Pueblo</option>
					</select>						  
			    	<s:hidden id="puebloSeleccionadoTitular" name="permisoInternacionalDto.titular.direccion.pueblo"/>
	   			</td>
				
	   			<td align="left" nowrap="nowrap">
	   				<label for="cpTitular">Código Postal: </label>
	   			</td>
	   			
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="cpTitular" disabled="%{flagDisabled}"  
		   				name="permisoInternacionalDto.titular.direccion.codPostal" 
		   				onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
	   			</td>
	      	</tr>
	      	
	      	<tr>        	       			
	   			<td align="left" nowrap="nowrap">
	   				<label for="tipoViaTitular">Tipo de vía: </label>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	         		<select id="tipoViaTitular" onblur="this.className='input2';" 
						${stringDisabled} onfocus="this.className='inputfocus';"
						onchange="seleccionarCampo('tipoViaSeleccionadaTitular','tipoViaTitular');
						cargarListaNombresVia('idProvinciaTitular', 'municipioSeleccionadoTitular', this, 'nombreViaTitular', viaTitularDuplicado);">
						<option value="-1">Seleccione Tipo Via</option>
					</select>
			    	<s:hidden id="tipoViaSeleccionadaTitular" name="permisoInternacionalDto.titular.direccion.idTipoVia"/>
	         	
	       		</td>
	   			
				<td align="left" nowrap="nowrap">
	   				<label for="nombreViaTitular">Nombre de vía: </label>
	   			</td>
	   			
	   			<td align="left" nowrap="nowrap" colspan="6">
		       		<s:textfield id="nombreViaTitular" onblur="this.className='input2';"
		       			disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
						name="permisoInternacionalDto.titular.direccion.nombreVia"
			       		cssStyle="width:200px;position:relative;" autocomplete="off" maxlength="50"/>
	       		</td>        
			</tr>
	    </table>
	
		<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
	      	<tr>    			
	   			<td align="left" nowrap="nowrap" width="7%">
	   				<label for="numeroDireccionTitular">Número: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="numeroDireccionTitular"  
		   				name="permisoInternacionalDto.titular.direccion.numero" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)"
		   				size="4" maxlength="4"/>
	   			</td>
	   			<td align="left" nowrap="nowrap" width="7%">
	   				<label for="letraDireccionTitular">Letra: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="letraDireccionTitular" 
		   				name="permisoInternacionalDto.titular.direccion.letra" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" onkeypress="return validarLetras(event)" 
		   				style="text-transform : uppercase" size="4" maxlength="3"/>
	   			</td>
	   			<td align="left" nowrap="nowrap" width="7%">
	   				<label for="escaleraDireccionTitular">Escalera: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="escaleraDireccionTitular" 
		   				name="permisoInternacionalDto.titular.direccion.escalera" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
	   			</td>
	   			<td align="left" nowrap="nowrap" width="7%">
	   				<label for="pisoDireccionTitular">Piso: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="pisoDireccionTitular" 
		   				name="permisoInternacionalDto.titular.direccion.planta"
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
	   			</td>
	      	</tr>
	      	<tr>    			
	   			<td align="left" nowrap="nowrap">
	   				<label for="puertaDireccionTitular">Puerta: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="puertaDireccionTitular" 
		   				name="permisoInternacionalDto.titular.direccion.puerta" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	   				<label for="bloqueDireccionTitular">Bloque: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="bloqueDireccionTitular" 
		   				name="permisoInternacionalDto.titular.direccion.bloque" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	   				<label for="kmDireccionTitular">Km: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="kmDireccionTitular" 
		   				name="permisoInternacionalDto.titular.direccion.km" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
		   				onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	   				<label for="hmDireccionTitular">Hm: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="hmDireccionTitular" 
		   				name="permisoInternacionalDto.titular.direccion.hm" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
	   			</td>
	      	</tr>
		 </table>
		 <s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().puedeGenerarMandato(permisoInternacionalDto)}">
			 <table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		      	<tr>
					<td align="center">
						<input id="ibGenMandatoTit" type="button" class="botonGrande" name="bGenMandatoTit" value="Generar Mandato" onClick="generarMandato()" onkeyPress="this.onClick"/>
					</td>
				</tr>
			</table>
		</s:if>
	 </div>
</div>
<script>	 
	var viaTitular = new BasicContentAssist(document.getElementById('nombreViaTitular'), [], null, true);
	recargarComboMunicipios('idProvinciaTitular','idMunicipioTitular','municipioSeleccionadoTitular');
	recargarComboTipoVia('idProvinciaTitular','tipoViaTitular','tipoViaSeleccionadaTitular');
	recargarComboPueblos('idProvinciaTitular','municipioSeleccionadoTitular','idPuebloTitular', 'puebloSeleccionadoTitular');
	recargarNombreVias('idProvinciaTitular', 'municipioSeleccionadoTitular', 'tipoViaSeleccionadaTitular','nombreViaTitular', viaTitular);
	cambioDomicilioPermiso();
</script>
