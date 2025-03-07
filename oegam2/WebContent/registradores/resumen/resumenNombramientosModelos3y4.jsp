<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<s:if test="tramiteRegistro.nombramientos != null && tramiteRegistro.nombramientos.size() > 0">
			<td align="right">
				<div class="divScroll">
					<display:table name="tramiteRegistro.nombramientos" excludedParams="*" class="tablacoin" uid="row" summary="Certificantes" style="width:98%"
						cellspacing="0" cellpadding="0">
							<display:column property="sociedadCargo.personaCargo.nif" title="Nif" sortable="false"
								style="width:10%;text-align:left;"/>
							<display:column property="sociedadCargo.personaCargo.nombre" title="Nombre" sortable="false" 
								style="width:15%;text-align:left;" />
							<display:column property="sociedadCargo.personaCargo.apellido1RazonSocial" title="Apellido1" sortable="false" style="width:25%;text-align:left;"/>
							<display:column property="sociedadCargo.personaCargo.apellido2" title="Apellido2" sortable="false" style="width:25%;text-align:left;"/>
							<display:column title="Nombrado" style="width:10%;text-align:left;">
								<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo@convertirTexto(#attr.row.sociedadCargo.codigoCargo)}" />
							</display:column>
							<display:column style="width:1%;">
							  	<img onclick="consultaEvolucionPersona('${row.sociedadCargo.personaCargo.nif}','<s:property value="tramiteRegistro.numColegiado"/>');" 
									style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png" title="Ver evolución">
							</display:column>
					</display:table>
				</div>
			</td>
		</s:if>
		<s:else>
			<td align="center" nowrap="nowrap" >
				<label for="sinCertificantes" style="color:red;font-size:12px">
					REQUERIDO. Al menos debe constar un acuerdo de tipo nombramiento
				</label>
			</td>
		</s:else>
	</tr>		
</table>
