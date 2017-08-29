package br.com.wellingtoncosta.mymovies.web;

import br.com.wellingtoncosta.mymovies.TestTemplate;
import br.com.wellingtoncosta.mymovies.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Wellington Costa on 14/05/17.
 */
public class UserControllerTest extends TestTemplate {

    private static final String URL = "/api/users";

    private static final String USER_TEST_EMAIL = "test@email.com";

    private MockHttpServletRequestBuilder request;

    private User user;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void saveNewUser() throws Exception {
        givenThatHaveANewUserWithEmail(USER_TEST_EMAIL);
        whenSaveANewUser(user);
        thenShouldReceiveAUserWithEmail(USER_TEST_EMAIL);
    }

    @Test
    public void cheeckToken() {
        assertNotNull(token);
    }

    private void givenThatHaveANewUserWithEmail(String email) {
        user = User.builder().name("User Test").email(email).password("T3st").build();
    }

    private void whenSaveANewUser(User user) {
        request = post(URL)
                .content(toJson(user))
                .contentType(contentType);
    }

    private void thenShouldReceiveAUserWithEmail(String email) throws Exception {
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(email)))
                .andReturn();

        token = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    }


}
