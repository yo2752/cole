<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!--  //TODO MPC. Cambio IVTM. Este jsp es el bueno -->
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/576.js" type="text/javascript"></script>
<script src="js/pagosEImpuestos.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/ivtm.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
<s:hidden id="idHiddenExpediente" name="numExpediente"/>
<%@include file="../../includes/erroresYMensajes.jspf" %>
<div class="cabecera">
	<table class="nav" cellSpacing="1" cellspacing="5" width="100%" align="left">
		<tr>
			<td class="tabular">
			<span class="titulo">Modificación Autoliquidación IVTM Madrid</span>
			</td>
		</tr>
	</table>
</div>
<div id="datosIVTM">
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos Autoliquidación a Modificar</span>
	 		</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>    		
	    	<td align="left" nowrap="nowrap">
        		<label for="autoliquidacion">Num. Autoliquidación: </label>
      		</td>

			<td align="left">
				<s:textfield name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.nrc" 
					id="autoliquidacion" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="20" maxlength="20" readonly="true"/>
			</td>
		</tr>
		<tr>        	       			
			<td align="left" nowrap="nowrap">
   				<label for="estado">Estado IVTM: </label>
   			</td>
   			
         	<td align="left" nowrap="nowrap">
         		<s:if test="%{tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.estadoIvtm!= null}">
   					<s:label value="%{@escrituras.utiles.UtilesVista@getInstance().getEstadoIvtm(tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.estadoIvtm)}"/>
   				</s:if>
   			</td>	
   		</tr>     
   		<tr>     
   			<td align="left" nowrap="nowrap">
   				<label for="P_Respuesta">Respuesta IVTM: </label>
   			</td>
   			
        	<td align="left" nowrap="nowrap">
	   			<%if(request.getAttribute("tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.mensaje")!=null){
		   			String[] aux = ((String)request.getAttribute("tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.mensaje")).split("<BR>");
			   		for(String s:aux){
			   			out.println(s+"<br/>");
			   		}
			   	}%>
	   		</td>	
   		</tr>
   		<tr>	
       		<td align="left" nowrap="nowrap" style="vertical-align:middle;">
      			<label for="diaPresentacion">Fecha de la antigua presentacion: </label>
      		</td>    
        	<td align="left" nowrap="nowrap" style="vertical-align:middle;">
	        	<table>
	        		<tr>
	  					<td align="left" nowrap="nowrap" width="1%">
							<s:textfield id="diaPresentacion" 
								name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.fechaReq.dia" 
								onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" 
								size="2" maxlength="2" style="background:#EEEEEE"
								readonly="true"/>
						</td>				
						<td width="1%">/</td>
						<td align="left" nowrap="nowrap" width="1%">
							<s:textfield id="mesPresentacion" 
								name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.fechaReq.mes" 
								onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" 
								size="2" maxlength="2" style="background:#EEEEEE"
								readonly="true"/>
						</td>
						<td width="1%">/</td>				
						<td align="left" nowrap="nowrap" width="1%">
							<s:textfield id="anioPresentacion" 
								name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.fechaReq.anio" 
								onblur="this.className='input2';" 
								onkeypress="return validarNumerosEnteros(event)"
								onfocus="this.className='inputfocus';" 
								size="4" maxlength="4" style="background:#EEEEEE"
								readonly="true"/>
						</td>
					</tr>
				</table>
			</td>	
   		</tr>
	</table>
	<s:if test="@escrituras.utiles.UtilesVista@getInstance().tieneNRC(tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.nrc)">
	<table class="acciones" width="95%" align="left">
		<tr>
			<td align="center" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
				<input type="button" class="boton-persona" name="bConsultarPet" id="idBotonConsultarPet" value="Consultar petición de la autiliquidación previa (<s:property value="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.nrc" />)" onClick="return consultarPeticionIVTM();" onKeyPress="this.onClick"/>
			</td>
			<td align="center" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
				<input type="button" class="boton-persona" name="bConsultarResp" id="idBotonConsultarResp" value="Consultar la respuesta de la autoliquidación previa (<s:property value="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.nrc" />)" onClick="return consultarRespuestaIVTM();" onKeyPress="this.onClick"/>
			</td>
		</tr>
	</table>
	</s:if>
