import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import datadog.trace.api.Trace;

public class HttpGetExample {

    @Trace(operationName = "main.method", resourceName = "get.google")
    public static void main(String[] args) {
        try {
            // Get the active span
            final Span span = GlobalTracer.get().activeSpan();
            if (span != null) {
                // Custom tags for Datadog APM
                span.setTag("custom.param.TestTagStringList.sl501..ddContainer", "ArrayList");
                span.setTag("custom.param.TestTagStringList.sl501.0", " SL501");
                span.setTag("customer.id", 123);
                span.setTag("customer.tier", 1234);
                span.setTag("cart.value", 12345);
            }

            // Send a GET request to Google's homepage
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Print response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read and print the response content
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder responseContent = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }
            in.close();

            System.out.println("Response Content:");
            System.out.println(responseContent.toString());

            // Close connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
