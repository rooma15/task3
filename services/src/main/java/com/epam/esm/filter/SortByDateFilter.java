package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByDateFilter extends Filter {

  private final Comparator<CertificateDto> comparator =
      (cert1, cert2) -> {
        LocalDateTime date1 = cert1.getCreateDate();
        LocalDateTime date2 = cert2.getCreateDate();
        int compareResult = date1.compareTo(date2);
        return param.equalsIgnoreCase("desc")
                ? -compareResult
                : compareResult;
      };

  public SortByDateFilter(String param) {
    super(param);
  }

  @Override
  List<CertificateDto> filter(List<CertificateDto> soughtList) {

    return soughtList.stream()
            .sorted(comparator)
            .collect(Collectors.toList());
  }
}
