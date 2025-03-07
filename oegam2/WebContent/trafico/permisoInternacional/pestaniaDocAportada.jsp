<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">			
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Permiso Internacional</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Documentación aportar</span>				
			</td>
		</tr>
	</table>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoInter@getInstance().hayDocumentacionAportada(permisoInternacionalDto)}">
		<display:table name="permisoInternacionalDto.listaDocumentos" class="tablacoin" uid="tablaDocAportadaPIntern" requestURI= ""
				id="tablaDocAportadaPIntern" summary="Listado Documentacion Aportada" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="idPermisoInternacional" media="none" paramId="idPermisoInternacional"/>	
				
				<display:column property="nombreFichero" title="Nombre Fichero"
					sortable="false" defaultorder="descending" style="width:16%;"/>
					
				<display:column property="numReferencia" title="Num.Referencia"
					sortable="false" defaultorder="descending" style="width:16%;"/>
					
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column style="width:8%" title="Descargar" >
						<a onclick="" href="javascript:descargarDocAportadaPInter('<s:property value="#attr.tablaDocAportadaPIntern.idPermisoInternacional"/>')"> Descargar </a>
					</display:column>
				</s:if>
			</display:table>
	</s:if>
	<s:else>
		<table cellSpacing="2" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="labelNumRefPI">Num.Referencia<span class="naranja">*</span>:</label>
				</td>
				<td  align="left" nowrap="nowrap">
					<s:textfield id="idNumReferenciaPI"
	       				name="permisoInternacionalDto.numReferencia" 
	       				 onfocus="this.className='inputfocus';" size="25" maxlength="25"/>
				</td>
			</tr>
			<tr>
				<td align="left">
					<label>Fichero<span class="naranja">*</span>:</label>
				</td>
				<td>
 					<s:file id="idFicheroDocAportarPInter" size="50" name="fichero" value="fichero"/>
 				</td>
 				<td>
 					<input class="boton" type="button" id="bSubirDocPInter" name="bSubirDocPInter" value="Subir Doc." onClick="javascript:subirDocPIntern();" onKeyPress="this.onClick"/>
 				</td>
			</tr>
		</table>
	</s:else>
									
</div>