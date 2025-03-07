<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">			
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Mandato</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Documentación mandato</span>
			</td>
		</tr>
	</table>
	<s:if test="%{duplicadoPermisoConducirDto.mandatos != null && duplicadoPermisoConducirDto.mandatos.size > 0}">
		<display:table name="duplicadoPermisoConducirDto.mandatos" class="tablacoin" uid="tablaMandatoDPC" requestURI= ""
			id="tablaMandatoDPC" summary="Mandato" excludedParams="*" sort="list"
			cellspacing="0" cellpadding="0" decorator="${decorator}">

			<display:column property="nombreDocumento" title="Nombre Documento" style="width:4%;"
					paramId="nombreDocumento" paramProperty="nombreDocumento" headerClass="sortable" defaultorder="descending"/>

			<display:column property="tipoDocumento" title="Tipo Documento" style="width:4%;" headerClass="sortable" defaultorder="descending" paramId="tipo"/>

			<display:column property="fechaCreacion" title="Fecha Creación" style="width:4%;" headerClass="sortable" defaultorder="descending" paramId="fechaCreacion"
					format="{0,date,dd-MM-yyyy}"/>

			<display:column property="fechaGenerado" title="Fecha Generación" style="width:4%;" headerClass="sortable" defaultorder="descending" paramId="fechaGenerado"
					format="{0,date,dd-MM-yyyy}"/>

			<display:column style="width:8%" title="">
				<a onclick="" href="javascript:descargarMandatoDPC()"> Descargar</a>
			</display:column>

			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplPermCond@getInstance().esAccionDoc(duplicadoPermisoConducirDto)}">
				<display:column style="width:8%" title="" >
					<a onclick="" href="javascript:eliminarMandatoDPC()"> Eliminar</a>
				</display:column>
			</s:if>
		</display:table>
	</s:if>
	<s:else>
		<div class="acciones center">
		<table width="95%">
			<tr>
				<td>
					<span>No se ha generado el mandato, puede generarlo desde la pestaña titular</span>
				</td>
			</tr>
		</table>
		</div>
	</s:else>
</div>