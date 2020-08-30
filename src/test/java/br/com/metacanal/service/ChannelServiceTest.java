package br.com.metacanal.service;

import br.com.metacanal.entity.Channel;
import br.com.metacanal.repository.ChannelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ChannelServiceTest {

    ChannelService channelService;

    @Mock
    ChannelRepository channelRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        channelService = new ChannelService(channelRepository);
    }

    @Test
    void create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //TODO: change me
        String channel = "{" +
                "\"name\": \"a name\"," +
                "\"description\": \"a description\"" +
                "}";

        channelService.create(objectMapper.readTree(channel));
        verify(channelRepository, times(1)).save(any());
    }

    @Test
    void getChannels() {
        channelService.getChannels();
        verify(channelRepository, times(1)).findAll();
    }

    @Test
    void getByName() {
        channelService.getByName("a name");
        verify(channelRepository, times(1)).findByName(anyString());
    }

    @Test
    void getById() {
        Channel channel = new Channel();
        channel.setId(1L);
        Optional<Channel> channelOptional = Optional.of(channel);

        when(channelRepository.findById(anyLong())).thenReturn(channelOptional);

        Optional<Channel> channelReturned = channelService.getById(1L);

        assertTrue(channelReturned.isPresent());
        verify(channelRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteChannel() {
        Channel channel = new Channel();
        channel.setId(2L);
        Optional<Channel> channelOptional = Optional.of(channel);

        when(channelRepository.findById(anyLong())).thenReturn(channelOptional);

        Long idToDelete = 2L;
        channelService.deleteChannel(idToDelete);
        verify(channelRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void updateName() {
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("name", "caneta azul");

        Channel channel = new Channel();
        channel.setId(1L);
        Optional<Channel> channelOptional = Optional.of(channel);

        when(channelRepository.findById(anyLong())).thenReturn(channelOptional);

        channelService.updateName(nameMap, 1L);
        verify(channelRepository, times(1)).findById(anyLong());
        verify(channelRepository, times(1)).save(any());
    }
}