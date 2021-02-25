package com.as.myfinances.controller;

import com.as.myfinances.exception.ErrorAutenticationException;
import com.as.myfinances.exception.RegraNegocioException;
import com.as.myfinances.model.dto.UsuarioDTO;
import com.as.myfinances.model.entity.Usuario;
import com.as.myfinances.service.LancamentoService;
import com.as.myfinances.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc //anotacao que da acesso ao objeto MockMvc
public class UsarioControllerTest {


     private static final String API = "/api/usuarios";
     private static MediaType JSON = MediaType.APPLICATION_JSON;

     @Autowired
     private MockMvc mvc;

     @MockBean
     private UsuarioService usuarioService;

     @MockBean
     private LancamentoService lancamentoService;


     @Test
     public void deveAutenticarUmUsuario() throws Exception {
     //cenario

         String email = "usuario@email.com";
         String senha = "senha";

         //1. criando dto
         UsuarioDTO dto = UsuarioDTO.builder()
                 .email(email)
                 .senha(senha)
                 .build();
         //2. cirando usuario
         Usuario usuario = Usuario.builder()
                 .id(1l)
                 .email(email)
                 .senha(senha)
                 .build();

         //3. Mockando o service.
         Mockito.when(usuarioService.autenticar(email, senha)).thenReturn(usuario);

         //5. Conertendo dto em JSON
         String json = new ObjectMapper().writeValueAsString(dto);

     //acao execucao

         //6. simulando requisicao
         MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
                                                         .post(API.concat("/autenticar"))
                                                         .accept(JSON)
                                                         .contentType(JSON)
                                                         .content(json);

        //7. executando a requiscao montada no passo 6
         mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome())  )
            .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()))
;
     }

    @Test
    public void deveRetonarBadRequestAoObterErroDeAutenticacao() throws Exception {
        //cenario

        String email = "usuario@email.com";
        String senha = "senha";

        //1. criando dto
        UsuarioDTO dto = UsuarioDTO.builder()
                .email(email)
                .senha(senha)
                .build();

        //3. Mockando o service.
        Mockito.when(usuarioService.autenticar(email, senha)).thenThrow(ErrorAutenticationException.class);

        //5. Conertendo dto em JSON
        String json = new ObjectMapper().writeValueAsString(dto);

        //acao execucao

        //6. simulando requisicao
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
                .post(API.concat("/autenticar"))
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        //7. executando a requiscao montada no passo 6
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveSalvarUmUsuario() throws Exception {
        //cenario

        String email = "usuario@email.com";
        String senha = "senha";

        //1. criando dto
        UsuarioDTO dto = UsuarioDTO.builder()
                .email(email)
                .senha(senha)
                .build();
        //2. cirando usuario
        Usuario usuario = Usuario.builder()
                .id(1l)
                .email(email)
                .senha(senha)
                .build();

        //3. Mockando o service.
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(RegraNegocioException.class);

        //5. Conertendo dto em JSON
        String json = new ObjectMapper().writeValueAsString(dto);

        //acao execucao

        //6. simulando requisicao
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
                .post(API)
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        //7. executando a requiscao montada no passo 6
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome())  )
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()))
        ;
    }

    @Test
    public void deveRetornarBadRequestAoTentarSalvarUmUsuarioInvalido() throws Exception {
        //cenario

        String email = "usuario@email.com";
        String senha = "senha";

        //1. criando dto
        UsuarioDTO dto = UsuarioDTO.builder()
                .email(email)
                .senha(senha)
                .build();

        //3. Mockando o service.
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(RegraNegocioException.class);

        //5. Conertendo dto em JSON
        String json = new ObjectMapper().writeValueAsString(dto);

        //acao execucao

        //6. simulando requisicao
        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
                .post(API)
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        //7. executando a requiscao montada no passo 6
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


}
