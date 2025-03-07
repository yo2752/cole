<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/solInfo.js" type="text/javascript"></script>







<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta Inteve</span></td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">
<s:hidden name="urlJNLP" id="urlJNLP" />
<s:hidden name="valorEstadoCambio" id="valorEstadoCambio" />
<s:hidden name="codSeleccionados" id="codSeleccionados" value=""/>

	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="right"><label for="tipo">Matricula:</label></td>
				<td>
						<s:textfield name="inteveViewBean.matricula" id="matricula"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="9"
						onkeypress="return validarMatricula(event)"
						onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" 
						 />
				</td>
				<td align="right"><label for="dato">Bastidor: </label></td>
				<td>
					<s:textfield name="inteveViewBean.bastidor" id="bastidor"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="35" />
				</td>
				<td align="right"><label for="dato">Estado: </label></td>
				<td>
					<s:select	list="@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getEstadoSolInfoVehiculo()"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" headerKey="-1"
	           			headerValue="TODOS" name="inteveViewBean.estado" listKey="valorEnum" listValue="nombreEnum"
						id="idEstadoTramite"/>
				</td>
			</tr>
			<tr>
				<td align="right"><label for="activo">Num. Expediente:</label></td>
				<td>
					<s:textfield name="inteveViewBean.numExpediente" id="numExpediente"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="15" 
						onkeypress="return validarNumerosEnteros(event)"/>
				</td>
				<td align="right"><label for="activo">Código Tasa:</label></td>
				<td>
					<s:textfield name="inteveViewBean.codigoTasa" id="codTasa"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="15" />
				</td>
			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esAdmin()}">
				<td align="right" nowrap="nowrap">
					<label for="labelContrato">Contrato:</label>
				</td>
				<td>
					<s:select id="idContrato" list="@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getComboContratosHabilitados()"
						onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" 
						headerKey="" listValue="descripcion" cssStyle="width:220px" name="inteveViewBean.idContrato">
					</s:select>
				</td>
			</s:if>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelFecha">Fecha de Alta:</label>
				</td>
				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaDesde">Desde: </label>
							</td>
							<td align="left">
								<s:textfield name="inteveViewBean.fechaAlta.diaInicio" id="diaInicio"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="inteveViewBean.fechaAlta.mesInicio" id="mesInicio" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="inteveViewBean.fechaAlta.anioInicio" id="anioInicio"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio, document.formData.mesInicio, document.formData.diaInicio);return false;" 
    								title="Seleccionar fecha">
    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
    							</a>
							</td>
						</tr>
					</table>
				</td>

				<td align="left">
					<table style="width:20%">
						<tr>
							<td align="right">
								<label for="labelFechaAltaHasta">Hasta:</label>
							</td>
							<td align="left">
								<s:textfield name="inteveViewBean.fechaAlta.diaFin" id="diaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="inteveViewBean.fechaAlta.mesFin" id="mesFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td align="left">
								<label class="labelFecha">/</label>
							</td>
							<td align="left">
								<s:textfield name="inteveViewBean.fechaAlta.anioFin" id="anioFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td align="left">
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFin, document.formData.mesFin, document.formData.diaFin);return false;" 
				  					title="Seleccionar fecha">
				  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
   		    					</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar"
					id="bBuscar" value="Consultar" onkeypress="this.onClick"
					onclick="consultaInteve();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					id="bLimpiar" value="Limpiar"
					onclick="return limpiarFormularioInteve();" /></td>

			</tr>

		</table>
	</div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultados de la
					Búsqueda:</td>
			</tr>
		</table>
	</div>
	<%@include file="../../includes/erroresYMensajes.jspf"%>
	<s:if test="%{resumen != null}">
		<br>
		<div id="resumenConsultaInteve" style="text-align: center;">
			<%@include file="resumenInteve.jspf" %>
		</div>
		<br><br>
	</s:if>
	<s:if
		test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right"><label
								for="idResultadosPorPaginaConsultaInteve">&nbsp;Mostrar resultados</label></td>
							<td width="10%" align="right"><s:select
									list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPaginaConsultaInteve" value="resultadosPorPagina"
									onchange="cambiarElementosPorPaginaConsultaInteve();" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>


	<div id="displayTagDivConsultaInteve" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>

		 <display:table name="lista" excludedParams="*"
			requestURI="navegarConsultaInteve.action" class="tablacoin"
			uid="tablaConsultas" summary="Listado de Tramites" cellspacing="0"
			cellpadding="0" sort="external" decorator="${decorator}">

			<display:column title="Num.Exp"
					sortable="true" headerClass="sortable" 
					defaultorder="ascending"
					style="width:2%" 
					property="numExpediente" 
					href="detalleAltaTramSolInformacion.action"
					paramId="numExpediente">
					
			</display:column>			
 			<display:column title="Bastidor"
					sortable="false" 
					style="width:2%" 
					property="bastidor" >
					
			</display:column>

			<display:column title="Matricula"
					sortable="false" 
					style="width:2%" 
					property="matricula" >
					
			</display:column>
			<display:column title="Tasa"
					sortable="false"
					style="width:2%" 
					property="tasa" >					
			</display:column>
			
			<s:if
				test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<display:column
				 property="nombreContrato"
				 title="Contrato"
				 sortable="true"
				 headerClass="sortable"
				 defaultorder="descending"
				 sortProperty="numColegiado"
				 style="width:4%;" />
			</s:if>
			

 			<display:column property="fechaAlta" title="Fecha Alta"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%" format="{0,date,dd/MM/yyyy}" />

			
				
 			<display:column title="Estado"
 					property="estado"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%">
					

			</display:column>			 

			<display:column
				title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultasInteve);' 
				onKeyPress='this.onClick'/>"
				style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;"><input type="checkbox"
							name="listaChecksConsultasInteve"
							value='<s:property value="#attr.tablaConsultas.numExpediente"/>' />
						</td>
						<td style="border: 0px;">
										  		
				 <img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				 onclick="abrirEvolucionInteve(<s:property value="#attr.tablaConsultas.numExpediente"/>,'divEmergenteConsultaInteveEvolucion');" title="Ver evolución"/>
				  		</td>
					</tr>
				</table>
			</display:column>


		</display:table>


	</div>
	<div class="acciones center">
		<s:if test="%{lista.getFullListSize()>0 }">
			<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esAdmin()}">
 				<input type="button" value="Cambiar Estado" id="bCambiarEstado" class="boton" onkeypress="this.onClick" onclick="abrirVentanaSeleccionEstados();" />
 			</s:if>	
			<input type="button" value="Descargar JNLP" class="boton" onkeypress="this.onClick" onclick="levantarJNLP();" />
			<input type="button" value="Descargar XML" class="boton" onkeypress="this.onClick" onclick="descargarXMLExterno();" />
			<input type="button" value="Generar XML" class="boton" onkeypress="this.onClick" onclick="generarXML();" />
			<input type="button" value="Descargar Informes" class="boton" onkeypress="this.onClick" onclick="descargarInformes();" />
		</s:if>
	</div>
