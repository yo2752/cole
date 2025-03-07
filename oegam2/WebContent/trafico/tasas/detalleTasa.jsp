<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<script type="text/javascript">	
	function desasignar(){
		if(document.getElementById("fechaAsignacion").value==null || document.getElementById("fechaAsignacion").value==""){
			alert("No se puede desasignar una tasa no asignada");
			return false;
		}
		if (confirm("¿Está seguro de que desea desasignar esta tasa?")){
			document.forms[0].action="desasignarConsultaTasa.action?idCodigoTasaSeleccion="+document.getElementById("codigo").value;
			document.forms[0].submit();
			return true;
		}else{
			return false;
		}
	}
	
	function cancelar(){
		document.forms[0].action="navegar${action}";
		document.forms[0].submit();
		return false;
	}
</script>
<div class="nav">
    <table cellspacing="0" cellpadding="5" width="100%">
		<tr>
           <td class="tabular"><span class="titulo">Detalle de la tasa</span></td>
        </tr>
    </table>
</div>
<s:form method="post">
<table cellSpacing=3 class="tablaformbasica" cellPadding="0">

	<tr>
		<td align="left">
    	        <label for="codigo">C&oacute;digo</label>
				<s:textfield name="detalle.codigoTasa" id="codigo" cssClass="inputview" size="12" readonly="true"/>
  		</td>
  		<td align="left">
    		<label for="tipo">Tipo</label>
			<s:textfield name="detalle.tipoTasa" id="tipo" cssClass="inputview" size="5" readonly="true"/>
  		</td>
	</tr>

	<tr>
   	    <td align="left">
   	        <label for="fechaAlta">Fecha alta</label>
			<s:textfield value="%{@org.gestoresmadrid.utilidades.components.UtilesFecha@formatoFecha(detalle.fechaAlta)}" id="fechaAlta" cssClass="inputview" readonly="true"/>
	    </td>
		<td align="left">
    		<label for="importe">Importe (&euro;)</label>
			<s:textfield value="%{detalle.precio}" id="precio" cssClass="inputview" readonly="true"/>
  		</td>
	</tr>

	<tr>
	    <td align="left">
			<label for="numExpediente">N&ordm; Expediente</label>
			<s:textfield name="detalle.numExpediente" id="numExpediente" cssClass="inputview" size="30" readonly="true"/>
	  	</td>
	  	<td align="left">
	        <label for="refPropia">Referencia Propia</label>
	    	<s:textfield name="detalle.refPropia" id="refPropia" cssClass="inputview" size="40" readonly="true"/>
	  	</td>
	  	<td  align="left">
	    	<label for="contratoColegiado">Vía Contrato</label>
			<s:textfield name="detalle.contrato.via" id="via" cssClass="inputview" size="30" readonly="true"/>
	    </td>
	</tr>

	<tr>
		<td  align="left">
	    	<label for="fechaAsignacion">Fecha asignaci&oacute;n</label>
			<s:textfield value="%{@org.gestoresmadrid.utilidades.components.UtilesFecha@formatoFecha(detalle.fechaAsignacion)}" id="fechaAsignacion" cssClass="inputview" readonly="true"/>
	    </td>
	    <td  align="left">
	    	<label for="contratoColegiado">Contrato</label>
			<s:textfield name="detalle.contrato.colegiadoDto.numColegiado" id="numColegiado" cssClass="inputview" size="30" readonly="true"/>
	    </td>
	    <td  align="left">
	    	<label for="contratoColegiado">Provincia Contrato</label>
			<s:textfield name="detalle.contrato.provinciaDto.nombre" id="nombre" cssClass="inputview" size="30" readonly="true"/>
	    </td>
	    
	</tr>

</table>

<table class="acciones">
	<tr>
		<td>
			<!-- GTMS2010_BOTÓN_VOLVER NUEVO BLOQUE CONDICIONAL -->
			<s:if test="%{detalle.numExpediente != null}">
				&nbsp;<input class="boton" type="button" id="bDesasignar" name="bDesasignar" value="Desasignar" onClick="desasignar();return false;" onKeyPress="this.onClick" />
			</s:if>
	        &nbsp;<input class="boton" type="button" name="bVolver" id="bVolver" value="Volver" onClick="cancelar();return false;" />
		</td>
	</tr>
</table>
<!-- Para controlar a la pagina que vuelve después de desasignar -->
<s:hidden name="detalle" value="true"/>

</s:form>
<%@include file="../../includes/erroresYMensajes.jspf" %>