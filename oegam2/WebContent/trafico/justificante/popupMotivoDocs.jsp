<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios">
	<div id="busqueda">
		<table width="100%">
			<tr>
				<td class="tabular" align="left"><span class="titulo">Motivo y documentos</span>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="left" nowrap="nowrap"><label
					for="idMotivoJustificante">Motivo: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:select
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="motivoJustificante"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getMotivosJustificante()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1"
						headerValue="Seleccione Motivo" disabled="false" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label
					for="idDocumentosJustificante">Documentos: <span class="naranja">*</span></label>
				</td>

				<td align="left" nowrap="nowrap">
					<s:select
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="documentosJustificante"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getDocumentosJustificante()"
						listKey="valorEnum" listValue="nombreEnum" headerKey="-1"
						headerValue="Seleccione Documentos" disabled="false" />
				</td>
			</tr>
		</table>
	</div>
</div>