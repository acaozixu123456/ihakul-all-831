package xiaoai.test.bigData;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xiaoai.bigData.service.IXiaoiDataForMongoDbService;
import com.xiaoai.entity.XiaoiDateForMongDb;
import com.xiaoai.util.Page;

public class TestMongoDb_count {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		IXiaoiDataForMongoDbService bean = context.getBean(IXiaoiDataForMongoDbService.class);
		
		long count = bean.getCount();
		
		System.out.println(count);
		
	}
	
	@Test
	public void test(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		IXiaoiDataForMongoDbService bean = context.getBean(IXiaoiDataForMongoDbService.class);

		Page page = new Page();
		page.setPageNow(5);
		page.setShowPage(10);
		List findByPage = bean.findByPage(page);
		System.out.println(findByPage);
	}
	
	@Test
	public void test2() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		IXiaoiDataForMongoDbService bean = context.getBean(IXiaoiDataForMongoDbService.class);

		List<XiaoiDateForMongDb> findAll = bean.findAll();
		
		System.out.println(findAll);
	}
}
