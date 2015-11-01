package test.restservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import controller.Application;
import model.Lead;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)

public class RestClientTest {

	private static final String BASE_URL = "http://localhost:8087/leads";
	@Autowired
	private RestTemplate restTemplate;

	@Ignore
	@Test
	public void saveAndGet() throws Exception {
		// create
		Lead input = new Lead();
		input.setName("Testlead");
		input.setInfo("testInfo");
		assertNull(input.getId());
		Lead output = restTemplate.postForObject(BASE_URL, input, Lead.class, new Object[] {});
		assertNotNull(output.getId());
		assertEquals(input.getName(), output.getName());

		// get all
		@SuppressWarnings("unchecked")
		List<Lead> leads = (List<Lead>) restTemplate.getForObject(BASE_URL + "/all", Lead.class, new Object[] {});
		assertNotNull("no leads", leads.get(55));
		assertNotNull("not empty", leads.size());

	}
}
