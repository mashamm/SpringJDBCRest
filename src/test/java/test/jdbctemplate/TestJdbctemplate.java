package test.jdbctemplate;

import java.io.IOException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.LeadDAO;
import dao.LeadDaoJdbcSupport;
import model.Lead;
import model.LeadNotFoundException;


public class TestJdbctemplate {

	public static void main(String[] args) {
		
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
         
        LeadDAO leadDAO = ctx.getBean("LeadDAO",LeadDaoJdbcSupport.class);

        leadDAO.create("Den3","info");
        Lead lead1 = leadDAO.get(8);
        System.out.println("Lead Retrieved::"+lead1);
        try {
			leadDAO.update(12,"Victoria","Manager2");
			
		} catch (LeadNotFoundException  e) {
			System.out.println("from update");
			 e.printStackTrace();
		}
       
        try {
			leadDAO.delete(-1);
		} catch (LeadNotFoundException e) {
			
			e.printStackTrace();
		}
        List<Lead> leadList = leadDAO.getAll();
        System.out.println(leadList);
        ctx.close();
         
        System.out.println("DONE");

	}

}
