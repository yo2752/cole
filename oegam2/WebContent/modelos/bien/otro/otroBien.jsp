<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<div id="otrosBienes">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Otros bienes</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<s:hidden name="modeloDto.otroBien.tipoInmueble.idTipoInmueble" value="OT" id="idTipoInmuebleOtro"/>
			
			<tr>
				<s:hidden name="modeloDto.otroBien.fechaAlta" />
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && remesa.otroBien.idBien != null}">
					<td align="left" nowrap="nowrap">
						<label for="labelIdBien">ID Bien:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="modeloDto.otroBien.idBien" id="idOtroBienDto" size="15" disabled="true"/>
					</td>
				</s:if>
				<s:else>
					<s:hidden id="idOtroBienHidden" name="modeloDto.otroBien.idBien"/>
				</s:else>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label id="lblIdRefCatrastalOtro" for="labelRefCatrastal">Referencia catastral<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="modeloDto.otroBien.refCatrastal" id="idRefCatrastalOtro" size="25" maxlength="20" onblur="this.className='input2';" 
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
					<label for="labelValorDeclarado">Valor declarado<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="modeloDto.otroBien.valorDeclarado" id="idValorDeclaradoOtro" size="15" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" onkeypress="return validarDecimal(event, 2);"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelPorcentajeTrans">Porcentaje transmisión<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td>
								<s:textfield name="modeloDto.otroBien.transmision" id="idPorcentajeTransOtro" size="6" maxlength="5" onblur="this.className='input2';" 
		       						onfocus="this.className='inputfocus';" onkeyup="return validarPorcentaje(this);" onkeypress="return validarDecimal(event, 2);"/>
							</td>
							<td>
								<label class="labelFecha">%</label>
							</td>
						</tr>
					</table>
				</td>
				<s:if test="modeloDto.modelo.modelo == '601'">
					<td align="left" nowrap="nowrap">
						<label for="labelValorTasacion" id="lblIdValorTasacion">Valor de tasación<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="modeloDto.otroBien.valorTasacion" id="idValorTasacion" size="15" onblur="this.className='input2';" 
			       			onfocus="this.className='inputfocus';" onkeypress="return validarDecimal(event, 2);"/>
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="idufirBienOtro">IDUFIR/CRU:</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="modeloDto.otroBien.idufir" id="idufirBienOtro" size="15" maxlength="14" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && modeloDto.otroBien.direccion.idDireccion != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdDir">Id dirección:</label>
					</td>
					<td>
						<s:textfield name="modeloDto.otroBien.direccion.idDireccion" id="idDireccionOtro" size="15" readonly="true" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" />
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:hidden id="idDireccionOtro" name="modeloDto.otroBien.direccion.idDireccion"/>
			</s:else>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelProvincia">Provincia:</label>
				</td>
				<td>
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaProvincias()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
				    		name="modeloDto.otroBien.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaBienOtro" 
							onchange="cargarListaMunicipiosCam('idProvinciaBienOtro','idMunicipioBienOtro');"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelMunicipio">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioBienOtro"
						headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
						name="modeloDto.otroBien.direccion.idMunicipio"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaMunicipiosPorProvincia(modeloDto,'OTRO')"
					/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="seccionRegistralBienOtro">Sección registral:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="modeloDto.otroBien.seccion" id="seccionRegistralBienOtro" size="11" maxlength="10" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaBienOtro">Número Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="modeloDto.otroBien.numeroFinca" id="numeroFincaBienOtro" size="11" maxlength="10" 
			  			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>				
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="subNumeroFincaBienOtro">Subnúmero Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="modeloDto.otroBien.subnumFinca" id="subNumeroFincaBienOtro" size="11" maxlength="10" 
			   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaDuplicadoBienOtro">Número Finca Duplicado:</label>
				</td>
				<td align="left" nowrap="nowrap" >	
					<s:textfield name="modeloDto.otroBien.numFincaDupl" id="numeroFincaDuplicadoBienOtro" size="11" maxlength="10" 
			    		onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>							
			</tr>							
			<tr>
				<td align="left" nowrap="nowrap">
					<label id="descripcionBienOtroLabel" for="descripcionBienOtro">Descripción del bien inmueble:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3" >
					<s:textfield name="modeloDto.otroBien.descripcion" id="descripcionBienOtro" size="60" maxlength="60" 
			    		onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>		
			</tr>
		</table>
	</div>
</div>
