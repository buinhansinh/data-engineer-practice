package com.fpt.analyzer.main;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by zack on 9/19/2016.
 */
public class ResultBuffer {

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public OutputStream buffer(){
        return this.bos;
    }

    public String getBuffer() throws UnsupportedEncodingException {

        return this.bos.toString("UTF-8");
    }
}
