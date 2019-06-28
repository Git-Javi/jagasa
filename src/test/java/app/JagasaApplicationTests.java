package app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import app.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class) //notice this line

public class JagasaApplicationTests {

	@Test
	public void contextLoads() {
	}

}
