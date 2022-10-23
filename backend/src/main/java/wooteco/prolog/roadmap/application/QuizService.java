package wooteco.prolog.roadmap.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.Quiz;
import wooteco.prolog.roadmap.exception.KeywordOrderException;
import wooteco.prolog.roadmap.repository.KeywordRepository;
import wooteco.prolog.roadmap.repository.QuizRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class QuizService {

    private final KeywordRepository keywordRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public Long createQuiz(Long keywordId, QuizRequest quizRequest) {
        final Keyword keyword = keywordRepository.findById(keywordId)
            .orElseThrow(KeywordOrderException::new);

        return quizRepository.save(new Quiz(keyword, quizRequest.getQuestion())).getId();
    }
}