<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>
<s:set var="idSancion" value=""></s:set>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Alta Sanción
			</span></td>
		</tr>
	</table>
</div>

<%@include file="../../includes/erroresMasMensajes.jspf" %>
<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

<s:hidden name="sancionDto.idSancion" />
<s:hidden name="sancionDto.estadoSancion" />
<s:hidden name="sancionDto.fechaAlta.dia"/>
<s:hidden name="sancionDto.fechaAlta.mes"/>
<s:hidden name="sancionDto.fechaAlta.anio"/>
<s:hidden name="sancionDto.fechaAlta.hora"/>
<s:hidden name="sancionDto.fechaAlta.minutos"/>
<s:hidden name="sancionDto.fechaAlta.segundos"/>
<s:hidden name="sancionDto.numColegiado" />

	<div id="formulario">	
		<table class="tablaformbasica" cellSpacing="2" class="acciones" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="nombre">Nombre: </label>
				</td>
				<td>
					<s:textfield id="sancionDto.nombre" name="sancionDto.nombre" label="Nombre" size="25"  maxlength="25"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="apellidosRazonSocial">Apellidos / Razón Social: </label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:textfield id="sancionDto.apellidos" name="sancionDto.apellidos" label="Apellidos" size="40"  maxlength="50"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="nifCif">NIF / CIF: </label>
				</td>
				<td>
					<s:textfield id="sancionDto.dni" name="sancionDto.dni" label="NIF / CIF" size="10"  maxlength="10"/>
				</td>
				<td>
					<input class="boton-persona" type="button" 
						onclick="buscarIntervinienteSancion()" 
						style="background-image: url(img/mostrar.gif);height:20px;width:20px;">
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="motivo">Motivo: </label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
				<s:select list="@org.icogam.sanciones.Utiles.UtilesVistaSancion@getInstance().getMotivos()"
									listKey="valorEnum" listValue="nombreEnum" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" headerKey="" headerValue="Todos" name="sancionDto.motivo"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="fechaPresentacion">Fecha Presentación: </label>
				</td>
				
				<td>						
					<table style="width:40%">
						<tr>
							<td>
								<s:textfield name="sancionDto.fechaPresentacion.dia" 
									id="diaSancion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="2" maxlength="2"/>
							</td>
							<td>/</td>	
							<td>
								<s:textfield name="sancionDto.fechaPresentacion.mes" 
									id="mesSancion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="2" maxlength="2"/>
							</td>	
							<td>/</td>
							<td>
								<s:textfield name="sancionDto.fechaPresentacion.anio" 
									id="anioSancion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="5" maxlength="4"/>
							</td>
							<td>
				   				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioSancion, document.formData.mesSancion, document.formData.diaSancion);return false;"
				   					title="Seleccionar fecha">
				    				<img class="PopcalTrigger" 
				    					align="left" 
				    					src="img/ico_calendario.gif" 
				    					width="15" height="16" 
				    					border="0" alt="Calendario"/>
				   				</a>
			   				</td>
			   			</tr>
			   		</table>
			   	</td>
			</tr>
		</table>
		
		<table align="center">
			<tr>
				<td><s:submit value="Guardar Sanción" action="guardarAltaSancionNuevo" cssClass="botonMasGrande"/></td>
				<td><input type="button" class="boton" value="Limpiar campos" id="bLimpiar" onclick="limpiarFormulario(this.form.id);"/></td>
			</tr>
		</table>	
	</div>

<br>

<b><font size="2">(Quedan excluidas de la presentación sanciones de Ayuntamientos, Cataluña y País Vasco)</font></b>
	
<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>	
</s:form>
<script>
	function buscarIntervinienteSancion(){
		document.formData.action = "getDetallePersonaAltaSancionNuevo";
		document.formData.submit();
	}
</script>
