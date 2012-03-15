<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/headerMinimal.jsp"%>

<%@ include file="template/localInclude.jsp"%>

<b class="boxHeader">
	<table width="100%" cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td align="left"><spring:message code="Encounter.observations" /></td>
			<td align="right"><a href="#" class="jqmClose">Close</a></td>
		</tr>
	</table>
</b>
<div class="box">
	<table width="100%" cellspacing="0">
		<tr>
			<th><spring:message code="Obs.concept"/></th>
			<th><spring:message code="general.value"/></th>
		</tr>
		<c:forEach items="${observations}" var="obs">
			<tr>
				<td>${obs.concept.name}</td>
				<td>${obs.valueNumeric}</td>
			</tr>
		</c:forEach>
	</table>
</div>

