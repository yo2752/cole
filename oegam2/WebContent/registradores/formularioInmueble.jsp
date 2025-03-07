<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

	<s:hidden name="tramiteRegistro.inmueble.bien.fechaAlta" />
	<s:hidden name="tramiteRegistro.inmueble.idInmueble"></s:hidden>
			
	<div id="divBienUrbano">
		<jsp:include page="bienUrbano.jsp" flush="false"></jsp:include>
	</div>

	<div id="divBienRustico">
		<jsp:include page="bienRustico.jsp" flush="false"></jsp:include>
	</div>
	<div id="divOtroBien">
		<jsp:include page="otroBien.jsp" flush="false"></jsp:include>
	</div>
	
<script>
	$(document).ready(function() {
		mostrarDivsTipoBien();
	});

</script>
	                                                                                                   