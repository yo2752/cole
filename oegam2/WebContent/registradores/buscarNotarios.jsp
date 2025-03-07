<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>


<script type="text/javascript">

	function actualizarNotarioPadre(codigoNotario, codigoNotaria) {
		window.document.formData.codigoNotario.value = codigoNotario;
		window.document.formData.teCodNotaria.value=codigoNotaria;
		$("#divEmergentePopUp").dialog("close");
	}

</script>
	

		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">B&uacute;squeda de notarios</span>
					</td>
				</tr>
			</table>
		</div>

		<s:hidden name="propiedad" value="SI"/>
	
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNombre">Nombre:</label>
					</td>
					<td  align="left">
						<s:textfield name="notario.nombre" id="idNotarioNombre" size="25" maxlength="100" onblur="this.className='input';" 
								onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelApellido1">Apellidos:</label>
					</td>
					<td  align="left">
						<s:textfield name="notario.apellidos" id="idNotarioApellidos" size="25" maxlength="100" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelCodNotario">C&oacute;digo Notario:</label>
				</td>
				<td  align="left">
					<s:textfield name="notario.codigoNotario" id="idCodNotario" size="25" maxlength="100" onblur="this.className='input';" 
						onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelCodNotaria">C&oacute;digo Notar&iacute;a:</label>
				</td>
				<td  align="left">
					<s:textfield name="notario.codigoNotaria" id="idCodNotaria" size="25" maxlength="100" onblur="this.className='input';" 
						onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
		</table>

	
	<div class="acciones center">
		<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return abrirVentanaNotario();"/>			
		<input type="button" class="boton" name="bLimpiar" onclick="javascript:limpiarNotario();" value="Limpiar"/>		
	</div>
	
	<br/>
	
	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	</div>
	
	<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
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
									id="idResultadosPorPagina" name= "resultadosPorPagina"
									value="resultadosPorPagina" title="Resultados por página"
									onchange="cambiarElementosPorPaginaConsulta('navegarNotarioReg.action', 'displayTagDiv', this.value);">
								</s:select>
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
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin" uid="row" requestURI= "navegarNotarioReg.action"
			summary="Listado de Notarios" excludedParams="*" cellspacing="0" cellpadding="0">	
				<display:column property="codigoNotario" title="Código Notario" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%"/>
				<display:column property="codigoNotaria" title="Codigo Notaría" sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:3%"/>
				<display:column property="nombre" title="Nombre" sortable="true" headerClass="sortable " 
					defaultorder="descending" style="width:4%"/>
				<display:column property="apellidos" title="Apellidos" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
				<display:column title="Seleccionar" style="width:1%;">
				  	<a onclick="" href="javascript:actualizarNotarioPadre('${row.codigoNotario}', '${row.codigoNotaria}')">Seleccionar</a>
				 </display:column>
		</display:table>
	</div>
	</div>

