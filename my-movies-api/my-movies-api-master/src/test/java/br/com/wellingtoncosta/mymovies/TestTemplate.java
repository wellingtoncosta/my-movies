package br.com.wellingtoncosta.mymovies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

/**
 * @author Wellington Costa on 14/05/17.
 */
@FlywayTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class
})
public class TestTemplate {

    @Inject
    protected WebApplicationContext context;

    @Inject
    protected FilterChainProxy springSecurityFilterChain;

    protected MockMvc mvc;

    protected static String token;

    protected static final MediaType contentType = MediaType.APPLICATION_JSON;

    private static final Gson gson = new GsonBuilder().create();


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
    }


    public String toJson(Object src) {
        return gson.toJson(src);
    }

}

