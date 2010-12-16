package org.springframework.data.mongodb.examples.hello;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello Mongo!
 */
public class App 
{
	public static void main( String[] args ) {
		System.out.println("Bootstrapping HelloMongo");

		ConfigurableApplicationContext context = null;
        context = new ClassPathXmlApplicationContext("META-INF/spring/bootstrap.xml");

        HelloMongo hello = context.getBean(HelloMongo.class);
        hello.run();
        
        System.out.println( "DONE!" );
	}
}
