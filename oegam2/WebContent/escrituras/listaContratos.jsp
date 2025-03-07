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
		<input type="button" class="boton" name="bNuevaConsulta" id="bNuevaConsulta" value="Nueva Consulta" onClick="return nuevaConsulta();" onKeyPress="this.onClick" />
		<input type="button" class="boton" name="bNuevaContrato" id="bNuevoContrato" value="Nuevo Contrato" onClick="return nuevoContrato();" onKeyPress="this.onClick" />
		</td>
	</tr>
</table>
</div>

<s:form id="formData" name="formData" action="buscarContratos.action">

	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
        		<td align="left" nowrap="nowrap">
        			<label for="cif">CIF:</label>
        		</td>
        		
        		<td align="left" >
        			<s:textfield name="cif" id="cif"
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
								name="estado"
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
        			<s:textfield name="razonSocial" id="razon_Social"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="35" maxlength="30" />
        		</td>
        		<td align="left" nowrap="nowrap">
        			<label for="num_Colegiado">N&uacute;mero Colegiado:</label>
        		</td>
        		
        		<td align="left">
        			<s:textfield name="numColegiado" id="num_Colegiado"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="4" maxlength="4" />
        		</td>
			</tr>
			
			
       		
		</table>
	
		<div id="botonBusqueda">
		
		<table width="100%">
			<tr>
				<td>
				<s:submit value="Buscar" cssClass="boton"/>
				</td>
			</tr>
		</table>
		</div>
     <div id="fieldError">
<s:actionmessage/>									
</div>	
	</div>

<div id="resultado">
	<table class="subtitulo" cellSpacing="0">
		<tr>
			<td>Resultado de la b&uacute;squeda</td>
		</tr>
	</table>
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
		requestURI="navegarContratos.action"
		class="tablacoin"   
		summary="Listado de Contratos"
		cellspacing="0" cellpadding="0" uid="listaContratosTable">
				

		<display:column property="cif" title="CIF"
			sortable="true" headerClass="sortable"			
			defaultorder="descending"
			style="width:4%" />
		
		
		<display:column property="provincia" title="Provincia"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:10%" />
				
		<display:column property="razon_Social" title="Razón Social"
			sortable="true" headerClass="sortable"
			href="modificarContratos.action"
			defaultorder="descending"
			paramId="idContratoSeleccion"
			paramProperty="id_Contrato"
			style="width:30%"  />

		<display:column property="num_Colegiado" title="Num Colegiado"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:5%"  />
			
			<!--			
			href="colegiadoContratos.action"
			paramId="idContratoSeleccion"
			paramProperty="id_Contrato"		  
        -->
        
        <display:column title="Estado"
			sortProperty="estado_Contrato" sortable="true" headerClass="sortable"
			defaultorder="descending"  style="width:8% ;color:red" >
			<s:property 
				value="%{@org.gestoresmadrid.core.model.enumerados.Estado@convertirTexto(#attr.listaContratosTable.estado_Contrato)}" />
		</display:column>
	    
	    <display:column  style="width:5%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.idContratos);' onKeyPress='this.onClick'/>">	
		<table align="center"> 
			<tr>
				<td style="border: 0px;">
				<input type="checkbox" name="idContratos" id="check<s:property value="#attr.listaContratosTable.id_Contrato"/>" 
					value='<s:property value="#attr.listaContratosTable.id_Contrato"/>' />
				</td>
				<td style="border: 0px;">				  		
			  		<img src="img/editar.gif" alt="Modificar Contrato" style="height:20px;width:20px;" 
			  		onclick="modificarContratos('${listaContratosTable.id_Contrato}');" title="Modificar Contrato"/>
			  	</td>
			</tr>
		</table>
		
	    </display:column>
	    
	    
</display:table>

<s:if test="%{listaContratos.tamanoLista()>0}">
<table class="acciones">
	<tr>
		<td>
			<div>
				<!--
				<input type="button" class="boton" name="bModificar" id="bModificar" value="Modificar" onClick="return modificarContratos();" onKeyPress="this.onClick" />
				&nbsp;
				--><input type="button" class="boton" name="bBloquear" id="bBloquear" value="Habilitar" onClick="return habilitarContratos();" onKeyPress="this.onClick" />
				&nbsp;
				<input type="button" class="boton" name="bDesbloquear" id="bDesbloquear" value="Deshabilitar" onClick="return deshabilitarContratos();" onKeyPress="this.onClick" />
				&nbsp;
				<input type="button" class="boton" name="bEliminar" id="bEliminar" value="Eliminar" onClick="return eliminarContratos();" onKeyPress="this.onClick" />
				
			</div>
		</td>
	</tr>
</table>
</s:if>
 <s:hidden key="id_Contrato" name="id_Contrato"/>

 <s:hidden name="cif2" id="cif2"></s:hidden>
</s:form>
<!--<script type="text/javascript">
<s:if test="%{#session.resultBean.listaMensajes.size!=0}">

var prueba = '<s:property value="#session.resultBean.listaMensajes"/>';
alert(prueba);
</s:if>
</script>
-->