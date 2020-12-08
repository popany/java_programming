// https://www.tutorialspoint.com/spring/spring_transaction_management.htm
// https://www.tutorialspoint.com/spring/declarative_management.htm

package org.example;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppDeclarativeTransactionManagement 
{
    public static void main( String[] args )
    {
        try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {
            StudentDAO studentJDBCTemplate = (StudentDAO)context.getBean("studentJDBCTemplate");
      
            System.out.println("------Records creation--------" );
            try {
                studentJDBCTemplate.create("Zara", 11, 99, 2010);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                studentJDBCTemplate.create("Nuha", 20, 97, 2010);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            try {
                studentJDBCTemplate.create("Ayan", 25, 100, 2011);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("------Listing all the records--------" );
            List<StudentMarks> studentMarks = studentJDBCTemplate.listStudents();
            
            for (StudentMarks record : studentMarks) {
                System.out.print("ID : " + record.getId() );
                System.out.print(", Name : " + record.getName() );
                System.out.print(", Marks : " + record.getMarks());
                System.out.print(", Year : " + record.getYear());
                System.out.println(", Age : " + record.getAge());
            }
        }
    }
}
