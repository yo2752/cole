<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<s:hidden name="consultaSociedadCargoBean.cifSociedad"/>
	
<div id="buscarAsistente">
	<%@include file="../../includes/erroresMasMensajes.jspf"%>

	<div class="nav">
		<table cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
			<tr>
				<td class="tabular" style=""><span class="titulo">B&uacute;squeda de asistentes</span></td>
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
			<td align="right" nowrap="nowrap">
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
					name="consultaSociedadCargoBean.codigoCargo" listValue="descCargo" listKey="codigoCargo" title="Cargo del asistente" headerKey="" headerValue="Seleccionar" />
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
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="javascript:buscarAsistente();"/>			
				</td>
				<td>
					<img id="loading3Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td> 
					<input type="button" class="boton" value="Limpiar campos" onclick="limpiarFormulario('buscarAsistente');"/>
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
				<td class="tabular" style=""><span class="titulo">Lista de asistentes</span></td>
			</tr>
		</table>
	</div>
	
	<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%" class="tablaformbasicaTC">
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
									title="Resultados por página" onchange="cambiarElementosPorPaginaConsulta('navegarAsistente.action','displayTagDiv', this.value);" />
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
		<display:table name="lista" requestURI="navegarAsistente.action" class="tablacoin" uid="row" summary="Listado de Cargos" cellspacing="0" cellpadding="0">

			<display:column property="personaCargo.nombre" title="Nombre" sortable="false" style="width:2%;text-align:left;" />

			<display:column property="personaCargo.apellido1RazonSocial" title="Apellido 1" sortable="false" style="width:3%;text-align:left;"/>

			<display:column property="personaCargo.apellido2" title="Apellido 2" sortable="false" style="width:3%;text-align:left;"/>

			<display:column property="id.nifCargo" title="Nif" sortable="false" style="width:3%;text-align:left;"/>

			<display:column property="personaCargo.correoElectronico" title="Email" sortable="false" style="width:2%;text-align:left;" />

			<display:column title="Cargo" sortable="false" style="width:3%;text-align:left;">
					<s:property value="%{@org.gestoresmadrid.core.registradores.model.enumerados.TipoCargo@convertirTexto(#attr.row.id.codigoCargo)}" />
			</display:column>


			<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.asistentesMarcados);' 
					onKeyPress='this.onClick'/>" style="width:1%;text-align:center;">
		    	<input type="checkbox" name="asistentesMarcados" value="${row.id.nifCargo}-${row.id.codigoCargo}" class="checkbox" 
		    		<s:if test="%{#attr.row.asist==1}">
		      			checked=checked
		      		</s:if>
		      	/>
			</display:column>

		</display:table>
	</div>
	<div id="botonBusqueda">
		<table width="100%">
			<tr>
				<td> 
					<input type="button" class="boton" value="Seleccionar" id="botonActualizarAsistente" onclick="seleccionarAsistente();"/>
					&nbsp;
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">  
				</td>
			</tr>
		</table>
	</div>

</div>