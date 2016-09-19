package com.fpt.bean.datasource;

import java.util.stream.Stream;

/**
 * Created by zack on 9/19/2016.
 */
public interface DataSource<T> {

    T openStream();
}
