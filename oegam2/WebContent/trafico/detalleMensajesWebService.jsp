<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<script src="js/genericas.js" type="text/javascript"></script>
<script type="text/javascript">
	function guardarMensajesWebService(){
		if(!confirm("¿Realmente desea guardar el mensaje del web service?")){
			return false;
		}
		document.formData.action="guardarDetalleMensajesWebService.action";
		document.formData.submit();
		return true;
	}
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">Detalle Mensajes Errores Web Services</span></td>
		</tr>
	</table>
</div>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

	<%@include file="../includes/erroresMasMensajes.jspf" %>

	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos Del Mensaje</td>
			</tr>
		</table>
	
		<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label style="text-aling:left;" for="codigo" >Código</label>
					<s:textfield  name="mensajesProcesos.codigo" id="codigo"  cssClass="inputview" size="60px" readonly="false"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label style="text-aling:left;" for="mensaje" >Mensaje</label>
					<s:textfield  name="mensajesProcesos.mensaje" id="mensaje"  cssClass="inputview" size="80px" readonly="false"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label style="text-aling:left;" for="descripción" >Descripción</label>
					<s:textfield  name="mensajesProcesos.descripcion" id="descripcion"  cssClass="inputview" size="80px" readonly="false"/>
				</td>
			</tr>
			<tr>

			<td align="left" nowrap="nowrap">
				<label style="text-aling:left;" for="recuperable">Recuperable</label>
				<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadoPresentacionJPT()"
					name="mensajesProcesos.recuperable" id="recuperable" headerKey="" headerValue="No seleccionado" listKey="nombreEnum" listValue="nombreEnum" />		
			</td>		
		</tr>
	</table>
	<table class="acciones" width="95%" align="left">
		<tr>
			<td align="center" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
				<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Guardar Mensaje" onClick="return guardarMensajesWebService();"
						onKeyPress="this.onClick" />
				&nbsp;&nbsp;&nbsp; 
				<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver" onClick="javascript:document.location.href='inicioConsultaMensajesWebService.action';"
					onKeyPress="this.onClick" />
			</td>
		</tr>
		<tr>
			<td>
				<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
			</td>
		</tr>	
	</table>
	</div>
</s:form>