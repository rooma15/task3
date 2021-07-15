package com.epam.esm.web.contoller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.web.TagService;
import com.epam.esm.web.hateoas.HateoasHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

  private final TagService tagService;
  private final HateoasHelper hateoasHelper;
  /**
   * Instantiates a new Tag controller.
   *
   * @param tagService the tag service
   */
  @Autowired
  public TagController(TagService tagService, HateoasHelper hateoasHelper) {
    this.tagService = tagService;
    this.hateoasHelper = hateoasHelper;
  }

  /**
   * Gets all tags.
   *
   * @return the all tags
   */
  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public CollectionModel<TagDto> getAllTags(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "5") int size) {
    List<TagDto> tags = tagService.getPaginated(page, size);
    return hateoasHelper.makeTagLinks(new HashSet<>(tags));
  }

  /**
   * Gets tag.
   *
   * @param id the id
   * @return the tag
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
  public TagDto getTag(@PathVariable int id) {
    TagDto tag = tagService.getById(id);
    return hateoasHelper.makeTagLinks(tag);
  }

  /**
   * Create tag tag dto.
   *
   * @param tag the tag
   * @return the tag dto
   */
  @RequestMapping(
      method = RequestMethod.POST,
      produces = "application/json",
      consumes = "application/json")
  public TagDto createTag(@RequestBody TagDto tag) {
    TagDto newTag = tagService.save(tag);
    return hateoasHelper.makeTagLinks(newTag);
  }

  /**
   * Delete tag.
   *
   * @param id the id
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTag(@PathVariable int id) {
    tagService.delete(id);
  }
}
