package test.restservices;


import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import static java.util.Arrays.asList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import model.lead;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationTestsApplicationConfiguration.class)

public class ControllerTest {
	 public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

	    @Autowired
	    private WebApplicationContext webApplicationContext;
	    private MockMvc mockMvc;

	    @Before
	    public void setUp() {
	        mockMvc = MockMvcBuilders
	                .webAppContextSetup(webApplicationContext)
	                .build();
	    }
	    @Test
	    public void getLeadById() throws Exception {
	        String jsonData = mockMvc
	        		.perform(get("/leads/{id}")
	                .param("id", "77"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                .andReturn()
	                .getResponse()
	                .getContentAsString();

	        lead lead = JsonUtil.readObject(jsonData, lead.class);

	        assertNotNull("Lead can't be a null", lead);
	        assertEquals("IDs aren't match", 77,lead.getId().longValue());
	        assertNotNull("Name can't be empty", lead.getName());
	        
	    }
	    @Test
	    public void getAll() throws Exception 
	    {	 String jsonData = mockMvc
	                 .perform(get("/leads/all"))
	                 .andExpect(status().isOk())
	                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                 .andReturn()
	                 .getResponse()
	                 .getContentAsString();
	    	List<lead> leads = asList(JsonUtil.readObject(jsonData, lead[].class));
	        	 assertFalse(leads.isEmpty());
      
	        	    }
	    @Test
	    public void create()throws Exception
	    {
	    	String jsonData=mockMvc
	    			.perform(post("/leads/create/{name}")
	    			.param("name","TestMockName")
	    			.param("info","TestInfo"))
	    			.andExpect(status().isOk())
	    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	    			.andReturn()
	    			.getResponse()
	    			.getContentAsString();
	    	
	    	Long idcreated=JsonUtil.readObject(jsonData, lead.class).getId();
	    	
	    	assertNotNull("Lead id cann't be a null",idcreated);
	    }
	    @Test
	    public void update() throws Exception
	    {
	    	String jsonData=mockMvc
	    			.perform(put("/leads/update/{id}")
	    			.param("id", "82","name","NameUpdated","info","InfoUpdated"))
	    			.andExpect(status().isOk())
	    			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	    			.andReturn()
	    			.getResponse()
	    			.getContentAsString();
	    	
	    	Long idupdated= JsonUtil.readObject(jsonData, lead.class).getId();
	    	assertNotNull("Lead id cannot be a null",idupdated);
	    }
}
