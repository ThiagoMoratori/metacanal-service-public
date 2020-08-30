package br.com.metacanal.controller;

import br.com.metacanal.entity.Video;
import br.com.metacanal.service.VideoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
public class VideoController {

    private final VideoService videosService;

    @Autowired
    public VideoController(VideoService videosService) {
        this.videosService = videosService;
    }

    //TODO: only supports files of size up to 10MB, fix it
    @Transactional
    @ApiOperation(value = "This route will create a new Video")
    @PostMapping(path = "/video/channel/{channelId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> store(@RequestPart("file") MultipartFile multipartFile, @PathVariable("channelId") Long channelId) throws IOException, URISyntaxException {
        log.info("Persisting new file: {}", multipartFile.getOriginalFilename());
        Video video = videosService.create(multipartFile, channelId);

        log.info("Persisted {} with id: {}", multipartFile.getOriginalFilename(), video.getId());
        return ResponseEntity.created(new URI("http://localhost:8081/videos/" + video.getId())).build();
    }

    @Transactional
    @ApiOperation(value = "This route fetches a Video by it's ID, if it exists on the database")
    @GetMapping(value = "/videos/{id}")
    public void load(@PathVariable("id") long id, HttpServletResponse response) throws IOException, EntityNotFoundException {
        try {
            log.info("Loading file id: {}", id);
            Video video = videosService.getById(id);

            response.addHeader("Content-Disposition", "attachment; filename=" + video.getTitle());
            IOUtils.copy(new ByteArrayInputStream(video.getPayload()), response.getOutputStream());
            log.info("Sent file id: {}", id);
        } catch (EntityNotFoundException entityNotFoundException) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    @ApiOperation(value = "This route fetches all Videos from the database")
    @GetMapping(path = "/videos")
    public ResponseEntity<List<Video>> getVideos() {
        return new ResponseEntity<>(videosService.getVideos(), HttpStatus.OK);
    }
}
