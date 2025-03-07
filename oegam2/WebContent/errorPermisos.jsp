<%@ page isErrorPage="true"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set var="opcMenu" value="%{null}" />

<br />
<br />
<br />
<br />
<br />

<div class="rbroundbox">
	<div class="rbtop">
		<div></div>
	</div>
	<div class="rbcontent" align="center">
		<br>
		<br>
		<table>
			<tr>
				<td>
					<ul>
						<li>No tiene los permisos suficientes para acceder a esta
							zona. Para cualquier aclaraci&oacute;n, pongase en contacto con el
							administrador del sistema.
						</li>
					</ul>
				</td>
			</tr>
		</table>
		<br />
		<br />
		<input type="button" class="boton" name="bReiniciar"
			id="bReiniciar" value="Volver" onClick="javascript:volverRaiz();"
			onKeyPress="this.onClick" />
	</div>
	<!-- /rbcontent -->
	<div class="rbbot">
		<div></div>
	</div>
</div>
<!-- /rbroundbox -->
<script type="text/javascript">
	function volverRaiz() {
		window.location = "/oegam2";
	}
</script>