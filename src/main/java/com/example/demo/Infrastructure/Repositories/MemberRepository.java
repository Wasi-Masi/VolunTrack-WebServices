package com.example.demo.Infrastructure.Repositories;

import com.example.demo.Domain.Model.Aggregates.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}