<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<s:hidden name="tramiteRegRbmDto.propiedadDto.idPropiedad" id="idPropiedad"/>
<s:hidden name="tramiteRegRbmDto.propiedadDto.idTramiteRegistro" />
<s:hidden name="tramiteRegRbmDto.propiedadDto.fecCreacion" />		

<div class="contenido">
   	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos bien cancelado</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
         <tr>
           <td align="left" nowrap="nowrap">
           <label for="propiedadDtoCategoria">Categor&iacute;a<span class="naranja">*</span></label>
           </td>
           <td colspan="3">  <s:select name="tramiteRegRbmDto.propiedadDto.categoria" 
                      list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCategory()" 
                      listKey="key"
                      headerKey="" 
                      headerValue="Selecionar categoría"
                      id="propiedadDtoCategoria"
                      onchange="borrarBienCancelacion();"/>
			</td>
         </tr>
    </table>
    
    <div id= "divVehiculo" style="display:none;">
    	<%@include file="../contratosRecursos/vehiculo.jsp"%>
    </div>
    <div id= "divMaquinaria" style="display:none;">
    	<%@include file="../contratosRecursos/maquinaria.jsp"%>
    </div>
    <div id= "divEstablecimiento" style="display:none;">
    	<%@include file="../contratosRecursos/establecimiento.jsp"%>
    </div>
        <div id= "divBuque" style="display:none;">
    	<%@include file="../contratosRecursos/buque.jsp"%>
    </div>
    <div id= "divAeronave" style="display:none;">
    	<%@include file="../contratosRecursos/aeronave.jsp"%>
    </div>
    <div id= "divPropiedadIndustrial" style="display:none;">
    	<%@include file="../contratosRecursos/propiedadIndustrial.jsp"%>
    </div>
    <div id= "divPropiedadIntelectual" style="display:none;">
    	<%@include file="../contratosRecursos/propiedadIntelectual.jsp"%>
    </div>
     <div id= "divOtrosBienes" style="display:none;">
    	<%@include file="../contratosRecursos/otrosBienes.jsp"%>
    </div>
	
 </div>
 
 
 <script type="text/javascript">
$( document ).ready(function() {
	
/* 	var estado =$('#idEstado').val();
	if(estado == 1 || solicitanteCancelacion == 28){
		$("#propiedadDtoCategoria").attr('disabled', false);
	}else{
		$("#propiedadDtoCategoria").attr('disabled', true);
	} */

	$('#propiedadDtoCategoria').val("01");
	$("#propiedadDtoCategoria").attr('disabled', true);
	
});
</script>
