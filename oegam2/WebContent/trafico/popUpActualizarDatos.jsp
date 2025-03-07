
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<div id="busqueda">
		<table width="100%">
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Actualizar Datos Expediente</span>
				</td>
			</tr>
		</table>
		<table>
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="datos">Dato a Actualizar:</label>
					</td>
					<td align="left">
						<s:select id="idDatoActualizar" headerKey="" headerValue="Seleccione Dato a Actualizar" onchange="mostrarDatosActualizar()"
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getDatosActualizados()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" listKey="valorEnum"
							listValue="nombreEnum" cssStyle="width:220px"></s:select>
					</td>
				</tr>
		</table>
		<table>
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="texto">Dato:</label>
				</td>
				<td align="left">
						<textarea  id="datosTexto" readonly="readonly"  disabled="disabled"></textarea>
				</td>
				</tr>
		</table>
		<table>
				<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="texto">Dato Actualizar:</label>
				</td>	
				<td align="left">
					<s:textfield name="idDatoNuevo" id="idDatoNuevo" size="10" maxlength="10" onkeypress="return validarNumeros(event)"/>
				</td>
				</tr>
		</table>
	</div>
</div>