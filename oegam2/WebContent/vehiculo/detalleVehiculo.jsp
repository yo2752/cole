<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/576.js" type="text/javascript"></script>
<script src="js/pagosEImpuestos.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Detalle Del Vehículo</span></td>
		</tr>
	</table>
</div>

<!-- Pestañas -->

<ol id="toc">
	<li><a href="#Vehiculo">Vehículo</a></li>
	<li><a href="#Expedientes">Expedientes</a></li>
</ol>

<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
	<s:hidden id="idDireccionVehiculo" name = "vehiculoDto.direccion.idDireccion"/>
	<s:hidden id="idVehiculoPrever" name = "vehiculoDto.idVehiculoPrever."/>
	<s:hidden id="idVehiculo" name = "vehiculoDto.idVehiculo"/>
	<s:hidden id="numColegiadoVehiculo" name = "vehiculoDto.numColegiado"/>

	<s:hidden id="idEstado" name ="estado"/>

	<s:hidden name="vehiculoDto.fechaItv.hora"></s:hidden>
	<s:hidden name="vehiculoDto.fechaItv.minutos"></s:hidden>
	<s:hidden name="vehiculoDto.fechaItv.segundos"></s:hidden>
	<s:hidden name="vehiculoDto.fechaInspeccion.hora"></s:hidden>
	<s:hidden name="vehiculoDto.fechaInspeccion.minutos"></s:hidden>
	<s:hidden name="vehiculoDto.fechaInspeccion.segundos"></s:hidden>
	<s:hidden name="vehiculoDto.fechaMatriculacion.hora"></s:hidden>
	<s:hidden name="vehiculoDto.fechaMatriculacion.minutos"></s:hidden>
	<s:hidden name="vehiculoDto.fechaMatriculacion.segundos"></s:hidden>

	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<s:if test="%{vehiculoDto.activo == 0}">
		<%@include file="../../vehiculo/detalleVehiculoMatriculacionDes.jsp" %>
	</s:if>
	<s:else>
		<%@include file="../../vehiculo/detalleVehiculoMatriculacion.jsp" %>
	</s:else>

	<%@include file="../../vehiculo/detalleExpedientes.jsp" %>

	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>
</s:form>
