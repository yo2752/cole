<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="contenido">	

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Cl&aacute;usulas</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	    <tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirClausula('Renting','formData', 'cargarPopUpClausulaRegistroRenting.action');" id="btnAniadirClausulaRenting" class="button corporeo" value="Añadir cláusula" />
				</s:if>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<%@include file="clausulasList.jsp" %>
			</td>
		</tr>
	</table>
	
	<%@include file="../contratosRecursos/avalista.jsp"%>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Representantes del avalista</span>
			</td>
		</tr>
	</table>	
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	    <tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirRepresentanteRBM('cargarPopUpRepresentanteAvalistaRegistroRenting.action','Avalista');" id="btnAniadirRepresentanteAvalistaRenting" class="button corporeo" value="Añadir representante avalista" />
				</s:if>
			</td>
		</tr>
	</table>  
 	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="representantesAvalistaList.jsp" />
        	</td>
        </tr>
	</table>
	&nbsp;
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Avalistas</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<%@include file="avalistasList.jsp" %>
        	</td>
        </tr>
	</table>

</div>
