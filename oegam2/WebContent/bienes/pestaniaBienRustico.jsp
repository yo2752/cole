<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Bien Rústico</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Bien Rústico:</span>
			</td>
		</tr>
	</table>
	<%@include file="../../../includes/erroresMasMensajes.jspf" %>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && bien.idBien != null}">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelIdBien">ID Bien:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="bien.idBien" id="idBienRusticoDto" size="15" disabled="true"/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden id="idBienRusticoHidden" name="bienRusticoDto.idBien"/>
		</s:else>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoInmueble">Tipo de Inmueble<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaTiposInmuebles(@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@Rustico)"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Inmueble" 
				    	name="bien.tipoInmueble.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmuebleRustico" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idufirBienRustico">IDUFIR/CRU:</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:textfield name="bien.idufir" id="idufirBienRustico" size="15" maxlength="14" 
	       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoGanaderia">Tipo Ganadería<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Ganaderia)"
					onblur="this.className='input2';" 
			    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Ganadería" 
			    	name="bien.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoGanaderia" 
			    	onchange="seleccionarUsoRustico('idTipoUsoRustico','GANADERIA');" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoCultivo">Tipo Cultivo<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Cultivo)"
					onblur="this.className='input2';" 
			    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Cultivo" 
			    	name="bien.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoCultivo" onchange="seleccionarUsoRustico('idTipoUsoRustico','CULTIVO');" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelOtros">Otros<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Otros)"
					onblur="this.className='input2';" 
			    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Cultivo" 
			    	name="bien.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoOtros" onchange="seleccionarUsoRustico('idTipoUsoRustico','OTROS');" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelSistemaExpl">Sistema de Explotación:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaSistemaExplotacion()"
					onblur="this.className='input2';" 
			    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Sistema de Explotación" 
			    	name="bien.sistemaExplotacion.idSistemaExplotacion" listKey="idSistemaExplotacion" listValue="descSistema" id="idSistemaExplotacionRustico" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelSuperficie">Superficie<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.superficie" id="idSuperficieRustico" size="15" onblur=" cambiarComa(this); this.className='input2';" 
	       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelUnidad">Unidad<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUnidadesMetricas()"
					onblur="this.className='input2';" 
			    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Unidad Métrica" 
			    	name="bien.unidadMetrica.unidadMetrica" listKey="unidadMetrica" listValue="descunidad" id="idUnidadMetricaRustico" />
			</td>
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="seccionRegistralBienRustico">Sección registral:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="bien.seccion" id="seccionRegistralBienRustico" size="11" maxlength="10" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaBienRustico">Número Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="bien.numeroFinca" id="numeroFincaBienRustico" size="11" maxlength="10" 
			  			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>				
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="subNumeroFincaBienRustico">Subnúmero Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="bien.subnumFinca" id="subNumeroFincaBienRustico" size="11" maxlength="10" 
			   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaDuplicadoBienRustico">Número Finca Duplicado:</label>
				</td>
				<td align="left" nowrap="nowrap" >	
					<s:textfield name="bien.numFincaDupl" id="numeroFincaDuplicadoBienRustico" size="11" maxlength="10" 
			    		onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>							
			</tr>							
			<tr>
				<td align="left" nowrap="nowrap">
					<label id="descripcionBienRusticoLabel" for="descripcionBienRustico">Descripción del bien inmueble:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3" >
					<s:textfield name="bien.descripcion" id="descripcionBienRustico" size="60" maxlength="60" 
			    		onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>		
			</tr>
		</tr>
	</table>
</div>
