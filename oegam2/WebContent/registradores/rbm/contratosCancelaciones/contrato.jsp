<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/registradores/registradores.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<script src="js/tabs.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Cancelaci&oacute;n</span>
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
	<ol id="toc">
		<li id="tab1" ><a href="#tab1">Datos Cancelaci&oacute;n</a></li>
		<li id="presentador"><a href="#presentador">Presentador</a></li>
		<li id="tab2" ><a href="#tab2">Entidad Solicitante</a></li>
		<li id="tab5" ><a href="#tab5">Entidad sucesora</a></li>
		<li id="tab3" ><a href="#tab3">Comprador/Arrendatario</a></li>
	    <li id="tab6" ><a href="#tab6">Bienes</a></li>
		<li id="tab4" ><a href="#tab4">Datos Inscripci&oacute;n</a></li>
		<li id="tab7" ><a href="#tab7">Datos Firma</a></li>
		<s:if test="%{tramiteRegRbmDto.acusesPendientes!=null && tramiteRegRbmDto.acusesPendientes.size>0}">
		      <li id="pesPendientes"><a href="#pesPendientes">Acuses pendientes</a></li>
		</s:if>
		<li id="documentacion"><a href="#documentacion">Documentación aportada/recibida</a></li>
		<li id="formaPago"><a id="pesFormaPago" href="#formaPago">Forma de pago</a></li>
		<s:if test="tramiteRegRbmDto.idTramiteRegistro != null">
			<li id="tab8" ><a href="#tab8">Resumen</a></li>
		 </s:if>
<%-- 		 <s:if test="%{tramiteRegRbmDto.facturasRegistro!=null && tramiteRegRbmDto.facturasRegistro.size>0}">
		     <li id="pesFacturas"><a href="#pesFacturas">Minutas / Facturas</a></li>
		</s:if> --%>
	</ol>

	<table class="subtitulo" align="left" cellspacing="0">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Datos cancelaci&oacute;n limitaciones</td>
				
				<s:if test="tramiteRegRbmDto.idTramiteRegistro != null">
					<td  align="right">
						<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  					onclick="consultaEvTramiteRegistro(<s:property value="%{tramiteRegRbmDto.idTramiteRegistro}"/>);" title="Ver evolución"/>
					</td>
				</s:if>
			</tr>
	</table>
	
	<%@include file="/includes/erroresYMensajes.jspf" %>
	<s:form action="guardarCancelacion.action"  id="formData" name="formData" enctype="multipart/form-data">
		<s:hidden name="tramiteRegRbmDto.idUsuario" id="idUsuario"/>
		<s:hidden name="tramiteRegRbmDto.idTramiteCorpme"/>
		<s:hidden name="tramiteRegRbmDto.tipoTramite" id="tipoTramiteRegistro"/>
		<s:hidden name="tramiteRegRbmDto.idContrato" id="idContratoRegistro" />
		<s:hidden name="tramiteRegRbmDto.numColegiado" />
		<s:hidden name="tramiteRegRbmDto.estado" id="idEstado"/>
		<s:hidden name="tramiteRegRbmDto.fechaCreacion.dia"/>
		<s:hidden name="tramiteRegRbmDto.fechaCreacion.mes"/>
		<s:hidden name="tramiteRegRbmDto.fechaCreacion.anio"/>
		<s:hidden name="tramiteRegRbmDto.fechaCreacion.hora"/>
		<s:hidden name="tramiteRegRbmDto.fechaCreacion.minutos"/>
		<s:hidden name="tramiteRegRbmDto.fechaCreacion.segundos"/>
		<s:hidden name="tipoContratoRegistro" id="tipoContratoRegistro" />
		<s:hidden name="tramiteRegRbmDto.subsanacion"/>
		
        <div id="tab1" class="contentTabs"> 
        	<jsp:include page="pestaniaDatosCancelacion.jsp" flush="true"/>
        </div> <!-- fin tab1 -->
        
        <div id="presentador" class="contentTabs">
        	<jsp:include page="../../pestaniaPresentadorRBM.jsp" flush="true"/>
	    </div>
	
        <div id="tab2" class="contentTabs"> 
        	<jsp:include page="pestaniaSolicitante.jsp" flush="true"/>
        </div> <!-- fin tab2 -->
        
        <div id="tab3" class="contentTabs"> 
        	<jsp:include page="pestaniaCompradorArrendatario.jsp" flush="true"/>
        </div> <!-- fin tab3 -->
        
        <div id="tab6" class="contentTabs"> 
        	<jsp:include page="pestaniaBienes.jsp" flush="true"/>
        </div> <!-- fin tab6 -->
        
        <div id="tab5" class="contentTabs"> 
        	<jsp:include page="pestaniaEntidades.jsp" flush="true"/>
        </div> <!-- fin tab6 -->
        
		<div id="tab4" class="contentTabs"> 
        	<jsp:include page="pestaniaDatosInscripcion.jsp" flush="true"/>
        </div> <!-- fin tab4 -->
      
        <div id="tab7" class="contentTabs"> 
        	<jsp:include page="pestaniaDatosFirma.jsp" flush="true"/>
        </div> <!-- fin tab7 -->
	   
	   	<s:if test="%{tramiteRegRbmDto.acusesPendientes!=null && tramiteRegRbmDto.acusesPendientes.size>0}">
	   		<jsp:include page="../../acusesPendientesRBM.jsp" flush="true"/>
	    </s:if>
	
		<div id="documentacion" class="contentTabs">
			<jsp:include page="../../resumen/listaFicherosRBM.jsp" flush="true"/>
		</div>
		
		<div id="formaPago" class="contentTabs">  
			<jsp:include page="../../pestaniaFormaPagoRBM.jsp" flush="true"/>
		</div>
	
        <div class="contentTabs" id="tab8" >  
       	 	<jsp:include page="pestaniaResumen.jsp" flush="true" />
        </div> <!-- fin tab8 -->
        
