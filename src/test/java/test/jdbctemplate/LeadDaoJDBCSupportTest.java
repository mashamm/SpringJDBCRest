package test.jdbctemplate;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import dao.leadDao;
import exception.InsertException;
import exception.LeadNotFoundException;
import model.lead;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-appcontext.xml")
@Transactional
public class LeadDaoJDBCSupportTest {
	
	 //private Logger logger = Logger.getLogger(LeadDaoJDBCSupportTest.class);
	 
	@Autowired
	private leadDao testDao ;
		@Test
	    public void daoTest() {
			 long id;
			 try {	
			 id = testDao.create("Test", "infoTest");
			 System.out.println("Id fromdao="+id);
			// logger.info("created test person with id="+id);
			 lead test = new lead();
			 test.setId(id);
			 test.setInfo("infoTest");
			 test.setName("Test");
			
			 long id2=testDao.create("Test2", "infoTest2");
			 lead test2 = new lead();
			 test2.setId(id2);
			 test2.setInfo("infoTest2");
			 test2.setName("Test2");
			
			 assertEquals(test,testDao.get(id));
			 assertEquals(test2,testDao.get(id2));
	
			 testDao.update(id, "testTest", "Infoinfo");
			 testDao.update(id2, "twoTest","2Info");

			 assertEquals("testTest",testDao.get(id).getName());
			 assertEquals("Infoinfo",testDao.get(id).getInfo());
			    
			 assertEquals("twoTest",testDao.get(id2).getName());
			 assertEquals("2Info",testDao.get(id2).getInfo());
			    
			} catch (InsertException e) {
				e.printStackTrace();}
    }
		@Test(expected=LeadNotFoundException.class)
		public void deleteLeadTest() {
			try {
				long  id = testDao.create("Testdelete", "infoTest");
				 testDao.delete(id);
				 testDao.get(id);
			} catch (InsertException e) {
				e.printStackTrace();
			}			
		}
		@Test(expected=LeadNotFoundException.class)
		public void updateTest(){
			try {
				long  id = testDao.create("Testupdate", "infoTest");
				 testDao.delete(id);
				 testDao.update(id,"T","t");
			} catch (InsertException e) {
				e.printStackTrace();
			}
		}
}
