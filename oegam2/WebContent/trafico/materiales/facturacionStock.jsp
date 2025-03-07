<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<script src="js/trafico/materiales/facturacionStock.js" type="text/javascript"></script>

<script type="text/javascript">


</script>

<div class="nav">
<table cellSpacing="0" cellPadding="5" width="100%">
	<tr>		
		<td class="tabular"><span class="titulo">Resumen Facturación Stock</span></td>		
	</tr>
</table>
</div>
<s:form id="formData" name="formData">
	<s:hidden name="facturacionStock.nombreFichero"/>
	<s:hidden name="facturacionStock.fechaGenExcel"/>
	<div id="busqueda">
	    <%@include file="../../includes/erroresMasMensajes.jspf" %>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
	     		<td align="left" nowrap="nowrap">       				
					<s:label for="contratoColegiado">Contrato:</s:label>
       			</td>     
       			<td align="left">
       				<s:select id="idContratoFctStock" 
			       		list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()" 
			       		onblur="this.className='input2';"  onfocus="this.className='inputfocus';" 
			       		listKey="codigo" listValue="descripcion" cssStyle="width:150px" name="facturacionStock.idContrato" 
			       		headerKey="" headerValue="Todos"/>
       			</td>				
			</tr>
			<tr>
	     		<td width="25%" align="left" nowrap="nowrap">       				
					<s:label for="tipoDocumento">Tipo Documento:</s:label>
       			</td>     
       			<td  width="25%" align="left">
       				<s:select id="idTipoDocFctStock" 
			       				list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getTipoDocumentoImprimir()" 
			       				onblur="this.className='input2';" 
			       				onfocus="this.className='inputfocus';"
			       				onchange="javascript:cargarComboTipoDistintivoFctStock();" 
			       				listKey="valorEnum" 
								listValue="nombreEnum"
			       				cssStyle="width:150px" 
			       				name="facturacionStock.tipo" 
			       				headerKey="" headerValue="Seleccione Tipo de Documento">
					</s:select>
       			</td>
       			<td width="25%" align="left" nowrap="nowrap">
						<s:label id="labelTipoDistintivo" for="tipoDocumento">Tipo Distintivo:</s:label>
       			</td>     
       			<td width="25%" align="left">
		       		<s:select id="idTipoDstvFctStock" disabled="true"
						list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getTipoDistintivo()" 
			       		onblur="this.className='input2';" onfocus="this.className='inputfocus';" name="facturacionStock.tipoDistintivo" 
		                listKey = "valorEnum" listValue = "nombreEnum" cssStyle="width:150px" emptyOption="true" headerKey=""
						headerValue="Todos" />						   	  
       			</td>				
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<s:label id="labelFechaDesde" for="labelFecha">
						Fecha de Alta<span class="naranja">*</span>:
					</s:label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<s:label for="labelFechaAltaDesde">Desde: </s:label>
							</td>
							<td align="left">
								<s:textfield name="facturacionStock.fecha.diaInicio" id="idDiaFechaIniFctStock"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<s:label class="labelFecha">/</s:label>
							</td>
							<td align="left">
								<s:textfield name="facturacionStock.fecha.mesInicio" id="idMesFechaIniFctStock" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<s:label class="labelFecha">/</s:label>
							</td>
							<td align="left">
								<s:textfield name="facturacionStock.fecha.anioInicio" id="idAnioFechaIniFctStock"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFechaIniFctStock, document.formData.idMesFechaIniFctStock, document.formData.idDiaFechaIniFctStock);return false;" 
    								title="Seleccionar fecha">
    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
    							</a>
							</td>
						</tr>
					</table>
				</td>
				<td align="left">
					<s:label id="labelFechaHasta" for="labelFechaH">
						Fecha de Alta<span class="naranja">*</span>:
					</s:label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<s:label for="labelFechaAltaHasta">hasta:</s:label>
							</td>
							<td align="left">
								<s:textfield name="facturacionStock.fecha.diaFin" id="idDiaFechaFinFctStock" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<s:label class="labelFecha">/</s:label>
							</td>
							<td align="left">
								<s:textfield name="facturacionStock.fecha.mesFin" id="idMesFechaFinFctStock" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<s:label class="labelFecha">/</s:label>
							</td>
							<td align="left">
								<s:textfield name="facturacionStock.fecha.anioFin" id="idAnioFechaFinFctStock"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFechaFinFctStock, document.formData.idMesFechaFinFctStock, document.formData.idDiaFechaFinFctStock);return false;" 
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
				<td>
					<div class="acciones center">
						<input type="button" class="boton" name="bGenFctStock" id="idBGenFctStock" value="Generar Tabla Excel" 
						       onClick="javascript:generarExcelFctStock();" onKeyPress="this.onClick"/>
						<input type="button" class="boton" name="bLimpiarFctStock" id="idBLimpiarFctStock" value="Limpiar" 
						       onClick="javascript:limpiarFctStock();" onKeyPress="this.onClick"/>
						 <s:if test="%{facturacionStock != null && facturacionStock.esDescargable}">
						 	<input type="button" class="boton" name="bDescargarFctStock" id="idBDescargarFctStock" value="Descargar" 
						       	onClick="javascript:descargarFicheroFctStock();" onKeyPress="this.onClick"/>
						 </s:if>
					</div>
				</td>
			</tr>
		</table>
		
	</div>

</s:form>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"></iframe>
