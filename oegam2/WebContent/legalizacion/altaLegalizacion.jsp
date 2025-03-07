<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}" ></s:set>
<s:set var="idPeticion" value=""></s:set>
<s:set var="isMinisterio" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMinisterio()}"></s:set>

<s:if test="%{#isMinisterio==false && #isAdmin==false && !legDto.fechaLegalizacion.equals('')}">
	
	<script type="text/javascript">
		alert('Recuerde que debe de entregar la documentación el día antes de la cita de legalizacion');
	</script>
</s:if>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Alta cita legalizacion documentación
			</span></td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresMasMensajes.jspf" %>
<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
<s:hidden name="legDto.idPeticion" />
	<div id="formulario">					
		<table cellSpacing="2" class="acciones" cellPadding="0">
			<s:if test="%{#isAdmin==true && legDto.solicitado==true}">
				<tr>
					<td>
						<s:property value="Se ha solicitado la documentacion por parte del Ministerio" />
					</td>
				</tr>
			</s:if>
			<tr>
				<td>
						<label for="diaMatrIni">Cita legalización: </label>
					</td>
					
					<td>
						<s:textfield name="legDto.fechaLegalizacion.dia" 
							id="diaFinLegalizacion"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							readonly="false" 
							size="2" maxlength="2"/>
					</td>
					<td>/</td>	
					<td>
						<s:textfield name="legDto.fechaLegalizacion.mes" 
							id="mesFinLegalizacion"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							readonly="false" 
							size="2" maxlength="2"/>
					</td>	
					<td>/</td>
					<td>
						<s:textfield name="legDto.fechaLegalizacion.anio" 
							id="anioFinLegalizacion"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							readonly="false" 
							size="5" maxlength="4"/>
					</td>
					<td>
    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinLegalizacion, document.formData.mesFinLegalizacion, document.formData.diaFinLegalizacion);return false;"
    					title="Seleccionar fecha">
	    				<img class="PopcalTrigger" 
	    					align="left" 
	    					src="img/ico_calendario.gif" 
	    					width="15" height="16" 
	    					border="0" alt="Calendario"/>
    				</a>
    				</td>
		
				<td>
					<label for="diaMatrIni">Fecha Documentación: </label>
				</td>
				
				<td>
					<s:textfield name="legDto.fechaDocumentacion.dia" 
						id="diaDocumentacion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						readonly="false" 
						size="2" maxlength="2"/>
				</td>
				<td>/</td>	
				<td>
					<s:textfield name="legDto.fechaDocumentacion.mes" 
						id="mesDocumentacion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						readonly="false" 
						size="2" maxlength="2"/>
				</td>	
				<td>/</td>
				<td>
					<s:textfield name="legDto.fechaDocumentacion.anio" 
						id="anioDocumentacion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						readonly="false" 
						size="5" maxlength="4"/>
				</td>
				<td>
   				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioDocumentacion, document.formData.mesDocumentacion, document.formData.diaDocumentacion);return false;"
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
		
		<table cellSpacing="2" class="acciones" cellPadding="0">
			<tr>
				<td>
					<s:text name="Nombre\Apellido"></s:text>
				</td>
				<td>
					<s:textfield id="legDto.nombre" name="legDto.nombre" label="Nombre " size="60px"  />
				</td>
				<td>
					<s:text name="Pais"></s:text>
				</td>
				<td>
					<s:textfield id="legDto.pais" name="legDto.pais" label="Pais " size="20px"  />
				</td>
				<s:if test="%{legDto.ficheroAdjunto==true}">
				<td>
					<a href="descargarAltaLegalizacion.action?legDto.idPeticion=<s:property value='legDto.idPeticion'/>">Descargar Documentacion</a>
				</td>
				</s:if>
			</tr>
			<tr>
				<td>
					<s:text name="Tipo Documento"></s:text>
				</td>
				<td>
				<s:select list="@org.icogam.legalizacion.utiles.UtilesVistaLegalizacion@getTiposDocumentos()"
									listKey="valorEnum"
									listValue="nombreEnum"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey="-1"
			           				headerValue="Todos"
									name="legDto.TipoDocumento"/>
				</td>
				<td>
					<s:text name="Clase Documento"></s:text>
				</td>
				<td>
				<s:select list="@org.icogam.legalizacion.utiles.UtilesVistaLegalizacion@getClaseDocumento()"
									listKey="valorEnum"
									listValue="nombreEnum"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey="-1"
			           				headerValue="Todos"
									name="legDto.ClaseDocumento"/>
				</td>
				
			</tr>
			<s:if test="%{#isAdmin==true}">
			<tr>
				<td>
					<s:text name="Subir Documentacion"></s:text>
				</td>
				<td>
					<s:file id="fichero" size="50" name="fileUpload" value="fichero"
					 onblur="this.className='input2';" onfocus="this.className='inputfocus';">
					</s:file>
				</td>
				<td>
					<s:text name="Num Colegiado"></s:text>
				</td>
				<td>
					<s:textfield id="legDto.numColegiado" name="legDto.numColegiado" label="Numero colegiado" size="20px" maxlength="4"  />
				</td>
			</tr>
			</s:if>
			<s:if test="%{#isMinisterio==false}">
				<tr>
					<td>
						<s:text name="Orden de aparición en el listado"></s:text>
					</td>
				
					<td>
						<s:textfield id="legDto.orden" name="legDto.orden" label="Orden " size="1px"  />
					</td>
				</tr>
			</s:if>	
		</table>	
		<s:if test="%{#isAdmin==true || #isMinisterio==false}">
			<table align="center">
				<tr>
					<td><s:submit value="Guardar Petición" action="guardarAltaLegalizacion" cssClass="botonMasGrande"/></td>
					<td><s:submit value="Limpiar campos" action="limpiarCamposAltaLegalizacion" cssClass="botonMasGrande"/></td>
				</tr>
				
			</table>
		</s:if>
	</div>
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
