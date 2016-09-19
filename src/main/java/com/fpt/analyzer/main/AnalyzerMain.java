package com.fpt.analyzer.main;

import com.fpt.bean.datasource.CSVDataSource;
import com.fpt.bean.datasource.DataSource;
import com.fpt.bean.datasource.ObjectDataSource;
import com.fpt.bean.datasource.OnlineDataSource;
import com.fpt.bean.model.Person;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zack on 9/19/2016.
 */
public class AnalyzerMain {
    private static final String ONLINE_SOURCE_URL = "http://archive.ics.uci.edu/ml/machine-learning-databases/adult/adult.data";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Vertx vertx = Vertx.vertx();
    private final HttpClient httpClient = vertx.createHttpClient(new HttpClientOptions().setConnectTimeout(20000));

    public void start() throws FileNotFoundException {
        final DataSource<Stream<Person>> personSource = new ObjectDataSource<>(new CSVDataSource(new OnlineDataSource(ONLINE_SOURCE_URL,httpClient)), csvLine -> new Person(csvLine));
        final List<Person> list = personSource.openStream().collect(Collectors.toList());
        final DataAnalyzer<Person> question2 = new Question2Analyzer();
        final DataAnalyzer<Person> question3 = new Question3Analyzer();
        final ResultDisplayer question2Result = question2.analyze(list.stream());
        final ResultDisplayer question3Result = question3.analyze(list.stream());
        question2Result.display(new FileOutputStream("question2.txt"));
        question3Result.display(new FileOutputStream("question3.txt"));
        logger.info("Analyzing completed");
        vertx.close();
    }


    public static void main(String... args) throws FileNotFoundException {
        new AnalyzerMain().start();
    }
}
