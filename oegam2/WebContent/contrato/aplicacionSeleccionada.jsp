 <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<s:hidden id="codAplicacionSeleccionada" name="codAplicacionActiva"></s:hidden>  
		
<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Permisos contrato/aplicación </td>
	</tr>
</table>
									
<display:table name="listaPermisosAplicacion" excludedParams="*" class="tablacoi" summary="Listado de Funciones" decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorAplicacionSeleccionada"
	cellspacing="0" cellpadding="0" uid="listaPermisosAplicacionTable">
		
		<display:column title="C&oacute;digo Aplicaci&oacute;n" sortable="false" headerClass="sortable" defaultorder="descending"							
			style="width:20%;text-align:left;" > 
				<s:iterator var="counter" begin="0" end="#attr.listaPermisosAplicacionTable.nivel - 1" >
	    			&nbsp;
					&nbsp;
 				</s:iterator>
				<s:if test="%{#attr.listaPermisosAplicacionTable.esPadre}">
					<img id="desp${listaPermisosAplicacionTable.codigoFuncion}"  alt="Mostrar" src="img/plus.gif" onclick="return mostrarLineas('${listaPermisosAplicacionTable.codigoFuncion}');" style="cursor:pointer;" />
				</s:if>
				<s:else>
					&nbsp;
					&nbsp;
				</s:else>
				${listaPermisosAplicacionTable.codigoAplicacion}
		</display:column>
		
		<display:column property="codFuncionPadre" title="Funci&oacute;n Padre" sortable="false" headerClass="sortable" defaultorder="descending" style="width:5%" />
			
		<display:column property="codigoFuncion" title="Funci&oacute;n" sortable="false" headerClass="sortable" defaultorder="descending" style="width:5%" />
			
		<display:column property="descFuncion" title="Descripci&oacute;n" sortable="false" headerClass="sortable" defaultorder="descending" style="width:40%" />
						
		<display:column property="url" title="URL" sortable="false" headerClass="sortable" defaultorder="descending" style="width:15%" maxLength="12"/>
			
		<display:column property="nivel" title="Nivel" defaultorder="descending" style="width:5%" />
			
		<display:column property="tipo" title="Tipo" sortable="false" headerClass="sortable" defaultorder="descending" style="width:5%" />
			
	    <display:column title=""  style="width:5%"> 
	       	<input 	type="checkbox" name="permisosAplicaciones" value="${listaPermisosAplicacionTable.codigoFuncion}" class="checkbox"  
      			<s:if test="%{#attr.listaPermisosAplicacionTable.asignada}">
      				checked=checked
      			</s:if> />
	   </display:column>
</display:table>
		
<table class="acciones">
	<tr>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||	@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
			<td>
				<input type="button" class="boton" name="bGuardarPermisos" id="idBotonGuardarPermisos" value="Guardar Permisos" onClick="return guardarPermisosContrato();" onKeyPress="this.onClick"/>
				&nbsp;
			</td>
		</s:if>
		<td align="center">
			<input type="button" class="boton" name="bCancelarPermisos" id="bCancelarPermisos" value="Cancelar" onClick="cancelarPermisos();" onKeyPress="this.onClick" />&nbsp;                     
	     </td>
	</tr>
</table>

<script type="text/javascript">ocultaHijos();</script>