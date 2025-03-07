<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
	<tr>
		<td align="left" nowrap="nowrap" width="20%" style="vertical-align: middle;">
			<label for="ncorpmeCliente">Código usuario abonado<span class="naranja">*</span>:</label>
		</td>
		<td>
			<s:textfield name="tramiteRegistro.ncorpme" id="codigoCorpme" size="10" maxlength="10" 
       			readonly="true" />
		</td>
		<td align="right" nowrap="nowrap" style="vertical-align: middle;">
			<label for="ncorpmeClientePass">Usuario / Contraseña<span class="naranja">*</span>:</label>
		</td>
		<td align="right" nowrap="nowrap">
   			<s:textfield name="tramiteRegistro.usuarioCorpme" id="usuarioCorpme" size="15" maxlength="25" 
   				readonly="true" />
		</td>
		<td>/</td>
		<td>
  			<s:password name="tramiteRegistro.passwordCorpme" id="passwordCorpme" size="15" maxlength="25" 
      			 readonly="true" showPassword="true"/>
		</td>
	</tr>		
</table>