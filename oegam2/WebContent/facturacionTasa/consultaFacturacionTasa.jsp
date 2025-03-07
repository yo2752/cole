<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/facturacionTasa.js" type="text/javascript"></script>

<script  type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Facturación de Tasa</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../../includes/erroresYMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">
	<div id="busqueda">					
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap">
	        			<label for="numExpediente">NºExpediente: </label>
	       		</td>
       			<td align="left" colspan="3">
        			<s:textfield name="consultaTramiteTraficoFactBean.numExpediente" id="numExpediente" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" maxlength="15" cssStyle="width:16em;" />
       			</td>
       			<td align="left" nowrap="nowrap">
       				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<label for="numColegiado">Num Colegiado: </label> 
       				</s:if>
       			</td>     
       			<td align="left">
       				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
       					<s:textfield name="consultaTramiteTraficoFactBean.numColegiado" id="numColegiado" onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" size="10" maxlength="9"/>
        			</s:if>
       			</td>							        	
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
	        			<label for="numExpediente">Código de Tasa: </label>
	       		</td>
       			<td align="left" colspan="3">
        			<s:textfield name="consultaTramiteTraficoFactBean.codigoTasa" id="codigoTasa" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" maxlength="15" cssStyle="width:16em;" />
       			</td>
       			<td align="left" nowrap="nowrap">
	        			<label for="numExpediente">Nif: </label>
	       		</td>
       			<td align="left" colspan="3">
        			<s:textfield name="consultaTramiteTraficoFactBean.nif" id="nif" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" maxlength="15" cssStyle="width:16em;" />
       			</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
        			<label for="bastidor">Bastidor: </label>
      			</td>
   				<td align="left">
       				<s:textfield name="consultaTramiteTraficoFactBean.bastidor" id="bastidor" onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';"  size="22" maxlength="21"/>
       			</td>
	        	<td align="left" nowrap="nowrap">
        			<label for="matricula">Matrícula: </label>
      			</td>
   				<td align="left">
        			<s:textfield name="consultaTramiteTraficoFactBean.matricula" id="matricula" onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" size="10" maxlength="10" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
       			</td>
			</tr>
		</table>
	    <table cellSpacing="3" class="tablaformbasica" cellPadding="0">	
        	<tr>
        		<td align="right" nowrap="nowrap">
       				<label>Fecha de Aplicación de Tasa: </label>
   				</td>
				<td align="left">
					<TABLE WIDTH=100%>
						<tr>
							<td align="right">
								<label for="fechaAplicacionIni">desde: </label>
							</td>
							<td>
								<s:textfield name="consultaTramiteTraficoFactBean.fechaFiltradoAplicacion.diaInicio" id="diaAplicacionIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  
									size="2" maxlength="2"/>
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTramiteTraficoFactBean.fechaFiltradoAplicacion.mesInicio" id="mesAplicacionIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="2" maxlength="2"/>
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTramiteTraficoFactBean.fechaFiltradoAplicacion.anioInicio" id="anioAplicacionIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="5" maxlength="4"/>
							</td>
							<td>
			    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAplicacionIni, document.formData.mesAplicacionIni, document.formData.diaAplicacionIni);return false;" title="Seleccionar fecha">
				    				<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
				    			</a>
							</td>
							<td width="2%"></td>
						</tr>
					</TABLE>
				</td>
				<td align="left">
					<TABLE WIDTH=100%>
						<tr>
							<td align="left">
								<label for="fechaAplicacionFin">hasta:</label>
							</td>
							<td>
								<s:textfield name="consultaTramiteTraficoFactBean.fechaFiltradoAplicacion.diaFin" id="diaAplicacionFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTramiteTraficoFactBean.fechaFiltradoAplicacion.mesFin" id="mesAplicacionFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTramiteTraficoFactBean.fechaFiltradoAplicacion.anioFin" id="anioAplicacionFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="5" maxlength="4" />
							</td>
							<td>
					    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAplicacionFin, document.formData.mesAplicacionFin, document.formData.diaAplicacionFin);return false;" title="Seleccionar fecha">
						    		<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
			   			    	</a>
							</td>
						</tr>
					</TABLE>
				</td>
			</tr>
		</table>
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarFacturacionTasa();"/>			
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" onclick="limpiarFacturacionTasa()" value="Limpiar"  />			
				</td>
			</tr>
		</table>
	
	<s:hidden key="contrato.idContrato"/>

	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

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
									onchange="cambiarElementosPorPaginaConsulta('navegarConsultaFacturacionTasas.action', 'displayTagDiv', this.value);" /> 
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
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" requestURI="navegarConsultaFacturacionTasas.action" class="tablacoin"
			uid="tablaFacturacionTasa" summary="Listado Facturación Tasa" cellspacing="0" cellpadding="0" sort="external">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<display:column property="numColegiado" title="Colegiado" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%;"/>	
				</s:if>
				<display:column property="id.numExpediente" title="Nº Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%;" href="detalleConsultaFacturacionTasas.action" paramId="numExpediente" />
				<display:column property="id.codigoTasa" title="Código Tasa" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%;"/>
				<display:column property="nif" title="Nif" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%;"/>
				<display:column property="bastidor" title="Bastidor" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%;"/>
				<display:column property="matricula" title="Matricula" sortable="true" headerClass="sortable"
					defaultorder="descending" maxLength="20" style="width:4%" />
				<display:column property="fechaAplicacion" title="Fecha Aplicación" sortable="true" headerClass="sortable" format="{0,date,dd-MM-yyyy}"
					defaultorder="descending" style="width:4%" />
		</display:table>	
	</div>
	</div>
</s:form> 

