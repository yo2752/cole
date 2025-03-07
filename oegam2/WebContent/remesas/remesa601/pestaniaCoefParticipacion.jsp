<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Remesa 601</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">PORCENTAJES DE PARTICIPACIÓN DE BIENES PARA SUJETOS PASIVOS</span>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td>
				<display:table name="remesa.listaPartSujPasivo" class="tablacoin"
					uid="tablaPartSujetosPasivos" 
					id="tablaPartSujetosPasivos" summary=""
					excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
				
					<display:column property="idParticipacion" title="" media="none" paramId="idParticipacion"/>
				
					<display:column property="bien.nombreBien" title="Nombre Bien" style="width:4%" />
							
					<display:column property="intervinienteModelos.persona.nif" title="NIF" style="width:4%" />
							
					<display:column property="intervinienteModelos.persona.apellido1RazonSocial" title="1er Apellido/Razón Social" style="width:4%" />
						
					<display:column property="intervinienteModelos.persona.apellido2" title="2do Apellido" style="width:4%" />
							
					<display:column property="intervinienteModelos.persona.nombre" title="Nombre" style="width:8%" />
						
					<display:column property="porcentaje" title="Porcentaje" style="width:4%"  />
												
					<display:column title="Modificar Porcentaje" style="width:2%;" >
						<input type="text" name="porctjaSujetoPasivo"	id="<s:property value="#attr.tablaPartSujetosPasivos.idParticipacion"/>" size="6" maxlength="6"
							onblur="this.className='input';" onfocus="this.className='inputfocus';" onkeypress="return validarNumeros(event)"/>
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">PORCENTAJES DE PARTICIPACIÓN DE BIENES PARA TRANSMITENTES</span>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td>
				<display:table name="remesa.listaPartTransmitente" class="tablacoin"
					uid="tablaPartTransmitente" 
					id="tablaPartTransmitente" summary=""
					excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
					
					<display:column property="idParticipacion" title="" media="none" paramId="idParticipacion"/>
					
					<display:column property="bien.nombreBien" title="Nombre Bien" style="width:4%" />
							
					<display:column property="intervinienteModelos.persona.nif" title="NIF" style="width:4%" />
							
					<display:column property="intervinienteModelos.persona.apellido1RazonSocial" title="1er Apellido/Razón Social" style="width:4%" />
						
					<display:column property="intervinienteModelos.persona.apellido2" title="2do Apellido" style="width:4%" />
							
					<display:column property="intervinienteModelos.persona.nombre" title="Nombre" style="width:8%" />
						
					<display:column property="porcentaje" title="Porcentaje" style="width:4%"  />
												
					<display:column title="Modificar Porcentaje" style="width:4%" >
						<input type="text" name="partTransmitente" id="<s:property value="#attr.tablaPartTransmitente.idParticipacion"/>" size="6" maxlength="6"
							onblur="this.className='input';" onfocus="this.className='inputfocus';" onkeypress="return validarNumeros(event)"/>
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
	<div id="bloqueBotonesCoefPart" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;" align="center">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
	<div class="acciones center">
		<input type="button" class="boton" name="bLimpiarCoefPart" id="idLimpiarCoefPart" value="Limpiar Coef.Part" onClick="javascript:limpiarCoefPart();"onKeyPress="this.onClick"/>	
	</div>
</div>
