package cl.kemolinaj.ms.laboratorio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsLaboratorioApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void applicationStartsWithoutErrors() {
        Assertions.assertDoesNotThrow(() -> MsLaboratorioApplication.main(new String[]{}));
    }

}
