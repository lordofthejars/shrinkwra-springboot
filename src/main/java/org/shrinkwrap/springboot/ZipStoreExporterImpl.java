package org.shrinkwrap.springboot;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.impl.base.exporter.AbstractExporterDelegate;
import org.jboss.shrinkwrap.impl.base.exporter.AbstractStreamExporterImpl;

import java.io.InputStream;

public class ZipStoreExporterImpl extends AbstractStreamExporterImpl implements ZipStoreExporter {

    public ZipStoreExporterImpl(Archive<?> archive) {
        super(archive);
    }

    @Override
    public InputStream exportAsInputStream() {
        // Create export delegate
        final AbstractExporterDelegate<InputStream> exportDelegate = new ZipStoreExporterDelegate(this.getArchive());

        // Export and get result
        return exportDelegate.export();
    }
}
