<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div align="center">

<s:if test="hasActionErrors() || hasActionMessages()">
  	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td>Resumen de la liberación:</td>
			<td><img alt="Imprimir" src="img/impresora.png" style="height: 23px; cursor:pointer;" onclick="return imprimirId('estadisticasValidacion',false,null);" /></td>
		</tr>
	</table>
	
	<table style="width:100%;font-size:11px;" class="tablacoin" summary="Resumen de la validación" border="0" cellpadding="0" cellspacing="0" style="margin-top:0px;">
   		<tr>
   			<th>&nbsp;</th>
   			<th>Liberados EEFF</th>
   			<th>Fallidos</th>
		</tr>
		
		<tr>
  			  <s:if test="%{resumenTotal.tipoTramite.equals('TOTAL')}">
  			  	<td style="font-weight:bold;"><s:property value="resumenTotal.tipoTramite"/></td>
  			  </s:if>
  			  
   			  <s:else>
   			  	<td><s:property value="resumenTotal.tipoTramite"/></td>
   			  </s:else>

   			  
   			  <s:if test="%{resumenTotal.numLiberadosEEFF != 0}">
   			  	<td style="color:green;font-weight:bold;"><s:property value="resumenTotal.numLiberadosEEFF"/>   			  
   			  		<s:if test="%{resumenTotal.tipoTramite.equals('TOTAL')}">
						<img id="imgLiberadosEEFF"  style="cursor:pointer;" alt="Mostrar" src="img/plus.gif" onclick="return mostrarBloqueLiberadosEEFF();" />
	   			 	</s:if>
   			  
   			  	</td>
   			  </s:if>
	   		  <s:else>
	   		  	<td style="color:green;"><s:property value="resumenTotal.numLiberadosEEFF"/></td>
	   		  </s:else>
	   			  
	   			  
  			  <s:if test="%{resumenTotal.numFallidosLiberadosEEFF != 0}">
  			  	<td style="color:red;font-weight:bold;"><s:property value="resumenTotal.numFallidosLiberadosEEFF "/>
  			    	<s:if test="%{resumenTotal.tipoTramite.equals('TOTAL')}">
  						<img id="imgFallidosLiberadosEEFF" alt="Mostrar" style="cursor:pointer;" src="img/plus.gif" onclick="return mostrarBloqueFallidosLiberadosEEFF();" />
	   				</s:if>
    			</td>
    		  </s:if>
    		  <s:else>
    		  	<td style="color:red;"><s:property value="resumenTotal.numFallidosLiberadosEEFF "/></td>
    		  </s:else>
     		       		  
		</tr>
		
 	</table>
 </s:if>
 		
 		
<div id="bloqueLiberadosEEFF" style="display:none;width:100%;font-size:11px;">
	<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
	<tr>
		<td  style="width:100%;text-align:center;">Detalle de Trámites Liberados EEFF</td>
	</tr>
	</table>						
		<ul style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;text-align: left;">
			<s:iterator value="actionMessages">
				<li><span style="text-align: left;"><s:property /></span></li>	
			</s:iterator>
		</ul>		
</div>		



<div id="bloqueFallidosLiberadoEEFF" style="display:none;width:100%;font-size:11px;">
	<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
		<tr>
			<td  style="width:100%;text-align:center;">Detalle de los errores</td>
		</tr>
	</table>	
		<s:if test="actionErrors">
			<ul style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;text-align: left;">
			<s:iterator value="actionErrors">
				<li><span><s:property /></span></li>
				
			</s:iterator>
		</ul>
		</s:if>	
</div> 		
 		
</div> 	

	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	

		

		<s:if test="%{listaConsultaTramiteTrafico.calculaTamanoListaBD()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
									onchange="return cambiarElementosPorPaginaConsultaTrafico();" 
									/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>

<div class="divScroll">
	<display:table name="listaConsultaTramiteTrafico" excludedParams="*"
					requestURI="navegarConsultaTramiteTrafico.action"
					class="tablacoin"
					uid="tablaConsultaTramite"
					summary="Listado de Tramites"
					cellspacing="0" cellpadding="0"
					decorator="trafico.utiles.DecoradorTablas"
	>		

	<display:column property="numExpediente" title="Num Expediente"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%;" 
			href="detalleConsultaTramiteTrafico.action"
			paramId="numExpediente" 	 
			/>
			
	<display:column property="referenciaPropia" title="Referencia Propia"
			sortable="true" headerClass="sortable"
			defaultorder="descending" maxLength="15"
			style="width:4%" />
			
	<display:column property="vehiculo.bastidor" title="Bastidor"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
				
	<display:column property="vehiculo.matricula" title="Matricula"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" />
			
	<!--Mantis 0003842: Solicitud de Mejora CTIT (FULL/TRADE/NOT) -->	
	<display:column property="tasa.tipoTasa" title="Tipo Tasa"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" />
			
	<display:column property="tasa.codigoTasa" title="Codigo Tasa"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" />
	
	<display:column title="Tipo Tramite" property="tipoTramite.nombreEnum"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" 
			paramId="idTipoTramite" >
	</display:column>
		
	<display:column title="Estado" property="estado.nombreEnum"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" >
	</display:column>
	<s:if test="%{!(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
		
	<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaTramite);' onKeyPress='this.onClick'/>" 
			style="width:1%" >
		<table align="center">
		<tr>
			<td style="border: 0px;">
				<input type="checkbox" name="listaChecksConsultaTramite" id="<s:property value="#attr.tablaConsultaTramite.tipoTramite.valorEnum"/>-<s:property value="#attr.tablaConsultaTramite.numExpediente"/>" 
				value='<s:property value="#attr.tablaConsultaTramite.numExpediente"/>' />
				
				<input type="hidden" name="listaEstadoConsultaTramite" id="estado_<s:property value="#attr.tablaConsultaTramite.numExpediente"/>" 
				value='<s:property value="#attr.tablaConsultaTramite.estado.valorEnum"/>' />
			</td>
			<td style="border: 0px;">				  		
		  		<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  		onclick="abrirEvolucionTramite(<s:property value="#attr.tablaConsultaTramite.numExpediente"/>);" title="Ver evolución"/>
		  	</td>
		</tr>
		</table>	
			
 	</display:column>
	
	</s:if>
	
	<s:if test="%{(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
		<display:column style="width:1%">
			<table align="center">
				<tr>
					<td style="border: 0px;">				  		
				  		<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  		onclick="abrirEvolucionTramite(<s:property value="#attr.tablaConsultaTramite.numExpediente"/>);" title="Ver evolución"/>
				  	</td>
				  </tr>
			</table>
		</display:column>
	</s:if>
	
				
	<input type="hidden" name="resultadosPorPagina"/>
</display:table>
</div>	

