package com.fpt.bean.datasource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by zack on 9/19/2016.
 */
public class CSVDataSource implements DataSource<Stream<String>> {
    private final DataSource<String> dataSource;

    public CSVDataSource(DataSource<String> dataSource) {
        if(dataSource == null) throw new RuntimeException("Data source can not be empty");
        this.dataSource = dataSource;

    }

    @Override
    public Stream<String> openStream() {
        final StringReader reader = new StringReader(this.dataSource.openStream());
        final BufferedReader bufferReader = new BufferedReader(reader);
        return bufferReader.lines();
    }
}
