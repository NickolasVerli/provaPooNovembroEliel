package com.bn.funcionario.services;

import com.bn.funcionario.models.FuncionarioModel;
import com.bn.funcionario.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServices {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<FuncionarioModel> findAll() {
        return funcionarioRepository.findAll();
    }

    public Optional<FuncionarioModel> buscarPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    public FuncionarioModel criarFuncionario(FuncionarioModel funcionarioModel) {
        funcionarioModel.setId(null); // garante que o POST sempre cria um novo registro
        return funcionarioRepository.save(funcionarioModel);
    }

    // deletar: retorna false se o funcionário não existir
    public boolean deletar(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            return false;
        }
        funcionarioRepository.deleteById(id);
        return true;
    }

    // atualizar: retorna vazio se o funcionário não existir
    public Optional<FuncionarioModel> atualizar(Long id, FuncionarioModel funcionarioModel) {
        return funcionarioRepository.findById(id).map(func -> {
            func.setNome(funcionarioModel.getNome());
            func.setCpf(funcionarioModel.getCpf());
            func.setRua(funcionarioModel.getRua());
            return funcionarioRepository.save(func);
        });
    }
}
