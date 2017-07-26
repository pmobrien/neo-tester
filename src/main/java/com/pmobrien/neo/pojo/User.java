package com.pmobrien.neo.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmobrien.neo.UUIDConverter;
import java.util.UUID;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;

@NodeEntity
public class User {

  @GraphId
  private Long id;
  
  @Convert(UUIDConverter.class)
  @Index(unique = true, primary = true)
  private UUID uuid;
  private String username;

  public UUID getUuid() {
    return uuid;
  }

  public User setUuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public User setUsername(String username) {
    this.username = username;
    return this;
  }
  
  public String toJson() {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch(JsonProcessingException ex) {
      return "";
    }
  }
}
