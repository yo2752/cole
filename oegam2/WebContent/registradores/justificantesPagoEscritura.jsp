<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<s:hidden name="idFicheroEliminar"/>
	<s:hidden name="idFicheroDescargar"/>

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
					<span class="titulo">Datos justificante pago</span>
				</td>
		</tr>
	</table>
	
	
	<table border="0" class="tablaformbasicaTC">
	<tr>
		<td align="right" nowrap="nowrap" style="vertical-align: middle;">
			<label for="cif">Estado:</label>
		</td>
		<td align="left" nowrap="nowrap" width="60px" colspan="3">
			<s:if test="%{tramiteRegistroJustificante.estado != null && !(tramiteRegistroJustificante.estado.equals(''))}">
				<s:label value="%{@org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@convertirTexto(tramiteRegistroJustificante.estado)}"/>
			</s:if>
			<s:else>
				<label for="estadoTramiteRegistroJustificante">Iniciado</label>
			</s:else>
		</td>
		
		<td align="right" nowrap="nowrap" colspan="2">
			<label for="numExpediente">Nº Expediente:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="2">
			<s:if test="%{tramiteRegistroJustificante.idTramiteRegistro != null && !(tramiteRegistroJustificante.idTramiteRegistro.equals(''))}">
				<div id="boxNumExp"><s:property value="%{tramiteRegistroJustificante.idTramiteRegistro}"/></div>
			</s:if>
			<s:else>
				<p style="color:red">NO GENERADO</p>
			</s:else>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="numExpediente">Nº Expediente Registro:</label>
		</td>
		<td align="left" nowrap="nowrap" width="60px" colspan="2">
			<s:if test="%{tramiteRegistroJustificante.idTramiteCorpme != null && !(tramiteRegistroJustificante.idTramiteCorpme.equals(''))}">
				<div id="boxNumExp"><s:property value="%{tramiteRegistroJustificante.idTramiteCorpme}"/></div>
			</s:if>
			<s:else>
				<p style="color:red">NO GENERADO</p>
			</s:else>
		</td>
	</tr>

	<tr>
		<td align="right" nowrap="nowrap" style="vertical-align: middle;">
			<label for="cif">Mensajes:</label>
		</td>
		<s:if test="tramiteRegistroJustificante.respuesta != null && tramiteRegistroJustificante.respuesta != ''">
			<td align="left" nowrap="nowrap" colspan="7">
				<s:textfield name="tramiteRegistroJustificante.respuesta" id="respuesta" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="90" maxlength="90"
					disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap" colspan="7">
				<s:textfield name="respuesta" id="respuesta"  
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="90" maxlength="90"
					value="Sin mensajes" disabled="true"  />
			</td>
		</s:else>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="respuesta">Fecha &uacute;ltimo env&iacute;o:</label>
		</td>
		<s:if test="%{tramiteRegistroJustificante.fechaEnvio != null}">
			<td align="left" nowrap="nowrap">
				<s:textfield name="fechaEnvio" id="fechaEnvio" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					value="%{tramiteRegistroJustificante.fechaEnvio.toString()}" disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap">
				<s:textfield name="fechaEnvio" id="fechaEnvio" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					value="No enviado" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
		
		<td align="right" nowrap="nowrap">
			<label for="respuesta">Número de localizador:</label>
		</td>
		<s:if test="%{tramiteRegistroJustificante.localizadorReg!= null}">
			<td align="left" nowrap="nowrap">
				<s:textfield name="localizador" id="localizador" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"
					value="%{tramiteRegistroJustificante.localizadorReg}" disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap">
				<s:textfield name="localizador" id="localizador" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"
					value="Sin localizador" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="respuesta">N&uacute;mero de entrada:</label>
		</td>
		<s:if test="tramiteRegistroJustificante.numReg != null">
			<td align="left" nowrap="nowrap">
				<s:textfield name="tramiteRegistroJustificante.numReg" id="numReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td align="left" nowrap="nowrap">
				<s:textfield name="numReg" id="numReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="11" maxlength="11"
					value="Sin recibir" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
		<td align="right">
			<label for="respuesta">Año:</label>
		</td>
		<s:if test="tramiteRegistroJustificante.anioReg != null && tramiteRegistroJustificante.anioReg != ''">
			<td>
				<s:textfield name="tramiteRegistroJustificante.anioReg" id="anioReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"
					disabled="true"  />
			</td>
		</s:if>
		<s:else>
			<td>
				<s:textfield name="anioReg" id="anioReg" 
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="5"
					value="-" disabled="true" cssStyle="color:red" />
			</td>
		</s:else>
	</tr>
