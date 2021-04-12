<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag trimDirectiveWhitespaces="true" %>
<%@attribute name="fieldName" required="true" %>
<%@attribute name="ascendingLabel" required="false" %>
<%@attribute name="descendingLabel" required="false" %>
<%@attribute name="selected" required="false" %>
<c:set var="ascendingLabel" value="${(empty ascendingLabel) ? 'Ascending' : ascendingLabel}"/>
<c:set var="descendingLabel" value="${(empty descendingLabel) ? 'Descending' : descendingLabel}"/>
<c:set var="selected"
       value="${param.get('sort') eq fieldName ? param.get('order') : selected }"/>
<form>
    <label for="${fieldName}Sort">Sorting:</label>
    <input type="hidden" name="sort" value="${fieldName}">
    <input type="hidden" name="query" value="${param.query}">
    <select id="${fieldName}Sort" name="order"
            onchange='this.form.submit()' class="form-select form-select-sm">
        <option></option>
        <option ${selected eq 'ASC' ? 'selected' : ''} value='ASC'>${ascendingLabel}</option>
        <option ${selected eq 'DESC' ? 'selected' : ''} value='DESC'>${descendingLabel}</option>
    </select>
</form>