package com.as.myfinances.service;

import com.as.myfinances.controller.exception.ErrorAutenticationException;
import com.as.myfinances.controller.exception.RegraNegocioException;
import com.as.myfinances.model.entity.Usuario;
import com.as.myfinances.repository.UsuarioRepository;
import com.as.myfinances.service.impl.UsuarioServiceImpl;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;



import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    private UsuarioServiceImpl usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setup(){
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }

    @Test
    public void deveSalvarUmUsuario(){
        Throwable exception = Assertions.catchThrowable(() -> {
           //cenario
           Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
           Usuario usuario = Usuario.builder()
                   .id(1L)
                   .nome("nome")
                   .email("email@email.com")
                   .senha("senha")
                   .build();

           Mockito.when( usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

           //acao
            Usuario usuarioSalvo =  usuarioService.salvarUsuario(new Usuario());

           //verificacao
            Assertions.assertThat(usuarioSalvo).isNotNull();
            Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
            Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
            Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
            Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");

        });
        Assertions.assertThat(exception).doesNotThrowAnyException();
    }

    @Test
    public void naoDeveSalvarUsuarioComEmailCadastrado(){
        Throwable exception = Assertions.catchThrowable(() -> {
            //Cenario
            Usuario usuario = Usuario.builder().email("email@email.com").build();
            String email = "email@email.com";
            Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(email);

            //acao
            usuarioService.salvarUsuario(usuario);

            //verficacao
            Mockito.verify(usuarioRepository, Mockito.never()).save(usuario);
        });
            Assertions.assertThat(exception).isInstanceOf(RegraNegocioException.class);

    }

    @Test
    public void usuarioDeveAutenticarComSucesso(){
        //funcao lambda para nao retornar acessao
        Throwable excepton = Assertions.catchThrowable(() -> {
            //cenario
            String email = "usuario@email.com";
            String senha = "senha";
            //Criar usuario
            Usuario usuario = Usuario.builder()
                    .email(email)
                    .senha(senha)
                    .id(1L)
                    .build();
            //Buscar Usuario (por email)
            Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

            // acao/execucao
            Usuario result =  usuarioService.autenticar(email, senha);

            //verificacao
            org.assertj.core.api.Assertions.assertThat(result.getId()).isNotNull();
        });
        Assertions.assertThat(excepton).doesNotThrowAnyException();
    }

    @Test
    public void deveLancarErroAoInformarASenhaIncorreta() {
        //funcao lambda para retonar acessao
       Throwable exception =  Assertions.catchThrowable(() -> {
            //cenario
            String senha = "senha";
            Usuario usuario = Usuario.builder()
                    .email("email@email.com")
                    .senha(senha)
                    .build();
            Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
            //acao
            usuarioService.autenticar("email@email.com", "1234");
            //verificacao
        });
        Assertions.assertThat(exception).isInstanceOf(ErrorAutenticationException.class).hasMessage("Senha inválida!");
    }

    @Test
    public void develancarErroQuandoNaoEncontrarUsuarioCadastradoComEmailPreenchido(){
        //funcao lambda para retonar acessao
        Throwable exception = Assertions.catchThrowable(() -> {
            //cenario
            Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

            //acao
            usuarioService.autenticar("email@email.com", "1234");
        });
        Assertions.assertThat(exception).isInstanceOf(ErrorAutenticationException.class);
    }

    @Test
    public void deveValidarEmail() {
        Throwable exception = Assertions.catchThrowable(() -> {
            //cenario
            Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
            //acao/execucao
            usuarioService.validarEmail("usuario@email.com");
        });
        Assertions.assertThat(exception).doesNotThrowAnyException();
    }

    @Test
    public void deveLancarErrorAoValidarEmailQuandoExistirEmailCadastrado (){
        Throwable exception = Assertions.catchThrowable(() -> {
            //cenario
            Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
            //acao/execucao
            usuarioService.validarEmail("usuario@email.com");
        });
        Assertions.assertThat(exception).isInstanceOf(RegraNegocioException.class);
    }
}
