<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<s:if test="tramiteRegistro.inmuebles != null && tramiteRegistro.inmuebles.size() > 0">
			<td align="right">
				<div class="divScroll">
					<display:table name="tramiteRegistro.inmuebles" excludedParams="*" 	class="tablacoin" uid="row" summary="Inmuebles referenciados" style="width:98%"
						cellspacing="0" cellpadding="0">
							<display:column property="bien.tipoInmueble.idTipoBien" title="Tipo Bien" style="text-align:left;"/>
								<display:column property="bien.idufir" title="IDUFIR/CRU" style="text-align:left;"/>
								<display:column property="bien.direccion.nombreProvincia" title="Provincia" style="text-align:left;"/>
								<display:column property="bien.direccion.nombreMunicipio" title="Municipio" style="text-align:left;"/>
								<display:column property="bien.seccion" title="Sección" style="text-align:right;"/>
								<display:column property="bien.numeroFinca" title="Nº Finca" style="text-align:right;"/>
								<display:column property="bien.subnumFinca" title="Sub. Nº Finca" style="text-align:right;"/>
								<display:column property="bien.numFincaDupl" title="Nº Finca Dup." style="text-align:right;"/>
					</display:table>
				</div>
			</td>
		</s:if>
		<s:elseif test='%{"S".equals(tramiteRegistro.inmatriculada) && (tramiteRegistro.inmuebles == null || tramiteRegistro.inmuebles.size())}'>
			<td align="center" nowrap="nowrap" >
				<label for="noInmatriculada" style="color:red;font-size:12px">
					FALTAN LOS DATOS DE LOS BIENES/INMUEBLES (declarada inmatriculada)
				</label>
			</td>
		</s:elseif>
		<s:else>
			<td align="center" nowrap="nowrap" >
				<label for="noInmatriculada" style="color:red;font-size:12px">
					YA MATRICULADOS EN REGISTRO (declarada no inmatriculada)
				</label>
			</td>
		</s:else>
	</tr>		
</table>