</div>
<div id="datosSujeto">
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">SUJETO PASIVO TITULAR DEL VEHÍCULO</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
       			<label for="tipoPersonaTitular">Tipo de persona: </label>
       		</td>

         	<td align="left" nowrap="nowrap">
	         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersonaTitular"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						value="%{titularTramite.personaDireccion.persona.tipoPersona}"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Tipo Persona"
						headerKey="-1" headerValue="-" style="background:#EEEEEE"
						disabled="true"/>
       		</td>	
		</tr>
		<tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="nifTitular">NIF / CIF / NIE: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
       	    	<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield name="titularTramite.personaDireccion.persona.id.nif" 
		       				id="nifTitular" 
		       				onblur="this.className='input2';calculaLetraNIF(this)" 
		       				onfocus="this.className='inputfocus';" 
		       				onchange="limpiarFormularioTitularMatriculacion()" style="background:#EEEEEE"
		       				size="9" maxlength="9" readonly="true"
		       				/>
						</td>
					</tr>
				</table>
       	    </td>			        		
       	</tr>
    	<tr>	
    		<td align="left" nowrap="nowrap" >
		       	<label for="apellido1RazonSocialTitular">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
		     </td>				        		
		     <td align="left" nowrap="nowrap" colspan="3">
		       			<s:textfield name="titularTramite.personaDireccion.persona.apellido1RazonSocial" 
		       				id="apellido1RazonSocialTitular" 
		       				onblur="this.className='input2';" 
		       				onfocus="this.className='inputfocus';" 
		       				maxlength="100"
		       				cssStyle="width:20em; background:#EEEEEE;" readonly="true"/>
		      </td>
       	</tr>
		<tr>	
			<td align="left" nowrap="nowrap">
       			<label for="apellido2Titular">Segundo Apellido: </label>
       		</td>				        				
       		<td align="left" nowrap="nowrap">
       			<s:textfield name="titularTramite.personaDireccion.persona.apellido2" 
       				id="apellido2Titular" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="30" maxlength="100" readonly="true"/>
       		</td>
       	
       		<td align="left" nowrap="nowrap">
       			<label for="nombreTitular">Nombre: <span class="naranja">*</span></label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield name="titularTramite.personaDireccion.persona.nombre" 
       				id="nombreTitular" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="30" maxlength="100" readonly="true"/>
       		</td>
       	</tr>
       	<tr>
			<td align="left" nowrap="nowrap">
				<label for="telefonoTitular">Teléfono: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap" >
				<s:textfield name="titularTramite.personaDireccion.persona.telefonos" 
					id="telefonoTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="9" maxlength="9" readonly="true"/>
			</td>			        		
       	 </tr>
       	 <tr>      			
			<td align="left" nowrap="nowrap">
				<label for="correoElectronicoTitular">Correo Electrónico: </label>
			</td>				        		
	       	        	       			
	        <td align="left" nowrap="nowrap" colspan="2">
				<s:textfield id="correoElectronicoTitular"
					name="titularTramite.personaDireccion.persona.correoElectronico"  
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="40" maxlength="100" readonly="true"/>
			</td>
		</tr>     	
	</table>
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="provinciaSujeto">Provincia: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield
					name="titularTramite.personaDireccion.direccion.municipio.provincia.nombre" 
					id="provinciaSujeto"
					onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="40" maxlength="100" readonly="true"/>
			</td>
	
			<td align="left" nowrap="nowrap">
				<label for="municipioSujeto">Municipio: <span class="naranja">*</span></label>	
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">			
				<s:textfield
					name="titularTramite.personaDireccion.direccion.municipio.nombre" 
					id="municipioSujeto"
					onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="40" maxlength="100" readonly="true"/>
			</td>
		</tr>
		
		<tr>			
			<td align="left" nowrap="nowrap">
				<label for="codPostalTitular">Código Postal: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
	        	<s:textfield name="titularTramite.personaDireccion.direccion.codPostal" 
	        		id="codPostalSujeto" 
	        		maxlength="5" size="5" 
	            	onblur="this.className='input';"
	               	onkeypress="return validarNumerosEnteros(event)" style="background:#EEEEEE"
	            	onfocus="this.className='inputfocus';" readonly="true"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaDireccionTitular">Tipo vía: <span class="naranja">*</span></label>
			</td>				        		
       	    
       	    <td align="left" nowrap="nowrap">
         	
         	<s:textfield id="tipoViaDireccionTitular" 
	       				onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="titularTramite.personaDireccion.direccion.idTipoVia"
		       			cssStyle="width:200px;position:relative; background:#EEEEEE;" size="40" maxlength="120" 
		       			autocomplete="off" readonly="true"/>
         	
       		</td>
			
			<td align="left" nowrap="nowrap">
				<label for="nombreViaDireccionTitular">Nombre vía: <span class="naranja">*</span></label>
			</td>				        		
       	       			
			<td align="left" nowrap="nowrap" colspan="3">
	       			<s:textfield id="nombreViaDireccionTitular" 
	       				onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="titularTramite.personaDireccion.direccion.nombreVia"
		       			cssStyle="width:200px;position:relative; background:#EEEEEE;" size="40" maxlength="120" 
		       			autocomplete="off" readonly="true"/>
						
       		</td>
		</tr>
	</table>
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>	
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionTitular">N&uacute;mero: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="titularTramite.personaDireccion.direccion.numero" 
					id="numeroDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarSN2(event,this)" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionTitular">Letra: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="titularTramite.personaDireccion.direccion.letra"
			id="letraDireccionTitular" onblur="this.className='input2';"
			onfocus="this.className='inputfocus';" size="5" maxlength="5" style="background:#EEEEEE"
			readonly="true" />
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionTitular">Escalera: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="titularTramite.personaDireccion.direccion.escalera" 
					id="escaleraDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionTitular">Piso: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="titularTramite.personaDireccion.direccion.planta" 
					id="pisoDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionTitular">Puerta: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="titularTramite.personaDireccion.direccion.puerta" 
					id="puertaDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
		</tr>	
	</table>				
