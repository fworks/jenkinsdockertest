package com.fworks.config;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.include=test")
@ActiveProfiles(profiles = "test")
public class ConfigApplicationTests {

  @Autowired
  MessageRestController messageRestController;
  
  private static MockMvc mvc;

  @Before
  public void setup() {
    if(mvc == null) {
      mvc = MockMvcBuilders.standaloneSetup(messageRestController).build();
    }
  }

  @Test
  public void contextLoads() throws UnsupportedEncodingException, Exception {
    String contentAsString = mvc.perform(get("/message")).andReturn().getResponse().getContentAsString();
    assertNotNull(contentAsString);
    System.out.println(contentAsString);
  }

}
