<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<div id="bienesRusticos">
		<s:hidden id="tipoUsoRustico" name="bienRustico.usoRustico.tipoUso"/>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Bienes Inmuebles de Naturaleza Rústica situados en la Comunidad de Madrid</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			
			<s:hidden name="modeloDto.bienRustico.fechaAlta" />
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && remesa.bienRustico.idBien != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdBien">ID Bien:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="modeloDto.bienRustico.idBien" id="idBienRusticoDto" size="15" disabled="true"/>
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:hidden id="idBienRusticoHidden" name="modeloDto.bienRustico.idBien"/>
			</s:else>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoInmueble">Tipo de Inmueble<span class="naranja">*</span>:</label>
				</td>
				
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaTiposInmuebles(@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@Rustico)"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Inmueble" 
				    	name="modeloDto.bienRustico.tipoInmueble.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmuebleRustico" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="idufirBienRustico">IDUFIR/CRU:</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="modeloDto.bienRustico.idufir" id="idufirBienRustico" size="15" maxlength="14" 
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
				    	name="modeloDto.bienRustico.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoGanaderia" 
				    	onchange="seleccionarUsoRustico('idTipoUsoRustico','GANADERIA');" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoCultivo">Tipo Cultivo<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Cultivo)"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Cultivo" 
				    	name="modeloDto.bienRustico.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoCultivo" onchange="seleccionarUsoRustico('idTipoUsoRustico','CULTIVO');" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelOtros">Otros<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Otros)"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Otros" 
				    	name="modeloDto.bienRustico.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoOtros" onchange="seleccionarUsoRustico('idTipoUsoRustico','OTROS');" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelSistemaExpl">Sistema de Explotación<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaSistemaExplotacion()"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Sistema de Explotación" 
				    	name="modeloDto.bienRustico.sistemaExplotacion.idSistemaExplotacion" listKey="idSistemaExplotacion" listValue="descSistema" id="idSistemaExplotacionRustico" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelPorcentajeTransm">Porcentaje de Transmisión<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td>
		       					<s:textfield name="modeloDto.bienRustico.transmision" id="idPorcentajeTransmisionRustico" size="6" maxlength="6" ></s:textfield>
							</td>
							<td>
								<label class="labelFecha">%</label>
							</td>
						</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelValorDeclarado">Valor Declarado<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="modeloDto.bienRustico.valorDeclarado" id="idValorDeclaradoRustico" size="15" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelSuperficie">Superficie<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="modeloDto.bienRustico.superficie" id="idSuperficieRustico" size="15" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
				</td>
							<td align="left" nowrap="nowrap">
					<label for="labelUnidad">Unidad<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUnidadesMetricas()"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Unidad Métrica" 
				    	name="modeloDto.bienRustico.unidadMetrica.unidadMetrica" listKey="unidadMetrica" listValue="descunidad" id="idUnidadMetricaRustico" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="seccionRegistralBienRustico">Sección registral:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="modeloDto.bienRustico.seccion" id="seccionRegistralBienRustico" size="11" maxlength="10" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaBienRustico">Número Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="modeloDto.bienRustico.numeroFinca" id="numeroFincaBienRustico" size="11" maxlength="10" 
			  			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>				
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="subNumeroFincaBienRustico">Subnúmero Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="modeloDto.bienRustico.subnumFinca" id="subNumeroFincaBienRustico" size="11" maxlength="10" 
			   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaDuplicadoBienRustico">Número Finca Duplicado:</label>
				</td>
				<td align="left" nowrap="nowrap" >	
					<s:textfield name="modeloDto.bienRustico.numFincaDupl" id="numeroFincaDuplicadoBienRustico" size="11" maxlength="10" 
			    		onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>							
			</tr>							
			<tr>
				<td align="left" nowrap="nowrap">
					<label id="descripcionBienRusticoLabel" for="descripcionBienRustico">Descripción del bien inmueble:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3" >
					<s:textfield name="modeloDto.bienRustico.descripcion" id="descripcionBienRustico" size="60" maxlength="60" 
			    		onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>		
			</tr>
		</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Dirección</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && modeloDto.bienRustico.direccion.idDireccion != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdDir">Id dirección:</label>
					</td>
					<td>
						<s:textfield name="modeloDto.bienRustico.direccion.idDireccion" id="idDireccionRustico" size="25" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" disabled="true"/>
					</td>
				</tr>
			</s:if>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelProvincia">Provincia<span class="naranja">*</span>:</label>
				</td>
				<td>
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaProvincias()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
				    		name="modeloDto.bienRustico.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaBienRustico" 
							onchange="cargarListaMunicipiosCam('idProvinciaBienRustico','idMunicipioBienRustico');"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelMunicipio">Municipio<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
				    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioBienRustico"
						headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
						name="modeloDto.bienRustico.direccion.idMunicipio" onchange="obtenerCodigoPostalPorMunicipio('idProvinciaBienRustico', this, 'idCodPostalRustico');"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaMunicipiosPorProvincia(modeloDto,'RUSTICO')"
					/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelCodPostal">Cód. Postal<span class="naranja">*</span>:</label>
				</td>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="modeloDto.bienRustico.direccion.codPostal" id="idCodPostalRustico" size="5" maxlength="5" onblur="this.className='input2';" 
					       			onfocus="this.className='inputfocus';"/>
							</td>
							<td align="left" nowrap="nowrap">
		       					<input type="button" class="boton-persona" id="botonCorreos" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:abrirVentanaCorreosPopUp();"/>
		       				</td>
						</tr>
					</table>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelParaje">Paraje o sitio<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="modeloDto.bienRustico.paraje" id="idParajeRustico" size="25" maxlength="30" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelCodPoligono">Polígono<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="modeloDto.bienRustico.poligono" id="idPoligonoRustico" size="25" maxlength="30" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelParaje">Parcela<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="modeloDto.bienRustico.parcela" id="idParcelaRustico" size="25" maxlength="30" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelSubParcela">SubParcela:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="modeloDto.bienRustico.subParcela" id="idSubParcelaRustico" size="25" maxlength="30" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" valign="bottom">
					<label for="labelRefCatrastal">Referencia Catastral:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="modeloDto.bienRustico.refCatrastal" id="idRefCatrastalRustico" size="25" maxlength="20" onblur="this.className='input2';" 
					       			onfocus="this.className='inputfocus';"/>
		       				</td>
							<td align="left" nowrap="nowrap">
		       					<input type="button" class="boton-persona" id="botonCatastro" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:abrirVentanaCatastroPopUp();"/>
		       				</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">
	deshabilitarCamposUsoBienRustico();
</script>