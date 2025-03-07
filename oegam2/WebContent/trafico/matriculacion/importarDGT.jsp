<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/importacion.js" type="text/javascript"></script>
	
<s:form method="post" name="formData" id="formData" enctype="multipart/form-data" action="importarDGTImportar.action">
	
		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular"><span class="titulo">
						Importación de trámites mediante ficheros
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
		</s:if>	-->				
		
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Seleccione el fichero a importar</td>
			</tr>
		</table>	
		
	<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().esColegiadoImportExcel()}">	
		<table cellSpacing=3 class="tablaformbasica" cellPadding=0>
		 <tr>
			<td nowrap="nowrap" align="left" width="30%">
				<table  align="left" >
						
						<tr>
							<td width="10"><input type="radio" id="xls_baja" name="tipoFicheroDGT" 
								value="<s:property value="@trafico.utiles.constantes.cons@VALOR_TIPO_FICHERO_XLS_BAJA"/>" onchange="deshabilitarContrato('xls_baja');">
							</td>
							<td style="vertical-align:bottom"><label for="xls_baja">.XLS BAJA</label></td>
						</tr>
						<tr>
							<td width="10"><input type="radio" id="xls_duplicado" name="tipoFicheroDGT" 
								value="<s:property value="@trafico.utiles.constantes.cons@VALOR_TIPO_FICHERO_XLS_DUPLICADO"/>" onchange="deshabilitarContrato('xls_duplicado');">
							</td>
							<td style="vertical-align:bottom"><label for="xls_duplicado">.XLS DUPLICADO</label></td>
						</tr>
						<tr>
							<td width="10"><input type="radio" id="xls_cambioServicio" name="tipoFicheroDGT" 
								value="<s:property value="@trafico.utiles.constantes.cons@VALOR_TIPO_FICHERO_XLS_CAMBIOSERVICIO"/>" onchange="deshabilitarContrato('xls_cambioServicio');">
							</td>
							<td style="vertical-align:bottom"><label for="xls_cambioServicio">.XLS CAMBIO SERVICIO</label></td>
						</tr>
				</table>
			</td>

	    	<td align="left" nowrap="nowrap" width="20%">
				<table align="left" >
					<tr>
						<td align="left"><label>Fichero</label></td>
					</tr>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<tr>
						<td align="left"><br><label>Contrato:</label></td>			    	
	    			</tr>
					</s:if>
					
				</table>
			</td>
			<td align="left" nowrap="nowrap" width="50%">
	    		<table>			    		
		    		<tr>
		    			<td>
		   					<s:file id="fichero" size="50" name="fichero" value="fichero" onblur="this.className='input2';" onfocus="this.className='inputfocus';"></s:file>
		   				</td>
		    		</tr>	
	    		
	    			<tr>			    			
	    				<td>
		    				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
		    					<s:select  list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()" onblur="this.className='input2';" onfocus="this.className='inputfocus';" listKey="codigo" listValue="descripcion" cssStyle="width:250px" name="contratoImportacion"></s:select>
		    				</s:if>
		    				<s:else>
		    					<s:hidden name="contratoImportacion" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getIdContratoBigDecimal()}"/>
		   					</s:else>
	    				</td>
	    			</tr>
	    		</table>
	    	</td>	
		 </tr>
		</table>	
		<table class="acciones">
			<tr>					
				<td>
					<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Enviar"  onkeypress="this.onClick" onclick="aceptarImportarDGT()"/>			
				</td>
			</tr>
			<tr>
				<td>
					<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	
		<div id="resumenTodo" style="display='block';">
	  	<s:if test="hasActionErrors() || hasActionMessages()">
	  	<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Resumen de la importación:</td>
				<td><img src="img/impresora.png" style="height: 23px;cursor:pointer;" onclick="imprimirId('resumenTodo',false,null);"></td>
			</tr>
		</table>
		
		<table style="width:720px;font-size:11px;" class="tablacoin" summary="Resumen de la importación" border="0" cellpadding="0" cellspacing="0" style="margin-top:0px;">
    		<tr>
     			<th>Tipo de trámite</th>
     			<th>Guardados</th>
     			<th>No guardados</th>
   			</tr>
			<s:iterator value="resumen" var="tramite">
				<tr>
    			  <s:if test="%{(guardadosBien != 0) || (guardadosMal != 0)}">
    			  	<td style="font-weight:bold;height:auto;">
    			  		<s:property value="tipoTramite"/>
    			  	</td>
    			  </s:if>
    			  <s:else>
    			  	<td style="height:auto;">
    			  		<s:property value="tipoTramite"/>
    			  	</td>
    			  </s:else>
      			  <s:if test="%{guardadosBien != 0}">
      			  	<td style="color:green;font-weight:bold;height:auto;">
      			  		<table style="width:100%;border:0px;">
   							<tr>
        						<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosBien"/></td>
       							<td style="width:45%;text-align:left;border:0px;height:auto;"><s:if test="%{tipoTramite.equals('TOTAL')}"> <img src='img/plus.gif' style='cursor:pointer;' onclick='cambiarMostrarInfo(this);'></s:if></td>
  							</tr>
						</table>
      			  		
      			  	</td>
      			  </s:if>
      			  <s:else>
      			  	<td style="color:green;height:auto;">
      			  		<table style="width:100%;border:0px;">
   							<tr>
        						<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosBien"/></td>
       							<td style="width:45%;text-align:left;border:0px;height:auto;"></td>
  							</tr>
						</table>
      			  	</td>
      			  </s:else>
     			  <s:if test="%{guardadosMal != 0}">
     			  	<td style="color:red;font-weight:bold;height:auto;">
     			  		<table style="width:100%;border:0px;">
   							<tr>
        						<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosMal"/></td>
       							<td style="width:45%;text-align:left;border:0px;height:auto;"><s:if test="%{tipoTramite.equals('TOTAL')}"> <img src='img/plus.gif' style='cursor:pointer;' onclick='cambiarMostrarError(this);'></s:if></td>
  							</tr>
						</table>
     			  		
     			  	</td>
     			  </s:if>
     			  <s:else>
     			  	<td style="color:red;height:auto;">
     			  		<table style="width:100%;border:0px;">
   							<tr>
        						<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosMal"/></td>
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

	  	</s:if>	  	
	  	<s:if test="hasActionErrors() && getTipoFichero()=='dgt'">
			<table cellSpacing=3 class="tablaformbasica" cellPadding=0>
				<tr>
				    <td align="left" nowrap="nowrap"  style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
						Puede descargar el fichero con las l&iacute;neas que no se importaron
						haciendo click en el siguiente enlace:
						<s:url var="ficheroDescarga" action="descargarFicheroImportar.action"></s:url>
						<s:a href="%{ficheroDescarga}">Enlace fichero</s:a>
					</td>
			    </tr>
			</table>
		</s:if>
			
			<s:hidden id="tipoFichero" name="tipoFichero"></s:hidden>			
		</div>
		<script>
		controlarMostrarNumEtiquetas();
			function deshabilitarContrato(id){

				if(document.getElementById("formData_contratoImportacion")!=null){
					if(id == "xls_baja" || id == "xls_duplicado"){
						
						if(document.getElementById(id).checked){
							document.getElementById("formData_contratoImportacion").disabled = 'true';
						}else{
							document.getElementById("formData_contratoImportacion").disabled = 'false';
						}
					}
					else{				
						document.getElementById("formData_contratoImportacion").disabled =false;
					}
				}

				controlarMostrarNumEtiquetas();
				
			}
		
		</script> 
		<table cellSpacing=50 class="tablaformbasica" cellPadding=0>
			<tr>	
				<td style=font-size:13px;>
					<br/><br/><br/><br/><br/><br/><br/><br/>A partir de ahora, para poder importar trámites en la plataforma OEgAM, <br><br> se deberá acceder a la siguiente página web, <a href="https://oegamservicios.gestoresmadrid.org/">OEGAM SERVICIOS</a>,
					
					donde deberá identificarse con su carné profesional.
				</td>
			</tr>
		</table>
	</s:if>		

	<s:else>	
		<!-- Seleccionar el archivo -->
		 <table cellSpacing=50 class="tablaformbasica" cellPadding=0>
			<tr>	
				<td style=font-size:13px;>
					<br/><br/><br/><br/><br/><br/><br/><br/>A partir de ahora, para poder importar trámites en la plataforma OEgAM, <br><br> se deberá acceder a la siguiente página web, <a href="https://oegamservicios.gestoresmadrid.org/">OEGAM SERVICIOS</a>,
					
					donde deberá identificarse con su carné profesional.
				</td>
			</tr>
		</table>
		
			   <%--  <tr>
			    
			    	<td nowrap="nowrap" align="left" width="30%">
					<table  align="left" >
						<tr>
							<td width="10"><input type="radio" id="dgt" name="tipoFicheroDGT" 
								value="<s:property value="@trafico.utiles.constantes.cons@VALOR_TIPO_FICHERO_DGT"/>" checked onchange ="deshabilitarContrato('dgt');">
							</td>
							<td style="vertical-align:bottom"><label for="dgt">.DGT</label></td>
						</tr>
					</table>
				</td>
			    	<td align="left" nowrap="nowrap" width="20%">
						<table align="left" >
							<tr>
								<td align="left"><label>Fichero</label></td>
							</tr>
							<tr>
								<td align="left"><br><label>Contrato:</label></td>			    	
			    			</tr>
						</table>
					</td>
					<td align="left" nowrap="nowrap" width="50%">
			    		<table>			    		
			    		<tr>
			    			<td>
		    					<s:file id="fichero" size="50" name="fichero" value="fichero" onblur="this.className='input2';" onfocus="this.className='inputfocus';"></s:file>
		    				</td>
			    		</tr>	
			    		
			    		<tr>			    			
			    			<td>
			    				<s:select  list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()" onblur="this.className='input2';" onfocus="this.className='inputfocus';" listKey="codigo" listValue="descripcion" cssStyle="width:250px" name="contratoImportacion"></s:select>
			    			</td>
			    		</tr>
			    		</table>
	      			</td>
	    		</tr>
		 </table>
		
		<%-- <div id="botonesImportarEtiqueta">
			
			<table cellSpacing=3 class="tablaformbasica" cellPadding=0>
				<tr>
		   			<td id="bloqueNumEtiquetas" style="display:none;text-align: left;"> 
		   			 	<table>
		   			 		<tr>
		   			 			<td>Numero Etiquetas<hr/>
							Por Trámite: <s:textfield name="etiquetaParametros.etiquetasTramite" id="etiquetasTramite" />
		   			 			Por Fila: <s:textfield name="etiquetaParametros.etiquetasFila" id="etiquetasFila" />
		   			 			Por Columna: <s:textfield name="etiquetaParametros.etiquetasColumna" id="etiquetasColumna" /></td>
		   			 			<td>Margen(mm)<hr/>
		   			 			Sup:<s:textfield name="etiquetaParametros.margenSup" id="margenSup" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/>
		   			    		Inf:<s:textfield name="etiquetaParametros.margenInf" id="margenInf" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/>
		   			    		Dcho:<s:textfield name="etiquetaParametros.margenDcho" id="margenDcho" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/>
		   			    		Izqd:<s:textfield name="etiquetaParametros.margenIzqd" id="margenIzqd" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/></td>
		   			    	</tr>
		   			    	<tr>	
		   			    		<td>Espaciado(mm)<hr/>
		   			 			Horizontal:<s:textfield name="etiquetaParametros.horizontal" id="horizontal" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/>
		   			    		Vertical:<s:textfield name="etiquetaParametros.vertical" id="vertical" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/></td>
		   			    		<td>Primera Etiqueta:<hr/>
		   			 			Fila:<s:textfield name="etiquetaParametros.filaPrimer" id="filaPrimer" />
		   			    		Columna:<s:textfield name="etiquetaParametros.columnaPrimer" id="columnaPrimer" /></td>
		   			 		</tr>
	   			 		</table>    
		   			</td>
	   			</tr>
			</table>
			
			<table class="acciones">
				<tr>					
					<td>
						<input type="button" class="boton" name="bAceptar" id="bAceptar" value="Enviar"  onkeypress="this.onClick" onclick="aceptarImportarDGT()"/>			
					</td>
				</tr>
				<tr>
					<td>
						<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
					</td>
				</tr>
			</table>
		</div>
		<div id="resumenTodo" style="display='block';">
	  	<s:if test="hasActionErrors() || hasActionMessages()">
	  	<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Resumen de la importación:</td>
				<td><img src="img/impresora.png" style="height: 23px;cursor:pointer;" onclick="imprimirId('resumenTodo',false,null);"></td>
			</tr>
		</table>
		
		<table style="width:720px;font-size:11px;" class="tablacoin" summary="Resumen de la importación" border="0" cellpadding="0" cellspacing="0" style="margin-top:0px;">
    		<tr>
     			<th>Tipo de trámite</th>
     			<th>Guardados</th>
     			<th>No guardados</th>
   			</tr>
			<s:iterator value="resumen" var="tramite">
				<tr>
    			  <s:if test="%{(guardadosBien != 0) || (guardadosMal != 0)}">
    			  	<td style="font-weight:bold;height:auto;">
    			  		<s:property value="tipoTramite"/>
    			  	</td>
    			  </s:if>
    			  <s:else>
    			  	<td style="height:auto;">
    			  		<s:property value="tipoTramite"/>
    			  	</td>
    			  </s:else>
      			  <s:if test="%{guardadosBien != 0}">
      			  	<td style="color:green;font-weight:bold;height:auto;">
      			  		<table style="width:100%;border:0px;">
   							<tr>
        						<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosBien"/></td>
       							<td style="width:45%;text-align:left;border:0px;height:auto;"><s:if test="%{tipoTramite.equals('TOTAL')}"> <img src='img/plus.gif' style='cursor:pointer;' onclick='cambiarMostrarInfo(this);'></s:if></td>
  							</tr>
						</table>
      			  		
      			  	</td>
      			  </s:if>
      			  <s:else>
      			  	<td style="color:green;height:auto;">
      			  		<table style="width:100%;border:0px;">
   							<tr>
        						<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosBien"/></td>
       							<td style="width:45%;text-align:left;border:0px;height:auto;"></td>
  							</tr>
						</table>
      			  	</td>
      			  </s:else>
     			  <s:if test="%{guardadosMal != 0}">
     			  	<td style="color:red;font-weight:bold;height:auto;">
     			  		<table style="width:100%;border:0px;">
   							<tr>
        						<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosMal"/></td>
       							<td style="width:45%;text-align:left;border:0px;height:auto;"><s:if test="%{tipoTramite.equals('TOTAL')}"> <img src='img/plus.gif' style='cursor:pointer;' onclick='cambiarMostrarError(this);'></s:if></td>
  							</tr>
						</table>
     			  		
     			  	</td>
     			  </s:if>
     			  <s:else>
     			  	<td style="color:red;height:auto;">
     			  		<table style="width:100%;border:0px;">
   							<tr>
        						<td style="width:55%;text-align:right;border:0px;height:auto;"><s:property value="guardadosMal"/></td>
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

	  	</s:if>	  	
	  	<s:if test="hasActionErrors() && getTipoFichero()=='dgt'">
			<table cellSpacing=3 class="tablaformbasica" cellPadding=0>
				<tr>
				    <td align="left" nowrap="nowrap"  style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
						Puede descargar el fichero con las l&iacute;neas que no se importaron
						haciendo click en el siguiente enlace:
						<s:url var="ficheroDescarga" action="descargarFicheroImportar.action"></s:url>
						<s:a href="%{ficheroDescarga}">Enlace fichero</s:a>
					</td>
			    </tr>
			</table>
			</s:if>
			
			<s:hidden id="tipoFichero" name="tipoFichero"></s:hidden>			
		</div>
		<script>
		controlarMostrarNumEtiquetas();
			function deshabilitarContrato(id){

				if(document.getElementById("formData_contratoImportacion")!=null){
					if(id == "xls_baja" || id == "xls_duplicado"){
						
						if(document.getElementById(id).checked){
							document.getElementById("formData_contratoImportacion").disabled = 'true';
						}else{
							document.getElementById("formData_contratoImportacion").disabled = 'false';
						}
					}
					else{				
						document.getElementById("formData_contratoImportacion").disabled =false;
					}
				}

				controlarMostrarNumEtiquetas();
				
			}
		
		</script> --%>
	</s:else>
	</s:form>
	

	
	
	
