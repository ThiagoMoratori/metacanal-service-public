package br.com.metacanal.service;

import br.com.metacanal.entity.Channel;
import br.com.metacanal.entity.User;
import br.com.metacanal.model.RecommendationSystem;
import br.com.metacanal.repository.ChannelRepository;
import br.com.metacanal.repository.UserRepository;
import br.com.metacanal.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicDatabaseInitializer {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    User user1;
    Channel channel1;

    @Autowired
    public BasicDatabaseInitializer(
            ChannelRepository channelRepository,
            UserRepository userRepository

    ) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void buildData() {

        channel1 = new Channel(
                1L,
                "I am the first channel",
                "First Channel",
                new ArrayList<>(),
                new ArrayList<>()
        );

        user1 = new User(
                1L,
                "Thiago",
                "thiago@metacanal.com.br",
                RecommendationSystem.ALL_VIDEOS,
                channel1,
                new ArrayList<>()
        );

        channelRepository.save(channel1);
        userRepository.save(user1);
    }

}
