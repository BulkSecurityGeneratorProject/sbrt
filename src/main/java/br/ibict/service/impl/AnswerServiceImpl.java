package br.ibict.service.impl;

import br.ibict.service.AnswerService;
import br.ibict.domain.Answer;
import br.ibict.domain.AnswerSummary;
import br.ibict.repository.AnswerRepository;
import br.ibict.service.dto.AnswerDTO;
import br.ibict.service.mapper.AnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Answer.
 */
@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private final AnswerRepository answerRepository;

    private final AnswerMapper answerMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerMapper answerMapper) {
        this.answerRepository = answerRepository;
        this.answerMapper = answerMapper;
    }

    /**
     * Save a answer.
     *
     * @param answerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnswerDTO save(AnswerDTO answerDTO) {
        log.debug("Request to save Answer : {}", answerDTO);
        Answer answer = answerMapper.toEntity(answerDTO);
        answer = answerRepository.save(answer);
        return answerMapper.toDto(answer);
    }

    /**
     * Get all the answers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnswerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Answers");
        return answerRepository.findAll(pageable)
            .map(answerMapper::toDto);
    }


    /**
     * Get one answer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerDTO> findOne(Long id) {
        log.debug("Request to get Answer : {}", id);
        return answerRepository.findById(id)
            .map(answerMapper::toDto);
    }

    /**
     * Delete the answer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Answer : {}", id);
        answerRepository.deleteById(id);
    }

    @Override
    public Page<AnswerDTO> findAllByLegalEntity(Pageable pageable, Long legalEntityId) {
        log.debug("Request to get all Answers from a legal entity");
        return answerRepository.findByLegalEntityId(pageable, legalEntityId)
            .map(answerMapper::toDto);
    }

    @Override
    public Page<AnswerDTO> findAllByCnae(Pageable pageable, String cnae) {
        log.debug("Request to get all Answers by CNAE");
        return answerRepository.findByCnaeCod(pageable, cnae)
            .map(answerMapper::toDto);
    }

    @Override
    @Transactional
    public Optional<AnswerDTO> incrementAnswerSeen(Optional<AnswerDTO> answerDTO) {
        if (answerDTO.isPresent()) {
            Answer answer = answerMapper.toEntity(answerDTO.get());
            answer.incrementSeen();
            answerRepository.save(answer);
            answerDTO = Optional.of(answerMapper.toDto(answer));
        }
        return answerDTO;
    }

    @Override
    public Page<AnswerSummary> getSummaries(Pageable pageable) {
        return answerRepository.getSummaries(pageable);
    }
}
