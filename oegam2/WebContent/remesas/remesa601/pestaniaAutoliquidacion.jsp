<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Remesa 601</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Autoliquidación</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelImporteTotal">Importe Total<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
       			<s:textfield id="idImporteTotal"  name="remesa.importeTotal"  
	       			onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="10" />
       		</td>
       		<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esConceptoBienes(remesa)}">
	       		<td align="left" nowrap="nowrap">
					<label for="labelNumBienes">Numero de Bienes:</label>
				</td>
				<td align="left" nowrap="nowrap">
	       			<s:textfield id="idNumBienes"  name="remesa.numBienes"  
		       			onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="3" disabled="true" />
	       		</td>
	       	</s:if>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelBonificacionCuota">Bonificación</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td>
							<s:select list="remesa.modelo.bonificaciones"
							id="idBonificaciones" 
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" 
							name="remesa.bonificacion.bonificacion"
							listValue="descripcion" 
							listKey="bonificacion" 
							title="Bonificación" headerKey="" 
							headerValue="Seleccione Bonificación" 
							onchange="javascript:cargarMonto(this,'idBonificacionMonto');"/>
						</td>
						<td>
							<s:textfield name="remesa.bonificacion.monto" id="idBonificacionMonto" size="3" maxlength="3" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';" disabled="true" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td  align="left" nowrap="nowrap">
				<table>
					<tr>
						<td>
							<label for="labelExento">Exento</label>
						</td>
						<td>
							<s:checkbox name="remesa.exento" id="idExento"	onblur="this.className='input';" onfocus="this.className='inputfocus';" 
								onchange="javascript:cambiarExento('idExento','idNoSujeto', 'idFundamentoExento','idFundamentoNoSujeto');"/>	
						</td>
						<td>
							<label for="labelNoSujeto">No Sujeto</label>
						</td>
						<td>
							<s:checkbox name="remesa.noSujeto" id="idNoSujeto" onblur="this.className='input';" onfocus="this.className='inputfocus';" 
								onchange="javascript:cambiarNoSujeto('idNoSujeto','idExento','idFundamentoNoSujeto','idFundamentoExento');"/>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelFundamento">Fundamento legal:</label>
						</td>
						<td align="left" nowrap="nowrap">
			       			<s:textfield id="idFundamentoNoSujeto"  name="remesa.fundamentoNoSujeccion"  
				       			onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="30"  disabled="true" tooltip="Fundamento legal de la exención o no sujeción"/>
				       		<s:select list="remesa.modelo.fundamentosExencion"
								id="idFundamentoExento" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" name="remesa.fundamentoExencion.fundamentoExcencion"
								listValue="descripcion" listKey="fundamentoExcencion" title="Fundamento legal de la exención o no sujeción" headerKey="-1" headerValue="Seleccione Fundamento"/>
			       		</td>
			       	</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td>
							<label for="labelliquidCompl">Liquidación Complementaria:</label>
						</td>
						<td>
							<s:checkbox name="remesa.liquidacionComplementaria" id="idLiquiComp" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';" onchange="javascript:habilitarCamposLiq('idDiaFechaPrimLiq','idMesFechaPrimLiq', 'idAnioFechaPrimLiq','idCalendarFechaPrimLiq','idLiquiComp','idNumJustPrimLiq','idNumPrimLiq');"/>	
						</td>
					</tr>
				</table>
			</td>
			<td>
				<table>
					<tr>
						<td align="left" nowrap="nowrap">	
							<label for="labelFecha">Fecha:</label>
						</td>
						<td align="left" nowrap="nowrap" colspan="2">
		   					<table>
								<tr>
									<td align="left" nowrap="nowrap" width="5%">
										<s:textfield name="remesa.fechaPrimLiquidacion.dia" id="idDiaFechaPrimLiq" onblur="this.className='input2';" 
											onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';" size="2" maxlength="2" disabled="true"/>
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%">
										<s:textfield name="remesa.fechaPrimLiquidacion.mes" id="idMesFechaPrimLiq" onblur="this.className='input2';" 
											onkeypress="return validarNumerosEnteros(event)" onfocus="this.className='inputfocus';"  size="2" maxlength="2" disabled="true"/>
									</td>
									<td width="1%">/</td>
									<td align="left" nowrap="nowrap" width="5%">
										<s:textfield name="remesa.fechaPrimLiquidacion.anio" id="idAnioFechaPrimLiq" onblur="this.className='input2';" onkeypress="return validarNumerosEnteros(event)"
											onfocus="this.className='inputfocus';" size="4" maxlength="4" disabled="true"/>
									</td>
									
									<td align="left" nowrap="nowrap">
							    		<a id="idCalendarFechaPrimLiq" href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.idAnioFechaPrimLiq, document.formData.idMesFechaPrimLiq, document.formData.idDiaFechaPrimLiq);return false;" 
							    			title="Seleccionar fecha">
							    			<img class="PopcalTrigger" align="middle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
							    		</a>
									</td>
								</tr>
							</table>
		   				</td>
		   			</tr>
		   		</table>
		   	</td>
		</tr>		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNJustiPrimLiq">N. justificante de primera Autoliquidación:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="remesa.numJustiPrimeraAutoliq" id="idNumJustPrimLiq" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"  size="10" maxlength="25" disabled="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelNumPresentacion">Número de presentación:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="remesa.numPrimPresentacion" id="idNumPrimLiq" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';"  size="10" maxlength="25" disabled="true"/>
			</td>
		</tr>	
	</table>
</div>
<script type="text/javascript">
	inicializarCamposAutoLiq('idExento','idNoSujeto','idFundamentoExento','idFundamentoNoSujeto','idDiaFechaPrimLiq','idMesFechaPrimLiq', 'idAnioFechaPrimLiq','idCalendarFechaPrimLiq','idLiquiComp','idNumJustPrimLiq','idNumPrimLiq');
</script>