</div>
<div id="datosOtro">
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">REPRESENTANTE</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
       			<label for="tipoPersonaRepresentante">Tipo de persona: </label>
       		</td>

         	<td align="left" nowrap="nowrap">
	         	<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersonaRepresentante"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						value="representanteTramite.personaDireccion.persona.tipoPersona"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Tipo Persona"
						headerKey="-1" headerValue="-" style="background:#EEEEEE"
						disabled="true"/>
       		</td>	
		</tr>
		<tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="nifTitular">NIF / CIF: <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" width="24%">
       	    	<table style="align:left;">
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield name="representanteTramite.personaDireccion.persona.id.nif" 
		       				id="nifTitular" 
		       				onblur="this.className='input2';calculaLetraNIF(this)" 
		       				onfocus="this.className='inputfocus';" 
		       				onchange="limpiarFormularioTitularMatriculacion()" style="background:#EEEEEE"
		       				size="9" maxlength="9" readonly="true"
		       				/>
						</td>
					</tr>
				</table>
       	    </td>			        		
       	</tr>
    	<tr>	
    		<td align="left" nowrap="nowrap" >
		       	<label for="apellido1RazonSocialTitular">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
		     </td>				        		
		     <td align="left" nowrap="nowrap" colspan="3">
		       			<s:textfield name="representanteTramite.personaDireccion.persona.apellido1RazonSocial" 
		       				id="apellido1RazonSocialTitular" 
		       				onblur="this.className='input2';" 
		       				onfocus="this.className='inputfocus';" 
		       				maxlength="100" 
		       				cssStyle="width:20em; background:#EEEEEE;" readonly="true"/>
		      </td>
       	</tr>
		<tr>	
			<td align="left" nowrap="nowrap">
       			<label for="apellido2Titular">Segundo Apellido: </label>
       		</td>				        				
       		<td align="left" nowrap="nowrap">
       			<s:textfield name="representanteTramite.personaDireccion.persona.apellido2" 
       				id="apellido2Titular" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="30" maxlength="100" readonly="true"/>
       		</td>
       	
       		<td align="left" nowrap="nowrap">
       			<label for="nombreTitular">Nombre: <span class="naranja">*</span></label>
       		</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
       			<s:textfield name="representanteTramite.personaDireccion.persona.nombre" 
       				id="nombreTitular" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="30" maxlength="100" readonly="true"/>
       		</td>
       	</tr> 	
	</table>
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="provinciaSujeto">Provincia: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield
					name="representanteTramite.personaDireccion.direccion.municipio.provincia.nombre" 
					id="provinciaSujeto"
					onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="40" maxlength="100" readonly="true"/>
			</td>
	
			<td align="left" nowrap="nowrap">
				<label for="municipioSujeto">Municipio: <span class="naranja">*</span></label>	
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">			
				<s:textfield
					name="representanteTramite.personaDireccion.direccion.municipio.nombre" 
					id="municipioSujeto"
					onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="40" maxlength="100" readonly="true"/>
			</td>
		</tr>
		
		<tr>			
			<td align="left" nowrap="nowrap">
				<label for="codPostalTitular">Código Postal: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
	        	<s:textfield name="representanteTramite.personaDireccion.direccion.codPostal" 
	        		id="codPostalSujeto" 
	        		maxlength="5" size="5" 
	            	onblur="this.className='input';"
	               	onkeypress="return validarNumerosEnteros(event)" style="background:#EEEEEE"
	            	onfocus="this.className='inputfocus';" readonly="true"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="tipoViaDireccionTitular">Tipo vía: <span class="naranja">*</span></label>
			</td>				        		
       	    
       	    <td align="left" nowrap="nowrap">
         	 	<s:textfield id="tipoViaDireccionTitular" 
	       				onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="representanteTramite.personaDireccion.direccion.idTipoVia"
		       			cssStyle="width:200px;position:relative; background:#EEEEEE;" size="40" maxlength="120" 
		       			autocomplete="off" readonly="true"/>
         	</td>
			
			<td align="left" nowrap="nowrap">
				<label for="nombreViaDireccionTitular">Nombre vía: <span class="naranja">*</span></label>
			</td>				        		
       	       			
			<td align="left" nowrap="nowrap" colspan="3">
	       			<s:textfield id="nombreViaDireccionTitular" 
	       				onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="representanteTramite.personaDireccion.direccion.nombreVia"
		       			cssStyle="width:200px;position:relative; background:#EEEEEE;" size="40" maxlength="120" 
		       			autocomplete="off" readonly="true"/>
						
       		</td>
		</tr>
	</table>
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>	
			<td align="left" nowrap="nowrap" width="7%">
				<label for="numeroDireccionTitular">N&uacute;mero: <span class="naranja">*</span></label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="representanteTramite.personaDireccion.direccion.numero" 
					id="numeroDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" 
					onkeypress="return validarSN2(event,this)" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap" width="7%">
				<label for="letraDireccionTitular">Letra: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="representanteTramite.personaDireccion.direccion.letra" 
					id="letraDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="escaleraDireccionTitular">Escalera: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="representanteTramite.personaDireccion.direccion.escalera" 
					id="escaleraDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
			
			<td align="left" nowrap="nowrap" width="7%">
				<label for="pisoDireccionTitular">Piso: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="representanteTramite.personaDireccion.direccion.planta" 
					id="pisoDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
		</tr>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="puertaDireccionTitular">Puerta: </label>
			</td>				        		
       	       			
       		<td align="left" nowrap="nowrap">
				<s:textfield name="representanteTramite.personaDireccion.direccion.puerta" 
					id="puertaDireccionTitular" 
					onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" style="background:#EEEEEE"
					size="5" maxlength="5" readonly="true"/>
			</td>
		</tr>	
	</table>				
