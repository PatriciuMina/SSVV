import org.example.domain.Tema;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentTest {

    private final static String filenameStudent = "test_studenti.xml";
    private final static String filenameTema = "test_teme.xml";
    private final static String filenameNota = "test_note.xml";
    private static Service service;

    @BeforeEach
    public void setup()
    {
        try {
            File myObj = new File(filenameTema);

            if (!myObj.createNewFile()) {
                FileWriter myWriter = new FileWriter(filenameTema);
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

    // invalid deadline
    @Test
    public void tc_1()
    {
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("1", "1", -15, 1));
        });
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("1", "1", 15, 1));
        });
    }

    // invalid primire
    @Test
    public void tc_2()
    {
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("1", "1", 1, 15));
        });
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("1", "1", 1, -15));
        });
    }

    // invalid description
    @Test
    public void tc_3()
    {
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("1", "", 2, 3));
        });
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("1", null, 2, 3));
        });
    }

    // invalid id
    @Test
    public void tc_4()
    {
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("", "abcd", 2, 3));
        });
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema(null, "abcd", 2, 3));
        });
    }

    // valid assignment
    @Test
    public void tc_5()
    {
        assertNotNull(service.addTema(new Tema("1", "abcd", 2, 3)));
    }

    // valid assignment, already existing
    @Test
    public void tc_6()
    {
        service.addTema(new Tema("1", "abcd", 2, 3));
        assertNotNull(service.addTema(new Tema("1", "abcd", 2, 3)));
    }
}
