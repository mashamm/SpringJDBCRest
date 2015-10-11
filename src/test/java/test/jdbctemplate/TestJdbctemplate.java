package test.jdbctemplate;

import java.io.IOException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.leadDao;
import dao.InsertException;
import dao.LeadDaoJdbcSupport;
import model.lead;
import model.LeadNotFoundException;


public class TestJdbctemplate {

	public static void main(String[] args) {
		
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
         
        leadDao leadDao = ctx.getBean("leadDao",LeadDaoJdbcSupport.class);

        try {
			leadDao.create("Den3","info");
		} catch (InsertException e1) {
			e1.printStackTrace();
		}
        lead lead1 = leadDao.get(8);
        System.out.println("Lead Retrieved::"+lead1);
        try {
			leadDao.update(12,"Victoria","Manager2");
			
		} catch (LeadNotFoundException  e) {
			 e.printStackTrace();
		}
       
        try {
			leadDao.delete(-1);
		} catch (LeadNotFoundException e) {
			e.printStackTrace();
		}
        List<lead> leadList = leadDao.getAll();
        System.out.println(leadList);
        ctx.close();
         
        System.out.println("DONE");

	}

}
