<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<s:hidden name="tramiteRegRbmDto.facturaRegistro.idTransferencia" id="idTransferencia"/>

<s:hidden name="tramiteRegRbmDto.facturaRegistro.fechaPago.dia" id="diafechaPago"/>
<s:hidden name="tramiteRegRbmDto.facturaRegistro.fechaPago.mes" id="mesfechaPago"/>
<s:hidden name="tramiteRegRbmDto.facturaRegistro.fechaPago.anio" id="aniofechaPago"/>
<s:hidden name="tramiteRegRbmDto.facturaRegistro.idFactura" id="idFactura"/>

<div class="contentTabs" id="pesFacturas">
	<!-- Listado de notificaciones recibidas fuera de secuencia, pendientes de envio de acuse -->
	<s:if test="%{tramiteRegRbmDto.facturasRegistro.size>0}">

		<div id="busqueda" class="busqueda">
			<table border="0" class="tablaformbasicaTC">
				<tr>
					<td>
						<div id="resultado"
							style="width: 100%; background-color: transparent;">
							<table class="subtitulo" cellSpacing="0" style="width: 100%;">
								<tr>
									<td style="text-align: center;">Facturas recibidas</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="divScroll">
							<s:if test="!@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esPermitidoConfirmarFactura(tramiteRegRbmDto.estado)">
								<s:label style="color:red;text-align:left;" value="El estado actual del trámite no permite confirmar facturas. Estado: %{@org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@convertirTexto(tramiteRegRbmDto.estado)}."/>
							</s:if>
							<display:table name="tramiteRegRbmDto.facturasRegistro" excludedParams="*" class="tablacoin" uid="row" summary="Listado de notificaciones" cellspacing="0" cellpadding="0"
							decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorRegistroMinutasFacturas">
								<display:column property="numFactura" title="Número de factura" style="width:20%;text-align:center;" />
								<display:column property="fechaFactura" title="Fecha de factura" style="width:15%;text-align:center;" defaultorder="descending"/>
								
								
									
									<display:column title="Identificador de transferencia" style="width:30%;text-align:center;">                                        
										<s:if test="!#attr.row.fechaEnvio">
											<input type="text" id="idTransferencia_<s:property value="#attr.row.idFactura"/>" size="35" maxlength="255" />
										</s:if>
										<s:else>
											<s:property value="#attr.row.idTransferencia"/>
										</s:else>
									</display:column>
									
									<display:column title="Fecha de pago" style="width:20%;text-align:center;"> 
										<s:if test="!#attr.row.fechaEnvio">                                       
											<table style="width:20%">
												<tr>
													<td align="left">
														<input type="text" id="diafechaPago_<s:property value="#attr.row.idFactura"/>"
															onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diafechaPago'), document.getElementById('mesfechaPago'), document.getElementById('aniofechaPago'));"/>
													</td>
													<td align="left">
														<label class="labelFecha">/</label>
													</td>
													<td align="left">
														<input type="text" id="mesfechaPago_<s:property value="#attr.row.idFactura"/>"
															onfocus="this.className='inputfocus';" size="2" maxlength="2" onblur = "validaInputFecha(document.getElementById('diafechaPago'), document.getElementById('mesfechaPago'), document.getElementById('aniofechaPago'));"/>
													</td>
													<td align="left">
														<label class="labelFecha">/</label>
													</td>
													<td align="left">
														<input type="text" id="aniofechaPago_<s:property value="#attr.row.idFactura"/>"
															onfocus="this.className='inputfocus';" size="5" maxlength="4" onblur = "validaInputFecha(document.getElementById('diafechaDocumento'), document.getElementById('mesfechaDocumento'), document.getElementById('aniofechaDocumento'));"/>
													</td>
													<td align="left">
														<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.aniofechaPago_<s:property value="#attr.row.idFactura"/>, document.formData.mesfechaPago_<s:property value="#attr.row.idFactura"/>, document.formData.diafechaPago_<s:property value="#attr.row.idFactura"/>);  return false;" 
							   								title="Seleccionar fecha">
							   								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
							   							</a>
													</td>
												</tr>
											</table>
										</s:if>
										<s:else>
											<s:property value="#attr.row.fechaPago"/>
										</s:else>
									</display:column>
									
									<s:if test="!#attr.row.fechaEnvio">
										<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esPermitidoConfirmarFactura(tramiteRegRbmDto.estado)">
											<display:column title="Factura" style="width:8%;text-align:center;">                                        
												<a onclick="" href="javascript:construirConfirmacionFactura('${row.idFactura}')">Confirmar</a>
											</display:column>
										</s:if>
										<s:else>
											<display:column title="Confirmar" style="width:40%;color:red;text-align:left;">                                        
												<s:label style="color:red;text-align:left;" value="No es posible"/>
											</display:column>
										</s:else>
									</s:if>
									<s:else>	
										<display:column title="Confirmar" style="width:8%;text-align:center;">                                        
											<s:label style="color:red;text-align:left;" value="Confirmada"/>
										</display:column>
									</s:else>
							</display:table>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</s:if>
</div>