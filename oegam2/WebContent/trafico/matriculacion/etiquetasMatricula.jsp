<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>

<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/importacion.js" type="text/javascript"></script>
<script  type="text/javascript">  

</script>

	
<div id="contenido" class="contentTabs" style="display: block;">
	<s:form method="post" name="formData" id="formData" enctype="multipart/form-data" action="importarEtiquetasMatricula.action">
	
		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular"><span class="titulo">
						Matriculación: Impresión de etiquetas de matrícula (Importación de fichero)
					</span></td>
				</tr>
			</table>
		</div> <!-- div nav -->
		
		
		<!-- Mostramos errores del fichero si es que los hubiera -->
		<!--<s:if test="resultBean.listaMensajes.size()>0">
	   		<div class="nav" id="tasasERROR">
	   		    <table  cellSpacing="0" cellpadding="5" width="100%">
					<tr>
						<td align="left"><b>Errores detectados en fichero:</b></td>
					</tr>
	   		    </table>
	   		</div>
	   		<table width="90%" border="0" cellpadding="0" cellspacing="0" class="tablacoin">
	   			<tr>
					<td align="left">
						<ul>
							<s:iterator value="resultBean.listaMensajes">
							<li><s:property/></li>
							</s:iterator>
						</ul>
					</td>
				</tr>
			</table>
		</s:if>-->
		
		
		
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Seleccione el fichero a importar</td>
			</tr>
		</table>	
		
		
		<!-- Seleccionar el archivo -->
		<table cellSpacing=3 class="tablaformbasica" cellPadding=0>
			    <tr>
			    	<td align="left" nowrap="nowrap">
						<label>Fichero</label>
					</td>
					<td align="left" nowrap="nowrap" colspan="5">
			    		<s:file id="fileUpload" size="50" name="fileUpload" value="fileUpload"></s:file>
	      			</td>
	    		</tr>
		</table>
	
	
	

		<div id="botonesImportarEtiqueta">
			<table class="acciones">
				<tr>
					<td>
						<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Aceptar"  onkeypress="this.onClick" onclick="aceptarImportarEtiquetas()"/>			
						<input type="button" class="boton" name="bCancelar" id="bCancelar" value="Cancelar"  onkeypress="this.onClick" onclick="cancelarImportarEtiquetas()"/>
					</td>
				</tr>
			</table>
		</div>
	
	</s:form>
	
	
</div> <!-- div contenido -->