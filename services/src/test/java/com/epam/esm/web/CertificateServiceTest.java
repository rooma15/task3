/*
package com.epam.esm.web;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.validator.impl.CertificateValidator;
import com.epam.esm.web.impl.CertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CertificateServiceTest {

  @Mock private CertificateRepository certificateRepository;

  @Mock private TagService tagService;

  @Mock private CertificateValidator certificateValidator;

  @InjectMocks private CertificateServiceImpl certificateService;
  private Tag biologyTag = new Tag(38, "biology");
  private Tag chemistryTag = new Tag(37, "chemistry");
  private Tag CsharpTag = new Tag(41, "Csharp");
  private Tag dfgTag = new Tag(43, "dfg");

  Certificate cert1 =
      new Certificate(
          55,
          "roman",
          "hello roman",
          BigDecimal.valueOf(10.00),
          1,
          LocalDateTime.parse("2021-06-11T02:17"),
          LocalDateTime.parse("2021-06-11T19:57:12"));
  Certificate cert2 =
      new Certificate(
          56,
          "andrew",
          "hello andrew",
          BigDecimal.valueOf(2.00),
          5,
          LocalDateTime.parse("2021-06-11T02:17"),
          LocalDateTime.parse("2021-06-11T02:17:23"));

  private List<CertificateDto> certificates;
  private List<Certificate> certificateModels;

  @Test
  public void findByTagNameTest() {
    List<CertificateDto> certificates = new ArrayList<>();
    List<Certificate> certificateModels = new ArrayList<>();
    List<TagDto> tags = new ArrayList<>();
    when(tagService.getByTagName("biology")).thenReturn(TagConverter.convertModelToDto(biologyTag));
    certificateModels.add(cert2);
    when(certificateRepository.findCertificatesByTagId(anyInt())).thenReturn(certificateModels);
    tags.add(TagConverter.convertModelToDto(biologyTag));
    tags.add(TagConverter.convertModelToDto(chemistryTag));
    when(tagService.getTagsByCertificateId( anyInt())).thenReturn(tags);
    certificates.add(CertificateConverter.convertModelToDto(cert2, tags));
    assertEquals(certificates, certificateService.getByTagName("biology"));
  }

  @Test
  public void findAllTest() {
    List<Certificate> certificates = new ArrayList<>();
    List<TagDto> tags1 = new ArrayList<>();
    List<TagDto> tags2 = new ArrayList<>();
    tags1.add(TagConverter.convertModelToDto(CsharpTag));
    tags1.add(TagConverter.convertModelToDto(dfgTag));
    tags2.add(TagConverter.convertModelToDto(biologyTag));
    tags2.add(TagConverter.convertModelToDto(chemistryTag));
    certificates.add(cert1);
    certificates.add(cert2);
    List<CertificateDto> certificateDtos = new ArrayList<>();
    certificateDtos.add(CertificateConverter.convertModelToDto(cert1, tags1));
    certificateDtos.add(CertificateConverter.convertModelToDto(cert2, tags2));
    when(certificateRepository.findAll()).thenReturn(certificates);
    when(tagService.getTagsByCertificateId(anyInt())).thenReturn(tags1).thenReturn(tags2);
    assertEquals(certificateService.getAll(), certificateDtos);
  }

  @Test
  public void findByIdTest() {
    List<TagDto> tags = new ArrayList<>();
    tags.add(TagConverter.convertModelToDto(CsharpTag));
    tags.add(TagConverter.convertModelToDto(dfgTag));
    when(certificateRepository.findOne(anyInt())).thenReturn(cert1);
    when(tagService.getTagsByCertificateId(anyInt())).thenReturn(tags);
    assertEquals(
        CertificateConverter.convertModelToDto(cert1, tags), certificateService.getById(55));
  }

  @Test
  public void deleteFromTableTest() {
    when(certificateRepository.findOne(anyInt())).thenReturn(cert1);
    when(certificateRepository.deleteLink(anyInt())).thenReturn(1);
    when((certificateRepository.delete(anyInt()))).thenReturn(1);
    assertEquals(1, certificateService.delete(55));
  }
}
*/
