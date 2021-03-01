package com.as.myfinances.repository;

import com.as.myfinances.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;

    //1.cenario
    @Test
    public void deveVerificarExistenciaDeEmail(){
       //1. Cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //2. acao /exeucao
        boolean existe = usuarioRepository.existsByEmail("usuario@email.com");

        //verificacao
        Assertions.assertThat(existe).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail(){
        // cenario

        //acao/execucao
        boolean existe = usuarioRepository.existsByEmail("usuario@email.com");

        //verificacao
        Assertions.assertThat(existe).isFalse();
    }

    @Test
    public void deveSalvarUsuarioNaBaseDeDados(){
        //cenario
        Usuario usuario = criarUsuario();
        //acao
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        //verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();

    }

    @Test
    public void deveRetonarUmUusarioPorEmail(){
        //cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //acao
        Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");

        //verificacao
        Assertions.assertThat(result.isPresent()).isTrue();

    }

    @Test
    public void deveRetonarVazioQuandoOUsuarioNaoEstiverNaBaseDeDados(){
        // cenario

        //acao/execucao
        Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");

        //verificacao
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    public static Usuario criarUsuario(){
         return  Usuario.builder()
                 .nome("usuario")
                 .email("usuario@email.com")
                 .senha("senha")
                 .build();
    }

}
