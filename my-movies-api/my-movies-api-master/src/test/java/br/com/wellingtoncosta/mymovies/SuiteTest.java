package br.com.wellingtoncosta.mymovies;

import br.com.wellingtoncosta.mymovies.web.MovieControllerTest;
import br.com.wellingtoncosta.mymovies.web.UserControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Wellington Costa on 14/05/17.
 */
@RunWith(Suite.class)
@SuiteClasses({
    UserControllerTest.class,
    MovieControllerTest.class
})
public class SuiteTest { }
