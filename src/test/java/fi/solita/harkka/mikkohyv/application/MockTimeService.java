package fi.solita.harkka.mikkohyv.application;

import fi.solita.harkka.mikkohyv.domain.shared.TimeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MockTimeService implements TimeService {
    private static final Date MOCK_NOW = new Date(2017,6,6,6,20,22);

    @Override
    public Date now() {
        return MOCK_NOW;
    }
}
