package configuration;

import api.ApiMethod;
import api.ApiRequest;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.annotations.*;

public class Before {
    public static ApiRequest apiRequest;
    public static Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

    @BeforeSuite
    public static void createRequestSpecification() {
        apiRequest = new ApiRequest()
                .baseUri(dotenv.get("URI"))
                .addHeader("X-TrackerToken", dotenv.get("TOKEN"));
    }

    @BeforeTest(groups = "GetRequest")
    public static void GetRequest() {
        apiRequest.method(ApiMethod.GET);
    }

    @BeforeTest(groups = "PostRequest")
    public static void PostRequest() {
        apiRequest.method(ApiMethod.POST);
    }

}
