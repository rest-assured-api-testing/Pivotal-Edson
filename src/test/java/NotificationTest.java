import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static configuration.Before.*;

public class NotificationTest {

    @BeforeClass
    public void loadRequestSpecification() {
        createRequestSpecification();
    }

    @Test
    public void getNotificationsTest() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/my/notifications")
                .addQueryParam("envelope", "true");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }
}
