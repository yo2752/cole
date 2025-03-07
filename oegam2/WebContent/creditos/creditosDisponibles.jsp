<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/creditos.js" type="text/javascript"></script>

<div class="nav">
	<table cellSpacing="0" cellPadding="5" width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Resumen créditos</span></td>
		</tr>
	</table>
</div>

<%@include file="../../includes/erroresMasMensajes.jspf"%>

<s:form id="formData" name="formData" action="buscarCreditosDisponiblesResumenCargaCredito.action">
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
		<div id="busqueda">
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
				<tr>
					<td align="left" style="width:10%; vertical-align:middle" nowrap="nowrap">
						<label for="num_Colegiado">Colegiado:</label>
					</td>
					<td align="left" >
					<s:select id="idcontratoColegiado" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
						headerKey="" headerValue="" listKey="codigo" listValue="descripcion" name="idContratoCreditosDisponibles"></s:select>
					</td>
				</tr>
			</table>
		</div>
	</s:if>

	<h3 style="text-align:left; margin-left:1em;">Créditos según el tipo</h3>	
	<display:table name="listaCreditosDisponiblesBean" excludedParams="*" style="width:100%"
		class="tablacoin" summary="Listado de Resumen" cellspacing="0" cellpadding="0" uid="listaCreditosTable">
			<display:column property="descripcionTipoCredito" title="Tipo crédito" defaultorder="descending" />
			<display:column property="creditos" title="Créditos" defaultorder="descending" />

			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()|| @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">			
				<display:column property="creditosDisponibles" title="Créditos disponibles"
				defaultorder="descending"
				style="width:10%" />

				<display:column property="creditosBloqueados" title="Créditos bloqueados"
				defaultorder="descending"
				style="width:10%" />
			</s:if>
	</display:table>

	<h3 style="text-align:left; margin-left:1em; left: ">Créditos acumulados en el mes actual</h3>
	<display:table name="listaCreditosAcumulados" excludedParams="*"
		class="tablacoin" summary="Listado de Resumen" style="width:100%"
		cellspacing="0" cellpadding="0" uid="listaCreditosTable">

			<display:column title="Tipo crédito" defaultorder="descending" >
				<s:property value="#attr.listaCreditosTable.credito.tipoCreditoDto.tipoCredito"/> - <s:property value="#attr.listaCreditosTable.credito.tipoCreditoDto.descripcion"/>
			</display:column>

			<display:column property="cantidadSumada" title="Créditos" defaultorder="descending" />
	</display:table>

	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()|| @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">		
		<div id="botonBusqueda">
			<table width="100%" align="center">
				<tr align="center">
					<td>
					<s:submit value="Buscar" cssClass="boton"/>
					<input type="button" class="boton" 	name="bLimpiar" id="bLimpiar" value="Limpiar"
						onkeypress="this.onClick" onclick="return limpiarFormConsultaCreditosDisponibles();"/>
					</td>
				</tr>
			</table>
		</div>
	</s:if>	
</s:form>