package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByNameFilter extends Filter{
    public SortByNameFilter(String param) {
        super(param);
    }

    private final Comparator<CertificateDto> comparator =
            (cert1, cert2) -> {
        String name1 = cert1.getName();
        String name2 = cert2.getName();
        int compareResult = name1.compareTo(name2);
        return param.equalsIgnoreCase("desc")
                ? -compareResult
                : compareResult;
    };

    @Override
    List<CertificateDto> filter(List<CertificateDto> soughtList) {
        return soughtList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
