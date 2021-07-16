package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CertificateConverter{

  public static CertificateDto convertModelToDto(Certificate certificate) {
    Set<TagDto> tags =
        certificate.getTags().stream()
            .map(TagConverter::convertModelToDto)
            .collect(Collectors.toSet());
    return new CertificateDto(
        certificate.getId(),
        certificate.getName(),
        certificate.getDescription(),
        certificate.getPrice(),
        certificate.getDuration(),
        certificate.getCreateDate(),
        certificate.getLastUpdateDate(),
        tags);
  }

  public static Certificate convertDtoToModel(CertificateDto certificate) {
    Set<Tag> tags = null;
    if (certificate.getTags() != null) {
      tags =
          certificate.getTags().stream()
              .map(TagConverter::convertDtoToModel)
              .collect(Collectors.toSet());
    }
    return new Certificate(
        certificate.getId(),
        certificate.getName(),
        certificate.getDescription(),
        certificate.getPrice(),
        certificate.getDuration(),
        certificate.getCreateDate(),
        certificate.getLastUpdateDate(),
        tags);
  }
}
