<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Remesa 600</td>
		</tr>
	</table>
	<%@include file="../../../../includes/erroresMasMensajes.jspf" %>
	
	<div id="divListadoBienesUrbanos">
		<jsp:include page="../bien/urbano/listBienesUrbanos.jsp" flush="false"></jsp:include>
	</div>
	<div id="divListadoBienesRusticos">
		<jsp:include page="../bien/rustico/listBienesRusticos.jsp" flush="false"></jsp:include>
	</div>
	<div id="divListadoOtrosBienes">
		<jsp:include page="../bien/otro/listOtrosBienes.jsp" flush="false"></jsp:include>
	</div>
	<div id="divAltaModiBienes">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Alta/Modificación de Bienes</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" width="150px">
					<label for="labeltipoBien">Tipo de Bien<span class="naranja">*</span>:</label>
				</td>
				<td align="left">
					<s:select  name="remesa.tipoBien"  id="idTipoBien"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getTipoBien()" 
			    		headerKey="" headerValue="Seleccione Tipo Bien" 
			    		onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" 
						listKey="valorEnum" listValue="nombreEnum" onchange="javascript:habilitarTipoBien(this)"/>
				</td>
			</tr>
			<tr></tr>
		</table>
	</div>
	<div id="divBienUrbano">
		<jsp:include page="../bien/urbano/bienUrbano.jsp" flush="false"></jsp:include>
	</div>
	<div id="divBienRustico">
		<jsp:include page="../bien/rustico/bienRustico.jsp" flush="false"></jsp:include>
	</div>
	<div id="divOtroBien">
		<jsp:include page="../bien/otro/otroBien.jsp" flush="false"></jsp:include>
	</div>
	<br/>
	<br/>
	<div class="acciones center">
		<input type="button" class="boton" name="bLimpiarBien" id="idLimpiarBien" 
		  	value="Limpiar Bien" onClick="javascript:limpiarBien();"onKeyPress="this.onClick"/>	
	</div>
</div>
<div id = "divEmergenteBienes" style="position: fixed; z-index: 999; height: 100%; width: 100%; top: 0; left:0;
 							background-color: #f4f4f4; filter: alpha(opacity=60); opacity: 0.6; -moz-opacity: 0.8;display:none">
</div>

<script type="text/javascript">
	habilitarTipoBien();
</script>