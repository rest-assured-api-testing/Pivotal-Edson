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
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }
}
