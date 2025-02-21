package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent()){
            throw  new NegocioException(
                    String.format("Já existe usuário com o email %s",
                            usuario.getEmail())
            );
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    public void associarGrupo(Long usuarioId, Long grupoId){

        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        usuario.associarGrupo(grupo);
    }

    public void desassociarGrupo(Long usuarioId, Long grupoId){

        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        usuario.deassociarGrupo(grupo);
    }
}