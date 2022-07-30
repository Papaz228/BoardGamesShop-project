
<%@ include file="header.jsp" %>
<div class="cart-box-main">
    <div class="container">

        <div class="row">
            <div class="col-sm-6 col-lg-6 mb-3">
                <div class="checkout-address">
                    <div class="title-left">
                        <h3>Billing address</h3>
                    </div>

                    <form class="form-signin" method="post" action="createOrder">

                        <div class="mb-3">
                            <label for="country">Bank Card Number</label>

                            <input type="text" class="form-control" id="country" placeholder="7777777777777777" name="bankCardNumber" required autofocus>
                        </div>

                        <div class="mb-3">
                            <label for="country">CVV</label>

                            <input type="password" class="form-control" id="" placeholder="***" name="bankCardCVV" required autofocus>
                        </div>

                            <input type="hidden" name="totalCost" value="<c:out value="${totalPrice}"/>">
                        <button name="button" value="sumbit" class="btn btn-outline-success my-2 my-sm-0"
                                type="submit">Place Order</button>
                    </form>
                </div>
            </div>
            <div class="col-sm-6 col-lg-6 mb-3">
                <div class="row">


                    <div class="col-md-12 col-lg-12">
                        <div class="order-box">
                            <div class="title-left">
                                <h3>Your order</h3>
                            </div>

                            <div class="d-flex gr-total">
                                <h5>Total price</h5>
                                <div class="ml-auto h5"> ${totalPrice}  </div>
                            </div>
                            <hr>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>