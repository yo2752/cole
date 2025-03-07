
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>

<s:form method="post" id="formData" name="formData" cssStyle="100%">
<div class="contenido">		
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<s:hidden name="reconocimiento.idReconocimiento" />
	<s:hidden name="reconocimiento.fechaAlta" />
	<s:hidden name="reconocimiento.colegiado.numColegiado" />
	<s:hidden name="reconocimiento.contrato.idContrato" />
	<s:hidden name="reconocimiento.persona.id.numColegiado" />
<!-- Evitamos que se pierdan la caducidad de la documentación  -->
<s:hidden name="reconocimiento.persona.fechaCaducidadNIF" />
<s:hidden name="reconocimiento.persona.tipoDocumentoAlternativo" />
<s:hidden name="reconocimiento.persona.fechaCaducidadAlternativo" />
<!-- Fin de evitar que se pierda la caducidad de la documentación  -->
	<s:if test="%{permisoClinica}">
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del Reconocimiento</span>		
			</td>
		</tr>
	</table>


		<table cellSpacing="2" class="tablaformbasica" cellPadding="0"
			align="left" border="0">
			<tr>
				<td align="left" nowrap="nowrap"><label for="idClinica">Clínica: <span class="naranja">*</span>
				</label></td>
				<td align="left" nowrap="nowrap">
						<s:hidden id="idClinica" name="reconocimiento.clinica.idContrato" value="%{clinicas.get(0).getIdContrato()}"></s:hidden>
						<s:property value="%{clinicas.get(0).getRazonSocial()}" />
				</td>

				<td align="left" nowrap="nowrap"><label for="idEstado">Estado:
				</label></td>
				<td align="left">
    					<s:hidden id="idEstado" name="reconocimiento.estado" />
						<s:property value="%{@general.utiles.enumerados.EstadoReconocimientoMedico@convertirTexto(reconocimiento.estado)}" />
				</td>

			</tr>
			<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="diaCita">Fecha de la cita:  <span class="naranja">*</span></label>
			</td>
				<td colspan="3" align="left" nowrap="nowrap" >
			<table>
						<tr>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaReconocimiento.dia" 
									id="diaCita"
									onblur="this.className='input2';" 
									onfocus="this.className='inputfocus';" 								
									size="2" maxlength="2"
									readonly="true" disabled="true"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaReconocimiento.mes" 
									id="mesCita"
									onblur="this.className='input2';" 
									onfocus="this.className='inputfocus';"								
									size="2" maxlength="2"
									readonly="true" disabled="true"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaReconocimiento.anio" 
									id="anioCita"
									onblur="this.className='input2';" 
									onfocus="this.className='inputfocus';" 								
									size="4" maxlength="4"
									readonly="true" disabled="true"/>
							</td>
							
							<td align="left" nowrap="nowrap" width="5%" >
					    		<a href="javascript:;"  
					    				title="Seleccionar fecha">
					    			<img class="PopcalTrigger" 
					    				align="left" 
					    				src="img/ico_calendario.gif" 
					    				width="15" height="16" 
					    				border="0" alt="Calendario"/>
					    		</a>
							</td>
							<td align="left" nowrap="nowrap" width="5%">
									<s:textfield name="fechaReconocimiento.hora" 
										id="horaCita"
										onblur="this.className='input2';" 
										onfocus="this.className='inputfocus';" 								
										size="2" maxlength="2"
										readonly="true" disabled="true"/>
							</td>
								
							<td width="1%">:</td>
								
							<td align="left" nowrap="nowrap" >
									<s:textfield name="fechaReconocimiento.minutos" 
										id="minutoCita"
										onblur="this.className='input2';" 
										onfocus="this.className='inputfocus';"								
										size="2" maxlength="2"
										readonly="true" disabled="true"/>
							</td>
						</tr>
					</table>
				</td>			
			</tr>
			<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="diaVisita">Fecha real de visita:</label>
			</td>
				<td colspan="3" align="left" nowrap="nowrap" >
			<table>
						<tr>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.dia"
									id="diaVisita" 			
									size="2" maxlength="2"
									 readonly="true" disabled="true"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.mes" 
									size="2" maxlength="2"
									 readonly="true" disabled="true"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.anio" 								
									size="4" maxlength="4"
									 readonly="true" disabled="true"/>
							</td>
							
							<td align="left" nowrap="nowrap" width="5%" >
					    		<a href="javascript:;"  
					    				title="Seleccionar fecha">
					    			<img class="PopcalTrigger" 
					    				align="left" 
					    				src="img/ico_calendario.gif" 
					    				width="15" height="16" 
					    				border="0" alt="Calendario" />
					    		</a>
							</td>
							<td align="left" nowrap="nowrap" width="5%">
									<s:textfield name="fechaVisita.hora" 								
										size="2" maxlength="2"
										 readonly="true" disabled="true" />
							</td>
								
							<td width="1%">:</td>
								
							<td align="left" nowrap="nowrap" >
									<s:textfield name="fechaVisita.minutos"							
										size="2" maxlength="2"
										 readonly="true" disabled="true" />
							</td>
						</tr>
					</table>
				</td>			
			</tr>
		</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos de la cita</span>		
			</td>
		</tr>
	</table>

	<table cellSpacing="2" class="tablaformbasica" cellPadding="0" align="left" border="0">		
		<tr>
			<td align="left" nowrap="nowrap" width="20%" >
				<label for="nifCliente">Tipo de reconocimiento: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="25%">
				<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoTramiteRenovacion()"
					headerKey="-1" headerValue="Seleccione tipo de renovación" 
	           		onblur="this.className='input2';" 
	      				onfocus="this.className='inputfocus';"
					name="reconocimiento.tipoTramiteRenovacion.idTipoTramiteRenovacion" 
					listKey="idTipoTramiteRenovacion" listValue="descripcion"
					id="idTipoTramiteRenovacion"
					readonly="true" disabled="true"/>
       	    </td>
       	    
       	    <td align="left" nowrap="nowrap" width="5%">
      			<label for="tipoPersonaTitular">Importe: </label>
      		</td>

        	<td align="left" nowrap="nowrap">
        		<s:textfield id="importe" name="importe" readonly="true" disabled="true" />
      		</td>
       	    
       	</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del Cliente</span>		
			</td>
		</tr>
	</table>
	<table cellSpacing="2" class="tablaformbasica" cellPadding="0" align="left" border="0">		
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="nifCliente">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
       	    	<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
						<s:hidden name="reconocimiento.persona.id.nif" />
						<s:textfield name="reconocimiento.persona.id.nif" 
		       				id="nifTitular" 
	       					size="9" maxlength="9" readonly="true" disabled="true"
	       				/>
						</td>
						<td align="left" nowrap="nowrap">
							&nbsp;
						</td>
					</tr>
				</table>
       	    </td>
       	    
       	    <td align="left" nowrap="nowrap">
      			<label for="tipoPersonaTitular">Tipo de persona: </label>
      		</td>

        	<td align="left" nowrap="nowrap">
         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaTitular"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="reconocimiento.persona.tipoPersona"
					value="%{reconocimiento.persona.tipoPersona}"
					listValue="nombreEnum"
					listKey="valorEnum"
					title="Tipo Persona"
					headerKey="-1" headerValue="-"
					disabled="true"/>
      		</td>
       	    
       	</tr>
       
    	<tr> 
    		<td align="left" nowrap="nowrap">
       			<label for="sexoCliente">Sexo: </label>
       		</td>
         	<td align="left" nowrap="nowrap">
	         	 <s:textfield id="sexoCliente" name="reconocimiento.persona.sexo" size="2" readonly="true" disabled="true"/>
       		</td>   			
    		<td align="left" nowrap="nowrap" >
		       	<label for="apellido1RazonSocialCliente">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
		    </td>				        		
		    <td align="left" nowrap="nowrap">
		       	<s:textfield id="apellido1RazonSocialCliente" name="reconocimiento.persona.apellido1RazonSocial" size="20" maxlength="100" readonly="true" disabled="true"/>
		    </td>
       	</tr>
	
		<tr>			
       		<td align="left" nowrap="nowrap">
       			<label for="apellido2Cliente">Segundo Apellido: </label>
       		</td>				        		
       		<td align="left" nowrap="nowrap">
       	       	<s:textfield id="apellido2Cliente" name="reconocimiento.persona.apellido2" size="20" maxlength="100" readonly="true" disabled="true"/>
       		</td>
       		  <td align="left" nowrap="nowrap">
       			<label for="nombreCliente">Nombre: <span class="naranja">*</span></label>
       		</td>				        		       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="nombreCliente" name="reconocimiento.persona.nombre" size="20" maxlength="100" readonly="true" disabled="true"/>
       		</td>
       		
       		
  		</tr>
  		
  		<tr>
      		
       		<td align="left" nowrap="nowrap">
       			<label for="nombreTitular">Anagrama <span class="naranja">*</span></label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield 
       				name="reconocimiento.persona.anagrama" 
       				id="anagramaTitular" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="5" maxlength="4"
       				readonly="true" disabled="true"/>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<s:hidden id="idDireccion" name="reconocimiento.persona.direccionActual.idDireccion"/>
			</td>	
      	</tr>       		

		<tr>

			<td align="left" nowrap="nowrap" width="17%">
				<label for="diaNacTitular">Fecha de Nacimiento: </label>
			</td>
		<td align="left" nowrap="nowrap" >
			<table>
				<tr>
					<td align="left" nowrap="nowrap" width="5%">
						<s:textfield name="fnacimiento.dia" 
							id="diaNacTitular"
							onblur="this.className='input2';" 
							onkeypress="return validarNumerosEnteros(event)"
							onfocus="this.className='inputfocus';" 								
							size="2" maxlength="2" readonly="true" disabled="true"/>
					</td>
					
					<td width="1%">/</td>
					
					<td align="left" nowrap="nowrap" width="5%">
						<s:textfield name="fnacimiento.mes" 
							id="mesNacTitular"							
							size="2" maxlength="2" readonly="true" disabled="true"/>
					</td>
					
					<td width="1%">/</td>
					
					<td align="left" nowrap="nowrap" width="5%">
						<s:textfield name="fnacimiento.anio" 
							id="anioNacTitular" 								
							size="4" maxlength="4" readonly="true" disabled="true"/>
					</td>
					
					<td align="left" nowrap="nowrap" >
			    		&nbsp;
					</td>
				</tr>
			</table>
		</td>			
			
			<td align="left" nowrap="nowrap">
      			<label for="estadoCivilTitular">Estado Civil: </label>
      		</td>				        	       	       			
      					
		<td align="left" nowrap="nowrap">
			<s:textfield id="estadoCivilTitular" name="reconocimiento.persona.estadoCivil" size="9" readonly="true" disabled="true"/>						
		</td>
			
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="telefonoCliente">Teléfono: <span class="naranja">*</span></label>
			</td>		
       		<td align="left" nowrap="nowrap" >
				<s:textfield id="telefonoCliente" name="reconocimiento.persona.telefonos" size="9" maxlength="9" readonly="true" disabled="true"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="correoElectronicoCliente">Correo Electrónico: </label>
			</td>   	       			
	        <td align="left" nowrap="nowrap" colspan="2">
				<s:textfield id="correoElectronicoCliente" name="reconocimiento.persona.correoElectronico" size="20" maxlength="30" readonly="true" disabled="true"/>
			</td>
		</tr>
		<tr>
		
       		
       		<td align="left" nowrap="nowrap">
       			<label for="diaCadCarnet">Caducidad Carné Conducir: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" >
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="fcaducidad.dia" 
								id="diaCadCarnet" readonly="true" disabled="true" size="2"/>
						</td>
						
						<td width="1%">/</td>
						
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="fcaducidad.mes" 
								id="mesCadCarnet" readonly="true" disabled="true" size="2" />
						</td>
						
						<td width="1%">/</td>
						
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="fcaducidad.anio"
										id="anioCadCarnet" readonly="true" disabled="true" size="2" />
								</td>
						
						<td align="left" nowrap="nowrap" >
				    		&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>	
	</table>

	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idProvinciaPersonaFacturacion">Provincia: <span class="naranja">*</span></label>
			</td>   
			    	       			
       		<td align="left" nowrap="nowrap">
		    	<s:hidden name="reconocimiento.persona.direccionActual.municipio.provincia.idProvincia"/>
				<s:textfield name="reconocimiento.persona.direccionActual.municipio.provincia.nombre" size="20" maxlength="30" readonly="true" disabled="true"/>				
		</td>
	
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioCliente">Municipio: <span class="naranja">*</span></label>	
			</td>
       		<td align="left" nowrap="nowrap">
				<s:textfield name="reconocimiento.persona.direccionActual.municipio.nombre" size="20" maxlength="30" readonly="true" disabled="true"/>
		    	<s:hidden name="reconocimiento.persona.direccionActual.municipio.id.idMunicipio"/>
			</td>
		</tr>
		
		<tr>									
			<td align="left" nowrap="nowrap">
				<label for="idPuebloCliente">Pueblo: </label>			
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="reconocimiento.persona.direccionActual.pueblo" size="20" maxlength="30" readonly="true" disabled="true"/>
			</td>						
			
			<td align="left" nowrap="nowrap">
				<label for="codPostalClienteFacturacion">Código Postal: <span class="naranja">*</span></label>
			</td>       	       			
       		<td align="left" nowrap="nowrap">
	        	<s:textfield id="codPostalClienteFacturacion" name="reconocimiento.persona.direccionActual.codPostal" size="5" maxlength="5" readonly="true" disabled="true"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaDireccionCliente">Tipo vía: <span class="naranja">*</span></label>
			</td>				        		
       	    
       	    <td align="left" nowrap="nowrap">
				<s:textfield name="reconocimiento.persona.direccionActual.idTipoVia" size="20" maxlength="30" readonly="true" disabled="true"/>
       		</td>
			
			<td align="left" nowrap="nowrap">
				<label for="nombreVia">Nombre vía: <span class="naranja">*</span></label>
			</td>		
			<td align="left" nowrap="nowrap" colspan="3">
	       		<s:textfield id="nombreVia" cssStyle="width:200px;position:relative;" name="reconocimiento.persona.direccionActual.nombreVia" size="40" maxlength="100" readonly="true" disabled="true" />
       		</td>        
       		
		</tr>
	</table>
	
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>	
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionCliente">N&uacute;mero: <span class="naranja">*</span></label>
			</td>       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield id="numeroDireccionCliente" name="reconocimiento.persona.direccionActual.numero" size="2" maxlength="3" readonly="true" disabled="true"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionCliente">Letra: </label>
			</td>		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="letraDireccionCliente" name="reconocimiento.persona.direccionActual.letra" size="2" maxlength="3" readonly="true" disabled="true"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionCliente">Escalera: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="escaleraDireccionCliente" name="reconocimiento.persona.direccionActual.escalera" size="7" maxlength="10" readonly="true" disabled="true"/>				
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionCliente">Piso: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield id="pisoDireccionCliente" name="reconocimiento.persona.direccionActual.planta" size="2" maxlength="3" readonly="true" disabled="true"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionCliente">Puerta: </label>
			</td>       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield id="puertaDireccionCliente" name="reconocimiento.persona.direccionActual.puerta" size="2" maxlength="5" readonly="true" disabled="true"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionCliente">Bloque: </label>
			</td>		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="bloqueDireccionCliente" name="reconocimiento.persona.direccionActual.bloque" size="2" maxlength="3" readonly="true" disabled="true"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="kmDireccionCliente">Km: </label>
			</td>		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="kmDireccionCliente" name="reconocimiento.persona.direccionActual.km" size="2" maxlength="3" readonly="true" disabled="true"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="hmDireccionCliente">Hm: </label>
			</td>		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="hmDireccionCliente" name="reconocimiento.persona.direccionActual.hm" size="2" maxlength="3" readonly="true" disabled="true"/>
			</td>
		</tr>
	</table>
	</s:if>
	<s:else><table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del Reconocimiento</span>		
			</td>
		</tr>
	</table>


		<table cellSpacing="2" class="tablaformbasica" cellPadding="0"
			align="left" border="0">
			<tr>
				<td align="left" nowrap="nowrap"><label for="idClinica">Clínica:
						<span class="naranja">*</span>
				</label></td>
				<td align="left" nowrap="nowrap"><s:if
						test="%{clinicas.size()==1}">
						<s:hidden id="idClinica" name="reconocimiento.clinica.idContrato"
							value="%{clinicas.get(0).getIdContrato()}"></s:hidden>
						<s:property value="%{clinicas.get(0).getRazonSocial()}" />
					</s:if> <s:else>
						<s:select list="clinicas" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" listKey="idContrato"
							listValue="razonSocial" cssStyle="width:150px" id="idClinica"
							name="reconocimiento.clinica.idContrato" emptyOption="true"></s:select>
					</s:else>
				</td>

				<td align="left" nowrap="nowrap"><label for="idEstado">Estado:
				</label></td>
				<td align="left">
    					<s:hidden id="idEstado" name="reconocimiento.estado" />
						<s:property value="%{@general.utiles.enumerados.EstadoReconocimientoMedico@convertirTexto(reconocimiento.estado)}" />
				</td>

			</tr>
			<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="diaCita">Fecha de la cita:  <span class="naranja">*</span></label>
			</td>
				<td colspan="3" align="left" nowrap="nowrap" >
			<table>
						<tr>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaReconocimiento.dia" 
									id="diaCita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" 								
									size="2" maxlength="2"
									onchange="obtenerImporteReconocimientoMedico()"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaReconocimiento.mes" 
									id="mesCita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';"								
									size="2" maxlength="2"
									onchange="obtenerImporteReconocimientoMedico()"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaReconocimiento.anio" 
									id="anioCita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" 								
									size="4" maxlength="4"
									onchange="obtenerImporteReconocimientoMedico()"/>
							</td>
							
							<td align="left" nowrap="nowrap" width="5%" >
					    		<a href="javascript:;" 
					    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCita, document.formData.mesCita, document.formData.diaCita); capturaEvento(); return false;" 
					    				title="Seleccionar fecha">
					    			<img class="PopcalTrigger" 
					    				align="left" 
					    				src="img/ico_calendario.gif" 
					    				width="15" height="16" 
					    				border="0" alt="Calendario"/>
					    		</a>
							</td>
							<td align="left" nowrap="nowrap" width="5%">
									<s:textfield name="fechaReconocimiento.hora" 
										id="horaCita"
										onblur="this.className='input2';" 
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" 								
										size="2" maxlength="2"/>
							</td>
								
							<td width="1%">:</td>
								
							<td align="left" nowrap="nowrap" >
									<s:textfield name="fechaReconocimiento.minutos" 
										id="minutoCita"
										onblur="this.className='input2';" 
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';"								
										size="2" maxlength="2"/>
							</td>
						</tr>
					</table>
				</td>			
			</tr>
			<tr>
			<td align="left" nowrap="nowrap" width="17%">
				<label for="diaVisita">Fecha real de visita:</label>
			</td>
				
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio() }">
			
				<td colspan="3" align="left" nowrap="nowrap" >
			<table>
						<tr>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.dia" 
									id="diaVisita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" 								
									size="2" maxlength="2"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.mes" 
									id="mesVisita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" 								
									size="2" maxlength="2"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.anio" 
									id="anioVisita"
									onblur="this.className='input2';" 
									onkeypress="return validarNumerosEnteros(event)"
									onfocus="this.className='inputfocus';" 								
									size="4" maxlength="4"/>
							</td>
							
							<td align="left" nowrap="nowrap" width="5%" >
					    		<a href="javascript:;" 
					    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioVisita, document.formData.mesVisita, document.formData.diaVisita);return false;" 
					    				title="Seleccionar fecha">
					    			<img class="PopcalTrigger" 
					    				align="left" 
					    				src="img/ico_calendario.gif" 
					    				width="15" height="16" 
					    				border="0" alt="Calendario"/>
					    		</a>
							</td>
							<td align="left" nowrap="nowrap" width="5%">
									<s:textfield name="fechaVisita.hora" 								
										size="2" maxlength="2"  />
							</td>
								
							<td width="1%">:</td>
								
							<td align="left" nowrap="nowrap" >
									<s:textfield name="fechaVisita.minutos"							
										size="2" maxlength="2" />
							</td>
						</tr>
					</table>
				</td>		
			</s:if>
			<s:else>
				<td colspan="3" align="left" nowrap="nowrap" >
				<table>
						<tr>
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.dia" 
									id="diaVisita"
									onblur="this.className='input2';" 
									onfocus="this.className='inputfocus';" 								
									size="2" maxlength="2"
									readonly="true" disabled="true"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.mes" 
									id="mesVisita"
									onblur="this.className='input2';" 
									onfocus="this.className='inputfocus';" 								
									size="2" maxlength="2"
									readonly="true" disabled="true"/>
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield name="fechaVisita.anio" 
									id="anioVisita"
									onblur="this.className='input2';" 
									onfocus="this.className='inputfocus';" 								
									size="4" maxlength="4"
									readonly="true" disabled="true"/>
							</td>
							
							<td align="left" nowrap="nowrap" width="5%" >
					    		<a href="javascript:;" 
					    				title="Seleccionar fecha">
					    			<img class="PopcalTrigger" 
					    				align="left" 
					    				src="img/ico_calendario.gif" 
					    				width="15" height="16" 
					    				border="0" alt="Calendario"/>
					    		</a>
							</td>
							<td align="left" nowrap="nowrap" width="5%">
									<s:textfield name="fechaVisita.hora" 								
										size="2" maxlength="2"
									readonly="true" disabled="true"  />
							</td>
								
							<td width="1%">:</td>
								
							<td align="left" nowrap="nowrap" >
									<s:textfield name="fechaVisita.minutos"							
										size="2" maxlength="2"
									readonly="true" disabled="true" />
							</td>
						</tr>
					</table>
				</td>		
			</s:else>	
			</tr>
		</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos de la cita</span>		
			</td>
		</tr>
	</table>

	<table cellSpacing="2" class="tablaformbasica" cellPadding="0" align="left" border="0">		
		<tr>
			<td align="left" nowrap="nowrap" width="20%" >
				<label for="nifCliente">Tipo de reconocimiento: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="25%">
				<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoTramiteRenovacion()"
					headerKey="-1" headerValue="Seleccione tipo de renovación" 
	           		onblur="this.className='input2';" 
	      				onfocus="this.className='inputfocus';"
					name="reconocimiento.tipoTramiteRenovacion.idTipoTramiteRenovacion" 
					listKey="idTipoTramiteRenovacion" listValue="descripcion"
					id="idTipoTramiteRenovacion"
					onchange="obtenerImporteReconocimientoMedico()"/>
       	    </td>
       	    
       	    <td align="left" nowrap="nowrap" width="5%">
      			<label for="tipoPersonaTitular">Importe: </label>
      		</td>

        	<td align="left" nowrap="nowrap">
        		<s:textfield id="importe" name="importe" readonly="true" disabled="true" />
      		</td>
       	    
       	</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos del Cliente</span>		
			</td>
		</tr>
	</table>
	<table cellSpacing="2" class="tablaformbasica" cellPadding="0" align="left" border="0">		
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="nifCliente">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
       	    	<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
						<s:textfield name="reconocimiento.persona.id.nif" 
		       				id="nifTitular" 
		       				onblur="this.className='input2';calculaLetraNIF(this)" 
	       					onfocus="this.className='inputfocus';" 
	       					onchange="limpiarFormularioClienteFacturacion()"
	       					size="9" maxlength="9"
	       				/>
						</td>
						<td align="left" nowrap="nowrap">
							<input type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
								onclick="javascript:buscarPersonaReconocimiento();"/>
						</td>
					</tr>
				</table>
       	    </td>
       	    
       	    <td align="left" nowrap="nowrap">
      			<label for="tipoPersonaTitular">Tipo de persona: </label>
      		</td>

        	<td align="left" nowrap="nowrap">
         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
					id="tipoPersonaTitular"
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="reconocimiento.persona.tipoPersona"
					value="%{reconocimiento.persona.tipoPersona}"
					listValue="nombreEnum"
					listKey="valorEnum"
					title="Tipo Persona"
					headerKey="-1" headerValue="-"
					disabled="true"/>
      		</td>
       	    
       	</tr>
       
    	<tr> 
    		<td align="left" nowrap="nowrap">
       			<label for="sexoCliente">Sexo: </label>
       		</td>
         	<td align="left" nowrap="nowrap">
	         	     	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboSexo()"
						id="sexoCliente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="reconocimiento.persona.sexo"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Sexo Persona"
						headerKey="-" headerValue="-"/>
       		</td>   			
    		<td align="left" nowrap="nowrap" >
		       	<label for="apellido1RazonSocialCliente">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
		    </td>				        		
		    <td align="left" nowrap="nowrap">
		       	<s:textfield id="apellido1RazonSocialCliente" name="reconocimiento.persona.apellido1RazonSocial" size="20" maxlength="100"/>
		    </td>
       	</tr>
	
		<tr>			
       		<td align="left" nowrap="nowrap">
       			<label for="apellido2Cliente">Segundo Apellido: </label>
       		</td>				        		
       		<td align="left" nowrap="nowrap">
       	       	<s:textfield id="apellido2Cliente" name="reconocimiento.persona.apellido2" size="20" maxlength="100"/>
       		</td>
       		  <td align="left" nowrap="nowrap">
       			<label for="nombreCliente">Nombre: <span class="naranja">*</span></label>
       		</td>				        		       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="nombreCliente" name="reconocimiento.persona.nombre" size="20" maxlength="100"/>
       		</td>
       		
       		
  		</tr>
  		
  		<tr>
      		
       		<td align="left" nowrap="nowrap">
       			<label for="nombreTitular">Anagrama <span class="naranja">*</span></label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield 
       				name="reconocimiento.persona.anagrama" 
       				id="anagramaTitular" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="5" maxlength="4"
       				readonly="true" disabled="true"/>
       		</td>
       		
       		<td align="left" nowrap="nowrap">
       			<s:hidden id="idDireccion" name="reconocimiento.persona.direccionActual.idDireccion"/>
			</td>	
      	</tr>       		

		<tr>

			<td align="left" nowrap="nowrap" width="17%">
				<label for="diaNacTitular">Fecha de Nacimiento: </label>
			</td>
		<td align="left" nowrap="nowrap" >
			<table>
				<tr>
					<td align="left" nowrap="nowrap" width="5%">
						<s:textfield name="fnacimiento.dia" 
							id="diaNacTitular"
							onblur="this.className='input2';" 
							onkeypress="return validarNumerosEnteros(event)"
							onfocus="this.className='inputfocus';" 								
							size="2" maxlength="2"/>
					</td>
					
					<td width="1%">/</td>
					
					<td align="left" nowrap="nowrap" width="5%">
						<s:textfield name="fnacimiento.mes" 
							id="mesNacTitular"
							onblur="this.className='input2';" 
							onkeypress="return validarNumerosEnteros(event)"
							onfocus="this.className='inputfocus';"								
							size="2" maxlength="2"/>
					</td>
					
					<td width="1%">/</td>
					
					<td align="left" nowrap="nowrap" width="5%">
						<s:textfield name="fnacimiento.anio" 
							id="anioNacTitular"
							onblur="this.className='input2';" 
							onkeypress="return validarNumerosEnteros(event)"
							onfocus="this.className='inputfocus';" 								
							size="4" maxlength="4"/>
					</td>
					
					<td align="left" nowrap="nowrap" >
			    		<a href="javascript:;" 
			    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioNacTitular, document.formData.mesNacTitular, document.formData.diaNacTitular);return false;" 
			    				title="Seleccionar fecha">
			    			<img class="PopcalTrigger" 
			    				align="left" 
			    				src="img/ico_calendario.gif" 
			    				width="15" height="16" 
			    				border="0" alt="Calendario"/>
			    		</a>
					</td>
				</tr>
			</table>
		</td>			
			
			<td align="left" nowrap="nowrap">
      			<label for="estadoCivilTitular">Estado Civil: </label>
      		</td>				        	       	       			
      					
		<td align="left" nowrap="nowrap">
			<s:select  
				onblur="this.className='input2';"
				onfocus="this.className='inputfocus';"
				name="reconocimiento.persona.estadoCivil" 
      				id="estadoCivilTitular" 					
				list="@escrituras.utiles.UtilesVista@getInstance().getComboEstadosCivil()"
				listKey="valorEnum"
				listValue="nombreEnum"
				headerKey="-"
	    	    headerValue="Estado Civil"/>							
		</td>
			
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="telefonoCliente">Teléfono: <span class="naranja">*</span></label>
			</td>		
       		<td align="left" nowrap="nowrap" >
				<s:textfield id="telefonoCliente" name="reconocimiento.persona.telefonos" size="9" maxlength="9"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="correoElectronicoCliente">Correo Electrónico: </label>
			</td>   	       			
	        <td align="left" nowrap="nowrap" colspan="2">
				<s:textfield id="correoElectronicoCliente" name="reconocimiento.persona.correoElectronico" size="20" maxlength="30"/>
			</td>
		</tr>
		<tr>
		
       		
       		<td align="left" nowrap="nowrap">
       			<label for="diaCadCarnet">Caducidad Carné Conducir: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" >
				<table>
					<tr>
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="fcaducidad.dia" 
								id="diaCadCarnet"
								onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" 								
								size="2" maxlength="2"/>
						</td>
						
						<td width="1%">/</td>
						
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="fcaducidad.mes" 
								id="mesCadCarnet"
								onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';"								
								size="2" maxlength="2"/>
						</td>
						
						<td width="1%">/</td>
						
						<td align="left" nowrap="nowrap" width="5%">
							<s:textfield name="fcaducidad.anio"
										id="anioCadCarnet" onblur="this.className='input2';"
										onkeypress="return validarNumerosEnteros(event)"
										onfocus="this.className='inputfocus';" size="4" maxlength="4" />
								</td>
						
						<td align="left" nowrap="nowrap" >
				    		<a href="javascript:;" 
				    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioCadCarnet, document.formData.mesCadCarnet, document.formData.diaCadCarnet);return false;" 
				    				title="Seleccionar fecha">
				    			<img class="PopcalTrigger" 
				    				align="left" 
				    				src="img/ico_calendario.gif" 
				    				width="15" height="16" 
				    				border="0" alt="Calendario"/>
				    		</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>	
	</table>

	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="idProvinciaPersonaFacturacion">Provincia: <span class="naranja">*</span></label>
			</td>   
			    	       			
       		<td align="left" nowrap="nowrap">
			<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvinciasCliente()"
				headerKey="-1" headerValue="Seleccione Provincia" 
           		onblur="this.className='input2';" 
      				onfocus="this.className='inputfocus';"
				name="reconocimiento.persona.direccionActual.municipio.provincia.idProvincia" 
				listKey="idProvincia" listValue="nombre"
				id="idProvinciaPersonaFacturacion"
				onchange="cargarListaMunicipios(this,'idMunicipioPersonaFacturacion','municipioSeleccionadoPersonaFacturacion');
				cargarListaTipoVia(this,'tipoViaDireccionPersonaFacturacion','tipoViaSeleccionadaPersonaFacturacion');
				inicializarTipoVia('tipoViaDireccionPersonaFacturacion','nombreViaPersonaFacturacion',viaPersonaFacturacion);
				borrarComboPueblo('idPuebloPersonaFacturacion');"/>					
		</td>
	
			<td align="left" nowrap="nowrap">
				<label for="idMunicipioCliente">Municipio: <span class="naranja">*</span></label>	
			</td>
       		<td align="left" nowrap="nowrap">			
				<select id="idMunicipioPersonaFacturacion"  
					onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';"
					onchange="cargarListaPueblos('idProvinciaPersonaFacturacion', this, 'idPuebloPersonaFacturacion', 'puebloSeleccionadoPersonaFacturacion');
					seleccionarCampo('municipioSeleccionadoPersonaFacturacion','idMunicipioPersonaFacturacion');						
					obtenerCodigoPostalPorMunicipio('idProvinciaPersonaFacturacion', this, 'codPostalClienteFacturacion');
					inicializarTipoVia('tipoViaDireccionPersonaFacturacion','nombreViaPersonaFacturacion',viaPersonaFacturacion);">						
					<option value="-1">Seleccione Municipio</option>
				</select>						  
		    	<s:hidden id="municipioSeleccionadoPersonaFacturacion" name="reconocimiento.persona.direccionActual.municipio.id.idMunicipio"/>
			</td>
		</tr>
		
		<tr>									
			<td align="left" nowrap="nowrap">
				<label for="idPuebloCliente">Pueblo: </label>			
			</td>
			<td align="left" nowrap="nowrap">				
				<select id="idPuebloPersonaFacturacion" onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';"
					onchange="seleccionarCampo('puebloSeleccionadoPersonaFacturacion','idPuebloPersonaFacturacion');
					document.getElementById('idPuebloIVTM').value=this.options[this.selectedIndex].text;"
					style="width:200px;" >
					<option value="-1">Seleccione Pueblo</option>
				</select>						  
			   	<s:hidden id="puebloSeleccionadoPersonaFacturacion" name="reconocimiento.persona.direccionActual.pueblo"/>
			</td>						
			
			<td align="left" nowrap="nowrap">
				<label for="codPostalClienteFacturacion">Código Postal: <span class="naranja">*</span></label>
			</td>       	       			
       		<td align="left" nowrap="nowrap">
	        	<s:textfield id="codPostalClienteFacturacion" name="reconocimiento.persona.direccionActual.codPostal" size="5" maxlength="5"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaDireccionCliente">Tipo vía: <span class="naranja">*</span></label>
			</td>				        		
       	    
       	    <td align="left" nowrap="nowrap">         	
	         	<select id="tipoViaDireccionPersonaFacturacion" 
	         			name="factura.persona.direccionActual.tipoVia"
						onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';"
						onchange="seleccionarCampo('tipoViaSeleccionadaPersonaFacturacion','tipoViaDireccionPersonaFacturacion');
								cargarListaNombresVia('idProvinciaPersonaFacturacion', 'municipioSeleccionadoPersonaFacturacion', this, 'nombreViaPersonaFacturacion',viaPersonaFacturacion);" >
						<option value="-1">Seleccione Tipo Via</option>
				</select>
				<s:hidden id="tipoViaSeleccionadaPersonaFacturacion" 
	    		name="reconocimiento.persona.direccionActual.idTipoVia"/>
       		</td>
			
			<td align="left" nowrap="nowrap">
				<label for="nombreViaPersonaFacturacion">Nombre vía: <span class="naranja">*</span></label>
			</td>		
			<td align="left" nowrap="nowrap" colspan="3">
	       		<s:textfield id="nombreViaPersonaFacturacion" cssStyle="width:200px;position:relative;" name="reconocimiento.persona.direccionActual.nombreVia" size="40" maxlength="100" />
       		</td>        
       		
		</tr>
	</table>
	
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>	
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionCliente">N&uacute;mero: <span class="naranja">*</span></label>
			</td>       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield id="numeroDireccionCliente" name="reconocimiento.persona.direccionActual.numero" size="2" maxlength="3"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionCliente">Letra: </label>
			</td>		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="letraDireccionCliente" name="reconocimiento.persona.direccionActual.letra" size="2" maxlength="3"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionCliente">Escalera: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="escaleraDireccionCliente" name="reconocimiento.persona.direccionActual.escalera" size="7" maxlength="10"/>				
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionCliente">Piso: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield id="pisoDireccionCliente" name="reconocimiento.persona.direccionActual.planta" size="2" maxlength="3"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionCliente">Puerta: </label>
			</td>       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield id="puertaDireccionCliente" name="reconocimiento.persona.direccionActual.puerta" size="2" maxlength="5"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="bloqueDireccionCliente">Bloque: </label>
			</td>		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="bloqueDireccionCliente" name="reconocimiento.persona.direccionActual.bloque" size="2" maxlength="3"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="kmDireccionCliente">Km: </label>
			</td>		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="kmDireccionCliente" name="reconocimiento.persona.direccionActual.km" size="2" maxlength="3"/>
			</td>
			
			<td align="left" nowrap="nowrap">
				<label for="hmDireccionCliente">Hm: </label>
			</td>		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="hmDireccionCliente" name="reconocimiento.persona.direccionActual.hm" size="2" maxlength="3"/>
			</td>
		</tr>
	</table>
	</s:else>

	<table class="acciones">
		<tr><td>&nbsp;</td></tr>
    	<tr>
    		<td width="20%">&nbsp;</td>
    		<!-- Si tiene permisos para guardar -->
			<s:if test="%{!permisoClinica}">
	    	<td>
	    		<input class="boton" class="boton" type="button" id="bGuardar" name="bGuardar" value="Guardar" onClick="return guardar(this);" onKeyPress="this.onClick" />
			</td>
			</s:if>
			<!--  end Si tiene permisos de administrador y si tiene permisos de clinica -->
    		<td>
				<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="return cancelar(this);" onKeyPress="this.onClick" />
	    	</td>
	    	<!--  end si tiene permisos de clinica -->
    		<td width="20%">&nbsp;</td>
    	</tr>
    </table>

