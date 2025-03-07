<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<s:hidden name="idFicheroEliminar"/>
	<s:hidden name="idFicheroDescargar"/>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Documentación</td>
			<s:if test="lcTramiteDto.numExpediente">
				<td  align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  					onclick="abrirEvolucion(<s:property value="%{lcTramiteDto.numExpediente}"/>,'divEmergentePopUp');" title="Ver evolución"/> 					
				</td>
			</s:if>
		</tr>
	</table>
	
	 <table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular" >
				<span class="titulo">Documentaci&oacute;n aportada</span>
			</td>
		</tr>
	</table>
					
	<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
		<table cellPadding="1" cellSpacing="3"  cellpadding="1"  class="tablaformbasica" width="100%" border="0">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idTipoDocumentoLic">Tipo Documento: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3">
					<s:select id="idTipoDocumentoLic" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="tipoDocumentoLic"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tiposDocumentos()"
						listKey="codigo" listValue="descripcion" headerKey="-1" headerValue="Seleccionar Tipo Documento"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="20%" nowrap="nowrap">
					<label id="fileUpload" >Fichero</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="5">
					<s:file size="50" name="fileUpload" value="fileUpload"></s:file>
				</td>
				<td align="right" nowrap="nowrap" width="30%">
					<input type="button" class="botonGrande" id="botonSubirFichero" value="Subir fichero" onclick="incluirFichero();" />
				</td>
				<td align="left" width="10%">
					<img id="loading3Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	</s:if>
				
	<table cellPadding="1" cellSpacing="3" cellpadding="1" class="tablaformbasica" width="100%" >
		<tr>
			<td align="center">
				<display:table name="lcTramiteDto.ficherosSubidos" excludedParams="*" style="width:95%"
					class="tablacoin" id="Bien" summary="Listado de Ficheros Subidos" cellspacing="0" cellpadding="0" uid="row">	
							
					<display:column property="tipo" title="Tipo" defaultorder="descending" style="width:8%"/>		
							
					<display:column property="nombre" title="Nombre del fichero" defaultorder="descending" style="width:8%"/>
							
					<display:column style="width:8%" title="Descargar" >
						<a onclick="" href="javascript:descargarDocumentacion('${row.nombre}')"> Descargar </a>
					</display:column>
							
					<display:column property="extension" title="Tipo de fichero" defaultorder="descending" style="width:8%"  />										
							
					<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
						<display:column style="width:8%" title="Eliminar" >
							<a onclick="" href="javascript:eliminarFichero('${row.nombre}')"> Eliminar </a>
						</display:column>
					</s:if>
				</display:table>
			</td>
		</tr>
		<s:if test="lcTramiteDto.ficherosSubidos != null && !lcTramiteDto.ficherosSubidos.isEmpty()">
			<tr>
				<td align="center">
					<input type="button" class="botonGrande" id="botonEnvDocu" value="Enviar Documentación" onclick="enviarDocumentacion();" />
				</td>
			</tr>
		</s:if>
	</table>
</div>
