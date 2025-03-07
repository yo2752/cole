<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>
<script src="js/intervinientes/intervinientes.js" type="text/javascript"></script>
<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Intervinientes</span>
				</td>
			</tr>
		</table>
	</div>
</div>
<%@include file="../../includes/erroresYMensajes.jspf"%>
<s:form  method="post" id="formData" name="formData">
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
	       		<td align="left" nowrap="nowrap">
	       			<label for="nif">Nif:</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
       				<s:textfield name="consultaIntervinienteBean.nif" id="idNif" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="9" />
       			</td>
       			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
	       			<td align="left" nowrap="nowrap">
						<label for="numColegiado">Num Colegiado:</label> 	    			
	   				</td>     
	   				<td align="left">
	   					<s:textfield name="consultaIntervinienteBean.numColegiado" id="idNumColegiado" onblur="this.className='input2';" 
	      					onfocus="this.className='inputfocus';" size="10" maxlength="9"/>
	   				</td>
	   			</s:if>
			</tr>
			<tr>
	       		<td align="left" nowrap="nowrap">
	       			<label for="nombre">Num Expediente:</label>
	       		</td>
	       		<td align="left">
	       			<s:textfield name="consultaIntervinienteBean.numExpediente" id="idNumExpediente" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="30" />
	       		</td>
			</tr>			
		</table>
		<div class="acciones center">
			<input type="button" class="boton" name="bBuscar" id="idBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaInterviniente();"/>			
			<input type="button" class="boton" name="bLimpiar" id="idLimpiar" onclick="limpiarConsultaInterviniente()" value="Limpiar"  />		
		</div>
		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenIntervinientes" style="text-align: center;">
				<%@include file="resumenConsultaInterviniente.jspf" %>
			</div>
			<br><br>
		</s:if>
		<div id="resultado" style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
				</tr>
			</table>
		</div>
			
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right">
									<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina"
										onchange="cambiarElementosPorPaginaConsulta();" /> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>	
		<script type="text/javascript">
			$(function() {
				$("#displayTagDiv").displayTagAjax();
			})
		</script>
		
		<div id="displayTagDiv" class="divScroll">
			<display:table name="lista" excludedParams="*" requestURI="navegarConsultaInterviniente.action" class="tablacoin"
				uid="tablaIntervinientes" summary="Listado de Intervinientes" cellspacing="0" cellpadding="0" sort="external">
				
				<display:column property="id.numExpediente" title="Num Expediente" sortable="true" 
					headerClass="sortable" defaultorder="descending" style="width:4%" />
					
				<display:column property="id.numColegiado" title="NºColegiado"
					sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" />
					
				<display:column property="tipoIntervinienteVO.descTipoInterv" title="Tipo Interviniente"
					sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
					
				<display:column property="tipoIntervinienteVO.tipoInterviniente" title="" media="none" paramId="tipoInterviniente"/>
					
				<display:column property="id.nif" title="Nif" sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:8%" />
					
				<display:column property="autonomo" title="Autónomo" sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:8%" />
					
				<display:column property="conceptoRepre" title="Concepto" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:8%" />
					
			    <display:column property="idMotivoTutela" title="Motivo"
					sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" /> 
					
				<display:column property="idMotivoTutela" title="" media="none" paramId="idMotivoTutela"/>
				
				<display:column property="fechaInicio" title="Fecha Inicio Renting" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:8%" format="{0,date,dd/MM/yyyy}" />
					
				<display:column property="fechaFin" title="Fecha Fin Renting" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:8%" format="{0,date,dd/MM/yyyy}" />
					
				<display:column style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaIntervinientes.id.nif"/>_<s:property value="#attr.tablaIntervinientes.id.numColegiado"/>_<s:property value="#attr.tablaIntervinientes.id.numExpediente"/>_<s:property value="#attr.tablaIntervinientes.id.tipoInterviniente"/>"
									value="<s:property value="#attr.tablaIntervinientes.id.nif"/>_<s:property value="#attr.tablaIntervinientes.id.numColegiado"/>_<s:property value="#attr.tablaIntervinientes.id.numExpediente"/>_<s:property value="#attr.tablaIntervinientes.id.tipoInterviniente"/>"/>
								<input type="hidden" id="idDescTipoIntervcheck<s:property value="#attr.tablaIntervinientes.id.nif"/>_<s:property value="#attr.tablaIntervinientes.id.numColegiado"/>_<s:property value="#attr.tablaIntervinientes.id.numExpediente"/>_<s:property value="#attr.tablaIntervinientes.id.tipoInterviniente"/>" 
									value="<s:property value="#attr.tablaIntervinientes.descTipoInterviniente"/>">
							</td>
							<td style="border: 0px;">
								<img src="img/mostrar.gif" alt="Modificar" style="height:20px;width:20px;cursor:pointer;" 
						  			onclick="recuperarInterviniente('<s:property value="#attr.tablaIntervinientes.id.nif"/>','<s:property value="#attr.tablaIntervinientes.id.numColegiado"/>','<s:property value="#attr.tablaIntervinientes.id.numExpediente"/>','<s:property value="#attr.tablaIntervinientes.id.tipoInterviniente"/>');" title="Modificar "/>
						  	</td>
				  		</tr>
			  		</table>
				</display:column>
				
			</display:table>	
		</div>
	</div>
	<s:if test="%{lista.getFullListSize() > 0 && @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
			<div id="bloqueLoadingIntervinientes" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones center">
				<input type="button" class="boton" name="bEliminar" id="idEliminar" value="Eliminar"  onkeypress="this.onClick" onclick="return eliminarInterviniente();"/>			
			</div>
		</s:if>
	</s:form>
