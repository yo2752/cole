<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Solicitud de Información</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumExp">Num.Expediente:</label>
			</td>
			<td>
				<s:textfield id="idNumExpTramSolInfo" name="tramiteInteveDto.numExpediente"  onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" size="20" maxlength="10" disabled="true"/>
       		</td>
       		<td align="left" nowrap="nowrap">
				<label for="labelRefPropia">Referencia Propia:</label>
			</td>
			<td>
				<s:textfield id="idRefPropiaTramSolInfo" name="tramiteInteveDto.refPropia"  onblur="this.className='input2';" 
       				onfocus="this.className='inputfocus';" size="30" maxlength="50"/>
       		</td>
       	</tr>
		<tr>		
       		<td align="left" nowrap="nowrap">
				<label for="labelEstado">Estado:</label>
			</td>
			<td>
			    <s:select list="@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getEstados()" onblur="this.className='input2';" 
			    	onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
			    	name="tramiteInteveDto.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoTramSolInfo" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelFechaPresentacion">Fecha Presentación:</label>
			</td>
			<td align="left">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="tramiteInteveDto.fechaPresentacion.dia" id="diaFechaPreIni"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteInteveDto.fechaPresentacion.mes" id="mesFechaPreIni" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="tramiteInteveDto.fechaPresentacion.anio" id="anioFechaPreIni"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
						</td>
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPreIni, document.formData.mesFechaPreIni, document.formData.diaFechaPreIni);return false;" 
   								title="Seleccionar fecha">
   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
   							</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esAdmin()}">
				<td align="left" nowrap="nowrap">
					<label for="labelContrato">Contrato:</label>
				</td>
				<td>
					<s:select id="idContratoTramSolInfo" list="@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" 
						headerKey="" listValue="descripcion" cssStyle="width:220px" name="tramiteInteveDto.contrato.idContrato"
						onchange="recargarTasas();">
					</s:select>
				</td>
			</s:if>
			<s:else>
				<s:hidden name="tramiteInteveDto.contrato.idContrato"/>
			</s:else>
			<td align="left" nowrap="nowrap">
	   			<label for="labelAnotaciones">Anotaciones:</label>
	   		</td>
	       	<td>
  				<s:textarea name="tramiteInteveDto.anotaciones" id="idAnotacionesTramSolInfo" onblur="this.className='input2';" 
   					onfocus="this.className='inputfocus';" rows="5" cols="50" maxlength="400" cssStyle="width:350px; resize:none"/>
			</td>
		</tr>
	</table>
</div>