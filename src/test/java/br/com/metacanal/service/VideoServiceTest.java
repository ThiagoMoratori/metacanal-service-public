package br.com.metacanal.service;

import br.com.metacanal.entity.Channel;
import br.com.metacanal.entity.Video;
import br.com.metacanal.repository.ChannelRepository;
import br.com.metacanal.repository.VideoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VideoServiceTest {

    public static final long ID_TO_DELETE = 1L;
    VideoService videoService;

    @Mock
    VideoRepository videoRepository;

    @Mock
    ChannelRepository channelRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        videoService = new VideoService(videoRepository, channelRepository);
    }

    @Test
    void create() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("fileThatDoesNotExists.txt",
                "fileThatDoesNotExists.txt",
                "text/plain",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        Channel returnedChannel = new Channel();
        returnedChannel.setId(1L);

        when(channelRepository.getOne(1L)).thenReturn(returnedChannel);

        videoService.create(multipartFile, returnedChannel.getId());
        verify(videoRepository, times(1)).save(any());
    }

    @Test
    void getVideos() {
        videoService.getVideos();
        verify(videoRepository, times(1)).findAll();
    }

    @Test
    void getById() {
        Video video = new Video();
        video.setId(1L);
        Optional<Video> videoOptional = Optional.of(video);

        when(videoRepository.findById(anyLong())).thenReturn(videoOptional);

        Video videoReturned = videoService.getById(1L);

        assertNotNull(videoReturned);
        verify(videoRepository, times(1)).findById(anyLong());
    }

    @Test
    void getOptionalById() {
        Video video = new Video();
        video.setId(1L);
        Optional<Video> videoOptional = Optional.of(video);

        when(videoRepository.findById(anyLong())).thenReturn(videoOptional);

        Optional<Video> videoReturned = videoService.getOptionalById(1L);

        assertTrue(videoReturned.isPresent());
        verify(videoRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteVideo() {
        Video video = new Video();
        video.setId(ID_TO_DELETE);
        Optional<Video> videoOptional = Optional.of(video);

        when(videoRepository.findById(anyLong())).thenReturn(videoOptional);

        videoService.deleteVideo(ID_TO_DELETE);

        verify(videoRepository, times(1)).deleteById(anyLong());
    }
}