<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/legalizacionBotones.js" type="text/javascript"></script>
<script src="js/grupoUsuarios/grupoUsuarios.js" type="text/javascript"></script>

<div class="nav">
<table cellSpacing="0" cellPadding="5" width="100%">
	<tr>
		<td class="tabular">
		<span class="titulo">Contratos: Consulta de grupos de datos sensibles</span>
		</td>
	</tr>
</table>
</div>

<s:form id="formData" name="formData" action="guardarModificarGrupoGrupoUsuarios.action">
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
       		
		</table>
	
		<div id="botonBusqueda">
		
			<table width="100%">
				<tr>
					<td>
						<!--<s:submit value="Buscar" cssClass="boton"/>-->
					</td>
				</tr>
			</table>
			
		</div>
		
	</div>

<div id="resultado">
	<table class="subtitulo" cellSpacing="0">
		<tr>
			<td>Resultado de la b&uacute;squeda</td>
		</tr>
	</table>
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">

			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina"
										title="Resultados por página"
										onchange="return cambiarElementosPorPaginaConsultaGrupoUsuarios();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
</div>

	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
	<div id="displayTagDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*"
				requestURI="navegar${action}"
				class="tablacoin"   
				summary="Listado de Contratos"
				cellspacing="0" cellpadding="0" uid="listaContratosTable" >
						
				<display:column property="idGrupo" title="Id grupo"
					sortable="true" headerClass="sortable"			
					defaultorder="descending"
					style="width:4%" />
						
				<display:column property="descGrupo" title="desc grupo"
					sortable="true" headerClass="sortable"			
					defaultorder="descending"
					style="width:4%" />
				
				<display:column property="correoElectronico" title="correo electronico"
					headerClass="sortable"			
					defaultorder="descending"
					style="width:4%" />
					
				<display:column property="tipo" title="tipo"
					sortable="true" headerClass="sortable"			
					defaultorder="descending"
					style="width:4%" />
				<display:column title="Eliminar" style="width:4%" >
					<img class="PopcalTrigger" onclick="eliminarGrupo('<s:property value="#attr.listaContratosTable.idGrupo"/>');"
						align="centre" src="img/error2.gif" width="15" height="16"
						border="0" alt="Calendario" />
				</display:column>	
				<display:column title="Modificar" style="width:4%" >
					<img class="PopcalTrigger" onclick="modificarGrupo('<s:property value="#attr.listaContratosTable.idGrupo"/>'
																		,'<s:property value="#attr.listaContratosTable.correoElectronico"/>'
																		,'<s:property value="#attr.listaContratosTable.descGrupo"/>'
																		,'<s:property value="#attr.listaContratosTable.tipo"/>');"
						align="centre" src="img/editar.gif" width="15" height="16"
						border="0" alt="Calendario" />
				</display:column>
			
				<input type="hidden" name="resultadosPorPagina"/>    
		</display:table>
	</div>
	<div id="boton aniadir">
		
			<table width="100%">
				<tr>
					<td>
						<input type="button" class="boton" name="bAniadirGrupo"
						id="idAniadirGrupo" value="Añadir grupo"
						onkeypress="this.onClick" onclick="mostrarDiv();" />
					</td>
				</tr>
			</table>
			
		</div>
		
	<div id="altaGrupo" style="display: none;">
			<table class="tablaformbasica" cellspacing="3" cellpadding="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">
						<table align="center">
							<tr>
								<td align="left"><label for="idDescGrupo">
										Id de Grupo</label></td>
								<td align="left"><label for="idDescGrupo">
										Descripción grupo</label></td>
								<td align="left"><label for="idDescGrupo">
										Correo electrónico</label></td>
								<td align="left"><label for="idDescGrupo">
										tipo</label></td>				
							</tr>
							<tr>
							<td><s:textfield name="grupoDto.idGrupo"
										id="idGrupo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" maxlength="3"/>
								</td>
								<td><s:textfield name="grupoDto.descripcionGrupo"
										id="idDescGrupo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"/>
								</td>
								<td><s:textfield name="grupoDto.correoElectronico"
										id="idCorreoElectronico"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"/>
								</td>
								<td><s:textfield name="grupoDto.tipo"
										id="idTipo"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td nowrap="nowrap" width="30%" align="center"
						style="vertical-align: middle">
						<!--<s:submit value="Añadir grupo" cssClass="botonMasGrande"/>-->
						<input
						type="button" value="Añadir grupo" class="boton"
						onclick="guardarModificarGrupo()"/>
						 &nbsp; <input
						type="button" value="Cancelar" class="boton"
						onclick="ocultarDiv()" /></td>
				</tr>
			</table>
		</div>		
</s:form>