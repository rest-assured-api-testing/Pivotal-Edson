import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import configuration.Before;
import entities.Account;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static configuration.Before.*;

public class AccountTest extends Before {

    @Test
    public void getAllAccounts() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/accounts");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }

    @Test
    public void getAAccount() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/accounts/{accountId}")
                .addPathParam("accountId", "1155189");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Account account = apiResponse.getBody(Account.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(account.getKind(), "account");
        apiResponse.validateBodySchema("schemas/account.json");

    }
}
