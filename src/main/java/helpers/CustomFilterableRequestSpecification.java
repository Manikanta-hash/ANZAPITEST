package helpers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.FilterableRequestSpecification;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CustomFilterableRequestSpecification {
    private FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification)RestAssured.given();

    public CustomFilterableRequestSpecification() {
    }

    public CustomFilterableRequestSpecification(CustomFilterableRequestSpecification requestSpecification) {
        FilterableRequestSpecification spec = requestSpecification.getFilterableRequestSpecification();
        this.addHeaders(spec.getHeaders());
        String type = spec.getContentType();
        this.setContentType(ContentType.fromContentType(type));
    }

    public void addCustomHeader(String headerName, String headerValue) {
        this.removeHeader(headerName);
        this.filterableRequestSpecification.header(headerName, headerValue, new Object[0]);
    }

    public final void addHeaders(Headers headers) {
        if (headers != null) {
            Iterator var2 = headers.iterator();

            while(var2.hasNext()) {
                Header header = (Header)var2.next();
                this.addCustomHeader(header.getName(), header.getValue());
            }
        }

    }


    public void addBodyToRequest(Object body) {
        this.filterableRequestSpecification.body(body);
    }

    public void addBodyToRequest(String body) {
        this.filterableRequestSpecification.body(body);
    }

    public void addPathParams(Map<String, String> pathParamsMap) {
        Set<String> keys = this.filterableRequestSpecification.getNamedPathParams().keySet();
        Object[] keyArray = keys.toArray();
        Object[] var4 = keyArray;
        int var5 = keyArray.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Object key = var4[var6];
            this.filterableRequestSpecification.removeNamedPathParam(key.toString());
        }

        this.filterableRequestSpecification.pathParams(pathParamsMap);
    }

    public void addQueryParams(Map<String, String> queryParamsMap) {
        Set<String> keys = this.filterableRequestSpecification.getQueryParams().keySet();
        Object[] keyArray = keys.toArray();
        Object[] var4 = keyArray;
        int var5 = keyArray.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Object key = var4[var6];
            this.filterableRequestSpecification.removeQueryParam(key.toString());
        }

        this.filterableRequestSpecification.queryParams(queryParamsMap);
    }

    public void addBasePath(String basePath) {
        this.filterableRequestSpecification.basePath(basePath);
    }

    public void addBaseURI(String baseURI) {
        this.filterableRequestSpecification.baseUri(baseURI);
    }

    public final void setContentType(ContentType contentType) {
        this.filterableRequestSpecification.contentType(contentType);
    }

    public void removeHeader(String headerName) {
        this.filterableRequestSpecification.removeHeader(headerName);
    }

    public void setRelaxedHttpsValidation() {
        this.filterableRequestSpecification.relaxedHTTPSValidation();
    }

    public FilterableRequestSpecification getFilterableRequestSpecification() {
        return this.filterableRequestSpecification;
    }
}

