<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div class="contenido">

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Datos Vehículo:</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNIF">DOI Titular Vehiculo:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idDoiVehiculoArrendatario" name="arrendatarioDto.doiVehiculo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="9"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelMatriculaArrendatario"><span class="naranja">*</span>Matricula:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idMatriculaArrendatario" name="arrendatarioDto.matricula"  onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="20" maxlength="7" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelBastidor"><span class="naranja">*</span>Bastidor:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idBastidorArrendatario" name="arrendatarioDto.bastidor" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="30" maxlength="21"/>
			</td>
		</tr>
	</table>
</div>