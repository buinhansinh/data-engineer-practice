package com.fpt;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.junit.After;
import org.junit.Before;

/**
 * Created by zack on 9/19/2016.
 */
public class AbstractTest {

    protected static final String ONLINE_SOURCE_URL = "http://archive.ics.uci.edu/ml/machine-learning-databases/adult/adult.data";
    protected HttpClient httpClient;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @After
    public void close(){
        this.httpClient.close();
    }

}
