package com.ardan1.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.server.environment.EnvironmentController;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ConfigApplicationTests {

  @Test
  public void contextLoads() {}

  @Autowired
  EnvironmentController environmentController;

  private MockMvc mvc;

  @Before
  public void setup() {
    this.mvc = MockMvcBuilders.standaloneSetup(environmentController).build();
  }

  @Test
  public void testProfiles() throws Exception {
    List<String> profiles = Arrays.asList("dev","test","prod");
    profiles.stream().forEach(profile -> {
      try {
        String path = "/appname/" + profile;
        String executePost = mvc.perform(get(path)).andExpect(status().is2xxSuccessful()).andReturn()
            .getResponse().getContentAsString();
        Assert.assertNotNull(executePost);
        log.info(executePost);
      } catch (Exception e) {
        log.error("Error testing profile!", e);
      }
    });
  }
}
