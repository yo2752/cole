<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<style>
	.hidden { display: none; }
</style>


<!--  //TODO MPC. Cambio IVTM. Este jsp es el bueno -->
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<script src="js/trafico/materiales/gestionStockMaterial.js" type="text/javascript"></script>

<script type="text/javascript">

</script>

<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Stock de Materiales</span>
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
					<label for="labelJefaturaProvJpt">Jefatura Provincial: </label>
				</td>
				<td  align="left" style="width: 25%;">
					<s:select id="labelJefaturaProvJpt"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getJefaturasJPTEnums()"
						headerKey=""
				   		headerValue="Seleccione Jefatura Provincial"
						name="stockFilterBean.jefaturaProvincial" 
						listKey="jefatura" 
						listValue="descripcion"/>	
				</td>

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
						name="stockFilterBean.materialId" 
						listKey="materialId" 
						listValue="nombre"/>	
				</td>
			</tr>
		</table>
			
	
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecUltRecarga">Fecha de Última Recarga:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFecUltRecargaDesde">Desde: </label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltRecarga.diaInicio" id="diaFecUltRecargaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltRecarga.mesInicio" id="mesFecUltRecargaIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltRecarga.anioInicio" id="anioFecUltRecargaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFecUltRecargaIni, document.formData.mesFecUltRecargaIni, document.formData.diaFecUltRecargaIni);return false;" 
    								title="Seleccionar fecha">
    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
    							</a>
							</td>
						</tr>
					</table>
				</td>
				<td align="left">
					<label for="labelFecUltRecargaH">Fecha de Última Recarga:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFecUltRecargaHasta">hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltRecarga.diaFin" id="diaFecUltRecargaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltRecarga.mesFin" id="mesFecUltRecargaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltRecarga.anioFin" id="anioFecUltRecargaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFecUltRecargaFin, document.formData.mesFecUltRecargaFin, document.formData.diaFecUltRecargaFin);return false;" 
				  					title="Seleccionar fecha">
				  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
   		    					</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecUltConsumo">Fecha de Último Consumo:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFecUltConsumoDesde">Desde: </label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltConsumo.diaInicio" id="diaFecUltConsumoIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecUltConsumo">/</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltConsumo.mesInicio" id="mesFecUltConsumoIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltConsumo.anioInicio" id="anioFecUltConsumoIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFecUltConsumoIni, document.formData.mesFecUltConsumoIni, document.formData.diaFecUltConsumoIni);return false;" 
    								title="Seleccionar fecha">
    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
    							</a>
							</td>
						</tr>
					</table>
				</td>
				<td align="left">
					<label for="labelFecUltConsumoH">Fecha de Último Consumo:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFecUltConsumoHasta">hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltConsumo.diaFin" id="diaFecUltConsumoFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltConsumo.mesFin" id="mesFecUltConsumoFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="stockFilterBean.fecUltConsumo.anioFin" id="anioFecUltConsumoFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFecUltConsumoFin, document.formData.mesFecUltConsumoFin, document.formData.diaFecUltConsumoFin);return false;" 
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
					       onclick="javascript:buscarGestionStockMateriales();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					       onclick="javascript:limpiarGestionStockMateriales();" value="Limpiar" />
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
										onchange="cambiarElementosPorPaginaConsulta('navegarGestionStock.action', 'displayTagDiv', this.value);" />
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
				requestURI="navegarGestionStock.action" class="tablanotifi"
				uid="tablaGestionStock" summary="Gestion de Stock de Materiales" id="tablaGestionStock"
				cellspacing="0" cellpadding="0" sort="external" decorator="${decorator}">
				
				<display:column property="materialStockId" class="hidden" headerClass="hidden" />
				
				<display:column property="jefaturaProvincial" title="Jefatura Provincial" sortable="true" 
								headerClass="sortable" defaultorder="descending" style="width:3%"  />

				<display:column property="materialVO" title="Material" sortable="true" 
					            headerClass="sortable" defaultorder="descending" style="width:3%"  />

				<display:column property="tipo" title="Tipo" sortable="true" 
					headerClass="sortable" defaultorder="descending" style="width:3%" />

				<display:column property="unidades" title="Unidades" sortable="true" class="estado"
					headerClass="sortable" defaultorder="descending" style="width:2%" />

				<display:column property="fecUltRecarga" title="Últ. Recargo" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:4%"
					format="{0, date, dd/MM/yyyy}" />

				<display:column property="fecUltConsumo" title="Últ. Consumo" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:4%"
					format="{0, date, dd/MM/yyyy}" />

				<display:column	style="width:1%" title="Sincronizar">
					<center>
						<input type="checkbox" name="IdActualizaciones" 
						    id="check_<s:property value="#attr.tablaGestionStock.materialStockId" />" 
							value='<s:property value="#attr.tablaGestionStock.materialStockId" />' />
					</center>							
				</display:column>

			</display:table>
			
			<table class="acciones">
				<tr>
					<td>
						<input type="button" 
						       class="boton" 
						       name="bSincronizarConsejo" 
						       id="bSincronizarConsejo" 
						       value="Sincronizar" 
						       onkeypress="this.onClick" 
						       onClick="actualizarInformacionStock();"/>
					</td>
				</tr>
			</table>
			
		</div>

	</div>
</s:form>

