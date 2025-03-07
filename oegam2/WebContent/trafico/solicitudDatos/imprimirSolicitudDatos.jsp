<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
 
<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Impresos de solicitud de datos</td>
			</tr>
		</table>	


		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
	    	<tr><td width="2%"></td><td></td></tr>
					
			<s:if test="@trafico.utiles.UtilesVistaTrafico@getInstance().nuevoInteve()">
	    		<tr>
	      			<td align="left">
	        			<input type="radio" name="impreso" id="SolicitudAvpo" value="SolicitudAvpo" checked="checked" onchange="return cambiarRadioImprimir();"/>
	      			</td>
	     			<td align="left" style="vertical-align: middle;">
	     			   	<label for="SolicitudAVPO">Informe consulta INTEVE</label>
	     			 </td>
	    		</tr>
			</s:if>
			<s:else>
			   	<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().isSeridorInformesTecnicosAVPO()}">
		    		<tr>
		      			<td align="left">
		        			<input type="radio" name="impreso" id="SolicitudAvpo" value="SolicitudAvpo" checked="checked" onchange="return cambiarRadioImprimir();"/>
		      			</td>
		     			<td align="left" style="vertical-align: middle;">
		     			   	<label for="SolicitudAVPO">Informe consulta AVPO</label>
		     			 </td>
		    		</tr>
		    	</s:if>
		    	<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().isSeridorInformesTecnicosINTEVE() ||
								@trafico.utiles.UtilesVistaTrafico@getInstance().sesionUsuarioPruebasInteve() == 'SI'}">
						<tr>
		      			<td align="left">
		        			<input type="radio" name="impreso" id="SolicitudAvpo" value="SolicitudAvpo" checked="checked" onchange="return cambiarRadioImprimir();"/>
		      			</td>
		     			<td align="left" style="vertical-align: middle;">
		     			   	<label for="SolicitudAVPO">Informe consulta INTEVE</label>
		     			 </td>
		    		</tr>		
				</s:if>
			</s:else>
	    	<s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().isSeridorInformesTecnicosATEM() ||
							@trafico.utiles.UtilesVistaTrafico@getInstance().sesionUsuarioPruebasServidorAtem() == 'SI'}">
				<tr>
	      			<td align="left">
	        			<input type="radio" name="impreso" id="SolicitudAvpo" value="SolicitudATEM" checked="checked" onchange="return cambiarRadioImprimir();"/>
	      			</td>
	     			<td align="left" style="vertical-align: middle;">
	     			   	<label for="SolicitudAVPO">Informe consulta ATEM</label>
	     			 </td>
	    		</tr>		
			</s:if>
    		<tr>
      			<td align="left">
        			<input type="radio" name="impreso" id="MandatoGenerico" value="MandatoGenerico" onchange="return cambiarRadioImprimir();"/>
      			</td>
     			<td align="left" style="vertical-align: middle;">
     			   	<label for="Mandato">Mandato Genérico</label>
     			 </td>
    		</tr>
    		    		<tr>
      			<td align="left" >
        			<input type="radio" name="impreso" id="MandatoEspecifico" value="MandatoEspecifico" onchange="return cambiarRadioImprimir();"/>
      			</td>
     			<td align="left" style="vertical-align: middle;">
     			   	<label for="Mandato">Mandato Específico</label>
     			 </td>
    		</tr>
		</table>
	
