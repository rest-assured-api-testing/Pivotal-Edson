import api.ApiManager;
import api.ApiResponse;
import configuration.Before;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NotificationTest extends Before {

    @Test(groups = "GetRequest")
    public void getNotifications() {
        apiRequest.endpoint("/my/notifications")
                .addQueryParam("envelope", "true");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = "GetRequest")
    public void ItShouldBodyVoidWithTheParamFalse() {
        apiRequest.endpoint("/my/notifications")
                .addQueryParam("envelope", "false");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = "GetRequest")
    public void ItShouldBodyVoidWithTheParamNumberZero() {
        apiRequest.endpoint("/my/notifications")
                .addQueryParam("envelope", "0");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = "GetRequest")
    public void ItShouldBodyVoidWithTheParamAnyNumberOLetter() {
        apiRequest.endpoint("/my/notifications")
                .addQueryParam("envelope", "1asd");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }
}
