package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByDateNameFilter extends Filter {
  private String dateParam;
  private String nameParam;
  private final Comparator<CertificateDto> comparator =
      (cert1, cert2) -> {
        LocalDateTime date1 = cert1.getCreateDate();
        LocalDateTime date2 = cert2.getCreateDate();
        int compareResult = date1.compareTo(date2);
        if (compareResult == 0) {
          int compareNameResult = cert1.getName().compareTo(cert2.getName());
          return nameParam.equalsIgnoreCase("desc") ? -compareNameResult : compareNameResult;
        } else {
          return dateParam.equalsIgnoreCase("desc") ? -compareResult : compareResult;
        }
      };

  public SortByDateNameFilter(String dateParam, String nameParam) {
    super();
    this.dateParam = dateParam;
    this.nameParam = nameParam;
  }

  @Override
  List<CertificateDto> filter(List<CertificateDto> soughtList) {
    return soughtList.stream().sorted(comparator).collect(Collectors.toList());
  }
}
