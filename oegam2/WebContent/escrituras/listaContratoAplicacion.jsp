<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<script src="js/genericas.js" type="text/javascript"></script>
<script type="text/javascript">
	function modificarPermiso(){
		if(!confirm("¿Realmente desea cambiar el permiso?")) {
			return false;
		}
		document.formData.action="modificarPermisoContratos.action";
		document.formData.submit();
		return true;
	}
	
	function volver(){		
		return  cancelarForm("detalleContratos.action?idContratoSeleccion=${idContratoSelect}");
		
	}
</script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">información básica  contrato-aplicación</span></td>
		</tr>
	</table>
</div>

<s:form method="post" name="formData" id="formData">
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>información básica sobre el contrato y  la aplicación </td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">

	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="cif">CIF</label>
			<s:textfield name="#session.apcontrato.cif" id="cif" cssClass="inputview" readonly="true"/>
		</td>
	</tr>

	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="razonSocial">Raz&oacute;n Social</label>
			<s:textfield name="#session.apcontrato.razon_social" id="razonSocial" cssClass="inputview" size="50" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td  align="left" nowrap="nowrap">
			<label for="anagramaContrato">anagrama Contrato</label>
			<s:textfield name="#session.apcontrato.anagrama_contrato" id="anagramaContrato" cssClass="inputview" size="50" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td>
			<table>
			<tr>
			<td  align="left" nowrap="nowrap">
				<label for="descripcionAplicacion">Descripcion aplicacion</label>
				<s:textfield name="#session.apcontrato.desc_aplicacion" id="descAplicacion" cssClass="inputview" size="20" readonly="true"/>
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="codigoAplicacion">codigo aplicacion</label>
				<s:textfield name="#session.apcontrato.codigo_Aplicacion" id="codigoAplicacion" size="14" cssClass="inputview" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="alias">alias</label>
				<s:textfield name="#session.apcontrato.alias" id="alias" size="5" cssClass="inputview" readonly="true"/>
			</td>
			
			
		</tr>
		</table>
		</td>
	</tr>

</table>
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Funciones Asociadas </td>
	</tr>
</table>

<div id="fieldError">
<s:actionmessage/>									
</div>
 
<display:table name="listafunciones" excludedParams="*"
		requestURI="loadFuncionesContratos.action"
		class="tablacoi"   
		summary="Listado de Funciones"
		cellspacing="0" cellpadding="0" uid="listaContatosTable">
	 
		
		<display:column title="codigo Aplicacion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"			
			style="width:20%;text-align:left"> 
			 <s:iterator var="counter" begin="0" end="#attr.listaContatosTable.nivel" >
    			<img  src="img/activo.gif" />
 			</s:iterator>
			${listaContatosTable.codigo_Aplicacion}
		</display:column>
	  
		<display:column property="codigo_Funcion" title="codigo Funcion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"			
			style="width:4%" />
			
			<display:column property="desc_Funcion" title="desc Funcion"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:6%" />
			
			<display:column property="codigo_Funcion_Padre" title="codigo Funcion Padre"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			<display:column property="url" title="url"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" maxLength="25"/>
			
			<display:column property="nivel" title="nivel"
			sortable="false" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" />
			
			<display:column property="orden" title="orden"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			<display:column property="tipo" title="tipo"
			sortable="false" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
			
			
			
			
			
			
			
		    <display:column title=""  style="width:4%">
		    	<input 	type="checkbox" name="asignadasFunciones" 
      					value="${listaContatosTable.codigo_Funcion}" 
      					class="checkbox"
      					<s:if test="%{#attr.listaContatosTable.asignada==1}">
      					checked=checked
      					</s:if>
      					>
		   
		   </display:column>
</display:table>
   
<div class="contenido">
					<table border="0" class="acciones">
					    <tr>
					    	<td align="center">
			                  <input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volver();" onKeyPress="this.onClick" />&nbsp;                     
					        </td>					        
					        <s:if test="%{#session.contratoEstado=='Habilitado'}">
					        <s:if test="%{#session.permisoAdmin==true||#session.permisoColegio==true}">
					        <s:if test="%{listafunciones.size()>0}">					       
					        <td align="center">
			                  <input type="button" class="boton" name="bAceptar" id="bAceptar" value="cambiar Permiso" onClick="modificarPermiso();" onKeyPress="this.onClick" />&nbsp;                     
					        </td>
					        </s:if>
					        </s:if>
					        </s:if>
					    </tr>
					</table>
</div>	
<s:hidden name="idContratoSelect" id="idContratoSelect"></s:hidden>	
<s:hidden  name="codigoAplicacion" id="codigoAplicacion"></s:hidden>	
<s:hidden  name="codigoFunciones" id="codigoFunciones"></s:hidden>
<s:hidden name="cifSeleccion" id="cifSeleccion"></s:hidden>
<s:hidden name="niveles" id="niveles"></s:hidden>
</s:form>

