<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
	&nbsp;
	<table class="subtitulo" cellSpacing="0" >
			<tr>
				<td style="width:100%;text-align:center;">Datos de las Solicitudes en Cola</td>
			</tr>
	</table>
		
	<script type="text/javascript">
			$(function() {
				$("#displayTagDivPeticionesCola").displayTagAjax();
			})
	</script>
	<div id="displayTagDivPeticionesCola" class="divScroll" >
		<s:if test="%{consultaGestionCola.gestionCola.listaSolicitudesCola.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right">
									<label for="labelResultadosPorPaginaPeticionesCola">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';"
												id="idResultadosPorPaginaSolicitudesCola"
												name= "consultaGestionCola.gestionCola.resultadosPorPaginaSolicitudesCola"
												value="consultaGestionCola.gestionCola.resultadosPorPaginaSolicitudesCola"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaPeticionesCola();" >
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<s:if test="%{consultaGestionCola.gestionCola.errorPetCola}">
			<%@include file="../../includes/erroresMasMensajes.jspf"%>
		</s:if>
		<display:table name="consultaGestionCola.gestionCola.listaSolicitudesCola" 
			requestURI="navegarPeticionesColaGCAjax.action?numPorPage=${consultaGestionCola.gestionCola.resultadosPorPaginaSolicitudesCola}#SolicitudesCola" 
			excludedParams="*"
			class="tablacoin" summary="Listado de Procesos" 
			cellspacing="0" cellpadding="0" uid="tablaPeticionesPendientes"
			decorator="org.gestoresmadrid.oegam2.colas.view.decorator.GestorColaDecorator">
	  
	  		<display:column property="idEnvio" title="Id Env." headerClass="centro"	style="width:2%;" />
	  
			<display:column property="proceso" title="Proceso" headerClass="centro"	style="width:5%;" />
		
			<display:column property="idTramite" title="Id Tramite" headerClass="centro" style="width:5%;"/>
	 
			<display:column property="bastidor" title="Bastidor" style="width:8%;" headerClass="centro"/>
			
			<display:column title="Matricula" property="matricula" headerClass="centro" style="width:5%;" />
			
			<display:column title="En" property="nIntento" headerClass="centro" style="width:1%;" />
			
			<display:column property="cola"	title="HI" headerClass="centro" style="width:1%;" />
			
			<display:column title="Estado Cola" property="estado" headerClass="centro" style="width:5%;"/>
			
			<display:column title="Fecha" property="fechaHora" headerClass="centro" style="width:10%;"/>
			
			<display:column title="<input type='checkbox' name='checkAllSolCola' onClick='marcarTodos(this);' 
				onKeyPress='this.onClick'/>" style="width:1%" >
				<table align="center">
					<tr>
						<td style="border: 0px;">
							<input type="checkbox" name="listaChecksPeticionesPendientes" id="<s:property value="#attr.tablaPeticionesPendientes.numExpediente"/>" 
							value="<s:property value="#attr.tablaPeticionesPendientes.idEnvio"/>"  onClick="desmarcarTodos()"/>
						</td>
						<s:if test="%{#attr.tablaPeticionesPendientes.respuesta != null}">
						<td style="border: 0px;">				  		
						  		<img src="img/mostrar.gif" alt="<s:property value="#attr.tablaPeticionesPendientes.respuesta"/>" 
						  		style="height:20px;width:20px;cursor: pointer;" title="<s:property value="#attr.tablaPeticionesPendientes.respuesta"/>"/>
						  </td>
						</s:if>
					</tr>
				</table>		
 			</display:column>
		</display:table>
	</div>
	<s:if test="%{rol == 'ROL_ADMIN'}">
		<div class="acciones center">
			<div id="bloqueLoadingPestaniaSolicitudes" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<input type="button" class="botonGrande" name="bSubirPrioridad" id="idBSubirPrioridad" value="Subir Prioridad"  onkeypress="this.onClick" onclick="subirPrioridad();"/>			
			<input type="button" class="botonGrande" name="bReactivarSolicitudes" id="idBReactivarSolicitudes" onclick="reactivarSolicitudes();" value="Reactivar Solicitud"/>	
			<input type="button" class="botonGrande" name="bFinalizarError" id="idBFinalizarError" value="Finalizar Error"  onkeypress="this.onClick" onclick="finalizarError();"/>			
			<input type="button" class="botonGrande" name="bFinalizarErrorServicio" id="idBFinalizarErrorServicio" onclick="finalizarErrorServicio();" value="Finalizar Error Servicio"/>	
			<input type="button" class="botonGrande" name="bSacarCola" id="idBSacarCola" onclick="sacarCola();" value="Sacar de Cola"/>		
		</div>
		</s:if>
	&nbsp;
