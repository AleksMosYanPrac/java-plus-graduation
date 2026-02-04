package ru.practicum.ewm.core.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;
import ru.practicum.ewm.core.api.contracts.compilations.dto.CompilationDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.core.api.contracts.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.core.compilations.interfaces.CompilationMapper;
import ru.practicum.ewm.core.compilations.interfaces.CompilationService;
import ru.practicum.ewm.core.events.Event;
import ru.practicum.ewm.core.events.EventRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper mapper;
    private final EventRepository eventRepository;

    @Override
    public List<CompilationDto> findAllByPinned(Boolean pinned, int from, int size) {
        log.info("Find Compilations by pinned:{} from:{} size:{}", pinned, from, size);
        PageRequest page = PageRequest.of(from, size);
        if (pinned != null && pinned) {
            return compilationRepository.findAllByPinned(pinned, page)
                    .stream().map(mapper::toDto).toList();
        } else {
            return compilationRepository.findAll(page)
                    .stream().map(mapper::toDto).toList();
        }
    }

    @Override
    public CompilationDto getById(Long compId) throws NotFoundException {
        log.info("Find Compilation by Id:{}", compId);
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found with ID:" + compId));
        return mapper.toDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto createNewCompilation(NewCompilationDto compilation) throws DataIntegrityViolation {
        log.info("Create new Compilation with title:{}", compilation.getTitle());
        if (compilationRepository.existsByTitle(compilation.getTitle())) {
            throw new DataIntegrityViolation("Compilation already exists with TITLE:" + compilation.getTitle());
        }
        List<Event> eventList = eventRepository.findAllById(compilation.getEvents());
        Compilation newCompilation = new Compilation();
        newCompilation.setEvents(eventList);
        newCompilation.setPinned(compilation.getPinned());
        newCompilation.setTitle(compilation.getTitle());

        return mapper.toDto(compilationRepository.save(newCompilation));
    }

    @Override
    @Transactional
    public void deleteCompilationById(Long compId) throws NotFoundException {
        log.info("Delete Compilation with id:{}", compId);
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found with ID:" + compId));
        compilationRepository.delete(compilation);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request) throws NotFoundException {
        log.info("Update Compilation with id:{} and newTitle:{}", compId, request.getTitle());
        Compilation updatedCompilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found with ID:" + compId));
        if (request.getPinned() != null) {
            updatedCompilation.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            updatedCompilation.setTitle(request.getTitle());
        }
        if (request.getEvents() != null) {
            updatedCompilation.setEvents(eventRepository.findAllById(request.getEvents()));
        }
        return mapper.toDto(compilationRepository.save(updatedCompilation));
    }
}