<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">B&uacute;squeda de sociedades</span>
				</td>
			</tr>
		</table>
	</div>


	<div id="busqueda">
		<table border="0" class="tablaformbasicaTC">
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="cif">CIF:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="consultaSociedadesBean.cifSociedad" id="cif" onblur="this.className='input2';this.value=this.value.toUpperCase();" onfocus="this.className='inputfocus';" size="10" maxlength="9" onkeyup="this.value=this.value.toUpperCase()" />
				</td>
				<td align="right" nowrap="nowrap">
					<label for="denominacionSocial">Denominaci&oacute;n social:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="4">
					<s:textfield name="consultaSociedadesBean.denominacionSocial" id="denominacionSocial" onkeyup="this.value=this.value.toUpperCase()" 
					onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="35" maxlength="50" cssStyle="text-align:left;" />
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="ius">IUS:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="consultaSociedadesBean.ius" id="ius" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="19" maxlength="15" onkeypress="return validarNumerosEnteros(event)"/>
				</td>
				<td align="right" nowrap="nowrap">
					<label for="seccion">Secci&oacute;n:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="consultaSociedadesBean.seccion" id="seccion" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="3" maxlength="3" onkeypress="return validarNumerosEnteros(event)" />
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="hoja">Hoja:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="consultaSociedadesBean.hoja" id="hoja" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="6" maxlength="6" onkeypress="return validarNumerosEnteros(event)" />
				</td>
				<td align="right" nowrap="nowrap">
					<label for="hojaBis">Hoja bis:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select label="Hoja bis:" name="consultaSociedadesBean.hojaBis" id="hojaBisBuscar" list="#{'':'-', 'SI':'SI', 'NO':'NO'}" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" required="true" />
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="tipoPersona">Subtipo:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select label="Tipo:" name="consultaSociedadesBean.subtipo" id="tipoPersonaBuscar" list="#{'':'-', 'PUBLICA':'Jurídica pública', 'PRIVADA':'Jurídica privada'}"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" required="true" />
				</td>
				<td align="right" nowrap="nowrap">
					<label for="email">Correo Electr&oacute;nico:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="4">
					<s:textfield name="consultaSociedadesBean.correoElectronico" id="email" onkeyup="this.value=this.value.toUpperCase()" onblur="this.value=this.value.toUpperCase(); this.className='input2';" onfocus="this.className='inputfocus';" size="35" maxlength="57" />
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
		</table>
		
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="javascript:buscarSociedad();"/>			
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" value="Limpiar campos" onclick="limpiarCamposConsultaSociedad();"/>		
				</td>
			</tr>
		</table>
	
	
	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>
	
	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	</div>
	
	<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
							</td>
							<td width="10%" align="right">
								<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									id="idResultadosPorPagina" value="resultadosPorPagina"
									title="Resultados por página" onchange="cambiarElementosPorPaginaConsulta('navegarSociedad.action', 'displayTagDiv', this.value);" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
	
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>
	
	<div id="displayTagDiv" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" requestURI="navegarSociedad.action"
			class="tablacoin" uid="row" summary="Listado de Sociedades" cellspacing="0" cellpadding="0">

			<display:column property="id.nif" title="CIF" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" />

			<display:column title="Razón social" sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:10%;text-align:left;">
				<a href="javascript:seleccionarSociedad('<s:property value='#attr.row.id.nif'/>');" >
					<s:property value='#attr.row.apellido1RazonSocial'/>
				</a>
			</display:column>

			<display:column property="ius" title="IUS" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%;text-align:left;" />

			<display:column property="seccion" title="Sección" sortable="true" headerClass="sortable" defaultorder="descending" style="width:3%;text-align:left;" />

			<display:column property="hoja" title="Hoja" sortable="true" headerClass="sortable" defaultorder="descending" style="width:3%;text-align:left;" />

			<display:column property="hojaBis" title="Bis" sortable="true" headerClass="sortable" defaultorder="descending" style="width:3%" />

			<display:column property="subtipo" title="Jurídica" sortable="true" headerClass="sortable" defaultorder="descending" style="width:3%" />

		</display:table>
	</div>
	<div id="bloqueLoadingConsultarReg" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
	
</div>

