package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagNameFilter extends Filter {
  public TagNameFilter(String param) {
    super(param);
  }

  @Override
  List<CertificateDto> filter(List<CertificateDto> soughtList) {
    String[] paramMass = param.split(" ");
    List<String> paramsList = (Arrays.asList(paramMass));
    soughtList =
        soughtList.stream()
            .filter(
                certificate ->
                    certificate.getTags().stream()
                        .map(TagDto::getName)
                        .collect(Collectors.toList())
                        .containsAll(paramsList))
            .collect(Collectors.toList());
    return next == null
            ? soughtList
            : next.filter(soughtList);
  }
}
