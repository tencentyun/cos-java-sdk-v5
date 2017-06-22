package com.qcloud.cos.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.qcloud.cos.exception.AbortedException;

/**
 * Base class for COS Java SDK specific {@link FilterInputStream}.
 */
public class SdkFilterInputStream extends FilterInputStream implements Releasable {
    protected SdkFilterInputStream(InputStream in) {
        super(in);
    }

    /**
     * Aborts with subclass specific abortion logic executed if needed. Note the interrupted status
     * of the thread is cleared by this method.
     * 
     * @throws AbortedException if found necessary.
     */
    protected final void abortIfNeeded() {
        if (Thread.interrupted()) {
            abort(); // execute subclass specific abortion logic
            throw new AbortedException();
        }
    }

    /**
     * Can be used to provide abortion logic prior to throwing the AbortedException. No-op by
     * default.
     */
    protected void abort() {
        // no-op by default, but subclass such as cosObjectInputStream may override
    }

    @Override
    public int read() throws IOException {
        abortIfNeeded();
        return in.read();
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        abortIfNeeded();
        return in.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        abortIfNeeded();
        return in.skip(n);
    }

    @Override
    public int available() throws IOException {
        abortIfNeeded();
        return in.available();
    }

    @Override
    public void close() throws IOException {
        in.close();
        abortIfNeeded();
    }

    @Override
    public synchronized void mark(int readlimit) {
        abortIfNeeded();
        in.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        abortIfNeeded();
        in.reset();
    }

    @Override
    public boolean markSupported() {
        abortIfNeeded();
        return in.markSupported();
    }

    @Override
    public void release() {
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
