<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<div id="busqueda">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Cambiar Estado</span>
				</td>
			</tr>
		</table>
		<table>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="estado">Estado:</label>
					</td>
					<td align="left">
						<s:select id="idEstadoNuevo" headerKey="" headerValue="Seleccione Estado"
							list="@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().getEstados()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" listKey="valorEnum"
							listValue="nombreEnum" cssStyle="width:220px"></s:select>
					</td>
				</tr>
			</s:if>
		</table>
	</div>
</div>
