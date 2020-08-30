package br.com.metacanal.service;

import br.com.metacanal.entity.Channel;
import br.com.metacanal.repository.ChannelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel create(JsonNode channel) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return channelRepository.save(objectMapper.treeToValue(channel, Channel.class));
    }

    public List<Channel> getChannels() {
        return channelRepository.findAll();
    }

    public Channel getByName(String name) {
        return channelRepository.findByName(name);
    }

    public Optional<Channel> getById(Long id) {
        return channelRepository.findById(id);
    }

    public void deleteChannel(Long id) {
        Optional<Channel> channelToBeDeleted = getById(id);
        if (channelToBeDeleted.isPresent()) {
            channelRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Channel updateName(Map<String, String> updates, Long id) {
        Optional<Channel> channelToBePatched = getById(id);
        if (channelToBePatched.isPresent()) {
            Channel channel = channelToBePatched.get();
            String newName = updates.get("name");
            channel.setName(newName);
            return channelRepository.save(channel);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
