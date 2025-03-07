<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/legalizacion/legalizacionBotones.js" type="text/javascript"></script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo"> Citas Legalización Listado Diario </span></td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresMasMensajes.jspf"%>
<s:form method="post" id="formData" name="formData">
	<div>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="center">
			<tr>
				<td align="left" style="vertical-align: middle;"><label for="diaMatrIni">Fecha de
						legalización: </label></td>

				<td align="left">
					<table style="width:20%">
						<tr>
							<td>
				
								<s:textfield
										name="fechaListado.dia"
										id="diaFechaListado" align="left"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2"
										maxlength="2" /></td>
								<td style="vertical-align: middle;">/</td>
								<td><s:textfield
										name="fechaListado.mes"
										id="mesFechaListado" align="left"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="2"
										maxlength="2" /></td>
								<td style="vertical-align: middle;">/</td>
								<td><s:textfield
										name="fechaListado.anio"
										id="anioFechaListado" align="left"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false" size="5"
										maxlength="4" /></td>
								<td style="vertical-align: middle;"><a href="javascript:;"
									onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaListado, document.formData.mesFechaListado, document.formData.diaFechaListado);return false;"
									title="Seleccionar fecha"> <img class="PopcalTrigger"
										align="left" src="img/ico_calendario.gif" width="15" height="16"
										border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
				<s:if test="%{#isAdmin==true}">
					<td align="left" style="vertical-align: middle;">
						<label>Núm. Colegiado: </label>
					</td>
					<td align="left">
						<s:textfield  id="numColegiado" name ="numColegiado" maxlength="4" size="7"/>
					</td>
				</s:if>
				<s:else>
					<td style="width:60%"></td>
					<input type="hidden" id="numColegiado" name="numColegiado" value="<s:property value="numColegiado"/>" />
				</s:else>
			</tr>
		</table>
		<br>
		<table align="center">
			<tr>
				<td>
					<input type="button" class="boton"
						name="blistadoLegal" id="idBListadoLegal"
						value="Listado" onkeypress="this.onClick"
						onclick="javascript:obtenerListado();" /></td>
				
			</tr>
		</table>
		
	</div>
	
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>
</s:form>