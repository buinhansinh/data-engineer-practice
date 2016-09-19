package com.fpt.analyzer.main;

import com.fpt.AbstractTest;
import com.fpt.bean.datasource.CSVDataSource;
import com.fpt.bean.datasource.DataSource;
import com.fpt.bean.datasource.ObjectDataSource;
import com.fpt.bean.datasource.OnlineDataSource;
import com.fpt.bean.model.Person;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by zack on 9/19/2016.
 */
public class Question2AnalyzerTest extends AbstractTest {
    private Question2Analyzer analyzer;

    @Before
    public void setUp(){
        this.analyzer = new Question2Analyzer();
        this.httpClient = Vertx.vertx().createHttpClient(new HttpClientOptions().setConnectTimeout(10000));
    }

    @Test
    public void testWithSimpleDataSource() throws UnsupportedEncodingException {
        final List<Person> personList = new ArrayList<Person>(){{
            add(new Person("52, Self-emp-inc, 287927, HS-grad, 9, Married-civ-spouse, Exec-managerial, Wife, White, Female, 15024, 0, 40, United-States, >50K"));
            add(new Person("50, Self-emp-inc, 287927, HS-grad, 4, Married-civ-spouse, Exec-managerial, Wife, White, Male, 15024, 0, 40, United-States, <=50K"));
        }};
        ResultDisplayer rd = analyzer.analyze(personList.stream());
        final ResultBuffer rb = new ResultBuffer();
        rd.display(rb.buffer());
        assertEquals("--- Counting and grouping by education level, where income >50K ----------------------------------------[HS-grad] groupSize = 1 takes 50.00%  in total-------------------------------------Total people that income > 50k = 1, takes 50.00% in totalTotal people = 2",rb.getBuffer().replaceAll("[\n\r]",""));
    }

    @Test
    public void testWithOnlineCSVDataSource() throws UnsupportedEncodingException {
        final DataSource<Stream<Person>> personSource = new ObjectDataSource<>(new CSVDataSource(new OnlineDataSource(ONLINE_SOURCE_URL,httpClient)), csvLine -> new Person(csvLine));
        ResultDisplayer rd = analyzer.analyze(personSource.openStream());
        final ResultBuffer rb = new ResultBuffer();
        rd.display(rb.buffer());
        logger.info("Statistics: ");
        logger.info(rb.getBuffer());
        assertEquals("--- Counting and grouping by education level, where income >50K ----------------------------------------[10th] groupSize = 62 takes 0.19%  in total-------------------------------------[11th] groupSize = 60 takes 0.18%  in total-------------------------------------[12th] groupSize = 33 takes 0.10%  in total-------------------------------------[1st-4th] groupSize = 6 takes 0.02%  in total-------------------------------------[5th-6th] groupSize = 16 takes 0.05%  in total-------------------------------------[7th-8th] groupSize = 40 takes 0.12%  in total-------------------------------------[9th] groupSize = 27 takes 0.08%  in total-------------------------------------[Assoc-acdm] groupSize = 265 takes 0.81%  in total-------------------------------------[Assoc-voc] groupSize = 361 takes 1.11%  in total-------------------------------------[Bachelors] groupSize = 2221 takes 6.82%  in total-------------------------------------[Doctorate] groupSize = 306 takes 0.94%  in total-------------------------------------[HS-grad] groupSize = 1675 takes 5.14%  in total-------------------------------------[Masters] groupSize = 959 takes 2.95%  in total-------------------------------------[Prof-school] groupSize = 423 takes 1.30%  in total-------------------------------------[Some-college] groupSize = 1387 takes 4.26%  in total-------------------------------------Total people that income > 50k = 7841, takes 24.08% in totalTotal people = 32561",rb.getBuffer().replaceAll("[\n\r]",""));
    }

    private OutputStream stringBuffer() {
        return new BufferedOutputStream(new ByteArrayOutputStream());
    }

}