package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.IntegrationTest;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class PersonServiceIntegrationTests extends IntegrationTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void verifyCacheIsReturnedAfterFirstCall() {
        personService.getAll();
        verify(personRepository, times(1)).findAll();
        personService.getAll();
        verifyNoMoreInteractions(personRepository);
    }
}
