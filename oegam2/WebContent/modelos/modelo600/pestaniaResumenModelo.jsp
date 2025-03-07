<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen del Modelo 600</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS GENERALES</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="labelRNumExpediente">Número de Expediente: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{modeloDto.numExpediente}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRestado">Estado: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{@org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos@convertirTexto(modeloDto.estado)}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRConcepto">Concepto: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{modeloDto.concepto.descConcepto}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRAbreviatura">Abreviatura: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{modeloDto.concepto.concepto}"/>
			</td>
		</tr>
	</table>
	<s:if test="%{modeloDto.notario != null}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DEL NOTARIO</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="15%">
					<label for="labelRNombreNt">Nombre y Apellidos: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{modeloDto.notario.nombre}"/>
					<s:label value="%{modeloDto.notario.apellidos}"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="15%">
					<label for="labelRCodigoNt">Codigo:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{modeloDto.notario.codigoNotario}"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" width="15%">
					<label for="labelRNotaria">Notaria:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{modeloDto.notario.codigoNotaria}"/>
				</td>
			</tr>
		</table>
	</s:if>
	<s:if test="%{modeloDto.sujetoPasivo != null}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE LOS SUJETOS PASIVOS</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="15%">
					<label for="labelRNombreSjP">Nombre y Apellidos: </label>
				</td>
				<td align="left" nowrap="nowrap" width="55%">
					<s:property value="%{modeloDto.sujetoPasivo.persona.nombre}"/>
					<s:property value="%{modeloDto.sujetoPasivo.persona.apellido1RazonSocial}"/>
					<s:property value="%{modeloDto.sujetoPasivo.persona.apellido2}"/>
				</td>
				<td align="left" nowrap="nowrap" width="5%">
					<label for="labelRNifNt">DNI:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:property value="%{modeloDto.sujetoPasivo.persona.nif}"/>
				</td>
			</tr>
		</table>
	</s:if>
	<s:if test="%{modeloDto.transmitente != null}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE LOS TRANSMITENTES</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="15%">
					<label for="labelRNombreTr">Nombre y Apellidos: </label>
				</td>
				<td align="left" nowrap="nowrap"  width="55%">
					<s:property value="%{modeloDto.transmitente.persona.nombre}"/>
					<s:property value="%{modeloDto.transmitente.persona.apellido1RazonSocial}"/>
					<s:property value="%{modeloDto.transmitente.persona.apellido2}"/>
				</td>
				<td align="left" nowrap="nowrap" width="5%">
					<label for="labelRNifTr">DNI:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:property value="%{modeloDto.transmitente.persona.nif}"/>
				</td>
			</tr>
		</table>
	</s:if>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()|| @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DEL PRESENTADOR</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="15%">
					<label for="labelRNombreP">Nombre y Apellidos: </label>
				</td>
				<td align="left" nowrap="nowrap" width="55%">
					<s:property value="%{modeloDto.presentador.persona.nombre}"/>
					<s:property value="%{modeloDto.presentador.persona.apellido1RazonSocial}"/>
					<s:property value="%{modeloDto.presentador.persona.apellido2}"/>
				</td>
				<td align="left" nowrap="nowrap" width="5%">
					<label for="labelRNifP">DNI:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:property value="%{modeloDto.presentador.persona.nif}"/>
				</td>
			</tr>
		</table>
	</s:if>
	<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esConceptoBienes(modeloDto)}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE LOS BIENES</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" width="10%">
					<label for="labelNumBienes">Número de Bienes: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{modeloDto.numBienes}"/>
				</td>
			</tr>
		</table>
		<s:if test="%{modeloDto.listaBienesUrbanos != null && modeloDto.listaBienesUrbanos.size()>0}">
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<s:iterator value="%{modeloDto.listaBienesUrbanos}">
					<tr>
						<td align="left" nowrap="nowrap" width="8%">
							<label for="labelTipoBienUrb">Tipo Bien: </label>
						</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:label for="LabelDescTipoBien">Urbano</s:label>
						</td>
						<td align="left" nowrap="nowrap" width="5%">
							<label for="labelTipoBienUrb">Nombre: </label>
						</td>
						<td align="left" nowrap="nowrap"width="5%">
							<s:property value="%{bien.nombreBien}"/>
						</td>
						<td align="left" nowrap="nowrap" width="10%">
							<label for="labelValDeclarado">Valor Declarado: </label>
						</td>
						<td align="left" nowrap="nowrap"width="5%">
							<s:property value="%{valorDeclarado}"/>
						</td>
						<td align="left" nowrap="nowrap" width="10%">
							<label for="labelTransmision">Porctj.Transmisión: </label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:property value="%{transmision}"/>
						</td>
					</tr>
				</s:iterator>
			</table>
		</s:if>
		<s:if test="%{modeloDto.listaBienesRusticos != null && modeloDto.listaBienesRusticos.size()>0}">
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<s:iterator value="%{modeloDto.listaBienesRusticos}">
					<tr>
						<td align="left" nowrap="nowrap" width="8%">
							<label for="labelTipoBienRus">Tipo Bien: </label>
						</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:label for="LabelDescTipoBienRus">Rustico</s:label>
						</td>
						<td align="left" nowrap="nowrap" width="5%">
							<label for="labelNombreBienRus">Nombre: </label>
						</td>
						<td align="left" nowrap="nowrap" width="5%">
							<s:property value="%{bien.nombreBien}"/>
						</td>
						<td align="left" nowrap="nowrap" width="10%">
							<label for="labelValDeclaradoRus">Valor Declarado: </label>
						</td>
						<td align="left" nowrap="nowrap"width="5%">
							<s:property value="%{valorDeclarado}"/>
						</td>
						<td align="left" nowrap="nowrap" width="10%">
							<label for="labelTransmisionRus">Porctj.Transmisión: </label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:property value="%{transmision}"/>
						</td>
					</tr>
				</s:iterator>
			</table>
		</s:if>
	</s:if>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">ESCRITURA</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"  width="10%"><label>Fichero: </label></td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{modeloDto.nombreEscritura}"/>
			</td>
		</tr>
	</table>
	<div style="visibility: hidden;">
		<display:table name="modeloDto.listaResultadoModelo" class="tablacoin"
				uid="tablaResultadoModelo" 
				id="tablaResultadoModelo" summary="" decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorResultadoModelo600601"
				excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
		<display:column property="idResultado" title="" media="none" paramId="idResultado"/>

		<display:column property="codigoResultado" title="" media="none"/>

		<display:column style="width:4%">
			<s:if test="#attr.tablaResultadoModelo.codigoResultado.equals('000')">
				<table align="center" >
					<tr>
						<td style="border: 0px;">
							<input type="checkbox" name="listaChecksResultados" id="check<s:property value="#attr.tablaResultadoModelo.idResultado"/>"
								value='<s:property value="#attr.tablaResultadoModelo.idResultado"/>' checked="checked"/>
						</td>
					</tr>
				</table>
			</s:if>
		</display:column>
		</display:table>
	</div>
	<br>
	<div class="acciones center">
		<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().esFinalizadoOk(modeloDto)}">
			<input type="button" class="boton" name="bImprimirCartaPago" id="idImprimirCartaPago" value="Imprimir Carta Pago" onClick="javascript:imprimirDocModelo('1');" onKeyPress="this.onClick"/>
			<input type="button" class="boton" name="bImprimirDiligencia" id="idImprimirDiligencia" value="Imprimir Diligencia" onClick="javascript:imprimirDocModelo('2');" onKeyPress="this.onClick"/>
			<input type="button" class="boton" name="bImprimirEscritura" id="idImprimirEscritura" value="Escritura presentada" onClick="javascript:imprimirEscritura();" onKeyPress="this.onClick"/>
		</s:if>
	</div>
	<br>
</div>