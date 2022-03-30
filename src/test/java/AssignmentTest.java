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

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class AssignmentTest {

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
    @Test
    public void tc_1()
    {
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("1", "1", 15, 1));
        });
    }

    @Test
    public void tc_2()
    {
        assertThrowsExactly(ValidationException.class, () -> {
            service.addTema(new Tema("1", "1", 1, 15));
        });
    }

}
