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
			<tr>
				<s:hidden name="remesa.otroBienDto.fechaAlta" />
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && remesa.otroBien.idBien != null}">
					<td align="left" nowrap="nowrap">
						<label for="labelIdBien">ID Bien:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="remesa.otroBienDto.idBien" id="idOtroBienDto" size="15" disabled="true"/>
					</td>
				</s:if>
				<s:else>
					<s:hidden id="idOtroBienHidden" name="remesa.otroBienDto.idBien"/>
				</s:else>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelRefCatrastal">Referencia catastral<span class="naranja">*</span>:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<table>
						<tr>
							<td align="left" nowrap="nowrap">
								<s:textfield name="remesa.otroBienDto.refCatrastal" id="idRefCatrastalOtro" size="25" maxlength="20" onblur="this.className='input2';" 
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
					<s:textfield name="remesa.otroBienDto.valorDeclarado" id="idValorDeclaradoOtro" size="15" onblur="this.className='input2';" 
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
								<s:textfield name="remesa.otroBienDto.transmision" id="idPorcentajeTransOtro" size="6" maxlength="5" onblur="this.className='input2';" 
		       						onfocus="this.className='inputfocus';" onkeyup="return validarPorcentaje(this);" onkeypress="return validarDecimal(event, 2);"/>
							</td>
							<td>
								<label class="labelFecha">%</label>
							</td>
						</tr>
					</table>
				</td>
				<s:if test="remesa.modelo.modelo == '601'">
					<td align="left" nowrap="nowrap">
						<label for="labelValorTasacion">Valor de tasación<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="remesa.otroBienDto.valorTasacion" id="idValorTasacion" size="15" onblur="this.className='input2';" 
			       			onfocus="this.className='inputfocus';" onkeypress="return controlNumeros(event);"/>
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="idufirBienOtro">IDUFIR/CRU:</label>
					<img src="img/botonDameInfo.gif" alt="Info" title="Identificador &uacute;nico de finca registral"/>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.otroBienDto.idufir" id="idufirBienOtro" size="15" maxlength="14" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && remesa.otroBienDto.direccion.idDireccion != null}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelIdDir">Id dirección:</label>
					</td>
					<td>
						<s:textfield name="remesa.otroBienDto.direccion.idDireccion" id="idDireccionOtro" size="15" readonly="true" onblur="this.className='input2';" 
		       			onfocus="this.className='inputfocus';" />
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:hidden id="idDireccionOtro" name="remesa.otroBienDto.direccion.idDireccion"/>
			</s:else>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelProvincia">Provincia:</label>
				</td>
				<td>
					<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaProvincias()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Provincia" 
				    		name="remesa.otroBienDto.direccion.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaBienOtro" 
							onchange="cargarListaMunicipiosCam('idProvinciaBienOtro','idMunicipioBienOtro');"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelMunicipio">Municipio:</label>
				</td>
				<td align="left" nowrap="nowrap">
				    <s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioBienOtro"
						headerKey="-1"	headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
						name="remesa.otroBienDto.direccion.idMunicipio"
						list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaMunicipiosPorProvincia(remesa,'OTRO')"
					/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="seccionRegistralBienOtro">Sección registral:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.otroBienDto.seccion" id="seccionRegistralBienOtro" size="11" maxlength="10" 
		       			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaBienOtro">Número Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.otroBienDto.numeroFinca" id="numeroFincaBienOtro" size="11" maxlength="10" 
			  			onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>
			</tr>				
			<tr>
				<td align="left" nowrap="nowrap" >
					<label for="subNumeroFincaBienOtro">Subnúmero Finca:</label>
				</td>
				<td align="left" nowrap="nowrap" >
					<s:textfield name="remesa.otroBienDto.subnumFinca" id="subNumeroFincaBienOtro" size="11" maxlength="10" 
			   			onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>
				<td align="left" nowrap="nowrap" >
					<label for="numeroFincaDuplicadoBienOtro">Número Finca Duplicado:</label>
				</td>
				<td align="left" nowrap="nowrap" >	
					<s:textfield name="remesa.otroBienDto.numFincaDupl" id="numeroFincaDuplicadoBienOtro" size="11" maxlength="10" 
			    		onblur="this.className='input';" onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
				</td>							
			</tr>							
			<tr>
				<td align="left" nowrap="nowrap">
					<label id="descripcionBienOtroLabel" for="descripcionBienOtro">Descripción del bien inmueble:</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="3" >
					<s:textfield name="remesa.otroBienDto.descripcion" id="descripcionBienOtro" size="60" maxlength="60" 
			    		onblur="this.className='input';" onfocus="this.className='inputfocus';" />
				</td>		
			</tr>
		</table>
	</div>
</div>
