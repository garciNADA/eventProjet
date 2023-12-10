package tn.esprit.eventsproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventServicesImplTests {

    @InjectMocks
    private EventServicesImpl eventServices;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private LogisticsRepository logisticsRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddParticipant() {
        // Create a mock Participant
        Participant participant = new Participant();
        when(participantRepository.save(participant)).thenReturn(participant);

        // Call the service method
        Participant result = eventServices.addParticipant(participant);

        // Verify the result
        assertEquals(participant, result);

        // Verify that the repository method was called
        verify(participantRepository, times(1)).save(participant);
    }

    @Test
    public void testAddAffectEvenParticipant() {
        // Create a mock Event
        Event event = new Event();
        event.setIdEvent(1);

        // Create a mock Participant
        Participant participant = new Participant();
        participant.setIdPart(1);

        when(participantRepository.findById(1)).thenReturn(java.util.Optional.of(participant));
        when(eventRepository.save(event)).thenReturn(event);

        // Call the service method
        Event result = eventServices.addAffectEvenParticipant(event, 1);

        // Verify the result
        assertEquals(event, result);

        // Verify that the repository methods were called
        verify(participantRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testAddAffectEvenParticipantWithSet() {
        // Create a mock Event
        Event event = new Event();
        event.setIdEvent(1);

        // Create a mock Participant with a set of events
        Participant participant = new Participant();
        participant.setIdPart(1);
        participant.setEvents(new HashSet<>());

        Set<Participant> participants = new HashSet<>();
        participants.add(participant);
        event.setParticipants(participants);

        when(participantRepository.findById(1)).thenReturn(java.util.Optional.of(participant));
        when(eventRepository.save(event)).thenReturn(event);

        // Call the service method
        Event result = eventServices.addAffectEvenParticipant(event);

        // Verify the result
        assertEquals(event, result);

        // Verify that the repository methods were called
        verify(participantRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).save(event);
    }

    // Add more tests for other methods as needed
}
