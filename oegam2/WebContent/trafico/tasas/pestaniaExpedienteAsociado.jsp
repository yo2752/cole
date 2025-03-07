<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/gestionTasa.js" type="text/javascript"></script>
<script type="text/javascript"></script>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Expedientes Asociados</td>
		</tr>
	</table>
	<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<display:table name="tasaDto.listaExpedientesAsignados" class="tablacoin"
						uid="tablaConsTasaDetalle" summary="Detalle de Tasas"
						excludedParams="*" cellspacing="0" cellpadding="0">	

							
			<display:column property="numExpediente" title="Num.Expediente" sortable="false"
							defaultorder="descending" style="width:4%"/>
			
			<display:column property="tasa.tipoTasa" title="Tipo de Tasa" sortable="false" 
							defaultorder="descending" style="width:4%"/>
							
			<display:column property="refPropia" title="Referencia Propia" sortable="false" 
							defaultorder="descending" style="width:4%"/>
							
			<display:column title="Tipo Tramite" sortable="false" 
							defaultorder="descending" style="width:4%">
				<s:property value="%{@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@convertirTexto(#attr.tablaConsTasaDetalle.tipoTramite)}" />
			</display:column>
				
			<display:column title="Estado" defaultorder="descending" style="width:3%">
				<s:property value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(#attr.tablaConsTasaDetalle.estado)}" />
			</display:column>	
			
			<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodas(this)' onKeyPress='this.onClick'/>" style="width:1%" >
						<table align="center">
							<tr>
					  			<td style="border: 0px;">
									<input type="checkbox" name="listaChecks" id="check<s:property value="##attr.tablaConsTasaDetalle.numExpediente"/>" 
										value='<s:property value="#attr.tablaConsTasaDetalle.numExpediente"/>' />
								</td>
							</tr>
						</table>
			</display:column>	
		</display:table>
	</table>

	<div class="acciones center">
		<div id="bloqueLoadingConsultaTasas" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
		<input class="boton" type="button" id="idDesasignar" name="bDesasignar" value="Desasignar" onClick="javascript:desasignar();" onKeyPress="this.onClick" />
		&nbsp;
		<input class="boton" type="button" id="idEliminar" name="bEliminar" value="Eliminar" onClick="javascript:eliminar();" onKeyPress="this.onClick" />
		&nbsp;
		<input class="boton" type="button" name="bVolver" id="bVolver" value="Volver" onClick="javascript:volverConsultaTasas();" />
		&nbsp;
	</div>
	
	<div id="divEmergenteConsultaTasas" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteConsultaTasasEvolucion" style="display: none; background: #f4f4f4;"></div>
</div>
<%@include file="../../includes/erroresYMensajes.jspf" %>	
