<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<div id="bienesUrbanos">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Bienes Inmuebles de Naturaleza Urbana situados en la Comunidad de Madrid</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			
			<s:hidden name="remesa.bienUrbanoDto.fechaAlta" />
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && remesa.bienUrbanoDto.idBien != null}"><s:hidden name="remesa.bienUrbano.direccion.idDireccion"/>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdBien">ID Bien:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="remesa.bienUrbanoDto.idBien" id="idBienUrbanoDto" size="15" disabled="true"/>
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:hidden id="idBienUrbanoHidden" name="remesa.bienUrbanoDto.idBien"/>
			</s:else>
			
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoInmueble">Tipo Inmueble<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaTiposInmuebles(@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@Urbano)"
						onblur="this.className='input2';" onchange="habilitarVHabitual('idTipoInmuebleUrbano')"
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Inmueble" 
				    	name="remesa.bienUrbanoDto.tipoInmueble.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmuebleUrbano" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="idufirBienUrbano">IDUFIR/CRU:</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.bienUrbanoDto.idufir" id="idufirBienUrbano" size="15" maxlength="14" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelSuperficieCons">Superf. Construcción:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienUrbanoDto.superficieConst" id="idSuperfConsUrbano" size="6" maxlength="6" onblur="this.className='input2';" 
		       						onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelAnioCons">Año. Construcción:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table style="width:20%">
						<tr>
							<td align="left">
								<s:textfield name="remesa.bienUrbanoDto.fechaConstruccion.dia" id="diaBusquedaUrbanoIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="remesa.bienUrbanoDto.fechaConstruccion.mes" id="mesBusquedaUrbanoIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="remesa.bienUrbanoDto.fechaConstruccion.anio" id="anioBusquedaUrbanoIni"
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
					<label for="labelRefCatrastal">Referencia Catrastal<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="remesa.bienUrbanoDto.refCatrastal" id="idRefCatrastalUrbano" size="25" maxlength="30" onblur="this.className='input2';" 
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
					<label for="labelSituacion">Situacion:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaSituacion()"
						onblur="this.className='input2';" 
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Situación" 
				    	name="remesa.bienUrbanoDto.situacion.idSituacion" listKey="idSituacion" listValue="descSituacion" id="idSituacionUrbano" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelPorcentajeTrans">Porcentaje Transmisión<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td>
								<s:textfield name="remesa.bienUrbanoDto.transmision" id="idPorcentajeTransUrbano" size="6" maxlength="6" onblur="this.className='input2';" 
		       						onfocus="this.className='inputfocus';"/>
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
					<s:textfield name="remesa.bienUrbanoDto.valorDeclarado" id="idValorDeclaradoUrbano" size="15" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelAnioContrato">Año Contrato<span class="naranja">*</span>:</label>
				</td>		
				<td align="left" nowrap="nowrap">	
					<s:textfield name="remesa.bienUrbanoDto.anioContratacion" id="idAnioContratacionUrbano" size="4" maxlength="4" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
		       	</td>
				<td align="left" nowrap="nowrap">
					<label for="labelVPO">V.P.O:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="remesa.bienUrbanoDto.viviendaProtOficial" id="idVPOUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelDescalificado">Descalificado:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="remesa.bienUrbanoDto.descalificado" id="idDescalificadoUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelPrecioMaxVenta">Precio Máx venta<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienUrbanoDto.precioMaximo" id="idPrecioMaximoUrbano" size="15" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelArrendamiento">Arrendamiento<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="remesa.bienUrbanoDto.arrendamiento" id="idValorArrendamientoUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelVHabitual">Vivienda habitual<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="remesa.bienUrbanoDto.viHabitual" id="idVHabitual" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="seccionRegistralBienUrbano">Sección registral:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.bienUrbanoDto.seccion" id="seccionRegistralBienUrbano" size="11" maxlength="10" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaBienUrbano">Número Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.bienUrbanoDto.numeroFinca" id="numeroFincaBienUrbano" size="11" maxlength="10" 
			  			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>				
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="subNumeroFincaBienUrbano">Subnúmero Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.bienUrbanoDto.subnumFinca" id="subNumeroFincaBienUrbano" size="11" maxlength="10" 
			   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaDuplicadoBienUrbano">Número Finca Duplicado:</label>
				</td>
				<td align="left" nowrap="nowrap" >	
					<s:textfield name="remesa.bienUrbanoDto.numFincaDupl" id="numeroFincaDuplicadoBienUrbano" size="11" maxlength="10" 
			    		onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>							
			</tr>							
			<tr>
				<td align="left" nowrap="nowrap">
					<label id="descripcionBienUrbanoLabel" for="descripcionBienUrbano">Descripción del bien inmueble:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3" >
					<s:textfield name="remesa.bienUrbanoDto.descripcion" id="descripcionBienUrbano" size="60" maxlength="60" 
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
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && remesa.bienUrbanoDto.direccion.idDireccion != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdDir">Id dirección:</label>
					</td>
					<td>
						<s:textfield name="remesa.bienUrbanoDto.direccion.idDireccion" id="idDireccionUrbano" size="25" onblur="this.className='input2';" 
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
				    		name="remesa.bienUrbanoDto.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaBienUrbano" 
							onchange="cargarListaMunicipiosCam('idProvinciaBienUrbano','idMunicipioBienUrbano'); 
								cargarListaTipoViaCam('idProvinciaBienUrbano','idTipoViaUrbano');"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelMunicipio">Municipio<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
				    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioBienUrbano"
						headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
						name="remesa.bienUrbanoDto.direccion.idMunicipio" onchange="obtenerCodigoPostalPorMunicipio('idProvinciaBienUrbano', this, 'idCodPostalUrbano');"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaMunicipiosPorProvincia(remesa,'URBANO')"
					/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoVia">Tipo Vía<span class="naranja">*</span>:</label>
				</td>
				<td>
					<s:select  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaTiposVias(remesa,'URBANO')"
						headerKey="-1" headerValue="Seleccione Tipo Via" listKey="idTipoViaCam" listValue="nombre" 
						name="remesa.bienUrbanoDto.direccion.idTipoVia"
						id="idTipoViaUrbano"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelNombre">Nombre Vía Pública<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienUrbanoDto.direccion.nombreVia" id="idNombreViaUrbano" size="25"  maxlength="100" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
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
								<s:textfield name="remesa.bienUrbanoDto.direccion.codPostal" id="idCodPostalUrbano" size="5" maxlength="5" onblur="this.className='input2';" 
					       			onfocus="this.className='inputfocus';"/>
							</td>
							<td align="left" nowrap="nowrap">
		       					<input type="button" class="boton-persona" id="botonCorreos" style="background-image: url(img/mostrar.gif);height:20px;width:20px;" 
									onclick="javascript:abrirVentanaCorreosPopUp();"/>
		       				</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelNumDireccion">Nº<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienUrbanoDto.direccion.numero" id="idNumeroViaUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelDupTri">Dup/Trip:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:select  name="remesa.bienUrbanoDto.dupTri"  id="idDupTriUrbano"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getDupleTri()" 
			    		headerKey="" headerValue="Seleccione Dup/Trip" 
			    		onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" 
						listKey="valorEnum" listValue="nombreEnum" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelNLocal">Nº Local:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienUrbanoDto.direccion.numLocal" id="idNumLocalUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelNPiso">Piso:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienUrbanoDto.direccion.planta" id="idPisoUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelPuerta">Puerta:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:textfield name="remesa.bienUrbanoDto.direccion.puerta" id="idPuertaUrbano" size="3" maxlength="2" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelNLocal">Escalera:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="remesa.bienUrbanoDto.direccion.escalera" id="idEscaleraUrbano" size="10" maxlength="2" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
		</table>
	</div>
	
</div>
