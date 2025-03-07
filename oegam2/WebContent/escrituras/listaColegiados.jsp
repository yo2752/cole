<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/creditos.js" type="text/javascript"></script>
<script type="text/javascript">

function cambiarElementosPorPagina(){

	document.location.href='navegarColegiados.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

</script>

<div class="nav">
<table cellSpacing="0" cellPadding="5" width="100%">
	<tr>
		<td class="tabular">
		<span class="titulo">Colegiados: Consulta de Colegiados</span>
		</td>
	</tr>
</table>
</div>

<s:form id="formData" name="formData" action="buscarColegiados.action">

	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
        		<td align="left" nowrap="nowrap">
        			<label for="nif">NIF:</label>
        		</td>
        		
        		<td align="left" >
        			<s:textfield name="nif" id="nif"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="35" maxlength="20" />
        		</td>
        		
        		
	     		
        		
			</tr>
			<tr>
        		
        			<td align="left" nowrap="nowrap">
        			<label for="apellidos_Nombre">Apellidos Nombre:</label>
        		</td>
        		
        		<td align="left">
        			<s:textfield name="apellidosNombre" id="apellidos_Nombre"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="45" maxlength="45" />
        		</td>
        		
        		<td align="left" nowrap="nowrap">
        			<label for="num_Colegiado">Colegiado:</label>
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
				<input type="button" 
					class="boton" 
					name="bLimpiar" 
					id="bLimpiar" 
					value="Limpiar"  
					onkeypress="this.onClick" 
					onclick="return limpiarFormConsultaColegiados();"/>
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
	<s:if test="%{listaColegiados.calculaTamanoListaBD()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
								onchange="javascript:cambiarElementosPorPagina();"
								/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
</div>


<display:table name="listaColegiados" excludedParams="*"
		requestURI="navegarColegiados.action"
		class="tablacoin"   
		summary="Listado de Colegiados"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
		
		<display:column property="num_Colegiado" title="Num Col"
			sortable="true" headerClass="sortable"			
			defaultorder="descending"			
			style="width:1%"  />	
		
		<display:column title="Contrato-Provincia"
			sortable="true" headerClass="sortable"	
			sortProperty="idContratoProvincia"
			defaultorder="descending"
			style="width:4%">
			<a href="cargarCreditos.action?idContratoProvincia=<s:property value='#attr.listaContatosTable.idContratoProvincia'/>&numColegiado=<s:property value='#attr.listaContatosTable.num_Colegiado'/>"><s:property value="#attr.listaContatosTable.idContratoProvincia"/></a>
			</display:column>	

		<display:column property="nif" title="NIF"
			sortable="true" headerClass="sortable"			
			defaultorder="descending"
			style="width:4%" />
		
		
		<display:column property="apellidos_Nombre" title="Apellidos Nombre"
			sortable="true" headerClass="sortable"
			defaultorder="descending" 
			style="width:17%" />
		
 		<display:column property="via" title="Vía" 
 			sortable="true" headerClass="sortable"			 
 			defaultorder="descending"			
			style="width:20%"  />		

		<display:column property="correo_Electronico" title="Correo Electrónico"
			sortable="true" headerClass="sortable"			
			defaultorder="descending"			
			style="width:2%"  />
    
</display:table>
<s:hidden name="id_Contrato"></s:hidden>
<s:hidden name="idContratoProvincia"></s:hidden>
<s:hidden name="num_Colegiado"></s:hidden>

</s:form>

