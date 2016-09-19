package com.fpt.bean.datasource;

import io.vertx.core.http.HttpClient;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Created by zack on 9/19/2016.
 */
public class OnlineDataSource implements DataSource<String> {
    private final String url;
    private final HttpClient httpClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OnlineDataSource(String url, HttpClient httpClient) {
        this.url = url;
        this.httpClient = httpClient;
    }

    public String openStream() {
        final CompletableFuture<String>  asyncResult = new CompletableFuture<String>();
        logger.info("Downloading data at url: "+url);
        this.httpClient.getAbs(this.url,res -> {
            logger.info("Receiving data from online source");
            res.bodyHandler(content -> {
               asyncResult.complete(content.toString(StandardCharsets.UTF_8));
                logger.info("Receiving content completed");
            });
        }).end();
        try {
            return asyncResult.get();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public String getUrl() {
        return url;
    }
}
