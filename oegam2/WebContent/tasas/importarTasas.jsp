<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<div id="busqueda">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Importar tasas</span>
				</td>
			</tr>
		</table>
		<table>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="contrato">Contrato:</label>
					</td>
					<td align="left">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<s:select id="contratoImportacion"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" listKey="codigo"
								listValue="descripcion" cssStyle="width:220px"
								name="contratoImportacion"></s:select>
						</s:if>
						<s:else>
							<s:select id="contratoImportacion"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitadosColegio(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoBigDecimal())"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" listKey="codigo"
								listValue="descripcion" cssStyle="width:220px"
								name="contratoImportacion"></s:select>
						</s:else>
					</td>
				</tr>
			</s:if>
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="formato">Formato:</label>
				</td>
				<td align="left">
					<s:radio cssStyle="display: inline;"
						name="formatoImportacion" id="formato"
						list="@org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa@values()"
						listKey="codigo" listValue="descripcion" labelposition="none" />
				</td>
			</tr>
		</table>
	</div>
</div>