</s:form>
	<div id="divEmergenteConsultaInteveEvolucion" style="display: none; background: #f4f4f4;"></div>

<script type="text/javascript">
<!--
	//-->
	var ventanaEstados;
	//Función que se ejecuta cuando se pulsa 'Cambiar Estados' en la consulta de trámites de tráfico
	function abrirVentanaSeleccionEstados() {
		if (numChecked() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}
		ventanaEstados = window
				.open(
						'abrirPopUpConsultaInteve.action',
						'popup',
						'width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
	}
	
	function numChecked() {
		var checks = document.getElementsByName("listaChecksConsultasInteve");	
		var numChecked = 0;
		for(var i=0; i<checks.length; i++) {
			if(checks[i].checked) numChecked++;
		}	
		return numChecked;
	}
	
	function invokeCambiarEstadoInteve(estado) {

		ventanaEstados.close();
		var checks = document.getElementsByName("listaChecksConsultasInteve");
		var codigos = "";
		var i = 0;
		while(checks[i] != null) {
			if(checks[i].checked) {
				if(codigos==""){
					codigos += checks[i].value;
				}else{
					codigos += "-"+ checks[i].value;
				}
			}
			i++;
		}
		$('#valorEstadoCambio').attr("value","");
		$('#valorEstadoCambio').attr("value",estado);
		
		$('#codSeleccionados').attr("value","");
		$('#codSeleccionados').attr("value",codigos);
		
		$('#formData').attr("action","cambiarEstadosConsultaInteve.action");
		$('#formData').submit();

	}
	
	function cambiarElementosPorPaginaConsultaInteve(){
		var $dest = $("#displayTagDivConsultaInteve");
		$.ajax({
			url:"navegarConsultaInteve.action",
			data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaInteve").val(),
			type:'POST',
			success: function(data){
				filteredResponse =  $(data).find($dest.selector);
				if(filteredResponse.size() == 1){
					$dest.html(filteredResponse);
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar las consulta de tramites.');
		    }
		});
	}
	
	function levantarJNLP(){
		var url = $("#urlJNLP").val();
		window.location =url;	
	}
</script>