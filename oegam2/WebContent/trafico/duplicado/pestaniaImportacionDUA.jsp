<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="org.gestoresmadrid.utilidades.components.GestorPropiedades" %>

<div class="contenido">
	<s:hidden name="idFicheroEliminar" />
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Importación DUA</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Documentación aportada</span></td>
		</tr>
	</table>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esConsultableOGuardableDuplicado(tramiteTraficoDuplicado)}">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left">
					<label>Fichero </label>
					<span class="naranja">(Archivos pdf y tamaño máximo de <s:property value="%{@org.gestoresmadrid.utilidades.components.GestorPropiedades@dameValorPropiedad('tamanio.maximo.adjuntos.DUA.duplicado')}"/> KB)</span>
				</td>
				<td align="right"><s:file id="fichero" size="50" name="fichero" accept="application/pdf" /></td>
				<td align="right" nowrap="nowrap" width="30%"><input type="button" class="boton" id="botonSubirFichero" value="Subir fichero" onclick="enviarFichero()" /></td>
			</tr>
		</table>
	</s:if>

	<table cellPadding="1" cellSpacing="3" cellpadding="1" class="tablaformbasica" width="100%">
		<tr>
			<td align="center">
				<display:table name="tramiteTraficoDuplicado.ficherosSubidos" excludedParams="*" style="width:95%" class="tablacoin" id="row"
					summary="Listado de Ficheros Subidos" cellspacing="0" cellpadding="0" uid="row">

					<display:column property="nombre" title="Nombre del fichero" defaultorder="descending" style="width:8%" />

					<display:column style="width:8%" title="Descargar">
						<a onclick="" href="javascript:descargarDocumentacion('${row.nombre}')"> Descargar</a>
					</display:column>

					<display:column property="extension" title="Tipo de fichero" defaultorder="descending" style="width:8%" />

					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esConsultableOGuardableDuplicado(tramiteTraficoDuplicado)}">
						<display:column style="width:8%" title="Eliminar">
							<a onclick="" href="javascript:eliminarFichero('${row.nombre}')"> Eliminar</a>
						</display:column>
					</s:if>
				</display:table>
			</td>
		</tr>
	</table>

</div>