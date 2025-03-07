<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/keyStores.js" type="text/javascript"></script>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

	<div id="busqueda">

		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align:left;">Administración
					de almacenes de claves para comunicaciones seguras (https)</td>
			</tr>
		</table>

		<table class="tablaformbasica" border="0">

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>

			<tr>
				<td nowrap="nowrap" align="left" width="5%">
					<label for="keyStoreName">Almacén de:</label>
				</td>
				<td align="left" width="80px"><s:select label="Tipo:"
						name="keyStoreName" id="keyStoreName"
						list="#{'':'seleccionar', 'CLAVES_PUBLICAS':'Claves públicas', 'CLAVES_PRIVADAS':'Claves privadas'}"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" required="true" /></td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">
					<label for="password">Password:</label>
				</td>

				<td align="left"><s:password name="keyStorePassword"
						id="keyStorePassword" size="15" maxlength="15"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" /></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>

		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bAbrirKeyStore"
					id="bAbrirKeyStore" value="Abrir almacén"
					onClick="return abrirKeyStore('bAbrirKeyStore');"
					onKeyPress="this.onClick" /> &nbsp; <input type="button"
					class="boton" name="bCerrarKeyStore" id="bCerrarKeyStore"
					value="Cerrar almacén"
					onClick="return cerrarKeyStore('bCerrarKeyStore');"
					onKeyPress="this.onClick" />&nbsp; <input type="button"
					class="boton" name="bLimpiar" id="idBotonLimpiarKeystore"
					value="Limpiar" onkeypress="this.onClick"
					onclick="return limpiarFormularioAbrirKeyStore();" />&nbsp;</td>
			</tr>
		</table>
	</div>

	<div id="bloqueLoadingAbrirKeyStore"
		style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
		<%@include file="../../includes/bloqueLoading.jspf"%>
	</div>

	<s:if test="%{certificatesClavesPublicas.length>0}">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Contenido del
					almacén de claves públicas</td>
			</tr>
		</table>

		<div class="divScroll">
			<display:table name="certificatesClavesPublicas" excludedParams="*"
				class="tablacoin" uid="tablaClavesPublicas"
				summary="Contenido del almacén de claves públicas"
				cellspacing="0" cellpadding="0" decorator="administracion.decorators.DecoradorTablaKeyStores">

				<display:column property="alias" title="ALIAS DEL CERTIFICADO"
					style="width:40%;text-align:left;" />

				<display:column property="validoDesde" title="VÁLIDO DESDE"
					maxLength="20" style="width:15%" />

				<display:column property="validoHasta" title="VÁLIDO HASTA"
					style="width:17%;" />

				<display:column property="diasValidezRestantes" title="DÍAS VALIDEZ"
					style="width:15%;" />

				<display:column title="" style="width:3%;">
					<input type="checkbox" name="listaChecksCertificados"
						id="<s:property value="#attr.tablaClavesPublicas.alias"/>"
						value="<s:property value='#attr.tablaClavesPublicas.alias'/>" />
				</display:column>

			</display:table>
		</div>
	</s:if>

	<s:if test="%{certificatesClavesPrivadas.length!=null}">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Contenido del
					almacén de claves privadas</td>
			</tr>
		</table>

		<div class="divScroll">
			<display:table name="certificatesClavesPrivadas" excludedParams="*"
				class="tablacoin" uid="tablaClavesPrivadas"
				summary="Contenido del almacén de claves privadas"
				cellspacing="0" cellpadding="0" decorator="administracion.decorators.DecoradorTablaKeyStores">

				<display:column property="alias" title="ALIAS DEL CERTIFICADO"
					style="width:40%;text-align:left;" />

				<display:column property="validoDesde" title="VÁLIDO DESDE"
					maxLength="20" style="width:15%" />

				<display:column property="validoHasta" title="VÁLIDO HASTA"
					style="width:17%;" />

				<display:column property="diasValidezRestantes" title="DÍAS VALIDEZ"
					style="width:15%;" />

				<display:column title="" style="width:3%;">
					<input type="checkbox" name="listaChecksCertificados"
						id="<s:property value="#attr.tablaClavesPrivadas.alias"/>"
						value="<s:property value='#attr.tablaClavesPrivadas.alias'/>" />
				</display:column>

			</display:table>
		</div>
	</s:if>

	<s:if
		test="%{(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin())&&
			((certificatesClavesPublicas!=null&&certificatesClavesPublicas.length>=0) || (certificatesClavesPrivadas!=null&&certificatesClavesPrivadas.length>=0))}">
		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bImportarCertificado"
					id="bImportarCertificado" value="Importar" onKeyPress="this.onClick"
					onclick="return mostrarBloqueImportacion();" />&nbsp;
					<!-- onclick="javascript:abrirVentanaImportacionCertificado('bImportarCertificado');" />&nbsp; --> 
					<input type="button" class="boton" name="bExportarCertificado"
					id="bExportarCertificado" value="Exportar" onKeyPress="this.onClick"
					onclick="javascript:exportarCertificado('bExportarCertificado');" />&nbsp;
					<input type="button" class="boton" name="bEliminarCertificado"
					id="bEliminarCertificado" value="Eliminar" onKeyPress="this.onClick"
					onclick="return accionCertificado('eliminar', 'bEliminarCertificado')" />&nbsp;
					<input type="button" class="boton" name="bRenombrarCertificado"
					id="bRenombrarCertificado" value="Renombrar" onKeyPress="this.onClick"
					onclick="return renombrarCertificado('bRenombrarCertificado')" />&nbsp;
					<input type="button" class="boton" name="bDetalleCertificado"
					id="bDetalleCertificado" value="Mostrar detalles"
					onclick="javascript:abrirVentanaDetalleCertificado('bDetalleCertificado');"
					onKeyPress="this.onClick" />&nbsp;
					<input type="button" class="boton" name="bComprobarUrl"
					id="bComprobarUrl" value="Comprobar URL"
					onclick="comprobar('bComprobarUrl')"
					onKeyPress="this.onClick" /></td>
			</tr>
		</table>
		<div id="bloqueLoadingAccionesCertificados"
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
	</s:if>

	<div id="bloqueImportacion" style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Seleccione el certificado que desea importar</td>
			</tr>
		</table>
		<table class="tablaformbasica" cellspacing="3" cellpadding="0">
			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" colspan="5">
					<s:file size="50" name="fileUpload" value="fileUpload"></s:file>
				</td>
			</tr>
			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
			<tr>
				<td nowrap="nowrap" width="8%">
					<input type="button" id="idBotonExportarPopUp" value="Importar" onclick="importarKeyStore()"/>
				</td>
				<td align="right">
					<s:checkbox name="sobrescribir" id="sobrescribir"/>
				</td>
				<td style="vertical-align: middle;" align="left" width="180px">
					<label for="mate">Sobrescribir si fuera necesario</label>
				</td>
				<s:if test="#attr.almacenAbierto=='CLAVES_PRIVADAS'">
					<td nowrap="nowrap" style="vertical-align: middle;" width="30px">
						<label for="passwordClavePrivada">Password clave privada:</label>
					</td>

					<td align="left"><s:password name="passwordClavePrivada"
							id="passwordClavePrivada" size="15" maxlength="15"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" /></td>
				</s:if>
			</tr>
			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
		</table>
	</div>

	<s:hidden name="aliasCertificado" id="aliasCertificado"/>
	<s:hidden name="nuevoAlias" id="nuevoAlias"/>
	<s:hidden id="almacenAbierto" name="almacenAbierto" />
	<s:hidden id="comprobarUrl" name="comprobarUrl"/>

</s:form>