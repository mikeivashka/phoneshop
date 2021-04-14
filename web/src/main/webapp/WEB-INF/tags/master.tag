<%@ attribute name="pageTitle" required="true" %>
<html>
<head>
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
</html>