<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script type="text/javascript"></script>


<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>

<h1 class="titulo">Consulta de estadísticas de placas de matriculación</h1>

<!-- INICIO VALIDACIÓN DE ERRORES -->
			
<%@include file="../../includes/erroresMasMensajes.jspf"%>

<!-- FIN VALIDACIÓN DE ERRORES -->

<div class="formularios">

	<!-- INICIO FORMULARIO DE FILTROS -->

	<s:form method="post" id="formData" name="formData">
	<div id="tablaEstadisticas">
	<s:if test="%{#isAdmin==true}">
	<fieldset id="fsFiltros" style="width:96.8%;margin-left:0.3em">
		<table style="width: 100%">
			<tr>
			<td>
 			<ul>
				<li class="label"><label for="fechaInicioDay">Fecha de
						Inicio:</label> <label for="fechaInicioMonth"></label> <label
					for="fechaInicioYear"></label></li>
				<li class="field"><span class="fechas"><s:textfield
							name="estadisticasPlacasBean.fecha.diaInicio" id="estadisticasFechaInicioDay"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" /></span>
					<span class="barraSep">&nbsp;/&nbsp;</span> <span class="fechas"><s:textfield
							name="estadisticasPlacasBean.fecha.mesInicio"
							id="estadisticasFechaInicioMonth" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" /></span>
					<span class="barraSep">&nbsp;/&nbsp;</span> <span class="fechas"><s:textfield
							name="estadisticasPlacasBean.fecha.anioInicio"
							id="estadisticasFechaInicioYear" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="4" maxlength="4" /></span>
					<span class="barraSep">&nbsp;&nbsp;</span> <span> <a
						href="javascript:;"
						top=100
						onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.estadisticasFechaInicioYear, document.formData.estadisticasFechaInicioMonth, document.formData.estadisticasFechaInicioDay);return false;"
						title="Seleccionar fecha"> <img class="PopcalTrigger"
							align="left" src="img/ico_calendario.gif" width="15" height="16"
							border="0" alt="Calendario" />
					</a>
				</span></li>

				<li class="label"><label for="fechaFinDay">Fecha de Fin:</label>
					<label for="fechaFinMonth"></label> <label for="fechaFinYear"></label>
				</li>
				<li class="field"><span class="fechas"><s:textfield
							name="estadisticasPlacasBean.fecha.diaFin" id="estadisticasFechaFinDay"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" /></span>
					<span class="barraSep">&nbsp;/&nbsp;</span> <span class="fechas"><s:textfield
							name="estadisticasPlacasBean.fecha.mesFin" id="estadisticasFechaFinMonth"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" /></span>
					<span class="barraSep">&nbsp;/&nbsp;</span> <span class="fechas"><s:textfield
							name="estadisticasPlacasBean.fecha.anioFin" id="estadisticasFechaFinYear"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="4" maxlength="4" /></span>
					<span class="barraSep">&nbsp;&nbsp;</span> <span> <a
						href="javascript:;"
						onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.estadisticasFechaFinYear, document.formData.estadisticasFechaFinMonth, document.formData.estadisticasFechaFinDay);return false;"
						title="Seleccionar fecha"> <img class="PopcalTrigger"
							align="left" src="img/ico_calendario.gif" width="15" height="16"
							border="0" alt="Calendario" />
					</a>
				</span></li>
				</ul>
				</td>
			</tr>
				</table>
			</fieldset>	
			<fieldset style="width:96.8%;margin-left:0.3em">
			<table>
			<tr>
				<td>
				<s:checkbox name="estadisticasPlacasBean.agrTipoVehiculo" 
						id="idAgrTipoVehiculo"></s:checkbox>
				</td>
				<td align="left" nowrap="nowrap">
	   				<label for="tipoVehiculo">Agrupar por tipo de vehículo</label>
   				</td>		
			</tr>
			
			<tr>
				<td>
				<s:checkbox  name="estadisticasPlacasBean.agrTipoCredito" 
						id="idAgrTipoCredito"></s:checkbox>
				</td>
				<td align="left" nowrap="nowrap">
	   				<label for="tipoCredito">Agrupar por tipo de crédito</label>
   				</td>		
			</tr>
			<tr>
				<td>
				<s:checkbox  name="estadisticasPlacasBean.numColegiado" 
						id="idAgrNumColegiado"></s:checkbox>
				</td>
				<td align="left" nowrap="nowrap">
	   				<label for="tipoCredito">Agrupar por colegiado</label>
   				</td>
			</tr>
			</table>
			</fieldset>
	<br/>
		<div style="text-align:center">
			<input type="button" value="Generar estadísticas" class="boton" onClick="generarEstadisticas()" />
		</div>
	</s:if>	
	</div>
	
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>
		
	</s:form>
</div>
<script type="text/javascript">

	function generarEstadisticas() {

		var formulario = document.getElementById('formData');
		formulario.action = 'estadisticasSolicitudPlacas.action';
		formulario.submit();
	}
</script>