package pl.sensilabs.praktyki.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sensilabs.praktyki.exceptions.PublisherNotFoundException;
import pl.sensilabs.praktyki.mappers.PublisherMapper;
import pl.sensilabs.praktyki.repositories.PublisherRepository;
import pl.sensilabs.praktyki.requests.PublisherRequest;
import pl.sensilabs.praktyki.responses.PublisherResponse;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public Iterable<PublisherResponse> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(PublisherMapper::toResponse)
                .collect(Collectors.toSet());
    }

    public PublisherResponse findById(UUID publisherId) {
        return publisherRepository.findById(publisherId)
                .map(PublisherMapper::toResponse)
                .orElseThrow(() -> new PublisherNotFoundException(publisherId));
    }

    @Transactional
    public void deleteById(UUID publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new PublisherNotFoundException(publisherId);
        }

        publisherRepository.deleteById(publisherId);
    }

    @Transactional
    public PublisherResponse create(PublisherRequest request) {
        return PublisherMapper.toResponse(publisherRepository.save(PublisherMapper.toEntity(request)));
    }
}
