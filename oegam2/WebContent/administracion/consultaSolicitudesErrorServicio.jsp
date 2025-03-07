<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>

<script type="text/javascript">
	function cambiarElementosPorPagina() {
		document.forms[1].action="navegarSolicitudes.action?resultadosPorPagina=" + document.formData.idResultadosPorPagina.value;
		document.forms[1].submit();
		return true;
	}
</script>

<!--<div id="contenido" class="contentTabs" style="display: block;"> -->
<%@include file="../includes/erroresMasMensajes.jspf" %>
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de solicitudes Erroneas</span>
				</td>
			</tr>
		</table>
	</div>

	<s:form method="post" id="formData" name="formData" action="inicioSolicitudes.action">
		<div id="busqueda"><!--
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
				<tr>
				<td align="left" nowrap="nowrap">
					<label for="proceso">Proceso:</label>
				</td>

				<td align="left" >
					<s:textfield name="proceso" id="idProceso"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="35" maxlength="20" />
				</td>

				<td align="left" nowrap="nowrap">
					<label for="mensaje">Cola:</label>
				</td>

				<td align="left">
					<s:textfield name="cola" id="idCola"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="10" maxlength="10" />
				</td>
			</tr>

		</table>

	--><div id="botonBusqueda">

		<table width="100%">
			<tr>
				<td>
				<s:submit value="Actualizar" cssClass="boton"/>
				</td>
			</tr>
		</table>
		</div>
	</div>

	<div id="resultado" style="width:100%;background-color:transparent;">
		<table class="subtitulo" cellSpacing="0" style="width:100%;">
			<tr>
				<td style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>

		<s:if test="%{listaSolicitudes.calculaTamanoListaBD()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
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

<div class="divScroll">
	<display:table name="listaSolicitudes" excludedParams="*"
					requestURI="navegarSolicitudes.action"
					class="tablacoin"
					uid="listaSolicitudes"
					summary="Listado de Solicitudes"
					cellspacing="0" cellpadding="0">

		<display:column property="proceso" title="Proceso"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:15%;" />

		<display:column property="cola" title="Cola"
				sortable="true" headerClass="sortable"
				defaultorder="descending" 
				style="width:15%" />

		<display:column property="tipo_tramite" title="Tipo Trámite"
				sortable="true" headerClass="sortable" 
				defaultorder="descending"
				style="width:15%" />

		<display:column property="id_tramite" title="Id Trámite"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:15%" />

		<display:column property="id_usuario" title="Id Usuario"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:15%" />

		<display:column property="respuesta" title="Respuesta"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:25%" />

		<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.idEnvios);' onKeyPress='this.onClick'/>"
				style="width:1%; padding-right: 5px; " >
			<table align="center">
				<tr>
					<td style="border: 0px;">
						<input type="checkbox" name="idEnvios" id="check<s:property value="#attr.listaSolicitudes.id_envio"/>" 
						value='<s:property value="#attr.listaSolicitudes.id_envio"/>' />
					</td>
				</tr>
			</table>

		</display:column>
	</display:table>
	<s:if test="%{listaSolicitudes.getFullListSize()>0}">
		<table align="center">
			<tr>
				<td>
				<input type="button"
						class="botonGrande"
						name="bReactivarSolicitudes"
						id="idReactivarSolicitudes"
						value="Reactivar Solicitudes"
						onClick="return reactivarSolicitudes();"
						onKeyPress="this.onClick" />
				</td>

				<td>
					<input type="button"
							class="botonGrande"
							name="bFinalizarConError"
							id="idFinalizarConError"
							value="Finalizar con Error"
							onClick="return FinalizarConError();"
							onKeyPress="this.onClick"
					/>
				</td>
			</tr>
		</table>
	</s:if>
</div>
<!--<s:hidden key="rowid_Solicitud" name="rowid_Solicitud" ></s:hidden>-->
</s:form>