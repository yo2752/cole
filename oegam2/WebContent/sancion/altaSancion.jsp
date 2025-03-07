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
				Alta sanción
			</span></td>
		</tr>
	</table>
</div>

<%@include file="../../includes/erroresMasMensajes.jspf" %>
<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
<s:hidden name="sancionDto.idSancion" />
<s:hidden name="sancionDto.estadoSancion" />
	<div id="formulario">	
		<table class="tablaformbasica" cellSpacing="2" class="acciones" cellPadding="0">
			<tr>
				<td>
					<s:text name="Nombre"></s:text>
				</td>
				<td>
					<s:textfield id="sancionDto.nombre" name="sancionDto.nombre" label="Nombre" size="30px"  />
				</td>
				<td>
					<s:text name="Apellidos / Razón Social"></s:text>
				</td>
				<td>
					<s:textfield id="sancionDto.apellidos" name="sancionDto.apellidos" label="Apellidos" size="30px"  />
				</td>
				<td>
					<s:text name="NIF / CIF"></s:text>
				</td>
				<td>
					<s:textfield id="sancionDto.dni" name="sancionDto.dni" label="NIF / CIF" size="20px"  maxlength="10"/>
				</td>
				<td>
					<input class="boton-persona" type="button" 
						onclick="buscarIntervinienteSancion()" 
						style="background-image: url(img/mostrar.gif);height:20px;width:20px;">
				</td>
			</tr>
		</table>					
		<table class="tablaformbasica" cellSpacing="2" class="acciones" cellPadding="0" align="center">
			<tr>
				<td>
					<s:text name="Motivo"></s:text>
				</td>
				<td>
				<s:select list="@org.icogam.sanciones.Utiles.UtilesVistaSancion@getInstance().getMotivos()"
									listKey="valorEnum" listValue="nombreEnum" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" headerKey="-1" headerValue="Todos" name="sancionDto.motivo"/>
				</td>
				<td>
					<label for="diaMatrIni">Fecha Presentación: </label>
				</td>
				
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
		
		<table align="center">
			<tr>
				<td><s:submit value="Guardar Sanción" action="guardarAltaSancion" cssClass="botonMasGrande"/></td>
				<td><s:submit value="Limpiar campos" action="limpiarCamposAltaSancion" cssClass="botonMasGrande"/></td>
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
		document.formData.action = "getDetallePersonaAltaSancion";
		document.formData.submit();
	}
</script>
