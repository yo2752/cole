<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

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
<script src="js/trafico/materiales/gestionMateriales.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Materiales</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">

	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
       			<td align="left" nowrap="nowrap">
	       			<label for="labelEstado">Estado:</label>
	       		</td>
       			<td align="left" nowrap="nowrap">
       				<s:select 
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getEstadoMaterial()"
						id="labelEstado"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="evolucionMaterialesFilterBean.estado"
						headerKey=""
				   		headerValue="Seleccione Estado Material"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Estado"
						disabled="false"/>
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
								<s:textfield name="evolucionMaterialesFilterBean.fechaAlta.diaInicio" id="diaFechaAltaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="evolucionMaterialesFilterBean.fechaAlta.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="evolucionMaterialesFilterBean.fechaAlta.anioInicio" id="anioFechaAltaIni"
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
					<label for="labelFechaH">Fecha de Alta:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaHasta">hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="evolucionMaterialesFilterBean.fechaAlta.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="evolucionMaterialesFilterBean.fechaAlta.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="evolucionMaterialesFilterBean.fechaAlta.anioFin" id="anioFechaAltaFin"
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

		<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>

		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar" 
				           id="bBuscar" value="Buscar" onkeypress="this.onClick"
					       onclick="javascript:buscarGestionMateriales();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					       onclick="javascript:limpiarGestionMateriales();" value="Limpiar" />
				</td>
			</tr>
		</table>

		<div id="resultado"
			style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
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
										onchange="cambiarElementosPorPaginaConsulta('navegarGestionMateriales.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>

		<%@include file="../../includes/erroresYMensajes.jspf"%>

		<div id="displayTagDiv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" excludedParams="*"
				requestURI="navegarGestionMateriales.action" class="tablanotifi"
				uid="tablaGestionMaterial" summary="Gestion de Materiales" id="tablaGestionMaterial"
				cellspacing="0" cellpadding="0" sort="external" decorator="${decorator}">

				<display:column property="evolucionMaterialId" class="hidden" headerClass="hidden" />
				<display:column property="materialId"          class="hidden" headerClass="hidden" />

				<display:column property="name" title="Nombre" sortable="true" 
					headerClass="sortable" defaultorder="descending" style="width:3%" />

				<display:column property="tipo" title="Tipo" sortable="true" 
					headerClass="sortable" defaultorder="descending" style="width:3%" />

				<display:column title="Descripción" style="width:1%">
					<input type="button" class="boton" name="bObtenerDescripcion" 
				           id="bBuscar_'<s:property value="#attr.tablaGestionMaterial.evolucionMaterialId"/>'" 
				           value="Ver Descripción" onkeypress="this.onClick"
					       onclick="javascript:mostrarDescripcion(<s:property value="#attr.tablaGestionMaterial.evolucionMaterialId"/>);" />
   				</display:column>

				<display:column property="precio" title="Precio" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" 
					format="{0, number, #0.0000} €"/>

				<display:column property="color" title="Color" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" />

				<display:column property="estado" title="Estado" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" 
					class="estado"/>

				<display:column property="fechaAlta" title="Fecha" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:4%"
					format="{0, date, dd/MM/yyyy}" />

				<display:column
					title=""
					style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/history.png" alt="Ver Evolución" style="margin-right: 10px; height: 20px; 
								     width: 20px; cursor: pointer;"	
								     onclick="abrirEvolucion('<s:property value="#attr.tablaGestionMaterial.evolucionMaterialId"/>',
								     						 '<s:property value="#attr.tablaGestionMaterial.materialId"/>',
								                             'divEmergenteConsultaHistoricoMateriales');"
								     title="Ver Evolución" />
							</td>
						</tr>
					</table>
				</display:column>

			</display:table>
		</div>

	</div>
	<div id="divEmergenteConsultaHistoricoMateriales" style="display: none; background: #f4f4f4;"></div>
</s:form>

