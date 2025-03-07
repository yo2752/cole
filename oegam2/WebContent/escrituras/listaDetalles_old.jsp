<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript">
	function volver(){
		window.history.back();
	}
	
</script>

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
										  <label for="telefono">Tel&eacute;fono</label>			                        					                      		
		                        		  <s:textfield name="#session.contratoDao.telefono"  disabled="true"/>
		                        		  </td>
										<td>
										  <label for="codigoPostal">c&oacute;digo Postal<span class="naranja">*</span></label>			                        					                      		
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
										 <label for="numero">N&uacute;mero</label>			                        					                      		
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


