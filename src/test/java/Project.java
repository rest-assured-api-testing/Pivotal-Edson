import api.ApiManager;
import api.ApiMethod;
import api.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static configuration.Before.apiRequest;
import static configuration.Before.createRequestSpecification;

public class Project {

    @BeforeClass
    public void loadRequestSpecification() {
        createRequestSpecification();
    }

    @Test
    public void getProject() {
        apiRequest.method(ApiMethod.GET)
                .endpoint("projects");
        ApiResponse apiResponse = ApiManager.execute(apiRequest);
        Assert.assertEquals(apiResponse.getStatusCode(), 200);
    }
}
