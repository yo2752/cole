<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/properties.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">

	<div id="busqueda">

		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align:left;">Propiedades OEGAM</td>
			</tr>
		</table>

		<table class="tablaformbasica" border="0">

			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;" width="30px">
					<label for="nombre">Nombre:</label>
				</td>

				<td align="left" width="50px" nowrap="nowrap">
					<s:textfield name="consultaPropertiesBean.nombre"
						id="nombre" size="120" maxlength="100"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;" width="30px">
					<label for="valor">Valor:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield name="consultaPropertiesBean.valor"
						id="valor" size="120" maxlength="600"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"/></td>
			</tr>
			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			</tr>
		</table>
	</div>

	<div class="acciones center">
		<input type="button" class="boton" name="bBuscarPropertie" id="idBuscarPropertie" value="Buscar" onkeypress="this.onClick" onclick="return buscarProperties();"/>
		<input type="button" class="boton" name="bLimpiarPropertie" id="idBLimpiarPropertie" value="Limpiar" onkeypress="this.onClick" onclick="return limpiarDiv('busqueda');"/>
		<input type="button" class="boton" name="bRefrescarPropertie" id="idRefrescarPropertie" value="Refrescar" onkeypress="this.onClick" onclick="return refrescarProperties();"/>
	</div>

	<div id="bloqueLoadingProperties"
		style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
		<%@include file="../../includes/bloqueLoading.jspf"%>
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
									onchange="cambiarElementosPorPaginaConsulta('navegarProperties.action', 'displayTagDiv', this.value);">
								</s:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
	<p style="font-weight:bold;color:black;padding-left: 0.5in;padding-right: 0.5in;">Recuerde que los valores aquí mostrados son los correspondientes a la BBDD. La aplicación podría utilizar otros, si así se hubiera indicado en el properties correspondiente.</p>

	<div id="displayTagDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" class="tablacoin" uid="tablaProperties"
			summary="Lista de propiedades" cellspacing="0" cellpadding="0" style="width:98%"
			requestURI="navegar${action}">

			<display:column property="id" media="none" /> 

			<display:column property="nombre" title="Nombre" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:35%;text-align:left" sortProperty="nombre"/>	

			<display:column title="Valor" style="width:50%" >
				<input type="text" id="valor_<s:property value="#attr.tablaProperties_rowNum"/>"
					value="<s:property value="#attr.tablaProperties.valor"/>"
					size="60" maxlength="600" />
			</display:column>

			<display:column style="width:5%;text-align:center;vertical-align: middle;">
				<img src="img/icono-guardar.jpg"
					alt="Guardar BBDD"
					style="height: 20px; width: 20px; cursor: pointer;"
					onclick="guardarBBDD(<s:property value="#attr.tablaProperties.id"/> , <s:property value="#attr.tablaProperties_rowNum"/>, '<s:property value="#attr.tablaProperties.nombre"/>');"
					title="Guardar BBDD" />
			</display:column>

			<display:column
				defaultorder="descending" style="width:5%;text-align:center">
				<img src="img/botonDameInfo.gif"
					alt="<s:property value="#attr.tablaProperties.comentario"/>"
					style="height: 20px; width: 20px; cursor: pointer;"
					title="<s:property value="#attr.tablaProperties.comentario"/>" />
			</display:column>

		</display:table>
	</div>

</s:form>