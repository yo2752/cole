<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
	<tr>
		<td class="tabular">
			<span class="titulo">Listado Solicitudes Inteve</span>
		</td>
	</tr>
</table>
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="center" style="width:100%;text-align:center;align:center">
	<tr>
		<td>
			<display:table name="tramiteInteve.tramitesInteves" class="tablacoin"
						uid="tablaSolicitudesInteve"
						id="tablaSolicitudesInteve" summary=""
						excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">

				<display:column property="idTramiteInteve" title="" media="none" paramId="idTramiteInteve"/>

				<display:column property="matricula" title="Matricula" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:1%" />

				<display:column property="bastidor" title="Bastidor" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%" />

				<display:column property="nive" title="Nive" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%" />

				<display:column property="codigoTasa" title="Tasa" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%" />

				<display:column title="Tipo Informe" style="width:1%;" sortable="false" headerClass="sortable" defaultorder="descending">
					<s:property value="%{@org.gestoresmadrid.core.enumerados.TipoInformeInteve@convertir(#attr.tablaSolicitudesInteve.tipoInforme).nombreEnum}" />
				</display:column>

				<display:column title="Estado" style="width:1%;" sortable="false" headerClass="sortable" defaultorder="descending">
					<s:property value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertir(#attr.tablaSolicitudesInteve.estado).nombreEnum}" />
				</display:column>

				<display:column property="respuestaDgt" title="Respuesta" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%" />

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosSolInteve(this)'
					onKeyPress='this.onClick'/>" style="width:1%;">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecksSolInteve" id="check<s:property value="#attr.tablaSolicitudesInteve.idTramiteInteve"/>"
									value='<s:property value="#attr.tablaSolicitudesInteve.idTramiteInteve"/>' />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</td>
	</tr>
</table>
<br/>
<s:if test="%{tramiteInteve.tramitesInteves != null && tramiteInteve.tramitesInteves.size() > 0}">
	<div class="acciones center">
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esEliminable(tramiteInteve)}">
			<input type="button" class="boton" name="bModificarSolicitudIntv" id="idBModificarSolicitudIntb" value="Modificar" onClick="javascript:modificarSolictudInteve();"onKeyPress="this.onClick"/>
			<input type="button" class="boton" name="bEliminarSolicitudesIntv" id="idEliminarSolicitudesIntv" value="Eliminar" onClick="javascript:eliminarSolicitudesInteve();"onKeyPress="this.onClick"/>
		</s:if>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esDescargable(tramiteInteve)}">
			<input type="button" class="boton" name="bDescargarSolicitudesIntv" id="idDescargarSolicitudesIntv" value="Descargar" onClick="javascript:descargarSolicitudesInteve();"onKeyPress="this.onClick"/>
		</s:if>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().esReiniciableEstados(tramiteInteve)}">
			<input type="button" class="boton" name="bReiniciarSolicitudesIntv" id="idReiniciarSolicitudesIntv" value="Reiniciar Estados" onClick="javascript:reiniciarSolicitudesInteve();"onKeyPress="this.onClick"/>
		</s:if>
	</div>
</s:if>
<br/>