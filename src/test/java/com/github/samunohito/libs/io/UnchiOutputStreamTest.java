package com.github.samunohito.libs.io;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class UnchiOutputStreamTest {
    @Test
    void write() throws IOException {
        String testText = "本日は晴天でござる";
        String actual;

        var baos = new ByteArrayOutputStream();
        var uos = new UnchiOutputStream(baos);
        uos.write(testText.getBytes(StandardCharsets.UTF_8));
        uos.flush();
        uos.close();
        baos.close();

        actual = baos.toString(StandardCharsets.UTF_8);

        System.out.println(actual);
    }

    @Test
    void writeWithWriter() throws IOException {
        String testText = "本日は晴天でござる";
        String actual;

        try (
                var baos = new ByteArrayOutputStream();
                var uos = new UnchiOutputStream(baos);
                var osw = new OutputStreamWriter(uos);
                var bw = new BufferedWriter(osw);
        ) {
            bw.write(testText);
            bw.flush();
            actual = baos.toString(StandardCharsets.UTF_8);
        }

        System.out.println(actual);
    }
}
