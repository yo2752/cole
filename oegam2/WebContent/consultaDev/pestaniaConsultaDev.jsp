<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta Dev</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Alta Consulta</span>
			</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelCif">CIF:</label>
			</td>
			<td align="left" nowrap="nowrap">
		 		<table style="align:left;" >
			 		<tr>
				 		<td>
							<s:textfield id="idCifConsultaDev" name="consultaDev.cif"  onblur="this.className='input2';" 
       							onfocus="this.className='inputfocus';" size="20" maxlength="10"/>
       					</td>
       					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && consultaDev.idConsultaDev != null}">
       						<td>
								<label for="idVehiculoLabel">Id</label>
							</td>
							<td>
								<s:textfield id="idConsultaDev" value="%{consultaDev.idConsultaDev}" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="5" maxlength="10" disabled="true"/>
							</td>
						</s:if>
					</tr>
				</table>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelEstadoConsulta">Respuesta DEV:</label>
			</td>
			<td align="left" nowrap="nowrap">
			   	<s:textfield value="%{@org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoCif@convertirTexto(consultaDev.estadoCif)}" disabled="true"/>
			   	<s:hidden name="consultaDev.estadoCif"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelEstadoConsulta">Estado Consulta:</label>
			</td>
			<td align="left" nowrap="nowrap">
			    <s:select list="@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().getEstadoConsulta()" onblur="this.className='input2';" 
			    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Estado" 
			    		name="consultaDev.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoConsultaDev" disabled="true"/>
			</td>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<td align="left" nowrap="nowrap">
					<label for="labelContrato">Contrato:</label>
				</td>
				<td  align="left">
						<s:select id="idContrato"
							list="@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';" headerValue="Seleccione Contrato"
							onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
							listValue="descripcion" cssStyle="width:220px"
							name="consultaDev.contrato.idContrato"></s:select>
				</td>
			</s:if>
			<s:else>
				<s:hidden name="consultaDev.contrato.idContrato"/>
			</s:else>
		</tr>
	</table>
</div>