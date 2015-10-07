package test.jdbctemplate;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Lead;
import model.LeadDAO;
import model.LeadDAOJDBCTemplateImpl;

public class TestJdbctemplate {

	public static void main(String[] args) {
		 //Get the Spring Context
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
         
        //Get the LeadDAO Bean
        LeadDAO leadDAO = ctx.getBean("LeadDAO", LeadDAOJDBCTemplateImpl.class);
         
        //Run some tests for JDBC CRUD operations
        Lead lead = new Lead();
       
        lead.setName("Jenny");
        lead.setInfo("developer");
         
        //Create
        leadDAO.save(lead);
         
        //Read
        Lead lead1 = leadDAO.getById(4);
        System.out.println("Lead Retrieved::"+lead1);
         
        //Update
        lead.setInfo("CEO");
        leadDAO.update(lead);
         
        //Get All
        List<Lead> leadList = leadDAO.getAll();
        System.out.println(leadList);
         
        //Delete
        leadDAO.deleteById(1);
         
        //Close Spring Context
        ctx.close();
         
        System.out.println("DONE");

	}

}
