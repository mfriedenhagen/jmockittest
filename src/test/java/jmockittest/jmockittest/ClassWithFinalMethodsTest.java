/**
 * Copyright 2010 Mirko Friedenhagen 
 */

package jmockittest.jmockittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import mockit.Expectations;
import mockit.Mocked;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

/**
 * @author mirko
 * 
 */
public class ClassWithFinalMethodsTest {

    @Mocked
    ClassWithFinalMethods mock;

    /**
     * Test method for {@link jmockittest.jmockittest.ClassWithFinalMethods#getString()}.
     */
    @Test
    public void testGetString() {
        new Expectations() {
            {
                mock.getString();
                result = "Hullo";
            }
        };
        assertEquals("Hullo", mock.getString());
    }

    @Test
    public void getGmxHomePage() throws URISyntaxException, ClientProtocolException, IOException {
        final HttpClient client = new DefaultHttpClient();
        final String homepage = "https://www.gmx.net/";
        new Expectations() {
            {
                mock.getString();
                result = homepage;
            }
        };
        final HttpGet get = new HttpGet(mock.getString());
        final URI actualURI = get.getURI();
        assertEquals(new URI(homepage), actualURI);
        final String html = client.execute(get, new BasicResponseHandler());
        assertThat(
                html,
                containsString("<title>GMX - E-Mail, FreeMail, De-Mail, Themen- &amp; Shopping-Portal - kostenlos</title>"));
    }

}
