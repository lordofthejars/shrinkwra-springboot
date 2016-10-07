package org.shrinkwrap.springboot;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipStoredExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;

import java.io.File;

public class SpringBootArchiveImplTest {


    @Test
    public void shouldBeAbleToGenerateSpringBootJarFile() {
        final SpringBootArchive springBootArchive = ShrinkWrap.create(SpringBootArchive.class);
        springBootArchive.addClass(HelloController.class);

        springBootArchive.addLibs(
                Maven.resolver()
                        .resolve("junit:junit:4.12", "org.springframework.boot:spring-boot-starter-web:1.4.1.RELEASE")
                        .withTransitivity()
                        .as(JavaArchive.class));

        springBootArchive.addLauncher(
                Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-loader:1.3.5.RELEASE")
                        .withTransitivity()
                        .as(JavaArchive.class));

        springBootArchive.addSpringBootApplication(Application.class);

        System.out.println(springBootArchive.toString(true));
        springBootArchive.as(ZipStoredExporter.class).exportTo(new File("/tmp/demo2.jar"));
    }

}
