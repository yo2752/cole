<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
  
<div class="contentTabs" id="AplicacionesAsociadas" >  
	<div class="contenido">		
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Aplicaciones Asociadas</td>
			</tr>
		</table>

		<display:table name="contratoDto.aplicacionesDto" excludedParams="*" class="tablacoi" 
				summary="Listado de Aplicaciones" cellspacing="0" cellpadding="0" uid="listaAplicacionesTable">

				<display:column property="codigoAplicacion" title="Código Aplicacion" sortable="false" defaultorder="descending" style="width:30%" />
				
				<display:column property="alias" title="Descripción" sortable="false" defaultorder="descending" style="width:35%" />
					
			    <display:column title="Estado Aplicación"  style="width:30%; color:red;"> 
			    	<s:if test="%{#attr.listaAplicacionesTable.asignada}">
		      			Asignada
	   				</s:if> 
	   				<s:else>
   						Desasignada
	   				</s:else>
			   	</display:column>
				   
			   	<display:column  style="width:1%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.idAplicaciones);' onKeyPress='this.onClick'/>">	
					<table align="left"> 
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="idAplicaciones" id="check<s:property value="#attr.listaAplicacionesTable.codigoAplicacion"/>" 
									value='<s:property value="#attr.listaAplicacionesTable.codigoAplicacion"/>' 
			      					<s:if test="%{#attr.listaAplicacionesTable.asignada}">
				    					checked=checked
		    	  					</s:if> />
							</td>
							<s:if test="%{#attr.listaAplicacionesTable.asignada}">
				    			<td style="border: 0px;">				  		
						  			<img src="img/mostrar.gif" alt="Modificar Permisos Aplicacion" style="height:20px;width:20px;cursor:pointer;" 
							  			onclick="recuperarAplicacion('${listaAplicacionesTable.codigoAplicacion}');" title="Modificar Permisos"/>
							 	</td>
		   					</s:if> 
						</tr>
					</table>
				</display:column>
		</display:table>
		
		<table class="acciones">
			<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<td>
						<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Guardar" onClick="return modificacionContrato('AplicacionesAsociadas');" onKeyPress="this.onClick"/>
						&nbsp;	
					</td>
				</s:if>
				<td align="center">
					<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volverContrato();" onKeyPress="this.onClick" />&nbsp;                     
			     </td>
			</tr>
		</table>
    
		<s:if test="%{aplicacionSel}">
			<div id="modificarAplicacionSeleccionado" style="display: block;">
				<%@include file="aplicacionSeleccionada.jsp" %>
			</div>
		</s:if>
	</div>
</div>