<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0"
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</div>
</s:form>

<script type="text/javascript">


	/** Obtiene una persona por NIF** */
	function buscarPersonaReconocimiento() {
		$('#formData').attr("action", "buscarPersona_ReconocimientoMedico.action").trigger("submit");
	}

	function guardar(boton){
		$('#formData').attr("action", "guardar_ReconocimientoMedico.action").trigger("submit");
	}

	//Método para finalizar cancelar la validacion
	function cancelar(boton){
		return cancelarForm("inicio_ReconocimientoMedico.action");
	}

	var viaPersonaFacturacion = new BasicContentAssist(document.getElementById('nombreViaPersonaFacturacion'), [], null, true);
	recargarComboMunicipios('idProvinciaPersonaFacturacion','idMunicipioPersonaFacturacion','municipioSeleccionadoPersonaFacturacion');
	recargarComboTipoVia('idProvinciaPersonaFacturacion','tipoViaDireccionPersonaFacturacion','tipoViaSeleccionadaPersonaFacturacion');
	recargarComboPueblos('idProvinciaPersonaFacturacion','municipioSeleccionadoPersonaFacturacion','idPuebloPersonaFacturacion', 'puebloSeleccionadoPersonaFacturacion');
	recargarNombreVias('idProvinciaPersonaFacturacion', 'municipioSeleccionadoPersonaFacturacion', 'tipoViaSeleccionadaPersonaFacturacion','nombreViaPersonaFacturacion',viaPersonaFacturacion);

	//Generar la llamada Ajax para obtener municipios
	
	var diaChecked = "";
	var mesChecked = "";
	var anioChecked = "";
	var tipoChecked = "";
	function obtenerImporteReconocimientoMedico(){
		var tipo = $("#idTipoTramiteRenovacion").val();
		var dia = $("#diaCita").val();
		var mes = $("#mesCita").val();
		var anio = $("#anioCita").val();
		if(tipo==-1 || dia=="" || mes=="" || anio==""){
			return;
		}
		if(tipo == tipoChecked && dia == diaChecked && mes == mesChecked && anio == anioChecked) {
			return;
		}
		var url="recuperarImporteReconocimiento_ReconocimientoMedico.action?reconocimiento.tipoTramiteRenovacion.idTipoTramiteRenovacion="+tipo;
		url+="&fechaReconocimiento.dia="+dia+"&fechaReconocimiento.mes="+mes+"&fechaReconocimiento.anio="+anio;
		var req_generico_calc_importe = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_calc_importe.onreadystatechange = function () { 
				establecerImporte(req_generico_calc_importe);
			}
		req_generico_calc_importe.open("POST", url, true);			
		req_generico_calc_importe.send(null);

		diaChecked = dia;
		mesChecked = mes;
		anioChecked = anio;
		tipoChecked = tipo;
	}
	
	//Callback function de obtenerImporteReconocimientoMedico
	function establecerImporte(req_generico_calc_importe){
		if (req_generico_calc_importe.readyState == 4) { // Complete
			if (req_generico_calc_importe.status == 200) { // OK response
				text = req_generico_calc_importe.responseText;
				$("#importe").val(text);
				
			}
		}
	}

	function capturaEvento(){
		var $cal = $("iframe");
		$cal.bind("DOMAttrModified", function(e) {
				obtenerImporteReconocimientoMedico();
		});
	}
</script>
