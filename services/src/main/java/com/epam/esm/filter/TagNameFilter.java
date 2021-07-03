package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.util.List;
import java.util.stream.Collectors;

public class TagNameFilter extends Filter {
    public TagNameFilter(String param) {
        super(param);
    }

    @Override
    List<CertificateDto> filter(List<CertificateDto> soughtList) {
        soughtList = soughtList
                .stream()
                .filter(certificate -> certificate.getTags()
                        .stream().anyMatch(tag -> tag.getName().contains(param)))
                .collect(Collectors.toList());
        return next == null
                ? soughtList
                : next.filter(soughtList);
    }
}
