<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">			
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos Documentación</td>
		</tr>
	</table>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esAccionDoc(duplicadoPermisoConducirDto)}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">Subir Documentación</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="2" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td>
						<label for="tipoDoc">Tipo: <span class="naranja">*</span></label>
					</td>
					<td>
						<s:select id="tipoDocumento" name="tipoDocumento"
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().getTipoDocumentos()"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" listValue="nombreEnum"
							listKey="valorEnum" title="Tipo Documento" headerKey="" headerValue="Tipo Documento"/>
					</td>
				</tr>
				<tr>
					<td align="left">
						<label>Fichero: <span class="naranja">*</span></label>
					</td>
					<td>
						<s:file id="idFicheroDocAportarDPC" size="50" name="fichero" value="fichero"/>
					</td>
					<td>
						<input class="boton" type="button" id="bSubirDocDPC" name="bSubirDocDPC" value="Subir Doc." onClick="javascript:subirDocDPC();" onKeyPress="this.onClick"/>
					</td>
				</tr>
		</table>
	</s:if>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">Listado Documentos</span>
				</td>
			</tr>
		</table>
	<s:if test="%{duplicadoPermisoConducirDto.listaDocumentos != null && duplicadoPermisoConducirDto.listaDocumentos.size > 0}">
		<display:table name="duplicadoPermisoConducirDto.listaDocumentos" class="tablacoin" uid="tablaDocAportadaDPC" requestURI=""
				id="tablaDocAportadaDPC" summary="Listado Documentacion" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="numExpediente" media="none" paramId="numExpediente"/>

				<display:column property="nombreFichero" title="Nombre Fichero" sortable="false" defaultorder="descending" style="width:16%;"/>

				<display:column property="tipo" title="Tipo" sortable="false" defaultorder="descending" style="width:7%;"/>

				<display:column property="estado" title="Estado" sortable="false" defaultorder="descending" style="width:7%;"/>

				<display:column style="width:8%" title="">
					<a onclick="" href="javascript:descargarDocAportadaDPC('<s:property value="#attr.tablaDocAportadaDPC.numExpediente"/>', '<s:property value="#attr.tablaDocAportadaDPC.nombreFichero"/>', '<s:property value="#attr.tablaDocAportadaDPC.tipo"/>')"> Descargar </a>
				</display:column>

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esAccionDoc(duplicadoPermisoConducirDto)}">
					<display:column style="width:8%" title="">
						<s:if test="%{#attr.tablaDocAportadaDPC.puedeEliminarse == 'SI'}">
							<a onclick="" href="javascript:eliminarDocAportadaDPC('<s:property value="#attr.tablaDocAportadaDPC.numExpediente"/>', '<s:property value="#attr.tablaDocAportadaDPC.nombreFichero"/>', '<s:property value="#attr.tablaDocAportadaDPC.tipo"/>')"> Eliminar </a>
						</s:if>
					</display:column>
				</s:if>
		</display:table>
	</s:if>
	<s:else>
		<div class="acciones center">
		<table width="95%">
			<tr>
				<td>
					<span>No hay documentación para este trámite</span>
				</td>
			</tr>
		</table>
		</div>
	</s:else>
</div>