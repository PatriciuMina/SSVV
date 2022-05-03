import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IntegrationTest {

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
            myObj = new File(filenameTema);
            if (!myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(filenameTema);
                myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<inbox>\n</inbox>");
                myWriter.close();
            }
            myObj = new File(filenameNota);
            if (!myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(filenameNota);
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

        service.addStudent(new Student("1","name",1,"random@gmail.com"));
        service.addTema(new Tema("1", "abcd", 4, 10));
    }

    @Test
    public void addStudentTest()
    {
        Student student = new Student("2","name",1,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void addAssignmentTest()
    {
        assertNotNull(service.addTema(new Tema("2", "abcd", 2, 3)));
    }

    @Test
    public void addGradeTest()
    {
        assertEquals(5.0, service.addNota(new Nota("1", "1", "1", 7.5, LocalDate.now()), "pass"));
    }

    @Test
    public void integration()
    {
        addStudentTest();
        addAssignmentTest();
        addGradeTest();
    }

    @Test
    public void addStudentTestInt()
    {
        Student student = new Student("2","name",1,"random@gmail.com");
        assertEquals(student, service.addStudent(student));
    }

    @Test
    public void addAssignmentTestInt()
    {
        service.addStudent(new Student("2","name",1,"random@gmail.com"));
        assertNotNull(service.addTema(new Tema("2", "abcd", 2, 3)));
    }

    @Test
    public void addGradeTestInt()
    {
        service.addStudent(new Student("2","name",1,"random@gmail.com"));
        service.addTema(new Tema("2", "abcd", 4, 10));
        assertEquals(5.0, service.addNota(new Nota("2", "2", "1", 7.5, LocalDate.now()), "pass"));
    }
}
