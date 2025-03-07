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
<s:form method="post" name="formData" id="formData" enctype="multipart/form-data" action="importarTasas.action">
  	<s:hidden name="retorno" value="Tasas"/>
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular"><span class="titulo">
					Tasas: Importación de fichero
				</span></td>
			</tr>
		</table>
	</div> 
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td>Seleccione el fichero a importar</td>
		</tr>
	</table>	
	<div id="busqueda">
		<table class="tablaformbasica">
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<tr>
					<td  align="left" nowrap="nowrap" style="width:10%;"> 
						<label for="contrato">Contrato:</label>
					</td>
					<td align="left">
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<s:select id="contrato"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" listKey="codigo"
								listValue="descripcion" cssStyle="width:308px"
								name="contratoImportacion"></s:select>
						</s:if>
						<s:else>
							<s:select id="contrato"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitadosColegio(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoBigDecimal())"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" listKey="codigo"
								listValue="descripcion" cssStyle="width:150px"
								name="contratoImportacion"></s:select>
						</s:else>
					</td>
				</tr>
			</s:if>
			<tr>
				<td  align="left" nowrap="nowrap" style="width:10%;">
					<label for=ficheroTasas>Fichero:</label>
				</td> 
				<td align="left">
					<s:file id="ficheroTasas" size="50" name="ficheroTasas" value="ficheroTasas" onblur="this.className='input2';" onfocus="this.className='inputfocus';"></s:file>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="formato">Formato:</label>
				</td>
				<td align="left">
					<s:radio cssStyle="display: inline;"
						name="formato" id="formato"
						list="@org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa@values()"
						listKey="codigo" listValue="descripcion" labelposition="none" />
				</td>
			</tr>
		</table>
	</div>
	<div id="botonesImportarTasas">
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Enviar"  onkeypress="this.onClick" onclick="aceptarImportarTasas()"/>			
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	</div>
	<s:if test="%{#session.subidaFinalizada!=null}">
		<div id="resumenTodo" style="display='block';">
		<br>
			<table class="subtitulo" cellSpacing="0" cellspacing="0">
				<tr>
					<td>Resumen de la importación:</td>
					<td><img src="img/impresora.png" style="height: 23px;cursor:pointer;" onclick="imprimirId('resumenTodo',false,null);"></td>
				</tr>
			</table>
			<table style="width:720px;font-size:11px;height:auto;" class="tablacoin" summary="Resumen de la importación" border="0" cellpadding="0" cellspacing="0" style="margin-top:0px;">
	    		<tr>
	     			<th>Tipo Tasa</th>
	     			<th>Correctas</th>
	     			<th>Incorrectas</th>
	   			</tr>
				<s:iterator value="listaResumen" var="tasa">
					<tr>
	    			  <s:if test="%{(guardadasBien != 0) || (guardadasMal != 0)}">
	    			  	<td style="font-weight:bold;height:auto;"><s:property value="tipoTasa"/></td>
	    			  </s:if>
	    			  <s:else>
	    			  	<td><s:property value="tipoTasa"/></td>
	    			  </s:else>
	      			  <s:if test="%{guardadasBien != 0}">
	      			  	<td style="color:green;font-weight:bold;height:auto;">
							<table style="width:100%;border:0px;">
	   							<tr>
	        						<td style="width:55%;text-align:right;border:0px;height:auto;">
	        							<s:property value="guardadasBien"/>
	        						</td>
	       							<td style="width:45%;text-align:left;border:0px;height:auto;">
	       								<!--<s:if test="%{tipoTasa.equals('TOTAL')}"> <img src='img/plus.gif' onclick='cambiarMostrarInfo(this);'></s:if>
	       							--></td>
	  							</tr>
							</table>
						</td>
	      			  </s:if>
	      			  <s:else>
	      			  	<td style="color:green;">
							<table style="width:100%;border:0px;">
	   							<tr>
	        						<td style="width:55%;text-align:right;border:0px;height:auto;">
	        							<s:property value="guardadasBien"/>
	        						</td>
	       							<td style="width:45%;text-align:left;border:0px;height:auto;">
	       							</td>
	  							</tr>
							</table>
						</td>
	      			  </s:else>
	     			  <s:if test="%{guardadasMal != 0}">
	     			  	<td style="color:red;font-weight:bold;height:auto;">
							<table style="width:100%;border:0px;">
	   							<tr>
	        						<td style="width:55%;text-align:right;border:0px;height:auto;">
	        							<s:property value="guardadasMal"/>
	        						</td>
	       							<td style="width:45%;text-align:left;border:0px;height:auto;">
	       								<s:if test="%{tipoTasa.equals('TOTAL')}"> <img src='img/plus.gif' style='cursor:pointer;' onclick='cambiarMostrarError(this);'></s:if>
	       							</td>
	  							</tr>
							</table>
						</td>
	     			  </s:if>
	     			  <s:else>
	     			  	<td style="color:red;">
							<table style="width:100%;border:0px;">
	   							<tr>
	        						<td style="width:55%;text-align:right;border:0px;height:auto;">
	        							<s:property value="guardadasMal"/>
	        						</td>
	       							<td style="width:45%;text-align:left;border:0px;height:auto;"></td>
	  							</tr>
							</table>
						</td>
	     			  </s:else>
	   		 		</tr>
				</s:iterator>
	 		</table>
			<br>
			<%@include file="../../includes/erroresMasMensajesImp.jspf" %>
			<s:if test="hasActionErrors()">
				<table cellSpacing=3 class="tablaformbasica" cellPadding=0>
					<tr>
					    <td align="left" nowrap="nowrap"  style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
							Puede descargar el fichero con las l&iacute;neas que no se importaron
							haciendo click en el siguiente enlace:
							<s:url var="ficheroDescarga" action="descargaFicheroImportarTasas.action"></s:url>
							<s:a href="%{ficheroDescarga}">Enlace fichero</s:a>
						</td>
				    </tr>
				</table>
			</s:if>
		</div> <!-- fin del resumen todo -->
	</s:if>
</s:form>
