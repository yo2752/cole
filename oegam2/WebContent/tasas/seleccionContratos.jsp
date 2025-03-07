<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<div id="busqueda">
		<table width="100%">
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Generar Tasas</span>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="left" nowrap="nowrap" width="8%">
					<label>Contratos: </label>
				</td>
				<td align="left">
					<s:if test="numColegiadoTasas == null">
						<s:select id="contrato"
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitadosColegiado()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" listKey="codigo"
							listValue="descripcion" cssStyle="width:150px"
							name="contratoTasas"></s:select>
					</s:if>
					<s:else>
						<s:select id="contrato"
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitadosColegiado(numColegiadoTasas)"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" listKey="codigo"
							listValue="descripcion" cssStyle="width:150px"
							name="contratoTasas"></s:select>
					</s:else>
				</td>
			</tr>
		</table>
	</div>
</div>
