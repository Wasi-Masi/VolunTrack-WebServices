package com.example.demo.Interfaces.REST.Controllers;

import com.example.demo.Domain.Model.Aggregates.Member;
import com.example.demo.Infrastructure.Repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/members", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Endpoint para crear un nuevo miembro.
     * @param member Los datos del miembro a crear (recibidos en el cuerpo de la solicitud JSON).
     * @return El miembro creado con su ID generado y un estado HTTP 201 Created.
     */
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {

        Member savedMember = memberRepository.save(member);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener todos los miembros.
     * @return Una lista de todos los miembros existentes y un estado HTTP 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {

        List<Member> members = memberRepository.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un miembro por su ID.
     * @param id El ID del miembro a buscar (parte de la URL).
     * @return El miembro encontrado y un estado HTTP 200 OK, o 404 Not Found si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para actualizar un miembro existente.
     * @param id El ID del miembro a actualizar.
     * @param memberDetails Los nuevos datos del miembro.
     * @return El miembro actualizado y un estado HTTP 200 OK, o 404 Not Found si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member existingMember = memberOptional.get();
            existingMember.setName(memberDetails.getName());
            Member updatedMember = memberRepository.save(existingMember);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un miembro.
     * @param id El ID del miembro a eliminar.
     * @return Un estado HTTP 204 No Content si la eliminación fue exitosa, o 404 Not Found.
     */
    @DeleteMapping("/{id}") // Mapea las solicitudes DELETE a /api/v1/members/{id}
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}