package com.example.springboot.advice;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class DecryptHttpInputMessage implements HttpInputMessage {

    private HttpHeaders headers;
    private InputStream body;

    DecryptHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
        this.headers = inputMessage.getHeaders();
        String token = this.headers.get("ACCESS-TOKEN").get(0);
        String content = (new BufferedReader(new InputStreamReader(inputMessage.getBody()))).lines().collect(Collectors.joining(System.lineSeparator()));
        String decryptBody;
        if (content.startsWith("{")) {
            log.info("Unencrypted without decryption:{}", content);
            decryptBody = content;
        } else {
            StringBuilder json = new StringBuilder();
            if (!StringUtils.isEmpty(content)) {
                String[] contents = content.split("\\|");
                String[] var7 = contents;
                int var8 = contents.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    String value = var7[var9];
                    value = this.decrypt(token, value);
                    log.info("decrypt value==>{}", value);
                    json.append(value);
                }
            }

            decryptBody = json.toString();
        }

        this.body = new ByteArrayInputStream(decryptBody.getBytes());
    }

    @Override
    public InputStream getBody() {
        return this.body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    private String decrypt(String token, String data) {
        //解密
        return null;
    }
}