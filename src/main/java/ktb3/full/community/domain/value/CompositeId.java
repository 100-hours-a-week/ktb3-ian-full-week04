package ktb3.full.community.domain.value;

import lombok.Value;

@Value
public class CompositeId<FID, SID> {
    FID firstId;
    SID secondId;
}
