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
					<span class="titulo">Recarga de Materiales</span>
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
					<label id="lblUnidadesRecarga">Unidades a recargar</label> 
				</td>
				<td>
					<s:textfield name="stockMaterialBean.unidades" type="text" id="unidades" maxLength="9" onkeypress='return validaNumericos(event)'/>
				</td>
			</tr>
		</table>
</div>
