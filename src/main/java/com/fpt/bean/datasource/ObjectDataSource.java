package com.fpt.bean.datasource;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by zack on 9/19/2016.
 */
public class ObjectDataSource<T> implements DataSource<Stream<T>> {

    private final DataSource<Stream<String>> stringSource;
    private final Function<String,T> mapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ObjectDataSource(DataSource<Stream<String>> stringSource, Function<String, T> mapper) {
        if(stringSource == null || mapper == null) throw new RuntimeException("Invalid mapper or data source");
        this.stringSource = stringSource;
        this.mapper = mapper;
    }

    @Override
    public Stream<T> openStream() {
        logger.warn("Empty lines will be filtered!");
        return this.stringSource.openStream().filter(s -> s.length() > 0 ).map(mapper);
    }
}
