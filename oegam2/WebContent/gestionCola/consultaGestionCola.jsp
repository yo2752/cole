<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/gestionCola/gestionCola.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Gestión de Solicitudes en Cola</span></td>
		</tr>
	</table>
</div>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" scrolling="no" frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<s:form id="formData" name="formData">
	<s:hidden name="logado"/>
	<s:hidden id="rol" name="rol"/>
	<div id="busqueda">
		<%@include file="../../includes/erroresMasMensajes.jspf" %>					
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap">
	    			<label for="labelNumExp">Nº Expediente:</label>
    	   		</td>
        		<td align="left" >
        			<s:textfield name="consultaGestionCola.numExpediente" id="idNumExpediente" onblur="this.className='input2';" 
        				onfocus="this.className='inputfocus';" size="15" maxlength="15" />
        		</td>
        		<td align="left" nowrap="nowrap">
	    			<label for="labelNumColegiado">Num Colegiado:</label>
    	   		</td>
        		<td align="left" >
        			<s:textfield name="consultaGestionCola.numColegiado" id="idNumColegiado" onblur="this.className='input2';" 
        				onfocus="this.className='inputfocus';" size="15" maxlength="15" />
        		</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
	    			<label for="labelMatricula">Matrícula:</label>
    	   		</td>
        		<td align="left" >
        			<s:textfield name="consultaGestionCola.matricula" id="idMatricula" onblur="this.className='input2';" 
        				onfocus="this.className='inputfocus';" size="15" maxlength="12" />
        		</td>
        		<td align="left" nowrap="nowrap">
	    			<label for="labelBastidor">Bastidor:</label>
    	   		</td>
        		<td align="left" >
        			<s:textfield name="consultaGestionCola.bastidor" id="idBastidor" onblur="this.className='input2';" 
        				onfocus="this.className='inputfocus';" size="25" maxlength="21" />
        		</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
	    			<label for="labelTipoTramite">Tipo Trámite:</label>
    	   		</td>
        		<td align="left" >
        			<s:select
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaGestionCola@getInstance().getTiposTramites()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey=""
						headerValue="TODOS" listKey="valorEnum" name="consultaGestionCola.tipoTramite"
						listValue="nombreEnum" id="idTipoTramite" />
        		</td>
        		<td align="left" nowrap="nowrap">
	    			<label for="labelCola">Cola:</label>
    	   		</td>
        		<td align="left" >
        			<s:select
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaGestionCola@getInstance().getNumColas()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey=""
						headerValue="TODAS" listKey="descripcion" name="consultaGestionCola.cola"
						listValue="codigo" id="idNumCola" />
        		</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
	    			<label for="labelEstado">Estado:</label>
    	   		</td>
        		<td align="left" >
        			<s:select
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaGestionCola@getInstance().getEstadosCola()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey=""
						headerValue="TODOS" listKey="valorEnum" name="consultaGestionCola.estado"
						listValue="nombreEnum" id="idEstado" />
        		</td>
        		<td align="left" nowrap="nowrap">
	    			<label for="labelProcesos">Proceso:</label>
    	   		</td>
        		<td align="left" >
        			<s:select
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaGestionCola@getInstance().getListaProcesos()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey=""
						headerValue="TODOS" listKey="nombreEnum" name="consultaGestionCola.proceso"
						listValue="nombreEnum" id="idProceso" />
        		</td>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecha">Fecha:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaDesde">Desde: </label>
							</td>
							<td align="left">
								<s:textfield name="consultaGestionCola.fecha.diaInicio" id="diaFechaAltaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha1">/</label>
							</td>
							<td align="left">
								<s:textfield name="consultaGestionCola.fecha.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha2">/</label>
							</td>
							<td align="left">
								<s:textfield name="consultaGestionCola.fecha.anioInicio" id="anioFechaAltaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIni, document.formData.mesFechaAltaIni, document.formData.diaFechaAltaIni);return false;" 
    								title="Seleccionar fecha">
    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
    							</a>
							</td>
						</tr>
					</table>
				</td>
				<td align="left">
					<label for="labelFechaH">Fecha:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaHasta">hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="consultaGestionCola.fecha.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha3">/</label>
							</td>
							<td align="left">
								<s:textfield name="consultaGestionCola.fecha.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha4">/</label>
							</td>
							<td align="left">
								<s:textfield name="consultaGestionCola.fecha.anioFin" id="anioFechaAltaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFin, document.formData.mesFechaAltaFin, document.formData.diaFechaAltaFin);return false;" 
				  					title="Seleccionar fecha">
				  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
   		    					</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bConsulta" id="idBConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return buscar();"/>			
		<input type="button" class="boton" name="bLimpiar" id="idBLimpiar" onclick="javascript:limpiarConsulta();" value="Limpiar"/>		
	</div>
	
	<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.existeBusqueda}">
	<div class="resultado" id="bloqueBusqueda">	
		<h3 class="formularios">Resultado</h3>
		<ol id="toc">
			<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.listaSolicitudesCola != null}">
				<li id="idSolicitudesCola"><a href="#SolicitudesCola">Solicitudes En Cola</a></li>
			</s:if>
			<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.listaEjecucionesProceso != null && !consultaGestionCola.gestionCola.listaEjecucionesProceso.isEmpty()}">
				<li id="idEjecucionesProceso"><a href="#EjecucionesProceso">Ejecuciones en Proceso</a></li>
			</s:if>
			<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.listaEjecucionesProcesoPorCola != null && !consultaGestionCola.gestionCola.listaEjecucionesProcesoPorCola.isEmpty()}">
				<li id="idEjecucionesProcesoPorCola"><a href="#EjecucionesProcesoPorCola">Ejecuciones en Proceso por Cola</a></li>
			</s:if>
			<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.listaUltimaEjecucion != null}">
				<li id="idUltimaEjecucion"><a href="#UltimaEjecucion">Últimas Peticiones por Cola</a></li>
			</s:if>
		</ol>
		<s:if test="%{resumen}">
			&nbsp;
			<div id="resumenGestionCola" style="text-align: center;">
				<%@include file="resumenGestionCola.jspf" %>
			</div>
		</s:if>	
		<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.listaSolicitudesCola != null}">
			<div class="contentTabs" id="SolicitudesCola" style="width:100%; border: none; background-color: rgb(235, 237, 235);">  
				<%@include file="pestaniaSolicitudesCola.jsp" %>
			</div>
		</s:if>
		<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.listaEjecucionesProceso != null && !consultaGestionCola.gestionCola.listaEjecucionesProceso.isEmpty()}">
			<div class="contentTabs" id="EjecucionesProceso" style="width:100%; border: none; background-color: rgb(235, 237, 235);">  
				<%@include file="pestaniaEjecucionesProceso.jsp" %>
			</div>
		</s:if>
		<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.listaEjecucionesProcesoPorCola != null && !consultaGestionCola.gestionCola.listaEjecucionesProcesoPorCola.isEmpty()}">
			<div class="contentTabs" id="EjecucionesProcesoPorCola" style="width:100%; border: none; background-color: rgb(235, 237, 235);">  
				<%@include file="pestaniaEjecucionesProcesoPorCola.jsp" %>
			</div>
		</s:if>
		<s:if test="%{consultaGestionCola.gestionCola != null && consultaGestionCola.gestionCola.listaUltimaEjecucion != null}">
			<div class="contentTabs" id="UltimaEjecucion" style="width:100%; border: none; background-color: rgb(235, 237, 235);">  
				<%@include file="pestaniaUltimaEjecucion.jsp" %>
			</div>
		</s:if>
	</div>
	</s:if>
	<s:else>
		<div class="acciones center">
		<table width="95%">
			<tr>
				<td>
					<span>No existen resultados</span>
				</td>
			</tr>
		</table>
		</div>
	</s:else>
</s:form>