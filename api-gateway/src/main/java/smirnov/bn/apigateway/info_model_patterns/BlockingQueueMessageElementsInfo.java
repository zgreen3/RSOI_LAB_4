package smirnov.bn.apigateway.info_model_patterns;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;

public class BlockingQueueMessageElementsInfo {
    URI uri;
    HttpMethod httpMethod;
    HttpEntity httpEntity;

    public BlockingQueueMessageElementsInfo() {}

    public BlockingQueueMessageElementsInfo(URI uri, HttpMethod httpMethod, HttpEntity httpEntity) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.httpEntity = httpEntity;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }
}
