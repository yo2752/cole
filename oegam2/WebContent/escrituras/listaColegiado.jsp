<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script type="text/javascript">
	function volver(){
		window.history.back();
	}
	
		
</script>
<ol id="toc">
    <li><a href="#DatosdelContrato">Datos del Contrato</a></li>
	<li><a href="#DatosdelColegiado">Datos del Colegiado</a></li>
	<li><a href="#AplicacionesAsociadas">Aplicaciones asociadas</a></li>
	<li><a href="#UsuariosAsociadas">Usuarios asociadas</a></li>
</ol>
   <div class="contentTabs" id="DatosdelContrato" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
   <div class="contenido">
					<table class="subtitulo" cellspacing="0">
						<tr>
							<td>Datos del Contrato</td>
						</tr>
					</table>
				</div>
				<!--Datos del titular-->
				<div class="contenido">
					<table cellspacing="3" class="tablaformbasica2" cellpadding="5" border="0" style="font-size:112%">
						<tr>
							<td>
								<table width="100%">
									<tr>
										  <td>
										  <label for="cif">CIF<span class="naranja">*</span></label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.cif"  disabled="true"/>
		                        		  </td>
		                        		  <td>
										  <label for="estado">Estado contrato</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoEstado"  disabled="true"/>
		                        		  </td>
										<td>
										  <label for="anagramaContrato">Anagrama Contrato<span class="naranja">*</span></label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.anagrama_Contrato"  disabled="true"/>
		                        		 </td>
		                        		 <td>
										 <label for="RazonSocial">Razon Social<span class="naranja">*</span></label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.razon_Social" size="35" disabled="true" />
		                        		 </td>	
		                        		 								
									</tr>
								</table>
							</td>
						</tr>
	                    <tr>
							<td>
								<table width="100%">
									<tr>
									   <td>
										 <label for="correoElectronico">Correo Electronico</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.correo_Electronico"  size="35" disabled="true"/>
		                        		 </td>	
										<td>
										  <label for="fechaInicio">fecha Inicio</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.fecha_Inicio"  disabled="true"/>
		                        		  </td>
										<td>
										  <label for="provencia">provincia</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.provincia"  disabled="true"/>
		                        		 </td>
		                        		 <td>
										 <label for="municipio">Municipo</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.municipio"  disabled="true"/>
		                        		 </td>	
		                        		 				
									</tr>
								</table>
							</td>
						</tr>
						 <tr>
							<td>
								<table width="100%">
									<tr>
									<td>
									 <label for="tipoVia">tipo Via</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.nombre"  disabled="true"/>
		                        		 </td>
		                        		 <td>
		                        		 <label for="via"> Via</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.via"  disabled="true"/>
		                        		 </td>				
										<td>
										  <label for="telefono">Telefono</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.telefono"  disabled="true"/>
		                        		  </td>
										<td>
										  <label for="codigoPostal">codigo Postal<span class="naranja">*</span></label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.cod_Postal"  disabled="true"/>
		                        		 </td>
		                          </tr>
								 </table>
							 </td>
						   </tr>
						    <tr>
							<td>
								<table width="100%">
									<tr>
									 <td>
										 <label for="numero">Numero</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.numero"  disabled="true"/>
		                        		 </td>	
		                        		 <td>
		                        		 <label for="letra">Letra</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.letra"  disabled="true"/>
		                        		 </td>
		                        		 <td>
		                        		 <label for="Escalera"> Escalera</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.escalera"  disabled="true"/>
		                        		 </td>
		                        		 <td>
										  <label for="piso">Piso</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.piso"  disabled="true"/>
		                        		  </td>
										<td>
										  <label for="puerta">Puerta</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.puerta"  disabled="true"/>
		                        		 </td>
		                        		 <td>				
		                          </tr>
								 </table>
							 </td>
						   </tr>
						   <tr>
						    <td>
		                        		 <label for="colegio">Colegio</label>			                        					                      		
		                        		  <s:textfield name="#session.nombreColegio" size="100" disabled="true"/>
		                        		 </td>
		                    </tr>
		                    <tr>    		 
		                        		  <td>
		                        		 <label for="jefatura">Jefatura</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoJefatura" size="60" disabled="true"/>
		                        		 </td> 
						   </tr>
					</table>
						        
		</div>
	
	  <div class="contenido">
		<table border="0" class="acciones">
		    <tr>
		    	<td align="center">
                  <input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volver();" onKeyPress="this.onClick" />&nbsp;                     
		        </td>
		    </tr>
		</table>
	</div>
   </div>
	<div class="contentTabs" id="DatosdelColegiado">
				<div class="contenido">
					<table class="subtitulo" cellspacing="0">
						<tr>
							<td>Datos del Colegiado</td>
						</tr>
					</table>
				</div>
				<!--Datos del titular-->
				<div class="contenido">
					<table cellspacing="3" class="tablaformbasica2" cellpadding="5" border="0" style="font-size:112%">
						<tr>
							<td>
								<table width="100%">
									<tr>
										  <td>
										  <label for="nif">NIF<span class="naranja">*</span></label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.nif"  disabled="true"/>
		                        		  </td>
		                        		  <td>
										  <label for="numeroColegiado">Numero Colegiado</label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.num_Colegiado"  disabled="true"/>
		                        		  </td>	
		                        		  <td></td>
		                        		  <td></td>													
									</tr>
								</table>
							</td>
						</tr>
					     <tr>
							<td>
								<table width="100%">
									<tr>
										  <td>
										  <label for="apellido1">Apellido1<span class="naranja">*</span></label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.apellido1_Razon_Social"  disabled="true"/>
		                        		  </td>
		                        		  <td>
										  <label for="apellido2">Apellido2</label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.apellido2"  disabled="true"/>
		                        		  </td>
										<td>
										  <label for="nombre">Nombre<span class="naranja">*</span></label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.nombre"   disabled="true"/>
		                        		 </td>
		                        		 <td>
										  <label for="ncorpme">Ncorpme<span class="naranja">*</span></label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.ncorpme"   disabled="true"/>
		                        		 </td>
		                        		 
		                        		 								
									</tr>
								</table>
							</td>
						</tr>
	                    <tr>
							<td>
								<table width="100%">
									<tr>
									   <td>
										 <label for="correoElectronico">Correo Electronico</label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.correo_Electronico" size="35" disabled="true"/>
		                        		 </td>	
										<td>
										  <label for="ultimaConexion">ultima Conexion</label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.ultima_Conexion"  disabled="true"/>
		                        		  </td>
										<td>
										  <label for="fecha renovacion">fecha renovacion</label>			                        					                      		
		                        		  <s:textfield name="#session.colegiadoDao.fecha_Renovacion"  disabled="true"/>
		                        		 </td>
		                        		<td></td>
		                        		 				
									</tr>
								</table>
							</td>
						</tr>
						
					</table>
						   
						        
				</div>	
	
				<div class="contenido">
					<table border="0" class="acciones">
					    <tr>
					    	<td align="center">
			                  <input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volver();" onKeyPress="this.onClick" />&nbsp;                     
					        </td>
					    </tr>
					</table>
				</div>
