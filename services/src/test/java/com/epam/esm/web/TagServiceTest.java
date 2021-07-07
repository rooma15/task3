/*
package com.epam.esm.web;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.validator.impl.TagValidator;
import com.epam.esm.web.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagValidator validator;

    @InjectMocks
    private TagServiceImpl tagService;

    private Tag biologyTag = new Tag(38, "biology");
    private Tag chemistryTag = new Tag(37, "chemistry");
    private Tag CsharpTag = new Tag(41, "Csharp");
    private Tag dfgTag = new Tag(43, "dfg");


    @Test
    public void getAllTagsTest(){
        List<Tag> tags = new ArrayList<>();
        tags.add(biologyTag);
        tags.add(chemistryTag);
        tags.add(CsharpTag);
        tags.add(dfgTag);
        List<TagDto> tagDtos = new ArrayList<>();
        tags.forEach(tag -> tagDtos.add(TagConverter.convertModelToDto(tag)));
        when(tagRepository.findAll()).thenReturn(tags);
        assertEquals(tagDtos, tagService.getAll());
    }


    @Test
    public void deleteTag_success(){
        when(tagRepository.findOne(anyInt())).thenReturn(CsharpTag);
        when(tagRepository.isTagConnected(anyInt())).thenReturn(false);
        when(tagRepository.delete(anyInt())).thenReturn(1);
        assertEquals(1, tagService.delete(41));
    }

    @Test
    public void getByIdTest(){
        when(tagRepository.findOne(anyInt())).thenReturn(biologyTag);
        assertEquals(TagConverter.convertModelToDto(biologyTag), tagService.getById(38));
    }

    @Test
    public void getByNameTest(){
        when(tagRepository.findByName(anyString())).thenReturn(biologyTag);
        assertEquals(TagConverter.convertModelToDto(biologyTag), tagService.getByTagName("biology"));
    }


    @Test
    public void getTagsByCertificateIdTest(){
        List<Tag> tags = new ArrayList<>();
        tags.add(biologyTag);
        tags.add(chemistryTag);
        when(tagRepository.findTagsByCertificateId(anyInt())).thenReturn(tags);
        assertEquals(tags.stream()
                .map(TagConverter::convertModelToDto)
                .collect(Collectors.toList()), tagService.getTagsByCertificateId(56));
    }

}
*/
