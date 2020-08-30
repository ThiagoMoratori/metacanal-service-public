package br.com.metacanal.controller;

import br.com.metacanal.entity.Channel;
import br.com.metacanal.service.ChannelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Check localhost:8081/swagger-ui.html for the Swagger Documentation!
 */

@Api(description = "Channel Controller")
@RestController
public class ChannelController {

    private final ChannelService channelService;

    @Autowired
    private ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @ApiOperation(value = "This route will create a new Channel")
    @PostMapping(path = "/channel", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Channel> createChannel(@RequestBody JsonNode channel) throws JsonProcessingException {
        return new ResponseEntity<>(channelService.create(channel), HttpStatus.OK);
    }

    @ApiOperation(value = "This route deletes a Channel by ID")
    @DeleteMapping(path = "/channels/{id}")
    public ResponseEntity<?> deleteChannelById(@PathVariable Long id) {
        channelService.deleteChannel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "This route patches a Channel with a new name")
    @PatchMapping(path = "/channel/{id}")
    public ResponseEntity<?> patchChannel(@RequestBody Map<String, String> updates, @PathVariable("id") Long id) {
        return new ResponseEntity<>(channelService.updateName(updates, id), HttpStatus.OK);
    }

    @ApiOperation(value = "This route fetches all Channels from the database")
    @GetMapping(path = "/channels")
    public ResponseEntity<List<Channel>> getChannels() {
        return new ResponseEntity<>(channelService.getChannels(), HttpStatus.OK);
    }

    @ApiOperation(value = "This route fetches a Channel by it's name")
    @GetMapping(path = "/channel/name/{name}")
    public ResponseEntity<Channel> getChannelByName(@PathVariable String name) {
        return new ResponseEntity<>(channelService.getByName(name), HttpStatus.OK);
    }

    @ApiOperation(value = "This route fetches a Channel by it's ID, if it exists on the database")
    @GetMapping(path = "/channel/id/{id}")
    public ResponseEntity<Optional<Channel>> getChannelById(@PathVariable Long id) {
        return new ResponseEntity<>(channelService.getById(id), HttpStatus.OK);
    }
}
