<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<s:if test="tramiteRegistro.asistentes != null && tramiteRegistro.asistentes.size() > 0">
			<td align="right">
				<div class="divScroll">
					<center>
						<display:table name="tramiteRegistro.asistentes" excludedParams="*" class="tablacoin" uid="row" summary="Asistentes a la reunión" style="width:98%"
							cellspacing="0" cellpadding="0">
								<display:column property="nifCargo" title="Nif" sortable="false" style="width:2%;text-align:left;" />
								<display:column property="sociedadCargo.personaCargo.nombre" title="Nombre" sortable="false" style="width:2%;text-align:left;" />
								<display:column property="sociedadCargo.personaCargo.apellido1RazonSocial" title="Apellido1" sortable="false" style="width:2%;text-align:left;" />
								<display:column property="sociedadCargo.personaCargo.apellido2" title="Nif" sortable="Apellido2" style="width:2%;text-align:left;" />
								<display:column title="Cargo" sortable="false" style="width:3%;text-align:left;">
									<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo@convertirTexto(#attr.row.codigoCargo)}" />
								</display:column>
								<display:column  style="width:1%">					  		
									<img onclick="consultaEvolucionPersona('${row.nifCargo}','<s:property value="tramiteRegistro.numColegiado"/>');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png" title="Ver evolución">
								</display:column>
						</display:table>
					</center>
				</div>
			</td>
		</s:if>
		<s:else>
			<td align="center" nowrap="nowrap" >
				<label for="sinMedios" style="color:red;font-size:12px">
					REQUERIDO (Miembros del Consejo de Administraci&oacute;n asistentes a la reuni&oacute;n)
				</label>
			</td>
		</s:else>
	</tr>		
</table>
