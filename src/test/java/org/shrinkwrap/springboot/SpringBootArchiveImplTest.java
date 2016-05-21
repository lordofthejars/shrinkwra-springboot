package org.shrinkwrap.springboot;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;

import java.io.File;

public class SpringBootArchiveImplTest {


    @Test
    public void shouldBeAbleToGenerateSpringBootJarFile() {
        final SpringBootArchive springBootArchive = ShrinkWrap.create(SpringBootArchive.class);
        springBootArchive.addClass(MyPojo.class);
        springBootArchive.addSpringBootApplication(MyMain.class);
        springBootArchive.addLibs(
                Maven.resolver()
                        .resolve("junit:junit:4.12")
                      .withTransitivity()
                      .as(JavaArchive.class));

        springBootArchive.addLauncher(
                Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-loader:1.3.5.RELEASE")
                      .withTransitivity()
                      .as(JavaArchive.class));

        System.out.println(springBootArchive.toString(true));
       springBootArchive.as(ZipExporter.class).exportTo(new File("/tmp/demo2.jar"));
    }

}
