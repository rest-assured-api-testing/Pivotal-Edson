import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import configuration.Before;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static configuration.Before.*;

public class NotificationTest extends Before {

    @Test
    public void getNotifications() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("/my/notifications")
                .addQueryParam("envelope", "true");

        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        apiResponse.getResponse().then().log().body();
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }
}
