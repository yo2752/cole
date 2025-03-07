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
			<%-- <s:hidden name="tramiteRegistro.inmueble.bien.tipoInmueble.idTipoInmueble" id="idTipoInmuebleUrbano"/> --%>
			<s:hidden name="tramiteRegistro.inmueble.bien.tipoInmueble.descTipoInmueble" id="descTipoInmuebleUrbano"/>
			<%-- <s:hidden name="tramiteRegistro.inmueble.bien.tipoInmueble.idTipoBien" value="URBANO" id="idTipoBienUrbano"/> --%>
			
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && tramiteRegistro.inmueble.bien.idBien != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdBien">ID Bien:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="tramiteRegistro.inmueble.bien.idBien" id="idBienUrbano" size="5" readonly="true"/>
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:hidden id="idBienUrbano" name="tramiteRegistro.inmueble.bien.idBien"/>
			</s:else>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoInmueble">Tipo Inmueble<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getListaTiposInmuebles(@org.gestoresmadrid.core.modelos.model.enumerados.TipoBien@Urbano)"
						onblur="this.className='input2';" onchange="habilitarVHabitual('idTipoInmuebleUrbano')"
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Tipo Inmueble" 
				    	name="tramiteRegistro.inmueble.bien.tipoInmueble.idTipoInmueble" listKey="idTipoInmueble" listValue="descTipoInmueble" id="idTipoInmuebleUrbano" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="idufirBienUrbano">IDUFIR/CRU<span class="naranja">*</span>:</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="tramiteRegistro.inmueble.bien.idufir" id="idufirBienUrbano" size="15" maxlength="14" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelSuperficieCons">Superf. Construcción:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.inmueble.bien.superficieConst" id="idSuperfConsUrbano" size="6" maxlength="6" onblur="this.className='input2';" 
		       						onfocus="this.className='inputfocus';" onkeypress="return validarNumeros(event)"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelAnioCons">Año. Construcción:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table style="width:20%">
						<tr>
							<td align="left">
								<s:textfield name="tramiteRegistro.inmueble.bien.fechaConstruccion.dia" id="diaBusquedaUrbanoIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="tramiteRegistro.inmueble.bien.fechaConstruccion.mes" id="mesBusquedaUrbanoIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="tramiteRegistro.inmueble.bien.fechaConstruccion.anio" id="anioBusquedaUrbanoIni"
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
								<s:textfield name="tramiteRegistro.inmueble.bien.refCatrastal" id="idRefCatrastalUrbano" size="25" maxlength="20" onblur="this.className='input2';" 
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
				    	onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Situación" 
				    	name="tramiteRegistro.inmueble.bien.situacion.idSituacion" listKey="idSituacion" listValue="descSituacion" id="idSituacionUrbano" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelAnioContrato">Año Contrato:</label>
				</td>		
				<td align="left" nowrap="nowrap">	
					<s:textfield name="tramiteRegistro.inmueble.bien.anioContratacion" id="idAnioContratacionUrbano" size="4" maxlength="4" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
		       	</td>
				<td align="left" nowrap="nowrap">
					<label for="labelVPO">V.P.O:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="tramiteRegistro.inmueble.bien.viviendaProtOficial" id="idVPOUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelDescalificado">Descalificado:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="tramiteRegistro.inmueble.bien.descalificado" id="idDescalificadoUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelPrecioMaxVenta">Precio Máx venta:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.inmueble.bien.precioMaximo" id="idPrecioMaximoUrbano" size="15" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelArrendamiento">Arrendamiento:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="tramiteRegistro.inmueble.bien.arrendamiento" id="idValorArrendamientoUrbano" onblur="this.className='input';" onfocus="this.className='inputfocus';"/>
				</td>
				
				<td align="left" nowrap="nowrap">
					<label for="labelVHabitual">Vivienda habitual:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:checkbox name="tramiteRegistro.inmueble.bien.viHabitual" id="idVHabitual"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="seccionRegistralBienUrbano">Sección registral<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="tramiteRegistro.inmueble.bien.seccion" id="seccionRegistralBienUrbano" size="11" maxlength="10" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaBienUrbano">Número Finca<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="tramiteRegistro.inmueble.bien.numeroFinca" id="numeroFincaBienUrbano" size="11" maxlength="10" 
			  			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>				
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="subNumeroFincaBienUrbano">Subnúmero Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="tramiteRegistro.inmueble.bien.subnumFinca" id="subNumeroFincaBienUrbano" size="11" maxlength="10" 
			   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaDuplicadoBienUrbano">Número Finca Duplicado:</label>
				</td>
				<td align="left" nowrap="nowrap" >	
					<s:textfield name="tramiteRegistro.inmueble.bien.numFincaDupl" id="numeroFincaDuplicadoBienUrbano" size="11" maxlength="10" 
			    		onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>							
			</tr>							
			<tr>
				<td align="left" nowrap="nowrap">
					<label id="descripcionBienUrbanoLabel" for="descripcionBienUrbano">Descripción del bien inmueble:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3" >
					<s:textfield name="tramiteRegistro.inmueble.bien.descripcion" id="descripcionBienUrbano" size="60" maxlength="60" 
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
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && tramiteRegistro.inmueble.bien.direccion.idDireccion != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdDir">Id direccion:</label>
					</td>
					<td>
						<s:textfield name="tramiteRegistro.inmueble.bien.direccion.idDireccion" id="idDireccionUrbano" size="15" readonly="true" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:hidden id="idDireccionUrbano" name="tramiteRegistro.inmueble.bien.direccion.idDireccion"/>
			</s:else>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelProvincia">Provincia<span class="naranja">*</span>:</label>
				</td>
				<td>
					<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getProvinciasBienes()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
				    		name="tramiteRegistro.inmueble.bien.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaBienUrbano" 
							onchange="cargarListaMunicipiosCam('idProvinciaBienUrbano','idMunicipioBienUrbano');"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelMunicipio">Municipio<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
				    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioBienUrbano"
						headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
						name="tramiteRegistro.inmueble.bien.direccion.idMunicipio"
						list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getMunicipiosInmueble(tramiteRegistro.inmueble)"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelTipoVia">Tipo Vía<span class="naranja">*</span>:</label>
				</td>
				<td>
				    <s:select  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
						list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListTipoViaRegistro()"
						headerKey="-1" headerValue="Seleccione Tipo Vía" listKey="key" listValue="name" 
						name="tramiteRegistro.inmueble.bien.direccion.idTipoVia"
						id="idTipoViaUrbano"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelNombre">Nombre Vía Pública<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.inmueble.bien.direccion.nombreVia" id="idNombreViaUrbano" size="25"  maxlength="100" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelCodPostal">Cod. Postal:</label>
				</td>
				<td>
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="tramiteRegistro.inmueble.bien.direccion.codPostal" id="idCodPostalUrbano" size="5" maxlength="5" onblur="this.className='input2';" 
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
					<label for="labelNumDireccion">Nº:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.inmueble.bien.direccion.numero" id="idNumeroViaUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelDupTri">Dup/Trip:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:select  name="tramiteRegistro.inmueble.bien.dupTri"  id="idDupTriUrbano"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getDupleTri()" 
			    		headerKey="" headerValue="Seleccione Dup/Trip" 
			    		onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" 
						listKey="valorEnum" listValue="nombreEnum" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelNLocal">Nº Local:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.inmueble.bien.direccion.numLocal" id="idNumLocalUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelNPiso">Piso:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.inmueble.bien.direccion.planta" id="idPisoUrbano" size="3" maxlength="3" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelPuerta">Puerta:</label>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<s:textfield name="tramiteRegistro.inmueble.bien.direccion.puerta" id="idPuertaUrbano" size="3" maxlength="2" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="labelNLocal">Escalera:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="tramiteRegistro.inmueble.bien.direccion.escalera" id="idEscaleraUrbano" size="10" maxlength="2" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
		</table>
	</div>
</div>
