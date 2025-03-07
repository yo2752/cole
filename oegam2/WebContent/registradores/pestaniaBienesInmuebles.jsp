<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div id="inmuebles">

	<table class="subtitulo" align="left" cellspacing="0">
		<tbody>
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Datos de escritura</td>
				<s:if test="tramiteRegistro.idTramiteRegistro">
					<td  align="right">
						<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  					onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegistro.idTramiteRegistro}"/>);" title="Ver evolución"/>
					</td>
				</s:if>
			</tr>
		</tbody>
	</table>
	
	<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
		<tr>
			<td class="tabular" >
				<span class="titulo">Bienes Inmuebles</span>
			</td>
		</tr>
	</table>
	


	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		<div>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr> 
					<td align="left" nowrap="nowrap" width="5%">
						<label for="labelTipoInmueble">Tipo Bien:</label>
					</td>
					<td align="left" nowrap="nowrap" width="20%">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesBienes@getInstance().getTipoBien()"
							onblur="this.className='input2';" 
					    	onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo Bien" 
					    	listKey="valorEnum" listValue="nombreEnum" name="tramiteRegistro.inmueble.bien.tipoInmueble.idTipoBien" id="idTipoBien" 
					    	onchange="limpiarDiv('formularioInmuebles');mostrarDivsTipoBien();"/>
					</td>
	
					<td align="left" width="20%">  
						<input type="button" value="Buscar Bien" class="boton" onclick="javascript:buscarBienes();" />
					</td>
					<td align="left"> 
						<img id="loading6Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif"> 
					</td>
	
				</tr>
			</table>
		</div>

		<div id="formularioInmuebles">	
			<jsp:include page="formularioInmueble.jsp" flush="false"></jsp:include>
		</div>
		
	</s:if>
	
	&nbsp;
	<s:if test="tramiteRegistro.inmuebles && tramiteRegistro.inmuebles.size > 0">
		<div id="busqueda" class="busqueda">
			<table border="0" class="tablaformbasicaTC">
				<tr>
					<td colspan="2">
						<div id="resultado" style="width: 100%; background-color: transparent;">
							<table class="subtitulo" cellSpacing="0" style="width: 97%;">
								<tr>
									<td style="width: 100%; text-align: center;">Lista de inmuebles</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">		
						<div class="divScroll">
							<display:table name="tramiteRegistro.inmuebles" excludedParams="*" style="width:97%" class="tablacoin" uid="row" summary="Listado de Bienes" cellspacing="0" cellpadding="0">
								<display:column property="bien.tipoInmueble.idTipoBien" title="Tipo Bien" style="text-align:left;"/>
								<display:column property="bien.idufir" title="IDUFIR/CRU" style="text-align:left;"/>
								<display:column property="bien.direccion.nombreProvincia" title="Provincia" style="text-align:left;"/>
								<display:column property="bien.direccion.nombreMunicipio" title="Municipio" style="text-align:left;"/>
								<display:column property="bien.seccion" title="Sección" style="text-align:right;"/>
								<display:column property="bien.numeroFinca" title="Nº Finca" style="text-align:right;"/>
								<display:column property="bien.subnumFinca" title="Sub. Nº Finca" style="text-align:right;"/>
								<display:column property="bien.numFincaDupl" title="Nº Finca Dup." style="text-align:right;"/>
									
								<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
					  				<display:column title="Eliminar" style="width:8%;text-align:center;">
					  					<a onclick="" href="javascript:eliminarInmueble(${row.idInmueble})">Eliminar</a>
					  				</display:column>
					  				<display:column title="Modificar" style="width:8%;text-align:center;">
					  					<a onclick="" href="javascript:modificarInmueble(${row.idInmueble})">Modificar</a>
					  				</display:column>
					  			</s:if>
							</display:table>
						</div>	
					</td>
				</tr>
			</table>
		</div>
	</s:if>
	<s:else>
		<table width="95%" class="tablaformbasicaTC">
			<tr>
				<td>
					<span>No existen inmuebles</span>
				</td>
			</tr>
		</table>	
	</s:else>
	&nbsp;
	&nbsp;
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
		<div id="botonBusqueda">
			<table width="100%">
				<tr>
					<td>
						<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistro.estado,tramiteRegistro.numReg)">
							<input type="button" class="boton" value="Guardar" style="size: 100%; TEXT-ALIGN: center;" onclick="javascript:guardarEscritura();" id="botonGuardarEscritura"/>
						</s:if>
					</td>
					<td>
						<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
					</td>
				</tr>
			</table>
		</div>
	</s:if>
</div>

 <div id = "divEmergenteBienes" style="position: fixed; z-index: 999; height: 100%; width: 100%; top: 0; left:0;
 							background-color: #f4f4f4; filter: alpha(opacity=60); opacity: 0.6; -moz-opacity: 0.8;display:none">
</div> 
	
