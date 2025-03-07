<%@ taglib prefix="s" uri="/struts-tags"%>

<h3 class="support">
	<span style="color: rgb(255, 255, 255); text-shadow: -1px 0px rgb(0, 0, 0), 1px 0px rgb(0, 0, 0), 0px 1px rgb(0, 0, 0), 0px -1px rgb(0, 0, 0); font-weight: normal; font-size: 1.8em; font-family: Arial;">
		Status OEGAM
	</span>
</h3>

<div id="status_oegam_interno" style="text-align: left; padding-left: 20px; color: white; display: table">
	<s:iterator value="listaSemaforosSesion" status="statusSemaforo">
		<s:if test="#statusSemaforo.odd == true">
		<div style="display: table-row;">
			<div style="display: table-cell;">
				<s:if test="listaSemaforosSesion[#statusSemaforo.index].estado == 1">
					<img src="img/verde.png" style="vertical-align: middle;">
				</s:if>
				<s:elseif test="listaSemaforosSesion[#statusSemaforo.index].estado == 2">
					<img src="img/naranja.png" style="vertical-align: middle;">
				</s:elseif>
				<s:elseif test="listaSemaforosSesion[#statusSemaforo.index].estado == 3">
					<img src="img/rojo.png" style="vertical-align: middle;">
				</s:elseif>
				<s:property value="listaSemaforosSesion[#statusSemaforo.index].proceso"/>
			</div>
			<s:if test="#statusSemaforo.last">
				<div style="display: table-cell; padding-left: 20px;">&nbsp;</div>
			</s:if>
			<s:else>
				<div style="display: table-cell; padding-left: 20px;">
					<s:if test="listaSemaforosSesion[#statusSemaforo.index + 1].estado == 1">
						<img src="img/verde.png" style="vertical-align: middle;">
					</s:if>
					<s:elseif test="listaSemaforosSesion[#statusSemaforo.index + 1].estado == 2">
						<img src="img/naranja.png" style="vertical-align: middle;">
					</s:elseif>
					<s:elseif test="listaSemaforosSesion[#statusSemaforo.index + 1].estado == 3">
						<img src="img/rojo.png" style="vertical-align: middle;">
					</s:elseif>
				</div>
				<s:property value="listaSemaforosSesion[#statusSemaforo.index + 1].proceso"/>
			</s:else>
		</div>
		</s:if>
	</s:iterator>
</div>