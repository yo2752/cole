 <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/576.js" type="text/javascript"></script>
<script src="js/pagosEImpuestos.js" type="text/javascript"></script>
<script src="js/facturacionBotones.js" type="text/javascript"></script>
<script src="js/content-assist.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>


<!--<s:hidden key="respuesta" name="rowid_Solicitud" ></s:hidden>-->

<script  type="text/javascript"></script>
<s:form method="post" id="formData" name="formData" action="GestionFacturar">

	<!--  DRC@06/08/2012 Se encargan de recoger los mensajes a mostrar, a traves de los botones borrador y pdf. El texto se recoje de facturacion.propertis. -->
	<s:hidden    id="mensajeBorrador" name="datosCliente.mensajeBorrador"/>
	<s:hidden    id="mensajePDF"      name="datosCliente.mensajePDF"/>
	<s:hidden    id="mensajeImprimir" name="datosCliente.mensajeImprimir"/>
	<s:hidden    id="mensajeClave"    name="datosCliente.mensajeClave"/>
	<s:hidden    id="checkPDF" 	      name="datosCliente.factura.checkPdf"/>
	
	<s:hidden    id="mensajeXML"      name="datosCliente.mensajeXML"/>	

	<!-- Se hace referencia a la parte de errores de los mensajes -->
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<s:if test="%{datosCliente.IsPantallaAlta}">
				<td>Nueva Factura</td>
			</s:if>
			<s:if test="%{!datosCliente.IsPantallaAlta}">
				<td>Modificar Factura</td>
			</s:if>
		</tr>
	</table>
	
	<s:hidden id="estadoPdf"   name="datosCliente.estadoPDF" />
	<s:hidden id="tipoPdf"     name="datosCliente.tipoPDF" />
	<s:hidden id="estadoXml"   name="datosCliente.estadoXML" />
	
	
	<!-- Se hace referencia a las tres partes de la nueva factura -->     
	<%@include file="DatosFacturacion.jspf" %>
	<%@include file="facturaEmisor.jspf" %>
	<%@include file="DatosClienteFacturacion.jspf" %>      
	<%@include file="DatosConceptoFacturacion.jspf" %>  

	<table class="acciones">
		<tr>
			<td>
				<img id="loadingImage" style="display:none; margin-left:auto; margin-right:auto; text-align:center;" src="img/loading.gif">
			</td>
		</tr>
	</table>
	
	<table align="center">
		<tr>
			<s:if test="%{datosCliente.IsPantallaAlta}">
					<s:if test="%{!datosCliente.isError}">
						<td>
							<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Guardar" onClick="return guardarFactura();" onKeyPress="this.onClick" />
						</td>						
						<td>
							<input class="boton" type="button" id="idGenerarBorrador" name="bGenerarBorrador" value="Generar Borrador" onClick="return generarBorradorFacturacion(this, 'imprimir');" onKeyPress="this.onClick" disabled style="color:#6E6E6E"/>
						</td>
						<td>
							<input class="boton" type="button" id="idGenerarPDF" name="bGenerarPDF" value="Generar PDF" onClick="return generarPDFFacturacion(this, 'imprimir');" onKeyPress="this.onClick" disabled style="color:#6E6E6E"/>
						</td>
					</s:if>
					<s:if test="%{datosCliente.isError}">
						<td>
							<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Guardar" onClick="return validarGuardarFactura(this);" onKeyPress="this.onClick" disabled style="color:#6E6E6E"/>
						</td>						
						<td>
							<input class="boton" type="button" id="idGenerarBorrador" name="bGenerarBorrador" value="Generar Borrador" onClick="return generarBorradorFacturacion(this, 'imprimir');" onKeyPress="this.onClick" />
						</td>						
						<td>
							<input class="boton" type="button" id="idGenerarPDF" name="bGenerarPDF" value="Generar PDF" onClick="return generarPDFFacturacion(this, 'imprimir');" onKeyPress="this.onClick" />
						</td>
					</s:if>				
			</s:if>
			
			<s:if test="%{!datosCliente.IsPantallaAlta}">
				<s:if test="%{!datosCliente.IsRectificativa}">			
					<td>
						<input type="button" class="boton" id="idBotonGuardar" name="bGuardar" value="Modificar" onClick="validarModificarFactura();" onKeyPress="this.onClick"/>
					</td>
					<td>
						<input class="boton" type="button" id="idGenerarBorrador" name="bGenerarBorrador" value="Generar Borrador" onClick="return generarBorradorFacturacion(this, 'generar');" onKeyPress="this.onClick" />
					</td>				
					<td>
						<input class="boton" type="button" id="idGenerarPDF" name="bGenerarPDF" value="Generar PDF" onClick="return generarPDFFacturacion(this, 'imprimir');" onKeyPress="this.onClick" />
					</td>
				</s:if>

				<s:if test="%{datosCliente.IsRectificativa}">
					<td>
						<input type="button" class="boton" id="idBotonGuardar" name="bGuardar" value="Modificar" onClick="return validarModificarFacturaRectificativa(this);" onKeyPress="this.onClick"/>
					</td>
					<td>
						<input class="boton" type="button" id="idGenerarBorrador" name="bGenerarBorrador" value="Generar Borrador" onClick="return generarBorradorFacturacion(this, 'generar');" onKeyPress="this.onClick" />
					</td>
					<td>
						<input class="boton" type="button" id="idGenerarPDF" name="bGenerarPDF" value="Imprimir PDF" onClick="return generarPDFFacturacion(this, 'imprimir');" onKeyPress="this.onClick" />
					</td>				
				</s:if>				
			</s:if>	
		</tr>		
	</table>
	
	<div id="bloqueLoadingFactura" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>	 

	<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 			
	frameborder="0" 	
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</s:form>

<script type="text/javascript">
	rellenarCerosNumeracion();
	rellenarFechaFactura();	
	setTimeout("cargarDatosFacturacion()", 1000);
</script>