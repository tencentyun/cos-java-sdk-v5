package com.qcloud.cos.internal.cihandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

// 自定义字符过滤器
public class CharacterFilter extends Reader {
    private final BufferedReader reader;

    public CharacterFilter(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int numChars = reader.read(cbuf, off, len);
        if (numChars == -1) {
            return -1;
        }
        // 过滤无效字符
        for (int i = off; i < off + numChars; i++) {
            char c = cbuf[i];
            if (isValidXMLCharacter(c)) {
                cbuf[i] = c;
            } else {
                cbuf[i] = ' '; // 替换为空格或其他有效字符
            }
        }
        return numChars;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    // 检查字符是否是有效的 XML 字符
    private static boolean isValidXMLCharacter(char c) {
        // 排除无效的控制字符
        return c == 0x9 || c == 0xA || c == 0xD || (c >= 0x20 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0xFFFD) || (c >= 0x10000 && c <= 0x10FFFF);
    }
}
