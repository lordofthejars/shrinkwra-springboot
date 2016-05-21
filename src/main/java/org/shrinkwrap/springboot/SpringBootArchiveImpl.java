package org.shrinkwrap.springboot;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.exporter.TarExporter;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.container.ContainerBase;
import org.jboss.shrinkwrap.impl.base.path.BasicPath;
import org.jboss.shrinkwrap.impl.base.spec.JavaArchiveImpl;

import java.util.logging.Logger;

/**
 * Implements Launching executable JARS for Spring Boot apps,
 * following http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#executable-jar-launching strategy
 *
 * Basically the most important thing to remain is that libs are placed in a root directory called lib.
 */
public class SpringBootArchiveImpl extends ContainerBase<SpringBootArchive> implements SpringBootArchive {

    public SpringBootArchiveImpl(Archive<?> archive) {
        super(SpringBootArchive.class, archive);
    }

    private static final Logger log = Logger.getLogger(JavaArchiveImpl.class.getName());

    /**
     * Path to the manifests inside of the Archive.
     */
    private static final ArchivePath PATH_MANIFEST = new BasicPath("META-INF");

    /**
     * Path to the resources inside of the Archive.
     */
    private static final ArchivePath PATH_RESOURCE = new BasicPath("/");

    /**
     * Path to the classes inside of the Archive.
     */
    private static final ArchivePath PATH_CLASSES = new BasicPath("/");

    /**
     * Path to the libraries inside of the Archive.
     */
    private static final ArchivePath PATH_LIBRARY = ArchivePaths.create(PATH_CLASSES, "lib");

    private static final String MANIFEST_FILE = "" +
            "Main-Class: org.springframework.boot.loader.JarLauncher" + System.lineSeparator() +
            "Start-Class: %s";

    @Override
    public SpringBootArchive addSpringBootApplication(Class<?> startClass) {
        this.addClass(startClass);
        final StringAsset manifestContent = new StringAsset(String.format(MANIFEST_FILE, startClass.getName()));

        this.addAsManifestResource(manifestContent, "MANIFEST.MF");
        return this;
    }

    @Override
    public SpringBootArchive addLib(JavaArchive lib) {
        this.addAsDirectory(PATH_LIBRARY);
        this.add(lib, PATH_LIBRARY, ZipStoreExporter.class);
        return this;
    }

    @Override
    public SpringBootArchive addLibs(JavaArchive... libs) {
        for (JavaArchive lib : libs) {
            this.addLib(lib);
        }
        return this;
    }

    @Override
    public SpringBootArchive addLauncher(JavaArchive... launcher) {

        for (JavaArchive l : launcher) {
            this.merge(l);
        }

        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.declarchive.impl.base.ContainerBase#getManifestPath()
     */
    @Override
    protected ArchivePath getManifestPath() {
        return PATH_MANIFEST;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.shrinkwrap.impl.base.container.ContainerBase#getClassesPath()
     */
    @Override
    protected ArchivePath getClassesPath() {
        return PATH_CLASSES;
    }

    @Override
    protected ArchivePath getLibraryPath() {
        return PATH_LIBRARY;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.declarchive.impl.base.ContainerBase#getResourcePath()
     */
    @Override
    protected ArchivePath getResourcePath() {
        return PATH_RESOURCE;
    }
}
