package es.cic.curso.ejerc007.coche;

import es.cic.curso.ejerc007.model.Coche;
import es.cic.curso.ejerc007.service.CocheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CocheControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CocheService cocheService;

    @Autowired
    private ObjectMapper objectMapper;

    private Coche coche;

    @BeforeEach
    public void setup() {
        coche = new Coche();
        coche.setMarca("Toyota");
        coche.setModelo("Corolla");
        coche.setAño(2020);
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testGetAllCoches() throws Exception {
        mockMvc.perform(get("/coches"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testGetCocheById() throws Exception {
        Coche savedCoche = cocheService.save(coche);
        mockMvc.perform(get("/coches/{id}", savedCoche.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.marca").value("Toyota"))
                .andExpect(jsonPath("$.modelo").value("Corolla"))
                .andExpect(jsonPath("$.año").value(2020));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testCreateCoche() throws Exception {
        mockMvc.perform(post("/coches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coche)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Toyota"))
                .andExpect(jsonPath("$.modelo").value("Corolla"))
                .andExpect(jsonPath("$.año").value(2020));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testUpdateCoche() throws Exception {
        Coche savedCoche = cocheService.save(coche);
        savedCoche.setModelo("Camry");

        mockMvc.perform(put("/coches/{id}", savedCoche.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedCoche)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Camry"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testDeleteCoche() throws Exception {
        Coche savedCoche = cocheService.save(coche);

        mockMvc.perform(delete("/coches/{id}", savedCoche.getId()))
                .andExpect(status().isNoContent());
    }
}