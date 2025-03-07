<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Liquidación</span>
			</td>
		</tr>
	</table>
	<table class="tablaformbasica">
		<tr>
			<td align="left">
				<label for="labelBaseImponible">Base Imponible..................................</label>
			</td>
			<td align="left">
				<table>
					<tr>
						<td width="1%">1</td>
						<td>
							<s:textfield id="idBaseImponible" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(modeloDto.baseImponible)}" size="10" readonly="true" onkeyup="return cambiarComa(this);"/>
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
							<label for="labelReduccion">Reduccion................</label>
						</td>
						<td>
							<table>
								<tr>
									<td width="1%">2</td>
									<td>
										<s:textfield id="idReduccion" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(null)}" readonly="true" size="3" onkeyup="return cambiarComa(this);"/>
									</td>
									<td width="1%">%</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td width="1%">3</td>
						<td><s:textfield id="idTextoLiq" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(null)}" readonly="true" onkeyup="return cambiarComa(this);"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelBaseLiquidable">Base Liquidable..........................{1-3}</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td width="1%">4</td>
						<td>
							<s:textfield id="idBaseLiquidable" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(modeloDto.baseLiquidable)}" size="10" readonly="true" onkeyup="return cambiarComa(this);"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTipo">Tipo...................................................</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td width="1%">5</td>
						<td>
							<s:if test="%{modeloDto.tipoImpuesto != null}">
								<s:textfield id="idTipo" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(modeloDto.tipoImpuesto.monto)}" size="10" readonly="true" onkeyup="return cambiarComa(this);"/>
							</s:if>
							<s:else>
								<s:textfield id="idTipo" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(null)}" size="10" readonly="true" onkeyup="return cambiarComa(this);"/>
							</s:else>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTipo">Cuota................................................</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td width="1%">6</td>
						<td>
							<s:textfield id="idCuota" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(modeloDto.cuota)}" size="10" readonly="true" onkeyup="return cambiarComa(this);"/>
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
							<label for="labelBonificacionCuota">Bonificación Cuota.......</label>
						</td>
						<td>
							<table>
								<tr>
									<td width="1%">7</td>
									<td>
										<s:if test="%{modeloDto.bonificacion != null}">
											<s:textfield id="idBoniMonto" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(modeloDto.bonificacion.monto)}" readonly="true" size="3" onkeyup="return cambiarComa(this);"/>
										</s:if>
										<s:else>
											<s:textfield id="idBoniMonto" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(null)}" readonly="true" size="3" onkeyup="return cambiarComa(this);"/>
										</s:else>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td width="1%">8</td>
						<td>
							<s:textfield id="idBoniCuota" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(modeloDto.bonificacionCuota)}" readonly="true" onkeyup="return cambiarComa(this);"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTipo">Ingresar....................................{6-8}</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td width="1%">9</td>
						<td>
							<s:textfield id="idIngresar" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(modeloDto.ingresar)}" size="10" readonly="true" onkeyup="return cambiarComa(this);"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr/>
		<tr/>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTipo">TOTAL A INGRESAR......................</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td width="1%">10</td>
						<td>
							<s:textfield id="idTotalIngresar" value="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().convertirTextoLiquidaciones(modeloDto.totalIngresar)}" size="10" readonly="true" onkeyup="return cambiarComa(this);"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>