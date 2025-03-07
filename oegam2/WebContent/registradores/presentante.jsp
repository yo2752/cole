<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div id="presentante">
	<table class="nav" cellspacing="1" cellpadding="5" width="100%">
		<tr>
			<td class=tabular>
				<span class="titulo">Presentante de escritura</SPAN>
			</td>
		</tr>
	</table>
	
	<table cellPadding="1" cellSpacing="3"  cellpadding="1" border="0" class="tablaformbasica" width="100%">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td align="left" width="150">
				<label for="presentateTramite">Presentante del tr&aacute;mite: <span class="naranja">*</span></label>
			</td>
			<td align="right">
				<s:checkbox name="tramiteRegistro.gestor" id="gestor" onkeypress="this.onClick" onclick="seleccionadoGestor();"/>
			</td>
			<td style="vertical-align: middle;" align="left">
				<label for="gestor">Gestor</label>
			</td>
			<td align="right">
				<s:checkbox name="tramiteRegistro.gestoria" id="gestoria" onkeypress="this.onClick" onclick="seleccionadoGestoria();"/>
			</td>								
				<td style="vertical-align: middle;" align="left">
					<label for="gestoria">Gestor&iacute;a</label>
				</td>					
			</tr>
	</table>
</div>