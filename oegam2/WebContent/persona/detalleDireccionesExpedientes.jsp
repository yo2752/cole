<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript">
	function seleccionar_check() {
		for(var i=0;i<document.formData.principal.length;i++){
			if(document.formData.principal[i].checked){
				if(!document.formData.fusion[i].checked){
					document.formData.fusion[i].checked = true;
				}
			}
		}
	}

	function indice(cual) {
		f = cual.form;
		n = cual.name;
		for (var i = 0, nombres = f[n], total = nombres.length; i < total; i++) if (f[n][i] == cual) return i;
	}

	function comprobar(indice) {
		if(!document.formData.fusion[indice].checked) {
			if(document.formData.principal[indice].checked){
				document.formData.fusion[indice].checked = true;
			}
		}
	}

	function contar_checks() {
		var cuenta = 0;
		var cuenta_principal = 0;
		var j = 0;
		var dir= new Array();
		for(var i=0;i<document.formData.fusion.length;i++){
			if(document.formData.fusion[i].checked){
				dir[j] = document.formData.fusion[i].value;
				j++;
				cuenta++;
			}
		}
		for(i=0;i<document.formData.principal.length;i++){
			if(document.formData.principal[i].checked){	
				cuenta_principal++;
			}
		}

		if(cuenta_principal < 1) {
			alert("Seleccione una dirección principal");
		} else {
			if(cuenta_principal > 1) {
				alert("Sólo una única dirección puede ser principal");
			} else {
				if(cuenta >= 2) {
					if(confirm("Va a fusionar direcciones. ¿Desea continuar?")){
						document.formData.action='fusionarDireccionDetallePersona.action#DireccionesExpedientes';
						document.formData.submit();
					}
				} else if(cuenta < 2) {
					alert("Debe seleccionar al menos 2 direcciones de la columna Fusionar/Eliminar");
				}
			}
		}
	}

	function contar_checks_eliminar() {
		var cuenta = 0;
		var dir= new Array();
		for(var i=0;i<document.formData.fusion.length;i++){
			if(document.formData.fusion[i].checked){
				dir[cuenta] = document.formData.fusion[i].value;
				cuenta++;
			}
		}
		if(cuenta > 0) {
			if(confirm("Va a eliminar direcciones. ¿Desea continuar?")){
				document.formData.action='eliminarDireccionDetallePersona.action#DireccionesExpedientes';
				document.formData.submit();
			}
		} else {
			alert("Debe seleccionar al menos 1 dirección de la columna Fusionar/Eliminar");
		}
	}

	function contar_checks_asignar() {
		var cuenta_principal = 0;
		for(var i=0;i<document.formData.principal.length;i++){
			if(document.formData.principal[i].checked){	
				cuenta_principal++;
			}
		}

		if(cuenta_principal < 1) {
			alert("Seleccione una dirección principal");
		} else if(cuenta_principal > 1) {
			alert("Sólo una única dirección puede ser principal");
		} else if(confirm("Va seleccionar la dirección como principal. ¿Desea continuar?")) {
			document.formData.action='asignarPrincipalDetallePersona.action#DireccionesExpedientes';
			document.formData.submit();
		}
	}
</script>

