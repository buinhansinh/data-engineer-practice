package com.fpt.analyzer.main;

import com.fpt.bean.datasource.DataSource;

import java.util.stream.Stream;

/**
 * Created by zack on 9/19/2016.
 */
public interface DataAnalyzer<T> {

    ResultDisplayer analyze(Stream<T> dataSource);
}
