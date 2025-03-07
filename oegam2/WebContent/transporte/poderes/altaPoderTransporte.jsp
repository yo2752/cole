<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/transporte/poderes/poderesTransporte.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Poder Transporte</span>
			</td>
		</tr>
	</table>
</div>
<div>
	<iframe width="174" 
		height="189" 
		name="gToday:normal:agenda.js" 
		id="gToday:normal:agenda.js" 
		src="calendario/ipopeng.htm" 
		scrolling="no" 
		frameborder="0" 
		style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresMasMensajes.jspf" %>
		<s:hidden name="poderTransporte.idPoderTransporte"/>
		<s:hidden name="poderTransporte.fechaAlta"/>
		<div class="contenido">	
			<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Poderdante</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNifPoderdante">N.I.F<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap" width="24%">
						<s:textfield id="idNifPoderdante" name="poderTransporte.nifPoderdante"  onblur="this.className='input2';calculaLetraNIF(this)" 
		       				onfocus="this.className='inputfocus';" style="text-transform : uppercase" size="9" maxlength="9"/>
			    	</td>
		       		<td align="left" nowrap="nowrap">
		       			<label for="labelPrimerApePoderdante">Primer Apellido<span class="naranja">*</span>:</label>
		       		</td>
		       		
		       		<td align="left" nowrap="nowrap">
		       			<s:textfield id="idPrimerApePoderdante"  name="poderTransporte.apellido1Poderdante"  
			       			onblur="this.className='input2';" onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;"
			       			size="65"/>
		       		</td> 	       			
		      	</tr>
		      	<tr>        	       			
		   			<td align="left" nowrap="nowrap">
		   				<label for="labelSegundoApePoderdante">Segundo Apellido:</label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">
		   				<s:textfield id="idSegundoApePoderdante" name="poderTransporte.apellido2Poderdante" onblur="this.className='input2';" 
			   				onfocus="this.className='inputfocus';" size="45" maxlength="100"/>
		   			</td>
		        	       			
		   			<td align="left" nowrap="nowrap">
		   				<label for="labelNombrePoderdante">Nombre<span class="naranja">*</span>:</label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap" colspan="2">
		   				<s:textfield id="idNombrePoderdante" name="poderTransporte.nombrePoderdante" onblur="this.className='input2';" 
			   				onfocus="this.className='inputfocus';" size="20" maxlength="100"/>
		   			</td>
		      	</tr>
		     </table>
		     <table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Empresa</td>
				</tr>
			</table>
			<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNifEmpresa">N.I.F<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap" width="24%">
						<s:textfield id="idNifEmpresa" name="poderTransporte.nifEmpresa"  onblur="this.className='input2';calculaLetraNIF(this)" 
		       				onfocus="this.className='inputfocus';" style="text-transform : uppercase" size="9" maxlength="9"/>
			    	</td>
		       		<td align="left" nowrap="nowrap">
		       			<label for="labelNombreEmpresa">Nombre Empresa<span class="naranja">*</span>:</label>
		       		</td>
		       		
		       		<td align="left" nowrap="nowrap">
		       			<s:textfield id="idNombreEmpresa"  name="poderTransporte.nombreEmpresa"  onblur="this.className='input2';" 
		       				onfocus="this.className='inputfocus';" maxlength="100" cssStyle="width:18em;" size="65"/>
		       		</td> 	       			
		      	</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelProvinciaEmpresa">Provincia<span class="naranja">*</span>:</label>
					</td>
					<td>
						<s:select list="@org.gestoresmadrid.oegam2.transporte.utiles.UtilesVistaPoderesTransporte@getInstance().getListaProvincias()" 
								onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Provincia" 
					    		name="poderTransporte.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvinciaEmpresa" 
								onchange="cargarListaMunicipios('idProvinciaEmpresa','idMunicipioEmpresa'); 
								cargarListaTipoVia('idProvinciaEmpresa','idTipoViaEmpresa');
								borrarComboPueblo('idPuebloEmpresa');"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMunicipioEmpresa">Municipio<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:select onblur="this.className='input2';" onfocus="this.className='inputfocus';" id="idMunicipioEmpresa"
							list="@org.gestoresmadrid.oegam2.transporte.utiles.UtilesVistaPoderesTransporte@getInstance().getListaMunicipios(poderTransporte)"
							headerKey="" headerValue="Seleccione Municipio" listKey="idMunicipio" listValue="nombre" 
							name="poderTransporte.idMunicipio"
							onchange="cargarListaPueblos('idProvinciaEmpresa', 'idMunicipioEmpresa', 'idPuebloEmpresa');
							obtenerCodigoPostalPorMunicipio('idProvinciaEmpresa', 'idMunicipioEmpresa', 'idCodPostalEmpresa');" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
		   				<label for="labelPuebloEmpresa">Pueblo: </label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">   				
		   				<s:select id="idPuebloEmpresa" onblur="this.className='input2';"
		   					list="@org.gestoresmadrid.oegam2.transporte.utiles.UtilesVistaPoderesTransporte@getInstance().getListaPueblos(poderTransporte)"
		   					headerKey="" headerValue="Seleccione Pueblo" listKey="pueblo" listValue="pueblo"
							name="poderTransporte.pueblo"
							onfocus="this.className='inputfocus';"
							onchange="obtenerCodigoPostalPorMunicipio('idProvinciaEmpresa', 'idMunicipioEmpresa', 'idCodPostalEmpresa');"
							style="width:200px;"/>						  
		   			</td>
				
					<td align="left" nowrap="nowrap">
						<label for="labelTipoViaEmpresa">Tipo Via<span class="naranja">*</span>:</label>
					</td>
					<td>
						<s:select  onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
							list="@org.gestoresmadrid.oegam2.transporte.utiles.UtilesVistaPoderesTransporte@getInstance().getListaTipoVias(poderTransporte)"
							headerKey="" headerValue="Seleccione Tipo Via" listKey="idTipoVia" listValue="nombre" 
							name="poderTransporte.idTipoVia"
							id="idTipoViaEmpresa"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNombreEmpresa">Nombre Vía Pública<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="poderTransporte.nombreVia" id="idNombreViaEmpresa" size="25" onblur="this.className='input2';" 
			       			onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelCodPostalEmpresa">Cod. Postal<span class="naranja">*</span>:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="poderTransporte.codPostal" id="idCodPostalEmpresa" size="5" maxlength="5" onblur="this.className='input2';" 
			       			onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
		      	<tr>    			
		   			<td align="left" nowrap="nowrap" width="7%">
		   				<label for="labelNumeroEmpresa">Número<span class="naranja">*</span>:</label>
		   			</td>
		   			
		       		<td align="left" nowrap="nowrap">
		   				<s:textfield id="idNumeroEmpresa" name="poderTransporte.numeroVia" 
			   				onblur="this.className='input2';" onfocus="this.className='inputfocus';" onkeypress="return validarSN2(event,this)" size="4" maxlength="5"/>
		   			</td>
		   		</tr>
			</table>
		</div>
		<div class="acciones center">
			<s:if test="%{poderTransporte.idPoderTransporte != null}">
				<input type="button" class="boton" name="bDescargarPoder" id="idDescargarPoder" value="Descargar Poder" onClick="javascript:descargarPoder();"
		 			onKeyPress="this.onClick"/>
		 	</s:if>
		 	<s:else>
		 		<input type="button" class="boton" name="bGenerarPoder" id="idGenerarPoder" value="Generar Poder" onClick="javascript:generarPoder();"
		 			onKeyPress="this.onClick"/>
		 		<input type="button" class="boton" name="bLimpiarPoder" id="idLimpiarPoder" value="Limpiar" onClick="javascript:limpiarPoder();"
		 			onKeyPress="this.onClick"/>	
		 	</s:else>
		</div>
	</s:form>
</div>
