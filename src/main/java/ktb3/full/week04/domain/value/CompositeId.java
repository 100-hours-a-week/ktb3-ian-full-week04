package ktb3.full.week04.domain.value;

import lombok.Value;

@Value
public class CompositeId<FID, SID> {
    FID firstId;
    SID secondId;
}
