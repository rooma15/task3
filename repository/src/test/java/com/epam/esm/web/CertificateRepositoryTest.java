package com.epam.esm.web;


import com.epam.esm.config.TestConfig;
import com.epam.esm.model.Certificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestConfig.class})
public class CertificateRepositoryTest {

    @Autowired
    private CertificateRepository certificateRepository;


    Certificate cert1 =
            new Certificate(
                    55,
                    "roman",
                    "hello roman",
                    BigDecimal.valueOf(10.0),
                    1,
                    LocalDateTime.parse("2021-06-11T02:17"),
                    LocalDateTime.parse("2021-06-11T19:57:12"));
    Certificate cert2 =
            new Certificate(
                    56,
                    "andrew",
                    "hello andrew",
                    BigDecimal.valueOf(2.0),
                    5,
                    LocalDateTime.parse("2021-06-11T02:17"),
                    LocalDateTime.parse("2021-06-11T02:17:23"));

    @Test
    public void createCertificateTest(){
        int id = certificateRepository.create(cert1);
        Certificate expCertificate = certificateRepository.findOne(id);
        assertEquals(expCertificate.getName(), cert1.getName());
        certificateRepository.delete(id);
    }


    @Test
    public void findAllTest(){
        List<Certificate> certificates = new ArrayList<>();
        certificates.add(cert1);
        certificates.add(cert2);
        assertEquals(certificates, certificateRepository.findAll());
    }

    @Test
    public void findByIdTest(){
        assertEquals(cert1, certificateRepository.findOne(55));
    }

    @Test
    public void deleteCertificateSuccessTest(){
        int id = certificateRepository.create(cert1);
        certificateRepository.delete(id);
        assertThrows(EmptyResultDataAccessException.class, () -> certificateRepository.findOne(id));
    }

    @Test
    public void updateCertificateTest(){
        Certificate certExpected = new Certificate(
                56,
                "dima",
                "hello andrew",
                BigDecimal.valueOf(2.0),
                5,
                LocalDateTime.parse("2021-06-11T02:17"),
                LocalDateTime.parse("2021-06-11T02:17:23"));
        certificateRepository.update(certExpected);
        Certificate certUpdated = certificateRepository.findOne(56);
        assertEquals(certExpected, certUpdated);
    }

}
