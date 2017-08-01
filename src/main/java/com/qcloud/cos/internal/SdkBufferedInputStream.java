package com.qcloud.cos.internal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.qcloud.cos.exception.AbortedException;

public class SdkBufferedInputStream extends BufferedInputStream {
    public SdkBufferedInputStream(InputStream in) {
        super(in);
    }

    public SdkBufferedInputStream(InputStream in, int size) {
        super(in, size);
    }
    
    /**
     * Aborts with subclass specific abortion logic executed if needed.
     * Note the interrupted status of the thread is cleared by this method.
     * @throws AbortedException if found necessary.
     */
    protected final void abortIfNeeded() {
        if (Thread.interrupted()) {
            abort();    // execute subclass specific abortion logic
            throw new AbortedException();
        }
    }

    /**
     * Can be used to provide abortion logic prior to throwing the
     * AbortedException. No-op by default.
     */
    protected void abort() {
    }

    @Override
    public int read() throws IOException {
        abortIfNeeded();
        return super.read();
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        abortIfNeeded();
        return super.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        abortIfNeeded();
        return super.skip(n);
    }

    @Override
    public int available() throws IOException {
        abortIfNeeded();
        return super.available();
    }

    @Override
    public void close() throws IOException {
        super.close();
        abortIfNeeded();
    }

    @Override
    public void mark(int readlimit) {
        abortIfNeeded();
        super.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        abortIfNeeded();
        super.reset();
    }

    @Override
    public boolean markSupported() {
        abortIfNeeded();
        return super.markSupported();
    }
}
