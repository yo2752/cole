<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<s:hidden name="idFicheroEliminar"/>
	<s:hidden name="idFicheroDescargar"/>
	
	<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Documentaci&oacute;n del tr&aacute;mite</td>
			<s:if test="tramiteRegistro.idTramiteRegistro">
				<td align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  				onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
				</td>
			</s:if>
		</tr>
	</table>	
	<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
		<tr>
			<td class="tabular" >
					<span class="titulo">Documentaci&oacute;n aportada</span>
				</td>
		</tr>
	</table>
					
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		<table cellPadding="1" cellSpacing="3"  cellpadding="1"  class="tablaformbasica" width="100%" border="0">
			<tr>
				<td align="right" width="20%" nowrap="nowrap">
					<label id="fileUpload" >Fichero</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="5">
					<s:file size="50" name="fileUpload" value="fileUpload"></s:file>
				</td>
				<td align="right" nowrap="nowrap" width="30%">
					<input type="button" class="boton" id="botonSubirFichero" value="Subir fichero" onclick="incluirFichero();" />
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
				<display:table name="tramiteRegistro.ficherosSubidos" excludedParams="*" style="width:95%"
					class="tablacoin" id="Bien" summary="Listado de Ficheros Subidos" cellspacing="0" cellpadding="0" uid="row">	
							
					<display:column property="nombre" title="Nombre del fichero" defaultorder="descending" style="width:8%"/>
							
					<display:column style="width:8%" title="Descargar" >
						<a onclick="" href="javascript:descargarDocumentacion('${row.nombre}','enviada')"> Descargar </a>
					</display:column>
							
					<display:column property="extension" title="Tipo de fichero" defaultorder="descending" style="width:8%"  />										
							
					<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
						<display:column style="width:8%" title="Eliminar" >
							<a onclick="" href="javascript:eliminarFichero('${row.nombre}')"> Eliminar </a>
						</display:column>
					</s:if>
				</display:table>
			</td>
		</tr>
	</table>
	
	<s:if test="tramiteRegistro.estado && tramiteRegistro.estado != 1 && tramiteRegistro.estado != 4
			&& tramiteRegistro.estado != 28 && tramiteRegistro.estado != 29" >		
		<!-- DOCUMENTACION RECIBIDA -->	
		<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
			<tr>
				<td class="tabular" >
						<span class="titulo">Documentaci&oacute;n recibida</span>
					</td>
			</tr>
		</table>
		<table cellPadding="1" cellSpacing="3" cellpadding="1" class="tablaformbasica" width="100%" >
			<tr>
				<td align="center">
					<display:table name="tramiteRegistro.listaDocuRecibida" excludedParams="*" style="width:95%"
						class="tablacoin" id="Bien" summary="Listado de Ficheros Subidos" cellspacing="0" cellpadding="0" uid="row">	
								
						<display:column property="nombre" title="Nombre del fichero" defaultorder="descending" style="width:8%"/>
								
						<display:column style="width:8%" title="Descargar" >
							<a onclick="" href="javascript:descargarDocumentacion('${row.nombre}','recibida')"> Descargar </a>
						</display:column>
								
						<display:column property="extension" title="Tipo de fichero" defaultorder="descending" style="width:8%"  />
					</display:table>
				</td>
			</tr>
		</table>
		
		<table cellPadding="1" cellSpacing="3" cellpadding="1" class="tablaformbasica" width="100%" >
			<tr>
				<td align="center">
					<input type="button" class="boton" value="Adjuntos" id="botonDescargarDocumentos" onclick="descargarAdjuntos();"/>
				</td>
			</tr>
		</table>
	</s:if>


