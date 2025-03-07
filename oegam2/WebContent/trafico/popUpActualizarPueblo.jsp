
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<div id="busqueda">
		<table width="100%">
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Actualizar pueblo interviniente</span>
				</td>
			</tr>
		</table>
		<table>
			<tr>
			
				<td align="left">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoIntervinientePueblo()" onchange="mostrarDatosInterviniente()" id="idIntervinientePueblo"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" value="%{intervinienteTrafico.tipoInterviniente}"
						listValue="nombreEnum" listKey="valorEnum" title="Tipo Persona" headerKey="-1" 
						headerValue="-" disabled="false"/>
				</td>
			</tr>
			</table>
			<table>
			<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="texto">Pueblo Interviniente:</label>
				</td>
				<td align="left">
						<textarea  id="idDatosInterviniente" readonly="readonly"  disabled="disabled"></textarea>
				</td>
			</tr>
		</table>
			<table>
				<tr>
				<td align="left" nowrap="nowrap" style="width:10%;">
					<label for="texto">Pueblo Actualizar:</label>
				</td>	
				<td align="left">
					<textarea  id="idPuebloNuevo"></textarea>
				</td>
				</tr>
		</table>
	</div>
</div>