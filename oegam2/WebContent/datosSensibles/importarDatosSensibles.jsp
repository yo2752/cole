<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/datosSensibles.js" type="text/javascript"></script>
<script src="js/trafico/importacion.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
	<s:hidden id="resultadosPorPagina" name="resultadosPorPagina"/>
	<s:hidden id="resultadosPorPaginaErroneos" name="resultadosPorPaginaErroneos"/>
	<s:hidden id="pageError" name="pageError"/>
	<s:hidden id="dirError" name="dirError"/>
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">
						Importación de Datos Sensibles
					</span>
				</td>
			</tr>
		</table>
	</div>
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td>
				Seleccione el fichero a importar
			</td>
		</tr>
	</table>
	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap" style="width:15%;">
					<label for="labelTipoDatosSensible">Tipo Datos Sensible: </label>
				</td>
				<td align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboDatosSensibles()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
							headerValue="Seleccione Tipo Dato"
							name="datosSensiblesBean.tipoAgrupacion"
							listKey="valorEnum" listValue="nombreEnum"
							id="idTipoAgrupacion"
							disabled="true"/>
							<s:hidden name="datosSensiblesBean.tipoAgrupacion" />
				</td>
				<td align="left" style="width:15%;">
					<label for="labelTiempo" >Tiempo de caducidad del dato:</label>
					<img id="info_caducidad_datos_sensibles" src="img/botonDameInfo.gif" alt="Info - Caducidad datos sensibles"
						onmouseover="dameInfoSinAjax('mostrar', this.id, 'caducidadDatosSensibles')" onmouseout="dameInfoSinAjax('ocultar', this.id, '')"
						class="botonesInfo" />
				</td>
				<td align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboBajaDatosSensibles()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="datosSensiblesBean.tiempoDatosSensibles"
						listKey="valorEnum" listValue="nombreEnum"
						id="idTiempo"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label >Tipo Fichero: </label>
				</td>
				<td align="left">
					<s:radio cssStyle="display: inline;"
							name="datosSensiblesBean.tipoFichero"
							list="@org.gestoresmadrid.core.model.enumerados.TipoFicheroImportacionBastidores@values()"
							listKey="codigo" listValue="descripcion" id="idTipoFichero"/>
				</td>
				<td align="left">
						<s:file label="Fichero" id="fichero" size="80" value="fichero" name="fichero" onblur="this.className='input2';" onfocus="this.className='inputfocus';"></s:file>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label >Grupo: </label>
				</td>
				<td align="left">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getComboGrupoUsuarios()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							name="datosSensiblesBean.grupo"
							listKey="codigo" listValue="descripcion"
							id="idTiempo"/>
					</s:if>
					<s:else>
						<s:textfield name="descGrupo" id="idDescGrupo" onblur="this.className='input2';" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDatosSensibles@getInstance().getDescripcionGrupo(datosSensiblesBean.grupo)}"
									onfocus="this.className='inputfocus';" readonly="true" size="25" maxlength="25"/>
						<s:hidden id="datosSensiblesBean.grupo" name="datosSensiblesBean.grupo"/>
					</s:else>
				</td>
			</tr>
		</table>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Importar" onkeypress="this.onClick" onclick="importarBastidor()" />
		<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
	</div>
	<br/>
	<div class="contenido" style="margin-left: 1em;">
		<s:if test="hasActionErrors() || hasActionMessages()">
			<table class="subtitulo" cellSpacing="0" cellspacing="0">
				<tr>
					<td>Resultado:</td>
				</tr>
			</table>
		</s:if>
		<%@include file="../../includes/erroresMasMensajes.jspf" %>
	</div>
	<br/>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
		<s:if test="%{listaResultadoImportarErroneos != null}">
			<table class="subtitulo" cellSpacing="0" style="width:100%;" >
				<tr>
					<td style="width:100%;text-align:center;">Resultado de la Importación Erroneos</td>
				</tr>
			</table>
			<s:if test="%{listaResultadoImportarErroneos.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
											id="idResultadosPorPaginaErroneos"
											name= "resultadosPorPaginaErroneos"
											value="resultadosPorPaginaErroneos"
											title="Resultados por página"
											onchange="return cambiarElementosPorPaginaImportardosDatosSensibles();">
										</s:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
			<div id="displayTagDiv" style="width:104%">
				<display:table name="listaResultadoImportarErroneos" 
					requestURI="navegarImportacion${action}" class="tablacoin" excludedParams="*"
					uid="tablaImportarBastidoresErroneos" summary="Listado de Bastidores Importados Erroneos"
					cellspacing="0" cellpadding="0" decorator="${decorator}" >
					<display:column property="bastidor" title="Bastidor" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%;" paramId="bastidor" />
					<display:column property="mensaje" title="Mensaje" sortable="true" headerClass="sortable" defaultorder="descending" />
					<display:column property="esRecuperable" title="error" sortable="true" headerClass="sortable" media ="none" />
					<display:column property="grupo" title="Grupo" sortable="true" headerClass="sortable" media ="none" />
					<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
						<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodas(this)' onKeyPress='this.onClick'/>"
							style="width:4%">
							<s:if test="#attr.tablaImportarBastidoresErroneos.esRecuperable">
								<input type="checkbox" name="listaChecksErrores" id="check<s:property value="#attr.tablaImportarBastidoresErroneos.bastidor"/>" 
										value='<s:property value="#attr.tablaImportarBastidoresErroneos.bastidor"/>_<s:property value="#attr.tablaImportarBastidoresErroneos.grupo"/>' />
							</s:if>
						</display:column>
					</s:if>
				</display:table>
			</div>
			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
				<div class="acciones center">	
					<input type="button" class="boton" name="bEnviarPeticion" id="bEnviarPeticion" value="Enviar Peticion" onkeypress="this.onClick" onclick="enviarPeticion()"/>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</div>
			</s:if>
		</s:if>
	</s:if>
