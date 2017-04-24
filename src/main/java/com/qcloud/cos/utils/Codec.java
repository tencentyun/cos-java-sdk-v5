package com.qcloud.cos.utils;

interface Codec {
    public byte[] encode(byte[] src);

    public byte[] decode(byte[] src, final int length);
}
