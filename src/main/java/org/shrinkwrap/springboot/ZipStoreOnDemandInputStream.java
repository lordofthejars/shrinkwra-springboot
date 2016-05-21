package org.shrinkwrap.springboot;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.impl.base.exporter.AbstractOnDemandInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class ZipStoreOnDemandInputStream extends AbstractOnDemandInputStream<ZipOutputStream> {

    /**
     * Creates stream directly from archive.
     *
     * @param archive
     */
    ZipStoreOnDemandInputStream(final Archive<?> archive) {
        super(archive);
    }

    @Override
    protected ZipOutputStream createOutputStream(final OutputStream outputStream) {
        ZipOutputStream z = new ZipOutputStream(outputStream);
        z.setMethod(ZipOutputStream.STORED);

        return z;
    }

    @Override
    protected void closeEntry(final ZipOutputStream outputStream) throws IOException {
        outputStream.closeEntry();
    }

    @Override
    protected void putNextEntry(final ZipOutputStream outputStream, final String context) throws IOException {
        final ZipEntry e = new ZipEntry(context);

        outputStream.putNextEntry(e);
    }
}