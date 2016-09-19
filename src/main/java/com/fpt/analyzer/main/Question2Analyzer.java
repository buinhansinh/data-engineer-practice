package com.fpt.analyzer.main;

import com.fpt.bean.datasource.DataSource;
import com.fpt.bean.model.Person;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Question2Analyzer implements DataAnalyzer<Person>{

        private static final String EXPECTED_SALARY = ">50K";
        private Map<String,Long> result;
        private long totalPeople;
        private long totalPeopleWithExpectedSalary;
         private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public ResultDisplayer analyze(Stream<Person> dataSource) {
            logger.info(this.getClass()+ "starts analyzing");
            List<Person> list = dataSource.collect(Collectors.toList());
            result = list.stream().filter(person -> person.getSalary().equals(EXPECTED_SALARY))
                                                                    .collect(Collectors.groupingBy(Person::getEducation, TreeMap::new,Collectors.counting()));
            totalPeopleWithExpectedSalary = result.values().stream().collect(Collectors.summingLong(val -> val));
            totalPeople = list.size();
            return new StatisticDisplayer();
        }


        private class StatisticDisplayer implements ResultDisplayer{

            @Override
            public void display(OutputStream outputStream) {
                try(  final PrintWriter pw = new PrintWriter(outputStream)){
                    pw.println("--- Counting and grouping by education level, where income >50K ---");
                    result.entrySet().forEach( entry -> {
                        printSeparator(pw);
                        pw.println(String.format("[%s] groupSize = %d takes %.2f++  in total",entry.getKey(),entry.getValue(),entry.getValue()*100.0/totalPeople).replace("++","%"));

                    });
                    printSeparator(pw);
                    pw.println(String.format("Total people that income > 50k = %d, takes %.2f++ in total",totalPeopleWithExpectedSalary,totalPeopleWithExpectedSalary*100.0/totalPeople).replace("++","%"));
                    pw.println(String.format("Total people = %d",totalPeople));
                }catch(Exception ex){
                    throw new RuntimeException(ex);
                }
            }

            private void printSeparator(PrintWriter pw) {
                pw.println("-------------------------------------");
            }
        }

}
