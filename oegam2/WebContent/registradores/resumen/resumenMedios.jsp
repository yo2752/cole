<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC" align="center">
	<tr align="center">
		<s:if test="tramiteRegistro.reunion.medios != null && tramiteRegistro.reunion.medios.size() > 0">
			<td align="right">
				<div class="divScroll">
					<center>
						<display:table name="tramiteRegistro.reunion.medios" excludedParams="*" 					
							class="tablacoin" uid="row" summary="Lista De Medios De Publicación" style="width:80%"
							cellspacing="0" cellpadding="0">
								<display:column property="medio.tipoMedio" title="Tipo medio" sortable="false"
									style="width:33%;text-align:left;"/>
								<display:column property="medio.descMedio" title="Descripción" sortable="false" 
									style="width:33%;text-align:left;" />
								<display:column property="fechaPublicacion" title="Fecha" sortable="false" 
									style="width:33%;text-align:left;"/>
						</display:table>
					</center>
				</div>
			</td>
		</s:if>
		<s:else>
			<td align="center" nowrap="nowrap" >
				<label for="sinMedios" style="color:red;font-size:12px">
					REQUERIDO (Al menos un medio de publicaci&oacute;n de la convocatoria)
				</label>
			</td>
		</s:else>
	</tr>		
</table>
