<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript">
function actualizarCargoPadre(nif, apellido1, apellido2, nombre, codigoCargo, correoElectronico, indefinido) {
	var tipoAcuerdoVar = document.getElementById("tipoAcuerdo").value;
	if (tipoAcuerdoVar == 'cese') {
		window.document.formData.nombreC.value = nombre;
		window.document.formData.apellido1C.value=apellido1;
		window.document.formData.apellido2C.value=apellido2;
		window.document.formData.nifC.value=nif;
		window.document.formData.cargoC.value=codigoCargo;
	} else {
		window.document.formData.nombreNom.value = nombre;
		window.document.formData.apellido1Nom.value=apellido1;
		window.document.formData.apellido2Nom.value=apellido2;
		window.document.formData.nifNom.value=nif;
		window.document.formData.cargoNom.value=codigoCargo;
		window.document.formData.emailNom.value=correoElectronico;
		window.document.formData.indefinido.value=indefinido;
	}
	$("#divEmergentePopUp").dialog("close");
}
</script>

<div id="buscarCargo">
	
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
	
	<div class="nav">
		<table cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
			<tr>
				<td class="tabular" style=""><span class="titulo">B&uacute;squeda de Cargos</span></td>
			</tr>
		</table>
	</div>
	
		<table border="0" class="tablaformbasicaTC">
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="nombre">Nombre:</label>
			</td>
			<td align="left" nowrap="nowrap"><s:textfield name="consultaSociedadCargoBean.nombre" id="nombre"
				onfocus="this.className='inputfocus';" size="20" maxlength="20" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"/>
			</td>
			<td align="right" nowrap="nowrap">
				<label for="apellido1">Apellido 1:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="consultaSociedadCargoBean.apellido1" id="apellido1" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
			</td>
			<td align="right" nowrap="nowrap">
				<label for="apellido2">Apellido 2:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="consultaSociedadCargoBean.apellido2" id="apellido2" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="numeroDocumento">Nif:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="consultaSociedadCargoBean.nifCargo" id="nif" onfocus="this.className='inputfocus';" size="10" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="correoElectronico">Correo Electrónico:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="4">
				<s:textfield  name="consultaSociedadCargoBean.correoElectronico" id="correoElectronico" onfocus="this.className='inputfocus';" size="40" maxlength="40" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';"/>
			</td>
		</tr>
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="cargo">Cargo:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3">
				<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getTipoCargos()" 
					id="cargoCer" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="consultaSociedadCargoBean.codigoCargo" listValue="descCargo" listKey="codigoCargo" headerKey="" headerValue="Seleccionar" />
			</td>
		</tr>
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>

	</table>
	<div id="botonBusqueda">
		<table width="100%">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="javascript:buscarCargo();"/>			
				</td>
				<td>
					<img id="loading3Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td> 
					<input type="button" class="boton" value="Limpiar campos" onclick="limpiarFormulario('buscarCargo');"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
		</table>
	</div>
	
	<div class="nav">
		<table cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
			<tr>
				<td class="tabular" style=""><span class="titulo">Lista de Cargos</span></td>
			</tr>
		</table>
	</div>
	
	<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%" class="nav">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
							</td>
							<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									id="idResultadosPorPagina" value="resultadosPorPagina"
									title="Resultados por página" onchange="cambiarElementosPorPaginaConsulta('navegarCargo.action','displayTagDiv', this.value);" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
	
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
	
	<div id="displayTagDiv" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" requestURI="navegarCargo.action" class="tablacoin" uid="row" summary="Listado de Cargos" cellspacing="0" cellpadding="0">

			<display:column title="Nif" sortable="false" style="width:3%;text-align:center;">
					<a href="javascript:actualizarCargoPadre('${row.id.nifCargo}','${row.personaCargo.apellido1RazonSocial}','${row.personaCargo.apellido2}','${row.personaCargo.nombre}','${row.id.codigoCargo}','${row.personaCargo.correoElectronico}','${row.indefinido}');">
						<s:property value="#attr.row.id.nifCargo" />
					</a>				 
			</display:column>
			
			<display:column property="personaCargo.nombre" title="Nombre" sortable="false" style="width:2%;text-align:left;" />

			<display:column property="personaCargo.apellido1RazonSocial" title="Apellido 1" sortable="false" style="width:3%;text-align:left;"/>

			<display:column property="personaCargo.apellido2" title="Apellido 2" sortable="false" style="width:3%;text-align:left;"/>
			
			<display:column property="personaCargo.correoElectronico" title="Email" sortable="false" style="width:2%;text-align:left;" />

			<display:column title="Cargo" sortable="false" style="width:3%;text-align:left;">
				<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo@convertirTexto(#attr.row.id.codigoCargo)}" />
			</display:column>

		</display:table>
	</div>
	
</div>