</div>
<div id="datosVehiculo">			
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">VEHÍCULO</span>
			</td>
		</tr>
	</table>
					
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		
		
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
       			<label for="idBastidor">N&uacute;mero de Bastidor: <span class="naranja">*</span></label>
       		</td>    
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idBastidor" 
       				name="tramiteTraficoDto.vehiculo.bastidor"  
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="22" maxlength="21" readonly="true"/>
    		</td>    				        		
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idTipoVehiculo">Tipo de Vehículo (Criterio Construcción): <span class="naranja">*</span></label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select id="idTipoVehiculo" 
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="tramiteTraficoDto.vehiculo.idCriterioConstruccion"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getClasificacionesCriterioConstruccion()"
					listKey="idCriterioConstruccion"
					listValue="descripcion"
					headerKey="-1" style="background:#EEEEEE"
			        headerValue="Criterio de Construcción" disabled="true"/>	
			</td>
		</tr>    			
	    			
		<tr>      				
    		<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="codigoMarca">Marca: <span class="naranja">*</span></label>
			</td>    
			       		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="codigoMarca" 
      				name="tramiteTraficoDto.vehiculo.marcaDgt.marca" 
      				onblur="this.className='input2';" 
      				onfocus="this.className='inputfocus';"
      				size="23"
      				maxlength="22" 
      				cssStyle="position:relative; float:left; background:#EEEEEE;" readonly="true"/>
			</td>
		</tr>
		
		<tr>      				
    		<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idModelo">Modelo: <span class="naranja">*</span></label>
			</td>    
			       		
       		<td align="left" nowrap="nowrap">
				<s:textfield id="idModelo" 
      				name="tramiteTraficoDto.vehiculo.modelo" 
      				onblur="this.className='input2';" 
      				onfocus="this.className='inputfocus';"
      				size="23"
      				maxlength="22" 
      				cssStyle="position:relative; float:left; background:#EEEEEE;" readonly="true" />
			</td>
		</tr>	    
	    		
		<tr>			
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idServicioTrafico">Servicio: <span class="naranja">*</span></label>
			</td>    
        	
        	<td align="left" nowrap="nowrap">
				<s:select id="idServicioTrafico" 
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="tramiteTraficoDto.vehiculo.idServicio"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getServiciosTrafico('T1')"
					listKey="idServicio"
					listValue="descripcion"
					headerKey="-1"
		    	    headerValue="Servicio" style="background:#EEEEEE"
		    	    disabled="true"
		    	    />							
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
				<label for="idCarburanteVehiculo">Tipo de Carburante: <span class="naranja">*</span></label>
			</td>   
        	
        	<td align="left" nowrap="nowrap">
				<s:select id="idCarburanteVehiculo" 
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';"
					name="tramiteTraficoDto.vehiculo.idCarburante"
					list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCarburantes()"
					listKey="valorEnum"
					listValue="nombreEnum"
					headerKey=""
			        headerValue="Carburante" style="background:#EEEEEE"
			        disabled="true" />			        							
			</td>
		</tr>
		<tr>	
   			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
       			<label for="idCo2">CO2: <span class="naranja">*</span></label>
       		</td>    
        		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idCo2" 
       				name="tramiteTraficoDto.vehiculo.co2" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" 
       				size="8" maxlength="7" style="background:#EEEEEE"
       				readonly="true" />
   			</td>
		</tr>
			
	</table>
	
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">	
		<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
      			<label for="idPotenciaFiscal">Potencia Fiscal: </label>
      		</td>    
        		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idPotenciaFiscal" 
       				name="tramiteTraficoDto.vehiculo.potenciaFiscal" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="7" maxlength="6" readonly="true"/>
    		</td>
    		<td align="left" nowrap="nowrap" style="vertical-align:middle;">
       			<label for="idNcilindros">Cilindrada: </label>
       		</td>    
        		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idNcilindros" 
       				name="tramiteTraficoDto.vehiculo.cilindrada"  
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="7" maxlength="6" readonly="true"/>
   			</td>
   		</tr>
   		<tr>        			
    		<td align="left" nowrap="nowrap" style="vertical-align:middle;">
        		<label for="idMma">MMA: </label>
        	</td>    
        		
       		<td align="left" nowrap="nowrap">
        		<s:textfield id="idMma" 
        			name="tramiteTraficoDto.vehiculo.pesoMma" 
        			onblur="this.className='input2';" 
        			onfocus="this.className='inputfocus';" style="background:#EEEEEE"
        			size="7" maxlength="6" readonly="true"/>
        	</td>
        	<td align="left" nowrap="nowrap" style="vertical-align:middle;">
        		<label for="idMma">Tara: </label>
        	</td>    
        		
       		<td align="left" nowrap="nowrap">
        		<s:textfield id="idTara" 
        			name="tramiteTraficoDto.vehiculo.tara" 
        			onblur="this.className='input2';" 
        			onfocus="this.className='inputfocus';" style="background:#EEEEEE"
        			size="7" maxlength="6" readonly="true"/>
        	</td>
        </tr>
        <tr>  			
       		<td align="left" nowrap="nowrap" style="vertical-align:middle;">
        		<label for="idPlazas">Plazas: </label>
        	</td>    
        		
       		<td align="left" nowrap="nowrap">
        		<s:textfield id="idPlazas" 
        			name="tramiteTraficoDto.vehiculo.plazas" 
        			onblur="this.className='input2';" 
        			onfocus="this.className='inputfocus';" style="background:#EEEEEE"
        			size="7" maxlength="6" readonly="true"/>
        	</td>  			
       	</tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">RESULTADO</span>
			</td>
		</tr>
	</table>
	
	<table  cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
    	<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
      			<label for="idImporte">Importe:</label>
      		</td>    
        		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idImporte" 
       				name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.importe" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="7" maxlength="6" readonly="true"/>
    		</td>
    		<td align="left" nowrap="nowrap" style="vertical-align:middle;">
      			<label for="idImporteAnual">Importe Anual:</label>
      		</td>    
        		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idImporteAnual" 
       				name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.importeAnual" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="7" maxlength="6" readonly="true"/>
    		</td>
    	</tr>
    	<tr>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
      			<label for="idCodGestor">Código Gestor: </label>
      		</td>    
        		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idCodGestor" 
       				name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.codGestor" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="9" maxlength="9" readonly="true"/>
    		</td>
    	
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
      			<label for="idDigitoControl">Digito Control: </label>
      		</td>    
        		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idDigitoControl" 
       				name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.digitoControl" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="4" maxlength="4" readonly="true"/>
    		</td>
			<td align="left" nowrap="nowrap" style="vertical-align:middle;">
      			<label for="idEmisor">Emisor: </label>
      		</td>    
        		
       		<td align="left" nowrap="nowrap">
       			<s:textfield id="idEmisor" 
       				name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.emisor" 
       				onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" style="background:#EEEEEE"
       				size="9" maxlength="9" readonly="true"/>
    		</td>
    	</tr>					
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS MODIFICABLES</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
	    	<td align="left" nowrap="nowrap">
   				<label for="idBodmedamb">Bonificación Medio Ambiente</label>
   			</td>
           	<td align="left" width="5%">
            	<s:checkbox name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.bonmedam" 
		            id="idBonMedAmbIvtm" onChange="activarMedioAmbienteIVTM();"/>
        	</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<span id="spanLabelPorcentajeBonMedAmbIvtm">
	       			<label for="idPorcentajeBonMedAmbIvtm">Porcentaje de la Bon. Medio Ambiente</label>
	       		</span>
	   		</td>
	       	<td>
	       		<span id="spanPorcentajeBonMedAmbIvtm">
	       			<s:textfield name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.porcentajebonmedam" id="idPorcentajeBonMedAmbIvtm" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5" onkeypress="return validarNumerosDecimales(event)" onChange="return validarPorcentajeBonificacionMedioAmbiente(event)"/>
	       		</span>
	       	</td>	     
		</tr>
		<tr>	
			<td align="left" nowrap="nowrap">
				<label for="ccc">CCC (formato IBAN - Empieza por ES) <span class="naranja">*</span></label>
  			</td>
 		 	<td align="left" nowrap="nowrap">
				<s:textfield name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.iban" id="idCCIvtm" onblur="this.className='input2';" onfocus="this.className='inputfocus';" onchange="comprobarIBANEs();" size="30" maxlength="24"/>
			</td>
		</tr>
		<tr>	
       		<td align="left" nowrap="nowrap" width="140" style="vertical-align:middle;">
      			<label for="diaAlta">Fecha de Pago: <span class="naranja">*</span></label>
      		</td>    
        	<td>
        	<table><tr>
  			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield id="diaAlta" 
					name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.fechaPago.dia" 
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="2" maxlength="2"/>
			</td>
			
			<td width="1%">/</td>
			
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield id="mesAlta" 
					name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.fechaPago.mes" 
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="2" maxlength="2"/>
			</td>
			
			<td width="1%">/</td>
			
			<td align="left" nowrap="nowrap" width="1%">
				<s:textfield id="anioAlta" 
					name="tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.fechaPago.anio" 
					onblur="this.className='input2';" 
					onkeypress="return validarNumerosEnteros(event)"
					onfocus="this.className='inputfocus';" 
					size="4" maxlength="4"/>
			</td>
			
			<td align="left" nowrap="nowrap">
	    		<a href="javascript:;" 
	    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAlta, document.formData.mesAlta, document.formData.diaAlta);return false;" 
	    				title="Seleccionar fecha">
	    			<img class="PopcalTrigger" 
	    				align="middle" 
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
	<table class="acciones" width="95%" align="left">
		<tr>
			<td align="center" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
				<input type="button" class="boton" name="volver" id="idBotonVolver" value="Volver" onClick="volverListadoModificacionIVTM();"/>
			</td>
	
			<s:if test="%{@escrituras.utiles.UtilesVista@getInstance().validarBotonIvtm(tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.nrc, tramiteTraficoDto.tramiteTrafMatr.ivtmMatriculacionDto.estadoIvtm)}">
				<td align="center" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
					<input type="button" class="boton" name="bEnviar" id="idBotonEnviar" value="Enviar" onClick="enviarModificacionIVTM();" onKeyPress="this.onClick"/>
				</td>		
			</s:if>
		</tr>
	</table>
</div>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>

</s:form>

<script type="text/javascript">

//PONEMOS A DISABLED LOS CAMPOS RELACIONADOS CON IVTM PARA QUE NO HAYA POSIBILIDAD DE MODIFICAR NADA EN CASO DE
//QUE NO EXISTA TODAVIA UNA AUTOLIQUIDACION DEL TRAMITE
$( document ).ready(function() {
	var nrc = document.getElementById("autoliquidacion").value;
	if (nrc == null || nrc =="") {
		
		$("#idBonMedAmbIvtm").prop('disabled',true);
		$("#idCCIvtm").prop('disabled',true);
		$("#idPorcentajeBonMedAmbIvtm").prop('disabled',true);
		$("#diaAlta").prop('disabled',true);
		$("#mesAlta").prop('disabled',true);
		$("#anioAlta").prop('disabled',true);

	}
});
</script>

