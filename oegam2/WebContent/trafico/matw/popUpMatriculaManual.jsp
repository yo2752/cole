
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/general.js" type="text/javascript"></script>
<div class="popup formularios">
	<div id="busqueda">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Introducir Matrícula</span>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="left">
				<label for="matriculaManual">Matrícula<span class="naranja">*</span></label>
				<s:textfield name="matriculaManual" id="matriculaManual" size="15" maxlength="15" 
					onblur="this.className='input';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"   />
				</td>
			</tr>
		</table>
	</div>
</div>