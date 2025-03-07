<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Bien Urbano</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Bien Urbano:</span>
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
					<s:textfield name="bien.idBien" id="idBienUrbanoDto" size="15" readonly="true"/>
				</td>
			</tr>
		</s:if>
		<s:else>
			<s:hidden id="idBienUrbanoHidden" name="bien.idBien"/>
		</s:else>
		
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTipoInmueble">Tipo Inmueble<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaTiposInmuebles(@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@Urbano)"
					onblur="this.className='input2';" onchange="habilitarVHabitual('idTipoInmuebleUrbano')"
			    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Inmueble" 
			    	name="bien.tipoInmueble.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmuebleUrbano" />
			</td>
			<td align="left" nowrap="nowrap">
				<label for="idufirBienUrbano">IDUFIR/CRU:</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:textfield name="bien.idufir" id="idufirBienUrbano" size="15" maxlength="14" 
	       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelSuperficieCons">Superf. Construcción:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.superficieConst" id="idSuperfConsUrbano" size="6" maxlength="6" onblur="this.className='input2';" 
	       						onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelAnioCons">Año. Construcción:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table style="width:20%">
					<tr>
						<td align="left">
							<s:textfield name="bien.fechaConstruccion.dia" id="diaBusquedaUrbanoIni"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="bien.fechaConstruccion.mes" id="mesBusquedaUrbanoIni" onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
						</td>
						<td align="left">
							<label class="labelFecha">/</label>
						</td>
						<td align="left">
							<s:textfield name="bien.fechaConstruccion.anio" id="anioBusquedaUrbanoIni"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
						</td>
						<td align="left">
							<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaUrbanoIni, document.formData.mesBusquedaUrbanoIni, document.formData.diaBusquedaUrbanoIni);return false;" 
    							title="Seleccionar fecha">
    							<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
    						</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRefCatrastal">Referencia Catastral:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td align="left" nowrap="nowrap">
							<s:textfield name="bien.refCatrastal" id="idRefCatrastalUrbano" size="25" maxlength="30" onblur="this.className='input2';" 
				       						onfocus="this.className='inputfocus';"/>
						</td>
						<td align="left" nowrap="nowrap">
	       					<input type="button" class="boton-persona" id="botonCatastro" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
								onclick="javascript:abrirVentanaCatastroPopUp();"/>
	       				</td>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelSituacion">Situación:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaSituacion()"
					onblur="this.className='input2';" 
			    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Situacion" 
			    	name="bien.situacion.idSituacion" listKey="idSituacion" listValue="descSituacion" id="idSituacionUrbano" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelAnioContrato">Año Contrato:</label>
			</td>		
			<td align="left" nowrap="nowrap">	
				<s:textfield name="bien.anioContratacion" id="idAnioContratacionUrbano" size="4" maxlength="4" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
	       	</td>
			<td align="left" nowrap="nowrap">
				<label for="labelVPO">V.P.O:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox name="bien.viviendaProtOficial" id="idVPOUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelDescalificado">Descalificado:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox name="bien.descalificado" id="idDescalificadoUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelPrecioMaxVenta">Precio Máx venta:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield name="bien.precioMaximo" id="idPrecioMaximoUrbano" size="15" onblur="this.className='input2';" 
	       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelArrendamiento">Arrendamiento<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox name="bien.arrendamiento" id="idValorArrendamientoUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>		
			<td align="left" nowrap="nowrap">
				<label for="labelVHabitual">Vivienda habitual<span class="naranja">*</span>:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:checkbox name="bien.viHabitual" id="idVHabitual" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="seccionRegistralBienUrbano">Sección registral:</label>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:textfield name="bien.seccion" id="seccionRegistralBienUrbano" size="11" maxlength="10" 
	       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
			</td>
			<td align="left" nowrap="nowrap" >
				<label for="numeroFincaBienUrbano">Número Finca:</label>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:textfield name="bien.numeroFinca" id="numeroFincaBienUrbano" size="11" maxlength="10" 
		  			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
			</td>
		</tr>				
		<tr>
			<td align="left" nowrap="nowrap" >
				<label for="subNumeroFincaBienUrbano">Subnúmero Finca:</label>
			</td>
			<td align="left" nowrap="nowrap" >
				<s:textfield name="bien.subnumFinca" id="subNumeroFincaBienUrbano" size="11" maxlength="10" 
		   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
			</td>
			<td align="left" nowrap="nowrap" >
				<label for="numeroFincaDuplicadoBienUrbano">Número Finca Duplicado:</label>
			</td>
			<td align="left" nowrap="nowrap" >	
				<s:textfield name="bien.numFincaDupl" id="numeroFincaDuplicadoBienUrbano" size="11" maxlength="10" 
		    		onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
			</td>							
		</tr>							
		<tr>
			<td align="left" nowrap="nowrap">
				<label id="descripcionBienUrbanoLabel" for="descripcionBienUrbano">Descripción del bien inmueble:</label>
			</td>
			<td align="left" nowrap="nowrap" colspan="3" >
				<s:textfield name="bien.descripcion" id="descripcionBienUrbano" size="60" maxlength="60" 
		    		onblur="this.className='input';" onfocus="this.className='inputfocus';" />
			</td>		
		</tr>
	</table>
</div>
