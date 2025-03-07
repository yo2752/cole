<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div align="center">
<!-- //TODO MPC. Cambio IVTM. Esto es añadido. 
-->

<s:if test="hasActionErrors() || hasActionMessages()">
  	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td>Resumen de la Autoliquidación:</td>
			<td><img alt="Imprimir" src="img/impresora.png" style="height: 23px; cursor:pointer;" onclick="return imprimirId('estadisticasValidacion',false,null);" /></td>
		</tr>
	</table>
	
	<table style="width:720px;font-size:11px;" class="tablacoin" summary="Resumen de la validación" border="0" cellpadding="0" cellspacing="0" style="margin-top:0px;">
   		<tr>
   			<th>&nbsp;</th>
   			<th>Autoliquidados IVTM</th>
   			<th>Fallidos</th>
		</tr>
		
		<tr>
  			  <s:if test="%{resumenTotalIVTM.tipoTramite.equals('TOTAL')}">
  			  	<td style="font-weight:bold;"><s:property value="resumenTotalIVTM.tipoTramite"/></td>
  			  </s:if>
  			  
   			  <s:else>
   			  	<td><s:property value="resumenTotalIVTM.tipoTramite"/></td>
   			  </s:else>

   			  
   			  <s:if test="%{resumenTotalIVTM.numAutoLiquidados != 0}">
   			  	<td style="color:green;font-weight:bold;"><s:property value="resumenTotalIVTM.numAutoLiquidados"/>   			  
   			  		<s:if test="%{resumenTotalIVTM.tipoTramite.equals('TOTAL')}">
						<img id="imgAutoliquidados"  style="cursor:pointer;" alt="Mostrar" src="img/plus.gif" onclick="return mostrarBloqueAutoliquidados();" />
	   			 	</s:if>
   			  
   			  	</td>
   			  </s:if>
	   		  <s:else>
	   		  	<td style="color:green;"><s:property value="resumenTotalIVTM.numAutoLiquidados"/></td>
	   		  </s:else>
	   			  
	   			  
  			  <s:if test="%{resumenTotalIVTM.numAutoliquidadosFallidos != 0}">
  			  	<td style="color:red;font-weight:bold;"><s:property value="resumenTotalIVTM.numAutoliquidadosFallidos "/>
  			    	<s:if test="%{resumenTotalIVTM.tipoTramite.equals('TOTAL')}">
  						<img id="imgFallidosAutoliquidados" alt="Mostrar" style="cursor:pointer;" src="img/plus.gif" onclick="return mostrarBloqueFallidosAutoliquidados();" />
	   				</s:if>
    			</td>
    		  </s:if>
    		  <s:else>
    		  	<td style="color:red;"><s:property value="resumenTotalIVTM.numAutoliquidadosFallidos "/></td>
    		  </s:else>
     		       		  
		</tr>
		
 	</table>
 </s:if>
 		
 		
<div id="bloqueAutoliquidados" style="display:none;width:720px;font-size:11px;">
	<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
	<tr>
		<td  style="width:100%;text-align:center;">Detalle de Trámites </td>
	</tr>
	</table>						
		<ul style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;text-align: left;">
			<s:iterator value="actionMessages">
				<li><span style="text-align: left;"><s:property /></span></li>	
			</s:iterator>
		</ul>		
</div>		



<div id="bloqueFallidosAutoliquidadosIVTM" style="display:none;width:720px;font-size:11px;">
	<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
		<tr>
			<td  style="width:100%;text-align:center;">Detalle de los errores</td>
		</tr>
	</table>	
		<s:if test="actionErrors">
			<ul style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;text-align: left;">
			<s:iterator value="actionErrors">
				<li><span><s:property /></span></li>
				
			</s:iterator>
		</ul>
		</s:if>	
</div> 		
 		
</div> 	

	