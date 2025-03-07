<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
<script src="js/trafico/materiales/gestionStockMateriales.js" type="text/javascript"></script>


<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Agregar Materiales</span>
				</td>
			</tr>
		</table>
	</div>
</div>

	
<div id="displayTagDivStockMateriales">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<table>
			<tr>
				<td>
					<label id="lblUnidadesRecarga">Unidades</label> 
				</td>
				<td>
					<label id="lblTipoMaterial">Tipo de Material</label>
				</td>
				<td>
					<label id="lblJefatura">Jefatura</label>
				</td>
			</tr>
			<tr>
				<td>
					<s:textfield name="stockMaterialBean.unidades" type="text" id="unidades" maxLength="9" onkeypress='return validaNumericos(event)'/>
				</td>
				<td  align="left">
					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaStockMateriales@getInstance().getTipoMateriales()" onblur="this.className='input2';" 
			    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo" 
			    		name="stockMaterialBean.tipoMaterial" listKey="valorEnum" listValue="nombreEnum" id="idTipoMaterial"/>
			    </td>
			    <td>
				    <s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaStockMateriales@getInstance().getJefaturasJPTEnum()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Jefatura" 
				    		name="stockMaterialBean.jefatura" listKey="jefatura" listValue="descripcion" id="idTipoJefatura"/>
			    </td>
			</tr>
		</table>
</div>
