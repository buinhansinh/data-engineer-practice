package com.fpt.bean.datasource;

import com.fpt.AbstractTest;
import com.fpt.bean.model.Person;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by zack on 9/19/2016.
 */

public class DataSourceTest extends AbstractTest {


    @Before
    public void setUp(){
        this.httpClient = Vertx.vertx().createHttpClient(new HttpClientOptions().setConnectTimeout(10000));
    }

    @Test
    public void testCanReadOnlineDataSource() throws Exception {
        final DataSource<String> onlineSource = new OnlineDataSource(ONLINE_SOURCE_URL,httpClient);
        final String content = onlineSource.openStream().replaceAll("\n","");
        assertEquals(result(),content);
    }

    @Test
    public void testCanReadOnlineCSVDataSource() throws Exception{
        final DataSource<Stream<String>> dataSource = new CSVDataSource(new OnlineDataSource(ONLINE_SOURCE_URL,httpClient));
        final Stream<String> csvLines = dataSource.openStream().filter(s -> s.length() > 0);
        assertEquals(32561,csvLines.count());
    }

    @Test
    public void testCanMapCSVSourceToPersonObject() throws Exception{
        final DataSource<Stream<Person>> personSource = new ObjectDataSource<>(new CSVDataSource(new OnlineDataSource(ONLINE_SOURCE_URL,httpClient)), csvLine -> new Person(csvLine));
        final Stream<Person> personStream = personSource.openStream();
        final Person person = personStream.limit(1).collect(Collectors.toList()).get(0);
        logger.info("Sample object: "+ Json.encodePrettily(person));
        assertEquals("{\"age\":39,\"workClass\":\"State-gov\",\"fnlwgt\":77516.0,\"education\":\"Bachelors\",\"educationNum\":13,\"maritialStatus\":\"Never-married\",\"occupation\":\"Adm-clerical\",\"relationship\":\"Not-in-family\",\"race\":\"White\",\"sex\":\"Male\",\"capitalGain\":2174.0,\"capticalLoss\":0.0,\"hoursPerWeek\":40.0,\"nativeCountry\":\"United-States\",\"salary\":\"<=50K\"}",Json.encode(person));
        assertEquals(32561,personSource.openStream().count());
    }

    private String result() throws Exception {
        return new String(Files.readAllBytes(Paths.get(getClass().getResource("/test.csv").toURI())),"UTF-8").replaceAll("[\n\r]","");
    }
}

