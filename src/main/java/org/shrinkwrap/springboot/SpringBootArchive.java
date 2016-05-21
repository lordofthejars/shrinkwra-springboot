package org.shrinkwrap.springboot;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.ServiceProviderContainer;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public interface SpringBootArchive extends Archive<SpringBootArchive>, ServiceProviderContainer<SpringBootArchive>  {

    SpringBootArchive addSpringBootApplication(Class<?> startClass);
    SpringBootArchive addLib(JavaArchive lib);
    SpringBootArchive addLibs(JavaArchive...libs);
    SpringBootArchive addLauncher(JavaArchive... launcher);

}
