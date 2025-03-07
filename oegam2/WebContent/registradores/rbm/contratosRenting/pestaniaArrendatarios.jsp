<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="contenido">	
	
	<%@include file="../contratosLeasingRenting/arrendatario.jsp"%>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Representantes Arrendatarios</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirRepresentanteRBM('cargarPopUpRepresentanteArrendatarioRegistroRenting.action','Arrendatario');" id="btnAniadirRepresentanteArrendatarioRenting" class="button corporeo"value="Añadir representante" />
				</s:if>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<jsp:include page="representantesArrendatariosList.jsp" flush="true" />
			</td>
		</tr>
	</table>
	&nbsp;
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Arrendatarios</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="arrendatariosList.jsp" />
        	</td>
        </tr>
	</table>
        
</div>
  