</div>


<div class="contentTabs" id="AplicacionesAsociadas" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">

<display:table name="listaAplicaciones" excludedParams="*"
		requestURI="cargarContratos.action"
		class="tablacoi"   
		summary="Listado de Aplicaciones"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
				

		<display:column property="codigo_Aplicacion" title="codigo Aplicacion"
			sortable="false" headerClass="sortable"	
			href="manteniemientoPermisosContratos.action?idContratoSelect=${idContratoSelect}"			
			defaultorder="descending"
			paramId="codigo_AplicacionSeleccion"
			paramProperty="codigo_Aplicacion"					
			style="width:4%" />
		
		<display:column property="desc_Aplicacion" title="desc Aplicacion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			
		    <display:column title=""  style="width:4%">
		    <s:if test="%{#attr.listaContatosTable.asignada==1}">
			<input type="checkbox" name="asignada" id="check<s:property value="#attr.listaContatosTable.asignada"/>"
				value="<s:property value="#attr.listaContatosTable.asignada"/>" checked="checked" disabled="disabled" />
			</s:if>
			<s:else>
			<input type="checkbox" name="asignada" id="check<s:property value="#attr.listaContatosTable.asignada"/>"
				value="<s:property value="#attr.listaContatosTable.asignada"/>"  disabled="disabled"/>
			</s:else>
		   </display:column>
</display:table>
	
		
				
<div class="contenido">
					<table border="0" class="acciones">
					    <tr>
					    	<td align="center">
			                  <input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volver();" onKeyPress="this.onClick" />&nbsp;                     
					        </td>
					    </tr>
					</table>
				</div>		

  
	

</div>
<div class="contentTabs" id="UsuariosAsociadas" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">

<display:table name="listaUsuarios" excludedParams="*"
		requestURI="loadUserContratos.action"
		class="tablacoi"   
		summary="Listado de Aplicaciones"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
				

			<display:column property="nif" title="nif"
			sortable="false" headerClass="sortable"	
			href="detalleUsuarioContratos.action?numColegiadoUsuario=${numColegiado}"
			defaultorder="descending"
			paramId="idUsuarioSeleccionado"
			paramProperty="id_Usuario"					
			style="width:4%" />
		
		<display:column property="apellidos_Nombre" title="apellidos_Nombre"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
		<display:column property="correo_Electronico" title="correo_Electronico"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />	
			
			
		<display:column property="ultima_Conexion" title="ultima_Conexion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />	
			
			 <display:column title="Estado Usuario"
			sortProperty="estado_Usuario" sortable="false" headerClass="sortable"
			defaultorder="descending" style="width:8% ;color:red"  >
			<s:property
				value="%{@org.gestoresmadrid.core.model.enumerados.Estado@convertirTexto(#attr.listaContatosTable.estado_Usuario)}" />
		   </display:column>
		
		
		    
</display:table>
	
		
	<div class="contenido">
					<table border="0" class="acciones">
					    <tr>
					    	<td align="center">
			                  <input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volver();" onKeyPress="this.onClick" />&nbsp;                     
					        </td>					        
					    </tr>
					</table>
	</div>			
		

  
	
<s:hidden name="numColegiado"  id="numColegiado"></s:hidden>
<s:hidden name="idContratoSelect" id="idContratoSelect"></s:hidden>
</div>
