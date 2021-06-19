import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import configuration.Before;
import entities.Account;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTest extends Before {

    @Test(groups = {"GetRequest"})
    public void getAllAccounts() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/accounts");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "GetAccounts"})
    public void getAnAccount() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/accounts/{accountId}")
                .addPathParam("accountId", account.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Account account = apiResponse.getBody(Account.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(account.getKind(), "account");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "GetAccounts"})
    public void ItShouldAnAccountWithParamsInUpperCase() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/accounts/{ACCOUNTID}")
                .addPathParam("ACCOUNTID", account.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Account account = apiResponse.getBody(Account.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(account.getKind(), "account");
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = {"GetRequest", "GetAccounts"})
    public void ItShouldAnAccountWithParamsInLowerCase() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/accounts/{accountid}")
                .addPathParam("accountid", account.getId().toString());

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Account account = apiResponse.getBody(Account.class);

        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        Assert.assertEquals(account.getKind(), "account");
        apiResponse.getResponse().then().log().body();
    }
}