</table>

	<table  class="nav" cellSpacing="1" cellPadding="5" width="100%" >
		<tr>
			<td class="tabular" >
					<span class="titulo">Justificante pago</span>
				</td>
		</tr>
	</table>
					
	<s:if test="tramiteRegistro.estado == null || tramiteRegistro.estado == 19">
	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistroJustificante.estado,tramiteRegistroJustificante.numReg)">
		<table cellPadding="1" cellSpacing="3"  cellpadding="1"  class="tablaformbasica" width="100%" border="0">
			<tr>
				<td align="left" width="10%" nowrap="nowrap">
					<label id="fileUpload" >Justificante</label>
				</td>
				<td align="left" nowrap="nowrap" colspan="5">
					<s:file size="50" name="fileUpload" value="fileUpload"></s:file>
				</td>
				<td align="left" nowrap="nowrap" width="30%">
					<input type="button" class="boton" id="botonSubirFichero" value="Subir fichero" onclick="javascript:doPost('formData', 'incluirJustificanteTramiteRegistroMd6.action\u0023justificantesPago', '', '');" />
				</td>
				<td align="left" width="10%">
					<img id="loading3Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
				</td>
			</tr>
		</table>
	</s:if>
	</s:if>
				
	<table cellPadding="1" cellSpacing="3" cellpadding="1" class="tablaformbasica" width="100%" >
		<tr>
			<td align="center">
				<display:table name="tramiteRegistroJustificante.ficherosSubidos" excludedParams="*"
					class="tablacoin" id="row" summary="Listado de Ficheros Subidos" cellspacing="0" cellpadding="0" uid="row">	
							
					<display:column property="nombre" title="Nombre del fichero" defaultorder="descending" style="width:8%"/>
							
					<display:column style="width:8%" title="Descargar" >
						<a onclick="" href="javascript:doPost('formData', 'descargarJustificanteTramiteRegistroMd6.action?idFicheroDescargar=${row.nombre}\u0023justificantesPago', null);"> Descargar </a>
					</display:column>
							
					<display:column property="extension" title="Tipo de fichero" defaultorder="descending" style="width:8%"  />										
							
					<s:if test="tramiteRegistro.estado == 19">
					<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistroJustificante.estado,tramiteRegistroJustificante.numReg)">
						<display:column style="width:8%" title="Eliminar" >
						<a onclick="" href="javascript:doConfirmPost('¿Está seguro de que desea borrar este documento?', 'formData', 'eliminarJustificanteTramiteRegistroMd6.action?idFicheroEliminar=<s:property value="#attr.row.nombre"/>\u0023justificantesPago', null);"> Eliminar </a>
						</display:column>
					</s:if>
					</s:if>
				</display:table>
			</td>
		</tr>
	</table>
	
	<div id="botonBusqueda">
		<table border="0" width="100%">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
				  	<s:if test="tramiteRegistro.estado == 19" >
				  	<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegistroJustificante.estado,tramiteRegistroJustificante.numReg)">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEnvioDprRegistradores()}">
						<input type="button" value="Enviar justificante" class="boton" id="btnEnviarDpr" onclick="doPost('formData', 'construirDprJustificantePagoTramiteRegistroMd6.action\u0023justificantesPago')" />
						&nbsp;
					</s:if>
					</s:if>
					</s:if>
				</td>
			</tr>
		</table>
	</div>
	
	<img id="loadingImage" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
