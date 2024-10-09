package SSOP.ssop.utils;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.domain.card.CardFan;
import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
import SSOP.ssop.dto.card.request.CardFanUpdateRequest;
import SSOP.ssop.dto.card.request.CardStudentUpdateRequest;
import SSOP.ssop.dto.card.request.CardUpdateRequest;
import SSOP.ssop.dto.card.request.CardWorkerUpdateRequest;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.Card.CardFanRepository;
import SSOP.ssop.repository.Card.CardStudentRepository;
import SSOP.ssop.repository.Card.CardWorkerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Component
public class CardUtils {
    private final CardStudentRepository cardStudentRepository;
    private final CardWorkerRepository cardWorkerRepository;
    private final CardFanRepository cardFanRepository;

    public CardUtils(CardStudentRepository cardStudentRepository,
                     CardWorkerRepository cardWorkerRepository,
                     CardFanRepository cardFanRepository) {
        this.cardStudentRepository = cardStudentRepository;
        this.cardWorkerRepository = cardWorkerRepository;
        this.cardFanRepository = cardFanRepository;
    }

    public CardResponse createCardResponse(Card card, boolean isNotMyCard, LocalDateTime time) {
        CardStudent cardStudent = null;
        CardWorker cardWorker = null;
        CardFan cardFan = null;

        switch (card.getCard_template()) {
            case "student":
            case "studentSchool":           // 초,중,고등학생
            case "studentUniv":             // 대학(원)생
                cardStudent = cardStudentRepository.findByCard_CardId(card.getCardId());
                break;
            case "worker":
                cardWorker = cardWorkerRepository.findByCard_CardId(card.getCardId());
                break;
            case "fan":
                cardFan = cardFanRepository.findByCard_CardId(card.getCardId());
                break;
            case "free":
                cardStudent = cardStudentRepository.findByCard_CardId(card.getCardId());
                cardWorker = cardWorkerRepository.findByCard_CardId(card.getCardId());
                cardFan = cardFanRepository.findByCard_CardId(card.getCardId());
                break;
        }
        return new CardResponse(card, cardStudent, cardWorker, cardFan, isNotMyCard, time);
    }

    // null값 확인
    public <T> void updateFieldIfNotNull(T value, Consumer<T> updater) {
        if (value != null) {
            updater.accept(value);
        }
    }

    // 템플릿별 카드 수정
    public void updateTemplateSpecificFields(Card card, CardUpdateRequest request) {
        Long cardId = card.getCardId();

        switch (card.getCard_template()) {
            case "student":
            case "studentSchool":           // 초,중,고등학생
            case "studentUniv":             // 대학(원)생
                if (request.getStudent() != null) {
                    CardStudent cardStudent = cardStudentRepository.findByCard_CardId(cardId);
                    if (cardStudent != null) {
                        updateStudentCard(cardStudent, request.getStudent());
                    } else {
                        throw new IllegalArgumentException("CardStudent entity not found for this card.");
                    }
                }
                break;
            case "worker":
                if (request.getWorker() != null) {
                    CardWorker cardWorker = cardWorkerRepository.findByCard_CardId(cardId);
                    if (cardWorker != null) {
                        updateWorkerCard(cardWorker, request.getWorker());
                    } else {
                        throw new IllegalArgumentException("CardWorker entity not found for this card.");
                    }
                }
                break;
            case "fan":
                if (request.getFan() != null) {
                    CardFan cardFan = cardFanRepository.findByCard_CardId(cardId);
                    if (cardFan != null) {
                        updateFanCard(cardFan, request.getFan());
                    } else {
                        throw new IllegalArgumentException("CardFan entity not found for this card");
                    }
                }
                break;
            case "free":
                if (request.getStudent() != null) {
                    CardStudent cardStudent = cardStudentRepository.findByCard_CardId(cardId);
                    if (cardStudent != null) {
                        updateStudentCard(cardStudent, request.getStudent());
                    } else {
                        throw new IllegalArgumentException("CardStudent entity not found for this card.");
                    }
                }
                if (request.getWorker() != null) {
                    CardWorker cardWorker = cardWorkerRepository.findByCard_CardId(cardId);
                    if (cardWorker != null) {
                        updateWorkerCard(cardWorker, request.getWorker());
                    } else {
                        throw new IllegalArgumentException("CardWorker entity not found for this card.");
                    }
                }
                if (request.getFan() != null) {
                    CardFan cardFan = cardFanRepository.findByCard_CardId(cardId);
                    if (cardFan != null) {
                        updateFanCard(cardFan, request.getFan());
                    } else {
                        throw new IllegalArgumentException("CardFan entity not found for this card");
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("지정된 템플릿이 없습니다.");
        }
    }

    private void updateStudentCard(CardStudent card, CardStudentUpdateRequest studentRequest) {
        updateFieldIfNotNull(studentRequest.getCard_student_school(), card::setCard_student_school);
        updateFieldIfNotNull(studentRequest.getCard_student_grade(), card::setCard_student_grade);
        updateFieldIfNotNull(studentRequest.getCard_student_major(), card::setCard_student_major);
        updateFieldIfNotNull(studentRequest.getCard_student_id(), card::setCard_student_id);
        updateFieldIfNotNull(studentRequest.getCard_student_club(), card::setCard_student_club);
        updateFieldIfNotNull(studentRequest.getCard_student_role(), card::setCard_student_role);
        updateFieldIfNotNull(studentRequest.getCard_student_status(), card::setCard_student_status);
    }

    private void updateWorkerCard(CardWorker card, CardWorkerUpdateRequest workerRequest) {
        updateFieldIfNotNull(workerRequest.getCard_worker_company(), card::setCard_worker_company);
        updateFieldIfNotNull(workerRequest.getCard_worker_job(), card::setCard_worker_job);
        updateFieldIfNotNull(workerRequest.getCard_worker_position(), card::setCard_worker_position);
        updateFieldIfNotNull(workerRequest.getCard_worker_department(), card::setCard_worker_department);
    }

    private void updateFanCard(CardFan card, CardFanUpdateRequest fanRequest) {
        updateFieldIfNotNull(fanRequest.getCard_fan_genre(), card::setCard_fan_genre);
        updateFieldIfNotNull(fanRequest.getCard_fan_first(), card::setCard_fan_first);
        updateFieldIfNotNull(fanRequest.getCard_fan_second(), card::setCard_fan_second);
        updateFieldIfNotNull(fanRequest.getCard_fan_reason(), card::setCard_fan_reason);
    }

    // 존재하는 경우 삭제
    public <T> void deleteIfExists(String template, String type, JpaRepository<T, Long> repository, long cardId) {
        if (template.equals(type) || template.equals("free")) {
            if (repository.existsById(cardId)) {
                repository.deleteById(cardId);
            }
        }
    }

}
