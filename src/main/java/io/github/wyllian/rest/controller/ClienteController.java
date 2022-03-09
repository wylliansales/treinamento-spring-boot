package io.github.wyllian.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.wyllian.domain.entity.Cliente;
import io.github.wyllian.domain.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}")
    @Deprecated
    public Cliente getClienteById(@PathVariable Integer id) {
        return this.clienteRepository
                    .findById(id)
                    .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "Cliente não encontrato")
                    );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Valid Cliente clienteBody) {
        return this.clienteRepository.save(clienteBody);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        this.clienteRepository.findById(id)
                              .map(cliente -> {
                                this.clienteRepository.delete(cliente);
                                return cliente;
                              })
                              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, 
                                 @RequestBody @Valid Cliente cliente) {
        this.clienteRepository
                        .findById(id)
                        .map( c -> {
                           cliente.setId(c.getId());
                           this.clienteRepository.save(cliente);
                           return c;
                        }).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/buscar")
    public List<Cliente> find(@RequestBody Cliente filtro) {
        ExampleMatcher exampleMatcher = ExampleMatcher
                                            .matching()
                                            .withIgnoreCase()
                                            .withStringMatcher(StringMatcher.CONTAINING);
        
        Example<Cliente> exp = Example.of(filtro, exampleMatcher);
        return this.clienteRepository.findAll(exp);
    }
    
    // @RequestMapping(
    //     value = {"/hello/{nome}", "/hello"}, 
    //     method = RequestMethod.GET,
    //     consumes = {"application/json", "application/xml"},
    //     produces = {"application/json"}
    // )
    // @ResponseBody
    // public String helloClientes(@PathVariable("nome") String nomeCliente) {
    //     return String.format("Hello %s", nomeCliente);
    // }
}
