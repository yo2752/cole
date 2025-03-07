
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<!-- Evitamos que se pierdan la caducidad de la documentación  -->
<s:hidden name="solicitud.solicitante.persona.fechaCaducidadNif.dia" />
<s:hidden name="solicitud.solicitante.persona.fechaCaducidadNif.mes" />
<s:hidden name="solicitud.solicitante.persona.fechaCaducidadNif.anio" />
<s:hidden name="solicitud.solicitante.persona.tipoDocumentoAlternativo" />
<s:hidden name="solicitud.solicitante.persona.fechaCaducidadAlternativo.dia" />
<s:hidden name="solicitud.solicitante.persona.fechaCaducidadAlternativo.mes" />
<s:hidden name="solicitud.solicitante.persona.fechaCaducidadAlternativo.anio" />

<!-- Fin de evitar que se pierda la caducidad de la documentación  -->


<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/solicitudes.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<script type="text/javascript">


</script>

<s:hidden id="permisoEspecial" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"/>
<s:set var="numExpediente" value="solicitud.tramiteTrafico.numExpediente"/>

<ol id="toc">	    
	    <li><a href="#Vehiculos">Vehículos</a></li>
	    <li><a href="#Solicitud">Solicitud</a></li>
	    <s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esFacturableLaTasaSolicitud(solicitud)}">
	    	<li><a href="#Facturacion">Facturación</a></li>
	    </s:if>	
	    <s:if test="%{solicitud.tramiteTrafico.numExpediente!=null}">	    
	    	<li><a href="#Resumen">Resumen</a></li>  
	    </s:if>
</ol>

<s:form method="post" id="formData" name="formData" cssStyle="100%">

