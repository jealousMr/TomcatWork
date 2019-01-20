package TomcatWorkThree;

import TomcatWorkThree.connector.http.HttpRequest;
import TomcatWorkThree.connector.http.HttpResponse;

import java.io.IOException;

public class StaticResourceProcessor {
    public void process(HttpRequest request, HttpResponse response) {
        try {
            response.sendStaticResource();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
