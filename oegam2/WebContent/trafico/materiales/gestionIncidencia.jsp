<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">


<style>
	.hidden { display: none; }
</style>

<!--  //TODO MPC. Cambio IVTM. Este jsp es el bueno -->
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
<script src="js/trafico/materiales/gestionIncidencias.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Materiales Defectuosos</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">

	<div id="busqueda">
		<table class="tablaformbasica">
			<tr style="width: 100%;">
				<td align="left" nowrap="nowrap" style="width: 25%;">
					<label for="labelMateriales">Material:</label>
				</td>
				<td style="width: 25%;">
					<s:select id="labelMateriales"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getComboMateriales()"
						headerKey=""
				   		headerValue="Seleccione Material"
						name="incidenciasFilterBean.materialId" 
						listKey="materialId" 
						listValue="nombre"/>	
				</td>
				
				<td align="left" nowrap="nowrap" style="width: 25%;">
					<label for="labelJefaturaProvJpt">Jefatura Provincial: </label>
				</td>
				<td  align="left" style="width: 25%;">
					<s:select id="labelJefaturaProvJpt"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getJefaturasJPTEnums()"
						headerKey=""
				   		headerValue="Seleccione Jefatura Provincial"
						name="incidenciasFilterBean.jefaturaProvincial" 
						listKey="jefatura" 
						listValue="descripcion"/>	
				</td>
			</tr>
			
			<tr style="width: 100%;">
				<td align="left" nowrap="nowrap" style="width: 25%;">
					<label for="labelTipo">Tipo Defecto:</label>
				</td>
				<td style="width: 25%;">
					<s:select id="labelTipo"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getTipoIncidencia()"
						headerKey=""
				   		headerValue="Seleccione Tipo Defecto"
						name="incidenciasFilterBean.tipo" 
						listKey="valorEnum" 
						listValue="nombreEnum"/>	
				</td>
				
				<td align="left" nowrap="nowrap" style="width: 25%;">
					<label for="labelEstadoIncidencia">Estado Incidencia:</label>
				</td>
				<td style="width: 25%;">
					<s:select id="labelEstadoIncidencia"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getEstadoIncidencia()"
						headerKey=""
				   		headerValue="Seleccione Estado Incidencia"
						name="incidenciasFilterBean.estado" 
						listKey="valorEnum" 
						listValue="nombreEnum"/>	
				</td>
			</tr>

		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecha">Fecha de Alta:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaDesde">Desde: </label>
							</td>
							<td align="left">
								<s:textfield name="incidenciasFilterBean.fecha.diaInicio" id="diaFechaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="incidenciasFilterBean.fecha.mesInicio" id="mesFechaIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="incidenciasFilterBean.fecha.anioInicio" id="anioFechaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaIni, document.formData.mesFechaIni, document.formData.diaFechaIni);return false;" 
    								title="Seleccionar fecha">
    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
    							</a>
							</td>
						</tr>
					</table>
				</td>
				<td align="left">
					<label for="labelFechaH">Fecha de Alta:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaHasta">hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="incidenciasFilterBean.fecha.diaFin" id="diaFechaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="incidenciasFilterBean.fecha.mesFin" id="mesFechaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="incidenciasFilterBean.fecha.anioFin" id="anioFechaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaFin, document.formData.mesFechaFin, document.formData.diaFechaFin);return false;" 
				  					title="Seleccionar fecha">
				  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
   		    					</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>

		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar" 
				           id="bBuscar" value="Buscar" onkeypress="this.onClick"
					       onclick="javascript:buscarGestionIncidencias();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					       onclick="javascript:limpiarGestionIncidencias();" value="Limpiar" />
				</td>
			</tr>
		</table>

		<div id="resultado"
			style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">
						Resultado de la	b&uacute;squeda
					</td>
				</tr>
			</table>
		</div>

		<s:if
			test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" 
										value="resultadosPorPagina"
										onchange="cambiarElementosPorPaginaConsulta('navegarGestionIncidencia.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>

		<div id="displayTagDiv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" excludedParams="*"
				requestURI="navegarGestionIncidencia.action" class="tablanotifi"
				uid="tablaGestionIncidencias" id="tablaGestionIncidencias" 
				summary="Relación de Materiales Defectuosos"
				cellspacing="0" cellpadding="0" sort="external">

				<display:column property="incidenciaId" class="hidden" headerClass="hidden" />

			    <display:column title="Incidencia" style="width:1%">
	                    <a href="#" onclick="consultarIncidencia(<s:property value="#attr.tablaGestionIncidencias.incidenciaId"/>);">
	                    	<s:property value="#attr.tablaGestionIncidencias.incidenciaId"/>
	                    </a>
			    </display:column>


				<display:column property="name" title="Material" style="width:2%" 
					sortable="true"
					headerClass="sortable" defaultorder="descending" sortProperty="materialVO.name"/>

				<display:column property="descripcion" title="Jefatura" style="width:2%" 
					sortable="true"
					headerClass="sortable" defaultorder="descending" sortProperty="jefaturaProvincial.descripcion"/>

				<display:column title="Observaciones" style="width:1%">
					<s:if test="%{#attr.tablaGestionIncidencias.observaciones.length() != 0}">
						<input type="button" class="boton" name="bObtenerDescripcion" 
					           id="bBuscar_'<s:property value="#attr.tablaGestionIncidencias.incidenciaId"/>'" 
					           value="Ver Descripción" onkeypress="this.onClick"
						       onclick="javascript:mostrarDescripcion(<s:property value="#attr.tablaGestionIncidencias.incidenciaId"/>);" />
					</s:if> 
   				</display:column>
					
				<display:column property="tipo" title="Tipo" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:1%" />
					
				<display:column property="autor" title="Autor" style="width:1%" 
					sortable="true"
					headerClass="sortable" defaultorder="descending" sortProperty="autorVO.nombre"/>
					
				<display:column property="fecha" title="Fecha" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%"
					format="{0, date, dd/MM/yyyy}" />
			</display:table>
		</div>
	</div>
	
	<div id="dialog-form" ></div>

	<s:if test="hasMsg">
		<div id="dialog-confirm" title="Resultado Modificacion Incidencia">
			<div id="divMsg">
				<table  >
					<tr>
						<td align="left">
							<s:if test="typeMsg.equals('ERROR')">
								<ul style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
									<s:iterator value="errores" status="errorStatus">
										<li><span><s:property/></span></li>							
									</s:iterator>
								</ul>
							</s:if>		
							<s:if test="typeMsg.equals('OK')">
								<ul style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
									<li><span><s:text name="msg" /></span></li>							
								</ul>
							</s:if>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</s:if>
	
	
	
</s:form>

