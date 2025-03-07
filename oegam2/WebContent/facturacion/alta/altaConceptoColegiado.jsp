<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/facturacionBotones.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script  type="text/javascript"></script>

<s:form method="post" id="formData" name="formData" action="GestionFacturar">

	<table class="tablaformbasica" id="mitablaconceptocolegiado" cellSpacing="0" cellspacing="0" align="left" border="0">
			<tr>    							
				<td align="center" style="width: 30%">			       	
			       	<p style="font-size:1.2 em; color: #000000; align:center"><label>Número Colegiado:</label></p>
			    </td>
			    <td align="center" style="width: 30%">
			    	<p style="font-size:1.2 em; color: #000000; align:center"><label>Identificador Concepto:</label></p> 
			    </td>
			    <td align="center" style="width: 30%">
			       	<p style="font-size:1.2 em; color: #000000; align:center"><label>Nombre Concepto:</label></p>
			    </td>		    		    			    		    
			    <td align="center" style="width: 10%">
			       	<label></label> 
			    </td>
		  	</tr>
		  	<tr> <td> &nbsp;</td> </tr>
		  	<tr>
		  		<td>
		  			<input type="text" id="idNumColegiado" disabled="disabled" name="idNumColegiado" class="inputfocus">
		  		</td>
		  		<td>
		  			<input type="text" id="idColegiadoConcepto" disabled="disabled" name="idColegiadoConcepto" class="inputfocus">
		  		</td>
		  		<td>
		  			<input type="text" id="nombreColegiadoConcepto" name="nombreColegiadoConcepto">
		  		</td>
		  	</tr>
		 </table>
		&nbsp;
	 	<table align="center">
			<tr>
				<td>
					<input class="boton" type="button" id="bGuardar" name="bGuardar" value="Guardar" onClick="saveConcepto();"/> 
				</td>
				<td>
				 	<input class="boton" type="button" id="bCancelar" name="bCancelar" value="Cancelar" onClick="muestraOcultaTabla();"/> 
				</td>
			</tr>
		</table>

</s:form >
