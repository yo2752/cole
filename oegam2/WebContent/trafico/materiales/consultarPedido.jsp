<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<s:set var="pedidoDgt" value="pedidoDto.pedidoDgt" />

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Consulta Pedido</span>
				</td>
			</tr>
		</table>
	</div>

	<div id="contenido">
		<div id="datosGenerales">
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" >
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelidpedido">Pedido:</label>
					</td>
					<td  align="left" >
						<s:textfield name="pedidoDto.pedidoId" 
									 id="labelidpedido" 
						             size="25" maxlength="25"  
						             readonly="true"/>
					</td>
					
					<td align="left" nowrap="nowrap">
						<label for="labelPedidoColegio">Pedido Colegio:</label>
					</td>
					<td  align="left" >
						<s:textfield name="pedidoDto.pedidoInvId" 
									 id="labelPedidoColegio" 
						             size="25" maxlength="25" 
						             readonly="true" />
					</td>
				</tr>
				
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelestado">Estado:</label>
					</td>
					<td  align="left" >
						<s:textfield name="pedidoDto.nomEstado" 
									 id="labelestado" 
						             size="25" maxlength="25" 
						             readonly="true" />
					</td>

					<s:if test="%{pedidoDto.pedidoDgt == \"N\"}">
						<td align="left" nowrap="nowrap" colspan="2">
							<span style="color:#009900">Pedido para el Consejo General (CGGA)</span>
						</td>
					</s:if>
					<s:else>
						<td align="left" nowrap="nowrap" colspan="2">
							<span style="color:#ff6600">Pedido para la DGT</span>
						</td>
					</s:else>
					
				</tr>
	      		<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelMaterialNombre">Material: </label>
					</td>
					<td  align="left">
						<s:textfield name="pedidoDto.materialNombre" id="labelMaterialNombre" 
				             size="25" maxlength="25"
				             readonly="true"/>
					</td>
					
					<td align="left" nowrap="nowrap">
						<label for="labelUnidades">Unidades Solicitadas: </label>
					</td>
					<td  align="left">
						<s:textfield name="pedidoDto.unidades" id="labelUnidades" 
				             size="25" maxlength="25"
				             readonly="true"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelobservaciones">Observaciones:</label>
					</td>
					<td  align="left" colspan="3">
						<s:textarea id="labelobservaciones" 
							name="pedidoDto.observaciones" 
							cols="100" rows="4" readonly="true"/>
					</td>
				</tr>
				
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelcodigoInicial">Código Inicial:</label>
					</td>
					<td  align="left">
						<s:textfield name="pedidoDto.codigoInicial" id="labelcodigoInicial" 
						             size="25" maxlength="25" readonly="true"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelcodigoFinal">Código Final:</label>
					</td>
					<td  align="left">
						<s:textfield name="pedidoDto.codigoFinal" id="labelcodigoFinal" 
						             size="25" maxlength="25" 
						             readonly="true" />
					</td>
				</tr>

	      		<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelJefaturaProvJpt">Jefatura Provincial: </label>
					</td>
					<td  align="left">
						<s:textfield name="pedidoDto.jefaturaDescripcion" id="labelJefaturaProvJpt" 
				             size="25" maxlength="25"
				             readonly="true"/>
					</td>
					
					<td align="left" nowrap="nowrap">
						<label for="labelAutor">Solicitante: </label>
					</td>
					<td  align="left">
						<s:textfield name="pedidoDto.autorNombre" id="labelAutor" 
				             size="25" maxlength="25"
				             readonly="true"/>
					</td>
				</tr>
	      		<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha Solicitud: </label>
					</td>
					<td  align="left">
						<s:textfield name="pedidoDto.fecha" id="labelFecha" 
				             size="25" maxlength="25"
				             readonly="true"/>
					</td>
				
				</tr>
			</table>				
		</div>
		
	</div>	
</div>
