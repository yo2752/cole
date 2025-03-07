<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>


	<div>
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Selección de estado</span>
				</td>
			</tr>
		</table>
	</div> <!-- div nav -->
					
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" style="width:95%">
		<tr>
			<td>Seleccione el nuevo estado</td>
		</tr>
	</table>	
	
	<!-- Seleccionar el archivo -->
	<table class="tablaformbasica" cellspacing="3" cellpadding="0" style="width:100%">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td nowrap="nowrap" align="left" width="30%" style="vertical-align:middle">
				<label for="estadoTramiteLabel">Estados<span class="naranja">*</span>:</label>
				<s:select id="estado" listKey="valorEnum" listValue="nombreEnum"
					headerKey="-1" headerValue=""
					list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().getEstadoLicencias()"/>
			</td>
		</tr>
		
	</table>		
