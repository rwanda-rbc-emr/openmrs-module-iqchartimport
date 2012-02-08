<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
    $('#iqpatients').dataTable();
});
</script>

<div style="margin-bottom: 20px">
	<input type="button" value="<spring:message code="general.back" />" onclick="location.href='upload.form'" />
</div>
		
<b class="boxHeader">
	<spring:message code="@MODULE_ID@.general.database" /> &gt; <spring:message code="@MODULE_ID@.general.patients" />
</b>
<div class="box">
	<table id="iqpatients" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th><spring:message code="@MODULE_ID@.general.tracnetID" /></th>
				<th><spring:message code="@MODULE_ID@.general.hospitalID" /></th>
				<th><spring:message code="general.name" /></th>
				<th><spring:message code="Person.birthdate" /></th>
				<th><spring:message code="Patient.gender" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${patients}" var="patient" varStatus="status">
				<tr  
					onclick="location.href='patient.form?tracnetID=${patient.tracnetID}'"
					style="cursor: pointer;"
					class="<c:choose><c:when test="${status.index % 2 == 0}">evenRow</c:when><c:otherwise>oddRow</c:otherwise></c:choose>"
				>
					<td>${patient.tracnetID}</td>
					<td><c:out value="${patient.hospitalID}" /></td>
					<td><c:out value="${patient.lastName}" />, <c:out value="${patient.firstName}" /></td>
					<td><openmrs:formatDate date="${patient.dob}" /></td>
					<td>
						<c:choose>
							<c:when test="${patient.sexCode == 0}">
								<spring:message code="Patient.gender.male" />
							</c:when>
							<c:when test="${patient.sexCode == 1}">
								<spring:message code="Patient.gender.female" />
							</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>