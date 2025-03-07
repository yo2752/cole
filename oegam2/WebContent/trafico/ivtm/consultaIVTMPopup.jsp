<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">PEDIR MATRÍCULA CONSULTA IVTM</span>
				</td>
			</tr>
		</table>
	</div>
	
	<s:form  method="post" id="formData2" name="formData2">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="numMatricula">Matrícula:<span class="naranja">*</span></label>
				</td>
				<td align="left" nowrap="nowrap">
				<s:textfield id="numMatricula" name="ivtmConsultaDto.matricula"  onblur="this.className='input2';" 
				       				onfocus="this.className='inputfocus';" size="7" maxlength="7"/>
				</td>
			</tr>
		</table> 

	</s:form>
		
<style>
    .leftButton
    {
        margin-left: 0px !important;
    }
    .rightButton
    {
        margin-right: 350px !important;
    }
</style>