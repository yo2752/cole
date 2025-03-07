<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<s:if test="tramiteRegistro.certificantes != null && tramiteRegistro.certificantes.size() > 0">
			<td align="left">
				<div class="divScroll">
					<display:table name="tramiteRegistro.certificantes" excludedParams="*" class="tablacoin" uid="row" summary="Certificantes"
						cellspacing="0" cellpadding="0" style="width:98%">
							<display:column property="nifCargo" title="Nif" sortable="false" style="width:10%;text-align:left;"/>
							<display:column property="sociedadCargo.personaCargo.nombre" title="Nombre" sortable="false" style="width:10%;text-align:left;"/>
							<display:column property="sociedadCargo.personaCargo.apellido1RazonSocial" title="Apellido1" sortable="false" style="width:10%;text-align:left;"/>
							<display:column property="sociedadCargo.personaCargo.apellido2" title="Apellido2" sortable="false" style="width:10%;text-align:left;"/>
							<display:column title="Cargo" sortable="false" style="width:3%;text-align:left;">
								<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo@convertirTexto(#attr.row.codigoCargo)}" />
							</display:column>
							<display:column property="sociedadCargo.personaCargo.correoElectronico" title="Email" sortable="false" style="width:10%;text-align:left;"/>
							<display:column title="Firmado" sortable="false" style="width:5%;text-align:center;">
									<s:if test="%{#attr.row.idFirma != null}">
		      							Si
		      						</s:if>
		      						<s:elseif test="%{#attr.row.idFirma == null}">
		      							No
		      						</s:elseif>
							</display:column>
							<display:column style="width:1%;">
							  	<img onclick="consultaEvolucionPersona('${row.nifCargo}','<s:property value="tramiteRegistro.numColegiado"/>');" 
									style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png" title="Ver evolución">
							</display:column>
					</display:table>
				</div>
			</td>
		</s:if>
		<s:elseif test="tramiteRegistro.certificantes == null || tramiteRegistro.certificantes.size() == 0">
			<td align="center" nowrap="nowrap" >
				<label for="sinCertificantes" style="color:red;font-size:12px">
					REQUERIDO (Al menos debe constar un cargo de la sociedad como certificante del tr&aacute;mite)
				</label>
			</td>
		</s:elseif>
	</tr>		
</table>
