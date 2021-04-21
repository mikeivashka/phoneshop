<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="pageTitle" required="true" %>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<header>
    <div class="row justify-content-around pt-4">
        <div>
            <a href="${pageContext.servletContext.contextPath}">
                <h1>PhoneShop</h1>
            </a>
        </div>
        <div>
            <div class="text-right mb-1">
                <c:choose>
                    <c:when test="${not empty pageContext.request.remoteUser}">
                        <div class="float-right d-inline">
                            <span class="text-light"><c:out value="${pageContext.request.remoteUser}"/></span>
                            <a href="<c:url value="/admin/orders"/>" class="ml-2">Admin</a>
                            <form:form action="${pageContext.request.contextPath}/logout" method="post"
                                       class="d-inline-flex mb-0 ml-2">
                                <button type="submit" class="btn btn-link p-0">Logout</button>
                            </form:form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="/login"/>">Login</a>
                    </c:otherwise>
                </c:choose>
            </div>
            <jsp:include page="/cart/minicart"/>
        </div>
    </div>
    <hr/>
</header>
<main>
    <div class="container">
        <jsp:doBody/>
    </div>
</main>
</body>
<script type="text/javascript">
    function addCsrf(request) {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        request.setRequestHeader(header, token);
    }

    function addToCart(productId, quantity) {
        $.ajax({
            url: '${pageContext.servletContext.contextPath}/ajaxCart',
            method: 'post',
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                productId: productId,
                quantity: quantity
            }),
            beforeSend: function (request) {
                addCsrf(request)
            },
            success: function (data) {
                $('#cartTotalPrice').text(data.cartSubtotal.toFixed(1))
                $('#cartTotalQuantity').text(data.cartQuantity)
                const label = $("label[for='" + productId + "']")
                if (data.successStatus === true) {
                    label.attr("class", "text-success")
                } else {
                    label.attr("class", "text-danger")
                }
                label.text(data.message)
            }
        });
    }

    function deleteFromCart(productId) {
        $.ajax({
            url: '${pageContext.servletContext.contextPath}/ajaxCart',
            method: 'delete',
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify({
                productId: productId
            }),
            beforeSend: function (request) {
                addCsrf(request)
            },
            success: setTimeout(
                function () {
                    document.location.reload()
                }, 50
            )

        });
    }
</script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</html>