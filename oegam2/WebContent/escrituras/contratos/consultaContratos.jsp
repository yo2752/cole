<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/genericas.js" type="text/javascript"></script>

<div class="nav">
<table cellSpacing="0" cellPadding="5" width="100%">
	<tr>
		<td class="tabular">
		<span class="titulo">Contratos: Consulta de Contratos</span>
		</td>
	</tr>
</table>
</div>

<s:form id="formData" name="formData" action="buscarContratoNuevo.action">

	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
        		<td align="left" nowrap="nowrap">
        			<label for="cif">CIF:</label>
        		</td>
        		
        		<td align="left" >
        			<s:textfield name="consultaContrato.cif" id="cif"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="35" maxlength="20" />
        		</td>
        		
        		
	     		<td align="left" nowrap="nowrap">
        			<label for="estado">Estado:</label>
        		</td>
        		
        		<td>
        			<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboEstados()"
								id="estadoContrato"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								name="consultaContrato.estado"
								listValue="nombreEnum"
								listKey="valorEnum"
								title="Estado Contrato"
								headerKey="-1" headerValue="TODOS"
								/>
        			
        		</td>
        		
        		
			</tr>
			<tr>
        		
        		
        		<td align="left" nowrap="nowrap">
        			<label for="razon_Social">Razón Social:</label>
        		</td>
        		
        		<td align="left">
        			<s:textfield name="consultaContrato.razonSocial" id="razon_Social"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="35" maxlength="30" />
        		</td>
        		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||
					@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
        		<td align="left" nowrap="nowrap">
        			<label for="num_Colegiado">N&uacute;mero Colegiado:</label>
        		</td>
        		
        		<td align="left">
        			<s:textfield name="consultaContrato.numColegiado" id="num_Colegiado"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="4" maxlength="4" />
        		</td>
        		</s:if>
			</tr>
			
			
       		
		</table>
	
		<div id="botonBusqueda">
		
		<table width="100%">
			<tr>
				<td>
				<s:submit value="Buscar" id="bConsConsulta" cssClass="boton"/>
				<input type="button" 
					class="boton" 
					name="bLimpiar" 
					id="bLimpiar" 
					value="Limpiar"  
					onkeypress="this.onClick" 
				    onclick="return limpiarFormConsultaContrato();"/>	
				</td>
				
			</tr>
		</table>
		</div>
	
	</div>

<div id="resultado">
	<table class="subtitulo" cellSpacing="0">
		<tr>
			<td>Resultado de la b&uacute;squeda</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<s:if test="%{listaContratos.calculaTamanoListaBD()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right">
								<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
							</td>
							<td width="10%" align="right">
							<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
								onblur="this.className='input2';" 
								onfocus="this.className='inputfocus';"
								id="idResultadosPorPagina"
								value="resultadosPorPagina"
								title="Resultados por página"
								onchange="javascript:cambiarElementosPorPaginaContratos();"
								/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
</div>


<display:table name="listaContratos" excludedParams="*"
		requestURI="navegarContratoNuevo.action"
		class="tablacoin"   
		summary="Listado de Contratos"
		cellspacing="0" cellpadding="0" uid="listaContratosTable">
				

		<display:column property="datosContrato.cif" title="CIF"
			sortable="true" headerClass="sortable"			
			defaultorder="descending"
			style="width:4%" />
		
		
		<display:column property="datosContrato.direccion.municipio.provincia.nombre" title="Provincia"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:6%" />
				
		<display:column property="datosContrato.razonSocial" title="Razón Social"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:18%"  />
			
		<display:column property="datosContrato.direccion.nombreVia" title="Vía"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:26%" />

		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
			<display:column property="datosColegiado.numColegiado" title="Num Colegiado"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:1%"  />
		</s:if>
		<s:else>
			<s:if test="%{#attr.listaContratosTable.num_Colegiado_Nacional != null && #attr.listaContratosTable.num_Colegiado_Nacional != ''}">
				<display:column property="datosColegiado.numColegiado" title="Num Colegiado"
					sortable="true" headerClass="sortable"
					defaultorder="descending"
					style="width:1%"  />
			</s:if>
			<s:else>
				<display:column property="datosColegiado.numColegiadoNacional" title="Num Colegiado"
					sortable="true" headerClass="sortable"
					defaultorder="descending"
					style="width:1%"  />
			</s:else>
		</s:else>
			
		<!-- INICIO INCIDENCIA MANTIS 6019 : Se muestra el alias para la consulta desde el Administrador -->
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||
					@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					
			<display:column property="datosColegiado.alias" title="Alias"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:5%"  />
				
		</s:if>
		
		<!-- FIN INCIDENCIA MANTIS 6019 : Se muestra el alias para la consulta desde el Administrador -->
        
        <display:column title="Estado"
			sortProperty="estado_Contrato" sortable="true" headerClass="sortable"
			defaultorder="descending"  style="width:8% ;color:red" >
			<s:property 
				value="%{@eorg.gestoresmadrid.core.model.enumerados.Estado@convertirTexto(#attr.listaContratosTable.datosContrato.estadoContrato)}" />
		</display:column>
	    
	    <display:column  style="width:5%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.idContratos);' onKeyPress='this.onClick'/>">	
		<table align="center"> 
			<tr>
				<td style="border: 0px;">
				  	<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  		onclick="abrirEvolucionContratoAnt(<s:property value="#attr.listaContratosTable.idContrato"/>);" title="Ver evolución"/>
				</td>
				<td style="border: 0px;">
					<input type="checkbox" name="idContratos" id="check<s:property value="#attr.listaContratosTable.idContrato"/>" 
						value='<s:property value="#attr.listaContratosTable.idContrato"/>' />
				</td>
				<td style="border: 0px;">				  		
			  		<img src="img/mostrar.gif" alt="Modificar Contrato" style="height:20px;width:20px;cursor:pointer;" 
			  			onclick="modificarContrato('${listaContratosTable.idContrato}');" title="Modificar Contrato"/>
			  	</td>
			</tr>
		</table>
		
	    </display:column>
	    
		<input type="hidden" name="resultadosPorPagina"/>    
</display:table>

<s:if test="%{listaContratos.tamanoLista()>0 && (
					@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||
					@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio())}">
					
	<div id="bloqueLoadingContratos" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
<table class="acciones">
	<tr>
		<td>
			<div>
				<input type="button" class="boton" name="bBloquear" id="bBloquear" value="Habilitar" onClick="return habilitarContrato();" onKeyPress="this.onClick" />
				&nbsp;
				<input type="button" class="boton" name="bDesbloquear" id="bDesbloquear" value="Deshabilitar" onClick="return deshabilitarContrato();" onKeyPress="this.onClick" />
				&nbsp;
				<input type="button" class="boton" name="bEliminar" id="bEliminar" value="Eliminar" onClick="return eliminarContrato();" onKeyPress="this.onClick" />
				
			</div>
		</td>
	</tr>
</table>
</s:if>
 <s:hidden key="idContrato" name="idContrato"/>
 
</s:form>
<div id="divEmergenteConsultaContrato" style="display: none; background: #f4f4f4;"></div>
<div id="divEmergenteConsultaContratoEvolucion" style="display: none; background: #f4f4f4;"></div>