<%--        	<s:if test="%{tramiteRegRbmDto.facturasRegistro!=null && tramiteRegRbmDto.facturasRegistro.size>0}">
	   		<jsp:include page="../../pestaniaMinutasFacturasRBM.jsp" flush="true"/>
	    </s:if> --%>
		
		&nbsp;
		<div class="acciones center">
			<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado, tramiteRegRbmDto.numReg)">
				<input type="button" value="Guardar" class="boton" id="btnGuardar" onclick="javascript:guardarTramiteRegRbm();"/>
			</s:if>
			<s:if test="tramiteRegRbmDto.estado==@org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@Iniciado">
				<input type="button" value="Validar" class="boton" id="btnValidar" onclick="javascript:validarTramiteRegRbm();"/>
			</s:if>
			<s:if test="tramiteRegRbmDto.estado==@org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro@Validado">
				<input type="button" value="Enviar Dpr" class="boton" id="btnEnviarDpr" onclick="construirDpr();" />
			</s:if>
			<s:if test="tramiteRegRbmDto.estado == 8 || tramiteRegRbmDto.estado == 10 || tramiteRegRbmDto.estado == 12 || tramiteRegRbmDto.estado == 14 || tramiteRegRbmDto.estado == 17"> 
				<input type="button" id="btnFirmarAcuse" value="Firmar acuse" class="boton" onclick="construirFirmarAcuse('', '');" />
			</s:if>
			<s:if test="tramiteRegRbmDto.estado==15 || tramiteRegRbmDto.estado==13" >
				<s:if test='!"S".equals(tramiteRegRbmDto.subsanacion)'>
					<input type="button" id="btnSubsanar" value="Subsanar" class="boton" onclick="subsanarRBM();" />
				</s:if>
			</s:if>
			<s:if test="tramiteRegRbmDto.estado == 18">
				<input type="button" id="btnDuplicar" value="Duplicar trámite" class="boton" onclick="duplicarRBM();" />
			</s:if>
		</div>
			
		<div id="bloqueLoadingRegistro" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../../includes/bloqueLoading.jspf" %>
		</div>

	</s:form>
	<div id="divEmergentePopUpCancelacion" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
	
</div>
<script type="text/javascript">

	$(document).ready(function(){
		mostrarDivCategoriaBienCancelacion();
	});
	
	trimCamposInputs();
</script>
