import org.example.Food;

public class RequestFactory
{
    public static ApiRequest createRequest(String method, String url, Food food)
    {
        if("GET".equalsIgnoreCase(method))
        {
            return new GetRequest(url);
        }
        if("POST".equalsIgnoreCase(method))
        {
            return new PostRequest(url,food);
        }
        throw new IllegalArgumentException("invalid method type");
    }
}
