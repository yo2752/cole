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
		alert('Recuerde que debe de entregar la documentación el día antes de la cita de legalización');
	</script>
</s:if>

<div class="contenido">
	<table class="subtitulo">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Alta cita legalización documentación</td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresMasMensajes.jspf" %>
<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">

<s:hidden name="legDto.idPeticion" />
<s:hidden name="legDto.estado" />
<s:hidden name="legDto.estadoPeticion" />
<s:hidden name="legDto.numColegiado" />
<s:hidden name="legDto.ficheroAdjunto" />

<s:hidden name="legDto.fecha.dia"/>
<s:hidden name="legDto.fecha.mes"/>
<s:hidden name="legDto.fecha.anio"/>
<s:hidden name="legDto.fecha.hora"/>
<s:hidden name="legDto.fecha.minutos"/>
<s:hidden name="legDto.fecha.segundos"/>

	<div id="formulario">
		<table cellPadding="1" cellSpacing="3"  cellpadding="1" border="0" class="tablaformbasica" width="100%">					
			<s:if test="%{#isAdmin==true && legDto.solicitado==true}">
				<tr>
					<td>
						<s:property value="Se ha solicitado la documentacion por parte del Ministerio" />
					</td>
				</tr>
			</s:if>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="diaMatrIni">Cita Legalización: </label>
				</td>
				
				<td>
					<table style="width:20%">
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="legDto.fechaLegalizacion.dia" 
									id="diaFinLegalizacion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="2" maxlength="2"/>
							</td>
							<td align="left" nowrap="nowrap" style="vertical-align: middle;">/</td>	
							<td align="left" nowrap="nowrap">
								<s:textfield name="legDto.fechaLegalizacion.mes" 
									id="mesFinLegalizacion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="2" maxlength="2"/>
							</td>	
							<td align="left" nowrap="nowrap" style="vertical-align: middle;">/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="legDto.fechaLegalizacion.anio" 
									id="anioFinLegalizacion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="5" maxlength="4"/>
							</td>
							<td align="left" nowrap="nowrap">
			    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinLegalizacion, document.formData.mesFinLegalizacion, document.formData.diaFinLegalizacion);return false;"
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
		
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="diaMatrIni">Fecha Documentación: </label>
				</td>
				
				<td>
					<table style="width:20%">
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="legDto.fechaDocumentacion.dia" 
									id="diaDocumentacion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="2" maxlength="2"/>
							</td>
							<td align="left" nowrap="nowrap" style="vertical-align: middle;">/</td>	
							<td align="left" nowrap="nowrap">
								<s:textfield name="legDto.fechaDocumentacion.mes" 
									id="mesDocumentacion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="2" maxlength="2"/>
							</td>	
							<td align="left" nowrap="nowrap" style="vertical-align: middle;">/</td>
							<td align="left" nowrap="nowrap">
								<s:textfield name="legDto.fechaDocumentacion.anio" 
									id="anioDocumentacion"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									readonly="false" 
									size="5" maxlength="4"/>
							</td>
							<td align="left" nowrap="nowrap">
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
			   	</td>
			</tr>
		
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="nombreApellido">Nombre\Apellido: </label>
				</td>
				<td align="left" nowrap="nowrap" colspan="2">
					<s:textfield id="legDto.nombre" name="legDto.nombre" label="Nombre" size="60" maxlength="150" />
				</td>
				
				<s:if test="%{legDto.ficheroAdjunto==true}">
					<td>
						<a href="descargarAltaLegalizacionNuevo.action?legDto.idPeticion=<s:property value='legDto.idPeticion'/>">Descargar Documentacion</a>
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;"> 
					<label for="tipoDocumento">Tipo Documento: </label>
				</td>
				<td align="left" nowrap="nowrap" >
				<s:select list="@org.gestoresmadrid.oegamLegalizaciones.view.utiles.UtilesVistaLegalizacion@getTiposDocumentos()"
									listKey="valorEnum"
									listValue="nombreEnum"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue="Todos"
									name="legDto.TipoDocumento"/>
				</td>
				
				<td align="left" nowrap="nowrap" style="vertical-align: middle;"> 
					<label for="claseDocumento">Clase Documento: </label>
				</td>
				<td align="left" nowrap="nowrap" >
				<s:select list="@org.gestoresmadrid.oegamLegalizaciones.view.utiles.UtilesVistaLegalizacion@getClaseDocumento()"
									listKey="valorEnum"
									listValue="nombreEnum"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue="Todos"
									name="legDto.ClaseDocumento"/>
				</td>
			</tr>
			
			<s:if test="%{#isAdmin==true}">
				<tr>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="subirDocumentacion">Subir Documentación: </label>
					</td>
					<td align="left" nowrap="nowrap" >
						<s:file id="fichero" size="50" name="fileUpload" value="fichero"
						 onblur="this.className='input2';" onfocus="this.className='inputfocus';">
						</s:file>
					</td>
					
	     			<td align="left" nowrap="nowrap" style="vertical-align: middle;">       				
						<label for="num_Colegiado">Colegiado:</label>       				
       				</td>     
       				<td align="left">             
       					<s:select id="contratoColegiado" 
       					onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
						cssStyle="width:250px" 
						headerKey="" 
						headerValue="Seleccione Contrato" 
						name="legDto.idContrato" 
						listKey="codigo"
						listValue="descripcion" />	        			
       				</td>				
				</tr>
			</s:if>
			<s:else>
				<s:hidden name="legDto.idContrato" />
			</s:else>
			<s:if test="legDto.referencia != null && legDto.referencia!= ''">
				<tr>
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="referencia">Referencia: </label>
					</td>
				
					<td align="left" nowrap="nowrap" >
						<s:textfield id="referencia" name="legDto.referencia" size="40" readonly="true" />
					</td>
				</tr>
			</s:if>
			
				<tr>
					<s:if test="%{#isMinisterio==false}">
						<td align="left" nowrap="nowrap" style="vertical-align: middle;">
							<label for="orden">Orden de aparición en listado:</label>
						</td>
					
						<td align="left" nowrap="nowrap" >
							<s:textfield id="legDto.orden" name="legDto.orden" label="Orden " size="4"  />
						</td>
					</s:if>	
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="pais">País: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield id="legDto.pais" name="legDto.pais" label="Pais" size="20" maxlength="20" />
					</td>
				</tr>
			
		</table>	
		<br>
		<s:if test="%{#isAdmin==true || #isMinisterio==false}">
			<table align="center">
				<tr>
					<td><s:submit value="Guardar Petición" action="guardarAltaLegalizacionNuevo" cssClass="boton"/></td>
					<td><input type="button" class="boton" value="Limpiar campos" id="bLimpiar" onclick="limpiarFormulario(this.form.id);"/></td>
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
