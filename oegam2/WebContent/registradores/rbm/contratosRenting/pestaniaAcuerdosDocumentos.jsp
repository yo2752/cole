<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="contenido">

	<%@include file="../contratosRecursos/acuerdo.jsp"%>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	    <tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:doPostValidate('formData', 'aniadirAcuerdoRenting.action#tab7', '', '');" id="btnAniadirAcuerdoRenting" class="button corporeo" value="Añadir acuerdo" />
				</s:if>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<jsp:include page="acuerdosList.jsp"  />
			</td>
		</tr>
	</table>

	
	<jsp:include page="../../resumen/listaFicherosRBM.jsp" flush="true"/>

</div>
