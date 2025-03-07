<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>
<script  type="text/javascript"></script>
<s:hidden id="textoLegal" name="propTexto"/>

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Alta de marca de vehículo</span>
				</td>
			</tr>
		</table>
	</div>

	<s:form method="post" id="formData" name="formData">
		<div id="busqueda">
			<table class="tablaformbasica" border="0">

				<tr><td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td><td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td></tr>

				<tr>
					<td nowrap="nowrap" colspan="2" style="vertical-align: middle;" align="left">
						<label for="tiposTramite">En caso de no existir Fabricante deje el campo en blanco. NO rellene con ND.</label>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap" style="vertical-align: middle;" width="30px">
						<label for="marca">Marca:</label>
					</td>

					<td align="left">
						<s:textfield name="marcaDgt.marca"
							id="marca" size="100" maxlength="100"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" />
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap" style="vertical-align: middle;" width="30px">
						<label for="fabricante">Fabricante:</label>
					</td>

					<td align="left">
						<s:textfield name="marcaDgt.fabricante"
							id="idFabricante" size="100" maxlength="100"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" />
					</td>
				</tr>

				<tr><td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td><td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td></tr>

				<tr>
					<td nowrap="nowrap" colspan="2" style="vertical-align: middle;" align="left">
						<label for="tiposTramite">Indique los tipos de trámite desde los que debe ser accesible la nueva marca:</label>
					</td>
				</tr>

				<tr><td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td><td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td></tr>

				<tr>
					<td align="left">
						<s:checkbox name="marcaDgt.mate" id="checkMate"/>
					</td>
					<td style="vertical-align: middle;" align="left">
						<label for="mate">Transmisi&oacute;n (CTIT)</label>
					</td>
				</tr>
				<tr>
					<td align="left">
						<s:checkbox name="marcaDgt.matw" id="checkMatw"/>
					</td>
					<td style="vertical-align: middle;" align="left">
						<label for="mate">Matriculaci&oacute;n (MATW)</label>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td><td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
				</tr>
			</table>

			<table class="acciones">
				<tr>
					<td>
						<input type="button"
							class="boton"
							name="bGuardarMarcaDgt"
							id="idBotonGuardarMarcaDgt"
							value="Guardar"
							onClick="return altaMarcaDgt();"
							onKeyPress="this.onClick"/>
						&nbsp;
						<input type="button"
							class="boton"
							name="bLimpiar"
							id="idBotonLimpiarMarcaDgt"
							value="Limpiar"
							onkeypress="this.onClick"
							onclick="return limpiarFormularioAltaMarcaDgt();" />
					</td>
				</tr>
			</table>
		</div>

	<div id="bloqueLoadingGuardarMarcaDgt" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>

</s:form>