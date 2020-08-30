package br.com.metacanal.service;

import br.com.metacanal.entity.Channel;
import br.com.metacanal.entity.Video;
import br.com.metacanal.model.Category;
import br.com.metacanal.repository.ChannelRepository;
import br.com.metacanal.repository.VideoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final ChannelRepository channelRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository, ChannelRepository channelRepository) {
        this.videoRepository = videoRepository;
        this.channelRepository = channelRepository;
    }

    public Video create(MultipartFile multipartFile, Long channelId) throws IOException {

        Channel channel = channelRepository.getOne(channelId);

        if (Objects.nonNull(channel)) {
            Video video = new Video();
            video.setTitle(multipartFile.getOriginalFilename());
            video.setPayload(IOUtils.toByteArray(multipartFile.getInputStream()));
            video.setChannel(channel);
            video.setPublic(true);
            video.setCategories(Collections.singletonList(Category.GENERAL));

            video = videoRepository.save(video);
            channel.getVideos().add(video);
            channelRepository.save(channel);

            return video;
        } else {
            throw new EntityNotFoundException(String.format("Channel %s was not found", channelId));
        }
    }

    public List<Video> getVideos() {
        return videoRepository.findAll();
    }

    public Video getById(Long id) {

        Optional<Video> optionalVideo = videoRepository.findById(id);

        Video video;
        if (optionalVideo.isPresent()) {
            return video = optionalVideo.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Optional<Video> getOptionalById(Long id) {
        return videoRepository.findById(id);
    }

    public void deleteVideo(Long id) {
        Optional<Video> videoToBeDeleted = getOptionalById(id);
        if (videoToBeDeleted.isPresent()) {
            videoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Video updateTitle(Map<String, String> updates, Long id) {
        Optional<Video> videoToBePatched = getOptionalById(id);
        if (videoToBePatched.isPresent()) {
            Video video = videoToBePatched.get();
            String newTitle = updates.get("title");
            video.setTitle(newTitle);
            return videoRepository.save(video);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
