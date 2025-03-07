<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/arrendatarios/arrendatarios.js" type="text/javascript"></script>
<script src="js/conductor/conductor.js" type="text/javascript"></script>


<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<s:if test="%{@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().esOperacionAltaConductor(conductorDto)}">	
					<span class="titulo">Alta Conductor Habitual</span>
				</s:if>
				<s:else>
					<span class="titulo">Modificación Conductor Habitual</span>
				</s:else>				
			</td>
		</tr>
	</table>
</div>

<div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
		scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"> 
	</iframe>
		
	<ol id="toc" >
		<li id="DatosTramite"><a href="#DatosTramite">Datos Tramite</a></li>
		<li id="Consulta"><a href="#Consulta">Conductor</a></li>
		<li id="Vehiculo"><a href="#Vehiculo">Vehículo</a></li>
		<s:if test="conductorDto != null && conductorDto.estado != null" >
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>
		
	<table class="subtitulo" cellSpacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				
				<s:if test="%{@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().esOperacionAltaConductor(conductorDto)}">	
					<td>Datos del Alta de Conductor Habitual</td>
				</s:if>
				<s:else>
					<td>Datos de la Modificación de Conductor Habitual</td>
				</s:else>	
								
				
				<td style="border: 0px;" align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  	onclick="abrirEvolucionAltaCayc('idNumExpConductor','divEmergenteEvolucionAltaCayc');" title="Ver evolución"/>
				</td>
			</tr>
	</table>
	
	<s:form method="post" id="formData" name="formData">	
		<s:hidden name="conductorDto.numColegiado"/>		
		<s:hidden name="conductorDto.fechaAlta"/>
		<s:hidden id="idNumExpConductor" name="conductorDto.numExpediente"/>
		<s:hidden name="conductorDto.idConductor"/>	
		<s:hidden name="conductorDto.estado"/>
		<s:hidden name="conductorDto.operacion"/>		
		<s:hidden name="conductorDto.numRegistro"/>
			
			
		<div class="contentTabs" id="DatosTramite" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaDatosTramiteConductor.jsp" %>
		</div>		
		<div class="contentTabs" id="Consulta" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaConsultaConductor.jsp" %>
		</div>
		<div class="contentTabs" id="Vehiculo" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaVehiculoConductor.jsp" %>
		</div>	
				
		 <div class="contentTabs" id="Resumen" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">  
			<%@include file="pestaniaResumenConductor.jsp" %>
		</div> 		 
		&nbsp;
		<div class="acciones center">
			<s:if test="%{@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().esEstadoGuardable(conductorDto)}">
				<input type="button" class="boton" name="bGuardarConductor" id="idGuardarConductor" value="Guardar" onClick="javascript:guardarConductor();"
		 			onKeyPress="this.onClick"/>
		 	</s:if>
		 	<s:if test="%{@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().esEstadoConsultable(conductorDto)}">	
				<input type="button" class="boton" name="bConsultarConductor" id="idConsultarConductor" value="Consultar" onClick="javascript:consultarConductor();"
			 		onKeyPress="this.onClick"/>	
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().esEstadoIniciado(conductorDto)}">	
				<input type="button" class="boton" name="bValidarConductor" id="idValidarConductor" value="Validar" onClick="javascript:validarConductor();"
			 		onKeyPress="this.onClick"/>	
			</s:if>		
			<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver Listado" onClick="javascript:Volver_Conductor(); "
			 		onKeyPress="this.onClick"/>	
			
					
		</div>
		<div id="divEmergenteEvolucionAltaCayc" style="display: none; background: #f4f4f4;"></div>
	</s:form>	
</div>
<script type="text/javascript">
	datosInicialesConductor();
</script>
