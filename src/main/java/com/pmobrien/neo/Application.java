package com.pmobrien.neo;

import com.pmobrien.neo.pojo.User;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;

public class Application {

  public static void main(String[] args) {
    Path path = null;
    
    try {
      path = Paths.get(Paths.get("").toAbsolutePath().toString(), "neo-store");
      path.toFile().mkdir();

      Configuration configuration = new Configuration();
      configuration.driverConfiguration()
          .setURI(path.toUri().toString())
          .setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");
      
      SessionFactory sessionFactory = new SessionFactory(configuration, "com.pmobrien.neo.pojo");

      User me = new User()
          .setUuid(UUID.randomUUID())
          .setUsername("patrick");
      
      sessionFactory.openSession().save(me);

      System.out.println(sessionFactory.openSession().load(User.class, me.getUuid()).toJson());
    } catch(Exception ex) {
      ex.printStackTrace(System.out);
    } finally {
      if(path != null) {
        FileUtils.deleteQuietly(path.toFile());
      }
      
      System.exit(0);
    }
  }
}
