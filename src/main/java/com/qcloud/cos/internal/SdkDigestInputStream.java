package com.qcloud.cos.internal;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;



public class SdkDigestInputStream extends DigestInputStream implements Releasable {
    public SdkDigestInputStream(InputStream stream, MessageDigest digest) {
        super(stream, digest);
    }

    @Override
    public final void release() {
        // Don't call IOUtils.release(in, null) or else could lead to infinite loop
        SdkIOUtils.closeQuietly(this);
        if (in instanceof Releasable) {
            // This allows any underlying stream that has the close operation
            // disabled to be truly released
            Releasable r = (Releasable) in;
            r.release();
        }
    }
}
