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
				<td><openmrs:format concept="${obs.concept}" /> (${obs.concept.conceptId})</td>
				<td>
					<c:choose>
						<c:when test="${obs.concept.datatype.boolean}">
							<c:choose>
								<c:when test="${obs.valueBoolean}">
									<spring:message code="general.true"/>
								</c:when>
								<c:otherwise>
									<spring:message code="general.false"/>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${obs.concept.datatype.numeric}">
							${obs.valueNumeric}
							${obs.concept.units}
						</c:when>
						<c:when test="${obs.concept.datatype.coded}">
							<openmrs:format concept="${obs.valueCoded}" /> (${obs.valueCoded.conceptId})
						</c:when>
						<c:when test="${obs.concept.datatype.text}">
							<c:out value="${obs.valueText}" />
						</c:when>
						<c:when test="${obs.concept.datatype.date}">
							<openmrs:formatDate date="${obs.valueDatetime}" type="medium" />
						</c:when>
						<c:otherwise>
							<spring:message code="ConceptComplex.name" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

