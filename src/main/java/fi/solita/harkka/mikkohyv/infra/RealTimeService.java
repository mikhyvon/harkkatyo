package fi.solita.harkka.mikkohyv.infra;

import fi.solita.harkka.mikkohyv.domain.shared.TimeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RealTimeService implements TimeService {
    @Override
    public Date now() {
        return new Date();
    }
}
