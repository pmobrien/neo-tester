package com.pmobrien.neo;

import com.pmobrien.neo.pojo.User;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApplicationTest extends Suite {

  private Path neoStorePath;
  private SessionFactory sessionFactory; 
  
  @BeforeClass
  public void beforeClass() {
    neoStorePath = Paths.get(Paths.get("").toAbsolutePath().toString(), "neo-store");
    neoStorePath.toFile().mkdir();

    Configuration configuration = new Configuration();
    configuration.driverConfiguration()
        .setURI(neoStorePath.toUri().toString())
        .setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");

    sessionFactory = new SessionFactory(configuration, "com.pmobrien.neo.pojo");
  }
  
  @AfterClass
  public void afterClass() {
    if(neoStorePath != null) {
      FileUtils.deleteQuietly(neoStorePath.toFile());
    }
  }
  
  @Test
  public void test() {
    User me = new User()
        .setUuid(UUID.randomUUID())
        .setUsername("patrick");

    sessionFactory.openSession().save(me);
    
    Assert.assertThat(
        sessionFactory.openSession().load(User.class, me.getUuid()).getUsername(),
        CoreMatchers.equalTo(("patrick"))
    );
  }
}
