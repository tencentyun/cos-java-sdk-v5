package com.qcloud.cos.utils;

import org.apache.commons.codec.binary.Base64;

class Base64Codec implements Codec {

    @Override
    public byte[] encode(byte[] src) {
        return Base64.encodeBase64(src, false);
    }

    @Override
    public byte[] decode(byte[] src, final int length) {
        return Base64.decodeBase64(src);
    }

}
