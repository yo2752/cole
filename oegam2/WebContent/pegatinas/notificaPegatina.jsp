<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
	<head>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
	</head>

	<script src="js/tabs.js" type="text/javascript"></script>
	<script src="js/contrato.js" type="text/javascript"></script>
	<script src="js/validaciones.js" type="text/javascript"></script>
	<script src="js/genericas.js" type="text/javascript"></script>
	<script src="js/pegatinasBotones.js" type="text/javascript"></script>

	<body class="popup">

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">NOTIFICACIÓN PEGATINA NO VÁLIDA</span>
				</td>
			</tr>
		</table>
	</div>

	<s:form  method="post" id="formData" name="formData">

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="tipo">Tipo:</label>
					<s:select 
						list="@org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.UtilesPegatinas@getTipoPegatinasPedirStock()"
						id="tipo"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="pegatinasNotificaBean.tipo"
						listValue="nombreEnum"
						listKey="nombreEnum"
						title="Tipo"
						disabled="false"/>
						<br/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="motivo">Motivo:<span class="naranja">*</span></label>

					<s:select 
						list="@org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.UtilesPegatinas@getMotivosInvalidos()"
						id="motivo"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						name="pegatinasNotificaBean.motivo"
						title="Motivo"
						disabled="false"/>
						<br/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="identificadorPegatina">Identificador Pegatina:<span class="naranja">*</span></label>
					<s:textfield id="identificadorPegatina" name="pegatinasNotificaBean.identificadorPegatina"  onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="20" maxlength="30"/>
					<br/>
				</td>
			</tr>
		</table>

		<table class="acciones">
			<tr>
				<td align="left">
					<div>
						<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar" onClick="return cerrar();" onKeyPress="this.onClick" />
						<input type="button" class="boton" name="bNotificar" id="bNotificar" value="Notificar" onkeypress="this.onClick" onclick="return notificar(this);"/>
					</div>
				</td>
			</tr>
		</table>

	</s:form>

	</body>
</html>