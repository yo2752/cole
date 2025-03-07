<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<s:hidden name="esDocPermisos" id="esDocPermisos"/>
	<div id="busqueda">
		<s:if test="esDocPermisos">
			<table width="100%">
				<tr>
					<td class="tabular" align="left">
						<span class="titulo">Introducir número de serie</span>
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelSerieInic">Serie inicial:</label>
					</td>
					<td  align="left">
						<s:textfield name="numeroSerie.serieInicial" id="idserieInicial" size="2" maxlength="2" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelSerieEspacio">-</label>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelSerieFin">Serie final:</label>
					</td>
					<td  align="left">
						<s:textfield name="numeroSerie.serieFinalI" id="idserieFinalI" size="8" maxlength="8" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelSerieBarra">/</label>
					</td>
					<td  align="left">
						<s:textfield name="numeroSerie.serieFinalF" id="idserieFinalF" size="8" maxlength="8" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<table width="100%" >
				<tr>
					<td>
						<label for="lMensaje">Recuerde que una vez descargado el documento ya no se podra volver a descargar. Si necesita otra copia debera de volver a solicitarlo.</label>
					</td>
				</tr>
			</table>
		</s:else>
	</div>
</div>