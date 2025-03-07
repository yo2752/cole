<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>


<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen de la Remesa 600</td>
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
       			<s:label value="%{remesa.numExpediente}"/>
       		</td>	
		</tr>
		<tr>        	       			
			<td align="left" nowrap="nowrap">
	   			<label for="labelRestado">Estado: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{@org.gestoresmadrid.core.modelos.model.enumerados.EstadoRemesas@convertirTexto(remesa.estado)}"/>
   			</td>	
       	</tr>
		<tr>
			<td align="left" nowrap="nowrap">
	   			<label for="labelRConcepto">Concepto: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{remesa.concepto.descConcepto}"/>
   			</td>
   			<td width="20"></td>
   			<td align="left" nowrap="nowrap">
	   			<label for="labelRAbreviatura">Abreviatura: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{remesa.concepto.concepto}"/>
   			</td>
		</tr>
	</table>
	<s:if test="%{remesa.notario != null}">
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
	   				<s:label value="%{remesa.notario.nombre}"/>
	   				<s:label value="%{remesa.notario.apellidos}"/>
	   			</td>
	   			<td width="10"></td>
	   			<td align="left" nowrap="nowrap" width="8%">
	   				<label for="labelRCodigoNt">Codigo:</label>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	   				<s:label value="%{remesa.notario.codigoNotario}"/>
	   			</td>
	   			<td width="10"></td>
	   			<td align="left" nowrap="nowrap" width="8%">
	   				<label for="labelRNotaria">Notaria:</label>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	   				<s:label value="%{remesa.notario.codigoNotaria}"/>
	   			</td>
			</tr>
		</table>
	</s:if>
	<s:if test="%{remesa.listaSujetosPasivos != null && remesa.listaSujetosPasivos.size()>0}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE LOS SUJETOS PASIVOS</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<s:iterator value="%{remesa.listaSujetosPasivos}">
				<tr>	
					<td align="left" nowrap="nowrap" width="15%">
						<label for="labelRNombreSjP">Nombre y Apellidos: </label>
			   		</td>
		         	<td align="left" nowrap="nowrap" width="55%">
		   				<s:property value="%{persona.nombre}"/>
		   				<s:property value="%{persona.apellido1RazonSocial}"/>
		   				<s:property value="%{persona.apellido2}"/>
		   			</td>
		   			<td align="left" nowrap="nowrap" width="5%">
		   				<label for="labelRNifNt">DNI:</label>
		   			</td>
		   			<td align="left" nowrap="nowrap">
		   				<s:property value="%{persona.nif}"/>
		   			</td>
		   		</tr>
			</s:iterator>
		</table>
	</s:if>
	<s:if test="%{remesa.listaTransmitentes != null && remesa.listaTransmitentes.size()>0}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">DATOS DE LOS TRANSMITENTES</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<s:iterator value="%{remesa.listaTransmitentes}">
				<tr>
					<td align="left" nowrap="nowrap" width="15%">
						<label for="labelRNombreTr">Nombre y Apellidos: </label>
			   		</td>
		         	<td align="left" nowrap="nowrap" width="55%">
		   				<s:property value="%{persona.nombre}"/>
		   				<s:property value="%{persona.apellido1RazonSocial}"/>
		   				<s:property value="%{persona.apellido2}"/>
		   			</td>
		   			<td align="left" nowrap="nowrap" width="5%">
		   				<label for="labelRNifTr">DNI:</label>
		   			</td>
		   			<td align="left" nowrap="nowrap">
		   				<s:property value="%{persona.nif}"/>
		   			</td>
		   		</tr>
			</s:iterator>
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
	   				<s:property value="%{remesa.presentador.persona.nombre}"/>
	   				<s:property value="%{remesa.presentador.persona.apellido1RazonSocial}"/>
	   				<s:property value="%{remesa.presentador.persona.apellido2}"/>
	   			</td>
	   			<td align="left" nowrap="nowrap" width="5%">
	   				<label for="labelRNifP">DNI:</label>
	   			</td>
	   			<td align="left" nowrap="nowrap">
	   				<s:property value="%{remesa.presentador.persona.nif}"/>
	   			</td>
		   	</tr>
		</table>
	</s:if>
	<s:if test="%{@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().esConceptoBienes(remesa)}">
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
			   		<s:label value="%{remesa.numBienes}"/>
			   	</td>
			</tr>
		</table>
		<s:if test="%{remesa.listaBienesUrbanos != null && remesa.listaBienesUrbanos.size()>0}">
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<s:iterator value="%{remesa.listaBienesUrbanos}">
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
		<s:if test="%{remesa.listaBienesRusticos != null && remesa.listaBienesRusticos.size()>0}">
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<s:iterator value="%{remesa.listaBienesRusticos}">
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
</div>
	
	