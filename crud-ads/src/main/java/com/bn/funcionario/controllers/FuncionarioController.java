package com.bn.funcionario.controllers;

import com.bn.funcionario.models.FuncionarioModel;
import com.bn.funcionario.services.FuncionarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioServices funcionarioServices;

    // GET /api/funcionarios -> 200 OK (público)
    @GetMapping
    public ResponseEntity<List<FuncionarioModel>> listar() {
        return ResponseEntity.ok(funcionarioServices.findAll());
    }

    // GET /api/funcionarios/{id} -> 200 OK ou 404 Not Found (público)
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioModel> buscarPorId(@PathVariable Long id) {
        return funcionarioServices.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/funcionarios -> 201 Created (público)
    @PostMapping
    public ResponseEntity<FuncionarioModel> criar(@RequestBody FuncionarioModel funcionarioModel) {
        FuncionarioModel criado = funcionarioServices.criarFuncionario(funcionarioModel);
        return ResponseEntity
                .created(URI.create("/api/funcionarios/" + criado.getId()))
                .body(criado);
    }

    // PUT /api/funcionarios/{id} -> 200 OK ou 404 Not Found (exige autenticação)
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioModel> atualizar(@PathVariable Long id,
                                                      @RequestBody FuncionarioModel funcionarioModel) {
        return funcionarioServices.atualizar(id, funcionarioModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/funcionarios/{id} -> 204 No Content ou 404 Not Found (exige autenticação)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return funcionarioServices.deletar(id)
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.notFound().build();
    }
}
