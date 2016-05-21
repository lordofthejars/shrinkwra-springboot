package org.shrinkwrap.springboot;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.impl.base.exporter.AbstractExporterDelegate;

import java.io.InputStream;

class ZipStoreExporterDelegate extends AbstractExporterDelegate<InputStream> {

    protected ZipStoreExporterDelegate(final Archive<?> archive) {
        super(archive);

        // Precondition check
        if (archive.getContent().isEmpty()) {
            throw new IllegalArgumentException(
                    "[SHRINKWRAP-93] Cannot use this JDK-based implementation to export as ZIP an archive with no content: "
                            + archive.toString());
        }
    }

    @Override
    protected void processNode(final ArchivePath path, final Node node) {
        // do nothing
    }

    @Override
    protected InputStream getResult() {
        return new ZipStoreOnDemandInputStream(getArchive());
    }
}
