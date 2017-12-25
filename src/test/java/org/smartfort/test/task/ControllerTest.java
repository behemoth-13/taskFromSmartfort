package org.smartfort.test.task;


import com.fasterxml.jackson.databind.JsonNode;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.netty.connector.NettyConnectorProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.netty.NettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.glassfish.jersey.test.util.runner.ConcurrentRunner;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.smartfort.test.task.controller.App;
import org.smartfort.test.task.controller.ControllerConfig;
import org.smartfort.test.task.controller.JacksonConfigurator;
import org.smartfort.test.task.model.User;
import org.smartfort.test.task.service.UserService;
import org.smartfort.test.task.service.impl.UserServiceImpl;

import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(ConcurrentRunner.class)
public class ControllerTest extends JerseyTest {
    @Override
    protected ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(ControllerConfig.class, JacksonConfigurator.class);
    }

    @Override
    protected void configureClient(ClientConfig clientConfig) {
        clientConfig.connectorProvider(new NettyConnectorProvider());
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new NettyTestContainerFactory();
    }

    @BeforeClass
    public static void fillDataInDB() {
        String url = "jdbc:mysql://localhost?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            conn.prepareStatement("DROP SCHEMA IF EXISTS smartforttest").execute();
            conn.prepareStatement("CREATE SCHEMA IF NOT EXISTS smartforttest").execute();
            conn.prepareStatement("CREATE TABLE `smartforttest`.`user` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `first_name` VARCHAR(45) NOT NULL,\n" +
                    "  `last_name` VARCHAR(45) NOT NULL,\n" +
                    "  `email` VARCHAR(45) NOT NULL,\n" +
                    "  `date_of_birth` DATE NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int id = 1;
        String firstName = "fname";
        String lastName = "lname";
        String email = "example@mail.com";
        String strDate = "20.10.2017";
        Date date = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            date  = dateFormat.parse(strDate);
        }
        catch(ParseException e){
            fail();
        }
        User user = new User(id, firstName, lastName, email, date);
        UserService service = UserServiceImpl.getInstance();
        service.createUser(user);
    }

    @Test
    public void testConnection() {
        Response response = target().path(App.ROOT_PATH).request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, response.getStatus());
    }



    @Test
    @Ignore
    public void testGetUserById(){
        int id = 1;
        String firstName = "fname";
        String lastName = "lname";
        String email = "example@mail.com";
        String strDate = "19.10.2017";
        Response output = target("/user/1").request().get();
        assertEquals("Should return status 200", 200, output.getStatus());
        JsonNode jsonNode = output.readEntity(JsonNode.class);
        assertEquals(id, jsonNode.get("id").asInt());
        assertEquals(firstName, jsonNode.get("firstName").asText());
        assertEquals(lastName, jsonNode.get("lastName").asText());
        assertEquals(email, jsonNode.get("email").asText());
        assertEquals(strDate, jsonNode.get("dateOfBirth").asText());
    }

    @Test
    @Ignore
    public void testAsyncClientRequests() throws InterruptedException {
        final int REQUESTS = 10;
        final CountDownLatch latch = new CountDownLatch(REQUESTS);
        final long tic = System.currentTimeMillis();
        for (int i = 0; i < REQUESTS; i++) {
            target().path(App.ROOT_PATH).request(MediaType.APPLICATION_JSON).async().get(new InvocationCallback<Response>() {
                @Override
                public void completed(Response response) {
                    try {
                        final String result = response.readEntity(String.class);
                        String expected = "[{\"id\":1,\"firstName\":\"fname\",\"lastName\":\"lname\",\"email\":\"example@mail.com\",\"dateOfBirth\":\"19.10.2017\"}]";
                        assertEquals(expected, result);
                    } finally {
                        latch.countDown();
                    }
                }

                @Override
                public void failed(Throwable error) {
                    error.printStackTrace();
                    latch.countDown();
                }
            });
        }
        latch.await(10 * getAsyncTimeoutMultiplier(), TimeUnit.SECONDS);
        final long toc = System.currentTimeMillis();
        Logger.getLogger(ControllerTest.class.getName()).info("Executed in: " + (toc - tic));
    }

    @Test
    public void testHead() {
        Response response = target().path(App.ROOT_PATH).request().head();
        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    public void testFooBarOptions() {
        Response response = target().path(App.ROOT_PATH).request().header("Accept", "foo/bar").options();
        assertEquals(200, response.getStatus());
        final String allowHeader = response.getHeaderString("Allow");
        _checkAllowContent(allowHeader);
        assertEquals("foo/bar", response.getMediaType().toString());
        assertEquals(0, response.getLength());
    }

    private void _checkAllowContent(final String content) {
        assertTrue(content.contains("GET"));
        assertTrue(content.contains("HEAD"));
        assertTrue(content.contains("OPTIONS"));
    }

    @Test
    public void testMissingResourceNotFound() {
        Response response;

        response = target().path(App.ROOT_PATH + "notFound").request().get();
        assertEquals(404, response.getStatus());

        response = target().path(App.ROOT_PATH).path("notFound").request().get();
        assertEquals(404, response.getStatus());
    }
}
