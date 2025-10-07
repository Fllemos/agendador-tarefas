package com.javanauta.agendadortarefas.infrastructure.security;



import com.javanauta.agendadortarefas.business.dto.UsuarioDTO;
import com.javanauta.agendadortarefas.infrastructure.security.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
public class UserDetailsServiceImpl{

// criamos esse  UsuarioClient  é por ele que vamos receber o toke do Usuario logado
// vamos criar um novo     loadUserByUsername e nao vamos + implements UserDetailsService pq ele so aceita um String
// depois vai no local onde o metodo (loadUserByUsername) era chamado = JwtRequestFilter  e troca pelo   carregaDadosUsuario

    @Autowired
    private UsuarioClient usuarioClient;


//  Implementação do método para carregar detalhes do usuário pelo e-mail
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        // Busca o usuário no banco de dados pelo e-mail
//        Usuario usuario = usuarioRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
//
//        // Cria e retorna um objeto UserDetails com base no usuário encontrado
//        return org.springframework.security.core.userdetails.User
//                .withUsername(usuario.getEmail()) // Define o nome de usuário como o e-mail
//                .password(usuario.getSenha()) // Define a senha do usuário
//                .build(); // Constrói o objeto UserDetails
//    }


    public UserDetails carregaDadosUsuario(String email, String token){

        UsuarioDTO usuarioDTO = usuarioClient.buscaUsuarioPorEmail(email, token);
        return User
                .withUsername(usuarioDTO.getEmail())
                .password(usuarioDTO.getSenha())
                .build();

    }








}
