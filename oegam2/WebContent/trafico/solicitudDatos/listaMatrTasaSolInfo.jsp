<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/trafico/solicitudes.js" type="text/javascript"></script>
<script src="js/trafico/tasas.js" type="text/javascript"></script>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">

	<div id="busqueda">
	
	<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align:left;">Modificación de matrículas y/o bastidores en trámites de solicitud de información</td>
			</tr>
		</table>

		<table class="tablaformbasica" border="0">

			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;" width="140px">
					<label for="numExpediente">Número de expediente :</label>
				</td>

				<td align="left"><s:textfield name="numExpediente"
						id="numExpediente" size="16" maxlength="15"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"/></td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			</tr>
			
			<s:if test="%{listaBeansPantalla.size()>0}">
			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
				<td nowrap="nowrap" style="vertical-align: middle;" align="left">
					<div class="divScroll">
						<display:table name="listaBeansPantalla" excludedParams="*"
							class="tablacoin" uid="tablaClavesPublicas"
							summary="Contenido del almacén de claves públicas"
							cellspacing="0" cellpadding="0" 
							decorator="trafico.decorators.MatriculasSolInfoDecorator">
			
							<display:column property="matricula" title="MATRÍCULA"
								style="width:25%;text-align:center;vertical-align:middle;" />
			
							<display:column property="bastidor" title="BASTIDOR"
								maxLength="20" style="width:25%;text-align:center;vertical-align: middle;" />
			
							<display:column property="tasa.codigoTasa" title="TASA"
								style="width:25%;text-align:center;vertical-align:middle;" />
								
							<display:column property="modificar" title=""
								style="width:25%;text-align:center;vertical-align:middle;" />
								
						</display:table>
					</div>
				</td>
			</tr>			
			<tr>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
				<td nowrap="nowrap" style="vertical-align: middle;">&nbsp;</td>
			</tr>
			</s:if>

		</table>

		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar"
					id="bBuscar" value="Buscar"
					onClick="return getMatrSolInfo('bBuscar');"
					onKeyPress="this.onClick" />
			</tr>
		</table>



	</div>

	<div id="bloqueLoadingMatrSolDatos"
		style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
		<%@include file="../../includes/bloqueLoading.jspf"%>
	</div>
	
	
	<s:hidden name="anuntis" id="anuntis"/>
	<s:hidden name="solInfo" id="solInfo"/>
	
	<s:hidden name="identificador" id="identificador"/>
	<s:hidden name="matriculaNew" id="matriculaNew"/>
	<s:hidden name="bastidorNew" id="bastidorNew"/>

</s:form>


