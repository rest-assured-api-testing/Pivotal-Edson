import api.ApiManager;
import api.ApiResponse;
import configuration.Before;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MeTest extends Before {

    @Test(groups = "GetRequest")
    public void getMe() {
        apiRequest.endpoint("me");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = "GetRequest")
    public void ItShouldFailWitheURIinUpperCaseWithError404() {
        apiRequest.endpoint("ME");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = "DeleteRequest")
    public void ItShouldFailWithTheProtocolDeleteWithError404() {
        apiRequest.endpoint("ME");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = "PostRequest")
    public void ItShouldFailWithTheProtocolPostWithError404() {
        apiRequest.endpoint("ME");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
        apiResponse.getResponse().then().log().body();
    }

    @Test(groups = "PutRequest")
    public void ItShouldFailWithTheProtocolPutWithError404() {
        apiRequest.endpoint("ME");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 404);
        apiResponse.getResponse().then().log().body();
    }


}
