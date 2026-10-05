package br.senac.sp.tads.dsw.exemplo5.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.senac.sp.tads.dsw.exemplo5.model.Departamento;
import br.senac.sp.tads.dsw.exemplo5.repository.DepartamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional 
public class DepartamentoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartamentoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarDepartamentoComSucesso() throws Exception {
        // 1 - Criaro objeto que queremos enviar
        Departamento departamento = new Departamento();
        departamento.setNome("Tecnologia da Informação");
        departamento.setOrcamento(150000.00);

        // Converter o objeto JAVA em para uma String (texto JSON)
        String jsonRequisicao = objectMapper.writeValueAsString(departamento);

        // 2 - Enviar o POST para a url da API
        mockMvc.perform(post("/api/departamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequisicao))
        // 3 - Verificar se a resposta é o esperado        
                .andExpect(status().isCreated()) // HTTP 201
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Tecnologia da Informação"));
    }
}
