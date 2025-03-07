<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/justificantes.js" type="text/javascript"></script>
<script src="js/trafico/solicitudes.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>

<script type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Verificación de Justificantes Profesionales de Matrícula</span>
			</td>
		</tr>
	</table>
</div>

<s:form method="post" id="formData" name="formData">

	<div id="busqueda">
		<table cellSpacing="2" class="tablaformbasica" cellPadding="0">
			<tr>

				<td align="left" nowrap="nowrap">
					<label for="csv">Código Seguro de Verificación:</label>
				</td>

				<td align="left">
					<s:textfield name="codigoSeguroVerif"
						id="csv"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						maxlength="100"
						cssStyle="width:45em;" />
				</td>
			</tr>
		</table>
		<%@include file="../../includes/erroresYMensajes.jspf" %>

		<table class="acciones">
			<tr>
				<td>
					<input type="button"
						class="boton"
						name="bBuscar"
						value="Buscar"
						onkeypress="this.onClick"
						onclick="buscarVerificacionJustificante(this);"/>
				</td>
				<td>
					<input type="button"
						class="boton"
						name="bVerificar"
						value="Verificar"
						onkeypress="this.onClick"
						onclick="verificarJustificante(this);"/>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<img id="loadingImage"
						style="display:none;margin-left:auto;margin-right:auto;"
						src="img/loading.gif">
				</td>
			</tr>
		</table>
	</div>

</s:form>