</s:form>

<script type="text/javascript">
	function marcarTodas(objCheck) {
	var marcar = objCheck.checked;
		if (document.formData.listaChecksErrores) {
			if (!document.formData.listaChecksErrores.length) { //Controlamos el caso en que solo hay un elemento...
				document.formData.listaChecksErrores.checked = marcar;
			}
			for (var i = 0; i < document.formData.listaChecksErrores.length; i++) {
				document.formData.listaChecksErrores[i].checked = marcar;
			}
		}
		if (document.formData.listaChecksErrores) {
			if (!document.formData.listaChecksErrores.length) { //Controlamos el caso en que solo hay un elemento...
				document.formData.listaChecksErrores.checked = marcar;
			}
			for (var j = 0; j < document.formData.listaChecksErrores.length; j++) {
				document.formData.listaChecksErrores[j].checked = marcar;
			}
		}
	}

	// DESHABILITA EL BOTÓN DE ACEPTAR, Y HACE VISIBLE LA IMAGEN DE LOADING
	function loadingImport() {
		document.getElementById("bAceptar").disabled = "true";
		document.getElementById("bAceptar").style.color = "#6E6E6E";
		document.getElementById("bAceptar").value = "Importando";
		document.getElementById("loadingImage").style.display = "block";
	}

	function cambiarElementosPorPaginaImportardosDatosSensibles() {
		var resultadoPagina = document.formData.idResultadosPorPagina;
		var resultadoPaginaErroneos = document.formData.idResultadosPorPaginaErroneos;
		var hreff = 'navegarImportacionDatosSensibles.action';
		var resultado = false;
		if(resultadoPagina != null) {
			hreff += '?resultadosPorPagina=' + resultadoPagina.value;
			resultado = true;
		}

		if (resultadoPaginaErroneos != null) {
			if (resultado) {
				hreff += '&resultadosPorPaginaErroneos='+ resultadoPaginaErroneos.value;
			} else {
				hreff += '?resultadosPorPaginaErroneos='+ resultadoPaginaErroneos.value;
			}
		}
		document.location.href = hreff;
	}
</script>