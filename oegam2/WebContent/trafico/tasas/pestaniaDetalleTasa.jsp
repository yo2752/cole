<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/gestionTasa.js" type="text/javascript"></script>
<script type="text/javascript">	</script>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Detalle de la Tasa</td>
		</tr>
	</table>
	<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
		<s:hidden name="detalleTasaNueva" value="true"/>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
				<label for="codigo">C&oacute;digo</label>
		  			<s:textfield name="tasaDto.codigoTasa" id="codigo" onblur="this.className='input2';" nfocus="this.className='inputfocus';" 
		  						size="20" maxlength="20"/>
		  		</td>
		  		<td align="left" nowrap="nowrap">
					<label for="tipo">Tipo</label>
		  				<s:textfield name="tasaDto.tipoTasa" id="tipo" onblur="this.className='input2';" nfocus="this.className='inputfocus';" 
		  						size="5" maxlength="20"/>
		  		</td>
			</tr>
			<tr>
		   	    <td align="left" nowrap="nowrap">
		   	        <label for="fechaAlta">Fecha alta</label>
						<s:textfield value="%{@org.gestoresmadrid.utilidades.components.UtilesFecha@formatoFecha(tasaDto.fechaAlta)}" id="tasaDto.fechaAlta" 
						onblur="this.className='input2';" nfocus="this.className='inputfocus';" size="10" maxlength="20"/> 
			    </td>
			     <td align="left" nowrap="nowrap">
					<label for="precio">Importe (&euro;)</label>
		  				<s:textfield name="tasaDto.precio" id="precio" onblur="this.className='input2';" nfocus="this.className='inputfocus';" 
		  						size="5" maxlength="20"/>
		  		</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="numExpediente">N&ordm; Expediente</label>
		  				<s:textfield name="tasaDto.numExpediente" id="numExpediente" onblur="this.className='input2';" nfocus="this.className='inputfocus';" 
		  						size="30" maxlength="30"/>
		  		</td>
				<td align="left" nowrap="nowrap">
					<label for="refPropia">Referencia Propia</label>
		  				<s:textfield name="tasaDto.refPropia" id="refPropia" onblur="this.className='input2';" nfocus="this.className='inputfocus';" 
		  						size="20" maxlength="20"/>
		  		</td>
				<td align="left" nowrap="nowrap">
					<label for="contratoColegiado">Vía Contrato</label>
		  				<s:textfield name="tasaDto.contrato.via" id="via" onblur="this.className='input2';" nfocus="this.className='inputfocus';" 
		  						size="30" maxlength="30"/>
		  		</td>
			</tr>
		
			<tr>
				<td align="left" nowrap="nowrap">
		   	        <label for="fechaAsignacion">Fecha asignaci&oacute;n</label>
						<s:textfield value="%{@org.gestoresmadrid.utilidades.components.UtilesFecha@formatoFecha(tasaDto.fechaAsignacion)}" id="tasaDto.fechaAsignacion" 
						onblur="this.className='input2';" nfocus="this.className='inputfocus';" size="10" maxlength="20"/> 
			    </td>
				  <td align="left" nowrap="nowrap">
					<label for="contratoColegiado">Contrato</label>
		  				<s:textfield name="tasaDto.contrato.colegiadoDto.numColegiado" id="numColegiado" onblur="this.className='input2';" nfocus="this.className='inputfocus';" 
		  						size="5" maxlength="20"/>
		  		</td>
		  	    <td align="left" nowrap="nowrap">
					<label for="contratoColegiado">Provincia Contrato</label>
		  				<s:textfield name="tasaDto.contrato.provinciaDto.nombre" id="nombre" onblur="this.className='input2';" nfocus="this.className='inputfocus';" 
		  						size="5" maxlength="30"/>
		  		</td>
			</tr>
		
		</table>
		
		<table class="acciones">
			<tr>
				<td>
					<!-- GTMS2010_BOTÓN_VOLVER NUEVO BLOQUE CONDICIONAL -->
			       <input class="boton" type="button" name="bVolver" id="bVolver" value="Volver" onClick="javascript:volverConsultaTasas();" />
				</td>
			</tr>
		</table>
</div>
<%@include file="../../includes/erroresYMensajes.jspf" %>