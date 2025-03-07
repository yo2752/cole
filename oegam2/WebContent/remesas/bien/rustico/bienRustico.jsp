<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<div id="bienesRusticos">
		<s:hidden id="tipoUsoRustico" name="bienRusticoDto.usoRustico.tipoUso"/>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Bienes Inmuebles de Naturaleza Rústica situados en la Comunidad de Madrid</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			
			<s:hidden name="remesa.bienRusticoDto.fechaAlta" />
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && remesa.bienRusticoDto.idBien != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdBien">ID Bien:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="remesa.bienRusticoDto.idBien" id="idBienRusticoDto" size="15" disabled="true"/>
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
				    	name="remesa.bienRusticoDto.tipoInmueble.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmuebleRustico" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="idufirBienRustico">IDUFIR/CRU:</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.bienRusticoDto.idufir" id="idufirBienRustico" size="15" maxlength="14" 
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
				    	name="remesa.bienRusticoDto.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoGanaderia" 
				    	onchange="seleccionarUsoRustico('idTipoUsoRustico','GANADERIA');" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoCultivo">Tipo Cultivo<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUsoRusticoPorTipo(@org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico@Cultivo)"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Cultivo" 
				    	name="remesa.bienRusticoDto.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoCultivo" onchange="seleccionarUsoRustico('idTipoUsoRustico','CULTIVO');" />
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
				    	name="remesa.bienRusticoDto.usoRustico.idUsoRustico" listKey="idUsoRustico" listValue="descUsoRustico" id="idUsoRusticoOtros" onchange="seleccionarUsoRustico('idTipoUsoRustico','OTROS');" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelSistemaExpl">Sistema de Explotación<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaSistemaExplotacion()"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Sistema de Explotación" 
				    	name="remesa.bienRusticoDto.sistemaExplotacion.idSistemaExplotacion" listKey="idSistemaExplotacion" listValue="descSistema" id="idSistemaExplotacionRustico" />
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
		       					<s:textfield name="remesa.bienRusticoDto.transmision" id="idPorcentajeTransmisionRustico" size="6" maxlength="6" ></s:textfield>
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
					<s:textfield name="remesa.bienRusticoDto.valorDeclarado" id="idValorDeclaradoRustico" size="15" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelSuperficie">Superficie<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienRusticoDto.superficie" id="idSuperficieRustico" size="15" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
				</td>
							<td align="left" nowrap="nowrap">
					<label for="labelUnidad">Unidad<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaUnidadesMetricas()"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Unidad Métrica" 
				    	name="remesa.bienRusticoDto.unidadMetrica.unidadMetrica" listKey="unidadMetrica" listValue="descunidad" id="idUnidadMetricaRustico" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="seccionRegistralBienRustico">Sección registral:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.bienRusticoDto.seccion" id="seccionRegistralBienRustico" size="11" maxlength="10" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaBienRustico">Número Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.bienRusticoDto.numeroFinca" id="numeroFincaBienRustico" size="11" maxlength="10" 
			  			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>				
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="subNumeroFincaBienRustico">Subnúmero Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.bienRusticoDto.subnumFinca" id="subNumeroFincaBienRustico" size="11" maxlength="10" 
			   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaDuplicadoBienRustico">Número Finca Duplicado:</label>
				</td>
				<td align="left" nowrap="nowrap" >	
					<s:textfield name="remesa.bienRusticoDto.numFincaDupl" id="numeroFincaDuplicadoBienRustico" size="11" maxlength="10" 
			    		onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>							
			</tr>							
			<tr>
				<td align="left" nowrap="nowrap">
					<label id="descripcionBienRusticoLabel" for="descripcionBienRustico">Descripción del bien inmueble:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3" >
					<s:textfield name="remesa.bienRusticoDto.descripcion" id="descripcionBienRustico" size="60" maxlength="60" 
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
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && remesa.bienRusticoDto.direccion.idDireccion != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdDir">Id direccion:</label>
					</td>
					<td>
						<s:textfield name="remesa.bienRusticoDto.direccion.idDireccion" id="idDireccionRustico" size="25" onblur="this.className='input2';" 
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
				    		name="remesa.bienRusticoDto.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaBienRustico" 
							onchange="cargarListaMunicipiosCam('idProvinciaBienRustico','idMunicipioBienRustico');"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelMunicipio">Municipio<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
				    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioBienRustico"
						headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
						name="remesa.bienRusticoDto.direccion.idMunicipio" onchange="obtenerCodigoPostalPorMunicipio('idProvinciaBienRustico', this, 'idCodPostalRustico');"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaMunicipiosPorProvincia(remesa,'RUSTICO')"
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
								<s:textfield name="remesa.bienRusticoDto.direccion.codPostal" id="idCodPostalRustico" size="5" maxlength="5" onblur="this.className='input2';" 
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
					<s:textfield name="remesa.bienRusticoDto.paraje" id="idParajeRustico" size="25" maxlength="30" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelCodPoligono">Polígono<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienRusticoDto.poligono" id="idPoligonoRustico" size="25" maxlength="30" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelParaje">Parcela<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienRusticoDto.parcela" id="idParcelaRustico" size="25" maxlength="30" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelSubParcela">SubParcela:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienRusticoDto.subParcela" id="idSubParcelaRustico" size="25" maxlength="30" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelRefCatrastal">Referencia Catrastal:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="remesa.bienRusticoDto.refCatrastal" id="idRefCatrastalRustico" size="25" maxlength="30" onblur="this.className='input2';" 
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