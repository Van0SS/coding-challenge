package com.van0ss.challenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate client;

    @Test
    public void testGet() {
//		String uuid = "de9e952f66914948b36031b00316f891";
//		ExampleModel model = ExampleModel.builder().id(uuid).field("some").build();
//		ResponseEntity<ExampleModel> resp = client.getForEntity("http://localhost:" + port + "/controller?id=" + uuid, ExampleModel.class);
//		assertThat(resp.getStatusCode(), is(HttpStatus.OK));
//		assertThat(resp.getBody(), is(model));
    }
}
