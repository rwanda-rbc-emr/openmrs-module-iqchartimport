<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<div style="margin-bottom: 20px">
	<input type="button" value="<spring:message code="general.back" />" onclick="location.href='patients.list'" />
</div>
		
<b class="boxHeader">
	<spring:message code="@MODULE_ID@.general.database" />
	&gt; <spring:message code="@MODULE_ID@.general.patients" />
	&gt; ${patient.tracnetID}
</b>
<div class="box" style="margin-bottom: 20px">
	<table width="100%" cellspacing="0">
		<tr>
			<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.general.hospitalID" /></td>
			<td><c:out value="${patient.hospitalID}" /></td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="general.name" /></td>
			<td><c:out value="${patient.lastName}" />, <c:out value="${patient.firstName}" /></td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="Person.birthdate" /></td>
			<td>
				<openmrs:formatDate date="${patient.dob}" />
				<c:if test="${patient.dobEstimated}">
					(<spring:message code="Person.birthdateEstimated" />)
				</c:if>
			</td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="Patient.gender" /></td>
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
	</table>
</div>

<b class="boxHeader">
	<spring:message code="@MODULE_ID@.patient.hivProgram" />
</b>
<div class="box" style="margin-bottom: 20px">
	<table width="100%" cellspacing="0">
		<tr>
			<td style="font-weight: bold" width="300"><spring:message code="Program.dateEnrolled" /></td>
			<td><openmrs:formatDate date="${patient.enrollDate}" /></td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="Program.dateCompleted" /></td>
			<td><openmrs:formatDate date="${patient.exitDate}" /></td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="@MODULE_ID@.patient.exitReason" /></td>
			<td>
				<c:choose>
					<c:when test="${patient.exitCode == 1}">Transferred</c:when>
					<c:when test="${patient.exitCode == 2}">Deceased</c:when>
					<c:when test="${patient.exitCode == 3}">Lost</c:when>
					<c:otherwise><c:out value="${patient.exitReasonOther}" /></c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</div>

<b class="boxHeader">
	<spring:message code="@MODULE_ID@.patient.observations" />
</b>
<div class="box">
	<table id="iqobs" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th><spring:message code="general.dateCreated" /></th>
				<th><spring:message code="@MODULE_ID@.patient.obsType" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${patientObs}" var="obs" varStatus="status">
				<tr class="<c:choose><c:when test="${status.index % 2 == 0}">evenRow</c:when><c:otherwise>oddRow</c:otherwise></c:choose>">
					<td><openmrs:formatDate date="${obs.date}" /></td>
					<td>
						<c:choose>
							<c:when test="${obs['class'].simpleName == 'IQCD4Obs'}">CD4</c:when>
							<c:when test="${obs['class'].simpleName == 'IQHeightObs'}">Height</c:when>
							<c:when test="${obs['class'].simpleName == 'IQWeightObs'}">Weight</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>