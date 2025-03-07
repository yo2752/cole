<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">		
<s:hidden name="idFicheroEliminar"/>
<s:hidden name="idFicheroDescargar"/>
<table class="subtitulo" align="left" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
			<td>Documentación</td>
		</tr>
	</table>
	<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >

		<tr>
			<td class="tabular" >
					<span class="titulo">Documentaci&oacute;n aportada</span>
				</td>
		</tr>
	</table>
					
		<table cellPadding="1" cellSpacing="3"  cellpadding="1"  class="tablaformbasica" width="100%" border="0">
			<tr>
				<td align="right" width="20%" nowrap="nowrap">
					<label id="fileUpload" >Fichero</label>
				</td>
						<td align="right"><s:file id="fichero" size="50" name="fichero" accept="application/pdf" /></td>
						<td align="right" nowrap="nowrap" width="30%"><input type="button" class="boton" id="botonSubirFichero" value="Subir fichero" onclick="incluirFichero()" /></td>
						<td align="right" nowrap="nowrap" width="30%"></td>
				<td align="left" width="10%">
					<img id="loading3Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
				
	 <table cellPadding="1" cellSpacing="3" cellpadding="1" class="tablaformbasica" width="100%" id="filesList">
		<tr>
			<td align="center">
				<display:table name="tramiteTraficoTransmisionBean.ficherosSubidos" excludedParams="*" style="width:95%"
					class="tablacoin" id="row" summary="Listado de Ficheros Subidos" cellspacing="0" cellpadding="0" uid="row">	
							
					<display:column property="nombre" title="Nombre del fichero" defaultorder="descending" style="width:8%"/>
							
					<display:column style="width:8%" title="Descargar" >
						<a onclick="" href="javascript:descargarDocumentacion('${row.nombre}','aportada')"> Descargar </a>
					</display:column>
							
					<display:column property="extension" title="Tipo de fichero" defaultorder="descending" style="width:8%"  />										
						<%-- <s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esConsultableOGuardableTransmision(tramiteTraficoTransmisionBean.tramiteTraficoBean)}"> --%>		
							<display:column style="width:8%" title="Eliminar" >
							<a onclick="" href="javascript:eliminarFichero('${row.nombre}')"> Eliminar </a>
							</display:column>
						<%-- </s:if> --%>
				</display:table>
			</td>
		</tr>
	</table> 
		<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esConsultableOGuardableTransmision(tramiteTraficoTransmisionBean.tramiteTraficoBean)}">
	<table class="acciones" width="95%" align="left">
		<tr>
			<td align="center" style="size: 100%; TEXT-ALIGN: center;list-style:none;">
				<input type="button" 
					   class="boton" 
					   name="bGuardar2" 
					   id="bGuardar2" 
					   value="Consultar / Guardar" 
					   onClick="sinPermiso();return guardarAltaTramiteTransmision('Documentacion')" 
					   onKeyPress="this.onClick"/>				
			</td>
		</tr>
	</table>
	</s:if>
</div>
