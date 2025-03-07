<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">		

	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Resumen Permiso Internacional</td>
			<td align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  			onclick="abrirEvolucionPIntern(<s:property value="%{permisoInternacionalDto.idPermiso}"/>, 'divEmergentePermInternResumen');" title="Ver evolución"/>
			</td>
		</tr>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS GENERALES</span>
			</td>
		</tr>
	</table>
	
 	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
   			<td align="left" nowrap="nowrap" width="120">
       			<label for="numExpediente">Número de Expediente: </label>
       		</td>
       		
         	<td align="left" nowrap="nowrap">
       			<s:label value="%{permisoInternacionalDto.numExpediente}"/>
       		</td>				        		
		</tr>		        		

		<s:if test="%{permisoInternacionalDto.refPropia!=null}">
			<tr>        	       			
				<td align="left" nowrap="nowrap">
	   				<label for="refPropiaResumen">Referencia Propia: </label>
	   			</td>
	   			
	   			<td align="left" nowrap="nowrap">
	       			<s:label value="%{permisoInternacionalDto.refPropia}"/>
	       		</td>	
	      	</tr>
	    </s:if>
	    
	    <s:if test="%{permisoInternacionalDto.estado != null}">     	
	       	<tr>        	       			
				<td align="left" nowrap="nowrap">
	   				<label for="estado">Estado: </label>
	   			</td>
	   			
	   			<td align="left" nowrap="nowrap">
	   				<s:label value="%{@org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga@convertirTexto(permisoInternacionalDto.estado)}"/>
	   			</td>
	       	</tr>
       	</s:if>
       	
       	<s:if test="%{permisoInternacionalDto.estadoImpresion != null}">     	
	       	<tr>        	       			
				<td align="left" nowrap="nowrap">
	   				<label for="estado">Estado Impresion: </label>
	   			</td>
	   			
	   			<td align="left" nowrap="nowrap">
	   				<s:label value="%{@org.gestoresmadrid.core.trafico.interga.model.enumerados.EstadoTramitesInterga@convertirTexto(permisoInternacionalDto.estadoImpresion)}"/>
	   			</td>
	       	</tr>
       	</s:if>
      	
      	<s:if test="%{permisoInternacionalDto.observaciones!=null}">
	       	<tr>        	       			
				<td align="left">
	   				<label for="observaciones">Observaciones:</label>
	   			</td>
	   			
				<td align="left" nowrap="nowrap">
	       			<s:label value="%{permisoInternacionalDto.observaciones}"/>
	       		</td>
	      	</tr>
      	</s:if>
      	
		
       	
       	<s:if test="%{permisoInternacionalDto.respuesta!=null}">     	
	       	<tr>        	       			
				<td align="left" nowrap="nowrap">
	   				<label for="respuesta">Respuesta DGT: </label>
	   			</td>
	   			
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="%{permisoInternacionalDto.respuesta}"/>
	   			</td>	
	       	</tr>
       	</s:if>
    </table>
    
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DE LA TRAMITACIÓN</span>
			</td>
		</tr>
	</table>
	
    <table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">		
		<s:if test="%{permisoInternacionalDto.codigoTasa!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="120">
	   				<label for="tasa">Tipo de Tasa: </label>
	   			</td>
	   			
	   			<td align="left" nowrap="nowrap" width="120">
	   				<label for="tasa">4.5</label>
	   			</td>
			</tr>
			
			<tr>
				<td align="left" nowrap="nowrap" width="120">
	   				<label for="tasa">Tasa: </label>
	   			</td>
	   			
	   			<td align="left" nowrap="nowrap">
	   				<s:label value="%{permisoInternacionalDto.codigoTasa}"/>
	   			</td>
			</tr>
		</s:if>
		<s:if test="%{permisoInternacionalDto.fechaPresentacion.dia!=null}">
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
	   				<label for="fechaPresentacion">Fecha de presentación: </label>
	   			</td>
	   			
	   			<td align="left" nowrap="nowrap">
	   				<s:label value="%{permisoInternacionalDto.fechaPresentacion.dia}"/>/
	   				<s:label value="%{permisoInternacionalDto.fechaPresentacion.mes}"/>/
	   				<s:label value="%{permisoInternacionalDto.fechaPresentacion.anio}"/>
	   			</td>
			</tr>
		</s:if>
	</table>
    
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">DATOS DE LOS INTERVINIENTES</span>
			</td>
		</tr>
	</table>
	 
    <table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">	        	
       	<s:if test="%{permisoInternacionalDto.titular.persona.nif!=null && permisoInternacionalDto.titular.persona.nif!=''}">
	       	<tr>        	       			
				<td align="left" nowrap="nowrap" width="24%">
	   				<label for="datosTitular">Nombre y apellidos del titular: </label>
	   			</td>
	   			
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="%{permisoInternacionalDto.titular.persona.nombre}"/>
	   				<s:label value="%{permisoInternacionalDto.titular.persona.apellido1RazonSocial}"/>
	   				<s:label value="%{permisoInternacionalDto.titular.persona.apellido2}"/>
	   			</td>
	   			
	   			<td width="20"></td>
	   			
	   			<td align="left" nowrap="nowrap" width="13%">
	   				<label for="nifTitular">DNI del titular: </label>
	   			</td>
	   			
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="%{permisoInternacionalDto.titular.persona.nif}"/>
	   			</td>	
	      	</tr>
      	</s:if>
	</table>
</div>
<div id="divEmergentePermInternResumen" style="display: none; background: #f4f4f4;"></div>