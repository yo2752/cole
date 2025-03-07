<%@ taglib prefix="s" uri="/struts-tags" %>
  
<div class="contenido">		
<div id="divTitularDuplPermCond">

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left" style="100%">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del titular</td>
			<td align="right">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().existeTitular(duplicadoPermisoConducirDto)}">
					<td align="right"><img title="Ver evolución" onclick="consultaEvolucionPersona('<s:property value="duplicadoPermisoConducirDto.titular.persona.nif"/>','<s:property value="duplicadoPermisoConducirDto.titular.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png"></td>
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
	
	<s:hidden name="duplicadoPermisoConducirDto.titular.id"/>
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.fechaCaducidadNif.dia" />
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.fechaCaducidadNif.mes" />
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.fechaCaducidadNif.anio" />
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.fechaCaducidadAlternativo.dia" />
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.fechaCaducidadAlternativo.mes" />
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.fechaCaducidadAlternativo.anio" />
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.tipoDocumentoAlternativo" />
	
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.estado" />
	<s:hidden id ="idHiddenDireccionTitularDuplPermCond" name="duplicadoPermisoConducirDto.titular.direccion.idDireccion" />
	
	<s:hidden name="duplicadoPermisoConducirDto.titular.numColegiado" />
	<s:hidden name="duplicadoPermisoConducirDto.titular.nif"/>
	<s:hidden name="duplicadoPermisoConducirDto.titular.numExpediente"/>
	<s:hidden name="duplicadoPermisoConducirDto.titular.persona.anagrama"/>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="dniTitular">NIF / CIF: <span class="naranja">*</span></label>
				</td>
				<td  align="left" nowrap="nowrap" width="24%">
	       	    	<table style="align:left;" >
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield id="idNifTitularDPC" disabled="%{flagDisabled}"
				       				name="duplicadoPermisoConducirDto.titular.persona.nif" onblur="this.className='input2';calculaLetraNIF(this)" 
				       				onchange="limpiarFormularioTitular()"  onfocus="this.className='inputfocus';" style="text-transform : uppercase" size="9" maxlength="9"/>
							</td>
							<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esGuardable(duplicadoPermisoConducirDto)}">
								<td align="left" nowrap="nowrap">
									<input type="button" class="boton-persona" id="botonBuscarTitular" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
										onclick="javascript:buscarIntervinienteDuplPermCond()"/>
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
						name="duplicadoPermisoConducirDto.titular.persona.tipoPersona" listValue="nombreEnum"
						listKey="valorEnum" title="Tipo Persona" headerKey="" headerValue="-" disabled="true"/>
	       		</td>	
		    </tr>
	       	<tr>       
	       		<td align="left" nowrap="nowrap">
	   				<label for="sexoTitular">Sexo: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap" >
		         	<s:select id="sexoTitular" name="duplicadoPermisoConducirDto.titular.persona.sexo"
		         		disabled="%{flagDisabled}" list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
						listKey="valorEnum" title="Sexo Persona" headerKey="" headerValue="-"/>
	       		</td>
	       		<td align="left" nowrap="nowrap">
	       			<label for="primerApellidoTitular">Primer Ape./Razón Social: <span class="naranja">*</span></label>
	       		</td>
	       		<td align="left" nowrap="nowrap">
	       			<s:textfield id="idPrApeRzsSolicitanteDPC" name="duplicadoPermisoConducirDto.titular.persona.apellido1RazonSocial"  
	       				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:16em;" />
	       		</td> 	       			
	      	</tr>
	      	<tr>        	       			
	   			<td align="left" nowrap="nowrap">
	   				<label for="segundoApellidoTitular">Segundo Apellido: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="segundoApellidoTitular" name="duplicadoPermisoConducirDto.titular.persona.apellido2" 
	   					disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="30" maxlength="100"/>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	   				<label for="nombreTitular">Nombre: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="nombreTitular" name="duplicadoPermisoConducirDto.titular.persona.nombre" 
	   					disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
	   			</td>
	      	</tr>
	      	<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="idFechaNacimientoTitular">Fecha de Nacimiento: </label>
			</td>
			<td align="left" nowrap="nowrap" >
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="duplicadoPermisoConducirDto.titular.persona.fechaNacimiento.dia" 
								id="diaNacTitular" onkeypress="return validarDia(this,event)" 
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="duplicadoPermisoConducirDto.titular.persona.fechaNacimiento.mes" 
								id="mesNacTitular" onkeypress="return validarMes(this,event)" 
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2"/>
						</td>
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield disabled="%{flagDisabled}" name="duplicadoPermisoConducirDto.titular.persona.fechaNacimiento.anio" 
								id="anioNacTitular" onkeypress="return validarAnio(this,event)" 
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="4" maxlength="4"/>
						</td>
						<td align="left" nowrap="nowrap">
				    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacTitular, document.formData.mesNacTitular, document.formData.diaNacTitular);return false;" title="Seleccionar fecha">
				    			<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" ${displayDisabled} width="15" height="16" border="0" alt="Calendario"/>
				    		</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	      	<tr>
	      		<td align="left" nowrap="nowrap">
	   				<label for="paisNacTitular">País Nacimiento: <span class="naranja">*</span></label>
	   			</td>
	   			<td align="left" nowrap="nowrap" >
	   				<s:select id="paisNacTitular" name="duplicadoPermisoConducirDto.titular.persona.paisNacimiento"
		         		disabled="%{flagDisabled}" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().getListaPaises()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombre"
						listKey="sigla2" title="País Nacimiento" headerKey="" headerValue="Seleccione País"/>
					</td>
				<td align="left" nowrap="nowrap">
	   				<label for="nacionalidad">Nacionalidad: <span class="naranja">*</span></label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="nacionalidad" name="duplicadoPermisoConducirDto.titular.persona.nacionalidad" 
	   					disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
	   			</td>
	      	</tr>
	      	<tr>
	      		<td align="left" nowrap="nowrap">
	   				<label for="correoElectronico">Correo Electrónico: </label>
	   			</td>
	       		<td align="left" nowrap="nowrap">
	   				<s:textfield id="emailDPC" name="duplicadoPermisoConducirDto.titular.persona.correoElectronico" 
	   					disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="50" maxlength="100"/>
	   			</td>
	      	</tr>
		</table>
		
		<table class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle">
					<b id="idMensajeNotificacion" for="idMensajeNotificacion"> Dirección Notificación del Titular (está dirección pasará a ser la dirección que DGT dispondrá a efecto de notificaciones).
				</b>
				</td>
			</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		      	<tr>
		      		<td align="left" nowrap="nowrap">
						<label for="idProvinciaTitular">Provincia: <span class="naranja">*</span></label>
					</td>
		      	    <td align="left" nowrap="nowrap">
						<s:select id="idProvinciaTitular" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
							name="duplicadoPermisoConducirDto.titular.direccion.idProvincia" 
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
						<label for="idMunicipioTitular">Municipio: <span class="naranja">*</span></label>
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
				    	<s:hidden id="municipioSeleccionadoTitular" name="duplicadoPermisoConducirDto.titular.direccion.idMunicipio"/>
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
				    	<s:hidden id="puebloSeleccionadoTitular" name="duplicadoPermisoConducirDto.titular.direccion.pueblo"/>
		   			</td>
					
		   			<td align="left" nowrap="nowrap">
		   				<label for="cpTitular">Código Postal: <span class="naranja">*</span></label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">
		   				<s:textfield id="cpTitular" disabled="%{flagDisabled}"  
			   				name="duplicadoPermisoConducirDto.titular.direccion.codPostal" 
			   				onkeypress="return validarNumerosEnteros(event)" onblur="this.className='input2';" 
			   				onfocus="this.className='inputfocus';" size="5" maxlength="5"/>
		   			</td>
		      	</tr>
		      	
		      	<tr>        	       			
		   			<td align="left" nowrap="nowrap">
		   				<label for="tipoViaTitular">Tipo de vía: <span class="naranja">*</span></label>
		   			</td>
		   			<td align="left" nowrap="nowrap">
		         		<select id="tipoViaTitular" onblur="this.className='input2';" 
							${stringDisabled} onfocus="this.className='inputfocus';"
							onchange="seleccionarCampo('tipoViaSeleccionadaTitular','tipoViaTitular');
							cargarListaNombresVia('idProvinciaTitular', 'municipioSeleccionadoTitular', this, 'nombreViaTitular', viaTitularDuplicado);">
							<option value="-1">Seleccione Tipo Via</option>
						</select>
				    	<s:hidden id="tipoViaSeleccionadaTitular" name="duplicadoPermisoConducirDto.titular.direccion.idTipoVia"/>
		         	
		       		</td>
		   			
					<td align="left" nowrap="nowrap">
		   				<label for="nombreViaTitular">Nombre de vía: <span class="naranja">*</span></label>
		   			</td>
		   			
		   			<td align="left" nowrap="nowrap" colspan="6">
			       		<s:textfield id="nombreViaTitular" onblur="this.className='input2';"
			       			disabled="%{flagDisabled}" onfocus="this.className='inputfocus';"
							name="duplicadoPermisoConducirDto.titular.direccion.nombreVia"
				       		cssStyle="width:200px;position:relative;" autocomplete="off" maxlength="50"/>
		       		</td>        
				</tr>
		    </table>
		 	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left" style="100%">
		      <tr>    			
		   		<td align="left" nowrap="nowrap" width="7%">
		   			<label for="numeroDireccionTitular">Número: <span class="naranja">*</span></label>
		   		</td>
		       	<td align="left" nowrap="nowrap">
		   			<s:textfield id="numeroDireccionTitular"  
			   			name="duplicadoPermisoConducirDto.titular.direccion.numero" 
			   			disabled="%{flagDisabled}" onblur="this.className='input2';" 
			   			onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)"
			   			size="4" maxlength="4"/>
		   		</td>
		   		<td align="left" nowrap="nowrap" width="7%">
		   			<label for="letraDireccionTitular">Letra: </label>
		   		</td>
		       	<td align="left" nowrap="nowrap">
		   			<s:textfield id="letraDireccionTitular" 
			   			name="duplicadoPermisoConducirDto.titular.direccion.letra" 
			   			disabled="%{flagDisabled}" onblur="this.className='input2';" 
			   			onfocus="this.className='inputfocus';" onkeypress="return validarLetras(event)" 
			   			style="text-transform : uppercase" size="4" maxlength="3"/>
		   		</td>
		   		<td align="left" nowrap="nowrap" width="7%">
		   			<label for="escaleraDireccionTitular">Escalera: </label>
		   		</td>
		       	<td align="left" nowrap="nowrap">
		   			<s:textfield id="escaleraDireccionTitular" 
			   			name="duplicadoPermisoConducirDto.titular.direccion.escalera" 
			   			disabled="%{flagDisabled}" onblur="this.className='input2';" 
			   			onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
		   		</td>
		   		<td align="left" nowrap="nowrap" width="7%">
		   			<label for="pisoDireccionTitular">Piso: </label>
		   		</td>
		       	<td align="left" nowrap="nowrap">
		   			<s:textfield id="pisoDireccionTitular" 
			 			name="duplicadoPermisoConducirDto.titular.direccion.planta"
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
		   				name="duplicadoPermisoConducirDto.titular.direccion.puerta" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		  				onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="bloqueDireccionTitular">Bloque: </label>
				</td>
		   		<td align="left" nowrap="nowrap">
					<s:textfield id="bloqueDireccionTitular" 
		   				name="duplicadoPermisoConducirDto.titular.direccion.bloque" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="kmDireccionTitular">Km: </label>
				</td>
		   		<td align="left" nowrap="nowrap">
					<s:textfield id="kmDireccionTitular" 
		   				name="duplicadoPermisoConducirDto.titular.direccion.km" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
		   				onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="hmDireccionTitular">Hm: </label>
				</td>
		   		<td align="left" nowrap="nowrap">
					<s:textfield id="hmDireccionTitular" 
		   				name="duplicadoPermisoConducirDto.titular.direccion.hm" 
		   				disabled="%{flagDisabled}" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" onkeypress="return validarNumerosEnteros(event)" size="4" maxlength="3"/>
				</td>
		   	</tr>
		</table>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().puedeGenerarMandato(duplicadoPermisoConducirDto)}">
			 <table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		      	<tr>
					<td align="center">
						<input id="ibGenMandatoTitDPC" type="button" class="botonGrande" name="bGenMandatoTitDPC" value="Generar Mandato" onClick="generarMandatoDPC()" onkeyPress="this.onClick"/>
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
</script>
