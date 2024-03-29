package com.company.boardgamesshop.action.impl.factory;

import com.company.boardgamesshop.action.Action;
import com.company.boardgamesshop.action.impl.admin.*;
import com.company.boardgamesshop.action.impl.common.*;
import com.company.boardgamesshop.action.impl.user.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class FactoryAction {
    private static final Map<String, Action> SERVICE_MAP = new HashMap<>();
    private static final FactoryAction SERVICE_FACTORY = new FactoryAction();

    static {
        SERVICE_MAP.put("/registration", new RegistrationAction());
        SERVICE_MAP.put("/login", new LoginAction());
        SERVICE_MAP.put("/home", new AddAllProductsToShopAction());
        SERVICE_MAP.put("/product", new GetOneProductAction());
        SERVICE_MAP.put("/addProductToCart", new AddProductToMyBasketAction());
        SERVICE_MAP.put("/myBasket", new CheckMyBasketAction());
        SERVICE_MAP.put("/deleteProductFromCart", new DeleteProductFromMyBasketAction());
        SERVICE_MAP.put("/createProduct", new CreateNewProductAction());
        SERVICE_MAP.put("/updateProduct", new UpdateProductAction());
        SERVICE_MAP.put("/deactivateProduct", new ChangeActiveModeToProductAction());
        SERVICE_MAP.put("/allUsers", new CheckAllUsersAction());
        SERVICE_MAP.put("/banUser", new BanUserAction());
        SERVICE_MAP.put("/unbanUser", new UnbanUserAction());
        SERVICE_MAP.put("/logout", new ExitAction());
        SERVICE_MAP.put("/createOrder", new CreateOrderFromMyBasketAction());
        SERVICE_MAP.put("/ordersAdmin", new ListOfOrdersForAdminAction());
        SERVICE_MAP.put("/myOrders", new CheckMyOrdersAction());
        SERVICE_MAP.put("/changeStatus", new ChangeStatusToOrderAction());
        SERVICE_MAP.put("/changeLanguage", new ChangeLanguageAction());
        SERVICE_MAP.put("/order", new MakeMyOrderAction());
        SERVICE_MAP.put("/myProfile", new CheckMyProfileAndChangePasswordAction());
        SERVICE_MAP.put("/createNewProductCategory", new CreateNewProductCategoryAction());
        SERVICE_MAP.put("/updateProductCountInBasket", new UpdateProductCountInBasketAction());
        SERVICE_MAP.put("/updateProductCategory", new UpdateProductCategoryAction());
    }

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public static FactoryAction getInstance() {
        return SERVICE_FACTORY;
    }

    public Action getService(String request) {
        Action action = SERVICE_MAP.get("/home");
        try {
            for (Map.Entry<String, Action> pair : SERVICE_MAP.entrySet()) {
                if (request.equalsIgnoreCase(pair.getKey())) {
                    action = SERVICE_MAP.get(pair.getKey());
                }
            }
        } catch (NullPointerException | ClassCastException e) {
            LOGGER.error(e);
            action = SERVICE_MAP.get("/home");
        }
        return action;
    }
}
