<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">

<!--  //TODO MPC. Cambio IVTM. Este jsp es el bueno -->
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
<script src="js/trafico/materiales/gestionStockMateriales.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Materiales</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">
	<%@include file="../../../includes/erroresYMensajes.jspf" %>
	<div id="busqueda">

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
       			<td align="left" nowrap="nowrap">
						<label for="labelTipoJefatura">Jefatura:</label>
				</td>
				<td  align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaStockMateriales@getInstance().getJefaturasJPTEnum()" onblur="this.className='input2';" 
			    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Jefatura" 
			    		name="stockMateriales.jefatura" listKey="jefatura" listValue="descripcion" id="idTipoJefatura"/>
			    </td>
			    
			    <td align="left" nowrap="nowrap">
						<label for="labelTipo">Tipo:</label>
				</td>
				<td  align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaStockMateriales@getInstance().getTipoMateriales()" onblur="this.className='input2';" 
			    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo" 
			    		name="stockMateriales.tipoMaterial" listKey="valorEnum" listValue="nombreEnum" id="idTipoMaterial"/>
			    </td>
			</tr>
		</table>
		<input type="button" class="boton" name="bPDIConsultaStock" id="idBPDIConsultaStock" value="Consultar"  onkeypress="this.onClick" onclick="return buscarMateriales();"/>			
			<input type="button" class="boton" name="bPDILimpiarStock" id="idBPDILimpiarStock" onclick="javascript:limpiarFiltrosStockMateriales();" value="Limpiar"/>
	</div>
	<div id="resultado">
		<table class="subtitulo" cellSpacing="0">
			<tr>
				<td>Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
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
									id="idResultadosPorPagina"
									value="resultadosPorPagina"
									title="Resultados por página"
									onchange="javascript:cambiarElementosPorPagina();"
									/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>	
		</s:if>	
	</div>
	<div id="displayTagDivStockMateriales" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaStockMateriales" requestURI= "navegarStockMateriales.action"
				id="tablaGestionMateriales" summary="Listado de Materiales" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	
				<display:column property="tipoMaterial" title="Material" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" sortProperty="tipoMaterial"/>	
				
				<display:column property="jefatura" title="Jefatura Provincial" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="jefatura"/>	
					
				<display:column property="unidades" title="Unidades" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="unidades"/>	
					
				<display:column style="width:0.4%" >
					<input type="button" name="listaChecksMateriales" id="check<s:property value="#attr.tablaGestionMateriales.id"/>" 
							value='Recargar' onClick="cargarPopUp(<s:property value="#attr.tablaGestionMateriales.id"/>)" /> 
				</display:column>
				<display:column style="width:0.2%" >
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  					onclick="cargarPopUpEvolucion(<s:property value="#attr.tablaGestionMateriales.id"/>,'divEmergenteGestionPrm');" title="Ver evolución"/>
				</display:column>
				<display:column style="width:0.2%" >
					<img src="img/eliminar.png" alt="eliminar material" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  					onclick="eliminarMaterial(<s:property value="#attr.tablaGestionMateriales.id"/>,'divEmergenteGestionPrm');" title="Eliminar Material"/>
				</display:column>
			</display:table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="BtnAniadirMaterial" id="idBtnAniadirMaterial" value="Agregar Material"  onkeypress="this.onClick" onClick="cargarPopUpAgregarMaterial(<s:property value="#attr.tablaGestionMateriales.id"/>)"/>
		</div>
</s:form>
		<div id="divEmergenteRecargarStock" style="display: none; background: #f4f4f4; overflow: hidden;"></div>
		<div id="divEmergenteEvolucionStock" style="display: none; background: #f4f4f4;"></div>
		<div id="divEmergenteAgregarMaterial" style="display: none; background: #f4f4f4; overflow: hidden;"></div>