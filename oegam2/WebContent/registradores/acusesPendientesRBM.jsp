<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="pesPendientes">
	<!-- Listado de notificaciones recibidas fuera de secuencia, pendientes de envio de acuse -->
	<s:if test="%{tramiteRegRbmDto.acusesPendientes.size>0}">

		<div id="busqueda" class="busqueda">
			<table border="0" class="tablaformbasicaTC">
				<tr>
					<td>
						<div id="resultado"
							style="width: 100%; background-color: transparent;">
							<table class="subtitulo" cellSpacing="0" style="width: 100%;">
								<tr>
									<td style="text-align: center;">Notificaciones recibidas pendientes de envío de acuse</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="divScroll">
							<display:table name="tramiteRegRbmDto.acusesPendientes" excludedParams="*" class="tablacoin" uid="row" summary="Listado de notificaciones" cellspacing="0" cellpadding="0"
							decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorRegistroFueraSecuencia">
								<display:column property="tipoMensaje" title="Tipo de mensaje" style="width:10%;text-align:left;" />
								<display:column property="fechaAlta" title="Fecha de recepción" style="width:15%;text-align:left;" defaultorder="descending"/>
								
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esPermitidoFirmarAcuseFueraSecuencia(tramiteRegRbmDto.estado,#attr.row.tipoMensaje)">
									<display:column title="Enviar acuse" style="width:40%;text-align:left;">                                        
										<a onclick="" href="javascript:construirFirmarAcuse('${row.estado}','${row.idRegistroFueraSecuencia}')">Enviar acuse</a>
									</display:column>
								</s:if>
								<s:else>
									<display:column title="Enviar acuse" style="width:40%;color:red;text-align:left;">                                        
										 
										<s:label style="color:red;text-align:left;" value="Para poder firmar este acuse debe ir antes a la pestaña resumen y firmar el acuse, ya que el trámite se encuentra en estado %{@org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@convertirTexto(tramiteRegRbmDto.estado)}."/>
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