<div class="contentTabs" id="DireccionesExpedientes">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Direcciones - Expedientes</span></td>
			</tr>
		</table>
	</div>

	<s:form method="post" id="formData" name="formData">
		<div class="busqueda">

			<div id="resultado"
				style="width: 100%; background-color: transparent;">
				<table class="subtitulo" cellSpacing="0" style="width: 100%;">
					<tr>
						<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
					</tr>
				</table>
			</div>

			<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
				<table width="100%">
					<tr>
						<td align="right">
							<table width="100%">
								<tr>
									<td width="90%" align="right">
										<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
									</td>
									<td width="10%" align="right">
										<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
											onblur="this.className='input2';" onfocus="this.className='inputfocus';"
											id="idResultadosPorPagina" value="resultadosPorPagina" title="Resultados por página"
											onchange="cambiarElementosPorPaginaConsulta('navegarDetallePersona.action', 'displayTagDiv', this.value);" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>

			<script type="text/javascript">
				$(function() {
					$("#displayTagDiv").displayTagAjax();
				})
			</script>
			
			<div id="displayTagDiv" class="divScroll">
				<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
					<%@include file="../../includes/bloqueLoading.jspf"%>
				</div>
				
				<display:table name="lista" excludedParams="*" class="tablacoin" requestURI="navegarDetallePersona.action"
					uid="tablaDireccionesDetallePersona" summary="Listado de Direcciones-Expedientes" cellspacing="0" cellpadding="0">
						<display:column title="Dirección" sortable="false" headerClass="sortable" defaultorder="descending" style="width:50%; text-align:left;">
							<s:if test="%{#attr.tablaDireccionesDetallePersona.direccion.idDireccion != null}">
								${tablaDireccionesDetallePersona.direccion.tipoViaDescripcion} ${tablaDireccionesDetallePersona.direccion.nombreVia},
								${tablaDireccionesDetallePersona.direccion.numero} ${tablaDireccionesDetallePersona.direccion.letra},
								${tablaDireccionesDetallePersona.direccion.codPostal}, ${tablaDireccionesDetallePersona.direccion.nombreMunicipio}, ${tablaDireccionesDetallePersona.direccion.nombreProvincia}
							</s:if>
							<s:else>
								${tablaDireccionesDetallePersona.direccion.nombreMunicipio}
							</s:else>
						</display:column>

						<display:column title="Expedientes">
							<a href="javascript:consultaListaExpedientesDireccion('<s:property value="personaModificar.nif" />', '<s:property value="personaModificar.numColegiado" />', '${tablaDireccionesDetallePersona.direccion.idDireccion}')" title="">Ver</a>
						</display:column>

						<display:column title="Fusionar/Eliminar">
							<s:if test="%{lista.getFullListSize() > 1}">
								<s:checkbox id="check_fusion" onchange="comprobar(indice(this))" name="fusion" fieldValue="%{#attr.tablaDireccionesDetallePersona.direccion.idDireccion}"></s:checkbox>
							</s:if>
						</display:column>

						<display:column title="Principal">
							<s:if test="%{lista.getFullListSize() > 1}">
								<input type="radio" onclick="seleccionar_check();" id="principal" name="principal" value="${tablaDireccionesDetallePersona.direccion.idDireccion}"></input>
							</s:if>
						</display:column>

						<display:column title="" defaultorder="descending">
							<s:if test="%{#attr.tablaDireccionesDetallePersona.fechaFin == null}">
								PRINCIPAL
							</s:if>
						</display:column>

						<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
							<display:column title="ID" style="width:5%">
								${tablaDireccionesDetallePersona.direccion.idDireccion}
							</display:column>
						</s:if>
				</display:table>
			</div>
		</div>
	</s:form>

	<table class="acciones" width="95%" align="left">
		<s:if test="%{lista.getFullListSize()>1}">
			<tr>
				<td align="center" style="size: 100%; text-align: center; list-style: none;">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoPersonas() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
						<input type="button" class="boton" name="bFusionarDireccion" id="bFusionarDireccion" value="Fusionar Dirección" onClick="return contar_checks();"
							onKeyPress="this.onClick" />
					</s:if>
					&nbsp;&nbsp;&nbsp;
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoPersonas() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
						<input type="button" class="boton" name="bEliminarDireccion" id="bEliminarDireccion" value="Eliminar Dirección" onClick="return contar_checks_eliminar();"
							onKeyPress="this.onClick" />
					</s:if>
					&nbsp;&nbsp;&nbsp;
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoPersonas() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
						<input type="button" class="boton" name="bAsignarPrincipal" id="bAsignarPrincipal" value="Asignar Principal" onClick="return contar_checks_asignar();"
							onKeyPress="this.onClick" />
					</s:if>
				</td>
			</tr>
		</s:if>
		<tr>
			<td align="center" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
				<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver" onClick="javascript:document.location.href='inicioConsultaPersona.action';"
					onKeyPress="this.onClick" />
			</td>
		</tr>
	</table>
</div>