<s:hidden id="idHiddenNumeroExpediente" name="solicitud.tramiteTrafico.numExpediente"></s:hidden>
<!--<s:hidden name="solicitud.tramiteTrafico.rowIdTraficoTramite"> </s:hidden>-->
<s:hidden name="solicitud.tramiteTrafico.vehiculo.idVehiculo"></s:hidden>
<s:hidden id="idContratoTramite" name="solicitud.tramiteTrafico.idContrato"></s:hidden>
<s:hidden id="idNumColegiado" name="solicitud.tramiteTrafico.numColegiado"></s:hidden>
<s:hidden name="solicitud.solicitudVehiculo.vehiculo.idVehiculo"></s:hidden>
<s:hidden name="solicitud.solicitudVehiculo.resultado"></s:hidden>
<!--<s:hidden name="solicitud.solicitudVehiculo.rowid"></s:hidden>-->
<!--<s:hidden name="solicitud.solicitudVehiculo.numExpediente"></s:hidden>
--><s:hidden name="solicitud.solicitudVehiculo.idContrato"></s:hidden>
<s:hidden name="solicitud.solicitudVehiculo.tipoTramite"></s:hidden>
<s:hidden name="idVehiculoBorrar" id="idVehiculoBorrar"></s:hidden>
<!--<s:hidden name="solicitud.solicitudVehiculo.vehiculo.rowIdVehiculo"></s:hidden>-->
<s:hidden name="solicitud.solicitudVehiculo.tramiteTrafico.jefaturaTrafico.jefaturaProvincial"></s:hidden>
<s:hidden name="solicitud.solicitudVehiculo.tramiteTrafico.estado"></s:hidden>
<s:hidden name="solicitud.tramiteTrafico.estado"></s:hidden>
<s:hidden id="puedoImprimir" name="puedoImprimir"></s:hidden>
<s:hidden id="tipoIntervinienteBuscar" name="tipoIntervinienteBuscar"></s:hidden>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	
<div class="contentTabs" id="Vehiculos"  style="width:100%;">
		
	
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">VEHÍCULO: </span>
					<s:set var="flagDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}"></s:set>
					<s:set var="stringDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().stringPermisoEspecial()}"></s:set>
					<s:set var="displayDisabled" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().displayPermisoEspecial()}"></s:set>
				</td>
			</tr>
		</table>

		
		<table cellPadding="1" cellSpacing="3"  class="tablaformbasica" width="100%">
			<tr>
				<td align="left" nowrap="nowrap" width="10%">
					<label for="matricula">Matrícula: </label>
				</td>
				<td>
					<s:textfield disabled="%{flagDisabled}" name="solicitud.solicitudVehiculo.vehiculo.matricula"
						id="matricula" 
						style="text-transform : uppercase"
           				onblur="this.className='input';" 
           				onkeypress="return validarMatricula(event)"
           				onfocus="this.className='inputfocus';" 
           				onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" 
           				size="9" maxlength="9"></s:textfield>
				</td>
				<td align="left" nowrap="nowrap" width="10%">
					<label for="bastidor">Nº Bastidor: </label>
				</td>

				<td>
					<s:textfield name="solicitud.solicitudVehiculo.vehiculo.bastidor"
						id="bastidor" 
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';"
           				onkeypress="return validarBastidor(event);" 
           				size="21" maxlength="21"></s:textfield>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="10%">
					<label for="tipoTasa">Tipo de Tasa: </label>
				</td>

				<td align="left" nowrap="nowrap" >
	               	<label for="tipoTasa">4.1</label>
				</td>
				<td align="left" nowrap="nowrap" width="13%">
	   				<label for="codigoTasa">Código de Tasa: <span class="naranja">*</span></label>
	   			</td>
	   			
	         	<td align="left" nowrap="nowrap" colspan="3">	
					<s:select id="codigoTasa"
						onblur="this.className='input2';"
						disabled="%{flagDisabled}"
						onfocus="this.className='inputfocus';"
						name="solicitud.solicitudVehiculo.tasa.codigoTasa"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getCodigosTasaBajas(solicitud.tramiteTrafico.idContrato)"
						headerKey="-1"
	          			headerValue="Seleccione Código de Tasa"
						listKey="codigoTasa" 
						listValue="codigoTasa"/>			
				</td>	
			</tr>
			<tr>        	       			
				<td align="left">
   					<label for="anotaciones">Motivo Informe:<span class="naranja">*</span></label>
   				</td>
	       		<td align="left" nowrap="nowrap" colspan="5">
	       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getMotivoInteve()"
						name="solicitud.solicitudVehiculo.motivoInteve" id="motivoInteve" headerKey=""
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						headerValue="Seleccionar Motivo" listKey="valorEnum"
						listValue="nombreEnum" />
				</td>
				<td align="left">
   					<label for="anotaciones">Tipo Informe:<span class="naranja">*</span></label>
   				</td>
	       		<td align="left" nowrap="nowrap" colspan="5">
	       			<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoInformeInteve()"
						name="solicitud.solicitudVehiculo.tipoInforme" id="idTipoInformeInteve" headerKey=""
						onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						headerValue="Seleccionar Tipo Informe" listKey="valorEnum"
						listValue="nombreEnum" />
				</td>
			</tr>
		</table>
		
		<table  cellPadding="1" cellSpacing="3" class="tablaformbasica" width="100%" align="center">
				<tr>
							<td align="center">
								<display:table name="solicitud.solicitudesVehiculos" excludedParams="*"
									class="tablacoin" id="SolicitudesVehiculos"
									summary="Listado de Solicitudes Vehículos"
									cellspacing="0" cellpadding="0" uid="row">	
									
									<display:column property="vehiculo.matricula" title="Matrícula"
										defaultorder="descending"
										style="width:3%" />				
									<display:column property="vehiculo.bastidor" title="Bastidor"
										defaultorder="descending"
										style="width:5%"/>
									<display:column property="tasa.codigoTasa" title="Código Tasa"
										defaultorder="descending"
										style="width:4%"/>	
									<display:column property="resultado" title="Resultado"
										defaultorder="descending"
										style="width:8%"/>		
									<display:column property="estado.nombreEnum" title="Estado"
										defaultorder="descending"
										style="width:3%"/>	
									<display:column title="Motivo Inteve" defaultorder="descending" style="width:3%">
										<s:property value="%{@trafico.inteve.MotivoConsultaInteve@convertirTexto(#attr.row.motivoInteve)}" />
									</display:column>	
									<display:column title="Tipo Informe" defaultorder="descending" style="width:3%">
										<s:property value="%{@trafico.inteve.TipoInformeInteve@convertirTexto(#attr.row.tipoInforme)}" />
									</display:column>	
									<display:column style="width:3%">
										<s:if test="%{!(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
											<s:if test="%{solicitud.tramiteTrafico.estado.valorEnum != 10}">
											<a onclick="" href="javascript:borrarSolicitudVehiculo('${row.vehiculo.idVehiculo}');"> Borrar </a>
											</s:if>
											<s:else>
											<a onclick="" href="javascript:borrarSolicitudVehiculoBloqueado();"> Borrar </a>
											</s:else>
										</s:if>
									</display:column>
								</display:table>
							</td>
						</tr>
					</table>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoSolicitudes() &&
		!(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">	 
			<table class="acciones">
				<tr>
					<s:if test="solicitud.tramiteTrafico.estado!=null && solicitud.tramiteTrafico.estado.valorEnum!=10  
					  && solicitud.tramiteTrafico.estado.valorEnum !=13 && solicitud.tramiteTrafico.estado.valorEnum !=33)">
						<td  align="center" style="size: 100%; TEXT-ALIGN: center;">											
						<input id="bGuardarSolicitud1" 
							type="button" 
							value="Guardar Solicitud Vehículo" 
							style="size: 100%; TEXT-ALIGN: center;" 
							onClick="return guardarSolicitud(this,'Vehiculos');"/>
						</td>	
					</s:if>
					
					<s:if test="solicitud.tramiteTrafico.estado!=null && solicitud.tramiteTrafico.estado.valorEnum==1">
						<%-- EN FUNCION DEL PROPERTIE dgt.aplicacion.inteve.nuevo.habilitar CREO EL BOTON CON ID QUE LUEGO EL JAVASCRIPT VALORA A QUE FUNCION
						DEL ACTION LLAMAR --%>
						<%-- 	<td align="right" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
								<input type="button" 
								   style="size: 100%; TEXT-ALIGN: center;" 
								   name="bFinalizar" 
								   id="botonInteveNuevo" 
								   value="Consultar en INTEVE3"
								   onClick="return avpoSolicitud(this);" 
								   onKeyPress="this.onClick"/>
							</td>--%>
					</s:if>

					<s:if test="solicitud.solicitudesVehiculos.size() > 0">
						<td align="right" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
							<input type="button" 
							   style="size: 100%; TEXT-ALIGN: center;" 
							   name="bReiniciar" 
							   id="bReiniciar" 
							   value="Reiniciar Estados"
							   onClick="return reiniciarEstados(this);" 
							   onKeyPress="this.onClick"/>
						</td>
					</s:if>		
					<s:if test="%{existeZip}">
						<td align="right" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
							<input type="button" 
							   style="size: 100%; TEXT-ALIGN: center;" 
							   name="bObtenerZip" 
							   id="bObtenerZip" 
							   value="Obtener Zip Generado"
							   onClick="return imprimirZipSolicitud(this);" 
							   onKeyPress="this.onClick"/>
						</td>
					</s:if>
					<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esDescargableXmlApp(solicitud)}">
						<td align="right" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
								<input type="button" 
								   style="size: 100%; TEXT-ALIGN: center;" 
								   name="bDescargarXml" 
								   id="idBDescargarXml" 
								   value="Descargar XML"
								   onClick="return descargarXmlApp(this);" 
								   onKeyPress="this.onClick"/>
							</td>
					</s:if>
				</tr>
		
				<tr>
					<td colspan="3">
						<img id="loadingImage1" 
							style="display:none;margin-left:auto;margin-right:auto;" 
							src="img/loading.gif"/>
					</td>
				</tr>
			</table>
		</s:if>
		
	</div>
	
	
	<div class="contentTabs" id="Solicitud"  style="width:100%;">
	
	
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">SOLICITUD:</span>
				</td>
			</tr>
		</table>

		<table cellPadding="1" cellSpacing="3"  cellpadding="1" border="0" class="tablaformbasica" width="100%">
			<tr>
				<td colspan="8">&nbsp;</td>
			</tr>
			
			<tr>
				<td align="left" nowrap="nowrap">
       				<label for="referenciaPropia">Referencia Propia:</label>
       			</td>
		         		
	       		<td align="left" nowrap="nowrap">
	       			<s:textfield disabled="%{flagDisabled}" name="solicitud.tramiteTrafico.referenciaPropia" 
		       			id="referenciaPropia" 
		       			onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" 
		       			size="50" maxlength="50"/>
	   			</td>
			</tr>
			
			<tr>        	       			
				<td align="left">
	   				<label for="anotaciones">Anotaciones:</label>
	   			</td>
			
	       		<td align="left" nowrap="nowrap" colspan="3">
	   				<s:textarea disabled="%{flagDisabled}" name="solicitud.tramiteTrafico.anotaciones" 
		   				id="anotaciones" 
		   				onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" 
	   					rows="5" cols="50"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" colspan="2">
					<table>
						<tr>
							<td align="left" nowrap="nowrap" width="17%">
								<label for="fechaPresentacion">Fecha Presentación: </label>
				   			</td>
				
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" id="diaPresentacion"
									name="solicitud.tramiteTrafico.fechaPresentacion.dia" 
									onblur="this.className='input2';" 
									onkeypress="return validarDia(this,event)"
									onfocus="this.className='inputfocus';" 
									size="2" maxlength="2" />
							</td>
							
							<td width="1%">/</td>
							
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" id="mesPresentacion"
									name="solicitud.tramiteTrafico.fechaPresentacion.mes" 
									onblur="this.className='input2';" 
									onkeypress="return validarMes(this,event)"
									onfocus="this.className='inputfocus';" 
									size="2" maxlength="2" />
							</td>
							
							<td width="1%">/</td>
									
							<td align="left" nowrap="nowrap" width="5%">
								<s:textfield disabled="%{flagDisabled}" id="anioPresentacion"
									name="solicitud.tramiteTrafico.fechaPresentacion.anio"
									onblur="this.className='input2';" 
									onkeypress="return validarAnio(this,event)"
									onfocus="this.className='inputfocus';" 
									size="4" maxlength="4" />
							</td>
							
							<td align="left" nowrap="nowrap">
					    		<a href="javascript:;" 
					    			onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacion, document.formData.mesPresentacion, document.formData.diaPresentacion);return false;" 
					    				title="Seleccionar fecha">
					    			<img class="PopcalTrigger" 
					    				align="left" 
					    				src="img/ico_calendario.gif" ${displayDisabled} 
					    				width="15" height="16" 
					    				border="0" alt="Calendario"/>
					    		</a>
							</td>
						</tr>
					</table>
				</td>
				
			</tr>
			
		</table>
							
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">SOLICITANTE:</span>
				</td>
			</tr>
		</table>

		<table cellPadding="1" cellSpacing="3"  cellpadding="1"  class="tablaformbasica" width="100%">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="nifSolicitante">NIF / CIF: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap">
					<table>
					  <tbody>
					     <tr> 
					        <td>   <s:textfield disabled="%{flagDisabled}" name="solicitud.solicitante.persona.nif" 
						id="nifSolicitante"			
						onblur="this.className='input';calculaLetraNIF(this)" 
						onfocus="this.className='inputfocus';" 
						size="9" maxlength="9" />
						</td>
					        <td>
					        <s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
								<input align="left" nowrap="nowrap" type="button" class="boton-persona" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:buscarIntervinienteSolicitud('Solicitante','Solicitud')"/>
							</s:if>
					        </td>
				     
					     </tr>
					   </tbody>
					
					
					</table>
					
					</td>	
				<td align="left" nowrap="nowrap" >
					<label for="apellido1RazonSocialSolicitante">Primer Apellido / Razón Social: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap">
	            	<s:textfield disabled="%{flagDisabled}" name="solicitud.solicitante.persona.apellido1RazonSocial" 
	            		id="apellido1RazonSocialSolicitante" 
               			onblur="this.className='input';" 
               			onfocus="this.className='inputfocus';" 
               			size="40" maxlength="100"/>
				</td>
			</tr>
					
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="apellido2Solicitante">Segundo Apellido: </label>
				</td>						

				<td align="left" nowrap="nowrap">
           			<s:textfield disabled="%{flagDisabled}" name="solicitud.solicitante.persona.apellido2" 
           				id="apellido2Solicitante" 
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';" 
           				size="25" maxlength="100"/>
				</td>
				
				<td align="left" nowrap="nowrap">
					<label for="nombreSolicitante">Nombre: </label>
				</td>

				<td align="left" nowrap="nowrap">
           			<s:textfield disabled="%{flagDisabled}" name="solicitud.solicitante.persona.nombre" 
           				id="nombreSolicitante" 
           				onblur="this.className='input';" 
           				onfocus="this.className='inputfocus';" 
           				size="25" maxlength="100"/>
				</td>	
			</tr>
			
		</table>
	
		<iframe width="174" height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>

		</div>
	
	<div class="contentTabs" id="Facturacion" 
		style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
		<%@include file="../facturacion/pestaniaFacturacion.jspf" %>
	</div>
	
	<s:if test="%{solicitud.tramiteTrafico.numExpediente!=null}">	
	<div class="contentTabs" id="Resumen"  style="width:100%;">
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">SOLICITUD: </span>
				</td>
			</tr>
		</table>

		<table cellPadding="1" cellSpacing="3"  cellpadding="1" border="0" class="tablaformbasica" width="100%">					
			<s:if test="%{solicitud.tramiteTrafico.numExpediente!=null}">
				<s:if test="%{solicitud.tramiteTrafico.numExpediente!='-1'}">
					<tr>
						<td align="left" nowrap="nowrap" width="120">
			       			<label for="numExpediente">Número de Expediente: </label>
			       		</td>
			       		
			         	<td align="left" nowrap="nowrap">
			       			<s:label value="%{solicitud.tramiteTrafico.numExpediente}"/>
			       		</td>	
					</tr>
				</s:if>	
			</s:if>
			
			<s:if test="%{solicitud.tramiteTrafico.referenciaPropia!=null}">
				<tr>
					<td align="left" nowrap="nowrap" width="13%">
	       				<label for="refPropiaId">Referencia Propia: </label>
	       			</td>
		         		
		       		<td align="left" nowrap="nowrap">
		       			<s:label value="%{solicitud.tramiteTrafico.referenciaPropia}" />
		   			</td>		
				</tr>
			</s:if>
			
			<s:if test="%{solicitud.tramiteTrafico.anotaciones!=null}">
				<tr>        	       			
					<td align="left">
		   				<label for="anotacionesId">Anotaciones: </label>
		   			</td>
				
		       		<td align="left" nowrap="nowrap" colspan="3">
		   				<s:label value="%{solicitud.tramiteTrafico.anotaciones}" />
					</td>
				</tr>
			</s:if>
			<s:if test="%{solicitud.tramiteTrafico.estado!=null}">
				<tr>        	       			
					<td align="left">
		   				<label for="estadoId">Estado: </label>
		   			</td>
				
		       		<td align="left" nowrap="nowrap" colspan="3">
		   				<s:label value="%{solicitud.tramiteTrafico.estado.nombreEnum}" />
					</td>
				</tr>
			</s:if>
			<s:if test="%{solicitud.tramiteTrafico.fechaPresentacion.dia!=null}">
				<tr>
					<td align="left" nowrap="nowrap" width="15%">
						<label for="fechaPagoPresentacion">Fecha de Presentación: </label>
		   			</td>
					
					<td align="left">
						<s:label value="%{solicitud.tramiteTrafico.fechaPresentacion.dia}"/>/
						<s:label value="%{solicitud.tramiteTrafico.fechaPresentacion.mes}"/>/
						<s:label value="%{solicitud.tramiteTrafico.fechaPresentacion.anio}"/>
		   			</td>
				</tr>
			</s:if>
			<tr>
				<td>
				</td>
			</tr>
		</table>
							
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">SOLICITANTE: </span>
				</td>
			</tr>
		</table>

		<table cellPadding="1" cellSpacing="3"  class="tablaformbasica" width="100%">
			<s:if test="%{solicitud.solicitante.persona.nif!=null}">
				<tr>        	       			
					<td align="left" nowrap="nowrap" width="24%">
		   				<label for="datosSolicitante">Nombre y apellidos del solicitante: </label>
		   			</td>
		   			
		         	<td align="left" nowrap="nowrap">
		   				<s:label value="%{solicitud.solicitante.persona.nombre}"/>
		   				<s:label value="%{solicitud.solicitante.persona.apellido1RazonSocial}"/>
		   				<s:label value="%{solicitud.solicitante.persona.apellido2}"/>
		   			</td>
		   			
		   			<td width="20"></td>
		   			
		   			<td align="left" nowrap="nowrap" width="13%">
		   				<label for="nifSolicitante">NIF del solicitante: </label>
		   			</td>
		   			
		         	<td align="left" nowrap="nowrap">
		   				<s:label value="%{solicitud.solicitante.persona.nif}"/>
		   			</td>	
		   			
		   			
		      	</tr>
	      	</s:if>
	      	<tr>
	      		<td>
	      		</td>
	      	</tr>
		</table>
	
		
	<s:if test="%{solicitud.solicitudesVehiculos!=null && solicitud.solicitudesVehiculos.size > 0}">
			
		 <table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
					<span class="titulo">VEHÍCULOS</span>
				</td>
			</tr>
		</table>
		<table cellPadding="1" cellSpacing="3"  class="tablaformbasica" width="100%">
			<s:iterator var="ite" value="%{solicitud.solicitudesVehiculos}">
								<tr>
									<td align="left" >
											<s:label value="%{vehiculo.matricula}"/>&nbsp;
											<s:label value="%{vehiculo.bastidor}"/>&nbsp;&nbsp;
											<s:label value="%{tasa.codigoTasa}"/>
											<s:label value="%{@trafico.inteve.MotivoConsultaInteve@convertirTexto(motivoInteve)}"/>
									</td>
								</tr>
							</s:iterator>
			</table>
		</s:if>	
		
		<%@include file="../resumenAcciones.jspf" %>
		
	
	</div>	
	
	
	</s:if>
