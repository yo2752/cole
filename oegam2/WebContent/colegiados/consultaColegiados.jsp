<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/creditos.js" type="text/javascript"></script>

<script type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Colegiados</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../../includes/erroresYMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">
	<div id="busqueda">		
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
        		<td align="left" nowrap="nowrap">
        			<label for="nif">NIF:</label>
        		</td>
        		<td align="left" >
        			<s:textfield name="consultaColegiadoBean.nif" id="nif" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="35" maxlength="20" />
        		</td>
			</tr>
			<tr>
       			<td align="left" nowrap="nowrap">
        			<label for="apellidos_Nombre">Apellidos Nombre:</label>
        		</td>
        		<td align="left">
        			<s:textfield name="consultaColegiadoBean.apellidosNombre" id="apellidosNombre" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="45" maxlength="45" />
        		</td>
        		<td align="left" nowrap="nowrap">
        			<label for="num_Colegiado">Colegiado:</label>
        		</td>
        		<td align="left">
        			<s:textfield name="consultaColegiadoBean.numColegiado" id="numColegiado" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4" />
        		</td>
			</tr>
		</table>
		
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaColegiados();"/>			
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" onclick="limpiarFormConsultaColegiadosNuevo()" value="Limpiar"  />			
				</td>
			</tr>
		</table>
		
		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>

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
										id="idResultadosPorPagina" value="resultadosPorPagina"
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaColegiado.action', 'displayTagDiv', this.value);" /> 
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
			
			<display:table name="lista" excludedParams="*" requestURI="navegarConsultaColegiado.action"
				class="tablacoin" summary="Listado de Colegiados" cellspacing="0" cellpadding="0" uid="listaContatosTable" sort="external">
				
					<display:column property="colegiadoDto.numColegiado" sortProperty="colegiado.numColegiado" title="Num Col" sortable="true" headerClass="sortable" defaultorder="descending" style="width:1%"/>	
		
					<display:column title="Contrato-Provincia" sortable="true" headerClass="sortable" sortProperty="idContrato" defaultorder="descending" style="width:4%"> 
						<a href="buscarConsultaCreditoCargar.action?idContratoProvincia=<s:property value='#attr.listaContatosTable.contratoProvincia'/>"><s:property value="#attr.listaContatosTable.contratoProvincia"/></a>
					</display:column>	

					<display:column property="colegiadoDto.usuario.nif" sortProperty="colegiado.usuario.nif" title="NIF" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
		
					<display:column property="colegiadoDto.usuario.apellidosNombre" sortProperty="colegiado.usuario.apellidosNombre" title="Apellidos Nombre" sortable="true" headerClass="sortable" defaultorder="descending" style="width:17%" />
		
			 		<display:column property="via" title="Vía" sortable="true" headerClass="sortable" defaultorder="descending" style="width:20%"  />		

					<display:column property="colegiadoDto.usuario.correoElectronico" sortProperty="colegiado.usuario.correoElectronico" title="Correo Electrónico" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%"  />
			</display:table>
		</div>
	</div>
</s:form>

