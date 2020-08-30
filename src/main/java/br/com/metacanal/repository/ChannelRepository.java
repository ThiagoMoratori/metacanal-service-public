package br.com.metacanal.repository;

import br.com.metacanal.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Channel findByName(String name);
    Optional<Channel> findById(Long id);
}