<div>	
<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoSolicitudes()}">
                        <table class="acciones">
                                <tr>
                                        <s:if test="solicitud.tramiteTrafico.estado!=null && solicitud.tramiteTrafico.estado.valorEnum==13">

                                        </s:if>
                                        <s:else>
                                              <%--  <td  align="center" style="size: 100%; TEXT-ALIGN: center;">                                                 
                                                        <input id="bGuardarSolicitud2"
                                                                type="button"
                                                                value="Consultar / Guardar"
                                                                style="size: 100%; TEXT-ALIGN: center;"
                                                                onClick="return guardarSolicitud(this,'Resumen');"/>
                                                </td>--%>
                                        </s:else>
                                </tr>

                                <tr>
                                        <td colspan="2">
                                                <img id="loadingImage2"
                                                        style="display:none;margin-left:auto;margin-right:auto;"
                                                        src="img/loading.gif"/>
                                        </td>
                                </tr>
                        </table>
                </s:if>
</div>

</s:form>

<!-- Scripts que se ejecutan para el funcionamiento de la pantalla -->
<script type="text/javascript">
	muestraDocumento_AVPO();
</script>

<s:if test="%{solicitud.tramiteTrafico.estado.valorEnum == 10}">
	<script type="text/javascript">
		bloqueosSiEstadoPendiente();
	</script>
</s:if>



	
