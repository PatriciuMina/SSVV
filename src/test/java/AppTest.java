import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.example.domain.Student;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.ValidationException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppTest
{

    private final static String filenameStudent = "test_studenti.xml";
    private final static String filenameTema = "test_teme.xml";
    private final static String filenameNota = "test_note.xml";
    private static Service service;

    @BeforeEach
    public void setup()
    {
        try {
            File myObj = new File(filenameStudent);

            if (!myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(filenameStudent);
                myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<inbox>\n</inbox>");
                myWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);

        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);

        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void tc_1_addStudent_Valid_Name()
    {
        Student student = new Student("1","name",1,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void tc_2_addStudent_Invalid_NullName()
    {
        Student student = new Student("1",null,1,"random@gmail.com");
        assertThrowsExactly(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void tc_3_addStudent_Invalid_EmptyName()
    {
        Student student = new Student("1","",1,"random@gmail.com");
        assertThrowsExactly(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void tc_4_addStudent_Valid_Email()
    {
        Student student = new Student("1","name",1,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void tc_5_addStudent_Invalid_NullEmail()
    {
        Student student = new Student("1","name",1,null);
        assertThrowsExactly(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void tc_6_addStudent_Invalid_EmptyEmail()
    {
        Student student = new Student("1","name",1,"");
        assertThrowsExactly(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void tc_7_addStudent_Valid_Id()
    {
        Student student = new Student("1","name",1,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void tc_8_addStudent_Invalid_NullId()
    {
        Student student = new Student(null,"name",1,"random@gmail.com");
        assertThrowsExactly(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void tc_9_addStudent_Invalid_EmptyId()
    {
        Student student = new Student("","name",1,"random@gmail.com");
        assertThrowsExactly(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void tc_10_addStudent_Valid_GroupLowerEdge()
    {
        Student student = new Student("1","name",1,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void tc_11_addStudent_Valid_GroupLowerBound()
    {
        Student student = new Student("1","name",2,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void tc_12_addStudent_Valid_GroupHigherBound()
    {
        Student student = new Student("1","name",Integer.MAX_VALUE - 1,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void tc_13_addStudent_Valid_GroupHigherEdge()
    {
        Student student = new Student("1","name", Integer.MAX_VALUE,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void tc_14_addStudent_Invalid_GroupOutsideLowerEdge()
    {
        Student student = new Student("1","name",-1,"random@gmail.com");
        assertThrowsExactly(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }

    @Test
    public void tc_15_addStudent_Invalid_GroupOutsideHigherEdge()
    {
        Student student = new Student("1","name",Integer.MAX_VALUE + 1,"random@gmail.com");
        assertThrowsExactly(ValidationException.class, () -> {
            service.addStudent(student);
        });
    }
}
