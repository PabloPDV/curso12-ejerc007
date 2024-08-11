package es.cic.curso.ejerc007.coche;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.ejerc007.model.Coche;
import es.cic.curso.ejerc007.repository.CocheRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CocheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CocheRepository cocheRepository;

    @BeforeEach
    public void setUp() {
        // Configurar el estado inicial de la base de datos
        Coche coche = new Coche();
        coche.setMarca("Toyota");
        coche.setModelo("Corolla");
        coche.setAño(2020);
        cocheRepository.save(coche);
    }

    @AfterEach
    public void tearDown() {
        // Limpiar el estado de la base de datos
        cocheRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetCochesWithUserRole() throws Exception {
        mockMvc.perform(get("/coches"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetCochesWithAdminRole() throws Exception {
        mockMvc.perform(get("/coches"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCochesWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/coches"))
                .andExpect(status().isUnauthorized());
    }
}