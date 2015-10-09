package test.jdbctemplate;

import java.io.IOException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.LeadDAO;
import dao.LeadDaoJdbcSupport;
import model.Lead;


public class TestJdbctemplate {

	public static void main(String[] args) {
		 //Get the Spring Context
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
         
        //Get the LeadDAO Bean
        LeadDAO leadDAO = ctx.getBean("LeadDAO",LeadDaoJdbcSupport.class);
         
        //Run some tests for JDBC CRUD operations
    
        
         
        //Create
        leadDAO.save("Masha","info");
         
        //Read
        Lead lead1 = leadDAO.getById(4);
        System.out.println("Lead Retrieved::"+lead1);
         
        //Update
        try {
			leadDAO.update(4,"Max","PM");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
         
        //Get All
        List<Lead> leadList = leadDAO.getAll();
        System.out.println(leadList);
         
        //Delete
        leadDAO.deleteById(-1);
         
        //Close Spring Context
        ctx.close();
         
        System.out.println("DONE");

	}

}
